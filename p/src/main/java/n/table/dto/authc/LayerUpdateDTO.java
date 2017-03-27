package n.table.dto.authc;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;

/**
 * 分组修改DTO
 * @author onsoul@qq.com 2016年8月15日 下午4:44:23
 */
public class LayerUpdateDTO extends TransientDTO<Integer> {
	
	private static final long serialVersionUID = 8386343228166934690L;
	@NotNull(message="id不能为空！")
	private Integer id;
	@NotBlank(message="分组名称不能为空！分组名称请按问题类型命名！")
	private String layerName;
	
	private String points;  // 目标积分
	
	private String imgPath;  // logo path
	
	private String comments; // 层级说明

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
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

}
