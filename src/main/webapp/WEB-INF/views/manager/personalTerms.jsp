<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="<c:url value="/resources/cms/js/jquery.cookie.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/jsbn.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rsa.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/prng4.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rng.js" />"></script>

<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:굴림;
	panose-1:2 11 6 0 0 1 1 1 1 1;}
@font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
	{font-family:"맑은 고딕";
	panose-1:2 11 5 3 2 0 0 2 0 4;}
@font-face
	{font-family:"\@맑은 고딕";
	panose-1:2 11 5 3 2 0 0 2 0 4;}
@font-face
	{font-family:"\@굴림";
	panose-1:2 11 6 0 0 1 1 1 1 1;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:8.0pt;
	margin-left:0cm;
	text-align:justify;
	text-justify:inter-ideograph;
	line-height:107%;
	text-autospace:none;
	word-break:break-hangul;
	font-size:10.0pt;
	font-family:"맑은 고딕";}
h1
	{mso-style-link:"제목 1 Char";
	margin-right:0cm;
	margin-left:0cm;
	font-size:24.0pt;
	font-family:"굴림",serif;}
span.1Char
	{mso-style-name:"제목 1 Char";
	mso-style-link:"제목 1";
	font-family:"굴림",serif;
	font-weight:bold;}
.MsoChpDefault
	{font-family:"맑은 고딕";}
.MsoPapDefault
	{margin-bottom:8.0pt;
	text-align:justify;
	text-justify:inter-ideograph;
	line-height:107%;}
 /* Page Definitions */
 @page WordSection1
	{size:595.3pt 841.9pt;
	margin:3.0cm 72.0pt 72.0pt 72.0pt;}
div.WordSection1
	{page:WordSection1;}
-->
</style>

<div id="container-wrap"  class="scontainer" >
	<div id="contents">


<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><b><span style='font-size:10.5pt;color:#666666'>개인정보취급방침</span></b></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'><br>
<br>
</span><span style='font-size:10.5pt;line-height:107%;color:#666666'>㈜잡앤파트너<span
lang=EN-US>(</span>이하<span lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;)</span>는
정보통신망 이용촉진 및 정보보호 등에 관한 법률<span lang=EN-US>, </span>제<span lang=EN-US> 27</span>조의<span
lang=EN-US> 2 </span>및 개인정보보호법<span lang=EN-US>, </span>제<span lang=EN-US>30</span>조에
따라 서비스 이용자<span lang=EN-US>(</span>구인자<span lang=EN-US>, </span>구직자<span
lang=EN-US>, </span>일가자 본점 및 지점<span lang=EN-US>)</span>의 개인정보를 보호하고 이와 관련한 규정을
준수하며<span lang=EN-US>, </span>이용자의 권익 보호와 고충을 신속하고 원활하게 처리할 수 있도록 다음과 같은 개인정보처리방침을
수립·공개합니다<span lang=EN-US>. <br>
<br>
</span>제<span lang=EN-US> 1 </span>조 개인정보의 수집 및 이용목적</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>회사는
다음의 목적을 위하여 개인정보를 처리합니다<span lang=EN-US>. </span>처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지
않으며<span lang=EN-US>, </span>이용목적이 변경되는 경우에는 법률에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다<span
lang=EN-US>.</span></span></p>

