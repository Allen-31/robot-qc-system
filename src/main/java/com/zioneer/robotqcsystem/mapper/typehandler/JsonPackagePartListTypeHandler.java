package com.zioneer.robotqcsystem.mapper.typehandler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zioneer.robotqcsystem.domain.entity.OperationPackagePart;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * List&lt;OperationPackagePart&gt; 与 DB jsonb 互转。仅在 Mapper XML 中对该列显式指定 typeHandler 使用，不注册为全局 List 处理器。
 */
public class JsonPackagePartListTypeHandler extends BaseTypeHandler<List<OperationPackagePart>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<OperationPackagePart> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter == null ? "[]" : JSONUtil.toJsonStr(parameter));
    }

    @Override
    public List<OperationPackagePart> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }

    @Override
    public List<OperationPackagePart> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public List<OperationPackagePart> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private static List<OperationPackagePart> parse(String json) {
        if (StrUtil.isBlank(json)) {
            return Collections.emptyList();
        }
        List<OperationPackagePart> list = JSONUtil.toList(JSONUtil.parseArray(json), OperationPackagePart.class);
        return list == null ? Collections.emptyList() : list;
    }
}
