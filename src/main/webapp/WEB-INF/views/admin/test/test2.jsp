<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


  <style type="text/css">
  .bt_wrap {width:100%;}
  .bt_wrap:after {content:""; display:block; clear:both; *zoom:1}
  .bt {float:left; margin-right:3px;}
  .bt a{
   display:block;
   width:33px; height:20px;
   border:1px solid #d7d7d7;
   text-align:center;
   padding-top:4px;
   text-decoration: none;   
   background-repeat:repeat-x;
   background:#fcfcfc;
   color:#9b9b9b;
   font-size:11px;
   font-family:"돋움,dotum,굴림,gulim,Vertical,Arial";
  }
 .bt1  a{
   float:left; margin-right:3px;
   display:block;
   width:33px; height:20px;
   border:1px solid #d7d7d7;
   text-align:center;
   padding-top:4px;
   text-decoration: none;   
   background-repeat:repeat-x;
   background:#5aa5da;
   color:#9b9b9b;
   font-size:11px;
   font-family:"돋움,dotum,굴림,gulim,Vertical,Arial";
  }
 
  .bt a:hover{
   display:block;
   width:33px; height:20px;
   border:1px solid #999999;
   text-align:center;
   padding-top:4px;
   text-decoration: none;
   background:#e5e5e7;
   color:#565656;
   font-size:11px;
      
  }

  </style>
  
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
 
  <link rel="stylesheet" href="/resources/cms/jqueryUi/jquery-ui.css" >
  <link rel="stylesheet" href="/resources/cms/grid/css/font-awesome.min.css" >
  <link rel="stylesheet" href="/resources/cms/grid/css/ui.jqgrid.min.css">
  <link rel="stylesheet" href="/resources/cms/select2/css/select2.min.css">
  
  <script type="text/javascript" src="/resources/cms/grid/js/jquery-1.11.0.min.js"></script>
  <script type="text/javascript" src="/resources/cms/jqueryUi/jquery-ui.min.js"></script>
  <script type="text/javascript" src="/resources/cms/select2/js/select2.full.min.js"></script>
  <script type="text/javascript" src="/resources/cms/grid/js/jquery.jqgrid.min.js"></script>
    
  
  <style>
    .ui-datepicker { font-size: 76.39%; }
  </style>
  
  
  
  
    
<script type="text/javascript">
$.datepicker.setDefaults(
    {
      changeMonth: true, 
      dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
           dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], 
           monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
           monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
           changeYear: true,
           nextText: '다음달',
           prevText: '이전달' ,
       showButtonPanel: true, 
           currentText: '오늘날짜', 
           closeText: '닫기', 
           dateFormat: "yy-mm-dd",
     }
  ); 
  
var now = new Date();
var year= now.getFullYear();
var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
        
