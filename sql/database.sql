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

INSERT INTO Staff (FullName, Role, Salary, Email, PasswordHash, Phone, DateJoined, BranchID)
VALUES ('Admin user', 'Manager', 10000, 'admin@dreamhome.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '0123456789', '2024-01-01', 1);