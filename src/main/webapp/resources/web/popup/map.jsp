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
String seq = request.getParameter("map_seq");
String lat = request.getParameter("map_lat");
String lng = request.getParameter("map_lng");
String addr = request.getParameter("map_addr");
String comment = request.getParameter("map_comment");

//String comment = new String(request.getParameter("map_comment").getBytes("8859_1"),"UTF-8");


%>

<body>
    <div id="floating-panel" class="noprint">
        <input id="sAddr" type="textbox" value="" size=50> 
        <input id="btnAddr" type="button" value="주소로검색">  
        <input id="btnMarker" type="button" value="마커로검색"> 
        <input id="submit" type="button"            value="적용하기"> 
        <input id="btnPrint" type="button" value="프린트" >
    </div>
    
    <div id="map"></div>
    
<!--     <div id ="map_comment" > -->
<!--         <form id="mapFrm" method="post"> -->
<%--           <input type="hidden" id="seq" name="seq" value="<%=seq%>" /> --%>
<!--           <input type="hidden" id="addr_comment" name="seq"  /> -->
<!--           <textarea  id="comment" rows="5" placeholder="위치 설명을 입력 하세요..최대 5줄 까지만 입력" ></textarea> -->
<!--           <input type="button" id="btnComment"  onclick="javascript:updateComment();" class="noprint" value="위치 설명 저장" /> -->
<!--         </form> -->
     
