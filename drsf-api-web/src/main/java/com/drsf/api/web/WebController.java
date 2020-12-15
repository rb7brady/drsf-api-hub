package com.drsf.api.web;

import com.drsf.api.entities.AccountOrder;
import com.drsf.api.postgres.repositories.AccountOrderRepository;
import com.drsf.api.postgres.repositories.LinkedAccountRepository;
import com.drsf.api.repositories.CompanyRepository;
import com.drsf.api.repositories.DividendsRepository;
import com.drsf.api.repositories.FinancialsRepository;
import com.drsf.api.repositories.OrdersRepository;
import com.drsf.api.robinhood.model.Order;
import com.drsf.api.robinhood.serializers.AccountOrderSerializer;
import com.dsrf.api.meta.HttpQueryMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {
    //TODO put this into an ApplicationContext, and remove from parameter altogether.
    public static final String username = "";

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    FinancialsRepository financialsRepository;

    @Autowired
    DividendsRepository dividendsRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    AccountOrderRepository accountOrderRepository;

    @Autowired
    LinkedAccountRepository linkedAccountRepository;

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
//    @GetMapping(value="/financials")
//    public String refreshFinancials(@RequestParam(value = "sym") String sym) {
//        return financialsRepository.query(username, sym, null, null, null).toString();
//    }
//    @GetMapping(value="/financials")
//    public String refreshFinancials(@RequestParam(value = "sym") String sym, @RequestParam(value = "limit") String limit) {
//        return financialsRepository.query(username, sym, limit, null, null).toString();
//    }
//    @GetMapping("/financials")
//    public String refreshFinancials(@RequestParam(value = "sym") String sym, @RequestParam(value = "limit") String limit, @RequestParam(value = "type") String type) {
//        return financialsRepository.query(username, sym, limit, type, null).toString();
//    }
    @GetMapping("/financials")
    public String refreshFinancials(@RequestParam(value = "sym") String sym, @RequestParam(value = "limit", required = false) String limit, @RequestParam(value = "type", required = false) String type,@RequestParam(value = "sort",required = false) String sort) {
        return financialsRepository.query(username, sym, limit, type, sort).toString();
    }

    /**
     *ROBINHOOD QUERIES
     */

    @GetMapping("/orders")
    public void refreshOrders(@RequestParam(value = "username") String username, @RequestParam(value = "createdAt", required = false) String createdAt) {
        //List<Object> orders = ordersRepository.findAll(username, new HttpQueryMeta().putParameterizedOptionalNullExcluded("createdAt", createdAt));

        ordersRepository
                .findEnrichedOrders(username,new HttpQueryMeta().putParameterizedOptionalNullExcluded("createdAt", createdAt))
                .log("Flux Log: ")
                .subscribe(oe -> {
                            System.out.println("Subscription Started");
                            //System.out.println(oe.getInstrument());
                    accountOrderRepository.save(oe);
                },
                        oe -> System.out.println("ERROR Encountered" + oe.toString()),
                        () -> System.out.println("SUCCESS"));


     //   ordersRepository.findAccountOrders(username,new HttpQueryMeta().putParameterizedOptionalNullExcluded("createdAt", createdAt))
               // .subscribe(a -> System.out.println(a.toString()));
//            AccountOrder accountOrder = AccountOrderSerializer.toEntity(order);
//            if (accountOrder != null) {
//                accountOrderRepository.save(accountOrder);
//            }
//        }
        //return null;
    }



}
