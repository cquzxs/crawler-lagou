<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>爬虫管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/headStyle.css">
    <link rel="stylesheet" href="css/buttonStyle.css">
    <link rel = "Shortcut Icon" href=img/icon2.ico>

    <script src="js/frame/jquery-3.1.1.min.js"></script>
    <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
    <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>

    <script src="js/custom/progressBar.js"></script><%--进度条--%>
    <script src="js/custom/speed.js"></script><%--爬取速度折线图--%>
    <script src="js/custom/crawler.js"></script>

    <style>
        body{
            background-image: url(img/bg11.jpg);
            background-size:100% 100%;
            background-attachment: fixed;
        }
        .flex-center{
            display: flex;
            flex-direction: column;
            align-items: left;
        }
        .container{
            display: inline-block;
            width: 25%;
            height: 20px;
            padding-right: 10px;
            border: 1px solid #999;
            border-radius: 5px;
        }
        .h-100P{
            height: 100%;
        }
        .bar{
            display: inline-block;
            background: #90bf46;
            color: white;
            font-weight: bold;
            padding: 0 5px;
            text-align: right;
            border-radius: 5px;
            border-right: 1px solid #999;
        }
    </style>
</head>
<body>

<ul class="pgwMenuCustom">
    <li><a href="getWelcomePage">首页</a></li>
    <li><a class="selected" href="getCrawlerPage">爬虫管理</a></li>
    <li><a href="getInfoListPage">招聘信息展示</a></li>
    <li><a href="getDataVisualPage">数据可视化</a></li>
</ul>

<div>
    <br>
    请选择招聘信息类别：
    <button id="b1" class="button button-pill button-tiny" onclick="chooseIsSchool();" style="background-color:#FF0066">应届生</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <button id="b2" class="button button-pill button-tiny" onclick="chooseIsSchool();">非应届生</button><br><br>
    是否删除现有数据&nbsp;&nbsp;&nbsp;：
    <button id="b3" class="button button-pill button-tiny" onclick="chooseIsDelete();" style="background-color:#FF0066">&nbsp;&nbsp;&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <button id="b4"class="button button-pill button-tiny" onclick="chooseIsDelete();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button><br><br>

    <button class="button button-3d button-primary button-rounded" onclick="startCrawler();">开始爬取招聘信息</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <button onclick="stopCrawler();" class="button button-3d button-action button-pill">停止爬取招聘信息</button>
    <br><br><br>

    爬取状态：<span id="a1">${info.status}</span><br>
    开始爬取时间：<span id="a2">${info.startTime}</span><br>
    结束爬取时间：<span id="a3">${info.endTime}</span><br>
    已爬取时长：<span id="a4">${info.duration}</span><br>
    <br>
    当前爬取数据总数：<span id="a5">${info.dataCount}</span><br>
    <%--当前爬取速度：--%><span id="a7" style="display: none">${info.crawlerSpeed}</span><br>
    <%--爬取速度折线图容器--%>
    <div id="container" style="width:640px;height:200px;display: none" ></div>
    <%--当前请求URL总数：<span id="a6">${info.requestTimes}</span><br>--%>
    <br>
    <%--        任务总数：<span id="a8" >${info.requestThreadCount}</span><br>
            已执行任务数：<span id="a10" >${info.executedTaskCount}</span><br>--%>
    <%--任务进度进度条--%>
    <div class="flex-center">
        <span>任务进度(<span id="a10" >${info.executedTaskCount}</span>/<span id="a8" >${info.requestThreadCount}</span>)</span>
        <span class="container">
                 <span id="progressBar" class="h-100P bar"></span>
        </span>
    </div>
    <br>
    <button class="button button-3d button-primary button-rounded" onclick="updateViews();">更新视图信息</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <br><br><br>
    更新状态：<span id="a12"></span><br>

</div>
</body>
</html>
