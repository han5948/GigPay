<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="/resources/cms/jstree/style.css" />
<script src="/resources/cms/jstree/jstree.min.js"></script>

	<div class="cont-box">
    	<article>
      		<table class="bd-form inputUI_simple" >
      			<colgroup>
        			<col width="150px" />
        			<col width="500px" />
      			</colgroup>
      			<tbody>
<%--
        <tr>
          <th>소속회사</th>
          <td colspan="3"><input type="text" class="inp-field wid650" id="company_name" name="company_name" value="<c:out value="${sessionScope.ADMIN_SESSION.company_name}" />" /></td>
        </tr>
--%>
					<tr>
						<th>관계 Tree</th>
						<td>
							<h1 style="margin-top:10px">TREE</h1>
							<div id="ewTree" class="demo" style="margin-top:10px;margin-bottom:20px"></div>
 						</td>
 					</tr>
 					<tr>
						<th>구인처추가</th>
          				<td>
          					<input type="text" class="inp-field wid300" id="employer_name" name="employer_name" value="" />
          					<div class="btn-module mglS">
      							<div class="rightGroup"><a class="btnStyle05" href="javascript:addEmployer();">추가</a></div>
    						</div>
          				</td>
 					</tr>
					<tr>
						<th>현장추가</th>
          				<td>
          					<input type="text" class="inp-field wid300" id="work_name" name="work_name" value="" />
          					<div class="btn-module mglS">
      							<div class="rightGroup"><a class="btnStyle05" href="javascript:addWork();">추가</a></div>
    						</div>
    					</td>
 					</tr>
 					<tr>
 						<td colspan="2">
 							<input type ="hidden" id="employer_seq" name="employer_seq" value="0"/>
 							<input type ="hidden" id="work_seq" name="work_seq" value="0" />
 				
 			  				<div class="btn-module mglS">
      							<a class="btnStyle05" href="javascript:delEmployer();">구인처삭제</a>
      							<a class="btnStyle05" href="javascript:delWork();">현장삭제</a>
    						</div>
 						</td>
 					</tr>
      			</tbody>
      		</table>
    	</article>
  	</div>

