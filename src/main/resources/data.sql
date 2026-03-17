-- seed base data (UTF-8 without BOM)
-- default admin password in data.sql is hashed (plain text was admin123)
INSERT INTO sys_role (code, name, description, created_at, updated_at)
SELECT 'admin', 'Super Admin', 'System default super admin role', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE code = 'admin');

INSERT INTO sys_user (code, name, phone, email, password_hash, status, created_at, updated_at)
SELECT 'admin', 'Admin', NULL, NULL,
       '$2a$10$zkcsuiF5pOTjIT4lb9lkoe8QQ5KykQ1eyu4VAhSul7gLDhT2zJhrO',
       'enabled', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE code = 'admin');

INSERT INTO sys_user_role (user_code, role_code)
SELECT 'admin', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM sys_user_role WHERE user_code = 'admin' AND role_code = 'admin');

-- seed robot types
INSERT INTO robot_type (type_no, type_name, parts_count, status, created_at, updated_at)
SELECT 'RT-001', 'Type-001', 1, 'enabled', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type WHERE type_no = 'RT-001');
INSERT INTO robot_type (type_no, type_name, parts_count, status, created_at, updated_at)
SELECT 'RT-002', 'Type-002', 1, 'enabled', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type WHERE type_no = 'RT-002');
INSERT INTO robot_type (type_no, type_name, parts_count, status, created_at, updated_at)
SELECT 'RT-003', 'Type-003', 1, 'enabled', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type WHERE type_no = 'RT-003');
INSERT INTO robot_type (type_no, type_name, parts_count, status, created_at, updated_at)
SELECT 'RT-004', 'Type-004', 1, 'enabled', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type WHERE type_no = 'RT-004');
INSERT INTO robot_type (type_no, type_name, parts_count, status, created_at, updated_at)
SELECT 'RT-005', 'Type-005', 1, 'enabled', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type WHERE type_no = 'RT-005');

-- seed robot type points
INSERT INTO robot_type_point (robot_type_id, part_name, part_position, x, y, rotation, remark, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_type WHERE type_no = 'RT-001'), 'arm', 'left', 10, 20, 5, 'p1', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type_point WHERE robot_type_id = (SELECT id FROM robot_type WHERE type_no = 'RT-001') AND part_name = 'arm');
INSERT INTO robot_type_point (robot_type_id, part_name, part_position, x, y, rotation, remark, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_type WHERE type_no = 'RT-002'), 'arm', 'right', 20, 30, 10, 'p2', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type_point WHERE robot_type_id = (SELECT id FROM robot_type WHERE type_no = 'RT-002') AND part_name = 'arm');
INSERT INTO robot_type_point (robot_type_id, part_name, part_position, x, y, rotation, remark, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_type WHERE type_no = 'RT-003'), 'head', 'top', 50, 20, 0, 'p3', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type_point WHERE robot_type_id = (SELECT id FROM robot_type WHERE type_no = 'RT-003') AND part_name = 'head');
INSERT INTO robot_type_point (robot_type_id, part_name, part_position, x, y, rotation, remark, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_type WHERE type_no = 'RT-004'), 'wheel', 'front', 30, 80, 0, 'p4', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type_point WHERE robot_type_id = (SELECT id FROM robot_type WHERE type_no = 'RT-004') AND part_name = 'wheel');
INSERT INTO robot_type_point (robot_type_id, part_name, part_position, x, y, rotation, remark, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_type WHERE type_no = 'RT-005'), 'sensor', 'center', 60, 60, 0, 'p5', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_type_point WHERE robot_type_id = (SELECT id FROM robot_type WHERE type_no = 'RT-005') AND part_name = 'sensor');

-- seed robot groups
INSERT INTO robot_group (group_no, group_name, description, created_at, updated_at)
SELECT 'RG-001', 'Group-001', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_group WHERE group_no = 'RG-001');
INSERT INTO robot_group (group_no, group_name, description, created_at, updated_at)
SELECT 'RG-002', 'Group-002', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_group WHERE group_no = 'RG-002');
INSERT INTO robot_group (group_no, group_name, description, created_at, updated_at)
SELECT 'RG-003', 'Group-003', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_group WHERE group_no = 'RG-003');
INSERT INTO robot_group (group_no, group_name, description, created_at, updated_at)
SELECT 'RG-004', 'Group-004', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_group WHERE group_no = 'RG-004');
INSERT INTO robot_group (group_no, group_name, description, created_at, updated_at)
SELECT 'RG-005', 'Group-005', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_group WHERE group_no = 'RG-005');

