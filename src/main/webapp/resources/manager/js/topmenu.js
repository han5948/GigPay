var mnOvNum = [];
var mainNavi_H = {
	mnObj : null,Timer:null,subTimer:null,isOver:false,initNum:[],
	_getMenu:function(){return $("#mainNavi ");	},
	_init:function(){
		var this_s = this;
		this.mnObj =this._getMenu();
		this.setBodyContainer();
		$("li",$(this.mnObj)).each(function(){
			var sLI = $("ul",$(this));
			if(sLI.length>0){
				if($(this).find(" > .tgl-btn").length<1){ 		$(this).prepend("<button class='tgl-btn' type='button'>ToggleMenu</button>");		}
				$(this).addClass("has-sub");
				$(this).addClass("is-close");
				if($(this).hasClass("over")){
					if(this_s.isOpen)	this_s.openSubMenu($(this));
				}
			}

		});
		this._initOvNum();
		this.setEvt();
	},
	
	_set:function(){},
	_unset:function(){
		this.closeMenu();
	},
	_retset:function(){
		this.setEvt();
	},
	isOpen:function(){
		return this.mnObj.hasClass("mn-h-open");
	},
	resetMenuStyle:function(){

	var chkW = $("#header.div-cont").eq(0).width();
	if(chkW > 1000) chkW = 1000;
	var tmpItems = $(".mn_l1",$(this.mnObj)) ;

	var totalW =parseInt($("#mainNavi").css("max-width"));
	if($("#mainNavi").css("max-width")=="none" || totalW < chkW) totalW = chkW;
	tmpItems.css({"width":"auto"});
	var mnW = Math.floor(totalW/tmpItems.length);
	var tmpW = 0;

	$(tmpItems).each(function(){

		if($(this).index()==(tmpItems.length-1)){
		}else{
		}
		tmpW = tmpW + mnW;
	});

	},
	resetMenuBar:function(){
		if(this.currentSeq==undefined){
			var toNum = this.initNum[0];
		}else{
			var toNum = this.currentSeq;
		}


		this.setMenuBar(toNum);
	},
	setMenuBar:function(n){
		var thisMenu = (n>0)? $(".mn_l1:eq(" + ( n-1 ) +")", this.mnObj):null;
		var otherMenu = $(".mn_l1", this.mnObj).not(thisMenu);
		if(this.ovbar!=undefined){
			var toLeft =   (thisMenu!=null)? thisMenu.offset().left  - $(this.mnObj).offset().left : 0 ;

			this.ovbar.stop().animate({

				width: (thisMenu!=null)? thisMenu.width()  - 0 : 0,
				left:toLeft
			},300); 
		}
		
		
	},
	initDefaultStyle:function(){
		$(".mn_l1",$(this.mnObj)).css({"width":"100%","left":"","right":"","top":""});
		$(".depth2-wrap" ,this.mnObj).css({"height":"auto","visibility":"visible","opacity":1});
	},
	clearEvt:function(){
		//$("a",$(this.mnObj)).unbind("mousedown mouseover focus click mouseout blur");
		$(".depth2-wrap",$(this.mnObj)).unbind("mousedown mouseover click focus mouseout blur");
		$("> li > .depth2-wrap",$(this.mnObj)).unbind("mousedown mouseover click focus mouseout blur");
		$(".depth2-wrap",$(this.mnObj)).stop().css({"padding-top":"0px","padding-bottom":"0px","height":"auto","left":"","top":"","visibility":"visible","opacity":1});
		$(".depth2-wrap",$(this.mnObj)).each(function(){ $(this).stop().hide();});

	},
	setEvt:function(){
		var this_s = this;
		this.clearEvt();
		$("a",$(this.mnObj)).bind("focus", function(){	 
			this_s.isOver = true;	
			if(!this_s.isOpen()){this_s.openMenu();	}
		});

		$(".depth2-wrap",$(this.mnObj)).bind("focus", function(){	 this_s.isOver = true;	});
		
		$("a",$(this.mnObj)).bind("blur", function(){	this_s.setMenuOut();	});
		$(".depth2-wrap",$(this.mnObj)).bind("blur", function(){	this_s.setMenuOut();	});

		this.initDefaultStyle();

		

		$("a",$(this.mnObj)).bind("click", function(){	

			if($(this).parent().hasClass("has-sub")){

				if($(this).parent().hasClass("is-open")){
					return true;
				}else{
					this_s.openSubMenu($(this).parent());
				}
				
				var selLI = $(this).parent();
				var pUL = $(this).parent().parent();
				$(" > li.has-sub",pUL).not(selLI).each(function(){
					this_s.closeSubMenu($(this));
				});
				return false;
			}
			
		});
		$(".tgl-btn",$(this.mnObj)).unbind("click").bind("click", function(){	

			this_s.toggleSubMenu($(this).parent());
		});
		 $("a,button",$(this.mnObj)).on("focus",function(){
			 var $li = $(this).parents("li").get(0);
			 var seq =  $($(this).parents("li").get(0)).index() + 1;
				if(!_isMobile_) {
					if($wbr.browser=="Chrome"){

					}
					this_s.setMenuListOn($li);
				}
				else{
					
				}
		 });
		this.setMenuOn(this.initNum[0]);
	},
	toggleMenu:function(){

		
		if(this.isOpen()){
			this.closeMenu();
		}else{
		
			this.openMenu();
		}

	},
	openMenu:function(){
		var this_s = this;
		
		var mnObj = this._getMenu()
		if(!mnObj.hasClass("mn-h-open")){
			mnObj.addClass("mn-h-open");
		}

		var $back = $(".header-ovclick");
		$back.unbind("click").bind("click",function(){			this_s.closeMenu();		}).show();

		$("body").css({"overflow-y":"hidden"});
		$(".body-slide-wr").css({"margin-left":0});
		if(wsize.win.w > 100){
			$(".body-slide-wr").css({"margin-left":0});
			$back.css({"left":0});
		}
		

		
	},
	closeMenu:function(){
		var mnObj = this._getMenu();
		
		if(mnObj.hasClass("mn-h-open")){
			mnObj.removeClass("mn-h-open");
		}
		$(".header-ovclick").css({"left":"0"}).hide();
		
		$("body").css({"overflow-y":"visible"});
		$(".body-slide-wr").css({"margin-left":0});
		$(".header-ovclick").css({"left":"0"});

		if(this.mnType=="H") this.setMenuOut();

	},

	setBodyContainer:function(){
		if(typeof(this.setBodyCont)=="undefind" || this.setBodyCont !=true ){
			this.setBodyCont = true;
			if($(".header-ovclick").length<1) $("<div class='header-ovclick'/>").appendTo($("#header")).hide();
			if($(".doc-pg").find(".body-slide-wr")==false){
				$(".doc-pg").stop().wrapInner("<div class='body-slide-wr'><div class='body-slide'></div></div>");
			}
		}
	},
	
	clearTimer:function(){
		try{clearTimeout(this.Timer);clearTimeout(this.subTimer);}catch(e){}
	},
	_initOvNum:function(initNum){
		
		if(initNum!=undefined) this.initNum = initNum;
		else{
			this.initNum[0] =( $(".mn_l1.over",this.mnObj).length> 0)? $(".mn_l1.over",this.mnObj).index() + 1 : 0;
			this.initNum[1] =( $(".mn_l2.over",this.mnObj).length> 0)? $(".mn_l2.over",this.mnObj).index() + 1 : 0;
			this.initNum[2] =( $(".mn_l3.over",this.mnObj).length> 0)? $(".mn_l3.over",this.mnObj).index() + 1 : 0;
		}
	},
	setMenuListOn:function(li){
		var pUL = $(li).parent();
	},
	setMenuOn:function(){
		clearTimeout(this.Timer );
		var this_s = this;
		var s  = new Array();

		for(var i=0; i<arguments.length;i++){
			s[i] = arguments[i];	
			var d = i+1;
			
			if(s[i]>0)	{
				var selMn = $(".mn_l"+d,this.mnObj).eq(s[i]-1);

				this_s.openSubMenu(selMn);
				$(".mn_l"+d,this.mnObj).not(selMn).each(function(){					this_s.closeSubMenu(this);				});
				
			}else{
				$(".mn_l"+d,this.mnObj).each(function(){ this_s.closeSubMenu(this); });
				
			}

		}
		
		
		this.currentSeq = s[0];

		
	},
	setMenuOut:function(){
		clearTimeout(this.Timer );
		var this_s = this;
		this.isOver = false;	
		this.Timer = setTimeout(function(){
			if(this_s.isOver==false) {
				this_s.setMenuOn(this_s.initNum[0],this_s.initNum[1],this_s.initNum[2]);
			}
		},400);
	},
	menuOnAction:function(obj){
		var thisParentEl = $($(obj).parents("li").get(0));
			var thisSubObj = $(".depth2-wrap",thisParentEl);
			if(thisSubObj.length>0 && this_s.currentSeq!=obj.seq){
				return false;
			}

					
	},
	
	toggleSubMenu:function(li){
		var this_s = this;

		if($(li).hasClass("is-open")) {
			
			this.closeSubMenu(li);
		}else{
			
			this.openSubMenu(li);
			
			$(">li",$(li).parent()).not(li).each(function(){ this_s.closeSubMenu(this);});
		}
		

	},
	openSubMenu:function(li){
		var this_s = this;
		
		$(li).addClass("is-open");			
		$(li).removeClass("is-close");			
	
		var $div = $(li).find("ul").eq(0).parent();
		$div.stop().show("blind",function(){this_s.setContentHeight(); });
	},
	closeSubMenu:function(li){
		var this_s = this;

		$(li).removeClass("is-open");
		$(li).addClass("is-close");			
		var $div = $(li).find("ul").eq(0).parent();
		$div.stop().hide("blind",function(){this_s.setContentHeight(); });
	},
	setContentHeight:function(){
		this.clearTimer();
		this.subTimer = setTimeout(function(){
			try{setLayoutMinHeight();}catch(e){}
		},300);
	},

	closeDesktop:function(){
		$('.pc-guide').stop().animate({'bottom': -160},600, 'easeInOutQuint');
		mainNavi_H.closeMenu();
		$(".header-ovclick").hide();
		$("body").css({"overflow-y":"auto"});
	},
	openDesktop:function(){
		$('.pc-guide').stop().animate({'bottom': 0},600, 'easeInOutQuint');
		mainNavi_H.closeMenu();
		$("body").css({"overflow":"hidden"});
		var $back = $(".header-ovclick");
		$back.unbind("click").bind("click",function(){	mainNavi_H.closeDesktop();}).show();
	}
}
var mainNavi = {
	mnObj : null,
	initNum:Array(),	currNum:Array(), Timer:null, mnType:"",
	_init:function(mn,initNum){
		var this_s = this;
		this.mnObj = $("#mainNavi");

		if($(".doc-pg").find(".body-slide-wr").length<1){
			$(".doc-pg").wrapInner("<div class='body-slide-wr'><div class='body-slide'></div></div>");
		}
			
		$(".body-slide-wr").css({"overflow":"hidden"});	

		this.checkMenuType();
		this.setMenu();

		var mnToggleBtn = $("#mn-ctrs-btns");
		var seToggleBtn = $("#tm05 > a.mn_a1");

		$(".mn-close-btn").off("click").click(function(){
			if(this_s.mnType!="H") return;
			mainNavi_H.closeMenu();
		});

		$(".pc-guide").find('.close').off("click").click(function(){
			if(this_s.mnType!="H") return;
			mainNavi_H.closeDesktop();
		});

		//버튼 초기화
		mnToggleBtn.off("click").on("click",function(){

			if(this_s.mnType!="H") return true;
			mainNavi_H.toggleMenu();
			return false;
		
		});
		//버튼 초기화
		seToggleBtn.off("click").on("click",function(){
			//if(this_s.mnType!="H") return;
			if(this_s.mnType!="H") { return;}
			mainNavi_H.openDesktop();
			return false;

		});


		$(".bt-mnclose").off("click").on("click",function(){

			if(this_s.mnType!="H") return;
			mainNavi_H.closeMenu();
			return false;
		
		});

	
		
	},
	_resize:function(){
		if(this.mnType!="H"){
			mainNavi_H.resetMenuStyle();
			mainNavi_H.resetMenuBar();
		}
	},
	_reset:function(){

		this.resetMenu();

	},
	setMenu:function(){
		if(this.mnType=="H"){
			mainNavi_H._init();
		}else{
			mainNavi_H._unset();		
		}
		
	},
	resetMenu:function(){
		var orgMnType = this.mnType;

		this.checkMenuType();	

		this._resize();
		
		if(orgMnType!=this.mnType){
			this.setMenu();
		}
	},
	checkMenuType:function(){

		getWindowSize();

		var chkWinW = wsize.win.w;
		var chkContW = $("body").width();

		if(chkContW > 100){
			this.mnType = "H";
		}
	},
	clearTimer:function(){
	}
}
function initNavigation() {
	$(document).ready(function(){	
		mainNavi._init(); 
	});
}

