$(function(){
	teamList();
	//历史记录查询时间
	$("#srechTime").on("focus","input",function(){
		laydate({istime:true,format: 'YYYY-MM-DD'});
	});
	$.fn.promptBox=function(msg){
		$(".promptBox").remove();
		html = "<div class='promptBox'>"+msg+"</div>";
		$("body").append(html);
		$(".promptBox").fadeIn("slow");
		setTimeout(function(){$(".promptBox").fadeOut("slow");},1500)
		setTimeout(function(){$(".promptBox").remove();},2000)
	}
	 //表情
    $('.emotion').qqFace({
		id : 'facebox', //表情盒子的ID
		assign:'input-edit', //给那个控件赋值
		path:ctx+'/resources/global/js/qq_face/face/'	//表情存放的路径
	});
	$(".js-teamTitle").on("click",function(){
		var _team=$(this).next(".user-list");
		if(_team.is(":hidden")){
			$(this).find("i").addClass("keep-it");
			_team.show();
		}else {
			$(this).find("i").removeClass("keep-it");
			_team.hide();
		}
	});
	$(".js-user").delegate("li","click",function(){
		stopBlinkNewMsg();
		$(".col-model").hide();
		$(".js-user li").removeClass("active");
		$(this).addClass("active");
		var $id = $(this).attr("data-id");
		var $account = $(this).attr("id");
		var $name = $(this).attr("data-name");
		$("#user_name").html($name);
		$("#user_name").attr("data-id",$id > 0 ? $id : -1);
		$("#user_name").attr("data-account",$account);
		$("#user_name").attr("data-name",$name);
		
		$(".msg_list>div").hide();
		$("#msgBox_"+$account).show();
		//切换关闭历史记录框
		$(".divisionShow").removeClass("col-send");
		$(".division_box").hide();
		
		//切换输入框清空
		$("#input-edit").val("");
		
		$("#sendMsgSession #"+$account).find("span").text("").hide();
		$("#sendMsgSession #"+$account+" #helpDiv").hide();
		signReadMsgSend($account, $id);
	});
	 //页码点击效果
    $(".page_num").on("click","a",function(){
        $(this).addClass("cur").siblings().removeClass("cur");
    });
	/*历史记录*/
	$(".js-sendRight").on("click",function(){
		$(".js-quickSend").hide();
		if($(".js-divisionHis").is(":hidden")){
			historySend();
			$(".divisionShow").addClass("col-send");
			$(".js-divisionHis").show();
		}else {
			$(".divisionShow").removeClass("col-send");
			$(".js-divisionHis").hide();
		}
	});
	/*快捷会话*/
	$(".js-quickRight").on("click",function(){
		$(".js-divisionHis").hide();
		if($(".js-quickSend").is(":hidden")){
			$.ajax({
				url : ctx + "/oc/prompt/quick/list",
				type : "GET",
				success : function(data) {
					$("#quickList").html("");
					var str = "";
					for (var i = 0; i < data.length; i++) {
						str += "<li class='talk_meMsg'>" + data[i].content + "</li>";
					}
					$("#quickList").append(str);
				}
			})
			$(".divisionShow").addClass("col-send");
			$(".js-quickSend").show();
		}else {
			$(".divisionShow").removeClass("col-send");
			$(".js-quickSend").hide();
		}
	});
	$("#quickList").on("click","li",function(){
		$("#input-edit").val($(this).text());
	});
	//移交
	$(".js-user").delegate(".js-transfer","click",function(){
		var userName = $(this).closest("div").attr("data-name");
		var uhost = $(this).closest("div").attr("data-uhost");
		layer.open({
			  title:"转移客服列表",
			  type: 1,
			  area: '800px', //宽高
			  content: $("#model")
			});
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
						str+= "<div class='model_contentBox'><h3 class='js-service' data-id='"+datas[i].id+"'>"+datas[i].layerName+"</h3><ul class='js-content' data-userName='"+userName+"' data-uhost='"+uhost+"'></ul></div>";
					}
				}
				$("#layerInfo").html(str);
			}
		});
	});
	$(document).on("click",".js-service",function(){
		var str="";
		var $cont = $(this).next("ul"); 
		var id = $(this).attr("data-id");
		$.ajax({
			url:ctx+"/oc/session/usable/serv/"+id,
			type:"GET",
			success:function(data){
				if(data.length != 0){
					for(var i=0;i<data.length;i++){
						str += "<li data-toUid='"+data[i].id+"'>"+data[i].nickName+"</li>";
					}
				} else {
					str+="<li>没有可移交的客服</li>";
				}
				$cont.html(str);
			}
		})
		if($cont.is(":hidden")){
			$cont.show();
		}else {
			$cont.hide();
		}
	});
	$(document).on("click",".js-content li",function(){
		var uid = PROS_.uid;
		var to_uid = $(this).attr("data-toUid");
		var userName = $(this).closest("ul").attr("data-userName");
		var uhost = $(this).closest("ul").attr("data-uhost");
		var account = $("#curAcc").val();//给客服发消息account取当前账户
		var nickname = $("#nickName").text();
		var sendChatMsg = userName+"移交给你";
		if(to_uid != undefined){
			layer.confirm("你确定要移交么？",function(index){
				socket.send(serChange(userName, to_uid, uhost));
				socket.send(serTextMsg(account, nickname, to_uid, uType.SERVICE,sendChatMsg));
				$("."+userName).remove();
				$("#msgBox_"+userName).remove();
				layer.msg('移交成功', {icon: 1});
				 parent.layer.closeAll();  
				 showOnlineNum();
			})
		}
	});
	//求助
	$(".js-user").delegate(".js-help","click",function(){
		var userName = $(this).closest("div").attr("data-name");
		var uhost = $(this).closest("div").attr("data-uhost");
		layer.open({
			title: userName + "求助列表",
			type: 1,
			area: '800px', //宽高
			content: $("#helpModel")
		});
		$.ajax({
			url : ctx+"/chat/charshare/select/share/id",
			type : "get",
			success : function(data) {
				if(data != undefined){
					var datas = data;
					var str = "<div class='model_contentBox'><ul class='js-help-content' data-userName='"+userName+"' data-uhost='"+uhost+"' style='display:block;'>";
					if(datas.length != 0){
						for(var i=0;i<datas.length;i++){
							str+= "<li data-toUid='"+datas[i].id+"'>"+datas[i].nickName+"</li>";
						}
					} else {
						str+= "<li>您没有设置共享或者对应的客服不在线！</li>";
					}
					str += "</ul></div>";
					$("#helpInfo").html(str);
				} else {
					layer.alert("您未登录！");
				}
			}
		});
	});
	$(document).on("click",".js-help-content li",function(){
		var uid = PROS_.uid;
		var to_uid = $(this).attr("data-toUid");
		var userName = $(this).closest("ul").attr("data-userName");
		var uhost = $(this).closest("ul").attr("data-uhost");
		var account = $("#curAcc").val();//给客服发消息account取当前账户
		var nickname = $("#nickName").text();
		var sendChatMsg = nickname+"向你寻求帮助！";
		if(to_uid != undefined){
			socket.send(askForHelp(userName, to_uid, uhost, sendChatMsg));
			layer.msg('请求已发出！', {icon: 1});
			parent.layer.closeAll();
		}
	});
	/*移除*/
	$(document).on("click",".js-remove",function(){
		var userName = $(this).closest("div").attr("data-name");
		layer.confirm("你确定要移除该游客么？",function(index){
			socket.send(sendClosePro(userName, 999,uType.SERVICE,"close"));
			$("."+userName).remove();
			$("#msgBox_"+userName).remove();
			layer.msg('移除成功', {icon: 1});
			showOnlineNum();
		})
		
	});
	$(document).on("click",".js-imgShow",function(){
		console.log($(this).attr('src'));
		var html="<img width='680px' src='"+$(this).attr('src')+"' />";
		$(".js-img").html(html);
		layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  area: '680px',
			  skin: 'layui-layer-nobg', //没有背景色
			  shadeClose: true,
			  content: $('#imgModel')
			});
	});
	$("#input-edit").on("click",function(){
		stopBlinkNewMsg();
	});
});
var PROS_ = JSON.parse(protocol);
function historySend (){
	var html="";
	var curUserAccount = $("#curAcc").val();
	var uid=$("#user_name").attr("data-id");
	var username=$("#user_name").attr("data-account");
	var sendTime = $("#sendTime").val();
	if(sendTime == ""){
		sendTime = new Date().format("yyyy-MM-dd");
	}
	var sendTimeEnd = $("#sendTimeEnd").val();
	if(sendTimeEnd == ""){
		sendTimeEnd = new Date().format("yyyy-MM-dd");
	}
	$.ajax({
		url:ctx+"/oc/jt-user/chatRecord",
		data:{"username":username,"userId":uid,"companyId":PROS_.companyID,"page":page-1,"size":pageSize,"sendTime":sendTime, "sendTimeEnd":sendTimeEnd},
		type : "POST",
		success:function(datas){
			var data = datas.content;
			if(data==""){
				$(this).promptBox("当天暂无历史聊天记录");
			}else {
				for(var i=0;i<data.length;i++){
					if(data[i].msgType){//图片
						 if(curUserAccount == data[i].fromUser){
							 html += "<dl class='customer'> "+
					           "         <dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd><img style=\"width: 100%;\" class=\"js-imgShow\" src='"+data[i].msg+"'/></dd> "+
					            "    </dl> ";
						 }else{
							 html += "<dl class='visitor'> "+
					           "         <dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd><img style=\"width: 100%;\" class=\"js-imgShow\" src='"+data[i].msg+"'/></dd> "+
					            "    </dl> ";
						 }
					} else {
						 if(curUserAccount == data[i].fromUser){
							 html += "<dl class='customer'> "+
					           "         <dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd>"+(data[i].msg.indexOf("em_") > 0 ? replace_em(data[i].msg) : data[i].msg)+"</dd> "+
					            "    </dl> ";
						 }else{
							 html += "<dl class='visitor'> " +
							 		"<dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd>"+(data[i].msg.indexOf("em_") > 0 ? replace_em(data[i].msg) : data[i].msg)+"</dd> "+
					            "    </dl> ";
						 }
					}
				}
				$(".division_his").html(html);
			}
			showPage(datas.totalElements, page, "#visitorBox");
		}
	});
}
pageWhitch("#visitorBox",historySend);
//声音提醒
function playAudio(){
	if ($("#noteEndAudio").length > 0) {
        $('#noteEndAudio').get(0).play();
    }
}
function openOnline() {
	socket.send(protocol);
}

