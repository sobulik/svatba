#!/bin/sh

mysql -u svatba_lubosovo_ -pagf*fGIe -h localhost --default-character-set=utf8 < db_create.sql
mysql -u svatba_lubosovo_ -pagf*fGIe -h localhost --default-character-set=utf8 svatba_lubosovo_net < db_setup_000.sql
