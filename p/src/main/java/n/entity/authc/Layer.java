package n.entity.authc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.PersistableEntity;

/**
 * 分组（分类）
 * @author onsoul@qq.com
 * @date 下午9:35:38 <br />
 */

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_LAYER")
public class Layer extends PersistableEntity<Integer> {

	private static final long serialVersionUID = 1L; 
	
	@Checked
	@Column(name = "LAYER_NAME", length = 20, nullable = false)
	private String layerName;
	@Checked
	@Column(name = "CODE", length = 20, unique=true, nullable = false)
	private String code;
	
	@Checked
	@Column(name = "POINTS", nullable = true)
	private String points;  // 目标积分
	
	@Checked
	@Column(name = "IMG_PATH",length=56 , nullable = true)
	private String imgPath;  // logo path
	
	@Column(name = "COMMENTS", nullable = true)
	private String comments; // 层级说明
	
	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATE", nullable = true)
	private LayerState state = LayerState.normal; // 状态

	/**
	 * normal-启用，forbid-禁用
	 * @author jt_wangshuiping @date 2016年12月28日
	 *
	 */
	public enum LayerState{
		normal,
		forbid
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LayerState getState() {
		return state;
	}

	public void setState(LayerState state) {
		this.state = state;
	}

}
