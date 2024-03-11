package io.github.georgeanson.impl.postgresql;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import io.github.georgeanson.constant.DataSourceConstantExtension;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Anson
 * @Create 2023-10-25
 * @Description <br/>
 */

public class ConfigInfoMapperByPostgresql extends AbstractMapper implements ConfigInfoMapper {

    private static final String DATA_ID = "dataId";

    private static final String GROUP = "group";

    private static final String APP_NAME = "appName";

    private static final String CONTENT = "content";

    private static final String TENANT = "tenant";

    @Override
    public String findConfigMaxId() {
        return "SELECT MAX(ID) FROM CONFIG_INFO";
    }

    @Override
    public String findAllDataIdAndGroup() {
        return "SELECT DISTINCT DATA_ID, GROUP_ID FROM CONFIG_INFO";
    }

    @Override
    public String findConfigInfoByAppCountRows() {
        return "SELECT COUNT(*) FROM CONFIG_INFO WHERE TENANT_ID LIKE ? AND APP_NAME= ?";
    }

    @Override
    public String findConfigInfoByAppFetchRows(int startRow, int pageSize) {
        return "SELECT ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT FROM CONFIG_INFO"
                + " WHERE TENANT_ID LIKE ? AND APP_NAME= ?" + " LIMIT " + pageSize + " OFFSET " + startRow;
    }

    @Override
    public String configInfoLikeTenantCount() {
        return "SELECT COUNT(*) FROM CONFIG_INFO WHERE TENANT_ID LIKE ?";
    }

    @Override
    public String getTenantIdList(int startRow, int pageSize) {
        return "SELECT TENANT_ID FROM CONFIG_INFO WHERE TENANT_ID != '' GROUP BY TENANT_ID LIMIT " + pageSize
                + " OFFSET " + startRow;
    }

    @Override
    public String getGroupIdList(int startRow, int pageSize) {
        return "SELECT GROUP_ID FROM CONFIG_INFO WHERE TENANT_ID ='' GROUP BY GROUP_ID LIMIT " + pageSize + " OFFSET "
                + startRow;
    }

    @Override
    public String findAllConfigKey(int startRow, int pageSize) {
        return " SELECT DATA_ID,GROUP_ID,APP_NAME  FROM ( "
                + " SELECT ID FROM CONFIG_INFO WHERE TENANT_ID LIKE ? ORDER BY ID LIMIT " + pageSize + " OFFSET "
                + startRow + " )" + " G, CONFIG_INFO T WHERE G.ID = T.ID  ";
    }

    @Override
    public String findAllConfigInfoBaseFetchRows(int startRow, int pageSize) {
        return "SELECT T.ID,DATA_ID,GROUP_ID,CONTENT,MD5"
                + " FROM ( SELECT ID FROM CONFIG_INFO ORDER BY ID LIMIT ?,?  ) "
                + " G, CONFIG_INFO T  WHERE G.ID = T.ID ";
    }

    @Override
    public String findAllConfigInfoFragment(int startRow, int pageSize) {
        return "SELECT ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT,MD5,GMT_MODIFIED,TYPE,ENCRYPTED_DATA_KEY "
                + "FROM CONFIG_INFO WHERE ID > ? ORDER BY ID ASC LIMIT " + pageSize + " OFFSET " + startRow;
    }

    @Override
    public String findChangeConfig() {
        return "SELECT DATA_ID, GROUP_ID, TENANT_ID, APP_NAME, CONTENT, GMT_MODIFIED,ENCRYPTED_DATA_KEY "
                + "FROM CONFIG_INFO WHERE GMT_MODIFIED >= ? AND GMT_MODIFIED <= ?";
    }

