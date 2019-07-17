package cn.leadeon.mybits.comm.dbconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: he.l
 * @create: 2019-04-12 17:35
 **/
@Component
@ConfigurationProperties(prefix = "hikari")
public class DBProperties {
    private HikariDataSource master;
    private HikariDataSource slave1;
    private HikariDataSource slave2;

    public HikariDataSource getMaster() {
        return master;
    }

    public void setMaster(HikariDataSource master) {
        this.master = master;
    }

    public HikariDataSource getSlave1() {
        return slave1;
    }

    public void setSlave1(HikariDataSource slave1) {
        this.slave1 = slave1;
    }

    public HikariDataSource getSlave2() {
        return slave2;
    }

    public void setSlave2(HikariDataSource slave2) {
        this.slave2 = slave2;
    }
}
