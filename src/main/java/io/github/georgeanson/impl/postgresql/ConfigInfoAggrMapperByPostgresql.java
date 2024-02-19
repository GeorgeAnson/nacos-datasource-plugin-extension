package io.github.georgeanson.impl.postgresql;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import io.github.georgeanson.constant.DataSourceConstantExtension;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoAggrMapper;

import java.util.List;

/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class ConfigInfoAggrMapperByPostgresql extends AbstractMapper implements ConfigInfoAggrMapper {

    /**
     * 批量删除
     *
     * @param datumList
     * @return String
     */
    @Override
    public String batchRemoveAggr(List<String> datumList) {
        final StringBuilder placeholderString = new StringBuilder();
        for (int i = 0; i < datumList.size(); i++) {
            if (i != 0) {
                placeholderString.append(", ");
            }
            placeholderString.append('?');
        }
        return "DELETE FROM CONFIG_INFO_AGGR WHERE DATA_ID = ? AND GROUP_ID = ? AND TENANT_ID = ? AND DATUM_ID IN ("
                + placeholderString + ")";
    }

    @Override
    public String aggrConfigInfoCount(int size, boolean isIn) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM CONFIG_INFO_AGGR WHERE DATA_ID = ? AND GROUP_ID = ? AND TENANT_ID = ? AND DATUM_ID");
        if (isIn) {
            sql.append(" IN (");
        } else {
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
                + "CONFIG_INFO_AGGR WHERE DATA_ID = ? AND GROUP_ID = ? AND TENANT_ID = ? ORDER BY DATUM_ID";
    }

    @Override
    public String findConfigInfoAggrByPageFetchRows(int startRow, int pageSize) {
        return "SELECT DATA_ID,GROUP_ID,TENANT_ID,DATUM_ID,APP_NAME,CONTENT FROM CONFIG_INFO_AGGR WHERE DATA_ID= ? AND "
                + "GROUP_ID= ? AND TENANT_ID= ? ORDER BY DATUM_ID LIMIT " + pageSize + " OFFSET " + startRow;
    }

    @Override
    public String findAllAggrGroupByDistinct() {
        return "SELECT DISTINCT DATA_ID, GROUP_ID, TENANT_ID FROM CONFIG_INFO_AGGR";
    }

    @Override
    public String getTableName() {
        return TableConstant.CONFIG_INFO_AGGR;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.POSTGRESQL;
    }
}