function closeOffline() {
	
}
//游客上线
function userOnline(PRO_) {
	var _data = template("chatBox_left_temp", PRO_);
	$("#groupList").append(_data);
	showOnlineNum();
	chatBoxRightTemp(PRO_);//生成右边聊天框
}
//显示在线人数
function showOnlineNum() {
	var gustNum = $("#groupList li").length;
		gustNum =gustNum;
	var off_line = $("#groupList .gray").length;
	var on_line = gustNum - off_line;
	$("#group-num").text(gustNum);
	$("#group-onLine").text(on_line);
	var num = $("#team li").length;
	var off_line = $("#team .gray").length;
	var on_line = num - off_line;
	$("#team-num").text(num);
	$("#team-onLine").text(on_line);
}
//游客离线
function userOffLine(PRO_) {
	
	if($("#user_name").text()==PRO_.username) {
		$("#user_name").text("聊天内容");
		$("#user_name").removeAttr("data-id");
		$("#user_name").removeAttr("data-account");
		$("#user_name").removeAttr("data-name");
	}
	$(this).promptBox(PRO_.body);
	$("#groupList #" + PRO_.username).find("img").addClass("gray");
	$("#groupList").append($("#groupList #" + PRO_.username));
	showOnlineNum();
	$("#groupList #" + PRO_.username).remove();
	$("#msgBox_"+PRO_.username).remove();
}
//客服上线
function customerOnline(PRO_) {
	$("#team #" + PRO_.username).removeClass("gray");
	$("#team #" + PRO_.username).find(".list-state").text("[在线]");
	$("#team").prepend($("#team #" + PRO_.username));
	showOnlineNum();
}
//客服下线
function customerOffline(PRO_) {
	$("#team #" + PRO_.username).addClass("gray");
	$("#team #" + PRO_.username).find(".list-state").text("[离线]");
	$("#team").append($("#team #" + PRO_.username));
	showOnlineNum();
}
var dataMsg={};//接收消息
//显示文本消息
function showTextMsg(PRO_){
	playAudio();
	var sendTime = new Date(PRO_.sendTime).format("hh:mm:ss");
	var chatMsg = PRO_.body;
	var uname = PRO_.username;
	if($("#msgBox_"+uname).length == 0){//查找聊天内容框是否存在,不存在则创建
		chatBoxRightTemp(PRO_);
	}
	chatMsg = chatMsg.indexOf("em_") > 0 ? replace_em(chatMsg) : chatMsg;
	var uType = PRO_.uType;
	var PRO_ = {
			chatMsg : chatMsg,
			sendTime : sendTime,
			to_uid:PRO_.to_uid,
			uid:PRO_.uid,
			nickname:PRO_.nickname
		}
	var chatHtml = "";
	if(uType == "SERVICE" && PRO_.uid != $("#curId").val() && PRO_.to_uid < 1){//判断是给客服共享用户发送消息
		var otherServ = $("#team").find("li[data-id='"+PRO_.uid+"']").attr("data-name");  
		var PRO_ = {
				chatMsg : "【"+otherServ+"】："+chatMsg,
				sendTime : sendTime,
				to_uid:PRO_.to_uid,
				uid:PRO_.uid,
				nickname:PRO_.nickname
			}
		chatHtml = template("chatBox_send", PRO_);
	} else {
		chatHtml = template("chatBox_Rec", PRO_);
	}
	$("#msgBox_"+uname).append(chatHtml);
	if( uType == "SERVICE" ){//如果接收客服的消息
		var $nickname = PRO_.nickname;
		var $showname = PRO_.nickname;
		var $id = PRO_.uid;
		if(PRO_.uid != $("#curId").val() && PRO_.to_uid < 1){
			var otherServ = $("#team").find("li[data-id='"+PRO_.uid+"']").attr("data-name");
			$id = -1;
			if($("#chartGroup #"+uname).length == 0){
				$showname = $showname + "【"+otherServ+"】";
			}
		}
		showOneUnRead(uname,$nickname,$showname,$id);
	} else {
		var $showname = PRO_.nickname;
		if($("#chartGroup #"+uname).length == 0){
			var otherServ = $("#team").find("li[data-id='"+PRO_.uid+"']").attr("data-name");
			$showname = $showname + "【"+otherServ+"】";
		}
		showOneUnRead(uname,PRO_.nickname,$showname,PRO_.to_uid);
	}
	scrollTop($(".chat-body"));
}
//显示图片消息
function showImgMsg(PRO_){
	playAudio();
	var sendTime = new Date(PRO_.sendTime).format("hh:mm:ss");
	var img = "<img style='width: 100%;' class='js-imgShow' src='" + PRO_.body + "'>";
	var uname = PRO_.username;
	var uType = PRO_.uType;
	if($("#msgBox_"+uname).length == 0){//查找聊天内容框是否存在,不存在则创建
		chatBoxRightTemp(PRO_);
	}
	var PRO_ = {
			chatMsg : img,
			sendTime : sendTime,
			to_uid:PRO_.to_uid,
			uid:PRO_.uid,
			nickname:PRO_.nickname
		}
	var chatHtml = "";
	if(uType == "SERVICE" && PRO_.uid != $("#curId").val() && PRO_.to_uid < 1){//判断是给客服共享用户发送消息
		var otherServ = $("#team").find("li[data-id='"+PRO_.uid+"']").attr("data-name");  
		var PRO_ = {
				chatMsg : "【"+otherServ+"】："+chatMsg,
				sendTime : sendTime,
				to_uid:PRO_.to_uid,
				uid:PRO_.uid,
				nickname:PRO_.nickname
			}
		chatHtml = template("chatBox_send", PRO_);
	} else {
		chatHtml = template("chatBox_Rec", PRO_);
	}
	$("#msgBox_"+uname).append(chatHtml);
	if( uType == "SERVICE" ){//如果接收客服的消息
		var $nickname = PRO_.nickname;
		var $showname = PRO_.nickname;
		var $id = PRO_.uid;
		if(PRO_.uid != $("#curId").val() && PRO_.to_uid < 1){
			var otherServ = $("#team").find("li[data-id='"+PRO_.uid+"']").attr("data-name");
			$id = -1;
			if($("#chartGroup #"+uname).length == 0){
				$showname = $showname + "【"+otherServ+"】";
			}
		}
		showOneUnRead(uname,$nickname,$showname,$id);
	} else {
		var showname = PRO_.nickname;
		if($("#chartGroup #"+uname).length == 0){
			var otherServ = $("#team").find("li[data-id='"+PRO_.uid+"']").attr("data-name");
			$showname = $showname + "【"+otherServ+"】";
		}
		showOneUnRead(uname,PRO_.nickname,$showname,PRO_.to_uid);
	}
	scrollTop($(".chat-body"));
}
//接收寻求帮助信息
function customerHelp(PRO_) {
	layer.msg(PRO_.body);
	$("#sendMsgSession #"+PRO_.username+" #helpDiv").show();
	$("#sendMsgSession #"+PRO_.username+" span").hide();
}