-- seed robot parts
INSERT INTO robot_part (part_no, name, position, type, model, vendor, supplier, lifecycle, status, remark, created_at, updated_at)
SELECT 'RP-001', 'Part-001', 'left', 'sensor', 'M1', 'V1', 'S1', '12m', 'enabled', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part WHERE part_no = 'RP-001');
INSERT INTO robot_part (part_no, name, position, type, model, vendor, supplier, lifecycle, status, remark, created_at, updated_at)
SELECT 'RP-002', 'Part-002', 'right', 'sensor', 'M2', 'V2', 'S2', '12m', 'enabled', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part WHERE part_no = 'RP-002');
INSERT INTO robot_part (part_no, name, position, type, model, vendor, supplier, lifecycle, status, remark, created_at, updated_at)
SELECT 'RP-003', 'Part-003', 'top', 'motor', 'M3', 'V3', 'S3', '24m', 'enabled', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part WHERE part_no = 'RP-003');
INSERT INTO robot_part (part_no, name, position, type, model, vendor, supplier, lifecycle, status, remark, created_at, updated_at)
SELECT 'RP-004', 'Part-004', 'bottom', 'wheel', 'M4', 'V4', 'S4', '24m', 'enabled', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part WHERE part_no = 'RP-004');
INSERT INTO robot_part (part_no, name, position, type, model, vendor, supplier, lifecycle, status, remark, created_at, updated_at)
SELECT 'RP-005', 'Part-005', 'center', 'camera', 'M5', 'V5', 'S5', '18m', 'enabled', 'seed', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part WHERE part_no = 'RP-005');

-- seed robot part params
INSERT INTO robot_part_param (part_id, name, value, unit, range, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_part WHERE part_no = 'RP-001'), 'range', '10', 'm', '0-10', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part_param WHERE part_id = (SELECT id FROM robot_part WHERE part_no = 'RP-001') AND name = 'range');
INSERT INTO robot_part_param (part_id, name, value, unit, range, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_part WHERE part_no = 'RP-002'), 'range', '20', 'm', '0-20', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part_param WHERE part_id = (SELECT id FROM robot_part WHERE part_no = 'RP-002') AND name = 'range');
INSERT INTO robot_part_param (part_id, name, value, unit, range, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_part WHERE part_no = 'RP-003'), 'power', '50', 'w', '0-100', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part_param WHERE part_id = (SELECT id FROM robot_part WHERE part_no = 'RP-003') AND name = 'power');
INSERT INTO robot_part_param (part_id, name, value, unit, range, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_part WHERE part_no = 'RP-004'), 'speed', '5', 'm/s', '0-10', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part_param WHERE part_id = (SELECT id FROM robot_part WHERE part_no = 'RP-004') AND name = 'speed');
INSERT INTO robot_part_param (part_id, name, value, unit, range, sort_order, created_at, updated_at)
SELECT (SELECT id FROM robot_part WHERE part_no = 'RP-005'), 'fps', '30', 'fps', '1-60', 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_part_param WHERE part_id = (SELECT id FROM robot_part WHERE part_no = 'RP-005') AND name = 'fps');

-- seed scene maps
INSERT INTO scene_map (code, name, type, edit_status, publish_status, map_version, resolution, origin_x, origin_y, created_at, updated_at)
SELECT 'MAP-001', 'Map-001', 'slam', 'draft', 'unpublished', 'v1', 0.05, 0, 0, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_map WHERE code = 'MAP-001');
INSERT INTO scene_map (code, name, type, edit_status, publish_status, map_version, resolution, origin_x, origin_y, created_at, updated_at)
SELECT 'MAP-002', 'Map-002', 'slam', 'draft', 'unpublished', 'v1', 0.05, 1, 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_map WHERE code = 'MAP-002');
INSERT INTO scene_map (code, name, type, edit_status, publish_status, map_version, resolution, origin_x, origin_y, created_at, updated_at)
SELECT 'MAP-003', 'Map-003', 'slam', 'draft', 'unpublished', 'v1', 0.05, 2, 2, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_map WHERE code = 'MAP-003');
INSERT INTO scene_map (code, name, type, edit_status, publish_status, map_version, resolution, origin_x, origin_y, created_at, updated_at)
SELECT 'MAP-004', 'Map-004', 'slam', 'draft', 'unpublished', 'v1', 0.05, 3, 3, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_map WHERE code = 'MAP-004');
INSERT INTO scene_map (code, name, type, edit_status, publish_status, map_version, resolution, origin_x, origin_y, created_at, updated_at)
SELECT 'MAP-005', 'Map-005', 'slam', 'draft', 'unpublished', 'v1', 0.05, 4, 4, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_map WHERE code = 'MAP-005');

