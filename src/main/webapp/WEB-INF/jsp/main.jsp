<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환영합니다.</title>
<meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1, user-scalable=yes,initial-scale=1.0" />
<!-- user-scalable:사용자단말의확대사용유무 -->

<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/media.css" />
<link rel="stylesheet" href="css/calendar.css" />
<!-- 
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.0/chart.umd.min.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	let arr = ['1.jpg','2.jpg','3.jpg','4.jpg'];
	let i = 0;
	setInterval(function(){
	    //   console.log(arr.length);
	     //  $("#content2 .banner img").attr('src','/images/'+arr[i]+'.jpg');
	       $("#content2 .banner img").css('opacity','0.7').stop().attr('src','/images/slide/'+arr[i]).animate({opacity:1},600);
	       $("#content1 .banner img").css('opacity','0.7').stop().attr('src','/images/slide/'+arr[i]).animate({opacity:1},600);
	       i++;
	       if(i==arr.length){
	    	   i=0;
	       }
	    },3000);

	calendarInit();
});


/*
달력 렌더링 할 때 필요한 정보 목록 

현재 월(초기값 : 현재 시간)
금월 마지막일 날짜와 요일
전월 마지막일 날짜와 요일
*/

function calendarInit() {
	
	// 날짜 정보 가져오기
	var date = new Date(); // 현재 날짜(로컬 기준) 가져오기
	var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000); // uct 표준시 도출
	var kstGap = 9 * 60 * 60 * 1000; // 한국 kst 기준시간 더하기
	var today = new Date(utc + kstGap); // 한국 시간으로 date 객체 만들기(오늘)
	
	var thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());
	// 달력에서 표기하는 날짜 객체
	
	
	var currentYear = thisMonth.getFullYear(); // 달력에서 표기하는 연
	var currentMonth = thisMonth.getMonth(); // 달력에서 표기하는 월
	var currentDate = thisMonth.getDate(); // 달력에서 표기하는 일
	
	// kst 기준 현재시간
	// console.log(thisMonth);
	
	// 캘린더 렌더링
	renderCalender(thisMonth);
	
	function renderCalender(thisMonth) {
	
		var thisMonthParam = String( thisMonth.getMonth()+1 );
		if(thisMonthParam.length === 1){
			thisMonthParam = '0' + thisMonthParam;
		}
		
		var list = null;
		
		// 선택한 월의 예약목록 조회
		$.ajax({
	        url:"/ReservList", // HelloServlet.java로 접근
	        type: "get", // GET 방식
	        data:{curDate: thisMonth.getFullYear() + '-' + thisMonthParam},
	        success:function(data){
	        //	console.log(data.list); // [object Object]
	        	list = data.list;
	        },
	        error:function(){
	            alert("error");
	        },
	        complete : function()
	        {
	        	// 렌더링을 위한 데이터 정리
	    	    currentYear = thisMonth.getFullYear();
	    	    currentMonth = thisMonth.getMonth();
	    	    currentDate = thisMonth.getDate();
	    	
	    	    // 이전 달의 마지막 날 날짜와 요일 구하기
	    	    var startDay = new Date(currentYear, currentMonth, 0);
	    	    var prevDate = startDay.getDate();
	    	    var prevDay = startDay.getDay();
	    	
	    	    // 이번 달의 마지막날 날짜와 요일 구하기
	    	    var endDay = new Date(currentYear, currentMonth + 1, 0);
	    	    var nextDate = endDay.getDate();
	    	    var nextDay = endDay.getDay();
	    	
	    	    // console.log(prevDate, prevDay, nextDate, nextDay);
	    	
	    	    // 현재 월 표기
	    	    $('.year-month').text(currentYear + '.' + (currentMonth + 1));
	    	
	    	    // 렌더링 html 요소 생성
	    	    calendar = document.querySelector('.dates')
	    	    calendar.innerHTML = '';
	    	    
	    	    // 지난달
	    	    for (var i = prevDate - prevDay + 1; i <= prevDate; i++) {
	    	        calendar.innerHTML = calendar.innerHTML + '<div class="day prev disable">' + i + '</div>'
	    	    }
	    	    // 이번달
	    	    for (var i = 1; i <= nextDate; i++) {
	    	        //calendar.innerHTML = calendar.innerHTML + '<div class="day current">' + i + '</div>'
	    	    	var no = '';
	    	    	var yes = '';
	    	    	
	    	    	if(list[i-1] != null || list[i-1] != 'undefined'){
	    	    		
	    	    	 	var toDayMonth = today.getMonth()+1;
	    	    	 	if( String(toDayMonth).length == 1){
	    	    	 		toDayMonth = '0' + toDayMonth;
	    	    	 	}
	    	    	 	
	    	    	 	var toDayDay = today.getDate();
	    	    	 	if( String(toDayDay).length == 1){
	    	    	 		toDayDay = '0' + toDayDay;
	    	    	 	}
	    	    	 	
	    	    		// 현재일 이전일 경우 빈값 넣기
	    	    		var date1 = new Date(today.getFullYear() +'-'+ toDayMonth +'-'+ toDayDay+' 12:00:00.000'); // 2012년 5월 17일 10:20:30
	    	    		
	    	    		var currentMonth2 = (currentMonth+1);
	    	    		if( String(currentMonth2).length == 1){
	    	    			currentMonth2 = '0' + currentMonth2;
	    	    		}
	    	    		var currentDay = i;
	    	    		if( String(currentDay).length == 1){
	    	    			currentDay = '0' + currentDay;
	    	    		}
	    	    		
	    	    		var date2 = new Date(currentYear+'-'+currentMonth2+'-'+currentDay+' 12:00:00.000'); // 2012년 5월 17일 10:20:30
	    	    		
	    	    		var beforeDate = new Date(date1).toISOString();
	    	    	    var afterDate = new Date(date2).toISOString();
						// console.log(beforeDate+' // '+afterDate);
	    	    	    if(beforeDate > afterDate){
	    	    			yes = ''; 
	    	    			no = '';
	    	    		}else{
	    	    			yes = list[i-1].yes;
	    	    			no = list[i-1].no;
	    	    		}
	    	    		
	    	    	}
	    	    	
	    	        calendar.innerHTML = calendar.innerHTML + '<div class="day current">' + i + ' ' +
	    	        	'<div style="margin:2px;"><div style="font-size:12px;color:pink;">'+no+' '+
	    	        	'</div><div style="font-size:12px;color:green;">'+yes+'</div></div>' +
	    	        	'</div>';
	    	    }
	    	    // 다음달
	    	    for (var i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay); i++) {
	    	        calendar.innerHTML = calendar.innerHTML + '<div class="day next disable">' + i + '</div>'
	    	    }
	    	
	    	    // 오늘 날짜 표기
	    	    if (today.getMonth() == currentMonth) {
	    	        todayDate = today.getDate();
	    	        var currentMonthDate = document.querySelectorAll('.dates .current');
	    	        currentMonthDate[todayDate -1].classList.add('today');
	    	    }
	    	    
	    	 // 날짜선택 이벤트
	    	    $('.day').on('click', function() {
	    			
	    			if($(this).attr("class").indexOf('prev') > 0 ){					// 이전달이동
	    				thisMonth = new Date(currentYear, currentMonth - 1, 1);
	    			    renderCalender(thisMonth);
	    			}else if($(this).attr("class").indexOf('next') > 0 ){			// 다음달이동
	    				thisMonth = new Date(currentYear, currentMonth + 1, 1);
	    			    renderCalender(thisMonth); 
	    			}else{	// 현재달 선택
	    				
	    				// 오늘 이후만 실행
	    				var toDayMonth = today.getMonth()+1;
	    	    	 	if( String(toDayMonth).length == 1){
	    	    	 		toDayMonth = '0' + toDayMonth;
	    	    	 	}
	    	    	 	
	    	    	 	var toDayDay = today.getDate();
	    	    	 	if( String(toDayDay).length == 1){
	    	    	 		toDayDay = '0' + toDayDay;
	    	    	 	}
			
	    	    	 	// 오늘날짜
	    	    		var date11 = new Date(today.getFullYear() +'-'+ toDayMonth +'-'+ toDayDay+' 12:00:00.000'); // 2012년 5월 17일 10:20:30
	    	    		
	    	    		var currentMonth2 = (currentMonth+1);
	    	    		if( String(currentMonth2).length == 1){
	    	    			currentMonth2 = '0' + currentMonth2;
	    	    		}
	    	    		
	    	    		var day_txt = '';
	    	    		var arr = $(this).text().split(' ');
	    				if(arr[0].length === 1){
	    					day_txt = '0' + arr[0];
	    				}else{
	    					day_txt = arr[0];
	    				}
	    				
	    	    		// 선택한 날짜
	    	    		var date22 = new Date(currentYear+'-'+currentMonth2+'-'+day_txt+' 12:00:00.000'); // 2012년 5월 17일 10:20:30
	    	    		var beforeDate2 = new Date(date11).toISOString();
	    	    	    var afterDate2 = new Date(date22).toISOString();
	    	    	    
	    	    	    if(beforeDate2 <= afterDate2){
	    	    	    	// 선택한날자 색상변경
	        				$(".day").removeClass("selday");
	        				$(this).addClass("selday");
	        				
	        				var month_txt = '';
	        				var day_txt = '';
	        				
	        				if(String(currentMonth+1).length === 1){
	        					month_txt = '0' + (currentMonth+1);
	        				}else{
	        					month_txt = (currentMonth+1);
	        				}
	        				
	        				var arr = $(this).text().split(' ');
	        				if(arr[0].length === 1){
	        					day_txt = '0' + arr[0];
	        				}else{
	        					day_txt = arr[0];
	        				}
	        				
	        				$("#content_2 .content").text('[선택한날자 : ' + currentYear+'-'+month_txt+'-'+day_txt + ']');
	        				$("#content_2 .reservCnt").text('예약수 : '+arr[1]+' 예약가능수 : '+arr[2]);
	        				
	    	    		}
	    	    	    
	    			}
	    		});
	    	 
	    	    $("#content_2 .content").text('[선택한날자 : 없음]');
				$("#content_2 .reservCnt").text('');
	        }
		});
	    
	}
	
	// 이전달로 이동
	$('.go-prev').on('click', function() {
	    thisMonth = new Date(currentYear, currentMonth - 1, 1);
	    renderCalender(thisMonth);
	});
	
	// 다음달로 이동
	$('.go-next').on('click', function() {
	    thisMonth = new Date(currentYear, currentMonth + 1, 1);
	    renderCalender(thisMonth); 
	});
	
}

