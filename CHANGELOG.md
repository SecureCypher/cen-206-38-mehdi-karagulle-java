# Changelog

Bu proje için yapılan tüm önemli değişiklikler bu dosyada belgelenmektedir.

Format [Keep a Changelog](https://keepachangelog.com/tr/1.1.0/) standardına uygundur.

## [2.0.0] - 2026-03-12

### Added
- Swing GUI arayüzü (FlatLaf + MigLayout ile modern tasarım)
- `MainFrame`, `PetPanel`, `ReminderPanel`, `SettingsPanel` bileşenleri
- MySQL desteği (`MySqlRepository`, `docker-compose.yml`)
- `RepositoryFactory` ile runtime storage seçimi (Binary / SQLite / MySQL)
- `StorageConfig` ile yapılandırma yönetimi
- JaCoCo ile test coverage (%100 hedef, GUI ve MySQL hariç)
- Checkstyle (Google Java Style) ve SpotBugs entegrasyonu
- CI/CD: `ci.yml` — her push/PR'da otomatik build & test
- Fat JAR oluşturma (maven-shade-plugin)

### Changed
- JUnit 4 → JUnit 5 geçişi (tüm test sınıfları)
- SQLite repository'lerde H2 uyumluluğu (AUTOINCREMENT → AUTO_INCREMENT)
- `PetSqliteRepository.mapRow()` Türkçe species eşleşmesi eklendi

### Fixed
- `PetModelTest` JUnit import hataları düzeltildi
- `StorageConfigTest` yanlış metod adı (`getBinaryDataPath` → `getBinaryDirectory`)
- `PetServiceTest` species filtreleme hatası (`"Dog"` → `"Kopek"`)
- Tüm SQLite repository tablo oluşturma hatası (H2 test DB için)

## [1.0.0] - 2026-02-15

### Added
- Proje temel yapısı (Maven, Java 11)
- `Pet` model sınıfı ve alt sınıflar (`Dog`, `Cat`, `Bird`)
- `Reminder` model sınıfı ve alt sınıflar (`FeedingReminder`, `VetReminder`, `GroomingReminder`, `WalkReminder`, `MedicationReminder`)
- `IRepository<T>` arayüzü (`save`, `findById`, `findAll`, `update`, `delete`)
- `BinaryRepository<T>` — dosya tabanlı kalıcı depolama (Serializable)
- `SqliteRepository<T>` — SQLite veritabanı depolama
- `PetService` ve `ReminderService` iş mantığı katmanları
- `ConsoleApp` — konsol tabanlı kullanıcı arayüzü
- JUnit 5 ile temel birim testleri
- README.md ve LICENSE dosyaları
