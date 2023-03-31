package net.lubosovo.svatba.repository.genericdao;

import net.lubosovo.svatba.repository.domain.DomainObject;

import java.util.List;

public interface GenericDao<T extends DomainObject> {

    T get(Long id);
    T get(T object);
    List<T> getAll();
    void persist(T object);
    T merge(T object);
    void remove(T object);
}
