<?php
/**
 * API Endpoint: Get All Surgeries
 * URL: http://your-domain.com/backend/api/surgeries.php
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
    $query = "SELECT id, surgery_name FROM surgeries ORDER BY surgery_name ASC";
    $stmt = $db->prepare($query);
    $stmt->execute();
    
    $surgeries = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    http_response_code(200);
    echo json_encode([
        "success" => true,
        "data" => $surgeries
    ]);
    
} catch(PDOException $e) {
    http_response_code(500);
    echo json_encode([
        "success" => false,
        "message" => "Error fetching surgeries: " . $e->getMessage()
    ]);
}
?>
