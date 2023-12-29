<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style type="text/css">
  .layer {display:none; position:fixed; _position:absolute; top:0; left:0; width:100%; height:100%; z-index:100;}
  .layer .bg {position:absolute; top:0; left:0; width:100%; height:100%; background:#000; opacity:.5; filter:alpha(opacity=50);}
  .layer .pop-layer {display:block;}

  .pop-layer {display:none; position: absolute; top: 50%; left: 50%; width: 410px; height:auto;  background-color:#fff; border: 5px solid #3571B5; z-index: 10;}
  .pop-layer .pop-container {padding: 20px 25px;}
  .pop-layer p.ctxt {color: #666; line-height: 25px;}
  .pop-layer .btn-r {width: 100%; margin:10px 0 20px; padding-top: 10px; border-top: 1px solid #DDD; text-align:right;}

  a.cbtn {display:inline-block; height:25px; padding:0 14px 0; border:1px solid #304a8a; background-color:#3f5a9d; font-size:13px; color:#fff; line-height:25px;}
  a.rbtn {display:inline-block; height:25px; padding:0 14px 0; border:1px solid #304a8a; background-color:#3f5a9d; font-size:13px; color:#fff; line-height:25px;}
  a.cbtn:hover {border: 1px solid #091940; background-color:#1f326a; color:#fff;}
</style>

    <script type="text/javascript" src="<c:url value="/resources/cms/js/jquery.cookie.js" />" charset="utf-8"></script>
    <script type="text/javascript" src="<c:url value="/resources/common/js/rsa/jsbn.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rsa.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/common/js/rsa/prng4.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rng.js" />"></script>

    <c:if test="${!empty sessionScope.USER_SESSION }">
      <c:set value="${sessionScope.SEARCH_SESSION}" var="search" scope="session"/>
    </c:if>
    <c:if test="${empty sessionScope.USER_SESSION }">
      <c:set value="${sessionScope.searchDTO}" var="search" scope="session"/>
    </c:if>

<script type="text/javascript">
  var cookieId = "userId";
  var result;

  $(document).ready(function(){
    
    <c:if test="${empty sessionScope.USER_SESSION }">
	    $("#logBtn").click();
	    $("#userId").focus();
    </c:if>
  });

  function commSetSearchType(val){
    if(val == 1){
      $("#comSearchFrm #inText").val($("#comSearchFrm #tmpInText").val());
      $("#commTrExText").show();
      $("#commTrOptions").show();
    }else if(val == 2){
      $("#comSearchFrm #inText").val($("#comSearchFrm #tmpReporter").val());
      $("#commTrExText").hide();
      $("#commTrOptions").hide();
    }
  }


  //레이어
  function layer_open(el){

    var id = $.cookie(cookieId);

    if (id != null && id != "") {
      $("input[name=userId]").val(id);
      $("#save_id").attr("checked", "checked");
      $("input[name=adminPwd]").focus();
    }

    var temp = $('#' + el);
    var bg = temp.prev().hasClass('bg');  //dimmed 레이어를 감지하기 위한 boolean 변수

    if(bg){
      $('.layer').fadeIn();  //'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다.
    }else{
      temp.fadeIn();
    }

    // 화면의 중앙에 레이어를 띄운다.
    if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
    else temp.css('top', '0px');
    if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
    else temp.css('left', '0px');

    temp.find('a.cbtn').click(function(e){
      if(bg){
        $('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다.
      }else{
        temp.fadeOut();
      }
      e.preventDefault();
    });

    $('.layer .bg').click(function(e){  //배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
      $('.layer').fadeOut();
      e.preventDefault();
    });

  }

  function loginLayer(){
    var _url = "<c:url value='/web/loginForm'/>";
    var _data = {};

    commonAjax(_url, _data, true, function(data) {
      if(data.code == "0001"){

        $("#publicKeyModulus").val(data.publicKeyModulus);
        $("#publicKeyExponent").val(data.publicKeyExponent);

        var id = $.cookie(cookieId);

        if (id != null && id != "") {
          $("input[name=userId]").val(id);
          $("#save_id").attr("checked", "checked");
          $("input[name=adminPwd]").focus();
        }
      }else{
        alert(data.code);
      }

    }, function(data) {
      //errorListener
      alert("error!!");
    }, function() {
      //beforeSendListener
    }, function() {
      //completeListener
    });
  }

  function login(){
    if(!idSaveCheck()){
      return;
    }

    var rsaPublicKeyModulus = "";
    var rsaPublicKeyExponent = "";
    try {
      rsaPublicKeyModulus = $("#publicKeyModulus").val();
      rsaPublicKeyExponent = $("#publicKeyExponent").val();
    } catch(err) {
      alert(err);
    }


    var rsa = new RSAKey();
    rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);

    // 사용자ID와 비밀번호를 RSA로 암호화한다.
    var _data = {
      userId :  rsa.encrypt($("input[name=userId]").val()),
      userPwd : rsa.encrypt($("input[name=userPwd]").val())
    };

    var _url = "<c:url value='/web/loginProc' />";
    commonAjax(_url, _data, true, function(data) {
      //successListener
      if(data.code == "0000"){
        location.href="<c:url value='/web/main' />";
      }else if(data.code == "8000"){
        alert("로그인 인증키가 만료되었습니다.\n새로고침 후 다시 로그인하세요.");
      }else{
        if( jQuery.type(data.message) != 'undefined'){
          if(data.message != ""){
            alert(data.message);
          }else{
            alert("로그인 정보가 유효하지 않습니다!");
          }
        }else{
          alert("로그인 정보가 유효하지 않습니다!");
        }
      }
    }, function(data) {
      //errorListener
      alert("오류가 발생했습니다.");
    }, function() {
      //beforeSendListener
    }, function() {
      //completeListener

    });
  }

  // 아이디 저장
  function idSaveCheck() {
    var checkStatus = $("#save_id").is(":checked");

    if (checkStatus) {
      $.cookie(cookieId, $("input[name=userId]").val(),{ expires:365, path: "/" });
    } else {
      $.cookie(cookieId, "",{ expires: -1, path: "/" });
    }

    return true;
  }

  function logout(){
    $("#logout").attr("action" , "<c:url value='/web/logout'/>").submit();
  }

  function searchSave(){
    var _url = "<c:url value='/common/searchSave'/>";
    var _data = $("#comSearchFrm").serialize();
    $.ajaxSettings.traditional = true;
    commonAjax(_url, _data, true, function(data) {
      if(data.code == "0000"){
        alert("저장되었습니다.");
      }else{
        alert("오류가 발생하였습니다.");
      }
    }, function(data) {
      //errorListener
      alert("error!!");
    }, function() {
      //beforeSendListener
    }, function() {
      //completeListener
      location.reload();
    });
  }

  function commSetSearchGrouopData(seq){
    if(seq == "0"){
      return;
    }
    var _url = '<c:url value="/web/setting/searchGroupDetail"/>';
    var _data = {groupSeq : seq};
    commonAjax(_url, _data, true, function(data) {
      if(data.code == "0000"){
        $("#comSearchFrm #ra_"+data.detail.options).click();
        $("#comSearchFrm #exText").val(data.detail.exText);


        if($("#ra_T").is(":checked")){
          $("#comSearchFrm #inText").val(data.detail.inText);
        }else if($("#ra_R").is(":checked")){
          $("#comSearchFrm #inText").val(data.detail.reporterText);
        }

        $("#comSearchFrm #tmpReporter").val(data.detail.reporterText);
        $("#comSearchFrm #tmpInText").val(data.detail.inText);

      }else{
        alert("시스템 에러 발생");
      }

    }, function(data) {
      //errorListener
      alert("error!!1");
    }, function() {
      //beforeSendListener
    }, function() {
      //completeListener
    });
  }

</script>

<form id="headerFrm" name="headerFrm" method="post">
  <input type="hidden" name="broadcastSeq" id="broadcastSeq" />
</form>

    <!-- 헤더 영역 -->
    <div id="header">
      <!-- top_section 영역 -->
      <div class="top">
        <div class="wrap">
          <div class="top_section">
            <h1 id="logo"><a href="<c:url value="/web"/>"><img src="/resources/web/images/logo/logo.png" alt="NEWS CASTER"/></a></h1>
            <div class="select_area">
              <form name="searchForm" id="searchForm" method="post" autocomplete="off">
              <input type="hidden" id="pageNo" name="paging.pageNo" value="${searchDTO.paging.pageNo}" />
              <input type="hidden" id="dateType" name="dateType" />
              <input type="hidden" id="startDate" name="startDate" value="${searchDTO.startDate}"/>
              <input type="hidden" id="endDate" name="endDate" value="${searchDTO.endDate}"/>

              <input type="text" style="display: none;" />
                <fieldset>
                  <legend>뉴스 키워드 검색</legend>
                  <div class="text_area">
                    <div><input type="text" title="검색어를 입력해주세요." name="inText" value="${searchDTO.inText}" onKeyDown="if(event.keyCode == 13) searchNews();"/></div>
                    <a href="javascript:void(0);" onclick="searchNews();"><img src="/resources/web/images/btn/btn01.png" alt="검색"/></a>
                  </div>
                  <c:if test="${!empty sessionScope.USER_SESSION }">
                  <a href="javascript:void(0);" class="search_option_btn" data-popbtn="set_search_case">검색조건</a>
                  </c:if>
                </fieldset>

              </form>
            </div>
            <ul class="user_util">
              <li class="home_btn"><a href="<c:url value="/web"/>">Home</a></li>
              <li class="login_btn">
                <c:if test="${empty sessionScope.USER_SESSION }">
                <a href="javascript:void(0);" data-popBtn="login" onclick="loginLayer();" id="logBtn">로그인</a>
                </c:if>
                <c:if test="${!empty sessionScope.USER_SESSION }">
                <a href="javascript:void(0);" onclick="logout();">로그아웃</a>
                </c:if>
              </li>
              <li class="preference_btn last"><a href="/web/setting/searchInfo">환경설정</a></li>
            </ul>
          </div>
        </div>
      </div>
      <!--// top_section 영역 -->
      <!-- gnb 영역 -->
      <div id="gnb">
        <div class="wrap" style="width:1320px;">
          <ul class="dept1" style="width:1017px;">
            <c:forEach items="${search.areas}" var="areas" varStatus="status">
            <li class="dept1_li">
              <a href="javascript:void(0);" class="dept1_btn">${areas.areaName }</a>
              <div class="dept2" <c:if test="${status.index eq 0 or status.index eq 1 }">style="left:120%;"</c:if> >
                <ul>
                  <c:forEach items="${areas.broadcasts}" var="boardcastList">
                    <c:if test="${empty sessionScope.USER_SESSION }">
                      <li><a href="javascript:loginLayer();" data-popBtn="login">${boardcastList.broadcastName }</a></li>
                    </c:if>
                    <c:if test="${!empty sessionScope.USER_SESSION }">
                      <c:if test="${boardcastList.accessYn eq 'Y' }">
                        <li><a href="javascript:goCommNewsList('${boardcastList.broadcastSeq }');">${boardcastList.broadcastName }</a></li>
                      </c:if>
                      <c:if test="${boardcastList.accessYn ne 'Y' }">
                        <li><a href="javascript:alert('권한이 없습니다.');">${boardcastList.broadcastName }</a></li>
                      </c:if>
                    </c:if>
                  </c:forEach>
                </ul>
              </div>
            </li>
            </c:forEach>
          </ul>
          <a href="/web/notice/noticeList" class="notice_btn"><span>공지사항</span></a>
          <a href="/web/scrap/scrapList" class="scrab_bag_btn"><span>스크랩 보관함</span></a>
        </div>
      </div>
      <!--// gnb 영역 -->
    </div>
    <!--//헤더 영역 -->



    <!-- 로그인 팝업 -->
  <div class="pop_wrap" data-popWrap="login">
    <div class="pop_contents login_pop" data-pop="login">
      <h1>로그인</h1>
      <p>로그인하시면 이용하실 수 있습니다.</p>
      <form>
        <fieldset>
          <legend>로그인하기</legend>
          <div><input type="text" placeholder="아이디" id="userId" name="userId"/></div>
          <div><input type="password" placeholder="비밀번호" id="userPwd" name="userPwd" onKeyDown="if(event.keyCode == 13) login();"/></div>
          <label for="save_id"><input type="checkbox" id="save_id"/>아이디/비밀번호 저장</label>
        </fieldset>
        <div class="btn_wrap">
          <a href="javascript:login();" class="btn03">로그인</a>
          <a href="javascript:void(0);" class="btn02" data-popExit="login">취소</a>
        </div>
      </form>
    </div>
  </div>
  <!--// 로그인 팝업 -->



  <form id="comSearchFrm" name="comSearchFrm" method="post">
  <input type="hidden" id="tmpReporter" value="${search.inText }"/>
  <input type="hidden" id="tmpInText" value="${search.inText }"/>
  <input type="hidden" id="pageNo" name="paging.pageNo" value="${searchDTO.paging.pageNo}" />
  <!-- 검색조건 팝업 -->
  <div class="pop_wrap" data-popWrap="set_search_case">
    <div class="pop_contents pop_set_case" data-pop="set_search_case">
      <div class="board02">
        <table>
          <caption>검색조건 설정</caption>
          <colgroup>
            <col style="width:160px;">
            <col>
            <col style="width:160px;">
            <col>
          </colgroup>
          <tbody>
            <tr>
              <th scope="row">검색기간</th>
              <td class="last" colspan="3">
                <label class="radio1"><input type="radio" name="dateType" <c:if test="${search.dateType eq '1' }">checked</c:if> value="1"> 당일</label>
                <label class="radio1"><input type="radio" name="dateType" <c:if test="${search.dateType eq '2' }">checked</c:if> value="2"> 최근 2일</label>
                <label class="radio1"><input type="radio" name="dateType" <c:if test="${search.dateType eq '3' }">checked</c:if> value="3"> 최근 3일</label>
                <label class="radio1"><input type="radio" name="dateType" <c:if test="${search.dateType eq '4' }">checked</c:if> value="4"> 기간선택</label>
                <div class="date_selector">
                  <input type="text" class="datepicker" name="startDate" id="startDate1" value="<c:if test="${search.dateType eq '4' }">${search.startDate}</c:if>">
                  <!-- <select>
                    <option value="0">00</option>
                    <option value="1">01</option>
                    <option value="2">02</option>
                  </select> -->
                </div>
                &nbsp;~&nbsp;
                <div class="date_selector">
                  <input type="text" class="datepicker" name="endDate" id="endDate1" value="<c:if test="${search.dateType eq '4' }">${search.endDate}</c:if>">
                  <!-- <select>
                    <option value="0">00</option>
                    <option value="1">01</option>
                    <option value="2">02</option>
                  </select> -->
                </div>
              </td>
            </tr>

            <c:forEach items="${search.areas}" var="areas">
            <tr>
              <th scope="row">${areas.areaName }</th>
              <td class="last" colspan="3">
                <c:forEach items="${areas.broadcasts}" var="boardcastList">
                <label class="check1"><input type="checkbox" name="broadcastSeqArr" id="cast${boardcastList.broadcastSeq}" value="${boardcastList.broadcastSeq}" <c:if test="${boardcastList.accessYn ne 'Y'}">disabled</c:if> > ${boardcastList.broadcastName }</label>
                </c:forEach>
              </td>
            </tr>
            </c:forEach>
            <tr>
              <th scope="row">검색어</th>
              <td class="last" colspan="3">
                <div style="display: inline-block;">
                  <label class="input_type1"><input type="text" name="inText" id="inText" value="${search.inText }" style="width:320px;"></label>
                </div>
                <span class="radio1 ignore_case" id="commTrOptions">
                  <label for="srch_txt">검색옵션</label>
                  <span><label class="radio1"><input type="radio" name="options" value="A" id="ra_A"<c:if test="${search.options eq 'A'}">checked</c:if> > AND</label></span>
                  <span><label class="radio1"><input type="radio" name="options" value="O" id="ra_O"<c:if test="${search.options eq 'O'}">checked</c:if> >OR</label></span>
                </span>
              </td>
            </tr>
            <tr id="commTrExText">
              <th scope="row">배재어</th>
              <td class="last" colspan="3">
                <label class="input_type1"><input type="text" name="exText" id="exText" value="${search.exText }" style="width:320px;"></label>
                <%-- <span class="radio1 ignore_case">
                  <label for="opt_set">옵션설정</label>
                  <span><input type="radio" name="options" value="1" <c:if test="${search.options eq '1'}">checked</c:if> > AND</span>
                  <span><input type="radio" name="options" value="2" <c:if test="${search.options eq '2'}">checked</c:if> > OR</span>
                </span> --%>

              </td>
            </tr>
            <tr>
              <th scope="row">검색어 그룹</th>
              <td class="last" colspan="3">
                <div class="date_selector"><select style="width:200px;" id="selSearchGroup" onchange="commSetSearchGrouopData(this.value);">
                    <option value="0">그룹선택</option>
                    <c:forEach items="${search.searchGroups }" var="list">
                    <option value="${list.groupSeq }" data-inText="" data-exText="" >${list.groupName }</option>
                    </c:forEach>
                </select></div>
                <span class="radio1 ignore_case">
                  <label for="srch_txt">검색내용</label>
                  <label class="radio1"><span><input type="radio" id="ra_T" name="type" value="1" <c:if test="${search.type eq '1'}">checked</c:if> onclick="commSetSearchType(this.value);"> 제목+내용</span></label>
                  <label class="radio1"><span><input type="radio" id="ra_R" name="type" value="2" <c:if test="${search.type eq '2'}">checked</c:if> onclick="commSetSearchType(this.value);"> 기자명</span></label>
                </span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="save mt40">
        <a href="javascript:$('#comSearchFrm')[0].reset();" class="btn02" data-popExit="set_search_case">취소</a>
        &nbsp;
         <!-- <a href="javascript:void(0);" onclick="searchSave();" class="btn03">저장</a> -->
        <a href="javascript:void(0);" onclick="goConSearch();" class="btn03">검색</a>
      </div>
    </div>
  </div>
  <!--// 검색조건 팝업 -->
  </form>



<form id="logout" method="post">
</form>
<%-- <form id="headerForm" method="post">
  <input type="hidden" id="userSeq" name="userSeq" />
  <input type="hidden" id="programSeq" name="programSeq" />
</form>
<div id="header" style="height:100px;width:100%;">
  <div style="width:1000px;height:10px;margin:0 auto;">
    <div align="right">
      <c:if test="${!empty sessionScope.USER_SESSION }">
        반갑습니다  <b>${sessionScope.USER_SESSION.userName }</b> 님&nbsp;&nbsp;
        <a href="javascript:void(0);" onclick="logout(); return false;">로그아웃</a>
      </c:if>
      <c:if test="${empty sessionScope.USER_SESSION }">
        <a href="javascript:void(0);" onclick="loginLayer(); return false;">로그인</a>
      </c:if>
    </div>
  </div>

  <div style="width:1000px;height:90px;margin:0 auto;">
    <ul id="areaAajx">
      <c:forEach items="${sessionScope.AREA_SESSION }" var="list">
        <li  style="display: inline; padding: 10px; cursor: pointer;" >
          <a href="javascript:void(0);" onclick="boardcastCall('${list.areaSeq}');">${list.areaName }</a>
        </li>
      </c:forEach>
    </ul>
      <br/>
    <ul id="boardAjax">
    </ul>
      <br/>
    <ul id="programAjax">
    </ul>
      <br/>
    <a href="javascript:void(0);" onclick="scrapList('${sessionScope.USER_SESSION.userSeq}');">스크랩보관함</a>
  </div>

</div>
 --%>
  <input type="hidden" id="publicKeyModulus" name="" />
  <input type="hidden" id="publicKeyExponent" name="" />


<!-- // header -->

        