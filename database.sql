DROP DATABASE IF EXISTS AIRLINE_DATABASE;
CREATE DATABASE AIRLINE_DATABASE; 
USE AIRLINE_DATABASE;

DROP TABLE IF EXISTS AIRCRAFT;
CREATE TABLE AIRCRAFT(
    aircraftName    varchar(50),

    PRIMARY KEY (aircraftName)
);

DROP TABLE IF EXISTS FLIGHT;
CREATE TABLE FLIGHT(
	flightID		varchar(50),
    aircraftName	varchar(50),
    origin			varchar(50),
    destination		varchar(50), 
    depTime			varchar(50),
    depDate			varchar(50),
    arrTime			varchar(50),
    arrDate			varchar(50),
    bClass			int,
    cClass			int,
    fClass			int,
    
	PRIMARY KEY (flightID),
    FOREIGN KEY (aircraftName) REFERENCES AIRCRAFT(aircraftName)
);

DROP TABLE IF EXISTS LOGIN;
CREATE TABLE LOGIN (
	username	varchar(50)	NOT NULL,
    pw			varchar(50),
    
    PRIMARY KEY (username)
);

DROP TABLE IF EXISTS CREDITCARD;
CREATE TABLE CREDITCARD (
	cardNumber	bigint,
    cvv			int,
    expiryMonth	int,
    expiryYear	int,
    username	varchar(50),
    
    PRIMARY KEY (cardNumber),
    FOREIGN KEY (username) REFERENCES LOGIN(username)
);

DROP TABLE IF EXISTS SEAT;
CREATE TABLE SEAT (
	seatNumber	int,
    cost		int,
    seatType	varchar(50),
	flightID	varchar(50),
    
    PRIMARY KEY (seatNumber),
    FOREIGN KEY (flightID) REFERENCES FLIGHT(flightID)
);

DROP TABLE IF EXISTS PILOT;
CREATE TABLE PILOT (
	pilotNumber		int,
    crewNumber		int,
    fName			varchar(50),
    lName			varchar(50),
    
    PRIMARY KEY (pilotNumber)
);

DROP TABLE IF EXISTS STEWARD;
CREATE TABLE STEWARD (
	employeeNumber	int,
    crewNumber		int,
    fName			varchar(50),
    lName			varchar(50),
    
    PRIMARY KEY (employeeNumber)
);

DROP TABLE IF EXISTS CUSTOMER;
CREATE TABLE CUSTOMER (
	username	varchar(50),
    pw			varchar(50),
    email		varchar(50),
    phoneNumber	varchar(50),
    fName		varchar(50),
    lName		varchar(50),
    
    FOREIGN KEY (username) REFERENCES LOGIN(username)
    
);

DROP TABLE IF EXISTS ADMINS;
CREATE TABLE ADMINS (
	username	varchar(50),
    pw			varchar(50),
    fName		varchar(50),
    lName		varchar(50),
    
    FOREIGN KEY (username) REFERENCES LOGIN(username)
);

DROP TABLE IF EXISTS AGENT;
CREATE TABLE AGENT (
	username	varchar(50),
    pw			varchar(50),
    fName		varchar(50),
    lName		varchar(50),
    
    FOREIGN KEY (username) REFERENCES LOGIN(username)
);

DROP TABLE IF EXISTS TICKET;
CREATE TABLE TICKET (
    username				varchar(50),
    seatNumber				int,
    flightNumber			varchar(50),

    FOREIGN KEY (username) REFERENCES CUSTOMER(username),
    FOREIGN KEY (seatNumber) REFERENCES SEAT(seatNumber),
    FOREIGN KEY (flightNumber) REFERENCES FLIGHT(flightID)
    
);

DROP TABLE IF EXISTS RESERVATION;
CREATE TABLE RESERVATION (
    username				varchar(50),
    flightNumber			varchar(50),

    FOREIGN KEY (username) REFERENCES CUSTOMER(username),
    FOREIGN KEY (flightNumber) REFERENCES FLIGHT(flightID)
);

-- Insert data into AICRAFT table
INSERT INTO AIRCRAFT VALUES
('Boeing 737'), 
('Airbus A320');

-- Insert data into FLIGHT table
INSERT INTO FLIGHT VALUES
('F1', 'Boeing 737', 'Calgary', 'Vancouver', '08:00', '2023-01-01', '10:00', '2023-01-01', 20, 30, 10),
('F2', 'Airbus A320', 'Toronto', 'Edmonton', '12:00', '2023-01-02', '14:00', '2023-01-02', 20, 30, 10),
('F3', 'Boeing 737', 'San Francisco', 'Dallas', '16:00', '2023-01-03', '18:00', '2023-01-03', 20, 30, 10);

-- Insert data into LOGIN table
INSERT INTO LOGIN VALUES
('user1', 'password1'),
('user2', 'password2'),
('admin1', 'adminpassword1'),
('agent1', 'agentpassword1');

-- Insert data into CREDITCARD table
INSERT INTO CREDITCARD VALUES
('1111222233334444', 123, 12, 2023, 'user1'),
('2222333344445555', 456, 11, 2024, 'user2');

-- Insert data into SEAT table
INSERT INTO SEAT VALUES
(1, 50, 'Commercial', 'F1'),
(2, 100, 'Business', 'F1'),
(3, 150, 'First Class', 'F1'),
(4, 40, 'Commercial', 'F2'),
(5, 90, 'Business', 'F2'),
(6, 130, 'First Class', 'F2'),
(7, 60, 'Commercial', 'F3'),
(8, 110, 'Business', 'F3'),
(9, 160, 'First Class', 'F3');

-- Insert data into PILOT table
INSERT INTO PILOT VALUES
(1, 101, 'John', 'Doe'),
(2, 102, 'Jane', 'Smith');

-- Insert data into STEWARD table
INSERT INTO STEWARD VALUES
(201, 101, 'Mark', 'Johnson'),
(202, 102, 'Emily', 'Wilson');

-- Insert data into CUSTOMER table
INSERT INTO CUSTOMER VALUES
('user1', 'password1', 'user1@email.com', '1234567890', 'John', 'Doe'),
('user2', 'password2', 'user2@email.com', '9876543210', 'Jane', 'Smith');

-- Insert data into ADMINS table
INSERT INTO ADMINS VALUES
('admin1', 'adminpassword1', 'Admin', 'One');

-- Insert data into AGENT table
INSERT INTO AGENT VALUES
('agent1', 'agentpassword1', 'Agent', 'Smith');

-- Insert data into TICKET table
INSERT INTO TICKET VALUES
('user1', 1, 'F1'),
('user2', 4, 'F2'),
('user1', 7, 'F3');

-- Insert data into RESERVATION table
INSERT INTO RESERVATION VALUES
('user1', 'F1'),
('user2', 'F2');