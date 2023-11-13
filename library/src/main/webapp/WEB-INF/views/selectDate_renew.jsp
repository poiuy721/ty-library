<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<title>연장 기간 선택</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Favicon -->
<link href="img/favicon.ico" rel="icon">

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
	@import url('https://fonts.googleapis.com/css?family=Questrial&display=swap');
	
		td {
            width: 30px;
            height: 30px;
        }

        .Calendar {
            text-align: center;
        }

        .Calendar>thead>tr:first-child>td {
            font-family: 'Questrial', sans-serif;
            font-size: 1.1em;
            font-weight: bold;
        }

        .Calendar>thead>tr:last-child>td {
            font-family: 'Questrial', sans-serif;
            font-weight: 600;     
        }

        .Calendar>tbody>tr>td>p {
            font-family: 'Montserrat', sans-serif;
            height: 35px;
            width: 35px;
            border-radius: 35px;
            transition-duration: .2s;
            line-height: 35px;
            margin: 2.5px;
            display: block;
            text-align: center;
        }        
	
	    .pastDay {
            color: lightgray;
        }

        .today {        
        	background-color: #C3C3C3;  
            color: #fff;
            font-weight: 600;
            cursor: pointer;
        }

        .futureDay {
         	ackground-color: #FFFFFF;
            cursor: pointer;
        }
        .futureDay:hover{
            background:#eee;
        }

	.futureDay.choiceDay, .today.choiceDay {
		background: #0A174E;
		color: #fff;
		font-weight: 600;
		cursor: pointer;
	}
	
	.choiceDay, .after2weeks {
		background-color: #99D9EA;
		color: #fff;
		font-weight: 600;
		cursor: pointer;
	}
       
</style>


