package io.github.georgeanson.impl.oracle;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoBetaMapper;
import io.github.georgeanson.constant.DataSourceConstantExtension;

/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class ConfigInfoBetaMapperByOracle extends AbstractMapper implements ConfigInfoBetaMapper {

    @Override
    public String updateConfigInfo4BetaCas() {
        return "UPDATE config_info_beta SET content = ?,md5 = ?,beta_ips = ?,src_ip = ?,src_user = ?,gmt_modified = ?,app_name = ? "
                + "WHERE data_id = ? AND group_id = ? AND (tenant_id = ? OR tenant_id IS NULL) AND (md5 = ? or md5 is null or md5 = '')";
    }

    @Override
    public String findAllConfigInfoBetaForDumpAllFetchRows(int startRow, int pageSize) {
        return "SELECT t.id,data_id,group_id,tenant_id,app_name,content,md5,gmt_modified,beta_ips,encrypted_data_key "
                + " FROM ( SELECT rownum ROW_ID,id FROM config_info_beta  WHERE  ROW_ID<= " + (startRow + pageSize)
                + " ORDER BY id )" + "  g, config_info_beta t WHERE g.id = t.id AND g.ROW_ID > " + startRow;
    }

    @Override
    public String getTableName() {
        return TableConstant.CONFIG_INFO_BETA;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.ORACLE;
    }

}
