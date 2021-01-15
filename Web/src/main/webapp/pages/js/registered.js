//用户名验证
$(function () {

    var flag1=false, flag2=false, flag3 = false, flag4 = false, flag5 = false;

    var uval,pwdlen,pwdval,repwdval,telval,emaval;

    //用户名验证
    $("#username_reg").blur(function () {
        uval = $(this).val();
        // console.log(uval);
        if (uval != ""){
            $.post("/FallDetection/isExist",
                {
                    username:uval
                },
                function (data) {
                    // 有重复返回true
                    // console.log(data);
                    if(data){  //用户名重复
                        $("#u-ck1").css("display","inline-block");
                        $("#u-ck2").css("display","none");
                    }
                    else {
                        flag1 = true;
                        $("#u-ck1").css("display","none");
                        $("#u-ck2").css("display","inline-block");
                    }
                }
            );
        }
    // console.log(flag1);
    });

    // 密码验证
    $("#pwd_reg").blur(function () {
        pwdlen = $(this).val().length;
        if($(this).val() != ""){
            if (pwdlen < 6) {
                $("#pwd-ck1").css("display","inline-block");
                $("#pwd-ck2").css("display","none");
            } else {    //验证通过
                $("#pwd-ck1").css("display","none");
                $("#pwd-ck2").css("display","inline-block");
                flag2 = true;
            }
        }

        // console.log("f2:" + flag2);
    });

    // 密码二次输入验证
    $("#repwd_reg").blur(function () {
        pwdval = $("#pwd_reg").val();
        repwdval = $("#repwd_reg").val();
        if (repwdval != "") {
            if (pwdval == repwdval) {   //验证通过
                $("#repwd-ck1").css("display","none");
                $("#repwd-ck2").css("display","inline-block");
                flag3 = true;
            } else {
                $("#repwd-ck1").css("display","inline-block");
                $("#repwd-ck2").css("display","none");
            }
        }

        // console.log("f3:" + flag3);
    });

    // 电话验证
    $("#tel").blur(function () {
        telval = $("#tel").val();
        if($(this).val() != ""){
            if (!(/^1[3456789]\d{9}$/.test(telval))) {
                $("#tel-ck1").css("display","inline-block");
                $("#tel-ck2").css("display","none");
            } else {    //验证通过
                $("#tel-ck1").css("display","none");
                $("#tel-ck2").css("display","inline-block");
                flag4 = true;
            }
        }

        // console.log("f4:" + flag4);
    });

    // 邮箱验证
    $("#email").blur(function () {
        emaval = $("#email").val();
        if($(this).val() != ""){
            if (!(/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(emaval))) {
                $("#ema-ck1").css("display","inline-block");
                $("#ema-ck2").css("display","none");
            } else {    //验证通过
                $("#ema-ck1").css("display","none");
                $("#ema-ck2").css("display","inline-block");
                flag5 = true;
            }
        }

        // console.log("f5:" + flag5);
    });


    $("#chkBtn").click(function () {
        if (flag1 && flag2 && flag3 && flag4 && flag5){
            // console.log("验证通过");

            var sexval = $("input[name='sex']:checked").val();
            sexval = parseInt(sexval);
            var addressval = $('#address').val();
            var birth = $('#birth').val();
            var birthval = new Date(birth);

            $.post('/FallDetection/register',
                {
                    username:uval,
                    pwd:pwdval,
                    phone:telval,
                    email:emaval,
                    gender:sexval,
                    birthdate:birthval,
                    address:addressval
                },
                function (data) {
                    // console.log(data);
                    alert('注册成功！即将返回登入界面。');
                    window.location.href = 'login.html';
            });
        }

    });

});


