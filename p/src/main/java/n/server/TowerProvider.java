package n.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * JT CHAT 提供服务
 * @author onsoul
 * @date 2016年7月19日 上午9:55:55
 * @verion 1.0
 */

@SuppressWarnings("resource")
public class TowerProvider {
	
	/**
	 * 提供后端服务主线程
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		String ctx_conf = "multi/ctx.xml";  				    //主配置 
		String provider_conf = "multi/provider.xml"; 			//服务提供配置
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(ctx_conf,provider_conf);
		ctx.start();
		System.out.println("TOWER Service successfully started");
		System.in.read();
	}
}
