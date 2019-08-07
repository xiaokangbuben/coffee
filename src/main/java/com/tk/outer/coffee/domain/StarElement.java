package com.tk.outer.coffee.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "star_element")
public class StarElement {

  private Integer id;
  private Integer starId;

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "idGenerator")
  @GenericGenerator(name = "idGenerator", strategy = "native")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Basic
  @Column(name = "star_id")
  public Integer getStarId() {
    return starId;
  }

  public void setStarId(Integer starId) {
    this.starId = starId;
  }
}
