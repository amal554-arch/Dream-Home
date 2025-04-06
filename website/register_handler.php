<?php
// website/register_handler.php
require_once '../config/db.php';
require_once 'utils/PasswordHasher.php';

// Helper to redirect
function redirect($url) {
    header("Location: $url");
    exit();
}

// Step 1: Validate required fields
$required = ['FullName', 'Phone', 'Email', 'PreferredType', 'MaxRent', 'BranchID', 'Password', 'ConfirmPassword'];
foreach ($required as $field) {
    if (empty($_POST[$field])) {
        die("Missing field: $field");
    }
}

// Step 2: Extract and sanitize input
$FullName = trim($_POST['FullName']);
$Phone = trim($_POST['Phone']);
$Email = trim($_POST['Email']);
$PreferredType = trim($_POST['PreferredType']);
$MaxRent = (int) $_POST['MaxRent'];
$BranchID = (int) $_POST['BranchID'];
$Password = $_POST['Password'];
$ConfirmPassword = $_POST['ConfirmPassword'];

if ($Password !== $ConfirmPassword) {
    die("Passwords do not match.");
}

// Step 3: Hash the password
$HashedPassword = PasswordHasher::hash($Password);

// Step 4: Find the manager of the selected branch
$query = $conn->prepare("SELECT StaffID FROM staff WHERE Role = 'Manager' AND BranchID = ? LIMIT 1");
$query->bind_param("i", $BranchID);
$query->execute();
$query->bind_result($StaffID);

if (!$query->fetch()) {
    die("No branch manager found for the selected branch.");
}
$query->close();

// Step 5: Insert into client table
$stmt = $conn->prepare("INSERT INTO client 
    (FullName, Phone, Email, PreferredType, MaxRent, BranchID, StaffID, PasswordHash) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
$stmt->bind_param("ssssdiis", $FullName, $Phone, $Email, $PreferredType, $MaxRent, $BranchID, $StaffID, $HashedPassword);

if ($stmt->execute()) {
    // Success → redirect to properties
    redirect("properties.php");
} else {
    die("Database error: " . $stmt->error);
}
?>
