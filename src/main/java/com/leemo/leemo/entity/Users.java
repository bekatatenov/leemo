package com.leemo.leemo.entity;

import com.leemo.leemo.enums.Roles;
import com.leemo.leemo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Balance balance;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_date")
    private Date createdDate;

    @Enumerated(EnumType.STRING)
    private Roles role;

}

