package com.wise.entity.sider;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wise.core.entity.annotation.Checked;
import com.wise.core.entity.support.CheckableEntity;

/**
 * 骑士表
 * @author
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_KNIGHT")
@NamedNativeQueries({
		@NamedNativeQuery(name = "findAllKnight", query = "SELECT k FROM Knight k", resultSetMapping = "allNameResult") })

@SqlResultSetMapping(name = "allNameResult", classes = {
		@ConstructorResult(targetClass = Knight.class, columns = { @ColumnResult(name = "name") }) }

)
public class Knight extends CheckableEntity<Integer> {

	private static final long serialVersionUID = -8061702407266997556L;

	/**
	 * 租户代号
	 */
	@Checked
	@Column(name = "TENANT_CODE",length = 5,columnDefinition = "varchar(16) COMMENT '租户代号'",nullable = false)
	private String tenantCode;


	/**
	 * 骑士名称
	 */
	@Checked
	@Column(name = "NAME", length = 10,columnDefinition = "varchar(16) COMMENT '用户帐号'", nullable = false)
	private String name;

	/**
	 * 余额(注：数据库保留5为小数)
	 */
	@Checked
	@Column(name = "ACCOUNT_BALANCE", columnDefinition = "DECIMAL(15,5) DEFAULT 0.0 COMMENT '账户余额'")
	private Double accountBalance = 0D;

	/**
	 * 登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGIN_TIME", columnDefinition = "TIMESTAMP NULL COMMENT '登录时间'")
	private Date loginTime;

	/**
	 * 是否删除（逻辑删除）
	 */
	@Checked
	@Column(name = "IS_DELETE", columnDefinition = "TINYINT(2) DEFAULT 0 COMMENT '是否删除'")
	private Boolean isDelete = Boolean.FALSE;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean delete) {
		isDelete = delete;
	}

}
