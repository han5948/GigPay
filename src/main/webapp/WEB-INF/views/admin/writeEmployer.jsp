<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="description" content="ilgaja, 일가자">
<meta name="author" content="NEMODREAM Co., Ltd.">
<link rel="icon"
	href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico"
	mce_href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico"
	type="image/x-icon">

<title>${htmlHeader}-일가자</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/jqueryUi/jquery-ui.css' />"
	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/grid/css/font-awesome.min.css' />"
	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/grid/css/ui.jqgrid.min.css' />"
	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/select2/css/select2.min.css' />"
	media="all" charset="utf-8"></link>

	


<script type="text/javascript"
	src="<c:url value='/resources/cms/grid/js/jquery-1.11.0.min.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/jqueryUi/jquery-ui.min.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/jqueryUi/select.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/select2/js/select2.full.min.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/grid/js/jquery.jqgrid.min.js' />"
	charset="utf-8"></script>
<%-- <script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/i18n/grid.locale-kr.js' />	charset="utf-8"></script> --%>

<script type="text/javascript"
	src="<c:url value='/resources/cms/js/jquery.form.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/js/xl-toast.js' />" charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/js/common.ilgaja.js?ver=1.0' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value="/resources/cms/js/newPaging.js" />" charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/validate.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value="/resources/common/js/common.js" />" charset="utf-8"></script>

<!-- dashboard  -->
<!-- Bootstrap Styles-->
<link href="/resources/bootstrap/assets/css/bootstrap.css" rel="stylesheet" />
<!-- FontAwesome Styles-->
<link href="/resources/bootstrap/assets/css/font-awesome.css" rel="stylesheet" />
<!-- Morris Chart Styles-->
<link href="/resources/bootstrap/assets/js/morris/morris-0.4.3.min.css"	rel="stylesheet" />
<!-- Custom Styles-->
<!-- Google Fonts-->
<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

