# DreamHome Application – Phase 1: Understanding Requirements

## 🧠 System Overview

The DreamHome system is a hybrid application comprising:
- A **JavaFX desktop application** for internal use by DreamHome employees.
- A **DreamHome website** for external use by clients to view properties, comment, and request leases.

The goal is to streamline internal processes like property/client registration and lease handling, while enabling clients to interact through a simple web interface.

---

## ✅ Functional Requirements

### 🔐 Authentication
- Staff must log in using credentials (username + password).
- If not registered, staff can register using a **staff registration form**.

### 👥 Staff Management
- View staff listings by selecting a branch number or address.
- Staff information shown in the format of the **staff listing report** from the case study.

### 🔐 Role-Based Access Control

- Each staff member has a **Role** field: Manager, Supervisor, or Assistant.
- A **Salary (GBP)** field is included in the Staff table.
- Only users with the role **Manager** can view the salary of other staff.
- This access control is enforced within the JavaFX application logic.

### 🏠 Property Management
- Register new properties with:
  - Owner-provided details as per the **property registration form**.
  - **Images** of the property (stored in filesystem or DB).
- Once registered, the property appears on the DreamHome website.

### 👤 Client Management
- Register clients using data from the **client registration form**.
- Weekly emails are sent to clients listing properties currently available for rent (randomized).
- Clicking a property in the email leads to the property page on the DreamHome website.

### 💬 Client Interaction via Website
- View property details, images, and client comments.
- Submit their own comments.
- Apply to lease a property via the website.
- After receiving a lease decision (approve/reject), clients can request an extension through a hidden lease-edit page.

### 📝 Lease Management (via Application)
- View submitted lease applications.
- Approve or reject leases; client notified by email.
- Approve or reject lease extension requests; follow-up email sent to client.

---

## 🔧 Non-Functional Requirements

| Category        | Requirement |
|----------------|-------------|
| **Security**   | Staff-only login; password hashing; client access limited to website |
| **Storage**    | Property images stored either as blobs or in file system |
| **Communication** | Email notifications for lease applications, decisions, and property listings |
| **User Experience** | Staff use full-featured JavaFX app; clients use simplified website |
| **Scalability** | Modular design allows app and site to evolve separately |
| **Data Integrity** | Enforced via foreign keys, unique constraints, and application validation |

---

## 👤 User Roles and Actions

### 👨‍💼 Staff (JavaFX App)
- Register/login
- Manage branches, properties, and staff
- Register clients
- Approve or reject lease applications and extension requests

### 🧍‍♂️ Client (Website)
- View property details and images
- Submit comments
- Apply for leases
- Request lease extensions

---

### 🧑‍💼 Staff Roles and Responsibilities (Revised)

The DreamHome system supports three employee roles, designed to reflect a realistic property rental business hierarchy. These roles are enforced via the `role` field in the `staff` table and determine access to different features in the JavaFX application.

---

#### 🧑‍💼 Manager (Branch Manager)
Responsible for administrative and managerial tasks.

| Responsibility         | Description                                                 |
|------------------------|-------------------------------------------------------------|
| Staff Management       | Can register new staff, assign roles, and set salaries      |
| Lease Approvals        | Final approval of lease and extension requests              |
| Branch Oversight       | Can view staff in all branches                              |
| Full Access            | Can access all modules and data, including salaries         |

---

#### 🧑‍🔧 Supervisor (Property Manager)
Manages rental operations including property and client handling.

| Responsibility         | Description                                                 |
|------------------------|-------------------------------------------------------------|
| Property Registration  | Registers new properties and uploads images                 |
| Client Onboarding      | Registers clients and sets their preferences                |
| Lease Processing       | Can review, recommend, and approve leases                   |
| View Staff             | Can view other staff (without salary visibility)            |
| Limited Access         | Cannot register staff or view/assign salaries               |

---

#### 🧑‍💻 Assistant (Front Desk / Entry-Level Staff)
Supports day-to-day operations and customer-facing tasks.

| Responsibility         | Description                                                 |
|------------------------|-------------------------------------------------------------|
| View Data              | Can view client and property information                    |
| Comment Management     | Can view property comments and assist with follow-up        |
| Read-Only Access       | Cannot register clients, properties, or approve leases      |
| Staff Viewer           | Can view staff assigned to their branch (without salary)    |

---

### 🧭 Shared Dashboard with Role-Based Access Control

Instead of using separate dashboard UIs for each role, the system will implement a **single shared dashboard (`dashboard.fxml`)**. At runtime, visibility of buttons and features will be adjusted based on the logged-in user's role (stored in `Session.getLoggedInStaff()`).

Example logic in the controller:

