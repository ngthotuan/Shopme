package com.shopme.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(length = 45, nullable = false)
    private String firstName;

    @Column(length = 45, nullable = false)
    private String lastName;

    @Column(length = 15, nullable = false)
    private String phoneNumber;

    @Column(length = 64, nullable = false)
    private String addressLine1;

    @Column(length = 64)
    private String addressLine2;

    @Column(length = 64, nullable = false)
    private String city;

    @Column(length = 45, nullable = false)
    private String state;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(length = 10, nullable = false)
    private String postalCode;

    private Date createdTime;

    private boolean enabled = false;

    @Column(length = 64)
    private String verificationCode;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AuthenticationType authenticationType;

    @Transient
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}

