package com.accesoriosdm.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD

@SpringBootTest
class InventoryServiceApplicationTests {

	@Test
	void contextLoads() {
	}

=======
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:tc:postgresql:15:///accesorios_dm?TC_INITSCRIPT=init.sql",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.liquibase.enabled=false"
})
class InventoryServiceApplicationTests {

    @Test
    void contextLoads() {
    }
>>>>>>> b4883ed0b5faca54e1ebb9465472557e808ca36f
}
