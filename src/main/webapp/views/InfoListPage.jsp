<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
    <title>招聘信息展示</title>

    <link rel="stylesheet" href="css/tableStyle.css">
    <link rel="stylesheet" href="css/headStyle.css">
    <link rel="stylesheet" href="css/buttonStyle.css">
    <link rel="stylesheet" href="css/customStyle.css">
    <link rel = "Shortcut Icon" href=img/icon2.ico>
    <script src="js/frame/jquery-3.1.1.min.js"></script>
    <script src="js/custom/paging.js"></script>
    <script src="js/custom/infoList.js"></script>
    <style>
        body{
            background-image: url(img/bg11.jpg);
            background-size:100% 100%;
            background-attachment: fixed;
        }
    </style>
</head>

<body>

<div><%--导航栏--%>
    <ul class="pgwMenuCustom">
        <li><a href="getWelcomePage">首页</a></li>
        <li><a href="getCrawlerPage">爬虫管理</a></li>
        <li><a class="selected" href="getInfoListPage">招聘信息展示</a></li>
        <li><a href="getDataVisualPage">数据可视化</a></li>
    </ul>
</div>

<div id="container"><%--存放招聘信息列表--%>
    <div style="height: 5%">

    </div>
    <div>   <%--搜索框--%>
        <button id="b11" class="button button-pill button-tiny" onclick="chooseIsSchool();" style="background-color:#FF0066">应届生</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button id="b22" class="button button-pill button-tiny" onclick="chooseIsSchool();">非应届生</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="text" placeholder="请输入职位名称" id="search_text" autocomplete="off" class="input_search_key" list="pasta">
        <button class="button button-3d button-royal" onclick="search();">搜索</button>
        <div id="auto_div" class="search_suggest"></div>
        <datalist id="pasta">
            <option>Java</option>
            <option>PHP</option>
            <option>Python</option>
        </datalist>
    </div>

    <div id="job"><%--存放职位列表--%>

    </div>
    <div style="height: 1%">

    </div>
    <div id="noInfo">
        <img src="img/noInfo.png" alt=""><br>
        <span id="noInfoSpan">&nbsp;暂&nbsp;无&nbsp;信&nbsp;息&nbsp;</span>
    </div>
    <div class="page-shopping-cart" id="shopping-cart" style="display: none">  <%--table-div--%>
        <h4 class="cart-title">招聘信息列表</h4>   <%--table-title--%>
        <div class="cart-product-title clearfix">   <%--table-head--%>
            <div class="td-code fl">公司名称</div>
            <div class="td-time fl">岗位名称</div>
            <div class="td-zg fl">工作城市</div>
            <div class="td-zg fl">薪资范围</div>
            <div class="td-zx fl">公司规模</div>
            <div class="td-buy1 fl">职位优势</div>
            <div class="td-sell1 fl">发布时间</div>
        </div>
        <div class="cart-product">  <%--table-body--%>
            <table>
                <tbody id="table-2">

                </tbody>
            </table>
        </div>
        <div id="pageInfo" class="cart-product-info"><%--表格分页--%><%--table-tail--%>
            当前&nbsp;<span id="currentPage"></span>/<span id="pageCount"></span>&nbsp;页&nbsp;&nbsp;
            每页&nbsp;<input id="pageSize" type="text" size="1" maxlength="2" onkeyup="value=value.replace(/[^\d]/g,'')"/>&nbsp;条<button id="setPageSize" onclick="setPageSize();">设置</button>&nbsp;
            共有&nbsp;<span id="totalCount"></span>&nbsp;条数据&nbsp;
            <button id="firstPage" onclick="firstPage();">首页</button>&nbsp;<button id="prePage" onclick="prePage();">上一页</button>&nbsp;<button id="nextPage" onclick="nextPage();">下一页</button>&nbsp;<button id="tailPage" onclick="tailPage();">尾页</button>&nbsp;
            转到&nbsp;<input id="pageNumber" type="text" size="1" maxlength="4" onkeyup="value=value.replace(/[^\d]/g,'')"/>&nbsp;页&nbsp;<button id="goToCertainPage" onclick="goToCertainPage();">跳转</button>
        </div>
    </div>
</div>
</body>
</html>
