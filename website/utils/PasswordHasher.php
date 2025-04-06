<?php
// utils/PasswordHasher.php

class PasswordHasher {
    public static function hash($password) {
        // Return SHA-256 hex string
        return hash('sha256', $password);
    }

    public static function verify($password, $hash) {
        // Re-hash input and compare
        return hash('sha256', $password) === $hash;
    }
}
?>
