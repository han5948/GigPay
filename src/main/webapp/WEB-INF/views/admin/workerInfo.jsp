<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

	<div class="cont-box">
    	<article>
			<table class="bd-form inputUI_simple" summary="구직자 정보보기 영역입니다.">
      			<caption>소속회사 근로자명 주민등록번호 핸드폰 전문분야 연락처 주소 은행명(예금주) 계좌번호 혈압 특징 신분증스캔여부 교육증스캔여부 일가자 예약 앱사용 푸쉬 정보보보기영역</caption>
      			<colgroup>
        			<col width="100px" />
        			<col width="280px" />
        			<col width="100px" />
        			<col width="280px" />
      			</colgroup>
      			<tbody>
<%--
        <tr>
          <th>소속회사</th>
          <td colspan="3"><input type="text" class="inp-field wid650" id="company_name" name="company_name" value="<c:out value="${sessionScope.ADMIN_SESSION.company_name}" />" /></td>
        </tr>
--%>
        			<tr>
          				<th>근로자명</th>
          				<td>${result.worker_name} (${result.worker_age})</td>
          				<th>주민등록번호</th>
          				<td>${result.worker_jumin}</td>
        			</tr>
        			<tr>
          				<th>핸드폰</th>
          				<td>${result.worker_phone}</td>
          				<th>전문분야</th>
          				<td>${result.worker_job_name}</td>
        			</tr>
        			<tr>
          				<th>연락처</th>
          				<td>${result.worker_tel1}, ${result.worker_tel2}, ${result.worker_tel3}</td>
          				<th>소장평가</th>
          				<td>${result.worker_rating eq 0 ? '미평가': result.worker_rating eq 1 ? 'F' : result.worker_rating eq 2 ? 'E' : result.worker_rating eq 3 ? 'D' : result.worker_rating eq 4 ? 'C' : result.worker_rating eq 5 ? 'B' : 'A'}</td>
        			</tr>
        			<tr>
          				<th>주소</th>
          				<td colspan="3">${result.worker_addr}</td>
        			</tr>
        			<tr>
          				<th>은행명</th>
          				<td>${result.worker_bank_name} (예금주: ${result.worker_bank_owner})</td>
          				<th>계좌번호</th>
          				<td>${result.worker_bank_account}</td>
        			</tr>
        			<tr>
          				<th>혈압</th>
          				<td>${result.worker_blood_pressure}</td>
          				<th>교육이수 여부</th>
          				<td>${result.worker_OSH_kr}</td>
        			</tr>
        			<tr>
          				<th>특징</th>
          				<td colspan="3" height="100">${result.worker_feature}</td>
        			</tr>
        			<tr>
          				<th>신분증 스캔여부</th>
          				<td>${result.worker_jumin_scan_kr}</td>
          				<th>교육증 스캔여부</th>
          				<td>${result.worker_OSH_scan_kr}</td>
        			</tr>
        			<tr>
          				<th>일가자</th>
          				<td>${result.worker_app_status_kr}</td>
          				<th>예약</th>
          				<td>${result.worker_reserve_push_status_kr}</td>
        			</tr>
        			<tr>
          				<th>앱사용</th>
          				<td>${result.worker_app_use_status_kr}</td>
          				<th>푸쉬</th>
          				<td>${result.worker_push_yn_kr}</td>
        			</tr>
     			</tbody>
      		</table>
    	</article>

    	<div class="btn-module mgtS">
      		<div class="rightGroup"><a class="btnStyle05" href="javascript:closeIrPopup('popup-layer2');">닫기</a></div>
    	</div>
  	</div>