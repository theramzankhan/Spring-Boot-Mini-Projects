CREATE TABLE IF NOT EXISTS customer_info (
    id VARCHAR(100) PRIMARY KEY,
    firstName VARCHAR(200),
    lastName VARCHAR(200), 
    email VARCHAR(100),  -- Ensures no duplicate emails
    gender VARCHAR(100),
    contactNo VARCHAR(100),  -- Ensures no duplicate contact numbers
    country VARCHAR(200),
    dob VARCHAR(100)  -- Changed to DATE type for better date storage
);

