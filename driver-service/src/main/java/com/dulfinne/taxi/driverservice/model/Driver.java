package com.dulfinne.taxi.driverservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(
    name = "driver",
    indexes = {
      @Index(name = "idx_phone_number", columnList = "phone_number"),
      @Index(name = "idx_username", columnList = "username")
    })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Driver {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "phone_number", nullable = false, unique = true)
  private String phoneNumber;

  @Column(name = "experience")
  private Integer experience;

  @Column(name = "sum_of_ratings")
  private Double sumOfRatings;

  @Column(name = "number_of_ratings")
  private Integer numberOfRatings;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "car_id", referencedColumnName = "id")
  @ToString.Exclude
  private Car car;

  @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<DriverRating> ratings;
}
