<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

	<div class="cont-box">
    	<article>
      		<table class="bd-form inputUI_simple" summary="구인처 정보보기 영역입니다.">
      			<caption>소속회사 구인처명 사업자등록번호 대표자 업태 업종 전화번호 팩스 폰번호 이메일 주소 특징 메모 정보보기 영역</caption>
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
          				<th>구인처명</th>
          				<td colspan="3">${result.employer_name}</td>
        			</tr>
        			<tr>
          				<th>사업자번호</th>
          				<td>${result.employer_num}</td>
          				<th>대표자</th>
          				<td>${result.employer_owner}</td>
        			</tr>
        			<tr>
          				<th>업태</th>
          				<td>${result.employer_business}</td>
          				<th>업종</th>
          				<td>${result.employer_sector}</td>
        			</tr>
        			<tr>
          				<th>전화번호</th>
          				<td>${result.employer_tel}</td>
          				<th>팩스</th>
          				<td>${result.employer_fax}</td>
        			</tr>
        			<tr>
          				<th>폰번호</th>
          				<td>${result.employer_phone}</td>
          				<th>이메일</th>
          				<td>${result.employer_email}</td>
        			</tr>
        			<tr>
          				<th>주소</th>
          				<td colspan="3">${result.employer_addr}</td>
        			</tr>
        			<tr>
          				<th>특징</th>
          				<td colspan="3" height="100">${result.employer_feature}</td>
        			</tr>
        			<tr>
          				<th>메모</th>
          				<td colspan="3" height="100">${result.employer_memo}</td>
        			</tr>
      			</tbody>
      		</table>
    	</article>

    	<div class="btn-module mgtS">
      		<div class="rightGroup"><a class="btnStyle05" href="javascript:closeIrPopup('popup-layer2');">닫기</a></div>
    	</div>
  	</div>
