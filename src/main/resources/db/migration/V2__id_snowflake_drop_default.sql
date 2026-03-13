-- 老表主键改为应用层 Snowflake 生成：去掉 id 列默认值（仅对已存在表执行一次）
-- 新库由 schema.sql 直接建 bigint 主键表，无需默认值
ALTER TABLE robot ALTER COLUMN id DROP DEFAULT;
ALTER TABLE qc_workstation ALTER COLUMN id DROP DEFAULT;
ALTER TABLE qc_station ALTER COLUMN id DROP DEFAULT;
ALTER TABLE qc_wire_harness_type ALTER COLUMN id DROP DEFAULT;
ALTER TABLE qc_terminal ALTER COLUMN id DROP DEFAULT;
ALTER TABLE qc_work_order ALTER COLUMN id DROP DEFAULT;
ALTER TABLE qc_reinspection_record ALTER COLUMN id DROP DEFAULT;