//聊天窗口右侧数据初始化
function chatBoxRightTemp(PRO_) {
	var content = template("msgBox_list", PRO_);
	$(".msg_list").append(content);
	
}
function teamList() {
	$.ajax({
		url : ctx+"/oc/jt-user/ser/list",
		type : "get",
		async : false,
		success : function(data) {
			var str = {
				list : data
			}
			var html = template("teamList", str);
			$("#team").html(html);
			$("#team").append($(".user-list").find(".gray"));
			showOnlineNum();
			grayscale(document.getElementsByClassName("gray"));
			
			var sendHtml = template("msgBoxadmin_list",str);
			$(".msg_list").append(sendHtml);
		}
	});
}
/*发送消息*/
function sendMsg() {
	var id = $("#user_name").attr("data-id");
	var account = $("#user_name").attr("data-account");
	var nickname = $("#user_name").attr("data-name");
	var $name = account;
	if(id==""){
		$(this).promptBox("请先选择聊天好友");
		return false;
	}
	if(id > 0){
		account = $("#curAcc").val();//给客服发消息account取当前账户
		nickname = $("#nickName").text();
	}
	var sendChatMsg = $("#input-edit").val();
	if(sendChatMsg == ""){
		$(this).promptBox("内容不能为空,400个字符以内！");
		return false;
	}
	if(sendChatMsg.length > 400){
		$(this).promptBox("超过了400个字符，当前"+sendChatMsg.length+"个字符！");
		return false;
	}
	var sendTime = new Date().format("hh:mm:ss");
	sendChatMsg = sendChatMsg.indexOf("em_") > 0 ? replace_em(sendChatMsg) : sendChatMsg;
	var PRO_ = {
		chatMsg : sendChatMsg,
		sendTime : sendTime
	}
	var chatHtml = template("chatBox_send", PRO_);
	if ($("#msgBox_" + $name).length == 0) {
		$(this).promptBox("该游客已离线，无法发送消息");
		return;
	}
	$("#msgBox_"+$name).append(chatHtml);
	socket.send(serTextMsg(account, nickname, id > 0 ? id : -1, uType.SERVICE,sendChatMsg));
	$("#input-edit").val("");
	scrollTop($(".chat-body"));
}

