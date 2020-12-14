package com.drsf.api.repositories;

import com.drsf.api.entities.LinkedAccount;
import com.drsf.api.robinhood.Endpoint;
import com.drsf.api.robinhood.model.Order;
import com.drsf.api.robinhood.model.PaginatedRobinhoodHTTPResponse;
import com.drsf.api.robinhood.service.RobinhoodProxy;
import com.dsrf.api.meta.HttpQueryMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class OrdersRepository extends BaseRobinhoodRepository {


    LinkedAccount linkedAccount = null;

    @Autowired
    RobinhoodProxy<PaginatedRobinhoodHTTPResponse> robinhoodProxy;

    public Object query(String username, String createdAt) {
         linkedAccount = linkedAccountRepository.findByUsernameAndType(username, "ROBINHOOD");

         HttpQueryMeta query = new HttpQueryMeta();

         query.putBaseEndpoint(Endpoint.getBaseUrl());
         query.putParamaterizedURI(Endpoint.ORDERS.getURI());
         query.putHeader("Authorization", "bearer " + linkedAccount.getBearerToken());
         query.putExpectedClass(PaginatedRobinhoodHTTPResponse.class);
         if (createdAt != null) {
             query.putParameterizedOptional("createdAt", createdAt);
         }

         PaginatedRobinhoodHTTPResponse<Order> response = (PaginatedRobinhoodHTTPResponse) robinhoodProxy.queryAsMono(query).block();
         boolean hasNextPage = response != null && response.getNext() != null;

        List<Order> allOrders = response.getResults();

        while (hasNextPage) {
            query = new HttpQueryMeta();
            query.putBaseEndpoint(response.getNext());
            query.putHeader("Authorization", "bearer " + linkedAccount.getBearerToken());
            query.putExpectedClass(PaginatedRobinhoodHTTPResponse.class);
            response = (PaginatedRobinhoodHTTPResponse)  paginatedQuery(query);
             allOrders.addAll(response.getResults());
             hasNextPage = response.getNext() != null && !"".equals(response.getNext());
         }

         return allOrders;

     }

    public Object paginatedQuery(HttpQueryMeta query) {


        return robinhoodProxy.queryAsMono(query).block();

    }

}
