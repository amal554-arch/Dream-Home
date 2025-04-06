<?php
// website/logout.php
session_start();
session_unset();     // Clear all session variables
session_destroy();   // Destroy the session

header("Location: properties.php");  // Redirect to homepage after logout
exit();
?>
