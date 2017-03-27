package n.play.lottery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import n.entity.play.GMGroup;

/**
 * 生成自动开奖号码
 * @author JTWise 2016年7月25日 上午10:42:38
 */
public class LNFactory {

	private static Map<String, List<String>> baseDataMap = new HashMap<String, List<String>>();
	private static LNFactory instance = null;
	
	private LNFactory() {
		initBaseData();
	}

	public static LNFactory getInstance() {
		if (instance == null) {
			instance = new LNFactory();
		}
		return instance;
	}

	public String makeNum(GMGroup groups) {
		switch (groups.getTypeName()) {
		// 高频彩
		case HIGH: {
			return makeNumber_SSC();
		}
		// 11选5
		case F11X5: {
			return makeNumber_11X5();
		}
		// 3D/P3
		case F3DP3: {
			return makeNumber_FC3D();
		}
		// 快乐彩
		case KLC: {
			return makeNumber_BJKL8();
		}
		// PK10
		case PK10: {
			return makeNumber_BJPK10();
		}
		default: {

		}
		}
		return null;
	}

	/**
	 * 取范围在min--max的随机数，包含min，不包含max
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	private int getRandomNum(int min, int max) {
		int num = (int) (Math.random() * max) + min;
		return num != 255 ? num : getRandomNum(min, max); // 尝试解决255数据越界的问题
		// return random.nextInt(max)%(max-min+1) + min;
	}

	private String makeNumber_SSC() {
		String lotteryNumber = null;
		List<String> cqssc = baseDataMap.get("cqssc");
		for (int i = 0; i < 5; i++) {
			int lineN = getRandomNum(0, cqssc.size());
			String lotteryNum = cqssc.get(lineN);
			String num[] = lotteryNum.split(",");
			int bitN = getRandomNum(0, 5);
			if (lotteryNumber == null) {
				lotteryNumber = num[bitN];
			} else {
				lotteryNumber += " " + num[bitN];
			}
		}
		return lotteryNumber;
	}

	private String makeNumber_11X5() {
		String lotteryNumber = null;
		List<String> sd11x5 = baseDataMap.get("sd11x5");
		for (int i = 0; i < 5; i++) {
			int lineN = getRandomNum(0, sd11x5.size());
			String lotteryNum = sd11x5.get(lineN);
			String num[] = lotteryNum.split(",");
			int bitN = getRandomNum(0, 5);
			while (lotteryNumber != null && lotteryNumber.indexOf(num[bitN]) > -1) {
				bitN = getRandomNum(0, 5);
			}
			if (lotteryNumber == null) {
				lotteryNumber = num[bitN];
			} else {
				lotteryNumber += " " + num[bitN];
			}
		}
		return lotteryNumber;
	}

	private String makeNumber_FC3D() {
		String lotteryNumber = null;
		List<String> fc3d = baseDataMap.get("fc3d");
		for (int i = 0; i < 3; i++) {
			int lineN = getRandomNum(0, fc3d.size());
			String lotteryNum = fc3d.get(lineN);
			String num[] = lotteryNum.split(",");
			int bitN = getRandomNum(0, 3);
			if (lotteryNumber == null) {
				lotteryNumber = num[bitN];
			} else {
				lotteryNumber += " " + num[bitN];
			}
		}
		return lotteryNumber;
	}

	private String makeNumber_BJKL8() {
		String lotteryNumber = null;
		List<String> bjkl8 = baseDataMap.get("bjkl8");
		for (int i = 0; i < 20; i++) {
			int lineN = getRandomNum(0, bjkl8.size());
			String lotteryNum = bjkl8.get(lineN);
			String num[] = lotteryNum.split(",");
			int bitN = getRandomNum(0, 20);
			while (lotteryNumber != null && lotteryNumber.indexOf(num[bitN]) > -1) {
				bitN = getRandomNum(0, 20);
			}
			if (lotteryNumber == null) {
				lotteryNumber = num[bitN];
			} else {
				lotteryNumber += " " + num[bitN];
			}
		}
		return lotteryNumber;
	}

	private String makeNumber_BJPK10() {
		String lotteryNumber = null;
		List<String> bjpk10 = baseDataMap.get("bjpk10");
		for (int i = 0; i < 10; i++) {
			int lineN = getRandomNum(0, bjpk10.size());
			String lotteryNum = bjpk10.get(lineN);
			String num[] = lotteryNum.split(",");
			int bitN = getRandomNum(0, 10);
			while (lotteryNumber != null && lotteryNumber.indexOf(num[bitN]) > -1) {
				bitN = getRandomNum(0, 10);
			}
			if (lotteryNumber == null) {
				lotteryNumber = num[bitN];
			} else {
				lotteryNumber += " " + num[bitN];
			}
		}
		return lotteryNumber;
	}

	private void initBaseData() {
		String path = new File(LNFactory.class.getResource("").getPath()).getPath() + "/data/";

		if (path.indexOf("%20") != -1) {
			path = path.replaceAll("%20", " ");
		}
		System.out.println("path:" + path);

		List<String> cqssc = new ArrayList<String>();
		readTxtFile(path + "cqssc.txt", cqssc);
		System.out.println("cqssc.size:" + cqssc.size());
		baseDataMap.put("cqssc", cqssc);
		// 11选5
		List<String> sd11x5 = new ArrayList<String>();
		readTxtFile(path + "sd11x5.txt", sd11x5);
		System.out.println("sd11x5.size:" + sd11x5.size());
		baseDataMap.put("sd11x5", sd11x5);
		// 3D/排列3
		List<String> fc3d = new ArrayList<String>();
		readTxtFile(path + "fc3d.txt", fc3d);
		System.out.println("sd11x5.size:" + fc3d.size());
		baseDataMap.put("fc3d", fc3d);
		// 北京快乐八
		List<String> bjkl8 = new ArrayList<String>();
		readTxtFile(path + "bjkl8.txt", bjkl8);
		System.out.println("bjkl8.size:" + bjkl8.size());
		baseDataMap.put("bjkl8", bjkl8);
		// 北京PK10
		List<String> bjpk10 = new ArrayList<String>();
		readTxtFile(path + "bjpk10.txt", bjpk10);
		System.out.println("bjpk10.size:" + bjpk10.size());
		baseDataMap.put("bjpk10", bjpk10);
	}

	private static void readTxtFile(String filePath, List<String> dataList) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String number = lineTxt.split("\\|")[1];
					dataList.add(number);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++)
			System.out.println(LNFactory.getInstance().makeNum(new GMGroup()));
	}
}
