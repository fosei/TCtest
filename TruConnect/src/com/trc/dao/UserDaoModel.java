package com.trc.dao;

import java.io.Serializable;
import java.util.Collection;

import com.trc.user.User;

public interface UserDaoModel {

  public Collection<User> getAllUsers();

  public Collection<User> getAllUsersWithRole(String role);

  public User getUserByUsername(String username);

  public User getUserByEmail(String email);

  public User getUserById(int id);

  public Collection<User> search(String param);

  public Collection<User> searchByEmail(String email);

  public Collection<User> searchByUsername(String username);

  public void deleteUser(User user);

  public void updateUser(User user);

  public Serializable saveUser(User user);

  public void saveOrUpdateUser(User user);

  public void persistUser(User user);

  public void enableUser(User user);

  public void disableUser(User user);

}
