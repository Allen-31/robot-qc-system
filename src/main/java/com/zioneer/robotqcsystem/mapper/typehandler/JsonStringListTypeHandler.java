package com.zioneer.robotqcsystem.mapper.typehandler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * List&lt;String&gt; 与 DB jsonb/varchar(JSON) 互转。仅在 Mapper XML 中显式声明使用。
 */
public class JsonStringListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter == null ? "[]" : JSONUtil.toJsonStr(parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private static List<String> parse(String json) {
        if (StrUtil.isBlank(json)) {
            return Collections.emptyList();
        }
        List<String> list = JSONUtil.toList(JSONUtil.parseArray(json), String.class);
        return list == null ? Collections.emptyList() : list;
    }
}
