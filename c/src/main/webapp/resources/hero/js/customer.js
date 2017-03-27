pageWhitch("#servicesManage",serList);

function serList(){
	$(".main_content .main_con").hide();
	$("#servicesManage").fadeIn();
	$.ajax({
		url:ctx+"/oc/ser/list",
		type:"post",
		data:{"page":page-1,"size":pageSize},
		success:function(data){
			var str = {
					cur:curUserAccount,
					list:data.content
			}
			var html = template("servList",str);
			$("#serList").html(html);
			var $serv = "#servicesManage";
			var total = data.totalPages;
			if(page>total){
				page = 1;
				pageAll = 1;
			}
			showPage(data.totalElements,page,$serv);//显示分页
		}
	});
}
//新增-页
function toAddSer(){
	var str="";
	$.ajax({
		url:ctx+"/oc/layer/list",
		type:"get",
		data:{"page":0,"size":50},
		success:function(data){
			//填充分组
//			$("#addOneSer .groups").text(data[0].layerName);
			var data = data.content;
			for(var i = 0; i < data.length; i++){
				str+="<li class='all_layer'>"+data[i].layerName+"<span class='del_group'></span><span class='ch_group' data-name='"+data[i].layerName+"'  data-aid="+data[i].id+"></span></li>";
			}
			$(".addGroup").next().html(str);
		}
	})
}
//新增
function addSer(){
	var perms="";
	$("input[type=checkbox][name=addperms]:checked").each(function(){
		perms += $(this).val()+",";
	});
	var p=$("input[name=addperms][type=radio]:checked").val();
	perms = perms+p;
	var account = $("#addOneSer #account").val();
	var userName = $("#addOneSer #userName").val();
	var nickName = $("#addOneSer #nickName").val();
	var receiveNum = $("#addOneSer #receiveNum").val();
	var layerid=$("#addOneSer #layerId").val();
	var autoAllot = $("#autoAllot").hasClass("allot_off") ? false : true;
	if(account == ""){
		layer.msg("客服账号不能为空！且长度必须在6~16位");$("#addOneSer #account").focus();return;
	}
	if(userName == ""){
		layer.msg("客服姓名不能为空！且长度必须在6~16位");return;
	}
	if(nickName == ""){
		layer.msg("客服昵称不能为空！且长度必须在6~16位");return;
	}
	if(layerid == ""){
		layer.msg("必须选择分组！");return;
	}
	$.ajax({
		url:ctx+"/oc/ser/add",
		data:{
			"perms":perms,
			"account":account,
			"userName":userName,
			"nickName":nickName,
			"receiveNum":receiveNum,
			"autoAllot":autoAllot,
			"layer.id":layerid
		},
		type:"POST",
		success:function(data){
			if(data.errors != undefined){
				var error = data.errors;
				layer.alert(error[0].field+":"+error[0].message);return ;
			}else if(data.msg != undefined){
				if(data.code){
					layer.msg(data.msg);
					serList();
					clearAddSer();
					closeBox($("#addOneSer"),$(".layer_bg"));
				}else{
					layer.msg(data.msg);
				}
			} else {
				layer.alert("登录已超时！");
				window.location.href = "${ctx}/login";
			}
		}
	});
}
//修改客服页
function toEditSer(id){
	$.ajax({
		url:ctx+"/oc/ser/edit/"+id,
		type:"get",
		async:false,
		success:function(data){
			var roles = data.roles;
			var layer = data.layer;
			var str = "";
			if(roles != null){
				for(var i = 0; i < roles.length; i++){
					$("input[type=checkbox][name=editperms]").each(function(){
						if($(this).val() == roles[i].roleName){
							//选中
							$(this).attr("checked",true);
						}
					});
					$("input[name=editperms][type=radio]").each(function(){
						if($(this).val() == roles[i].roleName){
							//选中
							$(this).attr("checked",true);
						}
					})
				}
			}
			//
			if(layer != null){
				for(var i = 0; i < layer.length; i++){
					if(layer[i].layerName == $("#editOneSer #groupName").val()) $("#editOneSer #layerId").val(layer[i].layerName);
					str+="<li class='all_layer'>"+layer[i].layerName+"<span class='del_group'></span><span class='ch_group' data-name='"+layer[i].layerName+"' data-eid="+layer[i].id+"></span></li>";
				}
			}
			$(".addGroup").next().html(str);
		}
	});
}
//修改客服信息
function editSer(){
	var perms="";
	$("input[type=checkbox][name=editperms]:checked").each(function(){
		perms += $(this).val()+",";
	});
	var p=$("input[name=editperms][type=radio]:checked").val();
	perms = perms+p;
	var id = $("#editOneSer").find(".ser_id").attr("data-sid");
	var account = $("#editOneSer").find(".ser_id").text();
	var userName = $("#editOneSer").find(".ser_name").text();
	var nickName = $("#editOneSer").find(".ser_nike").text();
	var receiveNum = $("#editOneSer #receiveNum").val();
	var layerid=$("#editOneSer #layerId").val();
	var autoAllot = $("#editOneSer #autoAllot").hasClass("allot_off") ? false : true;
	if(account == ""){
		layer.msg("客服账号不能为空！且长度必须在6~16位");return;
	}
	if(userName == ""){
		layer.msg("客服姓名不能为空！");return;
	}
	if(nickName == ""){
		layer.msg("客服昵称不能为空！");return;
	}
	if(layerid == ""){
		layer.msg("必须选择分组！");return;
	}
	$.ajax({
		url:ctx+"/oc/ser/edit",
		data:{
			"id":id,
			"perms":perms,
			"account":account,
			"userName":userName,
			"nickName":nickName,
			"receiveNum":receiveNum,
			"autoAllot":autoAllot,
			"layer.id":layerid
		},
		type:"POST",
		success:function(data){
			if(data.errors != undefined){
				var error = data.errors;
				layer.alert(error[0].field+":"+error[0].message);return ;
			}else if(data.msg != undefined){
				layer.alert(data.msg);
			}else {
				layer.alert("登录已超时！");
				window.location.href = "${ctx}/login";
			}
			var serls=$("#editSerList").find("li");
			if(serls.length <=1){
				serList();
				closeBox($("#editOneSer"),$(".layer_bg"));
				$("#editSerList").html("");
			} else {
				$("#editSerList").find("li.cur").remove();
				$("#editSerList").find("li").first().addClass("cur");
				serSwitch($("#editSerList"),$("#editOneSer"));
			}
		}
	});
}

