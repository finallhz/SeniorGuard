$(function(){

    var uidck;
    function  getcookie() {
        uidck = $.cookie('uidCookie');
    }
    getcookie();


    $('#account').blur(function () {
        if($(this).val() == ""){
            $('#prompt_1').html('请您输入的用户名');
            $('#prompt_1').show();
        }else {
            $('#prompt_1').hide();
        }
    });


    var tel;
    $('#btn-find').click(function () {
        var account = $('#account').val();
        $.post('/FallDetection/getUserTEL',
            {
                username:account
            },
            function (data) {
                // 用户不存在
                if(data == ''){
                    $('#prompt_1').html('您输入的用户名不存在');
                    $('#prompt_1').show();
                    return;
                }


                // 验证电话
                var last4 = data.slice(data.length-4);
                $('#content2 p').html('我们将向您尾号为'+ last4 +'的手机号发送验证码');

                $('#content').hide();
                $('#content2').show();
                tel = data;
            }
        );
    });

    // 发送验证码
    $('#send_code').click(function () {
        $.post('/FallDetection/sendCode',
            {phoneNum: tel},
            function (data) {
                console.log(data);
            });
    });

    // 验证验证码
    $('#btn-code').click(function () {
        var code = $('#ucode').val();
        $.post('/FallDetection/verify',
            {
                phoneNum: tel,
                code: code
            },
            function (data) {
                // 0错1对
                if(data == 0){
                    alert('验证码输入错误');
                    return;
                }
                $('#content2').hide();
                $('#content3').show();
            }
        );
    });

    // 修改新密码
    $('#btn-change').click(function () {
        var pwd1 = $('#upwd1').val();
        var pwd2 = $('#upwd2').val();
        if (pwd1 != pwd2){
            $('#prompt_2').html('输入的两次密码不一致');
            $('#prompt_2').show();
            return;
        }
        if(pwd1.length == 0){
            $('#prompt_2').html('您输入的密码为空');
            $('#prompt_2').show();
            return;
        }

        $.post('/FallDetection/updatePwd',
            {
                uid: uidck,
                pwd: pwd1
            },
            function (data) {
                console.log(data);
                if(data == 1){
                    $('#content3').hide();
                    $('#content4').show();
                }
            })
    });
});