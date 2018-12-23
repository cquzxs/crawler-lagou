/**
 * 包含功能
 * 点击开始爬取招聘信息按钮后，执行startCrawler()方法
 * 1.通过ajax启动爬虫线程
 * 2.每隔1秒通过ajax获取后台爬取数据时的实时数据 getCrawlerInfo()
 */
//页面加载
$(function() {
    getInfoInit();//爬取状态
    getInfo2Init();//更新状态
    createChart();
});
/*----------------------------爬取状态 start-------------------------------------*/
function getInfoInit() {
    $.ajax({
        type: "POST",//请求方式
        url: "getCrawlerInfo",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData(result);
            //开启定时任务
            startTimer(result);
        },
        error: function(){
            //alert("error getCrawlerInfo")
        } //错误执行方法
    });
    function startTimer(result){

        var status1=$("#a1").html();
        //alert("开启定时任务1："+status1);
        if(status1==="爬取中..."){
            //alert("开启定时任务1");
            getCrawlerInfo();
        }else{
            //alert("未开启定时任务1");
        }

        //加载应届生和是否删除按钮状态
        isSchool=result.isSchool;
        isDelete=result.isDelete;
        loadButtonStatus();
    }
    function loadButtonStatus() {
        //alert("isSchool:"+isSchool);
        //alert("isDelete:"+isDelete);

        if(isSchool==="1"){
            $("#b1").css("background-color","#FF0066");
            $("#b2").css("background-color","");
        }else{
            $("#b2").css("background-color","#FF0066");
            $("#b1").css("background-color","");
        }

        if(isDelete==="1"){
            $("#b3").css("background-color","#FF0066");
            $("#b4").css("background-color","");
        }else{
            $("#b4").css("background-color","#FF0066");
            $("#b3").css("background-color","");
        }
    }
}
/*----------------------------爬取状态 end-------------------------------------*/
/*----------------------------更新状态 start-------------------------------------*/
function getInfo2Init() {
    $.ajax({
        type: "POST",//请求方式
        url: "getUpdateInfo",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData2(result);
            //开启定时任务
            startTimer2(result);
        },
        error: function(){
            //alert("error getCrawlerInfo")
        } //错误执行方法
    });
}
function startTimer2(result){
    var status2=result;
    //alert("开启定时任务2："+status2);
    if(status2 === "更新中"
        || status2 === "正在更新视图1"
        || status2 === "正在更新视图2"
        || status2 === "正在更新视图3"
        || status2 === "正在更新视图4"
        || status2 === "正在更新视图5" ){

        //alert("开启定时任务2");
        getUpdateInfo();//开启定时任务
    }else{
        //alert("未开启定时任务2");
    }
}
/*----------------------------更新状态 end-------------------------------------*/

/*----------------------------选择是否是应届生以及是否删除现有数据 start-------------------------------------*/
var isSchool="1";    //1 应届生 0 非应届生
var isDelete="1";    //1 删除现有数据 0 不删除现有数据
function chooseIsSchool(){
    if(isSchool==="1"){
        isSchool="0";
    }else{
        isSchool="1";
    }
    if(isSchool==="1"){
        $("#b1").css("background-color","#FF0066");
        $("#b2").css("background-color","");
    }else{
        $("#b2").css("background-color","#FF0066");
        $("#b1").css("background-color","");
    }
}

function chooseIsDelete(){
    if(isDelete==="1"){
        isDelete="0";
    }else{
        isDelete="1";
    }
    if(isDelete==="1"){
        $("#b3").css("background-color","#FF0066");
        $("#b4").css("background-color","");
    }else{
        $("#b4").css("background-color","#FF0066");
        $("#b3").css("background-color","");
    }
}
/*----------------------------选择是否是应届生以及是否删除现有数据 end-------------------------------------*/
/*----------------------------开始爬虫 start-------------------------------------*/
var myInterval;
function startCrawler() {
    $("#a1").html("初始化");
    $("#a12").html("");
    if(myInterval!=null){
        window.clearInterval(myInterval);
    }
    $.ajax({
        type: "POST",//请求方式
        url: "startCrawler?isSchool="+isSchool+"&isDelete="+isDelete,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData(result);
            //开启定时任务
            getCrawlerInfo();
        },
        error: function(){
            //alert("error startCrawler")
        } //错误执行方法
    });
}
var currentPercentage=0;
function parseData(result) {
    console.log(result);
    $("#a1").html(result.status);
    $("#a2").html(result.startTime);
    $("#a3").html(result.endTime);
    $("#a4").html(result.duration);

    $("#a5").html(result.dataCount);
    $("#a7").html(result.crawlerSpeed);
    $("#a6").html(result.requestTimes);

    $("#a8").html(result.requestThreadCount);
    $("#a10").html(result.executedTaskCount);
    //alert(currentPercentage);
    //绘制进度条
    if(result.requestThreadCount!="" && result.executedTaskCount!=""){
        var temp=(parseInt(result.executedTaskCount)/parseInt(result.requestThreadCount))*100;
        var temp2=parseInt(temp);
        /*        if(temp2>currentPercentage){
                    currentPercentage=temp2;
                    createBar(temp2);
                }*/
        createBar(temp2);
    }
    //绘制速度实时折线图
    //createChart();


}


function getCrawlerInfo(){
    myInterval=setInterval("getInfo()",1000);//每5秒执行一次ajax请求
}
function getInfo() {
    $.ajax({
        type: "POST",//请求方式
        url: "getCrawlerInfo",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData(result);
        },
        error: function(){
            //alert("error getCrawlerInfo")
        } //错误执行方法
    });
}
/*----------------------------开始爬虫 end-------------------------------------*/
/*----------------------------停止爬虫 start-------------------------------------*/
function stopCrawler(){
    if(myInterval!=null){
        window.clearInterval(myInterval);
    }
    $.ajax({
        type: "POST",//请求方式
        url: "stopCrawler",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData(result);
        },
        error: function(){
            //alert("error updateViews")
        } //错误执行方法
    });
}
/*----------------------------停止爬虫 end-------------------------------------*/
/*----------------------------更新视图 start-------------------------------------*/
function updateViews(){
    $("#a12").html("更新中...");
    $.ajax({
        type: "POST",//请求方式
        url: "updateViews",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData2(result);
            //开启定时任务
            getUpdateInfo();
        },
        error: function(){
            //alert("error updateViews")
        } //错误执行方法
    });
}
function parseData2(result){
    $("#a12").html(result);
}
function getUpdateInfo() {
    myInterval=setInterval("getInfo2()",3000);//每5秒执行一次ajax请求
}
function getInfo2() {
    $.ajax({
        type: "POST",//请求方式
        url: "getUpdateInfo",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData2(result);
        },
        error: function(){
            //alert("error getCrawlerInfo")
        } //错误执行方法
    });
}
/*----------------------------更新视图 end-------------------------------------*/

