package net.lubosovo.svatba.repository.dao;

import net.lubosovo.svatba.repository.domain.User;
import net.lubosovo.svatba.repository.dao.UserDao;

import javax.sql.DataSource;
import java.sql.SQLException;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.DatabaseUnitException;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/dml-context.xml", "classpath:/spring/test-dml-context.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class UserDaoSimpleTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserDao userDao;


    @Before
    public void setUp() throws SQLException, DatabaseUnitException {
        IDatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection(this.dataSource.getConnection());
            // awful db dependent way to get rid of loads of warning messages, blame dbunit
            databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new org.dbunit.ext.h2.H2DataTypeFactory());

            FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
            flatXmlDataSetBuilder.setColumnSensing(true);
            IDataSet dataSet = flatXmlDataSetBuilder.build(this.getClass().getClassLoader().getResource("svatba-dbunit-sample.xml"));
            DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);
        } finally {
            if ( databaseConnection != null ) {
                databaseConnection.close();
            }
        }
    }

    @Test(expected=EmptyResultDataAccessException.class)
    public void testNonExGet() {
        this.userDao.get(new User("Mark XXX"));
    }

    @Test
    public void testExGet() {
        User user = new User("Jan XXXXXX");
        Assert.assertEquals(user, this.userDao.get(user));
    }
}
