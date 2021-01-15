$(function () {

    // 创建表格
    function createTB(users){
        $('#ad-tbody').html('');
        var content = '';
        users.forEach(function (item, index, array) {
            if(item.gender == 1){
                gd = '男';
            }else {
                gd = '女';
            }
            content += '<tr>\n' +
                '<td>'+ (index+1) +'</td>\n' +
                '<td>'+ item.username +'</td>\n' +
                '<td>'+ gd +'</td>\n' +
                '<td>'+ item.age +'</td>\n' +
                '<td>'+ item.phone +'</td>\n' +
                '<td>'+ item.email +'</td>\n' +
                '<td>'+ item.address +'</td>\n' +
                '<td>\n' +
                '<button>发送邮件</button>\n' +
                '<button data-num="'+ item.uid +'" >删除</button>\n' +
                '</td></tr>'
        });
        $('#ad-tbody').html(content);
    }

    // 点击触发发送邮件
    function emaClick(){
        $('#ad-tbody tr button:first-child').click(function () {
            $('#ad-email').show();
            $('#ad-user').hide();
            var ema = $(this).parent().siblings().eq(5).html();
            $('#ad_ema_reci').val(ema);
        });
    }
    // 取消发送
    function cancelSend(){
        $('#ad_cancel').click(function () {
            // 确定取消
            if (confirm('您确定要删除吗？系统不会保存您的输入')) {
                $('#ad-email').hide();
                $('#ad-user').show();
            }
        });
    }
    cancelSend();
    // 发送邮件
    function sendEmail(){
        $('#ad_send').click(function () {
            var reci = $('#ad_ema_reci').val();
            var theme = $('#ad_ema_theme').val();
            var content = $('#ad_ema_con').html();
            if (reci=='' || theme=='' || content==''){
                alert('请将信息填写完整！');
                return;
            }
            $.post('/FallDetection/sendCustomMail',
                {
                    to: reci,
                    subject: theme,
                    content: content
                },
                function (data) {
                    if (data) {
                        alert('发送成功！');
                        $('#ad-email').hide();
                        $('#ad-user').show();
                    }else {
                        alert('发送失败！请检查邮箱地址是否正确。')
                    }
                }
            );
        });
    }
    sendEmail();

    // 删除用户
    function deleteUser(){
        $('#ad-tbody tr button:nth-child(2)').click(function () {
            var uid = $(this).attr('data-num');
            uid = parseInt(uid);
            console.log(uid);
            $.post('/FallDetection/deleteUserByUID',
                {
                    uid: uid
                },
                function (data) {
                    alert('删除成功');
                    createTB(data);
                    emaClick();
                    deleteUser();
                }
            );
        });
    }

    // 搜索用户
    function findUser(){
        $('#ad_find').click(function () {
            var type = $('#ad_select option:selected').val();
            var keyword = $('#ad_keyword').val();
            if (keyword == ''){
                alert('请输入关键字');
            }
            $.post('/FallDetection/findUsers',
                {
                    content: keyword,
                    searchType: type
                },
                function (data) {
                    if (data.length == 0){
                        alert('未找到相关用户！');
                        return;
                    }
                    createTB(data);
                    emaClick();
                    deleteUser();
                }
            );
        });
    }
    findUser();

    // 请求全部用户
    function getAllUsers() {
        $.post('/FallDetection/getAllUser',
            function (data) {
                createTB(data);
                emaClick();
                deleteUser();
            }
        );
    }
    getAllUsers();

























});