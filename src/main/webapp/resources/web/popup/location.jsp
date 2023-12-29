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
    background-color: Yellow;
    
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
</style>
</head>

<%
	request.setCharacterEncoding("UTF-8");
	String seq = request.getParameter("location_seq");
	String lat = request.getParameter("location_lat");
	String lng = request.getParameter("location_lng");
	String kindCode = request.getParameter("location_kindCode");
	String ilbo_date = request.getParameter("ilbo_date");
%>

<body>
	<div id="floating-panel" class="noprint">
		<input type="checkbox" value="1" checked="checked" name="radiusChk">1Km 
		<input type="checkbox" value="3" checked="checked" name="radiusChk">3Km
		<input type="checkbox" value="5" checked="checked" name="radiusChk">5Km
		<input type="checkbox" value="10" name="radiusChk">10Km
		<input type="checkbox" value="20" name="radiusChk">20Km
		<input id="submit" type="button" value="적용하기"> 
	</div>
	<div id="map"></div>
	<form id="locationFrm">
		<input type="hidden" id="work_lat" name="work_lat" value="" />
		<input type="hidden" id="work_lng" name="work_lng" value="" />
		<input type="hidden" id="radius" name="radius" value="" />
		<input type="hidden" id="worker_job_code" name="worker_job_code" value="" />
		<input type="hidden" id="ilbo_date" name="ilbo_date" value="" />
	</form>
	<form id="ilboDTOFrm" name="ilboDTOFrm">
		<input type="hidden" id="worker_seq" name="worker_seq" value="" />
		<input type="hidden" id="worker_name" name="ilbo_worker_name" value="" />
		<input type="hidden" id="worker_phone" name="ilbo_worker_phone" value="" />
		<input type="hidden" id="worker_addr" name="ilbo_worker_addr" value="" />
		<input type="hidden" id="worker_latlng" name="ilbo_worker_latlng" value="" />
		<input type="hidden" id="worker_ilgaja_addr" name="ilbo_worker_ilgaja_addr" value="" />
		<input type="hidden" id="worker_ilgaja_latlng" name="ilbo_worker_ilgaja_latlng" value="" />
		<input type="hidden" id="worker_jumin" name="ilbo_worker_jumin" value="" />
		<input type="hidden" id="worker_job_code" name="ilbo_worker_job_code" value="" />
		<input type="hidden" id="worker_job_name" name="ilbo_worker_job_name" value="" />
		<input type="hidden" id="worker_barcode" name="ilbo_worker_barcode" value="" />
		<input type="hidden" id="worker_memo" name="ilbo_worker_memo" value="" />
		<input type="hidden" id="worker_bank_code" name="ilbo_worker_bank_code" value="" />
		<input type="hidden" id="worker_bank_name" name="ilbo_worker_bank_name" value="" />
		<input type="hidden" id="worker_bank_account" name="ilbo_worker_bank_account" value="" />
		<input type="hidden" id="worker_bank_owner" name="ilbo_worker_bank_owner" value="" />
		<input type="hidden" id="worker_bankbook_scan_yn" name="ilbo_worker_bankbook_scan_yn" value="" />
		<input type="hidden" id="worker_feature" name="ilbo_worker_feature" value="" />
		<input type="hidden" id="worker_blood_pressure" name="ilbo_worker_blood_pressure" value="" />
		<input type="hidden" id="worker_OSH_yn" name="ilbo_worker_OSH_yn" value="" />
		<input type="hidden" id="worker_jumin_scan_yn" name="ilbo_worker_jumin_scan_yn" value="" />
		<input type="hidden" id="worker_OSH_scan_yn" name="ilbo_worker_OSH_scan_yn" value="" />
		<input type="hidden" id="worker_app_status" name="ilbo_worker_app_status" value="" />
		<input type="hidden" id="worker_reserve_push_status" name="ilbo_worker_reserve_push_status" value="" />
		<input type="hidden" id="worker_app_use_status" name="ilbo_worker_app_use_status" value="" />
		<input type="hidden" id="worker_owner" name="worker_owner" value="" />
		<input type="hidden" id="employer_rating" name="employer_rating" value="" />
		<input type="hidden" id="worker_sex" name="ilbo_worker_sex" value="" />
		<input type="hidden" id="ilbo_seq" name="ilbo_seq" value="" />
	</form>
