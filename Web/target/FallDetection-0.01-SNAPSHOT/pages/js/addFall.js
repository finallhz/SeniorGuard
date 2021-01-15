$(function () {

    var uidck;
    function  getcookie() {
        uidck = $.cookie('uidCookie');
    }
    getcookie();

    // 创建表格
    function createFallTB(req){
        // $("#adf-tbody").html('');
        $.get("/FallDetection/getAllFall",
            req,
            function (data) {
                // console.log(data);

                var tbBody = '';
                data.list.forEach(function (item,index,array) {
                    tbBody +='<tr><td>'+(index+1)+'</td>' +
                        '<td>'+mytime(item.time) + '</td>' +
                        '<td>'+item.location + '</td>' +
                        '<td>'+(item.flag>0?'摔倒':'未摔倒')+'</td>' +
                        '<td><button data-num="'+item.id+'">误报</button></td></tr>';
                });
                $("#adf-tbody").html(tbBody);

                // 当前页
                var pageNum = data.pageNum;
                // 页码导航数组
                var nav = data.navigatepageNums;
                // 写页码
                var pageBD = '';
                for (var i=1; i<=nav.length; i++){
                    if (nav[i-1] == pageNum) {
                        pageBD +=  ' <span class="current" data-num="'+ nav[i-1] +'">'+ nav[i-1] +'</span>';
                    }else {
                        pageBD +=  ' <span data-num="'+ nav[i-1] +'">'+ nav[i-1] +'</span>';
                    }
                }
                $('#add_pages div').html(pageBD);

                var pageSize = data.pageSize;
                if (pageNum <= 1) {
                    $('#page_prev').addClass('forbid');
                }else {
                    $('#page_prev').removeClass('forbid');
                    $('#page_prev').attr('data-num',pageNum-1);
                }
                if (pageNum >= pageSize) {
                    $('#page_next').addClass('forbid');
                }else {
                    $('#page_next').removeClass('forbid');
                    $('#page_next').attr('data-num',pageNum+1);
                }

                changePages();
                check();
            }
        );
    }
    createFallTB({uid: 1});

    // 翻页
    function changePages() {
        // 点数字
        $('#add_pages div span').click(function () {
            var pn = $(this).attr('data-num');
            createFallTB({uid: 1, pn:pn});
        });
        // 点上下页
        $('#page_prev').click(function () {
            var pn = $(this).attr('data-num');
            createFallTB({uid: 1, pn:pn});
        });
        $('#page_next').click(function () {
            var pn = $(this).attr('data-num');
            createFallTB({uid: 1, pn:pn});
        });
    }


    // 核验数据
    function check(){
        $('#adf-tbody button').click(function () {
            var con = confirm("您确定修改数据吗");
            var numid = $(this).attr('data-num');
            if (con == true) {
                $.post('/FallDetection/deleteFall',
                    {
                        id:numid,
                        uid:uidck
                    },
                    function (data) {
                        createFallTB({uid: 1});
                    }
                );
            }
        });
    }

    function mytime(time) {
        var myDate = new Date(time);
        var YMD=myDate.getFullYear() + "-" + (myDate.getMonth()+1) + "-" + myDate.getDate();
        var HMS=myDate.getHours() + ":" + myDate.getMinutes() + ":" + myDate.getSeconds();
        var YMDHMS=YMD+" "+HMS;
        return YMDHMS;
    }



    // 获取数据并创建表格1
    function createFallData01(){
        $.post('/FallDetection/getFallStatistic',
            {uid: uidck},
            function (data) {
                // console.log(data);
                var fallDatas = [];
                var fallTimes = [];
                data.forEach(function (item, index, array) {
                    fallDatas.push(item.months);
                    fallTimes.push(item.nums);
                });
                // console.log(fallDatas);
                // console.log(fallTimes);

                option1 = {
                    title: {
                        text: '最近一年摔倒数据',
                        // subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: fallDatas
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: fallTimes,
                        type: 'bar'
                    }]
                };
                echarts.init(document.getElementById('adf-bar')).setOption(option1);
            }
        )



    }
    createFallData01();

    // 获取数据并创建表格2
    function createFallData02() {
        $.post('/FallDetection/getSucErr',
            {uid: uidck},
            function (data) {
                // console.log(data);

                option2 = {
                    title: {
                        text: '真实摔倒与摔倒误判',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['真实摔倒', '摔倒误判']
                    },
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: [
                                {value: data[1].SucErr, name: '真实摔倒'},
                                {value: data[0].SucErr, name: '摔倒误判'}
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
                echarts.init(document.getElementById('adf-pie')).setOption(option2);
            })
    }
    createFallData02();







});