<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;padding:
  0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>구분</span></p>
  </td>
  <td width=450 style='width:337.65pt;border:solid windowtext 1.0pt;border-left:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>목적</span></p>
  </td>
 </tr>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>회원 가입 및 관리</span></p>
  </td>
  <td width=450 style='width:337.65pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>회원<span lang=EN-US>(</span>구인자<span lang=EN-US>, </span>구직자<span
  lang=EN-US>) </span>가입의사확인<span lang=EN-US>, </span>회원제 서비스 제공에 따른 본인식별인증<span
  lang=EN-US>, </span>회원 자격 유지관리<span lang=EN-US>, </span>서비스 부정이용방지<span
  lang=EN-US>, </span></span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>각종 고지통지<span lang=EN-US>, </span>고충처리<span lang=EN-US>, </span>교육이수
  등</span></p>
  </td>
 </tr>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>서비스<span lang=EN-US>(</span>업무<span lang=EN-US>) </span>제공</span></p>
  </td>
  <td width=450 style='width:337.65pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>연령·성별 인증<span lang=EN-US>, </span>인력 중개서비스 제공<span lang=EN-US>,
  </span>구인<span lang=EN-US>/</span>구직 인력 상담 </span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>현장 출퇴근 확인 정보<span lang=EN-US>, </span>위치기반 서비스 제공<span
  lang=EN-US>, </span>정보콘텐츠 제공<span lang=EN-US>, </span></span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>결제정산정보 제공<span lang=EN-US>, </span>교육이수여부<span lang=EN-US>, </span>판매
  물품 정보 제공<span lang=EN-US>, </span>노무관리제공<span lang=EN-US>, </span>정부기관<span
  lang=EN-US>(</span>시군구청<span lang=EN-US>, </span>국세청<span lang=EN-US>, </span>노동부<span
  lang=EN-US>) </span>노무 관련 신고 등</span></p>
  </td>
 </tr>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>마케팅 및 광고</span></p>
  </td>
  <td width=450 style='width:337.65pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>서비스 접속빈도 파악 또는 회원의 서비스 이용에 대한 통계</span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>이벤트 등 광고성 정보 전달</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
style='font-size:10.5pt;line-height:107%;color:#666666'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US> 2 </span>조 개인정보 수집 항목 및 방법</span></p>

<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=101 style='width:76.1pt;border:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>목적</span></p>
  </td>
  <td width=79 style='width:59.35pt;border:solid windowtext 1.0pt;border-left:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>구분</span></p>
  </td>
  <td width=301 style='width:225.8pt;border:solid windowtext 1.0pt;border-left:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>개인정보항목</span></p>
  </td>
  <td width=119 style='width:89.55pt;border:solid windowtext 1.0pt;border-left:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>수집방법</span></p>
  </td>
 </tr>
 <tr>
  <td width=101 rowspan=2 style='width:76.1pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>구직자 회원가입 및 구직신청</span></p>
  </td>
  <td width=79 style='width:59.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>회원 가입</span></p>
  </td>
  <td width=301 style='width:225.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>휴대폰번호<span lang=EN-US>, </span>비밀번호<span lang=EN-US>, </span>이름<span
  lang=EN-US>, </span>닉네임</span></p>
  </td>
  <td width=119 rowspan=6 style='width:89.55pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>홈페이지<span lang=EN-US>, </span>모바일 애플리케이션<span lang=EN-US>, </span>이메일<span
  lang=EN-US>, </span>서면양식<span lang=EN-US>, </span>상담원을 통한 온라인 상담<span
  lang=EN-US>, </span>이벤트 응모</span></p>
  </td>
 </tr>
 <tr>
  <td width=79 style='width:59.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>구직 신청</span></p>
  </td>
  <td width=301 style='width:225.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>이름<span lang=EN-US>, </span>휴대폰번호<span lang=EN-US>, </span>생년월일<span
  lang=EN-US>, </span>거주 주소<span lang=EN-US>, </span></span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>전문분야<span lang=EN-US>, </span>경력설명<span lang=EN-US>, </span>핸드폰
  기종<span lang=EN-US>, </span>신분증<span lang=EN-US>, </span></span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>기초안전보건교육증<span lang=EN-US>, </span>통장사본</span></p>
  </td>
 </tr>
 <tr>
  <td width=101 rowspan=2 style='width:76.1pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>구인자 회원가입 및 구인신청</span></p>
  </td>
  <td width=79 style='width:59.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>회원 가입</span></p>
  </td>
  <td width=301 style='width:225.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>휴대폰번호<span lang=EN-US>, </span>비밀번호<span lang=EN-US>, </span>이름<span
  lang=EN-US>, </span>상호</span></p>
  </td>
 </tr>
 <tr>
  <td width=79 style='width:59.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>구인 신청</span></p>
  </td>
  <td width=301 style='width:225.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>현장주소<span lang=EN-US>, </span>작업일<span lang=EN-US>, </span>작업일수<span
  lang=EN-US>, </span>작업시간<span lang=EN-US>, </span></span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>구인처명<span lang=EN-US>, </span>신청자명<span lang=EN-US>, </span>이메일<span
  lang=EN-US>, </span>연락처<span lang=EN-US>, </span></span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>사업자등록번호<span lang=EN-US>(</span>임금체불 확인용<span lang=EN-US>)</span></span></p>
  </td>
 </tr>
 <tr>
  <td width=101 style='width:76.1pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>직업상담사 정보 등록 </span></p>
  </td>
  <td width=79 style='width:59.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>상담</span></p>
  </td>
  <td width=301 style='width:225.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>이름<span lang=EN-US>, </span>휴대폰번호<span lang=EN-US>, </span>주민번호<span
  lang=EN-US>, </span>거주 주소<span lang=EN-US>,</span></span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>직업상담사 자격증 정보<span lang=EN-US>, </span>상담 이력</span></p>
  </td>
 </tr>
 <tr>
  <td width=101 style='width:76.1pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>지점 문의</span></p>
  </td>
  <td width=79 style='width:59.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>지점문의</span></p>
  </td>
  <td width=301 style='width:225.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>이름<span lang=EN-US>, </span>휴대폰번호<span lang=EN-US>, </span>이메일<span
  lang=EN-US>, </span>생년월일<span lang=EN-US>, </span></span></p>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>지점 영업 희망처<span lang=EN-US>, </span>직업상담사 자격증번호</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>* </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>회사의 서비스 이용과정에서 서비스 이용기록<span lang=EN-US>, </span>방문기록<span
