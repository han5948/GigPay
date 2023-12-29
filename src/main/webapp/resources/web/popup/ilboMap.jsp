<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<title>Geocoding service</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<link rel="icon" href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico" mce_href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico" type="image/x-icon">

<script type="text/javascript"  src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ehjyv0iw4b&amp;submodules=geocoder"></script>
<script type="text/javascript"  src="/resources/cms/grid/js/jquery-1.11.0.min.js" charset="utf-8"></script>
<script type="text/javascript"  src="/resources/cms/js/xl-toast.js" ></script>

<style>
/* Always set the map height explicitly to define the size of the div  * element that contains the map. */

#map {
    height: 100%;
}
/* Optional: Makes the sample page fill the window. */
html,body {
    height: 100%;
    margin: 0;
    padding: 0;
}

#floating-panel {
    position: absolute;
    top: 0px;
    /* left: 5%; */
    z-index: 5;
    background-color: #5E8DD5;
    
    border: 1px solid #999;
    text-align: center;
    font-family: 'Roboto', 'sans-serif';
    line-height: 30px;
    padding: 5px;
    padding-left: 10px;
    width:100%
    
}

@media print
{
  .noprint { display:none; }
}

#btnComment {
    background-color: #5E8DD5;
    border: 1px solid #999;
    text-align: center;
    width:98%;
    margin-top:0px;
    height:30px
  
}

textarea { width: 800px; margin: 0; padding-left: 10px; padding-top: 10px; padding-right: 10px; border-width: 0; font-size:18px;}

</style>
</head>

<%
request.setCharacterEncoding("UTF-8");
String mode = request.getParameter("map_mode");
String lat = request.getParameter("map_lat");
String lng = request.getParameter("map_lng");
%>

<body>
    <div id="floating-panel" class="noprint">
        <input id="sAddr" type="text" value="" size=50> 
        <input id="btnAddr" type="button" value="주소로검색">  
        <input id="btnMarker" type="button" value="마커로검색"> 
        <input id="submit" type="button" value="적용하기"> 
        <input id="btnPrint" type="button" value="프린트" >
    </div>
    
    <div id="map"></div>
<script>
    var marker;
    var cLatlng;
    var cAddr;
    var info = false;
    var infowindow;
    var map = new naver.maps.Map('map');
    var contentString = "test";
    var infowindow = new naver.maps.InfoWindow({
        content: contentString
    });
    var mode        	= "<%=mode%>";
    var lat         		= "<%=lat%>";
    var lng         		= "<%=lng%>";
    var myLatlng = null;
    var type = 0;
    
    if(!lng){
        myLatlng = new naver.maps.LatLng(37.566535,126.97796919999996);    //기본 서울시청
    }
    
    map.setZoom(12);
    
    if(type == 1) {
        geocodeAddress();
    }else {
        geocodeLatLng();    
    }
    
    naver.maps.Event.addListener(map, 'click', function(e) {
    	cLatlng = "";
          
        myLatlng = e.coord;
        marker.setPosition(myLatlng);
          
        infowindow.close();
	});

    document.getElementById('btnMarker').addEventListener('click', function() {
    	geocodeLatLng();
    });

    document.getElementById('btnAddr').addEventListener('click', function() {
      	geocodeAddress();
    });
    
    document.getElementById('submit').addEventListener('click', function() {
        chkUpdate(cAddr,cLatlng);
    });
    
    document.getElementById('btnPrint').addEventListener('click', function() {
        gmapPrint();
    });
      
    //마커 위치에 대한 주소 가져오기
    function geocodeLatLng() {
        
        naver.maps.Service.reverseGeocode({ location: new naver.maps.LatLng(myLatlng),
        }, function(status, response) {
            if (status !== naver.maps.Service.Status.OK) {
                return alert('Something wrong!');
            }

            var result = response.result, // 검색 결과의 컨테이너
            items = result.items; // 검색 결과의 배열

            cAddr = result.items[0].address;
            cLatlng = result.items[0].point.y + "," + result.items[0].point.x
            cLatlng = myLatlng.lat() + "," + myLatlng.lng()
            
            map.setCenter(myLatlng); // 검색된 좌표로 지도 이동
            
            if(!marker) {
                marker = new naver.maps.Marker({
                    position: myLatlng,
                    map: map
                });
            }else {
                  marker.setPosition(myLatlng);
            }
            
            setInfo(result);
        });
	}

    //입력한 주소값에 대한 위치 가져오기
    function geocodeAddress() {
		var myaddress = document.getElementById('sAddr').value;
      
      	if(myaddress == "") {
          	$.toast({title: '주소 입력 하세요.', position: 'middle', backgroundColor: '#d60606', duration:2000 });
      	}
      
      	naver.maps.Service.geocode({address: myaddress}, function(status, response) {
          	if (status !== naver.maps.Service.Status.OK) {
              	setMarker(myLatlng);
              	return alert(myaddress + '의 검색 결과가 없거나 기타 네트워크 에러');
          	}
          
          	var result;
          	
          	try {
          		result = response.result;
          		cAddr = result.items[0].address;
                cLatlng = result.items[0].point.y + "," + result.items[0].point.x
                
                // 검색 결과 갯수: result.total
                // 첫번째 결과 결과 주소: result.items[0].address
                // 첫번째 검색 결과 좌표: result.items[0].point.y, result.items[0].point.x
               
                myLatlng = new naver.maps.Point(result.items[0].point.x, result.items[0].point.y);
                map.setCenter(myLatlng); // 검색된 좌표로 지도 이동
        	}catch(error) {
       	  		cAddr = "주소를 잘못 되었습니다.";
       	  		cLatlng = "";
        	}finally {
          		setMarker();
          		setInfo();
        	}
		});
	}
    
    function setMarker() {
        if(!myLatlng)
            myLatlng = new naver.maps.LatLng(37.566535,126.97796919999996);    //기본 서울시청
            
         if(!marker) {
             marker = new naver.maps.Marker({
                 position: myLatlng,
                 map: map
             });
         }else{
         	marker.setPosition(myLatlng);
         }
    }
    
    function setInfo(){
		contentString =  cAddr + "  ::  " + cLatlng
        infowindow.setContent(contentString);
        infowindow.open(map, marker);
    }
    
  	//상태변경
    function chkUpdate(cAddr,cLatlng) {
        var tmpSplit = cLatlng.split(",");
                     
        if(tmpSplit.length != 2) {
        	$.toast({title: '주소검색 또는 마커검색으로 좌표을 다시 지정 하세요.', position: 'middle', backgroundColor: '#d60606', duration:2000 });
              
            return false;
		}
           
      	var cUrl = "";    
      	var str;
      	
      	opener.document.getElementById("work_addr").value = cAddr;
      	opener.document.getElementById("work_latlng").value = cLatlng;
      	
      	self.close();
    }

    function gmapPrint() {
        window.print();
    }
        
</script>
</body>
</html>