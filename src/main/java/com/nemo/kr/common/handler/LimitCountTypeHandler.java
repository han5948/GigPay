package com.nemo.kr.common.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import com.nemo.kr.column.LimitCount;


@MappedTypes(LimitCount.class)
public class LimitCountTypeHandler<E extends Enum<E>> implements TypeHandler<LimitCount> {

	private Class<E> type;

	public LimitCountTypeHandler(Class<E> type) {
        this.type = type;
    }
 
    public void setParameter(PreparedStatement preparedStatement, int i, LimitCount status, JdbcType jdbcType) throws
        SQLException {
        preparedStatement.setString(i, status.getValue());
    }

	@Override
	public LimitCount getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		String limitCountValue = rs.getString(columnName);
		return getLimitCount(limitCountValue);
	}

	@Override
	public LimitCount getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		String limitCountValue = rs.getString(columnIndex);
		return getLimitCount(limitCountValue);
	}

	@Override
	public LimitCount getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		String limitCountValue = cs.getString(columnIndex);
		return getLimitCount(limitCountValue);
	}
	
	private LimitCount getLimitCount(String limitCountValue) {
        try {
        	LimitCount[] enumConstants = (LimitCount[])type.getEnumConstants();
            for (LimitCount limitCount : enumConstants) {
                if (limitCount.getValue().equals(limitCountValue)) {
                    return limitCount;
                }
            }
            return null;
        } catch (Exception exception) {
            throw new TypeException("Can't make enum object '" + type + "'", exception);
        }
    }
   
}
