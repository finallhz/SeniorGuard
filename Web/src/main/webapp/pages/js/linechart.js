$.get('tables/data0807.json', function (data) {

    var sacc = [];   //第一张图y轴
    var sgyr = [];   //第二张图y轴
    var stime = [];  //x轴
    var num = 0;

    for (var i = 500; i > 0; i--) {
        stime.push("0000-00-00 00:00:00");
    }
    for (i = 1; i < 501; i++) {
        sacc.push(null);
        sgyr.push(null);
    }

    function addData(shift) {
        sacc.push(data[num].acc);
        // sacc.push(data[num]["acc"]);     //这样写也行
        sgyr.push(data[num].gyr);
        stime.push(data[num].timestamp);

        if (shift){
            sacc.shift();
            sgyr.shift();
            stime.shift();
        }

        num++;
    }

    var option1 = {
        animation: false,
        title: {
            text: '加速度'
            /*left:"110px"*/
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            }
        },
        grid: {
            left: 50 /*"50px"*/ ,
            right: 15 /*"15px"*/
        },
        legend: {
            data: ['当前流量']
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: stime
        },
        yAxis: {
            boundaryGap: false,
            max: 0.25,   //y轴最大值
<<<<<<< HEAD
            min: 0,     //y轴最小值
=======
<<<<<<< HEAD
            min: 0,     //y轴最小值
=======
            min: 0     //y轴最小值
>>>>>>> 4bf2210... 页面更新
>>>>>>> 6dae2d0... lyy commit
            // splitNumber: 11     //11根刻度线，就是分成10等分
        },
        series: {
            name: '加速度',
            type: 'line',
            smooth: true,   //变成曲线图
            symbol: 'none', //去除坐标点
            data: sacc
        }

    };

    setInterval(function () {
       addData(true);
       myChart1.setOption(option1);
    }, 50);


    var option2 = {
        animation: false,
        title: {
            text: '角速度'
            /*left:"110px"*/
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            }
        },
        grid: {
            left: 50 /*"50px"*/ ,
            right: 15 /*"15px"*/
        },
        legend: {
            data: ['当前流量']
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: stime
        },
        yAxis: {
            boundaryGap: false,
            max: 20,   //y轴最大值
            min: 0,     //y轴最小值
            // splitNumber: 11     //11根刻度线，就是分成10等分
        },
        series: {
            name: '角速度',
            type: 'line',
            smooth: true,   //变成曲线图
            symbol: 'none', //去除坐标点
            data: sgyr
        }

    };

    setInterval(function () {
        addData(true);
        myChart2.setOption(option2);
    }, 50);


    var myChart1 = echarts.init(document.getElementById('line-chart-acc'));
    var myChart2 = echarts.init(document.getElementById('line-chart-gyr'));
    myChart1.setOption(option1);
    myChart2.setOption(option2);

}, 'json');
