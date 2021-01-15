$(function () {
    // 获取新闻
    function getAllNews(){
        $.post(
            '/FallDetection/getAllNews',
            function (data) {
                // console.log(data);
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
                        '                    <h3 class="new-bd-ri-edit">'+ item.title +'</h3>\n' +
                        '                    <span class="new-bd-ri-edit">'+  item.author+'</span>\n' +
                        '                    <i class="new-bd-ri-edit">'+ pbTime  +'</i>\n' +
                        '                    <p class="new-bd-ri-edit">'+ item.content +'</p>\n' +
                        '                </div>\n' +
                        '            </div>';
                    content = content.replace(/zhan159/i,con);
                });

                $('#news_box').html(content.substring(7));
            }
        );
    }
    getAllNews();
});