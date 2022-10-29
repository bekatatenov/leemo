package com.leemo.leemo.entity;

import com.leemo.leemo.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity(name = "Payment")
    public class Withdrawal {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "withdrawal_sum")
        private BigDecimal payment;

        @Enumerated(EnumType.STRING)
        @Column(name = "withdrawal-status")
        private PaymentStatus status;
}
