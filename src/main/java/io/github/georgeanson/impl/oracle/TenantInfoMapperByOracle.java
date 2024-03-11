package io.github.georgeanson.impl.oracle;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.TenantInfoMapper;
import io.github.georgeanson.constant.DataSourceConstantExtension;

/**
 * @Author Anson
 * @Create 2024-02-06
 * @Description <br/>
 */

public class TenantInfoMapperByOracle extends AbstractMapper implements TenantInfoMapper {
    @Override
    public String getTableName() {
        return TableConstant.TENANT_INFO;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.ORACLE;
    }
}