```java
if (Session.isManager()) {
    registerStaffButton.setVisible(true);
    salaryColumn.setVisible(true);
} else if (Session.isSupervisor()) {
    registerPropertyButton.setVisible(true);
    approveLeaseButton.setVisible(true);
} else {
    commentViewer.setVisible(true);
    propertyViewer.setVisible(true);
}
```
This approach reduces FXML duplication and centralizes navigation logic, while still enforcing access control both in the frontend and backend.

---

## 🌐 DreamHome Website Responsibilities
- Display available properties and images
- Show and submit property comments
- Provide lease application forms
- Handle lease extension requests via hidden URLs
- Interface with backend to notify staff (or update DB)
- Send client-related emails (listing updates, lease responses)

---

## 🧩 Architecture Summary

- JavaFX App (Staff UI) ←→ MySQL (Shared DB) ←→ PHP (Website Backend, optional)
- Images stored in `/images/` folder or as blobs in MySQL
- Email integration handled by Java backend or website backend

---

### 📦 Deployment Strategy – Standalone Offline Setup

The DreamHome system will be distributed as a **self-contained JavaFX application** with a **locally running MySQL database**. This allows the system to run on any machine without requiring an internet connection or server infrastructure.

---

#### ✅ Components Included in Distribution

1. **JavaFX Executable JAR**
   - Built with dependencies using Maven or Gradle
   - Launches the application GUI

2. **Local MySQL Database**
   - MySQL server installed locally (on the same machine)
   - Database preloaded with schema and optional test data

3. **Images Folder**
   - Property images stored locally in an `images/` folder
   - Image paths are stored in the database

4. **Config File**
   - A `config.properties` file holds database connection info, image folder path, etc.

```properties
db.host=localhost
db.port=3306
db.name=dreamhome
db.user=root
db.password=yourpassword
image.directory=images/
```
---

### 🧩 Module Overview – DreamHome Application

The following modules define the structure and responsibilities of the DreamHome desktop application (JavaFX) and supporting website/email features.

#### 1. Auth & Session
- Staff login and session management
- Secure password hashing and verification

#### 2. Staff Management
- Register new staff
- View staff by branch
- Salaries visible only to managers

#### 3. Branch Reference
- Branches are defined in the database only
- No UI for branch CRUD operations

#### 4. Property Management
- Register properties, assign staff, branch, owner
- Upload and store property images
- Mark property available/unavailable

#### 5. Owner Management
- Register new property owners
- Store owner contact info

#### 6. Client Management
- Register new clients with preferences
- Assign to a staff and branch
- Preferences used for email matching

#### 7. Viewing Reports (via website)
- Clients view properties on website
- Leave comments and apply for lease
- No scheduling system in JavaFX app

#### 8. Lease Management
- View incoming lease applications
- Approve or reject
- Handle lease extension requests

#### 9. Email Handling
- Weekly property report emails to clients
- Lease decision emails to clients
- Property rented confirmation email to owners

#### 10. System Settings
- Load DB configuration from `config.properties`
- Handle image path config, application-wide constants

---

## 🛠️ Phase 5 – Implementation Plan

This phase outlines the final file structure and step-by-step implementation strategy for the DreamHome system. Each phase will be developed independently, following a modular, testable approach.

---

### 📁 Updated File Structure – DreamHome Application

```file structure
DreamHome/
│
├── .vscode/
│   ├── launch.json
│   └── settings.json
│
├── docs/
│   ├── design-doc.md
│   ├── DreamHome.pdf
│   └── ER-DreamHome.png
│
├── src/
│   └── dreamhome/
│       ├── model/                      # Data classes
│       │   ├── Branch.java
│       │   ├── Client.java
│       │   ├── Lease.java
│       │   ├── Owner.java
│       │   ├── Property.java
│       │   ├── PropertyImage.java
│       │   ├── Staff.java
│       │   ├── StaffViewModel.java
│       │   └── Viewing.java
│       │
│       ├── dao/                        # Data access objects
│       │   ├── BranchDAO.java
│       │   ├── ClientDAO.java
│       │   ├── LeaseDAO.java
│       │   ├── OwnerDAO.java
│       │   ├── PropertyDAO.java
│       │   ├── PropertyImageDAO.java
│       │   ├── StaffDAO.java
│       │   └── ...
│       │
│       ├── controller/                 # JavaFX controllers
│       │   ├── DashboardController.java
│       │   ├── LeaseListController.java
│       │   ├── LoginController.java
│       │   ├── OwnerFormController.java
│       │   ├── OwnerLookupController.java
│       │   ├── PropertyFormController.java
│       │   ├── StaffFormController.java
│       │   └── StaffListController.java
│       │
│       ├── utils/                      # Utilities
│       │   ├── ConfigLoader.java
│       │   ├── DBUtil.java
│       │   ├── EmailUtil.java
│       │   ├── FileUtil.java
│       │   ├── PasswordHasher.java
│       │   └── Session.java
│       │
│       └── DreamHomeApp.java           # Main application
│
├── view/                               # JavaFX FXML views
│   ├── client_form.fxml
│   ├── dashboard.fxml
│   ├── lease_list.fxml
│   ├── login.fxml
│   ├── owner_form.fxml
│   ├── owner_lookup.fxml
│   ├── property_form.fxml
│   ├── staff_form.fxml
│   └── staff_list.fxml
│
├── images/                             # Property image folder
│   └── ...
│
├── config.properties                   # DB, image path config
├── README.md
└── sql/
    └── dreamhome_schema.sql

```

