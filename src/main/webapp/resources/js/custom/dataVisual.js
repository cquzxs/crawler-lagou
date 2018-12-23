/**
 * 数据可视化异步加载
 */

/*------------------------页面加载 start--------------------------*/
var jobName='Java';//key的url编码
var jobName2='Java';//key
var globelResult;
//页面加载   获取全部信息
$(function() {
    $.ajax({
        type: "POST",//请求方式
        url: "getJobList",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function (result) {//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData2(result);
            globelResult=result;
        },
        error: function () {
            //alert("error getJobList")
        }//错误执行方法

    });
});
function changeColor() {
    var temp=decodeURI(jobName2);
    switch (temp){
        case "PHP":
            changeColorHelp("PHP");
            break;
        case "Java":
            changeColorHelp("Java");
            break;
        case "web前端":
            changeColorHelp("web前端");
            break;
        case "市场营销":
            changeColorHelp("市场营销");
            break;
        case "UI设计师":
            changeColorHelp("UI设计师");
            break;
        case "Python":
            changeColorHelp("Python");
            break;
        case "算法工程师":
            changeColorHelp("算法工程师");
            break;
        default:
            changeColorHelp("");
            break;
    }
}
function changeColorHelp(id) {
    //全部重置颜色
    $("#PHP").css("background","");
    $("#算法工程师").css("background","");
    $("#Python").css("background","");
    $("#UI设计师").css("background","");
    $("#市场营销").css("background","");
    $("#web前端").css("background","");
    $("#Java").css("background","");
    //给按钮加背景色
    $("#"+id).css("background","#00ff00");
}
function parseData2(result) {
    console.log(result);//返回的是JSONObject
    var i=0;
    var temp='';
    for(var key in result){
        if(i<7){
            if(key.indexOf("PHP")>=0 ||
                key.indexOf("Python")>=0 ||
                key.indexOf("市场营销")>=0 ||
                key.indexOf("web前端")>=0 ||
                key.indexOf("算法工程师")>=0 ||
                key.indexOf("UI设计师")>=0 ||
                "Java".indexOf(key)>=0 ){
                var temp2='<button id="'+key+'" '+' class="button button-pill" onclick="setJobName(\''+key+'\',\''+result[key]+'\');">'+key+'</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                temp=temp+temp2;
                i++;
            }
        }else{
            break;
        }
    }
    $("#job").html(temp);
}

function search() {
    var temp=$("#search_text").val();
    if(temp===""){
        alert("搜索框为空！")
        return;
    }
    for(var key in globelResult){
        if(key.indexOf(temp)>=0){
            setJobName(key,globelResult[key]);
            break;
        }
    }
}
function setJobName(key,value){
    jobName2=key;
    jobName=value;
    //改变按钮颜色
    changeColor();
    show();
}
/*------------------------页面加载 end--------------------------*/

/*----------------------------选择是否是应届生 start-------------------------------------*/
var isSchool=1;    //1 应届生 0 非应届生
function chooseIsSchool(){
    if(isSchool==1){
        isSchool=0;
    }else{
        isSchool=1;
    }
    if(isSchool==1){
        $("#b111").css("background-color","#FF0066");
        $("#b222").css("background-color","");
    }else{
        $("#b222").css("background-color","#FF0066");
        $("#b111").css("background-color","");
    }
    show();
}
var currentChart=1;
function show() {
    if(currentChart===1){
        showChart1();
    }
    if(currentChart===2){
        showChart2();
    }
    if(currentChart===3){
        showChart3();
    }
    if(currentChart===4){
        showChart4();
    }
    if(currentChart===5){
        showChart5();
    }
    if(currentChart===6){
        showChart6();
    }
}
/*----------------------------选择是否是应届生 end-------------------------------------*/
/*------------------------视图1 start--------------------------*/
function showChart1() {
    currentChart=1;
    getSalaryCompareViewByJobName();
    $("#chartContainer").show();
    $("#chart2Container").hide();
    $("#chart3Container").hide();
    $("#chart4Container").hide();
    $("#chart5Container").hide();
    $("#chart6Container").hide();
    $("#a1").css("background-color","#9966CC");
    $("#a2").css("background-color","");
    $("#a3").css("background-color","");
    $("#a4").css("background-color","");
    $("#a5").css("background-color","");
    $("#a6").css("background-color","");
}
function getSalaryCompareViewByJobName(){
    $.ajax({
        type: "POST",//请求方式
        url: "getSalaryCompareViewByJobName?jobName="+jobName+"&isSchool="+isSchool,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            drawSalaryCompareView(result);
        },
        error: function(){
            //alert("error");
        }//错误执行方法
    });
}
function drawSalaryCompareView(result) {
    //result是一个集合,所以需要先遍历
    var wins2 = [];
    var teams = [];
    $.each(result, function (index, obj) {
        if (index < 10) {
            var temp = [ [ index, obj['avgSarary'] ] ];
            wins2.push(temp);
            var temp2 = [ index, obj['cityName'] ];
            teams.push(temp2);
        }
    });
    Flotr.draw(document.getElementById("chart"), wins2, {
        title: jobName2+"岗位基于城市的薪资对比柱状图",
        colors: ["#FF0000", "#FF00CC", "#FF3366", "#FF6666", "#FF99FF", "#FFCCCC", "#FFFF33", "#66FFFF", "#66FF00", "#66CC33"],
        bars: {
            show: true,
            barWidth: 0.5,
            shadowSize: 0,
            fillOpacity: 1,
            lineWidth: 0
        },
        yaxis: {
            title:"平均薪资（k）",
            min: 0,
            tickDecimals: 0
        },
        xaxis: {
            ticks: teams
        },
        grid: {
            horizontalLines: false,
            verticalLines: false
        }
    });
}
/*------------------------视图1 end--------------------------*/

