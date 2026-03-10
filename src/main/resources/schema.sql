-- 机器人（示例业务表，已有 CRUD 接口）
create table if not exists robot (
    id bigserial primary key,
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
