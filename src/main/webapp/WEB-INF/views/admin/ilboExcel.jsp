<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>${htmlHeader} -일가자</title>

<style>
  	table
    @page { mso-header-data: "&CMultiplication Table\000ADate\: &D\000APage &P";  mso-page-orientation:landscape; }

  	td { mso-number-format: '\@'; }
  	br { mso-data-placement: same-cell; }
  	p  { mso-data-placement: same-cell; }
</style>
</head>
<body>

	<table border="0">
	  	<tr align="center">
	    	<td bgcolor="#00B050"><font size="4" color="#FFFFFF">일용근로자 노무비 대장</font></td>
	  	</tr>
	  	<tr align="center">
		    <td>
			    <table border="0">
	      			<thead>
	        			<tr>
	          				<td bgcolor="#BFBFBF" colspan="2">회사명</td>
	          				<td colspan="37">${info.employer_name}</td>
	        			</tr>
	        			<tr>
	          				<td bgcolor="#BFBFBF" colspan="2">현장명</td>
	          				<td colspan="37">${info.ilbo_work_name}</td>
	        			</tr>
	        			<tr>
	          				<td bgcolor="#BFBFBF" colspan="2">기간</td>
	          				<td colspan="37">${info.start_ilbo_date} ~ ${info.end_ilbo_date}</td>
	        			</tr>
	        			<tr>
	          				<td colspan="37">&nbsp;</td>
	        			</tr>
	        			<tr>
	          				<th rowspan="2" bgcolor="#BFBFBF">성명</th>
	          				<th rowspan="2" bgcolor="#BFBFBF">주민번호</th>
	          				<th rowspan="2" bgcolor="#BFBFBF">전화</th>
	          				<th rowspan="2" bgcolor="#BFBFBF">주소</th>
	          				<th rowspan="2" bgcolor="#BFBFBF">계좌</th>
	        				<c:forEach items="${dayList}" var="day" varStatus="status">
	          					<th bgcolor="#E6B8B7">${fn:substring(day, 6, 8)}</th>
	        				</c:forEach>
	          				<th bgcolor="#CCC0DA">공수</th>
	          				<th rowspan="2" bgcolor="#CCC0DA">단가</th>
	          				<th bgcolor="#CCC0DA">총액</th>
	        			</tr>
	        			<tr>          
	        				<c:forEach items="${dayList}" var="day" varStatus="status">
	          					<th id="day_${status.index + 1}" bgcolor="#DDD9C4">0</th>
	        				</c:forEach>
	          				<th id="userTotals" bgcolor="#CCC0DA">0</th>
	          				<th id="priceTotals" bgcolor="#CCC0DA">0</th>
	        			</tr>
		      		</thead>
		
		      		<%-- 합계용 변수 --%>
		      		<c:set var="userTotal" value="0" />
		      		<c:set var="userTotals" value="0" />
				    <c:set var="priceTotals" value="0" />
				    <c:set var="dayTotal_1"  value="0" />
				    <c:set var="dayTotal_2"  value="0" />
				    <c:set var="dayTotal_3"  value="0" />
				    <c:set var="dayTotal_4"  value="0" />
				    <c:set var="dayTotal_5"  value="0" />
				    <c:set var="dayTotal_6"  value="0" />
				    <c:set var="dayTotal_7"  value="0" />
				    <c:set var="dayTotal_8"  value="0" />
				    <c:set var="dayTotal_9"  value="0" />
				    <c:set var="dayTotal_10" value="0" />
				    <c:set var="dayTotal_11" value="0" />
				    <c:set var="dayTotal_12" value="0" />
				    <c:set var="dayTotal_13" value="0" />
				    <c:set var="dayTotal_14" value="0" />
				    <c:set var="dayTotal_15" value="0" />
				    <c:set var="dayTotal_16" value="0" />
				    <c:set var="dayTotal_17" value="0" />
				    <c:set var="dayTotal_18" value="0" />
				    <c:set var="dayTotal_19" value="0" />
				    <c:set var="dayTotal_20" value="0" />
				    <c:set var="dayTotal_21" value="0" />
				    <c:set var="dayTotal_22" value="0" />
				    <c:set var="dayTotal_23" value="0" />
				    <c:set var="dayTotal_24" value="0" />
				    <c:set var="dayTotal_25" value="0" />
				    <c:set var="dayTotal_26" value="0" />
				    <c:set var="dayTotal_27" value="0" />
				    <c:set var="dayTotal_28" value="0" />
				    <c:set var="dayTotal_29" value="0" />
				    <c:set var="dayTotal_30" value="0" />
				    <c:set var="dayTotal_31" value="0" />
		      		<tbody>
		      			<c:choose>
		        			<c:when test="${empty list}">
		          				<tr>
		            				<td colspan="39" style="text-align: center; line-height: 100px;">조회된 결과값이 없습니다</td>
		          				</tr>
		        			</c:when>
		        			<c:otherwise>
		          				<c:forEach items="${list}" var="list" varStatus="status">
		            				<tr>
		              					<td scope="col">${list.worker_name}</td>
		              					<td scope="col">${list.worker_jumin}</td>
		              					<td scope="col">${list.worker_phone}</td>
		              					<td scope="col">${list.worker_addr}</td>
		              					<td scope="col">${list.worker_bank_name} ${list.worker_bank_account} ${list.worker_bank_owner}</td>
		              					<td scope="col" align="right">${list.day_1} </td><c:set var="dayTotal_1"  value="${dayTotal_1  + list.day_1}"  />
		              					<td scope="col" align="right">${list.day_2} </td><c:set var="dayTotal_2"  value="${dayTotal_2  + list.day_2}"  />
		              					<td scope="col" align="right">${list.day_3} </td><c:set var="dayTotal_3"  value="${dayTotal_3  + list.day_3}"  />
		              					<td scope="col" align="right">${list.day_4} </td><c:set var="dayTotal_4"  value="${dayTotal_4  + list.day_4}"  />
		              					<td scope="col" align="right">${list.day_5} </td><c:set var="dayTotal_5"  value="${dayTotal_5  + list.day_5}"  />
		              					<td scope="col" align="right">${list.day_6} </td><c:set var="dayTotal_6"  value="${dayTotal_6  + list.day_6}"  />
		              					<td scope="col" align="right">${list.day_7} </td><c:set var="dayTotal_7"  value="${dayTotal_7  + list.day_7}"  />
		              					<td scope="col" align="right">${list.day_8} </td><c:set var="dayTotal_8"  value="${dayTotal_8  + list.day_8}"  />
		              					<td scope="col" align="right">${list.day_9} </td><c:set var="dayTotal_9"  value="${dayTotal_9  + list.day_9}"  />
		              					<td scope="col" align="right">${list.day_10}</td><c:set var="dayTotal_10" value="${dayTotal_10 + list.day_10}" />
		             					<td scope="col" align="right">${list.day_11}</td><c:set var="dayTotal_11" value="${dayTotal_11 + list.day_11}" />
		              					<td scope="col" align="right">${list.day_12}</td><c:set var="dayTotal_12" value="${dayTotal_12 + list.day_12}" />
							            <td scope="col" align="right">${list.day_13}</td><c:set var="dayTotal_13" value="${dayTotal_13 + list.day_13}" />
							            <td scope="col" align="right">${list.day_14}</td><c:set var="dayTotal_14" value="${dayTotal_14 + list.day_14}" />
							            <td scope="col" align="right">${list.day_15}</td><c:set var="dayTotal_15" value="${dayTotal_15 + list.day_15}" />
							            <td scope="col" align="right">${list.day_16}</td><c:set var="dayTotal_16" value="${dayTotal_16 + list.day_16}" />
							            <td scope="col" align="right">${list.day_17}</td><c:set var="dayTotal_17" value="${dayTotal_17 + list.day_17}" />
							            <td scope="col" align="right">${list.day_18}</td><c:set var="dayTotal_18" value="${dayTotal_18 + list.day_18}" />
							            <td scope="col" align="right">${list.day_19}</td><c:set var="dayTotal_19" value="${dayTotal_19 + list.day_19}" />
							            <td scope="col" align="right">${list.day_20}</td><c:set var="dayTotal_10" value="${dayTotal_10 + list.day_20}" />
							            <td scope="col" align="right">${list.day_21}</td><c:set var="dayTotal_21" value="${dayTotal_21 + list.day_21}" />
							            <td scope="col" align="right">${list.day_22}</td><c:set var="dayTotal_22" value="${dayTotal_22 + list.day_22}" />
							            <td scope="col" align="right">${list.day_23}</td><c:set var="dayTotal_23" value="${dayTotal_23 + list.day_23}" />
							            <td scope="col" align="right">${list.day_24}</td><c:set var="dayTotal_24" value="${dayTotal_24 + list.day_24}" />
							            <td scope="col" align="right">${list.day_25}</td><c:set var="dayTotal_25" value="${dayTotal_25 + list.day_25}" />
							            <td scope="col" align="right">${list.day_26}</td><c:set var="dayTotal_26" value="${dayTotal_26 + list.day_26}" />
							            <td scope="col" align="right">${list.day_27}</td><c:set var="dayTotal_27" value="${dayTotal_27 + list.day_27}" />
							            <td scope="col" align="right">${list.day_28}</td><c:set var="dayTotal_28" value="${dayTotal_28 + list.day_28}" />
							            <td scope="col" align="right">${list.day_29}</td><c:set var="dayTotal_29" value="${dayTotal_29 + list.day_29}" />
							            <td scope="col" align="right">${list.day_30}</td><c:set var="dayTotal_30" value="${dayTotal_30 + list.day_30}" />
							            <td scope="col" align="right">${list.day_31}</td><c:set var="dayTotal_31" value="${dayTotal_31 + list.day_31}" />
		
		              					<c:set var="userTotal" value="${list.day_1 + list.day_2 + list.day_3 + list.day_4 + list.day_5 + list.day_6 + list.day_7 + list.day_8 + list.day_9 + list.day_10 + list.day_11 + list.day_12 + list.day_13 + list.day_14 + list.day_15 + list.day_16 + list.day_17 + list.day_18 + list.day_19 + list.day_20 + list.day_21 + list.day_22 + list.day_23 + list.day_24 + list.day_25 + list.day_26 + list.day_27 + list.day_28 + list.day_29 + list.day_30 + list.day_31}" />
		              					<c:set var="userTotals" value="${userTotals + userTotal}" />
		
		              					<td scope="col" align="right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${userTotal}" /></td>
		              					<td scope="col" align="right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${list.ilbo_unit_price}" /></td>
		              					<td scope="col" align="right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${list.total_price}" /></td>
					
		              					<c:set var="priceTotals" value="${priceTotals + list.total_price}" />
		            				</tr>
		          				</c:forEach>
		        			</c:otherwise>
		      			</c:choose>
		      		</tbody>  
		    	</table>
		    </td>
		</tr>
	</table>
