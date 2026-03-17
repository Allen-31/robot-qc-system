-- deploy task flow templates
create table if not exists deploy_task_flow_template (
    code varchar(64) primary key,
    name varchar(128) not null,
    enabled boolean not null default true,
    priority int not null default 1,
    created_by varchar(64),
    created_at timestamp not null,
    updated_by varchar(64),
    updated_at timestamp not null
);
create index if not exists idx_deploy_task_flow_template_enabled on deploy_task_flow_template(enabled);
create index if not exists idx_deploy_task_flow_template_priority on deploy_task_flow_template(priority);

-- deploy task templates
create table if not exists deploy_task_template (
    code varchar(64) primary key,
    name varchar(128) not null,
    enabled boolean not null default true,
    created_by varchar(64),
    created_at timestamp not null,
    updated_by varchar(64),
    updated_at timestamp not null
);
create index if not exists idx_deploy_task_template_enabled on deploy_task_template(enabled);
create index if not exists idx_deploy_task_template_created_at on deploy_task_template(created_at desc);