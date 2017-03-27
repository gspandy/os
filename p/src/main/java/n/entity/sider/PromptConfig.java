package n.entity.sider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.support.PersistableEntity;

/**
 * 聊天提示信息配置
 * @author jt_wangshuiping @date 2016年12月2日
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_PROMPTCONFIG")
public class PromptConfig extends PersistableEntity<Integer> {

	private static final long serialVersionUID = -2461189981104656631L;
	/**
	 * 提示内容
	 */
	@Column(name = "CONTENT", columnDefinition = "varchar(254)", nullable = false)
	private String content;
	/**
	 * 提示类型
	 */
	@Column(name = "TYPE", columnDefinition = "varchar(20)", nullable = false)
	@Enumerated(EnumType.STRING)
	private PromptType type;
	/**
	 * 超时时间(分钟)
	 */
	@Column(name = "TIMEOUT", nullable = true)
	private String time;
	/**
	 * 创建者id
	 */
	@Column(name = "USER_ID", columnDefinition = "bigint")
	private Long userId;
	/**
	 * 创建人账户
	 */
	@Column(name = "USER_ACCOUNT", columnDefinition = "varchar(20)")
	private String userAccount;
	
	/**
	 * 提示类型
	 * @author jt_wangshuiping @date 2016年12月2日
	 *
	 */
	public enum PromptType{
		welcome, 			//欢迎语
		await,				//等待提示语
		busy,				//繁忙提示语
		finish,				//结束语
		timeout,			//访客超时
		quick				//快速输入
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public PromptType getType() {
		return type;
	}


	public void setType(PromptType type) {
		this.type = type;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUserAccount() {
		return userAccount;
	}


	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

}
