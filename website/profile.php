<?php
session_start();
require_once '../config/db.php';

if (!isset($_SESSION['ClientID'])) {
    header("Location: login.php");
    exit();
}

$clientID = $_SESSION['ClientID'];
$success = "";
$error = "";

// Handle form submission
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $FullName = trim($_POST['FullName']);
    $Phone = trim($_POST['Phone']);
    $Email = trim($_POST['Email']);
    $PreferredType = $_POST['PreferredType'];
    $MaxRent = (int) $_POST['MaxRent'];
    $BranchID = (int) $_POST['BranchID'];

    $stmt = $conn->prepare("UPDATE client SET FullName=?, Phone=?, Email=?, PreferredType=?, MaxRent=?, BranchID=? WHERE ClientID=?");
    $stmt->bind_param("ssssiii", $FullName, $Phone, $Email, $PreferredType, $MaxRent, $BranchID, $clientID);

    if ($stmt->execute()) {
        $_SESSION['ClientName'] = $FullName; // Update navbar name
        $success = "Profile updated successfully!";
    } else {
        $error = "Error updating profile: " . $stmt->error;
    }
    $stmt->close();
}

// Fetch client data
$stmt = $conn->prepare("SELECT * FROM client WHERE ClientID = ?");
$stmt->bind_param("i", $clientID);
$stmt->execute();
$result = $stmt->get_result();
$client = $result->fetch_assoc();

// Fetch all branches
$branches = $conn->query("SELECT BranchID, Address FROM branch ORDER BY BranchID");
?>

<!DOCTYPE html>
<html>
<head>
    <title>My Profile - DreamHome</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f4f6f8; margin: 0; }
        .container {
            max-width: 600px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h2 { text-align: center; margin-bottom: 25px; }
        label { display: block; margin-top: 15px; font-weight: bold; }
        input, select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
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
        .message {
            margin-top: 15px;
            text-align: center;
            font-weight: bold;
        }
        .success { color: green; }
        .error { color: red; }
    </style>
</head>
<body>

    <div class="container">
        <h2>My Profile</h2>

        <?php if ($success): ?>
            <div class="message success"><?= htmlspecialchars($success) ?></div>
        <?php elseif ($error): ?>
            <div class="message error"><?= htmlspecialchars($error) ?></div>
        <?php endif; ?>

        <form method="POST">
            <label>Full Name</label>
            <input type="text" name="FullName" value="<?= htmlspecialchars($client['FullName']) ?>" required>

            <label>Phone</label>
            <input type="tel" name="Phone" value="<?= htmlspecialchars($client['Phone']) ?>" required>

            <label>Email</label>
            <input type="email" name="Email" value="<?= htmlspecialchars($client['Email']) ?>" required>

            <label>Preferred Type</label>
            <select name="PreferredType" required>
                <option value="Apartment" <?= $client['PreferredType'] == 'Apartment' ? 'selected' : '' ?>>Apartment</option>
                <option value="House" <?= $client['PreferredType'] == 'House' ? 'selected' : '' ?>>House</option>
                <option value="Villa" <?= $client['PreferredType'] == 'Villa' ? 'selected' : '' ?>>Villa</option>
            </select>

            <label>Max Rent</label>
            <input type="number" name="MaxRent" value="<?= htmlspecialchars($client['MaxRent']) ?>" required min="0">

            <label>Branch</label>
            <select name="BranchID" required>
                <?php while($b = $branches->fetch_assoc()): ?>
                    <option value="<?= $b['BranchID'] ?>" <?= $client['BranchID'] == $b['BranchID'] ? 'selected' : '' ?>>
                        <?= htmlspecialchars($b['Address']) ?>
                    </option>
                <?php endwhile; ?>
            </select>

            <button type="submit">Update Profile</button>
        </form>
    </div>

</body>
</html>
