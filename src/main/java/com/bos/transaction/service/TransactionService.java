package com.bos.transaction.service;

import com.bos.transaction.repository.address.KelurahanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    KelurahanRepository kelurahanRepository;

    public String getAddress(){
        String tmp_address = kelurahanRepository.getAddressByKelurahanId(12782);
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
}