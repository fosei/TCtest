package com.trc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.coupon.contract.Contract;

@Repository
public class ContractDao extends HibernateDaoSupport {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

  public int insertContract(Contract contract) {
    return (Integer) getHibernateTemplate().save(contract);
  }

  public void deleteContract(Contract contract) {
    getHibernateTemplate().delete(contract);
  }

  public void updateContract(Contract contract) {
    getHibernateTemplate().update(contract);
  }

  public Contract getContract(int contractId) {
    return getHibernateTemplate().get(Contract.class, contractId);
  }
}