function ajaxGetString(){
	
	$.ajax({    
		type : 'get',           // 타입 (get, post, put 등등)    
		url : '/AjaxTest',           // 요청할 서버url    
	//	async : true,            // 비동기화 여부 (default : true)    
		contentType:"application/json",
		dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)    
		data : {no:"한글명", age:"22222"},
		success:function(data){
            console.log(data); // [object Object]
            console.log(JSON.stringify(data)); // {"str":"안녕하세요"}
            console.log(data.str); // 안녕하세요
        },   
		error : function(request, status, error) { // 결과 에러 콜백함수        
			console.log('error!!!'+error)    
			}
	})
}

function ajaxPostObject(){
	
	$.ajax({
        url:"./AjaxTest", // HelloServlet.java로 접근
        type: "post", // GET 방식
        success:function(data){
        	console.log(data); // [object Object]
        	console.log(JSON.stringify(data)); // {"human":{"age":24,"name":"홍길동"}}
        	console.log(data.human.name); // 홍길동
        	console.log(data.human.age); // 24
        },
        error:function(){
            alert("error");
        }
        
    });
}

function ajaxGetList(){
	
	$.ajax({
        url:"./AjaxTest2", // HelloServlet.java로 접근
        type: "get", // GET 방식
        success:function(data){
        	console.log(data); // [object Object]
        	console.log(JSON.stringify(data)); // {"list":[{"age":24,"name":"홍길동"},{"age":16,"name":"성춘향"},{"age":22,"name":"홍두께"}]}
        	console.log(data.list[1].name); // 성춘향
        	console.log(data.list[0].age); // 24
        },
        error:function(){
            alert("error");
        }
        
    });
}