<link href="/resources/bootstrap/assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
<link href="/resources/bootstrap/assets/css/modal.css" rel="stylesheet" />


	
<link href="/resources/common/css/dashboard.css" rel="stylesheet" />
	
	
<!-- <script src="https://code.jquery.com/jquery-2.2.1.min.js"></script> -->
<script type="text/javascript" src="<c:url value='/resources/cms/js/common.ilgaja.js?ver=1.0' />"	charset="utf-8"></script>
<script src="/resources/bootstrap/assets/js/bootstrap-input-spinner.js"></script>
</head>
<script>
	var ilbo_date = window.opener.document.getElementById("start_ilbo_date").value;
	var regexp = /^[0-9]*$/

	$(document).ready(function() {
		$("input[type='number']").inputSpinner();
		
		$(".input-group").css("display", "flex");
		
		$("#copyManager").on("change", function() {
			if($("#copyManager").is(":checked")) {
				$("#work_manager_name").val($("#manager_name").val());
				$("#work_manager_phone").val($("#manager_phone").val());
			}else {
				$("#work_manager_name").val('');
				$("#work_manager_phone").val('');
			}
		});
		
		$("#manager_phone").on("blur", function() {
			var phoneNum = this.value;

			if(phoneNum == ''){
		        $("#manager_phone_check").val("1");

				return;
			}
			
			var result = validEmployerPhone(phoneNum,null,null);
			
			if ( !result[0] ){
				$(this).val("");
				
			    alert(result[1]);
			}else{
				//폰에서 - 빼기
				var RegNotNum  = /[^0-9]/g;
				phoneNum = phoneNum.replace(RegNotNum,'');
				  
			  	$.ajax({
			        url	: "/admin/chkManagerPhone", 
			        type : "POST", 
			        dataType : "json",
			       	data : { 
			       		term: phoneNum
			       		/* ,srh_use_yn : 1 */
			       	},	//{ term: phoneNum, srh_use_yn: 1, company_seq: company_seq },
				    success: function (data) {
				    	if(!data) {
				            $("#manager_phone_check").val("1");
						}else {
				            $("#manager_phone_check").val("0");
				        }
				    },
			 		beforeSend: function(xhr) {
						xhr.setRequestHeader("AJAX", true);
					},
			      	error: function(e) {
			        	if(data.status == "901" ) {
			            	location.href = "/admin/login";
						}else {
			            	alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
			            }
					}
				});
			}
		});
	});
	
	function fn_insertEmployer() {
		// validate 필요
		if($("#employer_name").val() == '') {
			alert("구인처명을 입력해주세요.");
			
			return false;
		}else if($("#work_name").val() == '') {
			alert("현장명을 입력해주세요.");
			
			return false;
		}else if($("#work_arrival").val() == '') {
			alert("현장 도착시간을 입력해주세요.");
			
			return false;
		}else if($("#work_finish").val() == '') {
			alert("현장 마감시간을 입력해주세요.");
			
			return false;
		}else if($("#work_addr").val() == '') {
			alert("현장 주소를 입력해주세요.");
			
			return false;
		}else if($("#work_addr_comment").val() == '') {
			alert("작업설명을 입력해주세요.");
			
			return false;
		}else if($("#work_age_min").val() == '' || !regexp.test($("#work_age_min").val())) {
			alert("최저나이를 입력해주세요.");
			
			return false;
		}else if($("#work_age").val() == '' || !regexp.test($("#work_age").val())) {
			alert("최고나이를 입력해주세요.");
			
			return false;
		}else if($("#work_blood_pressure").val() == '' || !regexp.test($("#work_blood_pressure").val())) {
			alert("혈압제한을 입력해주세요.");
			
			return false;
		}else if($("#manager_name").val() == '') {
			alert("현장매니저명을 입력해주세요.");
			
			return false;
		}else if($("#manager_phone").val() == '') {
			alert("현장매니저 전화를 입력해주세요.");
			
			return false;
		}else if($("#work_manager_name").val() == '') {
			alert("현장 담당자명을 입력해주세요.");
			
			return false;
		}else if($("#work_manager_phone").val() == '') {
			alert("현장 전화를 입력해주세요.");
			
			return false;
		}else if(!regexp.test($("#work_arrival").val()) || !regexp.test($("#work_finish").val())) {
			alert("현장 도착시간 또는 마감 시간은 숫자 4자리만 입력해주세요.");
			
			return false;
		}
		
// 		if($("#manager_phone_check").val() == '1') {
// 			alert("중복된 현장 매니저 번호입니다.");
			
// 			return false;
// 		}
		
		var _data = {
			// 구인처
			employer_name : $("#employer_name").val(),
			employer_num : $("#employer_num").val(),
			employer_owner : $("#employer_owner").val(),
			employer_business : $("#employer_nusiness").val(),
			employer_sector : $("#employer_sector").val(),
			tax_name : $("#tax_name").val(),
			tax_phone : $("#tax_phone").val(),
			employer_tel : $("#employer_tel").val(),
			employer_feature : $("#employer_feature").text(),
			employer_memo : $("#employer_memo").text(),
			employer_seq : $("#employer_seq").val(),
			
			// 현장
			work_name : $("#work_name").val(),
			work_arrival : $("#work_arrival").val(),
			work_finish : $("#work_finish").val(),
			work_addr : $("#work_addr").val(),
			work_latlng : $("#work_latlng").val(),
			work_breakfast_yn : $("input[name=work_breakfast_yn]:checked").val(),
			work_age_min : $("#work_age_min").val(),
			work_age : $("#work_age").val(),
			work_blood_pressure : $("#work_blood_pressure").val(),
			work_manager_name : $("#work_manager_name").val(),
			work_manager_phone : $("#work_manager_phone").val(),
			work_memo : $("#work_memo").text(),
			
			// 매니저
			manager_name : $("#manager_name").val(),
			manager_phone : $("#manager_phone").val(),
			manager_overlap_flag : $("#manager_phone_check").val(),
			
			// 일보
			ilbo_date : ilbo_date,
			copy_rows : $("#ilbo_cnt").val()
		};
		
		$.ajax({
			type : "POST",
			url : "/admin/insertEmployer",
			data : _data,
			success : function(data) {
				if(data.result == 'success') {
					window.opener.$("#list").trigger("reloadGrid");
					
					window.close();
				}
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error : function(e) {
				if(data.status == "901") {
                	location.href = "/admin/login";
              	}else {
                	alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
              	}
			}
		});
	}
	
	function fn_close() {
		window.close();
	}
	
	function mapWindowOpen(mode) {
		//기본값으로 서울 시청 값을 넣어 준다.
	  	var lat = "";
	  	var lng = "";
	  
	  	window.open("", "popup_window", "width=900, height=1000,  left=20, top=20, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
	  
	  	$("#map_mode").val(mode);
	  	$("#map_lat").val(lat);
	  	$("#map_lng").val(lng);
	  	
	  	$("#mapFrm").attr("target","popup_window").attr("action","/resources/web/popup/ilboMap.jsp").submit();
	}
	
	$(function() {
		$("#employer_name").autocomplete({
			source: function (request, response) {
	        	$.ajax({
					url : "/admin/selectEmployerList", 
					type : "POST", 
					dataType : "json",
	                data : { 
	                	term: request.term, 
	                	srh_use_yn: 1 
	                },
	                success	: function (data) {
	                	response($.map(data, function (item) {
							return item;
						}));
					},
	                beforeSend: function(xhr) {
	                	xhr.setRequestHeader("AJAX", true);
					},
	                error: function(e) {
	                	if(data.status == "901") {
	                    	location.href = "/admin/login";
						}
					}
				});
			},
	        minLength: 2,
	        focus: function (event, ui) {
	        	return false;
			},
	        select: function (event, ui) {
	        	// label 값을 넣어 주면 회사와 현장이 붙어서 나온다.
				// $(e).val(ui.item.label);
				// 회사명만 나오게
				
	            $("#employer_name").val(ui.item.employer_name);
				$("#employer_num").val(ui.item.employer_num);
				$("#employer_owner").val(ui.item.employer_owner);			
				$("#employer_business").val(ui.item.employer_business);
				$("#employer_sector").val(ui.item.employer_sector);
				$("#tax_name").val(ui.item.tax_name);
				$("#tax_phone").val(ui.item.tax_phone);
				$("#employer_tel").val(ui.item.employer_tel);
				$("#employer_feature").val(ui.item.employer_feature);
				$("#employer_memo").val(ui.item.employer_memo);
				$("#employer_seq").val(ui.item.employer_seq);
				
				return false;
			}
		});
	});
	
	
	
</script>
<body>
	
	<div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header" style="text-align: center;">
                            구인처 및 현장 등록
                        </h1>
                    </div>
             </div> 
                 

			<div class="row">
				<table class="table table-hover table-bordered" style="float: left; width:34%; margin-left: 15%;">
					<tr>
						<td style="width: 30%;">회사명</td>
						<td>
							<input type="text" name="employer_name" id="employer_name" class="form-control">
						</td>
					</tr>
					<tr>
						<td>사업자등록번호</td>
						<td>
							<input type="text" name="employer_num" id="employer_num" class="form-control">
						</td>
					</tr>
					<tr>
						<td>대표자</td>
						<td>
							<input type="text" name="employer_owner" id="employer_owner" class="form-control">
						</td>
					</tr>
					<tr>
						<td>업태</td>
						<td>
							<input type="text" name="employer_business" id="employer_business" class="form-control">
						</td>
					</tr>
					<tr>
						<td>업종</td>
						<td>
							<input type="text" name="employer_sector" id="employer_sector" class="form-control">
						</td>
					</tr>
					<tr>
						<td>담당자</td>
						<td>
							<input type="text" name="tax_name" id="tax_name" class="form-control">
						</td>
					</tr>
					<tr>
						<td>전화</td>
						<td>
							<input type="text" name="tax_phone" id="tax_phone" class="form-control">
						</td>
					</tr>
					<tr>
						<td>전화번호</td>
						<td>
							<input type="text" name="employer_tel" id="employer_tel" class="form-control">
						</td>
					</tr>
					<tr>
						<td>특징</td>
						<td>
							<textarea class="form-control" name="employer_feature" id="employer_feature"></textarea>
						</td>
					</tr>
					<tr>
						<td>메모</td>
						<td>
							<textarea class="form-control" name="employer_memo" id="employer_memo"></textarea>
						</td>
					</tr>
					<input type="hidden" id="employer_seq" name="employer_seq" value="0" />
				</table>
				<table class="table table-hover table-bordered" style="float: right; width:34%; margin-right: 15%;">
					<tr>
						<td style="width: 18%;">현장명</td>
						<td colspan="4">
							<input type="text" name="work_name" id="work_name" class="form-control">
						</td>
					</tr>
					<tr>
						<td>현장 도착 시간</td>
						<td>
							<input type="text" name="work_arrival" id="work_arrival" class="form-control" maxlength="4">
						</td>
						<td>현장 마감 시간</td>
						<td colspan="2">
							<input type="text" name="work_finish" id="work_finish" class="form-control" maxlength="4">
						</td>
					</tr>
					<tr>
						<td>현장 주소</td>
						<td colspan="4">
							<div class="bd-example">
								<div class="form-row align-items-center">
									<div class="col-auto">
										<input type="text" name="work_addr" id="work_addr" class="form-control mb-2" style="width: 50%; float: left;" readonly>
									</div>
									<div class="col-auto">
										<button class="btn btn-primary mb-2" onclick="mapWindowOpen('WORK')" style="float: right; position: absolute;">주소</button>
									</div>
									<input type="hidden" name="work_latlng" id="work_latlng" />
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>작업 설명</td>
						<td colspan="4">
							<textarea class="form-control" name="work_addr_comment" id="work_addr_comment" maxlength="500"></textarea>
						</td>
					</tr>
					<tr>
						<td>조식 유무</td>
						<td>
							<div>
								<input type="radio" name="work_breakfast_yn" id="work_breakfast_yn1" class="form-check-input" value="1">
								<label class="form-check-label" for="work_breakfast_yn1">유</label>					
								<input type="radio" name="work_breakfast_yn" id="work_breakfast_yn2" class="form-check-input" value="0" style="margin-left: 7%;" checked>
								<label class="form-check-label" for="work_breakfast_yn2">무</label>					
							</div>
						</td>
						<td>혈압 제한</td>
						<td colspan="2">
							<input type="text" name="work_blood_pressure" id="work_blood_pressure" class="form-control">
						</td>
					</tr>
					<tr>
						<td>최저 나이</td>
						<td>
							<input type="text" name="work_age_min" id="work_age_min" class="form-control">
						</td>
						<td>최고 나이</td>
						<td colspan="2">
							<input type="text" name="work_age" id="work_age" class="form-control">
						</td>
					</tr>
					<tr>
						
					</tr>
					<tr>
						<td>현장 매니저</td>
						<td>
							<input type="text" name="manager_name" id="manager_name" class="form-control">
						</td>
						<td>현장 매니저 전화</td>
						<td>
							<input type="text" name="manager_phone" id="manager_phone" class="form-control">
						</td>
						<td>
							<input type="checkbox" id="copyManager" value="1">
						</td>
					</tr>
					<tr>
						<td>현장 담당자</td>
						<td>
							<input type="text" name="work_manager_name" id="work_manager_name" class="form-control">
						</td>
						<td>현장 담당자 전화</td>
						<td colspan="2">
							<input type="text" name="work_manager_phone" id="work_manager_phone" class="form-control">
						</td>
					</tr>
					<tr>
						<td>현장 메모</td>
						<td colspan="4">
							<textarea class="form-control" name="work_memo" id="work_memo" maxlength="500" style="height: 200px;"></textarea>
						</td>
					</tr>
					<tr>
						<td>인원</td>
						<td colspan="4">
							<input type="number" id="ilbo_cnt" name="ilbo_cnt" value="1" min="1" max="100" step="1" />
						</td>
					</tr>
				</table>
			</div>
	
		</div>      <!-- /. ROW  -->
	</div>
	
	<footer class="page-footer font-small blue pt-4">
		<div class="container-fluid text-center text-md-left">
      	</div>
      	<div class="footer-copyright text-center py-3">
	        
	        <a class="btn btn-primary btn-lg" role="button"  onclick="fn_insertEmployer()">등록</a>
	        <a class="btn btn-danger btn-lg" role="button"  onclick="fn_close()">닫기</a>
	        
    	</div>
    </footer>
    
    <form id="mapFrm" method="post">
		<input type="hidden" id="map_mode" name="map_mode" value="" /> 
		<input type="hidden" id="map_seq" name="map_seq" value="" /> 
		<input type="hidden" id="map_lat" name="map_lat" value="" /> 
		<input type="hidden" id="map_lng" name="map_lng" value="" /> 
		<input type="hidden" id="map_addr" name="map_addr" value="" /> 
		<input type="hidden" id="map_comment" name="map_comment" value="" />
	</form>
	<input type="hidden" id="manager_phone_check" value="0" />
</body>