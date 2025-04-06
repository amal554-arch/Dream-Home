<?php
// website/login.php
session_start();
require_once '../config/db.php';
require_once 'utils/PasswordHasher.php';

$error = "";

// Handle login submission
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = trim($_POST['Email']);
    $password = $_POST['Password'];

    // Fetch client with this email
    $stmt = $conn->prepare("SELECT ClientID, FullName, PasswordHash FROM client WHERE Email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($row = $result->fetch_assoc()) {
        if (PasswordHasher::verify($password, $row['PasswordHash'])) {
            // Login success
            $_SESSION['ClientID'] = $row['ClientID'];
            $_SESSION['ClientName'] = $row['FullName'];
            header("Location: properties.php");
            exit();
        } else {
            $error = "Invalid password.";
        }
    } else {
        $error = "Client not found.";
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Client Login - DreamHome</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f8;
        }

        .container {
            max-width: 500px;
            margin: 60px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-top: 15px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        button {
            margin-top: 25px;
            width: 100%;
            padding: 12px;
            background-color: #2c3e50;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        .error {
            color: red;
            text-align: center;
            margin-top: 15px;
        }

        .register-link {
            text-align: center;
            margin-top: 20px;
        }

        .register-link a {
            color: #2980b9;
            text-decoration: none;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Client Login</h2>

        <?php if (!empty($error)): ?>
            <div class="error"><?= htmlspecialchars($error) ?></div>
        <?php endif; ?>

        <form method="POST" action="">
            <label>Email</label>
            <input type="email" name="Email" required>

            <label>Password</label>
            <input type="password" name="Password" required>

            <button type="submit">Login</button>
        </form>

        <div class="register-link">
            <p>New user? <a href="register.php">Register here</a></p>
        </div>
    </div>

</body>
</html>
