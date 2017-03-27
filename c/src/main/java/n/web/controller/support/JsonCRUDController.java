package n.web.controller.support;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import n.core.dto.support.IGenericDTO;
import n.core.dto.support.IGenericTable;
import n.core.repository.SearchFilter;
import n.core.service.support.IGenericService;
import n.core.web.controller.GenericController;

public abstract class JsonCRUDController<E extends Persistable<PK>, PK extends Serializable, DTO extends IGenericDTO<PK>, CDTO extends IGenericDTO<PK>, UDTO extends IGenericDTO<PK>, TAB extends IGenericTable<DTO, E>>
		extends GenericController {

	private Class<DTO> dtoClass;
	private Class<CDTO> createDtoClass;
	private Class<UDTO> updateDtoClass;
	private Class<TAB> tableClass;

	private String path;

	protected abstract IGenericService<E, PK> getService();

	@SuppressWarnings("unchecked")
	public JsonCRUDController(String path) {
		this.path = path;
		this.dtoClass = getClass(2);
		this.createDtoClass = getClass(3);
		this.updateDtoClass = getClass(4);
		this.tableClass = getClass(5);
	}

	/**
	 * 列表
	 */
	@RequestMapping()
	@ResponseBody
	public Object list(Model model, ServletRequest request) throws Exception {
		return preList(model, request);
	}

	protected IGenericTable<DTO, E> preList(Model model, ServletRequest request) throws Exception {
		return null;
	}

	protected void postList(Model model, Pageable pageable, ServletRequest request, List<SearchFilter> filters)
			throws Exception {
	}

	/**
	 * 新增 - 表单页面
	 */
	@RequestMapping(value = { "/create" })
	@ResponseBody
	public boolean create(Model model) throws Exception {
		CDTO createDTO = createDtoClass.newInstance();
		return preCreate(model, createDTO);
	}

	protected boolean preCreate(Model model, CDTO createDTO) throws Exception {
		return false;
	}

	protected void postCreate(Model model, CDTO createDTO, BindingResult br) throws Exception {
	}

	/**
	 * 修改 - 表单页面
	 */
	@RequestMapping(value = { "/update/{id}" })
	@ResponseBody
	public boolean update(Model model, @PathVariable PK id) throws Exception {
		UDTO updateDTO = getService().find(updateDtoClass, id);
		return preUpdate(model, updateDTO); // TODO VALI

	}

	protected boolean preUpdate(Model model, UDTO updateDTO) throws Exception {
		return false;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = { "/delete/{id}" })
	@ResponseBody
	public boolean delete(@PathVariable PK id) {
		try {
			getService().delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	private Class getClass(int index) {
		Type[] params = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
		Type type = params[index];
		if (type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getRawType();
		}
		return (Class) type;
	}

}
