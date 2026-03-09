/**
 * @file petreminderTest.java
 * @brief petreminder yardımcı sınıfı için JUnit 5 test sınıfı.
 * @details PDF zorunluluğu: 100% JUnit5 coverage.
 *          Template'deki petreminderTest.java'nın JUnit5'e uyarlanmış hali.
 */
package com.mehdi.petreminder;

import com.mehdi.petreminder.model.*;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @class petreminderTest
 * @brief petreminder sınıfının test sınıfı.
 * @details Template'deki testAddition metodunun JUnit5 karşılığı +
 *          tüm yeni metodların testleri.
 * @author Muhammed Mehdi Karagülle, Ibrahim Demirci, Zumre Uykun
 */
class petreminderTest {

    /** @brief Test objesi. */
    private petreminder lib;

    /** @brief Her testten önce yeni instance. */
    @BeforeEach
    void setUp() {
        lib = new petreminder();
    }

    // ── add() — Template uyumu ────────────────────────────────────────

    /** @brief Template testAddition karşılığı — 2+3=5. */
    @Test void testAddition() {
        assertEquals(5, lib.add(2, 3));
    }

    @Test void testAddPositive()    { assertEquals(10, lib.add(3, 7)); }
    @Test void testAddZero()        { assertEquals(5,  lib.add(5, 0)); }
    @Test void testAddNegative()    { assertEquals(-1, lib.add(2, -3)); }
    @Test void testAddBothNeg()     { assertEquals(-5, lib.add(-2, -3)); }

    // ── subtract() ────────────────────────────────────────────────────

    @Test void testSubtract()       { assertEquals(2,  lib.subtract(5, 3)); }
    @Test void testSubtractZero()   { assertEquals(5,  lib.subtract(5, 0)); }
    @Test void testSubtractNeg()    { assertEquals(8,  lib.subtract(5, -3)); }
    @Test void testSubtractNegRes() { assertEquals(-1, lib.subtract(2, 3)); }

    // ── multiply() ───────────────────────────────────────────────────

    @Test void testMultiply()       { assertEquals(6,  lib.multiply(2, 3)); }
    @Test void testMultiplyZero()   { assertEquals(0,  lib.multiply(5, 0)); }
    @Test void testMultiplyNeg()    { assertEquals(-6, lib.multiply(2, -3)); }
    @Test void testMultiplyBothNeg(){ assertEquals(6,  lib.multiply(-2, -3)); }

    // ── divide() ────────────────────────────────────────────────────

    @Test void testDivide()         { assertEquals(2.5, lib.divide(5, 2), 0.001); }
    @Test void testDivideExact()    { assertEquals(3.0, lib.divide(9, 3), 0.001); }
    @Test void testDivideByZero()   {
        assertThrows(IllegalArgumentException.class, () -> lib.divide(5, 0));
    }
    @Test void testDivideNeg()      { assertEquals(-2.5, lib.divide(5, -2), 0.001); }

    // ── isNullOrEmpty() ──────────────────────────────────────────────

    @Test void testIsNullOrEmptyNull()    { assertTrue(lib.isNullOrEmpty(null)); }
    @Test void testIsNullOrEmptyBlank()   { assertTrue(lib.isNullOrEmpty("")); }
    @Test void testIsNullOrEmptySpaces()  { assertTrue(lib.isNullOrEmpty("   ")); }
    @Test void testIsNullOrEmptyValue()   { assertFalse(lib.isNullOrEmpty("test")); }
    @Test void testIsNullOrEmptySpacedV() { assertFalse(lib.isNullOrEmpty(" a ")); }

    // ── getCurrentTimeFormatted() ─────────────────────────────────────

    @Test void testGetCurrentTimeNotNull() {
        assertNotNull(lib.getCurrentTimeFormatted());
    }
    @Test void testGetCurrentTimeFormat() {
        String t = lib.getCurrentTimeFormatted();
        assertTrue(t.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}"));
    }

    // ── printSeparator() / printHeader() ─────────────────────────────

    @Test void testPrintSeparatorNoException() {
        assertDoesNotThrow(() -> lib.printSeparator(40));
    }
    @Test void testPrintSeparatorZero() {
        assertDoesNotThrow(() -> lib.printSeparator(0));
    }
    @Test void testPrintHeaderNoException() {
        assertDoesNotThrow(() -> lib.printHeader());
    }

    // ── printPets() ──────────────────────────────────────────────────

    @Test void testPrintPetsNull() {
        assertDoesNotThrow(() -> lib.printPets(null));
    }
    @Test void testPrintPetsEmpty() {
        assertDoesNotThrow(() -> lib.printPets(new ArrayList<>()));
    }
    @Test void testPrintPetsWithData() {
        PrintStream orig = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        List<Pet> pets = new ArrayList<>();
        pets.add(new Dog(1, "Max", LocalDateTime.now().minusYears(2).toLocalDate(), 1));
        assertDoesNotThrow(() -> lib.printPets(pets));
        System.setOut(orig);
    }

    // ── printReminders() ─────────────────────────────────────────────

    @Test void testPrintRemindersNull() {
        assertDoesNotThrow(() -> lib.printReminders(null));
    }
    @Test void testPrintRemindersEmpty() {
        assertDoesNotThrow(() -> lib.printReminders(new ArrayList<>()));
    }
    @Test void testPrintRemindersWithData() {
        PrintStream orig = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        List<Reminder> reminders = new ArrayList<>();
        reminders.add(new FeedingReminder(1, 1, "Max",
            LocalDateTime.now().plusDays(1), "Kuru Mama", 100));
        assertDoesNotThrow(() -> lib.printReminders(reminders));
        System.setOut(orig);
    }
    @Test void testPrintRemindersCompletedOverdue() {
        PrintStream orig = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        List<Reminder> reminders = new ArrayList<>();
        // overdue
        FeedingReminder overdue = new FeedingReminder(2, 1, "Max",
            LocalDateTime.now().minusDays(1), "Yas", 80);
        reminders.add(overdue);
        // completed
        FeedingReminder done = new FeedingReminder(3, 1, "Max",
            LocalDateTime.now().plusDays(1), "Yas", 80);
        done.setCompleted(true);
        reminders.add(done);
        assertDoesNotThrow(() -> lib.printReminders(reminders));
        System.setOut(orig);
    }
}
