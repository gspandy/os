package com.wise.core.service.support;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.wise.core.dto.support.IGenericDTO;
import com.wise.core.dto.support.IGenericTable;
import com.wise.core.repository.AggregateExpression;


public interface IGenericService<E extends Persistable<PK>, PK extends Serializable> {
	
	<S extends E> S save(S entity) throws Exception;
	int save(Iterable<? extends E> entities) throws Exception;
	
	<S extends E> S update(S entity) throws Exception;
	void delete(PK id) throws Exception;
	void delete(E entity) throws Exception;
	void delete(Iterable<? extends E> entities) throws Exception;
	void deleteAll() throws Exception;
	boolean exists(PK id);
	long count();
	long count(Specification<E> spec);
	E find(PK id);
	E find(Specification<E> spec);
	List<E> findAll();
	List<E> findAll(Sort sort);
	List<E> findAll(Iterable<PK> ids);
	List<E> findAll(Specification<E> spec);
	Page<E> findAll(Specification<E> spec, Pageable pageable);
	Page<E> findAll(Specification<E> spec,Sort sort, Pageable pageable);
	List<E> findAll(Specification<E> spec, Sort sort);
	
	@SuppressWarnings("unchecked")
	<S> S sum(Class<S> resultClass, Specification<E> spec, SingularAttribute<E, ? extends Number>... properties);
	<S> S sum(Class<S> resultClass, Specification<E> spec, List<SingularAttribute<E, ? extends Number>> properties); 
	<S> S aggregate(Class<S> resultClass, Specification<E> spec, AggregateExpression<E> expression);
	
	<DTO extends IGenericDTO<PK>> void save(IGenericDTO<PK> dto) throws Exception;
	<DTO extends IGenericDTO<PK>> void update(IGenericDTO<PK> dto) throws Exception;
	<DTO extends IGenericDTO<PK>> DTO find(Class<DTO> dtoClass, PK id) throws Exception;
	<DTO extends IGenericDTO<PK>> DTO find(Class<DTO> dtoClass, Specification<E> spec) throws Exception;
	<DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass) throws Exception;
	<DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass, Sort sort) throws Exception;
	<DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass, Iterable<PK> ids) throws Exception;
	<DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass, Specification<E> spec) throws Exception;
	<DTO extends IGenericDTO<PK>> Page<DTO> findAll(Class<DTO> dtoClass, Specification<E> spec, Pageable pageable) throws Exception;
	<DTO extends IGenericDTO<PK>> List<DTO> findAll(Class<DTO> dtoClass, Specification<E> spec, Sort sort) throws Exception;
	
	<TAB extends IGenericTable<DTO, E>, DTO extends IGenericDTO<PK>> TAB findTable(Class<TAB> tableClass, Class<DTO> dtoClass, Specification<E> spec, Pageable pageable) throws Exception;
	
}

