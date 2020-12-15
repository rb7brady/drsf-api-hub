package com.drsf.api.robinhood.serializers;

import com.drsf.api.entities.AccountOrder;
import com.drsf.api.entities.AccountOrderBuilder;
import com.drsf.api.robinhood.model.Order;
import com.drsf.api.robinhood.service.RobinhoodProxy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccountOrderSerializer {

    @Autowired
    RobinhoodProxy<Order> robinhoodProxy;

    public static AccountOrder toEntity(Order order) {
        if (order != null) {
            AccountOrder accountOrder = new AccountOrderBuilder().createAccountOrder();
            accountOrder.setBuy(((String)order.getSide()).equalsIgnoreCase("buy"));
            accountOrder.setPrice(order.getPrice()==null?null:Double.parseDouble((String)order.getPrice()));
            accountOrder.setQuantity(order.getQuantity()==null?null:Double.parseDouble((String)order.getQuantity()));
            accountOrder.setType((String) order.getType());
            accountOrder.setTicker((String) order.getInstrument());
            //accountOrder.setTicker((String)((((Mono<LinkedHashMap>)order.getInstrument())).block()).get("ticker"));
            try {
                accountOrder.setSubmitted(order.getCreatedAt()==null?null:Date.from(Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse((String)order.getCreatedAt()))));
            } catch(Exception e) {
                e.printStackTrace();
            }
            return accountOrder;
        }
        return null;
    }

    public static List<AccountOrder> toEntities(List<Order> orders) {
        return orders.stream().map(order -> toEntity(order)).collect(Collectors.toList());
    }

}
