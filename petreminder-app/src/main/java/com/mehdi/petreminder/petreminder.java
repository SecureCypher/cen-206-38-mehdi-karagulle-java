/**
 * @file petreminder.java
 * @brief Pet Care Reminder System kütüphane yardımcı sınıfı.
 * @details Template'deki petreminder.java'nın projeye uyarlanmış hali.
 *          Bu sınıf utility / facade görevi görür.
 *          OOP Encapsulation: tüm yardımcı metodlar bu sınıfta toplanmıştır.
 */
package com.mehdi.petreminder;

import com.mehdi.petreminder.model.Pet;
import com.mehdi.petreminder.model.Reminder;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @class petreminder
 * @brief Pet Care Reminder System yardımcı/facade sınıfı.
 * @details PDF gereksinimi: Separate library (lib) and application (app) layers.
 *          Bu sınıf lib katmanının facade'ı olarak görev yapar.
 *          OOP Polymorphism: Reminder listesi üzerinden polimorfik işlemler.
 *          OOP Encapsulation: tüm alanlar private.
 * @author Muhammed Mehdi Karagülle
 * @author Ibrahim Demirci
 * @author Zumre Uykun
 * @version 1.0
 * @date 2026-03-27
 */
public class petreminder {

    /**
     * @brief Sınıf logger'ı.
     */
    private static final Logger logger =
        (Logger) LoggerFactory.getLogger(petreminder.class);

    /**
     * @brief Tarih-saat formatı (gösterim için).
     */
    private static final DateTimeFormatter DISPLAY_FORMAT =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * @brief Varsayılan yapıcı.
     */
    public petreminder() {
        logger.debug("petreminder yardimci sinifi olusturuldu.");
    }

    // ── Temel Aritmetik (template uyumu + test coverage) ──────────────

    /**
     * @brief İki tamsayıyı toplar.
     * @details Template'deki add metodu — test coverage için korunur.
     * @param a Birinci sayı
     * @param b İkinci sayı
     * @return Toplam
     */
    public int add(int a, int b) {
        logger.debug("add({}, {})", a, b);
        return a + b;
    }

    /**
     * @brief İki tamsayıyı çıkarır.
     * @param a Birinci sayı
     * @param b İkinci sayı
     * @return Fark
     */
    public int subtract(int a, int b) {
        logger.debug("subtract({}, {})", a, b);
        return a - b;
    }

    /**
     * @brief İki tamsayıyı çarpar.
     * @param a Birinci sayı
     * @param b İkinci sayı
     * @return Çarpım 
     */
    public int multiply(int a, int b) {
        logger.debug("multiply({}, {})", a, b);
        return a * b;
    }

    /**
     * @brief İki tamsayıyı böler.
     * @details Sıfıra bölme durumunda IllegalArgumentException fırlatır.
     *          PDF gereksinimi: Exception handling — try-catch.
     * @param a Bölünen
     * @param b Bölen (0 olamaz)
     * @return Bölüm (double)
     * @throws IllegalArgumentException bölen sıfır ise
     */
    public double divide(int a, int b) {
        if (b == 0) {
            logger.error("Sifira bolme denemesi: {} / {}", a, b);
            throw new IllegalArgumentException("Sifira bolme yapilamaz.");
        }
        logger.debug("divide({}, {})", a, b);
        return (double) a / b;
    }

    // ── Pet Yardımcı Metodları ────────────────────────────────────────

    /**
     * @brief Pet listesini biçimli şekilde konsola yazdırır.
     * @details OOP Polymorphism: List<Pet> üzerinden polimorfik toString.
     * @param pets Pet listesi
     */
    public void printPets(List<Pet> pets) {
        if (pets == null || pets.isEmpty()) {
            System.out.println("Hic evcil hayvan bulunamadi.");
            return;
        }
        System.out.println(String.format("%-5s %-15s %-12s %-10s",
            "ID", "Ad", "Tur", "Yas"));
        System.out.println("-".repeat(45));
        for (Pet pet : pets) {
            System.out.println(String.format("%-5d %-15s %-12s %-10s",
                pet.getId(), pet.getName(), pet.getSpecies(),
                pet.getAgeString()));
        }
    }

    /**
     * @brief Hatırlatıcı listesini biçimli şekilde konsola yazdırır.
     * @details OOP Polymorphism: Her Reminder alt sınıfının getSummary() metodu çağrılır.
     * @param reminders Hatırlatıcı listesi
     */
    public void printReminders(List<Reminder> reminders) {
        if (reminders == null || reminders.isEmpty()) {
            System.out.println("Hic hatirlatici bulunamadi.");
            return;
        }
        System.out.println(String.format("%-5s %-12s %-15s %-20s %-8s",
            "ID", "Tur", "Pet", "Zaman", "Durum"));
        System.out.println("-".repeat(65));
        for (Reminder r : reminders) {
            String durum = r.isCompleted() ? "[TAMAM]"
                : r.isOverdue() ? "[GECMIS]" : "[BEKLIYOR]";
            System.out.println(String.format("%-5d %-12s %-15s %-20s %-8s",
                r.getId(), r.getReminderType(), r.getPetName(),
                r.getFormattedTime(), durum));
        }
    }

    /**
     * @brief Mevcut tarih-saati biçimli string olarak döndürür.
     * @return "dd/MM/yyyy HH:mm" formatında şimdiki zaman
     */
    public String getCurrentTimeFormatted() {
        return LocalDateTime.now().format(DISPLAY_FORMAT);
    }

    /**
     * @brief Verilen string'in boş veya null olup olmadığını kontrol eder.
     * @param value Kontrol edilecek string
     * @return null veya boş ise true
     */
    public boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * @brief Konsol ayırıcı çizgi yazdırır.
     * @param width Çizgi genişliği
     */
    public void printSeparator(int width) {
        System.out.println("=".repeat(width));
    }

    /**
     * @brief Uygulama başlığını konsola yazdırır.
     */
    public void printHeader() {
        printSeparator(50);
        System.out.println("  " + petreminderApp.APP_NAME);
        System.out.println("  v" + petreminderApp.APP_VERSION);
        printSeparator(50);
    }
}
