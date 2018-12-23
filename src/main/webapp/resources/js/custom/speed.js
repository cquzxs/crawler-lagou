
function createChart(){
    test();
}
function test() {
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    function activeLastPointToolip(chart) {
        var points = chart.series[0].points;
        chart.tooltip.refresh(points[points.length -1]);
    }
    var chart = Highcharts.chart('container', {
        chart: {
            type: 'spline',
            marginRight: 10,
            events: {
                load: function () {
                    var series = this.series[0],
                        chart = this;
                    activeLastPointToolip(chart);
                    setInterval(function () {
                        var x = (new Date()).getTime(); // 当前时间
                  /*          y = Math.random();          // 随机值*/
                        var y=showChart();
                        series.addPoint([x, y], true, true);
                        activeLastPointToolip(chart);
                    }, 1000);
                }
            }
        },
        title: {
            text: '当前爬取速度'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: "次/秒"
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 1);
            }
        },
        legend: {
            enabled: false
        },
        series: [{
            name: '当前数据',
            data: (function () {
                // 生成随机值
                var data = [],
                    time = (new Date()).getTime(),
                    i;
                for (i = -5; i <= 0; i += 1) {
                    data.push({
                        x: time + i * 1000,
                        y: Math.random()*50
                    });
                }
                return data;
            }())
        }]
    });
}
function showChart() {
    var temp=$("#a1").html();
    if(temp==="爬取中..."){
        $("#container").css("display","");
        var temp2=$("#a7").html();
        var y = parseInt(temp2.split(".")[0]);
        return y;
    }else{
        $("#container").css("display","none");
        return Math.random()*50;
    }
}