package com.bos.transaction.service;

import bca.bit.proj.library.base.ResultEntity;
import bca.bit.proj.library.enums.ErrorCode;
import com.bos.transaction.model.response.ResponseDataTransaction;
import com.bos.transaction.model.response.ResponseDataTransactionDetail;
import com.bos.transaction.repository.BuyerRepository;
import com.bos.transaction.repository.TransactionDetailRepository;
import com.bos.transaction.repository.TransactionRepository;
import com.bos.transaction.repository.address.KelurahanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    KelurahanRepository g_kelurahanRepository;
    @Autowired
    TransactionRepository g_transactionRepository;
    @Autowired
    TransactionDetailRepository g_transactionDetailRepository;
    @Autowired
    BuyerRepository g_buyerRepository;

    private String getAddress(){
        String tmp_address = g_kelurahanRepository.getAddressByKelurahanId(12782);
        String[] tmp_addressArray = tmp_address.split(",");
        tmp_address = "";
        for (int i=0; i<tmp_addressArray.length; i++){
            if (i==0){
                tmp_address = tmp_addressArray[i];
            }else{
                tmp_address = tmp_address + ", " + tmp_addressArray[i];
            }
        }
        return tmp_address;
    }

    public ResultEntity getTransactions(int p_sellerId){
        ResultEntity l_output = null;
        try {
            List<ResponseDataTransaction> tmp_transactionList = g_transactionRepository.getResponseDataTransaction(p_sellerId);
            ArrayList<ResponseDataTransaction> tmp_transactionResponseList = new ArrayList<>();
            for (int i=0; i<tmp_transactionList.size(); i++){
                String tmp_orderTime = tmp_transactionList.get(i).getOrder_time().substring(0, 9);
                ResponseDataTransaction tmp_responseDataTransaction = new ResponseDataTransaction();
                tmp_responseDataTransaction.setId_transaction(tmp_transactionList.get(i).getId_transaction());
                tmp_responseDataTransaction.setBuyer_name(tmp_transactionList.get(i).getBuyer_name());
                tmp_responseDataTransaction.setOrder_time(tmp_orderTime);
                tmp_responseDataTransaction.setTotal_payment(tmp_transactionList.get(i).getTotal_payment());
                tmp_responseDataTransaction.setStatus(tmp_transactionList.get(i).getStatus());

                tmp_transactionResponseList.add(tmp_responseDataTransaction);

                l_output = new ResultEntity(tmp_transactionResponseList, ErrorCode.BIT_000);
            }

        }catch (Exception e){
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public List<ResponseDataTransactionDetail> getTransactionDetail(int p_transactionId){

        return g_transactionDetailRepository.getTransactionDetail(p_transactionId);
    }
}