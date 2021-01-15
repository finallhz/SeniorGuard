$(function () {

    var uidck;
    function  getcookie() {
        uidck = $.cookie('uidCookie');
    }
    getcookie();
    // console.log(uidck);

    $.post(
        '/FallDetection/getHeartAndStep',
        {
            uid: uidck
        },
        function (data) {
            // console.log(typeof data);
            var sz = data.substring(1,data.length-1).split('];[');
            var hearts = sz[0].split(',');
            var steps0 = sz[1].split(',');
            var steps = [];
            var times = [];
            steps0.forEach(function (item, index, array) {
                var t = item.split('%');
                u = t[1];
                steps.push(u);
                var v = t[0];
                times.push(v);
            });
            // console.log(hearts);
            // console.log(steps);
            // console.log(times);

            option1 = {
                title: {
                    text: '最近7次监测心跳',
                    left: 'center'
                },
                xAxis: {
                    type: 'category',
                    data: ['1', '2', '3', '4', '5', '6', '7']
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: false,
                    max: 120,   //y轴最大值
                    min: 0     //y轴最小值
                },
                series: [{
                    data: hearts,
                    type: 'line'
                }]
            };
            var heartChart = echarts.init(document.getElementById('heart_chart'));
            heartChart.setOption(option1);

            option2 = {
                title: {
                    text: '最近7天步数',
                    left: 'center'
                },
                xAxis: {
                    type: 'category',
                    data: times
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: false,
                    // max: 120,   //y轴最大值
                    min: 0     //y轴最小值
                },
                series: [{
                    data: steps,
                    type: 'line'
                }]
            };
            var stepChart = echarts.init(document.getElementById('step_chart'));
            stepChart.setOption(option2);













        });

    $(window).unload(function () {

        var heart = $('#ecg_heart').html();
        var step = $('#ecg_step').html();
        // console.log('-------------');
        // console.log(heart);
        // console.log(step);

        $.post(
            '/FallDetection/saveProp',
            {
                uid: uidck,
                heart: heart,
                step: step
            },
            function (data) {
                // console.log(data);
            });
    });

    
    
});