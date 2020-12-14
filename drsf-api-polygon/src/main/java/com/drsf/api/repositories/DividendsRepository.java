package com.drsf.api.repositories;

import com.drsf.api.entities.LinkedAccount;
import com.drsf.api.polygon.config.PolygonEndpoint;
import com.dsrf.api.meta.HttpQueryMeta;
import org.springframework.stereotype.Repository;

@Repository
public class DividendsRepository extends BaseRepository {

    public Object query(String username, String sym) {

        LinkedAccount linkedAccount = linkedAccountRepository.findByUsernameAndType(username, "ALPACA");

        if (linkedAccount != null) {
            HttpQueryMeta query = new HttpQueryMeta();
            query.putBaseEndpoint(PolygonEndpoint.getBaseUrl());
            query.putParamaterizedURI(PolygonEndpoint.DIVIDENDS.getURI());
            query.putURIParamValue(sym);
            query.putParameterizedOptional("apiKey", linkedAccount.getSimpleToken());

            return polygonProxy.queryAsMono(query).block();
        }
        return null;
    }


}
