package com.leemo.leemo.entity;

import com.leemo.leemo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity(name = "Balance")

    public class Balance {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "user_balance")
        private BigDecimal balance;

        @Enumerated(EnumType.STRING)
        @Column(name = "balance_status")
        private Status status;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "balance_payments", joinColumns = @JoinColumn(name = "balance_id"), inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private List<Payment> payments;

}
