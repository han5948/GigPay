<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<link href="/resources/cms/jqueryUi/jquery-ui.css" rel="stylesheet">
<script type="text/javascript" src="/resources/cms/grid/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/resources/cms/jqueryUi/jquery-ui.js"></script>
	
<script type="text/javascript">
var painting = false;
$(function(){
	var drawCanvas = document.getElementById('drawCanvas');
	if (typeof drawCanvas.getContext == 'function') {

		var ctx = drawCanvas.getContext('2d');
		var isDraw = false;
		var width = $('#width').val();;
		var color = $('#color').val();
		var pDraw = $('#drawCanvas').offset();
		var currP = null;

		$('#width').bind('change', function(){ width = $('#width').val(); });
		$('#color').bind('change', function(){ color = $('#color').val(); });

		// Event (마우스)
		$('#drawCanvas').bind('mousedown', function(e) {
			if (e.button===0) {
				e.preventDefault();
				ctx.beginPath();
				isDraw = true;
			}
		});

		$('#drawCanvas').bind('mousemove', function(e) {
			var event = e.originalEvent;
			e.preventDefault();
			currP = { X:event.offsetX, Y:event.offsetY };
			if(isDraw) draw_line(currP);
		});

		$('#drawCanvas').bind('mouseup', function(e) {
			e.preventDefault();
			isDraw = false;
		});

		$('#drawCanvas').bind('mouseleave', function(e) {
			isDraw = false;
		});



		// Event (터치스크린)
// 		$('#drawCanvas').bind('touchstart', function(e) {

// 			e.preventDefault();
// 			ctx.beginPath();
// 		});

// 		$('#drawCanvas').bind('touchmove', function(e) {

// 			var event = e.originalEvent;
// 			e.preventDefault();
// 			currP = { X:event.touches[0].pageX-pDraw.left, Y:event.touches[0].pageY-pDraw.top };
// 			draw_line(currP);
// 		});

// 		$('#drawCanvas').bind('touchend', function(e) {

// 			e.preventDefault();
// 		});

		// 선 그리기
		function draw_line(p) {

			ctx.lineWidth = width;
			ctx.lineCap = 'round';
			ctx.lineTo(p.X, p.Y);
			ctx.moveTo(p.X, p.Y);
			ctx.strokeStyle = color;
			ctx.stroke();
		}


		function clearCanvas() {
			ctx.clearRect(0, 0, drawCanvas.width, drawCanvas.height);
			ctx.beginPath();
			localStorage.removeItem('imgCanvas');
		}

		$('#btnClea').click(function() {
			clearCanvas();
		});

	}

});
function endPop(){
	opener.document.getElementById('sign').src = document.getElementById('drawCanvas').toDataURL();
	self.close();
}
</script>
<body class="bg">
	<div id="pdfDiv" style="font-size: 50px; width:320px;">
	    <div align="center">
			<canvas id="drawCanvas" width="320" height="320" style="border:1px solid #000000;">Canvas not supported</canvas>
		</div>
		<div align="center">
			<button id="btnClea">Clear</button>
		</div>
		<div align="center">
	    	<a href="javascript:endPop();">저장</a>
	    </div>
	</div>
</body>
	
	
	
   
   