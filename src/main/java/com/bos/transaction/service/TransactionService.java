package com.bos.transaction.service;

import bca.bit.proj.library.base.ResultEntity;
import bca.bit.proj.library.enums.ErrorCode;
import com.bos.transaction.model.dao.OfflineTransactionDao;
import com.bos.transaction.model.dao.OfflineTransactionDetailDao;
import com.bos.transaction.model.dao.OnlineTransactioinDao;
import com.bos.transaction.model.dao.OnlineTransactionDetailDao;
import com.bos.transaction.model.request.OrderShippedRequest;
import com.bos.transaction.model.response.ProductResponse;
import com.bos.transaction.model.response.BuyerResponse;
import com.bos.transaction.model.response.ResponseDataTransaction;
import com.bos.transaction.model.response.ResponseDataTransactionDetail;
import com.bos.transaction.model.response.TransactionDetailResponse;
import com.bos.transaction.repository.TransactionDetailRepository;
import com.bos.transaction.repository.TransactionRepository;
import com.bos.transaction.repository.KelurahanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    private String getAddress(int p_address){
        String tmp_address = g_kelurahanRepository.getAddressByKelurahanId(p_address);
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

    public ResultEntity getOnlineTransactions(int p_sellerId){
        ResultEntity l_output = null;
        try {
            List<OnlineTransactioinDao> tmp_getTransactionList = g_transactionRepository.getOnlineTransactionBySellerId(p_sellerId);
            ArrayList<ResponseDataTransaction> tmp_transactionResponseList = new ArrayList<>();
            for (int i=0; i<tmp_getTransactionList.size(); i++){
                BuyerResponse tmp_buyer = new BuyerResponse();
                tmp_buyer.setId_buyer(tmp_getTransactionList.get(i).getId_buyer());
                tmp_buyer.setBuyer_name(tmp_getTransactionList.get(i).getBuyer_name());
                tmp_buyer.setPhone(tmp_getTransactionList.get(i).getPhone());

                ResponseDataTransaction tmp_responseDataTransaction = new ResponseDataTransaction();
                tmp_responseDataTransaction.setId_transaction(tmp_getTransactionList.get(i).getId_transaction());
                tmp_responseDataTransaction.setBuyer(tmp_buyer);
                tmp_responseDataTransaction.setOrder_time(tmp_getTransactionList.get(i).getOrder_time().substring(0, 10));
                tmp_responseDataTransaction.setTotal_payment(tmp_getTransactionList.get(i).getTotal_payment());
                tmp_responseDataTransaction.setStatus(tmp_getTransactionList.get(i).getStatus());

                tmp_transactionResponseList.add(tmp_responseDataTransaction);

                l_output = new ResultEntity(tmp_transactionResponseList, ErrorCode.BIT_000);
            }

        }catch (Exception e){
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public ResultEntity getOfflineTransactions(int p_sellerId){
        ResultEntity l_output = null;
        try {
            List<OfflineTransactionDao> tmp_getTransactionList = g_transactionRepository.getOfflineTransactionBySellerId(p_sellerId);
            ArrayList<ResponseDataTransaction> tmp_transactionResponseList = new ArrayList<>();
            for (int i=0; i<tmp_getTransactionList.size(); i++){
                ResponseDataTransaction tmp_responseDataTransaction = new ResponseDataTransaction();
                tmp_responseDataTransaction.setId_transaction(tmp_getTransactionList.get(i).getId_transaction());
                tmp_responseDataTransaction.setOrder_time(tmp_getTransactionList.get(i).getOrder_time().substring(0, 10));
                tmp_responseDataTransaction.setTotal_payment(tmp_getTransactionList.get(i).getTotal_payment());
                tmp_responseDataTransaction.setStatus(tmp_getTransactionList.get(i).getStatus());

                tmp_transactionResponseList.add(tmp_responseDataTransaction);

                l_output = new ResultEntity(tmp_transactionResponseList, ErrorCode.BIT_000);
            }

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public ResultEntity getOnlineTransactionDetail(int p_transactionId){
        ResultEntity l_output;

        try {
            //Get transaction list
            List<OnlineTransactionDetailDao> tmp_getDetailList = g_transactionDetailRepository.getOnlineTransactionDetail(p_transactionId);
            int tmp_transactionId = tmp_getDetailList.get(0).getId_transaction();
            String tmp_totalPayment = tmp_getDetailList.get(0).getTotal_payment();
            String tmp_fullAddress = tmp_getDetailList.get(0).getAddress_detail() + ", " + getAddress(tmp_getDetailList.get(0).getId_kelurahan());
            String tmp_orderTime = tmp_getDetailList.get(0).getOrder_time();
            String tmp_shippingCode = tmp_getDetailList.get(0).getShipping_code();

            //Set buyer
            BuyerResponse tmp_buyer = new BuyerResponse();
            tmp_buyer.setId_buyer(tmp_getDetailList.get(0).getId_buyer());
            tmp_buyer.setBuyer_name(tmp_getDetailList.get(0).getBuyer_name());
            tmp_buyer.setPhone(tmp_getDetailList.get(0).getPhone());

            //Set list of transaction_detail
            ArrayList<TransactionDetailResponse> tmp_transactionDetailResponseList = new ArrayList<>();
            for (int i=0; i<tmp_getDetailList.size(); i++){
                //get sell_price*quantity
                double tmp_price = Double.valueOf(tmp_getDetailList.get(i).getSell_price())*tmp_getDetailList.get(i).getQuantity();

                //Set product
                ProductResponse tmp_productResponse = new ProductResponse();
                tmp_productResponse.setId_product(tmp_getDetailList.get(i).getId_product());
                tmp_productResponse.setProduct_name(tmp_getDetailList.get(i).getProduct_name());

                //Set transaction list
                TransactionDetailResponse tmp_transactionDetailResponse = new TransactionDetailResponse();
                tmp_transactionDetailResponse.setId_transaction_detail(tmp_getDetailList.get(i).getId_transaction_detail());
                tmp_transactionDetailResponse.setQuantity(tmp_getDetailList.get(i).getQuantity());
                tmp_transactionDetailResponse.setSell_price(String.valueOf(tmp_price));
                tmp_transactionDetailResponse.setProduct(tmp_productResponse);

                tmp_transactionDetailResponseList.add(tmp_transactionDetailResponse);
            }

            //Set response data of transaction detail
            ResponseDataTransactionDetail tmp_responseDataTransactionDetail = new ResponseDataTransactionDetail();
            tmp_responseDataTransactionDetail.setId_transaction(tmp_transactionId);
            tmp_responseDataTransactionDetail.setTotal_payment(tmp_totalPayment);
            tmp_responseDataTransactionDetail.setOrder_time(tmp_orderTime.substring(0, 10));
            tmp_responseDataTransactionDetail.setAddress(tmp_fullAddress);
            tmp_responseDataTransactionDetail.setShipping_code(tmp_shippingCode);
            tmp_responseDataTransactionDetail.setBuyer(tmp_buyer);
            tmp_responseDataTransactionDetail.setTransaction_detail(tmp_transactionDetailResponseList);

            l_output = new ResultEntity(tmp_responseDataTransactionDetail, ErrorCode.BIT_000);

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public ResultEntity getOfflineTransactionDetail(int p_transactionId){
        ResultEntity l_output;

        try {
            //Get transaction list
            List<OfflineTransactionDetailDao> tmp_getDetailList = g_transactionDetailRepository.getOfflineTransactionDetail(p_transactionId);
            int tmp_transactionId = tmp_getDetailList.get(0).getId_transaction();
            String tmp_totalPayment = tmp_getDetailList.get(0).getTotal_payment();
            String tmp_orderTime = tmp_getDetailList.get(0).getOrder_time();

            //Set list of transaction_detail
            ArrayList<TransactionDetailResponse> tmp_transactionDetailResponseList = new ArrayList<>();
            for (int i=0; i<tmp_getDetailList.size(); i++){
                //get sell_price*quantity
                double tmp_price = Double.valueOf(tmp_getDetailList.get(i).getSell_price())*tmp_getDetailList.get(i).getQuantity();

                //Set product
                ProductResponse tmp_productResponse = new ProductResponse();
                tmp_productResponse.setId_product(tmp_getDetailList.get(i).getId_product());
                tmp_productResponse.setProduct_name(tmp_getDetailList.get(i).getProduct_name());

                //Set transaction list
                TransactionDetailResponse tmp_transactionDetailResponse = new TransactionDetailResponse();
                tmp_transactionDetailResponse.setId_transaction_detail(tmp_getDetailList.get(i).getId_transaction_detail());
                tmp_transactionDetailResponse.setQuantity(tmp_getDetailList.get(i).getQuantity());
                tmp_transactionDetailResponse.setSell_price(String.valueOf(tmp_price));
                tmp_transactionDetailResponse.setProduct(tmp_productResponse);

                tmp_transactionDetailResponseList.add(tmp_transactionDetailResponse);
            }

            //Set response data of transaction detail
            ResponseDataTransactionDetail tmp_responseDataTransactionDetail = new ResponseDataTransactionDetail();
            tmp_responseDataTransactionDetail.setId_transaction(tmp_transactionId);
            tmp_responseDataTransactionDetail.setTotal_payment(tmp_totalPayment);
            tmp_responseDataTransactionDetail.setOrder_time(tmp_orderTime.substring(0, 10));
            tmp_responseDataTransactionDetail.setTransaction_detail(tmp_transactionDetailResponseList);

            l_output = new ResultEntity(tmp_responseDataTransactionDetail, ErrorCode.BIT_000);

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public ResultEntity updateOrderShipped(OrderShippedRequest p_orderShipped){
        ResultEntity l_output;

        try {
            Timestamp tmp_shippingTime = new Timestamp(System.currentTimeMillis());

            g_transactionRepository.updateOrderShipped(p_orderShipped.getShipping_code(), tmp_shippingTime, p_orderShipped.getId_transaction());

            l_output = new ResultEntity("Y", ErrorCode.BIT_000);

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }
}