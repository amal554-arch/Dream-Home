<?php
session_start();
require_once '../config/db.php';

$sql = "SELECT 
            p.PropertyID, p.Type, p.Address, p.Rent, p.Rooms, MIN(pi.ImagePath) AS ImagePath,
            b.Phone AS BranchPhone
        FROM property p
        LEFT JOIN propertyimage pi ON p.PropertyID = pi.PropertyID
        INNER JOIN branch b ON p.BranchID = b.BranchID
        WHERE p.IsAvailable = TRUE
        GROUP BY p.PropertyID
        ORDER BY p.PropertyID DESC";

$result = $conn->query($sql);
?>

<!DOCTYPE html>
<html>
<head>
    <title>Available Properties - DreamHome</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, sans-serif;
            margin: 0;
            background: #f0f2f5;
            display: flex;
            flex-direction: column;
            height: 100vh;
        }

        header {
            background-color:rgb(119, 166, 233);
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .nav-buttons {
            display: flex;
            gap: 15px;
        }

        .nav-buttons a, .dropdown-toggle {
            background-color:rgb(151, 203, 238);
            color: white;
            padding: 8px 16px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
            border: none;
            cursor: pointer;
        }

        .dropdown {
            position: relative;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            top: 45px;
            right: 0;
            background-color: white;
            min-width: 160px;
            box-shadow: 0 8px 16px rgba(255, 245, 245, 0.15);
            z-index: 100;
            border-radius: 6px;
        }

        .dropdown-content a {
            display: block;
            padding: 10px;
            color:rgb(253, 254, 254);
            text-decoration: none;
        }

        .dropdown-content a:hover {
            background-color: #f1f1f1;
        }

        .show { display: block; }

        .content {
            display: flex;
            flex: 1;
            overflow: hidden;
        }

        .sidebar {
            width: 300px;
            background-color: #ffffff;
            padding: 20px;
            border-left: 1px solid #ddd;
            box-shadow: inset 1px 0 5px rgba(0,0,0,0.05);
        }

        .main {
            flex: 1;
            padding: 30px 40px;
            overflow-y: auto;
        }

        .property-card {
            display: flex;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.05);
            margin-bottom: 25px;
            overflow: hidden;
            text-decoration: none;
            color: inherit;
            transition: transform 0.2s ease;
        }

        .property-card:hover {
            transform: scale(1.01);
        }

        .property-card img {
            width: 200px;
            height: 160px;
            object-fit: cover;
        }

        .property-info {
            padding: 15px 20px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            flex: 1;
        }

        .property-info h2 {
            font-size: 20px;
            margin: 0 0 5px;
            color: #2c3e50;
        }

        .price {
            color: #27ae60;
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 6px;
        }

        .details-row {
            display: flex;
            gap: 20px;
            margin: 10px 0;
            font-weight: 500;
        }

        .branch-phone {
            font-size: 14px;
            color: #888;
            margin-top: auto;
        }

        .placeholder-bar {
            margin-bottom: 20px;
            color: #666;
            font-style: italic;
        }

        a.property-card {
            display: flex;
            border: none;
        }

    </style>
</head>
<body>

<header>
    <h2>DreamHome</h2>
    <div class="nav-buttons">
        <?php if (isset($_SESSION['ClientID'])): ?>
            <div class="dropdown">
                <button class="dropdown-toggle" onclick="toggleDropdown()">Welcome, <?= htmlspecialchars($_SESSION['ClientName']) ?> ⯆</button>
                <div id="dropdownMenu" class="dropdown-content">
                    <a href="profile.php">View Profile</a>
                    <a href="logout.php">Logout</a>
                </div>
            </div>
        <?php else: ?>
            <a href="login.php">Login</a>
            <a href="register.php">Register</a>
        <?php endif; ?>
    </div>
</header>

<div class="content">
    <div class="main">
        <h1>Available Properties</h1>
        <p class="placeholder-bar">🔍 Filters and search will be added soon...</p>

        <?php if ($result && $result->num_rows > 0): ?>
            <?php while($row = $result->fetch_assoc()): ?>
                <a class="property-card" href="property_details.php?id=<?= $row['PropertyID'] ?>">
                    <img src="<?= htmlspecialchars($row['ImagePath']) ?: 'https://via.placeholder.com/300x200?text=No+Image' ?>" alt="Image">
                    <div class="property-info">
                        <h2><?= htmlspecialchars($row['Address']) ?></h2>
                        <div class="price">£<?= $row['Rent'] ?></div>
                        <div class="details-row">
                            <span><?= htmlspecialchars($row['Type']) ?></span>
                            <span><?= $row['Rooms'] ?> Rooms</span>
                        </div>
                        <div class="branch-phone">📞 <?= htmlspecialchars($row['BranchPhone']) ?></div>
                    </div>
                </a>
            <?php endwhile; ?>
        <?php else: ?>
            <p>No available properties found.</p>
        <?php endif; ?>
    </div>

    <div class="sidebar">
        <h3>Coming Soon</h3>
        <p>🔍 Filter by price, type, rooms</p>
        <p>📍 Map integration</p>
        <p>💬 Live chat support</p>
        <p>📢 Featured properties</p>
    </div>
</div>

<script>
function toggleDropdown() {
    document.getElementById("dropdownMenu").classList.toggle("show");
}
window.onclick = function(event) {
    if (!event.target.matches('.dropdown-toggle')) {
        document.querySelectorAll(".dropdown-content").forEach(el => el.classList.remove("show"));
    }
}
</script>

</body>
</html>
