<?php
/**
 * API Endpoint: Get All Drugs
 * URL: http://your-domain.com/backend/api/drugs.php
 */

require_once 'config.php';

$database = new Database();
$db = $database->getConnection();

if ($db === null) {
    http_response_code(500);
    echo json_encode([
        "success" => false,
        "message" => "Database connection failed"
    ]);
    exit();
}

try {
    $query = "SELECT id, drug_name, min_dose, max_dose, concentration, unit FROM drugs ORDER BY drug_name ASC";
    $stmt = $db->prepare($query);
    $stmt->execute();
    
    $drugs = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    http_response_code(200);
    echo json_encode([
        "success" => true,
        "data" => $drugs
    ]);
    
} catch(PDOException $e) {
    http_response_code(500);
    echo json_encode([
        "success" => false,
        "message" => "Error fetching drugs: " . $e->getMessage()
    ]);
}
?>
