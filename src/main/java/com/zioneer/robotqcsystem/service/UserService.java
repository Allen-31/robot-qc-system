package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.*;

/**
 * 用户管理服务
 */
public interface UserService {

    PageResult<com.zioneer.robotqcsystem.domain.vo.UserListVO> page(UserQuery query);

    void create(UserCreateDTO dto);

    void update(String code, UserUpdateDTO dto);

    void deleteByCode(String code);

    void updateRoles(String code, UserRolesUpdateDTO dto);

    /** 修改密码，返回 success 或 error 码 */
    com.zioneer.robotqcsystem.domain.vo.PasswordUpdateResultVO updatePassword(String code, PasswordUpdateDTO dto);
}
