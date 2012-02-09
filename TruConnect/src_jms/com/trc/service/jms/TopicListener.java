package com.trc.service.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class TopicListener extends Listener implements MessageListener {
  // private static final Logger logger =
  // LoggerFactory.getLogger(KenanQueueListener.class);

  // @Autowired
  public void init(JmsTemplate kenanTopicJmsTemplate) {
    setJmsTemplate(kenanTopicJmsTemplate);
  }

  public Message receive() {
    Message message = getJmsTemplate().receive();
    return message;
  }

  @Override
  public void onMessage(Message message) {
    // TODO Auto-generated method stub
  }
}
