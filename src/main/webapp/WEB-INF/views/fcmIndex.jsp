<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- manifest 참조추가 -->
<link rel="manifest" href="/resources/messaging/manifest.json">

<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.17.2/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.17.2/firebase-messaging.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.17.2/firebase-analytics.js"></script>

<script>
	var isNotificationSupported = 'Notification' in window;
	if (isNotificationSupported) {
	    Notification.requestPermission().then(function (result) {
	        if (result === 'granted') {
	            console.log('[Notification] 허용: ', result);
	        }else {
	            console.log('[Notification] 차단: ', result);
	        }
	    });
	}

	function initFirebase(serviceWorkRegistration) {
	  	// Your web app's Firebase configuration
	  	var firebaseConfig = {
	    	apiKey: "AIzaSyAd7KOqe_JByjA6kpBC0o1B9yzWZEc3UrU",
	    	authDomain: "ilgajaw-8ec37.firebaseapp.com",
	    	databaseURL: "https://ilgajaw-8ec37.firebaseio.com",
	    	projectId: "ilgajaw-8ec37",
	    	storageBucket: "ilgajaw-8ec37.appspot.com",
	    	messagingSenderId: "681187843965",
	    	appId: "1:681187843965:web:366d2d2dce80e76d01b614",
	    	measurementId: "G-TC6KR1ST3D"
	  	};
	  	
	  	// Initialize Firebase
	  	firebase.initializeApp(firebaseConfig);
	  	firebase.analytics();
	  
	  	//Messaging 서비스 활성화
	    var messaging = firebase.messaging();
	    messaging.useServiceWorker(serviceWorkRegistration);
	    messaging.usePublicVapidKey("BKYOQMtg45-f2wz087A1qKkA5fu65RJSRTn0D3RhKunxvsQQ7EBtsb62wPLx4Fsj4a304SPAKgzATonN6a-q0Bg");

	  	//Instance ID Token 발급 요청
	    messaging.getToken().then((currentToken) => {
	            if (currentToken) {
	                console.log('[InstanceID Token] 발행완료: ', currentToken);
	                $("#token").val(currentToken);
	                sendTokenToServer(currentToken); //Token을 서버로 전송
	            }else {
	                console.log('[InstanceID Token] 발행실패');
	                sendTokenToServer(null);
	            }
	        }).catch((err) => {
	            console.log('[InstanceID Token] 발행오류: ', err);
	            sendTokenToServer(null);
	        });
	 
	    	//Instance ID Token 변경 시 호출되는 이벤트
	    	messaging.onTokenRefresh(() => {
	        messaging.getToken().then((refreshedToken) => {
	            console.log('[InstanceID Token] 갱신완료', refreshedToken);
	            sendTokenToServer(refreshedToken); //Token을 서버로 전송
	        }).catch((err) => {
	            console.log('[InstanceID Token] 갱신실패', err);
	            sendTokenToServer(null);
	        });
	    });
	 
	    messaging.onMessage((payload) => {
	        //Push Message 수신 시 호출되는 이벤트
	        console.log('[PushMessage] 수신: ', payload);
	    });
	}
	
	//발급된 Instance ID Token을 서버에 전송
	function sendTokenToServer(token){
		console.log("서버전송:" + token);
		
	 /*    return fetch('/api/save-token/',
	        {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            body: token
	        })
	        .then(function (response)
	        {
	            if (!response.ok)
	                console.log('[InstanceID Token] 서버전송 실패: ', response);
	            else
	                console.log('[InstanceID Token] 서버전송 완료: ', response);
	 
	            return response.json();
	        })
	        .then(function (responseData)
	        {
	            if (!(responseData && responseData.Success))
	                console.log('[InstanceID Token] 서버전송 응답(실패): ', responseData);
	            else
	                console.log('[InstanceID Token] 서버전송 응답(성공): ', responseData);
	        });
	    
	     */
	}
</script>
<script type="text/javascript" src="/resources/service-worker.js" charset="utf-8"></script>

	
	<table width="50%">
	<tr>
		<th width="20%">패스워드</th><td><INPUT TYPE="text" NAME="password" style="width:500"/></td>
	</tr>
	<tr>
	<th>토큰</th><td><INPUT TYPE="text" NAME="token" id="token" style="width:500" ></td>
	</tr>
	<tr>
	<th>company_seq</th><td><INPUT TYPE="text" NAME="company_seq" style="width:500" ></td>
	</tr>

	</table>
