package com.trc.manager;

import java.io.Serializable;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trc.dao.UserDao;
import com.trc.exception.management.AccountManagementException;
import com.trc.user.Admin;
import com.trc.user.AnonymousUser;
import com.trc.user.User;
import com.trc.user.authority.Authority;
import com.trc.user.security.SecurityQuestion;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.context.SecurityContextFacade;
import com.trc.web.session.SessionManager;
import com.tscp.mvne.CustInfo;

@Service
public class UserManager implements UserManagerModel {
  public static final String USER_KEY = "user";
  public static final String ADMIN_KEY = "admin";
  public static final String MANAGER_KEY = "manager";
  private static SecurityContextFacade securityContext;
  private UserDao userDao;
  private SecurityQuestionManager securityQuestionManager;
  private AccountManager accountManager;

  @Autowired
  public void init(UserDao userDao, SecurityQuestionManager securityQuestionManager, AccountManager accountManager,
      SecurityContextFacade securityContextFacade) {
    this.userDao = userDao;
    this.securityQuestionManager = securityQuestionManager;
    this.accountManager = accountManager;
    securityContext = securityContextFacade;
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAllAdmins() {
    return userDao.getAllUsersWithRole("ROLE_ADMIN");
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAllManagers() {
    return userDao.getAllUsersWithRole("ROLE_MANAGER");
  }

  @Override
  public User getUserByEmail(String email) {
    return userDao.getUserByEmail(email);
  }

  @Override
  @Transactional(readOnly = true)
  public User getUserByUsername(String username) {
    return userDao.getUserByUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public User getUserById(int id) {
    return userDao.getUserById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> searchByEmail(String email) {
    return userDao.searchByEmail(email);
  }

  @Transactional(readOnly = true)
  public List<User> searchByEmailAndDate(String email, DateTime startDate, DateTime endDate) {
    return userDao.searchByEmailAndDate(email, startDate, endDate);
  }

  @Transactional(readOnly = true)
  public List<User> searchByNotEmailAndDate(String email, DateTime startDate, DateTime endDate) {
    return userDao.searchByNotEmailAndDate(email, startDate, endDate);
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> searchByUsername(String username) {
    return userDao.searchByUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> search(String param) {
    return userDao.search(param);
  }

  @Override
  public User getLoggedInUser() {
    Authentication authentication = getAuthentication();
    if (authentication == null || isAnonymousUser(authentication)) {
      return new AnonymousUser();
    } else {
      return (User) authentication.getPrincipal();
    }
  }

  private boolean isAnonymousUser(Authentication authentication) {
    return authentication.getName().equals("anonymousUser");
  }

  @Override
  public User getCurrentUser() {
    return getSessionUser();
  }

  @Override
  @Transactional(readOnly = false)
  public Serializable saveUser(User user) {
    if (user.getAuthorities().isEmpty()) {
      user.getRoles().add(new Authority(user, "ROLE_USER"));
    }
    return userDao.saveUser(user);
  }

  public void saveAdminSql(User user) {
    userDao.saveAdminSql(user);
  }

  public void saveAdminHql(Admin admin) {
    userDao.saveAdminHql(admin);
  }

  @Override
  @Transactional
  public void saveOrUpdateUser(User user) {
    userDao.saveOrUpdateUser(user);
  }

  @Override
  @Transactional
  public void persistUser(User user) {
    userDao.persistUser(user);
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteUser(User user) {
    userDao.deleteUser(user);
  }

  @Override
  @Transactional(readOnly = false)
  public void updateUser(User user) {
    userDao.updateUser(user);
  }

  @Override
  @Transactional(readOnly = false)
  public void enableUser(User user) {
    userDao.enableUser(user);
  }

  @Override
  @Transactional(readOnly = false)
  public void disableUser(User user) {
    userDao.disableUser(user);
  }

  @Override
  public boolean isUsernameAvailable(String username) {
    return getUserByUsername(username) == null;
  }

  @Override
  public boolean isEmailAvailable(String email) {
    return getUserByEmail(email) == null;
  }

  public User getSessionUser() {
    User user = (User) SessionManager.get(USER_KEY);
    return user == null ? new AnonymousUser() : user;
  }

  public User getSessionAdmin() {
    return (User) SessionManager.get(ADMIN_KEY);
  }

  public User getSessionManager() {
    return (User) SessionManager.get(MANAGER_KEY);
  }

  public void setSessionUser(User user) {
    SessionManager.set(USER_KEY, user);
  }

  public void setSessionAdmin(User user) {
    SessionManager.set(ADMIN_KEY, user);
  }

  public void setSessionManager(User user) {
    SessionManager.set(MANAGER_KEY, user);
  }

  private Authentication getAuthentication() {
    return securityContext.getContext().getAuthentication();
  }

  @Deprecated
  public List<SecurityQuestion> getSecurityQuestions() {
    return securityQuestionManager.getSecurityQuestions();
  }

  @Deprecated
  public SecurityQuestion getSecurityQuestion(int id) {
    return securityQuestionManager.getSecurityQuestion(id);
  }

  @Loggable(value = LogLevel.TRACE)
  public void getUserRealName(User user) {
    if (user.isAdmin()) {
      user.getContactInfo().setFirstName(user.getUsername());
      user.getContactInfo().setLastName("Administrator");
    } else if (user.isManager()) {
      user.getContactInfo().setFirstName(user.getUsername());
      user.getContactInfo().setLastName("Manager");
    } else if (user.isUser()) {
      try {
        CustInfo custInfo = accountManager.getCustInfo(user);
        user.getContactInfo().setFirstName(custInfo.getFirstName());
        user.getContactInfo().setLastName(custInfo.getLastName());
      } catch (AccountManagementException e) {
        user.getContactInfo().setFirstName(user.getUsername());
      }
      // try {
      // List<Account> accountList = accountManager.getAccounts(user);
      // Account account = accountList.get(0);
      // user.getContactInfo().setFirstName(account.getFirstname());
      // user.getContactInfo().setLastName(account.getLastname());
      // } catch (AccountManagementException e) {
      // user.getContactInfo().setFirstName(user.getUsername());
      // }
    } else {
      user.getContactInfo().setFirstName(user.getUsername());
    }
  }

}