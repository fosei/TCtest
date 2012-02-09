package com.trc.util.logger.activation;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "activation_state")
public class ActivationState {
  private ActivationStateId activationStateId = new ActivationStateId();
  private Date dateIn;
  private Date dateOut;
  private ActivationState parent;
  private ActState parentState;
  private Collection<ActivationState> children = new HashSet<ActivationState>();

  private ActivationStateId parentStateId = new ActivationStateId();

  public ActivationState() {
    // do nothing
  }

  public ActivationState(ActState actState, ActivationMap actMap) {
    getActivationStateId().setActivationMap(actMap);
    getActivationStateId().setActState(actState);
    setDateIn(new Date());
    getParentStateId();
  }

  /* **************************************************************
   * Getters / Setters
   * ***************************************************************
   */

  @EmbeddedId
  public ActivationStateId getActivationStateId() {
    return activationStateId;
  }

  public void setActivationStateId(ActivationStateId activationStateId) {
    this.activationStateId = activationStateId;
  }

  @Column(name = "date_in")
  public Date getDateIn() {
    return dateIn;
  }

  public void setDateIn(Date dateIn) {
    this.dateIn = dateIn;
  }

  @Column(name = "date_out")
  public Date getDateOut() {
    return dateOut;
  }

  public void setDateOut(Date dateOut) {
    this.dateOut = dateOut;
  }

  @Column(name = "parent_state")
  @Enumerated(EnumType.STRING)
  public ActState getParentState() {
    return parentState;
  }

  public void setParentState(ActState parentState) {
    if (parentState != null) {
      this.parentState = parentState;
    } else {
      this.parentState = null;
    }
  }

  public void setParentState(ActivationState parentState) {
    if (parentState != null) {
      this.parentState = parentState.getActivationStateId().getActState();
    } else {
      this.parentState = null;
    }
  }

  @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumns({ @JoinColumn(name = "act_id", referencedColumnName = "act_id", insertable = false, updatable = false),
      @JoinColumn(name = "parent_state", referencedColumnName = "state", insertable = false, updatable = false) })
  public ActivationState getParent() {
    return parent;
  }

  public void setParent(ActivationState parent) {
    this.parent = parent;
  }

  @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  public Collection<ActivationState> getChildren() {
    return children;
  }

  public void setChildren(Collection<ActivationState> children) {
    this.children = children;
  }

  /* **************************************************************
   * Helper Methods
   * ***************************************************************
   */

  @Transient
  public ActState getState() {
    return getActivationStateId().getActState();
  }

  @Transient
  public ActivationMap getActivationMap() {
    return getActivationStateId().getActivationMap();
  }

  @Transient
  public ActivationStateId getParentStateId() {
    parentStateId.setActivationMap(getActivationMap());
    parentStateId.setActState(parentState);
    return parentStateId;
  }

  @Transient
  public long getTimeSpent() {
    long timespent = (long) 0;
    if (dateIn != null && dateOut != null) {
      if (dateOut != null) {
        timespent = (dateOut.getTime() - dateIn.getTime()) / 1000;
      }
    }
    return timespent;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((activationStateId == null) ? 0 : activationStateId.hashCode());
    result = prime * result + ((children == null) ? 0 : children.hashCode());
    result = prime * result + ((dateIn == null) ? 0 : dateIn.hashCode());
    result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
    result = prime * result + ((parent == null) ? 0 : parent.hashCode());
    result = prime * result + ((parentState == null) ? 0 : parentState.hashCode());
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
    ActivationState other = (ActivationState) obj;
    if (activationStateId == null) {
      if (other.activationStateId != null)
        return false;
    } else if (!activationStateId.equals(other.activationStateId))
      return false;
    if (children == null) {
      if (other.children != null)
        return false;
    } else if (!children.equals(other.children))
      return false;
    if (dateIn == null) {
      if (other.dateIn != null)
        return false;
    } else if (!dateIn.equals(other.dateIn))
      return false;
    if (dateOut == null) {
      if (other.dateOut != null)
        return false;
    } else if (!dateOut.equals(other.dateOut))
      return false;
    if (parent == null) {
      if (other.parent != null)
        return false;
    } else if (!parent.equals(other.parent))
      return false;
    if (parentState != other.parentState)
      return false;
    return true;
  }

}
