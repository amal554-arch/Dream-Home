<?php
session_start();
require_once '../config/db.php';

if (!isset($_SESSION['ClientID'])) {
    // Redirect to login if not logged in
    header("Location: login.php");
    exit;
}

$clientId = $_SESSION['ClientID'];
$propertyId = isset($_POST['property_id']) ? intval($_POST['property_id']) : 0;
$comment = isset($_POST['comment']) ? trim($_POST['comment']) : "";

if ($propertyId <= 0 || empty($comment)) {
    // Redirect with error
    header("Location: property_details.php?id=$propertyId&error=1");
    exit;
}

$comment = htmlspecialchars($comment); // Basic sanitation
$viewingDate = date('Y-m-d');

// Insert into viewing table
$stmt = $conn->prepare("INSERT INTO viewing (ClientID, PropertyID, ViewingDate, Comment) VALUES (?, ?, ?, ?)");
$stmt->bind_param("iiss", $clientId, $propertyId, $viewingDate, $comment);

if ($stmt->execute()) {
    // Redirect with success
    header("Location: property_details.php?id=$propertyId#comments");
} else {
    // Redirect with failure
    header("Location: property_details.php?id=$propertyId&error=1");
}
exit;
