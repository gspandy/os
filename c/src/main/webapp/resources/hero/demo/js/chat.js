$(function(){
    //展开聊天窗口左侧分组
    $(".chart_group").on("click","li",function(){
        var tmList = $(this).find(".tm_list");
        if(tmList.css("opacity")==0){
            tmList.css({"opacity":1}).slideDown(200);
            $(this).find("i").addClass("keep_it");
        }else{
            $(this).find("i").removeClass("keep_it");
            tmList.css({"opacity":0}).slideUp(200);
        }
    });
    var tmListDl = $(".chart_group li").find("dl");
    //阻止冒泡
    tmListDl.on("click",function(event){
        event.stopPropagation();
    });

    //展开快速输入列表
    var chartBox =  $("#chartBox");
    chartBox.on("click",".quick_write",function(){
        var quickList = $(this).parent().parent().next();
        if(quickList.css("opacity")==0){
            $(this).addClass("open_quick").parent().parent().css({"padding-right":"298px"});
            quickList.css({"opacity":1,"right":0});
        }else{
            $(this).removeClass("open_quick").parent().parent().css({"padding-right":0});
            quickList.css({"opacity":0,"right":"-298px"});
        }
    });

    //写入快速内容
    $(".quick_list").on("click","li",function(){
        var textareas = $(this).parent().parent().prev().find("textarea"),
            reTxt = $(this).text();
        textareas.val(reTxt);
    });

    //发送内容
    chartBox.on("click",".send_txt",function(){
        var chartTxt = $(this).prev().find("textarea").val(),
            chartList = "<dl class='cf service'><dt><img src='images/service.jpg'></dt><dd><span class='send_time'>"+"11-08 18:31"
                +"</span><div class='send_con'>"+chartTxt+"</div></dd></dl>";
        if($.trim(chartTxt) != ""){
            $(this).parent().parent().find(".talk_list").append(chartList);
            $(this).prev().find("textarea").val("");
        }
    });
    //回车发送
    document.onkeydown = function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){
            $(".send_txt").click();
        }
    };

    //关闭聊天窗口
    chartBox.on("click",".close_chart",function(){
        closeBox($("#chartBox"));
    });

    //删除聊天成员
    chartBox.on("mousedown","a.close",function(){
        $(this).parent().parent().animate({"right":"-100px","opacity":.2},300,function(){
            $(this).remove();
        })
    });

});
