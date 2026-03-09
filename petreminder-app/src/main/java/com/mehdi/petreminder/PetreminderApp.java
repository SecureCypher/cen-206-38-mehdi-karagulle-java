/**
 * @file petreminderApp.java
 * @brief Pet Care Reminder System ana uygulama giriş noktası.
 * @details Bu dosya uygulamanın main() metodunu içerir.
 *          Console menüsünü başlatır, storage backend'i yapılandırır.
 *          PDF gereksinimi: Console-based menu with keyboard navigation.
 */

/**
 * @package com.mehdi.ibrahim.zumre.petreminder
 * @brief Ana uygulama paketi — Pet Care Reminder System.
 */
package com.mehdi.petreminder;

import com.mehdi.petreminder.config.StorageConfig;
import com.mehdi.petreminder.config.StorageType;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

/**
 * @class petreminderApp
 * @brief Pet Care Reminder System ana sınıfı.
 * @details PDF gereksinimi: Modular Architecture - app layer calls lib layer.
 *          OOP Encapsulation: logger private static final.
 *          OOP Abstraction: ConsoleApp soyutlaması üzerinden çalışır.
 * @author Muhammed Mehdi Karagülle
 * @author Ibrahim Demirci
 * @author Zumre Uykun
 * @version 1.0
 * @date 2026-03-27
 */
public class PetreminderApp {

    /**
     * @brief Uygulama logger'ı.
     * @details SLF4J + Logback — logback.xml ile yapılandırılır.
     */
    private static final Logger logger =
        (Logger) LoggerFactory.getLogger(PetreminderApp.class);

    /**
     * @brief Uygulama adı sabiti.
     */
    public static final String APP_NAME = "Pet Care Reminder System";

    /**
     * @brief Uygulama versiyonu.
     */
    public static final String APP_VERSION = "1.0.0";

    /**
     * @brief Pet Care Reminder System ana giriş noktası.
     * @details PDF gereksinimi:
     *          - Storage backend komut satırından seçilebilir (--storage=binary|sqlite|mysql)
     *          - Varsayılan: BINARY
     *          - ConsoleApp başlatılır; menü yönetimi orada yapılır.
     * @param args Komut satırı argümanları.
     *             --storage=binary  → Binary File I/O
     *             --storage=sqlite  → SQLite JDBC
     *             --storage=mysql   → MySQL (Docker Compose gerekli)
     */
    public static void main(String[] args) {
    	System.out.println("PROGRAM BASLADI"); // bu görünüyor mu?
        logger.info("{} v{} baslatiliyor...", APP_NAME, APP_VERSION);
        System.out.println("==========================================");
        System.out.println("  " + APP_NAME + " v" + APP_VERSION);
        System.out.println("  CEN206 - OOP Term Project - Spring 2026");
        System.out.println("  RTEU Computer Engineering");
        System.out.println("==========================================");

        // Komut satırı argümanlarından storage tipini oku
        StorageType storageType = parseStorageArg(args);
        StorageConfig.setActiveBackend(storageType);
        logger.info("Storage backend: {}", storageType);
        System.out.println("Storage: " + storageType.getDisplayName());
        System.out.println();

        // Console uygulamasını başlat
        try {
            ConsoleApp consoleApp = new ConsoleApp();
            consoleApp.start();
        } catch (Exception e) {
            logger.error("Uygulama hatasi: {}", e.getMessage(), e);
            System.err.println("Kritik hata: " + e.getMessage());
            System.exit(1);
        }

        logger.info("Uygulama kapatildi.");
    }

    /**
     * @brief Komut satırı argümanlarından storage tipini ayrıştırır.
     * @details --storage=binary|sqlite|mysql formatını destekler.
     *          Geçersiz/eksik argüman durumunda BINARY döner.
     * @param args Komut satırı argümanları
     * @return Seçilen StorageType (varsayılan: BINARY)
     */
    public static StorageType parseStorageArg(String[] args) {
        if (args == null || args.length == 0) {
            return StorageType.BINARY;
        }
        for (String arg : args) {
            if (arg != null && arg.startsWith("--storage=")) {
                String value = arg.substring("--storage=".length()).trim().toUpperCase();
                try {
                    return StorageType.valueOf(value);
                } catch (IllegalArgumentException e) {
                    logger.warn("Gecersiz storage tipi: {}. BINARY kullaniliyor.", value);
                    return StorageType.BINARY;
                }
            }
        }
        return StorageType.BINARY;
    }

    /**
     * @brief Uygulama versiyonunu döndürür.
     * @return Versiyon string'i
     */
    public static String getVersion() {
        return APP_VERSION;
    }

    /**
     * @brief Uygulama adını döndürür.
     * @return Uygulama adı
     */
    public static String getAppName() {
        return APP_NAME;
    }
}