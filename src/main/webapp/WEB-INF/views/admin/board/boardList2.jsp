<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <div class="title">
      <h3>공지사항</h3>
    </div>
    <button type="button" onclick="calculate()">calculate</button>
 
    <script type="text/javascript">
        window.onload = function () {
            if (window.Notification) {
                Notification.requestPermission();
            }
        }
 
        function calculate() {
         
                notify();
           
        }
 
        function notify() {
            if (Notification.permission !== 'granted') {
                alert('notification is disabled');
            }else {
				
                var notification = new Notification('Notification title', {
                    icon: 'http://cdn.sstatic.net/stackexchange/img/logos/so/so-icon.png',
                    body: 'Notification text',
                });
 
                notification.onclick = function () {
                    window.open('http://google.com');
                };
            }
        }
       </script>
	<article>
      <div class="inputUI_simple mgbM">
      <table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
        <caption>등록일시, 상태, 직접검색 영역</caption>
        <colgroup>
          <col width="120px" />
          <col width="250px" />
          <col width="120px" />
          <col width="" />
        </colgroup>
        <tr>
          <th scope="row">등록일시</th>
          <td colspan="3">
            <p class="floatL">
              <input type="text" id="start_reg_date" name="start_reg_date" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
              <input type="text" id="end_reg_date" name="end_reg_date" class="inp-field wid100 datepicker" />
            </p>
            <div class="inputUI_simple">
            <span class="contGp btnCalendar">
              <input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'all'   ); $('#btnSrh').trigger('click');" checked="checked" /><label for="day_type_1" class="label-radio on">전체</label>
              <input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'today' ); $('#btnSrh').trigger('click');" /><label for="day_type_2" class="label-radio">오늘</label>
              <input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'week'  ); $('#btnSrh').trigger('click');" /><label for="day_type_3" class="label-radio">1주일</label>
              <input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'month' ); $('#btnSrh').trigger('click');" /><label for="day_type_4" class="label-radio">1개월</label>
              <input type="radio" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '3month'); $('#btnSrh').trigger('click');" /><label for="day_type_5" class="label-radio">3개월</label>
              <input type="radio" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '6month'); $('#btnSrh').trigger('click');" /><label for="day_type_6" class="label-radio">6개월</label>
            </span>
            <span class="tipArea colorN02">* 직접 기입 시, ‘2017-06-28’ 형식으로 입력</span>
            </div>
          </td>
        </tr>
        <tr>
          <th scope="row">상태</th>
          <td>
            <input type="radio" id="srh_use_yn_1" name="srh_use_yn" class="inputJo" value=""  /><label for="srh_use_yn_1" class="label-radio">전체</label>
            <input type="radio" id="srh_use_yn_2" name="srh_use_yn" class="inputJo" value="1" checked="checked" /><label for="srh_use_yn_2" class="label-radio">사용</label>
            <input type="radio" id="srh_use_yn_3" name="srh_use_yn" class="inputJo" value="0" /><label for="srh_use_yn_3" class="label-radio">미사용</label>
          </td>
          <th scope="row" class="linelY pdlM">직접검색</th>
          <td>
            <div class="select-inner">
            <select id="srh_type" name="srh_type" class="styled02 floatL wid138" onChange="$('#srh_text').focus();">
              <option value="" selected="selected">선택</option>
              <option value="">전체</option>
              <option value="a.admin_name">이름</option>
              <option value="a.admin_id">아이디</option>
              <option value="a.admin_phone">폰번호</option>
              <option value="a.admin_email">이메일</option>
            </select>
            </div>
            <input type="text" id="srh_text" name="srh_text" class="inp-field wid300 mglS" onKeyDown="if ( event.keyCode == 13 ) $('#btnSrh').click();" />
            <div class="btn-module floatL mglS">
              <a href="#none" id="btnSrh" class="search">검색</a>
            </div>
          </td>
        </tr>
      </table>
      </div>
    </article>
    
	<div id="" class="mgtL">
		<table class="bd-list" summary="게시판리스트">
			<caption>등록일시, 상태, 직접검색 영역</caption>
		        <colgroup>
		          <col width="100px" />
		          <col width="250px" />
		          <col width="100px" />
		          <col width="250px" />
		        </colgroup>
		        
		        <tr>
		          <th>제목1</th>
		          <th >내용</th>
		          <th  >글쓴이</th>
		          <th >날짜</th>
		        </tr>
		        <tr>
		          <td>등록일시</td>
		          <td >tasdf</td>
		          <td >tasdf</td>
		          <td >tasdf</td>
		        </tr>
		</table>
	</div>

	<div class="btn-module mgtS mgbS">
      <div class="leftGroup">
        <a href="/admin/board/boardWrite" id="btnAdd" class="btnStyle01">글쓰기</a>
        
      </div>
    </div>
	
	
	<a href="/admin/board/boardWrite">글쓰기</a>
