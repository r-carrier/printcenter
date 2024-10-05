CREATE TABLE t_print_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255),
    color BOOLEAN,
    notes VARCHAR(255),
    requested_by VARCHAR(255),
    request_date TIMESTAMP,
    meeting_owner VARCHAR(255)
);