package n.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
	
	@Override
	public void initialize(Password constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 不能9位以下纯数字
		if (value.matches("^\\d{1,9}")) {
			return false;
		}
		// 不能相同字符组成
		if (value.matches("^(.)\\1+$")) {
			return false;
		}
		// 不能由连续字符组成
		if (consectiveStr(value)) {
			return false;
		}
		return true;
	}
	
	private static boolean consectiveStr(String value) {
		if (value.matches("^\\d+$")) {
			char[] arr = value.toCharArray();
			if (48 == arr[0] && 57 == arr[1]) {
				arr[0] = 58;
			}
			if (48 == arr[arr.length - 1] && 57 == arr[arr.length - 2]) {
				arr[arr.length - 1] = 58;
			}
			return consectiveStr2(arr);
		}
		if (value.matches("^[A-Z]+$") || value.matches("^[a-z]+$")) {
			return consectiveStr2(value.toCharArray());
		}
		return false;
	}
	
	private static boolean consectiveStr2(char[] arr) {
		int iFX = arr[1] - arr[0];
		if (1 != iFX && -1 != iFX) {
			return false;
		}
		for (int idx = 2; idx < arr.length; idx++) {
			if (arr[idx] - arr[idx - 1] != iFX) {
				return false;
			}
		}
		return true;
	}


}
