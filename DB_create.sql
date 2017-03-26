DROP DATABASE IF EXISTS Bus_station;
CREATE DATABASE Bus_station
CHARACTER SET 'utf8';

USE Bus_station;

CREATE TABLE Companies(
	Company INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	CompanyName NVARCHAR(510) NOT NULL UNIQUE
);

CREATE TABLE Clients(
	Client INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	FirstName NVARCHAR(510) NOT NULL,
    LastName NVARCHAR(510) NOT NULL,
	Patronymic NVARCHAR(510) NULL,
	Address NVARCHAR(510) NOT NULL,
    Telephone VARCHAR(30) NOT NULL UNIQUE,
    Email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Stations(
	Station INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	StationName NVARCHAR(510) NOT NULL UNIQUE
);

CREATE TABLE Runs(
	Run INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	RunNumber NVARCHAR(10) NOT NULL UNIQUE,
	Company INTEGER NOT NULL REFERENCES Companies(Company) ON DELETE CASCADE ON UPDATE CASCADE,
	BusCapacity INTEGER NOT NULL
);

CREATE TABLE Stops(
	`Stop` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	Run INTEGER NOT NULL REFERENCES Runs(Run) ON DELETE CASCADE ON UPDATE CASCADE,
	Station INTEGER NOT NULL REFERENCES Stations(Station) ON DELETE CASCADE ON UPDATE CASCADE,
	Arrival DATETIME NULL,
	Departure DATETIME NULL,
	
    UNIQUE (Run, Station),
    
    CHECK (Departure IS NULL OR Arrival IS NULL OR Departure > Arrival)
);

DELIMITER //
CREATE TRIGGER IntersectElimination
BEFORE INSERT ON Stops
FOR EACH ROW
BEGIN
	IF EXISTS (
		SELECT *
		FROM Stops s
		WHERE NEW.Run = s.Run
			  AND (
          s.Arrival IS NULL AND NEW.Arrival <= s.Departure OR

          s.Departure IS NULL AND NEW.Departure >= s.Arrival OR

          NEW.Arrival IS NULL AND s.Arrival <= NEW.Departure OR

          NEW.Departure IS NULL AND s.Departure >= NEW.Arrival OR

          s.Arrival IS NOT NULL AND s.Departure IS NOT NULL AND NEW.Arrival IS NOT NULL AND NEW.Departure IS NOT NULL AND
          (
				      NEW.Arrival between s.Arrival AND s.Departure OR
				      s.Arrival between NEW.Arrival AND NEW.Departure
          )
			  )
	) THEN
		SIGNAL SQLSTATE '11111';
	END IF;
END//
DELIMITER ;

DELIMITER //
CREATE TRIGGER SingleSourceAndDest
AFTER INSERT ON Stops
FOR EACH ROW
BEGIN
	IF (
		NEW.Arrival IS NULL AND (
			SELECT COUNT(*)
            FROM Stops
            WHERE Arrival IS NULL AND Run = NEW.Run
        ) != 1 OR
		NEW.Departure IS NULL AND (
			SELECT COUNT(*)
            FROM Stops
            WHERE Departure IS NULL AND Run = NEW.Run
        ) != 1
    ) THEN
		SIGNAL SQLSTATE '22222';
	END IF;
END//
DELIMITER ;

CREATE TABLE Parts(
	Part INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `From` INTEGER NOT NULL REFERENCES Stops(`Stop`) ON DELETE CASCADE ON UPDATE CASCADE,
    `To` INTEGER NOT NULL REFERENCES Stops(`Stop`) ON DELETE CASCADE ON UPDATE CASCADE,
    Price DECIMAL(8,2) NOT NULL,
    
    UNIQUE (`From`, `To`)
);

DELIMITER //
CREATE TRIGGER SameRuns_AND_ToGraterThanFrom
AFTER INSERT ON Parts
FOR EACH ROW
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM Stops f JOIN
				 Stops t ON f.Run = t.Run AND
							   f.Stop = NEW.From AND
                               t.Stop = NEW.To AND
							   (f.Arrival IS NULL OR t.Departure IS NULL OR f.Arrival < t.Arrival)
	) THEN
		SIGNAL SQLSTATE '33333';
	END IF;
END//
DELIMITER ;

CREATE TABLE Orders(
	`Order` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	Client INTEGER NOT NULL REFERENCES Clients(Client) ON DELETE CASCADE ON UPDATE CASCADE,
	Part INTEGER NOT NULL REFERENCES Parts(Part) ON DELETE RESTRICT ON UPDATE RESTRICT,
    Count INTEGER NOT NULL DEFAULT 1,
    Price DECIMAL(8,2) NOT NULL
);

DELIMITER //
CREATE TRIGGER DefaultPrice
BEFORE INSERT ON Orders
FOR EACH ROW
BEGIN
	IF (NEW.Price IS NULL)
    THEN
		SET NEW.Price = (
			SELECT Price
            FROM Parts
            WHERE Part = NEW.Part
        );
	END IF;
END//
DELIMITER ;