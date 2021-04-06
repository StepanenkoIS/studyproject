package ru.credit.calculator.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Credits")
@XmlAccessorType(XmlAccessType.NONE)
public class Credits {
  @XmlElement(name = "Credit")
  List<Credit> credits = null;

  public List<Credit> getCredits() {
    return credits;
  }

  public Credits setCredits(List<Credit> credits) {
    this.credits = credits;
    return this;
  }
}
