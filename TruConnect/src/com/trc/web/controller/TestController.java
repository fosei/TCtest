package com.trc.web.controller;

import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.coupon.Coupon;
import com.trc.coupon.UserCoupon;
import com.trc.exception.ValidationException;
import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.CouponManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.CouponManager;
import com.trc.manager.DeviceManager;
import com.trc.manager.PaymentManager;
import com.trc.manager.UserManager;
import com.trc.manager.webflow.PaymentFlowManager;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.user.User;
import com.trc.util.ClassUtils;
import com.trc.util.logger.DevLogger;
import com.trc.web.model.ResultModel;
import com.trc.web.validation.CouponValidator;
import com.tscp.mvne.Account;
import com.tscp.mvne.KenanContract;
import com.tscp.mvne.ServiceInstance;
import com.tscp.mvne.TruConnect;

// @Controller
//@RequestMapping("/test")
@SuppressWarnings("unused")
public class TestController {
  // @Autowired
  private PaymentFlowManager paymentFlowManager;
  // @Autowired
  private PaymentManager paymentManager;
  // @Autowired
  private UserManager userManager;
  // @Autowired
  private AccountManager accountManager;
  // @Autowired
  private DeviceManager deviceManager;
  // @Autowired
  private CouponManager couponManager;
  // @Autowired
  private CouponValidator couponValidator;

  private TruConnect truConnect;

  // @Autowired
  public void init(TruConnectGateway truConnectGateway) {
    truConnect = truConnectGateway.getPort();
  }

  // @RequestMapping(value = "/coupon/validation", method = RequestMethod.GET)
  public ModelAndView testCouponValidation() {
    ResultModel model = new ResultModel("test/success");
    try {
      User user = userManager.getUserById(547);
      Account account = accountManager.getAccounts(user).get(0);
      ServiceInstance serviceInstance = account.getServiceinstancelist().get(0);

      boolean isAvailable;
      boolean isApplied;
      boolean isAtAccountLimit;
      boolean isRecurring;
      boolean isStackable;
      boolean isEligible;
      List<Coupon> allCoupons = couponManager.getAllCoupons();
      List<UserCoupon> userCoupons = couponManager.getUserCoupons(user.getUserId());
      for (Coupon coupon : allCoupons) {
        DevLogger.log("\n\nChecking coupon:" + coupon.getCouponId() + " detail:"
            + coupon.getCouponDetail().getCouponDetailId() + " " + " contract:"
            + coupon.getCouponDetail().getContract().getDescription() + " duration:"
            + coupon.getCouponDetail().getDuration() + " amount:" + coupon.getCouponDetail().getAmount());
        isAvailable = couponValidator.isQuantityAvailable(coupon);
        isApplied = couponValidator.isApplied(coupon, user, account);
        isAtAccountLimit = couponValidator.isAtAccountLimit(coupon, user, account);
        isRecurring = coupon.isRecurring();
        isEligible = couponValidator.isEligible(coupon, user, account);
        DevLogger.log("....isAvailable=" + isAvailable);
        DevLogger.log("....isApplied=" + isApplied);
        DevLogger.log("....isAtAccountLimit=" + isAtAccountLimit);
        DevLogger.log("....isRecurring=" + isRecurring);
        for (UserCoupon uc : userCoupons) {
          isStackable = couponValidator.isStackable(coupon, uc.getId().getCoupon());
          DevLogger.log("....isStackable=" + isStackable + " [" + uc.getId().getCoupon().getCouponId() + ", "
              + coupon.getCouponId() + "]");
        }
        DevLogger.log("....isEligible=" + isEligible);
      }

    } catch (AccountManagementException e) {
      DevLogger.log("Account management error " + e.getMessage());
      return model.getAccessException();
    } catch (CouponManagementException e) {
      DevLogger.log("Coupon management error " + e.getMessage());
      return model.getAccessException();
    } catch (ValidationException e) {
      DevLogger.log("Exception while checking if coupon is applied");
      return model.getAccessException();
    }
    return model.getSuccess();
  }

  // @RequestMapping(value = "/coupon/insert")
  public String testInsertCoupon() {
    return "test/success";
  }

  // @RequestMapping(value = "/coupon/payment")
  public String testCouponPayment() {
    DevLogger.log("TestController /coupon/payment");
    DevLogger.log("fetching user...");
    User user = userManager.getUserByEmail("jonathan.pong@gmail.com");
    DevLogger.log("found user:" + user.toString());
    try {
      DevLogger.log("fetching account...");
      Account account = accountManager.getAccounts(user).get(0);
      ServiceInstance serviceInstance = account.getServiceinstancelist().get(0);
      DevLogger.log("found account: " + account.toString());
      DevLogger.log("attempting to apply coupon payment...");
      Coupon coupon = new Coupon();
      couponManager.applyCoupon(coupon, user, account, serviceInstance);
    } catch (AccountManagementException e) {
      DevLogger.log("error while fetching account");
      e.printStackTrace();
    } catch (CouponManagementException e) {
      DevLogger.log("error while applying coupon payment");
      e.printStackTrace();
    }
    return "test/success";
  }

