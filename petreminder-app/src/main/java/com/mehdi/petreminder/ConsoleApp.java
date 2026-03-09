/**
 * @file ConsoleApp.java
 * @brief Console uygulaması ana döngüsü ve menü yöneticisi.
 * @details PDF zorunluluğu: Console-based menu with keyboard navigation.
 *          Tüm CRUD işlemleri için menü ekranları buradan başlatılır.
 */
package com.mehdi.petreminder;

import com.mehdi.petreminder.config.StorageConfig;
import com.mehdi.petreminder.config.StorageType;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import java.util.Scanner;

/**
 * @class ConsoleApp
 * @brief Konsol uygulaması — ana menü döngüsü.
 * @details PDF gereksinimi:
 *          - Console UI: Keyboard-navigable menus (arrow keys / Tab)
 *          - Settings screen: storage backend değiştirme
 *          - Separate app layer (calls lib/service layer)
 *          OOP Encapsulation: scanner private.
 *          OOP Abstraction: her menü ekranı ayrı metod.
 * @author Muhammed Mehdi Karagülle
 * @author Ibrahim Demirci
 * @author Zumre Uykun
 * @version 1.0
 */
public class ConsoleApp {

    /** @brief Sınıf logger'ı. */
    private static final Logger logger =
        (Logger) LoggerFactory.getLogger(ConsoleApp.class);

    /** @brief Klavye girişi okuyucu. */
    private final Scanner scanner;

    /** @brief Çalışıyor mu bayrağı. */
    private boolean running;

    /**
     * @brief Varsayılan yapıcı — scanner başlatılır.
     */
    public ConsoleApp() {
        this.scanner = new Scanner(System.in);
        this.running = false;
    }

    /**
     * @brief Test amaçlı yapıcı — dışarıdan scanner verilir.
     * @param scanner Test scanner'ı
     */
    public ConsoleApp(Scanner scanner) {
        this.scanner = scanner;
        this.running = false;
    }

    /**
     * @brief Konsol uygulamasını başlatır, ana menü döngüsünü çalıştırır.
     * @details running=true yaparak döngüye girer; çıkış seçilince durur.
     */
    public void start() {
        this.running = true;
        logger.info("ConsoleApp baslatildi.");
        showMainMenu();
    }

    /**
     * @brief Ana menüyü gösterir ve seçim döngüsünü yönetir.
     */
    public void showMainMenu() {
        while (running) {
            printMainMenu();
            String choice = readInput();
            handleMainMenuChoice(choice);
        }
    }

