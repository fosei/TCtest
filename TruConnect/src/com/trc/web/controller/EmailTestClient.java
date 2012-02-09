package com.trc.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@SuppressWarnings("unused")
public class EmailTestClient {

  public static void main(String[] args) {
    InternetAddress from = new InternetAddress();
    InternetAddress to = new InternetAddress();
    InternetAddress bcc = new InternetAddress();
    List<InternetAddress> recipients = new ArrayList<InternetAddress>();
    List<InternetAddress> ccList = new ArrayList<InternetAddress>();
    List<InternetAddress> bccList = new ArrayList<InternetAddress>();
    String subject;
    String message;

    from.setAddress("jpong@telscape.net");
    to.setAddress("trualert@telscape.net");
    recipients.add(to);
    subject = "Telscape Notification Status Test";
    message = "E-mail notification system status ok";

    EmailTestClient emailTestClient = new EmailTestClient();
    try {
      emailTestClient.postMail(recipients, ccList, bccList, subject, message, from);
    } catch (MessagingException e) {
      e.printStackTrace();
    }

  }

  public void postMail(List<InternetAddress> recipients, List<InternetAddress> ccList, List<InternetAddress> bccList,
      String subject, String message, InternetAddress from) throws MessagingException {
    boolean debug = false;

    // Set the host smtp address
    Properties emailProps = new Properties();
    emailProps.put("mail.smtp.host", "209.127.162.26");

    // create some properties and get the default Session
    Session session = Session.getDefaultInstance(emailProps, null);
    session.setDebug(debug);

    // create a message
    Message msg = new MimeMessage(session);

    // set the from and to address
    // InternetAddress addressFrom = new InternetAddress(from);
    msg.setFrom(from);
    // Set the TO
    for (InternetAddress recipient : recipients) {
      msg.addRecipient(RecipientType.TO, recipient);
    }
    // Set the BCC
    if (bccList != null) {
      for (InternetAddress recipient : bccList) {
        msg.addRecipient(RecipientType.BCC, recipient);

      }
    }

    // Set the CC
    if (ccList != null) {
      InternetAddress[] ccArray = new InternetAddress[ccList.size()];
      for (InternetAddress recipient : ccList) {
        msg.addRecipient(RecipientType.CC, recipient);
      }
    }

    // Optional : You can also set your custom headers in the Email if you Want
    msg.addHeader("MyHeaderName", "myHeaderValue");

    // Setting the Subject and Content Type
    msg.setSubject(subject);
    msg.setContent(message, "text/html");
    try {
      Transport.send(msg);
    } catch (SendFailedException send_ex) {
      System.out.println("***** Send Failure Exception Thrown *****");
      send_ex.printStackTrace();
      Address[] validAddressList = send_ex.getValidSentAddresses();
      Address[] invalidAddressList = send_ex.getInvalidAddresses();
      String invalidEmailString = "";
      for (Address address : invalidAddressList) {
        invalidEmailString += address.toString() + "; ";
      }
      AddressException address_ex = new AddressException("The Following Email Address is invalid :: "
          + invalidEmailString);
      throw address_ex;
    }
  }
}