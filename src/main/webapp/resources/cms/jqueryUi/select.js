jQuery(function($){
	
	// Common
	var select_root = $('div.select');
	var select_value = $('.myValue');
	var select_a = $('div.select>ul>li>a');
	var select_input = $('div.select>ul>li>input[type=radio]');
	var select_label = $('div.select>ul>li>label');
	
	// Radio Default Value
	$('div.myValue').each(function(){
		var default_value = $(this).next('.iList').find('input[checked]').next('label').text();
		$(this).append(default_value);
	});
	
	// Line
	select_value.bind('focusin',function(){$(this).addClass('outLine');});
	select_value.bind('focusout',function(){$(this).removeClass('outLine');});
	select_input.bind('focusin',function(){$(this).parents('div.select').children('div.myValue').addClass('outLine');});
	select_input.bind('focusout',function(){$(this).parents('div.select').children('div.myValue').removeClass('outLine');});
	
	// Show
	function show_option(){
		$(this).parents('div.select:first').toggleClass('open');
	}
	
	// Hover
	function i_hover(){
		$(this).parents('ul:first').children('li').removeClass('hover');
		$(this).parents('li:first').toggleClass('hover');
	}
	
	// Hide
	function hide_option(){
		var t = $(this);
		setTimeout(function(){
			t.parents('div.select:first').removeClass('open');
		}, 1);
	}
	
	// Set Input
	function set_label(){
		var v = $(this).next('label').text();
		$(this).parents('ul:first').prev('.myValue').text('').append(v);
		$(this).parents('ul:first').prev('.myValue').addClass('selected');
	}
	
	// Set Anchor
	function set_anchor(){
		var v = $(this).text();
		$(this).parents('ul:first').prev('.myValue').text('').append(v);
		$(this).parents('ul:first').prev('.myValue').addClass('selected');
	}

	// Anchor Focus Out
	$('*:not("div.select a")').focus(function(){
		$('.aList').parent('.select').removeClass('open');
	});
	
	select_value.click(show_option);
	select_root.removeClass('open');
	select_root.mouseleave(function(){$(this).removeClass('open');});
	select_a.click(set_anchor).click(hide_option).focus(i_hover).hover(i_hover);
	select_input.change(set_label).focus(set_label);
	select_label.hover(i_hover).click(hide_option);
	
	// Form Reset
	$('input[type="reset"], button[type="reset"]').click(function(){
		$(this).parents('form:first').find('.myValue').each(function(){
			var origin = $(this).next('ul:first').find('li:first label').text();
			$(this).text(origin).removeClass('selected');
		});
	});
	
});



