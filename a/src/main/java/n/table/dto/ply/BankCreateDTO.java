package n.table.dto.ply;

import n.core.dto.PersistentDTO;
import n.entity.ply.Bank.AllowBindingStatus;
import n.entity.ply.Bank.TransferStatus;

/**
 * 用户DTO
 * 
 * @author onsoul@qq.com 2016年8月15日 下午3:59:07
 */
public class BankCreateDTO extends PersistentDTO<Integer> {
	private static final long serialVersionUID = -1922270907050463368L;

	private Integer id;

	/**
	 * 银行名称
	 */
	private String name;

	/**
	 * 银行简称
	 */
	private String shortName;

	/**
	 * 银行Logo图片路径
	 */
	private String logoFilePath;

	/**
	 * 网银地址
	 */
	private String url;

	/**
	 * 充值演示地址
	 */
	private String rechargeDemoUrl;

	/**
	 * 线下充值状态
	 */
	private TransferStatus transferStatus;

	/**
	 * 是否允许绑定
	 */
	private AllowBindingStatus allowBindingStatus;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getId() {
		return id;
	}

}
