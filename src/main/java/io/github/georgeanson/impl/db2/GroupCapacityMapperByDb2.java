package io.github.georgeanson.impl.db2;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import io.github.georgeanson.constant.DataSourceConstantExtension;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.GroupCapacityMapper;


/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class GroupCapacityMapperByDb2 extends AbstractMapper implements GroupCapacityMapper {

    @Override
    public String insertIntoSelect() {
        return "INSERT INTO GROUP_CAPACITY (group_id, quota, usage, max_size, max_aggr_count, max_aggr_size,gmt_create,"
                + " gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM CONFIG_INFO";
    }

    @Override
    public String insertIntoSelectByWhere() {
        return "INSERT INTO GROUP_CAPACITY (group_id, quota,usage, max_size, max_aggr_count, max_aggr_size, gmt_create,"
                + " gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM CONFIG_INFO WHERE group_id=? AND tenant_id = ''";
    }

    @Override
    public String incrementUsageByWhereQuotaNotEqualZero() {
        return "UPDATE GROUP_CAPACITY SET usage = usage + 1, gmt_modified = ? WHERE group_id = ? AND usage < quota AND quota != 0";
    }

    @Override
    public String incrementUsageByWhereQuotaEqualZero() {
        return "UPDATE GROUP_CAPACITY SET usage = usage + 1, gmt_modified = ? WHERE group_id = ? AND usage < ? AND quota = 0";
    }

    @Override
    public String incrementUsageByWhere() {
        return "UPDATE GROUP_CAPACITY SET usage = usage + 1, gmt_modified = ? WHERE group_id = ?";
    }

    @Override
    public String decrementUsageByWhere() {
        return "UPDATE GROUP_CAPACITY SET usage = usage - 1, gmt_modified = ? WHERE group_id = ? AND usage > 0";
    }

    @Override
    public String updateUsage() {
        return "UPDATE GROUP_CAPACITY SET usage = (SELECT count(*) FROM CONFIG_INFO), gmt_modified = ? WHERE group_id = ?";
    }

    @Override
    public String updateUsageByWhere() {
        return "UPDATE GROUP_CAPACITY SET usage = (SELECT count(*) FROM CONFIG_INFO WHERE group_id=? AND tenant_id = ''),"
                + " gmt_modified = ? WHERE group_id= ?";
    }

    @Override
    public String selectGroupInfoBySize() {
        return "SELECT id, group_id FROM GROUP_CAPACITY WHERE id > ? LIMIT ?";
    }

    @Override
    public String getTableName() {
        return TableConstant.GROUP_CAPACITY;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.DB2;
    }

}
