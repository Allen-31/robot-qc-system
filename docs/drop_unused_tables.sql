-- ============================================================
-- 清理 robot_qc 库中未使用的表（在 Navicat 等工具中执行）
-- 执行前请确认连接的是 robot_qc 库，且已备份重要数据
-- ============================================================

-- 1. 删除早期从 schema 移除的业务表（若存在）
DROP TABLE IF EXISTS qc_record CASCADE;
DROP TABLE IF EXISTS qc_task CASCADE;
DROP TABLE IF EXISTS workstation CASCADE;
DROP TABLE IF EXISTS workbench CASCADE;
DROP TABLE IF EXISTS inspection_point CASCADE;
DROP TABLE IF EXISTS qc_template CASCADE;

-- 2. 删除所有 act_ 开头的表（Activiti/Flowable 工作流引擎遗留）
DO $$
DECLARE
  r RECORD;
BEGIN
  FOR r IN (
    SELECT tablename
    FROM pg_tables
    WHERE schemaname = 'public'
      AND tablename LIKE 'act_%'
  ) LOOP
    EXECUTE format('DROP TABLE IF EXISTS %I CASCADE', r.tablename);
    RAISE NOTICE 'Dropped table: %', r.tablename;
  END LOOP;
END $$;

-- 执行完成后，库中应只保留这 5 张在用表：
-- robot, sys_user, sys_role, sys_user_role, sys_role_permission
