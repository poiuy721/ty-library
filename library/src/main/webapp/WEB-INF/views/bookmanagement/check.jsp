<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>확인</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Favicon -->
<link href="/img/favicon.ico" rel="icon">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
<link href="/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

<!-- Customized Bootstrap Stylesheet -->
<link href="/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="/css/style.css" rel="stylesheet">

<style>
	.td {
		word-break: keep-all;
		text-align: left;
	}
</style>

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
						<img class="rounded-circle" src="img/user.jpg" alt=""
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
			<nav class="navbar bg-light navbar-light sticky-top px-4 py-0">
				<a href="index.html" class="navbar-brand d-flex d-lg-none me-4">
					<h2 class="text-primary mb-0">TY Library</h2>
				</a>
				<ul class="nav justify-content-end">
					<li style="font-size: 11px; vertical-align: middle; text-align: right">
						<c:if test="${employee.e_id eq null}">
							<a style="font-size: 13px; vertical-align: middle; text-align: right" href="/tylibrary/login">로그인</a>
						</c:if>
						<c:if test="${employee.e_id ne null}">
							사원 번호: ${employee.e_id} | 사원명 : ${employee.e_name}
						</c:if>
					</li>
				</ul>
			</nav>
			<!-- Navbar End -->


			<!-- Sale & Revenue Start -->
			<div class="container-fluid pt-4 px-4">
				<div class="row g-4">
					<div class="col-sm-6 col-xl-3">
						<div class="bg-light rounded d-md-flex align-items-center p-4">
							<div class="ms-3">
								<c:if test="${management_type eq 'rent'}">
			                       <h2 class="mb-0 text-center">대여</h2>
			                    </c:if>
			                    <c:if test="${management_type eq 'renew'}">
			                       <h2 class="mb-0 text-center">연장</h2>
			                    </c:if>
			                    <c:if test="${management_type eq 'assign'}">
			                       <h2 class="mb-0 text-center">양도</h2>
			                    </c:if>
			                    <c:if test="${management_type eq 'return'}">
			                       <h2 class="mb-0 text-center">반납</h2>
			                    </c:if>
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
					<form action="">
						<div class="bg-light text-center rounded p-4">
							<div
								class="d-flex align-items-center justify-content-between mb-4">
								<c:if test="${management_type eq 'rent'}">
			                       <h6 class="mb-0">| 대여 정보 확인</h6>
			                    </c:if>
			                    <c:if test="${management_type eq 'renew'}">
			                       <h6 class="mb-0">| 연장 정보 확인</h6>
			                    </c:if>
			                    <c:if test="${management_type eq 'assign'}">
			                       <h6 class="mb-0">| 양도 정보 확인</h6>
			                    </c:if>
			                    <c:if test="${management_type eq 'return'}">
			                       <h6 class="mb-0">| 반납 정보 확인</h6>
			                    </c:if>
							</div>
							<table class="table table-borderless">
								<thead>
								</thead>
								<tbody>
									<tr>
										<th scope="row" width="80px">도서명</th>
										<td>${bookInfo.title}</td>
									</tr>
									<tr>
										<th scope="row" width="80px">지은이</th>
										<td>${bookInfo.author}</td>
									</tr>
									<tr>
										<c:if test="${management_type eq 'rent'}">
					                    	<th scope="row" width="80px">대여기간</th>
					                    	<td>${rent_date} ~ ${due_date}</td>
					                    </c:if>
					                    <c:if test="${management_type eq 'renew'}">
					                    	<th scope="row" width="80px">연장기간</th>
					                    	<td>${rent_date} ~ ${due_date}</td>
					                    </c:if>
					                    <c:if test="${management_type eq 'assign'}">
					                    	<th scope="row" width="80px">(양도)<br>대여기간</th>
					                    	<td>${rent_date} ~ ${due_date}</td>
					                    </c:if>
					                    <c:if test="${management_type eq 'return'}">
					                    	<th scope="row" width="80px">반납일자</th>
					                    	<td>${rent_date}</td>
					                    </c:if>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="text-center">
						<br>
						
							<c:if test="${management_type eq 'rent'}">
					              <button type="submit" class="btn btn-outline-primary m-2" onclick="javascript:form.action='/tylibrary/rent'">대여</button>
					        </c:if>
					        <c:if test="${management_type eq 'renew'}">
					              <button type="submit" class="btn btn-outline-primary m-2" onclick="javascript:form.action='/tylibrary/renew'">연장</button>
					        </c:if>
					        <c:if test="${management_type eq 'assign'}">
					              <button type="submit" class="btn btn-outline-primary m-2" onclick="javascript:form.action='/tylibrary/assign'">양도 받기</button>
					        </c:if>
					        <c:if test="${management_type eq 'return'}">
					              <button type="submit" class="btn btn-outline-primary m-2" onclick="javascript:form.action='/tylibrary/return'">반납</button>
					        </c:if>
							<button type="button" class="btn btn-outline-danger m-2" onclick="history.back();">뒤로</button>
						</div>
					</form>	
					</div>
					<!-- Sales Chart End -->
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
							Distributed By <a class="border-bottom" href="https://themewagon.com" target="_blank">ThemeWagon</a>
						</div>
					</div>
				</div>
				<br>
				<div class="text-center">
					<a href="/tylibrary/logout">로그아웃</a>
				</div>
			</div>
			
			<!-- Footer End -->
		</div>
		<!-- Content End -->


		<!-- Back to Top -->
	</div>

	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
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