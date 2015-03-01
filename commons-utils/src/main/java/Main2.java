import java.util.Calendar;

import org.joda.time.DateTime;

import com.nriet.util.DateTimeUtil;

public class Main2 {

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), 1, 1);
		calendar.roll(Calendar.DATE, false);
		System.out.println(calendar.get(Calendar.DATE));
	}

	public static void main2(String[] args) {
		String[] arr = new String[] { "20140728000000", "20140729000000",
				"20140730000000", "20140823000000", "20140824000000",
				"20140804000000", "20140805000000", "20140806000000",
				"20140814000000", "20140815000000", "20140816000000",
				"20140822000000", "20140926000000", "20140927000000",
				"20140928000000" };

		int count = 1;
		for (String string : arr) {
			Calendar cal = DateTimeUtil.toCalendar(string, "yyyyMMddHHmmss");
			for (int i = 0; i < 23; i++) {
				cal.set(Calendar.HOUR_OF_DAY, i);
				for (int j : new int[] { 0, 10, 20, 30, 40, 50 }) {
					cal.set(Calendar.MINUTE, j);
					System.out.println(count++ + ":"
							+ DateTimeUtil.toString(cal, "yyyyMMddHHmmss"));
				}
			}
		}
	}
}
