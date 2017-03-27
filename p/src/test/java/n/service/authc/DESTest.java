package n.service.authc;

import n.core.jutils.encrypt.EncryptAndDecryptUtils;

/**
 * 加密解密测试
 * 
 * @author onsoul@qq.com 2016年8月15日 下午5:26:13
 */
public class DESTest {
	public static void main(String[] args) {
		String KEY = "HEROLKJOIEOJLWKJLKJWLKJLJLKJLEKJL";
		String DATA = "花自飘零水自流.";
		
		
		//=========================================================
		String result=EncryptAndDecryptUtils.aesEncrypt(DATA, KEY);
		System.out.println(result);
		String re_data=EncryptAndDecryptUtils.aesDecrypt(result, KEY);
		System.out.println(re_data);
		
		System.out.println(System.getProperties().toString().replace(",", "\n===========================\n"));
		//=========================================================
		
		String md5_code=EncryptAndDecryptUtils.md5Encrypt(KEY+DATA);
		System.out.println(md5_code);
		
		String md5_code2=EncryptAndDecryptUtils.md5Encrypt(KEY+DATA);
		
		System.out.println(md5_code2);
		
	}
}
