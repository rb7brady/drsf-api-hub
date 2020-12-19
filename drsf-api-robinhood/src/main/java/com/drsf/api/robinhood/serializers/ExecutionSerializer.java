package com.drsf.api.robinhood.serializers;

import com.drsf.api.entities.AccountOrder;
import com.drsf.api.entities.AccountOrderBuilder;
import com.drsf.api.entities.Execution;
import com.drsf.api.robinhood.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExecutionSerializer {


    public static Execution toEntity(com.drsf.api.robinhood.model.Execution execution) {
        if (execution != null) {
            Execution executionEntity = new Execution();

            try {
               executionEntity.setSettlementDate(execution.getSettlementDate()==null?null: new SimpleDateFormat("yyyy-MM-dd").parse((String)execution.getSettlementDate()));
               executionEntity.setTimestamp(execution.getTimestamp()==null?null:Date.from(Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse((String)execution.getTimestamp()))));

            } catch (ParseException e) {
                e.printStackTrace();
            };
            executionEntity.setQuantity(execution.getQuantity()==null?null:Double.parseDouble((String)execution.getQuantity()));
            executionEntity.setPrice(execution.getPrice()==null?null:Double.parseDouble((String)execution.getPrice()));


            return executionEntity;
        }
        return null;
    }

    public static List<Execution> toEntities(List<com.drsf.api.robinhood.model.Execution> executions) {
        return executions.stream().map(e -> toEntity(e)).collect(Collectors.toList());

    }

}
