<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
<style type="text/css">
    body {
        padding: 10px;
    }
    #inputtext {
        width: 100%;
    }
    #login{
        width: 300px;
        margin:0px auto;
        padding-top: 60px;
    }
    #flushimg{
        text-decoration: underline;
    }
    #butt{
        width: 60%;
    }
</style>
<form action="/FallDetection/kapt" method="post">
    <h2 align="center">L O G I N</h2><br/><br/>
    <input type="text" name="userName" class="form-control" id="inputext" required autofocus placeholder="-----请输入用户名-----"/><br/>
    <input type="password" name="userName" class="form-control" id="inputtext" required placeholder="----请输入用户密码----"/><br/>
    <div id="flushimg">
        <img alt="验证码" onclick="this.src='/FallDetection/defaultKaptcha?d=' + new Date()*1" src="/FallDetection/defaultKaptcha" />
        <a>看不清？点击图片刷新一下</a>
    </div>
    <input type="text" name="tryCode" class="form-control" required placeholder="-----请输入验证码-----" />
    <h4 th:text="${info}" style="color: red"></h4>
    <input type="checkbox" name="rememberMe"/>记住我<br/>
    <div style="width: 100%;text-align: center;"><input type="submit" value="登 录" id="butt" class="btn btn-success" /></div>

</form>

<form action="/FallDetection/login?username=张三&&pwd=123" method="post">
<input type="submit" value="登 录">
</form>

<form action="/FallDetection/sportData" method="post">
    <input type="submit" value="登 录">
</form>
<script src="pages/js/boot.js"></script>

<form action="/FallDetection/addNews" method="post" enctype="multipart/form-data">
    图片：<input type="file" name="picFile">
    <input type="submit" name="提交">
</form>

<form action="/FallDetection/getModelScore" method="post" enctype="multipart/form-data">
    图片：<input type="file" name="filePath">
    测试集：<input type="text" name="testPre">
    交叉测试：<input type="text" name="crossTimes">
    <input type="submit" name="提交">
</form>
</body>

</html>
