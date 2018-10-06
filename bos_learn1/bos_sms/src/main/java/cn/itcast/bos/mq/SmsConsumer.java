package cn.itcast.bos.mq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Service;
@Service 
public class SmsConsumer implements MessageListener{

	@Override
	public void onMessage(Message message) {
	MapMessage mapMessage=(MapMessage) message;
	
	//String sendMsg = ALiYunMsgUtils.sendMsg(mapMessage.getString("telephone"), mapMessage.getString("msg"));
	try {
		System.out.println("手机号："+mapMessage.getString("telephone")+"验证码："+mapMessage.getString("msg"));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}

}
