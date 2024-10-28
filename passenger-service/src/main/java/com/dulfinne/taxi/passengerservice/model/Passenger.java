package com.dulfinne.taxi.passengerservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "passenger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Passenger {
    @Transient
    private static final String CURRENT_TIMEZONE = "Europe/Minsk";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    @ToString.Exclude
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @OneToOne(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private PassengerInfo info;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PassengerRating> ratings;

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now(ZoneId.of(CURRENT_TIMEZONE));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Passenger passenger = (Passenger) o;
        return getId() != null && Objects.equals(getId(), passenger.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
