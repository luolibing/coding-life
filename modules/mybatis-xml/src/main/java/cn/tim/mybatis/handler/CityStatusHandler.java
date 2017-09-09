package cn.tim.mybatis.handler;

import cn.tim.mybatis.entity.enums.CityStatus;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: luolibing
 * Date: 2017/9/9 11:25
 */
public class CityStatusHandler extends EnumOrdinalTypeHandler<CityStatus> {

    public CityStatusHandler(Class<CityStatus> type) {
        super(type);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CityStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.value);
    }

    @Override
    public CityStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return CityStatus.fromValue(rs.getInt(columnName));
    }

    @Override
    public CityStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return CityStatus.fromValue(rs.getInt(columnIndex));
    }

    @Override
    public CityStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return CityStatus.fromValue(cs.getInt(columnIndex));
    }
}
