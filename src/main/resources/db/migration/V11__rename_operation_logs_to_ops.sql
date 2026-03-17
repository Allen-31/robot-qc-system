-- Rename legacy operation_* tables to ops_* if they exist.
-- Safe to run on fresh DB (conditional checks).
do $$
begin
    if exists (select 1 from information_schema.tables where table_name = 'operation_exception_notification') then
        alter table operation_exception_notification rename to ops_exception_notification;
    end if;
    if exists (select 1 from information_schema.tables where table_name = 'operation_login_log') then
        alter table operation_login_log rename to ops_login_log;
    end if;
    if exists (select 1 from information_schema.tables where table_name = 'operation_operation_log') then
        alter table operation_operation_log rename to ops_operation_log;
    end if;
    if exists (select 1 from information_schema.tables where table_name = 'operation_api_log') then
        alter table operation_api_log rename to ops_api_log;
    end if;
end $$;
