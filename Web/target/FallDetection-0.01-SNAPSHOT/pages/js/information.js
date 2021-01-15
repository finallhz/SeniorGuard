$(function () {

    var uidck;
    function  getcookie() {
        uidck = $.cookie('uidCookie');
    }
    getcookie();

    // 转换Date格式
    Date.prototype.format = function(fmt) {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    };

    // 获取用户个人信息
    function getInfo() {
        $.post('/FallDetection/getUserByUID',
            {uid:uidck},
            function (data) {
                // console.log(data);
                $('#ifuname').val(data.username);
                $('#ifpwd').val(data.pwd);
                var bir = new Date(data.birthdate).format("yyyy-MM-dd");
                $('#ifbir').val(bir);
                $('#iftel').val(data.phone);
                $('#ifem').val(data.email);
                $('#ifadd').val(data.address);
                var gd = data.gender;
                if (gd == 1){
                    $('#ifgd option[value ="male"]').prop('selected','selected');
                }else {
                    $('#ifgd option[value ="female"]').prop('selected','selected');
                }
                $('#ifage').val(data.age);
            }
        );
    }
    getInfo();

    var ifflag = false;
    $('#cgifm_btn').click(function () {
         ifflag = !ifflag;
         if(ifflag){
             $(this).html("保存个人信息");
             $('#ifuname').removeAttr('disabled');
             // $('#ifgd').removeAttr('disabled');
             // $('#ifbir').removeAttr('disabled');
             $('#iftel').removeAttr('disabled');
             $('#ifem').removeAttr('disabled');
             $('#ifadd').removeAttr('disabled');
             $('#inf_cgpwd').show();
         }
         else {
             // 保存信息
             uploadInfo();
             $(this).html("修改个人信息");
             $('#ifuname').attr("disabled",'disabled');
             // $('#ifgd').attr("disabled",'disabled');
             // $('#ifbir').attr("disabled",'disabled');
             $('#iftel').attr("disabled",'disabled');
             $('#ifem').attr("disabled",'disabled');
             $('#ifadd').attr("disabled",'disabled');
             $('#inf_cgpwd').hide();
         }
    });

    $('#inf_cgpwd').click(function () {
        window.location.href = 'password_find.html';
    });

    // 更新信息
    function uploadInfo() {
        var username = $('#ifuname').val();
        // var bitrhdate = new Date($('#ifbir').val());
        var phone = $('#iftel').val();
        var email = $('#ifem').val();
        var address = $('#ifadd').val();
        // var gender;
        // if ($('#ifgd').val() == 'male') {
        //     gender = 1;
        // }else {
        //     gender = 0;
        // }
        // console.log(gender);
        // console.log(bitrhdate instanceof Date);
        $.post('/FallDetection/updateUser',
            {
                uid: uidck,
                username: username,
                // birthdate: bitrhdate,
                phone: phone,
                email: email,
                address: address
                // gender: gender
            }
        );

    }

});