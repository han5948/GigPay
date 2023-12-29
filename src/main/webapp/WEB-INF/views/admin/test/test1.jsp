<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="/resources/cms/jqueryUi/jquery-ui.css" rel="stylesheet">
<link href="/resources/cms/grid/css/ui.jqgrid.css" rel="stylesheet">
<script type="text/javascript" src="/resources/cms/grid/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/resources/cms/jqueryUi/jquery-ui.js"></script>

<script type="text/javascript" src="/resources/cms/grid/js/jquery.jqgrid.min.js"></script>
<script type="text/javascript" src="/resources/cms/grid/js/i18n/grid.locale-kr.js"></script>
		    	
	
<script type="text/javascript">
$(function(){
	
	 $("#m1").button();   

	 
	var listOptions = "C:Canada; U:United States; F:France; I:India";
	
	var lastsel;						//편집 sel

	
    //jqGrid껍데기 생성
    $("#list").jqGrid({
                url : "/test/getTestList",
               // editurl: "/test/updateTest",										// 추가, 수정, 삭제 url
    			datatype: "json",														//로컬그리드이용
    			mtype: "POST",
    			//datatype: "local",
                
                sortname: 'seq',
                sortorder: "desc",
            	rowList:[10,20,30],		//한페이지에 몇개씩 보여 줄건지?
            	height: "100%",															//그리드 높이
            	width: "auto",
            	
            	rowNum:15,
            	pager: '#paging',			// 네비게이션 도구를 보여줄 div요소
            	viewrecords: true,	  // 전체 레코드수, 현재레코드 등을 보여줄지 유무

            	
            	multiselect: true,
            	multiboxonly: true,
            	caption: "그리드 목록",	 //그리드타이틀
                
                rownumbers: true,		//그리드 번호 표시
            	rownumWidth: 40,		//번호 표시 너비
            	
            	cellEdit  :  true , 
            	cellsubmit  : "remote" , 
            	cellurl:  "/test/updateCellTest",										// 추가, 수정, 삭제 url
            	
                jsonReader: {
                	 id: 'seq',
                    repeatitems:false
               },


          colNames:['시퀀스','전체','이름','value','제목','구분1','구분2','구분3', '타입' ,'내용','등록일'],		//컬럼명들
        //컬럼모델
        colModel:[
            {name:'seq', index:'seq',   key:true, resizable:true ,align:"center",  searchoptions:{sopt:['eq','ne','le','lt','gt','ge']}},
            {name:'s_type' ,editable:true,align:"center", width:50, search:false,
            	editoptions:{
            		value:'1:0', 
            	
            	},
            	formatter: "checkbox",
            	edittype:"checkbox",  formatoptions:{disabled:false}},
            {name:'name', index:'name',  width:300, editable:true, searchoptions:{sopt:['cn','eq']},
            	editoptions: {
            	      size: 30,
            	      dataInit: function (e) {
            	    	  
            	    	  s_type = $("#list").jqGrid('getRowData', lastsel).s_type;
            	    	 
            	    	 $(e).select();			//INPUT TEXT 클릭 시 텍스트 전체 선택
            	    	 
            	        $(e).autocomplete({
            	        	//	source:[{name: "홍길동",flag: "0",seq: "1"},{name: "홍홍홍홍",flag: "0",seq: "2"},{name: "홍준표",flag: "0",seq: "3"},{name: "김유신",flag: "0",seq: "4"},{name: "김태희",flag: "0",seq: "5"},{name: "김기리",flag: "0",seq: "6"},{name: "박근혜",flag: "0",seq: "7"},{name: "박원순",flag: "0",seq: "8"},{name: "박혁거세",flag: "0",seq: "9"},{name: "aaa",flag: "0",seq: "10"},{name: "aaa",flag: "0",seq: "11"}],
            	        	//	source:[{"seq":30,label:"aaaa"}, {"seq":31,label:"abcd"}, {"seq":111,label:"aasdf"}],
            	        	//	source:["Product #1", "Product #2", "Product #3", "Product #4"],
            	        	 
            	        	source:"/test/getSearchName?s_type="+s_type,
            	        	
            	        	 
            	        	 minLength: 1,
            	             focus: function (event, ui) {
            	              	// $(e).val(ui.item.label);
            	               return false;
            	             },
            	             select: function (event, ui) {
            	                 
            	                 $(e).val(ui.item.label);
            	                   	             
            	                $("#list").jqGrid('setCell',lastsel,'name_seq',ui.item.seq,{color:'red'});	//다른 셀 바꾸기
            	     
            	                 return false;
            	               }
            	        });
            	      }
            	    },
            	    editrules: {
            	        edithidden: true,
            	        required: false
            	      },
            	    edittype: "text",
            	   // hidden: true,
            
            },
            
            
            {name:'name_seq', search:false,hidden:false},
            {name:'title',editable:true, sortable:false ,searchoptions:{sopt:['eq','cn']} 
            	,editoptions: {
	      		  size: 30,
	      	      dataInit: function (e) {
	      	    	// $(e).select();			//INPUT TEXT 클릭 시 텍스트 전체 선택
	      	      }
	      	    }},
            {name:'flag1' ,editable:true,align:"center" ,searchoptions:{sopt:['eq','cn']}, edittype:"select", formatter: 'select', editoptions:{value:"1:YES;0:NO"}},
            {name:'flag2' ,editable:true,align:"center" ,searchoptions:{sopt:['eq','cn']}, edittype:"select", formatter: 'select', editoptions:{value:"1:사용;0:미사용"}},
            {name:'flag3' ,editable:true,align:"center" ,searchoptions:{sopt:['eq','cn']}, formatter:'checkbox', editoptions:{value:'1:0'},edittype:"checkbox",  formatoptions:{disabled:false}},
            {name:'man_type' , editable:true,index: 'man_type',align:"center" ,searchoptions:{sopt:['eq','cn']},  edittype:"select", formatter: 'select',editoptions:{value:"a:android;i:아이폰"}},
            {name:'comment',width:300, editable: true,edittype:"textarea", editoptions:{rows:"1",cols:"20"}, search:false, cellattr: function(rowId, tv, rowObject, cm, rdata) {
                // rowObject 변수로 그리드 데이터에 접근
                // ProjectCode값이 Momot라면 현재 Column부터 3칸을 셀병합하고 글자 정렬 가운데로 설정
                if (rowObject.seq == '10' ) { 
                	//return 'colspan=3 , style="text-align:center;"'
                	return 'style="color:red; text-align:center;"'
                	}
                }
            },
            {name:'reg_date', editable:false,index:'reg_date', width:200,align:"cneter", search:false}    
        ],
        
        
        //row 를 선택 했을때 편집 할 수 있도록 한다.
        onSelectRow: function(id){
        	
    		if(id && id!==lastsel){
    			jQuery('#list').jqGrid('restoreRow',lastsel);
    			//jQuery('#list').jqGrid('editRow',id,true);
    		
    			
    			  jQuery("#list").jqGrid('editRow',id, 
    					 { 
    					     keys : true, 
    					     oneditfunc: function() {
    					       //  alert ("edited"); 
    					     },
    					     successfunc:function() {
    					        lastsel = -1;
    					        return true; 

    					     },
    					 }); 
    			 
    			lastsel=id;
    		}
    	},
    	
    	
    	onCellSelect: function(rowid, iCol, contents,e){ // cell을 클릭 시
                
    			var row = $("#list").jqGrid('getRowData', rowid);	//row data 전체 가져 오기
                var a = 	row.name;

       			alert(e.val());
       			lastsel = rowid;


	   },
    	 beforeSubmitCell : function(rowid, cellname, value) {   // submit 전
    		 	 if(cellname =="name"){	
    	          return {"seq":rowid, "cellName":cellname, "name_seq":$("#list").jqGrid('getRowData', rowid).name_seq};
    		 	 }else{
    		 		return {"seq":rowid, cellName:cellname};
    		 		//return {"seq":rowid, "cellName":cellname, "value":value};
    		 	 }
 		},


    	
    	
	/* 	
    	beforeSelectRow: function (rowid, e) {
    	    var $myGrid = $(this),
    	        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
    	        cm = $myGrid.jqGrid('getGridParam', 'colModel');
    	    return (cm[i].name === 'cb');
    	}

       	 */
      });
    
	
    $("#list").jqGrid('navGrid',"#paging",{ edit:false,add:true,del:false,search:false, refresh: false,position:'left'}); 
    
    $("#list").jqGrid('filterToolbar',{searchOperators : true});
    
    
    
    $("#m1").click( function() {
    	var s = $("#list").jqGrid('getGridParam','selarrrow');
    	alert(s);
    });
    
    $("#btnAdd").click( function() {
    	//jQuery("#list").addRowData("26",{},"first");
    	//jQuery("#list").trigger("reloadGrid");
    	var params ="";
    	 $.ajax({      
    	        type:"POST",  
    	        url:"/test/newInsertTest",      
    	        data:params,  
    	        dataType: 'json',  
    	        success:function(data){
    	        	
    	        	jQuery("#list").trigger("reloadGrid");
    	        	//jQuery("#list").addRowData(seq,{},"first");
    	                  
    	        },   
    	      //  beforeSend:showRequest,  
    	        error:function(e){  
    	            alert(e.responseText);  
    	        }  
    	    });  

    });
    
    $("#btnDell").click( function() {
    	
    	var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
    	var params ="seqs=" + recs;
    	
    	var rows = recs.length;
    	
    	
    	
   	 	$.ajax({      
   	        type:"POST",  
   	        url:"/test/deleteTest",      
   	        data:params,  
   	        dataType: 'json',  
   	        success:function(data){
   	        	
   	        	$("#list").trigger("reloadGrid");
   	        	/* 
   	        	for (var i = rows - 1; i >= 0; i--) {
   	     			$('#list').jqGrid('delRowData', recs[i]);
   	     		}
 				*/
   	                  
   	        },   
   	      //  beforeSend:showRequest,  
   	        error:function(e){  
   	            alert(e.responseText);  
   	        }  
   	    });  

   	 
    });
    
    
    
   
    

})



