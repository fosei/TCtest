package com.trc.service.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.trc.service.jms.Listener;
import com.trc.service.jms.message.KenanServiceInstance;

public class KenanQueueListener extends Listener {
  private static final Logger logger = LoggerFactory.getLogger(KenanQueueListener.class);

  // @Autowired
  public void init(JmsTemplate kenanJmsTemplate) {
    setJmsTemplate(kenanJmsTemplate);
  }

  @Override
  public void onMessage(Message message) {
    try {
      if (message instanceof ObjectMessage) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        if (objectMessage.getObject() instanceof KenanServiceInstance) {
          KenanServiceInstance kenanServiceInstance = (KenanServiceInstance) objectMessage.getObject();
          logger.info("\n\tTC JMS! received object:\n\tredelivered: " + objectMessage.getJMSRedelivered()
              + "\n\tattempt number: " + objectMessage.getIntProperty("attempt") + "\n\taccount: "
              + kenanServiceInstance.getAccount().getAccountno() + "\n\tfirst name: "
              + kenanServiceInstance.getAccount().getFirstname() + "\n\tlast name: "
              + kenanServiceInstance.getAccount().getLastname() + "\n\tesn: "
              + kenanServiceInstance.getNetworkInfo().getEsnmeiddec());
        }
        if (!isMaxAttempts(objectMessage)) {
          resendObjectMessage(objectMessage);
        }
      }
    } catch (JMSException e) {
      logger.error(e.getMessage(), e);
    }
  }

}
