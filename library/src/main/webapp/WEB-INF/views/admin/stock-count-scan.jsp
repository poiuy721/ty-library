<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>DASHMIN - Bootstrap Admin Template</title>

<style>
/* CSS for positioning the video and canvas elements */
#video-container {
	position: relative;
}

#video, #canvas {
	position: absolute;
	top: 0;
	left: 0;
}
</style>


<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">
<script type="text/javascript"
	src="https://unpkg.com/@zxing/library@latest/umd/index.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Favicon -->
<link href="img/favicon.ico" rel="icon">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap"
	rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="/lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">
<link href="/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css"
	rel="stylesheet" />

<!-- Customized Bootstrap Stylesheet -->
<link href="/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="/css/style.css" rel="stylesheet">
</head>
<body>


	<div class="container-xxl position-relative bg-white d-flex p-0">
		<!-- Spinner Start -->
		<div id="spinner"
			class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
			<div class="spinner-border text-primary"
				style="width: 3rem; height: 3rem;" role="status">
				<span class="sr-only">Loading...</span>
			</div>
		</div>
		<!-- Spinner End -->


		<!-- Sidebar Start -->
		<div class="sidebar pe-4 pb-3">
			<nav class="navbar bg-light navbar-light">
				<a href="index.html" class="navbar-brand mx-4 mb-3">
					<h3 class="text-primary">
						<i class="fa fa-hashtag me-2"></i>TY Library
					</h3>
				</a>
				<div class="d-flex align-items-center ms-4 mb-4">
					<div class="position-relative">
						<img class="rounded-circle" src="/img/user.jpg" alt=""
							style="width: 40px; height: 40px;">
						<div
							class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
					</div>
					<div class="ms-3">
						<h6 class="mb-0">Jhon Doe</h6>
						<span>Admin</span>
					</div>
				</div>
				<div class="navbar-nav w-100">
					<a href="index.html" class="nav-item nav-link active"><i
						class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
					<div class="nav-item dropdown">
						<a href="#" class="nav-link dropdown-toggle"
							data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Elements</a>
						<div class="dropdown-menu bg-transparent border-0">
							<a href="button.html" class="dropdown-item">Buttons</a> <a
								href="typography.html" class="dropdown-item">Typography</a> <a
								href="element.html" class="dropdown-item">Other Elements</a>
						</div>
					</div>
					<a href="widget.html" class="nav-item nav-link"><i
						class="fa fa-th me-2"></i>Widgets</a> <a href="form.html"
						class="nav-item nav-link"><i class="fa fa-keyboard me-2"></i>Forms</a>
					<a href="table.html" class="nav-item nav-link"><i
						class="fa fa-table me-2"></i>Tables</a> <a href="chart.html"
						class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
					<div class="nav-item dropdown">
						<a href="#" class="nav-link dropdown-toggle"
							data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
						<div class="dropdown-menu bg-transparent border-0">
							<a href="signin.html" class="dropdown-item">Sign In</a> <a
								href="signup.html" class="dropdown-item">Sign Up</a> <a
								href="404.html" class="dropdown-item">404 Error</a> <a
								href="blank.html" class="dropdown-item">Blank Page</a>
						</div>
					</div>
				</div>
			</nav>
		</div>
		<!-- Sidebar End -->


		<!-- Content Start -->
		<div class="content">
			<!-- Navbar Start -->
			<nav
				class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
				<a href="index.html" class="navbar-brand d-flex d-lg-none me-4">
					<h2 class="text-primary mb-0">TY Library</h2>
				</a>
			</nav>
			<!-- Navbar End -->


			<!-- ��ĳ�ʹڽ� -->

			<div class="container-fluid pt-4 px-4"
				style="width: 100%; height: 40%">
				<div class="row g-4" style="height: 100%">
					<div class="col-sm-12 col-xl-12" style="height: 100%">
						<div id="video-container"
							class="bg-light rounded d-md-flex align-items-center p-4"
							style="height: 100%; border: 2px solid gray; overflow: hidden;">
							<video id="video" style="object-fit: cover;"></video>
							<canvas id="canvas" style="object-fit: cover; display: none;"></canvas>
						</div>
					</div>
				</div>
			</div>

			<div class="container-fluid pt-4 px-4">
				<div class="row g-4">
					<div class="col-sm-12 col-xl-12">
						<form action="stock-count">
							<input type="submit" id="stock-select"
								class="btn btn-primary m-2 w-100" value="��Ȳ����"
								style="margin: 0 !important;" /><input type="text" name="state"
								value="1" style="display: none" />
						</form>
						<button id="switchButton" class="btn btn-primary m-2 w-100"
							style="margin: 0 !important; margin-top: 0.4em !important">ȭ����ȯ</button>
					</div>


					<!-- Ȯ�� ��� -->
					<div class="col-12">
						<div class="bg-light text-center rounded p-4">
							<h6 class="mb-0">��� �˻� �Ϸ�</h6>
							<div class="table-responsive">
								<table class="table small">
									<thead class="small">
										<tr>
											<th scope="col">������</th>
											<th scope="col">���� ID</th>
										</tr>
									</thead>
									<tbody class="small">
										<tr>
											<td id="book_info1">""</td>
											<td id="book_info2">""</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
			</div>
			<!-- Footer Start -->

			<div class="container-fluid pt-4 px-4">
				<div class="bg-light rounded-top p-4">
					<div class="row">
						<div class="col-12 col-sm-6 text-center text-sm-start">
							&copy; <a href="#">Your Site Name</a>, All Right Reserved.
						</div>
						<div class="col-12 col-sm-6 text-center text-sm-end">
							<!--/*** This template is free as long as you keep the footer author��s credit link/attribution link/backlink. If you'd like to use the template without the footer author��s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
							Designed By <a href="https://htmlcodex.com">HTML Codex</a> </br>
							Distributed By <a class="border-bottom"
								href="https://themewagon.com" target="_blank">ThemeWagon</a>
						</div>
					</div>
				</div>
			</div>
			<!-- Footer End -->
		</div>
		<!-- Content End -->


		<!-- Back to Top -->
	</div>

	<script type="text/javascript">
	const switching = document.getElementById("switchButton");
	const video = document.getElementById("video");
	const canvas = document.getElementById("canvas");
	let stock_state = ${state};
	let i = 0;
	let lastResult = "";
	
	//ī�޶� ���� �Լ�
	window.addEventListener('load', function () {
		let selectedDeviceId;
		const codeReader = new ZXing.BrowserMultiFormatReader();
		console.log('ZXing code reader initialized');
		codeReader.listVideoInputDevices().then((videoInputDevices) => {//��밡���� ī�޶� �ִ��� ã�� videoInputDevices�� �迭���� 
			let numOfCamera = videoInputDevices.length;
			if (numOfCamera<1){
				alert ("ī�޶� �ִ� ����̽��� �������ּ���");
				console.log("ī�޶� ã�� ���߽��ϴ�.");
				window.history.back();
				}else if(stock_state == 2){
					console.log("${stock_state}");
					$.ajax({
						url: "/tylibrary/stock-camera-ok",
						type: "POST",
						data: { cameraState: 3 },
						success: function (data) {
							console.log("Y -> N �����ʱ�ȭ ����");
							console.log(data);
							stock_state = data;
	                     },
	                     error: function () {
	                    	 alert("����@@�߻�")
	                     }
	                     });
					}
			selectedDeviceId = videoInputDevices[i].deviceId;
			switching.addEventListener('click', () => {
				i++;
				codeReader.reset();
				if(i == numOfCamera){
					i = 0;
					selectedDeviceId = videoInputDevices[i].deviceId
					}else{
						selectedDeviceId = videoInputDevices[i].deviceId;
						}
				codeReader.decodeFromVideoDevice(selectedDeviceId, 'video', (result, err) => {
					if (result) {
						if(result.text != lastResult){
							lastResult = result.text;
							let urlId = "/tylibrary/stock-is-exist?id="+result.text.split('?id=')[1];
							//ajax������� ���� �� ������ �����
							if(stock_state==3){
								$.ajax({
									url: urlId,
									type: "GET",
									dataType: "json",
									success: function (data) {
										if(data){
											canvas.width = video.offsetWidth;
											canvas.height = video.offsetHeight;
											canvas.getContext('2d').drawImage(video, 0, 0, video.offsetWidth, video.offsetHeight, 0, 0, video.offsetWidth,video.offsetHeight);
											canvas.style.display = "block";
											$("#video-container").css({"border-color": "green","border-width":"5px"});
											setTimeout(() => $("#video-container").css({"border-color": "gray","border-width":"2px"}),1000);
											setTimeout(() => canvas.style.display="none",1000);
											$("#book_info1").text(data.b_id);
											$("#book_info2").text(data.title);
											}
										},
										error: function (data) {
											alert("�����߻�");
											//alert(data.[0]);
											}
										});
								}
							}
						}
					if (err && !(err instanceof ZXing.NotFoundException)) {
						console.error(err);
						document.getElementById('result').textContent = err;
						}
					})
					})
					switching.click();
			}).catch((err) => {console.error(err)})
		})
	</script>
	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="/lib/chart/chart.min.js"></script>
	<script src="/lib/easing/easing.min.js"></script>
	<script src="/lib/waypoints/waypoints.min.js"></script>
	<script src="/lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="/lib/tempusdominus/js/moment.min.js"></script>
	<script src="/lib/tempusdominus/js/moment-timezone.min.js"></script>
	<script src="/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

	<!-- Template Javascript -->
	<script src="/js/main.js"></script>

</body>
</html>