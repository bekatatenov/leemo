package com.leemo.leemo.entity;

import com.leemo.leemo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity(name = "USERS")
    public class Users {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "user_name")
        private String name;

        @Column(name = "user_last_name")
        private String lastName;

        @Column(name = "user_email")
        private String email;

        @Column(name = "user_password")
        private String password;

        @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
        @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
        private List<Role> roles;

        @Column(name = "Birth_Date")
        private Date birthday;

        @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
        @JoinTable(name = "User_balance", joinColumns = @JoinColumn(name = "user_balance"), inverseJoinColumns =  @JoinColumn(name = "balance_id"))
        private Balance balance;

        @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
        @JoinTable(name = "user_tasks", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tasks_id"))
        private List<Tasks> tasks;

        @Enumerated(EnumType.STRING)
        @Column(name = "user_status")
        private Status status;

        @Column(name = "created_date")
        private Date date;
    }

