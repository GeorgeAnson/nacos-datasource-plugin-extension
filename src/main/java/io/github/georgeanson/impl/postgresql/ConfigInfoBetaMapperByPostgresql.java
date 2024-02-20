package io.github.georgeanson.impl.postgresql;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import io.github.georgeanson.constant.DataSourceConstantExtension;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoBetaMapper;


/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class ConfigInfoBetaMapperByPostgresql extends AbstractMapper implements ConfigInfoBetaMapper {

    @Override
    public String updateConfigInfo4BetaCas() {
        return "UPDATE CONFIG_INFO_BETA SET CONTENT = ?,MD5 = ?,BETA_IPS = ?,SRC_IP = ?,SRC_USER = ?,GMT_MODIFIED = ?,APP_NAME = ? "
                + "WHERE DATA_ID = ? AND GROUP_ID = ? AND TENANT_ID = ? AND (MD5 = ? OR MD5 IS NULL OR MD5 = '')";
    }

    @Override
    public String findAllConfigInfoBetaForDumpAllFetchRows(int startRow, int pageSize) {
        return " SELECT T.ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT,MD5,GMT_MODIFIED,BETA_IPS,ENCRYPTED_DATA_KEY "
                + " FROM ( SELECT ID FROM CONFIG_INFO_BETA  ORDER BY ID LIMIT " + pageSize + " OFFSET " + startRow
                + " )" + "  G, CONFIG_INFO_BETA T WHERE G.ID = T.ID ";
    }

    @Override
    public String getTableName() {
        return TableConstant.CONFIG_INFO_BETA;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.POSTGRESQL;
    }

}