-- seed scene devices
INSERT INTO scene_device (code, name, type, ip, online_status, is_abnormal, exception_detail, map_code, created_at, updated_at)
SELECT 'SD-001', 'Device-001', 'camera', '192.168.1.10', 'online', 0, NULL, 'MAP-001', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_device WHERE code = 'SD-001');
INSERT INTO scene_device (code, name, type, ip, online_status, is_abnormal, exception_detail, map_code, created_at, updated_at)
SELECT 'SD-002', 'Device-002', 'camera', '192.168.1.11', 'online', 0, NULL, 'MAP-002', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_device WHERE code = 'SD-002');
INSERT INTO scene_device (code, name, type, ip, online_status, is_abnormal, exception_detail, map_code, created_at, updated_at)
SELECT 'SD-003', 'Device-003', 'sensor', '192.168.1.12', 'offline', 0, NULL, 'MAP-003', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_device WHERE code = 'SD-003');
INSERT INTO scene_device (code, name, type, ip, online_status, is_abnormal, exception_detail, map_code, created_at, updated_at)
SELECT 'SD-004', 'Device-004', 'sensor', '192.168.1.13', 'offline', 0, NULL, 'MAP-004', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_device WHERE code = 'SD-004');
INSERT INTO scene_device (code, name, type, ip, online_status, is_abnormal, exception_detail, map_code, created_at, updated_at)
SELECT 'SD-005', 'Device-005', 'lidar', '192.168.1.14', 'online', 0, NULL, 'MAP-005', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM scene_device WHERE code = 'SD-005');

-- seed charge strategies
INSERT INTO robot_charge_strategy (code, name, status, robot_type_no, robot_group_no, low_battery_threshold, min_charge_minutes, charge_method, created_at, updated_at)
SELECT 'RCS-001', 'Charge-001', 'enabled', 'RT-001', 'RG-001', 20, 30, 'dock', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_charge_strategy WHERE code = 'RCS-001');
INSERT INTO robot_charge_strategy (code, name, status, robot_type_no, robot_group_no, low_battery_threshold, min_charge_minutes, charge_method, created_at, updated_at)
SELECT 'RCS-002', 'Charge-002', 'enabled', 'RT-002', 'RG-002', 25, 25, 'dock', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_charge_strategy WHERE code = 'RCS-002');
INSERT INTO robot_charge_strategy (code, name, status, robot_type_no, robot_group_no, low_battery_threshold, min_charge_minutes, charge_method, created_at, updated_at)
SELECT 'RCS-003', 'Charge-003', 'enabled', 'RT-003', 'RG-003', 30, 20, 'dock', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_charge_strategy WHERE code = 'RCS-003');
INSERT INTO robot_charge_strategy (code, name, status, robot_type_no, robot_group_no, low_battery_threshold, min_charge_minutes, charge_method, created_at, updated_at)
SELECT 'RCS-004', 'Charge-004', 'enabled', 'RT-004', 'RG-004', 15, 40, 'dock', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_charge_strategy WHERE code = 'RCS-004');
INSERT INTO robot_charge_strategy (code, name, status, robot_type_no, robot_group_no, low_battery_threshold, min_charge_minutes, charge_method, created_at, updated_at)
SELECT 'RCS-005', 'Charge-005', 'enabled', 'RT-005', 'RG-005', 18, 35, 'dock', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_charge_strategy WHERE code = 'RCS-005');

