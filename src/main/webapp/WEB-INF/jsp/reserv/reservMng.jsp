<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약관리하기</title>
<meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1, user-scalable=yes,initial-scale=1.0" />

<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/media.css" />
<link rel="stylesheet" href="css/calendar.css" />

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	
	calendarInit();
});

/*
달력 렌더링 할 때 필요한 정보 목록 

현재 월(초기값 : 현재 시간)
금월 마지막일 날짜와 요일
전월 마지막일 날짜와 요일
*/

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

function calendarInit() {
	
	// kst 기준 현재시간
	// console.log(thisMonth);
	
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
        	console.log(data.list); // [object Object]
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
    	    	
    	    	var no = '';
    	    	var yes = '';
    	    	
    	    	if(list[i-1] != null || list[i-1] != 'undefined'){
    	    		no = list[i-1].no;
    	    		
    	    		// 현재일 이전일 경우 빈값 넣기
    	    		var date1 = new Date(today.getFullYear() +'-'+ (today.getMonth()+1)+'-'+ today.getDate()+' 00:00:00'); // 2012년 5월 17일 10:20:30
    	    		var date2 = new Date(currentYear+'-'+(currentMonth+1)+'-'+i+' 00:00:00'); // 2012년 5월 17일 10:20:30
    	    		//console.log(i, date1 > date2);
    	    		if(date1 > date2){
    	    			//console.log(date, new Date(currentYear+'-'+(currentMonth+1)+'-'+i));
    	    			yes = ''; //list[i-1].yes;
    	    		}else{
    	    			yes = list[i-1].yes;
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
    				
    				//console.log($(this).attr("class") + ' ' + currentYear+' '+(currentMonth+1)+' '+$(this).text());
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
    				
    		//		console.log($(this).text());
    				var arr = $(this).text().split(' ');
    				if(arr[0].length === 1){
    					day_txt = '0' + arr[0];
    				}else{
    					day_txt = arr[0];
    				}
    				
    				$(".contentSubTitle font").text('[선택한날짜 : ' + currentYear+'-'+month_txt+'-'+day_txt + '], 예약수:'+arr[1]+', 예약가능수:'+arr[2]);
    				
    				// 예약목록 조회하기
    				console.log(currentYear+'-'+month_txt+'-'+day_txt);
    				$.ajax({
    			        url:"/ReservList", 
    			        type: "post",
    			        data: {selDate:currentYear+'-'+month_txt+'-'+day_txt},
    			        success:function(data){
    			        	console.log(data.list); // [object Object]
    			        
    			        	$('#reservMngTable > tbody').empty();
    			        		
    			        	for(let i=0; data.list.length > i; i++){
    			        	//	console.log(data.list[i]);
    			        		var innerHtml = "";
    			        		//	console.log(newNum);
    			        			
			        			innerHtml += '<tr class="reservInfo_'+(i+1)+'">';
			        			innerHtml += '	<td align="center"><input type="text" name="reservTime" id="reservTime" class="text_50"  maxlength="5" value="'+data.list[i].reserv_time+'" /></td>';
			        			innerHtml += '	<td align="center"><input type="text" name="name" id="name" maxlength="10" class="text_50" value="'+data.list[i].name+'" /></td>';
			        			innerHtml += '	<td align="center"><input type="text" name="tel" id="tel" maxlength="13" class="text_70" value="'+data.list[i].tel+'" /></td>';
			        			innerHtml += '	<td align="center">';
			        			innerHtml += '	<input type="button" value="저장" onclick="reservSave(\''+(i+1)+'\');" class="button_small" />';
			        			innerHtml += '	<input type="button" value="삭제" onclick="reservDelete(\''+(i+1)+'\');" class="button_small" />';
			        			innerHtml += '	</td>';
			        			innerHtml += '</tr>';
			        			
			        			
			        			$('#reservMngTable > tbody:last').append(innerHtml);
    			        	}
    			        },
    			        error:function(){
    			            alert("error");
    			        }
    			        
    			    });
    				
    			}
    		});
    	    
    	    // 선택한날짜 초기화
    	    $(".contentSubTitle font").text('[선택한날짜 : 없음]');
    	    
    	    /* 현재월일 경우만 다음달이동 버튼 숨기기
    	    const nowDate = new Date(today.getFullYear()+'-'+(today.getMonth()+1));
    		const curDate = new Date(currentYear+'-'+ (currentMonth+1));
    		
    	    if(!(curDate < nowDate)){
    	    	$(".go-next").hide();	
    	    }else{
    	    	$(".go-next").show();
    	    }*/
        }
        
    });
    
}

// 이전달로 이동
function goPrev() {
    thisMonth = new Date(currentYear, currentMonth - 1, 1);
    renderCalender(thisMonth);
}

