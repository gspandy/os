$(function(){
    //չ�����촰��������
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
    //��ֹð��
    tmListDl.on("click",function(event){
        event.stopPropagation();
    });

    //չ�����������б�
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

    //д���������
    $(".quick_list").on("click","li",function(){
        var textareas = $(this).parent().parent().prev().find("textarea"),
            reTxt = $(this).text();
        textareas.val(reTxt);
    });

    //��������
    chartBox.on("click",".send_txt",function(){
        var chartTxt = $(this).prev().find("textarea").val(),
            chartList = "<dl class='cf service'><dt><img src='images/service.jpg'></dt><dd><span class='send_time'>"+"11-08 18:31"
                +"</span><div class='send_con'>"+chartTxt+"</div></dd></dl>";
        if($.trim(chartTxt) != ""){
            $(this).parent().parent().find(".talk_list").append(chartList);
            $(this).prev().find("textarea").val("");
        }
    });
    //�س�����
    document.onkeydown = function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){
            $(".send_txt").click();
        }
    };

    //�ر����촰��
    chartBox.on("click",".close_chart",function(){
        closeBox($("#chartBox"));
    });

    //ɾ�������Ա
    chartBox.on("mousedown","a.close",function(){
        $(this).parent().parent().animate({"right":"-100px","opacity":.2},300,function(){
            $(this).remove();
        })
    });

});
