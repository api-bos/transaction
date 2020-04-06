package com.bos.transaction.service;

import bca.bit.proj.library.base.ResultEntity;
import bca.bit.proj.library.enums.ErrorCode;
import com.bos.transaction.model.dao.OfflineTransactionDao;
import com.bos.transaction.model.dao.OfflineTransactionDetailDao;
import com.bos.transaction.model.dao.OnlineTransactioinDao;
import com.bos.transaction.model.dao.OnlineTransactionDetailDao;
import com.bos.transaction.model.entity.OfflineTransaction;
import com.bos.transaction.model.entity.TransactionDetail;
import com.bos.transaction.model.request.OfflineTransactionRequest;
import com.bos.transaction.model.request.OrderShippedRequest;
import com.bos.transaction.model.response.ProductResponse;
import com.bos.transaction.model.response.BuyerResponse;
import com.bos.transaction.model.response.ResponseDataTransaction;
import com.bos.transaction.model.response.ResponseDataTransactionDetail;
import com.bos.transaction.model.response.TransactionDetailResponse;
import com.bos.transaction.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
    @Autowired
    ProductRepository g_productRepository;
    @Autowired
    OfflineTransactionRepository g_offlineTransactionRepository;

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
            BigInteger tmp_shippingFee = tmp_getDetailList.get(0).getShipping_fee();
            String tmp_shippingAgent = tmp_getDetailList.get(0).getShipping_agent();
            int tmp_status = tmp_getDetailList.get(0).getStatus();
            int tmp_sellerId = tmp_getDetailList.get(0).getId_seller();

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
            tmp_responseDataTransactionDetail.setShipping_fee(tmp_shippingFee);
            tmp_responseDataTransactionDetail.setShipping_agent(tmp_shippingAgent);
            tmp_responseDataTransactionDetail.setStatus(tmp_status);
            tmp_responseDataTransactionDetail.setId_seller(tmp_sellerId);
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

    public ResultEntity cancelOrder(int p_transactionId){
        ResultEntity l_output;

        try {
            g_transactionRepository.cancelOrder(p_transactionId);

            l_output = new ResultEntity("Y", ErrorCode.BIT_000);

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public ResultEntity deleteTransaction(int p_transactionId){
        ResultEntity l_output;
        try {
            List<TransactionDetail> tmp_transactionDetailList = g_transactionDetailRepository.getTransactionDetailByTransactionId(p_transactionId);

            for (int i=0; i<tmp_transactionDetailList.size(); i++){
                int tmp_productId = tmp_transactionDetailList.get(i).getId_product();
                int tmp_quantity = tmp_transactionDetailList.get(i).getQuantity();
                int tmp_stock = g_productRepository.getStockByProductId(tmp_productId);
                int tmp_totalStock = tmp_stock + tmp_quantity;

                //return stock
                g_productRepository.updateStockByProductId(tmp_totalStock, tmp_productId);

                //delete transaction detail
                g_transactionDetailRepository.deleteById(tmp_transactionDetailList.get(i).getId_transaction_detail());
            }

            //delete transaction
            g_transactionRepository.deleteById(p_transactionId);

            l_output = new ResultEntity("Y", ErrorCode.BIT_000);

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public ResultEntity updateCompletedTransaction(int p_transactionId){
        ResultEntity l_output;

        try {
            Timestamp tmp_confirmationTime = new Timestamp(System.currentTimeMillis());

            g_transactionRepository.updateCompletedTransaction(tmp_confirmationTime, p_transactionId);

            l_output = new ResultEntity("Y", ErrorCode.BIT_000);

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public ResultEntity addOfflineTransaction(OfflineTransactionRequest p_requestData){
        ResultEntity l_output = null;
        int l_stock;
        int l_quantity;
        int l_productId;
        ArrayList<String> l_arrayErrorMessage = new ArrayList<>();
        boolean l_transactionOccured = false;

        try{
            for (int i = 0; i<p_requestData.getProduct().size(); i++){
                l_productId = p_requestData.getProduct().get(i).getId_product();
                l_stock = g_productRepository.getStockByProductId(l_productId);
                l_quantity = p_requestData.getProduct().get(i).getQuantity();

                if (l_stock < l_quantity){
                    String tmp_productName = g_productRepository.getProductNameByProductId(l_productId);
                    String tmp_errorMessage = "Stok produk " + tmp_productName + " tidak mencukupi";

                    l_arrayErrorMessage.add(tmp_errorMessage);

                    l_transactionOccured = true;
                }
            }

            if (!l_transactionOccured){
                //Save data to transaction table
                OfflineTransaction tmp_transaction = new OfflineTransaction();
                Timestamp l_timestamp = new Timestamp(System.currentTimeMillis());
                tmp_transaction.setOrder_time(l_timestamp);
                tmp_transaction.setConfirmation_time(l_timestamp);
                tmp_transaction.setId_seller(p_requestData.getId_seller());
                tmp_transaction.setStatus(3);
                tmp_transaction.setTotal_payment(p_requestData.getTotal_payment());
                g_offlineTransactionRepository.save(tmp_transaction);

                //Get id_transaction from transaction table
                int tmp_transactionId = g_transactionRepository.getTransactionIdByOrderTime(l_timestamp);

                //Save data as much as product purchased to transaction_detail table
                for (int i = 0; i<p_requestData.getProduct().size(); i++){
                    l_productId = p_requestData.getProduct().get(i).getId_product();

                    //Decrease stock
                    l_quantity = p_requestData.getProduct().get(i).getQuantity();
                    l_stock = g_productRepository.getStockByProductId(l_productId);
                    int tmp_currentStock = l_stock - l_quantity;
                    g_productRepository.updateStockByProductId(tmp_currentStock, l_productId);

                    //Save data to transaction_detail table
                    TransactionDetail tmp_transactionDetail = new TransactionDetail();
                    tmp_transactionDetail.setId_transaction(tmp_transactionId);
                    tmp_transactionDetail.setId_product(l_productId);
                    tmp_transactionDetail.setQuantity(l_quantity);
                    tmp_transactionDetail.setSell_price(p_requestData.getProduct().get(i).getSell_price());
                    g_transactionDetailRepository.save(tmp_transactionDetail);

                    l_output = new ResultEntity("Y", ErrorCode.BIT_000);
                }

            }else {
                l_output = new ResultEntity(l_arrayErrorMessage, ErrorCode.BIT_999);
            }

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }
}