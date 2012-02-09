package com.trc.user;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.trc.user.authority.Authority;
import com.trc.user.contact.ContactInfo;

@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements UserModel, UserDetails {
  private static final long serialVersionUID = 1L;
  private int userId;
  private String username;
  private String password;
  private String email;
  private Date dateEnabled;
  private Date dateDisabled;
  private boolean enabled;
  private SecurityQuestionAnswer userHint = new SecurityQuestionAnswer();
  private Collection<Authority> authorities = new HashSet<Authority>();
  private ContactInfo contactInfo = new ContactInfo();

  @Override
  @Transient
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("userId=").append(userId).append(", ");
    sb.append("username=").append(username).append(", ");
    sb.append("password=").append(password).append(", ");
    sb.append("email=").append(email).append(", ");
    sb.append("dateEnabled=").append(dateEnabled).append(", ");
    sb.append("dateDisabled=").append(dateDisabled).append(", ");
    sb.append("enabled=").append(enabled).append(", ");
    sb.append("userHint={").append(userHint.toString()).append("}, ");
    sb.append("authorities={").append(authorities.toString()).append("}, ");
    return sb.toString();
  }

  @Transient
  public String toFormattedString() {
    StringBuffer sb = new StringBuffer();
    sb.append("--User--").append("\n");
    sb.append("  User ID=").append(userId).append("\n");
    sb.append("  Username=").append(username).append("\n");
    sb.append("  Password=").append(password).append("\n");
    sb.append("  Email=").append(email).append("\n");
    sb.append("  Date Enabled=").append(dateEnabled).append("\n");
    sb.append("  Date Disabled=").append(dateDisabled).append("\n");
    sb.append("  Enabled=").append(enabled).append("\n");
    sb.append("  User Hint={").append(userHint.toString()).append("\n");
    sb.append("  Authorities={").append(authorities.toString());
    return sb.toString();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  public int getUserId() {
    return this.userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  @Column(name = "username")
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  @Column(name = "password")
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "email")
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Transient
  public ContactInfo getContactInfo() {
    return contactInfo;
  }

  public void setContactInfo(ContactInfo contactInfo) {
    this.contactInfo = contactInfo;
  }

  @Column(name = "enabled", columnDefinition = "BOOLEAN")
  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Column(name = "date_enabled")
  public Date getDateEnabled() {
    return this.dateEnabled;
  }

  public void setDateEnabled(Date dateEnabled) {
    this.dateEnabled = dateEnabled;
  }

  @Column(name = "date_disabled")
  public Date getDateDisabled() {
    return this.dateDisabled;
  }

  public void setDateDisabled(Date dateDisabled) {
    this.dateDisabled = dateDisabled;
  }

  public SecurityQuestionAnswer getUserHint() {
    return this.userHint;
  }

  public void setUserHint(SecurityQuestionAnswer userHint) {
    this.userHint = userHint;
  }

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  @Cascade({ CascadeType.ALL })
  public Collection<Authority> getRoles() {
    return this.authorities;
  }

  public void setRoles(Collection<Authority> roles) {
    this.authorities = roles;
  }

  /*****************************************************************************
   * Begins Spring UserDetails implementation methods
   *****************************************************************************/
  // TODO User class should not implement UserDetails, instead we should create
  // another class that uses Assembler to keep our design separate from Spring

  @Transient
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
    for (Authority auth : getRoles()) {
      grantedAuthorities.add(new GrantedAuthorityImpl(auth.getAuthority()));
    }
    return grantedAuthorities;
  }

  @Transient
  @Override
  public boolean isAccountNonExpired() {
    return isEnabled();
  }

  @Transient
  @Override
  public boolean isAccountNonLocked() {
    return isEnabled();
  }

  @Transient
  @Override
  public boolean isCredentialsNonExpired() {
    return isEnabled();
  }

  @Transient
  public boolean isAdmin() {
    GrantedAuthority ga = new GrantedAuthorityImpl("ROLE_ADMIN");
    return getAuthorities().contains(ga);
  }

  @Transient
  public boolean isManager() {
    GrantedAuthority ga = new GrantedAuthorityImpl("ROLE_MANAGER");
    return getAuthorities().contains(ga);
  }

  @Transient
  public boolean isUser() {
    GrantedAuthority ga = new GrantedAuthorityImpl("ROLE_USER");
    return getAuthorities().contains(ga);
  }

  @Transient
  public boolean isAuthenticated() {
    GrantedAuthority ga = new GrantedAuthorityImpl("ROLE_ANONYMOUS");
    return !getAuthorities().contains(ga);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }

}