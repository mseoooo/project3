package com.sboot.sijak.chart.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sboot.sijak.chart.service.PaymentService2;

@RestController
@RequestMapping("/chart") //chart, api
public class ChartController {

    @Autowired
    private PaymentService2 paymentService2;

    @PostMapping("/threemsales")
    public Map<String, Integer> getThreeMSales() {
        return paymentService2.getThreeMSales();
    }
    
    @PostMapping("/regionsales")
    public Map<String, Double> getRegionSales() {
        return paymentService2.getRegionSales();
    }
    
}