lang=EN-US>, </span>불량 이용기록<span lang=EN-US>, IP </span>주소<span lang=EN-US>, </span>쿠키<span
lang=EN-US>, MAC</span>주소<span lang=EN-US>, </span>모바일 기기정보<span lang=EN-US>(</span>앱
버전<span lang=EN-US>, OS </span>버전<span lang=EN-US>, </span>디바이스토큰<span
lang=EN-US>), ADID/IDFA(</span>광고식별자<span lang=EN-US>) </span>등이 정보가 자동으로 생성되어 수집될
수 있습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>* </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>일가자 본점 및 지점 활용 동의 <span lang=EN-US>: </span>보다 나은 서비스 제공을 위하여 당사는
회원의 개인정보를 일가자 본점 및 지점에 제공할 수 있습니다<span lang=EN-US>. </span>이 경우 사전에 회원에게 일가자 본점
및 지점에 제공되는 개인정보의 항목<span lang=EN-US>, </span>개인정보 제공 목적<span lang=EN-US>, </span>제공기간<span
lang=EN-US>, </span>개인정보보호방안 등에 대해서 개별적으로 동의를 구하는 절차를 거치게 되며<span lang=EN-US>, </span>회원의
동의가 없는 경우에는 일가자 본점 및 지점에게 이를 제공하지 않으며<span lang=EN-US>, </span>계약 관계의 변화 또는 일가자
본점 및 지점과 계약이 종결될 경우 제공된 개인정보에 대해서는 회원님의 동의가 없는 경우 지체 없이 파기하도록 조치하며 개인정보 제공에 동의하더라도
언제든지 그 동의를 철회할 수 있습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>* </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>이벤트에 따라 수집 항목이 상이할 수 있으므로 응모 시 별도 동의를 받으며<span lang=EN-US>, </span>목적
달성 즉시 파기 합니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US> 3 </span>조 개인정보의 보유 및 이용기간</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>①
회사는 법령에 따른 개인정보 보유·이용 기간 또는 이용자로부터 개인정보 수집 시에 동의 받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다<span
lang=EN-US>.</span></span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>②
각각의 개인정보 처리 및 보유 기간은 다음과 같습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>1) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>회원 가입 및 관리<span lang=EN-US> : </span>회원 탈퇴 시 부정이용 방지를 위하여<span
lang=EN-US> 30</span>일 동안 보관<span lang=EN-US>(</span>휴대폰 번호<span lang=EN-US>, </span>비밀번호<span
lang=EN-US>) </span>후 파기하며<span lang=EN-US>, </span>서비스 제공에 해당하는 경우는 관련 법령에 의거하여
보유 기간 동안 보관 후 파기 합니다<span lang=EN-US>. </span>다만<span lang=EN-US>, </span>다음의 사유에
해당하는 경우에는 해당 사유 종료시까지</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;- </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>관련 법령 위반에 따른 수사 조사 등이 진행중인 경우에는 해당 수사 조사 종료시까지</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;- </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>서비스 이용에 따른 채권 채무관계 잔존시에는 해당 채권 채무 관계 정산시까지</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>2) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>개인정보 유효기간제<span lang=EN-US>(</span>휴면계정정책<span lang=EN-US>) : 1</span>년
이상 서비스 이용 기록이 없는 이용자의 개인정보는 정보통신망법 제<span lang=EN-US>29</span>조에 따라 일반 이용자의 개인정보와
분리<span lang=EN-US>(</span>휴면계정으로 전환<span lang=EN-US>)</span>하여 저장 및 관리됩니다<span
lang=EN-US>. </span>회사는 휴면계정으로 전환되기<span lang=EN-US> 30</span>일 이전<span
lang=EN-US>, </span>해당 내용에 대해 이메일<span lang=EN-US>, </span>문자 등을 통해 이용자에게 사전 통지를
하여 분리 저장된 개인정보는 관련 법령에 특별한 규정이 있는 경우를 제외하고 해당 개인정보를 이용·제공하지 않습니다<span
lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>3) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>서비스 및 재화 제공<span lang=EN-US> : </span>서비스·재화 공급완료 및 수수료결제·정산 완료시까지</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;- </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>비회원 서비스 이용<span lang=EN-US> : </span>업무 목적 달성일로부터<span
lang=EN-US> 5</span>년 보관 후 폐기</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;- </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>입점 문의 <span lang=EN-US>: </span>문의 완료일로부터<span lang=EN-US> 90</span>일
보관 후 파기</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>다만<span
lang=EN-US>, </span>다음의 사유에 해당하는 경우에는 해당 기간 종료시까지</span></p>

