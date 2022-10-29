package com.leemo.leemo.entity;

import com.leemo.leemo.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "ROLES")
    public class Role {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)

        @Enumerated(EnumType.STRING)
        @Column(name = "user_role")
        private Roles roles;

        @Column(name = "user_id")
        private Long id;

    }

