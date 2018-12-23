/*------------------------页面加载 start--------------------------*/
//页面加载   获取全部信息
var globelResult;
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
                var temp2='<button id="'+key+'" '+' class="button button-pill" onclick="showInfoList(\''+result[key]+'\',\'0\');">'+key+'</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
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
    var isFuzzyQuery="0";
    var temp=$("#search_text").val();
    if(temp===""){
        alert("搜索框为空！")
        return;
    }
    for(var key in globelResult){
        if(temp===key){
            showInfoList(globelResult[key],isFuzzyQuery);
            return;
        }
    }
    isFuzzyQuery="1";
    showInfoList(temp,isFuzzyQuery);
}
/*------------------------页面加载 end--------------------------*/

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