$(document).keyup(function() { 
	var text=$("#input-edit").val(); 
	var counter=text.length;
	if(counter > 400){
		layer.msg("超过了400个字符，当前"+counter+"个字符！");
		}
});

//回车发送
$("#input-edit").keypress(function(e) {

	if (e.ctrlKey && e.which == 13 || e.which == 10 || e.which == 13) {

		sendMsg();

		e.preventDefault();// 屏蔽enter对系统作用。按后增加\r\n等换行
	}
});
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
function serTextMsg(to_uname, to_nickname, to_uid, uType, msg) {
	PRO.body = msg;
	PRO.to_uid = to_uid;
	PRO.username = to_uname;
	PRO.nickname = to_nickname;
	PRO.uType = uType;
	PRO.msgType = msgType._TEXT_MSG;
	PRO.uid = PROS_.uid;
	PRO.userType = PROS_.userType;
	PRO.companyID = PROS_.companyID;
	return JSON.stringify(PRO);
}
function serImgMsg(to_uname, to_nickname, to_uid, uType, msg) {
	PRO.body = msg;
	PRO.to_uid = to_uid;
	PRO.username = to_uname;
	PRO.nickname = to_nickname;
	PRO.uType = uType;
	PRO.msgType = msgType._IMG_MSG;
	PRO.uid = PROS_.uid;
	PRO.userType = PROS_.userType;
	PRO.companyID = PROS_.companyID;
	return JSON.stringify(PRO);
}
//新消息提示
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
//关闭聊天左侧列表聊天成员
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
/**
 * 向同组的其他客服发出请求帮助
 * @param userName
 * @param to_uid
 * @param uhost
 * @param msg
 * @returns
 */
