$(document).ready( function(){
	gnb.init()	;
	btn.init();		//공통 버튼
	popUp.init();
	positionFunc.init();	//표지설정
	TransitionVisual($("[data-ui-type='visual_rolling']"));//메인비주얼
	//fileFunc.init();	//파일 등록
	tabFunc.init();	//탭 기능

	//달력 플러그인 호출
	$( ".datepicker" ).datepicker({
		monthNames : [".1",".2",".3",".4",".5",".6",".7",".8",".9",".10",".11",".12"],
		dayNamesMin : ["일","월","화","수","목","금","토"],
		dateFormat : "yy-mm-dd"
	});
})


//ajax common
function commonAjax(url, data,sync, successListener, errorListener,
		beforeSendListener, completeListener) {
	
	$.ajax({

		type : "POST",
		url : url,
		data : data,
		async: sync,
		dataType : "json",		
		/*contentType: "application/json",*/
/*		 contentType: "application/json",*/

		beforeSend : function(xhr) {
			xhr.setRequestHeader("AJAX", true);
			$(".wrap-loading").show();
            
			// 요청 전 선작업
			if (null != beforeSendListener) {
				beforeSendListener();
			}
		},
		success : function(data) {
			
			// 성공 시
			successListener(data);
			
		},
		error : function(data) {
			if(data.status == "901"){
				location.href = "/manager";
			}
			
			// 오류 발생
			if (null != errorListener) {
				errorListener(data);
			}
			
		},
		complete : function() {
			// 요청 완료 시
			
			$(".wrap-loading").hide();

			if (null != completeListener) {
		
				completeListener();
			}
			//App.unblockUI();
			
		}
	});

}


var gnb = {
	init : function(){
		this.btn = $(".dept1_btn");
		this.ul = this.btn.parent().parent();

		this.func();
	},

	func : function(){
		var that = this
		this.btn.click( function(){
			that.ul.find("li").removeClass("on");
			$(this).parent().addClass("on");
		})
	}
}
var fileFunc = {
	init: function(){
		this.fileWrap = $(".file_last");
		this.file = $("input[type='file']", this.fileWrap);
		this.txt = $("input[type='text']", this.fileWrap);

		this.func();
	},

	func : function(){
		var that= this;
		this.file.change( function(){
			that.txt.val( $(this).val() );
		})
	}

}
var tabFunc ={
	init : function(){
		this.wrap = $("[data-select='wrap']");
		this.activeBtn = $(".active_btn", this.wrap);
		this.ul = $(">ul", this.wrap);
		this.listBtn = $(">li>a", this.ul);

		//기능 실행
		this.func();
	},
	func : function(){
		var that = this;
		this.activeBtn.click( function(){
			that.wrap.addClass("on");
		})
		this.listBtn.on( {
			mouseenter : function(){
				that.activeBtn.text( $(this).text() );
			},

			click : function(){
				that.activeBtn.text( $(this).text() );
				that.wrap.removeClass("on");
			}
		})
	}
}
var btn = {

	init : function(){
		this.btn01 =  $("[data-ui='btn01']");
		this.btn02 = $(".btn10");

		//湲곕뒫 ?ㅽ뻾
		this.func();
	},

	func : function(){
		this.btn01.click( function(){
			$(this).toggleClass("on")	
		})
		this.btn02.click( function(){
			$(this).toggleClass("on")	
		})
	}
}

var popUp = {

	init : function(){
		//湲곕낯?앹뾽
		this.activeBtn = $("[data-popBtn]");
		this.exBtn = $("[data-popExit]");

		//湲곕뒫 ?ㅽ뻾
		this.func();
	},

	func : function(){
		var that = this;		
		this.activeBtn.click( function(){			
			that.popWrap = $("[data-popWrap=" + this.getAttribute("data-popBtn") + "]");
			that.contentsWrap =$("[data-pop=" + this.getAttribute("data-popBtn") + "]");
			that.popWrap.show();
			that.sizeChk()
		});
		
		this.exBtn.click( function(){		
			that.popWrap.hide();
		})
	},

	sizeChk : function(){
		var contentsWidth = this.contentsWrap.outerWidth();
		var contentsHeight = this.contentsWrap.outerHeight();
		var contentsX = contentsWidth/2;
		var contentsY = contentsHeight/2;
		this.contentsWrap.css({
			
				"margin-top" : -contentsY + "px",
				"margin-left" : -contentsX + "px"
			
		})

	}
}