    /**
     * @brief Ana menüyü konsola yazdırır.
     */
    public void printMainMenu() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("   PET CARE REMINDER SYSTEM - ANA MENU  ");
        System.out.println("==========================================");
        System.out.println("  [1] Evcil Hayvanlar");
        System.out.println("  [2] Hatirlaticilar");
        System.out.println("  [3] Veteriner Randevulari");
        System.out.println("  [4] Saglik Kayitlari");
        System.out.println("  [5] Ayarlar (Storage: "
            + StorageConfig.getActiveBackend().getDisplayName() + ")");
        System.out.println("  [0] Cikis");
        System.out.println("==========================================");
        System.out.print("Seciminiz: ");
    }

    /**
     * @brief Ana menü seçimini işler.
     * @param choice Kullanıcı seçimi
     */
    public void handleMainMenuChoice(String choice) {
        if (choice == null) {
            return;
        }
        switch (choice.trim()) {
            case "1": showPetsMenu(); break;
            case "2": showRemindersMenu(); break;
            case "3": showVetMenu(); break;
            case "4": showMedicalMenu(); break;
            case "5": showSettingsMenu(); break;
            case "0": exitApp(); break;
            default:
                System.out.println("Gecersiz secim. Tekrar deneyin.");
                logger.warn("Gecersiz ana menu secimi: {}", choice);
        }
    }

    /**
     * @brief Evcil hayvanlar menüsünü gösterir.
     */
    public void showPetsMenu() {
        System.out.println("\n--- EVCIL HAYVANLAR ---");
        System.out.println("  [1] Tum Hayvanlari Listele");
        System.out.println("  [2] Yeni Hayvan Ekle");
        System.out.println("  [3] Hayvan Duzenle");
        System.out.println("  [4] Hayvan Sil");
        System.out.println("  [0] Geri");
        System.out.print("Seciminiz: ");
        String choice = readInput();
        System.out.println("[" + choice + "] secildi. (Servis katmani hazirlanıyor...)");
    }

    /**
     * @brief Hatırlatıcılar menüsünü gösterir.
     */
    public void showRemindersMenu() {
        System.out.println("\n--- HATIRLATICILAR ---");
        System.out.println("  [1] Tum Hatirlaticilari Listele");
        System.out.println("  [2] Yeni Hatirlatici Ekle");
        System.out.println("  [3] Hatirlatici Tamamla");
        System.out.println("  [4] Hatirlatici Sil");
        System.out.println("  [0] Geri");
        System.out.print("Seciminiz: ");
        String choice = readInput();
        System.out.println("[" + choice + "] secildi.");
    }

    /**
     * @brief Veteriner menüsünü gösterir.
     */
    public void showVetMenu() {
        System.out.println("\n--- VETERINER RANDEVULARI ---");
        System.out.println("  [1] Randevulari Listele");
        System.out.println("  [2] Yeni Randevu Ekle");
        System.out.println("  [0] Geri");
        System.out.print("Seciminiz: ");
        String choice = readInput();
        System.out.println("[" + choice + "] secildi.");
    }

    /**
     * @brief Sağlık kayıtları menüsünü gösterir.
     */
    public void showMedicalMenu() {
        System.out.println("\n--- SAGLIK KAYITLARI ---");
        System.out.println("  [1] Kayitlari Listele");
        System.out.println("  [2] Yeni Kayit Ekle");
        System.out.println("  [0] Geri");
        System.out.print("Seciminiz: ");
        String choice = readInput();
        System.out.println("[" + choice + "] secildi.");
    }

    /**
     * @brief Ayarlar menüsünü gösterir.
     * @details PDF zorunluluğu: Runtime storage switching — no restart required.
     *          [1] Binary  [2] SQLite  [3] MySQL
     */
    public void showSettingsMenu() {
        System.out.println("\n--- AYARLAR ---");
        System.out.println("Mevcut Storage: "
            + StorageConfig.getActiveBackend().getDisplayName());
        System.out.println("  [1] Binary File I/O (.bin)");
        System.out.println("  [2] SQLite (.db)");
        System.out.println("  [3] MySQL (Docker gerekli)");
        System.out.println("  [0] Geri");
        System.out.print("Seciminiz: ");
        String choice = readInput();
        handleStorageSwitch(choice);
    }

    /**
     * @brief Storage backend'ini çalışma zamanında değiştirir.
     * @details PDF zorunluluğu: User switches from Settings screen at runtime.
     * @param choice Seçilen backend numarası
     */
    public void handleStorageSwitch(String choice) {
        if (choice == null) return;
        switch (choice.trim()) {
            case "1":
                StorageConfig.setActiveBackend(StorageType.BINARY);
                System.out.println("Storage: Binary File I/O secildi.");
                logger.info("Storage degistirildi: BINARY");
                break;
            case "2":
                StorageConfig.setActiveBackend(StorageType.SQLITE);
                System.out.println("Storage: SQLite secildi.");
                logger.info("Storage degistirildi: SQLITE");
                break;
            case "3":
                StorageConfig.setActiveBackend(StorageType.MYSQL);
                System.out.println("Storage: MySQL secildi.");
                System.out.println("NOT: MySQL icin Docker Compose calisir olmali.");
                logger.info("Storage degistirildi: MYSQL");
                break;
            case "0":
                break;
            default:
                System.out.println("Gecersiz secim.");
        }
    }

    /**
     * @brief Uygulamadan çıkış yapar.
     */
    public void exitApp() {
        running = false;
        System.out.println("\nPet Care Reminder System kapatiliyor...");
        System.out.println("Gorusmek uzere!");
        logger.info("Uygulama kullanici tarafindan kapatildi.");
    }

    /**
     * @brief Klavyeden bir satır okur.
     * @return Kullanıcı girişi (trim edilmiş), okunamazsa boş string
     */
    public String readInput() {
        try {
            if (scanner.hasNextLine()) {
                return scanner.nextLine().trim();
            }
        } catch (Exception e) {
            logger.error("Giris okuma hatasi: {}", e.getMessage());
        }
        return "";
    }

    /**
     * @brief Çalışıyor mu getter.
     * @return running durumu
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @brief running setter (test için).
     * @param running Yeni durum
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
}