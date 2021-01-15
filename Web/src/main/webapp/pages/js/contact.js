$(function() {

    var uidck;
    function  getcookie() {
        uidck = $.cookie('uidCookie');
    }
    getcookie();
    // console.log(uidck);

    // 创建表格函数
    function createConTB(data) {
        var tbBody = '';
        for (var i=0; i<data.length; i++){
            tbBody +='<tr data-num="'+ data[i].cid +'"><td>'+(i+1)+'</td>' +
                '<td><input disabled value="'+ data[i].cname +'"></td>' +
                '<td><input disabled value="'+data[i].phone+'"></td>' +
                '<td><input disabled value="'+data[i].email+'"></td>' +
                '<td><button>修改</button><button>确认</button>' +
                '<button>取消</button><button>删除</button></td></tr>';
        }
        $("#con-tbody").html(tbBody);

    }

    // 初始请求数据
    function reqData(){
        // 请求数据
        $.get('/FallDetection/getContacts',
            {uid: uidck},
            function (data) {
                // console.log(data);
                // 创建表格
                createConTB(data);

                delData();
                chanData();
                cancelChan();
                confirm();

            } // function end
        );
    }
    reqData();

    // 添加紧急联系人
    $("#con-addpeo").click(function () {
        $("#con-box").show();
    });
    // 确认添加
    $("#con-box-sure").click(function () {
        var cnamev = $("#con-cname").val();
        var phonev = $("#con-phone").val();
        var emailv = $("#con-email").val();
        if (cnamev == "" || phonev == "" || emailv == ""){
            alert("请填写完整信息！")
        }else{
            $.post("/FallDetection/addContact",
                {
                    cname: cnamev,
                    phone: phonev,
                    email: emailv
                },
                function (data) {
                    if (data == 1){
                        console.log("成功！");
                        alert("添加成功！");
                        $("#con-cname").val("");
                        $("#con-phone").val("");
                        $("#con-email").val("");
                        $("#con-box").hide();

                        // 请求数据
                        reqData();

                    }else {
                        alert("添加失败！")
                    }
                }
            )
        }
    });
    // 取消添加
    $("#con-box-no").click(function () {
        $("#con-box").hide();
    });

    // 搜索联系人
    $('#con-search').click(function () {
        var conname = $('#con-inpname').val();
        if (conname == ''){
            alert('您输入的为空');
        } else{
            $.post('/FallDetection/getContByName',
                {
                    uid: uidck,
                    cname:conname
                },
                function (data) {
                    // 没找到
                    if (data.length == 0){
                        alert('没有找到符合要求的紧急联系人');
                        return;
                    }
                    createConTB(data);
                    delData();
                    chanData();
                    cancelChan();
                    confirm();
            });

        }
    });



    // 删除数据
    function delData(){
        $("#con-tbody button:nth-child(4)").click(function () {
            // 正在修改其它数据
            if (flag0 == true){
                return;
            }
            var cid = $(this).parent().parent().attr('data-num');
            $.post("/FallDetection/deleteContact",
                {
                    cid:cid,
                    uid:uidck
                },
                function (data) {
                    createConTB(data);
                    delData();
                    chanData();
                    cancelChan();
                    confirm();
                }
            );
        });
    }


    // 修改数据
    var cname0,phone0,email0;
    var flag0 = false;  // 保证一次只能修改一条数据
    function chanData() {

        $("#con-tbody button:nth-child(1)").click(function () {
            // 正在修改其它数据
            if (flag0 == true){
                return;
            }
            flag0 = true;
            $(this).hide();
            $(this).siblings('button:nth-child(2)').show();
            $(this).siblings('button:nth-child(3)').show();
            $(this).siblings('button:nth-child(4)').hide();

            $(this).parent().siblings().children('input').attr('disabled',false);
            $(this).parent().siblings().children('input').css('outline','#59A7F7 dashed 1px');

            // 给取消数据用
            var tr = $(this).parent().parent();
            cname0 = tr.find('input')[0].value;
            phone0 = tr.find('input')[1].value;
            email0 = tr.find('input')[2].value;


        });
    }

    // 取消修改
    function cancelChan() {
        $("#con-tbody button:nth-child(3)").click(function () {
            $(this).hide();
            $(this).siblings('button:nth-child(1)').show();
            $(this).siblings('button:nth-child(4)').show();
            $(this).siblings('button:nth-child(2)').hide();

            $(this).parent().siblings().children('input').attr('disabled',true);
            $(this).parent().siblings().children('input').css('outline','none');

            // 恢复原来的值
            var tr = $(this).parent().parent();
            tr.find('input')[0].value = cname0;
            tr.find('input')[1].value = phone0;
            tr.find('input')[2].value = email0;

            flag0 = false;
        })
    }

    // 确认修改
    function confirm() {
        $("#con-tbody button:nth-child(2)").click(function () {
            $(this).hide();
            $(this).siblings('button:nth-child(1)').show();
            $(this).siblings('button:nth-child(4)').show();
            $(this).siblings('button:nth-child(3)').hide();

            $(this).parent().siblings().children('input').attr('disabled',true);
            $(this).parent().siblings().children('input').css('outline','none');

            var tr = $(this).parent().parent();
            var cid = tr.attr('data-num');
            var cname = tr.find('input')[0].value;
            var phone = tr.find('input')[1].value;
            var email = tr.find('input')[2].value;

            flag0 = false;

            $.post("/FallDetection/updateContact",
                {
                    cid:cid,
                    cname:cname,
                    phone:phone,
                    email:email
                },
                function (data) {
                    // console.log(data);
                }
            );


        })
    }







});