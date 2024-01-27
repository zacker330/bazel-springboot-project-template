package codes.showme.repository.impl;

import codes.showme.techlib.ioc.InstanceFactory;
import codes.showme.techlib.ioc.InstanceProvider;
import com.zaxxer.hikari.HikariDataSource;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

public abstract class AbstractIntegrationTest {

    public static final InstanceProvider instanceProvider = Mockito.mock(InstanceProvider.class);

    @BeforeClass
    public static void initInstanceFactory(){
        InstanceFactory.setInstanceProvider(instanceProvider);
    }

    protected static Database createDatabase(String seedsql, List<String> packages){
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl("jdbc:sqlite:" + UUID.randomUUID() + ".db");
        datasource.setAutoCommit(false);

        DatabaseConfig config = new DatabaseConfig();
        config.setName("for_test");
        config.setRegister(true);
        config.setDefaultServer(true);
        config.setDdlExtra(false);
        config.setPackages(packages);
        config.setDdlCreateOnly(false);
        config.setDdlGenerate(true);
        config.setDdlRun(true);
        config.setDataSource(datasource);

        if (StringUtils.isNoneEmpty(seedsql)) {
            config.setDdlSeedSql(seedsql);
        }

        // important!!!  this step is import, because it new a database instance for the dbserver provider
        // that DbMigration have to gather.
        return DatabaseFactory.create(config);
    }
}
