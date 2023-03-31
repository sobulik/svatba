package net.lubosovo.svatba.service;

import net.lubosovo.svatba.repository.domain.User;
import net.lubosovo.svatba.repository.dao.UserDao;
import net.lubosovo.svatba.repository.domain.Gift;
import net.lubosovo.svatba.repository.dao.GiftDao;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Set;
import java.text.SimpleDateFormat;

@Service("giftService")
public class GiftService {

    private UserDao userDao;
    private GiftDao giftDao;

    @Autowired
    protected final void setUserDao(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    protected final void setGiftDao(final GiftDao giftDao) {
        this.giftDao = giftDao;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return this.userDao.getAll();
    }

    @Transactional(readOnly = true)
    public List<Gift> getAllGifts() {
        return this.giftDao.getAll();
    }

    @Transactional(readOnly = true)
    public List<Gift> getAvailableGifts() {
        return this.giftDao.getAvailableGifts();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id, final boolean loadGifts) throws ServiceException {
        try {
            User user = this.userDao.get(id);
            if (loadGifts) {
                user.getGifts().iterator().hasNext();
            }
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new ServiceException("User with id = " + id.toString() + " doesn't exist");
        }
    }

    @Transactional(readOnly = true)
    public Gift getGiftById(Long id) throws ServiceException {
        try {
            return this.giftDao.get(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ServiceException("Gift with id = " + id.toString() + " doesn't exist");
        }
    }

    @Transactional
    // fiks should be @Transactional(isolation=Isolation.SERIALIZABLE)
    // but is not supported by JPA - https://jira.springsource.org/browse/SPR-5012
    public void addUser(User user) throws ServiceException {
        try {
            this.userDao.get(user);
            throw new ServiceException("User " + user.getName() + " already exists");
        } catch (EmptyResultDataAccessException e) {
            this.userDao.persist(user);
        }
    }

    @Transactional
    // fiks should be @Transactional(isolation=Isolation.SERIALIZABLE)
    // but is not supported by JPA - https://jira.springsource.org/browse/SPR-5012
    public void deleteUserById(Long id) throws ServiceException {
        try {
            User user = this.userDao.get(id);
            if (user.getGifts().size() == 0) {
                this.userDao.remove(user);
            } else {
                throw new ServiceException("User with id = " + id.toString() + " cannot be deleted");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new ServiceException("User with id = " + id.toString() + " doesn't exist");
        }
    }

    @Transactional
    public void setGiftById(Long id, Long id_user) throws ServiceException {
        Gift gift;
        try {
            gift = this.giftDao.get(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ServiceException("Gift with id = " + id.toString() + " doesn't exist");
        }
        User user = null;
        if (id_user > 0) {
            try {
                user = this.userDao.get(id_user);
            } catch (EmptyResultDataAccessException e) {
                throw new ServiceException("User with id = " + id_user.toString() + " doesn't exist");
            }
        }
        gift.assocUser(user);
    }
}
