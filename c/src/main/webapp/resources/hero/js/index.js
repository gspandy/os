$(function(){
//	//禁用右键
//	$(document).bind("contextmenu",function(e) { 
//		return false; 
//	}); 
//	//禁用F5
//	$(document).bind("keydown", function(e) {
//		e = window.event || e;
//		if (e.keyCode == 116) {
//			  return false;
//		}
//	});
	//关闭聊天弹框
	$("#boxDiv").click(function(e){			
	    var drag = $("#chartBox"),
	        dragel = $("#chartBox")[0],
	        target = e.target;
	    if (dragel !== target && !$.contains(dragel, target)) {
	        drag.hide(500);
	        $("#boxDiv").removeClass("box_div");
	        $("#chartGroup").find("dl").removeClass("chat_cur");
	    }		
	});	
    var aLi = $("#nav>li"); //只找下一级li元素
    //展开左侧二级菜单
    aLi.on("click",function(event){
        $(this).addClass("cur").siblings().removeClass("cur");
        if($(this).find("ul").length>0){
        	var subNav = $(this).find(".sub_nav");
            if( subNav.is(":visible")){
                $(this).find("span").css("transform","rotate(0deg)");
                subNav.slideUp();
            }else{
                $(this).find("span").css("transform","rotate(180deg)");
                subNav.slideDown();
            }
        }
//        var curMain = $(".main_con").eq($(this).index());
//       curMain.siblings().fadeOut(function(){
//            curMain.fadeIn();
//        });

    });
    //阻止冒泡
    aLi.on("click","li",function(event){
        event.stopPropagation();
        $(this).addClass("hover").siblings().removeClass("hover");
//        var curDialog = $(".web_dialogue").eq($(this).index());
//        curDialog.siblings().fadeOut(function(){
//            curDialog.fadeIn();
//        });
    });

    //展开客服列表
    var serTm = $("#serTeam");
    var closeTm = serTm.find("h2 .close");
    $("#servNews").on("click",function(){
        if(serTm.css("opacity")==0){
        	teamList();//加载客服成员列表
            serTm.css({
                "right":0,
                "opacity":1,
                "z-index":30
            });
            serTm.parent().css("z-index",4);
        }else{
            serTm.css({
                "right":"-300px",
                "opacity":0,
                "z-index":-1
            });
            serTm.parent().css("z-index",2);
        }
        quit.css({
            "top":"70px",
            opacity:0,
            "visibility":"hidden",
            "z-index":0
        });
    });
    closeTm.on("click",function(){
        serTm.css({
            "right":"-300px",
            "opacity":0,
            "z-index":-1
        });
        serTm.parent().css("z-index",2);
    });

    //
    $("#userNews").on("click",function(){
    	if($("#chartBox").css("display") == "none") {
    	     $("#boxDiv").addClass("box_div");
    		var total = $("#chartBox #groupList").children().length;
    		var teamTotal = $("#chartBox #teamsNum").children().length;
    		if($("#chartBox #teamsNum").find("span.red").length > 0 || total == 0){
    			var guest = $("#chartGroup li").first();
    			guest.find("i").addClass("keep_it");
    			guest.find(".tm_list").css({"opacity":1}).slideDown(0);
    			$("#chartBox #teamsNum dl").first().click();    			
    		}else{
    			$("#chartBox #groupList dl").first().click();
    		}
    		$("#chartBox").show(500);
    	} else {
    		$("#chartBox").hide(500);
    	}
    	showOnlineNum();
    });
    
    //弹出退出登录选项
    var quit = $(".quit");
    $("#onLine").on("click",function(){
        if(quit.css("opacity")==0){
            quit.css({
                "top":"60px",
                "opacity":1,
                "visibility":"inherit",
                "z-index":99
            });
        }else{
            quit.css({
                "top":"70px",
                opacity:0,
                "visibility":"hidden",
                "z-index":0
            });
        }
        serTm.css({
            "right":"-300px",
            "opacity":0,
            "z-index":-1
        });
    });
    //退出登录
    quit.on("click","a.exit",function(event){
        event.stopPropagation();
        userLogout();
    });
    //设置
    var sheZhi = $("#sheZhi");
    quit.on("click","a.shezhi",function(event){
    	event.stopPropagation();
    	$("#chartBox").hide();
    	openBox(sheZhi,$(".layer_bg"));
    	info();
    });
    $("#sheZhi").on("click",".close", function(){
    	closeBox(sheZhi,$(".layer_bg"));
    });
    //可移交列表关闭
    $("#changeNable").on("click",".close", function(){
    	closeBox($("#changeNable"),$(".layer_bg"));
    });
  //展开设置编辑项
    function slideEditSet(obj){
        var slides = obj.find("h5");
        slides.on("click","a",function(){
            if($(this).parent().next().is(":visible")){
                $(this).find("span").text("编辑").next().removeClass("up_ico");
                $(this).parent().next().slideUp();
            }else{
                $(this).find("span").text("收起").next().addClass("up_ico");
                $(this).parent().next().slideDown();
            }
        });
    }
    slideEditSet(sheZhi);

    //全选，多选功能
    function AllChose(allObj,choseObj){
        this.allObj = allObj;
        this.choseObj = choseObj;
        this.allChecked = this.allObj.find("input");
        this.chose();
    }
    AllChose.prototype.chose = function(){
        var _this = this;
        this.choseObj.on("change",function(){
    		if($(this).is(":checked")){
                $.each(_this.allObj.find("input"),function(i,e){
                    $(e).prop("checked",true);
                });
                _this.allObj.parent().find("thead").css("opacity",0).slideUp();
                $(this).parent().parent().next().css("opacity",1).slideDown();
            }else{
                $.each(_this.allObj.find("input"),function(i,e){
                    $(e).prop("checked",false);
                });
                _this.allObj.parent().find("thead").css("opacity",1).slideDown();
                $(this).parent().parent().next().css("opacity",0).slideUp();
            }
        });
        this.allObj.on("change","input",function(){
            if(!$(this).is(":checked")){
                _this.choseObj.prop("checked",false);
            }
            if($(this).is(":checked") && _this.allObj.find("input:checked").length > 1){
                _this.choseObj.parent().parent().next().css("opacity",1).slideDown();
                _this.allObj.parent().find("thead").css("opacity",0).slideUp();
            }else if(_this.allObj.find("input:checked").length < 1){
                _this.choseObj.parent().parent().next().css("opacity",0).slideUp();
                _this.allObj.parent().find("thead").css("opacity",1).slideDown();
            }
        });
    };

    //会话管理全选，多选编辑状态
    var sessionList = $("#sessionList"), choseAll = $("#choseAll"),
        serList = $("#serList"), choseAllSer = $("#choseAllSer"),
        clientList = $("#clientList"),choseAllClient = $("#choseAllClient");
//    new AllChose(sessionList,choseAll);
    //客服管理单选，多选编辑状态
    new AllChose(serList,choseAllSer);
    //客户管理单选，多选编辑状态
    new AllChose(clientList,choseAllClient);
    

    //页码点击效果
    $(".page_num").on("click","a",function(){
        $(this).addClass("cur").siblings().removeClass("cur");
    });

    //离线时头像变灰，只需在离线用户头像图片加class名gray即可
    //grayscale(document.getElementsByClassName("gray"));

    //右侧客服列表点击，打开聊天窗口
    serTm.on("click","dl",function(){
//        window.open("chatting.html");
    });

    //打开(单条)历史记录
    var historyList = $("#historyBox");
    sessionList.on("click",".history_ico",function(){
        var h2Txt = $(this).parent().siblings().find(".user_name").text();
        $(".visitor_list").addClass("hide").parent().prev().find("span").text(h2Txt).parent().parent().addClass("single_his");
        userHistory(h2Txt);
        openBox(historyList,$(".layer_bg"));
    });
    historyList.on("click",".close",function(){
        closeBox(historyList,$(".layer_bg"));
        $(".visitor_list").html("").removeClass("hide").parent().prev().find("span").text("历史记录").parent().parent().removeClass("single_his");
    });

    //禁止会话按钮
    sessionList.on("click",".un_talk",function(){
        var h2Txt = $(this).parent().siblings().find(".user_name").text();
       
    });
    
    //删除会话记录按钮
    sessionList.on("click",".del_talk",function(){
    	if(confirm("确认执行此操作？你们的聊天记录会被清除哦！")){
	        var h2Txt = $(this).parent().siblings().find(".user_name").text();
	        $.ajax({
	    		url:ctx+"/oc/jt-user/delChatRecord",
	    		data:{"username":h2Txt,"userId":PROS_.uid,"companyId":PROS_.companyID},
	    		type : "POST",
	    		success:function(data){
	    			//console.log(data);
	    			if(data.code == true){
	    				layer.msg(data.msg);
	    			}
	    		}
	    	});
    	}
    });
    //打开多条历史记录
    var muHistory = choseAll.parent().parent().next().find(".history_ico");
    muHistory.on("click",function(){
        var choseList = $("#sessionList").find("input:checked");
        $.each(choseList,function(i,e){
            var userName = $(e).parent().parent().siblings().find(".user_name").text(),
                lastTalk = $(e).parent().parent().siblings().find(".last_talk").text(),
                gray = "";
            //如果用户离线，值为gray,否则为空
            if($(e).parent().parent().siblings().hasClass("online")){
                gray = "";
            }else{
                gray = "gray";
            }
            var usrList = "<dl class='cf'><dt><img src='"+ctx+"/resources/hero/img/user_img1.jpg' class='"+ gray
                +"'></dt><dd>"+ userName +"</dd><dd>"+ lastTalk +"</dd></dl>";
            $(".visitor_list").append(usrList);
        });
        openBox(historyList,$(".layer_bg"));
    });

    //单条禁止状态
    function forbid(obj,childObj,txt1,txt2,fun){
        obj.on("click",childObj,function(){
            var forbidIco = $(this).parent().parent().find(".forbid");
            var id = $(this).parent().parent().find(".forbid").attr("data-cid");
            if(forbidIco.css("opacity")==0){//1代表是禁用状态
            	if(fun(id,false)){
    				forbidIco.css("opacity",1);
    				$(this).find("i").text(txt1);
    			}else{
    				layer.alert("禁用失败！")
    			}
            }else{
            	if(fun(id,true)){
            		forbidIco.css("opacity",0);
            		$(this).find("i").text(txt2);
            	} else {
            		layer.alert("启用失败！")
            	}
            }
        });
    }
//    forbid(sessionList,".un_talk","恢复会话","禁止会话",forbid);
    forbid(serList,".un_talk","启用","禁用",locked);
    forbid(clientList,".un_talk","启用","禁用",isEnable);

    //多条禁止状态
    function forbidAll(obj,objList,txt1,txt2,fun){
        var muForbid = obj.parent().parent().next().find(".un_talk");
        muForbid.on("click",function(){
            var choseList = objList.find("input:checked");
            $.each(choseList,function(i,e){
                var forbidIco = $(e).parent().parent().next().find(".forbid");
                var listForbid = $(e).parent().parent().siblings().find(".un_talk");
                var id = $(e).parent().parent().next().find(".forbid").attr("data-cid");
                if(forbidIco.css("opacity")==0){
                	if(fun(id,false)){
                		forbidIco.css("opacity",1);
                		muForbid.find("a").text(txt1);
                		listForbid.find("i").text(txt2);
                	}
                }else{
                	if(fun(id,true)){
                		forbidIco.css("opacity",0);
                		muForbid.find("a").text(txt2);
                		listForbid.find("i").text(txt1);
                	}
                }
            });
        });
    }
    //禁止多条会话
//    forbidAll(choseAll,sessionList,"恢复会话","禁止会话",forbid);
    //禁用多个客服
    forbidAll(choseAllSer,serList,"启用","禁用",locked);
    //禁用多个客户
    forbidAll(choseAllClient,clientList,"启用","禁用",isEnable);

    //删除单条记录
    function delSingle(obj,childObj,func){
        obj.on("click",childObj,function(){
        	if(confirm("确认执行此操作？")){
        		var id = $(this).parent().siblings().find(".forbid").attr("data-cid");
        		if(func(id)){
        			$(this).parent().parent().remove();
        		}
        	}
        });
    }
    //delSingle(sessionList,".del_talk");
    delSingle(serList,".del_talk",delSer);
    delSingle(clientList,".del_talk",delComp);

    //删除多条记录
    function delMul(oInput,oBtn,func,fn){
        var muDel = oInput.parent().parent().next().find(".del_talk");
        muDel.on("click",function(){
            var choseList = oBtn.find("input:checked");
            if(confirm("确认执行此操作？(此操作不可恢复！)")){
            	$.each(choseList,function(i,e){
            		var id = $(e).parent().parent().siblings().find(".forbid").attr("data-cid");
            		if(func(id)){
            			$(e).parent().parent().parent().remove();
            		}
            	});
            }
            if(oBtn.find("input:checked").length == 0){
                oInput.prop("checked",false);
            }
            //回调函数，删除完后加载新的数据
            if(fn){
                fn();
            }
        });
    }
    //删除会话管理记录
    delMul(choseAll,sessionList);
    //删除客服列表记录
    delMul(choseAllSer,serList,delSer);
    //删除客户列表记录
    delMul(choseAllClient,clientList,delComp);

    //会话列表打开聊天页
    sessionList.on("click",".talk_ico",function(){
//        window.open("chatting.html");
    	var $id = $(this).attr("data-name");
//    	var $id = $(this).parent().attr("data-name");
    	$("#chartBox #groupList").prepend($("#guest_" +$id));
    	$("#chartBox #groupList dl").first().click();
    	$("#chartBox").show(500);
    	$("#boxDiv").addClass("box_div");
    });
    //客服成员打开聊天
    $('.team_list').on("click","dl",function(){
        /*var $dl = $(this).clone(true).attr("id","team_"+$(this).attr("id")+"_");
        console.log($('#team_'+ $(this).attr("id")+"_").length)
        if($('#team_'+ $(this).attr("id")+"_").length<=0){
            $("#chartBox #teamsNum").prepend($dl);
        }else{
            return;
        }*/
        if($(this).hasClass("gray") || $(this).attr("id")==curUserAccount)return;
        var $id = $(this).attr("id");
        $("#chartBox #teamsNum").prepend($("#team_" +$id));
        var team = $("#chartGroup li").first();
		team.find("i").addClass("keep_it");
		team.find(".tm_list").css({"opacity":1}).slideDown(0);
        $("#chartBox #teamsNum dl").first().click();
        $("#chartBox").show(500);
    })
    //多选打开聊天页
    choseAll.parent().parent().next().find(".talk").on("click",function(){
//        window.open("chatting.html");
    });

    //切换客服管理自动分配
    serList.on("click",".auto_on",function(){
    	var id=$(this).parent().siblings().find(".forbid").attr("data-cid");
    	if(confirm("是否要执行？")){
    		if($(this).hasClass("auto_off")){
    			if(allot(id, true)){
    				$(this).removeClass("auto_off");
    			}
    		}else{
    			if(allot(id, false)){
    				$(this).addClass("auto_off");
    			}
    		}
    	}
    });

    //选择查询时间
    $("#choseTime").on("focus","input",function(){
        $(this).calendar();
    });

    //历史记录查询时间
	$("#srechTime").on("focus","input",function(){
		laydate({istime:true,format: 'YYYY-MM-DD'});
	});
    
    //新增客服信息
    var addOneSer = $("#addOneSer");
    var editOneSer = $("#editOneSer");
    $("#addService").on("click",function(){
        openBox(addOneSer,$(".layer_bg"));
        toAddSer();
    });
    //关闭新增窗口
    addOneSer.on("click",".close",function(){
    	clearAddSer();
        closeBox(addOneSer,$(".layer_bg"));
    });
    //关闭编辑窗口
    editOneSer.on("click",".close",function(){
    	clearEditSer();
        closeBox(editOneSer,$(".layer_bg"));
    });
    //展开编辑项
    function slideEdit(obj){
        var slides = obj.find("h3");
        slides.on("click","a",function(){
            if($(this).parent().next().is(":visible")){
                $(this).find("span").text("展开").next().removeClass("up_ico");
                $(this).parent().next().slideUp();
            }else{
                $(this).find("span").text("收起").next().addClass("up_ico");
                $(this).parent().next().slideDown();
            }
        });
    }
    //新增时展开编辑
    slideEdit(addOneSer);
    //修改时展开编辑
    slideEdit(editOneSer);

    //添加客服时设置是否自动分配
    function serAllot(obj){
        var allot = obj.find(".ser_data");
        allot.on("click",".allot",function(){
            if($(this).hasClass("allot_off")){
                $(this).removeClass("allot_off");
            }else{
                $(this).addClass("allot_off");
            }
        });
        //编辑分组
        allot.on("click",".groups",function(){
            if($(this).next().css("opacity")==0){
                $(this).next().css({
                    "visibility":"initial",
                    "opacity":1,
                    "top":"72px"
                });
            }else{
                $(this).next().css({
                    "visibility":"hidden",
                    "opacity":0,
                    "top":"80px"
                });
            }
        });
        //搜索分组
        allot.on("click",".addGroup",function(){
            var groupName = "";
            var str = "";
//            if($(this).prev().find("input").val()!=""){
//                groupName = $(this).prev().find("input").val();
//            }else{
//                $(this).prev().find("input").attr("placeholder","请输入组名");
//                return false;
//            }
//            str = "<li>"+groupName+"<span class='del_group'></span><span class='ch_group' data-name='"+groupName+"'></span></li>";
//            $(this).next().append(str);
//            $(this).prev().find("input").val("");
        });
        //删除分组
        allot.on("click",".del_group",function(){
        	if(!$(this).next().hasClass("chose")){
				$(this).parent().animate({"right":"-30px","opacity":.2},300,function(){
					$(this).remove();
				});	
			}
        });
        allot.on("click",".all_layer",function() {
        	$this = $(this).find(".ch_group");
            var gName = $this.attr("data-name");
            var eid = $this.attr("data-eid");
            var aid = $this.attr("data-aid");
            if(eid != undefined && eid != ""){
            	$("#editOneSer #layerId").val(eid);
            }
            if(aid != undefined && aid != ""){
            	$("#addOneSer #layerId").val(aid);
            }
            $this.prev().css("color","#e5e5e5").parent().siblings().find(".del_group").css("color","#9da1a6");                          
            $this.addClass("chose");                         
            var currentLi = $this.parent();        
        	currentLi.parents(".set_group").prev().text(gName).next().css({
        		"visibility":"hidden",
                "opacity":0,
                "top":"80px"
            });            	
        	$this.parent().siblings().find(".ch_group").removeClass("chose").closest(".set_group");            
        });
    }
    //新增时设置是否自动分配
    serAllot(addOneSer);
    serAllot(editOneSer);

    //修改单个客服资料
    serList.on("click",".revise",function(){
        editOneSer.find(".ser_list").addClass("hide");
        openBox(editOneSer,$(".layer_bg"));
        var serId = $(this).parent().parent().find(".ser_id").text(),
            serName = $(this).parent().parent().find(".ser_name").text(),
            serNike = $(this).parent().parent().find(".ser_nike").text(),
            serGroup = $(this).parent().parent().find(".ser_group").text(),
            serLimit = $(this).parent().parent().find(".group_num").text(),
            autoOn = $(this).parent().parent().find("#allot");
        var id = $(this).parent().parent().find(".forbid").attr("data-cid");
        toEditSer(id);
        
        editOneSer.find(".ser_id").text(serId);
        editOneSer.find(".ser_id").attr("data-sid",id);
        editOneSer.find(".ser_name").text(serName);
        editOneSer.find(".ser_nike").text(serNike);
//        editOneSer.find(".groups").text(serGroup);
        editOneSer.find("select").val(serLimit);
        if(autoOn.hasClass("auto_off")){
            editOneSer.find(".allot").addClass("allot_off");
        }else{
            editOneSer.find(".allot").removeClass("allot_off");
        }
    });

    //修改多个客服资料
    var editALlSer = choseAllSer.parent().parent().next().find(".revise");
    editALlSer.on("click",function(){
        editOneSer.find(".ser_list").removeClass("hide");
        setTimeout(function(){
            editOneSer.addClass("mul_service");
            openBox(editOneSer,$(".layer_bg"));
        },50);
        var choseList = serList.find("input:checked");
        $.each(choseList,function(i,e){
            var userId = $(e).parent().parent().siblings().find(".ser_id").text(),
                userName = $(e).parent().parent().siblings().find(".ser_name").text(),
                userNike = $(e).parent().parent().siblings().find(".ser_nike").text(),
//                userGroup = $(e).parent().parent().siblings().find(".ser_group").text(),
                userLimit = $(e).parent().parent().siblings().find(".group_num").text(),
                liClass = i==0?"cur":"",
                id = $(e).parent().parent().siblings().find(".forbid").attr("data-cid"),
                userAuto = $(e).parent().parent().siblings().find(".auto_on").hasClass("auto_off")?"off":"on";
            var list = "<li class='"+liClass+"' data-id='"+userId+"' data-sid='"+id+"' data-name='"+userName+"' data-nike='"+userNike+"' data-group='"+
                userGroup+"' data-limit='"+userLimit+"' data-auto='"+userAuto+"'>"+userId+"</li>";
            $("#editSerList").append(list);
        });
        serSwitch($("#editSerList"),editOneSer)
    });
    //当前选中客服资料详细
    function serSwitch(obj,curObj){
        $.each(obj.find("li"),function(i,e){
            if($(e).hasClass("cur")){
            	toEditSer($(e).attr("data-sid"));
                curObj.find(".ser_id").text($(e).attr("data-id"));
                curObj.find(".ser_id").attr("data-sid",$(e).attr("data-sid"));
                curObj.find(".ser_name").text($(e).attr("data-name"));
                curObj.find(".ser_nike").text($(e).attr("data-nike"));
                curObj.find(".groups").text($(e).attr("data-group"));
                curObj.find("select").val($(e).attr("data-limit"));
                if($(this).attr("data-auto") =="off"){
                    curObj.find(".allot").addClass("allot_off");
                }else{
                    curObj.find(".allot").removeClass("allot_off");
                }
            }
        });
    }
    
    //切换客服资料
    $("#editSerList").on("click","li",function(){
        $(this).addClass("cur").siblings().removeClass("cur");
        serSwitch($("#editSerList"),editOneSer)
    });

    //编辑快速输入内容
    var quickEnter = $("#quickEnter");
    var quickItem = $("#quickItem");
    $(".quick_enter").on("click",function(){
    	quickList();
        openBox(quickEnter,$(".layer_bg"));
    });
    quickEnter.on("click",".close",function(){
        closeBox(quickEnter,$(".layer_bg"));
    });
    var addQuick = quickItem.next().find("span");
    addQuick.on("click",function(){
    	if(addQuickCon()){
    		var addTxt = $(this).prev().val(),
    		addList = "<li><span>"+ addTxt +"</span><a href='javascript:void(0);' class='del_li close_chart'></a></li>";
    		if($.trim(addTxt) != ""){
    			quickItem.append(addList);
    			$(this).prev().val("");
    		}
    	}
    });
    quickItem.on("click",".del_li",function(){
    	var _this = $(this);
    	layer.confirm("确定要删除该内容？",{},function(){
    		var id = _this.attr("data-qid");
    		if(delQuickCon(id)){
    			_this.parent().animate({
    				"left":"15px","opacity":0
    			},function(){
    				_this.remove();
    			});
    		}
    	});
    });

    //编辑提示内容
    var setTips = $("#setTips");
    $(".quick_tips").on("click",function(){
    	toAddTips();
        openBox(setTips,$(".layer_bg"));
    });
    setTips.on("click",".close",function(){
        closeBox(setTips,$(".layer_bg"));
    });
    //切换提示
    $("#tipTab").on("click","li",function(){
        $(this).addClass("cur").siblings().removeClass("cur");
        var currentTip = $(".tips").eq($(this).index());
        $(".tips:visible").fadeOut(300,function(){
            currentTip.fadeIn();
        });
    });

    //新增分组
    var addLayer = $("#addLayer"),
        addOneLayer = $("#addOneLayer"),
        editOneLayer = $("#editOneLayer");
    addLayer.on("click",function(){
        openBox(addOneLayer,$(".layer_bg"));
    });
    addOneLayer.on("click",".close",function(){
        closeBox(addOneLayer,$(".layer_bg"));
    });
    editOneLayer.on("click",".close",function(){
    	closeBox(editOneLayer,$(".layer_bg"));
    });
    //新增客户
    var addClient = $("#addClient"),
    addOneClient = $("#addOneClient");
    addClient.on("click",function(){
    	openBox(addOneClient,$(".layer_bg"));
    });
    addOneClient.on("click",".close",function(){
    	closeBox(addOneClient,$(".layer_bg"));
    });
    //编辑单个客户资料
    var editClient = $("#editClient");
    clientList.on("click",".revise",function(){
        var curId = $(this).parent().siblings().find(".c_id").text(),
            curIp = $(this).parent().siblings().find(".c_ip").text(),
            curComPy = $(this).parent().siblings().find(".c_dress").text(),
            curDomain = $(this).parent().siblings().find(".c_domain").text(),
            curPhone = $(this).parent().siblings().find(".c_tel").text();
        var curCompanyId = $(this).parent().siblings().find(".forbid").attr("data-cid");
        $("#company_id").val(curCompanyId);
        $("#cusId").val(curId);
        $("#cusIp").val(curIp);
        $("#cusCompany").val(curComPy);
        $("#doMain").val(curDomain);
        $("#cusPhone").val(curPhone);
        var token = getToken();
        $("#token").val(token);
        openBox(editClient,$(".layer_bg"));
    });
	editClient.on("click", ".close", function() {
		closeBox(editClient, $(".layer_bg"));
		editClient.removeClass("add_mul_client");
		$("#customerList").html("");
	});


    //编辑多个客户资料
    var editMuClient = choseAllClient.parent().parent().next().find(".revise"),
        customerList = $("#customerList");
    editMuClient.on("click",function(){
    	customerList.html("");
        editClient.addClass("add_mul_client");
        var editList = clientList.find("input:checked");
        openBox(editClient,$(".layer_bg"));
        $.each(editList,function(i,e){
            var cusId = $(e).parent().parent().siblings().find(".c_id").text(),
                cusIp = $(e).parent().parent().siblings().find(".c_ip").text(),
                cusComPy = $(e).parent().parent().siblings().find(".c_dress").text(),
                cusDomain = $(e).parent().parent().siblings().find(".c_domain").text(),
                cusPhone = $(e).parent().parent().siblings().find(".c_tel").text(),
                liClass = i==0?"cur":"";
            var curCompanyId = $(e).parent().parent().siblings().find(".forbid").attr("data-cid");
            
            var cusLi = "<li class='"+liClass+"' data-cid='"+curCompanyId+"' data-id='"+cusId+"' data-ip='"+cusIp+"' data-compy='"+cusComPy+"' data-dom='"+cusDomain+"' data-tel='"+cusPhone+"'>"+cusId+"</li>";
            customerList.append(cusLi);
        });
        clientSwitch(customerList);
    });
    customerList.on("click","li",function(){
        $(this).addClass("cur").siblings().removeClass("cur");
        clientSwitch(customerList);
    });
    //当前选中客户资料
    function clientSwitch(obj){
        $.each(obj.find("li"),function(i,e){
            if($(e).hasClass("cur")){
            	$("#company_id").val($(e).attr("data-cid"));
                $("#cusId").val($(e).attr("data-id"));
                $("#cusIp").val($(e).attr("data-ip"));
                $("#cusCompany").val($(e).attr("data-compy"));
                $("#doMain").val($(e).attr("data-dom"));
                $("#cusPhone").val($(e).attr("data-tel"));
            }
        });
    }
    
    //新增权限窗口关闭
    var addOnePerm = $("#addOnePerm"),
    	editOnePerm = $("#editOnePerm");
    addOnePerm.on("click",".close", function(){
    	closeBox(addOnePerm,$(".layer_bg"));
    });
    //修改权限窗口关闭
    editOnePerm.on("click",".close", function(){
    	closeBox(editOnePerm,$(".layer_bg"));
    });
    
    //角色新增
    var addRole = $("#addRole"),
    	addOneRole = $("#addOneRole");
    addRole.on("click",function(){
    	openBox(addOneRole,$(".layer_bg"));
    	toAddRole();
    });
    addOneRole.on("click",".close",function(){
    	closeBox(addOneRole,$(".layer_bg"));
    });
    
    

	//点击关闭弹出层
	$(".layer_bg").on("click",function(){		
		$(this).children("div").css({
			"opacity":0,
			"top":"60%",
			"z-index":2
		}).removeClass("hide");
		var _this = $(this);
		setTimeout(function(){
			_this.fadeOut().css("z-index",0);
		},300);
		 $("#customerList li").remove();
		 $("#editSerList").html("");
	});
	$(".layer_bg").children("div").on("click",function(event){
		event.stopPropagation();
	});
});

