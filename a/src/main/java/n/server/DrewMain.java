package n.server;

import java.io.IOException;

import com.google.zxing.common.BitMatrix;

import n.core.jutils.QRCode.MatrixToImageWriterEx;

public class DrewMain {
	public static void main(String[] args) throws IOException {
		 
	}
}

class DrewRCode extends Thread {

	// 画张二维码
	private boolean _drowRCode(String content, Integer width, Integer hegiht) {
		try {
			BitMatrix matrix = MatrixToImageWriterEx.createQRCode(content, width, hegiht);
			MatrixToImageWriterEx.writeToFile(matrix, "png", "D://hh.png", "");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void run() {
		this._drowRCode("这娃火是因为她有气质!", 300, 300);
		/*
		 * String line = "abcdefghijklmnopqrstuvwxyz"; // 字母列表 String
		 * split_laster[] = line.split("");
		 * 
		 * int o = 1; for (int i = 0; i < split_laster.length; i++) { for (int j
		 * = 0; j < split_laster.length; j++) { System.out.print(split_laster[i]
		 * + split_laster[j] + ","); o++; } } System.out.println("\n" + o + ","
		 * + 26 * 26); super.run();
		 */
	}

}
