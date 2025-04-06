<?php
// website/register.php
require_once '../config/db.php';

// Fetch branches from DB
$branches = $conn->query("SELECT BranchID, Address FROM branch ORDER BY BranchID");
?>

<!DOCTYPE html>
<html>
<head>
    <title>Client Registration - DreamHome</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            background-color: #f4f6f8;
        }

        .container {
            max-width: 600px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        form label {
            display: block;
            margin: 15px 0 5px;
            font-weight: bold;
        }

        form input, form select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        form button {
            margin-top: 25px;
            width: 100%;
            padding: 12px;
            background-color: #2c3e50;
            color: white;
            border: none;
            font-weight: bold;
            font-size: 16px;
            border-radius: 6px;
            cursor: pointer;
        }

        form button:hover {
            background-color: #1a252f;
        }

        .back-link {
            margin-top: 20px;
            text-align: center;
        }

        .back-link a {
            color: #2980b9;
            text-decoration: none;
        }

        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Client Registration</h2>

        <form method="POST" action="register_handler.php">
            <label>Full Name</label>
            <input type="text" name="FullName" required>

            <label>Phone</label>
            <input type="tel" name="Phone" required>

            <label>Email</label>
            <input type="email" name="Email" required>

            <label>Preferred Property Type</label>
            <select name="PreferredType" required>
                <option value="">-- Select --</option>
                <option value="Apartment">Apartment</option>
                <option value="House">House</option>
                <option value="Villa">Villa</option>
            </select>

            <label>Max Rent (£)</label>
            <input type="number" name="MaxRent" required min="0">

            <label>Branch</label>
            <select name="BranchID" required>
                <option value="">-- Select Branch --</option>
                <?php while($row = $branches->fetch_assoc()): ?>
                    <option value="<?= $row['BranchID'] ?>"><?= htmlspecialchars($row['Address']) ?></option>
                <?php endwhile; ?>
            </select>

            <label>Password</label>
            <input type="password" name="Password" required>

            <label>Confirm Password</label>
            <input type="password" name="ConfirmPassword" required>

            <button type="submit">Register</button>
        </form>

        <div class="back-link">
            <p>Already have an account? <a href="login.php">Login here</a></p>
        </div>
    </div>

</body>
</html>
