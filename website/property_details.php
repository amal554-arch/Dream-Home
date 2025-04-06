<?php
session_start();
require_once '../config/db.php';

$propertyId = isset($_GET['id']) ? intval($_GET['id']) : 0;
if ($propertyId <= 0) {
    echo "Invalid Property ID."; exit;
}

$sql = "SELECT p.*, 
            b.Address AS BranchAddress, b.Phone AS BranchPhone,
            o.FullName AS OwnerName, o.Phone AS OwnerPhone, o.Email AS OwnerEmail,
            s.Email AS StaffEmail
        FROM property p
        JOIN branch b ON p.BranchID = b.BranchID
        JOIN owner o ON p.OwnerID = o.OwnerID
        JOIN staff s ON p.StaffID = s.StaffID
        WHERE p.PropertyID = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $propertyId);
$stmt->execute();
$property = $stmt->get_result()->fetch_assoc();

// Images
$images = [];
$imgStmt = $conn->prepare("SELECT ImagePath FROM propertyimage WHERE PropertyID = ?");
$imgStmt->bind_param("i", $propertyId);
$imgStmt->execute();
$imgRes = $imgStmt->get_result();
while ($row = $imgRes->fetch_assoc()) {
    $images[] = $row['ImagePath'];
}

// Comments
$comments = [];
$commentStmt = $conn->prepare("SELECT c.FullName, v.Comment, v.ViewingDate 
                               FROM viewing v 
                               JOIN client c ON v.ClientID = c.ClientID 
                               WHERE v.PropertyID = ?
                               ORDER BY v.ViewingDate DESC");
$commentStmt->bind_param("i", $propertyId);
$commentStmt->execute();
$commentRes = $commentStmt->get_result();
while ($row = $commentRes->fetch_assoc()) {
    $comments[] = $row;
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Property Details - DreamHome</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            background: #f0f2f5;
        }

        header {
            background-color:rgb(119, 166, 233);
            padding: 15px 30px;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .dropdown {
            position: relative;
        }

        .dropdown-toggle {
            background-color:rgb(151, 203, 238);
            border: none;
            color: white;
            padding: 8px 16px;
            font-weight: bold;
            border-radius: 6px;
            cursor: pointer;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            top: 45px;
            right: 0;
            background-color: white;
            min-width: 160px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.15);
            z-index: 100;
            border-radius: 6px;
        }

        .dropdown-content a {
            display: block;
            padding: 10px;
            color: #2c3e50;
            text-decoration: none;
        }

        .dropdown-content a:hover {
            background-color: #f1f1f1;
        }

        .show { display: block; }

        .container {
            max-width: 1100px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .carousel-container {
            display: flex;
            align-items: center;
            margin-bottom: 30px;
        }

        .carousel-gallery {
            display: flex;
            gap: 12px;
            overflow-x: auto;
            scroll-behavior: smooth;
            scrollbar-width: none;
        }

        .carousel-gallery::-webkit-scrollbar {
            display: none;
        }

        .carousel-gallery img {
            height: 220px;
            border-radius: 8px;
            cursor: zoom-in;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            flex-shrink: 0;
        }

        .carousel-btn {
            background-color: #3498db;
            color: white;
            border: none;
            font-size: 20px;
            font-weight: bold;
            border-radius: 50%;
            padding: 10px 14px;
            cursor: pointer;
        }

        .carousel-btn:hover {
            background-color: #2980b9;
        }

        .details {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.07);
            margin-bottom: 30px;
        }

        .details h2 {
            margin-top: 0;
            font-size: 24px;
            color: #2c3e50;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 16px;
            margin-top: 20px;
        }

        .info-box {
            background: #f9f9f9;
            border-left: 4px solid #3498db;
            padding: 12px 15px;
            border-radius: 6px;
        }

        .info-box strong {
            display: block;
            font-weight: 600;
            margin-bottom: 5px;
            color: #34495e;
        }

        .action {
            margin-top: 25px;
        }

        button {
            padding: 12px 25px;
            background-color: #3498db;
            color: white;
            border: none;
            font-weight: bold;
            font-size: 15px;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #2980b9;
        }

        .comments {
            background: white;
            padding: 25px 30px;
            border-radius: 10px;
            margin-top: 30px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.08);
        }

        .comments h3 {
            margin-top: 0;
            font-size: 22px;
            color: #2c3e50;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }

        .comment {
            margin-bottom: 25px;
            padding: 15px;
            background-color: #f9f9f9;
            border-left: 4px solid #3498db;
            border-radius: 6px;
        }

        .comment strong {
            display: block;
            margin-bottom: 5px;
            font-size: 16px;
            color: #34495e;
        }

        .comment small {
            color: #888;
            font-size: 12px;
        }

        textarea {
            width: 100%;
            height: 100px;
            resize: vertical;
            padding: 10px;
            font-family: inherit;
        }

        .form-group {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>

<header>
    <h2>DreamHome</h2>
    <div>
        <?php if (isset($_SESSION['ClientID'])): ?>
            <div class="dropdown">
                <button class="dropdown-toggle" onclick="toggleDropdown()">Welcome, <?= htmlspecialchars($_SESSION['ClientName']) ?> ⯆</button>
                <div id="dropdownMenu" class="dropdown-content">
                    <a href="profile.php">View Profile</a>
                    <a href="logout.php">Logout</a>
                </div>
            </div>
        <?php else: ?>
            <a href="login.php" style="color:white; margin-right:10px;">Login</a>
            <a href="register.php" style="color:white;">Register</a>
        <?php endif; ?>
    </div>
</header>

<div class="container">

    <!-- Carousel -->
    <div class="carousel-container">
        <button class="carousel-btn" onclick="scrollGallery(-1)">←</button>
        <div class="carousel-gallery" id="galleryStrip">
            <?php foreach ($images as $img): ?>
                <img src="<?= htmlspecialchars($img) ?>" alt="Property Image" onclick="zoomImage(this.src)">
            <?php endforeach; ?>
            <?php if (empty($images)): ?>
                <img src="https://via.placeholder.com/400x300?text=No+Image" alt="No Image">
            <?php endif; ?>
        </div>
        <button class="carousel-btn" onclick="scrollGallery(1)">→</button>
    </div>

    <!-- Property Info -->
    <div class="details">
        <h2>🏠 <?= $property['Type'] ?> – <span style="color: #27ae60;">£<?= $property['Rent'] ?></span></h2>

        <div class="info-grid">
            <div class="info-box"><strong>📍 Address</strong><?= $property['Address'] ?></div>
            <div class="info-box"><strong>🛏️ Rooms</strong><?= $property['Rooms'] ?></div>
            <div class="info-box"><strong>🏢 Branch</strong><?= $property['BranchAddress'] ?></div>
            <div class="info-box"><strong>📞 Branch Phone</strong><?= $property['BranchPhone'] ?></div>
            <div class="info-box"><strong>👤 Owner</strong><?= $property['OwnerName'] ?></div>
            <div class="info-box"><strong>📞 Owner Phone</strong><?= $property['OwnerPhone'] ?></div>
            <div class="info-box"><strong>📧 Owner Email</strong><?= $property['OwnerEmail'] ?></div>
            <div class="info-box"><strong>📨 Assigned Staff Email</strong><?= $property['StaffEmail'] ?></div>
        </div>

        <div class="action">
            <?php if (isset($_SESSION['ClientID'])): ?>
                <form method="POST" action="apply.php">
                    <input type="hidden" name="property_id" value="<?= $propertyId ?>">
                    <button type="submit">Apply for Lease</button>
                </form>
            <?php else: ?>
                <a href="login.php"><button>Login to Apply</button></a>
            <?php endif; ?>
        </div>
    </div>

    <!-- Comments -->
    <div class="comments">
        <h3>💬 Client Comments</h3>
        <?php if ($comments): ?>
            <?php foreach ($comments as $c): ?>
                <div class="comment">
                    <strong><?= htmlspecialchars($c['FullName']) ?></strong>
                    <small><?= date('F j, Y', strtotime($c['ViewingDate'])) ?></small>
                    <p><?= nl2br(htmlspecialchars($c['Comment'])) ?></p>
                </div>
            <?php endforeach; ?>
        <?php else: ?>
            <p>No comments yet.</p>
        <?php endif; ?>

        <?php if (isset($_SESSION['ClientID'])): ?>
            <form method="POST" action="comment_handler.php">
                <input type="hidden" name="property_id" value="<?= $propertyId ?>">
                <div class="form-group">
                    <label for="comment">Post a comment:</label>
                    <textarea name="comment" required></textarea>
                </div>
                <button type="submit">Submit Comment</button>
            </form>
        <?php else: ?>
            <p><a href="login.php">Login</a> to post a comment.</p>
        <?php endif; ?>
    </div>
</div>

<script>
function scrollGallery(dir) {
    const container = document.getElementById("galleryStrip");
    container.scrollBy({ left: dir * 250, behavior: 'smooth' });
}

function zoomImage(src) {
    const zoomWin = window.open("", "_blank");
    zoomWin.document.write("<img src='" + src + "' style='width:100%;' />");
}

function toggleDropdown() {
    document.getElementById("dropdownMenu").classList.toggle("show");
}

window.onclick = function(e) {
    if (!e.target.matches('.dropdown-toggle')) {
        document.querySelectorAll(".dropdown-content").forEach(d => d.classList.remove("show"));
    }
}
</script>

</body>
</html>
