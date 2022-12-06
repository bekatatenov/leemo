package com.leemo.leemo.controller;

import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Payment;

import com.leemo.leemo.entity.Withdrawal;
import com.leemo.leemo.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
public class BalanceHistoryController {
    @Autowired
    BalanceService balanceService;

    @RequestMapping(value = "/getBalance")
    public ModelAndView getBalance(@RequestParam(name = "id")Long id){
        ModelAndView modelAndView = new ModelAndView("balance");
        Balance balance = balanceService.getBalance(id);
        modelAndView.addObject(balance);
        return modelAndView;
    }

    @RequestMapping(value = "/paymentsHistory", method = RequestMethod.GET)
    public ModelAndView paymentsHistory(@RequestParam(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("paymentsHistory");
        List<Payment> paymentHistory = balanceService.getPaymentsHistory(id);
        modelAndView.addObject("paymentHistory", paymentHistory);
        return modelAndView;
    }

    @RequestMapping(value = "/withdrawalHistory", method = RequestMethod.GET)
    public ModelAndView withdrawalHistory(@RequestParam(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("withdrawalHistory");
        List<Withdrawal> withdrawalHistory = balanceService.getWithdrawalHistory(id);
        modelAndView.addObject("withdrawalHistory", withdrawalHistory);
        return modelAndView;
    }
}



//    @GetMapping(value = "/getRequisite")
//    public ResponseEntity<Boolean> checkRequisite(@RequestParam(name = "login") String login,
//                                                  @RequestParam(name = "password") String password,
//                                                  @RequestParam(name = "requisite") String requisite) {
//        RequestHistory requestHistory = new RequestHistory();
//        requestHistory.setRequestDate(new Date());
//        requestHistory.setRequestData(login + " " + password + " " + requisite);
//        requestHistory.setOperations(Operations.GET_REQUISITE);
//        boolean validate = this.clientService.checkClient(login, password);
//        if (validate) {
//            boolean validRequisite = this.requisiteService.checkRequisite(requisite);
//            if (validRequisite) {
//                requestHistory.setResponseData("TRUE");
//                this.requestHistoryService.save(requestHistory);
//                return new ResponseEntity<>(true, HttpStatus.OK);
//            } else {
//                requestHistory.setResponseData("FALSE " + HttpStatus.NOT_FOUND);
//                this.requestHistoryService.save(requestHistory);
//                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
//            }
//        } else {
//            requestHistory.setResponseData("FALSE " + HttpStatus.FORBIDDEN);
//            this.requestHistoryService.save(requestHistory);
//            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
//        }
//    }