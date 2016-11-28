package org.isheep.config.jpa;

/**
 * Created by raymo on 09/11/2016.
 */
import org.isheep.config.condition.DevProfileCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Created by Anthony on 06/01/2016.
 */
@Configuration
@SuppressWarnings("unused")
public class DevDatabaseConfiguration {

    /**
     * Instead of using the applications.properties that try to connect to the real database, we start an Embedded
     * databases for development purpose. Only when {@link DevProfileCondition} is satisfied.
     *
     * @return A {@link EmbeddedDatabase} instance.
     */
    @Bean
    @Conditional(value = {DevProfileCondition.class})
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("embedded-jpa")
                .build();
    }

}