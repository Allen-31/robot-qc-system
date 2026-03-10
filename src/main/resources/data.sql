-- 初始角色与管理员（仅当表为空时插入，避免重复执行报错）
-- 管理员默认密码: admin123
INSERT INTO sys_role (code, name, description, created_at, updated_at)
SELECT 'admin', '超级管理员', '系统默认超级管理员角色', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE code = 'admin');

INSERT INTO sys_user (code, name, phone, email, password_hash, status, created_at, updated_at)
SELECT 'admin', '管理员', NULL, NULL,
       '$2a$10$zkcsuiF5pOTjIT4lb9lkoe8QQ5KykQ1eyu4VAhSul7gLDhT2zJhrO',
       'enabled', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE code = 'admin');

INSERT INTO sys_user_role (user_code, role_code)
SELECT 'admin', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM sys_user_role WHERE user_code = 'admin' AND role_code = 'admin');
