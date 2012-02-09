package com.trc.service.jms;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

//@Service
public class TopicSender {
  private JmsTemplate jmsTemplate = new JmsTemplate();
  // @Autowired
  private ConnectionFactory jmsTopicConnectionFactory;
  // @Autowired
  private Destination kenanTopic;

  // @PostConstruct
  public void init() {
    jmsTemplate.setConnectionFactory(jmsTopicConnectionFactory);
    jmsTemplate.setDefaultDestination(kenanTopic);
    jmsTemplate.setSessionTransacted(true);
    jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
    jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
    jmsTemplate.setPubSubDomain(true);
  }

  public void send(final Serializable object, Destination destination) {
    jmsTemplate.send(destination, new MessageCreator() {
      public ObjectMessage createMessage(Session session) throws JMSException {
        ObjectMessage message = session.createObjectMessage(object);
        message.setIntProperty("attempt", 1);
        return message;
      }
    });
  }

}