    @Override
    public String findChangeConfigCountRows(Map<String, String> params, final Timestamp startTime,
                                            final Timestamp endTime) {
        final String tenant = params.get(TENANT);
        final String dataId = params.get(DATA_ID);
        final String group = params.get(GROUP);
        final String appName = params.get(APP_NAME);
        final String tenantTmp = StringUtils.isBlank(tenant) ? StringUtils.EMPTY : tenant;
        final String sqlCountRows = "SELECT COUNT(*) FROM CONFIG_INFO WHERE ";
        String where = " 1=1 ";
        if (!StringUtils.isBlank(dataId)) {
            where += " AND DATA_ID LIKE ? ";
        }
        if (!StringUtils.isBlank(group)) {
            where += " AND GROUP_ID LIKE ? ";
        }

        if (!StringUtils.isBlank(tenantTmp)) {
            where += " AND TENANT_ID = ? ";
        }

        if (!StringUtils.isBlank(appName)) {
            where += " AND APP_NAME = ? ";
        }
        if (startTime != null) {
            where += " AND GMT_MODIFIED >=? ";
        }
        if (endTime != null) {
            where += " AND GMT_MODIFIED <=? ";
        }
        return sqlCountRows + where;
    }

    @Override
    public String findChangeConfigFetchRows(Map<String, String> params, final Timestamp startTime,
                                            final Timestamp endTime, int startRow, int pageSize, long lastMaxId) {
        final String tenant = params.get(TENANT);
        final String dataId = params.get(DATA_ID);
        final String group = params.get(GROUP);
        final String appName = params.get(APP_NAME);
        final String tenantTmp = StringUtils.isBlank(tenant) ? StringUtils.EMPTY : tenant;
        final String sqlFetchRows = "SELECT ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT,TYPE,MD5,GMT_MODIFIED FROM CONFIG_INFO WHERE ";
        String where = " 1=1 ";
        if (!StringUtils.isBlank(dataId)) {
            where += " AND DATA_ID LIKE ? ";
        }
        if (!StringUtils.isBlank(group)) {
            where += " AND GROUP_ID LIKE ? ";
        }

        if (!StringUtils.isBlank(tenantTmp)) {
            where += " AND TENANT_ID = ? ";
        }

        if (!StringUtils.isBlank(appName)) {
            where += " AND APP_NAME = ? ";
        }
        if (startTime != null) {
            where += " AND GMT_MODIFIED >=? ";
        }
        if (endTime != null) {
            where += " AND GMT_MODIFIED <=? ";
        }
        return sqlFetchRows + where + " AND ID > " + lastMaxId + " ORDER BY ID ASC" + " LIMIT " + 0 + " OFFSET "
                + pageSize;
    }

    @Override
    public String listGroupKeyMd5ByPageFetchRows(int startRow, int pageSize) {
        return "SELECT T.ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,MD5,TYPE,GMT_MODIFIED,ENCRYPTED_DATA_KEY FROM "
                + "( SELECT ID FROM CONFIG_INFO ORDER BY ID LIMIT " + pageSize + " OFFSET " + startRow
                + " ) G, CONFIG_INFO T WHERE G.ID = T.ID";
    }

