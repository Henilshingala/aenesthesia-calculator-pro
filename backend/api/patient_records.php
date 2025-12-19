<?php
/**
 * API Endpoint: Patient Records Management
 * URL: http://your-domain.com/backend/api/patient_records.php
 * Methods: GET, POST, DELETE
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

$method = $_SERVER['REQUEST_METHOD'];

try {
    switch($method) {
        case 'GET':
            // Fetch patient records for a specific device
            $device_id = isset($_GET['device_id']) ? $_GET['device_id'] : '';
            
            if (empty($device_id)) {
                http_response_code(400);
                echo json_encode([
                    "success" => false,
                    "message" => "Device ID is required"
                ]);
                exit();
            }
            
            $query = "SELECT * FROM patient_records WHERE device_id = :device_id ORDER BY date_created DESC";
            $stmt = $db->prepare($query);
            $stmt->bindParam(':device_id', $device_id);
            $stmt->execute();
            
            $records = $stmt->fetchAll(PDO::FETCH_ASSOC);
            
            // Parse JSON fields
            foreach ($records as &$record) {
                $record['selected_drug_names'] = json_decode($record['selected_drug_names'], true);
                $record['dosage_results'] = json_decode($record['dosage_results'], true);
            }
            
            http_response_code(200);
            echo json_encode([
                "success" => true,
                "data" => $records
            ]);
            break;
            
        case 'POST':
            // Save a new patient record
            $data = json_decode(file_get_contents("php://input"), true);
            
            if (empty($data['device_id']) || empty($data['patient_name'])) {
                http_response_code(400);
                echo json_encode([
                    "success" => false,
                    "message" => "Required fields missing"
                ]);
                exit();
            }
            
            // Generate unique record ID
            $record_id = uniqid('rec_', true);
            
            $query = "INSERT INTO patient_records 
                     (record_id, device_id, patient_name, patient_age, patient_weight, 
                      procedure_type, selected_drug_names, dosage_results, notes) 
                     VALUES 
                     (:record_id, :device_id, :patient_name, :patient_age, :patient_weight, 
                      :procedure_type, :selected_drug_names, :dosage_results, :notes)";
            
            $stmt = $db->prepare($query);
            $stmt->bindParam(':record_id', $record_id);
            $stmt->bindParam(':device_id', $data['device_id']);
            $stmt->bindParam(':patient_name', $data['patient_name']);
            $stmt->bindParam(':patient_age', $data['patient_age']);
            $stmt->bindParam(':patient_weight', $data['patient_weight']);
            $stmt->bindParam(':procedure_type', $data['procedure_type']);
            
            $selected_drugs = json_encode($data['selected_drug_names'] ?? []);
            $dosage_results = json_encode($data['dosage_results'] ?? []);
            $notes = $data['notes'] ?? '';
            
            $stmt->bindParam(':selected_drug_names', $selected_drugs);
            $stmt->bindParam(':dosage_results', $dosage_results);
            $stmt->bindParam(':notes', $notes);
            
            if ($stmt->execute()) {
                http_response_code(201);
                echo json_encode([
                    "success" => true,
                    "message" => "Patient record saved successfully",
                    "record_id" => $record_id
                ]);
            } else {
                http_response_code(500);
                echo json_encode([
                    "success" => false,
                    "message" => "Failed to save patient record"
                ]);
            }
            break;
            
        case 'DELETE':
            // Delete a patient record
            $data = json_decode(file_get_contents("php://input"), true);
            
            if (empty($data['record_id'])) {
                http_response_code(400);
                echo json_encode([
                    "success" => false,
                    "message" => "Record ID is required"
                ]);
                exit();
            }
            
            $query = "DELETE FROM patient_records WHERE record_id = :record_id";
            $stmt = $db->prepare($query);
            $stmt->bindParam(':record_id', $data['record_id']);
            
            if ($stmt->execute()) {
                http_response_code(200);
                echo json_encode([
                    "success" => true,
                    "message" => "Record deleted successfully"
                ]);
            } else {
                http_response_code(500);
                echo json_encode([
                    "success" => false,
                    "message" => "Failed to delete record"
                ]);
            }
            break;
            
        default:
            http_response_code(405);
            echo json_encode([
                "success" => false,
                "message" => "Method not allowed"
            ]);
            break;
    }
    
} catch(PDOException $e) {
    http_response_code(500);
    echo json_encode([
        "success" => false,
        "message" => "Database error: " . $e->getMessage()
    ]);
}
?>
