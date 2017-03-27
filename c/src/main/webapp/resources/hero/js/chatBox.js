$(function() {
	// 默认展开访客列表
	var guest = $(".chart_group li").first().next();
	guest.find("i").addClass("keep_it");
	guest.find(".tm_list").css({
		"opacity" : 1
	}).slideDown(0);
	
	// 搜索
	$(".search").on("click", function() {
		var sName = $("#sName").val();
		var $list = $("#chartGroup").find("dl");
		$list.show();
		if (sName != "") {
			$list.hide();
			$list.each(function() {
				var dataName = $(this).attr("data-name");
				if (dataName.indexOf(sName) >= 0) {
					$(this).show();
				}
			})
		}
	});

	// 展开聊天窗口左侧分组
	$(".chart_group").on("click", "li", function() {
		var tmList = $(this).find(".tm_list");
		if (tmList.css("opacity") == 0) {
			tmList.css({
				"opacity" : 1
			}).slideDown(0);
			$(this).find("i").addClass("keep_it");
		} else {
			$(this).find("i").removeClass("keep_it");
			tmList.css({
				"opacity" : 0
			}).slideUp(0);
		}
	});
	var tmListDl = $(".chart_group li").find("dl");

	// 阻止冒泡
	tmListDl.on("click", function(event) {
		event.stopPropagation();
	});

	// 切换聊天用户
	$("#chartGroup").on("click","dl",function(event) {
				event.stopPropagation();
				$(this).addClass("chat_cur");
				$(this).siblings().removeClass("chat_cur");
				$(this).parent().parent().siblings().find("dl").removeClass(
						"chat_cur");
				var curChartName = $(this).attr("data-name");
				$("#user_name").text(curChartName);
				$("#user_name").show();
				if($(this).attr("data-id") == undefined){
					showOneUnRead(curChartName, 0, "#guest_");// 未读消息清空
				} else {
					showOneUnRead(curChartName, 0, "#team_");// 未读消息清空
				}
				showUnRead();
				signReadMsgSend();
				//收回快速输入列表
				var quickList = $("#chartBox .quick_list");
				var historyList = $("#chartBox .to_history");
				quickList.css({
					"opacity" : 0,
					"right" : "-298px"
				});
				//historyList.css({"opacity":0,"right":"-298px"});
				$("#chatBox_"+curChartName).siblings().find(".quick_write").removeClass("open_quick").parent().parent().css({
					"padding-right" : 0
				});
				if($("#guest_"+curChartName).hasClass("chat_cur")||$("#team_"+curChartName).hasClass("chat_cur")){
					$("#chatBox_"+curChartName).find(".quick_write").removeClass("open_quick").parent().parent().css({
						"padding-right" : 0
					});
				}
			});

	// 展开快速输入列表
	var chartBox = $("#chartBox");
    chartBox.on("click",".quick_write",function(){		
        var quickList = $(this).parent().parent().parent().next();
		var historyList = $(this).parent().parent().parent().next().next();
        if(quickList.css("opacity")==0){
        	quickListCon();
            $(this).addClass("open_quick").parent().parent().css({"padding-right":"298px"});
            quickList.css({"opacity":1,"right":0});
			historyList.css({"opacity":0,"right":"-298px"});
        }else{
            $(this).removeClass("open_quick").parent().parent().css({"padding-right":0});
            quickList.css({"opacity":0,"right":"-298px"});
        }
        historyList.removeClass("open_quick").css({"opacity":0,"right":"-298px"});
		$(this).next().removeClass("open_quick");
    });

	//展开历史记录
	chartBox.on("click",".to_history",function(){
        var quickList = $(this).parent().parent().parent().next();
		var historyList = $(this).parent().parent().parent().next().next();
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

	// 写入快速内容
	$(".quick_list").on("click","li",function() {
				var textareas = $(this).parent().parent().prev().find(
						"textarea"), reTxt = $(this).text();
				textareas.val(reTxt);
			});

	// 发送内容
	// chartBox.on("click",".send_txt",function(){
	// var chartTxt = $(this).prev().find("textarea").val(),
	// chartName = $("#user_name").text(),
	// chartList = "<dl class='cf service'><dt><img
	// src='"+SERVER_PATH+"/resources/hero/img/service.jpg'></dt><dd><span
	// class='send_time'>"+"11-08 18:31"
	// +"</span><div class='send_con'>"+chartTxt+"</div></dd></dl>";
	// if($.trim(chartTxt) != ""){
	// $(this).parent().parent().find(".talk_list").append(chartList);
	// $(this).prev().find("textarea").val("");
	// }
	// sendMsg(chartTxt,chartName);
	// });

	// 回车发送
	$(document).keypress(function(e) {

		if (e.ctrlKey && e.which == 13 || e.which == 10 || e.which == 13) {

			$(".send_txt").click();

			e.preventDefault();// 屏蔽enter对系统作用。按后增加\r\n等换行

			$(".talk_input textarea").val("");
		}
	});

	// 关闭聊天窗口
	chartBox.on("click", ".close_chart", function() {
//		$(this).parent().parent().hide();
		$("#chartBox").hide(500);
		$("#chartGroup").find("dl").removeClass("chat_cur");
		$("#boxDiv").removeClass("box_div");
	});

	// 删除聊天成员
	chartBox.on("mousedown",".cf a.close",function() {
			$(this).parent().parent().animate({
								"right" : "-100px",
								"opacity" : .2
							},
							300,
							function() {
								var username = $(this).attr(
										"data-name");
								$("#sessionList #" + username).remove();
								socket.send(sendClosePro(
												username, 999,
												uType.SERVICE,
												"close"));
								$("#chatBox_" + username).hide();
								$("#user_name").hide();
								$(this).remove();
								$("#chartBox #groupList dl")
										.first().click();
								showOnlineNum();
								if ($("#chartBox #teamsNum")
										.children().length == 0
										&& $("#chartBox #groupList")
												.children().length == 0)
								$("#chartBox").hide();
								$("#boxDiv").removeClass("box_div");
							})
		});
});

// 新消息提示
var g_blinkid = 0;
var g_blinkswitch = 0;
var g_blinktitle = document.title;

function blinkNewMsg() {
	document.title = g_blinkswitch % 2 == 0 ? "【　　　】 - " + g_blinktitle
			: "【新消息】 - " + g_blinktitle;
	g_blinkswitch++;
}
function stopBlinkNewMsg() {
	if (g_blinkid) {
		clearInterval(g_blinkid);
		g_blinkid = 0;
		document.title = g_blinktitle;
	}
}
function send(thisObj, username) {
	if ($("#teamsNum").find(".chat_cur").length > 0) {
		username = curUserAccount;
	}
	var chatMsg = $(thisObj).parent().find("textarea.write").val();
	var sendChatMsg = chatMsg;
	chatMsg = chatMsg.indexOf("em_") > 0 ? replace_em(chatMsg) : chatMsg;
	if ($.trim(chatMsg) != "") {
		var sendTime = new Date().format("hh:mm:ss");
		var PRO_ = {
			chatMsg : chatMsg,
			sendTime : sendTime
		}
		var chatHtml = template("chatBox_right_msg_serv_temp", PRO_);
		var $talk = $(thisObj).parent().parent().find(".talk_list");
		$talk.append(chatHtml);
		scrollTop($talk);
		$(thisObj).parent().find("textarea.write").val("");
		var id = parseInt($("#teamsNum").find(".chat_cur").attr("data-id"));
		socket.send(serTextMsg(username, id > 0 ? id : -1, uType.SERVICE,
				sendChatMsg));
	}
}

function showUserMsg(PRO_) {
	var sendTime = new Date(PRO_.sendTime).format("hh:mm:ss");
	var chatMsg = PRO_.body;
	chatMsg = chatMsg.indexOf("em_") > 0 ? replace_em(chatMsg) : chatMsg;
	var head = PRO_.uType != "SERVICE" ? ctx+"/resources/hero/img/user_img1.jpg":$('#team_'+PRO_.username + " img").attr("src");
	var o = {
		chatMsg : chatMsg,
		sendTime : sendTime,
		head:head
	}
	var chatHtml = template("chatBox_right_msg_user_temp", o);
	var $talk = $("#chatBox_" + PRO_.username + " .talk_list");
	$talk.append(chatHtml);
	scrollTop($talk);
}
//移交
$("#sessionList").on("click", ".un_talk", function(){
	var userName = $(this).parent().attr("data-name");
	var uhost = $(this).parent().attr("data-uhost");
	$("#changeNable #curVisitName").text(userName);
	openBox($("#changeNable"),$(".layer_bg"));
	$.ajax({
		url : ctx+"/oc/layer/list",
		type : "post",
		data : {
			"page" : 0,
			"size" : 100
		},
		success : function(data) {
			var datas = data.content;
			var str = "";
			if(datas.length != 0){
				for(var i=0;i<datas.length;i++){
					str+= "<div class=\"set_dl\"><h5><small></small><a  class=\"change-left\" href=\"javascript:void(0);\" onclick=\"choseSerByLayer("+datas[i].id+",'"+userName+"','"+uhost+"')\">"+datas[i].layerName+"</a></h5><div class=\"set_ddd\" id=\"question"+datas[i].id+"\"></div></div>";
				}
			}
			$("#changeNable #layerInfo").html(str);
			
		}
	});
})
function choseSerByLayer(id,userName,uhost){
	$.ajax({
		url:ctx+"/oc/session/usable/serv/"+id,
		type:"GET",
		success:function(data){
			var str ="<ul>";
			if(data.length != 0){
				for(var i=0;i<data.length;i++){
					str += "<li><span><a href=\"javascript:void(0);\" onclick=\"changeServTo("+data[i].id+",'"+userName+"','"+uhost+"')\">"+data[i].nickName+"</a></span></li>";
				}
			} else {
				str+="<li><a><small>没有可移交的客服</small></a></li>";
			}
			str += "</ul>";
			$("#changeNable #question"+id).html(str);
		}
	})
	
}
function changeServTo(servId,userName,uhost){
	var uid = PROS_.uid;
	var to_uid = servId;
//	console.log(userName,uid,to_uid,uhost);
	layer.confirm("你确定要移交么？",function(){
		socket.send(serChange(userName, to_uid, uhost));
		$("#guest_" + userName).remove();$("#sessionList #" + userName).remove();
		layer.msg('移交成功', {icon: 1});
		closeBox($("#changeNable"),$(".layer_bg"));
	})
}
// 游客离线消息提示
function userOffLine(PRO_) {
	$("#sessionList #" + PRO_.username + " .online").removeClass("online");
	$("#sessionList #" + PRO_.username + " .leaveTime").text(
			new Date().format("MM-dd hh:mm:ss"));
	$("#sessionList #" + PRO_.username + " .last_talk").text(PRO_.body);
	$("#guest_" + PRO_.username + " dt img").addClass("gray");
	$("#guest_" + PRO_.username + " #lastMsg").text(PRO_.body);
	sysMsg(PRO_);
	showOnlineNum();
}
//唯一在线
function onlyOnlineMsg(PRO_){
	layer.alert(PRO_.body);
	userLogout();
}
function openOnline() {
	//layer.alert("正在连接服务器...");
	//TODO 组装客服上线协议
	socket.send(protocol);
	//layer.alert("连接成功!");
}

function closeOffline(evt) {
	protocol.msgType="_CUSTOMER_OFFLINE";
	socket.send(protocol);
	//console.log(evt.code + " " + evt.reson)
}

//{"uid":7062581249,"to_uid":999,"username":"","ssid":null,"uType":"USER","msgType":"_TEXT_MSG","uhost":"127.0.0.1:58817","shost":"127.0.0.1:8080","body":"567567"}
// 游客上线
function userOnline(PRO_) {
	sessionList(PRO_);

	chatBoxLeftTemp(PRO_);

	chatBoxRightTemp(PRO_);

	$("#guest_" + PRO_.username).click(function() {
		var $chatBoxObj = $("#chatBox_" + PRO_.username);
		$("#chartBox .chatBoxList .talk_cn").hide();

		if ($chatBoxObj.length > 0) {
			$chatBoxObj.show();
		} else {
			chatBoxRightTemp(PRO_);
		}
		var $talk = $("#chatBox_" + PRO_.username + " .talk_list");
		scrollTop($talk);
		showUnRead();
	});
	showOnlineNum();

}

// 客服上线
function customerOnline(PRO_) {
	//右侧客服成员列表
	teamList();
	/* $(".team_list").prepend($("#"+PRO_.username));
    $("#"+PRO_.username).removeClass("gray").find("dd span:first").text("[在线]"); */
    
    //左侧(团队)成员移到最上面
	if($("#teamsNum").find("dl[data-id="+PRO_.uid+"]").length>0){
		if($("#team_"+PRO_.username).hasClass("gray")){
			$("#teamsNum").prepend($("#team_"+PRO_.username));
	        $("#team_"+PRO_.username).removeClass("gray").find("dd span:first").text("[在线]");
	        $("#user_name").removeClass("gray");
	        showOnlineNum();
	        return;
		}
		return;
	}
	
    //获取右侧客服成员的头像设到左侧
	PRO_.body=$(".team_list #"+PRO_.username + " img").attr("src");
    chatBoxLeftTemp_C(PRO_);
    $("#team_"+PRO_.username +" img").attr("src",(ctx+PRO_.body).replace("\/jt-c",""));
    
    chatBoxRightTemp(PRO_);

	$("#team_" + PRO_.username).click(function() {
		var $chatBoxObj = $("#chatBox_" + PRO_.username);
		$("#chartBox .chatBoxList .talk_cn").hide();

		if ($chatBoxObj.length > 0) {
			$chatBoxObj.show();
		} else {
			chatBoxRightTemp(PRO_);
		}
		var $talk = $("#chatBox_" + PRO_.username + " .talk_list");
		scrollTop($talk);
		showUnRead();
	});
	//左侧(团队)成员移到最上面
    $("#teamsNum").prepend($("#team_"+PRO_.username));
    $("#team_"+PRO_.username).removeClass("gray").find("dd span:first").text("[在线]");

    showOnlineNum();
}

//客服下线
function customerOffline(PRO_){
     var name=PRO_.username;
     //右侧客服员工移到下面,变灰
     $(".team_list").append($("#"+name));
     $("#"+name).addClass("gray").find("dd span:first").text("[离线]");
		
     //左侧团队成员移到下面,变灰
     $("#teamsNum").append($("#team_"+name));
     $("#team_"+name).addClass("gray").find("dd span:first").text("[离线]");
     $("#user_name").addClass("gray");

     showOnlineNum();
 }
//点击图片放大
picUpDown("#chartBox", "img.EnlargePhoto");
// 系统提示消息
function sysMsg(PRO_) {
	var sendTime = new Date(PRO_.sendTime).format("hh:mm:ss");
	var o = {
		chatMsg : PRO_.body,
		sendTime : sendTime
	}
	var content = template("chatBox_right_msg_sys_temp", o);
	var $talk = $("#chatBox_" + PRO_.username + " .talk_list");
	$talk.append(content);
	scrollTop($talk);
}
function showTextMsg(PRO_) {
	playAudio();
	showUserMsg(PRO_);// 展示发给对应用户的消息
	var type = PRO_.uType != "SERVICE" ? "#guest_" : "#team_";
	$(type + PRO_.username + " #lastMsg").text(PRO_.body);
	$("#sessionList").prepend($("#sessionList #" + PRO_.username));
	$("#sessionList #" + PRO_.username + " .last_talk").text(PRO_.body);
	$("#sessionList #" + PRO_.username + " .last_talk").append("<a style=\"color:#f86f59;\">[新消息]</a>");
	showOneUnRead(PRO_.username, 1, type);// 未读消息显示
	showUnRead();
	signReadMsgSend();
}
// 显示图片信息
function showImgMsg(PRO_) {
	var img = "<img style='width: 100%;' class='EnlargePhoto' src='" + PRO_.body + "'>";
	var sendTime = new Date(PRO_.sendTime).format("hh:mm:ss");
	var head = PRO_.uType != "SERVICE" ? ctx+"/resources/hero/img/user_img1.jpg":$('#team_'+PRO_.username + " img").attr("src");
	var o = {
		chatMsg : img,
		sendTime : sendTime,
		head:head
	}
	var chatHtml = template("chatBox_right_msg_user_temp", o);
	var $talk = $("#chatBox_" + PRO_.username + " .talk_list");
	$talk.append(chatHtml);
	scrollTop($talk);
	var type = PRO_.uType != "SERVICE" ? "#guest_" : "#team_";
	$(type + PRO_.username + " #lastMsg").text("[图片消息]");
	$("#sessionList").prepend($("#sessionList #" + PRO_.username));
	$("#sessionList #" + PRO_.username + " .last_talk").text("[图片消息]");
	$("#sessionList #" + PRO_.username + " .last_talk").append("<a style=\"color:#f86f59;\">[新消息]</a>");
	showOneUnRead(PRO_.username, 1, type);// 未读消息显示
	showUnRead();
	playAudio();
	signReadMsgSend();
}
// 显示图片信息
function showSerImgMsg(url, username) {
	var img = "<img style='width: 100%;' class='EnlargePhoto' src='" + url + "'>";
	var sendTime = new Date().format("hh:mm:ss");
	var o = {
		chatMsg : img,
		sendTime : sendTime
	}
	var chatHtml = template("chatBox_right_msg_serv_temp", o);
	var $talk = $("#chatBox_" + username + " .talk_list");
	$talk.append(chatHtml);
	scrollTop($talk);
	var id = parseInt($("#teamsNum").find(".chat_cur").attr("data-id"));
	if ($("#teamsNum").find(".chat_cur").length > 0) {
		username = curUserAccount;
	}
	socket.send(serImgMsg(username, id > 0 ? id : -1, uType.SERVICE, url));
	$("#file_" + username).val("");
}
/**
 * 发送消息已读
 * @returns
 */
function signReadMsgSend(){
	var curChatName = $("#chartGroup dl.chat_cur").attr("data-name");
	if(curChatName != undefined){
		var id = parseInt($("#teamsNum").find(".chat_cur").attr("data-id"));
		socket.send(signReadMsg(curChatName, id > 0 ? id : -1, uType.SERVICE,
				"read"));
	}
}
// 滚动条到最后
function scrollTop($id) {
	if ($id != undefined && $id.length > 0) {
		setTimeout(function() {
			$id.scrollTop($id[0].scrollHeight);
		}, 100);
	}
}

function covert(protocol){
	var PRO_ = JSON.parse(protocol);
//	alert(PRO_.uid);
	return PRO_;
}
/**
 * 发送移交协议
 * 
 * @returns
 */
function serChange(to_uname, to_uid, uhost) {
	PRO.to_uid = to_uid;
	PRO.username = to_uname;
	PRO.uid = PROS_.uid;
	PRO.userType = PROS_.userType;
	PRO.uhost = uhost;
	PRO.msgType = msgType._CHANGE_SERV;
	PRO.companyID = PROS_.companyID;
	return JSON.stringify(PRO);
}
/**
 * 发送文本消息
 * 
 * @param tou
 * @param uid
 * @param uType
 * @param msg
 * @returns
 */
function serTextMsg(to_uname, to_uid, uType, msg) {
	PRO.body = msg;
	PRO.to_uid = to_uid;
	PRO.username = to_uname;
	PRO.uType = uType;
	PRO.msgType = msgType._TEXT_MSG;
	PRO.uid = PROS_.uid;
	PRO.userType = PROS_.userType;
	PRO.companyID = PROS_.companyID;
	return JSON.stringify(PRO);
}
function serImgMsg(to_uname, to_uid, uType, msg) {
	PRO.body = msg;
	PRO.to_uid = to_uid;
	PRO.username = to_uname;
	PRO.uType = uType;
	PRO.msgType = msgType._IMG_MSG;
	PRO.uid = PROS_.uid;
	PRO.userType = PROS_.userType;
	PRO.companyID = PROS_.companyID;
	return JSON.stringify(PRO);
}
/**
 * 标记消息已读
 * @param to_uname
 * @param to_uid
 * @param uType
 * @param msg
 * @returns
 */
function signReadMsg(to_uname, to_uid, uType, msg) {
	PRO.body = msg;
	PRO.to_uid = to_uid;
	PRO.username = to_uname;
	PRO.uType = uType;
	PRO.msgType = msgType._SIGN_READ;
	PRO.uid = PROS_.uid;
	PRO.userType = PROS_.userType;
	PRO.companyID = PROS_.companyID;
	return JSON.stringify(PRO);
}

// 关闭聊天左侧列表聊天成员
function sendClosePro(to_uname, to_uid, uType, msg) {
	PRO.body = msg;
	PRO.to_uid = to_uid;
	PRO.username = to_uname;
	PRO.uType = uType;
	PRO.msgType = msgType._USER_CLOSED;
	PRO.uid = PROS_.uid;
	PRO.userType = PROS_.userType;
	PRO.companyID = PROS_.companyID;
	return JSON.stringify(PRO);
}
// 聊天窗口左边在线人数统计
function showOnlineNum() {
	var total = $("#chartBox #groupList").children().length;
	var offline = $("#chartBox #groupList").children().find("img[class=gray]").length;
	var online = total - offline;
	$("#guest #guestNum").text(online + "/" + total);
	var teamTotal = $("#chartBox #teamsNum").children().length;
	var teamOff = $("#chartBox #teamsNum").find(".gray").length;
	var teamOn = teamTotal - teamOff;
	$(".tm_name #teamNum").text(teamOn + "/" + teamTotal);
}
// 显示总未读消息数
function showUnRead() {
	var $userNews = $("#userNews i");
	$userNews.text("0");
	$userNews.hide();
	stopBlinkNewMsg();
	var total = 0;
	$("#chartBox #groupList").children().find("span.red").each(function() {
		total += parseInt($(this).text());
	});
	$("#chartBox #teamsNum").children().find("span.red").each(function() {
		total += parseInt($(this).text());
	});
	$userNews.text(total);
	if ($userNews.text() != undefined && parseInt($userNews.text()) > 0) {
		if(!g_blinkid)
			g_blinkid = setInterval(blinkNewMsg, 1000);
		$userNews.show();
	}
}
// 左侧聊天列表提示未读消息数
function showOneUnRead($id, num, uType) {
	var spanRed = $(uType + $id + " span.red");
	if (spanRed.length == 0) {
		var t = "<span class=\"red\">" + 1 + "</span>";
		$(uType + $id + " #lastMsg").after(t);
	} else {
		var i = parseInt(spanRed.text());
		i++;
		spanRed.text(i);
	}
	var n = parseInt(num);
	if (n == 0
			|| ($("#chatBox_" + $id).css("display") != "none" && $("#chartBox")
					.css("display") != "none")) {
		$(uType + $id + " span.red").remove();
		$("#sessionList #" + $id + " .last_talk a").remove();//移除掉会话列表【新消息】提示
	} else {
		if($(uType + $id).attr("data-id") == undefined){
			$("#chartBox #groupList").prepend($(uType + $id));
		}else {
			$("#chartBox #teamsNum").prepend($(uType + $id));
		}
	}
}
function quickListCon() {
	$.ajax({
		url : ctx + "/oc/prompt/quick/list",
		type : "GET",
		success : function(data) {
			$("#quickList").html("");
			var str = "";
			for (var i = 0; i < data.length; i++) {
				str += "<li>" + data[i].content + "</li>";
			}
			$("#quickList").append(str);
		}
	})
}
//会话列表
function sessionList(PRO_) {
//	if (PRO_.state == "RECONNECT") {//删除之前的上线记录
//		$("#sessionList #" + PRO_.username).remove();
//		$("#guest_" + PRO_.username).remove();
//		sysMsg(PRO_);
//	}
	$("#sessionList #" + PRO_.username).remove();
	$("#guest_" + PRO_.username).remove();
	sysMsg(PRO_);
    PRO_.uid = $("#sessionList tr").length + 1;
	var content = template("sessionListTemp", PRO_);
	$("#sessionList").prepend(content);
	$("#sessionList #" + PRO_.username + " .inTime").text(
			new Date(PRO_.sendTime).format("MM-dd hh:mm:ss"));
}
//聊天窗口右侧数据初始化
function chatBoxRightTemp(PRO_) {
	var content = template("chatBox_right_temp", PRO_);
	$("#chartBox .chatBoxList").append(content);
	//表情
	$('#chatBox_' + PRO_.username + ' .emotion').qqFace({
		id : 'facebox', //表情盒子的ID
		assign : 'chatBox_' + PRO_.username + ' #inputext', //给那个控件赋值
		path : ctx+'/resources/global/js/qq_face/face/' //表情存放的路径
	});
}

//聊天窗左侧列表
function chatBoxLeftTemp(PRO_) {
	var content2 = template("chatBox_left_temp", PRO_);
	$("#chartBox #groupList").prepend(content2);
}

//聊天窗左侧列表-客服
function chatBoxLeftTemp_C(PRO_) {
	var content2 = template("chatBox_left_team_temp", PRO_);
	$("#chartBox #teamsNum").prepend(content2);
}
