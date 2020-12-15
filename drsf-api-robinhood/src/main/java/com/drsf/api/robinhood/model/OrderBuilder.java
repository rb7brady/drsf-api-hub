package com.drsf.api.robinhood.model;

import java.util.List;

public class OrderBuilder {
    private Object id;
    private Object refId;
    private Object url;
    private Object account;
    private Object position;
    private Object cancel;
    private Object instrument;
    private Object cumulativeQuantity;
    private Object averagePrice;
    private Object fees;
    private Object state;
    private Object type;
    private Object side;
    private Object timeInForce;
    private Object trigger;
    private Object price;
    private Object stopPrice;
    private Object quantity;
    private Object rejectReason;
    private Object createdAt;
    private Object updatedAt;
    private Object lastTransactionAt;
    private List<Object> executions;
    private Object extendedHours;
    private Object overrideDtbpChecks;
    private Object overrideDayTradeChecks;
    private Object responseCategory;
    private Object stopTriggeredAt;
    private Object lastTrailPrice;
    private Object lastTrailPriceUpdatedAt;
    private Object totalNotional;
    private Object executedNotional;
    private Object investmentScheduleId;

    public OrderBuilder setId(Object id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setRefId(Object refId) {
        this.refId = refId;
        return this;
    }

    public OrderBuilder setUrl(Object url) {
        this.url = url;
        return this;
    }

    public OrderBuilder setAccount(Object account) {
        this.account = account;
        return this;
    }

    public OrderBuilder setPosition(Object position) {
        this.position = position;
        return this;
    }

    public OrderBuilder setCancel(Object cancel) {
        this.cancel = cancel;
        return this;
    }

    public OrderBuilder setInstrument(Object instrument) {
        this.instrument = instrument;
        return this;
    }

    public OrderBuilder setCumulativeQuantity(Object cumulativeQuantity) {
        this.cumulativeQuantity = cumulativeQuantity;
        return this;
    }

    public OrderBuilder setAveragePrice(Object averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public OrderBuilder setFees(Object fees) {
        this.fees = fees;
        return this;
    }

    public OrderBuilder setState(Object state) {
        this.state = state;
        return this;
    }

    public OrderBuilder setType(Object type) {
        this.type = type;
        return this;
    }

    public OrderBuilder setSide(Object side) {
        this.side = side;
        return this;
    }

    public OrderBuilder setTimeInForce(Object timeInForce) {
        this.timeInForce = timeInForce;
        return this;
    }

    public OrderBuilder setTrigger(Object trigger) {
        this.trigger = trigger;
        return this;
    }

    public OrderBuilder setPrice(Object price) {
        this.price = price;
        return this;
    }

    public OrderBuilder setStopPrice(Object stopPrice) {
        this.stopPrice = stopPrice;
        return this;
    }

    public OrderBuilder setQuantity(Object quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder setRejectReason(Object rejectReason) {
        this.rejectReason = rejectReason;
        return this;
    }

    public OrderBuilder setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OrderBuilder setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public OrderBuilder setLastTransactionAt(Object lastTransactionAt) {
        this.lastTransactionAt = lastTransactionAt;
        return this;
    }

    public OrderBuilder setExecutions(List<Object> executions) {
        this.executions = executions;
        return this;
    }

    public OrderBuilder setExtendedHours(Object extendedHours) {
        this.extendedHours = extendedHours;
        return this;
    }

    public OrderBuilder setOverrideDtbpChecks(Object overrideDtbpChecks) {
        this.overrideDtbpChecks = overrideDtbpChecks;
        return this;
    }

    public OrderBuilder setOverrideDayTradeChecks(Object overrideDayTradeChecks) {
        this.overrideDayTradeChecks = overrideDayTradeChecks;
        return this;
    }

    public OrderBuilder setResponseCategory(Object responseCategory) {
        this.responseCategory = responseCategory;
        return this;
    }

    public OrderBuilder setStopTriggeredAt(Object stopTriggeredAt) {
        this.stopTriggeredAt = stopTriggeredAt;
        return this;
    }

    public OrderBuilder setLastTrailPrice(Object lastTrailPrice) {
        this.lastTrailPrice = lastTrailPrice;
        return this;
    }

    public OrderBuilder setLastTrailPriceUpdatedAt(Object lastTrailPriceUpdatedAt) {
        this.lastTrailPriceUpdatedAt = lastTrailPriceUpdatedAt;
        return this;
    }

    public OrderBuilder setTotalNotional(Object totalNotional) {
        this.totalNotional = totalNotional;
        return this;
    }

    public OrderBuilder setExecutedNotional(Object executedNotional) {
        this.executedNotional = executedNotional;
        return this;
    }

    public OrderBuilder setInvestmentScheduleId(Object investmentScheduleId) {
        this.investmentScheduleId = investmentScheduleId;
        return this;
    }

    public Order createOrder() {
        return new Order(id, refId, url, account, position, cancel, instrument, cumulativeQuantity, averagePrice, fees, state, type, side, timeInForce, trigger, price, stopPrice, quantity, rejectReason, createdAt, updatedAt, lastTransactionAt, executions, extendedHours, overrideDtbpChecks, overrideDayTradeChecks, responseCategory, stopTriggeredAt, lastTrailPrice, lastTrailPriceUpdatedAt, totalNotional, executedNotional, investmentScheduleId);
    }
}