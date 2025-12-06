package com.example.quick_cash.controllers;
import java.math.BigDecimal;
public class PaymentValidator {
    public static boolean isAmountValid(String amountStr) {
        if (amountStr == null || amountStr.trim().isEmpty()) return false;
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            return amount.compareTo(BigDecimal.ZERO) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Converts a valid string to BigDecimal
     */
    public static BigDecimal parseAmount(String amountStr) {
        return new BigDecimal(amountStr.trim());
    }
}
