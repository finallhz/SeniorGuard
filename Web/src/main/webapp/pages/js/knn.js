$(function () {
    var sendPre;
    $.post('/FallDetection/getCurrScore',
        function (data) {
            var num = (parseFloat(data)*100).toFixed(2);
            var prec = num + '%';
            $('#knn_prec').val(prec);
        }
    );

    $('#knn_trainpre').blur(function () {
        var trainPre = $('#knn_trainpre').val();
        if (trainPre == ''){
            return;
        }
        if (trainPre>100 || trainPre<1){
            $('#knn_trainpre').val('');
            $('#knn_testpre').val('');
            alert('请输入1-100之间的数字');
            return;
        }
        var testPre = 100 - parseInt(trainPre);
        $('#knn_testpre').val(testPre);

    });


    $('#knn_opera').click(function () {
        var times = $('#knn_times').val();
        var testPre = $('#knn_testpre').val();
        var formData = new FormData();
        formData.append("filePath", $("#knn_file")[0].files[0]);
        formData.append('crossTimes', times);
        // console.log(formData);


        if (times == '' || testPre == ''){
            alert('请输入完整信息');
            return;
        }

        testPre /= 100;
        formData.append('testPre', testPre);

        $('#knn_wait').html('加载中，请稍后');

        $.ajax({
            url:'/FallDetection/getModelScore',
            type: 'POST',
            data : formData,
            // dataType:"json",
            // async: false,
            cache : false,
            processData : false,
            contentType : false,
            success: function (data) {
                $('#knn_minK').val(data[1]);
                var updataPre = (parseFloat(data[2])*100).toFixed(2);
                sendPre = data[2];
                updataPre += '%';
                $('#knn_updataPre').val(updataPre);


                // console.log(data);
                var szdata = data[0].substring(1,data[0].length-1).split(',');
                // console.log(szdata);

                $('#knn_wait').hide();
                $('#knn_chart').show();
                var option1 = {
                    title: {
                        text: 'KNN图',
                        left: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]
                    },
                    yAxis: {
                        type: 'value',
                        boundaryGap: false,
                        // max: 120,   //y轴最大值
                        min: 0     //y轴最小值
                    },
                    series: [{
                        data: szdata,
                        type: 'line'
                    }]
                };
                var knnChart = echarts.init(document.getElementById('knn_chart'));
                knnChart.setOption(option1)



            },
            error:function (e) {
                console.log(e);
            }
        });

    });

    $('#knn_update').click(function () {
        if (confirm('您确定要更新模型吗?')) {
            $.post('/FallDetection/replaceModel',
                {modelScore : sendPre},
                function (data) {
                    var num = (parseFloat(data)*100).toFixed(2);
                    var prec = num + '%';
                    $('#knn_prec').val(prec);
                }
            )
        }
    })




});






























