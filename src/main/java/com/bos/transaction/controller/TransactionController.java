package com.bos.transaction.controller;

import bca.bit.proj.library.base.ResultEntity;
import com.bos.transaction.model.request.OrderShippedRequest;
import com.bos.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bos")
@CrossOrigin(origins = {"*"})
public class TransactionController {
    @Autowired
    TransactionService g_transactionService;

    @GetMapping("/onlineTransaction/{id_seller}")
    public ResultEntity getOnlineTransaction(@PathVariable("id_seller") int id_seller){
        return g_transactionService.getOnlineTransactions(id_seller);
    }

    @GetMapping("/offlineTransaction/{id_seller}")
    public ResultEntity getOfflineTransaction(@PathVariable("id_seller") int id_seller){
        return g_transactionService.getOfflineTransactions(id_seller);
    }

    @GetMapping("transactionDetail/{id_transaction}")
    public ResultEntity getTransactionDetail(@PathVariable("id_transaction") int id_transaction){
        return g_transactionService.getTransactionDetail(id_transaction);
    }

    @PutMapping("orderShipped")
    public ResultEntity updateOrderShipped(@RequestBody OrderShippedRequest p_orderShipped){
        return g_transactionService.updateOrderShipped(p_orderShipped);
    }
}
