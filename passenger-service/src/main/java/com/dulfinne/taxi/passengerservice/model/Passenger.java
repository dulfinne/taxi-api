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
import java.util.List;

@Entity
@Table(
    name = "passenger",
    indexes = {
      @Index(name = "idx_phone_number", columnList = "phone_number"),
      @Index(name = "idx_username", columnList = "username")
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Passenger {

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

  @Column(name = "phone_number", unique = true)
  private String phoneNumber;

  @Column(name = "payment", nullable = false)
  @Enumerated(EnumType.STRING)
  private Payment payment;

  @Column(name = "ride_count")
  private Integer rideCount;

  @Column(name = "sum_of_ratings")
  private Double sumOfRatings;

  @Column(name = "number_of_ratings")
  private Integer numberOfRatings;

  @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<PassengerRating> ratings;
}
