/**
 * @file StorageType.java
 * @brief Storage backend türlerini tanımlayan enum.
 */
package com.mehdi.petreminder.config;

/**
 * @enum StorageType
 * @brief Desteklenen storage backend türleri.
 * @details PDF zorunluluğu: Binary, SQLite, MySQL — all three required.
 * @author Muhammed Mehdi Karagülle, Ibrahim Demirci, Zumre Uykun
 * @version 1.0
 */
public enum StorageType {

    /** @brief Binary File I/O — ObjectOutputStream / .bin dosyası. */
    BINARY("Binary File I/O (.bin)"),

    /** @brief SQLite — org.xerial:sqlite-jdbc / .db dosyası. */
    SQLITE("SQLite (.db)"),

    /** @brief MySQL — mysql-connector-j + Docker Compose. */
    MYSQL("MySQL (Docker)");

    /** @brief Kullanıcıya gösterilen isim. */
    private final String displayName;

    /**
     * @brief Enum yapıcısı.
     * @param displayName Görünen ad
     */
    StorageType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @brief Görünen adı döndürür.
     * @return Display name string
     */
    public String getDisplayName() {
        return displayName;
    }
}