function askForHelp(userName, to_uid, uhost, msg) {
	PRO.body = msg;
	PRO.to_uid = to_uid;
	PRO.username = userName;
	PRO.msgType = msgType._HELP;
	PRO.uid = PROS_.uid;
	PRO.userType = PROS_.userType;
	PRO.companyID = PROS_.companyID;
	return JSON.stringify(PRO);
}
/**
 * 发送消息已读
 * @returns
 */
function signReadMsgSend($account, $id){
	socket.send(signReadMsg($account, $id > 0 ? $id : -1, uType.SERVICE,
			"read"));
}
//file值发生变化
function changeFile() {
	var file_value = $("#file").val();
	if (file_value != null && file_value != undefined) {
		var index = file_value.lastIndexOf("\\");
		file_value = file_value.substring(index + 1, file_value.length);
		if (file_value.length > 20) {
			file_value = file_value.substring(0, 20) + "...";
		}
		ajaxFileUploads();
	}
}
/** 上传图片 */
function ajaxFileUploads() {
	var file_value = $("#file").val();
	if (file_value == '' || file_value == null
			|| file_value == undefined) {
		layer.alert("请先选择图片!");
		return;
	}
	var houzui = file_value.substring(file_value.lastIndexOf(".") + 1,
			file_value.length);
	if (houzui == '' || houzui == null || houzui == undefined) {
		layer.alert("请选择正确图片格式!");
	}
	houzui = houzui.toLowerCase()
	if (houzui == 'jpg' || houzui == 'gif' || houzui == 'png'
			|| houzui == 'bmp' || houzui == 'swf') {
		//图片等待处理

		//上传图片
		$.ajaxFileUpload({
			url : ctx+'/upload/img',
			secureuri : false,
			fileElementId : 'file',
			dataType : 'json',
			success : function(data, status) {
				if (data.result == "0") {
					var url = data.remoteurl;
					var	urlShow ="<img class='js-imgShow' width='100%' src='"+url+"' />"
					var id = $("#user_name").attr("data-id");
					var $name = $("#user_name").attr("data-account");
					var $nickName = $("#user_name").attr("data-name");
					var sendTime = new Date().format("hh:mm:ss");
					
					var PRO_ = {
						chatMsg : urlShow,
						sendTime : sendTime
					}
					var chatHtml = template("chatBox_send", PRO_);
					$("#msgBox_"+$name).append(chatHtml);
					socket.send(serImgMsg($name,$nickName,id > 0 ? id : -1, uType.SERVICE,url));
					scrollTop($(".chat-body"));
				} else if (data.result == "1") {
					layer.alert("请先选择图片！");
				} else {
					layer.alert("上传出现异常！");
				}
			},
			error : function(data, status, e) { //相当于java中catch语句块的用法
				layer.alert("发生异常,上传失败!");
				$("#10001").remove();
			}
		});
	} else {
		layer.alert("图片格式不正确!当前只支持jpg/gif/png/bmp/swf格式.");
	}
}

