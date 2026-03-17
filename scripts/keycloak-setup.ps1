param(
    [string]$KeycloakUrl = "http://localhost:8081",
    [string]$AdminUsername = "admin",
    [string]$AdminPassword = "",
    [string]$RealmName = "robot-qc",
    [string]$ClientId = "robot-qc-frontend",
    [string]$TestUsername = "admin",
    [string]$TestUserPassword = "admin123",
    [string[]]$WebOrigins = @("http://localhost:5173", "http://127.0.0.1:5173"),
    [string[]]$RedirectUris = @("http://localhost:5173/*", "http://127.0.0.1:5173/*")
)

$ErrorActionPreference = "Stop"

if ([string]::IsNullOrWhiteSpace($AdminPassword)) {
    $AdminPassword = $env:KEYCLOAK_ADMIN_PASSWORD
}

if ([string]::IsNullOrWhiteSpace($AdminPassword)) {
    throw "Admin password is required. Pass -AdminPassword or set KEYCLOAK_ADMIN_PASSWORD."
}

$base = $KeycloakUrl.TrimEnd("/")
$tokenUrl = "$base/realms/master/protocol/openid-connect/token"

Write-Host "Keycloak URL: $base"
Write-Host "Target realm: $RealmName"

function Get-AdminToken {
    param(
        [string]$Url,
        [string]$Username,
        [string]$Password
    )

    $body = @{
        grant_type = "password"
        client_id  = "admin-cli"
        username   = $Username
        password   = $Password
    }

    $resp = Invoke-RestMethod -Method Post -Uri $Url -Body $body -ContentType "application/x-www-form-urlencoded"
    if (-not $resp.access_token) {
        throw "Failed to get admin token."
    }
    return $resp.access_token
}

function Get-Headers {
    param([string]$Token)
    return @{
        Authorization = "Bearer $Token"
        "Content-Type" = "application/json"
    }
}

function Ensure-Realm {
    param(
        [string]$BaseUrl,
        [hashtable]$Headers,
        [string]$Name
    )

    $realmUrl = "$BaseUrl/admin/realms/$Name"
    try {
        Invoke-RestMethod -Method Get -Uri $realmUrl -Headers $Headers | Out-Null
        Write-Host "Realm already exists: $Name"
    } catch {
        $payload = @{
            realm   = $Name
            enabled = $true
        } | ConvertTo-Json
        Invoke-RestMethod -Method Post -Uri "$BaseUrl/admin/realms" -Headers $Headers -Body $payload | Out-Null
        Write-Host "Realm created: $Name"
    }
}

function Get-Client {
    param(
        [string]$BaseUrl,
        [hashtable]$Headers,
        [string]$Realm,
        [string]$ClientIdValue
    )

    $url = "$BaseUrl/admin/realms/$Realm/clients?clientId=$ClientIdValue"
    $clients = Invoke-RestMethod -Method Get -Uri $url -Headers $Headers
    if ($clients -and $clients.Count -gt 0) {
        return $clients[0]
    }
    return $null
}

function Ensure-Client {
    param(
        [string]$BaseUrl,
        [hashtable]$Headers,
        [string]$Realm,
        [string]$ClientIdValue,
        [string[]]$Origins,
        [string[]]$Uris
    )

    $client = Get-Client -BaseUrl $BaseUrl -Headers $Headers -Realm $Realm -ClientIdValue $ClientIdValue
    $clientRep = @{
        clientId = $ClientIdValue
        enabled = $true
        protocol = "openid-connect"
        publicClient = $true
        directAccessGrantsEnabled = $true
        standardFlowEnabled = $true
        serviceAccountsEnabled = $false
        redirectUris = $Uris
        webOrigins = $Origins
        attributes = @{
            "pkce.code.challenge.method" = "S256"
        }
    }

    if ($null -eq $client) {
        $payload = $clientRep | ConvertTo-Json -Depth 6
        Invoke-RestMethod -Method Post -Uri "$BaseUrl/admin/realms/$Realm/clients" -Headers $Headers -Body $payload | Out-Null
        Write-Host "Client created: $ClientIdValue"
    } else {
        $payload = $clientRep | ConvertTo-Json -Depth 6
        Invoke-RestMethod -Method Put -Uri "$BaseUrl/admin/realms/$Realm/clients/$($client.id)" -Headers $Headers -Body $payload | Out-Null
        Write-Host "Client updated: $ClientIdValue"
    }
}

