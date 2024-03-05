package io.github.georgeanson.impl.postgresql;

import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import io.github.georgeanson.constant.DataSourceConstantExtension;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigTagsRelationMapper;

import java.util.Map;

/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class ConfigTagsRelationMapperByPostgresql extends AbstractMapper implements ConfigTagsRelationMapper {

    @Override
    public String findConfigInfo4PageCountRows(final Map<String, String> params, final int tagSize) {
        final String appName = params.get("appName");
        final String dataId = params.get("dataId");
        final String group = params.get("group");
        StringBuilder where = new StringBuilder(" WHERE ");
        final String sqlCount = "SELECT COUNT(*) FROM CONFIG_INFO  A LEFT JOIN CONFIG_TAGS_RELATION B ON A.ID=B.ID";
        where.append(" A.TENANT_ID=? ");
        if (StringUtils.isNotBlank(dataId)) {
            where.append(" AND A.DATA_ID=? ");
        }
        if (StringUtils.isNotBlank(group)) {
            where.append(" AND A.GROUP_ID=? ");
        }
        if (StringUtils.isNotBlank(appName)) {
            where.append(" AND A.APP_NAME=? ");
        }
        where.append(" AND B.TAG_NAME IN (");
        for (int i = 0; i < tagSize; i++) {
            if (i != 0) {
                where.append(", ");
            }
            where.append('?');
        }
        where.append(") ");
        return sqlCount + where;
    }

    @Override
    public String findConfigInfo4PageFetchRows(Map<String, String> params, int tagSize, int startRow, int pageSize) {
        final String appName = params.get("appName");
        final String dataId = params.get("dataId");
        final String group = params.get("group");
        StringBuilder where = new StringBuilder(" WHERE ");
        final String sql = "SELECT A.ID,A.DATA_ID,A.GROUP_ID,A.TENANT_ID,A.APP_NAME,A.CONTENT FROM CONFIG_INFO  A LEFT JOIN "
                + "CONFIG_TAGS_RELATION B ON A.ID=B.ID";

        where.append(" A.TENANT_ID=? ");

        if (StringUtils.isNotBlank(dataId)) {
            where.append(" AND A.DATA_ID=? ");
        }
        if (StringUtils.isNotBlank(group)) {
            where.append(" AND A.GROUP_ID=? ");
        }
        if (StringUtils.isNotBlank(appName)) {
            where.append(" AND A.APP_NAME=? ");
        }

        where.append(" AND B.TAG_NAME IN (");
        for (int i = 0; i < tagSize; i++) {
            if (i != 0) {
                where.append(", ");
            }
            where.append('?');
        }
        where.append(") ");
        return sql + where + " LIMIT " + pageSize + " OFFSET " + startRow;
    }

    @Override
    public String findConfigInfoLike4PageCountRows(final Map<String, String> params, int tagSize) {
        final String appName = params.get("appName");
        final String content = params.get("content");
        final String dataId = params.get("dataId");
        final String group = params.get("group");
        StringBuilder where = new StringBuilder(" WHERE ");
        final String sqlCountRows = "SELECT COUNT(*) FROM CONFIG_INFO  A LEFT JOIN CONFIG_TAGS_RELATION B ON A.ID=B.ID ";

        where.append(" A.TENANT_ID LIKE ? ");
        if (!StringUtils.isBlank(dataId)) {
            where.append(" AND A.DATA_ID LIKE ? ");
        }
        if (!StringUtils.isBlank(group)) {
            where.append(" AND A.GROUP_ID LIKE ? ");
        }
        if (!StringUtils.isBlank(appName)) {
            where.append(" AND A.APP_NAME = ? ");
        }
        if (!StringUtils.isBlank(content)) {
            where.append(" AND A.CONTENT LIKE ? ");
        }

        where.append(" AND B.TAG_NAME IN (");
        for (int i = 0; i < tagSize; i++) {
            if (i != 0) {
                where.append(", ");
            }
            where.append('?');
        }
        where.append(") ");
        return sqlCountRows + where;
    }

    @Override
    public String findConfigInfoLike4PageFetchRows(final Map<String, String> params, int tagSize, int startRow,
                                                   int pageSize) {
        final String appName = params.get("appName");
        final String content = params.get("content");
        final String dataId = params.get("dataId");
        final String group = params.get("group");
        StringBuilder where = new StringBuilder(" WHERE ");
        final String sqlFetchRows = "SELECT A.ID,A.DATA_ID,A.GROUP_ID,A.TENANT_ID,A.APP_NAME,A.CONTENT "
                + "FROM CONFIG_INFO A LEFT JOIN CONFIG_TAGS_RELATION B ON A.ID=B.ID ";

        where.append(" A.TENANT_ID LIKE ? ");
        if (!StringUtils.isBlank(dataId)) {
            where.append(" AND A.DATA_ID LIKE ? ");
        }
        if (!StringUtils.isBlank(group)) {
            where.append(" AND A.GROUP_ID LIKE ? ");
        }
        if (!StringUtils.isBlank(appName)) {
            where.append(" AND A.APP_NAME = ? ");
        }
        if (!StringUtils.isBlank(content)) {
            where.append(" AND A.CONTENT LIKE ? ");
        }

        where.append(" AND B.TAG_NAME IN (");
        for (int i = 0; i < tagSize; i++) {
            if (i != 0) {
                where.append(", ");
            }
            where.append('?');
        }
        where.append(") ");
        return sqlFetchRows + where + " LIMIT " + startRow + "," + pageSize;
    }

    @Override
    public String getTableName() {
        return TableConstant.CONFIG_TAGS_RELATION;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.POSTGRESQL;
    }

}
