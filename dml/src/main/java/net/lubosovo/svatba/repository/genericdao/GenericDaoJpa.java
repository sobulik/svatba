package net.lubosovo.svatba.repository.genericdao;

import net.lubosovo.svatba.repository.domain.DomainObject;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.dao.EmptyResultDataAccessException;

public abstract class GenericDaoJpa<T extends DomainObject> implements GenericDao<T> {

    private Class<T> type;
    private EntityManager entityManager;

    public GenericDaoJpa(final Class<T> type) {
        this.type = type;
    }

    public final Class<T> getType() {
        return this.type;
    }

    public final EntityManager getEntityManager() {
        return this.entityManager;
    }

    @PersistenceContext
    public final void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true, noRollbackFor = EmptyResultDataAccessException.class)
    public final T get(final Long id) {
        T entity = this.entityManager.find(this.type, id);
        if (entity == null) {
            throw new EmptyResultDataAccessException(1); //fiks shouldn't javax.persistence.NoResultException be thrown?
        }
        return entity;
    }

    @Override
    public abstract T get(T object);

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.<T>createQuery(this.type);
        criteriaQuery.from(this.type);
        return this.entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public final void persist(final T object) {
        this.entityManager.persist(object);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public final T merge(final T object) {
        return this.entityManager.merge(object);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public final void remove(final T object) {
        this.entityManager.remove(object);
    }
}
