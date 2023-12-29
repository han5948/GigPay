<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/common/css/slider.css' />" media="all"></link>

<script type="text/javascript">
	var _addLocationList = [];
	var _delLocationList = [];
	var isSelect = false;
	$(function() {
		// 행 추가
  		$("#btnAddForm").click( function() {
  			openAddForm();
  		});
		
		$("#btnOptionAdd").click( function(){
			optionAdd();
		});
		
		$("#btnJobEdit").click( function(){
			jobEdit();
		});
		
		$("#btnDel").click( function(){
			optionDel();
		});
		
		$("#btnLocationAdd").click( function(){
			locationAdd();
		});
		
		$("#btnLocationSave").click( function(){
			locationSave();
		});
		
	   	$("#onOff").click(function(){
	   		$(".toggles").toggle();
	   		
	   		// 체크여부 확인
	   		if($("#onOff").is(":checked") == true) {
	   			console.log("on");
	   			setTransferWorkerOnOff(1);
	   		}else{
	   			console.log("off");
	   			setTransferWorkerOnOff(0);
	   		}
		});
		
		$(document).on("click", ".jobList", function() {
			if( $(this).hasClass('on') ){
				$(this).removeClass('on');
				return;
			}
			
        	$(this).addClass('on');
	    });
		
		getAdministrativeDistrictList();
		
		$(".jobRow").hide();
		
		$("input[name='job_kind']:radio").change(function () {
			$(".jobList").removeClass('on');
			
	        var _jobKind = this.value;
	        if( _jobKind == "0"){
	        	$(".jobRow").hide();    	
			}else if( _jobKind == "1" ){
	        	$("#jobKind2").hide();
	        	$("#jobKind1").show();
	        	$("#editJobKind2").hide();
	        	$("#editJobKind1").show();
	        }else{
	        	$("#jobKind1").hide();
	        	$("#jobKind2").show();
	        	$("#editJobKind1").hide();
	        	$("#editJobKind2").show();
	        }
		});
		
		var use_opts = "1:사용;0:미사용";
		var gender_opts = "N:무관;M:남자;F:여자";
		var osh_opts = "0:미이수;1:이수;2:무관";
		var job_kind_opts = "0:무관;1:일반인력;2:기술인력";
		var srh_all_opt = ":모두;";
		
		// jqGrid 생성
		$("#list").jqGrid({
			url: "/admin/transferWorker/getTransferWorkerOptionList",
			datatype: "json",
			mtype: "POST",
			postData: {
				filters : '{"groupOp":"AND","rules":[{"field":"o.use_yn","op":"eq","data":"1"}]}',
                page: 1
			},
			sortname: 'o.option_rank',
			sortorder: "asc",
			rowList: [25, 50, 100, 200],				// 한페이지에 몇개씩 보여 줄건지?
			height: "100%",
			width: "1500px",
			rowNum: 50,
			pager: '#paging',                           // 네비게이션 도구를 보여줄 div요소
			viewrecords: true,                          // 전체 레코드수, 현재레코드 등을 보여줄지 유무
			multiselect: true,
			multiboxonly: true,
			caption: "구직자 이관 옵션 목록",					// 그리드타이틀
			rownumbers: true,                           // 그리드 번호 표시
			rownumWidth: 40,                            // 번호 표시 너비 (px)
			cellEdit: true ,
			cellsubmit: "remote" ,
			cellurl: "/admin/transferWorker/setTransferWorkerOption",            // 추가, 수정, 삭제 url
			reorderRows:true,
			
// 			jsonReader: {
// 				id: "transfer_seq",
// 				repeatitems: false
// 			},

            // 컬럼명
			colNames: ['순번', '우선순위', '지점순번', '지점명', '성별', '직종', '직종선택', '기초안전보건교육', '지역', '이용상태', '등록자', '등록일'],

            // 컬럼별 속성
			colModel: [
				{name: 'transfer_seq', index: 'o.transfer_seq', key: true, width: 10, search: false, searchoptions: {sopt: ['cn','eq','nc','ge','le']}},
				{name: 'option_rank', index: 'o.option_rank', align: "center", width: 12, formatter: formatRank, unformat: unformatRank, search: true, searchoptions: {sopt: ['eq']}},
				{name: 'company_seq', index: 'o.company_seq', hidden: true},
				{name: 'company_name', index: 'c.company_name', align: "center", width: 40, search: true, searchoptions: {sopt: ['cn','eq','nc','ge','le']}
					, editable : true
					, edittype: "text"
                    	, editrules: {edithidden: true, required: false, custom: true, custom_func: validCompanyName}
                		, searchoptions: {sopt: ['cn','eq','nc']}
                 		, editoptions: {
							size: 30,
                        	dataInit: function (e, cm) {
								$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
								$(e).autocomplete({
									source: function (request, response) {
										$.ajax({
											url: "/admin/getCompanyNameList", 
											type: "POST", 
											dataType: "json",
											data: { term: request.term, srh_use_yn: 1 },
											success: function (data) {
												response($.map(data, function (item) {
													return item;
												}))
											},
                                            beforeSend: function(xhr) {
                                            	xhr.setRequestHeader("AJAX", true);
											},
                                            error: function(e) {
                                            	if ( data.status == "901" ) {
													location.href = "/admin/login";
												} else {
													alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
												}
											}
										})
									},
									minLength: 2,
                                    focus: function (event, ui) {
										return false;
									},
                                    select: function (event, ui) {
										var lastsel = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
										$(e).val(ui.item.label);
										$("#list").jqGrid('setCell', lastsel, 'company_seq', ui.item.code, '', true);
										isSelect = true;
										//Enter가 아니면
						                if( event.keyCode != "13" ){
						                	var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
						                	$("#list").jqGrid("saveCell", iRow, cm.iCol);
						                }

										return false;
									}
								});
                         	}
                 		}
				},
				{name: 'gender', index: 'o.gender', align: "center", width: 10, editable: true, edittype: "select", editoptions: {value: gender_opts}, formatter: "select",
					stype:"select", search: true, searchoptions: {sopt: ['eq'], value: srh_all_opt + gender_opts}
				},
				{name: 'job_kind', index: 'o.job_kind', align: "center", width: 20, edittype: "select", editoptions: {value: job_kind_opts}, formatter: "select",
					stype:"select", search: true, searchoptions: {sopt: ['eq'], value: srh_all_opt + job_kind_opts}
				},
				{name: 'job_name', index: 'o.job_name', align: "center", width: 60, formatter: formatJobCode, search: true, searchoptions: {sopt: ['cn','eq','nc','ge','le']}},
				{name: 'osh_yn', index: 'o.osh_yn', align: "center", width: 20, editable: true, edittype: "select", editoptions: {value: osh_opts}, formatter: "select",
					stype:"select", search: true, searchoptions: {sopt: ['eq'], value: srh_all_opt + osh_opts}
				},
				{name: 'location_cnt', index: 'o.location_cnt', align: "center", width: 5, formatter: formatLocation, search: false},
				{name: 'use_yn', index: 'o.use_yn', align: "center", width: 10, editable:true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					stype:"select", search: true, searchoptions: {sopt: ['eq'], value: use_opts, defaultValue:1}
				},
				{name: 'reg_admin', index: 'o.reg_admin', align: "center", width: 20, search: true, searchoptions: {sopt: ['cn','eq','nc','ge','le']}},
				{name: 'reg_date', index: 'o.reg_date', align: "center", width: 40, search: true, searchoptions: {sopt: ['cn','eq','nc','ge','le']}},

			],
         	// row 를 선택 했을때 편집 할 수 있도록 한다.
         	onSelectRow: function(id) {
// 				if ( id && id !== lastsel ) {
// 					$("#list").jqGrid('restoreRow', lastsel);

//                     $("#list").jqGrid('editRow', id, {
// 						keys: true,
// 						oneditfunc: function() {
// 						},
// 						successfunc: function() {
// 							lastsel = -1;

// 							return true;
// 						},
// 					});
                    
// 					lastsel = id;
// 				}
			},
        	// cell을 클릭 시
			onCellSelect: function(rowid, index, contents, event) {
				//row data 전체 가져 오기
//                 var list = $("#list").jqGrid('getRowData', rowid);
//                 selectCompanySeq = $("#list").jqGrid('getRowData', rowid).company_seq;
//                 lastsel = rowid;
            },
            // submit 전
    		beforeSubmitCell: function(rowid, cellname, value) {
//     			var appUseStatus = $("#list").jqGrid('getRowData', rowid).worker_app_use_status;
    			if(cellname == 'company_name' ) {
    				return {
    					cellname : 'company_seq',
    					company_seq : $("#list").jqGrid('getRowData', rowid).company_seq,
    					transfer_seq: rowid
    				}
//     				//if(confirm("구직자에게 문자로 알림을 보내시겠습니까?")){
//     					return {
//     						cellname : cellname,
//     						worker_seq : rowid,
//     						company_seq : $("#list").jqGrid('getRowData', rowid).company_seq
//     						//companyFlag : '1'
//     					}	
//     				//}
    			}
    			
// 				if ( cellname == "use_yn" && value == "0" ) {
// 					$("#"+rowid).hide();
// 				}
                       
//                 if(cellname == "worker_ilgaja_addr"){
//                 	fomateGeocodeNaver(rowid, cellname, value );
// 					return false;                	
// 				} 

//                 if (cellname == "worker_addr" ) {
//                 	$("#list").jqGrid('setCell', rowid,cellname, value, '', true);  //    바뀐 값을 먼저 cell에 넣어 둔다. 그렇치 않으면 ilgaja_addr_edit 에서 이상하게 인식 한다.
//                     $("#list").jqGrid('setCell', rowid,'ilgaja_addr_edit', "1", '', true);  
                            
//                     return {
//                     	worker_seq: rowid,
// 					};
// 				} 
                        
//                 if (cellname == "worker_rating" ) {
//                 	return {
//                     	cellname : cellname,
//                     	worker_seq: rowid
//                     };
// 				}
                
                return {
                	cellname : cellname,
                	transfer_seq: rowid
				};
			},
        	// Grid 로드 완료 후
        	loadComplete: function(data) {
				//총 개수 표시
                $("#totalRecords").html(numberWithCommas( $("#list").getGridParam("records") ));
//                 if( $("#list").getGridParam("records") < 1 ){
// 			    	$("#btnExcel").click(function(){
// 			        	alert("출력할 결과물이 없습니다.");
// 					})
// 				}else{
// 			    	$("#btnExcel").click(function(){
// 			        	document.defaultFrm.action = "/admin/workerListExcel";
//                         document.defaultFrm.submit();
// 					})
// 				}

// 				isGridLoad = true;
				$(".wrap-loading").hide();
			},
			afterInsertRow: function(rowId, rowdata, rowelem) {	//그리드를 그릴때 호출 되는 구나....
// 				fn_setEditable(rowId, rowdata.company_seq);
			},
			loadBeforeSend: function(xhr) {
//             	isGridLoad = false;
                optHTML = "";
//                        closeOpt();
                xhr.setRequestHeader("AJAX", true);
                        
                $(".wrap-loading").show();
			},
			loadError: function(xhr, status, error) {
            	if ( xhr.status == "901" ) {
                	location.href = "/admin/login";
				}
			},
                      
       		beforeSelectRow: selectRowid                
		});

  		$("#list").jqGrid('navGrid', "#paging", {edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});
  		$("#list").jqGrid('filterToolbar', {searchOperators : true});
  
  		//헤더툴팁
  		setTooltipsOnColumnHeader($("#list"), 7, "A:6 B:5 C:4 D:3 E:2 F:1 미평가:0");
	});
	
	function setTransferWorkerOnOff(useYn){
		var _url = "/admin/transferWorker/setTransferWorkerOnOff";
		var _data = {
				function_type : 'T',
				use_yn : useYn
		};
		
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			toastOk("정상적으로 처리 되었습니다.");
    		}else {
    			toastFail(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert(data);
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function validCompanyName(value, cellTitle, valref){
		if( !isSelect ){
			return [false, '] 지점명을 선택해주세요.'];
		}
		return [true, ''];
	}
	
	function locationSave(){
		var _url = "/admin/transferWorker/locationSave";
		var _data = {
				del_location_list : _delLocationList
				, transfer_seq : $("#popup-layer3 input[name=transfer_seq]").val()
		};
		
		for(var i=0; i<_addLocationList.length; i++){
			_data['add_location_list[' + i + '].sido_code'] = _addLocationList[i].sido_code;
			_data['add_location_list[' + i + '].sido_name'] = _addLocationList[i].sido_name;
			_data['add_location_list[' + i + '].sigungu_code'] = _addLocationList[i].sigungu_code;
			_data['add_location_list[' + i + '].sigungu_name'] = _addLocationList[i].sigungu_name;
			_data['add_location_list[' + i + '].eupmyeondong_code'] = _addLocationList[i].eupmyeondong_code;
			_data['add_location_list[' + i + '].eupmyeondong_name'] = _addLocationList[i].eupmyeondong_name;
		}
		
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			alert("저장 완료되었습니다.");
    			
    			closeIrPopup('popup-layer3');
    			$("#list").trigger("reloadGrid");
    		}else {
    			alert(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert(data);
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function initSelectBox(id){
		$("#"+id).html("<option value='0' selected='selected'>전체</option>");
	}
	
	function setSelectBox(list, id){
		for( var i=0; i<list.length; i++ ){
			$("#" +id).append("<option value=" + list[i].code + ">" + list[i].name + "</option>");	
		}
	}
	
	function sigunguSejong(list){
		$("#sigunguList").append("<option value=" + list[0].code + " selected></option>");
		$("#sigunguList option:eq(1)").prop("selected", true);
		
		setSelectBox(list[0].eupmyeondongList, "eupmyeondongList");
		
		$("#sigunguList").attr("disabled", true);
	}
	
	// 	행정구역목록 가져오기
	function getAdministrativeDistrictList(){
		var _url = "/admin/transferWorker/getAdministrativeDistrictList";
		commonAjax(_url, null, true, function(data) {
			
    		for( var i=0; i<data.length; i++ ){
    			$("#sidoList").append("<option value=" + data[i].code + ">" + data[i].name + "</option>");	
    		}
    		
    		$("#sidoList").change(function(){
    			$("#sigunguList").attr("disabled", false);
    			
    			var _index = $("#sidoList option").index($("#sidoList option:selected")) - 1;
    			initSelectBox("sigunguList");
    			initSelectBox("eupmyeondongList");
    			
    			if( _index < 0 ){
    				return;
    			}
    			
    			if(data[_index].code == "36"){
    				sigunguSejong(data[_index].sigunguList);
    				return;
    			}
    			
    			setSelectBox(data[_index].sigunguList, "sigunguList");
    		});
    		
    		$("#sigunguList").change(function(){
    			var _sidoIndex = $("#sidoList option").index($("#sidoList option:selected")) - 1;
    			var _sigunguIndex = $("#sigunguList option").index($("#sigunguList option:selected")) - 1;
    			initSelectBox("eupmyeondongList");
    			if( _sigunguIndex < 0 ){
    				return;
    			}
    			setSelectBox(data[_sidoIndex].sigunguList[_sigunguIndex].eupmyeondongList, "eupmyeondongList");
    		});
    	}, function(data) {
    		//errorListener
    		alert("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function openAddForm() {
		initOptionAddForm();
		openIrPopup('popup-layer');
	}
	
	function initOptionAddForm(){
		$("#companyList option:eq(0)").prop("selected", true);
		$('input:radio[name=gender]').eq(0).click();
		$('input:radio[name=OSH_scan_yn]').eq(0).click();
		$('input:radio[name=job_kind]').eq(0).click();
		$(".jobList").removeClass('on');
	}
	
	//옵션 추가
	function optionAdd(){
		
		var _url = "/admin/transferWorker/addTransferWorkerOption";
		var _data = $("#optionTable").find('input').serializeArray();
		_data.push({ name : "company_seq", value : $("#companyList").val() });
		_data.push({ name : "company_name", value : $("#companyList option:checked").text() });
		
		var _jobKind = $("input[name='job_kind']:checked").val();
		if( _jobKind != "0" ){
			var _jobChoiceCnt = $(".jobList.on").length;
			if( _jobChoiceCnt == 0 ){
				alert("직종을 선택해 주세요.");
				return;
			}
			var _jobCodeList = [];
			var _jobNameList = [];
			
			$(".jobList.on").each(function(){
				_jobCodeList.push($(this).attr("job_seq"));
				_jobNameList.push($(this).text());
			})
			_data.push({ name : "job_code", value : _jobCodeList.toString()});
			_data.push({ name : "job_name", value : _jobNameList.toString()});	
		}
		
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			alert("추가 완료되었습니다.");
    			$("#list").trigger("reloadGrid");
    			closeIrPopup('popup-layer');
    		}else {
    			alert(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function optionDel(){
		var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
		
		if( recs.length < 1 ){
			alert("최소 1개 이상 선택하셔야합니다.");
			return;
		}
		
		if(!confirm("선택사항을 삭제하시겠습니까?")){
			return;
		}
		
		var params = {
			sel_transfer_seq : recs
		};
		var rows = recs.length;
	
		$.ajax({
			type: "POST",
			url: "/admin/transferWorker/removeTransferWorkerOption",
			data: params,
			dataType: 'json',
			success: function(data) {
				$("#list").trigger("reloadGrid");
			},
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( data.status == "901" ) {
					location.href = "/admin/login";
				} else {
					alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
	    });
	}
	
	function locationAdd(){
		var _sidoCode = $("#sidoList option:selected").val();
		var _sidoName = $("#sidoList option:selected").text();
		var _sigunguCode = $("#sigunguList option:selected").val();
		var _sigunguName = $("#sigunguList option:selected").text();
		var _eupmyeondongCode = $("#eupmyeondongList option:selected").val();
		var _eupmyeondongName = $("#eupmyeondongList option:selected").text();
		
		var location = {
				sido_code: _sidoCode
				, sido_name: _sidoName
				, sigungu_code: _sigunguCode
				, sigungu_name: _sigunguName
				, eupmyeondong_code: _eupmyeondongCode
				, eupmyeondong_name: _eupmyeondongName
		}
		
		var _locationDataList = getLocationDataList();
		var _idx = _locationDataList.findIndex(i => (i.sido_code + i.sigungu_code + i.eupmyeondong_code) == (_sidoCode + _sigunguCode + _eupmyeondongCode) );
		if( _idx > -1 ){
			alert("이미 선택된 지역입니다");
			return;
		}
		
		if( _locationDataList.findIndex(i => i.sido_code == 0) > -1 ){
			alert("전국이 선택되어 있습니다.");
			return;
		}
		
		if( _sidoCode == 0 ){
			if( _locationDataList.length > 0 ){
				alert("전국을 설정하실 땐 다른지역을 모두 삭제해 주세요.");
				return;
			}
		}
		
		if( _locationDataList.findIndex(i => i.sido_code == _sidoCode && i.sigungu_code == 0 ) > -1 ){
			alert(_sidoName + " 전체가 선택되어 있습니다.");
			return;
		}
		
		if( _sigunguCode == 0 ){
			var sigunguIdx = _locationDataList.findIndex(i => i.sido_code == _sidoCode);
			if( sigunguIdx > -1 ){
				alert(_sidoName + "의 모든 시군구를 삭제해 주세요.");
				return;
			}
		}
		
		if( _locationDataList.findIndex(i => i.sido_code + i.sigungu_code == _sidoCode + _sigunguCode && i.eupmyeondong_code == 0 ) > -1 ){
			alert(_sidoName + " " + _sigunguName +  " 전체가 선택되어 있습니다.");
			return;
		}
		
		if( _eupmyeondongCode == 0 ){
			var eupmyeondongIdx = _locationDataList.findIndex(i => i.sido_code + i.sigungu_code == _sidoCode + _sigunguCode);
			if( eupmyeondongIdx > -1 ){
				alert(_sidoName + " " + _sigunguName + "의 모든 읍면동을 삭제해 주세요.");
				return;
			}
		}
		
		_addLocationList.push(location);	
		drawLocationInfo(location);
	}
	function getLocationDataList(){
		var resultList = [];
		var _trArr = $("#locationBody").find("tr");
		
		for(var i=0; i<_trArr.length; i++){
			var _sidoCode = _trArr.eq(i).find("input[name='sido_code']").val();
			var _sidoName = _trArr.eq(i).find("td").eq(0).text();
			var _sigunguCode = _trArr.eq(i).find("input[name='sigungu_code']").val();
			var _sigunguName = _trArr.eq(i).find("td").eq(1).text();
			var _eupmyeondongCode = _trArr.eq(i).find("input[name='eupmyeondong_code']").val();
			var _eupmyeondongName = _trArr.eq(i).find("td").eq(2).text();
			var locationInfo = {
					sido_code: _sidoCode
					, sido_name: _sidoName
					, sigungu_code: _sigunguCode
					, sigungu_name: _sigunguName
					, eupmyeondong_code: _eupmyeondongCode
					, eupmyeondong_name: _eupmyeondongName
			}
			resultList.push(locationInfo);
		}
		
		return resultList;
	}
	
	function drawLocationInfo(item){
		var _txt = "";
		
		_txt += "<tr>";
		_txt += "<td>";
		_txt += "<input type='hidden' name='sido_code' value=\"" + item.sido_code + "\">";
		_txt += item.sido_name;
		_txt += "</td>";
		_txt += "<td>";
		_txt += "<input type='hidden' name='sigungu_code' value=\"" + item.sigungu_code + "\">";
		_txt += item.sigungu_name;
		_txt += "</td>";
		_txt += "<td>";
		_txt += "<input type='hidden' name='eupmyeondong_code' value=\"" + item.eupmyeondong_code + "\">";
		_txt += item.eupmyeondong_name;
		_txt += "</td>";
		_txt += "<td>";
		_txt += "<a onclick=\"locationDelete(this)\" style='cursor: pointer;'>삭제</a>";
		_txt += "</td>";
		_txt += "</tr>";
		
		$("#locationBody").append(_txt);
	}
	
	function initLocationForm(){
		_addLocationList = [];
		_delLocationList = [];
		$("#sigunguList").prop("disabled", false);

 		$("#sidoList option:eq(0)").prop("selected", true);
 		$("#sidoList option:eq(0)").trigger("change");
// 		$("#sigunguList option:eq(0)").prop("selected", true);
// 		$("#eupmyeondongList option:eq(0)").prop("selected", true);
	}
	
	function openLocationForm(transferSeq){
		initLocationForm();
		
		$("#popup-layer3 input[name=transfer_seq]").val(transferSeq);
		
		var _url = "/admin/transferWorker/getTransferWorkerLocationList";
		var _data = {
				transfer_seq : transferSeq
		}
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			drawLocationTable(data.list);
    			openIrPopup("popup-layer3");
    		}else {
    			alert(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function drawLocationTable(list){
		var _txt = "";
		
		for(var i=0; i < list.length; i++){
			var item = list[i];
			_txt += "<tr>";
			_txt += "<td>";
			_txt += "<input type='hidden' name='sido_code' value=\"" + item.sido_code + "\">";
			_txt += item.sido_name;
			_txt += "</td>";
			_txt += "<td>";
			_txt += "<input type='hidden' name='sigungu_code' value=\"" + item.sigungu_code + "\">";
			_txt += item.sigungu_name;
			_txt += "</td>";
			_txt += "<td>";
			_txt += "<input type='hidden' name='eupmyeondong_code' value=\"" + item.eupmyeondong_code + "\">";
			_txt += item.eupmyeondong_name;
			_txt += "</td>";
			_txt += "<td>";
			_txt += "<a onclick=\"locationDelete(this, '"+ item.location_seq + "')\" style='cursor: pointer;'>삭제</a>";
			_txt += "</td>";
			_txt += "</tr>";
		}
		
		$("#locationBody").html(_txt);
	}
	
	function locationDelete(obj, locationSeq){
		var _tr = $(obj).parent().parent();
		
		if( locationSeq !== undefined ){
			_delLocationList.push(locationSeq);
		}else{
			
			var _sidoCode = _tr.find("input[name=sido_code]").val();
			var _sigunguCode = _tr.find("input[name=sigungu_code]").val();
			var _eupmyeondongCode = _tr.find("input[name=eupmyeondong_code]").val();
			var _idx = _addLocationList.findIndex(i => (i.sido_code + i.sigungu_code + i.eupmyeondong_code) == (_sidoCode + _sigunguCode + _eupmyeondongCode) );
			
			if( _idx > -1 ){
				_addLocationList.splice(_idx, 1);
			}
		}
		_tr.remove();
	}
	
	function rankDown(transferSeq, optionRank){
		optionRank *= 1;
		var _recordCnt = $("#list").getGridParam("records");
		if( optionRank == _recordCnt ){
			return;
		}

		var _idxs = $("#list").jqGrid('getDataIDs');
		var _index = _idxs.indexOf(transferSeq);
		var targetTransferSeq = _idxs[_index+1];
		var targetOptionRank = $("#list").jqGrid('getRowData', targetTransferSeq).option_rank;
		
		rankChange(transferSeq, targetOptionRank, targetTransferSeq, optionRank );
	}
	
	function rankUp(transferSeq, optionRank){
		optionRank *= 1;
		if( optionRank == 1 ){
			return;
		}
		
		var _idxs = $("#list").jqGrid('getDataIDs');
		var _index = _idxs.indexOf(transferSeq);
		var targetTransferSeq = _idxs[_index-1];
		var targetOptionRank = $("#list").jqGrid('getRowData', targetTransferSeq).option_rank;
		
		rankChange(transferSeq, targetOptionRank , targetTransferSeq, optionRank );
	}
	
	function rankChange(transferSeq, optionRank, destTransferSeq, destOptionRank){
		var _url = "/admin/transferWorker/rankChange";
		var _data = {
				transfer_seq : transferSeq
				, option_rank : optionRank
				, dest_transfer_seq : destTransferSeq
				, dest_option_rank : destOptionRank
		}
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			$("#list").trigger("reloadGrid");
    		}else {
    			alert(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	function unformatRank(cellvalue, options, rowObject){
		return $("div", rowObject).attr("value");
	}
	
	function formatRank(cellvalue, options, rowObject){
		var str="";
		str += "<div class='bt_wrap' value='" + cellvalue + "'>";
	   	str += "<div class=\"bt\"><a onclick=\"rankDown(\'" + rowObject.transfer_seq + "\', \'" + cellvalue + "\')\"> "+ "▼" +" </a></div>";
	   	str += "<div class=\"bt\"><a onclick=\"rankUp(\'" + rowObject.transfer_seq + "\', \'" + cellvalue + "\')\"> "+ "▲" +" </a></div>";
		str += "</div>";
		return str;
	}
	
	function formatLocation(cellvalue, options, rowObject){
		
		var str="";
		str += "<div class='bt_wrap'>";
	   	str += "<div class=\"bt" + (cellvalue > 0 ? "_t1" : "" ) + "\"><a onclick=\"openLocationForm(\'" + rowObject.transfer_seq + "\')\"> "+ "L" +" </a></div>";
		str += "</div>";
		return str;
	}
	
	function formatJobCode(cellvalue, options, rowObject) {
		var str = "";
		if( rowObject.job_kind == 0 ) {
			str += "<div class='bt_wrap'>";
		   	str += "<div class=\"bt_on\"><a onclick=\"JavaScript:openEditJobFrm('"+ rowObject.transfer_seq +"', '"+ rowObject.job_kind+"', '" + rowObject.job_code + "');\" > "+ "직종선택" +" </a></div>";
			str += "</div>";
			return str;
		}
	
		str += "<div class='bt_wrap'>";
	   	str += "<div class=\"bt_on\"><a onclick=\"JavaScript:openEditJobFrm('"+ rowObject.transfer_seq +"', '"+ rowObject.job_kind+"', '" + rowObject.job_code + "');\" > "+ rowObject.job_name +" </a></div>";
		str += "</div>";
		return str;
	}
	
	function openEditJobFrm(transferSeq, jobKind, jobCode){
		$("#popup-layer2 .jobList").removeClass("on");
		
		$("#popup-layer2 input[name='job_kind']").eq(jobKind).click();
		
		var _jobCodeArr = jobCode.split(",");
		_jobCodeArr.forEach(function(_jobCodeitem){
			$("#popup-layer2 .jobList[job_seq='" + _jobCodeitem + "']").addClass('on');
		});
		
		$("#popup-layer2 input[name='transfer_seq']").val(transferSeq);
		openIrPopup('popup-layer2');
	}
	
	function jobEdit(){
		var _url = "/admin/transferWorker/setTransferWorkerOption";
		var _data = $("#jobTable").find('input').serializeArray();
		
		var _transfer_seq = $("#popup-layer2 input[name='transfer_seq']").val();
		_data.push({name: "transfer_seq", value: _transfer_seq});
		
		var _jobKind = $("#popup-layer2 input[name='job_kind']:checked").val();
		if( _jobKind != "0" ){
			var _jobChoiceCnt = $(".jobList.on").length;
			if( _jobChoiceCnt == 0 ){
				alert("직종을 선택해 주세요.");
				return;
			}
		}
		
		var _jobCodeList = [];
		var _jobNameList = [];
		
		$("#popup-layer2 .jobList.on").each(function(){
			_jobCodeList.push($(this).attr("job_seq"));
			_jobNameList.push($(this).text());
		})
		_data.push({ name : "job_code", value : _jobCodeList.toString()});
		_data.push({ name : "job_name", value : _jobNameList.toString()});	
		
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			alert("수정 완료되었습니다.");
    			$("#list").trigger("reloadGrid");
    			closeIrPopup('popup-layer2');
    		}else {
    			alert(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
</script>
	<div class="title">
		<h3> 구직자이관 </h3>
		<div>
	    	<label class="switch">
				<input type="checkbox" id="onOff" ${transferWorkerOnOff eq '1' ? 'checked' : '' }>
				<span class="slider round"></span>
			</label>
			<p class="toggles" ${transferWorkerOnOff == 1 ? 'style="display:none;"' : '' }>OFF</p>
			<p class="toggles" ${transferWorkerOnOff == 1 ? '' : 'style="display:none;"' }>ON</p>
		</div>
	</div>
    <div class="btn-module mgtS mgbS">
      	<div class="leftGroup">
       		<a href="#none" id="btnAddForm" class="btnStyle01">추가</a>
        	<a href="#none" id="btnDel" class="btnStyle05">삭제</a>
      	</div>
    </div>
	<table id="list"></table>
	<div id="paging"></div>

<div id="opt_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b;"></div>
<!-- 팝업 : 스캔 첨부파일 등록 -->
<div id="popup-layer">
	<header class="header">
		<h1 class="tit">옵션 추가</h1>
	    <a href="#none" class="close" onclick="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
	</header>
	<form id="frmPopup" name="frmPopup" method="post" enctype="multipart/form-data">
		<section>
			<div class="cont-box">
		    	<article>
		      		<table id="optionTable" summary="첨부파일 등록 영역입니다.">
		      			<colgroup>
		      				<col width="120px">
		      				<col width="350px">
		      			</colgroup>
		      			<tbody>
		        			<tr>
		          				<th>지점명</th>
		          				<td>
		          					<div>
		            				<select id="companyList" style="width:100%;">
		            					<c:forEach items="${companyList }" var="companyDTO">
		            						<option value="${companyDTO.company_seq }">${companyDTO.company_name }</option>
		            					</c:forEach>
		            				</select>
		            				</div>
		          				</td>
		        			</tr>
		        			<tr>
		          				<th>성별</th>
		          				<td>
		            				<div>
		            					<label>
		            						<input type="radio" name="gender" value="N" checked="checked">
		            						<span>모두</span>
		            					</label>
		            					<label>
		            						<input type="radio" name="gender" value="M">
		            						<span>남성</span>
		            					</label>
		            					<label>
		            						<input type="radio" name="gender" value="F">
		            						<span>여성</span>
		            					</label>
		            				</div>
		          				</td>
		        			</tr>
		        			<tr>
		          				<th>기초안전보건교육증</th>
		          				<td>
		            				<div>
		            					<label>
		            						<input type="radio" name="osh_yn" value="2" checked="checked">
		            						<span>모두</span>
		            					</label>
		            					<label>
		            						<input type="radio" name="osh_yn" value="1">
		            						<span>이수</span>
		            					</label>
		            					<label>
		            						<input type="radio" name="osh_yn" value="0">
		            						<span>미이수</span>
		            					</label>
		            				</div>
		          				</td>
		        			</tr>
		        			<tr>
		        				<th>직종</th>
		          				<td>
		            				<div>
		            					<label>
		            						<input type="radio" name="job_kind" value="0" checked="checked">
		            						<span>모두</span>
		            					</label>
		            					<label>
		            						<input type="radio" name="job_kind" value="1">
		            						<span>일반인부</span>
		            					</label>
		            					<label>
		            						<input type="radio" name="job_kind" value="2">
		            						<span>기공인부</span>
		            					</label>
		            				</div>
		          				</td>
		        			</tr>
		        			<tr id="jobKind1" class="jobRow">
		        				<td colspan="2">
		        					<div class="jobListArea panel1">
			        					<div class="cal_title">일반인부</div>
			        					<c:forEach items="${jobList }" var="jobDTO">
			        						<c:if test="${jobDTO.job_kind eq 1 }">
			        							<div class="jobList buttonPanel" job_seq="${jobDTO.job_seq }">${jobDTO.job_name }</div>
			        						</c:if>
		            					</c:forEach>
		        					</div>
		        				</td>
		        			</tr>
		        			<tr id="jobKind2" class="jobRow">
		        				<td colspan="2">
		        					<div class="jobListArea panel1">
			        					<div class="cal_title">기공인부</div>
			        					<c:forEach items="${jobList }" var="jobDTO">
			        						<c:if test="${jobDTO.job_kind eq 2 }">
			        							<div class="jobList buttonPanel" job_seq="${jobDTO.job_seq }">${jobDTO.job_name }</div>
			        						</c:if>
		            					</c:forEach>
		        					</div>
		        				</td>
		        			</tr>
		      			</tbody>
		      		</table>
		    	</article>
		
		    	<div class="btn-module" style="width: 100%;text-align: center; margin-top: 20px;">
		      		<div><a href="#popup-layer" id="btnOptionAdd" class="btnStyle01">추가</a></div>
		    	</div>
		  	</div>
		</section>
	</form>
</div>
<div id="popup-layer2">
	<header class="header">
		<h1 class="tit">직종 수정</h1>
	    <a href="#none" class="close" onclick="javascript:closeIrPopup('popup-layer2');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
	</header>
	<form id="jobEditFrm" name="jobEditFrm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="transfer_seq" value="">
		<section>
			<div class="cont-box" style="overflow: hidden;">
		    	<article>
		      		<table id="jobTable" summary="첨부파일 등록 영역입니다.">
		      			<colgroup>
		      				<col width="120px">
		      				<col width="350px">
		      			</colgroup>
		      			<tbody>
		        			<tr>
		        				<th>직종</th>
		          				<td>
		            				<div>
		            					<label>
		            						<input type="radio" name="job_kind" value="0" checked="checked">
		            						<span>모두</span>
		            					</label>
		            					<label>
		            						<input type="radio" name="job_kind" value="1">
		            						<span>일반인부</span>
		            					</label>
		            					<label>
		            						<input type="radio" name="job_kind" value="2">
		            						<span>기공인부</span>
		            					</label>
		            				</div>
		          				</td>
		        			</tr>
		        			<tr id="editJobKind1" class="jobRow">
		        				<td colspan="2">
		        					<div class="jobListArea panel1">
			        					<div class="cal_title">일반인부</div>
			        					<c:forEach items="${jobList }" var="jobDTO">
			        						<c:if test="${jobDTO.job_kind eq 1 }">
			        							<div class="jobList buttonPanel" job_seq="${jobDTO.job_seq }">${jobDTO.job_name }</div>
			        						</c:if>
		            					</c:forEach>
		        					</div>
		        				</td>
		        			</tr>
		        			<tr id="editJobKind2" class="jobRow">
		        				<td colspan="2">
		        					<div class="jobListArea panel1">
			        					<div class="cal_title">기공인부</div>
			        					<c:forEach items="${jobList }" var="jobDTO">
			        						<c:if test="${jobDTO.job_kind eq 2 }">
			        							<div class="jobList buttonPanel" job_seq="${jobDTO.job_seq }">${jobDTO.job_name }</div>
			        						</c:if>
		            					</c:forEach>
		        					</div>
		        				</td>
		        			</tr>
		      			</tbody>
		      		</table>
		    	</article>
		    	<div class="btn-module" style="width: 100%;text-align: center; margin-top: 20px;">
		      		<div><a href="#popup-layer" id="btnJobEdit" class="btnStyle01">수정</a></div>
		    	</div>
		  	</div>
		</section>
	</form>
</div>
<div id="popup-layer3">
	<header class="header">
		<h1 class="tit">지역 선택</h1>
	    <a href="#none" class="close" onclick="javascript:closeIrPopup('popup-layer3');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
	</header>
	<input type="hidden" name="transfer_seq" value="">
	<section>
		<div class="cont-box" style="overflow-y: auto; max-height: 500px">
	    	<article>
	      		<table id="jobTable" summary="첨부파일 등록 영역입니다.">
	      			<colgroup>
	      				<col width="60px">
	      				<col width="120px">
	      				<col width="60px">
	      				<col width="120px">
	      				<col width="60px">
	      				<col width="120px">
	      				<col width="50px">
	      			</colgroup>
	      			<tbody>
	      				<tr>
	      					<td>시도</td>
	      					<td>
	      						<select id="sidoList" style="width: 90%">
									<option value="0" selected="selected">전체</option>
								</select>
	      					</td>
	      					<td>시군구</td>
	      					<td>
	      						<select id="sigunguList" style="width: 90%">
									<option value="0" selected="selected">전체</option>
								</select>
	      					</td>
	      					<td>읍면동</td>
	      					<td>
	      						<select id="eupmyeondongList" style="width: 90%">
										<option value="0" selected="selected">전체</option>
								</select>
							</td>
							<td>
								<div class="btn-module" style="width: 100%;text-align: center;">
	      							<div><a href="#popup-layer" id="btnLocationAdd" class="btnStyle04">추가</a></div>
	    						</div>
	    					</td>
	      				</tr>
	      			</tbody>
	      		</table>
	      		<table id="1" class="bd-list" style="table-layout: auto;" summary="지역">
	      			<colgroup>
	      				<col width="30%">
	      				<col width="30%">
	      				<col width="30%">
	      				<col width="10%">
	      			</colgroup>
	      			<thead>
	      				<tr>
	      					<th>시도</th>
	      					<th>시군구</th>
	      					<th>읍동면</th>
	      					<th></th>
	      				</tr>
	      			</thead>
	      			<tbody id="locationBody">
	      			</tbody>
	      			<tbody id="LocationDeleteBody" style="display:none;">
	      				
	      			</tbody>
	      		</table>
	    	</article>
	    	<div class="btn-module" style="width: 100%;text-align: center; margin-top: 20px;">
	      		<div><a href="#popup-layer" id="btnLocationSave" class="btnStyle01">저장</a></div>
	    	</div>
	  	</div>
	</section>
</div>
<script type="text/javascript">
</script>
