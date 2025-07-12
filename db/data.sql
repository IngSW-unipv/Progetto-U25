INSERT INTO linea VALUES (1), (2);

INSERT INTO conducente (nome, cognome, cf, pass_hash)
VALUES
("Bumi","Rumble","RBMB123456XYZ789","52c8e02987ccb1b0eef9c7c966aebf03c92a159aafda37fbcf03f4838dbcd48d"),
("Toph","Beifong","BFNG987654QWE321","ecd1378bc9dc130008f00d58db5d26f60db55934a49b949af7e6f6a8da2a2beb"),
("Kao","Kahn","KHNK654321ABC987","25a63e52a98f6fdaa6187da559a0a3a55d845d0322ca183ad6ad5e006c4ed646"),
("Jin","Li","JNL123789QAZ456P","a031eda273ac18861d11324af880859527405b4d475a9ac589bc4c8bfd598893"),
("Ty","Lee","TYLE567890QWE432","6f021f0268d5dd0d4e286ad5202f57335f2a8cc568704c4f7865c97879443ef8");

INSERT INTO tipi_checkpoint (nome)
VALUES
("checkpoint"),
("stazione");

INSERT INTO checkpoint (nome, tipo)
VALUES
-- checkpoints
("Earthstone Junction", 1),
("Boulder Spur", 1),
("Clay Ridge Crossing", 1),
("Ironroot Signal", 1),
("Quartz Switch", 1),
("Muddy Hollow Point", 1),
("Granite Loop", 1),
("Terracotta Marker", 1),
("Mossbank Relay", 1),
("Sandbar Station", 1),
-- stations
("Jade Empress Terminal", 2),
("Royal Banyan Station", 2),
("Golden Wall Pavilion", 2),
("Palace Gate Central", 2),
("Chamber of the Five Elders", 2),
("Silk Market Square", 2),
("University Plaza", 2),
("Tales Crossing", 2);

INSERT INTO stazione (id, lunghezza, durata_fermataS)
VALUES
(11, 220, 120),
(12, 180, 120),
(13, 150, 120),
(14, 200, 180),
(15, 170, 60),
(16, 160, 60),
(17, 250, 60),
(18, 190, 60);

INSERT INTO orari_inizio (linea, orario, capolinea)
VALUES
(1, "09:00:00", 15),
(1, "10:00:00", 15),
(1, "11:00:00", 15),
(1, "12:00:00", 15),
(1, "09:30:00", 11),
(1, "10:30:00", 11),
(1, "11:30:00", 11),
(1, "12:30:00", 11),
(2, "18:00:00", 18),
(2, "19:00:00", 18),
(2, "20:00:00", 18),
(2, "21:00:00", 18),
(2, "18:00:00", 15),
(2, "19:00:00", 15),
(2, "20:00:00", 15),
(2, "21:00:00", 15);

INSERT INTO checkpoint_su_linea (linea, checkpoint, numero, delayS)
VALUES
(1, 11, 1, 300),
(1, 1, 2, 360),
(1, 2, 3, 240),
(1, 12, 4, 420),
(1, 4, 5, 600),
(1, 5, 6, 300),
(1, 13, 7, 360),
(1, 6, 8, 240),
(1, 14, 9, 420),
(1, 15, 10, 600),

(2, 15, 1, 300),
(2, 7, 2, 360),
(2, 8, 3, 300),
(2, 16, 4, 360),
(2, 9, 5, 300),
(2, 10, 6, 360),
(2, 3, 7, 300),
(2, 6, 8, 360),
(2, 4, 9, 300),
(2, 17, 10, 360),
(2, 12, 11, 300),
(2, 2, 12, 360),
(2, 1, 13, 300),
(2, 18, 14, 360);