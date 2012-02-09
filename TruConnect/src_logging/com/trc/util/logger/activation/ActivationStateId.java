package com.trc.util.logger.activation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ActivationStateId implements Serializable {
  private static final long serialVersionUID = 1L;
  private ActState actState;
  private ActivationMap activationMap;

  public ActivationStateId() {
    // do nothing
  }

  public ActivationStateId(ActivationMap activationMap, ActState actState) {
    this.actState = actState;
    this.activationMap = activationMap;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "act_id")
  public ActivationMap getActivationMap() {
    return activationMap;
  }

  public void setActivationMap(ActivationMap activationMap) {
    this.activationMap = activationMap;
  }

  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  public ActState getActState() {
    return actState;
  }

  public void setActState(ActState actState) {
    this.actState = actState;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((activationMap == null) ? 0 : activationMap.hashCode());
    result = prime * result + ((actState == null) ? 0 : actState.hashCode());
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
    ActivationStateId other = (ActivationStateId) obj;
    if (activationMap == null) {
      if (other.activationMap != null)
        return false;
    } else if (!activationMap.equals(other.activationMap))
      return false;
    if (actState != other.actState)
      return false;
    return true;
  }

}
