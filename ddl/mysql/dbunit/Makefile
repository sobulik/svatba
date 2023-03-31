
M2REP=$(HOME)/.m2/repository
EXPORTFILE = dbunit-export.xml

JAVAC = javac
JAVA = java

# SLF4J API
JARS := $(M2REP)/org/slf4j/slf4j-api/1.6.1/slf4j-api-1.6.1.jar
# SLF4J simple backend
JARS := $(JARS):$(M2REP)/org/slf4j/slf4j-simple/1.6.1/slf4j-simple-1.6.1.jar
# logback backend
#JARS := $(JARS):$(M2REP)/ch/qos/logback/logback-classic/0.9.29/logback-classic-0.9.29.jar
#JARS := $(JARS):$(M2REP)/ch/qos/logback/logback-core/0.9.29/logback-core-0.9.29.jar
# dbunit
JARS := $(JARS):$(M2REP)/org/dbunit/dbunit/2.4.8/dbunit-2.4.8.jar
# MySQL driver
JARS := $(JARS):$(M2REP)/mysql/mysql-connector-java/5.1.13/mysql-connector-java-5.1.13.jar

.PHONY: all
all: DbUnitExport.class

.PHONY: export
export: $(EXPORTFILE)

.PHONY: clean
clean:
	rm -f DbUnitExport.class $(EXPORTFILE)

%.class: %.java
	$(JAVAC) -classpath $(JARS) $<

$(EXPORTFILE): DbUnitExport.class
	$(JAVA) -classpath $(JARS):. $(basename $<) \
        com.mysql.jdbc.Driver \
        jdbc:mysql://localhost:3306/svatba_lubosovo_net \
        svatba_lubosovo_ \
        agf*fGIe \
        svatba_lubosovo_net \
        "" \
        $@
