package com.lwh147.common.mybatisplus.properties;

import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.*;

/**
 * 数据库类型及对应方言枚举类
 *
 * @author lwh
 * @date 2021/12/12 23:17
 **/
public enum DbType {
    MYSQL("mysql", new MySqlDialect()),
    MARIADB("mariadb", new MySqlDialect()),
    ORACLE("oracle", new OracleDialect()),
    ORACLE_12C("oracle12c", new Oracle12cDialect()),
    DB2("db2", new DB2Dialect()),
    H2("h2", new PostgreDialect()),
    HSQL("hsql", new PostgreDialect()),
    SQLITE("sqlite", new PostgreDialect()),
    POSTGRE_SQL("postgresql", new PostgreDialect()),
    SQL_SERVER2005("sqlserver2005", new SQLServer2005Dialect()),
    SQL_SERVER("sqlserver", new SQLServerDialect()),
    DM("dm", new OracleDialect()),
    XU_GU("xugu", new MySqlDialect()),
    KINGBASE_ES("kingbasees", new PostgreDialect()),
    PHOENIX("phoenix", new PostgreDialect()),
    GAUSS("zenith", new OracleDialect()),
    CLICK_HOUSE("clickhouse", null),
    GBASE("gbase", new MySqlDialect()),
    OSCAR("oscar", new MySqlDialect()),
    SYBASE("sybase", new SybaseDialect()),
    OCEAN_BASE("oceanbase", null),
    FIREBIRD("Firebird", null),
    OTHER("other", null);

    private final String type;
    private final IDialect dialect;

    DbType(final String type, final IDialect dialect) {
        this.type = type;
        this.dialect = dialect;
    }

    public String getType() {
        return type;
    }

    public IDialect getDialect() {
        return dialect;
    }
}
