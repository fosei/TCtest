package com.trc.service.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.trc.service.jms.Listener;
import com.trc.service.jms.message.NetworkActivation;

public class NetworkQueueListener extends Listener {
  private static final Logger logger = LoggerFactory.getLogger(NetworkQueueListener.class);

  // @Autowired
  public void init(JmsTemplate networkJmsTemplate) {
    setJmsTemplate(networkJmsTemplate);
  }

  @Override
  public void onMessage(Message message) {
    try {
      if (message instanceof ObjectMessage) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        if (objectMessage.getObject() instanceof NetworkActivation) {
          NetworkActivation networkActivation = (NetworkActivation) objectMessage.getObject();
          logger.info("\n\tTC JMS! received object\n\tredelivered: " + objectMessage.getJMSRedelivered() + " "
              + "\n\tattempt number: " + objectMessage.getIntProperty("attempt") + "\n\tusername: "
              + networkActivation.getUser().getUsername() + "\n\tesn: "
              + networkActivation.getNetworkInfo().getEsnmeiddec());
          if (!isMaxAttempts(objectMessage)) {
            resendObjectMessage(objectMessage);
          }
        }
      }
    } catch (JMSException e) {
      logger.error(e.getMessage(), e);
    }
  }

}