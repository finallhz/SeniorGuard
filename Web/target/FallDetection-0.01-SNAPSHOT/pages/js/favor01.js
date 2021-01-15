$(function() {
    $("#fav_main div").hover(function() {
        $(this).children("span").css("display", "inline-block");
        $(this).children("img:first").css("transform", "scale(1.1)");
    });

    $("#fav_main div").mouseleave(function() {
        $(this).children("span").css("display", "none");
        $(this).children("img:first").css("transform", "scale(1)");
    });

    $(".fav_sp2 img").click(function() {
        var aximg = $(this).parent().siblings(".fav_d1");
        if (aximg.css("display") == "none") {
            aximg.css("display", "inline-block");
        } else {
            aximg.css("display", "none");
        }
    });

});

