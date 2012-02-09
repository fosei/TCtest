package com.trc.util.logger.activation;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.trc.user.User;

@Entity
@Table(name = "activation_map")
public class ActivationMap {
  private User user;
  private int activationId;
  private Collection<ActivationState> states = new HashSet<ActivationState>();

  public ActivationMap() {
    // do nothing
  }

  public ActivationMap(User user) {
    setUser(user);
  }

  /* **************************************************************
   * Getters / Setters
   * **************************************************************
   */

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "act_id")
  public int getActivationId() {
    return activationId;
  }

  public void setActivationId(int activationId) {
    this.activationId = activationId;
  }

  @OneToOne
  @JoinColumn(name = "user_id")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "act_id")
  public Collection<ActivationState> getStates() {
    return states;
  }

  public void setStates(Collection<ActivationState> states) {
    this.states = states;
  }

  @Transient
  public ActivationState getRootState() {
    for (ActivationState aState : getStates()) {
      if (aState.getState().equals(ActState.ROOT))
        return aState;
    }
    return null;
  }

  @Override
  public String toString() {
    return "ActivationMap [user=" + user.getUserId() + ", activationId=" + activationId + ", states=" + states.size()
        + "]";
  }

}