//다음달로 이동
function goNext() {
	thisMonth = new Date(currentYear, currentMonth + 1, 1);
    renderCalender(thisMonth); 
}

//예약시간 추가
function addRservTime(){
	
	var trCnt = $('#reservMngTable tbody tr').length;
	//console.log(trCnt);
	
	if(trCnt < 10){
		
		let newNum = 0;
		if(trCnt === 0){
			newNum = 1;
		}else{
			newNum = Number($('#reservMngTable tbody tr:last').attr('class').replace('reservInfo_',''))+1;
		}
		var innerHtml = "";
	//	console.log(newNum);
		
		innerHtml += '<tr class="reservInfo_'+newNum+'">';
		innerHtml += '	<td align="center"><input type="text" name="reservTime" id="reservTime" class="text_50"  maxlength="5" /></td>';
		innerHtml += '	<td align="center"><input type="text" name="name" id="name" maxlength="10" class="text_50" /></td>';
		innerHtml += '	<td align="center"><input type="text" name="tel" id="tel" maxlength="13" class="text_70" /></td>';
		innerHtml += '	<td align="center">';
		innerHtml += '	<input type="button" value="저장" onclick="reservSave(\''+newNum+'\');" class="button_small" />';
		innerHtml += '	<input type="button" value="삭제" onclick="reservDelete(\''+newNum+'\');" class="button_small" />';
		innerHtml += '	</td>';
		innerHtml += '</tr>';
		
		
		$('#reservMngTable > tbody:last').append(innerHtml);
		
	}else{
		alert('최대 10개까지만 가능합니다.');
		return false;
	}
}


// 예약시간 삭제
function reservDelete(num){
	$("#reservMngTable > tbody .reservInfo_"+num).remove();
}

//예약시간 저장
function reservSave(num){

	if($(".contentSubTitle font").text().indexOf('없음') > 0){
		alert('날짜를 선택하세요.');
		return false;
	}
	
	var findTxt = $(".contentSubTitle font").text().indexOf('-');
	var findTxt2 = $(".contentSubTitle font").text().lastIndexOf('-');
	console.log( $(".contentSubTitle font").text().substring(findTxt-4, findTxt2+3) );
	
	//console.log( $(".reservInfo_"+num+" #reservTime").val() );
	var reserv_time = $(".reservInfo_"+num+" #reservTime").val();
	var name = $(".reservInfo_"+num+" #name").val();
	var tel = $(".reservInfo_"+num+" #tel").val();
	
	if(reserv_time == ''){
		alert('예약시간을 입력하세요.');
		$(".reservInfo_"+num+" #reservTime").focus();
		return false;
	}
	
	$.ajax({    
		type : 'post',   
		url : '/ReservMng',           // 요청할 서버url    
	//	async : true,            // 비동기화 여부 (default : true)    
	//	contentType:"application/json",
		dataType : 'text',       // 데이터 타입 (html, xml, json, text 등등)    
		data : {reserv_date:$(".contentSubTitle font").text().substring(findTxt-4, findTxt2+3), reserv_time:reserv_time, name:name, tel:tel},
		success:function(data){
            console.log(data); // [object Object]
         // 캘린더 렌더링
        	renderCalender(thisMonth);
        	$('#reservMngTable > tbody').empty();
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
	
		<p class="contentTitle">예약관리</p>
		
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
		
		<p class="contentSubTitle">예약관리하기 <font>[없음]</font></p>
		
		<div id="tableList">
		
			<table id="reservMngTable">
				<thead>
					<tr>
						<th width="20%">예약시간</th>
						<th width="20%">성명</th>
						<th width="30%">연락처</th>
						<th width="30%">관리</th>
					</tr>
				</thead>
				<tbody>
				<!-- 
				<c:forEach var="num" begin="1" end="5" step="1">
				
					<tr class="reservInfo_<c:out value="${num}"/>">
						<td align="center"><input type="text" name="reservTime" id="reservTime" class="text_50" maxlength="5" /></td>
						<td align="center"><input type="text" name="name" id="name" class="text_50" maxlength="10" /></td>
						<td align="center"><input type="text" name="tel" id="tel" class="text_70" maxlength="13" /></td>
						<td align="center">
							<input type="button" value="저장" onclick="reservSave('<c:out value="${num}"/>');" class="button_small" />
							<input type="button" value="삭제" onclick="reservDelete('<c:out value="${num}"/>');" class="button_small" />
						</td>
					</tr>
					
				</c:forEach>
					 -->
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
							<input type="button" value="추가하기" onclick="addRservTime();" class="button_middle" />
							<input type="button" value="전체저장" onclick="" class="button_middle" />
							<input type="button" value="전체삭제" onclick="" class="button_middle" />
						</td>
					</tr>
				</tfoot>
			</table>
			
		</div>
		
	</div>
	
</div>

<footer></footer>

</body>
</html>