var toDay = year + '-' + mon + '-' + day;




  
$(function(){

  $("#datepicker1, #datepicker2").datepicker();
  $("#datepicker1, #datepicker2").val(toDay);
  
  var lastsel2;           //편집 sel

    //jqGrid껍데기 생성
    $("#list").jqGrid({
                url : "/test/getTestList",
                editurl: "/test/updateTest",                    // 추가, 수정, 삭제 url
          datatype: "json",                           //로컬그리드이용
          mtype: "POST",
          postData : {
            reg_date:toDay,
            page:1
          },
          //datatype: "local",
                
                sortname: 'seq',
                sortorder: "desc",
              rowList:[10,20,30],   //한페이지에 몇개씩 보여 줄건지?
              height: "100%",                             //그리드 높이
              width: "auto",
              // autowidth:true,
              
              rowNum:15,
              pager: '#paging',     // 네비게이션 도구를 보여줄 div요소
              viewrecords: true,    // 전체 레코드수, 현재레코드 등을 보여줄지 유무

              
              multiselect: true,
              multiboxonly: true,
              caption: "그리드 목록aa",   //그리드타이틀
                
                rownumbers: true,   //그리드 번호 표시
              rownumWidth: 40,    //번호 표시 너비
              
              //forceFit: true,
                emptyrecords: "No record was loaded",
            //    cellEdit: true, cellsubmit:"remote",
                jsonReader: {
                   id: 'seq',
                    repeatitems:false
               },


          colNames:['시퀀스','이름','이름번호','제목','구분1','구분2','구분2','구분2','구분2','구분2','구분2','구분2','구분2','구분2','구분2','구분3','구분4', '타입' ,'내용','등록일'],    //컬럼명들
        //컬럼모델
        colModel:[
            {name:'seq', index:'seq',   key:true, resizable:true ,align:"center",  searchoptions:{sopt:['eq','ne','le','lt','gt','ge']}},
            {name:'name', index:'name',   align:"center", searchoptions:{sopt:['eq','cn']},
              editable:true, 
              edittype: "select", 
              cellattr: function(rowId, tv, rowObject, cm, rdata) {
                    // rowObject 변수로 그리드 데이터에 접근
                    // ProjectCode값이 Momot라면 현재 Column부터 3칸을 셀병합하고 글자 정렬 가운데로 설정
                    if (rowObject.name_seq == '0' ) { 
                      //return 'colspan=3 , style="text-align:center;"'
                      return 'style="color:red"';
                    }
                } ,
              editoptions: {
          dataUrl: "/test/getNameList?flag=0",
          defaultValue: "0",
          dataEvents:[{
                        type:'change',
                        fn:function(e) {                // 값 : this.value || e.target.val()
                          
                            $('#list').jqGrid('saveRow',lastsel2);
          
                            if (this.value == '0' ) { 
                              //return 'colspan=3 , style="text-align:center;"'
                              jQuery("#list").jqGrid('setCell',lastsel2,'name','',{color:'red'}); //셀 바꾸기
                            }else{
                              jQuery("#list").jqGrid('setCell',lastsel2,'name','',{color:'#000000'}); //셀 바꾸기
                            }
                                                        
                            jQuery("#list").jqGrid('setCell',lastsel2,'name_seq',this.value,{color:'red'}); //다른 셀 바꾸기
                            
                  lastsel2 = -1;
                  
                  
                        },
                    }],
          
          selectFilled: function (options) {
              $(options.elem).select2({
                dropdownCssClass: "ui-widget ui-jqdialog",
                width: "100%"
              });
          }
        }
            ,
        stype: "select", 
        searchoptions: {
          sopt: [ "eq","cn"],
          dataUrl: "/test/getSearchNameList",
          noFilterText: "전체",
          selectFilled: function (options) {
            $(options.elem).select2({
              dropdownCssClass: "ui-widget ui-jqdialog",
              width: "100%"
            });
          }
        } 
              
            },
            {name:'name_seq', index:'name_seq',   resizable:true , align:"center", 
              search:false, 
              //hidden:true
            },
            
            {name:'title',editable:true, sortable:false ,width:300, searchoptions:{sopt:['eq','cn']},edittype:"text",
              editoptions: {
          dataEvents:[{
                        type:'change',
                        fn:function(e) {                // 값 : this.value || e.target.val()
                          
                      lastsel2 = -1;
                  
                  
                        },
                    }],
          
        
        } 
            
            },
            {name:'flag1' ,editable:true,align:"center" ,searchoptions:{sopt:['eq','cn']}, edittype:"select", formatter: 'select', 
              editoptions:{
                value:"1:YES;0:NO",
                dataEvents:[{
                        type:'change',
                        fn:function(e) {                // 값 : this.value || e.target.val()
                            $('#list').jqGrid('saveRow',lastsel2);
                            lastsel2 = -1;
                        },
                    }]  
              },
             
            },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt1     },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt2     },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt2     },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt2     },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt2     },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt2     },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt2     },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt2     },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt2     },
            {name:'flag2' , align:"center" , fixed:true, edittype:"select", editoptions:{value:'1:0'},formatter:formatOpt1     },
            
            {name:'flag3' ,editable:true,align:"center" ,searchoptions:{sopt:['eq','cn']}, formatter:'checkbox', 
              editoptions:{value:'1:0'},
              edittype:"checkbox",  
              formatoptions:{disabled:false},
              cellattr: function(rowId, tv, rowObject, cm, rdata) {
                    // rowObject 변수로 그리드 데이터에 접근
                    // ProjectCode값이 Momot라면 현재 Column부터 3칸을 셀병합하고 글자 정렬 가운데로 설정
                    if (rowObject.flag3 == '0' ) { 
                      //return 'colspan=3 , style="text-align:center;"'
                      return 'style="text-align:center;background-color:#ffddff"'
                      }
                    } 
            
            },
            
            {name:'flag2' , align:"center" , fixed:true, formatter:formatOpt2     },
            
            {name:'man_type' , editable:true,index: 'man_type',align:"center" ,searchoptions:{sopt:['eq','cn']},  edittype:"select", formatter: 'select',editoptions:{value:"a:android;i:아이폰"}},
            
            {name:'comment',width:200, editable: true,edittype:"textarea", editoptions:{rows:"1",cols:"20"}, search:false, 
              cellattr: function(rowId, tv, rowObject, cm, rdata) {
                // rowObject 변수로 그리드 데이터에 접근
                // ProjectCode값이 Momot라면 현재 Column부터 3칸을 셀병합하고 글자 정렬 가운데로 설정
                if (rowObject.seq == '44' ) { 
                  //return 'colspan=3 , style="text-align:center;"'
                  return 'style="color:red; text-align:center;background-color: #FFFFF0"'
                  }
                }
            },
            {name:'reg_date', index:'reg_date',  align:"center", fixed:true, search:false, 
              formatter: "date",
                formatoptions: { newformat: " Y-m-d" }   }    
              
        ],
        
        //처음 데이터가 들어 갈때...
        afterInsertRow: function(rowid, aData){
          
          //alert(aData.name);
          switch (aData.name) {
            case '홍길동':
              jQuery("#list").jqGrid('setCell',rowid,'name','',{color:'green'});
            break;
            case 'Client 2':
              jQuery("#list").jqGrid('setCell',rowid,'name','',{color:'red'});
            break;
            case 'Client 3':
              jQuery("#list").jqGrid('setCell',rowid,'name','',{color:'blue'});
            break;
            
          }
        },      
        
        //row 를 선택 했을때 편집 할 수 있도록 한다.
        onSelectRow: function(id){
	        if(id && id!==lastsel2){
	          
	           $('#list').jqGrid('restoreRow',lastsel2);
	          //jQuery('#list').jqGrid('editRow',id,true);
	        
	          
	            $("#list").jqGrid('editRow',id, 
	               { 
	                   keys : true, 
	                   oneditfunc: function() {
	                     //  alert ("edited"); 
	                   },
	                   successfunc:function() {
	                      lastsel2 = -1;
	                      return true; 
	                   },
	               }); 
	            
	          lastsel2=id;
	        }
      },
      
      
        
      
    /*
      beforeSelectRow: function (rowid, e) {
          var $myGrid = $(this),
              i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
              cm = $myGrid.jqGrid('getGridParam', 'colModel');
          
          return (cm[i].name == 'cb');
      },
        
       */   
        /*  afterSaveCell: function () {
              alert("aftersaveCell");
          },
         beforeEditCell: function (id, name, val, iRow, iCol) {
          alert("beforeEditCell");
          },
           beforeSubmitCell: function (rowid, celname, value, iRow, iCol) {
               alert("beforeSubmitCell");
          },
          
      afterEditCell:function(rowid, cellname, value, iRow, iCol){
         alert(11);
        $("#"+rowid+"_"+cellname).blur(function(){
              $("#list").jqGrid("saveCell",iRow,iCol);
          });
      },
 */

      
      
  /*  
      beforeSelectRow: function (rowid, e) {
          var $myGrid = $(this),
              i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
              cm = $myGrid.jqGrid('getGridParam', 'colModel');
          return (cm[i].name === 'cb');
      }

         */
         
         
         
      }); // End  $("#list").jqGrid
    
  
    $("#list").jqGrid('navGrid',"#paging",{ edit:false,add:true,del:false,search:false, refresh: false,position:'left'}); 
    
    $("#list").jqGrid('filterToolbar',{searchOperators : true});
    
   
    
    $("#btnCheckConfirm").click( function() {
      var s = $("#list").jqGrid('getGridParam','selarrrow');
      alert(s);
    });
    
    
    //행추가
    $("#btnAdd").click( function() {
      //jQuery("#list").addRowData("26",{},"first");
      //jQuery("#list").trigger("reloadGrid");
      var params ="";
       $.ajax({      
              type:"POST",  
              url:"/test/newInsertTest",      
              data:"reg_date="+$("#datepicker1").val(),  
              dataType: 'json',  
              success:function(data){
                
                $("#list").trigger("reloadGrid");
                //jQuery("#list").addRowData(seq,{},"first");
                        
              },   
            //  beforeSend:showRequest,  
              error:function(e){  
                  alert(e.responseText);  
              }  
          });  

    });
    
    
    
    //체크 삭제
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

    
    
    $(window).resize(function() {
      $("#list").setGridWidth($(this).width() -20);
    });
    
   
  //새로고침
    $("#btnReload").click( function() {
      
    
              $("#list").trigger("reloadGrid"); //그리드 다시그리기
      
    });

  //체크박스 해제
  $("#btnCheckReset").click( function() {
    $("#list").jqGrid('resetSelection');
  });
  
  
  //날짜 검색
  $("#btnDate").click( function() {
     $("#list").jqGrid('setGridParam',{
      postData : {
        reg_date:$("#datepicker1").val(),
        page:1
      }
    });
    $("#list").trigger("reloadGrid"); 
  });
  
  

   //체크 복사
    $("#btnPaste").click( function() {
      
      var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
      if(recs ==""){
        alert("체크된 항목이 없습니다.");
        return;
      }
      var regdate = $("#datepicker2").val();
      if(recs ==""){
        alert("선택된 날짜가 없습니다.");
        return;
      }
      
      var params ="seqs=" + recs +"&reg_date="+regdate;
      
      var rows = recs.length;
      
      
      
      $.ajax({      
            type:"POST",  
            url:"/test/pasteInsertTest",      
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
    });// $("#btnPaste").click(
   
   
   
      
/*       //검색 버튼클릭
      $("#btnDate").click( function() {
        alert(1);
        /* $("#list").jqGrid('setGridParam',{
          postData : {
            where:"reg_date="+$("#datepicker1").val(),
            //param2:$("#param2").val()
          }
        });
        $("#tbYourGrid").trigger("reloadGrid"); 
      }); */

      

      
      

  
  
  function formatOpt1(cellvalue, options, rowObject){ 
      var str = "";
      var val1 = rowObject[0];
      var val2 = rowObject[1];
         
           
       str += "<div class=\"bt_wrap\">";
       
       if(cellvalue == 0){
        str +="<div class=\"bt1\"><a href=JavaScript:clickButton('"+rowObject.seq+"','"+0+"');>A</a></div>";
        str +="<div class=\"bt\"><a href=JavaScript:clickButton('"+rowObject.seq+"','"+1+"');>B</a></div>";
        str +="<div class=\"bt\"><a href=JavaScript:clickButton('"+rowObject.seq+"','"+2+"');>C</a></div>";
       }else if(cellvalue == 1){
         str +="<div class=\"bt\"><a href=JavaScript:clickButton('"+rowObject.seq+"','"+0+"');>A</a></div>";
          str +="<div class=\"bt1\"><a href=JavaScript:clickButton('"+rowObject.seq+"','"+1+"');>B</a></div>";
          str +="<div class=\"bt\"><a href=JavaScript:clickButton('"+rowObject.seq+"','"+2+"');>C</a></div>";
       }else{
         str +="<div class=\"bt\"><a href=JavaScript:clickButton('"+rowObject.seq+"','"+0+"');>A</a></div>";
          str +="<div class=\"bt\"><a href=JavaScript:clickButton('"+rowObject.seq+"','"+1+"');>B</a></div>";
          str +="<div class=\"bt1\"><a href=JavaScript:clickButton('"+rowObject.seq+"','"+2+"');>C</a></div>";
       }
       str +="</div>";    
       //<a href=JavaScript:clickButton('"+rowObject.seq+"','"+1+"');>
        return str;
    }
  
  function formatOpt2(cellvalue, options, rowObject){ 
      var str = "";
      var val1 = rowObject[0];
      var val2 = rowObject[1];
          
      //<a href=JavaScript:clickButton('"+rowObject.seq+"','"+1+"');>
       
           
       str += "<div class=\"bt_wrap\">";
       str +="<div class=\"bt1\"  onclick='itemClick(event);return false;'>AAAA</div>";
       str +="</div>";
       
       return str;
       
    }

});//end $

