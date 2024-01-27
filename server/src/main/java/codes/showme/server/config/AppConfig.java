package codes.showme.server.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

@Configuration
public class AppConfig {

    @Value("${app.jdbcUrl}")
    private String jdbcUrl;


    @Bean
    @Primary
    public io.ebean.Database database() {
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(getJdbcUrl());
        datasource.setAutoCommit(true);
        datasource.setMaximumPoolSize(3);
        datasource.setMinimumIdle(1);
        datasource.setDriverClassName("org.sqlite.JDBC");

        DatabaseConfig config = new DatabaseConfig();
        config.setName("domain-sql");
        config.setRegister(true);
        config.setDefaultServer(true);
        config.setDdlExtra(false);
        config.setPackages(Arrays.asList("codes.showme"));
        config.setDdlCreateOnly(false);
        config.setDdlGenerate(true);
        config.setDdlRun(false);
        config.setDataSource(datasource);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(MapperFeature.AUTO_DETECT_CREATORS,
                MapperFeature.AUTO_DETECT_FIELDS,
                MapperFeature.AUTO_DETECT_GETTERS,
                MapperFeature.AUTO_DETECT_SETTERS,
                MapperFeature.AUTO_DETECT_IS_GETTERS);
        config.setObjectMapper(objectMapper);
        return DatabaseFactory.create(config);
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }
}
