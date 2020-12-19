package com.drsf.api.entities;

import net.bytebuddy.dynamic.scaffold.MethodGraph;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
public class AccountOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    protected LinkedAccount linkedAccount;
    protected String ticker;
    protected Boolean buy;
    protected String type;
    protected Double price;
    protected Double quantity;
    protected Date submitted;
    protected String tif;
    @OneToMany(cascade=ALL, mappedBy="accountOrder")
    protected Set<Execution> executions;

    public AccountOrder() {

    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public LinkedAccount getLinkedAccount() {
        return linkedAccount;
    }

    public void setLinkedAccount(LinkedAccount linkedAccount) {
        this.linkedAccount = linkedAccount;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }

    public String getTif() {
        return tif;
    }

    public void setTif(String tif) {
        this.tif = tif;
    }

//    public List<Execution> getExecutions() {
//        return executions;
//    }
//
//    public void setExecutions(List<Execution> executions) {
//        this.executions = executions;
//    }

    public AccountOrder(long id, LinkedAccount linkedAccount, String ticker, Boolean buy, String type, Double price, Double quantity, Date submitted, String tif) {
        Id = id;
        this.linkedAccount = linkedAccount;
        this.ticker = ticker;
        this.buy = buy;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.submitted = submitted;
        this.tif = tif;
    }
}
