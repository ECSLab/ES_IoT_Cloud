<%--
  Created by IntelliJ IDEA.
  User: coder
  Date: 18-1-5
  Time: 下午7:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="static/css/login.css">
    <link rel="stylesheet" type="text/css" href="static/css/iconfont.css">
    <link href="static/icheck/skins/square/blue.css" rel="stylesheet">
    <script type="text/javascript" src="static/js/jquery-3.1.1.min.js"></script>
</head>
<body>
<div class="container">
    <div class="videoPlayer">
        <video src="static/img/cloud.mp4" autoplay="autoplay" loop="loop"></video>
    </div>
    <div class="panel_login">
        <div class="login-logo"></div>
        <form class="form" id="form">
            <div class="group">
                <h3>账号登录</h3>
                <a href="#" class="reg">立即注册</a>
            </div>
            <div class="group">
                <i class="iconfont icon-user"></i>
                <input type="text" name="username" id="name" placeholder="请输入手机号或邮箱">
                <ul class="errors-list filled" id="require_1">
                    <li class="parsley-required">
                        <span class="icon-cancel-circled">必填项</span>
                    </li>
                </ul>
            </div>
            <div class="group">
                <i class="iconfont icon-mima2"></i>
                <input type="password" name="password" id="pass" required="">
                <ul class="errors-list filled" id="require_2">
                    <li class="parsley-required">
                        <span class="icon-cancel-circled">必填项</span>
                    </li>
                </ul>
            </div>
            <!-- <div class="group">
                <i class="iconfont icon-verification-code"></i>
                <input type="text" name="scode" id="scode" data-parsley-id="8">
            </div> -->
            <div class="group group-single">
                <input type="checkbox">
                <label for="rememberme" class="">自动登录</label>&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#" class="forget-pwd" target="_blank">忘记密码</a>
            </div>
            <div class="group">
                <button class="button j_submit button-login" type="button" id="submit">登&nbsp;&nbsp;录</button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="static/icheck/icheck.js"></script>
<script type="text/javascript" src="static/js/login.js"></script>
</body>
</html>
