$(function(){    
    //展开快速输入列表
	var chartBox =  $("#visitorBox");
    chartBox.on("click",".quick_write",function(){    	
        var quickList = $(this).parent().parent().next();
		var historyList = $(this).parent().parent().next().next();
		if($(window).width()>768){
			if(quickList.css("opacity")==0){
	            $(this).addClass("open_quick").parent().parent().css({"padding-right":"298px"});
	            quickList.css({"opacity":1,"right":0});
				historyList.css({"opacity":0,"right":"-298px"});
	        }else{
	            $(this).removeClass("open_quick").parent().parent().css({"padding-right":0});
	            quickList.css({"opacity":0,"right":"-298px"});
	        }
	        historyList.removeClass("open_quick").css({"opacity":0,"right":"-298px"});
			$(this).next().removeClass("open_quick");			
		}else{
			quickList.addClass("histor_mobile");
			quickList.css({"opacity":1,"right":0});
		}        
    });
    //移动端返回聊天
    $("#backTalk").on("click",function(){
    	$(this).parent().removeClass("histor_mobile");
    	$(this).parent().css({"opacity":0,"right":"100%"});
    	
    });

	//展开历史记录
	chartBox.on("click",".to_history",function(){		
        var quickList = $(this).parent().parent().next();
		var historyList = $(this).parent().parent().next().next();
        if(historyList.css("opacity")==0){
            $(this).addClass("open_quick").parent().parent().css({"padding-right":"298px"});
            historyList.css({"opacity":1,"right":0});
			quickList.css({"opacity":0,"right":"-298px"});
        }else{
            $(this).removeClass("open_quick").parent().parent().css({"padding-right":0});
            historyList.css({"opacity":0,"right":"-298px"});
        }
        quickList.removeClass("open_quick").css({"opacity":0,"right":"-298px"});
		$(this).prev().removeClass("open_quick");
    });
	
	//历史记录查询时间
	$("#srechTime").on("focus","input",function(){
		laydate({istime:true,format: 'YYYY-MM-DD'});
	});

    //写入快速内容
//    $(".quick_list").on("click","li",function(){
//        var textareas = $(this).parent().parent().prev().find("textarea"),
//            reTxt = $(this).text();
//        textareas.val(reTxt);
//    });

    //发送内容
    chartBox.on("click",".send_txt",function(){
        var chartTxt = $(this).prev().find("textarea").val();
        	if(chartTxt == "") {layer.msg("请输入聊天内容,400个字符以内");$(this).prev().find("textarea").text("");return;}
        	if(chartTxt.length > 400){
        		layer.msg("超过了400个字符，当前"+chartTxt.length+"个字符！");return;
        	}
        var sendTxt = chartTxt;
        	chartTxt = chartTxt.indexOf("em_")>0 ? replace_em(chartTxt):chartTxt;
        	
        var	chartName = $("#user_name").text(),
            chartList = "<dl class='cf other'>" 
            		+"<dt><img src='"+SERVER_PATH+"/resources/hero/img/user_img1.jpg'></dt>" 
            		+"<dd><span class='send_time'>"+new Date().format('hh:mm:ss')
            		+"</span><div class='send_con'>"+chartTxt+"</div></dd></dl>";
        if($.trim(chartTxt) != ""){
            $(this).parent().parent().find(".talk_list").append(chartList);
            setTimeout(function(){
            	$("#talkList").scrollTop($("#talkList")[0].scrollHeight);
            },100);
            $(this).prev().find("textarea").val("");
            //判断是否是发送离线消息
            var id = ($("#server_name").attr("data-id") != undefined && $("#server_name").attr("data-id") != "") ?$("#server_name").attr("data-id"):-1;
            var pro_msg = userSendText(id,chartName,pr.uType,$.trim(sendTxt));
            socket.send(pro_msg);
        }
        $(".talk_input textarea").val("");
    })
    //点击图片放大
	picUpDown(".talk_list", "img.view");
    picUpDown("#chatHistory", "img.view");
    // 回车发送
	$("#visitorBox").keypress(function(e) {

		if (e.ctrlKey && e.which == 13 || e.which == 10 || e.which == 13) {

			$(".send_txt").click();

			e.preventDefault();// 屏蔽enter对系统作用。按后增加\r\n等换行

		}
	});
	$(document).keyup(function() { 
		var text=$("#inputext").val(); 
		var counter=text.length;
		if(counter > 400){
			layer.msg("超过了400个字符，当前"+counter+"个字符！");
			}
	});

    // 关闭聊天窗口
    chartBox.on("click",".close_chart",function(){
        closeBox($("#chartBox"));
    });

    //删除聊天成员
    chartBox.on("mousedown","a.close",function(){
        $(this).parent().parent().animate({"right":"-100px","opacity":.2},300,function(){
            $(this).remove();
        })
    });
    
    //表情
    $('.emotion').qqFace({
		id : 'facebox', //表情盒子的ID
		assign:'inputext', //给那个控件赋值
		path:ctx+'/resources/global/js/qq_face/face/'	//表情存放的路径
	});
});

