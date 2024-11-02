package com.dulfinne.taxi.passengerservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@Table(
    name = "passenger_info",
    indexes = {@Index(name = "idx_phone_number", columnList = "phone_number")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PassengerInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "passenger_id", unique = true, nullable = false)
  private Long passengerId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "phone_number", unique = true)
  private String phoneNumber;

  @Column(name = "payment", nullable = false)
  @Enumerated(EnumType.STRING)
  private Payment payment;

  @Column(name = "ride_count")
  private Integer rideCount;

  @Column(name = "average_rating")
  private Double averageRating;

  @OneToMany(mappedBy = "passengerInfo", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<PassengerRating> ratings;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    PassengerInfo passengerInfo = (PassengerInfo) o;
    return getId() != null && Objects.equals(getId(), passengerInfo.getId());
  }

  @Override
  public int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
