package com.trc.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponDetail;
import com.trc.coupon.UserCoupon;
import com.trc.coupon.contract.ProrationCalculator;
import com.trc.dao.CouponDao;
import com.trc.dao.CouponDetailDao;
import com.trc.dao.UserCouponDao;
import com.trc.exception.service.CouponServiceException;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.user.User;
import com.trc.util.Formatter;
import com.trc.util.logger.DevLogger;
import com.tscp.mvne.Account;
import com.tscp.mvne.KenanContract;
import com.tscp.mvne.ServiceInstance;
import com.tscp.mvne.TruConnect;

@Service
public class CouponService {
  private TruConnect truConnect;
  private CouponDao couponDao;
  private CouponDetailDao couponDetailDao;
  private UserCouponDao userCouponDao;

  /* *****************************************************************
   * Initialization
   * *****************************************************************
   */

  @Autowired
  public void init(TruConnectGateway truConnectGateway, CouponDao couponDao, CouponDetailDao couponDetailDao,
      UserCouponDao userCouponDao) {
    this.truConnect = truConnectGateway.getPort();
    this.couponDao = couponDao;
    this.couponDetailDao = couponDetailDao;
    this.userCouponDao = userCouponDao;
  }

  /* *****************************************************************
   * Coupon DAO layer interaction
   * *****************************************************************
   */

