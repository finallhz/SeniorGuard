// 年龄
var ageChart = echarts.init(document.getElementById('age_body'));
var option1 = {
    title: {
        text: '用户年龄统计',
        left: 'center',
        top: 10
    },
    tooltip: {
        // trigger: 'item',
        formatter: '{b} : {c}'+'人'
    },
    xAxis: {
        type: 'category',
        data: ['50岁以下', '50~59岁', '60~69岁', '70~79岁', '80~89岁', '90岁以上']
    },
    yAxis: {
        type: 'value',
        name: '人数/人'

    },
    series: [{
        data: [88, 204, 129,63, 56, 1],
        type: 'bar'
    }]
};
ageChart.setOption(option1);

// 性别
var sexChart = echarts.init(document.getElementById('sex_body'));
var option2 = {
    title: {
        text: '用户性别统计',
        left: 'center',
        top: 10
    },
    tooltip: {
        // trigger: 'item',
        formatter: '{a} <br/>{b} : {c} '+ '人' +'({d}%)'
    },
    legend: {
        orient: 'vertical',
        // left: 'left',
        right: 10,
        top: 25,
        bottom: 20,
        data: ['男性', '女性']
    },
    series: [
        {
            name: '性别',
            type: 'pie',
            radius: '65%',
            center: ['50%', '60%'],
            data: [
                {value: 235, name: '男性'},
                {value: 306, name: '女性'}
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
sexChart.setOption(option2);

// 城市
var cityChart = echarts.init(document.getElementById('city_body'));
var dataMap =[
    {name: '北京',value: 26 },{name: '天津',value: 10 },
    {name: '上海',value: 35 },{name: '重庆',value: 5 },
    {name: '河北',value: 21 },{name: '河南',value: 31 },
    {name: '云南',value: 12 },{name: '辽宁',value: 21 },
    {name: '黑龙江',value: 5 },{name: '湖南',value: 34 },
    {name: '安徽',value: 28 },{name: '山东',value: 12 },
    {name: '新疆',value: 1 },{name: '江苏',value: 24 },
    {name: '浙江',value: 32 },{name: '江西',value: 13 },
    {name: '湖北',value: 15 },{name: '广西',value: 7 },
    {name: '甘肃',value: 8 },{name: '山西',value: 11 },
    {name: '内蒙古',value: 2 },{name: '陕西',value: 84 },
    {name: '吉林',value: 21 },{name: '福建',value: 11 },
    {name: '贵州',value: 14 },{name: '广东',value: 11 },
    {name: '青海',value: 7 },{name: '西藏',value: 5 },
    {name: '四川',value: 6 },{name: '宁夏',value: 4 },
    {name: '海南',value: 9 },{name: '台湾',value: 1 },
    {name: '香港',value: 3 },{name: '澳门',value: 2 },{name: '南海诸岛',value: 0 }
];
// 需要在页面上直接标记出来的城市
var specialMap = [];
// 对dataMap进行处理，使其可以直接在页面上展示
for (var i = 0; i < specialMap.length; i++) {
    for (var j = 0; j < dataMap.length; j++) {
        if (specialMap[i] == dataMap[j].name) {
            dataMap[j].selected = true;
            break;
        }

    }
}
var option3 = {
    title: {
        text: '用户省份统计',
        left: 'center',
        top: 10
    },
    tooltip: {
        formatter: function (params) {
            var info1 = '<p style="font-size:18px; color: #fff">' + params.name
                + '</p><p style="font-size:14px; color: #fff"">用户数:'+params.value+'人'+'</p>';
            return info1;
        },
        backgroundColor: "#385373"//提示标签背景颜色
        // textStyle: { color: "#fff" } //提示标签字体颜色
    },
//左侧小导航图标
    visualMap: {
        show : true,
        left: 20,
        top: 25,
        bottom: 20,
        splitList: [
            {start: 50, end:100},{start: 35, end: 45},
            {start: 25, end: 35},{start: 15, end: 25},
            {start: 5, end: 15},{start: 0, end: 5}
        ],
        color: ['#0D47A1', '#1976D2', '#2196F3','#64B5F6', '#BBDEFB', '#E3F2FD']
    },
    series: [
        {
            name: '中国',
            type: 'map',
            mapType: 'china',

            label: {
                normal: {
                    show: true//显示省份标签
                },
                emphasis: {
                    show: true//对应的鼠标悬浮效果
                }
            },

            data: dataMap
        }
    ]
};
cityChart.setOption(option3);

// 睡眠时长
var sleepChart = echarts.init(document.getElementById('sleep_body'));
var hours = ['5', '5.5', '6', '6.5', '7', '7.5', '8',
    '8.5', '9', '9.5','10','10.5',
    '11', '11.5', '12'];
var ages = ['50以下', '50~59', '60~69',
    '70~79', '80~89', '90以上'];
var data = [[0,0,0],[0,1,1],[0,2,5],[0,3,6],[0,4,11], [0,5,12],[0,6,13],[0,7,9],
    [0,8,12],[0,9,11],[0,10,6], [0,11,2],[0,12,4],[0,13,1],[0,14,1],
    [1,0,1],[1,1,2],[1,2,5],[1,3,7],[1,4,12],[1,5,13],[1,6,12],[1,7,10],
    [1,8,9], [1,9,9],[1,10,5],[1,11,2],[1,12,2],[1,13,1],[1,14,2],
    [2,0,1],[2,1,1],[2,2,6],[2,3,11],[2,4,15],[2,5,16],[2,6,18], [2,7,12],
    [2,8,10],[2,9,5],[2,10,3],[2,11,2],[2,12,1],[2,13,0],[2,14,0],
    [3,0,2],[3,1,3],[3,2,7],[3,3,14],[3,4,19],[3,5,13],[3,6,17],[3,7,10],
    [3,8,10],[3,9,4],[3,10,5], [3,11,4],[3,12,7],[3,13,1],[3,14,0],
    [4,0,3],[4,1,5],[4,2,5],[4,3,10],[4,4,12],[4,5,10],[4,6,15],[4,7,8],
    [4,8,9],[4,9,5],[4,10,5], [4,11,4],[4,12,5],[4,13,1],[4,14,1],
    [5,0,0],[5,1,3],[5,2,2],[5,3,2],[5,4,0],[5,5,1],[5,6,2],[5,7,2],[5,8,1],
    [5,9,2],[5,10,0], [5,11,4],[5,12,1],[5,13,1],[5,14,0]];
var option4 = {
    title: {
        text: '用户睡眠时长统计',
        left: 'center',
        top: 10
    },
    visualMap: {
        max: 20,
        inRange: {
            color: ['#313695', '#4575b4', '#74add1', '#abd9e9', '#e0f3f8', '#ffffbf', '#fee090', '#fdae61', '#f46d43', '#d73027', '#a50026']
        }
    },
    xAxis3D: {
        type: 'category',
        data: hours,
        name: '睡眠时长/小时'
    },
    yAxis3D: {
        type: 'category',
        data: ages,
        name: '年龄/岁'
    },
    zAxis3D: {
        type: 'value',
        name: '人数/人'
    },
    grid3D: {
        boxWidth: 200,
        boxDepth: 80,
        viewControl: {
            // projection: 'orthographic'
        },
        light: {
            main: {
                intensity: 1.2,
                shadow: true
            },
            ambient: {
                intensity: 0.3
            }
        }
    },
    series: [{
        type: 'bar3D',
        data: data.map(function (item) {
            return {
                value: [item[1], item[0], item[2]]
            }
        }),
        shading: 'lambert',

        label: {
            textStyle: {
                fontSize: 16,
                borderWidth: 1
            }
        },

        emphasis: {
            label: {
                textStyle: {
                    fontSize: 20,
                    color: '#900'
                }
            },
            itemStyle: {
                color: '#900'
            }
        }
    }]
};
sleepChart.setOption(option4);