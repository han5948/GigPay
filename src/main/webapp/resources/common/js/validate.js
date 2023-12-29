var fnChkEmptyForm = function(frm){
	var isCheck = true;
	var reg_num = /^[+-]?\d*(\.?\d*)$/;                                             // 숫자 검사식
	var reg_email = /^([\w\.-]+)@([a-z\d\.-]+)\.([a-z\.]{2,6})$/;                   // 이메일 검사식
	var reg_tel = /^\d{10,11}$/;                                        // 전화번호 검사식
	
	$('#'+ frm +' .notEmpty').each(function() {
		
		var type = $(this).prop('type');
		var val = $(this).val();
		val = val.replace(/\s/gi,""); //공백 제거
		var title = $(this).attr('title');
		
		if ( isCheck && (type == 'text' || type == 'password' || type == 'textarea' || type == 'tel') ) {
			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 입력해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && type == 'file' ) {
			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 파일을 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && (type == 'checkbox' || type == 'radio') ) {
			var name = $(this).prop('name');
			val = $('input[name="'+ name +'"]:checked').val();

			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && (type == 'select-one' || type == 'select-multiple') ) {
			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		}
	});
	
	// 속성 체크 - 숫자
	$('#'+ frm +' .num').each(function() {
		var val = $(this).val();
		
		if ( isCheck && !(val == undefined || val == '' || val == '.') && !reg_num.test(val) ) {
			toastW('[숫자 입력 오류] 숫자만 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});
	
	// 속성 체크 - 이메일
	$('#'+ frm +' .email').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_email.test(val) ) {
			toastW('[이메일 입력 오류] 유효한 이메일을 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});
	
	// 속성 체크 - 전화번호
	$('#'+ frm +' .tel').each(function() {
		var val = $(this).val();
		if ( isCheck && !(val == undefined || val == '') && !reg_tel.test(val) ) {
			toastW('[전화번호 입력 오류] 유효한 전화번호를 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});
	return isCheck;
}

// 입력 폼 필수 값 체크
var fnChkValidForm = function(frm) {
	var isCheck = true;

	// 유효성 체크 정규식
//	var reg_num = /^[0-9]+$/;                                                       // 숫자 검사식
	var reg_num = /^[+-]?\d*(\.?\d*)$/;                                             // 숫자 검사식
	var reg_kor = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|0-9\s]+$/;                                   // 한글 검사식
	var reg_eng = /^[a-z|A-Z|'|0-9\s]+$/;                                           // 영문 검사식
//	var reg_tel = /^[0-9]{3,11}$/;                                                  // 전화번호 검사식
	var reg_tel = /^\d{2,3}-\d{3,4}-\d{4}$/;                                        // 전화번호 검사식
	var reg_email = /^([\w\.-]+)@([a-z\d\.-]+)\.([a-z\.]{2,6})$/;                   // 이메일 검사식
	var reg_url = /^(https?:\/\/)?([a-z\d\.-]+)\.([a-z\.]{2,6})([\/\w\.-]*)*\/?$/;  // URL 검사식

	// 날짜 유효성 검사 정규식
	var reg_date = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;      // 날짜  YYYY-MM-DD
	var reg_time = /^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$/;                       // 시간  HH24:mm (24시간)
	var reg_dt = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])\s([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$/;   // 복합 YYYY-MM-DD HH24:mm (중간 공백)


	// 필수 값 체크
	$('#'+ frm +' .notEmpty').each(function() {
		var type = $(this).prop('type');
		var val = $(this).val();
		var title = $(this).attr('title');
console.log(type);
		if ( isCheck && (type == 'text' || type == 'password' || type == 'textarea' || type == 'tel') ) {
			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 입력해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && type == 'file' ) {
			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 파일을 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && (type == 'checkbox' || type == 'radio') ) {
			var name = $(this).prop('name');
			val = $('input[name="'+ name +'"]:checked').val();

			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && (type == 'select-one' || type == 'select-multiple') ) {
			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
/*
		} else {
			var tag = $(this).prop('tagName');

			console.log('tag ['+ tag +']');
*/
		}
	});

	// 속성 체크 - 숫자
	$('#'+ frm +' .num').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '' || val == '.') && !reg_num.test(val) ) {
			toastW('[숫자 입력 오류] 숫자만 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});	

	// 속성 체크 - 한글
	$('#'+ frm +' .kor').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_kor.test(val) ) {
			toastW('[한글 입력 오류] 한글만 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 영문
	$('#'+ frm +' .eng').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_eng.test(val) ) {
			toastW('[영문 입력 오류] 영문만 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 전화번호
	$('#'+ frm +' .tel').each(function() {
		var val = $(this).val();
		
		val = phoneNumHypen(val);
		
		if ( isCheck && !(val == undefined || val == '') && !reg_tel.test(val) ) {
			toastW('[전화번호 입력 오류] 유효한 전화번호를 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 이메일
	$('#'+ frm +' .email').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_email.test(val) ) {
			toastW('[이메일 입력 오류] 유효한 이메일을 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - URL
	$('#'+ frm +' .url').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_url.test(val) ) {
			toastW('[URL 입력 오류] 유효한 URL을 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 날짜
	$('#'+ frm +' .date').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_date.test(val) ) {
			toastW('[날짜 입력 오류] "YYYY-MM-DD"(2018-01-01) 형식으로 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 시간
	$('#'+ frm +' .time').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_time.test(val) ) {
			toastW('[시간 입력 오류] "HH24:mm"(24시간, 17:35) 형식으로 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 날짜 + 시간
	$('#'+ frm +' .datetime').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_dt.test(val) ) {
			toastW('[날짜 입력 오류] "YYYY-MM-DD HH24:mm"(2018-01-01 17:35) 형식으로 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	return isCheck;
	
}

var fnChkValid = function() {
	var isCheck = true;

	// 유효성 체크 정규식
//	var reg_num = /^[0-9]+$/;                                                       // 숫자 검사식
	var reg_num = /^[+-]?\d*(\.?\d*)$/;                                             // 숫자 검사식
	var reg_kor = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|0-9\s]+$/;                                   // 한글 검사식
	var reg_eng = /^[a-z|A-Z|'|0-9\s]+$/;                                           // 영문 검사식
//	var reg_tel = /^[0-9]{3,11}$/;                                                  // 전화번호 검사식
	var reg_tel = /^\d{2,3}-\d{3,4}-\d{4}$/;                                        // 전화번호 검사식
	var reg_email = /^([\w\.-]+)@([a-z\d\.-]+)\.([a-z\.]{2,6})$/;                   // 이메일 검사식
	var reg_url = /^(https?:\/\/)?([a-z\d\.-]+)\.([a-z\.]{2,6})([\/\w\.-]*)*\/?$/;  // URL 검사식

	// 날짜 유효성 검사 정규식
	var reg_date = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;      // 날짜  YYYY-MM-DD
	var reg_time = /^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$/;                       // 시간  HH24:mm (24시간)
	var reg_dt = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])\s([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$/;   // 복합 YYYY-MM-DD HH24:mm (중간 공백)


	// 필수 값 체크
	$('.notEmpty').each(function() {
		var type = $(this).prop('type');
		var val = $(this).val();
		var title = $(this).attr('title');

		if ( isCheck && (type == 'text' || type == 'password' || type == 'textarea' || type == 'tel') ) {
			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 입력해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && (type == 'checkbox' || type == 'radio') ) {
			var name = $(this).prop('name');
			val = $('input[name="'+ name +'"]:checked').val();

			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && (type == 'select-one' || type == 'select-multiple') ) {
			if ( val == undefined || val == '' ) {
				toastW(title +"을(를) 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
/*
		} else {
			var tag = $(this).prop('tagName');

			console.log('tag ['+ tag +']');
*/
		}
	});

	// 속성 체크 - 숫자
	$('.num').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '' || val == '.') && !reg_num.test(val) ) {
			toastW('[숫자 입력 오류] 숫자만 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});	

	// 속성 체크 - 한글
	$('.kor').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_kor.test(val) ) {
			toastW('[한글 입력 오류] 한글만 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 영문
	$('.eng').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_eng.test(val) ) {
			toastW('[영문 입력 오류] 영문만 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 전화번호
	$('.tel').each(function() {
		var val = $(this).val();
		val = phoneNumHypen(val);
		if ( isCheck && !(val == undefined || val == '') && !reg_tel.test(val) ) {
			toastW('[전화번호 입력 오류] 유효한 전화번호를 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 이메일
	$('.email').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_email.test(val) ) {
			toastW('[이메일 입력 오류] 유효한 이메일을 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - URL
	$('.url').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_url.test(val) ) {
			toastW('[URL 입력 오류] 유효한 URL을 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 날짜
	$('.date').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_date.test(val) ) {
			toastW('[날짜 입력 오류] "YYYY-MM-DD"(2018-01-01) 형식으로 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 시간
	$('.time').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_time.test(val) ) {
			toastW('[시간 입력 오류] "HH24:mm"(24시간, 17:35) 형식으로 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	// 속성 체크 - 날짜 + 시간
	$('.datetime').each(function() {
		var val = $(this).val();

		if ( isCheck && !(val == undefined || val == '') && !reg_dt.test(val) ) {
			toastW('[날짜 입력 오류] "YYYY-MM-DD HH24:mm"(2018-01-01 17:35) 형식으로 입력해 주세요.');
			$(this).focus();
			isCheck = false;

			return false;
		}
	});

	return isCheck;
};