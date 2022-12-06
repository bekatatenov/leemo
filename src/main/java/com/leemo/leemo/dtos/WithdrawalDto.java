package com.leemo.leemo.dtos;

import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class WithdrawalDto {
    BigDecimal amount;
    PaymentStatus status;
    Balance balance;
    String email;
    Date createdDate;
    String requisite;
}
