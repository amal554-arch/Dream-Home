-- ========================
-- BRANCH TABLE
-- ========================
CREATE TABLE Branch (
  BranchID INT PRIMARY KEY AUTO_INCREMENT,
  Address VARCHAR(255) NOT NULL,
  Phone VARCHAR(20)
);

-- ========================
-- STAFF TABLE
-- ========================
CREATE TABLE Staff (
  StaffID INT PRIMARY KEY AUTO_INCREMENT,
  FullName VARCHAR(100) NOT NULL,
  Role ENUM('Manager', 'Supervisor', 'Assistant') NOT NULL,
  Salary FLOAT NOT NULL,
  Email VARCHAR(100) UNIQUE NOT NULL,
  PasswordHash VARCHAR(255) NOT NULL,
  Phone VARCHAR(20),
  DateJoined DATE,
  BranchID INT,
  FOREIGN KEY (BranchID) REFERENCES Branch(BranchID)
);

-- ========================
-- OWNER TABLE
-- ========================
CREATE TABLE Owner (
  OwnerID INT PRIMARY KEY AUTO_INCREMENT,
  FullName VARCHAR(100) NOT NULL,
  Phone VARCHAR(20),
  Email VARCHAR(100),
  Address VARCHAR(255)
);

-- ========================
-- PROPERTY TABLE
-- ========================
CREATE TABLE Property (
  PropertyID INT PRIMARY KEY AUTO_INCREMENT,
  Address VARCHAR(255) NOT NULL,
  Type VARCHAR(50),
  Rooms INT,
  Rent FLOAT,
  IsAvailable BOOLEAN DEFAULT TRUE,
  BranchID INT,
  OwnerID INT,
  StaffID INT,
  FOREIGN KEY (BranchID) REFERENCES Branch(BranchID),
  FOREIGN KEY (OwnerID) REFERENCES Owner(OwnerID),
  FOREIGN KEY (StaffID) REFERENCES Staff(StaffID)
);

-- ========================
-- PROPERTY IMAGE TABLE
-- ========================
CREATE TABLE PropertyImage (
  ImageID INT PRIMARY KEY AUTO_INCREMENT,
  PropertyID INT,
  ImagePath VARCHAR(255) NOT NULL,
  FOREIGN KEY (PropertyID) REFERENCES Property(PropertyID)
);

-- ========================
-- CLIENT TABLE
-- ========================
CREATE TABLE Client (
  ClientID INT PRIMARY KEY AUTO_INCREMENT,
  FullName VARCHAR(100) NOT NULL,
  Phone VARCHAR(20),
  Email VARCHAR(100),
  PreferredType VARCHAR(50),
  MaxRent FLOAT,
  StaffID INT,
  BranchID INT,
  PasswordHash VARCHAR(255),
  FOREIGN KEY (StaffID) REFERENCES Staff(StaffID),
  FOREIGN KEY (BranchID) REFERENCES Branch(BranchID)
);

-- ========================
-- VIEWING TABLE
-- ========================
CREATE TABLE Viewing (
  ViewingID INT PRIMARY KEY AUTO_INCREMENT,
  ClientID INT,
  PropertyID INT,
  ViewingDate DATE,
  Comment TEXT,
  FOREIGN KEY (ClientID) REFERENCES Client(ClientID),
  FOREIGN KEY (PropertyID) REFERENCES Property(PropertyID)
);

-- ========================
-- LEASE TABLE
-- ========================
CREATE TABLE Lease (
  LeaseID INT PRIMARY KEY AUTO_INCREMENT,
  PropertyID INT,
  ClientID INT,
  StaffID INT,
  StartDate DATE NOT NULL,
  EndDate DATE NOT NULL,
  Status ENUM('Pending', 'Approved', 'Rejected', 'Extended') DEFAULT 'Pending',
  FOREIGN KEY (PropertyID) REFERENCES Property(PropertyID),
  FOREIGN KEY (ClientID) REFERENCES Client(ClientID),
  FOREIGN KEY (StaffID) REFERENCES Staff(StaffID)
);

-- ==========================
-- INITIALIZE THE APPLICATION
-- ==========================

INSERT INTO branch (address, phone) VALUES
('45 Sauchiehall Street, Glasgow G2 3AP', '0141 101 0001'),
('129 Byres Road, Glasgow G12 8TT', '0141 101 0002'),
('18 Argyle Street, Glasgow G1 2NN', '0141 101 0003'),
('200 Dumbarton Road, Glasgow G11 6UN', '0141 101 0004'),
('78 Buchanan Street, Glasgow G1 3HA', '0141 101 0005'),
('12 Duke Street, Glasgow G4 0UL', '0141 101 0006'),
('8 Pollokshaws Road, Glasgow G41 1QG', '0141 101 0007'),
('65 Great Western Road, Glasgow G4 9HT', '0141 101 0008'),
('33 George Square, Glasgow G2 1DU', '0141 101 0009'),
('50 Renfield Street, Glasgow G2 1LU', '0141 101 0010');


INSERT INTO Staff (FullName, Role, Salary, Email, PasswordHash, Phone, DateJoined, BranchID)
VALUES ('Admin user', 'Manager', 10000, 'admin@dreamhome.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '0123456789', '2024-01-01', 1);