  @Transactional
  public int insertCoupon(Coupon coupon) throws CouponServiceException {
    try {
      return couponDao.insertCoupon(coupon);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error inserting Coupon from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public void deleteCoupon(Coupon coupon) throws CouponServiceException {
    try {
      couponDao.deleteCoupon(coupon);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error deleting Coupon from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public void updateCoupon(Coupon coupon) throws CouponServiceException {
    try {
      couponDao.updateCoupon(coupon);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error updating Coupon from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public void incCouponUsedCount(Coupon coupon) throws CouponServiceException {
    coupon.setUsed(coupon.getUsed() + 1);
    updateCoupon(coupon);
  }

  @Transactional
  public Coupon getCoupon(int couponId) throws CouponServiceException {
    try {
      return couponDao.getCoupon(couponId);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error fetching Coupon from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public List<Coupon> getAllCoupons() throws CouponServiceException {
    try {
      return couponDao.getAllCoupons();
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error fetching Coupon from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public Coupon getCouponByCode(String couponCode) throws CouponServiceException {
    try {
      return couponDao.getCouponByCode(couponCode);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error fetching Coupon from DAO layer: " + e.getMessage());
    }
  }

  /* *****************************************************************
   * CouponDetail DAO layer interaction
   * *****************************************************************
   */

  @Transactional
  public int insertCouponDetail(CouponDetail couponDetail) throws CouponServiceException {
    try {
      return (Integer) couponDetailDao.insertCouponDetail(couponDetail);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error inserting CouponDetail from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public void deleteCouponDetail(CouponDetail couponDetail) throws CouponServiceException {
    try {
      couponDetailDao.deleteCouponDetail(couponDetail);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error deleting CouponDetail from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public void updateCouponDetail(CouponDetail couponDetail) throws CouponServiceException {
    try {
      couponDetailDao.updateCouponDetail(couponDetail);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error updating CouponDetail from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public CouponDetail getCouponDetail(int couponDetailId) throws CouponServiceException {
    try {
      return couponDetailDao.getCouponDetail(couponDetailId);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error fetching CouponDetail from DAO layer: " + e.getMessage());
    }
  }

  /* *****************************************************************
   * UserCoupon DAO layer interaction
   * *****************************************************************
   */

  @Transactional
  public void insertUserCoupon(UserCoupon userCoupon) throws CouponServiceException {
    try {
      userCouponDao.insertUserCoupon(userCoupon);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error inserting UserCoupon from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public void deleteUserCoupon(User user, Coupon coupon, Account account) throws CouponServiceException {
    UserCoupon userCoupon = new UserCoupon(coupon, user, account);
    try {
      userCouponDao.deleteUserCoupon(userCoupon);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error updating UserCoupon from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public void updateUserCoupon(UserCoupon userCoupon) throws CouponServiceException {
    try {
      userCouponDao.updateUserCoupon(userCoupon);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error updating UserCoupon from DAO layer: " + e.getMessage());
    }
  }

  @Deprecated
  public List<KenanContract> getContracts(Account account, ServiceInstance serviceInstance)
      throws CouponServiceException {
    // TODO there needs to be a way for individual contracts to map back to
    // individual coupons
    try {
      List<KenanContract> contracts = truConnect.getContracts(account, serviceInstance);
      return contracts;
    } catch (WebServiceException e) {
      throw new CouponServiceException(e.getMessage(), e.getCause());
    }
  }

  @Transactional
  public UserCoupon getUserCoupon(UserCoupon userCoupon) throws CouponServiceException {
    try {
      return userCouponDao.getUserCoupon(userCoupon);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error fetching UserCoupon from DAO layer: " + e.getMessage());
    }
  }

  @Transactional
  public List<UserCoupon> getUserCoupons(int userId) throws CouponServiceException {
    try {
      return userCouponDao.getUserCoupons(userId);
    } catch (DataAccessException e) {
      throw new CouponServiceException("Error fetching UserCoupon from DAO layer: " + e.getMessage());
    }
  }

  /* *****************************************************************
   * Application of Coupons in Kenan using TSCPMVNE
   * *****************************************************************
   */

  @Transactional
  public int applyCouponPayment(Coupon coupon, User user, Account account, Date date) throws CouponServiceException {
    try {
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
      String stringAmount = Formatter.formatDollarAmountQuery(coupon.getCouponDetail().getAmount());
      int trackingId = truConnect.applyCouponPayment(account, stringAmount, xmlCal);
      try {
        UserCoupon userCoupon = new UserCoupon(coupon, user, account);
        userCoupon.setKenanContractId(-1);
        userCoupon.setActive(true);
        insertUserCoupon(userCoupon);
        incCouponUsedCount(coupon);
        return trackingId;
      } catch (DataAccessException e) {
        // TODO rollback the credit that was given
        throw new CouponServiceException("Error inserting UserCoupon: " + e.getMessage(), e.getCause());
      }
    } catch (DatatypeConfigurationException dce) {
      throw new CouponServiceException(dce.getMessage(), dce.getCause());
    } catch (WebServiceException e) {
      throw new CouponServiceException("Error applying coupon payment: " + e.getMessage(), e.getCause());
    }
  }

  @Transactional
  public int applyCoupon(User user, Coupon coupon, Account account, ServiceInstance serviceInstance)
      throws CouponServiceException {
    try {
      KenanContract kenanContract = new KenanContract();
      kenanContract.setAccount(account);
      kenanContract.setServiceInstance(serviceInstance);
      kenanContract.setContractType(coupon.getCouponDetail().getContract().getContractType());
      kenanContract.setDuration(coupon.getCouponDetail().getDuration());
      int contractId = truConnect.applyContract(kenanContract);

      // Previously wanted to credit the customer for the prorated amount they
      // would be charged for the first month before the coupon took effect
      //
      // ProrationCalculator pc = new ProrationCalculator();
      // double amount = pc.getProratedAmount();
      // GregorianCalendar calendar = new GregorianCalendar();
      // XMLGregorianCalendar xmlCal =
      // DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
      // String stringAmount = Formatter.formatDollarAmountQuery(amount);
      // truConnect.applyCouponPayment(account, stringAmount, xmlCal);

      try {
        UserCoupon userCoupon = new UserCoupon(coupon, user, account);
        userCoupon.setKenanContractId(kenanContract.getContractId());
        userCoupon.setActive(true);
        insertUserCoupon(userCoupon);
        incCouponUsedCount(coupon);
        return contractId;
      } catch (DataAccessException e) {
        kenanContract.setDuration(0);
        truConnect.updateContract(kenanContract);
        throw new CouponServiceException("Error inserting UserCoupon: " + e.getMessage(), e.getCause());
      }
    } catch (WebServiceException e) {
      throw new CouponServiceException(e.getMessage(), e.getCause());
    }
  }

  @Transactional
  public void cancelCoupon(User user, Coupon coupon, Account account, ServiceInstance serviceInstance)
      throws CouponServiceException {
    try {
      List<KenanContract> contracts = truConnect.getContracts(account, serviceInstance);
      KenanContract kenanContract = findContractInList(contracts, coupon.getCouponDetail().getContract()
          .getContractType());
      if (kenanContract != null) {
        int originalDuration = kenanContract.getDuration();
        kenanContract.setAccount(account);
        kenanContract.setServiceInstance(serviceInstance);
        kenanContract.setDuration(0);
        truConnect.updateContract(kenanContract);
        try {
          UserCoupon userCoupon = new UserCoupon(coupon, user, account);
          userCoupon.setKenanContractId(kenanContract.getContractId());
          userCoupon.setActive(false);
          updateUserCoupon(userCoupon);
        } catch (DataAccessException e) {
          kenanContract.setDuration(originalDuration);
          truConnect.updateContract(kenanContract);
          throw new CouponServiceException("Error could not update UserCoupon: " + e.getMessage(), e.getCause());
        }
      } else {
        throw new CouponServiceException("Could not find associated contract for coupon " + coupon.getCouponId());
      }
    } catch (WebServiceException e) {
      throw new CouponServiceException(e.getMessage(), e.getCause());
    }
  }

  private KenanContract findContractInList(List<KenanContract> contracts, int contractId) {
    for (KenanContract kc : contracts) {
      if (kc.getContractType() == contractId) {
        return kc;
      }
    }
    return null;
  }
}