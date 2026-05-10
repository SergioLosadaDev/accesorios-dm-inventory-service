package com.accesoriosdm.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:tc:postgresql:15:///accesorios_dm?currentSchema=catalogo,inventario",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.liquibase.enabled=false"
})
class InventoryServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
