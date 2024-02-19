package io.github.georgeanson.impl.oracle;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.GroupCapacityMapper;
import io.github.georgeanson.constant.DataSourceConstantExtension;

/**
 * @Author Anson
 * @Create 2024-02-06
 * @Description <br/>
 */

public class GroupCapacityMapperByOracle extends AbstractMapper implements GroupCapacityMapper {
    @Override
    public String insertIntoSelect() {
        return "INSERT INTO GROUP_CAPACITY (GROUP_ID, QUOTA, `USAGE`, `MAX_SIZE`, MAX_AGGR_COUNT, MAX_AGGR_SIZE,GMT_CREATE,"
                + " GMT_MODIFIED) SELECT ?, ?, COUNT(*), ?, ?, ?, ?, ? FROM CONFIG_INFO";
    }

    @Override
    public String insertIntoSelectByWhere() {
        return "INSERT INTO GROUP_CAPACITY (GROUP_ID, QUOTA,`USAGE`, `MAX_SIZE`, MAX_AGGR_COUNT, MAX_AGGR_SIZE, GMT_CREATE,"
                + " GMT_MODIFIED) SELECT ?, ?, COUNT(*), ?, ?, ?, ?, ? FROM CONFIG_INFO WHERE GROUP_ID=? AND TENANT_ID = ''";
    }

    @Override
    public String incrementUsageByWhereQuotaEqualZero() {
        return "UPDATE GROUP_CAPACITY SET `USAGE` = `USAGE` + 1, GMT_MODIFIED = ? WHERE GROUP_ID = ? AND `USAGE` < ? AND QUOTA = 0";
    }

    @Override
    public String incrementUsageByWhereQuotaNotEqualZero() {
        return "UPDATE GROUP_CAPACITY SET `USAGE` = `USAGE` + 1, GMT_MODIFIED = ? WHERE GROUP_ID = ? AND `USAGE` < QUOTA AND QUOTA != 0";
    }

    @Override
    public String incrementUsageByWhere() {
        return "UPDATE GROUP_CAPACITY SET `USAGE` = `USAGE` + 1, GMT_MODIFIED = ? WHERE GROUP_ID = ?";
    }

    @Override
    public String decrementUsageByWhere() {
        return "UPDATE GROUP_CAPACITY SET `USAGE` = `USAGE` - 1, GMT_MODIFIED = ? WHERE GROUP_ID = ? AND `USAGE` > 0";
    }

    @Override
    public String updateUsage() {
        return "UPDATE GROUP_CAPACITY SET `USAGE` = (SELECT COUNT(*) FROM CONFIG_INFO), GMT_MODIFIED = ? WHERE GROUP_ID = ?";
    }

    @Override
    public String updateUsageByWhere() {
        return "UPDATE GROUP_CAPACITY SET `USAGE` = (SELECT COUNT(*) FROM CONFIG_INFO WHERE GROUP_ID=? AND TENANT_ID = ''),"
                + " GMT_MODIFIED = ? WHERE GROUP_ID= ?";
    }

    @Override
    public String selectGroupInfoBySize() {
        return "SELECT ID, GROUP_ID FROM GROUP_CAPACITY WHERE ID > ? ROWNUM > ?";
    }

    @Override
    public String getTableName() {
        return TableConstant.GROUP_CAPACITY;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.ORACLE;
    }
}
