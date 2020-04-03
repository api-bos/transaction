package com.bos.transaction.controller;

import bca.bit.proj.library.base.ResultEntity;
import com.bos.transaction.model.request.OfflineTransactionRequest;
import com.bos.transaction.model.request.OrderShippedRequest;
import com.bos.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bos", produces = "application/json")
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

    @GetMapping("onlineTransactionDetail/{id_transaction}")
    public ResultEntity getOnlineTransactionDetail(@PathVariable("id_transaction") int id_transaction){
        return g_transactionService.getOnlineTransactionDetail(id_transaction);
    }

    @GetMapping("offlineTransactionDetail/{id_transaction}")
    public ResultEntity getOfflineTransactionDetail(@PathVariable("id_transaction") int id_transaction){
        return g_transactionService.getOfflineTransactionDetail(id_transaction);
    }

    @PutMapping("orderShipped")
    public ResultEntity updateOrderShipped(@RequestBody OrderShippedRequest p_orderShipped){
        return g_transactionService.updateOrderShipped(p_orderShipped);
    }

    @DeleteMapping("transaction/{id_transaction}")
    public ResultEntity deleteTransaction(@PathVariable("id_transaction") int id_transaction){
        return g_transactionService.deleteTransaction(id_transaction);
    }

    @PutMapping("completedTransaction/{id_transaction}")
    public ResultEntity updateCompletedTransaction(@PathVariable("id_transaction") int p_transactionId){
        return g_transactionService.updateCompletedTransaction(p_transactionId);
    }

    @PostMapping(value = "/offlineTransaction", consumes = "application/json")
    public ResultEntity addOfflineTransaction(@RequestBody OfflineTransactionRequest p_offlineTrxRequest){
        return g_transactionService.addOfflineTransaction(p_offlineTrxRequest);
    }
}
