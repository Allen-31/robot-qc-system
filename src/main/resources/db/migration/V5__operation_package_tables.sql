-- operation-package management
create table if not exists operation_package (
    id bigint primary key,
    name varchar(255) not null,
    type varchar(64) not null,
    target_parts jsonb not null default '[]',
    description varchar(1024),
    size_bytes bigint not null,
    md5 varchar(64) not null,
    uploader varchar(64),
    uploaded_at timestamp not null,
    storage_path varchar(512) not null
);
create index if not exists idx_operation_package_type on operation_package(type);
create index if not exists idx_operation_package_md5 on operation_package(md5);
create index if not exists idx_operation_package_uploaded_at on operation_package(uploaded_at desc);
