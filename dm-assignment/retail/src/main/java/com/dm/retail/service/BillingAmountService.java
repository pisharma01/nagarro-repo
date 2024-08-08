package com.dm.retail.service;

import com.dm.retail.dto.request.BillingAmountRequestDto;
import com.dm.retail.dto.response.BillingAmountResponseDto;

public interface BillingAmountService {
    BillingAmountResponseDto computeNetBillingAmount(BillingAmountRequestDto billingAmountRequestDto);
}
