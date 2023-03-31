package net.lubosovo.svatba.repository.dao;

import net.lubosovo.svatba.repository.domain.Gift;
import net.lubosovo.svatba.repository.domain.Gift_;
import net.lubosovo.svatba.repository.genericdao.GenericDaoJpa;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@Repository("giftDao")
public class GiftDaoSimple extends GenericDaoJpa<Gift> implements GiftDao {

    public GiftDaoSimple() {
        super(Gift.class);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true, noRollbackFor = EmptyResultDataAccessException.class)
    public final Gift get(final Gift gift) {
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        ParameterExpression<String> title = criteriaBuilder.parameter(String.class);

        CriteriaQuery<Gift> criteriaQuery = criteriaBuilder.createQuery(this.getType());
        Root<Gift> root = criteriaQuery.from(this.getType());
        criteriaQuery.select(root);

        Predicate predicate = criteriaBuilder.equal(root.get(Gift_.title), title);
        criteriaQuery.where(predicate);

        TypedQuery<Gift> query = this.getEntityManager().createQuery(criteriaQuery);
        query.setParameter(title, gift.getTitle());
        return query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true, noRollbackFor = EmptyResultDataAccessException.class)
    public final List<Gift> getAvailableGifts() {
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Gift> criteriaQuery = criteriaBuilder.createQuery(this.getType());
        Root<Gift> root = criteriaQuery.from(this.getType());
        criteriaQuery.select(root);

        Predicate predicate = root.get(Gift_.user).isNull();
        criteriaQuery.where(predicate);

        TypedQuery<Gift> query = this.getEntityManager().createQuery(criteriaQuery);
        return query.getResultList();
    }
}
