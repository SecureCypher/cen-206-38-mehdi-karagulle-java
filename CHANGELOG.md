# Changelog
All notable changes to the Pet Care Reminder System will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-03-27

### Added
- **Core Models:** 
  - Implementation of polymorphic hierarchy for `Pet` (`Dog`, `Cat`, `Bird`).
  - Implementation of polymorphic hierarchy for `Reminder` (`FeedingReminder`, `MedicationReminder`, `VetAppointment`, `ExerciseReminder`, `GroomingReminder`).
- **Repositories:** 
  - Multiple Storage Backend Implementations (Binary, SQLite, MySQL) via `RepositoryFactory`.
  - Advanced Template Method usage in `SqliteRepository`.
  - Enhanced binary serialization in `BinaryRepository`.
- **Observer Pattern:** 
  - Implemented EventManager mechanism to notify listeners on CRUD operations (`PET_ADDED`, `REMINDER_COMPLETED`, etc.).
- **Unit Testing:** 
  - Comprehensive JUnit 5 test suite reaching >95% Instruction and Branch Coverage (JaCoCo).
- **Documentation:**
  - Javadoc/Doxygen documentation coverage 100%.
  - Included Class, Use-Case, ER, and Sequence Diagrams across `design/plantuml` folder.
- **CI/CD:**
  - Integrated GitHub Actions CI Pipeline (`.github/workflows/ci.yml`) to perform automated Maven builds and JaCoCo reports on pushes/PRs.

### Fixed
- Addressed file resource leaks (e.g. `FileInputStream`) during testing within `BinaryRepository.java`.
- Corrected reflection errors around ID mappings during fallback edge cases.
- Ensured thread safety in the `EventManager` singleton by switching to `CopyOnWriteArrayList` and robust lock mechanisms.
