document.getElementById("toReg").addEventListener("click", function() {
    window.location.href = 'registered.html';
});


$(function(){

    $("#chkBtn").click(function(){
        var username = $("#username").val();
        var pwd = $("#password").val();
        var tryCode = $("#tryCode").val();
        var flag = false;
        $.ajaxSettings.async = false;
        $.post("/FallDetection/kapt",
            {tryCode : tryCode},
            function(data){
                if (data == 0) {
                    alert("验证码错误");
                    flag = true;
                }
            }
        );

        if (flag){
            return false;
        }

        $.post("/FallDetection/login",
            {
                username:username,
                pwd:pwd
            },
            function (data) {
                // console.log(data);
                if (data == ''){
                    alert("用户名或密码错误");
                    return;
                }else {
                    var role;
                    function  getcookie() {
                        role = $.cookie('roleCookie');
                    }
                    getcookie();
                    if (role == '0'){
                        window.location.href = 'iframe01.html';
                    } else {
                        window.location.href = 'iframe02.html';
                    }
                }
            }
        );
        


    });
    
    
    
    
    
    
    
});