$(function () {
    // 获取websocket数据

    function wsk() {

        var websocket = null;
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://localhost:8080/FallDetection/ws/listen");
        }
        else {
            alert('当前浏览器 Not support websocket');
            return;
        }

        //连接发生错误的回调方法
        websocket.onerror = function () {
            console.log("WebSocket连接发生错误");
        };

        //连接成功建立的回调方法
        websocket.onopen = function () {
            console.log("WebSocket连接成功");
        };

        //连接关闭的回调方法
        websocket.onclose = function () {
            console.log("WebSocket连接关闭");
        };

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
            clearInterval(timer01);
        };

        $('#btclo').click(function () {
            closeWebSocket();
        });

        //关闭WebSocket连接
        function closeWebSocket() {
            websocket.close();
        }


        var accChart = echarts.init(document.getElementById('line-chart-acc'));
        var gyrChart = echarts.init(document.getElementById('line-chart-gyr'));
        var acc = [],
            gyr = [];
        var stime = [];  //x轴

        for (var i = 0; i < 30; i++) {
            acc.push(null);
            gyr.push(null);
            stime.push(" ");
        }

        var acc30 = acc.slice(0,30),
            gyr30 = gyr.slice(0,30),
            stime30 = stime.slice(0,30);

        function setChart() {
            var option1 = {
                animation: false,
                title: {
                    text: '加 速 度',
                    left: 'center'
                    /*left:"110px"*/
                },
                tooltip: {
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
                    data: stime30
                },
                yAxis: {
                    boundaryGap: false,
                    max: 16,   //y轴最大值
                    min: 6     //y轴最小值
                    // splitNumber: 11     //11根刻度线，就是分成10等分
                },
                series: {
                    name: '加速度',
                    type: 'line',
                    smooth: true,   //变成曲线图
                    symbol: 'none', //去除坐标点
                    data: acc30,
                    areaStyle: {}   //给图片下方加阴影
                }
            };
            accChart.setOption(option1);
            var option2 = {
                animation: false,
                title: {
                    text: '角 速 度',
                    left: 'center'
                },
                // visualMap: {
                //     show : true,
                //     left: 20,
                //     top: 25,
                //     bottom: 20
                // },
                legend: {
                    data: ['当前流量']
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: stime30
                },
                yAxis: {
                    boundaryGap: false,
                    max: 1,   //y轴最大值
                    min: 0     //y轴最小值
                    // splitNumber: 11     //11根刻度线，就是分成10等分
                },
                series: {
                    // name: '单位：m/s ',
                    type: 'line',
                    smooth: true,
                    symbol: 'none',
                    data: gyr30,
                    areaStyle: {}
                }
            };
            gyrChart.setOption(option2);
        }
        setChart();

        var fallStatus = false;
        // 更改运动状态
        function changeStatus(f) {
            if (f){
                $('#fall_status').html('摔倒');
            } else {
                $('#fall_status').html('行走');
            }
        }

        // 控制摔倒多停一会儿
        var flag01 = false;

        //接收到消息的回调方法
        websocket.onmessage = function (event) {

            // 步数
            var step = JSON.parse(event.data).stepCount;
            $('#fall_step').html(step);

            // 摔倒数据
            var status = JSON.parse(event.data).status;
            if (status == 0){
                // 行走
                fallStatus = false;
            } else {
                // 摔倒
                fallStatus = true;
            }
            // 如果是摔倒，就停止3秒，3秒后恢复
            if (fallStatus) {
                flag01 = true;
                setTimeout(function () {
                    flag01 = fallStatus;
                },3000);
                $.post('/FallDetection/addFallData');
            }

            // 加速度角速度图
            var sportDatas = JSON.parse(event.data).sportDatas;
            for (var i=0; i<sportDatas.length; i++){
                acc.push(sportDatas[i].acc);
                gyr.push(sportDatas[i].gyr);
                var date = new Date();
                var hour = date.getHours();
                var minu = date.getMinutes();
                var sec = date.getSeconds();
                var time = hour+'时'+minu+'分'+sec+'秒';
                stime.push(time);
            }

        };

        function changeData() {
            // 如果acc长度小于30，说明数据量不够，停止图表刷新
            if (acc.length < 30){
                return;
            }

            acc30 = acc.slice(0,30);
            gyr30 = gyr.slice(0,30);
            stime30 = stime.slice(0,30);
            acc.shift();
            gyr.shift();
            stime.shift();
        }
        var timer01 = setInterval(function () {
            changeData();
            setChart();
            changeStatus(flag01);
        }, 200);

    }
    wsk()

});