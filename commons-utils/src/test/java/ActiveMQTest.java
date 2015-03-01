import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.nriet.util.SpringUtil;

public class ActiveMQTest {

	@Test
	public void testName() throws Exception {
		JmsTemplate bean = SpringUtil.getBean("applicationContext.xml", JmsTemplate.class);
		bean.send("TEST.TEST", new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("Hello");
			}
		});
	}
}
