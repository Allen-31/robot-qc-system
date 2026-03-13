package com.zioneer.robotqcsystem.service.auth;

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

    /** 管理员重置用户密码（无需原密码），用于用户忘记密码或首次密码未设置成功时 */
    void resetPasswordByAdmin(String code, AdminResetPasswordDTO dto);
}