<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=236 style='width:176.95pt;border:solid windowtext 1.0pt;padding:
  0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>대상</span></p>
  </td>
  <td width=293 style='width:219.7pt;border:solid windowtext 1.0pt;border-left:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>관련 법 조항</span></p>
  </td>
  <td width=72 style='width:54.15pt;border:solid windowtext 1.0pt;border-left:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>보유기간</span></p>
  </td>
 </tr>
 <tr>
  <td width=236 style='width:176.95pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>계약 철회 등에 관한 기록</span></p>
  </td>
  <td width=293 rowspan=3 style='width:219.7pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>전자상거래등에서 소비자보호에 관한 법률</span></p>
  </td>
  <td width=72 style='width:54.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span lang=EN-US style='font-size:10.5pt;
  color:#666666'>5</span><span style='font-size:10.5pt;color:#666666'>년</span></p>
  </td>
 </tr>
 <tr>
  <td width=236 style='width:176.95pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>수수료 결제 및 재화 등의 공급에 관한 기록</span></p>
  </td>
  <td width=72 style='width:54.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span lang=EN-US style='font-size:10.5pt;
  color:#666666'>5</span><span style='font-size:10.5pt;color:#666666'>년</span></p>
  </td>
 </tr>
 <tr>
  <td width=236 style='width:176.95pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>소비자의 불만 또는 분쟁처리에 관한 기록</span></p>
  </td>
  <td width=72 style='width:54.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span lang=EN-US style='font-size:10.5pt;
  color:#666666'>3</span><span style='font-size:10.5pt;color:#666666'>년</span></p>
  </td>
 </tr>
 <tr>
  <td width=236 style='width:176.95pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>본인 확인에 대한 기록</span></p>
  </td>
  <td width=293 style='width:219.7pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>정보통신 이용촉진 및 정보보호 등에 관한 법률</span></p>
  </td>
  <td width=72 style='width:54.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span lang=EN-US style='font-size:10.5pt;
  color:#666666'>6</span><span style='font-size:10.5pt;color:#666666'>개월</span></p>
  </td>
 </tr>
 <tr>
  <td width=236 style='width:176.95pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>인터넷 로그기록자료<span lang=EN-US>, </span>접속자 추적자료</span></p>
  </td>
  <td width=293 style='width:219.7pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>통신비밀보호법</span></p>
  </td>
  <td width=72 style='width:54.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span lang=EN-US style='font-size:10.5pt;
  color:#666666'>3</span><span style='font-size:10.5pt;color:#666666'>개월</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US> 4 </span>조 개인정보의 파기절차 및 방법</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>①
