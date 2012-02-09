package com.trc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.coupon.CouponDetail;

@Repository
public class CouponDetailDao extends HibernateDaoSupport {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

  public int insertCouponDetail(CouponDetail couponDetail) {
    return (Integer) getHibernateTemplate().save(couponDetail);
  }

  public void deleteCouponDetail(CouponDetail couponDetail) {
    getHibernateTemplate().delete(couponDetail);
  }

  public void updateCouponDetail(CouponDetail couponDetail) {
    getHibernateTemplate().update(couponDetail);
  }

  public CouponDetail getCouponDetail(int couponDetailId) {
    return getHibernateTemplate().get(CouponDetail.class, couponDetailId);
  }
}
