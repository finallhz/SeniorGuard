$(function () {

    // 获取新闻
    function getAllNews(){
        $.post(
            '/FallDetection/getAllNews',
            function (data) {
                console.log(data);
                var content = 'zhan159';
                data.forEach(function (item, index, array) {
                    // 根据毫秒数创建日期对象
                    var data = new Date(item.publishTime);
                    // 格式化日期
                    var pbTime = data.toLocaleString();
                    var con = 'zhan159<div class="clearfix news-bd">\n' +
                        '                <div class="new-bd-le fl">\n' +
                        '                    <img src="'+ item.pic +'">\n' +
                        '                </div>\n' +
                        '                <div class="new-bd-ri fl clearfix">\n' +
                        '                    <button class="news-del" data-num="'+ item.nid +'">删除</button>\n' +
                        // '                    <button class="news-edit" data-num="'+ item.nid +'">编辑</button>\n' +
                        '                    <h3 class="new-bd-ri-edit">'+ item.title +'</h3>\n' +
                        '                    <span class="new-bd-ri-edit">'+  item.author+'</span>\n' +
                        '                    <i class="new-bd-ri-edit">'+ pbTime  +'</i>\n' +
                        '                    <p class="new-bd-ri-edit">'+ item.content +'</p>\n' +
                        '                </div>\n' +
                        '            </div>';
                    content = content.replace(/zhan159/i,con)
                });

                $('#news_box').html(content.substring(7));
                deleteNews();
                // updateNews();
            }
        );

    }
    getAllNews();

    // 删除新闻
    function deleteNews(){
        $('.news-del').click(function () {
            var nid = parseInt($(this).attr('data-num'));
            if(confirm('您确定要删除吗')){
                $.post(
                    '/FallDetection/deleteNews',
                    {nid: nid},
                    function (data) {
                        getAllNews();
                    }
                )
            }
        })
    }

    // 编辑新闻
    // function updateNews(){
    //     var nid = $(this).attr('data-num');
    //     $(this).siblings().attr('')
    // }



    // 新增新闻按钮
    $('#add_news').click(function () {
        $('.add-news').show();
        $(this).hide();
    });

    // 取消新增
    $('#add_no').click(function () {
        $('.add-news').hide();
        $('#add_news').show();
    });

    // 完成新增
    $('#add_ok').click(function () {
        var title =$('#news_title').val();
        var author =$('#news_author').val();
        var content = $('#news_con').html();
        var formData = new FormData();
        formData.append("picFile", $("#news_pic")[0].files[0]);
        formData.append("title", title);
        formData.append("author", author);
        formData.append("content", content);

        if(title=='' || author=='' || content==''){
            alert('请填写完整信息！');
            return;
        }
        if($("#news_pic")[0].files[0] == null){
            alert('请上传图片！');
            return;
        }


        $.ajax({
            url: '/FallDetection/addNews',
            type: 'POST',
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data == 0) {
                    alert('发布失败！');
                    return;
                }
                // console.log(data);
                alert('发布成功！');
                getAllNews();
                $('#news_title').val('');
                $('#news_author').val('');
                $('#news_con').html('');
                // $("#news_pic")[0].files[0] = null;
            }
        })
    });

});