function Ensure-RealmRole {
    param(
        [string]$BaseUrl,
        [hashtable]$Headers,
        [string]$Realm,
        [string]$RoleName
    )

    $url = "$BaseUrl/admin/realms/$Realm/roles/$RoleName"
    try {
        Invoke-RestMethod -Method Get -Uri $url -Headers $Headers | Out-Null
        Write-Host "Role already exists: $RoleName"
    } catch {
        $payload = @{
            name = $RoleName
            description = "Built-in role for Robot QC"
        } | ConvertTo-Json
        Invoke-RestMethod -Method Post -Uri "$BaseUrl/admin/realms/$Realm/roles" -Headers $Headers -Body $payload | Out-Null
        Write-Host "Role created: $RoleName"
    }
}

function Get-UserByUsername {
    param(
        [string]$BaseUrl,
        [hashtable]$Headers,
        [string]$Realm,
        [string]$Username
    )

    $url = "$BaseUrl/admin/realms/$Realm/users?username=$Username&exact=true"
    $users = Invoke-RestMethod -Method Get -Uri $url -Headers $Headers
    if ($users -and $users.Count -gt 0) {
        return $users[0]
    }
    return $null
}

function Ensure-User {
    param(
        [string]$BaseUrl,
        [hashtable]$Headers,
        [string]$Realm,
        [string]$Username
    )

    $user = Get-UserByUsername -BaseUrl $BaseUrl -Headers $Headers -Realm $Realm -Username $Username
    if ($null -ne $user) {
        Write-Host "User already exists: $Username"
        return $user
    }

    $payload = @{
        username = $Username
        enabled = $true
        emailVerified = $true
        firstName = "Admin"
        lastName = "User"
    } | ConvertTo-Json

    Invoke-RestMethod -Method Post -Uri "$BaseUrl/admin/realms/$Realm/users" -Headers $Headers -Body $payload | Out-Null
    Write-Host "User created: $Username"
    return (Get-UserByUsername -BaseUrl $BaseUrl -Headers $Headers -Realm $Realm -Username $Username)
}

function Set-UserPassword {
    param(
        [string]$BaseUrl,
        [hashtable]$Headers,
        [string]$Realm,
        [string]$UserId,
        [string]$Password
    )

    $payload = @{
        type = "password"
        value = $Password
        temporary = $false
    } | ConvertTo-Json

    Invoke-RestMethod -Method Put -Uri "$BaseUrl/admin/realms/$Realm/users/$UserId/reset-password" -Headers $Headers -Body $payload | Out-Null
}

function Ensure-UserRole {
    param(
        [string]$BaseUrl,
        [hashtable]$Headers,
        [string]$Realm,
        [string]$UserId,
        [string]$RoleName
    )

    $role = Invoke-RestMethod -Method Get -Uri "$BaseUrl/admin/realms/$Realm/roles/$RoleName" -Headers $Headers
    $assignBody = @(
        @{
            id = $role.id
            name = $role.name
        }
    ) | ConvertTo-Json

    Invoke-RestMethod -Method Post -Uri "$BaseUrl/admin/realms/$Realm/users/$UserId/role-mappings/realm" -Headers $Headers -Body $assignBody | Out-Null
    Write-Host "Role assigned to user: $RoleName -> $UserId"
}

$token = Get-AdminToken -Url $tokenUrl -Username $AdminUsername -Password $AdminPassword
$headers = Get-Headers -Token $token

Ensure-Realm -BaseUrl $base -Headers $headers -Name $RealmName
Ensure-Client -BaseUrl $base -Headers $headers -Realm $RealmName -ClientIdValue $ClientId -Origins $WebOrigins -Uris $RedirectUris
Ensure-RealmRole -BaseUrl $base -Headers $headers -Realm $RealmName -RoleName "admin"

$user = Ensure-User -BaseUrl $base -Headers $headers -Realm $RealmName -Username $TestUsername
Set-UserPassword -BaseUrl $base -Headers $headers -Realm $RealmName -UserId $user.id -Password $TestUserPassword
Ensure-UserRole -BaseUrl $base -Headers $headers -Realm $RealmName -UserId $user.id -RoleName "admin"

Write-Host ""
Write-Host "Setup completed."
Write-Host "Realm: $RealmName"
Write-Host "Client: $ClientId"
Write-Host "User: $TestUsername"
