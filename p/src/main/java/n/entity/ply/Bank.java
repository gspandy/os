package n.entity.ply;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.CheckableEntity;

/**
 * 银行卡
 * @author onsoul
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_BANK", indexes = { @Index(name = "IDX_NAME", columnList = "NAME") })
public class Bank extends CheckableEntity<Integer> {

	private static final long serialVersionUID = 4133849097582140477L;

	/**
	 * 银行名称
	 */
	@Checked
	@Column(name = "NAME", columnDefinition = "varchar(10) COMMENT '银行名称'", nullable = false)
	private String name;

	/**
	 * 银行简称
	 */
	@Checked
	@Column(name = "SHORT_NAME", columnDefinition = "varchar(10) COMMENT '银行简称'")
	private String shortName;

	/**
	 * 银行Logo图片路径
	 */
	@Checked
	@Column(name = "LOGO_FILE_PATH", columnDefinition = "varchar(50) COMMENT '银行Logo图片路径'")
	private String logoFilePath;

	/**
	 * 网银地址
	 */
	@Checked
	@Column(name = "URL", columnDefinition = "varchar(200) COMMENT '网银地址'", nullable = false)
	private String url;

	/**
	 * 充值演示地址
	 */
	@Checked
	@Column(name = "RECHARGE_DEMO_URL", columnDefinition = "varchar(200) COMMENT '充值演示地址'", nullable = false)
	private String rechargeDemoUrl;

	/**
	 * 线下充值状态
	 */
	@Checked
	@Enumerated(EnumType.STRING)
	@Column(name = "TRANSFER_STATUS", columnDefinition = "varchar(20) COMMENT '线下充值状态'", nullable = false)
	private TransferStatus transferStatus;

	/**
	 * 是否允许绑定
	 */
	@Checked
	@Enumerated(EnumType.STRING)
	@Column(name = "ALLOW_BINDING_STATUS", columnDefinition = "varchar(20) COMMENT '是否允许绑定'", nullable = false)
	private AllowBindingStatus allowBindingStatus;

	public enum TransferStatus {
		off, on
	}

	public enum AllowBindingStatus {
		off, on
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoFilePath() {
		return logoFilePath;
	}

	public void setLogoFilePath(String logoFilePath) {
		this.logoFilePath = logoFilePath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRechargeDemoUrl() {
		return rechargeDemoUrl;
	}

	public void setRechargeDemoUrl(String rechargeDemoUrl) {
		this.rechargeDemoUrl = rechargeDemoUrl;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public TransferStatus getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(TransferStatus transferStatus) {
		this.transferStatus = transferStatus;
	}

	public AllowBindingStatus getAllowBindingStatus() {
		return allowBindingStatus;
	}

	public void setAllowBindingStatus(AllowBindingStatus allowBindingStatus) {
		this.allowBindingStatus = allowBindingStatus;
	}
	
}
