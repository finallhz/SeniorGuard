$.get('tables/ECGSample.json', function (data) {

var totalFlowRate = echarts.init(document.getElementById('totalFlowRate'));
var xAxisData = [];
var yAxisData = [];
var num = 0;

for (var i = 500; i > 0; i--) {
    xAxisData.push("0:00.000");
}
for (i = 1; i < 501; i++) {
    yAxisData.push(null);
}

function addData(shift) {
    xAxisData.push(data[num]["Elapsed time"]);
    yAxisData.push(data[num]["MLII"]);
    if (shift){
        xAxisData.shift();
        yAxisData.shift();
    }
    num++;
}


var totalFlowRateOption = {
    animation: false,
    title: {
        text: 'MLII'
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
        boundaryGap: false,
        data: xAxisData
    },
    yAxis: {
        boundaryGap: false,
        max: 1.3,   //y轴最大值
        min: -1     //y轴最小值
    },
    series: {
        symbol: "none",  /*去掉小圆点*/
        name: '当前流量',
        type: 'line',
        data: yAxisData
        /*smooth:true    //显示为平滑的曲线*/
    }
};

totalFlowRate.setOption(totalFlowRateOption);

setInterval(function() {
    addData(true);
    totalFlowRate.setOption(totalFlowRateOption);
}, 50);

}, 'json');
