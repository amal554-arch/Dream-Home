# 🏠 DreamHome Property Management System

DreamHome is a hybrid property rental management system with:
- A **JavaFX desktop application** for staff operations.
- A **PHP + MySQL website** for client interactions and property browsing.

---

## 📦 Project Structure

```
DreamHome/
├── src/                  # JavaFX application
├── view/                 # FXML views
├── images/               # Property images
├── website/              # PHP backend for clients
├── sql/                  # SQL schema
├── config.properties     # App config
└── README.md
```

---

## 🚀 How to Run (JavaFX App)

### Prerequisites:
- Java 17+ (recommended Java 21)
- JavaFX SDK
- MySQL Server (localhost)
- Maven or IDE with JavaFX support

### Setup Steps:

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/dreamhome.git
   cd dreamhome
   ```

2. **Configure Database**
   - Import `sql/dreamhome_schema.sql` into your local MySQL server.

3. **Set Config**
   Edit `config.properties`:
   ```
   db.host=localhost
   db.port=3306
   db.name=dreamhome
   db.user=root
   db.password=yourpassword
   image.directory=images/
   ```

4. **Run the App**
   - Use your IDE to launch `DreamHomeApp.java`, or build with Maven.

---

## 🌐 How to Run (PHP Website)

### Prerequisites:
- PHP 7.4+ (preferably 8.0+)
- Apache or XAMPP
- MySQL Server

### Setup Steps:

1. **Copy `website/` folder** into your XAMPP `htdocs` directory (or equivalent).
   ```bash
   cp -r website/ /path/to/htdocs/dreamhome
   ```

2. **Set up DB Connection**
   Edit `website/config/db.php`:
   ```php
   $host = 'localhost';
   $db = 'dreamhome';
   $user = 'root';
   $pass = 'yourpassword';
   ```

3. **Serve and Visit:**
   ```
   http://localhost/dreamhome/properties.php
   ```

---

## 👥 Collaborating

To contribute:

1. Fork this repository
2. Create a new branch
3. Push your changes and open a pull request
4. Add your name to CONTRIBUTORS.md (optional)

If you're a direct collaborator, simply pull the latest changes before committing.

---

## 📌 Current Phase: 5.2

We're currently building the **public property listing page** (`properties.php`) and laying the foundation for future client features.

---

## 🧑‍💻 Tech Stack

- JavaFX (frontend app)
- MySQL (database)
- PHP (website backend)
- HTML/CSS (website UI)
- JavaMail (optional for email sending)

---

## 📄 License

MIT License