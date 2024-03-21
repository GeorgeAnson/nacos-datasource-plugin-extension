package io.github.georgeanson.impl.postgresql;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import io.github.georgeanson.constant.DataSourceConstantExtension;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.HistoryConfigInfoMapper;


/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class HistoryConfigInfoMapperByPostgresql extends AbstractMapper implements HistoryConfigInfoMapper {

    @Override
    public String removeConfigHistory() {
        return "DELETE FROM HIS_CONFIG_INFO WHERE GMT_MODIFIED < ? LIMIT ?";
    }

    @Override
    public String findConfigHistoryCountByTime() {
        return "SELECT COUNT(*) FROM HIS_CONFIG_INFO WHERE GMT_MODIFIED < ?";
    }

    @Override
    public String findDeletedConfig() {
        return "SELECT DISTINCT DATA_ID, GROUP_ID, TENANT_ID FROM HIS_CONFIG_INFO WHERE OP_TYPE = 'D' AND GMT_MODIFIED >= ? AND GMT_MODIFIED <= ?";
    }

    @Override
    public String findConfigHistoryFetchRows() {
        return "SELECT NID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,SRC_IP,SRC_USER,OP_TYPE,GMT_CREATE,GMT_MODIFIED FROM HIS_CONFIG_INFO "
                + "WHERE DATA_ID = ? AND GROUP_ID = ? AND TENANT_ID = ? ORDER BY NID DESC";
    }

    public String pageFindConfigHistoryFetchRows(int pageNo, int pageSize) {
        final int offset = (pageNo - 1) * pageSize;
        final int limit = pageSize;
        return  "SELECT NID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,SRC_IP,SRC_USER,OP_TYPE,GMT_CREATE,GMT_MODIFIED FROM HIS_CONFIG_INFO "
                + "WHERE DATA_ID = ? AND GROUP_ID = ? AND TENANT_ID = ? ORDER BY NID DESC  LIMIT " + offset + "," + limit;
    }

    @Override
    public String detailPreviousConfigHistory() {
        return "SELECT NID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT,MD5,SRC_USER,SRC_IP,OP_TYPE,GMT_CREATE,GMT_MODIFIED "
                + "FROM HIS_CONFIG_INFO WHERE NID = (SELECT MAX(NID) FROM HIS_CONFIG_INFO WHERE ID = ?) ";
    }

    @Override
    public String getTableName() {
        return TableConstant.HIS_CONFIG_INFO;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.POSTGRESQL;
    }

}
