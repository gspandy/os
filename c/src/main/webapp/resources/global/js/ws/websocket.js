var socket;
var host = window.location.host;

if ('WebSocket' in window) {
	socket = new WebSocket("ws://" + host + "/jt-io/wsc");
} else if ('MozWebSocket' in window) {
	socket = new MozWebSocket("ws://" + host + "/jt-io/wsc");
} else {
	socket = new SockJS("http://" + host + "/jt-io/wsc/js");
}
// 当Browser和WebSocketServer连接成功后，会触发onopen消息
socket.onopen = function(evt) {
	openOnline();
};

// 当Browser接收到WebSocketServer发送过来的数据时，就会触发onmessage消息，参数evt中包含server传输过来的数据
socket.onmessage = function(evt) {
	analyzeMsg(evt);
};

// 当Browser接收到WebSocketServer端发送的关闭连接请求时，就会触发onclose消息
socket.onclose = function(evt) {
	closeOffline(evt);
};

// 如果连接失败，发送、接收数据失败或者处理数据出现错误，browser会触发onerror消息
socket.onerror = function(evt) {
	alert("与服务器的连接出现了问题.");
};

/**
 * 解析消息
 * 
 * @param evt
 */
function analyzeMsg(evt) {
	var PRO_ = JSON.parse(evt.data); // 序列化数据
//	console.log("ananlyze:" + evt.data);
	switch (PRO_.msgType) {
	case msgType._CUSTOMER_ONLINE:
		customerOnline(PRO_);
		break;
	case msgType._CUSTOMER_OFFLINE:
		customerOffline(PRO_);
		break;
	case msgType._USER_ONLINE:
		if (PRO_.uid == null)
			return;
		userOnline(PRO_);
		break;
	case msgType._USER_OFFLINE:
		if (PRO_.uid == null)
			return;
		userOffLine(PRO_);
		break;
	case msgType._TEXT_MSG:
		showTextMsg(PRO_);
		break;
	case msgType._IMG_MSG:
		showImgMsg(PRO_);
		break;
	case msgType._SYS_MSG:
		if(PRO_.state == STATES.BUSYNESS){
			reSelLayer(PRO_);break;
		}
		showSysMsg(PRO_);
		break;
	case msgType._LAYER_MSG:
		showLayerMsg(PRO_.body);
		break;
	case msgType._ONLY_ONLINE:
		onlyOnlineMsg(PRO_);
		break;
	case msgType._LEAVE_MSG:
		userOnline(PRO_);
		break;
	case msgType._HELP:
		customerHelp(PRO_);
		break;
	}
}

/**
 * 协议处理
 */
var uType = {
	USER : "USER", // 普通用户
	SERVICE : "SERVICE", // 客服
	ADMIN : "ADMIN", // 管理员
	NONE : "NONE",
	JT : "JT"
}
var msgState = {
	UNREAD:"UNREAD",	//未读
	READ:"READ"			//已读
}
var msgType = {
	_CUSTOMER_ONLINE : "_CUSTOMER_ONLINE", // 客服上线
	_CUSTOMER_OFFLINE : "_CUSTOMER_OFFLINE", // 客服离线
	_USER_ONLINE : "_USER_ONLINE", // 用户上线
	_USER_OFFLINE : "_USER_OFFLINE",
	_VIDEO_MSG : "_VIDEO_MSG", // 声音消息
	_TEXT_MSG : "_TEXT_MSG", // 文字消息
	_IMG_MSG : "_IMG_MSG", // 图片消息
	_SYS_MSG : "_SYS_MSG",
	_USER_CLOSED : "_USER_CLOSED",// 关闭成员
	_LAYER_MSG : "_LAYER_MSG", // 分类
	_SIGN_READ : "_SIGN_READ", // 标记消息已读
	_CHANGE_SERV : "_CHANGE_SERV", // 客服移交
	_LEAVE_MSG : "_LEAVE_MSG", // 留言
	_HELP : "_HELP", 			// 求助
	_ONLY_ONLINE : "_ONLY_ONLINE" // 唯一在线
}

var STATES = {
	ONLINE : "ONLINE",
	RECONNECT : "RECONNECT",
	BYLAYER:"BYLAYER",				// 按类型连接
	OFFLINE : "OFFLINE",
	BUSYNESS : "BUSYNESS"
}

var PRO = {
	uid : -1,
	to_uid : 999,
	username : "",
	nickname : "",
	uType : uType.USER,
	msgType : msgType._USER_ONLINE,
	uhost : "",
	shost : "",
	body : "",
	companyID : -1,
}

function userProtocol(uid, msg) {
	PRO.body = msg;
	PRO.uid = uid;
	return JSON.stringify(PRO);
}

function covertProtocol(protocol, msg) {
	var PRO_ = JSON.parse(protocol);
	PRO_.body = msg;
	PRO.uType = PRO_.uType;
	var msg = JSON.stringify(PRO_);
	return msg;
}


function covert(protocol) {
	var PRO_ = JSON.parse(protocol);
	return PRO_;
}

function covertLayer(protocol,layerId){
	var PRO_ = JSON.parse(protocol);
	PRO_.layerId=layerId;
	PRO_.msgType=msgType._USER_ONLINE;
	PRO_.state=STATES.BYLAYER;
	return JSON.stringify(PRO_);;
}




