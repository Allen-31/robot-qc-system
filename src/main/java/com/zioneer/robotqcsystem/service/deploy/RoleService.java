package com.zioneer.robotqcsystem.service.deploy;

import com.zioneer.robotqcsystem.domain.dto.RoleCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RolePermissionUpdateDTO;
import com.zioneer.robotqcsystem.domain.dto.RoleUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RolePermissionVO;
import com.zioneer.robotqcsystem.domain.vo.RoleVO;

import java.util.List;

/**
 * 角色管理服务
 */
public interface RoleService {

    List<RoleVO> list(String keyword);

    void create(RoleCreateDTO dto);

    void update(String code, RoleUpdateDTO dto);

    void deleteByCode(String code);

    List<RolePermissionVO> getPermissions(String roleCode);

    void savePermissions(String roleCode, RolePermissionUpdateDTO dto);
}
