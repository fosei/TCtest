package com.trc.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trc.coupon.Coupon;
import com.trc.coupon.ajax.CouponResponse;
import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.CouponManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.CouponManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.account.Overview;
import com.trc.util.logger.DevLogger;
import com.trc.web.model.ResultModel;
import com.trc.web.validation.CouponValidator;
import com.tscp.mvne.Account;
import com.tscp.mvne.ServiceInstance;

@Controller
@RequestMapping("/coupons")
public class CouponController extends EncryptedController {
  @Autowired
  private CouponManager couponManager;
  @Autowired
  private UserManager userManager;
  @Autowired
  private AccountManager accountManager;
  @Autowired
  private CouponValidator couponValidator;

  private void encodeAccountNums(List<AccountDetail> accountDetailList) {
    for (AccountDetail accountDetail : accountDetailList) {
      accountDetail.setEncodedAccountNum(super.encryptId(accountDetail.getAccount().getAccountno()));
    }
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
  @RequestMapping(value = "/validate", method = RequestMethod.GET)
  public @ResponseBody
  CouponResponse validateCoupon(@RequestParam String couponCode) {
    try {
      Coupon coupon = couponManager.getCouponByCode(couponCode);
      if (coupon != null) {
        if (coupon.getCouponDetail().getContract().getContractType() > 0) {
          return new CouponResponse(true, coupon.getCouponDetail().getContract().getDescription() + " for "
              + coupon.getCouponDetail().getDuration() + " months");
        } else {
          return new CouponResponse(true, "Credit value of $" + coupon.getCouponDetail().getAmount());
        }
      } else {
        return new CouponResponse(false, "Not a valid coupon");
      }
    } catch (CouponManagementException e) {
      return new CouponResponse(false, "Internal error, try again");
    }
  }

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView showRedeemCoupon() {
    ResultModel model = new ResultModel("coupon/addCoupon");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user);
    encodeAccountNums(overview.getAccountDetails());
    List<AccountDetail> accountList = overview.getAccountDetails();
    model.addObject("coupon", new Coupon());
    model.addObject("accountList", accountList);
    return model.getSuccess();
  }

  @RequestMapping(method = RequestMethod.POST)
  public ModelAndView postRedeemCoupon(HttpServletRequest request, @ModelAttribute("coupon") Coupon coupon,
      BindingResult result) {
    DevLogger.log("form submission caught, fetching coupon with code: " + coupon.getCouponCode());
    ResultModel model = new ResultModel("coupon/addCouponSuccess", "coupon/addCoupon");
    User user = userManager.getCurrentUser();
    String encodedAccountNumber = request.getParameter("account");
    DevLogger.log("selected radio button has value of: " + encodedAccountNumber);
    int accountNumber = 0;
    if (encodedAccountNumber != null) {
      accountNumber = super.decryptId(encodedAccountNumber);
      DevLogger.log("decoded radio button value is: " + accountNumber);
    }
    try {

      coupon = couponManager.getCouponByCode(coupon.getCouponCode());
      if (coupon != null) {
        DevLogger.log("received coupon: " + coupon.toFormattedString());
      }
      couponValidator.validate(coupon, accountNumber, result);
      if (result.hasErrors()) {
        List<AccountDetail> accountList = accountManager.getOverview(user).getAccountDetails();
        encodeAccountNums(accountList);
        model.addObject("accountList", accountList);
        return model.getError();
      } else {
        try {
          Account account = accountManager.getAccount(accountNumber);
          ServiceInstance serviceInstance = null;
          if (account != null && account.getServiceinstancelist() != null
              && account.getServiceinstancelist().size() > 0) {
            serviceInstance = account.getServiceinstancelist().get(0);
          }
          if (serviceInstance != null) {
            couponManager.applyCoupon(coupon, user, account, serviceInstance);
          } else {
            return model.getAccessException();
          }
        } catch (AccountManagementException e) {
          model.getAccessException();
        }
        List<AccountDetail> accountList = accountManager.getOverview(user).getAccountDetails();
        AccountDetail accountDetail = null;
        for (AccountDetail ad : accountList) {
          if (ad.getAccount().getAccountno() == accountNumber) {
            accountDetail = ad;
          }
        }
        model.addObject("accountDetail", accountDetail);
        model.addObject("coupon", coupon);
        return model.getSuccess();
      }
    } catch (CouponManagementException e) {
      return model.getAccessException();
    }
  }
}