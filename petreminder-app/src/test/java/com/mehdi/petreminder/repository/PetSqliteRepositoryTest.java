/**
 * @file PetSqliteRepositoryTest.java
 * @brief PetSqliteRepository JUnit5 testleri — H2 in-memory kullanır.
 */
package com.mehdi.petreminder.repository;

import com.mehdi.petreminder.model.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class PetSqliteRepositoryTest
 * @brief SQLite repository testleri — H2 in-memory veritabanı.
 * @author Muhammed Mehdi Karagülle
 * @author Ibrahim Demirci
 * @author Zumre Uykun
 * @version 1.0
 */
@DisplayName("PetSqliteRepository Testleri")
class PetSqliteRepositoryTest {

    /** @brief H2 bağlantısı. */
    private Connection conn;

    /** @brief Test repo. */
    private PetSqliteRepository repo;

    /**
     * @brief Her testten önce H2 in-memory bağlantısı açılır.
     * @throws Exception Bağlantı hatası
     */
    @BeforeEach
    void setUp() throws Exception {
        conn = DriverManager.getConnection(
            "jdbc:h2:mem:testdb_" + System.nanoTime() + ";DB_CLOSE_DELAY=-1");
        repo = new PetSqliteRepository(conn);
    }

    /**
     * @brief Her testten sonra bağlantı kapatılır.
     * @throws Exception Kapatma hatası
     */
    @AfterEach
    void tearDown() throws Exception {
        if (conn != null && !conn.isClosed()) conn.close();
    }

    @Test
    @DisplayName("save Dog: ID üretilir")
    void testSaveDog() {
        Dog dog = new Dog(0, "Rex", LocalDate.of(2020, 3, 10), 1);
        dog.setBreed("Golden");
        int id = repo.save(dog);
        assertTrue(id > 0);
        assertEquals(id, dog.getId());
    }

    @Test
    @DisplayName("save Cat: kaydedilir")
    void testSaveCat() {
        Cat cat = new Cat(0, "Tekir", LocalDate.of(2021, 6, 1), 1);
        cat.setBreed("Ankara Kedisi");
        cat.setIndoor(true);
        int id = repo.save(cat);
        assertTrue(id > 0);
    }

    @Test
    @DisplayName("save Bird: kaydedilir")
    void testSaveBird() {
        Bird bird = new Bird(0, "Cici", null, 1);
        bird.setBirdType("Muhabbet Kuşu");
        bird.setCanTalk(true);
        int id = repo.save(bird);
        assertTrue(id > 0);
    }

    @Test
    @DisplayName("save null: exception")
    void testSaveNull() {
        assertThrows(RepositoryException.class, () -> repo.save(null));
    }

    @Test
    @DisplayName("findById Dog: geri döner")
    void testFindById() {
        Dog dog = new Dog(0, "Max", LocalDate.of(2019, 1, 1), 1);
        int id = repo.save(dog);
        Optional<Pet> found = repo.findById(id);
        assertTrue(found.isPresent());
        assertEquals("Max", found.get().getName());
        assertInstanceOf(Dog.class, found.get());
    }

    @Test
    @DisplayName("findById olmayan: empty")
    void testFindByIdNotFound() {
        assertTrue(repo.findById(9999).isEmpty());
    }

    @Test
    @DisplayName("findAll: tüm petler")
    void testFindAll() {
        repo.save(new Dog(0, "d1", null, 1));
        repo.save(new Cat(0, "c1", null, 1));
        repo.save(new Bird(0, "b1", null, 1));
        List<Pet> all = repo.findAll();
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("findAll: boş repo")
    void testFindAllEmpty() {
        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    @DisplayName("update: isim değişir")
    void testUpdate() {
        Dog dog = new Dog(0, "OldName", null, 1);
        repo.save(dog);
        dog.setName("NewName");
        assertTrue(repo.update(dog));
        assertEquals("NewName", repo.findById(dog.getId()).get().getName());
    }

    @Test
    @DisplayName("update null: false")
    void testUpdateNull() {
        assertFalse(repo.update(null));
    }

    @Test
    @DisplayName("delete: pet silinir")
    void testDelete() {
        Dog dog = new Dog(0, "ToDelete", null, 1);
        int id = repo.save(dog);
        assertTrue(repo.delete(id));
        assertTrue(repo.findById(id).isEmpty());
    }

    @Test
    @DisplayName("delete olmayan: false")
    void testDeleteNotFound() {
        assertFalse(repo.delete(9999));
    }

    @Test
    @DisplayName("deleteAll: tümü silinir")
    void testDeleteAll() {
        repo.save(new Dog(0, "d1", null, 1));
        repo.save(new Dog(0, "d2", null, 1));
        repo.deleteAll();
        assertEquals(0, repo.count());
    }

    @Test
    @DisplayName("count: doğru sayı")
    void testCount() {
        assertEquals(0, repo.count());
        repo.save(new Dog(0, "d1", null, 1));
        assertEquals(1, repo.count());
    }

    @Test
    @DisplayName("close: kaynaklar kapatılır")
    void testClose() {
        assertDoesNotThrow(() -> repo.close());
    }

    @Test
    @DisplayName("getConnection: null değil")
    void testGetConnection() {
        assertNotNull(repo.getConnection());
    }

    @Test
    @DisplayName("findByOwnerId: filtreler")
    void testFindByOwnerId() {
        repo.save(new Dog(0, "OwnedByOne", null, 1));
        repo.save(new Dog(0, "OwnedByTwo", null, 2));
        List<Pet> list = repo.findByOwnerId(1);
        assertEquals(1, list.size());
        assertEquals("OwnedByOne", list.get(0).getName());
    }

    @Test
    @DisplayName("Pet birthDate null: kaydedilir")
    void testNullBirthDate() {
        Dog dog = new Dog(0, "NoBirthday", null, 1);
        int id = repo.save(dog);
        Optional<Pet> found = repo.findById(id);
        assertTrue(found.isPresent());
        assertNull(found.get().getBirthDate());
    }
}
