package com.drsf.api.repositories;

import com.drsf.api.entities.LinkedAccount;
import com.drsf.api.polygon.config.PolygonEndpoint;
import com.dsrf.api.meta.HttpQueryMeta;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialsRepository extends BaseRepository {

    public Object query(String username, String sym, String limit, String type, String sort) {

        LinkedAccount linkedAccount = linkedAccountRepository.findByUsernameAndType(username, "ALPACA");

        if (linkedAccount != null) {
            HttpQueryMeta query = new HttpQueryMeta();
            query.putBaseEndpoint(PolygonEndpoint.getBaseUrl());
            query.putParamaterizedURI(PolygonEndpoint.FINANCIALS.getURI());
            query.putURIParamValue(sym);
            query.putParameterizedOptional("limit", limit);
            if(type!=null) {
                query.putParameterizedOptional("type", type);
            }
            if (sort!=null) {
                query.putParameterizedOptional("sort", sort);
            }
            query.putParameterizedOptional("apiKey", linkedAccount.getSimpleToken());

            return polygonProxy.queryAsMono(query).block();
        }

        return null;


    }


}
