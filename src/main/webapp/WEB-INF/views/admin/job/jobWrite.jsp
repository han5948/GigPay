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
	
	function insertForm() {
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
			url : "/admin/job/insertJob",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						toastOk("정상적으로 추가 되었습니다.");
						
						location.href = '/admin/job/jobList';
					} else {
						toastFail(data.message);
					}
			} ,
			beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
	    	error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("등록 실패");
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
				<h3>직종 추가</h3>
			</div>
			
			<table class="bd-form" summary="직종 추가">
				<colgroup>
					<col width="200px" />
					<col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<th>직종 구분</th>
						<td class="linelY">
							<input type="radio" name="job_kind" value="1" checked>보통인부
							<input type="radio" name="job_kind" value="2">기공
							<input type="radio" name="job_kind" value="3">노무제공자
						</td>
						<td></td>
						<td></td>
					</tr>	
					<tr>
						<th>분야 구분</th>
						<td class="linelY">
							<input type="radio" name="job_field" value="1" checked>건설 분야
							<input type="radio" name="job_field" value="2">건설 외 분야
							<input type="radio" name="job_field" value="3">특고직
							<input type="radio" name="job_field" value="4">플랫폼 종사자
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th>직종명</th>
						<td class="linelY">
							<input type="text" id="job_name" name="job_name" class="inp-field wid700 notEmpty">
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th>아이콘 명</th>
						<td class="linelY">
							<input type="text" id="job_image_name" name="job_image_name" class="inp-field wid100 notEmpty">
						</td>
					</tr>
					<tr>
						<th>구인자 수수료</th>
						<td class="linelY">
							<p class="floatL">
								<input type="text" id="job_work_fee" name="job_work_fee" class="inp-field wid50 notEmpty">
								<span class="dateTxt">%</span>
							</p>
						</td>
					</tr>
					<tr>
						<th>구직자 수수료</th>
						<td class="linelY">
							<p class="floatL">
								<input type="text" id="job_worker_fee" name="job_worker_fee" class="inp-field wid50 notEmpty">
								<span class="dateTxt">%</span>
							</p>
						</td>
					</tr>
					<tr>
						<th>사용 여부</th>
						<td class="linelY">
							<input type="radio" name="use_yn" value="1" checked="checked">사용
							<input type="radio" name="use_yn" value="0">미사용
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th>세부직종</th>
						<td class="linelY">
							<input type="radio" name="detail_use_yn" value="0" checked="checked">없음
							<input type="radio" name="detail_use_yn" value="1">있음
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr id="basicPriceTr">
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
									<th class="linelY">기본 단가<br>(06 ~ 17)</th>
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
					<input type="hidden" name="job_rank" value="1" />
				</tbody>
			</table>
			<div class="btn-module mgtL" style="text-align: center;">
				<a style="float: left;" href="/admin/job/jobList" id="btnList" class="btnStyle04">
					목록
				</a>
				<a href="javascript:void(0);" onclick="javascript:insertForm();" id="btnAdd" class="btnStyle04">
					등록
				</a>
			</div>
		</div>
	</article>