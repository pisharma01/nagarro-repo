package com.dm.retail.service.impl;

import com.dm.retail.dto.ProductDto;
import com.dm.retail.dto.request.BillingAmountRequestDto;
import com.dm.retail.dto.response.BillingAmountResponseDto;
import com.dm.retail.entity.Product;
import com.dm.retail.enums.ProductType;
import com.dm.retail.enums.UserType;
import com.dm.retail.exception.CustomRetailException;
import com.dm.retail.repository.ProductRepository;
import com.dm.retail.repository.UserRepository;
import com.dm.retail.service.BillingAmountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class to compute discounted price
 */
@Service
public class BillingAmountServiceImpl implements BillingAmountService {

    private static final Logger logger = LoggerFactory.getLogger(BillingAmountServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Method to compute discounted price
     * @param billingAmountRequestDto - Request Object
     * @return BillingAmountResponseDto - response object
     */
    @Override
    public BillingAmountResponseDto computeNetBillingAmount(BillingAmountRequestDto billingAmountRequestDto) {
        logger.info("Inside BillingAmountServiceImpl.computeNetBillingAmount");
        try{
            if(billingAmountRequestDto.getUserId() == null){
               throw new CustomRetailException("UserId can't be null");
            }
            UserType userType = UserType.valueOf(userRepository.getUserTypeByUserID(billingAmountRequestDto.getUserId()));
            List<Product> productDetails = getProductDetails(billingAmountRequestDto.getProducts());
            if(productDetails == null || productDetails.isEmpty()){
                throw new CustomRetailException("Product Details doesn't exist or Invalid product codes");
            }
            BigDecimal billingAmountExcludingGrocery = productDetails.stream()
                    .filter(product -> product.getType() != ProductType.GROCERY)
                    .map(p ->
                         p.getPrice().multiply(BigDecimal.valueOf(billingAmountRequestDto.getProducts()
                                .stream().filter(q ->
                                        q.getProductCode().equals(p.getProductCode())).findFirst().get().getQuantity()))
                    )
                    .reduce(new BigDecimal("0"), BigDecimal::add);
            logger.info("billingAmountExcludingGrocery {}" , billingAmountExcludingGrocery);
            BigDecimal initialAmount = productDetails.stream()
                    .map(p ->
                            p.getPrice().multiply(BigDecimal.valueOf(billingAmountRequestDto.getProducts()
                                .stream().filter(q ->
                                        q.getProductCode().equals(p.getProductCode())).findFirst().get().getQuantity()))
                    )
                    .reduce(new BigDecimal("0"), BigDecimal::add);
            logger.info("InitialAmount {}", initialAmount);
            BigDecimal flatDiscount = getFlatDiscount(initialAmount);
            BigDecimal percentageDiscount = getPercentageBasedDiscount(billingAmountExcludingGrocery, userType);
            logger.info("percentageDiscount {}", percentageDiscount);
            logger.info("flatDiscount {}", flatDiscount);
            logger.info("userType.getDiscount() {}", userType.getDiscount());
            BigDecimal totalDiscountOffered =
            percentageDiscount.add(flatDiscount, new MathContext(2, RoundingMode.UNNECESSARY));
            logger.info("TotalDiscountOffered {}", totalDiscountOffered);
            return new BillingAmountResponseDto(initialAmount.subtract(totalDiscountOffered)
                    .setScale(2, RoundingMode.UNNECESSARY));
        }catch(Exception exception){
            logger.error("Exception occurred inside computeNetBillingAmount for username {}", billingAmountRequestDto.getUserId());
            throw new CustomRetailException(exception.getMessage(), exception);
        }
    }

    /**
     * Method to compute percentage discount.
     * @param amount - Amount on which percentage discount to be computed
     * @param userType - Type of User
     * @return amount
     */
    private BigDecimal getPercentageBasedDiscount(BigDecimal amount, UserType userType){
        return amount.multiply(new BigDecimal(userType.getDiscount())
                        , new MathContext(2, RoundingMode.UNNECESSARY))
                .divide(new BigDecimal(100),
                        new MathContext(2, RoundingMode.UNNECESSARY));

    }

    /**
     * Method to compute flat discount.
     * @param amount - flat discount
     * @return flatDiscount
     */
    private BigDecimal getFlatDiscount(BigDecimal amount){
        BigInteger divisorForHundredMultiples = amount.toBigInteger()
                .divide(BigInteger.valueOf(100));
        BigInteger hundredMultiplesCount =
                divisorForHundredMultiples.compareTo(BigInteger.valueOf(1)) < 0
                        ? BigInteger.valueOf(0) : divisorForHundredMultiples;
       return new BigDecimal(hundredMultiplesCount.multiply(BigInteger.valueOf(5)),
                new MathContext(2, RoundingMode.UNNECESSARY));
    }

    /**
     * Method to pull product details.
     * @param products - List of product dto
     * @return list of products entities
     */
    private List<Product> getProductDetails(List<ProductDto> products){
        Set<String> productCodes = products.stream().map(ProductDto::getProductCode)
                .collect(Collectors.toSet());
        return productRepository.getProductsByProductCode(productCodes);
    }

}
