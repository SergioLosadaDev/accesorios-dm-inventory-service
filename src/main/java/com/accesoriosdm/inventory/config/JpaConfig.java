package com.accesoriosdm.inventory.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    /**
     * Forces Hibernate to treat 'catalogo' as the default schema for unqualified
     * entity mappings owned by this service (ADR-001 / ADR-005).
     * The search_path is set on the connection via the JDBC URL currentSchema param.
     */
    @Bean
    public HibernatePropertiesCustomizer hibernateSchemaCustomizer() {
        return properties -> properties.put("hibernate.default_schema", "catalogo");
    }
}
