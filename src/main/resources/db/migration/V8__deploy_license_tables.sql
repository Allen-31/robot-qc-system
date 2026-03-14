-- deploy-license management table
create table if not exists deploy_license (
    id bigint primary key,
    name varchar(255) not null,
    file_name varchar(255) not null,
    storage_path varchar(512) not null,
    size_bytes bigint not null,
    md5 varchar(64),
    effective_at timestamp,
    expire_at timestamp,
    applicant varchar(64),
    status varchar(32) not null default 'active',
    imported_at timestamp not null
);
create index if not exists idx_deploy_license_status on deploy_license(status);
create index if not exists idx_deploy_license_imported_at on deploy_license(imported_at desc);

