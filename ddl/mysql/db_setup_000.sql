INSERT INTO users
(name, phone, email, login, password)
VALUES ('Jan XXXXXX', '+420XXXXXXXXX', 'X.XXX@centrum.cz', 'hejda', 'chinaski');

INSERT INTO users
(name, phone, email, login, password)
VALUES ('Zuzana XXXXXX', '+420XXXXXXXXX', 'XXXXXXX@seznam.cz', 'zuzka', 'rila');

INSERT INTO users
(name, phone, email, login, password)
VALUES ('Ladislav XXXXXXX', '+420XXXXXXXXX', 'XXXXXXX@email.cz', NULL, NULL);

-- blobs must be owned by user mysql
-- and there is some other restriction on directory as well (/tmp umask is 777 and works)
INSERT INTO gifts
(id_user, title, descrip, link, price, thumb)
VALUES (NULL, 'Hodina zpěvu', 'Svěrák nespí na ponku a Uhlíř topí dřevem', 'http://hodinazpevu.cz', 345, LOAD_FILE('/tmp/hodina_zpevu.jpg'));

INSERT INTO gifts
(id_user, title, descrip, link, price, thumb)
VALUES (NULL, 'Hodina zpěvu 2', 'Barbora z tábora', 'http://hodinazpevu.cz', 345, LOAD_FILE('/tmp/hodina_zpevu.jpg'));

INSERT INTO gifts
(id_user, title, descrip, link, price, thumb)
VALUES (NULL, 'Hrnec na těstoviny', 'S cedníčkem pro snadné slévání', 'http://hrnec.tescoma.cz', 1295, LOAD_FILE('/tmp/hrnec.jpg'));

INSERT INTO gifts
(id_user, title, descrip, link, price, thumb)
VALUES (NULL, 'Cyklopumpa', 'Ale pořádná', 'http://pump.evans.co.uk', 833, LOAD_FILE('/tmp/pumpa.jpg'));

-- blob dump
-- SELECT thumb
--     INTO DUMPFILE '/tmp/dump.jpg'
--     FROM gifts;
