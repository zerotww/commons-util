import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.commons.collections.ListUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.tww.json.GsonUtil;

import com.nriet.util.SpringUtil;

public class Main {

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<String>();
		list1.add("1");
		list1.add("2");

		List<String> list2 = new ArrayList<String>();
		list2.add("1");
		list2.add("3");

		List<String> list = ListUtils.intersection(list1, list2);
		for (String string : list) {
			System.out.println(list);
		}

		List list3 = ListUtils.sum(list1, list2);
		System.out.println(list3);

		List list4 = ListUtils.subtract(list1, list2);
		System.out.println(list4);
		
	}

	public static void main2(String[] args) {

		String[] dirs = new String[] { "AWS-REG_20140728", "AWS-REG_20140729",
				"AWS-REG_20140730", "AWS-REG_20140823", "AWS-REG_20140824",
				"AWS-REG_20140804", "AWS-REG_20140805", "AWS-REG_20140806",
				"AWS-REG_20140814", "AWS-REG_20140815", "AWS-REG_20140816",
				"AWS-REG_20140822", "AWS-REG_20140926", "AWS-REG_20140927",
				"AWS-REG_20140928" };

		String[] dirs2 = new String[] { "AWS_20140729", "AWS_20140730",
				"AWS_20140728", "AWS_20140805", "AWS_20140806", "AWS_20140814",
				"AWS_20140823", "AWS_20140804", "AWS_20140927", "AWS_20140815",
				"AWS_20140824", "AWS_20140928", "AWS_20140816", "AWS_20140926",
				"AWS_20140822" };

		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < 1; i++) {
			map.put("VC_DATA_TYPE", "AWS");
			map.put("VC_DATA_SUB_TYPE", "AWS_REG");
			map.put("VC_TEMPLATE", ".*");
			map.put("VC_SRC_DIR", "/home/mnt");
			map.put("VC_COLL_TYPE", 0);
			map.put("VC_DEAL_TYPE", 0);
			map.put("VC_COLL_FREQ", "0 0 0/1 * * ?");
			map.put("N_ISSTOP", 0);
			map.put("N_ISCOLL", 0);
			map.put("N_ISRENAME", 1);
			map.put("VC_DEST_DIR", "/gpfs/test3/" + dirs[i]);
			map.put("VC_PATHOF_HOUR", "NOHOUR");
			final String msg = GsonUtil.toJson(map);

			System.out.println(msg);

			JmsTemplate jmsTemplate = SpringUtil.getBean("spring-jms.xml",
					JmsTemplate.class);
			Destination destination = SpringUtil.getBean("spring-jms.xml",
					Destination.class);

			for (int j = 0; j < 1; j++) {

				jmsTemplate.send(destination, new MessageCreator() {

					@Override
					public Message createMessage(Session session)
							throws JMSException {
						return session.createTextMessage(msg);
					}
				});
			}
			map.clear();
		}

	}
}
