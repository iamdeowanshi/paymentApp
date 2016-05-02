package com.tecsol.batua.user.data.model;

/**
 * @author Arnold Laishram.
 */
public class WalletTransaction extends BaseModel {

    private String amount;
    private String date;
    private String orderNumber;
    private String cardType;
    private String cashBackAgainst;

    public WalletTransaction(String amount, String date, String orderNumber, String cardType, String cashBackAgainst) {
        this.amount = amount;
        this.date = date;
        this.orderNumber = orderNumber;
        this.cardType = cardType;
        this.cashBackAgainst = cashBackAgainst;
    }

    public String getCashBackAgainst() {
        return cashBackAgainst;
    }

    public void setCashBackAgainst(String cashBackAgainst) {
        this.cashBackAgainst = cashBackAgainst;
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
