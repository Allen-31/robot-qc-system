create table if not exists ops_exception_notification (
    id bigint primary key,
    code varchar(64) not null unique,
    level varchar(16) not null,
    type varchar(128) not null,
    source_system varchar(128),
    issue varchar(1024),
    status varchar(32) not null default 'pending',
    related_task varchar(64),
    robot_code varchar(64),
    created_at timestamp not null,
    updated_at timestamp not null
);
create index if not exists idx_op_exception_notification_level on ops_exception_notification(level);
create index if not exists idx_op_exception_notification_status on ops_exception_notification(status);
create index if not exists idx_op_exception_notification_created_at on ops_exception_notification(created_at desc);

create table if not exists ops_login_log (
    id bigint primary key,
    log_code varchar(64) not null unique,
    user_code varchar(64) not null,
    type varchar(32) not null,
    ip varchar(64),
    created_at timestamp not null
);
create index if not exists idx_op_login_log_user on ops_login_log(user_code);
create index if not exists idx_op_login_log_type on ops_login_log(type);
create index if not exists idx_op_login_log_created_at on ops_login_log(created_at desc);

create table if not exists ops_operation_log (
    id bigint primary key,
    log_code varchar(64) not null unique,
    user_code varchar(64),
    operation_type varchar(128),
    result varchar(32) not null,
    fail_reason varchar(512),
    response_time int,
    ip varchar(64),
    request_info text,
    response_info text,
    created_at timestamp not null
);
create index if not exists idx_op_operation_log_user on ops_operation_log(user_code);
create index if not exists idx_op_operation_log_result on ops_operation_log(result);
create index if not exists idx_op_operation_log_created_at on ops_operation_log(created_at desc);

create table if not exists ops_api_log (
    id bigint primary key,
    log_code varchar(64) not null unique,
    api_name varchar(256),
    call_result varchar(32) not null,
    fail_reason varchar(512),
    response_time int,
    request_info text,
    response_info text,
    created_at timestamp not null
);
create index if not exists idx_op_api_log_result on ops_api_log(call_result);
create index if not exists idx_op_api_log_created_at on ops_api_log(created_at desc);
