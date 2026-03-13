package com.zioneer.robotqcsystem.service.qc;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.QcWorkstationCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkstationUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkstationConfigVO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkstationVO;

import java.util.List;

/**
 * 工作站（业务 2.1.1 + 配置 2.2.1）
 */
public interface QcWorkstationService {

    /** 业务列表：keyword，返回 list+total */
    PageResult<QcWorkstationVO> listBusiness(String keyword);

    /** 配置列表：keyword、workshopCode，返回数组 */
    List<QcWorkstationConfigVO> listConfig(String keyword, String workshopCode);

    QcWorkstationVO getByIdBusiness(Long id);

    QcWorkstationConfigVO getByIdConfig(Long id);

    QcWorkstationConfigVO create(QcWorkstationCreateDTO dto);

    void update(Long id, QcWorkstationUpdateDTO dto);

    void deleteById(Long id);
}
