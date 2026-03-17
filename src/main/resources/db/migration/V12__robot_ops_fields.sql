alter table if exists robot add column if not exists chassis_mode varchar(32);
alter table if exists robot add column if not exists arm_mode varchar(32);
alter table if exists robot add column if not exists is_charging boolean not null default false;
alter table if exists robot add column if not exists is_homing boolean not null default false;
alter table if exists robot add column if not exists is_lifted boolean not null default false;
