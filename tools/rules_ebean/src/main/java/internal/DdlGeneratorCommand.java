package internal;

import com.zaxxer.hikari.HikariDataSource;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.annotation.Platform;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.dbplatform.DatabasePlatform;
import io.ebean.dbmigration.DbMigration;
import io.ebean.migration.MigrationConfig;
import io.ebean.migration.MigrationRunner;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.UUID;

public class DdlGeneratorCommand {
    public static final String TESTS_DB = "ebean_ddlgeneratorcommand";
    public static final String DB_SCHEMA = "public";
    public static final String META_TABLE = "cp_db_migrations";
    public static final String MIGRATION_PATH = "migrations";
    private static Options OPTIONS = new Options();
    private static CommandLine commandLine;
    private static String HELP_STRING = null;

    public static void main(String[] args) throws ParseException, IOException {

        CommandLineParser commandLineParser = new DefaultParser();
        // help
        OPTIONS.addOption("help", "usage help");

        OPTIONS.addOption(Option.builder("d").required(false).longOpt("dbPath").hasArgs()
                .type(String.class).desc("the path of sqlite db").build()
        );

        OPTIONS.addOption(Option.builder("s").required(false).longOpt("seedsPath").hasArgs()
                .type(String.class).desc("the seed sql path of sqlite db").build()
        );

        OPTIONS.addOption(Option.builder("p").required().hasArg(true).longOpt("path").hasArgs()
                .type(String.class).desc("the path of where model is").build());
        OPTIONS.addOption(Option.builder("g").required().type(String.class)
                .hasArgs().longOpt("packages").desc("the packages would be scan by Ebean").build());
        try {
            commandLine = commandLineParser.parse(OPTIONS, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + "\n" + getHelpString());
            System.exit(0);
        }

        String sqlBasePath = commandLine.getOptionValue("path");
        String dbPath = commandLine.getOptionValue("dbPath", "/tmp/" + UUID.randomUUID().toString() + ".db");
        String seedsPath = commandLine.getOptionValue("seedsPath");
        String[] packages = commandLine.getOptionValues("packages");

        HikariDataSource datasource = new HikariDataSource();
        if (StringUtils.isNoneEmpty(dbPath)) {
            System.out.println("sqlite path: " + dbPath);
            datasource.setJdbcUrl("jdbc:sqlite:" + dbPath);
        } else {
            datasource.setJdbcUrl("jdbc:sqlite:" + DdlGeneratorCommand.class.getName() + ".db");
        }

        datasource.setAutoCommit(true);

        DatabaseConfig config = new DatabaseConfig();
        config.setName("domain-sql");
        config.setRegister(true);
        config.setDefaultServer(true);
        config.setDdlExtra(false);
        config.setPackages(Arrays.asList(packages));
        config.setDdlCreateOnly(false);
        config.setDdlGenerate(true);
        config.setDdlRun(false);
//        config.setDbSchema(DB_SCHEMA);
//        config.setDatabasePlatform(new DatabasePlatform());
        config.setDataSource(datasource);
        if (StringUtils.isNoneEmpty(seedsPath)) {
            config.setDdlSeedSql(seedsPath);
        }

        // important!!!  this step is import, because it new a database instance for the dbserver provider
        // that DbMigration have to gather.
        Database database = DatabaseFactory.create(config);
        DbMigration dbMigration = DbMigration.create();
        dbMigration.setServerConfig(config);
        dbMigration.setStrictMode(false);
        dbMigration.setPlatform(Platform.SQLITE);

        dbMigration.setMigrationPath(MIGRATION_PATH);
        dbMigration.setPathToResources(sqlBasePath);
        String generateInitMigration = dbMigration.generateInitMigration();
        String reuslt = dbMigration.generateMigration();

        MigrationConfig migrationConfig = new MigrationConfig();
        migrationConfig.setPlatform("sqlite");
        migrationConfig.setDbUrl("jdbc:sqlite:db1.db");
        migrationConfig.setMetaTable(META_TABLE);
        migrationConfig.setMigrationPath("filesystem:" + sqlBasePath + File.separator + MIGRATION_PATH);

//         just for have a test for the sqls
        MigrationRunner runner = new MigrationRunner(migrationConfig);
        runner.run(datasource);
    }


    private static String getHelpString() {
        if (HELP_STRING == null) {
            HelpFormatter helpFormatter = new HelpFormatter();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
            helpFormatter.printHelp(printWriter, HelpFormatter.DEFAULT_WIDTH, "ddl -help", null,
                    OPTIONS, HelpFormatter.DEFAULT_LEFT_PAD, HelpFormatter.DEFAULT_DESC_PAD, null);
            printWriter.flush();
            HELP_STRING = new String(byteArrayOutputStream.toByteArray());
            printWriter.close();
        }
        return HELP_STRING;
    }
}
