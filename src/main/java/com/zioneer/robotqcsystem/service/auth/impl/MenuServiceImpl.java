package com.zioneer.robotqcsystem.service.auth.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.MenuCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.MenuOrderDTO;
import com.zioneer.robotqcsystem.domain.dto.MenuUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.SysMenu;
import com.zioneer.robotqcsystem.domain.vo.MenuTreeNodeVO;
import com.zioneer.robotqcsystem.mapper.SysMenuMapper;
import com.zioneer.robotqcsystem.service.auth.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuTreeNodeVO> getMenus(String status, boolean tree) {
        List<SysMenu> list = sysMenuMapper.selectList(status);
        if (!tree) {
            return list.stream().map(this::toVO).collect(Collectors.toList());
        }
        return buildTree(list, null);
    }

    private List<MenuTreeNodeVO> buildTree(List<SysMenu> all, Long parentId) {
        List<MenuTreeNodeVO> result = new ArrayList<>();
        for (SysMenu m : all) {
            boolean match = (parentId == null && m.getParentId() == null)
                    || (parentId != null && parentId.equals(m.getParentId()));
            if (!match) continue;
            MenuTreeNodeVO vo = toVO(m);
            vo.setChildren(buildTree(all, m.getId()));
            result.add(vo);
        }
        return result;
    }

    private MenuTreeNodeVO toVO(SysMenu m) {
        MenuTreeNodeVO vo = new MenuTreeNodeVO();
        vo.setId(m.getId());
        vo.setCode(m.getCode());
        vo.setNameKey(m.getNameKey());
        vo.setPath(m.getPath());
        vo.setParentId(m.getParentId());
        vo.setSortOrder(m.getSortOrder() != null ? m.getSortOrder() : 0);
        vo.setIcon(m.getIcon());
        vo.setPermission(m.getPermission());
        vo.setStatus(m.getStatus());
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(MenuCreateDTO dto) {
        if (sysMenuMapper.selectByCode(dto.getCode()) != null) {
            log.warn("create menu failed, code already exists: code={}", dto.getCode());
            throw new BusinessException("菜单编码已存在: " + dto.getCode());
        }
        if (dto.getParentId() != null && sysMenuMapper.selectById(dto.getParentId()) == null) {
            log.warn("create menu failed, parent not found: parentId={}", dto.getParentId());
            throw new BusinessException("父菜单不存在");
        }
        SysMenu menu = SysMenu.builder()
                .code(dto.getCode())
                .nameKey(dto.getNameKey())
                .path(dto.getPath())
                .parentId(dto.getParentId())
                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0)
                .icon(dto.getIcon())
                .permission(dto.getPermission())
                .status(dto.getStatus() != null ? dto.getStatus() : "enabled")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        sysMenuMapper.insert(menu);
        log.info("create menu, code={}, id={}", dto.getCode(), menu.getId());
        return menu.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, MenuUpdateDTO dto) {
        SysMenu exist = sysMenuMapper.selectById(id);
        if (exist == null) {
            log.warn("update menu failed, menu not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "菜单不存在");
        }
        SysMenu menu = SysMenu.builder()
                .id(id)
                .updatedAt(LocalDateTime.now())
                .build();
        if (dto.getNameKey() != null) menu.setNameKey(dto.getNameKey());
        if (dto.getPath() != null) menu.setPath(dto.getPath());
        if (dto.getSortOrder() != null) menu.setSortOrder(dto.getSortOrder());
        if (dto.getIcon() != null) menu.setIcon(dto.getIcon());
        if (dto.getPermission() != null) menu.setPermission(dto.getPermission());
        if (dto.getStatus() != null) menu.setStatus(dto.getStatus());
        sysMenuMapper.updateById(menu);
        log.info("update menu, id={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        SysMenu exist = sysMenuMapper.selectById(id);
        if (exist == null) {
            log.warn("delete menu failed, menu not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "菜单不存在");
        }
        int childCount = sysMenuMapper.countByParentId(id);
        if (childCount > 0) {
            log.warn("delete menu failed, has children: id={}, childCount={}", id, childCount);
            throw new BusinessException("存在子菜单，无法删除");
        }
        sysMenuMapper.deleteById(id);
        log.info("delete menu, id={}, code={}", id, exist.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(MenuOrderDTO dto) {
        if (dto.getOrders() == null || dto.getOrders().isEmpty()) {
            return;
        }
        for (MenuOrderDTO.OrderItem item : dto.getOrders()) {
            SysMenu menu = sysMenuMapper.selectById(item.getId());
            if (menu != null) {
                SysMenu update = SysMenu.builder()
                        .id(item.getId())
                        .sortOrder(item.getSortOrder())
                        .updatedAt(LocalDateTime.now())
                        .build();
                sysMenuMapper.updateById(update);
            }
        }
    }
}
