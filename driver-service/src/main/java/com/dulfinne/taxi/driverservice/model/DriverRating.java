package com.dulfinne.taxi.driverservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "driver_rating")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DriverRating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "rating", nullable = false)
  private Integer rating;

  @Column(name = "feedback")
  private String feedback;

  @ManyToOne
  @JoinColumn(name = "driver_id", referencedColumnName = "id")
  @ToString.Exclude
  private Driver driver;
}
