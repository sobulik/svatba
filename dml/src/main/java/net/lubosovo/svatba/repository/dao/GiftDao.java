package net.lubosovo.svatba.repository.dao;

import net.lubosovo.svatba.repository.domain.Gift;
import net.lubosovo.svatba.repository.genericdao.GenericDao;

import java.util.List;

public interface GiftDao extends GenericDao<Gift> {

    List<Gift> getAvailableGifts();
}