-- seed homing strategies
INSERT INTO robot_homing_strategy (code, name, status, robot_type_no, robot_group_no, idle_wait_seconds, created_at, updated_at)
SELECT 'RHS-001', 'Homing-001', 'enabled', 'RT-001', 'RG-001', 120, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_homing_strategy WHERE code = 'RHS-001');
INSERT INTO robot_homing_strategy (code, name, status, robot_type_no, robot_group_no, idle_wait_seconds, created_at, updated_at)
SELECT 'RHS-002', 'Homing-002', 'enabled', 'RT-002', 'RG-002', 150, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_homing_strategy WHERE code = 'RHS-002');
INSERT INTO robot_homing_strategy (code, name, status, robot_type_no, robot_group_no, idle_wait_seconds, created_at, updated_at)
SELECT 'RHS-003', 'Homing-003', 'enabled', 'RT-003', 'RG-003', 180, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_homing_strategy WHERE code = 'RHS-003');
INSERT INTO robot_homing_strategy (code, name, status, robot_type_no, robot_group_no, idle_wait_seconds, created_at, updated_at)
SELECT 'RHS-004', 'Homing-004', 'enabled', 'RT-004', 'RG-004', 210, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_homing_strategy WHERE code = 'RHS-004');
INSERT INTO robot_homing_strategy (code, name, status, robot_type_no, robot_group_no, idle_wait_seconds, created_at, updated_at)
SELECT 'RHS-005', 'Homing-005', 'enabled', 'RT-005', 'RG-005', 240, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM robot_homing_strategy WHERE code = 'RHS-005');

-- -- seed robots
-- INSERT INTO robot (robot_code, robot_name, serial_no, ip, model, firmware_version, robot_type_no, robot_type_name, group_no, group_name, status, online_status, battery, mileage_km, current_map_code, current_map_name, dispatch_mode, control_status, exception_status, video_url, location, registered_at)\r\nSELECT 'RB-001', 'Robot-001', 'SN-001', '192.168.10.1', 'M1', '1.0', 'RT-001', 'Type-001', 'RG-001', 'Group-001', 'idle', 'online', 80, 12.5, 'MAP-001', 'Map-001', 'auto', 'remote', 'normal', NULL, 'A1', now()\r\nWHERE NOT EXISTS (SELECT 1 FROM robot WHERE robot_code = 'RB-001');
-- INSERT INTO robot (robot_code, robot_name, serial_no, ip, model, firmware_version, robot_type_no, robot_type_name, group_no, group_name, status, online_status, battery, mileage_km, current_map_code, current_map_name, dispatch_mode, control_status, exception_status, video_url, location, registered_at)\r\nSELECT 'RB-002', 'Robot-002', 'SN-002', '192.168.10.2', 'M2', '1.0', 'RT-002', 'Type-002', 'RG-002', 'Group-002', 'idle', 'online', 75, 10.0, 'MAP-002', 'Map-002', 'auto', 'remote', 'normal', NULL, 'A2', now()\r\nWHERE NOT EXISTS (SELECT 1 FROM robot WHERE robot_code = 'RB-002');
-- INSERT INTO robot (robot_code, robot_name, serial_no, ip, model, firmware_version, robot_type_no, robot_type_name, group_no, group_name, status, online_status, battery, mileage_km, current_map_code, current_map_name, dispatch_mode, control_status, exception_status, video_url, location, registered_at)\r\nSELECT 'RB-003', 'Robot-003', 'SN-003', '192.168.10.3', 'M3', '1.0', 'RT-003', 'Type-003', 'RG-003', 'Group-003', 'idle', 'offline', 60, 8.0, 'MAP-003', 'Map-003', 'manual', 'local', 'normal', NULL, 'A3', now()\r\nWHERE NOT EXISTS (SELECT 1 FROM robot WHERE robot_code = 'RB-003');
-- INSERT INTO robot (robot_code, robot_name, serial_no, ip, model, firmware_version, robot_type_no, robot_type_name, group_no, group_name, status, online_status, battery, mileage_km, current_map_code, current_map_name, dispatch_mode, control_status, exception_status, video_url, location, registered_at)\r\nSELECT 'RB-004', 'Robot-004', 'SN-004', '192.168.10.4', 'M4', '1.0', 'RT-004', 'Type-004', 'RG-004', 'Group-004', 'idle', 'offline', 55, 6.0, 'MAP-004', 'Map-004', 'manual', 'local', 'normal', NULL, 'A4', now()\r\nWHERE NOT EXISTS (SELECT 1 FROM robot WHERE robot_code = 'RB-004');
-- INSERT INTO robot (robot_code, robot_name, serial_no, ip, model, firmware_version, robot_type_no, robot_type_name, group_no, group_name, status, online_status, battery, mileage_km, current_map_code, current_map_name, dispatch_mode, control_status, exception_status, video_url, location, registered_at)\r\nSELECT 'RB-005', 'Robot-005', 'SN-005', '192.168.10.5', 'M5', '1.0', 'RT-005', 'Type-005', 'RG-005', 'Group-005', 'idle', 'online', 90, 15.0, 'MAP-005', 'Map-005', 'auto', 'remote', 'normal', NULL, 'A5', now()\r\nWHERE NOT EXISTS (SELECT 1 FROM robot WHERE robot_code = 'RB-005');

