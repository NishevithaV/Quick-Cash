package com.example.quick_cash.controllers;
import java.math.BigDecimal;

/**
 * The type Payment validator.
 */
public class PaymentValidator {
    /**
     * Is amount valid boolean.
     *
     * @param amountStr the amount str
     * @return the boolean
     */
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
     *
     * @param amountStr the amount str
     * @return the big decimal
     */
    public static BigDecimal parseAmount(String amountStr) {
        return new BigDecimal(amountStr.trim());
    }
}