function historyRecord(){
	var userName = $("#user_name").text();
	var sendTime = $(".history_list #sendTime").val();
	var sendTimeEnd = $(".history_list #sendTimeEnd").val();
	if(sendTime == ""||sendTimeEnd ==""){
		sendTime = new Date().format("yyyy-MM-dd");
		sendTimeEnd = new Date().format("yyyy-MM-dd");
	}
	if(sendTime > sendTimeEnd){
		layer.alert("开始时间必须小于等于结束时间！");return;
	}
	$.ajax({
		url:ctx+"/c/chat/record",
		data:{"username":userName,"companyId":company_id,"page":page-1,"size":pageSize,"sendTime":sendTime, "sendTimeEnd":sendTimeEnd},
		type : "POST",
		success:function(datas){
			var data = datas.content;
			var html = "";
			if(data == ""){
				html+="<dl>当天暂无历史聊天记录.</dl>";
			}else{
				for(var i=0;i<data.length;i++){
					if(data[i].msgType){//图片
						 if(userName == data[i].fromUser){
							 html+="<dl class='visitor'>"+
							 "         <dt>我&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd><img style=\"width: 50%;\" class=\"view\" src='"+data[i].msg+"'/></dd> "+
					            "    </dl> ";
						 }else{
							 html+="<dl class='customer'>"+
							 "         <dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd><img style=\"width: 50%;\" class=\"view\" src='"+data[i].msg+"'/></dd> "+
					            "    </dl> ";
						 }
					} else {
						 if(userName == data[i].fromUser){
							 html += "<dl class='visitor'>"+
							 "         <dt>我&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd>"+(data[i].msg.indexOf("em_") > 0 ? replace_em(data[i].msg) : data[i].msg)+"</dd> "+
					            "    </dl> ";
						 }else{
							 html += "<dl class='customer'>"+
							 "         <dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd>"+(data[i].msg.indexOf("em_") > 0 ? replace_em(data[i].msg) : data[i].msg)+"</dd> "+
					            "    </dl> ";
						 }
					}
			          
				}
			}
			$("#chatHistory").html(html);
			showPage(datas.totalElements, page, "#visitorBox");
		}
	});
	
}
pageWhitch("#visitorBox",historyRecord);

//离线留言弹窗
$("#tipsDiv").on("click",".leaveBtn",function(){
	$("#tipsDiv .tip-content").addClass("hide_tips");
	$("#tipsDiv").removeClass("Over-2");
	$("#server_name").text("问答机器人");
	$("#server_name").attr("data-id","-2");//用id=-2表示给相关问题分组留言
	var PRO_ = {
			body:"先跟机器人聊聊天吧！^_^"
	};
	showSysMsg(PRO_);
})


function userSendText(tou, uname, uType, msg) {
	PRO.body = msg;
	PRO.username = uname;
	PRO.nickname = uname;
	PRO.to_uid = tou;
	PRO.uType = uType;
	PRO.companyID=company_id;
	PRO.msgType = msgType._TEXT_MSG;
	PRO.layerId = $("#layId").val();
	return JSON.stringify(PRO);
}
//图片消息
function userSendImg(tou, uname, uType, msg) {
	PRO.body = msg;
	PRO.username = uname;
	PRO.nickname = uname;
	PRO.to_uid = tou;
	PRO.uType = uType;
	PRO.companyID=company_id;
	PRO.msgType = msgType._IMG_MSG;
	PRO.layerId = $("#layId").val();
	return JSON.stringify(PRO);
}
