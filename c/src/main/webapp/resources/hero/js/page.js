var page = 1; //当前页
var pageAll = 1;//总页数
var pageSize = 10;//每页显示条数
//分页方法
function showPage(total,now,id){
    var _LENGTH = 5;//最大页数5
    var pageArr = [];//
    
    //整页
    if(total%pageSize==0){
        pageAll = total/pageSize;
    }else{//非整页
        pageAll = (total - total%pageSize)/pageSize + 1;
    }
    
    //总页数小于5页
    if(pageAll<=_LENGTH){
        for(var i = 0 ;i < pageAll; i++){
            pageArr[i] = i+1;
        }
        
    }else if(pageAll > _LENGTH){//总页数大于5页
        if(now+2 <= pageAll&&now-2 > 0){//当前页没有超过总页数
            for(var i = 0 ;i < _LENGTH; i++){
                pageArr[i] = now-2 + i;
            }
        }else if(now<=2){
                pageArr = [1,2,3,4,5] ;
        }else{
            for(var i = 0 ;i < _LENGTH; i++){
                pageArr[i] = pageAll-4+i;
            }
        }
    }
    //绘制页面
    drawPage(pageArr,now,pageAll,id);    
}
//绘制分页dom方法
function drawPage(pageArr,now,pageAll,id){
    var _html = "";
    if(id == "#visitorBox"){
    	_html = "<span class=\"prev_page\"></span>";
    }
    for(var i = 0; i<pageArr.length; i++){
    	if(parseInt(pageArr[i]) >= 0)
        _html +=  "<a href='javascript:void(0);'>"+pageArr[i]+"</a>";
    }
    _html += "<span class=\"next_page\"></span>";
    $(id+" .page_num").html(_html);
    if(id == "#visitorBox"){
    	$(id+" .page_num > a").hide();
    }
    
    var index = getIndex(pageArr,now);
    $(id+" .page_num > a").eq(index).addClass("cur");
    
//    $(".pagenow").text(now+"/"+pageAll);
    
}

//获取点击当前页坐标
function getIndex(pageArr,now){
    var index = 0;
    for (var i = 0 ; i < pageArr.length; i++){
        if(pageArr[i] == now ){
            index = i;
        }
    }
    return index;
}
//点击分页
function pageWhitch(pageId, pageFunc){
	$(pageId+" .page_num").on("click","a",function(){
	    var text = $(this).text();//获取当前点击页数
	    page = parseInt(text);//将获取的页数转化成数字    
	    pageFunc();
	});
	$(pageId+" .page_num").on("click","span.next_page",function(){
	    if(page<pageAll){
	        page++;
	    }else{
	        layer.msg("已经是最后一页");
	    }
	    pageFunc();
	});
	$(pageId+" .page_num").on("click","span.prev_page",function(){
		if(page>1){
			page--;
		}else{
			layer.msg("已经是第一页");
		}
		pageFunc();
	});
	$(pageId+" .jump_page").on("click","a",function(){
		var to = parseInt($("#toPage").val());
		if(0 < to && to <= pageAll){
			page = to;
			pageFunc();
		}else if(to > pageAll){
			page = pageAll;
			pageFunc();
		}
	})
}