/*------------------------视图2 start--------------------------*/
function showChart2() {
    currentChart=2;
    getCityDistributeViewByJobName();
    $("#chartContainer").hide();
    $("#chart2Container").hide();
    $("#chart3Container").show();
    $("#chart4Container").hide();
    $("#chart5Container").hide();
    $("#chart6Container").hide();
    $("#a1").css("background-color","");
    $("#a2").css("background-color","#9966CC");
    $("#a3").css("background-color","");
    $("#a4").css("background-color","");
    $("#a5").css("background-color","");
    $("#a6").css("background-color","");
}
function getCityDistributeViewByJobName(){
    $.ajax({
        type: "POST",//请求方式
        url: "getCityDistributeViewByJobName?jobName="+jobName+"&isSchool="+isSchool,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            drawCityDistributeView(result);
        },
        error: function(){
            //alert("error");
        } //错误执行方法
    });
}
function drawCityDistributeView(result) {
    //result是一个集合,所以需要先遍历
    var data = [];
    var other = 0;
    $.each(result, function (index, obj) {
        if (index <= 5) {
            var temp = {data: [[index, obj['count']]], label: obj['cityName']};
            data.push(temp);
        } else {
            other = other + obj['count'];
        }
    });
    var temp = {data: [[6, other]], label: "其他"};
    data.push(temp);
    Flotr.draw(document.getElementById("chart3"), data, {
        title: jobName2+"岗位需求量城市分布饼状图",
        colors: ["#FF0000", "#FF00CC", "#66CC33", "#FF6666", "#FF99FF", "#FFCCCC", "#FFFF33"],
        pie: {
            show: true
        },
        yaxis: {
            showLabels: false
        },
        xaxis: {
            showLabels: false
        },
        grid: {
            horizontalLines: false,
            verticalLines: false
        }
    });
}
/*------------------------视图2 end--------------------------*/

/*------------------------视图3 start--------------------------*/
function showChart3() {
    currentChart=3;
    selectAvgSalaryByJobName();
    $("#chartContainer").hide();
    $("#chart2Container").show();
    $("#chart3Container").hide();
    $("#chart4Container").hide();
    $("#chart5Container").hide();
    $("#chart6Container").hide();
    $("#a1").css("background-color","");
    $("#a2").css("background-color","");
    $("#a3").css("background-color","#9966CC");
    $("#a4").css("background-color","");
    $("#a5").css("background-color","");
    $("#a6").css("background-color","");
}
function selectAvgSalaryByJobName(){
    $.ajax({
        type: "POST",//请求方式
        url: "selectAvgSalaryByJobName?isSchool="+isSchool,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            drawSalaryCompareViewByJobName(result);
        },
        error: function(){
            //alert("error");
        }//错误执行方法

    });
}
function drawSalaryCompareViewByJobName(result) {
    //result是一个集合,所以需要先遍历
    var wins2 = [];
    var teams = [];
    var temp2=['Java','PHP','C++','Python','Android','iOS','web前端','C#','.NET','VB'];
    var flag=0;
    var i=0;
    $.each(result, function (index, obj) {
        flag=0;
        switch (obj['jobName']){
            case temp2[0]:
                flag=1;
                break;
            case temp2[1]:
                flag=1;
                break;
            case temp2[2]:
                flag=1;
                break;
            case temp2[3]:
                flag=1;
                break;
            case temp2[4]:
                flag=1;
                break;
            case temp2[5]:
                flag=1;
                break;
            case temp2[6]:
                flag=1;
                break;
            case temp2[7]:
                flag=1;
                break;
            case temp2[8]:
                flag=1;
                break;
            case temp2[9]:
                flag=1;
                break;
            default:
                break;
        }
        if (flag==1) {
            var temp = [[i, obj['avgSarary']]];
            wins2.push(temp);
            var temp3 = [i, obj['jobName']];
            teams.push(temp3);
            i++;
        }
    });
    Flotr.draw(document.getElementById("chart2"), wins2, {
        title: "基于岗位的薪资对比柱状图（参考）",
        colors: ["#FF0000", "#FF00CC", "#FF3366", "#FF6666", "#FF99FF", "#FFCCCC", "#FFFF33", "#66FFFF", "#66FF00", "#66CC33"],
        bars: {
            show: true,
            barWidth: 0.5,
            shadowSize: 0,
            fillOpacity: 1,
            lineWidth: 0
        },
        yaxis: {
            title:"平均薪资（k）",
            min: 0,
            tickDecimals: 0
        },
        xaxis: {
            ticks: teams
        },
        grid: {
            horizontalLines: false,
            verticalLines: false
        }
    });
}
/*------------------------视图3 end--------------------------*/


