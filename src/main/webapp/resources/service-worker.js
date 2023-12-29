var isServiceWorkerSupported = 'serviceWorker' in navigator;

if (isServiceWorkerSupported){
  //브라우저에 Service Worker를 등록
  navigator.serviceWorker.register('service-worker.js', { scope: '/resources/'})
    .then(function(registration)
    {
       console.log('[ServiceWorker] 등록 성공1: ', registration.scope);
       
     //Firebase 초기화
       initFirebase(registration);
    })
    .catch(function(err)
    {
       console.log('[ServiceWorker] 등록 실패: ', err);
    });
}

//Push Message 수신 이벤트
self.addEventListener('push', function (event)
{
	var push_data = eval("(" + event.data.text() + ")");
	var url = push_data.notification.click_action;
	
    //Push 정보 조회
    var title = push_data.notification.title;
    var body = push_data.notification.body;
    var icon = event.data.icon || '/resources/common/images/notificationImg.png'; //512x512
    var badge = event.data.badge || '/Images/badge.png'; //128x128
    var options = {
		action : url,
        body: body,
        icon: icon,
        badge: badge,
        tag : url,
        data : push_data.data.status + '|' + push_data.data.seq
    };
    
   self.registration.showNotification(title, options);
});
 
//사용자가 Notification을 클릭했을 때
self.addEventListener('notificationclick', function (event) {
    event.notification.close();
    event.waitUntil(clients.matchAll({includeUncontrolled : true, type: "window" }).then(function (clientList) {
                //실행된 브라우저가 있으면 Focus
    			var locationUrl = event.notification.tag;
    			
                for (var i = 0; i < clientList.length; i++) {
                    var client = clientList[i];
                    var url = client.url;
                    
                    if(url.indexOf('ims.ilgaja.com') > -1) {
                        if(url.indexOf('login') == -1) {
                            if(url.indexOf('www.') > -1) {
                                return clients.openWindow(locationUrl + '?status=' + encodeURI(event.notification.data));
                            }else{
                                return clients.openWindow(locationUrl.replace(/\www./g, '') + '?status=' + encodeURI(event.notification.data));
                            }
                        }
                    }
                }
    			
//    			for(var i = 0; i < clientList.length; i++) {
//    				var client = clientList[i];
//    				var url = client.url;
//    				
//    				if(url.indexOf('localhost') > -1) {
//    					if(url.indexOf('login') == -1) {
//    						return clients.openWindow(locationUrl + '?status=' + encodeURI(event.notification.data));
//    						//return clients.openWindow(locationUrl);
//    					}
//    				}
//    			}
                
                //실행된 브라우저가 없으면 Open
                if (clients.openWindow) {
                	//return clients.openWindow('localhost:8080/admin/login');
                	return clients.openWindow('https://ims.ilgaja.com/admin/login');
                }
            })
    );
});
 
console.log('[ServiceWorker] 시작');




