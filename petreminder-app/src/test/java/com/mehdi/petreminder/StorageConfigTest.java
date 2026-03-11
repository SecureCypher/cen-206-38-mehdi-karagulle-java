/**
 * @file StorageConfigTest.java
 * @brief StorageConfig ve StorageType için JUnit 5 testleri.
 */
package com.mehdi.petreminder;

import com.mehdi.petreminder.config.StorageConfig;
import com.mehdi.petreminder.config.StorageType;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @class StorageConfigTest
 * @brief StorageConfig ve StorageType test sınıfı.
 * @author Muhammed Mehdi Karagülle, Ibrahim Demirci, Zumre Uykun
 */
class StorageConfigTest {

    // ── StorageType enum ─────────────────────────────────────────────

    @Test void testStorageTypeBinaryDisplayName() {
        assertNotNull(StorageType.BINARY.getDisplayName());
        assertTrue(StorageType.BINARY.getDisplayName().contains("Binary"));
    }

    @Test void testStorageTypeSqliteDisplayName() {
        assertNotNull(StorageType.SQLITE.getDisplayName());
        assertTrue(StorageType.SQLITE.getDisplayName().contains("SQLite"));
    }

    @Test void testStorageTypeMysqlDisplayName() {
        assertNotNull(StorageType.MYSQL.getDisplayName());
        assertTrue(StorageType.MYSQL.getDisplayName().contains("MySQL"));
    }

    @Test void testStorageTypeValues() {
        StorageType[] values = StorageType.values();
        assertEquals(3, values.length);
    }

    @Test void testStorageTypeValueOf() {
        assertEquals(StorageType.BINARY,  StorageType.valueOf("BINARY"));
        assertEquals(StorageType.SQLITE,  StorageType.valueOf("SQLITE"));
        assertEquals(StorageType.MYSQL,   StorageType.valueOf("MYSQL"));
    }

    // ── StorageConfig ────────────────────────────────────────────────

    @Test void testGetActiveBackendNotNull() {
        assertNotNull(StorageConfig.getActiveBackend());
    }

    @Test void testSetActiveBackendBinary() {
        StorageConfig.setActiveBackend(StorageType.BINARY);
        assertEquals(StorageType.BINARY, StorageConfig.getActiveBackend());
    }

    @Test void testSetActiveBackendSqlite() {
        StorageConfig.setActiveBackend(StorageType.SQLITE);
        assertEquals(StorageType.SQLITE, StorageConfig.getActiveBackend());
    }

    @Test void testSetActiveBackendMysql() {
        StorageConfig.setActiveBackend(StorageType.MYSQL);
        assertEquals(StorageType.MYSQL, StorageConfig.getActiveBackend());
    }

    @Test void testSetActiveBackendNull() {
        StorageType before = StorageConfig.getActiveBackend();
        StorageConfig.setActiveBackend(null);
        assertEquals(before, StorageConfig.getActiveBackend()); // değişmemiş olmalı
    }

    @Test void testGetMysqlUrlNotNull() {
        assertNotNull(StorageConfig.getMysqlUrl());
    }

    @Test void testGetMysqlUsernameNotNull() {
        assertNotNull(StorageConfig.getMysqlUsername());
    }

    @Test void testGetMysqlPasswordNotNull() {
        assertNotNull(StorageConfig.getMysqlPassword());
    }

    @Test void testGetSqliteFilePathNotNull() {
        assertNotNull(StorageConfig.getSqliteFilePath());
    }

    @Test void testGetBinaryDirectoryNotNull() {
        assertNotNull(StorageConfig.getBinaryDirectory());
    }

    @Test void testLoadFromFileNoException() {
        assertDoesNotThrow(StorageConfig::loadFromFile);
    }
}
