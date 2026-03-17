alter table operation_task
    add column if not exists task_flow_template_code varchar(64);

alter table operation_task
    add column if not exists task_template_code varchar(64);

create index if not exists idx_operation_task_flow_template_code
    on operation_task(task_flow_template_code);

create index if not exists idx_operation_task_template_code
    on operation_task(task_template_code);
