package com.bos.transaction.controller;

import bca.bit.proj.library.base.ResultEntity;
import com.bos.transaction.model.dao.TransactionDetailDao;
import com.bos.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bos")
public class TransactionController {
    @Autowired
    TransactionService g_transactionService;

    @GetMapping("/transaction/{id_seller}")
    public ResultEntity getAddress(@PathVariable("id_seller") int id_seller){
        return g_transactionService.getTransactions(id_seller);
    }

    @GetMapping("transactionDetail/{id_transaction}")
    public ResultEntity getDetail(@PathVariable("id_transaction") int id_transaction){
        return g_transactionService.getTransactionDetail(id_transaction);
    }
}
