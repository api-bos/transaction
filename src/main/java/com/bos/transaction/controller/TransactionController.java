package com.bos.transaction.controller;

import bca.bit.proj.library.base.ResultEntity;
import com.bos.transaction.model.response.ResponseDataTransaction;
import com.bos.transaction.model.response.ResponseDataTransactionDetail;
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

    @GetMapping("/get/{id_seller}")
    public ResultEntity getAddress(@PathVariable("id_seller") int id_seller){
        return g_transactionService.getTransactions(id_seller);
    }

    @GetMapping("getDetail/{id_transaction}")
    public List<ResponseDataTransactionDetail> getDetail(@PathVariable("id_transaction") int id_transaction){
        return g_transactionService.getTransactionDetail(id_transaction);
    }
}
