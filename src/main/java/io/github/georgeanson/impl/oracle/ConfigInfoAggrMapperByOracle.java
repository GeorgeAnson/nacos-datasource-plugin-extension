package io.github.georgeanson.impl.oracle;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoAggrMapper;
import io.github.georgeanson.constant.DataSourceConstantExtension;

import java.util.List;

/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class ConfigInfoAggrMapperByOracle extends AbstractMapper implements ConfigInfoAggrMapper {

    /**
     * 批量删除
     *
     * @param datumList
     * @return String
     */
    @Override
    public String batchRemoveAggr(List<String> datumList) {
        final StringBuilder datumString = new StringBuilder();
        for (String datum : datumList) {
            datumString.append('\'').append(datum).append("',");
        }
        datumString.deleteCharAt(datumString.length() - 1);
        return "DELETE FROM CONFIG_INFO_AGGE WHERE DATA_ID = ? AND GROUP_ID = ? AND (TENANT_ID = ? OR TENANT_ID IS NULL) AND DATUM_ID IN ("
                + datumString + ")";
    }

    @Override
    public String aggrConfigInfoCount(int size, boolean isIn) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM CONFIG_INFO_AGGE WHERE DATA_ID = ? AND GROUP_ID = ? AND (TENANT_ID = ? OR TENANT_ID IS NULL) AND DATUM_ID");
        if (isIn) {
            sql.append(" IN (");
        }
        else {
            sql.append(" NOT IN (");
        }
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append('?');
        }
        sql.append(')');

        return sql.toString();
    }

    @Override
    public String findConfigInfoAggrIsOrdered() {
        return "SELECT DATA_ID,GROUP_ID,TENANT_ID,DATUM_ID,APP_NAME,CONTENT FROM "
                + "CONFIG_INFO_AGGE WHERE DATA_ID = ? AND GROUP_ID = ? AND (TENANT_ID = ? OR TENANT_ID IS NULL) ORDER BY DATUM_ID";
    }

    @Override
    public String findConfigInfoAggrByPageFetchRows(int startRow, int pageSize) {
        return "SELECT * FROM (" +
                    "SELECT TMP.*, ROWNUM RN FROM ("
                        +"SELECT DATA_ID,GROUP_ID,TENANT_ID,DATUM_ID,APP_NAME,CONTENT FROM CONFIG_INFO_AGGE WHERE DATA_ID= ? AND GROUP_ID= ? AND (TENANT_ID= ? OR TENANT_ID IS NULL) ORDER BY DATUM_ID"+
                    ")TMP WHERE ROWNUM <= " +(startRow + pageSize)+
                ") WHERE RN >= "+startRow;
    }

    @Override
    public String findAllAggrGroupByDistinct() {
        return "SELECT DISTINCT DATA_ID, GROUP_ID, TENANT_ID FROM CONFIG_INFO_AGGE";
    }

    @Override
    public String getTableName() {
        return TableConstant.CONFIG_INFO_AGGR;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.ORACLE;
    }
}
