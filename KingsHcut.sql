CREATE DATABASE KingsHCut

USE KingsHcut

CREATE TABLE MsUser(
	UserID CHAR(5) PRIMARY KEY,
	Username VARCHAR(30) NOT NULL,
	UserPassword VARCHAR(20) NOT NULL,
	UserEmail VARCHAR(40) NOT NULL,
	UserRole VARCHAR(10) NOT NULL,
	UserGender VARCHAR(10) NOT NULL,
	UserPhoneNumber VARCHAR(12) NOT NULL
)

CREATE TABLE ReservationHeader(
	ReservationID CHAR(5) PRIMARY KEY,
	UserID CHAR(5) REFERENCES MsUser(UserID) NOT NULL,
	ReservationDate DATE NOT NULL,
	StartReservationTime TIME NOT NULL,
	EndReservationTime TIME NOT NULL,
	ReservationStatus VARCHAR(15) NOT NULL
)

CREATE TABLE MsServiceType(
	ServiceTypeID CHAR(5) PRIMARY KEY,
	ServiceTypeName VARCHAR(30) NOT NULL
)

CREATE TABLE MsService(
	ServiceID CHAR(5) PRIMARY KEY,
	ServiceTypeID CHAR(5) REFERENCES MsServiceType(ServiceTypeID),
	ServiceName VARCHAR(30) NOT NULL,
	ServicePrice INT NOT NULL,
	ServiceDuration INT NOT NULL
)

CREATE TABLE ReservationDetail(
	ReservationID CHAR(5) REFERENCES ReservationHeader(ReservationID),
	ServiceID CHAR(5) REFERENCES MsService(ServiceID)
	PRIMARY KEY(ReservationID, ServiceID)
)


INSERT INTO MsUser VALUES
	('US001', 'khadmin', 'khadmin123', 'khadmin@gmail.com', 'Admin', 'Male', '085512312312'),
	('US002', 'lucas', 'lucas123', 'lucas@gmail.com', 'User', 'Male', '081232321123'),
	('US003', 'sophia', 'sophia123', 'sophia@gmail.com', 'User', 'Female', '0855122312'),
	('US004', 'ethanadmin', 'ethanadmin123', 'ethanadmin@gmail.com', 'Admin', 'Male', '08551233127'),
	('US005', 'avaadmin', 'avaadmin123', 'avaadmin@gmail.com', 'Admin', 'Female', '085553543321'),
	('US006', 'olivia', 'olivia123', 'olivia@gmail.com', 'User', 'Female', '08157879123'),
	('US007', 'liam', 'liam123', 'liam@gmail.com', 'User', 'Male', '08123344553')

INSERT INTO MsServiceType VALUES
	('ST001', 'Haircut'),
	('ST002', 'Hair Perming'),
	('ST003', 'Hair Coloring'),
	('ST004', 'Hair Treatment'),
	('ST005', 'Hair Tattoo')

INSERT INTO MsService VALUES
	('SV001', 'ST001', 'Adult Haircut', 75000, 45),
	('SV002', 'ST002', 'Down Perm', 175000, 90),
	('SV003', 'ST002', 'Korean Perm', 400000, 90),
	('SV004', 'ST001', 'Kids Haircut', 50000, 45),
	('SV005', 'ST003', 'Basic Hair Color', 275000, 90),
	('SV006', 'ST003', 'Fashion Hair Color', 300000, 90),
	('SV007', 'ST004', 'Hair Loss Treatment', 125000, 60),
	('SV008', 'ST004', 'Hair Repair Treatment', 150000, 60),
	('SV009', 'ST004', 'Anti-Dandruff Treatment', 100000, 60),
	('SV010', 'ST005', 'Hair Tattoo', 140000, 60)

INSERT INTO ReservationHeader VALUES
	('RS001', 'US001', '2024-01-19', '14:30', '16:15', 'Finished'),
	('RS002', 'US002', '2024-01-19', '16:30', '18:45', 'Finished'),
	('RS003', 'US003', '2024-01-20', '10:30', '12:45', 'Finished'),
	('RS004', 'US004', '2024-01-20', '13:30', '15:00', 'Finished'),
	('RS005', 'US005', '2024-01-21', '11:30', '15:45', 'Finished'),
	('RS006', 'US006', '2024-01-22', '10:30', '12:45', 'Cancelled'),
	('RS007', 'US007', '2024-01-23', '13:30', '15:45', 'Finished'),
	('RS008', 'US002', '2024-01-24', '16:00', '17:45', 'Cancelled'),
	('RS009', 'US003', '2024-01-24', '18:00', '20:15', 'Finished'),
	('RS010', 'US004', '2024-01-25', '18:00', '18:45', 'In progress')

INSERT INTO ReservationDetail VALUES
	('RS001', 'SV001'),
	('RS001', 'SV007'),
	('RS002', 'SV001'),
	('RS002', 'SV002'),
	('RS003', 'SV001'),
	('RS003', 'SV005'),
	('RS004', 'SV006'),
	('RS005', 'SV001'),
	('RS005', 'SV007'),
	('RS005', 'SV008'),
	('RS005', 'SV009'),
	('RS006', 'SV001'),
	('RS006', 'SV002'),
	('RS007', 'SV001'),
	('RS007', 'SV006'),
	('RS008', 'SV001'),
	('RS008', 'SV010'),
	('RS009', 'SV001'),
	('RS009', 'SV003'),
	('RS010', 'SV004')

SELECT * FROM MsUser
SELECT * FROM MsService
SELECT * FROM MsServiceType
SELECT * FROM ReservationHeader
SELECT * FROM ReservationDetail