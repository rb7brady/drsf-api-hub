package com.drsf.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path="/psql")
public class MyController {

//    @Autowired
//    private DividendRepository repository;
//
//    @GetMapping(path="/all")
//    public List<Dividend> findDividends() {
//        return test();
//       // return (List<Dividend>) repository.findAll();
//    }
////
//    @GetMapping(path="/add")
//    public void addDividend() {
//        Dividend d = new Dividend();
//        d.setAmount(50);
//        d.setDeclaredDate(new Date());
//        d.setExDate(new Date());
//        d.setPaymentDate(new Date());
//        d.setTicker("AAPL");
//        //dividendRepository.save(d);
//    }

//    public void test(JpaRepository<? extends SecurityBase, Long> repo) {
//        Dividend d = new Dividend();
//        d.setAmount(50);
//        d.setDeclaredDate(new Date());
//        d.setExDate(new Date());
//        d.setPaymentDate(new Date());
//        d.setTicker("AAPL");
//        repo.findAll();
//        //repo.save(d);
//    }

}
