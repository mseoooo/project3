package com.sboot.sijak.chart.service;
import com.sboot.sijak.last_payment.dto.PaymentDTO;
import com.sboot.sijak.last_payment.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService2Impl implements PaymentService2 {
    
	@Autowired // 여기서 PaymentMapper를 주입
    private PaymentMapper paymentMapper;
	
   @Override
   public Map<String, Integer> getThreeMSales() {
        List<PaymentDTO> salesData2 = paymentMapper.getThreeMSales();
        Map<String, Integer> threeMSales = new HashMap<>();

        // salesData가 null인지 확인
        if (salesData2 == null || salesData2.isEmpty()) {
            return threeMSales; // 빈 맵 반환
        }

        for (PaymentDTO payment : salesData2) {
            if (payment != null && payment.getPurchaseDate() != null) { // null 체크 추가
                // java.util.Date를 LocalDate로 변환
                LocalDate purchaseDate = payment.getPurchaseDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
                
                // "YYYY-MM" 형식으로 변경
                String monthKey = purchaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                int price = payment.getProductPrice();

                // 해당 월에 대한 판매 금액을 합산
                threeMSales.put(monthKey, threeMSales.getOrDefault(monthKey, 0) + price);
            } else {
                System.out.println("Received null paymentDTO or purchaseDate");//확인용
            }
        }
        return threeMSales;
    }
   
   @Override
   public Map<String, Double> getRegionSales() {
       List<PaymentDTO> salesData3 = paymentMapper.getRegionSales();
       Map<String, Integer> regionSalesMap = new HashMap<>();
       int totRegionSales = 0;

       // 각 지역별 판매 금액 합계 계산
       for (PaymentDTO payment : salesData3) {
           if (payment != null && payment.getProductName() != null) {
               String region = getRegionFromProductName(payment.getProductName());
               int price = payment.getProductPrice();

               // 지역별 합계 계산
               regionSalesMap.put(region, regionSalesMap.getOrDefault(region, 0) + price);
               totRegionSales += price; // 전체 판매 금액 누적
           }
       }
       
       // 각 지역의 판매 금액을 전체 금액으로 나누어 비율 계산
       Map<String, Double> regionSalesPercentageMap = new HashMap<>();
       for (Map.Entry<String, Integer> entry : regionSalesMap.entrySet()) {
           String region = entry.getKey();
           double sales = entry.getValue();
           double percentage = (sales / totRegionSales) * 100; // 비율 계산
           regionSalesPercentageMap.put(region, Math.round(percentage * 10.0) / 10.0); // 소수점 두 자리 반올림
       }

       return regionSalesPercentageMap; // 지역별 판매 비율 반환
   }

   // 지역명을 추출하는 메서드
   private String getRegionFromProductName(String productName) {
       if (productName.contains("경상도")) {
           return "경상도";
       } else if (productName.contains("경기도")) {
           return "경기도";
       } else if (productName.contains("강원도")) {
           return "강원도";
       } else if (productName.contains("충청도")) {
           return "충청도";
       } else if (productName.contains("서울")) {
           return "서울";
       } else if (productName.contains("제주도")) {
           return "제주도";
       } else if (productName.contains("전라도")) {
           return "전라도";
       } else {
           return "기타";
       }
   }
}