//左侧聊天会话列表提示未读消息数
function showOneUnRead($name,$nickname,$showname,$id) {
//		console.log($id);
		var $nameLength=$("#sendMsgSession").find("#"+$name).length;
		if($("#msgBox_"+$name).is(":hidden")){
			if($nameLength==0){
				var html ="<li id='"+$name+"' class='"+$name+"' data-id='"+$id+"' data-name='"+$nickname+"'>"+$showname;
				html += "<span class='list-num' style='display:block;'>1</span><div class='list-num' id='helpDiv' style='display:none; background-color:#06f'>!</div>";
				html += "</li>";
				$("#sendMsgSession").append(html);
			}else {
				var i = $("#"+$name).find("span").text()/1;
					i++;
				$("#"+$name).find("span").text(i).show();
			}
		}else {
			if($nameLength==0){
				var html ="<li id='"+$name+"' data-id='"+$id+"' class='"+$name+"' data-name='"+$nickname+"'>"+$showname;
				html += "<span class='list-num' style='display:none;'></span><div class='list-num'>!</div>";
				html += "</li>";
				$("#sendMsgSession").append(html);
			}
			signReadMsgSend($name, $id);
		}
		if(!g_blinkid)
			g_blinkid = setInterval(blinkNewMsg, 1000);
}
//滚动条到最后
function scrollTop($id) {
	if ($id != undefined && $id.length > 0) {
		setTimeout(function() {
			$id.scrollTop($id[0].scrollHeight);
		}, 100);
	}
}
