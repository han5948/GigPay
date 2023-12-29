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
	{font-family:"MS Mincho";
	panose-1:2 2 6 9 4 2 5 8 3 4;}
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
@font-face
	{font-family:"\@MS Mincho";
	panose-1:2 2 6 9 4 2 5 8 3 4;}
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
	font-family:굴림;}
a:link, span.MsoHyperlink
	{color:#0563C1;
	text-decoration:underline;}
a:visited, span.MsoHyperlinkFollowed
	{color:#954F72;
	text-decoration:underline;}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:8.0pt;
	margin-left:40.0pt;
	text-align:justify;
	text-justify:inter-ideograph;
	line-height:107%;
	text-autospace:none;
	word-break:break-hangul;
	font-size:10.0pt;
	font-family:"맑은 고딕";}
span.1Char
	{mso-style-name:"제목 1 Char";
	mso-style-link:"제목 1";
	font-family:굴림;
	font-weight:bold;}
span.num
	{mso-style-name:num;}
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
 /* List Definitions */
 ol
	{margin-bottom:0cm;}
ul
	{margin-bottom:0cm;}
-->
</style>
<div id="container-wrap"  class="scontainer" >
	<div id="contents">


<div class=WordSection1>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><b><span style='font-size:10.5pt;color:#666666'>서비스 이용약관</span></b></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'><br>
</span><span style='font-size:10.5pt;color:#666666'>제<span lang=EN-US> 1 </span>조<span
lang=EN-US> (</span>목적<span lang=EN-US>)<br>
</span>본 이용약관은 ㈜잡앤파트너<span lang=EN-US>(</span>이하<span lang=EN-US> &quot;</span>회사<span
lang=EN-US>&quot;)</span>가 운영하는 일가자 구인<span lang=EN-US>, </span>일가자 구직 서비스<span
lang=EN-US>(</span>이하 <span lang=EN-US>“</span>서비스<span lang=EN-US>”)</span>와 관련하여
이를 이용하는 구인자와 구직자<span lang=EN-US>(</span>이하 <span lang=EN-US>“</span>이용자<span
lang=EN-US>”)</span>의 이용 조건 및 제반 절차<span lang=EN-US>, </span>권리<span
lang=EN-US>, </span>의무 및 책임사항<span lang=EN-US>, </span>기타 필요한 사항을 규정함을 목적으로 한다<span
lang=EN-US>.<br>
<br>
</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>제<span
lang=EN-US> 2 </span>조<span lang=EN-US>(</span>용어의 정리<span lang=EN-US>)</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>이 약관에서 사용하는 용어의
정의는 아래와 같다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>①<span
lang=EN-US> “</span>사이트<span lang=EN-US>” </span>및 <span lang=EN-US>“</span>앱<span
lang=EN-US>”</span>이라 함은 회사가 서비스를 이용자에게 </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>제공하기 위하여 컴퓨터 등 정보통신설비를 이용하여
설정한 가상의 영업장 또는 회사가 운영하는 웹사이트<span lang=EN-US>, </span>모바일웹<span lang=EN-US>, </span>앱
등의 서비스를 제공하는 모든 매체를 통칭하며<span lang=EN-US>, </span>통합된 하나의 회원 계정<span
lang=EN-US>(</span>아이디 및 비밀번호<span lang=EN-US>)</span>을 이용하여 서비스를 제공받을 수 있는 아래의
사이트를 말한다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666;
letter-spacing:-.4pt;background:white'>- www.ilgaja.com</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666;
letter-spacing:-.4pt;background:white'>- www.jnpartner.kr</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666;
letter-spacing:-.4pt;background:white'>- m.ilgaja.com</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666;
letter-spacing:-.4pt;background:white'>- m.jnpartner.kr</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666;
letter-spacing:-.4pt;background:white'>- </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>일가자 구인 앱</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666;
letter-spacing:-.4pt;background:white'>- </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>일가자 구직 앱</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666;
letter-spacing:-.4pt;background:white'>&nbsp;</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666;letter-spacing:
-.4pt;background:white'>② </span><span lang=EN-US style='font-size:10.5pt;
color:#666666'>&quot;</span><span style='font-size:10.5pt;color:#666666'>서비스<span
lang=EN-US>&quot;</span>라 함은 회사가 운영하는 사이트와 앱을 통해 구인자가 등록하는 구인 정보를 구직자에게 제공 및 소개하는
서비스 및 </span><span style='font-size:10.5pt;color:#666666;letter-spacing:-.4pt;
background:white'>구직<span lang=EN-US>, </span>교육 등의 목적으로 등록하는 자료를<span
lang=EN-US> DB</span>화하여 각각의 목적에 맞게 분류 가공<span lang=EN-US>, </span>집계하여 정보를 제공하는
모든 부대 서비스를 말한다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666;letter-spacing:
-.4pt;background:white'>③</span><span style='font-size:10.5pt;font-family:"Arial",sans-serif;
color:#666666;letter-spacing:-.4pt;background:white'> <span lang=EN-US>“</span></span><span
style='font-size:10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>회원</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>” </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>이라</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>함은</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> <span lang=EN-US>&quot;</span></span><span
style='font-size:10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>회사</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>가</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>제공하는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>서비스를</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>이용하거나</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>이용하려는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>자로</span><span lang=EN-US
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>, &quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>회사</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>와</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>이용</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>계약을</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>체결한자</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>또는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>체결하려는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>자를</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>포함하며</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>, </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>휴대전화번호</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>또는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>사업자번호와</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>비밀번호의</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>설정</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>등</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>회원가입</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>절차를</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>거쳐</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>회원가입확인</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>휴대전화번호</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>등을</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>통해</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>회사로부터</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>회원으로</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>인정받은</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> &quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>구인</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>회원</span><span lang=EN-US
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;, “</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>구직</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>회원</span><span lang=EN-US
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>”</span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>을</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>말한다</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>.</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666;letter-spacing:
-.4pt;background:white'>④</span><span lang=EN-US style='font-size:10.5pt;
font-family:"Arial",sans-serif;color:#666666;letter-spacing:-.4pt;background:
white'> &quot;</span><span style='font-size:10.5pt;color:#666666;letter-spacing:
-.4pt;background:white'>아이디</span><span lang=EN-US style='font-size:10.5pt;
font-family:"Arial",sans-serif;color:#666666;letter-spacing:-.4pt;background:
white'>&quot;</span><span style='font-size:10.5pt;color:#666666;letter-spacing:
-.4pt;background:white'>라</span><span style='font-size:10.5pt;font-family:"Arial",sans-serif;
color:#666666;letter-spacing:-.4pt;background:white'> </span><span
style='font-size:10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>함은</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666'>함은 회원의 식별과 회원의 서비스 이용을 위하여 이용자의 휴대폰 본인확인을 통해 인증된 고유 휴대전화번호 또는 구인자의
사업자번호를 말한다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666;letter-spacing:
-.4pt;background:white'>⑤</span><span style='font-size:10.5pt;font-family:"Arial",sans-serif;
color:#666666;letter-spacing:-.4pt;background:white'> <span lang=EN-US>“</span></span><span
style='font-size:10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>비밀번호</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>”</span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>라</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>함은</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>위</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>제</span><span lang=EN-US
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>4</span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>항에</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>따라</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>회원이</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>회원</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>가입시</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>아이디를</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>설정하면서</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>아이디를</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>부여받은</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>자와</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>동일인임을</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>확인하고</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> &quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>회원</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>의</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>권익을</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>보호하기</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>위하여</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> &quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>회원</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>이</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>선정한</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>문자와</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>숫자의</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>조합을</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>말한다</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>.</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>⑥<span
lang=EN-US> &quot;</span>구인자<span lang=EN-US>&quot;</span>라 함은 일용직 근로자를 구하기 위해 동
약관에 동의하고<span lang=EN-US> &quot;</span>구인자<span lang=EN-US> ID&quot;</span>를 부여받은
이용자를 말합니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>⑦<span
lang=EN-US> &quot;</span>지점<span lang=EN-US>&quot;</span>이라 함은 일용근로자 일자리를 중개 위하여
동 약관에 동의하고<span lang=EN-US> “</span>아이디<span lang=EN-US>&quot;</span>를 부여받은 구인자<span
lang=EN-US>(</span>법인<span lang=EN-US>, </span>개인<span lang=EN-US>)</span>와 구직자
개인을 중개하는 일가자 직업소개소를 말한다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>⑧<span
lang=EN-US> &quot;</span>일 중개<span lang=EN-US>&quot;</span>라 함은 구인자가 일용직<span
lang=EN-US>, </span>시간제 근로자를 구하기 위해 관련 정보를 일가자 서비스에 제공하여 구직자가 확인할 수 있도록 제공하고 중개하는
것을 말한다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>제<span
lang=EN-US> 3 </span>조<span lang=EN-US> (</span>이용약관의 효력 및 변경<span lang=EN-US>)<br>
</span>① 본 약관은 서비스를 신청한 고객 또는 개인정보주체가 본 약관에 동의하고 회사가 정한 소정의 절차에 따라 서비스의 이용자로 등록함으로써
효력이 발생합니다<span lang=EN-US>.<br>
</span>② 회원이 온라인에서 본 약관의<span lang=EN-US> &quot;</span>동의<span lang=EN-US>&quot;
</span>버튼을 클릭하였을 경우 본 약관의 내용을 모두 읽고 이를 충분히 이해하였으며<span lang=EN-US>, </span>그 적용에
동의한 것으로 봅니다<span lang=EN-US>.<br>
</span>③ 회사는 위치정보의 보호 및 이용 등에 관한 법률<span lang=EN-US>, </span>콘텐츠산업진흥법<span
lang=EN-US>, </span>전자상거래 등에서의 소비자보호에 관한 법률<span lang=EN-US>, </span>소비자기본법<span
lang=EN-US>, </span>직업정보제공사업 업무처리 규정 약관의 규제에 관한 법률 등 관련법령을 위배하지 않는 범위에서 본 약관을 개정할
수 있습니다<span lang=EN-US>.<br>
</span>④ 회사가 약관을 개정할 경우에는 기존 약관과 개정 약관 및 개정약관의 적용일자와 개정사유를 명시하여 현행 약관과 함께 그 적용일자<span
lang=EN-US> 10</span>일 전부터 적용일 이후 상당한 기간 동안 공지만을 하고<span lang=EN-US>, </span>개정
내용이 회원에게 불리한 경우에는 그 적용일자<span lang=EN-US> 30</span>일 전부터 적용일 이후 상당한 기간 동안 각각 이를
서비스 홈페이지에 게시하거나 회원에게 전자적 형태<span lang=EN-US>(</span>전자우편<span lang=EN-US>, SMS,
</span>카카오톡 등<span lang=EN-US>)</span>로 약관 개정 사실을 발송하여 고지합니다<span lang=EN-US>.<br>
</span>⑤ 회사가 전항에 따라 회원에게 통지하면서 공지 또는 공지&#8729;고지일로부터 개정약관 시행일<span lang=EN-US> 7</span>일
후까지 거부의사를 표시하지 아니하면 이용약관에 승인한 것으로 봅니다<span lang=EN-US>. </span>회원이 개정약관에 동의하지 않을
경우 회원은 이용계약을 해지할 수 있습니다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 4 </span>조<span lang=EN-US> (</span>관계법령의 적용<span
lang=EN-US>)<br>
</span>① 이 약관에서 규정하지 않은 사항에 관해서는 약관의 규제에 관한 법률<span lang=EN-US>, </span>전기통신기본법<span
lang=EN-US>, </span>전기통신사업법<span lang=EN-US>, </span>정보통신망 이용촉진 및 정보보호 등에 관한 법률
등의 관계법령에 따른다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>② </span><span
style='font-size:10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>각</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>사이트</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>및</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>앱</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>서비스</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>이용약관이</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>있는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>경우에는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>서비스</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>이용약관이</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>우선한다</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>.</span><span lang=EN-US
style='font-size:10.5pt;color:#666666'><br>
</span><span class=num><span lang=EN-US style='font-size:10.5pt;color:#666666;
letter-spacing:-.4pt;background:white'>③ </span></span><span lang=EN-US
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>회원</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>이</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> &quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>회사</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>와</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>개별</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>계약을</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>체결하여</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>서비스를</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>이용하는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>경우에는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>개별</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>계약이</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>우선한다</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>.</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;
color:#666666;letter-spacing:-.4pt;background:white'>&nbsp;</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>제<span
lang=EN-US> 5 </span>조<span lang=EN-US> (</span>서비스의 내용<span lang=EN-US>)<br>
</span>회사가 제공하는 서비스는 아래와 같습니다<span lang=EN-US><br>
</span>서비스 명<span lang=EN-US> <br>
&nbsp;- </span>일가자 구인<span lang=EN-US><br>
&nbsp;- </span>일가자 구직</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>일가자 인력</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>일가자 기공</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>일가자 파출</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>일가자 간병</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>서비스 내용</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>구인자 신청 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>구인자 등록 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>구인자 상담 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>구인자 정보 등록 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>구직자 상담 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>구직자 정보 등록 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>일자리 구함<span lang=EN-US>(</span>구직
신청<span lang=EN-US>) </span>서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>구인자<span lang=EN-US>-</span>구직자
상호 평가 및 이용 후기 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>노무<span lang=EN-US>, </span>입금
대장 제공 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>사회보험 관리 및 전자문서 제공 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>일가자인력 지점의 일자리 매칭 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>일가자인력 지점 평가 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>위치 기반 출역근태관리 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666'>전문 기술직 헤드헌팅<span
lang=EN-US>/</span>아웃소싱 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;-
</span><span style='font-size:10.5pt;color:#666666;letter-spacing:-.4pt;
background:white'>기타</span><span lang=EN-US style='font-size:10.5pt;font-family:
"Arial",sans-serif;color:#666666;letter-spacing:-.4pt;background:white'> &quot;</span><span
style='font-size:10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>회사</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>가</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>추가</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>개발하거나</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>제휴</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>계약</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>등을</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>통해</span><span lang=EN-US
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> &quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>회원</span><span
lang=EN-US style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'>&quot;</span><span style='font-size:
10.5pt;color:#666666;letter-spacing:-.4pt;background:white'>에게</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>제공하는</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>일체의</span><span
style='font-size:10.5pt;font-family:"Arial",sans-serif;color:#666666;
letter-spacing:-.4pt;background:white'> </span><span style='font-size:10.5pt;
color:#666666;letter-spacing:-.4pt;background:white'>서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>일가자 구인<span
lang=EN-US>, </span>일가자 구직 서비스는 구인자와 일가자 본점과 지점<span lang=EN-US>(</span>직업소개소<span
lang=EN-US>)</span>을 통해 구직자<span lang=EN-US>(</span>일용근로자<span lang=EN-US>)</span>들에게
쉽고<span lang=EN-US>, </span>빠르게 일감 정보를 제공하고 구직자의 출역 및 관리<span lang=EN-US>, </span>노무
서비스를 지원하는 전국 현장 인력 실시간 중개망 서비스</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'><br>
</span><span style='font-size:10.5pt;color:#666666'>제<span lang=EN-US> 6 </span>조<span
lang=EN-US> (</span>일용직 구직·구인을 위한 이용자의 개인정보 및 위치정보 제공<span lang=EN-US>)<br>
</span>① 일가자 본점<span lang=EN-US>, </span>지점의 근무 요청 알림에 응답하고 개인정보 제공에 동의한 이용자의 개인정보<span
lang=EN-US>(</span>이름<span lang=EN-US>, </span>연락처<span lang=EN-US>, </span>위치정보<span
lang=EN-US>)</span>는 해당 근무 요청을 한 구인자<span lang=EN-US> &amp; </span>일가자 본점 및 지점에
제공되며 구인자와 일가자 본점 및 지점은 일용직 구인을 위해 해당 구직자의 개인정보<span lang=EN-US>(</span>이름<span
lang=EN-US>, </span>생년<span lang=EN-US>, </span>연락처<span lang=EN-US>)</span>를 저장·관리할
수 있습니다<span lang=EN-US>.<br>
</span>② 일자리 정보를 제공받기 위하여 이용자의 상태 정보 설정에 따라 개인정보<span lang=EN-US>(</span>이름<span
lang=EN-US>, </span>생년월일<span lang=EN-US>, </span>위치정보<span lang=EN-US>)</span>는
구인자<span lang=EN-US> &amp; </span>직업소개소 회원에게 제공될 수 있습니다<span lang=EN-US>. <br>
</span>제공받는 자<span lang=EN-US>: </span>일가자 구인자<span lang=EN-US> &amp; </span>직업소개소
회원<span lang=EN-US><br>
</span>제공받는 목적<span lang=EN-US>: </span>개인회원의 일용직 구직 의사에 따른 구직활동<span
lang=EN-US><br>
</span>제공하는 항목<span lang=EN-US>: </span>개인회원이 구직활동을 위해 내 상태 정보 메뉴에 공개한 정보<span
lang=EN-US><br>
<br>
</span>제<span lang=EN-US> 7 </span>조<span lang=EN-US> (</span>서비스 이용신청<span
lang=EN-US>)<br>
</span>①회원으로 가입하여 본 서비스를 이용하고자 하는 이용고객은 회사에서 요청하는 제반 정보<span lang=EN-US>(</span>이름<span
lang=EN-US>, </span>생년<span lang=EN-US>, </span>휴대폰 번호<span lang=EN-US>, </span>위치정보<span
lang=EN-US>, </span>경력 등<span lang=EN-US>)</span>를 제공하여야 합니다<span lang=EN-US>.<br>
</span>②실명으로 등록하지 않은 사용자는 일체의 권리를 주장할 수 없습니다<span lang=EN-US>.<br>
</span>③타인의 명의를 도용하여 이용신청을 한 회원의 모든<span lang=EN-US> ID</span>는 삭제되며<span
lang=EN-US>, </span>관계법령에 따라 처벌을 받을 수 있습니다<span lang=EN-US>.<br>
</span>④만<span lang=EN-US> 18</span>세 미만 자의 회원가입은 금지되며 성명<span lang=EN-US>, </span>연령<span
lang=EN-US>, </span>기타의 정보를 허위로 기재한 경우에는 이용정지<span lang=EN-US>, </span>강제탈퇴<span
lang=EN-US>, </span>손해배상청구 등 불이익이 부과될 수 있습니다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 8 </span>조<span lang=EN-US> (</span>이용신청의 승낙과 제한<span
lang=EN-US>)<br>
</span>①회사는 전조의 규정에 이한 이용신청 고객에 대하여 업무 수행 상 또는 기술상 지장이 없는 경우에는 원칙적으로 접수순서에 따라 서비스
이용을 승낙하게 됩니다<span lang=EN-US>.<br>
</span>②회사는 아래사항에 해당하는 경우에 대해서는 이용신청을 승낙하지 않습니다<span lang=EN-US>.<br>
1. </span>실명이 아니거나 타인의 명의를 이용하여 신청한 경우<span lang=EN-US><br>
2. </span>구인<span lang=EN-US>-</span>구직 신청 내용을 허위로 기재한 경우<span lang=EN-US><br>
</span>③ 회사는 아래사항에 해당하는 경우에는 그 신청에 대하여 승낙제한 사유가 해소될 때까지 승낙을 유보할 수 있습니다<span
lang=EN-US>.<br>
1. </span>회사가 설비의 여유가 없는 경우<span lang=EN-US><br>
2. </span>회사의 기술상 지장이 있는 경우<span lang=EN-US><br>
3. </span>기타 회사의 사정<span lang=EN-US>(</span>회사의 귀책 사유 있는 경우도 포함<span
lang=EN-US>)</span>으로 이용승낙이 곤란한 경우<span lang=EN-US><br>
<br>
</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>제<span
lang=EN-US> 9</span>조<span lang=EN-US> (</span>이용요금<span lang=EN-US>)</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>① <span
lang=EN-US>&quot;</span>회원<span lang=EN-US>&quot; </span>가입과 구인<span
lang=EN-US>-</span>구직 정보 등록은 무료이다<span lang=EN-US>. </span>다만 회사는 구직자에게 보다 효과적으로
구인 정보를 빠르게 노출시키기 위한 별도의 광고서비스나 구직자가 우선적으로 구인정보를 받을 수 있는 특화서비스를 유료로 제공할 수 있다<span
lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>②<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>는 유료서비스를 제공할 경우 사이트에 요금에
대해서 공지를 하여야 한다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>③<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>는 유료서비스 이용금액을 서비스의 종류 및
기간에 따라<span lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>가 예고 없이 변경할
수 있다<span lang=EN-US>. </span>다만<span lang=EN-US>, </span>변경 이전에 적용 또는 계약한 금액은 소급하여
적용하지 아니한다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>④ 유료 서비스 신청 후<span
lang=EN-US> &quot;</span>회원<span lang=EN-US>&quot; </span>사정에 의해 서비스가 취소될 경우<span
lang=EN-US>, &quot;</span>회사<span lang=EN-US>&quot;</span>는 내부 규정 범위 내에서 시간 별로 환불
수수료를 차등 부과할 수 있다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'><br>
</span><span style='font-size:10.5pt;color:#666666'>제<span lang=EN-US> 10 </span>조<span
lang=EN-US> (</span>제휴를 통한 서비스<span lang=EN-US>)<br>
</span>①<span lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>는 제휴 관계를
체결한 여타 인터넷 웹 사이트 및 채용박람회 또는 신문<span lang=EN-US>, </span>잡지 등의 오프라인 매체를 통해 사이트에 등록한<span
lang=EN-US> &quot;</span>회원<span lang=EN-US>&quot;</span>의 구인<span lang=EN-US>-</span>구직
정보가 열람될 수 있도록 서비스를 제공할 수 있다<span lang=EN-US>. </span>단<span lang=EN-US>, </span>제휴
서비스를 통해 노출되는 연락처 정보는<span lang=EN-US> “</span>회원<span lang=EN-US>”</span>이 구인<span
lang=EN-US>-</span>구직 등록 시 선택한 연락처 공개 여부에 따라 제공된다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>②<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>는 제휴를 통해 타 사이트 및 매체에 등록될
수 있음을 고지해야 하며<span lang=EN-US>, </span>제휴 사이트 목록을 사이트 내에서 상시 열람할 수 있도록 해야 한다<span
lang=EN-US>. </span>단<span lang=EN-US>, </span>등록의 형태가<span lang=EN-US> &quot;</span>회사<span
lang=EN-US>&quot;</span>가 직접 구축하지 않고<span lang=EN-US>, </span>제휴사가 연동 규격 또는<span
lang=EN-US> API </span>형태로<span lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>로부터
제공 받아 구축한 매체 목록은 본 약관 외 별도의 제휴리스트에서 열람할 수 있도록 한다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>③<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>는<span lang=EN-US>
&quot;</span>회원<span lang=EN-US>&quot;</span>이 공개를 요청한 구인<span lang=EN-US>-</span>구직
정보에 한해 제휴를 맺은 타 사이트에 관련 정보를 제공한다<span lang=EN-US>. (</span>본 약관 시행일 현재에는 제휴를 통한
타 사이트 및 매체는 없다<span lang=EN-US>.)</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>④ 본 조 제<span
lang=EN-US> 3</span>항 제휴를 통한 사이트의 변동사항 발생 시 공지사항을 통해 고지 후 진행 한다<span
lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>제<span
lang=EN-US> 11 </span>조<span lang=EN-US> (</span>정보의 제공 및 광고의 게재<span
lang=EN-US>)</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>①<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>는<span lang=EN-US>
&quot;</span>회원<span lang=EN-US>&quot;</span>에게 서비스 이용에 필요가 있다고 인정되거나 서비스 개선 및 회원
대상의 서비스 소개 등의 목적으로 하는 각종 정보에 대해서 전자우편이나 서신우편을 이용한 방법으로 제공할 수 있다<span
lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>②<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>는 제공하는 서비스와 관련되는 정보 또는
광고를 서비스 화면<span lang=EN-US>, </span>홈페이지<span lang=EN-US>, </span>전자우편 등에 게재할 수
있으며<span lang=EN-US>, </span>광고가 게재된 전자우편을 수신한<span lang=EN-US> &quot;</span>회원<span
lang=EN-US>&quot;</span>은 수신거절을<span lang=EN-US> &quot;</span>회사<span
lang=EN-US>&quot;</span>에게 할 수 있다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>③<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>는 서비스상에 게재되어 있거나 본 서비스를
통한 광고주의 판촉활동에<span lang=EN-US> &quot;</span>회원<span lang=EN-US>&quot;</span>이 참여하거나
교신 또는 거래를 함으로써 발생하는 모든 손실과 손해에 대해 책임을 지지 않는다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>④ 본 서비스의<span
lang=EN-US> &quot;</span>회원<span lang=EN-US>&quot;</span>은 서비스 이용 시 노출되는 광고게재에 대해
동의 하는 것으로 간주한다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 12 </span>조<span lang=EN-US> (</span>서비스 내용 변경 통지 등<span
lang=EN-US>)<br>
</span>①회사가 서비스 내용을 변경하거나 종료하는 경우 회사는 회원의 등록된 연락처 또는 전자우편 주소로 문자 또는 이메일을 통하여 서비스
내용의 변경 사항 또는 종료를 통지할 수 있습니다<span lang=EN-US>.<br>
</span>②①항의 경우 불특정 다수인을 상대로 통지를 함에 있어서는 웹사이트 등 기타 회사의 공지사항을 통하여 회원들에게 통지할 수 있습니다<span
lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 13 </span>조<span lang=EN-US> (</span>서비스 이용시간<span
lang=EN-US>)<br>
</span>①회사는 특별한 사유가 없는 한 연중무휴<span lang=EN-US>, 1</span>일<span lang=EN-US> 24</span>시간
서비스를 제공합니다<span lang=EN-US>. </span>다만<span lang=EN-US>, </span>회사는 서비스의 종류나 성질에
따라 제공하는 서비스 중 일부에 대해서는 별도로 이용시간을 정할 수 있으며<span lang=EN-US>, </span>이 경우 회사는 그 이용시간을
사전에 회원에게 공지 또는 통지하여야 합니다<span lang=EN-US>.<br>
</span>②회사는 자료의 가공과 갱신을 위한 시스템 작업시간<span lang=EN-US>, </span>장애해결을 위한 보수작업 시간<span
lang=EN-US>, </span>정기 운영유지보수 작업<span lang=EN-US>, </span>시스템 교체작업<span
lang=EN-US>, </span>회선 장애 등이 발생한 경우 일시적으로 서비스를 중단할 수 있으며 계획된 작업의 경우 공지에 서비스 중단 시간과
작업 내용을 알려야 합니다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 14 </span>조<span lang=EN-US> (</span>서비스이용의 제한 및 중지<span
lang=EN-US>)<br>
</span>①회사는 아래 각 호의<span lang=EN-US> 1</span>에 해당하는 사유가 발생한 경우에는 회원의 서비스 이용을 제한하거나
중지시킬 수 있습니다<span lang=EN-US>.<br>
1. </span>회원이 회사 서비스의 운영을 고의 또는 중과실로 방해하는 경우<span lang=EN-US><br>
2. </span>서비스용 설비 점검<span lang=EN-US>, </span>보수 또는 공사로 인하여 부득이한 경우<span
lang=EN-US><br>
3. </span>전기통신사업법에 규정된 기간통신사업자가 전기통신 서비스를 중지했을 경우<span lang=EN-US><br>
4. </span>국가비상사태<span lang=EN-US>, </span>서비스 설비의 장애 또는 서비스 이용의 폭주 등으로 서비스 이용에 지장이
있는 때<span lang=EN-US><br>
5. </span>기타 중대한 사유로 인하여 회사가 서비스 제공을 지속하는 것이 부적당하다고 인정하는 경우<span lang=EN-US><br>
</span>②회사는 전항의 규정에 의하여 서비스의 이용을 제한하거나 중지한 때에는 그 사유 및 제한기간 등을 회원에게 알려야 합니다<span
lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 15 </span>조<span lang=EN-US> (</span>자료 내용의 책임과 회사의 정보
수정 권한<span lang=EN-US>)<br>
</span>①자료내용은 회원이 등록한 개인정보 및 구인을 위한 일자리의 정보를 말합니다<span lang=EN-US>.<br>
</span>②회원은 자료내용 및 일자리 정보를 사실에 근거하여 성실하게 작성해야 하며<span lang=EN-US>, </span>만일 자료의
내용이 사실이 아니거나 부정확하게 작성되어 발생하는 모든 책임은 회원에게 있습니다<span lang=EN-US>.<br>
</span>③모든 자료내용의 관리와 작성은 회원 본인이 하는 것이 원칙이나 사정상 위탁 또는 대행관리를 하더라도 자료내용의 책임은 회원에게 있으며
회원은 주기적으로 자신의 자료를 확인하여 항상 정확하게 관리가 되도록 노력해야 합니다<span lang=EN-US>.<br>
</span>④회사는 개인회원이 등록한 자료 내용에 오자<span lang=EN-US>, </span>탈자 또는 사회적 통념에 어긋나는 문구가
있을 경우 이를 언제든지 수정할 수 있습니다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 16 </span>조<span lang=EN-US> (</span>회사의 의무<span
lang=EN-US>)<br>
</span>①회사는 본 약관에서 정한 바에 따라 계속적<span lang=EN-US>, </span>안정적으로 서비스를 제공할 수 있도록 최선의
노력을 다할 것입니다<span lang=EN-US>.<br>
</span>②회사는 서비스와 관련한 회원의 불만사항이 접수되는 경우 이를 즉시 처리하여야 하며<span lang=EN-US>, </span>즉시
처리가 곤란한 경우에는 그 사유와 처리일정을 서비스 화면 또는 기타 방법을 통해 동 회원에게 통지하여야 합니다<span lang=EN-US>.<br>
</span>③회사는 유료 결제와 관련한 결제 사항 정보를 상법 등 관련법령의 규정에 의하여<span lang=EN-US> 5</span>년 이상
보존합니다<span lang=EN-US>.<br>
</span>④천재지변 등 예측하지 못한 일이 발생하거나 시스템의 장애가 발생하여 서비스가 중단될 경우 이에 대한 손해에 대해서는 회사가 책임을
지지 않습니다<span lang=EN-US>. </span>다만 자료의 복구나 정상적인 서비스 지원이 되도록 최선을 다할 의무를 가집니다<span
lang=EN-US>.<br>
</span>⑤ 회원의 자료를 본 서비스 이외의 목적으로 제<span lang=EN-US>3</span>자에게 제공하거나 열람 시킬 경우 반드시
회원의 동의를 얻어야 합니다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 17 </span>조<span lang=EN-US> (</span>회원의 의무<span
lang=EN-US>)<br>
</span>①회원은 관계법령과 본 약관의 규정 및 기타 회사가 통지하는 사항을 준수하여야 하며<span lang=EN-US>, </span>기타
회사의 업무에 방해되는 행위를 해서는 안 됩니다<span lang=EN-US>.<br>
</span>②회원은 본 서비스를 건전한 구인 구직 이외의 목적으로 사용해서는 안 되며 이용 중 다음 각 호의 행위를 해서는 안 됩니다<span
lang=EN-US>.<br>
1. </span>다른 회원의 아이디를 부정 사용하는 행위<span lang=EN-US><br>
2. </span>범죄 행위을 목적으로 하거나 기타 범죄행위와 관련된 행위<span lang=EN-US><br>
3. </span>타인의 명예를 훼손하거나 모욕하는 행위<span lang=EN-US><br>
4. </span>타인의 지적재산권 등의 권리를 침해하는 행위<span lang=EN-US><br>
5. </span>해킹행위 또는 바이러스의 유포 행위<span lang=EN-US><br>
6. </span>타인의 의사에 반하여 광고성 정보 등 일정한 내용을 계속적으로 전송하는 행위<span lang=EN-US><br>
7. </span>서비스의 안정적인 운영에 지장을 주거나 줄 우려가 있다고 판단되는 행위<span lang=EN-US><br>
8. </span>사이트의 정보 및 서비스를 이용한 영리 행위<span lang=EN-US><br>
9. </span>그 외에 선량한 풍속<span lang=EN-US>, </span>기타 사회질서를 해하거나 관계법령에 위반하는 행위<span
lang=EN-US><br>
<br>
</span>제<span lang=EN-US> 18 </span>조<span lang=EN-US> (</span>회원의 가입해지<span
lang=EN-US>/</span>서비스중지<span lang=EN-US>/</span>자료삭제<span lang=EN-US>)<br>
</span>①<b>회원이 가입 해지를 하고자 할 때는 전화 또는 모바일 앱 상의 환경설정에서<span lang=EN-US> &quot;</span>탈퇴하기<span
lang=EN-US>&quot; </span>메뉴를 이용해 가입 해지 신청을 하면 됩니다<span lang=EN-US>.</span></b><span
lang=EN-US><br>
</span>②<span lang=EN-US>&quot;</span>회원<span lang=EN-US>&quot;</span>이 회원을 탈퇴할
경우<span lang=EN-US>, </span>관련법 및 개인정보취급방침에 따라<span lang=EN-US> &quot;</span>회사<span
lang=EN-US>&quot;</span>가 회원정보를 보유하는 경우를 제외하고는 해지 즉시<span lang=EN-US> &quot;</span>회원<span
lang=EN-US>&quot;</span>의 모든 데이터는 소멸됩니다<span lang=EN-US>.</span></span><span
lang=EN-US style='font-size:10.5pt;color:#00B0F0'><br>
</span><span style='font-size:10.5pt;color:#666666'>③다음의 사항에 해당하는 경우 회사는 사전 동의 없이
가입해지나 서비스 중지<span lang=EN-US>, </span>이력서 삭제 조치를 취할 수 있습니다<span lang=EN-US>.<br>
1. </span>회원의 의무를 성실하게 이행하지 않았을 때<span lang=EN-US><br>
2. </span>회원이 등록한 정보의 내용이 사실과 다르거나 조작되었을 때<span lang=EN-US><br>
3. </span>서비스의 명예를 훼손하였거나 회사가 판단하기에 적합하지 않은 목적으로 회원가입을 하였을 때</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>4.
</span><span style='font-size:10.5pt;color:#666666'>그 외에 회사 및 일가자인력 지점의 영업 활동에 현저히
지장을 줄 수 있다고 판단할 때<span lang=EN-US><br>
</span>④ 회사는 회원 가입이 해제된 경우에 해당 회원의 정보를 임의로 삭제하거나 서비스 이용을 제한할 수 있습니다<span
lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 19 </span>조<span lang=EN-US> (</span>회원의 개인정보보호<span
lang=EN-US>)<br>
</span>회사는 이용자의 개인정보보호를 위하여 노력합니다<span lang=EN-US>. </span>이용자의 개인정보보호에 관해서는 정보통신망이용촉진
및 정보보호 등에 관한 법률에 따르고<span lang=EN-US>, </span>사이트에<span lang=EN-US> &quot;</span>개인정보취급방침<span
lang=EN-US>&quot;</span>을 고지합니다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 20</span>조<span lang=EN-US> (</span>개인위치정보의 이용 또는 제공<span
lang=EN-US>)<br>
</span>①회사는 개인위치정보를 이용하여 서비스를 제공하고자 하는 경우에는 미리 이용약관에 명시한 후 개인위치정보주체의 동의를 얻어야 합니다<span
lang=EN-US>.<br>
</span>②회원 및 법정대리인의 권리와 그 행사방법은 제소 당시의 이용자의 주소에 의하며<span lang=EN-US>, </span>주소가
없는 경우에는 거소를 관할하는 지방법원의 전속관할로 합니다<span lang=EN-US>. </span>다만<span lang=EN-US>, </span>제소
당시 이용자의 주소 또는 거소가 분명하지 않거나 외국 거주자의 경우에는 민사소송법상의 관할법원에 제기합니다<span lang=EN-US>.<br>
</span>③회사는 타사업자 또는 이용 고객과의 요금정산 및 민원처리를 위해 위치정보 이용·제공</span><span lang=EN-US
style='font-size:10.5pt;font-family:"MS Mincho",serif;color:#666666'>&#8228;</span><span
style='font-size:10.5pt;color:#666666'>사실</span><span style='font-size:10.5pt;
color:#666666'> 확인자료를 자동 기록·보존하며<span lang=EN-US>, </span>해당 자료는<span
lang=EN-US> 1</span>년간 보관합니다<span lang=EN-US>.<br>
</span>④회사는 개인위치정보를 회원이 지정하는 제<span lang=EN-US>3</span>자에게 제공하는 경우에는 개인위치정보를 수집한
당해 통신 단말장치로 매회 회원에게 제공받는 자<span lang=EN-US>, </span>제공 일시 및 제공 목적을 즉시 통보합니다<span
lang=EN-US>. </span>단<span lang=EN-US>, </span>아래 각 호의<span lang=EN-US> 1</span>에
해당하는 경우에는 회원이 미리 특정하여 지정한 통신 단말장치 또는 전자우편주소로 통보합니다<span lang=EN-US>.<br>
1. </span>개인위치정보를 수집한 당해 통신단말장치가 문자<span lang=EN-US>, </span>음성 또는 영상의 수신기능을 갖추지
아니한 경우<span lang=EN-US><br>
2. </span>회원이 온라인 게시 등의 방법으로 통보할 것을 미리 요청한 경우<span lang=EN-US><br>
<br>
</span>제 <span lang=EN-US>21 </span>조<span lang=EN-US> (</span>개인위치정보주체의 권리<span
lang=EN-US>)<br>
</span>①회원은 회사에 대하여 언제든지 개인위치정보를 이용한 위치기반서비스 제공 및 개인위치정보의 제<span lang=EN-US>3</span>자
제공에 대한 동의의 전부 또는 일부를 철회할 수 있습니다<span lang=EN-US>. </span>이 경우 회사는 수집한 개인위치정보 및 위치정보
이용<span lang=EN-US>, </span>제공사실 확인자료를 파기합니다<span lang=EN-US>.<br>
</span>②회원은 회사에 대하여 언제든지 개인위치정보의 수집<span lang=EN-US>, </span>이용 또는 제공의 일시적인 중지를
요구할 수 있으며<span lang=EN-US>, </span>회사는 이를 거절할 수 없고 이를 위한 기술적 수단을 갖추고 있습니다<span
lang=EN-US>.<br>
</span>③회원은 회사에 대하여 아래 각 호의 자료에 대한 열람 또는 고지를 요구할 수 있고<span lang=EN-US>, </span>당해
자료에 오류가 있는 경우에는 그 정정을 요구할 수 있습니다<span lang=EN-US>. </span>이 경우 회사는 정당한 사유 없이 회원의
요구를 거절할 수 없습니다<span lang=EN-US>.<br>
1. </span>본인에 대한 위치정보 수집<span lang=EN-US>, </span>이용<span lang=EN-US>, </span>제공사실
확인자료<span lang=EN-US><br>
2. </span>본인의 개인위치정보가 위치정보의 보호 및 이용 등에 관한 법률 또는 다른 법률 규정에 의하여 제<span
lang=EN-US>3</span>자에게 제공된 이유 및 내용<span lang=EN-US><br>
</span>④회원은 제<span lang=EN-US>1</span>항 내지 제<span lang=EN-US>3</span>항의 권리행사를 위해
회사의 소정의 절차를 통해 요구할 수 있습니다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 22 </span>조 <span lang=EN-US>(</span>위치정보관리책임자의 지정<span
lang=EN-US>)<br>
</span>①회사는 위치정보를 적절히 관리·보호하고 개인위치정보주체의 불만을 원활히 처리할 수 있도록 실질적인 책임을 질 수 있는 지위에 있는
자를 위치정보관리책임자로 지정해 운영합니다<span lang=EN-US>.<br>
</span>②위치정보관리책임자는 위치기반서비스를 제공하는 부서의 부서장으로서 구체적인 사항은 본 약관의 부칙에 따릅니다<span
lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 23 </span>조<span lang=EN-US> (</span>손해배상<span
lang=EN-US>)<br>
</span>①회사가 위치정보의 보호 및 이용 등에 관한 법률 제<span lang=EN-US>15</span>조 내지 제<span
lang=EN-US>26</span>조의 규정을 위반한 행위로 회원에게 손해가 발생한 경우 회원은 회사에 대하여 손해배상 청구를 할 수 있습니다<span
lang=EN-US>. </span>이 경우 회사는 고의<span lang=EN-US>, </span>과실이 없음을 입증하지 못하는 경우 책임을
면할 수 없습니다<span lang=EN-US>.<br>
</span>②회원이 본 약관의 규정을 위반하여 회사에 손해가 발생한 경우 회사는 회원에 대하여 손해배상을 청구할 수 있습니다<span
lang=EN-US>. </span>이 경우 회원은 고의<span lang=EN-US>, </span>과실이 없음을 입증하지 못하는 경우 책임을
면할 수 없습니다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>제<span
lang=EN-US> 24 </span>조<span lang=EN-US> (</span>신용정보의 제공 활용 동의<span
lang=EN-US>)</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>①<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>가 회원가입과 서비스 이용과 관련하여 취득한<span
lang=EN-US> &quot;</span>회원<span lang=EN-US>&quot;</span>의 개인신용정보를 타인에게 제공하거나 활용하고자
할 때에는 신용정보의 이용 및 보호에 관한 법률 제<span lang=EN-US>23</span>조의 규정에 따라 사전에 그 사유 및 해당기관
또는 업체 명 등을 밝히고 해당<span lang=EN-US> &quot;</span>회원<span lang=EN-US>&quot;</span>의
동의를 얻어야 한다<span lang=EN-US>.</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>② 본 서비스와 관련하여<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>가<span lang=EN-US>
&quot;</span>회원<span lang=EN-US>&quot;</span>으로부터 신용정보의 이용 및 보호에 관한 법률에 따라 타인에게
제공 활용에 동의를 얻은 경우<span lang=EN-US> &quot;</span>회원<span lang=EN-US>&quot;</span>은<span
lang=EN-US> &quot;</span>회사<span lang=EN-US>&quot;</span>가 신용정보 사업자 또는 신용정보 집중기관에
정보를 제공하여<span lang=EN-US> &quot;</span>회원<span lang=EN-US>&quot;</span>의 신용을 판단하기
위한 자료로 활용하거나<span lang=EN-US>, </span>공공기관에서 정책자료로 활용되도록 정보를 제공하는 데 동의한 것으로 간주한다<span
lang=EN-US>.<br>
<br>
</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>제<span
lang=EN-US> 25 </span>조<span lang=EN-US> (</span>면책<span lang=EN-US>)<br>
</span>①회사는 다음 각 호의 경우로 서비스를 제공할 수 없는 경우 이로 인하여 회원에게 발생한 손해에 대해서는 책임을 부담하지 않습니다<span
lang=EN-US>.<br>
1. </span>천재지변 또는 이에 준하는 불가항력의 상태가 있는 경우<span lang=EN-US><br>
2. </span>서비스 제공을 위하여 회사와 서비스 제휴계약을 체결한 제<span lang=EN-US>3</span>자의 고의적인 서비스 방해가
있는 경우<span lang=EN-US><br>
3. </span>회원의 귀책사유로 서비스 이용에 장애가 있는 경우<span lang=EN-US><br>
4. </span>제<span lang=EN-US>1</span>호 내지 제<span lang=EN-US>3</span>호를 제외한 기타 회사의
고의·과실이 없는 사유로 인한 경우<span lang=EN-US><br>
</span>②회사는 서비스 및 서비스에 게재된 정보<span lang=EN-US>, </span>자료<span lang=EN-US>, </span>사실의
신뢰도<span lang=EN-US>, </span>정확성 등에 대해서는 보증을 하지 않으며 이로 인해 발생한 회원의 손해에 대하여는 책임을 부담하지
아니합니다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 26 </span>조<span lang=EN-US> (</span>규정의 준용<span
lang=EN-US>)<br>
</span>①본 약관은 대한민국법령에 의하여 규정되고 이행됩니다<span lang=EN-US>.<br>
</span>②본 약관에 규정되지 않은 사항에 대해서는 관련법령 및 상관습에 의합니다<span lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 27 </span>조<span lang=EN-US> (</span>분쟁의 조정 및 기타<span
lang=EN-US>)<br>
</span>①회사는 위치정보와 관련된 분쟁에 대해 당사자간 협의가 이루어지지 아니하거나 협의를 할 수 없는 경우에는 위치정보의 보호 및 이용
등에 관한 법률 제<span lang=EN-US>28</span>조의 규정에 의한 방송통신위원회에 재정을 신청할 수 있습니다<span
lang=EN-US>.<br>
</span>②회사 또는 고객은 위치정보와 관련된 분쟁에 대해 당사자간 협의가 이루어지지 아니하거나 협의를 할 수 없는 경우에는 개인정보보호법
제<span lang=EN-US>43</span>조의 규정에 의한 개인정보분쟁조정위원회에 조정을 신청할 수 있습니다<span
lang=EN-US>.<br>
<br>
</span>제<span lang=EN-US> 28 </span>조<span lang=EN-US> (</span>회사의 연락처<span
lang=EN-US>)<br>
</span>회사의 상호 및 주소 등은 다음과 같습니다<span lang=EN-US>.<br>
1. </span>상 호<span lang=EN-US> : </span>㈜잡앤파트너<span lang=EN-US><br>
2. </span>대 표 자<span lang=EN-US> : </span>박종일<span lang=EN-US><br>
3. </span>주 소<span lang=EN-US> : </span>서울시 마포구  동교로<span lang=EN-US>22</span>길 <span lang=EN-US>50 </span>진희빌딩 <span lang=EN-US>301</span>호<spanlang=EN-US><br>
4. </span>대표전화<span lang=EN-US> : 070-8666-1634<br>
<br>
</span></span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span style='font-size:10.5pt;color:#666666'>부 칙<span
lang=EN-US><br>
</span>제<span lang=EN-US>1</span>조<span lang=EN-US> (</span>시행일<span
lang=EN-US>) </span>이 약관은<span lang=EN-US> 2019</span>년<span lang=EN-US> 08</span>월<span
lang=EN-US> 01</span>일부터 시행합니다<span lang=EN-US>.<br>
</span>제<span lang=EN-US>2</span>조 회사 연락처와 위치정보 관리책임자는 다음과 같습니다<span
lang=EN-US>.<br>
- </span>상호<span lang=EN-US> : &nbsp;</span>㈜잡앤파트너<span lang=EN-US><br>
- </span>주소<span lang=EN-US> : </span>서울시 마포구  동교로<span lang=EN-US>22</span>길 <span lang=EN-US>50 </span>진희빌딩 <span lang=EN-US> 301</span>호<span lang=EN-US><br>
- </span>대표전화<span lang=EN-US> : 070-8666-1634<br>
- </span>위치정보관리책임자<span lang=EN-US> : </span>장재호<span lang=EN-US><br>
- </span>공고일자<span lang=EN-US>: 2019</span>년<span lang=EN-US> 08 </span>월<span
lang=EN-US> 01</span>일<span lang=EN-US><br>
- </span>시행일자<span lang=EN-US>: 2019</span>년<span lang=EN-US> 08 </span>월<span
lang=EN-US> 01</span>일</span></p>

<p class=MsoNormal align=left style='margin-bottom:0cm;margin-bottom:.0001pt;
text-align:left;line-height:normal;text-autospace:ideograph-numeric ideograph-other;
word-break:keep-all'><span lang=EN-US style='font-size:10.5pt;color:#666666'>&nbsp;</span></p>

</div>

	</div>
</div>
<!-- e: #container-wrap //-->