<!--     </div> -->
    
    

    <script>
    
    var marker;
    var cLatlng;
    var cAddr;
    
    var info = false;
    var infowindow;
    
    var map = new naver.maps.Map('map');
    var contentString = "test";
    
    var sido = "";
    var sigugun = "";
    var dongmyun = "";
    var rest = "";
    
    var infowindow = new naver.maps.InfoWindow({
        content: contentString
    });
    
    
     
    var mode        	= "<%=mode%>";
    var seq         		= "<%=seq%>";
    var jumin_addr  	= "<%=addr%>";
    var comment     	= "<%=comment%>";
    var lat         		= "<%=lat%>";
    var lng         		= "<%=lng%>";
    
    if(comment =="null") comment = "";
    comment = comment.replace(/<br>/g, "\r\n");
    
    if(mode == "WORKER"){
        $("#map_comment").hide();
        $("#map").css("height","100%");
    }else{
        $("#comment").html(comment);
      
    }
    
    
    var myLatlng = null;
    var type = 0;
	
    if(!lng){
        if(mode == "WORKER" || "ORDER"){
            if(!jumin_addr || jumin_addr == "null"){
                myLatlng = new naver.maps.LatLng(37.566535,126.97796919999996);    //기본 서울시청        
            }else{
                jumin_addr = decodeURIComponent((jumin_addr + '').replace(/\+/g, '%20'));
                document.getElementById('sAddr').value = jumin_addr;
                type = 1;
            }
        }else{
            myLatlng = new naver.maps.LatLng(37.566535,126.97796919999996);    //기본 서울시청
        }
        
        
    }else{
    	 document.getElementById('sAddr').value = jumin_addr;
         myLatlng = new naver.maps.LatLng(Number(lat), Number(lng) );
    }
    
    
    map.setZoom(12);
    
  
    
    if(type == 1){
        geocodeAddress();
    }else{
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
        chkUpdate(cAddr,cLatlng)
      

    });
    
    document.getElementById('btnPrint').addEventListener('click', function() {
        gmapPrint()
      

    });
      
     
   
    //마커 위치에 대한 주소 가져오기
    function geocodeLatLng() {
    	
        myLatlng = new naver.maps.LatLng(myLatlng);
        
        naver.maps.Service.reverseGeocode({ location: new naver.maps.LatLng(myLatlng),
        }, function(status, response) {
        	
            if (status !== naver.maps.Service.Status.OK) {
                return alert('Something wrong!');
            }

            var result = response.result, // 검색 결과의 컨테이너
            items = result.items; // 검색 결과의 배열

            cAddr = result.items[0].address;
            cLatlng = myLatlng.lat() + "," + myLatlng.lng()
            
           // cLatlng = myLatlng.coord.y + "," + myLatlng.coord.y;
           sido = items[0].addrdetail.sido;
           sigugun = items[0].addrdetail.sigugun;
           dongmyun = items[0].addrdetail.dongmyun;
           rest = items[0].addrdetail.rest;
           
           map.setCenter(myLatlng); // 검색된 좌표로 지도 이동
           if(!marker){
               marker = new naver.maps.Marker({
                   position: myLatlng,
                   map: map
               });
           }else{
                 marker.setPosition(myLatlng);
           }
           
                
           setInfo(result);
        });
        
        
    }


    //입력한 주소값에 대한 위치 가져오기
    function geocodeAddress() {
      
      
      var myaddress = document.getElementById('sAddr').value;
      
      if(myaddress == ""){
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
                
                var elements = response.v2.addresses[0].addressElements;
                for(var i =0; i<elements.length; i++){
                	if( elements[i].types[0] == "SIDO" ){
                		sido = elements[i].longName;
                	}else if( elements[i].types[0] == "SIGUGUN" ){
                		sigugun = elements[i].longName;
                	}else if( elements[i].types[0] == "DONGMYUN" ){
                		dongmyun = elements[i].longName;
                	}else if( elements[i].types[0] == "LAND_NUMBER" ){
                		rest = elements[i].longName;
                	}
                }
                // 검색 결과 갯수: result.total
                // 첫번째 결과 결과 주소: result.items[0].address
                // 첫번째 검색 결과 좌표: result.items[0].point.y, result.items[0].point.x
                
                myLatlng = new naver.maps.Point(result.items[0].point.x, result.items[0].point.y);
                
                map.setCenter(myLatlng); // 검색된 좌표로 지도 이동
               
        	}
       	  	catch(error) {
       	  		cAddr = "주소를 잘못 되었습니다.";
       	  		cLatlng = "";
        	}
          	finally {
          		setMarker();
          		setInfo();
        	}
          
           
         
          
          
          
      });
    }
    
    function setMarker(){
        
        if(!myLatlng)
            myLatlng = new naver.maps.LatLng(37.566535,126.97796919999996);    //기본 서울시청
            
         if(!marker){
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
                     
           if(tmpSplit.length != 2){
              
              $.toast({title: '주소검색 또는 마커검색으로 좌표을 다시 지정 하세요.', position: 'middle', backgroundColor: '#d60606', duration:2000 });
              
              return false;
           }
           
      var cUrl = "";    
      var str;
      
      if(mode == "WORK"){
          cUrl = "/admin/setWorkInfo";
          str = '{"work_seq": '+ seq +', "work_addr": "'+ cAddr +'", "work_sido": "' + sido + '", "work_sigugun": "' + sigugun + '", "work_dongmyun": "' + dongmyun + '", "work_rest": "' + rest + '" ,"work_latlng":"'+ cLatlng +'"}';
      }else if(mode == "ILBO"){//ILBO
          cUrl = "/admin/setIlboInfo";
          str = '{"ilbo_seq": '+ seq +', "ilbo_work_addr": "'+ cAddr +'", "work_sido": "' + sido + '", "work_sigugun": "' + sigugun + '", "work_dongmyun": "' + dongmyun + '", "work_rest": "' + rest + '" , "ilbo_work_latlng":"'+ cLatlng + '"}';
          
      }else if(mode == "WORKER"){
          cUrl = "/admin/setWorkerInfo";
          str = '{"worker_seq": '+ seq +', "worker_ilgaja_addr": "'+ cAddr +'", "worker_ilgaja_latlng":"'+ cLatlng + '"}';
          
      }else if(mode == "ORDER"){
    	  cUrl = "/admin/setOrderInfo";
          str = '{"order_seq": '+ seq +', "work_addr": "'+ cAddr +'", "work_sido": "' + sido + '", "work_sigugun": "' + sigugun + '", "work_dongmyun": "' + dongmyun + '", "work_rest": "' + rest + '" , "work_latlng":"'+ cLatlng + '"}';
      }
      
      var params = jQuery.parseJSON(str);
      $.ajax({
           type: "POST",
            url: cUrl,
           data: params,
       dataType: 'json',
        success: function(data) {
					  if( mode == "ORDER" ){
						  opener.parent.mapResponse(seq,params);
					  }else{
						  opener.parent.mapResponse(seq,cAddr,cLatlng);  
					  }			
                      
                      self.close();
                 },
    // beforeSend:showRequest,
          error: function(e) {
                   alert(e.responseText);
                 }
      });
      
    }
  
   
  
  function updateComment() {
         
      var addr_comment =  $("#comment").val();
      
      addr_comment = addr_comment.replace(/\r\n/g, '<br>');
      addr_comment = addr_comment.replace(/\n/g, '<br>');
      addr_comment = addr_comment.replace(/\r/g, '<br>');
  
      $("#addr_comment").val(addr_comment);
       
      
        if(mode == "WORK"){
            cUrl = "/admin/setWorkInfo";
            $("#seq").attr("name","work_seq");
            $("#addr_comment").attr("name","work_addr_comment");
            
          //  str = '{"work_seq": '+ seq +', "work_addr_comment": "'+ addr_comment +'"}';
        }else if(mode == "ILBO"){//ILBO
            cUrl = "/admin/setIlboInfo";
            $("#seq").attr("name","ilbo_seq");
            $("#addr_comment").attr("name","ilbo_work_addr_comment");
                      
          //  str = '{"ilbo_seq": '+ seq +', "ilbo_work_addr_comment": "'+ addr_comment + '"}';
            
        }else if(mode == "WORKER"){
            
            
        }
        
        
        var params = $("#mapFrm").serialize();
        
        
    
        $.ajax({
             type: "POST",
              url: cUrl,
             data: params,
         dataType: 'json',
          success: function(data) {
              $.toast({title: '저장 되었습니다.', position: 'middle', backgroundColor: '#5aa5da', duration:1500 });
              opener.parent.mapGridUpdate(mode, seq, addr_comment);   
                   },
      // beforeSend:showRequest,
            error: function(e) {
            	console.log(e);
                   }
        });
         
    }
    
        
    function gmapPrint() {
        
        window.print();
     
    }
        
      </script>
  </body>
</html>