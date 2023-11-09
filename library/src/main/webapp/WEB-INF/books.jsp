<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<title>Books</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Favicon -->
<link href="/boot/img/favicon.ico" rel="icon">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="/boot/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
<link href="/boot/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

<!-- Customized Bootstrap Stylesheet -->
<link href="/boot/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="/boot/css/style.css" rel="stylesheet">

	<script type="text/javascript">	

	// 버튼 모두 표시하는 경우 : sort 구분
		function Login(id) {
			 //const id = t.getAttribute("no");
			 var arr = new Array();
			 arr.push(id);
		     $.ajax({
		    	    url: "/tylibrary/login/${b_id}",
		    	    data: {arr:arr},
		    	    dataType : 'json',
		    	    type: "POST",
		    	    success : function(data){
		    	    	//console.log(data);
		    	    },
		    	    error : function(data){
		    	    	var d = JSON.parse(JSON.stringify(data));
		    	    	location.href = d['responseText'];
		    	    }
		     });  
		}
	
	</script>
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
						<img class="rounded-circle" src="/boot/img/user.jpg" alt=""
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


			<!-- Sale & Revenue Start -->
			<div class="container-fluid pt-4 px-4">
				<div class="row g-4">
					<div class="col-sm-6 col-xl-3">
						<div class="bg-light rounded d-md-flex align-items-center p-4">
							<div class="ms-3">
								<h2 class="mb-0 text-center">도서 정보</h2>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Sale & Revenue End -->


			<!-- Sales Chart Start -->
			<div class="container-fluid pt-4 px-4">
				<div class="row g-4">
					<div class="col-sm-12 col-xl-6">
						<div class="bg-light text-center rounded p-4">
							<div
								class="d-flex align-items-center justify-content-between mb-4">
								<h6 class="mb-0">${bookInfo.title}</h6>
							</div>
							<table class="table table-borderless">
								<thead>
								</thead>
								<tbody>
									<tr>
										<th scope="row">지은이</th>
										<td></td>
										<td>${bookInfo.author}</td>
									</tr>
									<tr>
										<th scope="row">출판사</th>
										<td></td>
										<td>${bookInfo.publisher}</td>
									</tr>
									<tr>
										<th scope="row">카테고리</th>
										<td></td>
										<td>${bookInfo.category}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="col-sm-12 col-xl-6">
						<div class="bg-light text-center rounded p-4">
							<div
								class="d-flex align-items-center justify-content-between mb-4">
								<h6 class="mb-0">소장 정보</h6>
							</div>
							<table class="table">
								<thead>
									<tr>
										<th scope="col">#</th>
										<th scope="col">도서 ID</th>
										<th scope="col">도서 상태</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${bookStatus}" var="bookStatus" varStatus="status">
									<c:set var="i" value="${i+1}"/>
									<tr>
										<th scope="row">${i}</th>
										<td>${bookStatus.b_id}</td>
										<c:choose>
											<c:when test="${empty bookStatus.b_status}">
												<td>대여 가능</td>
					                        </c:when>
					                        <c:otherwise>
					                            <td>대여 불가</td>
					                        </c:otherwise>
										</c:choose>	
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- Sales Chart End -->

					<div class="col-sm-12 col-xl-6">
						<div class="text-center rounded">					
							<c:if test="${status eq 'NN'}">
			                    <button type="button" class="btn btn-outline-primary m-2" id="rent" onclick="Login(this.id)">대여</button>
			                </c:if>
			                <c:if test="${status eq 'YY'}">
			                    <button type="button" class="btn btn-outline-primary m-2" id="renew" onclick="Login(this.id)">연장</button>
								<button type="button" class="btn btn-outline-primary m-2" id="assign" onclick="Login(this.id)">양도</button>
								<button type="button" class="btn btn-outline-primary m-2" id="return" 
								<c:choose>
									<c:when test="${adminId eq 'admin'}"></c:when>
					                <c:otherwise>style="display:none"</c:otherwise>
								</c:choose>								
								onclick="Login(this.id)">반납</button>
			                </c:if>
					   <!-- <button type="button" class="btn btn-outline-primary m-2" id="return" onclick="openCamera();">반납</button> -->
						</div>
					</div>	
				</div>
			</div>

			<!-- Footer Start -->

			<div class="container-fluid pt-4 px-4">
				<div class="bg-light rounded-top p-4">
					<div class="row">
						<div class="col-12 col-sm-6 text-center text-sm-start">
							&copy; <a href="#">TY Library</a>, All Right Reserved.
						</div>
						<div class="col-12 col-sm-6 text-center text-sm-end">
							<!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
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
		<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
			class="bi bi-arrow-up"></i></a>
	</div>

	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="/boot/lib/chart/chart.min.js"></script>
	<script src="/boot/lib/easing/easing.min.js"></script>
	<script src="/boot/lib/waypoints/waypoints.min.js"></script>
	<script src="/boot/lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="/boot/lib/tempusdominus/js/moment.min.js"></script>
	<script src="/boot/lib/tempusdominus/js/moment-timezone.min.js"></script>
	<script src="/boot/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

	<!-- Template Javascript -->
	<script src="/boot/js/main.js"></script>

<!--  //한 화면에 버튼 다 표시하는 경우
	<button type="button" class="btn btn-outline-primary m-2" id="rent" onclick="Login(this.id)">대여</button>
	<button type="button" class="btn btn-outline-primary m-2" id="renew" onclick="Login(this.id)">연장</button>
	<button type="button" class="btn btn-outline-primary m-2" id="assign" onclick="Login(this.id)">양도</button>
	<button type="button" class="btn btn-outline-primary m-2" id="return"
		<c:choose>
			<c:when test="${adminId eq 'admin'}"></c:when>
			<c:otherwise>style="display:none"</c:otherwise>
		</c:choose>
	onclick="Login(this.id);">반납</button>
-->




</body>

</html>