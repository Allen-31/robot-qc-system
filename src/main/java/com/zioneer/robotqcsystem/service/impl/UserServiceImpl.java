package com.zioneer.robotqcsystem.service.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageQuery;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.*;
import com.zioneer.robotqcsystem.domain.entity.SysUser;
import com.zioneer.robotqcsystem.domain.vo.PasswordUpdateResultVO;
import com.zioneer.robotqcsystem.domain.vo.UserListVO;
import com.zioneer.robotqcsystem.mapper.SysUserMapper;
import com.zioneer.robotqcsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理服务实现
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResult<UserListVO> page(String keyword, String role, String status, PageQuery pageQuery) {
        long total = sysUserMapper.countList(keyword, role, status);
        if (total == 0) {
            return PageResult.empty(pageQuery);
        }
        List<SysUser> list = sysUserMapper.selectList(keyword, role, status, pageQuery.getOffset(), pageQuery.getPageSize());
        List<UserListVO> voList = list.stream()
                .map(this::toListVO)
                .collect(Collectors.toList());
        // 去重：同一用户可能多行（多角色），合并为一条
        List<UserListVO> merged = mergeUserListVO(voList);
        return PageResult.of(merged, total, pageQuery.getPageNum(), pageQuery.getPageSize());
    }

    private UserListVO toListVO(SysUser u) {
        UserListVO vo = new UserListVO();
        vo.setCode(u.getCode());
        vo.setName(u.getName());
        vo.setPhone(u.getPhone());
        vo.setEmail(u.getEmail());
        vo.setStatus(u.getStatus());
        vo.setLastLoginAt(u.getLastLoginAt());
        vo.setRoles(u.getRoles() != null ? u.getRoles() : new ArrayList<>());
        return vo;
    }

    private List<UserListVO> mergeUserListVO(List<UserListVO> list) {
        return new ArrayList<>(list.stream().collect(Collectors.toMap(UserListVO::getCode, v -> v, (a, b) -> {
            if (b.getRoles() != null && !b.getRoles().isEmpty()) {
                List<String> roles = new ArrayList<>(a.getRoles() != null ? a.getRoles() : List.of());
                for (String r : b.getRoles()) {
                    if (!roles.contains(r)) roles.add(r);
                }
                a.setRoles(roles);
            }
            return a;
        })).values());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserCreateDTO dto) {
        if (sysUserMapper.selectByCode(dto.getCode()) != null) {
            throw new BusinessException("用户编码已存在: " + dto.getCode());
        }
        SysUser user = SysUser.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .status(dto.getStatus() != null ? dto.getStatus() : "enabled")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        sysUserMapper.insert(user);
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            for (String roleCode : dto.getRoles()) {
                sysUserMapper.insertUserRole(dto.getCode(), roleCode);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String code, UserUpdateDTO dto) {
        SysUser exist = sysUserMapper.selectByCode(code);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        SysUser user = SysUser.builder()
                .code(code)
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .status(dto.getStatus())
                .updatedAt(LocalDateTime.now())
                .build();
        sysUserMapper.updateByCode(user);
        sysUserMapper.deleteUserRoles(code);
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            for (String roleCode : dto.getRoles()) {
                sysUserMapper.insertUserRole(code, roleCode);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        if (sysUserMapper.selectByCode(code) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        sysUserMapper.deleteUserRoles(code);
        sysUserMapper.deleteByCode(code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoles(String code, UserRolesUpdateDTO dto) {
        if (sysUserMapper.selectByCode(code) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        sysUserMapper.deleteUserRoles(code);
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            for (String roleCode : dto.getRoles()) {
                sysUserMapper.insertUserRole(code, roleCode);
            }
        }
    }

    @Override
    public PasswordUpdateResultVO updatePassword(String code, PasswordUpdateDTO dto) {
        SysUser user = sysUserMapper.selectByCode(code);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPasswordHash())) {
            return PasswordUpdateResultVO.builder().success(false).error("old_password_invalid").build();
        }
        LocalDateTime now = LocalDateTime.now();
        sysUserMapper.updatePasswordHash(code, passwordEncoder.encode(dto.getNewPassword()), now);
        return PasswordUpdateResultVO.builder().success(true).build();
    }
}
