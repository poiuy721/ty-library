<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">

<title>대여 기간 선택</title>
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


<style>
/*
*
* ==========================================
* CUSTOM UTIL CLASSES
* ==========================================
*
*/
.datepicker td, .datepicker th {
    width: 2.5rem;
    height: 2.5rem;
    font-size: 0.85rem;
}

.datepicker {
    margin-bottom: 3rem;
}

/*
*
* ==========================================
* FOR DEMO PURPOSES
* ==========================================
*
*/
body {
    min-height: 100vh;
    background-color: #fafafa;
}

.input-group {
    border-radius: 30rem;
}

input.form-control {
    border-radius: 30rem 0 0 30rem;
    border: none;
}

input.form-control:focus {
    box-shadow: none;
}

input.form-control::placeholder {
    font-style: italic;
}

.input-group-text {
    border-radius: 0 30rem 30rem 0;
    border: none;
}

.datepicker-dropdown {
    box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
}

</style>

</head>

<body>
	<div class="container-xxl position-relative bg-white d-flex p-0">

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
			<nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
				<a href="index.html" class="navbar-brand d-flex d-lg-none me-4">
					<h2 class="text-primary mb-0">TY Library</h2>
				</a>
				<ul class="nav justify-content-end">
		            <li style="font-size:11px; vertical-align:middle; text-align:right" >사원 번호: ${employee.e_id}  | 사원명 : ${employee.e_name}
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
								<c:if test="${management_type eq 'assign'}">
									<h2 class="mb-0 text-center">양도</h2>
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
						<div class="bg-light rounded p-4">
							<div class="d-flex align-items-center justify-content-between mb-4">
								<h6 class="mb-0">| 대여 기간 선택</h6>
							</div>
							<ul class="mb-0 text-left">
								<li>기본 대여 기간은 2주입니다.</li>
								<li>기본 대여 기간을 초과하여 대여하기를 원하신다면, 하단 달력에서 반납 일자를 선택하여 주시길
									바랍니다.</li>
								<li>최대 대여 기간은 다음달 까지입니다.</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="container-fluid pt-4 px-4">
				<div class="row g-4">
					<div class="col-sm-12 col-xl-6">
						<div class="bg-light rounded p-4">
						
				            <!-- Date Picker Input -->
				            <div class="form-group mb-4">
				                <div class="datepicker date input-group p-0 shadow-sm">
				                    <input type="text" placeholder="대여 기간을 선택하세요" class="form-control py-2.2 px-4" id="datepicker">
				                    <div class="input-group-append"><span class="input-group-text px-4"><i>선택</i></span></div>
				                </div>
				            </div><!-- DEnd ate Picker Input -->
				            
						</div>
					</div>
				</div>
			</div>
			<div class="text-center">
				<c:if test="${management_type eq 'rent'}">
					<button type="button" class="btn btn-outline-primary m-2" onclick="location='/tylibrary/rent/check'">다음</button>
				</c:if>
				<c:if test="${management_type eq 'assign'}">
					<button type="button" class="btn btn-outline-primary m-2" onclick="location='/tylibrary/assign/check'">다음</button>
				</c:if>
			</div>
			<!-- Sales Chart End -->

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
				</div>
				<!-- Footer End -->
			</div>
			<!-- Content End -->


			<!-- Back to Top -->
		</div>
		
	<script>

		$(function() {
	
			// INITIALIZE DATEPICKER PLUGIN
			$('#datepicker').datepicker({
				clearBtn : true,
				dateFormat : "yy/mm/dd",
				showMonthAfterYear:true,
				yearSuffix: "년",
				monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				dayNamesMin: ['일','월','화','수','목','금','토'],
				minDate: "0",
				maxDate: "+1M"
			});
			
			// 기본 연장 기간 설정
			let today = new Date();
			let basic_return_date = new Date(today.setDate(today.getDate()+14));
			let brd = basic_return_date.getFullYear()+"/"+leftPad(basic_return_date.getMonth()+1)+"/"+leftPad(basic_return_date.getDate());
			
			// ========== 기본 대여 기간 설정 : start ==========
			var arr = new Array();
			arr.push(brd);
	
		    var url;
		    if('${management_type}'=='rent')
		    	url = "/tylibrary/rent/due";
		    else
		    	url = "/tylibrary/assign/due";
			
			$.ajax({
			    url: url,
			    data: {arr,arr},
			    dataType : 'json',
			    type: "POST",
			    success : function(data){
			    },
			    error : function(data){		
			    }
			});
			// ========== 기본 대여 기간 설정 : end ==========
	
			// 날짜 선택 시, 날짜 데이터 전송
			$('#datepicker').on('change', function() {
				var pickedDate = $('input').val();
				$('#pickedDate').html(pickedDate);
	
				var arr = new Array();
				arr.push(pickedDate);
				$.ajax({
				    url: url,
				    data: {arr,arr},
				    dataType : 'json',
				    type: "POST",
				    success : function(data){
				    },
				    error : function(data){		
				    }
				}); 
				
			});
	
			// 기본 대여 기간 설정       
			$( "#datepicker" ).datepicker( 'setDate', '14D' );
			
			// 공백에 '0' 삽입하는 메소드
	        function leftPad(value) {
	            if (value < 10) {
	                value = "0" + value;
	                return value;
	            }
	            return value;
	        }
	
		});
		 
	</script>

		<!-- JavaScript Libraries -->
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
		

</body>

</html>