package com.trc.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.manager.AccountManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.account.Overview;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/account")
public class AccountController extends EncryptedController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private AccountManager accountManager;

  @Loggable(value = LogLevel.TRACE)
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView showOverview(HttpSession session) {
    ResultModel model = new ResultModel("account/overview");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user);
    encodeAccountNums(overview.getAccountDetails());
    model.addObject("accountDetails", overview.getAccountDetails());
    model.addObject("paymentHistory", overview.getPaymentDetails());
    return model.getSuccess();
  }

  @RequestMapping(value = "/activity", method = RequestMethod.GET)
  public ModelAndView showActivity(HttpSession session) {
    ResultModel model = new ResultModel("account/activity");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user);

    encodeAccountNums(overview.getAccountDetails());

    int numAccounts = overview.getAccountDetails().size();
    List<AccountDetail> accountList = overview.getAccountDetails();
    List<AccountDetail> firstAccount = numAccounts > 0 ? overview.getAccountDetails().subList(0, 1)
        : new ArrayList<AccountDetail>();

    model.addObject("numAccounts", numAccounts);
    model.addObject("accountList", accountList);
    model.addObject("accountDetails", firstAccount);
    model.addObject("encodedAccountNumber", firstAccount.get(0).getEncodedAccountNum());
    return model.getSuccess();
  }

  @RequestMapping(value = "/activity/{encodedAccountNum}", method = RequestMethod.GET)
  public String showAccountActivity(@PathVariable("encodedAccountNum") String encodedAccountNum) {
    return "redirect:/account/activity/" + encodedAccountNum + "/1";
  }

  @RequestMapping(value = "/activity/{encodedAccountNum}/{page}", method = RequestMethod.GET)
  public ModelAndView showAccountActivity(HttpSession session,
      @PathVariable("encodedAccountNum") String encodedAccountNum, @PathVariable("page") int page) {
    ResultModel model = new ResultModel("account/activity");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user);

    int numAccounts = overview.getAccountDetails().size();
    int accountNum = super.decryptId(encodedAccountNum);

    encodeAccountNums(overview.getAccountDetails());

    List<AccountDetail> accountList = overview.getAccountDetails();
    overview.getAccountDetail(accountNum).getUsageHistory().setCurrentPageNum(page);
    List<AccountDetail> accountDetails = new ArrayList<AccountDetail>();
    accountDetails.add(overview.getAccountDetail(accountNum));

    model.addObject("numAccounts", numAccounts);
    model.addObject("accountList", accountList);
    model.addObject("accountDetails", accountDetails);
    model.addObject("encodedAccountNumber", encodedAccountNum);
    return model.getSuccess();
  }

  private void encodeAccountNums(List<AccountDetail> accountDetailList) {
    for (AccountDetail accountDetail : accountDetailList) {
      accountDetail.setEncodedAccountNum(super.encryptId(accountDetail.getAccount().getAccountno()));
    }
  }

}