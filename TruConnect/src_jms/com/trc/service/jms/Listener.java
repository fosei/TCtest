package com.trc.service.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public abstract class Listener implements MessageListener {
  private int maxAttempts = 5;
  private JmsTemplate jmsTemplate;

  public JmsTemplate getJmsTemplate() {
    return jmsTemplate;
  }

  public void setJmsTemplate(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  public int getMaxAttempts() {
    return maxAttempts;
  }

  public void setMaxAttempts(int maxAttempts) {
    this.maxAttempts = maxAttempts;
  }

  public void resendObjectMessage(final ObjectMessage objectMessage) {
    getJmsTemplate().send(new MessageCreator() {
      public ObjectMessage createMessage(Session session) throws JMSException {
        ObjectMessage objMessage = session.createObjectMessage(objectMessage.getObject());
        objMessage.setIntProperty("attempt", objectMessage.getIntProperty("attempt") + 1);
        return objMessage;
      }
    });
  }

  public boolean isMaxAttempts(Message message) throws JMSException {
    int numAttempts = message.getIntProperty("attempt");
    if (numAttempts < getMaxAttempts()) {
      return false;
    } else {
      return true;
    }
  }
}
