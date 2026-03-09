/**
 * @file StorageConfig.java
 * @brief Storage konfigürasyonu — aktif backend'i yönetir.
 * @details PDF zorunluluğu: Config persisted in .properties file.
 *          RepositoryFactory bu sınıfı okur.
 */
package com.mehdi.petreminder.config;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @class StorageConfig
 * @brief Storage backend konfigürasyon yöneticisi.
 * @details OOP Encapsulation: static final Properties nesnesi.
 *          PDF zorunluluğu: Config persisted in .properties file so
 *          the choice survives restart.
 *          Singleton pattern: tüm erişim static metodlar üzerinden.
 * @author Muhammed Mehdi Karagülle, Ibrahim Demirci, Zumre Uykun
 * @version 1.0
 */
public class StorageConfig {

    /** @brief Logger. */
    private static final Logger logger =
        (Logger) LoggerFactory.getLogger(StorageConfig.class);

    /** @brief Properties dosyası yolu. */
    private static final String CONFIG_FILE = "config/storage.properties";

    /** @brief Aktif backend property anahtarı. */
    private static final String KEY_BACKEND = "storage.backend";

    /** @brief Varsayılan backend. */
    private static final StorageType DEFAULT_BACKEND = StorageType.BINARY;

    /** @brief In-memory aktif backend (runtime). */
    private static StorageType activeBackend = DEFAULT_BACKEND;

    /** @brief Properties nesnesi. */
    private static final Properties props = new Properties();

    /** @brief Gizli yapıcı — instantiation önlenir. */
    private StorageConfig() { }

    static {
        loadFromFile();
    }

    /**
     * @brief Properties dosyasından konfigürasyonu yükler.
     * @details Dosya yoksa varsayılan değerlerle devam eder.
     */
    public static void loadFromFile() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            logger.info("Config dosyasi bulunamadi, varsayilan kullaniliyor: {}",
                DEFAULT_BACKEND);
            activeBackend = DEFAULT_BACKEND;
            return;
        }
        try (FileInputStream fis = new FileInputStream(configFile)) {
            props.load(fis);
            String backendStr = props.getProperty(KEY_BACKEND,
                DEFAULT_BACKEND.name());
            activeBackend = StorageType.valueOf(backendStr.toUpperCase());
            logger.info("Config yuklendi: {}", activeBackend);
        } catch (IOException | IllegalArgumentException e) {
            logger.warn("Config yuklenemedi, varsayilan kullaniliyor: {}", e.getMessage());
            activeBackend = DEFAULT_BACKEND;
        }
    }

    /**
     * @brief Aktif backend'i döndürür.
     * @return Aktif StorageType
     */
    public static StorageType getActiveBackend() {
        return activeBackend;
    }

    /**
     * @brief Aktif backend'i değiştirir ve dosyaya kaydeder.
     * @details PDF zorunluluğu: no restart required.
     * @param type Yeni StorageType
     */
    public static void setActiveBackend(StorageType type) {
        if (type == null) {
            logger.warn("null StorageType verildi, degistirilmedi.");
            return;
        }
        activeBackend = type;
        saveToFile();
        logger.info("Storage backend degistirildi: {}", type);
    }

    /**
     * @brief Konfigürasyonu dosyaya kaydeder.
     */
    private static void saveToFile() {
        try {
            File configDir = new File("config");
            if (!configDir.exists()) {
                configDir.mkdirs();
            }
            props.setProperty(KEY_BACKEND, activeBackend.name());
            try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
                props.store(fos, "Pet Care Reminder System - Storage Config");
            }
            logger.debug("Config dosyaya kaydedildi: {}", CONFIG_FILE);
        } catch (IOException e) {
            logger.warn("Config kaydedilemedi: {}", e.getMessage());
        }
    }

    /**
     * @brief MySQL bağlantı URL'i döndürür.
     * @return JDBC MySQL URL
     */
    public static String getMysqlUrl() {
        return props.getProperty("mysql.url",
            "jdbc:mysql://localhost:3306/petreminder_db?useSSL=false&allowPublicKeyRetrieval=true");
    }

    /**
     * @brief MySQL kullanıcı adı döndürür.
     * @return Kullanıcı adı
     */
    public static String getMysqlUsername() {
        return props.getProperty("mysql.username", "petreminder_user");
    }

    /**
     * @brief MySQL şifresi döndürür.
     * @return Şifre
     */
    public static String getMysqlPassword() {
        return props.getProperty("mysql.password", "petreminder_pass");
    }

    /**
     * @brief SQLite dosya yolunu döndürür.
     * @return SQLite .db dosya yolu
     */
    public static String getSqliteFilePath() {
        return props.getProperty("sqlite.path", "data/petreminder.db");
    }

    /**
     * @brief Binary dosya dizinini döndürür.
     * @return Binary .bin dosyaları dizini
     */
    public static String getBinaryDataPath() {
        return props.getProperty("binary.path", "data/binary");
    }
}