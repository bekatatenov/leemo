package com.leemo.leemo.controller;

import com.leemo.leemo.dtos.PaymentDto;
import com.leemo.leemo.dtos.WithdrawalDto;
import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Payment;
import com.leemo.leemo.entity.Users;
import com.leemo.leemo.enums.PaymentStatus;
import com.leemo.leemo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    BalanceService balanceService;
    @Autowired
    UserService userService;
    @Autowired
    WithdrawalService withdrawalService;
    @Autowired
    TaskService taskService;


//    @GetMapping(value = "/pay")
//    public ResponseEntity<Boolean> pay(@RequestBody PaymentDto dto) {
//        boolean validUser = this.userService.chekUser(dto.getEmail());
//        if (validUser) {
//            Balance b = dto.getBalance();
//            boolean validRequisite = this.balanceService.chekBalance(b.getId());
//            if (validRequisite) {
//                this.paymentService.paymentToBalance(dto, b.getId());
//                return new ResponseEntity<>(true, HttpStatus.OK);
//            } else return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
//        } else return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
//    }

    @PostMapping("/pay-cash")
    public ModelAndView pay(@RequestBody Payment payment){
        ModelAndView modelAndView = new ModelAndView("payment");
        paymentService.paymentToBalance(payment);
        modelAndView.addObject("paymentToBalance", payment);
        return modelAndView;
    }

    @RequestMapping(value = "/newPay", method = RequestMethod.GET)
    public ModelAndView newPay() {
        ModelAndView modelAndView = new ModelAndView("payment");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userService.findByMail(authentication.getName());
        Payment payment = new Payment();
        payment.setBid(users.getBalance().getId());
        modelAndView.addObject("payment", new Payment());
        return modelAndView;

    }


    @PostMapping("/PayToSite")
    public String addNewPayment(@ModelAttribute("payment") Payment payment) {
        paymentService.paymentToBalance(payment);
        return "redirect:mainpage";
    }



//    @GetMapping(value = "/get-payments-by-period")
//    public ModelAndView getPaymentsByPeriod(@RequestParam(name = "fromDate") Date fromDate,
//                                            @RequestParam(name = "toDate") Date toDate) {
//        ModelAndView modelAndView = new ModelAndView("payments");
//        modelAndView.addObject("payments", this.paymentService.findAllByPeriod(fromDate, toDate));
//        return modelAndView;
//    }

    @PostMapping(value = "pay-for-work")
    public ResponseEntity<Boolean> payForWork(@RequestParam(name = "taskId") Long taskId, @RequestParam(name = "executorId")Long executorId){
        boolean validTask = this.taskService.checkTask(taskId);
        if (validTask){
            this.taskService.payForDoneTask(taskId,executorId);
            return  new ResponseEntity<>(true,HttpStatus.ACCEPTED);
        }
        else return new ResponseEntity<>(false,HttpStatus.FORBIDDEN);
    }


}

