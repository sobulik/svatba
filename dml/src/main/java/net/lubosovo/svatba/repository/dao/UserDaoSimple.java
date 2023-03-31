package net.lubosovo.svatba.repository.dao;

import net.lubosovo.svatba.repository.domain.User;
import net.lubosovo.svatba.repository.domain.User_;
import net.lubosovo.svatba.repository.genericdao.GenericDaoJpa;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@Repository("userDao")
public class UserDaoSimple extends GenericDaoJpa<User> implements UserDao {

    public UserDaoSimple() {
        super(User.class);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public final List<User> getAll() {
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(this.getType());
        Root<User> root = criteriaQuery.from(this.getType());
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(User_.name)));
        return this.getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true, noRollbackFor = EmptyResultDataAccessException.class)
    public final User get(final User user) {
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        ParameterExpression<String> name = criteriaBuilder.parameter(String.class);

        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(this.getType());
        Root<User> root = criteriaQuery.from(this.getType());
        criteriaQuery.select(root);

        Predicate predicate = criteriaBuilder.equal(root.get(User_.name), name);
        criteriaQuery.where(predicate);

        TypedQuery<User> query = this.getEntityManager().createQuery(criteriaQuery);
        query.setParameter(name, user.getName());
        return query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true, noRollbackFor = EmptyResultDataAccessException.class)
    public final User getByLogin(final String loginParameter) {
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        ParameterExpression<String> login = criteriaBuilder.parameter(String.class);

        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(this.getType());
        Root<User> root = criteriaQuery.from(this.getType());
        criteriaQuery.select(root);

        Predicate predicate = criteriaBuilder.equal(root.get(User_.login), login);
        criteriaQuery.where(predicate);

        TypedQuery<User> query = this.getEntityManager().createQuery(criteriaQuery);
        query.setParameter(login, loginParameter);
        return query.getSingleResult();
    }
}
