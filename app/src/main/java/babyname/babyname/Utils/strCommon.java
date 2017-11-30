package babyname.babyname.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class strCommon {
	/**
	 * 是否为空
	 *
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (null == s)
			return true;
		if (s.length() == 0)
			return true;
		if (s.trim().length() == 0)
			return true;
		if(s.equals("null")){
			return true;
		}
		return false;
	}

	/**
	 * 是否为数字
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
