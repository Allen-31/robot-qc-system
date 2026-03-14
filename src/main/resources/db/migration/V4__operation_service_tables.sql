-- operation-service management tables
create table if not exists operation_service (
    id bigint primary key,
    name varchar(128) not null,
    type varchar(64) not null,
    version varchar(64),
    ip varchar(64),
    status varchar(32) not null default 'running',
    cpu_usage numeric(6,2),
    memory_usage numeric(6,2),
    runtime varchar(64),
    created_at timestamp not null,
    updated_at timestamp not null
);
create index if not exists idx_operation_service_type on operation_service(type);
create index if not exists idx_operation_service_status on operation_service(status);

create table if not exists operation_service_log (
    id bigint primary key,
    service_id bigint not null,
    log_name varchar(255) not null,
    type varchar(64),
    content text,
    created_at timestamp not null,
    updated_at timestamp not null,
    foreign key (service_id) references operation_service(id) on delete cascade
);
create index if not exists idx_operation_service_log_service on operation_service_log(service_id);
create index if not exists idx_operation_service_log_created_at on operation_service_log(created_at desc);
