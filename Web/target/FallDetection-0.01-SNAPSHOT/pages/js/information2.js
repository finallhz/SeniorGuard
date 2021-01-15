$(function () {

    var option1 = {
        title: {
            text: '饮食偏好分析'
        },
        radar: [
            {
                indicator: [
                    { text: '补血' },
                    { text: '养胃' },
                    { text: '生津' },
                    { text: '安神' },
                    { text: '健脾' }
                ],
                // center: ['25%', '50%'],
                radius: 120,
                startAngle: 90,
                splitNumber: 4,
                shape: 'circle',
                name: {
                    formatter: '【{value}】',
                    textStyle: {
                        color: '#72ACD1'
                    }
                },
                splitArea: {
                    areaStyle: {
                        color: ['rgba(114, 172, 209, 0.2)',
                            'rgba(114, 172, 209, 0.4)', 'rgba(114, 172, 209, 0.6)',
                            'rgba(114, 172, 209, 0.8)', 'rgba(114, 172, 209, 1)'],
                        shadowColor: 'rgba(0, 0, 0, 0.3)',
                        shadowBlur: 10
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: 'rgba(255, 255, 255, 0.5)'
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: 'rgba(255, 255, 255, 0.5)'
                    }
                }
            }
        ],
        series: [
            {
                name: '雷达图',
                type: 'radar',
                emphasis: {
                    lineStyle: {
                        width: 4
                    }
                },
                data: [
                    {
                        value: [60, 5, 13, 100, 15],
                        name: '图二',
                        areaStyle: {
                            color: 'rgba(255, 255, 255, 0.5)'
                        }
                    }
                ]
            }
        ]
    };
    var Chart1 = echarts.init(document.getElementById('food_ana'));
    Chart1.setOption(option1);
});