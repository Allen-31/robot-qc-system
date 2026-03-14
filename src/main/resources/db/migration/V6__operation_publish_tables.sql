-- operation-publish management tables
create table if not exists operation_publish (
    id bigint primary key,
    name varchar(255) not null,
    package_name varchar(255) not null,
    target_robots jsonb not null default '[]',
    target_robot_groups jsonb not null default '[]',
    target_robot_types jsonb not null default '[]',
    strategy varchar(32) not null,
    restart_after_upgrade boolean not null default false,
    status varchar(32) not null default 'pending',
    creator varchar(64),
    created_at timestamp not null,
    updated_at timestamp not null,
    completed_at timestamp
);
create index if not exists idx_operation_publish_status on operation_publish(status);
create index if not exists idx_operation_publish_created_at on operation_publish(created_at desc);

create table if not exists operation_publish_device (
    id bigint primary key,
    publish_id bigint not null,
    device_name varchar(128) not null,
    ip varchar(64),
    status varchar(32) not null default 'pending',
    package_name varchar(255) not null,
    version varchar(64),
    progress int not null default 0,
    updated_at timestamp not null,
    completed_at timestamp,
    foreign key (publish_id) references operation_publish(id) on delete cascade
);
create index if not exists idx_operation_publish_device_publish on operation_publish_device(publish_id);
