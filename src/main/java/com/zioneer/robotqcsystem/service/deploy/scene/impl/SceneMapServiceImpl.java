package com.zioneer.robotqcsystem.service.deploy.scene.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.SceneMapCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.SceneMapQuery;
import com.zioneer.robotqcsystem.domain.dto.SceneMapUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.SceneMap;
import com.zioneer.robotqcsystem.domain.vo.SceneMapVO;
import com.zioneer.robotqcsystem.mapper.SceneMapMapper;
import com.zioneer.robotqcsystem.service.deploy.scene.SceneMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 场景地图业务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SceneMapServiceImpl implements SceneMapService {

    private final SceneMapMapper sceneMapMapper;

    @Override
    public PageResult<SceneMapVO> page(SceneMapQuery query) {
        long total = sceneMapMapper.countList(query.getKeyword(), query.getType(),
                query.getEditStatus(), query.getPublishStatus());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<SceneMap> list = sceneMapMapper.selectList(query.getKeyword(), query.getType(),
                query.getEditStatus(), query.getPublishStatus(), query.getOffset(), query.getPageSize());
        List<SceneMapVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public SceneMapVO getByCode(String code) {
        SceneMap map = sceneMapMapper.selectByCode(code);
        if (map == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "地图不存在");
        }
        return toVO(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SceneMapCreateDTO dto) {
        if (sceneMapMapper.selectByCode(dto.getCode()) != null) {
            log.warn("create scene map failed, code already exists: {}", dto.getCode());
            throw new BusinessException("地图编码已存在: " + dto.getCode());
        }
        LocalDateTime now = LocalDateTime.now();
        SceneMap map = SceneMap.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .type(dto.getType())
                .editStatus(dto.getEditStatus())
                .publishStatus(dto.getPublishStatus())
                .mapFileUrl(dto.getMapFileUrl())
                .mapVersion(dto.getMapVersion())
                .resolution(dto.getResolution())
                .originX(dto.getOriginX())
                .originY(dto.getOriginY())
                .editedBy("admin")
                .editedAt(now)
                .publishedBy("published".equalsIgnoreCase(dto.getPublishStatus()) ? "admin" : null)
                .publishedAt("published".equalsIgnoreCase(dto.getPublishStatus()) ? now : null)
                .createdAt(now)
                .updatedAt(now)
                .build();
        sceneMapMapper.insert(map);
        log.info("create scene map, code={}", dto.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String code, SceneMapUpdateDTO dto) {
        SceneMap exist = sceneMapMapper.selectByCode(code);
        if (exist == null) {
            log.warn("update scene map failed, not found: code={}", code);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "地图不存在");
        }
        LocalDateTime now = LocalDateTime.now();
        SceneMap map = SceneMap.builder()
                .code(code)
                .name(dto.getName())
                .type(dto.getType())
                .editStatus(dto.getEditStatus())
                .publishStatus(dto.getPublishStatus())
                .mapFileUrl(dto.getMapFileUrl())
                .mapVersion(dto.getMapVersion())
                .resolution(dto.getResolution())
                .originX(dto.getOriginX())
                .originY(dto.getOriginY())
                .editedBy("admin")
                .editedAt(now)
                .publishedBy("published".equalsIgnoreCase(dto.getPublishStatus()) ? "admin" : null)
                .publishedAt("published".equalsIgnoreCase(dto.getPublishStatus()) ? now : null)
                .updatedAt(now)
                .build();
        sceneMapMapper.updateByCode(map);
        log.info("update scene map, code={}", code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        if (sceneMapMapper.selectByCode(code) == null) {
            log.warn("delete scene map failed, not found: code={}", code);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "地图不存在");
        }
        sceneMapMapper.deleteByCode(code);
        log.info("delete scene map, code={}", code);
    }

    private SceneMapVO toVO(SceneMap map) {
        return SceneMapVO.builder()
                .id(map.getId())
                .code(map.getCode())
                .name(map.getName())
                .type(map.getType())
                .editStatus(map.getEditStatus())
                .publishStatus(map.getPublishStatus())
                .mapFileUrl(map.getMapFileUrl())
                .mapVersion(map.getMapVersion())
                .resolution(map.getResolution())
                .originX(map.getOriginX())
                .originY(map.getOriginY())
                .editedBy(map.getEditedBy())
                .editedAt(map.getEditedAt())
                .publishedBy(map.getPublishedBy())
                .publishedAt(map.getPublishedAt())
                .createdAt(map.getCreatedAt())
                .updatedAt(map.getUpdatedAt())
                .build();
    }
}
