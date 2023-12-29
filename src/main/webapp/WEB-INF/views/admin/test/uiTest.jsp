<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 
	<link rel="stylesheet" href="/resources/cms/jqueryUi/jquery-ui.css" >
	<link rel="stylesheet" href="/resources/cms/grid/css/font-awesome.min.css" >
	<link rel="stylesheet" href="/resources/cms/grid/css/ui.jqgrid.min.css">
	<link rel="stylesheet" href="/resources/cms/select2/css/select2.min.css">
	
	<script type="text/javascript" src="/resources/cms/grid/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/resources/cms/jqueryUi/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/resources/cms/select2/js/select2.full.min.js"></script>
	<script type="text/javascript" src="/resources/cms/grid/js/jquery.jqGrid.min.js"></script>
		
		
<script type="text/javascript">
	$(function(){
		$("#example-basic").steps({
		    headerTag: "h3",
		    bodyTag: "section",
		    transitionEffect: "slideLeft",
		    autoFocus: true
		});
	});
	
</script>
	
	<body class="bg">

	<button id="button" class="ui-button  ui-corner-all  ui-widget">element</button>
	<div>
	이름 : <input id="worker_name" type="text" class="ui-input">	
	</div>
   
   