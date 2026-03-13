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