function abspos(e){
    this.x = e.clientX + (document.documentElement.scrollLeft?document.documentElement.scrollLeft:document.body.scrollLeft);
    this.y = e.clientY + (document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop);
    
  
    return this;
}


function itemClick(e){
	
	
	
    var ex_obj = document.getElementById('lay');
    if(!e) e = window.Event;
    
   
    
    pos = abspos(e);
    
    
    ex_obj.style.left = (pos.x - 100)+"px";
    ex_obj.style.top = (pos.y-20)+"px";
    
    
    ex_obj.style.display = ex_obj.style.display=='none'?'block':'none';
    
    ex_obj.style.height ="50px"; 
    ex_obj.style.background = "#F22FF0";
}


  
function clickButton( seq,val){
  
  
  var params ="oper=edit&seq=" + seq+"&flag2=" + val;
  
    $.ajax({      
          type:"POST",  
          url:"/test/updateTest",      
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
  
}
  
</script>
  
  <br><br><br>
  
  
  <body class="bg">
  
  	<div id="lay" style="position:absolute; display:none;  z-index: 1;" >
	내용이 어쩌고 저쩌고ssssssssssssssssssssssssss
 	</div>

    <input type="text" id="datepicker1">  <button id="btnDate">조회</button> 
    <button id="btnReload">새로고침</button> 
    <button id="btnCheckReset">체크해제</button>
    <button id="btnCheckConfirm">체크확인</button> 
    <button id="btnDell">체크삭제</button>
    
    <button id="btnAdd">행추가</button>
    <input type="text" id="datepicker2">  <button id="btnPaste">복사</button>     
    <p> </p>        
  
    <table id="list"></table>
    <div id="paging"></div>




  </body>
  
  
  
   
   