package demo;

import com.xingcloud.ml.ML;

public class demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ML ml = new ML("javatest", "98d21e42db699c5ef061434c69f57694", "cn", "en", "D:\\test", true, true);
		String s1 = ml.trans("北京");
		System.out.println(s1);
		String s2 = ml.trans("人民大会堂");
		System.out.println(s2);
	}

}
