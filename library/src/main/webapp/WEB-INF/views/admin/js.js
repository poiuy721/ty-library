/**
 * 
 */
window.onload = function() { buildCalendar(); }    // 웹 페이지가 로드되면 buildCalendar 실행

let nowMonth = new Date();  // 현재 달을 페이지를 로드한 날의 달로 초기화
let today = new Date();     // 페이지를 로드한 날짜를 저장
today.setHours(0, 0, 0, 0);    // 비교 편의를 위해 today의 시간을 초기화

let month; let year; let day;
day = new Date(today.setDate(today.getDate() + 14));
var arr = new Array();

// 달력 생성 : 해당 달에 맞춰 테이블을 만들고, 날짜를 채워 넣는다.
function buildCalendar() {

	let firstDate = new Date(today.getFullYear(), today.getMonth(), 1);     // 이번달 1일
	let lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);  // 이번달 마지막날



	let tbody_Calendar = document.querySelector(".Calendar > tbody");
	document.getElementById("calYear").innerText = today.getFullYear();             // 연도 숫자 갱신
	document.getElementById("calMonth").innerText = leftPad(today.getMonth() + 1);  // 월 숫자 갱신

	year = document.getElementById("calYear").innerText;
	month = document.getElementById("calMonth").innerText;

	arr.push(year);
	arr.push(month);
	arr.push(leftPad(day));



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
			newDIV.onclick = function() { choiceDate(this, year, month); }
		}
		else if (nowDay.getFullYear() == today.getFullYear() && nowDay.getMonth() == today.getMonth() && nowDay.getDate() == day) {
			newDIV.className = "choiceDay";
			newDIV.onclick = function() { choiceDate(this, year, month); }
		}
		else if (nowDay.getFullYear() == today.getFullYear() && nowDay.getMonth() == today.getMonth() + 1 && nowDay.getDate() == today.getDate()) {
			newDIV.className = "choiceDay";
			newDIV.onclick = function() { choiceDate(this, year, month); }
		}
		else {                                      // 미래인 경우
			newDIV.className = "futureDay";
			newDIV.onclick = function() { choiceDate(this, year, month); }
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
	var one_date = today.getFullYear() + "-" + (today.getMonth() + 2) + "-" + today.getDate();
	var one = new Date(one_date);

	if (due > one) {
		return;
	} else {

		var arr = new Array();
		arr.push(year);
		arr.push(month);
		arr.push(day);

		var url;
		if ('${sort}' == 'rent')
			url = "/tylibrary/rent/due";
		else
			url = "/tylibrary/assign/due";

		$.ajax({
			url: url,
			data: { arr, arr },
			dataType: 'json',
			type: "POST",
			success: function(data) {
				//window.location.reload();
			},
			error: function() {
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






const switching = document.getElementById("switchButton");
const video = document.getElementById("video");
const canvas = document.getElementById("canvas");
const bookinfo = document.getElementById("bookInfo");
let state = '${state}'//반납인지 대여인지
if (!state) {
	alert("잘못된 접근 입니다.");
	console.log("state");
	window.history.back();
}
let i = 0;
let lastResult = "";

//카메라 관련 함수
window.addEventListener('load', function() {
	let selectedDeviceId;
	const codeReader = new ZXing.BrowserMultiFormatReader()
	console.log('ZXing code reader initialized')
	codeReader.listVideoInputDevices().then((videoInputDevices) => {
		let numOfCamera = videoInputDevices.length;
		if (numOfCamera > 1) {
			alert("카메라가 있는 디바이스로 접속해주세요")
			console.log("카메라를 찾지 못했습니다.")
			window.history.back();
		}
		selectedDeviceId = videoInputDevices[i].deviceId
		switching.addEventListener('click', () => {
			i++;
			codeReader.reset();
			if (i == numOfCamera) {
				i = 0;
				selectedDeviceId = videoInputDevices[i].deviceId;
			} else {
				selectedDeviceId = videoInputDevices[i].deviceId;
			}
			codeReader.decodeFromVideoDevice(selectedDeviceId, 'video', (result, err) => {
				if (result) {
					if (state == 'return') {
						if (result.text != lastResult) {
							lastResult = result.text;
							let urlId = "/tylibrary/check-bookInfo?id=" + result.text.split('?id=')[1] + "&state=" + state;
							$.ajax({
								url: urlId,
								type: "GET",
								dataType: "json",
								success: function(data) {
									console.log(data)
									canvas.width = video.offsetWidth;
									canvas.height = video.offsetHeight;
									canvas.getContext('2d').drawImage(video, 0, 0, video.offsetWidth, video.offsetHeight, 0, 0, video.offsetWidth, video.offsetHeight);
									canvas.style.display = "block";
									bookinfo.style.display = "block";
									$("#video-container").css({ "border-color": "green", "border-width": "5px" });
									setTimeout(() => $("#video-container").css({ "border-color": "gray", "border-width": "2px" }), 1000);
									setTimeout(() => canvas.style.display = "none", 1000);
									$("#book_info1").text(data.b_id);
									$("#book_info2").text(data.title);

								},
								error: function() {
									alert("오류발생");
								}
							});
						}
					} else if (state == 'rent') {
						var data = {
							id: result.text.split('?id=')[1],
							state: state;
						};

						var xhr = new XMLHttpRequest();
						var url = '/tylibrary/check-bookInfo';

						// POST 방식으로 요청 설정
						xhr.open('POST', url, true);

						// 요청 헤더 설정
						xhr.setRequestHeader('Content-Type', 'application/json');

						// 요청 완료 후 실행될 콜백 함수
						xhr.onreadystatechange = function() {
							if (xhr.readyState === XMLHttpRequest.DONE) {
								// 요청이 완료되면 여기에 처리 코드 작성
								if (xhr.status === 200) {
									console.log('요청이 성공적으로 전송되었습니다.');
									xhr.responseText
								} else {
									console.error('요청 전송 중 오류가 발생했습니다.');
								}
							}
						};

						// 데이터 전송
						xhr.send(JSON.stringify(data));

					}
				}
				if (err && !(err instanceof ZXing.NotFoundException)) {
					console.error(err);
				}
			})
		})
		switching.click();
	}).catch((err) => { console.error(err) })
})













window.onload = function() { buildCalendar(); }    // 웹 페이지가 로드되면 buildCalendar 실행

let month; let year; let choiceDay; let maxDay; let day; let defaultDay;
let today = new Date();
today.setHours(0, 0, 0, 0);
choiceDay = new Date(today);
choiceDay.setDate(today.getDate() + 14);//현재날 +14이 기본선택이며 최저 선택
defaultDay = new Date(choiceDay);
maxDay = new Date(defaultDay);
maxDay.setDate(defaultDay.getDate() + 14);//기본 선택 +14일이 최대 선택 가능날
let firstDate = new Date(today.getFullYear(), today.getMonth(), 1);
let lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0)
//console.log 빠르게 찍기위해 만든 함수
const c = a => console.log(a);

c(today); c(defaultDay); c(choiceDay); c(maxDay); c(firstDate); c(lastDate);
c("기초설정확인=====오늘.기본.선택.최대.첫날.마지막날=========");
// 달력 생성 : 해당 달에 맞춰 테이블을 만들고, 날짜를 채워 넣는다.
function buildCalendar() {


	let tbody_Calendar = document.querySelector(".Calendar > tbody");
	document.getElementById("calYear").innerText = firstDate.getFullYear();             // 연도 숫자 갱신
	document.getElementById("calMonth").innerText = leftPad(firstDate.getMonth() + 1);  // 월 숫자 갱신

	year = document.getElementById("calYear").innerText;
	month = document.getElementById("calMonth").innerText;

	while (tbody_Calendar.rows.length > 0) {                        // 이전 출력결과가 남아있는 경우 초기화
		tbody_Calendar.deleteRow(tbody_Calendar.rows.length - 1);
	}

	let nowRow = tbody_Calendar.insertRow();        // 첫번째 행 추가           

	for (let j = 0; j < firstDate.getDay(); j++) {  // 이번달 1일의 요일만큼
		let nowColumn = nowRow.insertCell();        // 열 추가
	}

	for (let nowDay = new Date(firstDate); nowDay <= lastDate; nowDay.setDate(nowDay.getDate() + 1)) {   // day는 날짜를 저장하는 변수, 이번달 마지막날까지 증가시키며 반복  

		let nowColumn = nowRow.insertCell();        // 새 열을 추가하고

		let newDIV = document.createElement("p");
		newDIV.innerHTML = leftPad(nowDay.getDate());        // 추가한 열에 날짜 입력
		nowColumn.appendChild(newDIV);

		//선택가능한 날 

		//선택 불가능한 날 = 주말 + 과거 + maxDay 이후
		if (nowDay.getDay() == 6) {                 // 토요일인 경우
			nowRow = tbody_Calendar.insertRow();
			newDIV.className = "disable";// 새로운 행 추가
		} else if (nowDay.getDay() == 0) {
			newDIV.className = "disable";
		} else if (nowDay < today) {
			newDIV.className = "disable";
		} else if (nowDay > maxDay) {
			newDIV.className = "disable";
		} else if (maxDay >= nowDay && nowDay > today) {
			newDIV.className = "selectable";
			newDIV.onclick = function() { choiceDate(this, year, month); }
		}

		if (nowDay > today && nowDay < choiceDay) {
			newDIV.classList.add("rentperiod");
		} else if (nowDay - today == 0) { 	// 오늘인 경우           
			newDIV.className = "today";
		} else if (choiceDay - nowDay == 0) {  //choiceDay 설정          
			newDIV.className = "choiceDay";
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
	choiceDay = new Date(year + "-" + month + "-" + newDIV.innerHTML)
	choiceDay.setHours(0, 0, 0, 0);
	console.log(choiceDay);
	c("선택한날")
	// 선택한 연장 날짜
	buildCalendar();
}

// 이전달 버튼 클릭
function MoveMonth(a) {
	firstDate.setMonth(firstDate.getMonth() + a);
	lastDate.setMonth(lastDate.getMonth() + a);


	if (firstDate > maxDay) {
		firstDate.setMonth(firstDate.getMonth() - a);
		lastDate.setMonth(lastDate.getMonth() - a);
		return "";
	}
	if (lastDate < today) {
		firstDate.setMonth(firstDate.getMonth() - a);
		lastDate.setMonth(lastDate.getMonth() - a);
		return "";
	}
	buildCalendar();
}

// input값이 한자리 숫자인 경우 앞에 '0' 붙혀주는 함수
function leftPad(value) {

	if (value < 10) {
		value = "0" + value;
		return value;
	}
	return value;
}

















document.getElementById("sing-up").addEventListener("click", function() {
	let formData = new FormData($('#sing-up-form')[0]);
	console.log(formData);
	$.ajax({
		url: 'user-sign-up',
		type: 'POST',
		data: formData,
		processData: false,
		contentType: false,
		success: function(response) {
			// 성공 시 처리할 내용
			let content = '';
			for (var i = 0; i < data.length; i++) {
				content += `
        <tr>
            <td>${num}</td>
            <td>${data[i].title}</td>
            <td>${data[i].b_id}</td>
            <td><button type="button" class="btn btn-sm btn-danger rounded-pill deleteBtn" data-b_id="${data[i].b_id}">삭제</button></td>
        </tr>
    `;
				num++;
			}
		},
		error: function(xhr, status, error) {
			// 실패 시 처리할 내용
			alert("에러: " + error);
		}
	});
});
