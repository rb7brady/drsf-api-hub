package com.drsf.api.robinhood.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @JsonProperty("id")
    private String id;
    @JsonProperty("ref_id")
    private String refId;
    @JsonProperty("url")
    private String url;
    @JsonProperty("account")
    private String account;
    @JsonProperty("position")
    private String position;
    @JsonProperty("cancel")
    private String cancel;
    @JsonProperty("instrument")
    private String instrument;
    @JsonProperty("cumulative_quantity")
    private String cumulativeQuantity;
    @JsonProperty("average_price")
    private String averagePrice;
    @JsonProperty("fees")
    private String fees;
    @JsonProperty("state")
    private String state;
    @JsonProperty("type")
    private String type;
    @JsonProperty("side")
    private String side;
    @JsonProperty("time_in_force")
    private String timeInForce;
    @JsonProperty("trigger")
    private String trigger;
    @JsonProperty("price")
    private String price;
    @JsonProperty("stop_price")
    private String stopPrice;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("reject_reason")
    private String rejectReason;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("last_transaction_at")
    private String lastTransactionAt;
    @JsonProperty("executions")
    private List<Object> executions;
    @JsonProperty("extended_hours")
    private String extendedHours;
    @JsonProperty("override_dtbp_checks")
    private String overrideDtbpChecks;
    @JsonProperty("override_day_trade_checks")
    private String overrideDayTradeChecks;
    @JsonProperty("response_category")
    private String responseCategory;
    @JsonProperty("stop_triggered_at")
    private String stopTriggeredAt;
    @JsonProperty("last_trail_price")
    private String lastTrailPrice;
    @JsonProperty("last_trail_price_updated_at")
    private String lastTrailPriceUpdatedAt;
    @JsonProperty("total_notional")
    private Object totalNotional;
    @JsonProperty("executed_notional")
    private Object executedNotional;
    @JsonProperty("investment_schedule_id")
    private String investmentScheduleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getCumulativeQuantity() {
        return cumulativeQuantity;
    }

    public void setCumulativeQuantity(String cumulativeQuantity) {
        this.cumulativeQuantity = cumulativeQuantity;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(String timeInForce) {
        this.timeInForce = timeInForce;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(String stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLastTransactionAt() {
        return lastTransactionAt;
    }

    public void setLastTransactionAt(String lastTransactionAt) {
        this.lastTransactionAt = lastTransactionAt;
    }

    public List<Object> getExecutions() {
        return executions;
    }

    public void setExecutions(List<Object> executions) {
        this.executions = executions;
    }

    public String getExtendedHours() {
        return extendedHours;
    }

    public void setExtendedHours(String extendedHours) {
        this.extendedHours = extendedHours;
    }

    public String getOverrideDtbpChecks() {
        return overrideDtbpChecks;
    }

    public void setOverrideDtbpChecks(String overrideDtbpChecks) {
        this.overrideDtbpChecks = overrideDtbpChecks;
    }

    public String getOverrideDayTradeChecks() {
        return overrideDayTradeChecks;
    }

    public void setOverrideDayTradeChecks(String overrideDayTradeChecks) {
        this.overrideDayTradeChecks = overrideDayTradeChecks;
    }

    public String getResponseCategory() {
        return responseCategory;
    }

    public void setResponseCategory(String responseCategory) {
        this.responseCategory = responseCategory;
    }

    public String getStopTriggeredAt() {
        return stopTriggeredAt;
    }

    public void setStopTriggeredAt(String stopTriggeredAt) {
        this.stopTriggeredAt = stopTriggeredAt;
    }

    public String getLastTrailPrice() {
        return lastTrailPrice;
    }

    public void setLastTrailPrice(String lastTrailPrice) {
        this.lastTrailPrice = lastTrailPrice;
    }

    public String getLastTrailPriceUpdatedAt() {
        return lastTrailPriceUpdatedAt;
    }

    public void setLastTrailPriceUpdatedAt(String lastTrailPriceUpdatedAt) {
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

    public String getInvestmentScheduleId() {
        return investmentScheduleId;
    }

    public void setInvestmentScheduleId(String investmentScheduleId) {
        this.investmentScheduleId = investmentScheduleId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", refId='" + refId + '\'' +
                ", url='" + url + '\'' +
                ", account='" + account + '\'' +
                ", position='" + position + '\'' +
                ", cancel='" + cancel + '\'' +
                ", instrument='" + instrument + '\'' +
                ", cumulativeQuantity='" + cumulativeQuantity + '\'' +
                ", averagePrice='" + averagePrice + '\'' +
                ", fees='" + fees + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", trigger='" + trigger + '\'' +
                ", price='" + price + '\'' +
                ", stopPrice='" + stopPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", lastTransactionAt='" + lastTransactionAt + '\'' +
                ", executions=" + executions +
                ", extendedHours='" + extendedHours + '\'' +
                ", overrideDtbpChecks='" + overrideDtbpChecks + '\'' +
                ", overrideDayTradeChecks='" + overrideDayTradeChecks + '\'' +
                ", responseCategory='" + responseCategory + '\'' +
                ", stopTriggeredAt='" + stopTriggeredAt + '\'' +
                ", lastTrailPrice='" + lastTrailPrice + '\'' +
                ", lastTrailPriceUpdatedAt='" + lastTrailPriceUpdatedAt + '\'' +
                ", totalNotional=" + totalNotional +
                ", executedNotional=" + executedNotional +
                ", investmentScheduleId='" + investmentScheduleId + '\'' +
                '}';
    }
}
