DROP USER 'svatba_lubosovo_'@'localhost';

CREATE USER 'svatba_lubosovo_'@'localhost'
    IDENTIFIED BY 'agf*fGIe';

GRANT ALL PRIVILEGES
    ON svatba_lubosovo_net.*
    TO 'svatba_lubosovo_'@'localhost';

GRANT FILE ON *.* TO 'svatba_lubosovo_'@'localhost';
