-- 为已有 qc_workshop 表增加车间地点字段（若表为 schema.sql 新建则已包含，无需执行）
ALTER TABLE qc_workshop ADD COLUMN IF NOT EXISTS location varchar(256);
