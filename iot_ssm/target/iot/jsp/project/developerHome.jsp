<%@ page import="com.wust.iot.model.User" %><%--
  Created by IntelliJ IDEA.
  User: coder
  Date: 18-1-6
  Time: 下午12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>开发者首页</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="static/css/iconfont.css">
    <link rel="stylesheet" type="text/css" href="static/css/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="static/css/jquery-ui.theme.min.css">
    <link rel="stylesheet" type="text/css" href="static/css/developerHome.css">
    <link type="favicon" rel="shortcut icon" href="static/img/favicon.ico"/>
    <script type="text/javascript" src="static/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="static/js/jquery-ui.min.js"></script>
</head>
<body>
<form>
    <input type="hidden" class="user_id" value="${sessionScope.user.id}">
</form>
<div class="header">
    <div class="header-detail">
        <div class="menuList">
            <a href="#" class="logo"></a>
            <span class="apart"></span>
            <a href="#" class="menu" title="首页">首页</a>
            <div class="menu multi">
                <span>案例与伙伴</span>
            </div>
            <a href="#" class="menu" title="开发文档">开发文档</a>
            <a href="#" class="menu" title="发现">发现</a>
            <a href="#" class="menu" title="社区">社区</a>
            <div class="menu multi">
                <span>动态</span>
            </div>
        </div>
        <div></div>
        <div class=""></div>
    </div>
</div>
<div class="ui-product">
    <div class="product-nav">
        <a href="project/createPorject" class="create"><i class="iconfont icon-chuangjianxiangmu"></i>创建产品</a>
    </div>
    <div class="product-body">
        <div class="p-wrap" id="wrap">
        </div>
    </div>
</div>
<div id="dialog-modal" class="no-close">
    <p>您确认要删除此产品？</p>
</div>

<script type="text/javascript" src="static/js/developerHome.js"></script>
</body>
</html>