</body>
</html>

<script>
	document.getElementById("day_1" ).innerHTML = "${dayTotal_0}";
  	document.getElementById("day_2" ).innerHTML = "${dayTotal_1}";
  	document.getElementById("day_3" ).innerHTML = "${dayTotal_2}";
  	document.getElementById("day_4" ).innerHTML = "${dayTotal_3}";
  	document.getElementById("day_5" ).innerHTML = "${dayTotal_4}";
  	document.getElementById("day_6" ).innerHTML = "${dayTotal_5}";
  	document.getElementById("day_7" ).innerHTML = "${dayTotal_6}";
  	document.getElementById("day_8" ).innerHTML = "${dayTotal_7}";
  	document.getElementById("day_9" ).innerHTML = "${dayTotal_8}";
  	document.getElementById("day_10").innerHTML = "${dayTotal_9}";
  	document.getElementById("day_11").innerHTML = "${dayTotal_10}";
  	document.getElementById("day_12").innerHTML = "${dayTotal_11}";
  	document.getElementById("day_13").innerHTML = "${dayTotal_12}";
  	document.getElementById("day_14").innerHTML = "${dayTotal_13}";
  	document.getElementById("day_15").innerHTML = "${dayTotal_14}";
  	document.getElementById("day_16").innerHTML = "${dayTotal_15}";
  	document.getElementById("day_17").innerHTML = "${dayTotal_16}";
  	document.getElementById("day_18").innerHTML = "${dayTotal_17}";
  	document.getElementById("day_19").innerHTML = "${dayTotal_18}";
  	document.getElementById("day_20").innerHTML = "${dayTotal_19}";
  	document.getElementById("day_21").innerHTML = "${dayTotal_20}";
  	document.getElementById("day_22").innerHTML = "${dayTotal_21}";
  	document.getElementById("day_23").innerHTML = "${dayTotal_22}";
  	document.getElementById("day_24").innerHTML = "${dayTotal_23}";
  	document.getElementById("day_25").innerHTML = "${dayTotal_24}";
  	document.getElementById("day_26").innerHTML = "${dayTotal_25}";
  	document.getElementById("day_27").innerHTML = "${dayTotal_26}";
  	document.getElementById("day_28").innerHTML = "${dayTotal_27}";
  	document.getElementById("day_29").innerHTML = "${dayTotal_28}";
  	document.getElementById("day_30").innerHTML = "${dayTotal_29}";
  	document.getElementById("day_31").innerHTML = "${dayTotal_30}";
	
  	document.getElementById("userTotals").innerHTML = "${userTotals}";
	
  	document.getElementById("priceTotals").innerHTML = "${priceTotals}";
	
</script>