<script>
	var managerSeq = ${emDTO.manager_seq};
  	var selectEmSeq = 0;
  	var selectWmSeq = 0;
  	var childrenLength = 0;
  	var selectEmployerSeq = 0;
  	var jsonData = ${jsonData };
  
  	$(function(){
  		$("#employer_name").autocomplete({
			source: function (request, response) {
				$.ajax({
					url: "/admin/getEmployerNameList", type: "POST", dataType: "json",
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
	        open: function(){
				setTimeout(function () {
					$('.ui-autocomplete').css('z-index', 99999999999999);
	            }, 0);
	        },
	      	select: function (event, ui) {
				$("#employer_name").val(ui.item.label);
				$("#employer_seq").val(ui.item.code);
	                
				return false;
			}
		});
	  
	  	$("#work_name").autocomplete({
			source: function (request, response) {
				$.ajax({
					url: "/admin/getWorkNameList3", type: "POST", dataType: "json",
					data: { 
						term: request.term,
						employer_seq : selectEmployerSeq,
						srh_use_yn: 1 
					},
					success: function (data) {
						response($.map(data, function (item) {
							return item;
						}))
					},
					beforeSend: function(xhr) {
						if(selectEmSeq ==0){
	               			alert("구인처를 먼저 선택 하세요.");
	               			$("#work_name").val("");
	               			return false;
						}
						
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
	        open: function(){
	            setTimeout(function () {
	                $('.ui-autocomplete').css('z-index', 99999999999999);
	            }, 0);
	        },
	      	select: function (event, ui) {
				$("#work_name").val(ui.item.label);
	            $("#work_seq").val(ui.item.code);
	            $("#employer_seq").val(selectEmployerSeq);
	            return false;
			}
	 	});
	
	  	//$('#html').jstree();
// 		$('#html').jstree({"core" : {
// 			'data' : {
// 				"url" : "/admin/getManagerEWTree?manager_seq="+ managerSeq,
// 				"dataType" : "json" // needed only if you do not supply JSON headers
// 			}}
// 		});
		// inline data demo
		$("#ewTree").jstree({
			"core" : {
				'data' : jsonData
			}	
		});
		
		$('#ewTree').on("changed.jstree", function (e, data) {
			if(data.selected.length) {
				if(data.instance.get_node(data.selected[0]).parent ==="#"){
					selectEmSeq = data.instance.get_node(data.selected[0]).id;
					childrenLength = data.instance.get_node(data.selected[0]).children.length;
					selectEmployerSeq = data.instance.get_node(data.selected[0]).original.employer_seq;
					selectWmSeq = 0;
				}else{
					selectEmSeq =0;
					childrenLength = 0;
					selectEmployerSeq =0;
					selectWmSeq = data.instance.get_node(data.selected[0]).id;
				}
			}else{
				selectEmSeq =0;
				selectEmployerSeq =0;
				childrenLength = 0;
				selectWmSeq = 0;
			}
		}).jstree({
			"core" : {
				'data' : {
					"url" : "/admin/getManagerEWTree?manager_seq="+ managerSeq,
					"dataType" : "json" // needed only if you do not supply JSON headers
				}
			}
		});
  	});
    
	function addEmployer(){
		var employerSeq = $("#employer_seq").val();
		if(employerSeq ==0 ){
			alert("구인처를 검색 하여 선택 하세요.");
			return;
		}
		 
		$.ajax({
			type: "POST",
		   	url: "/admin/insertEm",
		    data: { 
		    	employer_seq: employerSeq
		  	   , manager_seq: managerSeq
		  	},
		    dataType: 'json',
		  	success: function(data) {
		        if(data.code == "0000"){
		        	$("#employer_name").val("");
		            $("#employer_seq").val("0");

		            var treeData = JSON.parse(data.jsonData);
		            
		            $("#ewTree").jstree(true).settings.core.data = treeData;
		            $("#ewTree").jstree(true).refresh();
		        }else{
		        	alert(data.message);
		        }
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
	
	function addWork(){
		var employerSeq = $("#employer_seq").val();
		if(employerSeq ==0 ){
			alert("구인처가 선택 되지 않았습니다.");
			return;
		}
		
		var workSeq = $("#work_seq").val();
		if(workSeq ==0 ){
			alert("현장을 검색 하여 선택 하세요.");
			return;
		}
		 
		$.ajax({
			type: "POST",
		   	url: "/admin/insertWm",
		    data: {
				employer_seq : employerSeq
		    	, work_seq: workSeq
				, manager_seq: managerSeq
		  	},
		    dataType: 'json',
		  	success: function(data) {
		        if(data.code == "0000"){
		        	$("#work_name").val("");
		            $("#work_seq").val("0");
		            
		            var treeData = JSON.parse(data.jsonData);
		            
		        	$("#ewTree").jstree(true).settings.core.data = treeData;
		            $("#ewTree").jstree(true).refresh();
		        }else{
		        	alert(data.message);
		        }
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
	
	function delEmployer(){
		if(selectEmSeq > 0 ){
			if(childrenLength > 0){
				alert("현장이 존재하는 구인처는 삭제 하실 수 없습니다.");
				return;
			}
			
			if(confirm("선택한 구인처를 삭제 하시겠습니까?")){
				$.ajax({
					type: "POST",
				   	url: "/admin/deleteEm",
				    data: { 
				    	em_seq: selectEmSeq
				    	, manager_seq: managerSeq
				  	},
				    dataType: 'json',
				  	success: function(data) {
				        if(data.code == "0000"){
				        	selectEmSeq =0;
				        	selectEmployerSeq =0;
				        	childrenLength =0;
				        	
				        	var treeData = JSON.parse(data.jsonData);
				            
				        	$("#ewTree").jstree(true).settings.core.data = treeData;
				            $("#ewTree").jstree(true).refresh();
				        }else{
				        	alert(data.message);
				        }
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
		}else{
			alert("삭제할 구인처를 선택 하세요.");
		}
	}
	
	function delWork(){
		if(selectWmSeq > 0 ){
			if( confirm("선택한 현장을 삭제 하시겠습니까?") ){
				$.ajax({
					type: "POST",
				   	url: "/admin/deleteWm",
				    data: { 
				    	wm_seq: selectWmSeq
				    	, manager_seq: managerSeq
				  	},
				    dataType: 'json',
				  	success: function(data) {
				        if(data.code == "0000"){
				        	selectWmSeq =0;
				        	
				        	var treeData = JSON.parse(data.jsonData);
				        	
				        	$("#ewTree").jstree(true).settings.core.data = treeData;
				            $("#ewTree").jstree(true).refresh();
				        }else{
				        	alert(data.message);
				        }
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
		}else{
			alert("삭제할 현장을 선택 하세요.")
		}
	}
</script>
