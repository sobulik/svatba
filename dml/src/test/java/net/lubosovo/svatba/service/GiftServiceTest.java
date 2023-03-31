package net.lubosovo.svatba.service;

import net.lubosovo.svatba.repository.domain.User;
import net.lubosovo.svatba.repository.domain.Gift;
import net.lubosovo.svatba.repository.dao.UserDao;
import net.lubosovo.svatba.repository.dao.GiftDao;

import java.util.Arrays;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;

import org.springframework.dao.EmptyResultDataAccessException;

@RunWith(JMock.class)
public class GiftServiceTest {

    private Mockery context;
    private GiftService giftService;
    UserDao userDao;
    GiftDao giftDao;

    @Before
    public void initializeTest() {
        this.context = new JUnit4Mockery();
        this.userDao = this.context.mock(UserDao.class);
        this.giftDao = this.context.mock(GiftDao.class);
        this.giftService = new GiftService();
        this.giftService.setUserDao(this.userDao);
        this.giftService.setGiftDao(this.giftDao);
    }

    @Test
    public void testGetAllUsers() {
        this.context.checking(new Expectations() {{ oneOf(GiftServiceTest.this.userDao).getAll();
                will(returnValue(Arrays.asList(new User("Hejda"), new User("Puk")))); }});
        Assert.assertEquals(2, this.giftService.getAllUsers().size());
    }

    @Test
    public void testGetAllGifts() {
        this.context.checking(new Expectations() {{ oneOf(GiftServiceTest.this.giftDao).getAll();
                will(returnValue(Arrays.asList(new Gift("Kosa"), new Gift("Srp")))); }});
        Assert.assertEquals(2, this.giftService.getAllGifts().size());
    }

    @Test
    public void testExGetUserById() throws ServiceException {
        final User user = new User("Puk");
        this.context.checking(new Expectations() {{ oneOf(GiftServiceTest.this.userDao).get(with(any(Long.class)));
                will(returnValue(user)); }});
        Assert.assertEquals(user, this.giftService.getUserById(Long.valueOf(26L), false));
    }

    @Test(expected=ServiceException.class)
    public void testNonExGetUserById() throws ServiceException {
        this.context.checking(new Expectations() {{ oneOf(GiftServiceTest.this.userDao).get(with(any(Long.class)));
                will(throwException(new EmptyResultDataAccessException(1))); }});
        this.giftService.getUserById(Long.valueOf(27L), false);
    }

    @Test
    public void testExGetGiftById() throws ServiceException {
        final Gift gift = new Gift("Srp");
        this.context.checking(new Expectations() {{ oneOf(GiftServiceTest.this.giftDao).get(with(any(Long.class)));
                will(returnValue(gift)); }});
        Assert.assertEquals(gift, this.giftService.getGiftById(Long.valueOf(22L)));
    }

    @Test(expected=ServiceException.class)
    public void testNonExGetGiftById() throws ServiceException {
        this.context.checking(new Expectations() {{ oneOf(GiftServiceTest.this.giftDao).get(with(any(Long.class)));
                will(throwException(new EmptyResultDataAccessException(1))); }});
        this.giftService.getGiftById(Long.valueOf(22L));
    }

    @Test(expected=ServiceException.class)
    public void testExAddUser() throws ServiceException {
        final User user = new User("Hejda");
        this.context.checking(new Expectations() {{ oneOf(GiftServiceTest.this.userDao).get(user);
                will(returnValue(user)); }});
        this.context.checking(new Expectations() {{ never(GiftServiceTest.this.userDao).persist(with(any(User.class))); }});
        this.giftService.addUser(user);
    }

    @Test
    public void testNonExAddUser() throws ServiceException {
        final User user = new User("Hejda");
        this.context.checking(new Expectations() {{ oneOf(GiftServiceTest.this.userDao).get(user);
                will(throwException(new EmptyResultDataAccessException(1))); }});
        this.context.checking(new Expectations() {{ oneOf(GiftServiceTest.this.userDao).persist(user); }});
        this.giftService.addUser(user);
    }
}
