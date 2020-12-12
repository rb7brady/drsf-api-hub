package com.drsf.api.entities;

import com.drsf.api.service.IDividendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class MyController {

    @Autowired
    private IDividendService dividendService;

    public List<Dividend> findDividends() {
        return (List<Dividend>) dividendService.findAll();
    }
}
