<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 예약하기</title>
<meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1, user-scalable=yes,initial-scale=1.0" />

<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/media.css" />
<link rel="stylesheet" href="css/calendar.css" />

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	
	calendarInit();
});

//날짜 정보 가져오기
var date = new Date(); // 현재 날짜(로컬 기준) 가져오기
var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000); // uct 표준시 도출
var kstGap = 9 * 60 * 60 * 1000; // 한국 kst 기준시간 더하기
var today = new Date(utc + kstGap); // 한국 시간으로 date 객체 만들기(오늘)

var thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());
// 달력에서 표기하는 날짜 객체


var currentYear = thisMonth.getFullYear(); // 달력에서 표기하는 연
var currentMonth = thisMonth.getMonth(); // 달력에서 표기하는 월
var currentDate = thisMonth.getDate(); // 달력에서 표기하는 일

/*
달력 렌더링 할 때 필요한 정보 목록 

현재 월(초기값 : 현재 시간)
금월 마지막일 날짜와 요일
전월 마지막일 날짜와 요일
*/

function calendarInit() {

	// 캘린더 렌더링
	renderCalender(thisMonth);
}


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
    	    	    	// 선택한날짜 색상변경
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
        				
        				$(".contentSubTitle font").text('[선택한날짜 : ' + currentYear+'-'+month_txt+'-'+day_txt + ']');
        				$(".reservCntChk .no").text('예약수 : '+arr[1]);
        				if(arr[2] != ''){
        					$(".reservCntChk .yes").text(' 예약가능수 : '+arr[2]);    					
        				}else{
        					$(".reservCntChk .yes").text('');
        				}
        				
        				// 예약목록 조회하기
        				$.ajax({
        			        url:"/ReservList", 
        			        type: "post",
        			        data: {selDate:currentYear+'-'+month_txt+'-'+day_txt},
        			        success:function(data){
        			        	console.log(data.list); // [object Object]
        			        
        			        	$('#reservTable > tbody').empty();
        			        		
        			        	for(let i=0; data.list.length > i; i++){
        			        	//	console.log(data.list[i]);
        			        		var innerHtml = "";
        			        		//	console.log(newNum);
        			        			
    			        			innerHtml += '<tr class="reservInfo_'+(i+1)+'">';
    			        			innerHtml += '	<td align="center"><div id="reservTime">'+data.list[i].reserv_time+'</div></td>';
    			        			innerHtml += '	<td align="center"><input type="text" name="name" id="name" maxlength="10" class="text_50" value="'+data.list[i].name+'" /></td>';
    			        			innerHtml += '	<td align="center"><input type="text" name="tel" id="tel" maxlength="13" class="text_70" value="'+data.list[i].tel+'" /></td>';
    			        			innerHtml += '	<td align="center">';
    			        			innerHtml += '	<input type="button" value="저장" onclick="reservSave(\''+(i+1)+'\');" class="button_small" />';
    			        			if(data.list[i].name != '' || data.list[i].tel != ''){
    			        				innerHtml += '	<input type="button" value="취소" onclick="reservDelete(\''+(i+1)+'\');" class="button_small" />';	
    			        			}
    			        			innerHtml += '	</td>';
    			        			innerHtml += '</tr>';
    			        			
    			        			if(data.list[i].name == '' && data.list[i].tel == ''){
    			        				$('#reservTable > tbody:last').append(innerHtml);
    			        			}
        			        	}
        			        },
        			        error:function(){
        			            alert("error");
        			        }
        			        
        			    });
    	    		}
    	    	    
    			}
    		});
         
    	 	// 선택한날짜 초기화
    	    $(".contentSubTitle font").text('[선택한날짜 : 없음]');
    	 
    	 	// 예약목록 지우기
    	    $('#reservTable > tbody').empty();
        }
	});
	
    
}

//이전달로 이동
function goPrev() {
    thisMonth = new Date(currentYear, currentMonth - 1, 1);
    renderCalender(thisMonth);
}

//다음달로 이동
function goNext() {
	thisMonth = new Date(currentYear, currentMonth + 1, 1);
    renderCalender(thisMonth); 
}

//예약시간 저장
function reservSave(num){

	if($(".contentSubTitle font").text().indexOf('없음') > 0){
		alert('날짜를 선택하세요.');
		return false;
	}
	
	var findTxt = $(".contentSubTitle font").text().indexOf('-');
	var findTxt2 = $(".contentSubTitle font").text().lastIndexOf('-');
	
	var reserv_time = $(".reservInfo_"+num+" #reservTime").text();
	console.log(reserv_time);
	var name = $(".reservInfo_"+num+" #name").val();
	var tel = $(".reservInfo_"+num+" #tel").val();
	
	if(name == ''){
		alert('예약자 성명을 입력하세요.');
		$(".reservInfo_"+num+" #name").focus();
		return false;
	}
	
	if(tel == ''){
		alert('예약자 연락처를 입력하세요.');
		$(".reservInfo_"+num+" #tel").focus();
		return false;
	}
	
	$.ajax({    
		type : 'post',   
		url : '/ReservMng',           // 요청할 서버url    
		dataType : 'text',       // 데이터 타입 (html, xml, json, text 등등)    
		data : {reserv_date:$(".contentSubTitle font").text().substring(findTxt-4, findTxt2+3), reserv_time:reserv_time, name:name, tel:tel},
		success:function(data){
         	// 캘린더 렌더링
        	renderCalender(thisMonth);
        },   
		error : function(request, status, error) { // 결과 에러 콜백함수        
			console.log('error!!!'+error)    
			}
	})
}

</script>

</head>
<body>

<jsp:include page="/header"></jsp:include>

<div id="main">

	<div id="content_all">
	
		<p class="contentTitle">사용자예약</p>
		
		<hr/>
		
		<div id="tableList">
			<div class="sec_cal">
		  		<div class="cal_nav">
		    		<a href="javascript:goPrev();" class="nav-btn go-prev">prev</a>
		    		<div class="year-month"></div>
		    		<a href="javascript:goNext();" class="nav-btn go-next">next</a>
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
		</div>
		
		<hr/>
		
		<p class="contentSubTitle">예약가능목록 <font>[없음]</font></p>
		
		<div id="tableList">
		
			<table id="reservTable">
				<thead>
					<tr>
						<th width="25%">예약시간</th>
						<th width="25%">이름</th>
						<th width="25%">연락처</th>
						<th width="25%">예약하기</th>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
			
		</div>
		
		<input type="button" value="예약관리" class="button" onclick="location.href='/ReservMng'"/>
		
	</div>
	
</div>

<footer></footer>

</body>
</html>