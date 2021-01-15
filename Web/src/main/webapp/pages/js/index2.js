$(function() {
    $('.card-l').mouseover(function() {
        $('.card-l').removeClass('change');
        $(this).addClass('change');
        $('.card-r').hide();
        $(this).siblings().show();
        $(this).siblings().css('width', '50%');
        $('.card').css('width', '20%');
        $(this).parent().css('width', '40%');
    });




    setTimeout(function () {
        $('.site-car-img img').eq(0).fadeOut(1000);
        $('.site-car-img img').eq(1).fadeIn(2000);
        $('.site-car-point div').eq(1).addClass('xz');
        $('.site-car-point div').eq(1).siblings().removeClass('xz');
    },3000);

    setTimeout(function () {
        var f1 = 1;
        var timer1 = setInterval(function() {
            $('.site-car-img img').eq(f1 % 3).fadeOut(1000);
            f1 += 1;
            $('.site-car-img img').eq(f1 % 3).fadeIn(2000);
            $('.site-car-point div').eq(f1 % 3).addClass('xz');
            $('.site-car-point div').eq(f1 % 3).siblings().removeClass('xz');
        }, 5000);
    },3000);



});