회사는 개인정보 보유기간의 경과<span lang=EN-US>, </span>처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당
개인정보를 파기 합니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>②
이용자로부터 동의 받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는
해당 개인정보를 별도의 데이터베이스로 욺기거나 보관장소를 달리하여 보존합니다<span lang=EN-US>. </span>별도 보존되는 개인정보는
법률에 의한 경우가 아니고서는 보존목적 이외의 다른 목적으로 이용되지 않습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>③
개인정보 파기의 절차 및 방법은 다음과 같습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>1) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>파기 절차</span></p>

<p class=MsoNormal style='text-indent:10.5pt'><span lang=EN-US
style='font-size:10.5pt;line-height:107%;color:#666666'>- </span><span
style='font-size:10.5pt;line-height:107%;color:#666666'>회사는 파기 사유가 발생한 개인정보를 선정하고<span
lang=EN-US>, </span>회사의 개인정보 보호책임자의 승인을 받아 개인정보를 파기 합니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>2) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>파기 방법</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;&nbsp;- </span><span style='font-size:10.5pt;line-height:
107%;color:#666666'>회사는 전자적 파일형태로 기록·저장된 개인정보는 기록을 재상할 수 없도록 로우레벨포맷<span
lang=EN-US>(Low Level Format) </span>등의 방법을 이용하여 파기하며<span lang=EN-US>, </span>종이
문서에 기록된 개인정보는 분쇄기로 분쇄하거나 소각하여 파기 합니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US> 5 </span>조 개인정보의 제<span lang=EN-US>3</span>자 제공</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>회사는
이용자의 개인정보를 수집 시 명시된 범위 내에서만 처리하며<span lang=EN-US>, </span>이용자의 동의가 있거나 다른 법률에 특별한
규정이 있는 경우에만 개인정보를 제<span lang=EN-US>3</span>자에게 제공합니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US>6</span>조 개인정보처리의 위탁</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>①
회사는 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다<span lang=EN-US>.</span></span></p>

<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;padding:
  0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>위탁 받는 자</span></p>
  </td>
  <td width=450 style='width:337.65pt;border:solid windowtext 1.0pt;border-left:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>위탁하는 업무의 내용</span></p>
  </td>
 </tr>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>㈜ <span lang=EN-US>KT</span></span></p>
  </td>
  <td width=450 style='width:337.65pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>시스템 운영 관리 및 데이터 보관</span></p>
  </td>
 </tr>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>㈜엘지유플러스</span></p>
  </td>
  <td width=450 style='width:337.65pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>문자발송 서비스</span></p>
  </td>
 </tr>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>㈜엘지유플러스</span></p>
  </td>
  <td width=450 style='width:337.65pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>결제 서비스</span></p>
  </td>
 </tr>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>일가자 본점 및 지점</span></p>
  </td>
  <td width=450 style='width:337.65pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>구인 구직 상담</span></p>
  </td>
 </tr>
 <tr>
  <td width=151 style='width:113.15pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>서울보증보험</span></p>
  </td>
  <td width=450 style='width:337.65pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='margin-bottom:0cm;margin-bottom:.0001pt;
  text-align:center;line-height:normal'><span style='font-size:10.5pt;
  color:#666666'>보증보험 발행</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>②