---

### 🚀 Implementation Roadmap

The project will be built in the following phases:

---

#### ✅ Phase 1: Environment Setup & Core Utilities
- Create folder structure and `config.properties`
- Build utilities:
  - `DBUtil.java`
  - `ConfigLoader.java`
  - `Session.java`
  - `PasswordHasher.java`
- Test DB connection and session handling

---

#### ✅ Phase 2: Auth Module (Login)
- Build `login.fxml` and `LoginController`
- Authenticate using `StaffDAO` and `PasswordHasher`
- Store active user in `Session`
- Redirect to role-based dashboard

---

#### ✅ Phase 3: Staff Management
- `staff_form.fxml` to register staff (managers see salary field)
- `staff_list.fxml` to view staff by branch
- Role-based salary visibility

---

#### ✅ Phase 4: Owner Management
- Register new property owners via form
- Link owner during property registration
- Use `OwnerDAO` for all DB interactions

---

## ✅ Phase 5.1: Property Management (JavaFX)

- Register new properties with all required details (type, rooms, rent, address).
- Associate properties with registered owners using the owner lookup feature.
- Upload property images (stored via path, not blob).
- Auto-assign current staff and branch from the logged-in session.
- Mark properties as available/unavailable.
- Integrated testing completed with property, owner, and owner lookup modules.
- ❌ Properties are not yet visible to clients on the website.

---

## 🚧 Phase 5.2: Website Property Display (Public View)

This phase includes building the **website-side infrastructure** required to display properties to the public.

### 📌 Objective:
Allow all visitors to the website to browse **available properties** without logging in.

### 🧱 Tasks:

1. **Website Bootstrap**
   - Set up the `website/` folder structure.
   - Add config and utility scripts (e.g. `db.php`, `PasswordHasher.php`).

2. **Property Retrieval Backend**
   - Create PHP logic to fetch available properties from MySQL.
   - Join with image paths and optional owner/branch details.

3. **Public Property Listing UI (`properties.php`)**
   - Render available properties in a card or grid layout.
   - Display image, type, rent, rooms, address, availability.
   - Include a placeholder for “Apply” or “Login to Apply”.

4. **Image Access Setup**
   - Ensure the `/images/` directory is accessible by the website.
   - Dynamically load images using paths from the database.

5. **Finalize Public Interface**
   - Fully working public `properties.php` page.
   - Clean layout ready for styling and future interactions.

✅ **Phase 5.2 completes once `properties.php` is functional and deployed.**

---

## ✅ Phase 6.1: Client Management (JavaFX)

- Allow staff to register new clients using a form.
- Store full name, phone, email, preferred property type, and max rent.
- Auto-assign staff and branch based on current session.
- Clients added to the weekly email list.
- ❌ Requires clients to visit the branch in person (being replaced in Phase 6.2).

---

## 🔄 Phase 6.2: Website Client Registration & Authentication

This phase replaces JavaFX-based client registration with **self-registration via the website**.

### 📌 Objective:
Allow clients to register themselves from home using a secure web form.

### 🧱 Tasks:

1. **Client Registration (`register_client.php`)**
   - Form to collect full name, phone, email, password (hashed), preferred type, max rent.
   - Client selects a branch; assigned staff defaults to branch manager.

2. **Client Login (`login_client.php`)**
   - Email + password authentication system.
   - Session management to persist login state.

3. **Post-Registration Flow**
   - Redirect to property listing with confirmation message.
   - Add new client to weekly email list.

4. **Client Session Handling**
   - Track logged-in client for future lease applications and comments.

✅ **Phase 6.2 completes once client registration, login, and email enrollment are working.**

---

#### ✅ Phase 7: Lease Management
- Review applications from website
- Approve, reject, or extend lease
- Trigger client and owner notifications via email

---

#### ✅ Phase 8: Email Handling
- Use SMTP to:
  - Send weekly listings to clients
  - Lease approvals/rejections to clients
  - Property rented confirmations to owners
- Centralized `EmailUtil` for formatting and sending

---

#### ✅ Phase 9: Packaging & Testing
- Final project testing
- Package app as `.jar` or executable
- Include setup instructions and dummy data
