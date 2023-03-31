import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileOutputStream;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class DbUnitExport
{
    public static void main(String[] args) throws Exception
    {
        // database connection
        Class driverClass = Class.forName(args[0]);
        Connection jdbcConnection = DriverManager.getConnection(args[1], args[2], args[3]);
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection, args[4], true);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable(args[5] + "users");
        partialDataSet.addTable(args[5] + "gifts");
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream(args[6]));

        // full database export
        //IDataSet fullDataSet = connection.createDataSet();
        //FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));

        // dependent tables database export: export table X and all tables that
        // have a PK which is a FK on X, in the right order for insertion
        //String[] depTableNames = TablesDependencyHelper.getAllDependentTables( connection, "X" );
        //IDataSet depDataset = connection.createDataSet( depTableNames );
        //FlatXmlDataSet.write(depDataSet, new FileOutputStream("dependents.xml"));
    }
}
