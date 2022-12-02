package com.leemo.leemo.dtos;

import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.enums.PaymentStatus;


import java.math.BigDecimal;
import java.util.Date;

public class WithdrawalDto {
    BigDecimal amount;
    Balance balance;
    PaymentStatus status;
    Date createdDate;

}