function ajaxPostHashMap(){
	
	$.ajax({
        url:"./AjaxTest2", // HelloServlet.java로 접근
        type: "post", // GET 방식
        success:function(data){
        	console.log(data); // [object Object]
        	console.log(JSON.stringify(data)); // {"map":{"mylist":[{"age":24,"name":"홍길동"},{"age":16,"name":"성춘향"}],"title":"안녕하세요"}}
        	console.log(data.map.mylist[1].name); // 성춘향
        	console.log(data.map.title); // 안녕하세요
        },
        error:function(){
            alert("error");
        }
        
    });
}

</script>

</head>
<body>

<jsp:include page="/header"></jsp:include>

<div id="main">

	<!-- calendar -->
	<div id="content_2">
		<p class="title">예약현황</p>
		<div class="sec_cal">
	  		<div class="cal_nav">
	    		<a href="javascript:;" class="nav-btn go-prev">prev</a>
	    		<div class="year-month"></div>
	    		<a href="javascript:;" class="nav-btn go-next">next</a>
	  		</div>
		  	<div class="cal_wrap">
		    	<div class="days">
		      		<div class="day">MON</div>
		      		<div class="day">TUE</div>
		      		<div class="day">WED</div>
		      		<div class="day">THU</div>
		      		<div class="day">FRI</div>
		      		<div class="day">SAT</div>
		      		<div class="day">SUN</div>
		    	</div>
		    	<div class="dates"></div>
		  	</div>
		</div>
		<p class="content">예약건수 : 999, 예약가능건수 : 999</p>
		<p class="reservCnt"></p>
		<p><a href="/UserReserv">[예약하러가기]</a></p>
		
	</div>
	
	<div id="content2">
		<div class="banner">
			<img src="/images/test.jpg" alt="">
		</div>
	</div>
	
	<div id="content1">
		
			<div class="picture">
				<a href="/ViewDetail">
					<img src="/images/green.jpg" />
				</a>
			</div>
			<div class="title">
				<p>2024-03-06, 홍길동</p>
				<p>적어도 하나의 JAR가 TLD들을 찾기 위해 스캔되었으나 아무 것도 찾지 못했습니다. 스캔했으나 TLD가 없는 JAR들의 전체 목록을 보시려면, 로그 레벨을 디버그 레벨로 설정하십시오.</p>
			</div>
	</div>
	
	<!-- 공지사항 -->
	<div id="content2">
		<div class="board_list">
			<p>공지사항 <a href="BoardList"><img src="/images/plus_icon.png" width="19" height="19" /></a></p>
			<hr/>
			<c:forEach items="${boardList}" var="boardList" varStatus="status">
			<div class="title_list">
				<div>
					<a href="/BoardView?seq=<c:out value="${boardList.seq}" />" >
						<c:out value="${boardList.title}" />
					</a></div>
				<div>
					[<c:out value="${boardList.regDate}" />]
				</div>
			</div>
			</c:forEach>
			
		</div>
	</div>
	
	<div id="content1">
		<p>차트</p>
		<canvas id="myChart"></canvas>
	</div>
	
	<div id="content1">
		<div class="picture">
				<img src="/images/RW193CV.jpg" />
			</div>
			<div class="title">
			<p>2024-03-06, 관리자</p>
			<p>알고리즘을 사용하여, 세션 ID를 생성하기 위한 SecureRandom 객체를 생성하는데, [189] 밀리초가 소요됐습니다.</p>
			</div>
	</div>
	
	<div id="content1">
		<div class="banner">
			<img src="/images/test.jpg" alt="">
		</div>
	</div>
	
	
	<div id="content1">
		
			<div class="picture">
				<a href="/ViewDetail">
					<img src="/images/green.jpg" />
				</a>
			</div>
			<div class="title">
				<p>2024-03-06, 홍길동</p>
				<p>적어도 하나의 JAR가 TLD들을 찾기 위해 스캔되었으나 아무 것도 찾지 못했습니다. 스캔했으나 TLD가 없는 JAR들의 전체 목록을 보시려면, 로그 레벨을 디버그 레벨로 설정하십시오.</p>
			</div>
		
	</div>
	<div id="content1">
		<div class="picture">
				<img src="/images/RW193CV.jpg" />
			</div>
			<div class="title">
			<p>2024-03-06, 관리자</p>
			<p>알고리즘을 사용하여, 세션 ID를 생성하기 위한 SecureRandom 객체를 생성하는데, [189] 밀리초가 소요됐습니다.</p>
			</div>
	</div>
	<div id="content1">
		<ul>
			<li><a href="javascript:ajaxGetString();">Ajax String</a></li>
			<li><a href="javascript:ajaxPostObject();">Ajax Object</a></li>
			<li><a href="javascript:ajaxGetList();">Ajax list</a></li>
			<li><a href="javascript:ajaxPostHashMap();">Ajax HashMap</a></li>
		</ul>
	</div>
	<div id="content_2"></div>
	<div id="content1"></div>
	<div id="content1"></div>
	<div id="content1"></div>
	<div id="content1"></div>
</div>

<footer></footer>

<script type="text/javascript">
let myCt = document.getElementById('myChart');

let myChart = new Chart(myCt, {
  type: 'bar',
  data: {
    labels: ['2020', '2021', '2022', '2023', '2024'],
    datasets: [
      {
        label: 'Dataset',
        data: [10,20,30,40,50],
      }
    ]
  },
});
</script>

</body>
</html>