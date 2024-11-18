package com.dulfinne.taxi.passengerservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "passenger_rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PassengerRating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "passenger_id", referencedColumnName = "id")
  @ToString.Exclude
  private Passenger passenger;

  @Column(name = "rating", nullable = false)
  private Integer rating;

  @Column(name = "feedback")
  private String feedback;
}
