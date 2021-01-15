$(function () {
    $('.menu li').click(function () {
        $('.menu li').removeClass('active');
        $(this).addClass('active');
    });

    $('.menu li').eq(0).click(function () {
        $('#ifra').prop('src','fallData.html');
    });

    $('.menu li').eq(1).click(function () {
        $('#ifra').prop('src','addFall.html');
    });

    $('.menu li').eq(2).click(function () {
        $('#ifra').prop('src','ECG.html');
    });

    $('.menu li').eq(3).click(function () {
        $('#ifra').prop('src','Map.html');
    });

    $('.menu li').eq(4).click(function () {
        $('#ifra').prop('src','contact.html');
    });

    $('.menu li').eq(5).click(function () {
        $('#ifra').prop('src','sports.html');
    });

    $('.menu li').eq(6).click(function () {
        $('#ifra').prop('src','foods.html');
    });

    $('.menu li').eq(7).click(function () {
        $('#ifra').prop('src','news.html');
    });

    $('.menu li').eq(8).click(function () {
        $('#ifra').prop('src','information.html');
    });

    $('#menu_info').click(function () {
        $('#ifra').prop('src','information.html');
        $('.menu li').removeClass('active');
        $('.menu li').eq(8).addClass('active');
    });

    $('#menu_logout').click(function () {
        $.post('/FallDetection',
            function (data) {
                window.location.href = 'login.html';
            }
        )
    });

});