<script>
	var marker;
	var marker1;
	var cLatlng;
	var cAddr;
	var info = false;
	var mapOptions = {
	        zoomControl: true,
	        zoomControlOptions: {
	            style: naver.maps.ZoomControlStyle.SMALL,
	            position: naver.maps.Position.BOTTOM_RIGHT
	        }
	    };
	var map = new naver.maps.Map('map', mapOptions);
	var contentString = "test";
	var seq = "<%=seq%>";
	var lat = "<%=lat%>";
	var lng = "<%=lng%>";
	var kindCode = "<%=kindCode %>";
	var ilbo_date = "<%=ilbo_date %>";
	var myLatlng = null;
	var type = 0;
	var circleArr = new Array();
	var mapArr = new Array();
	var overlays = [];
	var infoWindows = [];
	var workerDTO = [];
	
	myLatlng = new naver.maps.LatLng(Number(lat), Number(lng));
	
	map.setZoom(13);
	
	geocodeLatLng();    
	
	circleArr[0] = new naver.maps.Circle({
		map: map,
		center: myLatlng,
		radius: 1000,
		fillColor: 'green',
		fillOpacity: 0.1
	});
	
	circleArr[1] = new naver.maps.Circle({
		map: map,
		center: myLatlng,
		radius: 3000,
		fillColor: 'green',
		fillOpacity: 0.1
	});
	
	circleArr[2] = new naver.maps.Circle({
		map: map,
		center: myLatlng,
		radius: 5000,
		fillColor: 'green',
		fillOpacity: 0.1
	});
	
	getList('5');
	
	/**
	 * 사용자 정의 오버레이 구현하기
	 */
	var CustomOverlay = function(options, name, phone) {
		var content ='<div style="position:absolute;left:0;top:0;width:180px;height:23px;background-color:#fff;border:1px solid black;">' + name + ' | ' +  phone + '</div>'
	    this._element = $(content);

	    this.setPosition(options.position);
	    this.setMap(options.map || null);
	};

	// CustomOverlay는 OverlayView를 상속받습니다.
	CustomOverlay.prototype = new naver.maps.OverlayView();

	CustomOverlay.prototype.constructor = CustomOverlay;

	CustomOverlay.prototype.onAdd = function() {
	    var overlayLayer = this.getPanes().overlayLayer;
	    
	    this._element.appendTo(overlayLayer);
	};

	CustomOverlay.prototype.draw = function() {
	    // 지도 객체가 설정되지 않았으면 draw 기능을 하지 않습니다.
	    if (!this.getMap()) {
	        return;
	    }

	    // projection 객체를 통해 LatLng 좌표를 화면 좌표로 변경합니다.
	    var projection = this.getProjection(),
	        position = this.getPosition();

	    var pixelPosition = projection.fromCoordToOffset(position);

	    this._element.css('left', pixelPosition.x - 82);
	    this._element.css('top', pixelPosition.y - 60);
	};

	CustomOverlay.prototype.onRemove = function() {
	    this._element.remove();
	    
	    // 이벤트 핸들러를 설정했다면 정리합니다.
	    this._element.off();
	};

	CustomOverlay.prototype.setPosition = function(position) {
	    this._position = position;
	    this.draw();
	};

	CustomOverlay.prototype.getPosition = function() {
	    return this._position;
	};

	function getList(radius) {
		$("#work_lat").val(lat);
		$("#work_lng").val(lng);
		$("#radius").val(radius);
		$("#worker_job_code").val(kindCode);
		$("#ilbo_date").val(ilbo_date);
		
		for(var i = 0; i < mapArr.length; i++) {
			mapArr[i].setMap(null);
			overlays[i].setMap(null);
		}
		
		var params = $("#locationFrm").serialize();
		
		$.ajax({
			type : "POST",
			url : "/admin/locationMap",
			data : params,
			dataType : 'json',
			success : function(data) {
				for(var i = 0; i < data.workerList.length; i++) {
					var workerLat = data.workerList[i].worker_ilgaja_latlng.split(',')[0];
					var workerLng = data.workerList[i].worker_ilgaja_latlng.split(',')[1];
					
					if(data.workerList[i].output_status_code == '100002' || 
							data.workerList[i].output_status_code == '100017' ||
							data.workerList[i].output_status_code == '100012' ||
							data.workerList[i].output_status_code == '100013' ||
							data.workerList[i].output_status_code == '100020' ||
							data.workerList[i].output_status_code == '100019' ||
							!data.workerList[i].output_status_code ||
							data.workerList[i].output_status_code == '0') {
						mapArr[i] = new naver.maps.Marker({
							position : new naver.maps.LatLng(workerLat, workerLng),
							map : map,
							icon: {
						        url: '/resources/web/images/marker_img.png',
						        scaledSize: new naver.maps.Size(20, 30)
						    }
						});
						
						var btnStyle = '';
						btnStyle += '<div class="btn-module mgtL" style="text-align: center;">';
						btnStyle += '	<a style="background-color : #5aa5da; border : 1px solid #5aa5da; text-decoration : none; color : black;" href="javascript:void(0);" onclick="javascript:fn_insert();" class="btnStyle04">적용</a>';
					    btnStyle += '</div>';
					}else if(data.workerList[i].output_status_code == '100005' ||
								data.workerList[i].output_status_code == '100003' ||
								data.workerList[i].output_status_code == '100014' ||
								data.workerList[i].output_status_code == '100015') {
						mapArr[i] = new naver.maps.Marker({
							position : new naver.maps.LatLng(workerLat, workerLng),
							map : map,
							icon: {
						        url: '/resources/web/images/marker_img_blue.png',
						        scaledSize: new naver.maps.Size(20, 30)
						    }
						});
						
						var btnStyle = '';
					}
					
					var infoWindow = new naver.maps.InfoWindow({
						content : '이름 : ' +  data.workerList[i].worker_name + '<br>번호 : ' + data.workerList[i].worker_phone + '<br>직종 : ' + data.workerList[i].worker_job_name + '<br><br>' + btnStyle
					});
					
					// 오버레이 생성
					var overlay = new CustomOverlay({
					    position: new naver.maps.LatLng(workerLat, workerLng),
					    map: map
					}, data.workerList[i].worker_name, data.workerList[i].worker_phone, data.workerList[i].worker_job_name);

					infoWindows.push(infoWindow);
					overlays.push(overlay);
					workerDTO.push(data.workerList[i]);
				}
			},
			error : function(e) {
				console.log(e);
			},
			complete : function() {
				for (var i=0, ii=mapArr.length; i<ii; i++) {
				    naver.maps.Event.addListener(mapArr[i], 'click', getClickHandler(i));
				}
			}
		});
	}
	
	function getClickHandler(i) {
		return function(e) {
	        var markerInfo = mapArr[i],
	            infoWindow = infoWindows[i];

	        if (infoWindow.getMap()) {
	            infoWindow.close();
	        } else {
	            infoWindow.open(map, markerInfo);
	            
	            $("#worker_seq").val(workerDTO[i].worker_seq);
	            $("#worker_name").val(workerDTO[i].worker_name);
	            $("#worker_phone").val(workerDTO[i].worker_phone);
	            $("#worker_addr").val(workerDTO[i].worker_addr);
	            $("#worker_latlng").val(workerDTO[i].worker_latlng);
	            $("#worker_ilgaja_addr").val(workerDTO[i].worker_ilgaja_addr);
	            $("#worker_ilgaja_latlng").val(workerDTO[i].worker_ilgaja_latlng);
	            $("#worker_jumin").val(workerDTO[i].worker_jumin);
	            $("#worker_job_code").val(workerDTO[i].worker_job_code);
	            $("#worker_job_name").val(workerDTO[i].worker_job_name);
	            $("#worker_barcode").val(workerDTO[i].worker_barcode);
	            $("#worker_memo").val(workerDTO[i].worker_memo);
	            $("#worker_bank_code").val(workerDTO[i].worker_bank_code);
	            $("#worker_bank_name").val(workerDTO[i].worker_bank_name);
	            $("#worker_bank_account").val(workerDTO[i].worker_bank_account);
	            $("#worker_bank_owner").val(workerDTO[i].worker_bank_owner);
	            $("#worker_bankbook_scan_yn").val(workerDTO[i].worker_bankbook_scan_yn);
	            $("#worker_feature").val(workerDTO[i].worker_feature);
	            $("#worker_blood_pressure").val(workerDTO[i].worker_blood_pressure);
	            $("#worker_OSH_yn").val(workerDTO[i].worker_OSH_yn);
	            $("#worker_jumin_scan_yn").val(workerDTO[i].worker_jumin_scan_yn);
	            $("#worker_OSH_scan_yn").val(workerDTO[i].worker_OSH_scan_yn);
	            $("#worker_app_status").val(workerDTO[i].worker_app_status);
	            $("#worker_reserve_push_status").val(workerDTO[i].worker_reserve_push_status);
	            $("#worker_app_use_status").val(workerDTO[i].worker_app_use_status);
	            $("#worker_owner").val(workerDTO[i].owner_id);
	            $("#employer_rating").val(workerDTO[i].worker_rating);
	            $("#ilbo_seq").val(seq);
	        }
	    }
	}
	
	$(document).ready(function() {
		$("#submit").on("click", function() {
			var chkArr = new Array();
			var arrCnt = 0;
			
			for(var i = 0; i < $("input[name=radiusChk]").length; i++) {
				if($("input[name=radiusChk]").eq(i).is(":checked")) {
					chkArr[arrCnt] = $("input[name=radiusChk]").eq(i).val();
					
					++arrCnt;
				}	
			}
			
			if(arrCnt < 1) {
				alert("보여질 반경을 선택해주세요.");
				
				return false;
			}
			
			for(var i = 0; i < circleArr.length; i++) {
				circleArr[i].setMap(null);
			}

			for(var i = 0; i < arrCnt; i++) {
				var radius;
				
				if(chkArr[i] == '1') {
					radius = '1000';	
				}else if(chkArr[i] == '3') {
					radius = '3000';
				}else if(chkArr[i] == '5') {
					radius = '5000';
				}else if(chkArr[i] == '10') {
					radius = '10000';
				}else {
					radius = '20000';
				}
				
				circleArr[i] = new naver.maps.Circle({
					map : map,
					center : myLatlng,
					radius : radius,
					fillColor : 'green',
					fillOpacity : 0.1
				});
			}
			
			if(chkArr[arrCnt - 1] == '1') {
				map.setZoom(15);
			}else if(chkArr[arrCnt - 1] == '3') {
				map.setZoom(14);
			}else if(chkArr[arrCnt - 1] == '5') {
				map.setZoom(13);
			}else if(chkArr[arrCnt - 1] == '10') {
				map.setZoom(12);
			}else {
				map.setZoom(11);
			}	
			
			getList(chkArr[arrCnt - 1]);
		});
		
	});
	
	//마커 위치에 대한 주소 가져오기
	function geocodeLatLng() {
		naver.maps.Service.reverseGeocode({ 
			location: new naver.maps.LatLng(myLatlng),
		}, function(status, response) {
			if(status !== naver.maps.Service.Status.OK) {
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
			}
		});
	}
	
	function fn_insert() {
		var params = $("#ilboDTOFrm").serialize();
		
		$.ajax({
			type: "POST",
			url: "/admin/setIlboInfoToLocation",
			data: params,
			dataType: 'json',
			success: function(data) {
				opener.location.href = 'javascript:fn_reload();';
				
				window.self.close();
			},
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( data.status == "901" ) {
					location.href = "/admin/login";
				}
			}
		});
	}
</script>
</body>
</html>