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

import com.trc.service.jms.message.KenanServiceInstance;
import com.tscp.mvne.Account;
import com.tscp.mvne.NetworkInfo;

//@Service
public class KenanQueueSender {
  private static final Logger logger = LoggerFactory.getLogger(KenanQueueSender.class);

  // @Autowired
  private JmsTemplate kenanJmsTemplate;

  public void generateMessages() throws JMSException {
    Account account = new Account();
    account.setAccountno(123456);
    account.setFirstname("Captain");
    account.setLastname("Yesterday");

    NetworkInfo networkInfo = new NetworkInfo();
    networkInfo.setEsnmeiddec("01234567891");
    networkInfo.setMdn("1234567");
    networkInfo.setStatus("test case");

    KenanServiceInstance kenanActivation = new KenanServiceInstance();
    kenanActivation.setAccount(account);
    kenanActivation.setNetworkInfo(networkInfo);

    final KenanServiceInstance kenanActivationMsg = kenanActivation;

    kenanJmsTemplate.send(new MessageCreator() {
      public ObjectMessage createMessage(Session session) throws JMSException {
        ObjectMessage message = session.createObjectMessage(kenanActivationMsg);
        message.setIntProperty("attempt", 1);
        logger.info("\n\tTC JMS! Sending object: " + session.getAcknowledgeMode() + " "
            + kenanActivationMsg.getAccount().getFirstname() + " " + kenanActivationMsg.getAccount().getLastname());
        return message;
      }
    });

  }
}