    @Override
    public String findAllConfigInfo4Export(List<Long> ids, Map<String, String> params) {
        String tenant = params.get("tenant");
        String tenantTmp = StringUtils.isBlank(tenant) ? StringUtils.EMPTY : tenant;
        String sql = "SELECT ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT,TYPE,MD5,GMT_CREATE,GMT_MODIFIED,SRC_USER,SRC_IP,"
                + "C_DESC,C_USE,EFFECT,C_SCHEMA,ENCRYPTED_DATA_KEY FROM CONFIG_INFO";
        StringBuilder where = new StringBuilder(" WHERE ");
        List<Object> paramList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ids)) {
            where.append(" ID IN (");
            for (int i = 0; i < ids.size(); i++) {
                if (i != 0) {
                    where.append(", ");
                }
                where.append('?');
                paramList.add(ids.get(i));
            }
            where.append(") ");
        }
        else {
            where.append(" TENANT_ID= ? ");
            paramList.add(tenantTmp);
            if (!StringUtils.isBlank(params.get(DATA_ID))) {
                where.append(" AND DATA_ID LIKE ? ");
            }
            if (StringUtils.isNotBlank(params.get(GROUP))) {
                where.append(" AND GROUP_ID= ? ");
            }
            if (StringUtils.isNotBlank(params.get(APP_NAME))) {
                where.append(" AND APP_NAME= ? ");
            }
        }
        return sql + where;
    }

    @Override
    public String findConfigInfoBaseLikeCountRows(Map<String, String> params) {
        final String sqlCountRows = "SELECT COUNT(*) FROM CONFIG_INFO WHERE ";
        String where = " 1=1 AND tenant_id='' ";

        if (!StringUtils.isBlank(params.get(DATA_ID))) {
            where += " AND DATA_ID LIKE ? ";
        }
        if (!StringUtils.isBlank(params.get(GROUP))) {
            where += " AND GROUP_ID LIKE ";
        }
        if (!StringUtils.isBlank(params.get(CONTENT))) {
            where += " AND CONTENT LIKE ? ";
        }
        return sqlCountRows + where;
    }

    @Override
    public String findConfigInfoBaseLikeFetchRows(Map<String, String> params, int startRow, int pageSize) {
        final String sqlFetchRows = "SELECT ID,DATA_ID,GROUP_ID,TENANT_ID,CONTENT FROM CONFIG_INFO WHERE ";
        String where = " 1=1 AND TENANT_ID='' ";
        if (!StringUtils.isBlank(params.get(DATA_ID))) {
            where += " AND DATA_ID LIKE ? ";
        }
        if (!StringUtils.isBlank(params.get(GROUP))) {
            where += " AND GROUP_ID LIKE ";
        }
        if (!StringUtils.isBlank(params.get(CONTENT))) {
            where += " AND CONTENT LIKE ? ";
        }
        return sqlFetchRows + where + " LIMIT " + startRow + "," + pageSize;
    }

    @Override
    public String findConfigInfo4PageCountRows(Map<String, String> params) {
        final String appName = params.get(APP_NAME);
        final String dataId = params.get(DATA_ID);
        final String group = params.get(GROUP);
        final String sqlCount = "SELECT COUNT(*) FROM CONFIG_INFO";
        StringBuilder where = new StringBuilder(" WHERE ");
        where.append(" TENANT_ID=? ");
        if (StringUtils.isNotBlank(dataId)) {
            where.append(" AND DATA_ID=? ");
        }
        if (StringUtils.isNotBlank(group)) {
            where.append(" AND GROUP_ID=? ");
        }
        if (StringUtils.isNotBlank(appName)) {
            where.append(" AND APP_NAME=? ");
        }
        return sqlCount + where;
    }

    @Override
    public String findConfigInfo4PageFetchRows(Map<String, String> params, int startRow, int pageSize) {
        final String appName = params.get(APP_NAME);
        final String dataId = params.get(DATA_ID);
        final String group = params.get(GROUP);
        final String sql = "SELECT ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT,TYPE,ENCRYPTED_DATA_KEY FROM CONFIG_INFO";
        StringBuilder where = new StringBuilder(" WHERE ");
        where.append(" TENANT_ID=? ");
        if (StringUtils.isNotBlank(dataId)) {
            where.append(" AND DATA_ID=? ");
        }
        if (StringUtils.isNotBlank(group)) {
            where.append(" AND GROUP_ID=? ");
        }
        if (StringUtils.isNotBlank(appName)) {
            where.append(" AND APP_NAME=? ");
        }
        return sql + where + " LIMIT " + pageSize + " OFFSET " + startRow;
    }

    @Override
    public String findConfigInfoBaseByGroupFetchRows(int startRow, int pageSize) {
        return "SELECT ID,DATA_ID,GROUP_ID,CONTENT FROM CONFIG_INFO WHERE GROUP_ID=? AND TENANT_ID=?" + " LIMIT "
                + pageSize + " OFFSET " + startRow;
    }

    @Override
    public String findConfigInfoLike4PageCountRows(Map<String, String> params) {
        String dataId = params.get(DATA_ID);
        String group = params.get(GROUP);
        final String appName = params.get(APP_NAME);
        final String content = params.get(CONTENT);
        final String sqlCountRows = "SELECT COUNT(*) FROM CONFIG_INFO";
        StringBuilder where = new StringBuilder(" WHERE ");
        where.append(" TENANT_ID LIKE ? ");
        if (!StringUtils.isBlank(dataId)) {
            where.append(" AND DATA_ID LIKE ? ");
        }
        if (!StringUtils.isBlank(group)) {
            where.append(" AND GROUP_ID LIKE ? ");
        }
        if (!StringUtils.isBlank(appName)) {
            where.append(" AND APP_NAME = ? ");
        }
        if (!StringUtils.isBlank(content)) {
            where.append(" AND CONTENT LIKE ? ");
        }
        return sqlCountRows + where;
    }

    @Override
    public String findConfigInfoLike4PageFetchRows(Map<String, String> params, int startRow, int pageSize) {
        String dataId = params.get(DATA_ID);
        String group = params.get(GROUP);
        final String appName = params.get(APP_NAME);
        final String content = params.get(CONTENT);
        final String sqlFetchRows = "SELECT ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT,ENCRYPTED_DATA_KEY FROM CONFIG_INFO";
        StringBuilder where = new StringBuilder(" WHERE ");
        where.append(" TENANT_ID LIKE ? ");
        if (!StringUtils.isBlank(dataId)) {
            where.append(" AND DATA_ID LIKE ? ");
        }
        if (!StringUtils.isBlank(group)) {
            where.append(" AND GROUP_ID LIKE ? ");
        }
        if (!StringUtils.isBlank(appName)) {
            where.append(" AND APP_NAME = ? ");
        }
        if (!StringUtils.isBlank(content)) {
            where.append(" AND CONTENT LIKE ? ");
        }
        return sqlFetchRows + where + " LIMIT " + pageSize + " OFFSET " + startRow;
    }

    @Override
    public String findAllConfigInfoFetchRows(int startRow, int pageSize) {
        return "SELECT T.ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT,MD5 "
                + " FROM (  SELECT ID FROM CONFIG_INFO WHERE TENANT_ID LIKE ? ORDER BY ID LIMIT " + pageSize
                + " OFFSET " + startRow + " )" + " G, CONFIG_INFO T  WHERE G.ID = T.ID ";
    }

    @Override
    public String findConfigInfosByIds(int idSize) {
        StringBuilder sql = new StringBuilder(
                "SELECT ID,DATA_ID,GROUP_ID,TENANT_ID,APP_NAME,CONTENT,MD5 FROM CONFIG_INFO WHERE ");
        sql.append("ID IN (");
        for (int i = 0; i < idSize; i++) {
            if (i != 0) {
                sql.append(", ");
            }
            sql.append('?');
        }
        sql.append(") ");
        return sql.toString();
    }

    @Override
    public String removeConfigInfoByIdsAtomic(int size) {
        StringBuilder sql = new StringBuilder("DELETE FROM CONFIG_INFO WHERE ");
        sql.append("ID IN (");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sql.append(", ");
            }
            sql.append('?');
        }
        sql.append(") ");
        return sql.toString();
    }

    @Override
    public String updateConfigInfoAtomicCas() {
        return "UPDATE CONFIG_INFO SET "
                + "CONTENT=?, MD5 = ?, SRC_IP=?,SRC_USER=?,GMT_MODIFIED=?, APP_NAME=?,C_DESC=?,C_USE=?,EFFECT=?,TYPE=?,C_SCHEMA=? "
                + "WHERE DATA_ID=? AND GROUP_ID=? AND TENANT_ID=? AND (MD5=? OR MD5 IS NULL OR MD5='')";
    }

    @Override
    public String getTableName() {
        return TableConstant.CONFIG_INFO;
    }

    @Override
    public String getDataSource() {
        return DataSourceConstantExtension.POSTGRESQL;
    }

}
