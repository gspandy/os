package n.table.dto.authc;

import n.core.dto.PersistentDTO;
import n.entity.authc.Layer.LayerState;

/**
 * 用户DTO
 * @author onsoul@qq.com
 * 2016年8月15日 下午3:59:07
 */
public class LayerDTO  extends PersistentDTO<Integer> {
 
	private static final long serialVersionUID = -7915066523824664077L;

	private Integer id;
	
	private String layerName;
	
	private String code;
	
	private String points;  // 目标积分
	
	private String imgPath;  // logo path
	
	private String comments; // 层级说明
	
	private LayerState state; // 状态

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
}
