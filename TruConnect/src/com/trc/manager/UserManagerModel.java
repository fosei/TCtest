package com.trc.manager;

import java.io.Serializable;
import java.util.Collection;

import com.trc.user.User;

public interface UserManagerModel {

  public Collection<User> getAllUsers();

  public User getUserByEmail(String email);

  public User getUserByUsername(String username);

  public User getUserById(int id);

  public Collection<User> getAllAdmins();

  public Collection<User> getAllManagers();

  public Collection<User> searchByEmail(String email);

  public Collection<User> searchByUsername(String username);

  public Collection<User> search(String param);

  public User getLoggedInUser();

  public User getCurrentUser();

  public Serializable saveUser(User user);

  public void saveOrUpdateUser(User user);

  public void persistUser(User user);

  public void deleteUser(User user);

  public void updateUser(User user);

  public void enableUser(User user);

  public void disableUser(User user);

  public boolean isUsernameAvailable(String username);

  public boolean isEmailAvailable(String email);

}
