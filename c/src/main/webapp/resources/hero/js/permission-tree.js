var UITree = function () {
    var permissionControl = function() {

        $("#permission_container").jstree({
            "core": {
                "themes": {
                    "responsive": false
                }, 
                "data": {
                    "url": ctx+"/back/admin/permission/json",
                    "dataType": "json"
                },
                "check_callback": function(operation, node, parent, position, more) {
                    return true;
                }
            },
            "types": {
                "default": {
                    "icon": "fa fa-lock icon-state-info icon-lg"
                },
                "file": {
                    "icon": "fa fa-lock icon-state-info icon-lg"
                }
            },
            "state": { "key": "demo2" },
            "plugins": [ "contextmenu", "state", "types" ],
            "contextmenu": {
                "items": function($node) {
                    var tree = $("#permission_container").jstree(true);
                    var btns = new Object();
                    if($.inArray('create', permissions) > -1) {
                    	btns['create'] = {
                            "label": "新增子权限",
                            "action": function (obj) {
                                var $a = $("#"+$node.id).children("a");
                                $a.attr('href', ctx+'/back/admin/permission/create?parentPermissionId='+$node.id+'&floor='+($node.parents.length+1));
                                $a.attr('data-toggle', 'modal');
                                $a.attr('data-target', '#ajax-modal');
                                $a.trigger('click');
                                $a.removeAttr('data-target').removeAttr('data-toggle');
                                $a.attr('href', '#');
                                $.ajax({
                                	url:ctx+'/back/admin/permission/create?parentPermissionId='+$node.id+'&floor='+($node.parents.length+1),
                                	type:"get",
                                	dataType:"json",
                                	success:function(data){
                                		var d = data.createDTO;
                                		$("#addOnePerm #floor").val(d.floor);
                                		$("#addOnePerm #parentPermissionId").val(d.parentPermissionId);
                                		openBox($("#addOnePerm"),$(".layer_bg"));
                                	}
                                })
                            }
                    	};
                    }
                	if($.inArray('update', permissions) > -1) {
                    	btns['update'] = {
                            "label": "修改权限",
                            "action": function (obj) {
                                var $a = $("#"+$node.id).children("a");
                                $a.attr('href', ctx+'/back/admin/permission/update/'+$node.id);
                                $a.attr('data-toggle', 'modal');
                                $a.attr('data-target', '#ajax-modal');
                                $a.trigger('click');
                                $a.removeAttr('data-target').removeAttr('data-toggle');
                                $a.attr('href', '#');
                                
                                $.ajax({
                                	url:ctx+'/back/admin/permission/update/'+$node.id,
                                	type:"get",
                                	dataType:"json",
                                	success:function(data){
                                		console.log(data)
                                		var d = data.updateDTO;
                                		$("#editOnePerm #floor").val(d.floor);
                                		$("#editOnePerm #parentPermissionId").val(d.parentPermissionId);
                                		$("#editOnePerm #id").val(d.id);
                                		$("#editOnePerm #permissionName").val(d.permissionName);
                                		$("#editOnePerm #path").val(d.path);
                                		$("#editOnePerm #code").val(d.code);
                                		$("#editOnePerm #deep").val(d.deep);
                            			$("#editOnePerm #permissionType").children("option").each(function(){
                            				if($(this).val() == d.permissionType){
                            					$(this).attr("selected","selected");
                            				}
                            			})
                                		openBox($("#editOnePerm"),$(".layer_bg"));
                                	}
                                });
                                
                            }
                    	};
                    }
                	if($.inArray('deletePermission', permissions) > -1) {
                    	btns['remove'] = {
                            "label": "删除权限",
                            "action": function (obj) {
                                var $a = $("#"+$node.id).children("a");
                                $a.attr('href', 'javascript:void(0);');
                                $a.attr('onclick', 'deletePermission('+$node.id+');');
                                $a.trigger('click');
                                $a.removeAttr('onclick');
                                $a.attr('href', '#');
                            }
                        };
                    }
                    	
                    btns['reload'] = {
                        "label": "刷新节点",
                        "action": function (obj) {
                            tree.refresh($node);
                        }
                    };
                    return btns;
                }
            }
        });
    }

    return {
        init: function () {
            permissionControl();
        }
    };
}();

function deletePermission(id) {
	var loadi=0;
  	layer.confirm('确认删除？',{icon: 3, title:'确认删除？',skin: 'layui-layer-lan'},
		function(index){
			layer.close(index);
		 	$.ajax({
				type:"GET",
				url:ctx+"/back/admin/permission/del/" + id,
				data:'',
				async:false,
				beforeSend: function() { loadi = layer.msg('处理中...', {icon: 16,skin: 'layui-layer-lan'}); },
				complete: function() { layer.close(loadi); }, 
				success:function(data){
					if(data.success){
						$("#permission_container").jstree(true).refresh();
					} else{
						layer.alert(data.message,{icon:2,title:'提示',skin: 'layui-layer-lan'});
					}
				}
			}); 
		});
}