<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css" crossorigin="anonymous">
<link rel="stylesheet" href="/resources/cropper/css/bootstrap.css" >
<link rel="stylesheet" href="/resources/cropper/css/cropper.css">
<link rel="stylesheet" href="/resources/cropper/css/main.css">

      <!-- Scripts -->
<!--   <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script> -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="/resources/cropper/js/cropper.js"></script>
<script src="/resources/cropper/js/main.js"></script>
  
<script type="text/javascript">
	var defaultRatio = 16 / 10;
	var service_type = "${result.service_type}";
   	var service_code = "${result.service_code}";
   	var service_seq = "${result.service_seq}";
   	var file_path = "${result.file_path}";
   	var file_name= "${result.file_name}";
   
   	function deleteFile(service_type,service_code,service_seq) {
	   	if(!confirm("삭제 하시겠습니까?")){
			return;
		}
	   
		var _data = {
			service_type: service_type,
			service_code: service_code,
			service_seq: service_seq
		};
		var _url = "<c:url value='/admin/deleteFile' />";

		commonAjax(_url, _data, true, function(data) {
			if (data.code == "0000") {
				window.opener.fnReloadGrid();
				alert("삭제 하였습니다.");
				self.close();
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						alert(data.message);
					} else {
						toastFail("오류가 발생했습니다.1");
					}
				} else {
					toastFail("오류가 발생했습니다.2");
				}
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.3");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}
   	function setDefaultRatio(serviceCode){
   		if(serviceCode == 'MYPHOTO'){
   			$("#oneOnOneRatio").addClass("active");
   			return defaultRatio = 1;
   		}
   		$("#idCardRatio").addClass("active");
   	}
   	$(function(){
   		setDefaultRatio(service_code);
   	})
</script>
  
   <!-- Content -->
  	<div class="container">
    	<div class="row">
      		<div class="col-md-12">
        		<!-- <h3>Demo:</h3> -->
        		<div class="img-container">
        	 		<!-- <img src='/admin/imgLoad?path=C:/Users/Jangjaeho/Desktop/upload/worker/&name=201910231638540.jpg'/> -->
          	 		<img src='/admin/decImgLoad?path=${result.file_path}&name=${result.file_name}'/>
        		</div>
      		</div>
    	</div>

    	<div class="row" id="actions">
      		<div class="col-md-12 docs-buttons">
      			<div class="docs-toggles">
        			<!-- <h3>Toggles:</h3> -->
        			<div class="btn-group d-flex flex-nowrap" data-toggle="buttons">
          				<label class="btn btn-primary">
	            			<input type="radio" class="sr-only" id="aspectRatio1" name="aspectRatio" value="1.7777777777777777">
	            			<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 16 / 9">
	              				16:9
	            			</span>
          				</label>
		  				<label class="btn btn-primary" id="idCardRatio">
            				<input type="radio" class="sr-only" id="aspectRatio1" name="aspectRatio" value="1.5943029">
            				<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 16 / 10">
              					신분증
            				</span>
          				</label>
          				<label class="btn btn-primary">
            				<input type="radio" class="sr-only" id="aspectRatio2" name="aspectRatio" value="1.3333333333333333">
            				<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 4 / 3">
              					4:3
            				</span>
          				</label>
          				<label class="btn btn-primary" id="oneOnOneRatio">
            				<input type="radio" class="sr-only" id="aspectRatio3" name="aspectRatio" value="1">
            				<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 1 / 1">
              					1:1
            				</span>
          				</label>
          				<label class="btn btn-primary">
            				<input type="radio" class="sr-only" id="aspectRatio4" name="aspectRatio" value="0.9">
            				<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 9 / 10">
              					9:10
            				</span>
          				</label>
		  				<label class="btn btn-primary">
            				<input type="radio" class="sr-only" id="aspectRatio4" name="aspectRatio" value="0.8">
            				<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 4 / 5">
	              				4:5
            				</span>
          				</label>
		  				<label class="btn btn-primary">
            				<input type="radio" class="sr-only" id="aspectRatio4" name="aspectRatio" value="0.75">
            				<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 3 / 4">
              					3:4
            				</span>
          				</label>
		  				<label class="btn btn-primary">
            				<input type="radio" class="sr-only" id="aspectRatio4" name="aspectRatio" value="0.70721358">
            				<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 1 / 1.414">
              					A4
            				</span>
          				</label>
          				<label class="btn btn-primary">
            				<input type="radio" class="sr-only" id="aspectRatio4" name="aspectRatio" value="0.6666666666666666">
            				<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 2 / 3">
              					2:3
            				</span>
          				</label>
		  
		   				<label class="btn btn-primary">
            				<input type="radio" class="sr-only" id="aspectRatio4" name="aspectRatio" value="0.5">
            				<span class="docs-tooltip" data-toggle="tooltip" title="aspectRatio: 1 / 2">
             					1:2
            				</span>
          				</label>
        			</div>
     			</div>
<!-- 
        <div class="btn-group">
          <button type="button" class="btn btn-primary" data-method="setDragMode" data-option="move" title="Move">
            <span class="docs-tooltip" data-toggle="tooltip" title="cropper.setDragMode(&quot;move&quot;)">
              <span class="fa fa-arrows-alt"></span>
            </span>
          </button>
          <button type="button" class="btn btn-primary" data-method="setDragMode" data-option="crop" title="Crop">
            <span class="docs-tooltip" data-toggle="tooltip" title="cropper.setDragMode(&quot;crop&quot;)">
              <span class="fa fa-crop-alt"></span>
            </span>
          </button>
        </div>
 -->
				<div class="btn-group">
          			<button type="button" class="btn btn-primary" data-method="zoom" data-option="0.1" title="Zoom In">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.zoom(0.1)">
              				<span class="fa fa-search-plus"></span>
            			</span>
          			</button>
          			<button type="button" class="btn btn-primary" data-method="zoom" data-option="-0.1" title="Zoom Out">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.zoom(-0.1)">
              				<span class="fa fa-search-minus"></span>
            			</span>
          			</button>
        		</div>

        		<div class="btn-group">
          			<button type="button" class="btn btn-primary" data-method="move" data-option="-10" data-second-option="0" title="Move Left">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.move(-10, 0)">
              				<span class="fa fa-arrow-left"></span>
            			</span>
          			</button>
          			<button type="button" class="btn btn-primary" data-method="move" data-option="10" data-second-option="0" title="Move Right">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.move(10, 0)">
              				<span class="fa fa-arrow-right"></span>
            			</span>
          			</button>
          			<button type="button" class="btn btn-primary" data-method="move" data-option="0" data-second-option="-10" title="Move Up">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.move(0, -10)">
              				<span class="fa fa-arrow-up"></span>
            			</span>
          			</button>
          			<button type="button" class="btn btn-primary" data-method="move" data-option="0" data-second-option="10" title="Move Down">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.move(0, 10)">
              				<span class="fa fa-arrow-down"></span>
            			</span>
          			</button>
        		</div>

        		<div class="btn-group">
          			<button type="button" class="btn btn-primary" data-method="rotate" data-option="-45" title="Rotate Left">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.rotate(-45)">
              				<span class="fa fa-undo-alt"></span>
            			</span>
          			</button>
          			<button type="button" class="btn btn-primary" data-method="rotate" data-option="45" title="Rotate Right">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.rotate(45)">
              				<span class="fa fa-redo-alt"></span>
            			</span>
          			</button>
        		</div>

        		<div class="btn-group">
          			<button type="button" class="btn btn-primary" data-method="scaleX" data-option="-1" title="Flip Horizontal">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.scaleX(-1)">
              				<span class="fa fa-arrows-alt-h"></span>
            			</span>
          			</button>
          			<button type="button" class="btn btn-primary" data-method="scaleY" data-option="-1" title="Flip Vertical">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.scaleY(-1)">
              				<span class="fa fa-arrows-alt-v"></span>
            			</span>
          			</button>
        		</div>

        		<div class="btn-group">
          			<button type="button" class="btn btn-primary" data-method="crop" title="Crop">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.crop()">
              				<span class="fa fa-check"></span>
            			</span>
          			</button>
          			<button type="button" class="btn btn-primary" data-method="clear" title="Clear">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.clear()">
              				<span class="fa fa-times"></span>
            			</span>
          			</button>
        		</div>
<!-- 
        <div class="btn-group">
          <button type="button" class="btn btn-primary" data-method="disable" title="Disable">
            <span class="docs-tooltip" data-toggle="tooltip" title="cropper.disable()">
              <span class="fa fa-lock"></span>
            </span>
          </button>
          <button type="button" class="btn btn-primary" data-method="enable" title="Enable">
            <span class="docs-tooltip" data-toggle="tooltip" title="cropper.enable()">
              <span class="fa fa-unlock"></span>
            </span>
          </button>
        </div> -->

        		<div class="btn-group">
          			<button type="button" class="btn btn-primary" data-method="reset" title="Reset">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.reset()">
              				<span class="fa fa-sync-alt"></span>
            			</span>
          			</button>
          <!-- 
          
          <label class="btn btn-primary btn-upload" for="inputImage" title="Upload image file">
            <input type="file" class="sr-only" id="inputImage" name="file" accept="image/*">
            <span class="docs-tooltip" data-toggle="tooltip" title="Import image with Blob URLs">
              <span class="fa fa-upload"></span>
            </span>
          </label>
          -->
        		</div>

        		<div class="btn-group btn-group-crop">
          			<button type="button" class="btn btn-success" data-method="getCroppedCanvas" data-option="{ &quot;maxWidth&quot;: 4096, &quot;maxHeight&quot;: 4096 }">
            			<span class="docs-tooltip" data-toggle="tooltip" title="cropper.getCroppedCanvas({ maxWidth: 4096, maxHeight: 4096 })">
              				저장하기
            			</span>
          			</button>
          <!-- 
          <button type="button" class="btn btn-success" data-method="getCroppedCanvas" data-option="{ &quot;width&quot;: 160, &quot;height&quot;: 90 }">
            <span class="docs-tooltip" data-toggle="tooltip" title="cropper.getCroppedCanvas({ width: 160, height: 90 })">
              저장하기
            </span>
          </button>
           -->
        		</div>
   
   				<div class="btn-module mgtL textC">
      				<!-- <div class="rightGroup"> -->
      				<a class="btnStyle05" href="javascript:self.close();">닫기</a>
      				<c:if test="${vFlag == 'worker' && isFile == 'Y' }">
      					<a class="btnStyle05 mglL" href="javascript:void(0)" onclick="deleteFile('${result.service_type}', '${result.service_code}','${result.service_seq}')">삭제</a>
      				</c:if>
    			</div>
    
        		<!-- Show the cropped image in modal -->
        		<div class="modal fade docs-cropped" id="getCroppedCanvasModal" role="dialog" aria-hidden="true" aria-labelledby="getCroppedCanvasTitle" tabindex="-1">
          			<div class="modal-dialog">
            			<div class="modal-content">
              				<div class="modal-header">
                				<h5 class="modal-title" id="getCroppedCanvasTitle">Cropped</h5>
                				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  					<span aria-hidden="true">&times;</span>
                				</button>
              				</div>
              				
              				<div class="modal-body"></div>
              				<div class="modal-footer">
                				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                				<a class="btn btn-primary" id="download" href="javascript:void(0);" download="cropped.jpg">Download</a>
              				</div>
            			</div>
          			</div>
        		</div><!-- /.modal -->
      		</div><!-- /.docs-buttons -->
    	</div>
  	</div>
    