function openBox(obj,objBg){//打开弹窗
    if(objBg)objBg.fadeIn().css("z-index",99);
    obj.css({"top":"50%","opacity":1,"z-index":99}).siblings().addClass("hide");
}
function closeBox(obj,objBg){//关闭弹窗
    obj.css({"top":"60%","opacity":0,"z-index":-2}).siblings().removeClass("hide");
    setTimeout(function(){
        if(objBg)objBg.fadeOut().css("z-index",0);
    },300);
}
$("#readLeaveMsg").on("click", function(){
	openBox($("#leaveBox"),$(".layer_bg"));
})
$("#leaveBox").on("click",".close",function(){
	leaveMsgNum();
	closeBox($("#leaveBox"),$(".layer_bg"));
});
//聊天历史记录
function userHistory(username){
	if(username == undefined){
		username = $(".visitor_list").parent().prev().find("span").text();
	}
	var sendTime = $("#historyBox #sendTime").val();
	if(sendTime == ""){
		sendTime = new Date().format("yyyy-MM-dd");
	}
	$.ajax({
		url:ctx+"/oc/jt-user/chatRecord",
		data:{"username":username,"userId":PROS_.uid,"companyId":PROS_.companyID,"page":page-1,"size":pageSize,"sendTime":sendTime},
		type : "POST",
		success:function(datas){
			var data = datas.content;
			if(data == ""){
				$("#historyBox #content").html("当天暂无历史聊天记录.");
			}else{
				var html=" <div class='visitor_his'> ";
				for(var i=0;i<data.length;i++){
					if(data[i].msgType){//图片
						 if(curUserAccount == data[i].fromUser){
							 html += "<dl class='customer'> "+
					           "         <dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd><img style=\"width: 100%;\" class=\"view\" src='"+data[i].msg+"'/></dd> "+
					            "    </dl> ";
						 }else{
							 html += "<dl class='visitor'> "+
					           "         <dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd><img style=\"width: 100%;\" class=\"view\" src='"+data[i].msg+"'/></dd> "+
					            "    </dl> ";
						 }
					} else {
						 if(curUserAccount == data[i].fromUser){
							 html += "<dl class='customer'> "+
					           "         <dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd>"+(data[i].msg.indexOf("em_") > 0 ? replace_em(data[i].msg) : data[i].msg)+"</dd> "+
					            "    </dl> ";
						 }else{
							 html += "<dl class='visitor'> "+
					           "         <dt>"+data[i].fromUser+"&nbsp;&nbsp;"+new Date(data[i].sendTime).format("MM-dd hh:mm:ss")+"</dt> "+
					           "         <dd>"+(data[i].msg.indexOf("em_") > 0 ? replace_em(data[i].msg) : data[i].msg)+"</dd> "+
					            "    </dl> ";
						 }
					}
			          
				}
			   html+= " </div> ";
					$("#historyBox #content").html(html);
			}
			showPage(datas.totalElements, page, "#historyBox");
		}
	});
}
pageWhitch("#historyBox",userHistory);
//点击图片放大
picUpDown("#historyBox", "img.view");
