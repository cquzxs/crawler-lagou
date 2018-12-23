<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据可视化</title>
    <link rel="stylesheet" href="css/headStyle.css">
    <link rel="stylesheet" href="css/buttonStyle.css">
    <link rel="stylesheet" href="css/customStyle.css">
    <link rel = "Shortcut Icon" href=img/icon2.ico>
    <script src="js/frame/jquery-3.1.1.min.js"></script>
    <script src="js/frame/flotr2.min.js"></script>
    <script src="js/custom/dataVisual.js"></script>
    <style>
        body{
            background-image: url(img/bg11.jpg);
            background-size:100% 100%;
            background-attachment: fixed;
        }
    </style>
</head>
<body>
<ul class="pgwMenuCustom">
    <li><a href="getWelcomePage">首页</a></li>
    <li><a href="getCrawlerPage">爬虫管理</a></li>
    <li><a href="getInfoListPage">招聘信息展示</a></li>
    <li><a class="selected" href="getDataVisualPage">数据可视化</a></li>
</ul>
<div>
    <div id="container"><%--存放招聘信息列表--%>
        <div style="height: 5%">

        </div>
        <div>   <%--搜索框--%>
            <button id="b111" class="button button-pill button-tiny" onclick="chooseIsSchool();" style="background-color:#FF0066">应届生</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button id="b222" class="button button-pill button-tiny" onclick="chooseIsSchool();">非应届生</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

            <input type="text" placeholder="请输入职位名称" id="search_text" list="pasta">
            <button class="button button-3d button-royal" onclick="search();">搜索</button>
            <datalist id="pasta">
                <option>Java</option>
                <option>PHP</option>
                <option>Python</option>
            </datalist>
        </div>

        <div id="job"><%--存放职位列表--%>

        </div>
        <div style="height: 1%"></div>
        <div class="dataVisual">
            <ul class="pgwMenuCustom2">
                <li><a onclick="showChart1();" id="a1">基于城市的薪资对比柱状图</a></li>
                <li><a onclick="showChart2();" id="a2">城市分布饼状图</a></li>
                <li><a onclick="showChart3();" id="a3">基于岗位的薪资对比柱状图</a></li>
                <li><a onclick="showChart4();" id="a4">基于发布时间的折线图</a></li>
                <li><a onclick="showChart5();" id="a5">基于公司规模的雷达图</a></li>
                <li><a onclick="showChart6();" id="a6">基于薪资范围的柱状图</a></li>
            </ul>
            <div id="chartContainer" style="display: none">
                <div id="chart" style="width: 600px;height:300px;margin: auto"></div>
                <div style="height: 1%"></div>
                <div class="introduction1">
                    薪资对比柱状图以城市名称为横坐标，以平均薪资为纵坐标，展现了当前岗位在不同城市间平均薪资的对比。
                </div>
                <div style="height: 1%"></div>
            </div>
            <div id="chart2Container" style="display: none">
                <div id="chart2" style="width: 600px;height:300px;margin: auto"></div>
                <div style="height: 1%"></div>
                <div class="introduction2">
                    基于工作岗位的薪资对比柱状图以岗位名称为横坐标，以岗位的平均薪资为纵坐标，展现了不同岗位的平均薪资对比。
                </div>
                <div style="height: 1%"></div>
            </div>
            <div id="chart3Container" style="display: none">
                <div id="chart3" style="width: 600px;height:300px;margin: auto"></div>
                <div style="height: 1%"></div>
                <div class="introduction3">
                    城市分布饼状图展现了当前岗位在不同城市的需求量占比。
                </div>
                <div style="height: 1%"></div>
            </div>
            <div id="chart4Container" style="display: none">
                <div id="chart4" style="width: 1200px;height:300px;margin: auto"></div>
                <div style="height: 1%"></div>
                <div class="introduction4">
                    基于发布时间的折线图展示了当前岗位什么时间需求量大，什么时间需求量小。
                </div>
                <div style="height: 1%"></div>
            </div>
            <div id="chart5Container" style="display: none">
                <div id="chart5" style="width: 600px;height:300px;margin: auto"></div>
                <div style="height: 1%"></div>
                <div class="introduction5">
                    基于公司规模的雷达图展示了当前工作岗位在不同公司规模的公司的需求量。
                </div>
                <div style="height: 1%"></div>
            </div>
            <div id="chart6Container" style="display: none">
                <div id="chart6" style="width: 600px;height:300px;margin: auto"></div>
                <div style="height: 1%"></div>
                <div class="introduction6">
                    基于薪资范围的柱状图展示了当前岗位的薪资范围。
                </div>
                <div style="height: 1%"></div>
            </div>
        </div>

    </div>
</div>
</body>
</html>
