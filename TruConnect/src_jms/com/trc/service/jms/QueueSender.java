package com.trc.service.jms;

import java.io.Serializable;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

//@Service
public class QueueSender {
  private JmsTemplate jmsTemplate = new JmsTemplate();
  // @Autowired
  private ConnectionFactory jmsConnectionFactory;
  // @Autowired
  private Destination truConnectQueue;

  // @PostConstruct
  public void init() {
    jmsTemplate.setConnectionFactory(jmsConnectionFactory);
    jmsTemplate.setDefaultDestination(truConnectQueue);
    jmsTemplate.setSessionTransacted(true);
    jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
    jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
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
