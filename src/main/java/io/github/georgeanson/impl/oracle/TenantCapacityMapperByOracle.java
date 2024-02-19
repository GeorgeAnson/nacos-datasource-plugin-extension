package io.github.georgeanson.impl.oracle;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.TenantCapacityMapper;
import io.github.georgeanson.constant.DataSourceConstantExtension;

/**
 * @Author Anson
 * @Create 2024-02-06
 * @Description <br/>
 */

public class TenantCapacityMapperByOracle extends AbstractMapper implements TenantCapacityMapper {
    @Override
    public String incrementUsageWithDefaultQuotaLimit() {
        return "UPDATE TENANT_CAPACITY SET `USAGE` = `USAGE` + 1, GMT_MODIFIED = ? WHERE ((TENANT_ID = ? OR TENANT_ID IS NULL) OR TENANT_ID IS NULL) AND `USAGE` <"
                + " ? AND QUOTA = 0";
    }

    @Override
    public String incrementUsageWithQuotaLimit() {
        return "UPDATE TENANT_CAPACITY SET `USAGE` = `USAGE` + 1, GMT_MODIFIED = ? WHERE (TENANT_ID = ? OR TENANT_ID IS NULL) AND `USAGE` < "
                + "QUOTA AND QUOTA != 0";
    }

    @Override
    public String incrementUsage() {
        return "UPDATE TENANT_CAPACITY SET `USAGE` = `USAGE` + 1, GMT_MODIFIED = ? WHERE (TENANT_ID = ? OR TENANT_ID IS NULL)";
    }

    @Override
    public String decrementUsage() {
        return "UPDATE TENANT_CAPACITY SET `USAGE` = `USAGE` - 1, GMT_MODIFIED = ? WHERE (TENANT_ID = ? OR TENANT_ID IS NULL) AND `USAGE` > 0";
    }

    @Override
    public String correctUsage() {
        return "UPDATE TENANT_CAPACITY SET `USAGE` = (SELECT COUNT(*) FROM CONFIG_INFO WHERE (TENANT_ID = ? OR TENANT_ID IS NULL)), "
                + "GMT_MODIFIED = ? WHERE (TENANT_ID = ? OR TENANT_ID IS NULL)";
    }

    @Override
    public String getCapacityList4CorrectUsage() {
        return "SELECT ID, TENANT_ID FROM TENANT_CAPACITY WHERE ID> AND  ROWNUM > ?";
    }

    @Override
    public String insertTenantCapacity() {
        return "INSERT INTO TENANT_CAPACITY (TENANT_ID, QUOTA, `USAGE`, `MAX_SIZE`, MAX_AGGR_COUNT, MAX_AGGR_SIZE, "
                + "GMT_CREATE, GMT_MODIFIED) SELECT ?, ?, COUNT(*), ?, ?, ?, ?, ? FROM CONFIG_INFO WHERE TENANT_ID=? OR TENANT_ID IS NULL;";
    }

    @Override
    public String getTableName() {
        return TableConstant.TENANT_CAPACITY;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.ORACLE;
    }
}
