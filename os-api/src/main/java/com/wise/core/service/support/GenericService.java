package com.wise.core.service.support;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wise.core.dto.support.IGenericDTO;
import com.wise.core.dto.support.IGenericTable;
import com.wise.core.exception.EntityNotExistsException;
import com.wise.core.jutils.bean.BeanMapper;
import com.wise.core.repository.AggregateExpression;
import com.wise.core.repository.support.GenericRepository;

public abstract class GenericService<E extends Persistable<PK>, PK extends Serializable>
		implements IGenericService<E, PK> {

	protected Logger log = LoggerFactory.getLogger(getClass());

	@PersistenceContext
	protected EntityManager em;

	protected final Class<E> entityClass;

	public GenericService(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract GenericRepository<E, PK> getRepository();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public <S extends E> S save(S entity) throws Exception {
		return getRepository().save(entity);
	}

	@Override
	public int save(Iterable<? extends E> entities) throws Exception {
		return getRepository().save(entities).size();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public <S extends E> S update(S entity) throws Exception {
		return getRepository().update(entity);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public E find(PK id) {
		return getRepository().findOne(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public E find(Specification<E> spec) {
		return getRepository().findOne(spec);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public boolean exists(PK id) {
		return getRepository().exists(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<E> findAll() {
		return getRepository().findAll();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<E> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<E> findAll(Iterable<PK> ids) {
		return getRepository().findAll(ids);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<E> findAll(Specification<E> spec) {
		return getRepository().findAll(spec);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Page<E> findAll(Specification<E> spec, Pageable pageable) {
		return getRepository().findAll(spec, pageable);
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Page<E> findAll(Specification<E> spec,Sort sort, Pageable pageable) {
		return getRepository().findAll(spec, pageable);
	}
	

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<E> findAll(Specification<E> spec, Sort sort) {
		return getRepository().findAll(spec, sort);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public long count() {
		return getRepository().count();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public long count(Specification<E> spec) {
		return getRepository().count(spec);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(PK id) throws Exception {
		getRepository().delete(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(E entity) throws Exception {
		getRepository().delete(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Iterable<? extends E> entities) throws Exception {
		getRepository().delete(entities);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAll() throws Exception {
		getRepository().deleteAll();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <S> S sum(Class<S> resultClass, Specification<E> spec,
			List<SingularAttribute<E, ? extends Number>> properties) {
		return getRepository().sum(resultClass, spec, properties);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <S> S sum(Class<S> resultClass, Specification<E> spec,
			SingularAttribute<E, ? extends Number>... properties) {
		return getRepository().sum(resultClass, spec, properties);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <S> S aggregate(Class<S> resultClass, Specification<E> spec, AggregateExpression<E> expression) {
		return getRepository().aggregate(resultClass, spec, expression);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public <DTO extends IGenericDTO<PK>> void save(IGenericDTO<PK> dto) throws Exception {
		E entity = null;
		if (dto.getId() != null) { // 修改
			entity = find(dto.getId());
			if (entity == null)
				throw EntityNotExistsException.ERROR;
			BeanMapper.map(dto, entity);
		} else { // 新增
			entity = BeanMapper.map(dto, entityClass);
		}
		save(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public <DTO extends IGenericDTO<PK>> void update(IGenericDTO<PK> dto) throws Exception {
		E entity = null;
		if (dto.getId() != null) {
			entity = find(dto.getId());
			if (entity == null)
				throw EntityNotExistsException.ERROR;
			BeanMapper.map(dto, entity);
		} else {
			entity = BeanMapper.map(dto, entityClass);
		}
		update(entity);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <DTO extends IGenericDTO<PK>> DTO find(Class<DTO> dtoClass, PK id) throws Exception {
		return BeanMapper.map(find(id), dtoClass);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <DTO extends IGenericDTO<PK>> DTO find(Class<DTO> dtoClass, Specification<E> spec) throws Exception {
		return BeanMapper.map(find(spec), dtoClass);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass) throws Exception {
		return BeanMapper.map(findAll(), dtoClass);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass, Sort sort) throws Exception {
		return BeanMapper.map(findAll(sort), dtoClass);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass, Iterable<PK> ids) throws Exception {
		return BeanMapper.map(findAll(ids), dtoClass);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass, Specification<E> spec)
			throws Exception {
		return BeanMapper.map(findAll(spec), dtoClass);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <DTO extends IGenericDTO<PK>> Page<DTO> findAll(Class<DTO> dtoClass, Specification<E> spec,
			Pageable pageable) throws Exception {
		Page<E> sp = findAll(spec, pageable);
		Page<DTO> dp = new PageImpl<DTO>(BeanMapper.map(sp.getContent(), dtoClass), pageable, sp.getTotalElements());
		return dp;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass, Specification<E> spec, Sort sort)
			throws Exception {
		return BeanMapper.map(findAll(spec, sort), dtoClass);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <TAB extends IGenericTable<DTO, E>, DTO extends IGenericDTO<PK>> TAB findTable(Class<TAB> tableClass,
			Class<DTO> dtoClass, Specification<E> spec, Pageable pageable) throws Exception {
		TAB tinst = tableClass.newInstance();
		TAB t = null;
		Page<E> sp = findAll(spec, pageable);
		if (sp.getTotalElements() > 0) {
			t = aggregate(tableClass, spec, tinst);
		}
		if (t == null) {
			t = tinst;
		}
		t.setData(BeanMapper.map(sp.getContent(), dtoClass));
		t.setRecordsTotal(sp.getTotalElements());
		t.setRecordsFiltered(sp.getTotalElements());
		return t;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
