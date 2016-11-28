package org.isheep.testconfig;

import org.isheep.testconfig.condition.TestProfileCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Created by raymo on 27/11/2016.
 */
@Configuration
@SuppressWarnings("unused")
public class EmbeddedDatasourceConfig {

    @Bean
    @Conditional(TestProfileCondition.class)
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("embedded-test-database")
                .build();
    }

}
