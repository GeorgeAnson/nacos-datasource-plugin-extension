package io.github.georgeanson.impl.db2;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import io.github.georgeanson.constant.DataSourceConstantExtension;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoTagMapper;


/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class ConfigInfoTagMapperByDb2 extends AbstractMapper implements ConfigInfoTagMapper {

    @Override
    public String updateConfigInfo4TagCas() {
        return "UPDATE CONFIG_INFO_TAG SET content = ?, md5 = ?, src_ip = ?,src_user = ?,gmt_modified = ?,app_name = ? "
                + "WHERE data_id = ? AND group_id = ? AND tenant_id = ? AND tag_id = ? AND (md5 = ? OR md5 IS NULL OR md5 = '')";
    }

    @Override
    public String findAllConfigInfoTagForDumpAllFetchRows(int startRow, int pageSize) {
        return " SELECT t.id,data_id,group_id,tenant_id,tag_id,app_name,content,md5,gmt_modified "
                + " FROM (  SELECT id FROM CONFIG_INFO_TAG  ORDER BY id LIMIT " + pageSize + " OFFSET " + startRow
                + " ) " + "g, config_info_tag t  WHERE g.id = t.id  ";
    }

    @Override
    public String getTableName() {
        return TableConstant.CONFIG_INFO_TAG;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.DB2;
    }

}