  // @RequestMapping(value = "/coupon/fetch")
  public String testFetchCoupons() {
    try {
      User user = userManager.getUserById(547);
      Account account = accountManager.getAccounts(user).get(0);
      ServiceInstance serviceInstance = account.getServiceinstancelist().get(0);

      List<KenanContract> contracts = truConnect.getContracts(account, serviceInstance);
      for (KenanContract kc : contracts) {
        DevLogger.log(kc.toString());
      }

      List<UserCoupon> userCoupons = couponManager.getUserCoupons(user.getUserId());
      for (UserCoupon uc : userCoupons) {
        DevLogger.log(uc.toString());
      }
    } catch (AccountManagementException e) {
      e.printStackTrace();
    } catch (CouponManagementException e) {
      e.printStackTrace();
    }
    return "test/success";
  }

  // @RequestMapping(value = "/coupon/cancel")
  public String testUpdateCoupon() {
    try {
      User user = userManager.getUserByEmail("jonathan.pong@gmail.com");
      DevLogger.log(ClassUtils.toString(user));
      DevLogger.log("fetching account");
      Account account = accountManager.getAccounts(user).get(0);
      DevLogger.log(ClassUtils.toString(account));
      DevLogger.log("fetching service instance");
      ServiceInstance serviceInstance = account.getServiceinstancelist().get(0);
      DevLogger.log(ClassUtils.toString(serviceInstance));
      List<KenanContract> contracts = truConnect.getContracts(account, serviceInstance);
      DevLogger.log("found " + contracts.size() + " contracts");
      DevLogger.log("fetching contracts with " + account.getAccountno() + " " + serviceInstance.getExternalid());

      for (KenanContract kc : contracts) {
        DevLogger.log("contract: " + kc.getContractId() + " " + kc.getContractType() + " " + kc.getDuration());
      }

      Coupon coupon = new Coupon();
      List<UserCoupon> userCoupons;
      try {
        userCoupons = couponManager.getUserCoupons(user.getUserId());
      } catch (CouponManagementException e) {
        DevLogger.log("error fetching coupons, returning empty list");
        userCoupons = new Vector<UserCoupon>();
      }
      DevLogger.log("found " + userCoupons.size() + " user coupons in local database");
      List<Coupon> coupons = new Vector<Coupon>();
      for (UserCoupon uc : userCoupons) {
        // coupon = couponManager.getCoupon(uc.getId().getCouponId());
        coupon = uc.getId().getCoupon();
        DevLogger.log("" + coupon.toString());
        coupons.add(coupon);
      }

      for (Coupon c : coupons) {
        try {
          couponManager.cancelCoupon(coupon, user, account, serviceInstance);
        } catch (CouponManagementException e) {
          e.printStackTrace();
        }
      }

    } catch (AccountManagementException e) {
      DevLogger.log("error while fetching account");
      e.printStackTrace();
    }
    return "test/success";
  }

  /*
   * @RequestMapping(value = "/jms", method = RequestMethod.GET) public String
   * testJms() { try { queueSender.send(generateNetworkActivation(),
   * networkQueue); queueSender.send(generateKenanActivation(), kenanQueue); }
   * catch (JMSException e) { e.printStackTrace(); } return "test/success"; }
   * 
   * private NetworkActivation generateNetworkActivation() throws JMSException {
   * User user = new User(); user.setUsername("Super King"); NetworkInfo
   * networkInfo = new NetworkInfo(); networkInfo.setEsnmeiddec("01234567891");
   * networkInfo.setMdn("1234567"); networkInfo.setStatus("test case");
   * NetworkActivation truConnectActivation = new NetworkActivation();
   * truConnectActivation.setUser(user);
   * truConnectActivation.setNetworkInfo(networkInfo); return
   * truConnectActivation; }
   * 
   * private KenanServiceInstance generateKenanActivation() throws JMSException
   * { Account account = new Account(); account.setAccountno(123456);
   * account.setFirstname("Captain"); account.setLastname("Yesterday");
   * NetworkInfo networkInfo = new NetworkInfo();
   * networkInfo.setEsnmeiddec("01234567891"); networkInfo.setMdn("1234567");
   * networkInfo.setStatus("test case"); KenanServiceInstance kenanActivation =
   * new KenanServiceInstance(); kenanActivation.setAccount(account);
   * kenanActivation.setNetworkInfo(networkInfo); return kenanActivation; }
   */
}