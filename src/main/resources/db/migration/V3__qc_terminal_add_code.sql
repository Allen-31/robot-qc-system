-- 终端表增加编码字段（老库迁移）
ALTER TABLE qc_terminal ADD COLUMN IF NOT EXISTS code varchar(64);
UPDATE qc_terminal SET code = 'T-' || id WHERE code IS NULL;
ALTER TABLE qc_terminal ALTER COLUMN code SET NOT NULL;
CREATE UNIQUE INDEX IF NOT EXISTS qc_terminal_code_key ON qc_terminal (code);
