package n.table.dto.authc;

import java.io.Serializable;
import java.util.List;

public class MenuTreeDTO implements Serializable {
	
	private static final long serialVersionUID = 130710525722243695L;
	
	private Integer id;
	private String name;
	private String path;
	private Integer deep;
	private Integer isDisplay;
	private String icon;
	private List<MenuTreeDTO> subList = null;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDeep() {
		return deep;
	}
	public void setDeep(Integer deep) {
		this.deep = deep;
	}
	public List<MenuTreeDTO> getSubList() {
		return subList;
	}
	public void setSubList(List<MenuTreeDTO> subList) {
		this.subList = subList;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
