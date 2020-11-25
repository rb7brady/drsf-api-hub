package com.drsf.polygon.model;

import com.drsf.polygon.annotations.persist;

import java.util.Date;


public class DividendResult {

        String ticker;
        @persist
        Long exDate;
        @persist
        Long paymentDate;
        @persist(key = true)
        Long recordDate;
        @persist
        Long declaredDate;
        @persist
        double amount;

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }
        public Long getExDate() {
            return exDate;
        }
        public void setExDate(Long exDate) {
            this.exDate = exDate;
        }
        public Long getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(Long paymentDate) {
            this.paymentDate = paymentDate;
        }

        public Long getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(Long recordDate) {
            this.recordDate = recordDate;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

    public Long getDeclaredDate() {
        return declaredDate;
    }

    public void setDeclaredDate(Long declaredDate) {
        this.declaredDate = declaredDate;
    }
}
