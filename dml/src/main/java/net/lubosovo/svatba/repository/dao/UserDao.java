package net.lubosovo.svatba.repository.dao;

import net.lubosovo.svatba.repository.domain.User;
import net.lubosovo.svatba.repository.genericdao.GenericDao;

public interface UserDao extends GenericDao<User> {

    User getByLogin(String login);
}
