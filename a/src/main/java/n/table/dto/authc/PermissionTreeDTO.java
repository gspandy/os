package n.table.dto.authc;

import java.io.Serializable;
import java.util.List;

public class PermissionTreeDTO implements Serializable {
	
	private static final long serialVersionUID = 130710525722243695L;
	
	private Integer id;
	private String text;
	private Integer deep;
	private List<PermissionTreeDTO> children = null;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getDeep() {
		return deep;
	}
	public void setDeep(Integer deep) {
		this.deep = deep;
	}
	public List<PermissionTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<PermissionTreeDTO> children) {
		this.children = children;
	}
	
}