/*------------------------视图4 start--------------------------*/
function showChart4() {
    currentChart=4;
    getData4();
    $("#chartContainer").hide();
    $("#chart2Container").hide();
    $("#chart3Container").hide();
    $("#chart4Container").show();
    $("#chart5Container").hide();
    $("#chart6Container").hide();
    $("#a1").css("background-color","");
    $("#a2").css("background-color","");
    $("#a3").css("background-color","");
    $("#a4").css("background-color","#9966CC");
    $("#a5").css("background-color","");
    $("#a6").css("background-color","");
}
function getData4(){
    $.ajax({
        type: "POST",//请求方式
        url: "selectBaseCreateTimeViewByJobName?jobName="+jobName+"&isSchool="+isSchool,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function (result) {//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData4(result);
            console.log(result);
        },
        error: function () {
            //alert("error selectBaseCreateTimeViewByJobName")
        }//错误执行方法

    });
}
function parseData4(result){
    /*var co3=[ [1959,315.97],[1960,320.91],[1961,308.64],[1962,345.45] ];*/
    var co2=[];
    var co4=[];
    var sum=0;
    var length=0;
    $.each(result, function (index, obj) {
        var countFlag=parseInt(obj['count']);
        if(countFlag>=0 && countFlag<=1000){
            var temp=[ index,obj['count']  ];//第一层
            co2.push(temp);//第二层

            var temp2=[ index,obj['createTime'].substring(5,10) ];//第一层
            co4.push(temp2);//第二层

            sum=sum+obj['count'];
            length++;
        }
    });
    //计算平均值
    var avg=sum/length;
    var co22=[];
    var co44=[];
    //只保留比平均值大的项
    for(var i=0;i<co2.length;i++){
        if(co2[i][1]>1){
            co22.push(co2[i]);
            co44.push(co4[i]);
        }
    }
    for(var i=0;i<co22.length;i++){
        co22[i][0]=i;
        co44[i][0]=i;
    }
    var ymax;
    if(isSchool===1){
        ymax=100;
    }else{
        ymax=400;
    }
    Flotr.draw(
        document.getElementById("chart4"),
        [
            {data:co22,lines:{show:true}}
        ],
        {
            title:jobName2+"岗位基于发布时间的折线图",
            grid:{horizontalLines:false,verticalLines:false},
            xaxis:{ticks:co44},
            yaxis:{min:0,max:ymax}

        }
    );
}
/*------------------------视图4 end--------------------------*/


