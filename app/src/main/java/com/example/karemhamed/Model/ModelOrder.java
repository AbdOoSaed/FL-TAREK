/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class ModelOrder implements Serializable {
    private Boolean orderStatus;
    private String strOrderDate;//
    private String strOrderNumber;//
    private String strOrderName;//
    private String strOrderPrics;
    private String strOrderDeliveryPrice;
    private String strOrderAddress;
    @Exclude
    private String strOrderId;

    public ModelOrder() {
    }

    public ModelOrder(Boolean orderStatus, String strOrderDate, String strOrderNumber,
                      String strOrderName, String strOrderPrics, String strOrderDeliveryPrice, String strOrderAddress, String strOrderId) {
        this.orderStatus = orderStatus;
        this.strOrderDate = strOrderDate;
        this.strOrderNumber = strOrderNumber;
        this.strOrderName = strOrderName;
        this.strOrderPrics = strOrderPrics;
        this.strOrderDeliveryPrice = strOrderDeliveryPrice;
        this.strOrderAddress = strOrderAddress;
        this.strOrderId = strOrderId;
    }

    public String getStrOrderDate() {
        return strOrderDate;
    }

    public void setStrOrderDate(String strOrderDate) {
        this.strOrderDate = strOrderDate;
    }

    public String getStrOrderNumber() {
        return strOrderNumber;
    }

    public void setStrOrderNumber(String strOrderNumber) {
        this.strOrderNumber = strOrderNumber;
    }

    public String getStrOrderName() {
        return strOrderName;
    }

    public void setStrOrderName(String strOrderName) {
        this.strOrderName = strOrderName;
    }

    public String getStrOrderAddress() {
        return strOrderAddress;
    }

    public void setStrOrderAddress(String strOrderAddress) {
        this.strOrderAddress = strOrderAddress;
    }

    public String getStrOrderPrics() {
        return strOrderPrics;
    }

    public void setStrOrderPrics(String strOrderPrics) {
        this.strOrderPrics = strOrderPrics;
    }

    public String getStrOrderDeliveryPrice() {
        return strOrderDeliveryPrice;
    }

    public void setStrOrderDeliveryPrice(String strOrderDeliveryPrice) {
        this.strOrderDeliveryPrice = strOrderDeliveryPrice;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStrOrderId() {
        return strOrderId;
    }

    public void setStrOrderId(String strOrderId) {
        this.strOrderId = strOrderId;
    }
}