회사는 위탁계약 체결 시 위탁업무 수행목적 외 개인정보 처리금지<span lang=EN-US>, </span>기술적 보호조치<span
lang=EN-US>, </span>재위탁 제한<span lang=EN-US>, </span>수탁자에 대한 관리 감독<span
lang=EN-US>, </span>손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고<span lang=EN-US>, </span>수탁자가
개인정보를 안전하게 처리하는지를 감독하고 있습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>③
위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US>7</span>조 이용자 및 법정대리인의 권리와 그 행사방법</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>①
이용자는 회사에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>1)</span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>개인정보 열람요구</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>2)</span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>오류 등이 있을 경우 정정요구</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>3)</span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>개인정보 삭제요구</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>4)</span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>개인정보 처리정지 요구</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>②
이용자는 홈페이지 등을 통해 <span lang=EN-US>“</span>개인정보수정<span lang=EN-US>”, “</span>회원탈퇴<span
lang=EN-US>”</span>에 대해 서면<span lang=EN-US>, </span>전화<span lang=EN-US>, </span>전자우편<span
lang=EN-US>, </span>팩스 등을 통하여 권리 행사를 하실 수 있으며<span lang=EN-US>, </span>회사는 이에 대해
지체없이 조치하겠습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>③
이용자가 개인정보 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 회사는 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지
않습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>④
이용자에 따른 권리 행사는 이용자의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다<span lang=EN-US>. </span>이
경우 위임장을 제출하셔야 합니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>⑤
이용자는 개인정보 보호 유관법령을 위반하여 회사가 처리하고 있는 이용자 본인이나 타인의 개인정보 및 사생활을 침해하여서는 안됩니다<span
lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US>8</span>조 개인정보 자동수집 장치의 설치<span lang=EN-US>, </span>운영 및 그 거부에 관한 사항</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>①
회사는 이용자에게 개별적인 맞춤서비스를 제공하기 위해 이용정보를 저장하고 수시로 불러오는 <span lang=EN-US>‘</span>쿠키<span
lang=EN-US>(Cookie)’</span>를 사용합니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>1) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>쿠키의 사용목적<span lang=EN-US> : </span>이용자가 방문한 각 서비스와 웹 사이트들에 대한 방문
및 이용형태<span lang=EN-US>, </span>보안접속 여부 등을 파악하여 이용자에게 최적화된 정보 제공을 위해 사용됩니다<span
lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>2) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>쿠키의 설치 운영 및 거부<span lang=EN-US> : </span>웹브라우저 상단의 도구<span
lang=EN-US> &gt; </span>인터넷 옵션<span lang=EN-US> &gt; </span>개인정보 메뉴의 옵션 설정을 통해 쿠키
자동 저장을 거부 할 수 있습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'><br>
</span><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US>9</span>조 모바일 앱 사용 시 광고 식별자<span lang=EN-US>(ADID/IDFA) </span>수집</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>회사는
이용자의 <span lang=EN-US>ADID/IDFA</span>를 수집할 수 있습니다<span lang=EN-US>. ADID/IDFA</span>란
모바일 앱 이용자의 광고 식별값으로<span lang=EN-US>, </span>사용자의 맞춤 서비스 제공이나 맞춤형 광고를 제공하기 위해 수집될
수 있습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>1) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>광고 식별자 거부 </span></p>

<p class=MsoNormal style='text-indent:10.5pt'><span lang=EN-US
style='font-size:10.5pt;line-height:107%;color:#666666'>- Android &gt; </span><span
style='font-size:10.5pt;line-height:107%;color:#666666'>설정<span lang=EN-US>
&gt; </span>구글<span lang=EN-US> &gt; </span>광고<span lang=EN-US> &gt; </span>광고 맞춤설정
선택 해제</span></p>

