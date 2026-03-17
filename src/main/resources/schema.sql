-- 部署-设备（呼叫盒等，3.3.2 设备管理）
create table if not exists deploy_device (
    id bigint primary key,
    code varchar(64) not null unique,
    name varchar(128) not null,
    type varchar(32) not null,
    device_group varchar(128),
    map_code varchar(64),
    status varchar(32) not null default 'offline',
    ip varchar(64),
    created_at timestamp not null,
    updated_at timestamp not null
);

-- 机器人（主键 ID 使用 Snowflake）
create table if not exists robot (
    id bigint primary key,
    robot_code varchar(64) not null unique,
    robot_name varchar(128) not null,
    model varchar(128),
    status varchar(32) not null,
    location varchar(128),
    last_inspection_at timestamp
);

-- 用户与角色（登录、用户管理、角色权限）
create table if not exists sys_user (
    id bigserial primary key,
    code varchar(64) not null unique,
    name varchar(128) not null,
    phone varchar(32),
    email varchar(128),
    password_hash varchar(256) not null,
    status varchar(32) not null default 'enabled',
    last_login_at timestamp,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists sys_role (
    id bigserial primary key,
    code varchar(64) not null unique,
    name varchar(128) not null,
    description varchar(512),
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists sys_user_role (
    user_code varchar(64) not null,
    role_code varchar(64) not null,
    primary key (user_code, role_code),
    foreign key (user_code) references sys_user(code) on delete cascade,
    foreign key (role_code) references sys_role(code) on delete cascade
);

create table if not exists sys_role_permission (
    role_code varchar(64) not null,
    menu_key varchar(128) not null,
    actions jsonb not null default '[]',
    primary key (role_code, menu_key),
    foreign key (role_code) references sys_role(code) on delete cascade
);

-- 菜单（部署配置 - 菜单管理，树形）
create table if not exists sys_menu (
    id bigserial primary key,
    code varchar(64) not null unique,
    name_key varchar(128) not null,
    path varchar(256),
    parent_id bigint,
    sort_order int not null default 0,
    icon varchar(64),
    permission varchar(128),
    status varchar(32) not null default 'enabled',
    created_at timestamp not null,
    updated_at timestamp not null
);

-- 机器人类型/分组/策略等（同事新增）
create table if not exists robot_type (
    id bigserial primary key,
    type_no varchar(64) not null unique,
    type_name varchar(128) not null,
    image_2d varchar(256),
    image_2d_data text,
    parts_count int not null default 0,
    status varchar(32) not null default 'enabled',
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists robot_type_point (
    id bigserial primary key,
    robot_type_id bigint not null,
    part_name varchar(128) not null,
    part_position varchar(128),
    x numeric(8,2) not null,
    y numeric(8,2) not null,
    rotation numeric(8,2) not null,
    remark varchar(512),
    sort_order int not null default 1,
    created_at timestamp not null,
    updated_at timestamp not null,
    foreign key (robot_type_id) references robot_type(id) on delete cascade
);

create table if not exists robot_part (
    id bigserial primary key,
    part_no varchar(64) not null unique,
    name varchar(128) not null,
    position varchar(128),
    type varchar(64) not null,
    model varchar(128),
    vendor varchar(128),
    supplier varchar(128),
    lifecycle varchar(64),
    status varchar(32) not null default 'enabled',
    remark varchar(512),
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists robot_part_param (
    id bigserial primary key,
    part_id bigint not null,
    name varchar(128) not null,
    value varchar(256),
    unit varchar(32),
    range varchar(64),
    sort_order int not null default 1,
    created_at timestamp not null,
    updated_at timestamp not null,
    foreign key (part_id) references robot_part(id) on delete cascade
);

create table if not exists robot_group (
    id bigserial primary key,
    group_no varchar(64) not null unique,
    group_name varchar(128) not null,
    description varchar(512),
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists robot_charge_strategy (
    id bigserial primary key,
    code varchar(64) not null unique,
    name varchar(128) not null,
    status varchar(32) not null default 'enabled',
    robot_type_no varchar(64),
    robot_group_no varchar(64),
    low_battery_threshold int not null,
    min_charge_minutes int not null,
    charge_method varchar(32) not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists robot_homing_strategy (
    id bigserial primary key,
    code varchar(64) not null unique,
    name varchar(128) not null,
    status varchar(32) not null default 'enabled',
    robot_type_no varchar(64),
    robot_group_no varchar(64),
    idle_wait_seconds int not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

-- 质检业务（与《后端接口需求说明-按前端功能反推》对齐）
-- 车间配置：主键 code
create table if not exists qc_workshop (
    code varchar(64) primary key,
    name varchar(128) not null,
    location varchar(256),
    enabled boolean not null default true,
    created_at timestamp not null,
    updated_at timestamp not null
);

-- 工作站（主键 ID 使用 Snowflake）
create table if not exists qc_workstation (
    id bigint primary key,
    code varchar(64) not null unique,
    name varchar(128) not null,
    workshop_code varchar(64),
    status varchar(32) default 'running',
    robot_group varchar(64),
    wire_harness_type varchar(128),
    enabled boolean not null default true,
    created_at timestamp not null,
    updated_at timestamp not null
);

-- 工位/质检台（主键 ID 使用 Snowflake）
create table if not exists qc_station (
    id bigint primary key,
    station_id varchar(64) not null,
    workstation_id bigint not null,
    name varchar(128),
    map_point varchar(128),
    status varchar(32) default 'running',
    call_box_code varchar(64),
    wire_harness_type varchar(128),
    detection_enabled boolean not null default true,
    enabled boolean not null default true,
    created_at timestamp not null,
    updated_at timestamp not null,
    unique (workstation_id, station_id)
);

-- 线束类型（主键 ID 使用 Snowflake）
create table if not exists qc_wire_harness_type (
    id bigint primary key,
    name varchar(128) not null,
    project varchar(128),
    task_type varchar(64),
    planar_structure_file varchar(512),
    three_d_structure_file varchar(512),
    created_at timestamp not null,
    updated_at timestamp not null
);

-- 终端配置（主键 ID 使用 Snowflake）
create table if not exists qc_terminal (
    id bigint primary key,
    code varchar(64) not null unique,
    sn varchar(64) not null unique,
    terminal_type varchar(32) not null,
    terminal_ip varchar(64) not null,
    workstation_id varchar(64),
    bound_station_ids jsonb default '[]',
    online boolean not null default false,
    current_operator varchar(64),
    created_at timestamp not null,
    updated_at timestamp not null
);

-- 工单（主键 ID 使用 Snowflake）
create table if not exists qc_work_order (
    id bigint primary key,
    work_order_no varchar(64) not null unique,
    harness_code varchar(64) not null,
    moving_duration decimal(10,2),
    harness_type varchar(128),
    station_code varchar(64),
    station_id bigint,
    status varchar(32) not null default 'pending',
    quality_result varchar(32) default 'pending',
    task_ids jsonb default '[]',
    detection_duration decimal(10,2),
    created_at timestamp not null,
    started_at timestamp,
    ended_at timestamp,
    defect_type varchar(128),
    defect_description varchar(512),
    video_url varchar(512),
    image_url varchar(512),
    updated_at timestamp not null
);

-- 复检记录（主键 ID 使用 Snowflake）
create table if not exists qc_reinspection_record (
    id bigint primary key,
    reinspection_no varchar(64) not null unique,
    work_order_id bigint not null,
    quality_result varchar(32),
    status varchar(32) not null default 'pending',
    reinspection_result varchar(32) default 'pending',
    defect_type varchar(128),
    reinspection_time timestamp,
    reviewer varchar(64),
    video_url varchar(512),
    image_url varchar(512),
    created_at timestamp not null,
    updated_at timestamp not null
);

-- 场景地图与设备（同事新增）
create table if not exists scene_map (
    id bigserial primary key,
    code varchar(64) not null unique,
    name varchar(128) not null,
    type varchar(32) not null,
    edit_status varchar(32),
    publish_status varchar(32),
    map_file_url varchar(256),
    map_version varchar(64),
    resolution numeric(10,4),
    origin_x numeric(10,4),
    origin_y numeric(10,4),
    edited_by varchar(64),
    edited_at timestamp,
    published_by varchar(64),
    published_at timestamp,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists scene_device (
    id bigserial primary key,
    code varchar(64) not null unique,
    name varchar(128) not null,
    type varchar(64) not null,
    ip varchar(64),
    online_status varchar(32),
    is_abnormal int not null default 0,
    exception_detail varchar(512),
    map_code varchar(64),
    last_online_at timestamp,
    last_heartbeat_at timestamp,
    created_at timestamp not null,
    updated_at timestamp not null
);

drop index if exists idx_robot_charge_strategy_type_no;
create index idx_robot_charge_strategy_type_no on robot_charge_strategy(robot_type_no);
drop index if exists idx_robot_charge_strategy_group_no;
create index idx_robot_charge_strategy_group_no on robot_charge_strategy(robot_group_no);
drop index if exists idx_robot_homing_strategy_type_no;
create index idx_robot_homing_strategy_type_no on robot_homing_strategy(robot_type_no);
drop index if exists idx_robot_homing_strategy_group_no;
create index idx_robot_homing_strategy_group_no on robot_homing_strategy(robot_group_no);
drop index if exists idx_scene_device_map_code;
create index idx_scene_device_map_code on scene_device(map_code);

-- 部署配置-设定-许可证管理
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
create unique index if not exists uk_deploy_license_name on deploy_license(name);
create unique index if not exists uk_deploy_license_md5 on deploy_license(md5) where md5 is not null;

-- 部署配置-任务-任务流模板
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

-- 部署配置-任务-任务模板
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

-- 运营-文件管理（4.4.1）
create table if not exists operation_file (
    id bigint primary key,
    name varchar(255) not null,
    type varchar(64) not null,
    size_bytes bigint not null,
    tags varchar(512) not null default '',
    storage_path varchar(512) not null,
    preview_content text,
    created_at timestamp not null
);
create index if not exists idx_operation_file_type on operation_file(type);
create index if not exists idx_operation_file_created_at on operation_file(created_at desc);

-- operation-service management
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

-- 运营-升级-安装包管理
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

-- 运营-升级-发布管理
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

-- 运营-任务-任务流管理
create table if not exists operation_task (
    id bigint primary key,
    code varchar(64) not null unique,
    external_code varchar(128),
    task_flow_template_code varchar(64),
    task_template_code varchar(64),
    status varchar(32) not null default 'pending',
    robot_code varchar(64),
    priority int not null default 1,
    description varchar(512),
    created_at timestamp not null,
    ended_at timestamp,
    updated_at timestamp not null
);
alter table operation_task
    add column if not exists task_flow_template_code varchar(64);
alter table operation_task
    add column if not exists task_template_code varchar(64);
create index if not exists idx_operation_task_status on operation_task(status);
create index if not exists idx_operation_task_robot_code on operation_task(robot_code);
create index if not exists idx_operation_task_created_at on operation_task(created_at desc);
create index if not exists idx_operation_task_flow_template_code on operation_task(task_flow_template_code);
create index if not exists idx_operation_task_template_code on operation_task(task_template_code);