<script type="text/javascript">
	window.onload = function () { buildCalendar(); }    // 웹 페이지가 로드되면 buildCalendar 실행
	
	let nowMonth = new Date();  // 현재 달을 페이지를 로드한 날의 달로 초기화
	
	// 달력 생성 : 해당 달에 맞춰 테이블을 만들고, 날짜를 채워 넣는다.
	function buildCalendar() {
		
	    let date = JSON.stringify(${latestDueDate});     // 페이지를 로드한 날짜를 저장
	    let today_str = date.slice(0,4) + '-' + date.slice(4, 6) + '-' + date.slice(6,8);
	    let today = new Date(today_str);
		today.setHours(0, 0, 0, 0);    // 비교 편의를 위해 today의 시간을 초기화
	    
		let month; let year; let day;
	    day = today.getDate()+14;
	
	    var arr = new Array();
	
	    let firstDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth(), 1);     // 이번달 1일
	    let lastDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth() + 1, 0);  // 이번달 마지막날
	
	    if(lastDate.getDate()==31 && today.getDate()>17 && today.getDate()<32){
	    	day = day-31;
	    } else if(lastDate.getDate()==30 && today.getDate()>16 && today.getDate()<31){
	    	day = day-30;
	    } else if(lastDate.getDate()==28 && today.getDate()>14 && today.getDate()<29){
	    	day = day-28;
	    }
	    
	    console.log(day);
	    
	    let tbody_Calendar = document.querySelector(".Calendar > tbody");
	    document.getElementById("calYear").innerText = nowMonth.getFullYear();             // 연도 숫자 갱신
	    document.getElementById("calMonth").innerText = leftPad(nowMonth.getMonth() + 1);  // 월 숫자 갱신
	    
	    year = document.getElementById("calYear").innerText;
	    month = document.getElementById("calMonth").innerText;
	
	    arr.push(year);
	    arr.push(month);
	    arr.push(leftPad(day));
	    
		    $.ajax({
		    url: "/tylibrary/renew/due",
		    data: {arr,arr},
		    dataType : 'json',
		    type: "POST",
		    success : function(data){
		    	//window.location.reload();
		    },
		    error : function(){		
		    	//window.location.reload();
		    }
		}); 
	
	    while (tbody_Calendar.rows.length > 0) {                        // 이전 출력결과가 남아있는 경우 초기화
	        tbody_Calendar.deleteRow(tbody_Calendar.rows.length - 1);
	    }
	
	    let nowRow = tbody_Calendar.insertRow();        // 첫번째 행 추가           
	
	    for (let j = 0; j < firstDate.getDay(); j++) {  // 이번달 1일의 요일만큼
	        let nowColumn = nowRow.insertCell();        // 열 추가
	    }
	
	    for (let nowDay = firstDate; nowDay <= lastDate; nowDay.setDate(nowDay.getDate() + 1)) {   // day는 날짜를 저장하는 변수, 이번달 마지막날까지 증가시키며 반복  
	
	        let nowColumn = nowRow.insertCell();        // 새 열을 추가하고
	
	        let newDIV = document.createElement("p");
	        newDIV.innerHTML = leftPad(nowDay.getDate());        // 추가한 열에 날짜 입력
	        nowColumn.appendChild(newDIV);
	
	        if (nowDay.getDay() == 6) {                 // 토요일인 경우
	            nowRow = tbody_Calendar.insertRow();    // 새로운 행 추가
	        }
	        if (nowDay < today) {                       // 지난날인 경우
	            newDIV.className = "pastDay";
	        }
	        else if (nowDay.getFullYear() == today.getFullYear() && nowDay.getMonth() == today.getMonth() && nowDay.getDate() == today.getDate()) { // 오늘인 경우   
	        	newDIV.className = "today";
	            newDIV.onclick = function () { choiceDate(this, year, month); }
	        }
	        else if (nowDay.getFullYear() == today.getFullYear() && nowDay.getMonth() == today.getMonth() && nowDay.getDate() == day) {   
	            newDIV.className = "choiceDay";
	            newDIV.onclick = function () { choiceDate(this, year, month); }
	        }
	        else if (nowDay.getFullYear() == today.getFullYear() && nowDay.getMonth() == today.getMonth()+1 && nowDay.getDate() == day) {   
	            newDIV.className = "choiceDay";
	            newDIV.onclick = function () { choiceDate(this, year, month); }
	        }
	        else {                                      // 미래인 경우
	            newDIV.className = "futureDay";
	            newDIV.onclick = function () { choiceDate(this, year, month); }
	        }
	    }
	}
	
	// 날짜 선택
	function choiceDate(newDIV, year, month) {
	    if (document.getElementsByClassName("choiceDay")[0]) {                              // 기존에 선택한 날짜가 있으면
	        document.getElementsByClassName("choiceDay")[0].classList.remove("choiceDay");  // 해당 날짜의 "choiceDay" class 제거
	    }
	    newDIV.classList.add("choiceDay");           // 선택된 날짜에 "choiceDay" class 추가
	    day = newDIV.innerHTML;
	    
	    // 선택한 연장 날짜
	    var due_date = year + "-" + month + "-" + newDIV.innerHTML;
	    var due = new Date(due_date);
	
	    // 연장 가능한 최대 날짜
	    var one_date = today.getFullYear() + "-" + (today.getMonth()+2) + "-" + today.getDate();
	    var one = new Date(one_date);
	
	    if(due>one){
	    	return;
	    } else {
	    	
	        var arr = new Array();
	        arr.push(year);
	        arr.push(month);
	        arr.push(day);
	
	   	    $.ajax({
		    	    url: "/tylibrary/renew/due",
		    	    data: {arr,arr},
		    	    dataType : 'json',
		    	    type: "POST",
		    	    success : function(data){
		    	    	//window.location.reload();
		    	    },
		    	    error : function(){		
		    	    	//window.location.reload();
		    	    }
		    }); 
	    }
	}
	
	// 이전달 버튼 클릭
	function prevCalendar() {
	    nowMonth = new Date(nowMonth.getFullYear(), nowMonth.getMonth() - 1, nowMonth.getDate());   // 현재 달을 1 감소
	    buildCalendar();    // 달력 다시 생성
	}
	// 다음달 버튼 클릭
	function nextCalendar() {
	    nowMonth = new Date(nowMonth.getFullYear(), nowMonth.getMonth() + 1, nowMonth.getDate());   // 현재 달을 1 증가
	    buildCalendar();    // 달력 다시 생성
	}
	
	// input값이 한자리 숫자인 경우 앞에 '0' 붙혀주는 함수
	function leftPad(value) {
	    if (value < 10) {
	        value = "0" + value;
	        return value;
	    }
	    return value;
	}
</script>



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
								<h2 class="mb-0 text-center">연장</h2>
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
							<div
								class="d-flex align-items-center justify-content-between mb-4">
								<h6 class="mb-0">| 연장 기간 선택</h6>
							</div>
							<ul class="mb-0 text-left">
								<li>대여 연장 기간은 2주입니다.</li>
								<li>기본 연장 기간을 초과하여 대여하기를 원하신다면, 하단 달력에서 반납 일자를 선택하여 주시길
									바랍니다.</li>
								<li>최대 연장 기간은 1달 까지입니다.</li>
							</ul>
							<br>
								<div>
							        <table class="Calendar">
							            <thead>
							                <tr>
							                    <td onClick="prevCalendar();" style="cursor:pointer;">&#60;</td>
							                    <td colspan="5">
							                        <span id="calYear"></span>년
							                        <span id="calMonth"></span>월
							                    </td>
							                    <td onClick="nextCalendar();" style="cursor:pointer;">&#62;</td>
							                </tr>
							                <tr>
							                    <td>일</td>
							                    <td>월</td>
							                    <td>화</td>
							                    <td>수</td>
							                    <td>목</td>
							                    <td>금</td>
							                    <td>토</td>
							                </tr>
							            </thead>
							            <tbody>
							            </tbody>
							        </table>
							    </div>
						</div>
						<div class="text-center">
							<button type="button" class="btn btn-outline-primary m-2" onclick ="location='/tylibrary/renew/check'">다음</button>
						</div>
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