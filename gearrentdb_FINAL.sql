
 Database: gearrentdb


DROP DATABASE IF EXISTS gearrentdb;
CREATE DATABASE gearrentdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gearrentdb;

CREATE TABLE settings (
    setting_key VARCHAR(100) PRIMARY KEY,
    setting_value VARCHAR(255) NOT NULL
);

INSERT INTO settings VALUES
('MAX_RENTAL_DAYS','30'),
('MAX_DEPOSIT_PER_CUSTOMER','500000'),
('LONG_RENTAL_DAYS','7'),
('LONG_RENTAL_DISCOUNT','10');

CREATE TABLE membership_levels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL,
    discount_percent DECIMAL(5,2) DEFAULT 0.00
);

INSERT INTO membership_levels (name, discount_percent) VALUES
('Regular',0),('Silver',5),('Gold',10);

CREATE TABLE branches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR(100),
    address VARCHAR(255),
    contact VARCHAR(50)
);

INSERT INTO branches VALUES
(1,'PAN','Panadura Branch','Panadura','038-1234567'),
(2,'GAL','Galle Branch','Galle','091-9876543'),
(3,'COL','Colombo Branch','Colombo','011-4567890');

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password_hash VARCHAR(128),
    role ENUM('Admin','BranchManager','Staff'),
    branch_id INT,
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);

INSERT INTO users VALUES
(1,'admin',SHA2('admin123',256),'Admin',NULL),
(2,'pan_mgr',SHA2('pan123',256),'BranchManager',1),
(3,'gal_staff',SHA2('gal123',256),'Staff',2);

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    base_price_factor DECIMAL(5,2),
    weekend_multiplier DECIMAL(5,2),
    late_fee_per_day DECIMAL(10,2)
);

INSERT INTO categories VALUES
(1,'Camera',1.00,1.15,1500),
(2,'Lens',0.50,1.10,800),
(3,'Drone',1.40,1.25,2500),
(4,'Lighting',0.60,1.10,500),
(5,'Audio',0.55,1.10,600);

CREATE TABLE equipment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50),
    category_id INT,
    branch_id INT,
    brand VARCHAR(50),
    model VARCHAR(50),
    daily_price DECIMAL(10,2),
    deposit DECIMAL(10,2),
    status ENUM('Available','Reserved','Rented','Maintenance'),
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);

INSERT INTO equipment (code,category_id,branch_id,brand,model,daily_price,deposit,status) VALUES
('CAM001',1,1,'Canon','EOS R5',15000,100000,'Available'),
('CAM002',1,1,'Sony','A7 IV',14000,90000,'Available'),
('CAM003',1,2,'Nikon','Z6',13000,85000,'Available'),
('CAM004',1,3,'Canon','5D IV',12000,80000,'Available'),
('LEN001',2,1,'Canon','24-70mm',5000,30000,'Available'),
('LEN002',2,2,'Sigma','85mm',4500,28000,'Available'),
('LEN003',2,3,'Tamron','70-200mm',4800,32000,'Available'),
('DRN001',3,1,'DJI','Mavic 3',18000,150000,'Available'),
('DRN002',3,2,'DJI','Air 2S',15000,120000,'Available'),
('DRN003',3,3,'Autel','EVO II',16000,130000,'Available'),
('LGT001',4,1,'Godox','SL60',3000,15000,'Available'),
('LGT002',4,2,'Aputure','120D',4000,20000,'Available'),
('AUD001',5,1,'Rode','NTG4+',2000,10000,'Available'),
('AUD002',5,2,'Zoom','H6',2500,12000,'Available'),
('AUD003',5,3,'Sennheiser','G4',3000,15000,'Available'),
('CAM005',1,3,'Sony','FX3',15500,110000,'Available'),
('LEN004',2,1,'Sony','35mm',4200,25000,'Available'),
('LGT003',4,3,'Aputure','MC',1500,8000,'Available'),
('AUD004',5,2,'Tascam','DR-40',1800,9000,'Available'),
('CAM006',1,2,'Panasonic','S5',9500,60000,'Available');

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    nic VARCHAR(20),
    contact VARCHAR(50),
    email VARCHAR(100),
    membership_id INT,
    FOREIGN KEY (membership_id) REFERENCES membership_levels(id)
);

INSERT INTO customers (name,nic,contact,email,membership_id) VALUES
('Nimal Perera','901234567V','0771111111','nimal@gmail.com',1),
('Saman Silva','902345678V','0772222222','saman@gmail.com',2),
('Kamal Fernando','903456789V','0773333333','kamal@gmail.com',3),
('Amaya Jayasuriya','904567890V','0774444444','amaya@gmail.com',1),
('Ishara Perera','905678901V','0775555555','ishara@gmail.com',2),
('Dinesh Kumar','906789012V','0776666666','dinesh@gmail.com',1),
('Priya Rodrigo','907890123V','0777777777','priya@gmail.com',3),
('Ruwan De Silva','908901234V','0778888888','ruwan@gmail.com',1),
('Kavindi Fernando','909012345V','0779999999','kavindi@gmail.com',2),
('Suresh Wijesinghe','900123456V','0760000000','suresh@gmail.com',1);

CREATE TABLE rentals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipment_id INT,
    customer_id INT,
    branch_id INT,
    start_date DATE,
    end_date DATE,
    status ENUM('Active','Returned','Overdue'),
    FOREIGN KEY (equipment_id) REFERENCES equipment(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);

INSERT INTO rentals VALUES
(1,1,3,1,'2025-11-01','2025-11-05','Returned'),
(2,8,2,1,'2025-11-10','2025-11-15','Overdue'),
(3,5,1,1,'2025-12-01','2025-12-03','Active');

CREATE TABLE reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipment_id INT,
    customer_id INT,
    branch_id INT,
    start_date DATE,
    end_date DATE,
    status ENUM('Active','Cancelled'),
    FOREIGN KEY (equipment_id) REFERENCES equipment(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);

INSERT INTO reservations VALUES
(1,2,4,1,'2025-12-20','2025-12-24','Active'),
(2,9,5,2,'2025-12-10','2025-12-12','Active');





USE gearrentdb;
ALTER TABLE rentals
ADD COLUMN actual_return_date DATE NULL,
ADD COLUMN damage_charge DECIMAL(10,2) NOT NULL DEFAULT 0,
ADD COLUMN late_fee DECIMAL(10,2) NOT NULL DEFAULT 0,
ADD COLUMN discount DECIMAL(10,2) NOT NULL DEFAULT 0,
ADD COLUMN total_charge DECIMAL(10,2) NOT NULL DEFAULT 0,
ADD COLUMN amount_paid DECIMAL(10,2) NOT NULL DEFAULT 0,
ADD COLUMN payment_status ENUM('Paid','Unpaid') NOT NULL DEFAULT 'Unpaid';


ALTER TABLE reservations 
ADD COLUMN payment_status ENUM('Paid', 'Unpaid') NOT NULL DEFAULT 'Unpaid';