function autocomplete_element(value, options) {
  		// create input element
		var $ac = $('<input type="text" />');
		// setting value to the one passed from jqGrid
		$ac.val(value);
		// creating autocomplete
		$ac.autocomplete(
			{
			//	source: [{"value":30,"label":"Washington, DC, USA"},{"value":31,"label":"Windsor, Ontario, Canada"}, {"value":111,"label":"Wylie, Texas, USA"}]
			 source:["Product #1", "Product #2", "Product #3", "Product #4"]
				
			}
		);
		
		// returning element back to jqGrid
		return $ac.get(0); 

	}
 
	function autocomplete_value(elem, op, v) {
		
		
		if (op == 'set') {
			$(elem).val(v);
		}
		
	  return $(elem).val();
	}

</script>



	<br><br><br>
	
	<body class="bg">
	<input type="text" class="">
	<button id="button" class="ui-button  ui-corner-all  ui-widget">element</button>
	
	<label for="search-mini">Search Input:</label>
	<input type="search" name="search-mini" id="search-mini" value="" data-mini="true">
		<button id="m1">체크값 확인</button> <button id="btnAdd">행추가</button> <button id="btnDell">체크삭제</button>		
		<p> </p>		    
	
		<table id="list"></table>
		<div id="paging"></div>




	</body>
	
	
	
   
   