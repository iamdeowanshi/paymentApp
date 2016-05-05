package com.tecsol.batua.user.data.model.User;

import com.tecsol.batua.user.data.model.BaseModel;

/**
 * @author Arnold Laishram.
 */
public class TransactionHistory extends BaseModel {

    private String amount;
    private String date;
    private String orderNumber;
    private String cardType;
    private String transactionAgainst;
    private String transactionId;

    public TransactionHistory(String amount, String date, String orderNumber, String cardType, String transactionAgainst, String transactionId) {
        this.amount = amount;
        this.date = date;
        this.orderNumber = orderNumber;
        this.cardType = cardType;
        this.transactionAgainst = transactionAgainst;
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionAgainst() {
        return transactionAgainst;
    }

    public void setTransactionAgainst(String cashBackAgainst) {
        this.transactionAgainst = cashBackAgainst;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