/*------------------------视图5 start--------------------------*/
function showChart5(){
    currentChart=5;
    getData5();
    $("#chartContainer").hide();
    $("#chart2Container").hide();
    $("#chart3Container").hide();
    $("#chart4Container").hide();
    $("#chart5Container").show();
    $("#chart6Container").hide();
    $("#a1").css("background-color","");
    $("#a2").css("background-color","");
    $("#a3").css("background-color","");
    $("#a4").css("background-color","");
    $("#a5").css("background-color","#9966CC");
    $("#a6").css("background-color","");
}
function getData5(){
    $.ajax({
        type: "POST",//请求方式
        url: "selectBaseCompanySizeViewByJobName?jobName="+jobName+"&isSchool="+isSchool,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function (result) {//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData5(result);
            console.log(result);
        },
        error: function () {
            //alert("error selectBaseCompanySizeViewByJobName")
        }//错误执行方法

    });
}
function parseData5(result){
    var labels=[
        [0,"50人以下"],
        [1,"50-150人"],
        [2,"150-500人"],
        [3,"500-2000人"],
        [4,"2000人以上"]
    ];

    var datas=[];
    $.each(result, function (index, obj) {
        if(obj['cityName']==="北京" ||
            obj['cityName']==="上海" ||
            obj['cityName']==="广州" ||
            obj['cityName']==="杭州" ||
            obj['cityName']==="深圳" ){
            var data0=[];
            data0.push(obj['cityName']);
            var tempArray=obj['companySize'].split(":");
            if(tempArray.length>=10){
                data0.push(tempArray[1]);
                data0.push(tempArray[3]);
                data0.push(tempArray[5]);
                data0.push(tempArray[7]);
                data0.push(tempArray[9]);
            }
            datas.push(data0);
        }
    });
    var total=[];
    for(var i=1;i<=5;i++){
        var temp=parseInt(datas[0][i])+parseInt(datas[1][i])+parseInt(datas[2][i])+parseInt(datas[3][i])+parseInt(datas[4][i]);
        total.push(temp);
    }
    var objs=[];
    var ymax2=0;
    for(var i=0;i<5;i++){
        var obj={};
        obj.label=datas[i][0];
        var temp=[];
        for(var j=1;j<=5;j++){
            var temp2;
            if(total[j-1]!=0){
                temp2=[j-1,parseInt(datas[i][j])/total[j-1]];
                if(parseInt(datas[i][j])/total[j-1]>ymax2){
                    ymax2=parseInt(datas[i][j])/total[j-1];
                }
            }else{
                temp2=[j-1,0];
            }
            temp.push(temp2);
        }
        obj.data=temp;
        objs.push(obj);
    }
    ymax2=ymax2+0.1;
    Flotr.draw(
        document.getElementById("chart5"),
        [
            objs[1],
            objs[0],
            objs[2],
            objs[4],
            objs[3]

        ],
        {
            title:jobName2+"岗位基于公司规模的雷达图",
            radar:{show:true},
            grid:{circular:true},
            xaxis:{ticks:labels},
            yaxis:{showLabels:false,min:0,max:ymax2}
        }
    );
}
/*------------------------视图5 end--------------------------*/


/*------------------------视图6 start--------------------------*/
function showChart6(){
    currentChart=6;
    getData6();
    $("#chartContainer").hide();
    $("#chart2Container").hide();
    $("#chart3Container").hide();
    $("#chart4Container").hide();
    $("#chart5Container").hide();
    $("#chart6Container").show();
    $("#a1").css("background-color","");
    $("#a2").css("background-color","");
    $("#a3").css("background-color","");
    $("#a4").css("background-color","");
    $("#a5").css("background-color","");
    $("#a6").css("background-color","#9966CC");
}
function getData6(){
    $.ajax({
        type: "POST",//请求方式
        url: "selectBaseSalaryRangeViewByJobName?jobName="+jobName+"&isSchool="+isSchool,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function (result) {//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData6(result);
            console.log(result);
        },
        error: function () {
            //alert("error selectBaseCompanySizeViewByJobName")
        }//错误执行方法

    });
}
function parseData6(result){
    //result是一个集合,所以需要先遍历
    var wins2 = [];//纵坐标
    var teams = [];//横坐标
    $.each(result, function (index, obj) {
        if (index < 7) {
            var temp = [ [ index, obj['count'] ] ];
            wins2.push(temp);
            var temp2 = [ index, obj['salaryRange'] ];
            teams.push(temp2);
        }
    });
    Flotr.draw(document.getElementById("chart6"), wins2, {
        title: jobName2+"岗位基于薪资范围的柱状图",
        colors: ["#FF0000", "#FF00CC", "#FF3366", "#FF6666", "#FF99FF", "#FFCCCC", "#FFFF33"],
        bars: {
            show: true,
            barWidth: 0.5,
            shadowSize: 0,
            fillOpacity: 1,
            lineWidth: 0
        },
        yaxis: {
            title:"招聘信息数（条）",
            min: 0,
            tickDecimals: 0
        },
        xaxis: {
            ticks: teams
        },
        grid: {
            horizontalLines: false,
            verticalLines: false
        }
    });
}
/*------------------------视图6 end--------------------------*/


//搜索框自动提示
function getSearchList(){
    var temp=$("#search_text").val();
    var searchList=[];
    for(var key in globelResult){
        if(key.indexOf(temp)>=0){
            searchList.push(key);
        }
    }
    return searchList;
}
$(function () {
    $("#search_text").focus(function () {
        if ($("#search_text").val() != "") {
            var searchList=getSearchList();
            createAutoDiv(searchList);
        }
    });
    $("#search_text").keyup(function () {
        if ($("#search_text").val() != "") {
            var searchList=getSearchList();
            createAutoDiv(searchList);
        }
    });
    $("#search_text").blur(function () {
    });
});
function createAutoDiv(searchList) {
    var html="";
    for(var i=0;i<searchList.length;i++){
        html=html+'<option>'+searchList[i]+'</option>';// onclick="fillSearch(\''+searchList[i]+'\')"
    }
    $("#pasta").html(html);
}



