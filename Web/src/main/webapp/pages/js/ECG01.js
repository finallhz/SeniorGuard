$(function () {
    // 获取websocket数据

    function wsk() {

        var websocket = null;
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://localhost:8080/FallDetection/ws/listen");
        }
        else {
            alert('当前浏览器 Not support websocket');
            return;
        }

        //连接发生错误的回调方法
        websocket.onerror = function () {
            console.log("WebSocket连接发生错误");
        };

        //连接成功建立的回调方法
        websocket.onopen = function () {
            console.log("WebSocket连接成功");
        };

        //连接关闭的回调方法
        websocket.onclose = function () {
            console.log("WebSocket连接关闭");
        };

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
        };

        // $('#btclo').click(function () {
        //     closeWebSocket();
        // });

        //关闭WebSocket连接
        function closeWebSocket() {
            websocket.close();
        }



        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            // console.log(JSON.parse(event.data));

            var heart = JSON.parse(event.data).heart;
            var step = JSON.parse(event.data).stepCount;
            // console.log(heart);
            $('#ecg_heart').html(heart);
            $('#ecg_step').html(step);

            // $('#btclo').click(function () {
            //     closeWebSocket();
            // });



        };



    }
    wsk()

});