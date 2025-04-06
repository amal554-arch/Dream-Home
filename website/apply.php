<?php
session_start();
require_once '../config/db.php';

if (!isset($_SESSION['ClientID'])) {
    header("Location: login.php");
    exit;
}

$clientId = $_SESSION['ClientID'];
$clientName = $_SESSION['ClientName'];
$propertyId = isset($_POST['property_id']) ? intval($_POST['property_id']) : 0;

// Fetch property & branch & staff info
$sql = "SELECT p.Address, p.BranchID, b.Address AS BranchAddress, b.Phone AS BranchPhone,
               s.Email AS StaffEmail, s.Phone AS StaffPhone
        FROM property p
        JOIN branch b ON p.BranchID = b.BranchID
        JOIN staff s ON p.StaffID = s.StaffID
        WHERE p.PropertyID = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $propertyId);
$stmt->execute();
$info = $stmt->get_result()->fetch_assoc();

if (!$info) {
    echo "Invalid property."; exit;
}

$today = date("Y-m-d");
?>

<!DOCTYPE html>
<html>
<head>
    <title>Apply for Lease - DreamHome</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f3f4f7;
            margin: 0;
        }

        header {
            background-color: #2c3e50;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        form {
            background: white;
            max-width: 700px;
            margin: 40px auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        h2 {
            margin-top: 0;
            color: #2c3e50;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-bottom: 6px;
        }

        input[type="text"], input[type="email"], input[type="date"], input[type="number"], select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 15px;
        }

        input[readonly] {
            background-color: #f0f0f0;
        }

        .info-box {
            background-color: #f6f8fa;
            padding: 12px;
            margin-bottom: 15px;
            border-left: 4px solid #3498db;
            border-radius: 6px;
        }

        .info-box span {
            display: block;
            color: #333;
            margin: 4px 0;
        }

        button {
            background-color: #3498db;
            color: white;
            padding: 12px 25px;
            border: none;
            font-size: 15px;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>

<header>
    <h2>DreamHome</h2>
    <div>
        <a href="properties.php" style="color:white; text-decoration: none; font-weight: bold;">← Back to Listings</a>
    </div>
</header>

<form action="submit_lease.php" method="POST">
    <h2>Apply for Lease</h2>

    <div class="form-group">
        <label>Property ID</label>
        <input type="text" name="property_id" value="<?= $propertyId ?>" readonly>
    </div>

    <div class="form-group">
        <label>Property Address</label>
        <input type="text" value="<?= htmlspecialchars($info['Address']) ?>" readonly>
    </div>

    <div class="form-group">
        <label>Client ID</label>
        <input type="text" value="<?= $clientId ?>" readonly>
    </div>

    <div class="form-group">
        <label>Client Name</label>
        <input type="text" value="<?= htmlspecialchars($clientName) ?>" readonly>
    </div>

    <div class="form-group">
        <label>Start Date</label>
        <input type="text" value="<?= $today ?>" readonly>
    </div>

    <div class="form-group">
        <label>Branch</label>
        <input type="text" value="<?= htmlspecialchars($info['BranchAddress']) ?>" readonly>
    </div>

    <div class="info-box">
        <strong>📞 Branch Contact:</strong>
        <span><?= htmlspecialchars($info['BranchPhone']) ?></span>
        <strong>📧 Staff Contact:</strong>
        <span>Email: <?= htmlspecialchars($info['StaffEmail']) ?></span>
        <span>Phone: <?= htmlspecialchars($info['StaffPhone']) ?></span>
    </div>

    <div class="form-group">
        <label>End Date</label>
        <input type="date" name="end_date" required min="<?= $today ?>">
    </div>

    <div class="form-group">
        <label>Deposit Amount (£)</label>
        <input type="number" name="deposit_amount" required step="0.01">
    </div>

    <div class="form-group">
        <label>Payment Frequency</label>
        <select name="payment_frequency" required>
            <option value="">-- Select --</option>
            <option value="Monthly">Monthly</option>
            <option value="Quarterly">Quarterly</option>
            <option value="Annually">Annually</option>
        </select>
    </div>

    <div class="form-group">
        <label>Payment Method</label>
        <select name="payment_method" required>
            <option value="">-- Select --</option>
            <option value="Bank Transfer">Bank Transfer</option>
            <option value="Credit Card">Credit Card</option>
            <option value="Cash">Cash</option>
        </select>
    </div>

    <button type="submit">Submit Application</button>
</form>

</body>
</html>
