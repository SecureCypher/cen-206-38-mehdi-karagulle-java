-- @file init.sql
-- @brief Pet Reminder uygulaması için MySQL şema tanımı.
-- @details PDF ER Diagram gereksinimiyle uyumlu tablo yapısı.

CREATE DATABASE IF NOT EXISTS petreminder_db CHARACTER SET utf8mb4 COLLATE utf8mb4_turkish_ci;
USE petreminder_db;

-- Kullanıcılar tablosu (KVKK: şifre hash olarak saklanır)
CREATE TABLE IF NOT EXISTS users (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(50) NOT NULL UNIQUE,
    email        VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name    VARCHAR(100),
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_login   DATETIME,
    active       BOOLEAN DEFAULT TRUE
);

-- Evcil hayvanlar tablosu
CREATE TABLE IF NOT EXISTS pets (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    owner_id     INT NOT NULL,
    name         VARCHAR(50) NOT NULL,
    species      VARCHAR(30) NOT NULL,
    breed        VARCHAR(50),
    birth_date   DATE,
    gender       VARCHAR(10),
    weight       DOUBLE,
    notes        TEXT,
    can_talk     BOOLEAN DEFAULT FALSE,
    bird_type    VARCHAR(50),
    is_trained   BOOLEAN DEFAULT FALSE,
    is_indoor    BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Hatırlatıcılar tablosu
CREATE TABLE IF NOT EXISTS reminders (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    pet_id              INT NOT NULL,
    pet_name            VARCHAR(50),
    reminder_type       VARCHAR(30) NOT NULL,
    scheduled_time      DATETIME NOT NULL,
    completed           BOOLEAN DEFAULT FALSE,
    description         TEXT,
    priority            VARCHAR(10) DEFAULT 'ORTA',
    recurring           BOOLEAN DEFAULT FALSE,
    recurrence_pattern  VARCHAR(30),
    -- FeedingReminder fields
    food_type           VARCHAR(50),
    portion_grams       DOUBLE,
    includes_water_change BOOLEAN DEFAULT FALSE,
    -- MedicationReminder fields
    medication_name     VARCHAR(100),
    dosage              DOUBLE,
    dosage_unit         VARCHAR(20),
    prescribed          BOOLEAN DEFAULT FALSE,
    -- ExerciseReminder fields
    exercise_type       VARCHAR(50),
    duration_minutes    INT,
    distance_km         DOUBLE,
    -- GroomingReminder fields
    grooming_type       VARCHAR(50),
    professional        BOOLEAN DEFAULT FALSE,
    groomer_name        VARCHAR(100),
    -- VetAppointment fields
    veterinarian_name   VARCHAR(100),
    clinic_name         VARCHAR(100),
    reason              VARCHAR(100),
    estimated_cost      DOUBLE,
    confirmed           BOOLEAN DEFAULT FALSE,
    clinic_phone        VARCHAR(20),
    FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE
);

-- Sağlık kayıtları tablosu
CREATE TABLE IF NOT EXISTS medical_records (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    pet_id              INT NOT NULL,
    pet_name            VARCHAR(50),
    record_date         DATE NOT NULL,
    record_type         VARCHAR(30) NOT NULL,
    diagnosis           TEXT,
    treatment           TEXT,
    veterinarian_name   VARCHAR(100),
    cost                DOUBLE DEFAULT 0,
    next_check_date     DATE,
    notes               TEXT,
    vaccine_name        VARCHAR(100),
    FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE
);

-- Demo verisi
INSERT IGNORE INTO users (username, email, password_hash, full_name)
VALUES ('demo', 'demo@petreminder.com',
        'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f',
        'Demo Kullanici');
