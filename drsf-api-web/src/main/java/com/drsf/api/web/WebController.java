package com.drsf.api.web;

import com.drsf.api.entities.AccountOrder;
import com.drsf.api.entities.Execution;
import com.drsf.api.postgres.repositories.AccountOrderRepository;
import com.drsf.api.postgres.repositories.ExecutionRepository;
import com.drsf.api.postgres.repositories.LinkedAccountRepository;
import com.drsf.api.repositories.CompanyRepository;
import com.drsf.api.repositories.DividendsRepository;
import com.drsf.api.repositories.FinancialsRepository;
import com.drsf.api.repositories.OrdersRepository;
import com.drsf.api.robinhood.model.Order;
import com.drsf.api.robinhood.serializers.AccountOrderSerializer;
import com.drsf.api.robinhood.serializers.ExecutionSerializer;
import com.dsrf.api.meta.HttpQueryMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class WebController {
    //TODO put this into an ApplicationContext, and remove from parameter altogether.
    public static final String username = "";

    //Repositories for external rest APIs. (Polygon, Robinhood etc..)
    @Autowired CompanyRepository companyRepository;
    @Autowired FinancialsRepository financialsRepository;
    @Autowired DividendsRepository dividendsRepository;
    @Autowired OrdersRepository ordersRepository;

    //ORM Repositories
    @Autowired AccountOrderRepository accountOrderRepository;
    @Autowired LinkedAccountRepository linkedAccountRepository;
    @Autowired ExecutionRepository executionRepository;
    /**
     *POLYGON QUERIES
     */

    @GetMapping("/company")
    public String getCompanyDetail(@RequestParam(value = "sym") String sym) {
        return companyRepository.query(username, sym).toString();
    }
    @GetMapping("/dividends")
    public String refreshDividends(@RequestParam(value = "sym") String sym) {
        return dividendsRepository.query(username, sym).toString();
    }

    @GetMapping("/financials")
    public String refreshFinancials(@RequestParam(value = "sym") String sym, @RequestParam(value = "limit", required = false) String limit, @RequestParam(value = "type", required = false) String type,@RequestParam(value = "sort",required = false) String sort) {
        return financialsRepository.query(username, sym, limit, type, sort).toString();
    }

    /**
     *ROBINHOOD QUERIES
     */

    @GetMapping("/orders")
    public void refreshOrders(@RequestParam(value = "username") String username, @RequestParam(value = "createdAt", required = false) String createdAt) {
        Flux<Order> orders = ordersRepository.findAllWithInstruments(username, new HttpQueryMeta().putParameterizedOptionalNullExcluded("createdAt", createdAt));

        orders.subscribe(o -> {
            AccountOrder accountOrder = AccountOrderSerializer.toEntity(o);
            accountOrderRepository.save(accountOrder);

            List<Execution> executions = ExecutionSerializer.toEntities(o.getExecutions());
            executions.forEach(e -> e.setAccountOrder(accountOrder));
            executionRepository.saveAll(executions);
        });

    }



}
