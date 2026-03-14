-- operation-task management tables
create table if not exists operation_task (
    id bigint primary key,
    code varchar(64) not null unique,
    external_code varchar(128),
    status varchar(32) not null default 'pending',
    robot_code varchar(64),
    priority int not null default 1,
    description varchar(512),
    created_at timestamp not null,
    ended_at timestamp,
    updated_at timestamp not null
);
create index if not exists idx_operation_task_status on operation_task(status);
create index if not exists idx_operation_task_robot_code on operation_task(robot_code);
create index if not exists idx_operation_task_created_at on operation_task(created_at desc);

