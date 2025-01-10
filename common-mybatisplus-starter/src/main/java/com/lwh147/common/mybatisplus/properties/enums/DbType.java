package com.lwh147.common.mybatisplus.properties.enums;

import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.*;
import lombok.Getter;

/**
 * 常见数据库类型及对应方言枚举类
 *
 * @author lwh
 * @date 2021/12/12 23:17
 **/
@Getter
public enum DbType {
    MYSQL("mysql", MySqlDialect.class),
    MARIADB("mariadb", MySqlDialect.class),
    ORACLE("oracle", OracleDialect.class),
    ORACLE_12C("oracle12c", Oracle12cDialect.class),
    DB2("db2", DB2Dialect.class),
    H2("h2", PostgreDialect.class),
    HSQL("hsql", PostgreDialect.class),
    SQLITE("sqlite", PostgreDialect.class),
    POSTGRE_SQL("postgresql", PostgreDialect.class),
    SQL_SERVER2005("sqlserver2005", SQLServer2005Dialect.class),
    SQL_SERVER("sqlserver", SQLServerDialect.class),
    DM("dm", OracleDialect.class),
    XU_GU("xugu", MySqlDialect.class),
    KINGBASE_ES("kingbasees", PostgreDialect.class),
    PHOENIX("phoenix", PostgreDialect.class),
    GAUSS("zenith", OracleDialect.class),
    CLICK_HOUSE("clickhouse", null),
    GBASE("gbase", MySqlDialect.class),
    OSCAR("oscar", MySqlDialect.class),
    SYBASE("sybase", SybaseDialect.class),
    OCEAN_BASE("oceanbase", null),
    FIREBIRD("Firebird", null),
    OTHER("other", null);

    private final String type;
    private final Class<? extends IDialect> dialect;

    DbType(final String type, final Class<? extends IDialect> dialect) {
        this.type = type;
        this.dialect = dialect;
    }
}
