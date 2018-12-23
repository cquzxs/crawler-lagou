//后端分页
/*----------------------------选择是否是应届生 start-------------------------------------*/
var isSchool=1;    //1 应届生 0 非应届生
var job='Java';
var isFuzzyQuery="0";
function chooseIsSchool(){
    if(isSchool==1){
        isSchool=0;
    }else{
        isSchool=1;
    }
    if(isSchool==1){
        $("#b11").css("background-color","#FF0066");
        $("#b22").css("background-color","");
    }else{
        $("#b22").css("background-color","#FF0066");
        $("#b11").css("background-color","");
    }
    showInfoList(job,isFuzzyQuery);
}
/*----------------------------选择是否是应届生 end-------------------------------------*/
function showInfoList(jobName,isFuzzy){
    job=jobName;
    isFuzzyQuery=isFuzzy;
    //alert("模糊查询："+isFuzzy);
    $.ajax({
        type: "POST",//请求方式
        url: "showInfoList?jobName="+jobName+"&isSchool="+isSchool+"&isFuzzyQuery="+isFuzzyQuery,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData4(result);
            //显示分页信息
            showPageInfo();
            //改变按钮颜色
            changeColor();
        },
        error: function(){
            //alert("error showInfoList")
        } //错误执行方法
    });
}
function changeColor() {
    var temp=decodeURI(job);
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
function parseData4(result) {
    var html='';
    $.each(result,function (index,item) {
        html=html+'<tr class="mytr'+index%2+'">';
        html=html+'<td class="td-code fl"><a target="_blank" href="'+item.detailUrl+'">'+item.companyName+'</a></td>';
        html=html+'<td class="td-time fl">'+item.jobName+'</td>';
        html=html+'<td class="td-zg fl">'+item.cityName+'</td>';
        html=html+'<td class="td-zg fl">'+item.salaryRange+'</td>';

        html=html+'<td class="td-zx fl">'+item.companySize+'</td>';
        html=html+'<td class="td-buy1 fl">'+item.positionAdvantage+'</td>';

        html=html+'<td class="td-sell1 fl">'+item.createTime+'</td>';
        html=html+'</tr>';
    });
    if(result.length<=0){
        $("#noInfo").show();
        $("#shopping-cart").hide();
    }else{
        $("#noInfo").hide();
        $("#shopping-cart").show();
    }
    var temp=html;
    $("#table-2").html(temp);
}

function showPageInfo() {
    $.ajax({
        type: "POST",//请求方式
        url: "showPageInfo",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData5(result);
        },
        error: function(){
            //alert("error showPageInfo")
        } //错误执行方法
    });
}

function parseData5(result){
    var currentPage=result.currentPage;
    var pageCount=result.pageCount;
    var pageSize=result.pageSize;
    var totalCount=result.totalCount;

    $("#currentPage").html(currentPage);
    $("#pageCount").html(pageCount);
    $("#pageSize").val(pageSize);
    $("#totalCount").html(totalCount);

    $("#changePage").val();
}

function firstPage(){
    $.ajax({
        type: "POST",//请求方式
        url: "firstPage",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData4(result);
            //显示分页信息
            showPageInfo();
            //回到顶部
            $(document).scrollTop(0);
        },
        error: function(){
            //alert("error firstPage")
        } //错误执行方法
    });
}

function setPageSize(){
    var pageSize=$("#pageSize").val();
    if(pageSize===""){
        alert("输入框为空");
        return;
    }
    $.ajax({
        type: "POST",//请求方式
        url: "setPageSize?pageSize="+pageSize,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData4(result);
            //显示分页信息
            showPageInfo();
            //回到顶部
            $(document).scrollTop(0);
        },
        error: function(){
            //alert("error setPageSize");
        } //错误执行方法
    });
}

function prePage(){
    $.ajax({
        type: "POST",//请求方式
        url: "prePage",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData4(result);
            //显示分页信息
            showPageInfo();
            //回到顶部
            $(document).scrollTop(0);
        },
        error: function(){
            //alert("error prePage")
        } //错误执行方法
    });
}

function nextPage(){
    $.ajax({
        type: "POST",//请求方式
        url: "nextPage",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData4(result);
            //显示分页信息
            showPageInfo();
            //回到顶部
            $(document).scrollTop(0);
        },
        error: function(){
            //alert("error nextPage")
        } //错误执行方法
    });
}

function tailPage(){
    $.ajax({
        type: "POST",//请求方式
        url: "tailPage",//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData4(result);
            //显示分页信息
            showPageInfo();
            //回到顶部
            $(document).scrollTop(0);
        },
        error: function(){
            //alert("error tailPage")
        } //错误执行方法
    });
}

function goToCertainPage(){
    var pageNumber=$("#pageNumber").val();
    if(pageNumber===""){
        alert("输入框为空");
        return;
    }
    $.ajax({
        type: "POST",//请求方式
        url: "goToCertainPage?pageNumber="+pageNumber,//地址，就是json文件的请求路径
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(result){//返回的参数就是 action里面所有的有get和set方法的参数
            //解析数据
            parseData4(result);
            //显示分页信息
            showPageInfo();
            //回到顶部
            $(document).scrollTop(0);
        },
        error: function(){
            //alert("error goToCertainPage")
        } //错误执行方法
    });
}