-- seed operation services
INSERT INTO operation_service (id, name, type, version, ip, status, cpu_usage, memory_usage, runtime, created_at, updated_at)
SELECT 1001, 'Task Scheduler Service', 'business', 'v2.3.1', '10.10.2.11', 'running', 24.3, 61.5, '6d 12h', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_service WHERE id = 1001);
INSERT INTO operation_service (id, name, type, version, ip, status, cpu_usage, memory_usage, runtime, created_at, updated_at)
SELECT 1002, 'Device Access Gateway', 'gateway', 'v1.9.4', '10.10.2.12', 'running', 32.7, 54.1, '13d 3h', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_service WHERE id = 1002);
INSERT INTO operation_service (id, name, type, version, ip, status, cpu_usage, memory_usage, runtime, created_at, updated_at)
SELECT 1003, 'Quality Report Service', 'business', 'v3.0.0', '10.10.2.13', 'abnormal', 71.2, 83.9, '2d 7h', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_service WHERE id = 1003);
INSERT INTO operation_service (id, name, type, version, ip, status, cpu_usage, memory_usage, runtime, created_at, updated_at)
SELECT 1004, 'File Storage Service', 'base', 'v1.4.8', '10.10.2.21', 'stopped', 0.0, 0.0, '0h', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_service WHERE id = 1004);

-- seed operation service logs
INSERT INTO operation_service_log (id, service_id, log_name, type, content, created_at, updated_at)
SELECT 2001, 1001, 'task-scheduler-20260304.log', 'runtime', '[INFO] Scheduler started\n[INFO] queueDepth=18', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_service_log WHERE id = 2001);
INSERT INTO operation_service_log (id, service_id, log_name, type, content, created_at, updated_at)
SELECT 2002, 1002, 'device-gateway-20260304.log', 'runtime', '[INFO] Gateway online\n[INFO] activeConnections=112', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_service_log WHERE id = 2002);
INSERT INTO operation_service_log (id, service_id, log_name, type, content, created_at, updated_at)
SELECT 2003, 1003, 'report-service-20260304.log', 'runtime', '[WARN] report queue backlog\n[ERROR] timeout', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_service_log WHERE id = 2003);

-- seed operation packages
INSERT INTO operation_package (id, name, type, target_parts, description, size_bytes, md5, uploader, uploaded_at, storage_path)
SELECT 3001, 'cloud-release-20260304.zip', 'cloud',
       '[{"part":"api-gateway","version":"v3.2.1"},{"part":"report-service","version":"v2.8.0"}]'::jsonb,
       'Cloud patch package', 130636800, '9f8a2e7d40f11bc7e42a9d8228f8a0a4', 'admin', now(), 'uploads/operation-packages/3001_cloud-release-20260304.zip'
WHERE NOT EXISTS (SELECT 1 FROM operation_package WHERE id = 3001);
INSERT INTO operation_package (id, name, type, target_parts, description, size_bytes, md5, uploader, uploaded_at, storage_path)
SELECT 3002, 'robot-rb-series-v5.1.0.zip', 'robot',
       '[{"part":"motion-controller","version":"v5.1.0"},{"part":"camera-driver","version":"v2.4.6"},{"part":"arm-firmware","version":"v5.1.0"}]'::jsonb,
       'Robot upgrade package', 90439680, '34ea1cb9f06d45b689d2a6c1ed5f3b21', 'ops', now(), 'uploads/operation-packages/3002_robot-rb-series-v5.1.0.zip'
WHERE NOT EXISTS (SELECT 1 FROM operation_package WHERE id = 3002);

-- seed operation publish tasks
INSERT INTO operation_publish (id, name, package_name, target_robots, target_robot_groups, target_robot_types, strategy, restart_after_upgrade, status, creator, created_at, updated_at, completed_at)
SELECT 4001, 'Assembly Line 1 Night Upgrade', 'robot-rb-series-v5.1.0.zip',
       '["RB-A101","RB-A102"]'::jsonb, '["Line-1"]'::jsonb, '["AMR"]'::jsonb,
       'idle', true, 'running', 'ops', now(), now(), NULL
