<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style>

</style>
<script type="text/javascript">
	var careerDTO = '${careerDTO }';
	
	$(document).ready(function() {
		if(!careerDTO) {
			$("#option_setting_name_1").val("초초급");			
			$("#option_setting_name_2").val("초급");			
			$("#option_setting_name_3").val("상급");			
			$("#option_setting_name_4").val("최상급");			
			$("#option_setting_default_name").val("기본");
			
			$("#option_setting_price_1").val("22000");
			$("#option_setting_price_2").val("11000");
			$("#option_setting_price_3").val("11000");
			$("#option_setting_price_4").val("22000");
		}
	});
	
	function fn_careerSave() {
		if($("#option_setting_name_1").val() == ''
			|| $("#option_setting_name_2").val() == ''
			|| $("#option_setting_name_3").val() == ''
			|| $("#option_setting_name_4").val() == ''
			|| $("#option_setting_default_name").val() == '') {
			alert("경력명을 입력해주세요.");
		}
		
		if($("#option_setting_price_1").val() == ''
			|| $("#option_setting_price_2").val() == ''
			|| $("#option_setting_price_3").val() == ''
			|| $("#option_setting_price_4").val() == '') {
			alert("금액을 입력해주세요.");
		}
		
		var exp = new RegExp('^[0-9-_]*$');
		
		if(!exp.test($("#option_setting_price_1").val())
			|| !exp.test($("#option_setting_price_2").val())	
			|| !exp.test($("#option_setting_price_3").val())
			|| !exp.test($("#option_setting_price_4").val())) {
			alert("금액은 숫자만 입력해주세요.");
			
			return false;
		}
		
		var _url;
		var _data = {
			m_view_option : $("input[name=m_view_option]:checked").val()
			, kind_apply_option : $("input[name=kind_apply_option]:checked").val()
			, option_setting_use_yn_1 : $("#option_setting_use_yn_1").is(":checked") ? '1' : '0'
			, option_setting_name_1 : $("#option_setting_name_1").val()
			, option_setting_price_1 : $("#option_setting_price_1").val()
			, option_setting_use_yn_2 : $("#option_setting_use_yn_2").is(":checked") ? '1' : '0'
			, option_setting_name_2 : $("#option_setting_name_2").val()
			, option_setting_price_2 : $("#option_setting_price_2").val()
			, option_setting_use_yn_3 : $("#option_setting_use_yn_3").is(":checked") ? '1' : '0'
			, option_setting_name_3 : $("#option_setting_name_3").val()
			, option_setting_price_3 : $("#option_setting_price_3").val()
			, option_setting_use_yn_4 : $("#option_setting_use_yn_4").is(":checked") ? '1' : '0'
			, option_setting_name_4 : $("#option_setting_name_4").val()
			, option_setting_price_4 : $("#option_setting_price_4").val()
			, option_setting_default_name : $("#option_setting_default_name").val()
		};
		
		if(!careerDTO) {
			_url = '/admin/career/insertCareer';	
		}else {
			_url = '/admin/career/updateCareer';
		}
		
		$.ajax({
			type: "POST",
			url: _url,
			data: _data,
			dataType: 'json',
			success: function(data) {
				if(data.code == '0000') {
					alert("저장이 완료되었습니다.");
					
					location.reload();
				}else {
					alert(data.message);
				}
			},
	 		beforeSend: function(xhr) {
	 			xhr.setRequestHeader("AJAX", true);
	 		},
	 		error: function(e) {
	 			alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
	 		}
		});
	}
