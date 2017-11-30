package babyname.babyname.Utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class timeCommon {

	/**
	 * 获得当前日
	 * @return
	 */
	public static int getDay(){
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 获得当前月
	 * @return
	 */
	public static int getMonth(){
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int month = c.get(Calendar.MONTH)+1;
		return month;
	}


	/**
	 * 获得当前年
	 * @return
	 */
	public static int getYear(){
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int year = c.get(Calendar.YEAR);
		return year;
	}


	/**
	 * 获得当前小时
	 * @return
	 */
	public static int getHour(){
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	/**
	 * 获得当前分钟
	 * @return
	 */
	public static int getMinute(){
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int minute = c.get(Calendar.MINUTE);
		return minute;
	}
	/**
	 * 返回星期几
	 *
	 * @return 1代表 星期天 以此类推
	 */
	public static String getWeekDay(int mWay) {
		String weekDay = "";
		switch (mWay) {
			case 1:
				weekDay = "SUN";
				break;
			case 2:
				weekDay = "MON";
				break;
			case 3:
				weekDay = "TEU";
				break;
			case 4:
				weekDay = "WED";
				break;
			case 5:
				weekDay = "THU";
				break;
			case 6:
				weekDay = "FRI";
				break;
			case 7:
				weekDay = "SAT";
				break;
			default:
				break;
		}
		return weekDay;
	}

	/**
	 * 返回星期几
	 *
	 * @return 1代表 星期天 以此类推
	 */
	public static int getWeekDay() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int mWay = c.get(Calendar.DAY_OF_WEEK);
		return mWay;
	}

	/**
	 * 毫秒数转时间字符串
	 *
	 * @parampattern
	 *            时间格式
	 * @param dateTime
	 *            毫秒数
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getTimeFromMinmis(String Format, long dateTime) {
		Log.i("",dateTime+":Minmis");
		if(dateTime==0){
			return "";
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat(Format);
		return sDateFormat.format(new Date(dateTime + 0));
	}
	/**
	 * 将毫秒数除以1000的数 转化为时间
	 * @param Format
	 * @param dateTime
	 * @return
	 */
	public static String getTimeFromMinmis(String Format, String dateTime){
		if(strCommon.isEmpty(dateTime)){
			return "";
		}
		if(!strCommon.isNumeric(dateTime)){
			return "";
		}
		return getTimeFromMinmis(Format,Long.valueOf(dateTime)*1000);
	}
	/**
	 * 获得当前时间
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTimeString(String Format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(Format);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获得一个时间N天之后的时间
	 *
	 * @return
	 */
	public static String getTimeAfter(Date date, int dayNum, String dateFormat) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + dayNum);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String dateString = formatter.format(now.getTime());
		return dateString;
	}

	/**
	 * 字符串转date
	 *
	 * @param strTime
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date getDateFromString(String strTime, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = formatter.parse(strTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}


	/**
	 * 时间差  天
	 */

	public static long getDateCutDay(String startTime, String endTime,
									 String format) {
		DateFormat df = new SimpleDateFormat(format);
		long days = 0;
		try {
			Date d1 = df.parse(startTime);
			Date d2 = df.parse(endTime);
			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			days = diff / (1000 * 60 * 60 * 24);
			// System.out.println(""+days+"天"+hours+"小时"+minutes+"分");

		} catch (Exception e) {
		}
		return days;
	}


	/**
	 * 时间差  时
	 */

	public static long getDateCutHour(String startTime, String endTime,
									  String format) {
		DateFormat df = new SimpleDateFormat(format);
		long days = 0;
		long hours =0;
		try {
			Date d1 = df.parse(startTime);
			Date d2 = df.parse(endTime);
			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			days = diff / (1000 * 60 * 60 * 24);

			hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			// System.out.println(""+days+"天"+hours+"小时"+minutes+"分");

		} catch (Exception e) {
		}
		return hours;
	}

	//时间戳转字符串
	public static String getStrTime(String timeStamp,String format){
		String timeString = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long  l = Long.valueOf(timeStamp);
		timeString = sdf.format(new Date(l));//单位秒
		return timeString;
	}
	//字符串转时间戳
	public static String getTime(String user_time,String format) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d;
		try {


			d = sdf.parse(user_time);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}
}