WHERE NOT EXISTS (SELECT 1 FROM operation_publish WHERE id = 4001);
INSERT INTO operation_publish (id, name, package_name, target_robots, target_robot_groups, target_robot_types, strategy, restart_after_upgrade, status, creator, created_at, updated_at, completed_at)
SELECT 4002, 'Cloud Patch Full Release', 'cloud-release-20260304.zip',
       '[]'::jsonb, '[]'::jsonb, '[]'::jsonb,
       'immediate', false, 'completed', 'admin', now(), now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_publish WHERE id = 4002);

-- seed operation publish devices
INSERT INTO operation_publish_device (id, publish_id, device_name, ip, status, package_name, version, progress, updated_at, completed_at)
SELECT 5001, 4001, 'robot-rb-a101', '10.10.11.101', 'completed', 'robot-rb-series-v5.1.0.zip', 'v5.1.0', 100, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_publish_device WHERE id = 5001);
INSERT INTO operation_publish_device (id, publish_id, device_name, ip, status, package_name, version, progress, updated_at, completed_at)
SELECT 5002, 4001, 'robot-rb-a102', '10.10.11.102', 'upgrading', 'robot-rb-series-v5.1.0.zip', 'v5.1.0', 65, now(), NULL
WHERE NOT EXISTS (SELECT 1 FROM operation_publish_device WHERE id = 5002);

-- seed operation tasks
INSERT INTO operation_task (id, code, external_code, status, robot_code, priority, description, created_at, ended_at, updated_at)
SELECT 6001, 'TK-20260304-001', 'MES-WO-778201', 'running', 'RB-A101', 1, 'Assembly line 1 inspection task', now(), NULL, now()
WHERE NOT EXISTS (SELECT 1 FROM operation_task WHERE id = 6001);
INSERT INTO operation_task (id, code, external_code, status, robot_code, priority, description, created_at, ended_at, updated_at)
SELECT 6002, 'TK-20260304-002', 'MES-WO-778205', 'paused', 'RB-A102', 2, 'Assembly line 2 reinspection task', now(), NULL, now()
WHERE NOT EXISTS (SELECT 1 FROM operation_task WHERE id = 6002);
INSERT INTO operation_task (id, code, external_code, status, robot_code, priority, description, created_at, ended_at, updated_at)
SELECT 6003, 'TK-20260304-003', 'MES-WO-778209', 'completed', 'RB-A101', 1, 'Assembly line 1 rework task', now(), now(), now()
WHERE NOT EXISTS (SELECT 1 FROM operation_task WHERE id = 6003);

-- seed deploy licenses
INSERT INTO deploy_license (id, name, file_name, storage_path, size_bytes, md5, effective_at, expire_at, applicant, status, imported_at)
SELECT 7001, 'Factory Basic License', 'factory-basic.lic', 'uploads/deploy-licenses/7001_factory-basic.lic', 2048,
       'a8f5f167f44f4964e6c998dee827110c', now() - interval '7 day', now() + interval '358 day',
       'admin', 'active', now()
WHERE NOT EXISTS (SELECT 1 FROM deploy_license WHERE id = 7001);
-- seed deploy task flow templates
INSERT INTO deploy_task_flow_template (code, name, enabled, priority, created_by, created_at, updated_by, updated_at)
SELECT 'TASK-T01', 'Inspection Task Flow Template', true, 1, 'admin', now(), 'admin', now()
WHERE NOT EXISTS (SELECT 1 FROM deploy_task_flow_template WHERE code = 'TASK-T01');
INSERT INTO deploy_task_flow_template (code, name, enabled, priority, created_by, created_at, updated_by, updated_at)
SELECT 'TASK-T02', 'Handling Task Flow Template', false, 2, 'operator', now(), 'operator', now()
WHERE NOT EXISTS (SELECT 1 FROM deploy_task_flow_template WHERE code = 'TASK-T02');

-- seed deploy task templates
INSERT INTO deploy_task_template (code, name, enabled, created_by, created_at, updated_by, updated_at)
SELECT 'TASK-A01', 'Inspection Task Template', true, 'admin', now(), 'admin', now()
WHERE NOT EXISTS (SELECT 1 FROM deploy_task_template WHERE code = 'TASK-A01');
INSERT INTO deploy_task_template (code, name, enabled, created_by, created_at, updated_by, updated_at)
SELECT 'TASK-A02', 'Charging Task Template', true, 'admin', now(), 'admin', now()
WHERE NOT EXISTS (SELECT 1 FROM deploy_task_template WHERE code = 'TASK-A02');