</script>
	</form>
    <div class="content mgtS mgbL">
		<div class="title">
			<h3>맞춤 설정</h3>
		</div>
		<form id="optionFrm" name="optionFrm">
			<div class="inputUI_simple">
				<table class="bd-form s-form" summary="경력옵션 설정 테이블">
				    <colgroup>
					    <col width="30px" />
					    <col width="200px" />
				    </colgroup>
					<tbody id="m_view_body">
						<tr>
							<th scope="row" class="lineRY pdlM">M에서 노출옵션</th>
							<td>
	          					<input type="radio" value="0" id="m_view_option_0" name="m_view_option" class="inputJo" <c:if test="${careerDTO eq null or careerDTO.m_view_option eq '0' }">checked="checked"</c:if> /><label for="m_view_option_0" class="label-radio">사용(회원)</label>
	          					<input type="radio" value="1" id="m_view_option_1" name="m_view_option" class="inputJo" <c:if test="${careerDTO.m_view_option eq '1' }">checked="checked"</c:if> /><label for="m_view_option_1" class="label-radio">사용(회원 + 비회원)</label>
	          					<input type="radio" value="2" id="m_view_option_2" name="m_view_option" class="inputJo" <c:if test="${careerDTO.m_view_option eq '2' }">checked="checked"</c:if> /><label for="m_view_option_2" class="label-radio">미사용</label>
							</td>
						</tr>
						<tr>
							<th scope="row" class="lineRY pdlM">적용직종</th>
							<td>
	          					<input type="radio" value="0" id="kind_apply_option_0" name="kind_apply_option" class="inputJo" <c:if test="${careerDTO eq null or careerDTO.kind_apply_option eq '0' }">checked="checked"</c:if> /><label for="kind_apply_option_0" class="label-radio">보통인부 건설직종</label>
	          					<input type="radio" value="1" id="kind_apply_option_1" name="kind_apply_option" class="inputJo" <c:if test="${careerDTO.kind_apply_option eq '1' }">checked="checked"</c:if> /><label for="kind_apply_option_1" class="label-radio">보통인부 건설외직종</label>
	          					<input type="radio" value="2" id="kind_apply_option_2" name="kind_apply_option" class="inputJo" <c:if test="${careerDTO.kind_apply_option eq '2' }">checked="checked"</c:if> /><label for="kind_apply_option_2" class="label-radio">보통인부 모두</label>
							</td>
						</tr>
			    	</tbody>
				</table>
			</div>
			<br/>
			<br/>
			<br/>
			<div class="inputUI_simple">
				<table class="bd-form" summary="옵션설정" style="width: 540px;">
					<caption>옵션설정</caption>
					<tr>
						<th scope="row" class="linelY" style="width: 55px;">사용 여부</th>
						<th scope="row" class="linelY" style="width: 50px; text-align: center;">경력명</th>
						<th scope="row" class="linelY" style="text-align: center;">금액</th>
					</tr>
					<tr>
						<td>
							<input type="checkbox" id="option_setting_use_yn_1" name="option_setting_use_yn_1" class="inputJo" value="1" <c:if test="${careerDTO.option_setting_use_yn_1 eq '1' }">checked="checked"</c:if> /><label for="option_setting_use_yn_1" class="checkTxt"></label>
						</td>
						<td>
							<input type="text" id="option_setting_name_1" name="option_setting_name_1" value="${careerDTO.option_setting_name_1 }">
						</td>
						<td>
							<p class="floatL">
								<span class="dateTxt">-</span>
								<input type="text" id="option_setting_price_1" name="option_setting_price_1" class="inp-field wid100 textCls" value="${careerDTO.option_setting_price_1 }" />
								<span class="dateTxt">원</span>
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" id="option_setting_use_yn_2" name="option_setting_use_yn_2" class="inputJo" value="1" <c:if test="${careerDTO eq null or careerDTO.option_setting_use_yn_2 eq '1' }">checked="checked"</c:if> /><label for="option_setting_use_yn_2" class="checkTxt"></label>
						</td>
						<td>
							<input type="text" id="option_setting_name_2" name="option_setting_name_2" value="${careerDTO.option_setting_name_2 }">
						</td>
						<td>
							<p class="floatL">
								<span class="dateTxt">-</span>
								<input type="text" id="option_setting_price_2" name="option_setting_price_2" class="inp-field wid100 textCls" value="${careerDTO.option_setting_price_2 }" />
								<span class="dateTxt">원</span>
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" id="option_setting_default" name="option_setting_default" class="inputJo" checked value="1" disabled /><label for="option_setting_default" class="checkTxt"></label>
						</td>
						<td>
							<input type="text" id="option_setting_default_name" name="option_setting_default_name" value="${careerDTO.option_setting_default_name }">
						</td>
						<td></td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" id="option_setting_use_yn_3" name="option_setting_use_yn_3" class="inputJo" value="1" <c:if test="${careerDTO eq null or careerDTO.option_setting_use_yn_3 eq '1' }">checked="checked"</c:if> /><label for="option_setting_use_yn_3" class="checkTxt"></label>
						</td>
						<td>
							<input type="text" id="option_setting_name_3" name="option_setting_name_3" value="${careerDTO.option_setting_name_3 }">
						</td>
						<td>
							<p class="floatL">
								<span class="dateTxt">+</span>
								<input type="text" id="option_setting_price_3" name="option_setting_price_3" class="inp-field wid100 textCls" value="${careerDTO.option_setting_price_3 }" />
								<span class="dateTxt">원</span>
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" id="option_setting_use_yn_4" name="option_setting_use_yn_4" class="inputJo" value="1" <c:if test="${careerDTO.option_setting_use_yn_4 eq '1' }">checked="checked"</c:if> /><label for="option_setting_use_yn_4" class="checkTxt"></label>
						</td>
						<td>
							<input type="text" id="option_setting_name_4" name="option_setting_name_4" value="${careerDTO.option_setting_name_4 }">
						</td>
						<td>
							<p class="floatL">
								<span class="dateTxt">+</span>
								<input type="text" id="option_setting_price_4" name="option_setting_price_4" class="inp-field wid100 textCls" value="${careerDTO.option_setting_price_4 }" />
								<span class="dateTxt">원</span>
							</p>
						</td>
					</tr>
				</table>
			</div>
		</form>
	
		<div class="btn-module mgtL">
			<a href="javascript:void(0);" id="btnAdd" class="btnStyle04" onclick="fn_careerSave()">
				저장
			</a>
		</div>
	</div>
