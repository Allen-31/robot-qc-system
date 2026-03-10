package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.domain.dto.MenuCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.MenuOrderDTO;
import com.zioneer.robotqcsystem.domain.dto.MenuUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.MenuTreeNodeVO;

import java.util.List;

/**
 * 菜单管理服务
 */
public interface MenuService {

    /**
     * 获取菜单树（或扁平列表）
     *
     * @param status enabled / disabled / 空为全部
     * @param tree   true 返回树形，false 返回扁平
     */
    List<MenuTreeNodeVO> getMenus(String status, boolean tree);

    Long create(MenuCreateDTO dto);

    void update(Long id, MenuUpdateDTO dto);

    void deleteById(Long id);

    void updateOrder(MenuOrderDTO dto);
}
