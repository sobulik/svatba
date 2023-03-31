package net.lubosovo.svatba.repository.dao;

import net.lubosovo.svatba.repository.domain.Gift;
import net.lubosovo.svatba.repository.dao.GiftDao;
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
public class GiftDaoSimpleTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    GiftDao giftDao;
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
        this.giftDao.get(new Gift("Pes"));
    }

    @Test
    public void testExGet() {
        Gift gift = new Gift("Cyklopumpa");
        Assert.assertEquals(gift, this.giftDao.get(gift));
    }

    @Test
    public void testRel1() {
        Gift gift = this.giftDao.get(new Gift("Hodina zpěvu 2"));
        User user = this.userDao.get(new User("Zuzana XXXXXX"));
        Assert.assertEquals(user, gift.getUser());
    }

    @Test
    public void testRel2() {
        Gift gift = this.giftDao.get(new Gift("Cyklopumpa"));
        Assert.assertNull(gift.getUser());
    }

    @Test
    public void testRel3() {
        User user = this.userDao.get(new User("Ladislav XXXXXXX"));
        Assert.assertEquals(2, user.getGifts().size());
    }

    @Test
    public void testAssoc1() {
        Gift gift = this.giftDao.get(new Gift("Cyklopumpa"));
        User user = this.userDao.get(new User("Jan XXXXXX"));
        Assert.assertNull(gift.getUser());
        Assert.assertFalse(user.getGifts().contains(gift));
        gift.assocUser(user);
        Assert.assertEquals(user, gift.getUser());
        Assert.assertTrue(user.getGifts().contains(gift));
    }

    @Test
    public void testAssoc2() {
        Gift gift = this.giftDao.get(new Gift("Hodina zpěvu 2"));
        User user = this.userDao.get(new User("Zuzana XXXXXX"));
        Assert.assertEquals(user, gift.getUser());
        Assert.assertTrue(user.getGifts().contains(gift));
        gift.assocUser(null);
        Assert.assertNull(gift.getUser());
        Assert.assertFalse(user.getGifts().contains(gift));
    }
}
