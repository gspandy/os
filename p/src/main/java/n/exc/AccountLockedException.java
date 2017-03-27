package n.exc;

import n.core.exception.BusinessException;

/**
 * 帐号已禁用
 * @author User
 */
public class AccountLockedException extends BusinessException {

	private static final long serialVersionUID = 8345852737557453506L;

	private static final String CODE = AccountLockedException.class.getName();
	
	public static final AccountLockedException ERROR = new AccountLockedException();
	
	public AccountLockedException() {
		super(CODE);
	}

	public AccountLockedException(String code) {
		super(code);
	}

}