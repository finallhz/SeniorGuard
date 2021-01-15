$(function () {
    $('.menu li').click(function () {
        $('.menu li').removeClass('active');
        $(this).addClass('active')
    });

    $('.menu li').eq(0).click(function () {
        $('#ifra').prop('src','knn.html')
    });

    $('.menu li').eq(1).click(function () {
        $('#ifra').prop('src','administrator.html')
    });

    $('.menu li').eq(2).click(function () {
        $('#ifra').prop('src','statistics.html')
    });

    $('.menu li').eq(3).click(function () {
        $('#ifra').prop('src','newsAdmini.html')
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