<p class=MsoNormal style='text-indent:10.5pt'><span lang=EN-US
style='font-size:10.5pt;line-height:107%;color:#666666'>- iOS &gt; </span><span
style='font-size:10.5pt;line-height:107%;color:#666666'>설정<span lang=EN-US>
&gt; </span>개인정보보호<span lang=EN-US> &gt; </span>광고<span lang=EN-US> &gt; </span>광고
추적 제한 </span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US>10</span>조 온라인 맞춤형 광고 서비스</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>회사는
다음과 같이 온라인 맞춤형 광고 사업자가 행태 정보를 수집하도록 허용하고 있습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>1) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>행태정보를 수집 및 처리하는 광고 사업자<span lang=EN-US> : </span>구글<span
lang=EN-US>, </span>페이스북<span lang=EN-US>, NHN, </span>카카오</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>2) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>행태 정보 수집 방법<span lang=EN-US> : </span>이용자가 당사 웹사이트를 방문하거나 앱을 실행할
때 자동 수집 및 전송</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US> 11</span>조 개인정보의 기술적·관리적 보호조치</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>회사는
개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>1) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>관리적 조치<span lang=EN-US> : </span>내부관리계획 수립 시행<span lang=EN-US>, </span>정기적
직원 교육 등</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>2) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>기술적 조치<span lang=EN-US> : </span>개인정보처리시스템 등의 접근권한 관리<span
lang=EN-US>, </span>접근통제시스템 설치<span lang=EN-US>, </span>고유식별정보 등의 암호화<span
lang=EN-US>, </span>보안프로그램 설치 등</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>3) </span><span style='font-size:10.5pt;line-height:107%;
color:#666666'>물리적 조치<span lang=EN-US> : </span>클라우드 시스템<span lang=EN-US>, </span>자료
보관실 등의 접근 통제 등</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:10.5pt;line-height:107%;
color:#666666'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:10.5pt;line-height:107%;color:#666666'>제<span
lang=EN-US> 12</span>조 개인정보관리책임자 및 담당자의 연락처<span lang=EN-US><br>
</span>이용자는 회사의 서비스를 이용하며 발생하는 모든 개인정보보호 관련 민원을 개인정보관리책임자 혹은 담당부서로 신고할 수 있습니다<span
lang=EN-US>. </span>회사는 이용자들의 신고사항에 대해 신속하게 충분한 답변을 드립니다<span lang=EN-US>.<br>
<br>
</span>개인정보 관리책임자<span lang=EN-US><br>
</span>성명<span lang=EN-US> : </span>권성인<span lang=EN-US><br>
</span>소속<span lang=EN-US>/</span>직위<span lang=EN-US> : (</span>주<span
lang=EN-US>)</span>잡앤파트너 <span lang=EN-US>/ </span>사업이사<span lang=EN-US><br>
</span>전자우편<span lang=EN-US> : saint@jnpartner.kr<br>
</span>전화번호<span lang=EN-US> : 070-8666-1634<br>
<br>
</span>개인정보 보호 관리 담당자<span lang=EN-US><br>
</span>성명<span lang=EN-US>: </span>장재호<span lang=EN-US><br>
</span>소속<span lang=EN-US>/</span>직위<span lang=EN-US>: </span>㈜잡앤파트너<span
lang=EN-US> / </span>개발이사<span lang=EN-US><br>
</span>전자우편<span lang=EN-US>: jhjang@jnpartner.kr<br>
</span>전화번호<span lang=EN-US> : 070-8666-1634<br>
<br>
</span>기타 개인정보침해에 대한 신고나 상담이 필요하신 경우에는 아래 기관에 문의하시기 바랍니다<span lang=EN-US>.<br>
-</span>개인정보침해신고센터<span lang=EN-US> ( / 118)<br>
-</span>정보보호마크인증위원회<span lang=EN-US> ( / 02-580-0533~4)<br>
-</span>대검찰청 첨단범죄수사과<span lang=EN-US> ( / 02-3480-2000)<br>
-</span>경찰청 사이버테러대응센터<span lang=EN-US> ( / 02-392-0330)<br>
<br>
</span>제<span lang=EN-US> 13</span>조 고지의 의무<span lang=EN-US><br>
</span>현 개인정보취급방침의 내용 추가<span lang=EN-US>, </span>삭제 및 수정이 있을 시에는 시행일자 최소<span
lang=EN-US> 7</span>일전부터 회사의 웹사이트<span lang=EN-US> ,</span>회사가 제공하는 서비스 내 “공지사항”
화면을 통해 공고할 것입니다<span lang=EN-US>.<br>
- </span>공고일자<span lang=EN-US>: 2019</span>년<span lang=EN-US> 7 </span>월<span
lang=EN-US> 24</span>일<span lang=EN-US><br>
- </span>시행일자<span lang=EN-US>: 2019</span>년<span lang=EN-US> 8 </span>월<span
lang=EN-US> 01</span>일</span></p>



	</div>
</div>
<!-- e: #container-wrap //-->