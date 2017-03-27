package n.table.dto.authc;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;
import n.entity.authc.Company;
import n.entity.authc.Layer.LayerState;

/**
 * 分组创建DTO
 * @author onsoul@qq.com 2016年8月15日 下午4:44:23
 */
public class LayerCreateDTO extends TransientDTO<Integer> {
	
	private static final long serialVersionUID = 8386343228166934690L;
	@NotBlank(message="分组名称不能为空！分组名称请按问题类型命名！")
	private String layerName;
	@Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message="code格式不正确！长度必须在6-16位之间")
	private String code;
	
	private String points;  // 目标积分
	
	private String imgPath;  // logo path
	
	private String comments; // 层级说明
	
	private LayerState state = LayerState.normal; // 状态
	
	private Company company;

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LayerState getState() {
		return state;
	}

	public void setState(LayerState state) {
		this.state = state;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