//인풋박스값 초기화 - 텍스트/이미지
$.fn.placeholder = function( _options ){
	return this.each(function(){
		(_options.type == "text") ? text(this, _options.explain) : background( this );
	});

	function text( scope, str ){
		var ele = scope, $ele = $(scope);
		if( ele.value !== str ) $ele.removeClass("placeholder");

		$ele.bind("focus", function(){
			$ele.addClass("placeholder-focus");
			$ele.removeClass("placeholder");
			if( ele.value === str ) ele.value = "";
		})
		$ele.bind("blur", function(){
			$ele.removeClass("placeholder-focus");
			if( ele.value === "" ){
				$ele.addClass("placeholder");
				ele.value = str;
			}
		});
	}

	function background( scope ){
		var ele = scope, $ele = $(scope);
		if( ! ele.value=="" ){
			$(ele).removeClass("placeholder");
			ele.style.backgroundImage = "none";
		}
		$ele.bind("focus", function(){
			$ele.addClass("placeholder-focus");
			$ele.removeClass("placeholder");
			ele.style.backgroundImage = "none";
			
		}).bind("blur", function(){
			$ele.removeClass("placeholder-focus");
			if( ele.value === "" ){
				$ele.addClass("placeholder");
				ele.style.backgroundImage = "";
			}
		});	
	}
	
};


	$(function(){
		$('#snbMenu ul').hide();
		$('#snbMenu ul').children('.current').parent().show();

        var lastEvent = null;
        var slide  = "#snbMenu > li > ul";
        var alink  = "#snbMenu > li > a";
   
    	function menuAction(){
    	    if (this == lastEvent) return false;
    		lastEvent = this;
    		setTimeout(function() {lastEvent = null}, 200);
    		
    		if ($(this).attr('class') != 'active') {
    			$(slide).slideUp();
    			$(this).next(slide).slideDown();
    			$(alink).removeClass('active');
                $(this).addClass('active');
    		} else if ($(this).next(slide).is(':hidden')) {
    			$(slide).slideUp();
    			$(this).next(slide).slideDown();
    		} else {
    			$(this).next(slide).slideUp();
    		}
    		
    	}
    	
    	$(alink).click(menuAction).focus(menuAction);

     }); 
	
	
	$().ready(function(){
		$('.styled').customSelect();
		$('.styled02').customSelect();
		$('.styled03').customSelect();
	});
		
	

	/* --------------------------------------------------------
	ie, type, device check
	-------------------------------------------------------- */
	jQuery(function($) {
		$.fn.extend({
			inputJo : function(opt){
				var defaults = {
					objSelect : 0
				};
				var opt = $.extend(defaults, opt);
				
				return this.each(function(){
					var $this 		= $(this),
						type		= $this.attr('type'),
						inputId 	= $this.attr('id'),
						inputName 	= $this.attr('name'),
						$label		= $('label[for=' + inputId + ']'),
						$inputName	= $('.' + inputName),
						objSelect	= opt.objSelect;
					
					if(!inputName){ inputName = ''; }
					
					var app = {
						init : function(){
							$label = $('label[for=' + inputId + ']');
							$label.addClass('label-' + type+ ' ' + inputName);
							$this.data('objSelect', objSelect);
							
							if($this.prop('checked') === true){
								$label.addClass('on');
							}
							if($this.prop('disabled') === true){
								$label.addClass('off');
							}
						},
						objChecked : function(){
							if(returnBool && inputName == 'worker_distance') {
								if(returnBool == 1) {
									return false;
								}
							}
							
							$label		= $('label[for=' + inputId + ']');
							if(inputName){ $inputName	= $('.' + inputName); }

							switch(type){
								case 'radio' :
									$inputName.removeClass('on');
									$label.addClass('on');
									break;
								case 'checkbox' :
									var allCheck = $this.val();
									
									if(allCheck === 'allCheck'){
										if($this.is(':checked')){
											$('ipnut[name="' + inputName + '"][disabled!=disabled]').prop('checked', true);
											if(inputName){ $inputName.addClass('on'); }
										} else {
											$('ipnut[name="' + inputName + '"][disabled!=disabled]').prop('checked', false);
											if(inputName){ $inputName.removeClass('on'); }
										}
									} else {
										if($label.hasClass('on')){
											$label.removeClass('on');
										} else {
											$label.addClass('on');
										}
									}
									break;
								//no default
							}
						},
						checkLimit : function(){
							if(returnBool && inputName == 'worker_distance') {
								if(returnBool == 1) {
									return false;
								}
							}
							
							if(objSelect > 0){
								$inputName = $('.' + inputName);
								if($this.data('objSelect') === $('input[name="' + inputName + '"]:checked').length){
									$('input[name="' + inputName + '"]:not(:checked)').prop('disabled','disabled');
									$inputName.addClass('off');
									$('.' + inputName + '.on').removeClass('off');
								} else {
									$('input[name="' + inputName + '"]').removeProp('disabled');
									$inputName.removeClass('off');
								}
							}
						},
						eventHandle : function(){
							$this.off().on({
								click : function(){
									$this.addClass('focus');
									app.objChecked();
									app.checkLimit();
								},
								focus : function(){
									$label.addClass('focus');
									$this.addClass('focus');
								},
								blur : function(){
									$this.removeClass('focus');
									$('.label-radio, .label-checkbox').removeClass('focus');
								}
							});
						}
					};
					app.init();
					app.checkLimit();
					app.eventHandle();
				});
			}
			
		});
		/* --------------------------------------------------------
		document ready
		-------------------------------------------------------- */
		$(document).ready(function(){
			$('input.inputJo').inputJo();
			
		});	
		
	});
	
	
	
	(function(a){a.fn.extend({customSelect:function(c){if(typeof document.body.style.maxHeight==="undefined"){return this}var e={customClass:"customSelect",mapClass:true,mapStyle:true},c=a.extend(e,c),d=c.customClass,f=function(h,k){var g=h.find(":selected"),j=k.children(":first"),i=g.html()||"&nbsp;";j.html(i);if(g.attr("disabled")){k.addClass(b("DisabledOption"))}else{k.removeClass(b("DisabledOption"))}setTimeout(function(){k.removeClass(b("Open"));a(document).off("mouseup."+b("Open"))},60)},b=function(g){return d+g};return this.each(function(){var g=a(this),i=a("<span />").addClass(b("Inner")),h=a("<span />");g.after(h.append(i));h.addClass(d);if(c.mapClass){h.addClass(g.attr("class"))}if(c.mapStyle){h.attr("style",g.attr("style"))}g.addClass("hasCustomSelect").on("update",function(){f(g,h);var k=parseInt(g.outerWidth(),10)-(parseInt(h.outerWidth(),10)-parseInt(h.width(),10));h.css({display:"inline-block"});var j=h.outerHeight();if(g.attr("disabled")){h.addClass(b("Disabled"))}else{h.removeClass(b("Disabled"))}i.css({width:k,display:"inline-block"});g.css({"-webkit-appearance":"menulist-button",width:h.outerWidth(),position:"absolute",opacity:0,height:j,fontSize:h.css("font-size")})}).on("change",function(){h.addClass(b("Changed"));f(g,h)}).on("keyup",function(j){if(!h.hasClass(b("Open"))){g.blur();g.focus()}else{if(j.which==13||j.which==27){f(g,h)}}}).on("mousedown",function(j){h.removeClass(b("Changed"))}).on("mouseup",function(j){if(!h.hasClass(b("Open"))){if(a("."+b("Open")).not(h).length>0&&typeof InstallTrigger!=="undefined"){g.focus()}else{h.addClass(b("Open"));j.stopPropagation();a(document).one("mouseup."+b("Open"),function(k){if(k.target!=g.get(0)&&a.inArray(k.target,g.find("*").get())<0){g.blur()}else{f(g,h)}})}}}).focus(function(){h.removeClass(b("Changed")).addClass(b("Focus"))}).blur(function(){h.removeClass(b("Focus")+" "+b("Open"))}).hover(function(){h.addClass(b("Hover"))},function(){h.removeClass(b("Hover"))}).trigger("update")})}})})(jQuery);

	
	
