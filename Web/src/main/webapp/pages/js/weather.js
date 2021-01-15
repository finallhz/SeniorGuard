jsonp({
    url: 'https://wis.qq.com/weather/common',
    data: {
        source: 'pc',
        weather_type: 'forecast_24h',
        province: '陕西省',
        city: '西安市'
    },
    success: function (data) {
        // console.log(data.data.forecast_24h);
        var whe = data.data.forecast_24h;
        var con = '';
        for (key in whe){
            con += '<tr>\n' +
                '<td>'+ whe[key].time +'</td>\n' +
                '<td>'+ whe[key].day_weather +'</td>\n' +
                '<td>'+ whe[key].max_degree + '°/' + whe[key].min_degree+ '°' +'</td>\n' +
                '<td>'+ whe[key].day_wind_direction +'</td>\n' +
                '</tr>';
        }
        $('#weather tbody').html(con);
        // var timed = [],
        //     adywhe = [],
        //     nigthe = [],
        //     maxd = [],
        //     mind = [],
        //     winddir = [],
        //     windpow = [];
        //
        // for (key in whe){
        //     timed.push(whe[key].time);
        //     adywhe.push(whe[key].day_weather);
        //     nigthe.push(whe[key].night_weather);
        //     maxd.push(whe[key].max_degree);
        //     mind.push(whe[key].min_degree );
        //     winddir.push(whe[key].day_wind_direction);
        //     windpow.push(whe[key].day_wind_power);
        // }
        //
        // // console.log(maxd);
        // var option3 = {
        //     // title: {
        //     //     text: '七日天气预报'
        //     // },
        //     // tooltip: {
        //     //     trigger: 'axis'
        //     // },
        //     grid: {
        //         left: '3%',
        //         right: '4%',
        //         bottom: '3%',
        //         containLabel: true
        //     },
        //     // toolbox: {
        //     //     feature: {
        //     //         saveAsImage: {}
        //     //     }
        //     // },
        //     xAxis: {
        //         type: 'category',
        //         boundaryGap: false,
        //         data: timed,
        //         show:false
        //     },
        //     yAxis: {
        //         type: 'value',
        //         show:false
        //     },
        //     series: [
        //         {
        //             type: 'line',
        //             data: maxd,
        //             symbol: 'circle',     //设定为实心点
        //             symbolSize: 5,   //设定实心点的大小
        //             itemStyle: {
        //                 normal: {
        //                     label: {
        //                         show: true, //开启显示
        //                         position: 'top', //在上方显示
        //                         textStyle: { //数值样式
        //                             color: '#666',
        //                             fontSize: 16
        //                         }
        //                     },
        //                     lineStyle: {
        //                         color: "#FCC370"//折线的颜色
        //                     }
        //                 }
        //             }
        //         },
        //         {
        //             type: 'line',
        //             data: mind,
        //             symbol: 'circle',     //设定为实心点
        //             symbolSize: 5,   //设定实心点的大小
        //             itemStyle: {
        //                 normal: {
        //                     label: {
        //                         show: true, //开启显示
        //                         position: 'bottom', //在上方显示
        //                         textStyle: { //数值样式
        //                             color: '#666',
        //                             fontSize: 16
        //                         }
        //                     },
        //                     lineStyle: {
        //                         color: "#94CCF9"//折线的颜色
        //                     }
        //                 }
        //             }
        //         }
        //     ]
        // };
        // var weatherChart = echarts.init(document.getElementById('weather_chart'));
        // weatherChart.setOption(option3);
        //
        // var li01 = '';
        // timed.forEach(function (item,index,array) {
        //     li01 += '<li>'+ item.substr(5,2) + '月' + item.substr(8,2)+ '日'+'</li>'
        // })
        // $('#weather_time').html(li01);

    }
});