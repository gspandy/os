package com.hitler.core.repository.support;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.hitler.core.repository.AggregateExpression;



/**
 * 基础 repository
 *
 * @author  jtwise
 * @date 2016年7月19日 下午2:18:38
 * @verion 1.0
 */
@NoRepositoryBean
public interface GenericRepository<E extends Persistable<PK>, PK extends Serializable>
		extends JpaRepository<E, PK>, JpaSpecificationExecutor<E> {

	EntityManager getEntityManager();

	E findOne(PK id, LockModeType lockMode);

	E findOne(Specification<E> spec, LockModeType lockMode);

	List<E> findTop(int top, Specification<E> spec, Sort sort);

	List<E> findTop(int top, Specification<E> spec, Sort sort, LockModeType lockMode);

	<S extends E> S update(S entity);

	@SuppressWarnings("unchecked")
	<S> S sum(Class<S> resultClass, Specification<E> spec, SingularAttribute<E, ? extends Number>... properties);

	@SuppressWarnings("unchecked")
	<S> S sum(Class<S> resultClass, Specification<E> spec, LockModeType lockMode,
			SingularAttribute<E, ? extends Number>... properties);

	<S> S sum(Class<S> resultClass, Specification<E> spec, List<SingularAttribute<E, ? extends Number>> properties);

	<S> S sum(Class<S> resultClass, Specification<E> spec, LockModeType lockMode,
			List<SingularAttribute<E, ? extends Number>> properties);

	<S> S aggregate(Class<S> resultClass, Specification<E> spec, AggregateExpression<E> expression);

	<S> S aggregate(Class<S> resultClass, Specification<E> spec, AggregateExpression<E> expression,
			LockModeType lockMode);
}
