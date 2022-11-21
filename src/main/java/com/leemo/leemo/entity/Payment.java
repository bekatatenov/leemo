package com.leemo.leemo.entity;

import com.leemo.leemo.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Balance balance;

    @Column
    private Date createdDate;

}
