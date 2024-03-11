package io.github.georgeanson.impl.db2;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import io.github.georgeanson.constant.DataSourceConstantExtension;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.TenantCapacityMapper;


/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class TenantCapacityMapperByDb2 extends AbstractMapper implements TenantCapacityMapper {


    @Override
    public String incrementUsageWithDefaultQuotaLimit() {
        return "UPDATE TENANT_CAPACITY SET USAGE = USAGE + 1, GMT_MODIFIED = ? WHERE TENANT_ID = ? AND USAGE <"
                + " ? AND QUOTA = 0";
    }

    @Override
    public String incrementUsageWithQuotaLimit() {
        return "UPDATE TENANT_CAPACITY SET USAGE = USAGE + 1, GMT_MODIFIED = ? WHERE TENANT_ID = ? AND USAGE < "
                + "QUOTA AND QUOTA != 0";
    }

    @Override
    public String incrementUsage() {
        return "UPDATE TENANT_CAPACITY SET USAGE = USAGE + 1, GMT_MODIFIED = ? WHERE TENANT_ID = ?";
    }

    @Override
    public String decrementUsage() {
        return "UPDATE TENANT_CAPACITY SET USAGE = USAGE - 1, GMT_MODIFIED = ? WHERE TENANT_ID = ? AND USAGE > 0";
    }

    @Override
    public String correctUsage() {
        return "UPDATE TENANT_CAPACITY SET USAGE = (SELECT COUNT(*) FROM CONFIG_INFO WHERE TENANT_ID = ?), "
                + "GMT_MODIFIED = ? WHERE TENANT_ID = ?";
    }

    @Override
    public String getCapacityList4CorrectUsage() {
        return "SELECT ID, TENANT_ID FROM TENANT_CAPACITY WHERE ID>? LIMIT ?";
    }

    @Override
    public String insertTenantCapacity() {
        return "INSERT INTO TENANT_CAPACITY (TENANT_ID, QUOTA, USAGE, MAX_SIZE, MAX_AGGR_COUNT, MAX_AGGR_SIZE,"
                + "GMT_CREATE, GMT_MODIFIED) SELECT ?, ?, COUNT(*), ?, ?, ?, ?, ? FROM CONFIG_INFO WHERE TENANT_ID=?;";
    }

    @Override
    public String getTableName() {
        return TableConstant.TENANT_CAPACITY;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.DB2;
    }

}
