package com.trc.dao;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trc.user.Admin;
import com.trc.user.User;
import com.trc.user.authority.Authority;

@Repository
@SuppressWarnings("unchecked")
public class UserDao extends HibernateDaoSupport implements UserDaoModel {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

  private boolean isUniqueResult(Collection<User> results) {
    return results != null && results.size() == 1;
  }

  private User getUniqueResult(List<User> results) {
    return results.get(0);
  }

  private String wildcard(String param) {
    return "%" + param + "%";
  }

  @Override
  public List<User> getAllUsers() {
    return getHibernateTemplate().find("from User");
  }

  @Override
  public List<User> getAllUsersWithRole(String role) {
    DetachedCriteria subCriteria = DetachedCriteria.forClass(Authority.class);
    subCriteria.add(Property.forName("authority").eq(role));
    subCriteria.setProjection(Projections.property("user"));
    return getHibernateTemplate().findByCriteria(subCriteria);
  }

  @Override
  public User getUserByUsername(String username) {
    List<User> results = getHibernateTemplate().find("from User user where username = ?", username);
    if (isUniqueResult(results)) {
      return getUniqueResult(results);
    } else {
      return null;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public User getUserByEmail(String email) {
    List<User> results = getHibernateTemplate().find("from User user where email = ?", email);
    if (isUniqueResult(results)) {
      return getUniqueResult(results);
    } else {
      return null;
    }
  }

  @Override
  public List<User> search(String param) {
    List<User> results = getHibernateTemplate().find("from User user where username like ? or email like ?",
        wildcard(param), wildcard(param));
    return results;
  }

  @Override
  public List<User> searchByEmail(String email) {
    List<User> results = getHibernateTemplate().find("from User user where email like ?", wildcard(email));
    return results;
  }

  public List<User> searchByEmailAndDate(String email, DateTime startDate, DateTime endDate) {
    endDate.plusDays(1);
    Date sqlStartDate = new Date(startDate.getMillis());
    Date sqlEndDate = new Date(endDate.getMillis());
    List<User> results = getHibernateTemplate().find(
        "from User user where email like ? and dateEnabled between ? and ?", wildcard(email), sqlStartDate, sqlEndDate);
    return results;
  }

  public List<User> searchByNotEmailAndDate(String email, DateTime startDate, DateTime endDate) {
    endDate.plusDays(1);
    Date sqlStartDate = new Date(startDate.getMillis());
    Date sqlEndDate = new Date(endDate.getMillis());
    List<User> results = getHibernateTemplate().find(
        "from User user where email not like ? and dateEnabled between ? and ?", wildcard(email), sqlStartDate,
        sqlEndDate);
    return results;
  }

  public List<User> getUsersByDate(Date startDate, Date endDate) {
    java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
    java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
    List<User> results = getHibernateTemplate().find("from User user where dateEnabled between ? and ?", sqlStartDate,
        sqlEndDate);
    return results;
  }

  public List<User> searchByEmailCriteria(String email) {
    DetachedCriteria authCriteria = DetachedCriteria.forClass(Authority.class);
    authCriteria.add(Restrictions.eq("authority", "ROLE_USER"));
    authCriteria.setProjection(Projections.property("user.userId"));

    DetachedCriteria userCriteria = DetachedCriteria.forClass(User.class);
    userCriteria.add(Property.forName("email").like(email, MatchMode.ANYWHERE));
    userCriteria.add(Property.forName("userId").in(authCriteria));

    List<User> results = getHibernateTemplate().findByCriteria(userCriteria);
    return results;
  }

  @Override
  public List<User> searchByUsername(String username) {
    List<User> results = getHibernateTemplate().find("from User user where username like ?", wildcard(username));
    return results;
  }

  @Override
  public User getUserById(int id) {
    return getHibernateTemplate().get(User.class, id);
  }

  @Override
  public void deleteUser(User user) {
    getHibernateTemplate().delete(user);
  }

  @Override
  public void updateUser(User user) {
    getHibernateTemplate().update(user);
  }

  @Override
  public Serializable saveUser(User user) {
    return getHibernateTemplate().save(user);
  }

  public void saveAdminSql(User user) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String sDateEnabled = dateFormat.format(user.getDateEnabled());
    String sDateDisabled;
    if (user.getDateDisabled() != null) {
      sDateDisabled = dateFormat.format((user.getDateDisabled()));
    } else {
      sDateDisabled = "null";
    }

    String columns = "user_id, username, password, email, hint_id, hint_answer, enabled, date_enabled, date_disabled";
    String values = user.getUserId() + ", '" + user.getUsername() + "', '" + user.getPassword() + "', '"
        + user.getEmail() + "', " + user.getUserHint().getHintId() + ", '" + user.getUserHint().getHintAnswer() + "', "
        + (user.isEnabled() ? 1 : 0) + ", '" + sDateEnabled + "', " + sDateDisabled;

    final String sql = "insert into users (" + columns + ") values (" + values + ")";

    Long count = (Long) getHibernateTemplate().execute(new HibernateCallback<Object>() {
      public Object doInHibernate(Session session) throws HibernateException {
        SQLQuery query = session.createSQLQuery(sql);
        long value = query.executeUpdate();
        return Long.valueOf(value);
      }
    });
  }

  public void saveAdminHql(Admin admin) {
    getHibernateTemplate().save(admin);
  }

  @Override
  public void saveOrUpdateUser(User user) {
    getHibernateTemplate().saveOrUpdate(user);
  }

  @Override
  public void persistUser(User user) {
    getHibernateTemplate().persist(user);
  }

  @Override
  public void enableUser(User user) {
    user.setEnabled(true);
    user.setDateEnabled(new DateTime().toDate());
    updateUser(user);
  }

  @Override
  public void disableUser(User user) {
    user.setEnabled(false);
    user.setDateDisabled(new DateTime().toDate());
    updateUser(user);
  }
}