package com.trc.service.jms.sender;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.trc.service.jms.message.NetworkActivation;
import com.trc.user.User;
import com.tscp.mvne.NetworkInfo;

//@Service
public class NetworkQueueSender {
  private static final Logger logger = LoggerFactory.getLogger(NetworkQueueSender.class);

  // @Autowired
  private JmsTemplate networkJmsTemplate;

  public void generateMessages() throws JMSException {
    User user = new User();
    user.setUsername("Super King");

    NetworkInfo networkInfo = new NetworkInfo();
    networkInfo.setEsnmeiddec("01234567891");
    networkInfo.setMdn("1234567");
    networkInfo.setStatus("test case");

    NetworkActivation networkActivation = new NetworkActivation();
    networkActivation.setUser(user);
    networkActivation.setNetworkInfo(networkInfo);

    final NetworkActivation truConnectActivationMsg = networkActivation;

    networkJmsTemplate.send(new MessageCreator() {
      public ObjectMessage createMessage(Session session) throws JMSException {
        ObjectMessage message = session.createObjectMessage(truConnectActivationMsg);
        message.setIntProperty("attempt", 1);
        logger.info("\n\tTC JMS! Sending object: " + session.getAcknowledgeMode() + " "
            + truConnectActivationMsg.getUser().getUsername());
        return message;
      }
    });

  }
}
