package com.trc.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponDetail;
import com.trc.coupon.contract.Contract;

/**
 * Used for Coupon testing.
 * 
 */
@Deprecated
public class HibernateUtil_Coupon {
  private static final String configurationFile = "hibernate.coupon.cfg.xml";
  private static final SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    try {
      return new Configuration().configure(configurationFile).addAnnotatedClass(Coupon.class).addAnnotatedClass(
          Contract.class).addAnnotatedClass(CouponDetail.class).buildSessionFactory();
    } catch (Throwable ex) {
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static Session openSession() {
    return getSessionFactory().openSession();
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static Session getCurrentSession() {
    return getSessionFactory().getCurrentSession();
  }

  public static Transaction beginTransaction() {
    return getCurrentSession().beginTransaction();
  }

  public static void closeSession(Session session) {
    if (session.isOpen()) {
      session.close();
    }
  }
}
