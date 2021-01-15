$(function () {
    var date = new Date();
    var hour = date.getHours();
    var min = date.getMinutes();
    $("#spo-h").html(hour);
    $("#spo-m").html(min);

    var energy;
    $.post('/FallDetection/getEnergy',function (data){
        $('#hint').hide();
        energy = parseInt(data);
        // console.log(energy);
        $('input[name="cons"]').val(energy);
        spo(energy);
        }
    );

    // 控制显示区域
    function spo(ener) {
        var abs = parseInt($('input[name="abs"]').val());
        var cons = ener;
        var dif = abs-cons;
        if (dif <= 0){   // 运动量够了
            $('.recospo2').show();
            $('.recospo1').hide();
        }else {     // 运动量不够
            if (dif < 100){
                $('#re1-2 div:nth-child(1)').html('做家务<img src="images/spo01.jpg">');
                $('#re1-2 div:nth-child(2)').html('上下楼梯<img src="images/spo02.jpg">');
                $('#re1-3 div:nth-child(1)').html('插花<img src="images/spo03.jpg">');
                $('#re1-3 div:nth-child(2)').html('散步<img src="images/spo04.jpg">');
            }else if (dif <300) {
                $('#re1-2 div:nth-child(1)').html('打太极<img src="images/spo05.jpg">');
                $('#re1-2 div:nth-child(2)').html('慢走<img src="images/spo06.jpg">');
                $('#re1-3 div:nth-child(1)').html('健身操<img src="images/spo07.jpg">');
                $('#re1-3 div:nth-child(2)').html('打拳<img src="images/spo08.jpg">');
            }else {
                $('#re1-2 div:nth-child(1)').html('乒乓球<img src="images/spo09.jpg">');
                $('#re1-2 div:nth-child(2)').html('慢跑<img src="images/spo10.jpg">');
                $('#re1-3 div:nth-child(1)').html('骑自行车<img src="images/spo11.jpg">');
                $('#re1-3 div:nth-child(2)').html('广场舞<img src="images/spo12.jpg">');
            }

            if (dif<30){
                $('#sport-time').html('10分钟左右');
            } else if(dif<60){
                $('#sport-time').html('20分钟左右');
            }else if(dif<100){
                $('#sport-time').html('30分钟左右');
            }else if(dif<160){
                $('#sport-time').html('10分钟左右');
            }else if(dif<220){
                $('#sport-time').html('20分钟左右');
            }else if(dif<300){
                $('#sport-time').html('30分钟左右');
            }else if(dif<400){
                $('#sport-time').html('10分钟左右');
            }else if(dif<500){
                $('#sport-time').html('20分钟左右');
            }else if(dif<600){
                $('#sport-time').html('30分钟左右');
            }else {
                $('#sport-time').html('30分钟以上');
            }






            $('.recospo2').hide();
            $('.recospo1').show();

        }
    }

    $('#sp-bt1').click(function () {
        $(this).hide();
        $('#sp-bt2').show();
        $('#sp-bt3').show();
        $('.show-box input').removeAttr('disabled');
    });

    // 确定键
    $('#sp-bt2').click(function () {
        $(this).hide();
        $('#sp-bt3').hide();
        $('#sp-bt1').show();
        $('.show-box input').prop('disabled','true');

        var energy = parseInt($('input[name="cons"]').val());
        spo(energy);
    });

    // 取消键
    $('#sp-bt3').click(function () {
        $(this).hide();
        $('#sp-bt2').hide();
        $('#sp-bt1').show();
        $('.show-box input').prop('disabled','true');
    });




});