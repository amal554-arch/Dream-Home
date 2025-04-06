<?php
session_start();
require_once '../config/db.php';

if (!isset($_SESSION['ClientID'])) {
    header("Location: login.php");
    exit;
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $propertyId = intval($_POST['property_id']);
    $clientId = $_SESSION['ClientID'];
    $startDate = date('Y-m-d');
    $endDate = $_POST['end_date'];
    $depositAmount = floatval($_POST['deposit_amount']);
    $paymentFrequency = $_POST['payment_frequency'];
    $paymentMethod = $_POST['payment_method'];

    // Get staffID from property table
    $staffQuery = $conn->prepare("SELECT StaffID, IsAvailable FROM property WHERE PropertyID = ?");
    $staffQuery->bind_param("i", $propertyId);
    $staffQuery->execute();
    $staffResult = $staffQuery->get_result()->fetch_assoc();

    if (!$staffResult || !$staffResult['IsAvailable']) {
        echo "<script>alert('This property is no longer available.'); window.location='properties.php';</script>";
        exit;
    }

    $staffId = $staffResult['StaffID'];

    // Insert into lease
    $insert = $conn->prepare("INSERT INTO lease (PropertyID, ClientID, StaffID, StartDate, EndDate, DepositAmount, PaymentFrequency, PaymentMethod) 
                              VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    $insert->bind_param("iiissdss", $propertyId, $clientId, $staffId, $startDate, $endDate, $depositAmount, $paymentFrequency, $paymentMethod);

    if ($insert->execute()) {
        echo "<script>alert('Your lease application has been submitted successfully.'); window.location='properties.php';</script>";
    } else {
        echo "<script>alert('Something went wrong. Please try again.'); window.history.back();</script>";
    }
}
?>
