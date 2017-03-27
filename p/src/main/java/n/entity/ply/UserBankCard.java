package n.entity.ply;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.PersistableEntity;
import n.entity.authc.User;

/**
 * 用户提现银行卡
 * @author
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_USER_BANK_CARD", indexes = { @Index(name = "IDX_USER_ID", columnList = "USER_ID"),
		@Index(name = "IDX_BANK_ID", columnList = "BANK_ID"),
		@Index(name = "IDX_ACCOUNT_NO", columnList = "ACCOUNT_NO") })
public class UserBankCard extends PersistableEntity<Long> {

	private static final long serialVersionUID = 7216029214808207053L;

	/**
	 * 银行ID
	 */
	@Checked
	@ManyToOne
	@JoinColumn(name = "BANK_ID", referencedColumnName = "id", nullable = false)
	private Bank bank;

	@Checked
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "id", nullable = false)
	private User user;

	/**
	 * 支行名称
	 */
	@Checked
	@Column(name = "BRANCH_NAME", columnDefinition = "varchar(20) COMMENT '支行名称'", nullable = false)
	private String branchName;

	/**
	 * 户名
	 */
	@Checked
	@Column(name = "ACCOUNT_NAME", columnDefinition = "varchar(10) COMMENT '户名'", nullable = false)
	private String accountName;

	/**
	 * 卡号
	 */
	@Checked
	@Column(name = "ACCOUNT_NO", columnDefinition = "varchar(20) COMMENT '卡号'", nullable = false)
	private String accountNo;

	// MD5(户名+卡号)
	@Checked
	@Column(name = "SECRET", columnDefinition = "varchar(32) COMMENT 'SECRET'", nullable = false)
	private String secret;

	/**
	 * 最后修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED_TIME", columnDefinition = "TIMESTAMP NULL COMMENT '最后修改时间'")
	private Date lastModifiedTime = new Date();

	/**
	 * 银行卡状态
	 */
	@Checked
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "varchar(20) COMMENT '银行卡状态'", nullable = false)
	private CardStatus status = CardStatus.NORMAL;
	
	/**
	 * 解冻时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UNFREEZE_TIME", columnDefinition = "TIMESTAMP NULL COMMENT '解冻时间'")
	private Date unfreezeTime;

	public enum CardStatus {
		NORMAL, // 正常
		FROZEN, // 冻结
		DISABLED, // 停用
		DELETED; // 删除
	}
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Date getUnfreezeTime() {
		return unfreezeTime;
	}

	public void setUnfreezeTime(Date unfreezeTime) {
		this.unfreezeTime = unfreezeTime;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CardStatus getStatus() {
		return status;
	}

	public void setStatus(CardStatus status) {
		this.status = status;
	}
	
}