//파일첨부 //
$(document).ready(function() {
	$('body').on('change', ".filebox .upload-hidden", function() {
//console.log('before ['+ $(this).siblings('.upload-name').val() +']');
	 	if ( window.FileReader ) {
	 		var filename = $(this)[0].files[0].name;
		} else {
			var filename = $(this).val().split('/').pop().split('\\').pop();
    }
//console.log('filename ['+ filename +']');
	 	$(this).siblings('.upload-name').val(filename);
//console.log('after ['+ $(this).siblings('.upload-name').val() +']');
	});

//카드형 리스트 추가버튼 및 삭제//

	// $('.block-add > a.add').click(function() {
	// 	var card = $('.card');
	// 	card.find(".upload-name").text("");
	// 	var ch = 0;
	// 	$('#card-list .card').each(function(i, v) {
	// 		if (i == 15) {
	// 			ch = 99;
	// 		} else {
	// 			ch = i
	// 		}
	// 	});//추가
	// 	if (ch == 99) {
	// 		alert("더이상 추가가 불가능 합니다(최대16개)");
	// 	} else {
	// 		$('#card-list').append(card.eq(0).clone(true).each(function(){
	// 			$(this).find('input').val("");
	// 		})
	// 		);
			
	// 		$("#card-list li").each(function(index) {
	// 			$(this).find("label").attr("for","ex_card"+index);
	// 			$(this).find("input[type='file']").attr("id","ex_card"+index);
	// 		});
	// 	}
	// })
	
//삭제
	$('body').on("click",'.btn-module a.row_del',function() {
		var listLength = $('#card-list li').length;

		if(listLength > 1) {
			$(this).parents("li").addClass('on');
			$('#card-list li.on').remove();
		} else {
			$(this).parents("li").addClass('on');
			$('#card-list li.on').remove();
			
			$("<li class='card'>").append(
				$("<div></div>", {"class":"btn-module filebox floatL"}).append(
					$("<input />", {"class":"upload-name wid300 mgRS", "disabled":"disabled"})
					,$("<label />").html("파일 첨부")
					,$("<input />", {"class":"upload-hidden", "type":"file"})
					
				),
				$("<div></div>", {"class":"btn-module"}).append(
					$("<a />", {"href":"#none", "class":"btnStyle13 row_del"}).text("삭제")
				)
			).appendTo("#card-list");


			$("#card-list li").each(function(index) {
							$(this).find("label").attr("for","ex_card"+index);
							$(this).find("input[type='file']").attr("id","ex_card"+index);
						});
		}
		$('#add_Question li.on').remove();
		$('#subjective-item li.on').remove();
	})
	$('body').on("click", '.btn-module a.poll_del', function() {
		$(this).parents("li").addClass('poll_on');
		$('#poll-area li.poll_on').remove();
	})
	
//설문조사 문항추가 //	
	
	// $('.block-add > a.add').bind('click', function(){
	// 	var question = $('#add_Question li:eq(2)').clone(true); 
	// 	question.find(".btn-module").css('display','inline-block'); //버튼 보이기
	// 	var ch = 0;
	// 	$('#add_Question .question').each(function(i, v) {
	// 		if (i == 19) {
	// 			ch = 99;
	// 		} else {
	// 			ch = i
	// 		}
	// 	});//추가
	// 	if (ch == 99) {
	// 		alert("더이상 추가가 불가능 합니다(최대20개)");
	// 	} else {
	// 	//붙여 넣을 곳
	// 		$('#add_Question').append(question);
	// 	}
	// })
	// var poll = $('.poll');
	// $('.poll-add > a.add').bind('click', function(){
	// 	$('#poll-area').append(poll.eq(0).clone());
	// });
	
	// var subjec = $('#subjective-item');
	// $('.block-add > a.sub-add').bind('click', function(){
	// 	subjec.find(".subjec-input").css('display','block'); 
	// });
	

	//알림 리스트 마우스 오버 //

		var alarmBox = $('.alarm-list li');
		alarmBox.bind({
			mouseover:function(){
				var tg = $(this);  // this를 통해서 event를 발생한 엘리먼트에 접근가능하다.
				tg.removeClass().addClass('alarm-over');
			},
			mouseout:function(){
				var tg = $(this);
				tg.removeClass().addClass('alarm-out');
			}
		});
		
	//알림 layer popup Show Hide //	
		$(".userInfo > a.Alarm").click(function(){
	        $("#alarm-popup-layour").slideDown();
	    });

		$('.layer-close-bg').click(function(){
			$("#alarm-popup-layour").slideUp();
		});
		
});