//当前选中客服资料详细
function serSwitch(obj,curObj){
    $.each(obj.find("li"),function(i,e){
        if($(e).hasClass("cur")){
            curObj.find(".ser_id").text($(e).attr("data-id"));
            curObj.find(".ser_id").attr("data-sid",$(e).attr("data-sid"));
            curObj.find(".ser_name").text($(e).attr("data-name"));
            curObj.find(".ser_nike").text($(e).attr("data-nike"));
            curObj.find(".groups").text($(e).attr("data-nike"));
            curObj.find("select").val($(e).attr("data-limit"));
            if($(this).attr("data-auto") =="off"){
                curObj.find(".allot").addClass("allot_off");
            }else{
                curObj.find(".allot").removeClass("allot_off");
            }
        }
    });
}
//客服启用禁用
function locked(id,bol){
	var res = false;
	$.ajax({
		url:ctx+"/oc/ser/lock:"+!bol+"/"+id,
		type:"POST",
		async:false,
		success:function(data){
			res=data;
		}
	})
	return res;
}
//删除
function delSer(id){
	var res = false;
	$.ajax({
		url:ctx+"/oc/ser/del/"+id,
		type:"POST",
		async:false,
		success:function(data){
			if (!data) {
				layer.alert("删除失败！");
			}
			res=data;
		}
	})
	return res;
}
//自动分配
function allot(id,bol){
	var res = false;
	$.ajax({
		url:ctx+"/oc/ser/allot:"+bol+"/"+id,
		type:"POST",
		async:false,
		success:function(data){
			res=data;
		}
	})
	return res;
}
function clearAddSer(){
	$("#account").val("");
	$("#userName").val("");
	$("#nickName").val("");
	$("#receiveNum").val("");
	$("input[type=checkbox][name=addperms]").each(function() {
		$(this).attr("checked", false);
	});
	$("input[name=addperms][type=radio]").each(function() {
			$(this).attr("checked", false);
	})
}
function clearEditSer(){
	$("input[type=checkbox][name=editperms]").each(function() {
		$(this).attr("checked", false);
	});
	$("input[name=editperms][type=radio]").each(function() {
		$(this).attr("checked", false);
	})
	$("#editSerList").html("");
}


//----------------------------------------------------------神的分割线----------------------------------------------------------------------------------------------------------------------------------------------------//
