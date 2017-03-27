/**
 * 验证电话号码（手机号码+电话号码）
 * @param obj
 * @returns {Boolean}
 */
function checkPhoneNum(obj){
    if(/^((\d{3}-\d{8}|\d{4}-\d{7,8})|(1[3|5|7|8][0-9]{9}))$/.test(obj)){
        return true;
    }
}
/**
 * 验证邮箱
 * @param obj
 * @returns {Boolean}
 */
function checkEmail(obj){
	if(/^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test(obj)){
		return true;
	}
}
/**
 * 验证用户名输入格式
 * @param obj
 * @returns {Boolean}
 */
function checkUserName(obj){
    if(/^[a-zA-Z0-9_-]{6,16}$/.test(obj)){
        return true;
    }
}
/**
 * 验证密码输入格式
 * @param obj
 * @returns {Boolean}
 */
function verifyPassword(obj){
    if(/^[a-z0-9_-]{6,18}$/.test(obj)){
        return true;
    }
}