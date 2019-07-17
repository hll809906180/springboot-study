package cn.leadeon.mybits.comm.dbconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @description:
 * @author: he.l
 * @create: 2019-04-16 15:37
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 数据源路由，此方用于产生要选取的数据源逻辑名称
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //从共享线程中获取数据源名称
        return DynamicDataSourceHolder.getDataSource();
    }
}
