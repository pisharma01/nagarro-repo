package com.dm.retail.controller;

import com.dm.retail.dto.request.BillingAmountRequestDto;
import com.dm.retail.dto.response.BillingAmountResponseDto;
import com.dm.retail.service.BillingAmountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
public class BillingAmountController {

    private static final Logger logger = LoggerFactory.getLogger(BillingAmountController.class);

    @Autowired
    private BillingAmountService billingAmountService;

    @PostMapping("/amount")
    public ResponseEntity<BillingAmountResponseDto> computeNetBillingAmount(@RequestBody BillingAmountRequestDto billingAmountRequestDto){
        logger.info("Inside BillingAmountController");
        return new ResponseEntity<BillingAmountResponseDto>(billingAmountService.computeNetBillingAmount(billingAmountRequestDto), HttpStatus.OK);
    }
}
