$(function () {
    $('#foods_prompt_close').click(function () {
        $('#foods_prompt').hide();
    });

    var likeFoods = [];

    $.post('/FallDetection/getAllFoods',function (data) {
        var foodsALL = data;  // 全部食品数据 对象数组
        var lenALL = foodsALL.length;
        var food01 = [],    //粤
            food02 = [],    //鲁
            food03 = [],    //川
            food04 = [],    //湘
            food05 = [],    //闵
            food06 = [],    //浙
            food07 = [],    //苏
            food08 = [],    //徽
            food09 = [];    //其它
        // var foodfind = [];      //搜索的数组
        for(var z=0; z<lenALL; z++) {
            switch (data[z].dept) {
                case '粤':
                    food01.push(data[z]);
                    break;
                case '鲁':
                    food02.push(data[z]);
                    break;
                case '川':
                    food03.push(data[z]);
                    break;
                case '湘':
                    food04.push(data[z]);
                    break;
                case '闽':
                    food05.push(data[z]);
                    break;
                case '浙':
                    food06.push(data[z]);
                    break;
                case '苏':
                    food07.push(data[z]);
                    break;
                case '徽':
                    food08.push(data[z]);
                    break;
                default:
                    food09.push(data[z]);
            }
        }

        var text01 = '';
        for(var i=0; i<lenALL; i++){
            text01 += '<li data_num="'+foodsALL[i].fid+'">\n' +
                '                    <div>\n' +
                '                        <span class="fav_sp1">'+foodsALL[i].title+'</span>\n' +
                '                        <img src="images/foods/food'+foodsALL[i].fid+'.JPG" alt="">\n' +
                '                        <span class="fav_sp2">\n' +
                // '                            <img src="images/foods/爱心1.png" alt="">\n' +
                '                        </span>\n' +
                // '                        <img class="fav_d1" src="images/foods/爱心1.png" alt="">\n' +
                '                    </div>\n' +
                '                </li>';
        }
        $('#fav_main_ul').html(text01);

        // 点击菜品种类
        $('#fav_kind_ul li').click(function () {
            $('#fav_kind_ul li').removeClass('clicked');
            $(this).addClass('clicked');

            var text01 = '';
            var liid = $(this).attr('data_num');
            var fdept = [];
            switch (liid) {
                case '0':
                    fdept = foodsALL;
                    break;
                case '1':
                    fdept = food01;
                    break;
                case '2':
                    fdept = food02;
                    break;
                case '3':
                    fdept = food03;
                    break;
                case '4':
                    fdept = food04;
                    break;
                case '5':
                    fdept = food05;
                    break;
                case '6':
                    fdept = food06;
                    break;
                case '7':
                    fdept = food07;
                    break;
                case '8':
                    fdept = food08;
                    break;
                case '9':
                    fdept = food09;
            }
            for(var a=0; a<fdept.length; a++){
                text01 += '<li data_num="'+fdept[a].fid+'">\n' +
                    '                    <div>\n' +
                    '                        <span class="fav_sp1">'+fdept[a].title+'</span>\n' +
                    '                        <img src="images/foods/food'+fdept[a].fid+'.JPG" alt="">\n' +
                    '                        <span class="fav_sp2">\n' +
                    // '                            <img src="images/foods/爱心1.png" alt="">\n' +
                    '                        </span>\n' +
                    // '                        <img class="fav_d1" src="images/foods/爱心1.png" alt="">\n' +
                    '                    </div>\n' +
                    '                </li>';
            }
            $('#fav_main_ul').html(text01);

            // 点击菜品
            $('#fav_main_ul li').click(dealFoods);

        });

        // 搜索菜品
        $('#foods_shbtn').click(function () {
            var title = $('#foods_shinp').val();
            $.post(
                '/FallDetection/getFoodsByTitle',
                {
                    title:title
                },
                function (data) {
                    if (data.length == 0){
                        alert('抱歉，没有符合要求的菜品！');
                        return;
                    }
                    var findfoods = data;
                    var text01 = '';
                    for(var i=0; i<findfoods.length; i++){
                        text01 += '<li data_num="'+findfoods[i].fid+'">\n' +
                            '                    <div>\n' +
                            '                        <span class="fav_sp1">'+findfoods[i].title+'</span>\n' +
                            '                        <img src="images/foods/food'+findfoods[i].fid+'.JPG" alt="">\n' +
                            '                        <span class="fav_sp2">\n' +
                            // '                            <img src="images/foods/爱心1.png" alt="">\n' +
                            '                        </span>\n' +
                            // '                        <img class="fav_d1" src="images/foods/爱心1.png" alt="">\n' +
                            '                    </div>\n' +
                            '                </li>';
                    }
                    $('#fav_main_ul').html(text01);

                    // 点击菜品
                    $('#fav_main_ul li').click(dealFoods);
                }
            )
        });


        // 创建表格
        function creatTable() {
            if (likeFoods.length == 0){
                $('#foods_number').html('0');
                $('#tb_checked_item').html('');
            }else {
                var text02 = '';
                for (var b = 0; b < likeFoods.length; b++) {
                    var c = parseInt(likeFoods[b]);
                    text02 += '\n' +
                        '                        <span>' + foodsALL[c - 1].title + '<button class="" ' +
                        'data_num="' + foodsALL[c - 1].fid + '">删除</button></span>\n';
                    $('#tb_checked_item').html(text02);
                }

                $('#foods_number').html(likeFoods.length);

                // 删除菜品
                $('#tb_checked button').click(function () {
                    console.log('ok');
                    var num = $(this).attr('data_num');     // 要删除菜品的编号
                    for(var i=0; i<likeFoods.length; i++){
                        if (likeFoods[i] == num){
                            likeFoods.splice(i,1);
                            break;
                        }
                    }
                    creatTable();
                    console.log(likeFoods);
                });
            }
        }

        // 菜品被点击后处理函数
        function dealFoods () {
            if (likeFoods.length >= 8){
                return;
            }
            var fid = $(this).attr('data_num');
            var flag = true;
            for (var j = 0; j < likeFoods.length; j++) {
                if (likeFoods[j] == fid) {
                    flag = false;
                }
            }
            if (flag) {
                likeFoods.push(fid);
            }

            // console.log(likeFoods);

            creatTable();

        }

        // 点击菜品
        $('#fav_main_ul li').click(dealFoods);

        var flag03 = 0;
        // 查看结果
        $('#changeOK').click(function () {
            if (flag03 == 0) {

                if (likeFoods.length<5){
                    console.log('请选满五道菜');
                    return;
                }

                likeFoods = likeFoods.toString();
                $.post('/FallDetection/getFoodsNum',{L:likeFoods},function (data) {
                    if (data != ''){
                        var recomFoods = data;  //推荐结果对象数组

                        //
                        text03 = '';
                        for(var i=0; i<recomFoods.length; i++){
                            text03 += '<table class="table table-bordered res_tb">\n' +
                                '                <tr class="res_tb_head">\n' +
                                '                    <td colspan="3">'+recomFoods[i].title+'</td>\n' +
                                '                </tr>\n' +
                                '\n' +
                                '                <tr>\n' +
                                '                    <td rowspan="5" class="res_tb_img"><img src="images/foods/food'+recomFoods[i].fid+'.JPG" class="res_tb_img"></td>\n' +
                                '                    <td class="res_tb_left">主&nbsp;&nbsp;料</td>\n' +
                                '                    <td class="res_tb_right">'+recomFoods[i].mainM+'</td>\n' +
                                '                </tr>\n' +
                                '\n' +
                                '                <tr>\n' +
                                '                    <td class="res_tb_left">辅&nbsp;&nbsp;料</td>\n' +
                                '                    <td class="res_tb_right">'+recomFoods[i].mixM+'</td>\n' +
                                '                </tr>\n' +
                                '\n' +
                                '                <tr>\n' +
                                '                    <td class="res_tb_left">调&nbsp;&nbsp;料</td>\n' +
                                '                    <td class="res_tb_right">'+recomFoods[i].subM+'</td>\n' +
                                '                </tr>\n' +
                                '\n' +
                                '                <tr>\n' +
                                '                    <td class="res_tb_left">做&nbsp;&nbsp;法</td>\n' +
                                '                    <td class="res_tb_right">'+recomFoods[i].cooking+'</td>\n' +
                                '                </tr>\n' +
                                '\n' +
                                '                <tr>\n' +
                                '                    <td class="res_tb_left">注意事项</td>\n' +
                                '                    <td class="res_tb_right">'+recomFoods[i].attention+'</td>\n' +
                                '                </tr>\n' +
                                '            </table>';
                        }
                        $('#results').html(text03);

                    } else {

                        console.log('没有推荐');
                        $('#results').html('<h1>没有推荐</h1>')
                    }
                    likeFoods = likeFoods.split(',');

                    flag03 = 1;
                    $('#changeOK').html('返回重新选择菜品');
                    $('#fav_main_ul').hide();


                })
            }else {
                flag03 = 0;
                $('#changeOK').html('选择完毕，查看推荐结果');
                $('#fav_main_ul').show();
                $('#results').html('');
            }






        });


    });  // post结束









});