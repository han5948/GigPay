<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(document).ready(function() {
		
		$("input[name=detail_use_yn]").on("change", function() {
			if($(this).val() == '1') {
				$("#basicPriceTr").hide();
				
			}else {
				$("#basicPriceTr").show();
			}
		});
	});
	
	function updateForm() {
		var regexp = /^[0-9]*$/;
		
		if($("#job_name").val().replace(/\s/g, "").length == 0) {
			alert("직종명을 입력해주세요.");
			
			$("#job_name").focus();
			
			return false;
		}
		
		if($("#job_worker_fee").val().replace(/\s/g, "").length == 0) {
			alert("구인자 수수료를 입력해주세요.");
			
			$("#job_worker_fee").focus();
			
			return false;
		}
		
		if($("#job_work_fee").val().replace(/\s/g, "").length == 0) {
			alert("구인처 수수료를 입력해주세요.");
			
			$("#job_work_fee").focus();
			
			return false;
		}
		
		if($("#job_color").val().length == 0){
			alert("폰트색값을 입력해 주세요.");
			$("#job_color").focus();
			
			return false;
		}
		
		if($("#job_background").val().length == 0){
			alert("배경색값을 입력해 주세요.");
			$("#job_background").focus();
			
			return false;
		}
		
		var _colorRegExp = /^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/;
		if( !_colorRegExp.test($("#job_color").val()) ){
			alert("폰트색 입력을 다시해주세요.");
			$("#job_color").focus();
			
			return false;
		}
		if( !_colorRegExp.test($("#job_background").val()) ){
			alert("배경색 입력을 다시해주세요.");
			$("#job_background").focus();
			
			return false;
		}
		
		
		if($("input[name=detail_use_yn]:checked").val() == '0') {
			if($("#basic_price").val() == '') {
				alert("기본 단가를 입력해주세요.");
				
				return false;
			}
			
			if($("#short_price").val() == '') {
				alert("단시간 단가(주간)을 입력해주세요.");
				
				return false;
			}
			
			if($("#short_price_night").val() == '') {
				alert("단시간 단가(야간)을 입력해주세요.");
				
				return false;
			}
			
			if($("#add_day_time").val() == '') {
				alert("주간 추가 단가를 입력해주세요.");
				
				return false;
			}
			
			if($("#add_night_time").val() == '') {
				alert("야간 추가 단가를 입력해주세요.");
				
				return false;
			}
		}else {
			$("#basic_price").val("0");
			$("#short_price").val("0");
			$("#short_price_night").val("0");
			$("#add_day_time").val("0");
			$("#add_night_time").val("0");
		}
		
		$("#contentFrm").ajaxForm({
			url : "/admin/job/updateJob",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if(data.code == "0000") {
						toastOk("정상적으로 수정 되었습니다.");
						
						location.href = '/admin/job/jobList';
					}else {
						toastFail(data.message);
					}
			} ,
			beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
	    	error : function(e) {
				if(e.status == "901") {
					location.href = "/admin/login";
				}else {
					toastFail("수정 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		}).submit();
	}
	
	function deleteForm() {
		if(!confirm("해당 직종을 삭제하시겠습니까?")) {
			return false;
		}
		
		$("#contentFrm").ajaxForm({
			url : "/admin/job/deleteJob",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if(data.code == "0000") {
						toastOk("정상적으로 삭제 되었습니다.");
						
						location.href = '/admin/job/jobList';
					}else {
						toastFail("삭제 실패");
					}
			} ,
			beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
	    	error : function(e) {
				if(e.status == "901") {
					location.href = "/admin/login";
				}else {
					toastFail("삭제 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		}).submit();
	}
</script>
	<article>
		<div class="content mgtS">
			<div class="title">
				<h3>직종 수정</h3>
			</div>
			
			<table class="bd-form" summary="직종 수정">
				<colgroup>
					<col width="200px" />
					<col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<th>직종 구분</th>
						<td class="linelY">
							<input type="radio" name="job_kind" value="1" <c:if test="${jobInfo.job_kind eq '1' }">checked="checked"</c:if>>보통인부
							<input type="radio" name="job_kind" value="2" <c:if test="${jobInfo.job_kind eq '2' }">checked="checked"</c:if>>기공
							<input type="radio" name="job_kind" value="3" <c:if test="${jobInfo.job_kind eq '3' }">checked="checked"</c:if>>노무제공자
						</td>
					</tr>	
					<tr>
						<th>분야 구분</th>
						<td class="linelY">
							<input type="radio" name="job_field" value="1" <c:if test="${jobInfo.job_field eq '1' }">checked</c:if>>건설 분야
							<input type="radio" name="job_field" value="2" <c:if test="${jobInfo.job_field eq '2' }">checked</c:if>>건설 외 분야
							<input type="radio" name="job_field" value="3" <c:if test="${jobInfo.job_field eq '3' }">checked</c:if>>특고직
							<input type="radio" name="job_field" value="4" <c:if test="${jobInfo.job_field eq '4' }">checked</c:if>>플랫폼 종사자
						</td>
					</tr>
					<tr>
						<th>직종명</th>
						<td class="linelY">
							<input type="text" id="job_name" name="job_name" value="${jobInfo.job_name }" class="inp-field wid700 notEmpty">
						</td>
					</tr>
					<tr>
						<th>아이콘 명</th>
						<td class="linelY">
							<input type="text" id="job_image_name" name="job_image_name" value="${jobInfo.job_image_name }" class="inp-field wid100 notEmpty">
						</td>
					</tr>
					<tr>
						<th>구인자 수수료</th>
						<td class="linelY">
							<p class="floatL">
								<input type="text" id="job_work_fee" name="job_work_fee" value="${jobInfo.job_work_fee }" class="inp-field wid50 notEmpty">
								<span class="dateTxt">%</span>
							</p>
						</td>
					</tr>
					<tr>
						<th>구직자 수수료</th>
						<td class="linelY">
							<p class="floatL">
								<input type="text" id="job_worker_fee" name="job_worker_fee" value="${jobInfo.job_worker_fee }" class="inp-field wid50 notEmpty">
								<span class="dateTxt">%</span>
							</p>
						</td>
					</tr>
					<tr>
						<th>사용 여부</th>
						<td class="linelY">
							<input type="radio" name="use_yn" value="1" <c:if test="${jobInfo.use_yn eq '1' }">checked="checked"</c:if>>사용
							<input type="radio" name="use_yn" value="0" <c:if test="${jobInfo.use_yn eq '0' }">checked="checked"</c:if>>미사용
						</td>
					</tr>
					<tr>
						<th>세부직종</th>
						<td class="linelY">
							<input type="radio" name="detail_use_yn" value="0" <c:if test="${jobInfo.detail_use_yn eq '0' }">checked="checked"</c:if>>없음
							<input type="radio" name="detail_use_yn" value="1" <c:if test="${jobInfo.detail_use_yn eq '1' }">checked="checked"</c:if>>있음
						</td>

					</tr>
					<tr>
						<th>폰트색</th>
						<td class="linelY">
							<input type="text" id="job_color" name="job_color" class="inp-field wid150 notEmpty" value="${jobInfo.job_color }" placeholder="#000000">
						</td>
					</tr>
					<tr>
						<th>배경색</th>
						<td class="linelY">
							<input type="text" id="job_background" name="job_background" class="inp-field wid150 notEmpty" value="${jobInfo.job_background }" placeholder="#000000">
						</td>
					</tr>
					<tr id="basicPriceTr" <c:if test="${jobInfo.detail_use_yn eq '1' }">style="display: none;"</c:if>>
						<td></td>
						<td>
							<table class="bd-form" summary="직종 수정">
							<colgroup>
								<col width="180px" />
								<col width="150px" />
								<col width="180px" />
								<col width="150px" />
								<col width="180px" />
								<col width="150px" />
								<col width="180px" />
								<col width="150px" />
								<col width="180px" />
								<col width="*" />
							</colgroup>
							<tbody>
								<tr>
									<th class="linelY">기본 단가(06 ~ 17)</th>
									<td>
										<input type="text" id="basic_price" name="basic_price" class="inp-field wid100 notEmpty" value="${jobInfo.basic_price }">
									</td>	
									<th class="linelY">단시간<br>(주간)</th>
									<td>
										<input type="text" id="short_price" name="short_price" class="inp-field wid100 notEmpty" value="${jobInfo.short_price }">
									</td>
									<th class="linelY">단시간<br>(야간)</th>
									<td>
										<input type="text" id="short_price_night" name="short_price_night" class="inp-field wid100 notEmpty" value="${jobInfo.short_price_night }">
									</td>
									<th class="linelY">시간 추가(08 ~ 18)</th>
									<td>
										<input type="text" id="add_day_time" name="add_day_time" class="inp-field wid100 notEmpty" value="${jobInfo.add_day_time }">
									</td>
									<th class="linelY">시간 추가(18 ~ 06)</th>
									<td>
										<input type="text" id="add_night_time" name="add_night_time" class="inp-field wid100 notEmpty" value="${jobInfo.add_night_time }">
									</td>
								</tr>
							</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td class="linelY">
							${jobInfo.reg_admin }
						</td>
					</tr>
					<tr>
						<th>작성일</th>
						<td class="linelY">
							${jobInfo.reg_date }
						</td>
					</tr>
				</tbody>
			</table>
			
			<input type="hidden" name="job_seq" value="${jobInfo.job_seq }" />
			<input type="hidden" name="before_code" value="${jobInfo.job_code }" />
			<input type="hidden" name="before_job_name" value="${jobInfo.job_name }" />
			<div class="btn-module mgtL" style="text-align: center;">
				<a style="float: left;" href="/admin/job/jobList" id="btnList" class="btnStyle04">목록</a>
			 	<a href="javascript:void(0);" onclick="javascript:deleteForm();" id="btnDel" class="btnStyle04">삭제</a>
			  	<a href="javascript:void(0);" onclick="javascript:updateForm();" id="btnEdit" class="btnStyle04">수정</a>
		    </div>
		</div>
	</article>