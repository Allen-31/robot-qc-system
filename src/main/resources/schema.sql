-- 机器人（示例业务表，已有 CRUD 接口�?
create table if not exists robot (
    id bigserial primary key,
    robot_code varchar(64) not null unique,
    robot_name varchar(128) not null,
    serial_no varchar(128),
    ip varchar(64),
    model varchar(128),
    firmware_version varchar(64),
    robot_type_no varchar(64),
    robot_type_name varchar(128),
    group_no varchar(64),
    group_name varchar(128),
    status varchar(32) not null,
    online_status varchar(32),
    battery int,
    mileage_km numeric(12,2),
    current_map_code varchar(64),
    current_map_name varchar(128),
    dispatch_mode varchar(32),
    control_status varchar(32),
    exception_status varchar(32),
    video_url varchar(256),
    location varchar(128),
    last_inspection_at timestamp,
    registered_at timestamp,
    last_online_at timestamp,
    last_heartbeat_at timestamp
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

-- 菜单（部署配�?- 菜单管理，树形）
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

-- ����������
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

-- 机器人零部件
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

-- �����˷���
create table if not exists robot_group (
    id bigserial primary key,
    group_no varchar(64) not null unique,
    group_name varchar(128) not null,
    description varchar(512),
    created_at timestamp not null,
    updated_at timestamp not null
);

-- 充电策略
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

-- 归位策略
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
drop index if exists idx_robot_charge_strategy_type_no;
create index idx_robot_charge_strategy_type_no on robot_charge_strategy(robot_type_no);
drop index if exists idx_robot_charge_strategy_group_no;
create index idx_robot_charge_strategy_group_no on robot_charge_strategy(robot_group_no);
drop index if exists idx_robot_homing_strategy_type_no;
create index idx_robot_homing_strategy_type_no on robot_homing_strategy(robot_type_no);
drop index if exists idx_robot_homing_strategy_group_no;
create index idx_robot_homing_strategy_group_no on robot_homing_strategy(robot_group_no);

-- 场景地图
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

-- 场景设备
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

drop index if exists idx_scene_device_map_code;
create index idx_scene_device_map_code on scene_device(map_code);
