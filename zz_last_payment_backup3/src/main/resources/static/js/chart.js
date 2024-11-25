window.onload = async function() {
    try {
        const threeMSales = await fetchThreeMSales(); // 3개월 판매 데이터 가져오기
        drawThreeMSalesChart(threeMSales); // 첫 번째 차트 그리기

        const regionSales = await fetchRegionSales(); // 지역별 판매 데이터 가져오기
        drawRegionSalesChart(regionSales); // 두 번째 차트 그리기
    } catch (error) {
        console.error('Error fetching sales data:', error);
        alert('Error fetching sales data: ' + error.message);
    }
};

// 3개월 판매 데이터 가져오기 (POST 방식)
async function fetchThreeMSales() {
    const response = await fetch('/chart/threemsales', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return response.json(); // JSON 형식으로 변환하여 반환
}

// 지역별 판매 데이터 가져오기 (POST 방식)
async function fetchRegionSales() {
    const response = await fetch('/chart/regionsales', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return response.json(); // JSON 형식으로 변환하여 반환
}

// 3개월 판매 데이터 차트 그리기
function drawThreeMSalesChart(data) {
    const categories = Object.keys(data).reverse();
    const salesData = Object.values(data).reverse();

    Highcharts.chart('sales-chart', {
        chart: {
            type: 'column'
        },
        title: {
            text: '3개월간의 판매추이'
        },
        xAxis: {
            categories: categories,
            title: {
                text: '년-월'
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '월별 판매금액'
            }
        },
        series: [{
            name: '판매금액(원)',
            data: salesData,
			color: '#FF5E00' // 오렌지색으로 변경
        }]
    });
}

// 지역별 판매 데이터 차트 그리기
function drawRegionSalesChart(data) {
    const regions = Object.keys(data);
    const salesData = Object.values(data);

    Highcharts.chart('region-sales-chart', {
        chart: {
            type: 'pie'
        },
        title: {
            text: '지난 달 지역별 판매 데이터'
        },
        series: [{
            name: 'Sales',
            colorByPoint: true,
            data: regions.map((region, index) => ({
                name: region,
                y: salesData[index]
            }))
        }]
    });
}