//메인비주얼
function TransitionVisual(obj){
	function TransitionVisual(obj){
		this.element = $(obj);
	}
	TransitionVisual.prototype.init = function(){
		this.ctrl = $(this.element.attr("data-ctrl"), this.element);
		this.prev = $(".prev", this.ctrl);
		this.stop = $(".stop", this.element);
		this.next = $(".next", this.ctrl);
		this.listwrap = $(this.element.attr("data-list"), this.element);
		this.list = $("li", this.listwrap);
		this.description = $("ul", this.ctrl.parents(".summaries"));
		this.description_list = $("li", this.description);
		this.cnt = 0;
		this.lastCnt = 0;
		this.state = true;
		this.useThumbs = false;
		this.thumbs = $(".tabs", this.element);
		this.thumb = $("li", this.thumbs);
		this.duration = 1000;
		var that = this, auto, delay = 4000, power = "on";
		this.list.eq(0).addClass("on");

		if(this.thumb.size() > 0){
			this.useThumbs = true;
			this.thumb.eq(0).addClass("on");
		}
		if(this.list.size() == 1){
			this.ctrl.remove();
			this.description.addClass("single");
			return false;
		}

		this.list.eq(0).css("z-index", this.list.size() - 1);
		this.description_list.each(function(i){
			if(that.cnt != i){
				$(this).css({"z-index" : that.description_list.size() - i - 1, "opacity" : 0});
			}else{
				$(this).css({"z-index" : that.description_list.size() - 1, "opacity" : 1});
			}
		});

		auto = setInterval(function(){
			if(power == "off") return false;
			that.next.click();
		}, delay);

		this.prev.click(function(){
			if(that.state == false) return false;
			that.state = false;
			if(that.cnt > 0){
				that.cnt--;
			}else{
				that.cnt = that.list.size() - 1;
			}
			that.move();
			return false;
		});
		this.next.click(function(){
			if(that.state == false) return false;
			that.state = false;
			if(that.cnt < that.list.size() - 1){
				that.cnt++;
			}else{
				that.cnt = 0;
			}
			that.move();
			return false;
		});
		this.stop.click(function(){
			if(power == "on"){
				power = "off";
				$(this).addClass("play");
			}else{
				power = "on";
				$(this).removeClass("play");
			}
			return false;
		});
		this.thumb.click(function(){
			if(that.cnt == $(this).index()){
				that.state = true;
				return false;
			}
			that.state = true;
			that.cnt = $(this).index();
			that.move();
			return false;
		});
	}
	TransitionVisual.prototype.move = function(){
		this.useThumbnails();
		var that = this;
		this.list.each(function(i){
			if(that.cnt != i){
				$(this).css("z-index", $(this).css("z-index") - 1);
				$(this).removeClass("on");
			}else{
				$(this).css("z-index", that.list.size() - 1);
				$(this).addClass("on");
			}
		});
		this.description_list.each(function(i){
			if(that.cnt != i){
				$(this).css("z-index", $(this).css("z-index") - 1);
			}else{
				$(this).css("z-index", that.description_list.size() - 1);
			}
		});

		if(this.lastCnt != this.cnt){
			this.description_list.eq(this.lastCnt).animate({
				"opacity" : 0
			},{
				duration : that.duration / 2
			});
		}
		this.description_list.eq(this.cnt).css({"opacity" : 0}).animate({
			"opacity" : 1
		},{
			duration : that.duration / 2
		});
		this.list.eq(this.cnt).css({"opacity" : 0}).animate({
			"opacity" : 1
		},{
			duration : that.duration,
			complete : function(){
				that.state = true;
			}
		});
		this.lastCnt = this.cnt;
	}
	TransitionVisual.prototype.useThumbnails = function(){
		this.thumb.removeClass("on").eq(this.cnt).addClass("on");
	}

	var i = 0, inst;
	for(var i = 0; i < obj.length; i++){
		var inst = new TransitionVisual(obj[i]);
		inst.init();
	}
}

var positionFunc = {
	init : function(){
		this.contentWrap = $("[data-size='contents']");
		this.positionBtn = $("[data-position='wrap'] li a");
	
		//기능 실행
		this.positionChk();
	},

	positionChk : function(){
		var that = this;
		this.positionBtn.click( function(){			
			var rowPoint = this.getAttribute("data-row");
			var colPoint = this.getAttribute("data-col");
			var positionPoint = this.getAttribute("data-position");
				
			if( that.contentWrap.attr("class") ){
				that.contentWrap.removeClass()
			}
			that.contentWrap.addClass(rowPoint);
			that.contentWrap.addClass(colPoint);
			
		})
	}

}