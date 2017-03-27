// ================= 使用/禁用蒙层效果 ===========================
	function CoverLayer(tag) {
		with ($('#Over')) {
			if (tag == 1) {
				css('height', $(document).height());
				css('display', 'block');
				css('opacity', 0.9);
				css("background-color", "#000");
			} else {
				css('display', 'none');
			}
		}
	}
	function picUpDown(id, ele){
		$(id).on("click",ele,function(){
			var currImg = $(this);
			CoverLayer(1);
			var TempContainer = $('<div class=TempContainer></div>');
			with (TempContainer) {
				appendTo("body");
				css('top', (currImg.offset().top-90));
				html("<img border=0 src=" + currImg.attr('src') + ">");
			}
			TempContainer.click(function() {
				$(this).remove();
				CoverLayer(0);
			});
		});
	}