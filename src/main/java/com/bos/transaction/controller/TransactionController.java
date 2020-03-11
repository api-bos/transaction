package com.bos.transaction.controller;

import com.bos.transaction.model.Address;
import com.bos.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bos")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/getAddress")
    public String getAddress(){
        return transactionService.getAddress();
    }
}
