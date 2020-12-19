package com.drsf.api.robinhood.model;

import com.drsf.api.robinhood.RobinhoodResponsePropertyURL;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @JsonProperty("id")
    private Object id;
    @JsonProperty("ref_id")
    private Object refId;
    @JsonProperty("url")
    private Object url;
    @RobinhoodResponsePropertyURL(requiresAuthentication = true)
    @JsonProperty("account")
    private Object account;
    @JsonProperty("position")
    private Object position;
    @JsonProperty("cancel")
    private Object cancel;
    @RobinhoodResponsePropertyURL
    @JsonProperty("instrument")
    private Object instrument;
    @JsonProperty("cumulative_quantity")
    private Object cumulativeQuantity;
    @JsonProperty("average_price")
    private Object averagePrice;
    @JsonProperty("fees")
    private Object fees;
    @JsonProperty("state")
    private Object state;
    @JsonProperty("type")
    private Object type;
    @JsonProperty("side")
    private Object side;
    @JsonProperty("time_in_force")
    private Object timeInForce;
    @JsonProperty("trigger")
    private Object trigger;
    @JsonProperty("price")
    private Object price;
    @JsonProperty("stop_price")
    private Object stopPrice;
    @JsonProperty("quantity")
    private Object quantity;
    @JsonProperty("reject_reason")
    private Object rejectReason;
    @JsonProperty("created_at")
    private Object createdAt;
    @JsonProperty("updated_at")
    private Object updatedAt;
    @JsonProperty("last_transaction_at")
    private Object lastTransactionAt;
    @JsonProperty("executions")
    private List<Execution> executions;
    @JsonProperty("extended_hours")
    private Object extendedHours;
    @JsonProperty("  ")
    private Object overrideDtbpChecks;
    @JsonProperty("override_day_trade_checks")
    private Object overrideDayTradeChecks;
    @JsonProperty("response_category")
    private Object responseCategory;
    @JsonProperty("stop_triggered_at")
    private Object stopTriggeredAt;
    @JsonProperty("last_trail_price")
    private Object lastTrailPrice;
    @JsonProperty("last_trail_price_updated_at")
    private Object lastTrailPriceUpdatedAt;
    @JsonProperty("total_notional")
    private Object totalNotional;
    @JsonProperty("executed_notional")
    private Object executedNotional;
    @JsonProperty("investment_schedule_id")
    private Object investmentScheduleId;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getRefId() {
        return refId;
    }

    public void setRefId(Object refId) {
        this.refId = refId;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Object getAccount() {
        return account;
    }

    public void setAccount(Object account) {
        this.account = account;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public Object getCancel() {
        return cancel;
    }

    public void setCancel(Object cancel) {
        this.cancel = cancel;
    }

    public Object getInstrument() {
        return instrument;
    }

    public void setInstrument(Object instrument) {
        this.instrument = instrument;
    }

    public Object getCumulativeQuantity() {
        return cumulativeQuantity;
    }

    public void setCumulativeQuantity(Object cumulativeQuantity) {
        this.cumulativeQuantity = cumulativeQuantity;
    }

    public Object getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Object averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Object getFees() {
        return fees;
    }

    public void setFees(Object fees) {
        this.fees = fees;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getSide() {
        return side;
    }

    public void setSide(Object side) {
        this.side = side;
    }

    public Object getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(Object timeInForce) {
        this.timeInForce = timeInForce;
    }

    public Object getTrigger() {
        return trigger;
    }

    public void setTrigger(Object trigger) {
        this.trigger = trigger;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(Object stopPrice) {
        this.stopPrice = stopPrice;
    }

    public Object getQuantity() {
        return quantity;
    }

    public void setQuantity(Object quantity) {
        this.quantity = quantity;
    }

    public Object getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(Object rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getLastTransactionAt() {
        return lastTransactionAt;
    }

    public void setLastTransactionAt(Object lastTransactionAt) {
        this.lastTransactionAt = lastTransactionAt;
    }

    public List<Execution> getExecutions() {
        return executions;
    }

    public void setExecutions(List<Execution> executions) {
        this.executions = executions;
    }

    public Object getExtendedHours() {
        return extendedHours;
    }

    public void setExtendedHours(Object extendedHours) {
        this.extendedHours = extendedHours;
    }

    public Object getOverrideDtbpChecks() {
        return overrideDtbpChecks;
    }

    public void setOverrideDtbpChecks(Object overrideDtbpChecks) {
        this.overrideDtbpChecks = overrideDtbpChecks;
    }

    public Object getOverrideDayTradeChecks() {
        return overrideDayTradeChecks;
    }

    public void setOverrideDayTradeChecks(Object overrideDayTradeChecks) {
        this.overrideDayTradeChecks = overrideDayTradeChecks;
    }

    public Object getResponseCategory() {
        return responseCategory;
    }

    public void setResponseCategory(Object responseCategory) {
        this.responseCategory = responseCategory;
    }

    public Object getStopTriggeredAt() {
        return stopTriggeredAt;
    }

    public void setStopTriggeredAt(Object stopTriggeredAt) {
        this.stopTriggeredAt = stopTriggeredAt;
    }

    public Object getLastTrailPrice() {
        return lastTrailPrice;
    }

    public void setLastTrailPrice(Object lastTrailPrice) {
        this.lastTrailPrice = lastTrailPrice;
    }

    public Object getLastTrailPriceUpdatedAt() {
        return lastTrailPriceUpdatedAt;
    }

    public void setLastTrailPriceUpdatedAt(Object lastTrailPriceUpdatedAt) {
        this.lastTrailPriceUpdatedAt = lastTrailPriceUpdatedAt;
    }

    public Object getTotalNotional() {
        return totalNotional;
    }

    public void setTotalNotional(Object totalNotional) {
        this.totalNotional = totalNotional;
    }

    public Object getExecutedNotional() {
        return executedNotional;
    }

    public void setExecutedNotional(Object executedNotional) {
        this.executedNotional = executedNotional;
    }

    public Object getInvestmentScheduleId() {
        return investmentScheduleId;
    }

    public void setInvestmentScheduleId(Object investmentScheduleId) {
        this.investmentScheduleId = investmentScheduleId;
    }

    public Order() {
    }

    public Order(Object id, Object refId, Object url, Object account, Object position, Object cancel, Object instrument, Object cumulativeQuantity, Object averagePrice, Object fees, Object state, Object type, Object side, Object timeInForce, Object trigger, Object price, Object stopPrice, Object quantity, Object rejectReason, Object createdAt, Object updatedAt, Object lastTransactionAt, List<Execution> executions, Object extendedHours, Object overrideDtbpChecks, Object overrideDayTradeChecks, Object responseCategory, Object stopTriggeredAt, Object lastTrailPrice, Object lastTrailPriceUpdatedAt, Object totalNotional, Object executedNotional, Object investmentScheduleId) {
        this.id = id;
        this.refId = refId;
        this.url = url;
        this.account = account;
        this.position = position;
        this.cancel = cancel;
        this.instrument = instrument;
        this.cumulativeQuantity = cumulativeQuantity;
        this.averagePrice = averagePrice;
        this.fees = fees;
        this.state = state;
        this.type = type;
        this.side = side;
        this.timeInForce = timeInForce;
        this.trigger = trigger;
        this.price = price;
        this.stopPrice = stopPrice;
        this.quantity = quantity;
        this.rejectReason = rejectReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastTransactionAt = lastTransactionAt;
        this.executions = executions;
        this.extendedHours = extendedHours;
        this.overrideDtbpChecks = overrideDtbpChecks;
        this.overrideDayTradeChecks = overrideDayTradeChecks;
        this.responseCategory = responseCategory;
        this.stopTriggeredAt = stopTriggeredAt;
        this.lastTrailPrice = lastTrailPrice;
        this.lastTrailPriceUpdatedAt = lastTrailPriceUpdatedAt;
        this.totalNotional = totalNotional;
        this.executedNotional = executedNotional;
        this.investmentScheduleId = investmentScheduleId;
    }
}
