<?php
// website/config/db.php

$host = 'localhost';
$db   = 'dreamhome_db';
$user = 'root';
$pass = '@amALMOHANAN';

$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    die("Database connection failed: " . $conn->connect_error);
}
?>
