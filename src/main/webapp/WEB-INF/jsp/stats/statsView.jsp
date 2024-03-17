<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>통계보기</title>
<meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1, user-scalable=yes,initial-scale=1.0" />

<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/media.css" />

<script src="https://code.jquery.com/jquery-3.6.0.js"></script> 

<script type="text/javascript" src="/chart/node_modules/chart.js/dist/chart.umd.js"></script>
<script type="text/javascript" src="/js/Utils.js"></script>

</head>
<body>

<jsp:include page="/header"></jsp:include>

<div id="main">

	<div id="content_all">
	
		<p class="contentTitle">통계보기</p>
		
		<hr/>
		
		<!-- Line Chart2 -->
		<div class="stats">
			<div class="contentThirdTitle">Line Chart</div>
			<canvas id="lineChart2"></canvas>
			<script>
		
				$.ajax({
			        url:"./StatsView", // HelloServlet.java로 접근
			        type: "post", // GET 방식
			        success:function(data){
			        	
			        	const lineChart2 = document.getElementById('lineChart2');
			    		
						const DATA_COUNT10 = 8;
						const NUMBER_CFG9 = {count: DATA_COUNT10, min: 0, max: 100};

						const labels8 = data.labels;
						console.log(Samples.utils.transparentize(window.CHART_COLORS.red, 0.5));
						
						new Chart(lineChart2, {
						    type: 'line',
						    data: {
						    	  labels: labels8,
						    	  datasets: data.list 
						    	}
						    ,
						    options: {
						        responsive: true,
						        plugins: {
						          legend: {
						            position: 'top',
						          },
						          title: {
						            display: true,
						            text: '한달간 접속자수 통계'
						          }
						        }
						      }
						  });
						
			        },
			        error:function(){
			            alert("error");
			        }
			        
			    });
			
				
			
			</script>
			
		</div>
		
		<div class="stats">
			<div class="contentThirdTitle">막대차트</div>
			<canvas id="myChart"></canvas>
			<script>
			  const ctx = document.getElementById('myChart');
			
			  new Chart(ctx, {
			    type: 'bar',
			    data: {
			      labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
			      datasets: [{
			        label: '# of Votes',
			        data: [12, 19, 3, 5, 2, 3],
			        borderWidth: 1
			      }]
			    },
			    options: {
			      scales: {
			        y: {
			          beginAtZero: true
			        }
			      }
			    }
			  });
			</script>
		</div>
		
		<!-- Stacked bar/line -->
		<div class="stats">
			<div class="contentThirdTitle">Stacked bar/line</div>
			<canvas id="stackedBarLine"></canvas>
			<script>
		
				const stackedBarLine = document.getElementById('stackedBarLine');
		
				const DATA_COUNT8 = 7;
				const NUMBER_CFG8 = {count: DATA_COUNT8, min: 0, max: 100};

				const labels7 = Samples.utils.months({count: 7});
				
				new Chart(stackedBarLine, {
				    type: 'line',
				    data: {
				    	  labels: labels7,
				    	  datasets: [
				    	    {
				    	      label: 'Dataset 1',
				    	      data: Samples.utils.numbers(NUMBER_CFG8),
				    	      borderColor: window.CHART_COLORS.red,
				    	      backgroundColor: Samples.utils.transparentize(window.CHART_COLORS.red, 0.5),
				    	      stack: 'combined',
				    	      type: 'bar'
				    	    },
				    	    {
				    	      label: 'Dataset 2',
				    	      data: Samples.utils.numbers(NUMBER_CFG8),
				    	      borderColor: window.CHART_COLORS.blue,
				    	      backgroundColor: Samples.utils.transparentize(window.CHART_COLORS.blue, 0.5),
				    	      stack: 'combined'
				    	    }
				    	  ]
				    	}
				    ,
				    options: {
				        plugins: {
				            title: {
				              display: true,
				              text: 'Chart.js Stacked Line/Bar Chart'
				            }
				          },
				          scales: {
				            y: {
				              stacked: true
				            }
				          }
				        }
				  });
			
			</script>
			
		</div>
		
		<div class="stats">
			<div class="contentThirdTitle">막대차트</div>
			<canvas id="barChart"></canvas>
			<script>
		
			const barChart = document.getElementById('barChart');
			const labels = Samples.utils.months({count: 7});
			
			new Chart(barChart, {
			    type: 'bar',
			    data: {
			    	  labels: labels,
			    	  datasets: [
			    	    {
			    	      label: 'Dataset 1',
			    	      data: labels.map(() => {
			    	        return [Samples.utils.rand(-100, 100), Samples.utils.rand(-100, 100)];
			    	      }),
			    	      backgroundColor: window.CHART_COLORS.red,
			    	    },
			    	    {
			    	      label: 'Dataset 2',
			    	      data: labels.map(() => {
			    	        return [Samples.utils.rand(-100, 100), Samples.utils.rand(-100, 100)];
			    	      }),
			    	      backgroundColor: window.CHART_COLORS.blue,
			    	    },
			    	  ]
			    	}
			    ,
			    options: {
			        responsive: true,
			        plugins: {
			          legend: {
			            position: 'top',
			          },
			          title: {
			            display: true,
			            text: 'Chart.js Floating Bar Chart'
			          }
			        }
			      }
			  });
			
			</script>
			
		</div>
		
		<!-- Horizontal Bar Chart -->
		<div class="stats">
			<div class="contentThirdTitle">Horizontal Bar Chart</div>
			<canvas id="horizontalBarChart"></canvas>
			<script>
				
				const horizontalBarChart = document.getElementById('horizontalBarChart');
			
				const DATA_COUNT4 = 7;
				const NUMBER_CFG2 = {count: DATA_COUNT4, min: -100, max: 100};
	
				const labels3 = Samples.utils.months({count: 7});
				
				
				new Chart(horizontalBarChart, {
				    type: 'bar',
				    data: {
					  labels: labels3,
					  datasets: [
					    {
					      label: 'Dataset 1',
					      data: Samples.utils.numbers(NUMBER_CFG2),
					      borderColor: window.CHART_COLORS.red,
					      backgroundColor: Samples.utils.transparentize(window.CHART_COLORS.red, 0.5),
					    },
					    {
					      label: 'Dataset 2',
					      data: Samples.utils.numbers(NUMBER_CFG2),
					      borderColor: window.CHART_COLORS.blue,
					      backgroundColor: Samples.utils.transparentize(window.CHART_COLORS.blue, 0.5),
					    }
					  ]
					},
					options : {
					    indexAxis: 'y',
					    // Elements options apply to all of the options unless overridden in a dataset
					    // In this case, we are setting the border of each horizontal bar to be 2px wide
					    elements: {
					      bar: {
					        borderWidth: 2,
					      }
					    },
					    responsive: true,
					    plugins: {
					      legend: {
					        position: 'right',
					      },
					      title: {
					        display: true,
					        text: 'Chart.js Horizontal Bar Chart'
					      }
					    }
					  }
				});
			</script>
		</div>
		
		
		<div class="stats">
			<div class="contentThirdTitle">라인차트</div>
			<canvas id="lineChart"></canvas>
			<script>
		
				const lineChart = document.getElementById('lineChart');
		
				const DATA_COUNT = 12;
				const labels2 = [];
				for (let i = 0; i < DATA_COUNT; ++i) {
				  labels2.push(i.toString());
				}
				const datapoints = [0, 20, 20, 60, 60, 120, NaN, 180, 120, 125, 105, 110, 170];
				
				new Chart(lineChart, {
				    type: 'line',
				    data: {
				    	  labels: labels2,
				    	  datasets: [
				    	    {
				    	      label: 'Cubic interpolation (monotone)',
				    	      data: datapoints,
				    	      borderColor: window.CHART_COLORS.red,
				    	      fill: false,
				    	      cubicInterpolationMode: 'monotone',
				    	      tension: 0.4
				    	    }, {
				    	      label: 'Cubic interpolation',
				    	      data: datapoints,
				    	      borderColor: window.CHART_COLORS.blue,
				    	      fill: false,
				    	      tension: 0.4
				    	    }, {
				    	      label: 'Linear interpolation (default)',
				    	      data: datapoints,
				    	      borderColor: window.CHART_COLORS.green,
				    	      fill: false
				    	    }
				    	  ]
				    	}
				    ,
				    options: {
				        responsive: true,
				        plugins: {
				          title: {
				            display: true,
				            text: 'Chart.js Line Chart - Cubic interpolation mode'
				          },
				        },
				        interaction: {
				          intersect: false,
				        },
				        scales: {
				          x: {
				            display: true,
				            title: {
				              display: true
				            }
				          },
				          y: {
				            display: true,
				            title: {
				              display: true,
				              text: 'Value'
				            },
				            suggestedMin: -10,
				            suggestedMax: 200
				          }
				        }
				      }
				  });
			
			</script>
			
		</div>
		
		<!-- Progressive Line -->
		<div class="stats">
			<div class="contentThirdTitle">Progressive Line</div>
			<canvas id="progressiveLine"></canvas>
			<script>
		
				const progressiveLine = document.getElementById('progressiveLine');
		
				const data3 = [];
				const data2 = [];
				let prev = 100;
				let prev2 = 80;
				for (let i = 0; i < 1000; i++) {
				  prev += 5 - Math.random() * 10;
				  data3.push({x: i, y: prev});
				  prev2 += 5 - Math.random() * 10;
				  data2.push({x: i, y: prev2});
				}
				
				const totalDuration = 10000;
				const delayBetweenPoints = totalDuration / data3.length;
				const previousY = (ctx) => ctx.index === 0 ? ctx.chart.scales.y.getPixelForValue(100) : ctx.chart.getDatasetMeta(ctx.datasetIndex).data[ctx.index - 1].getProps(['y'], true).y;
				const animation = {
				  x: {
				    type: 'number',
				    easing: 'linear',
				    duration: delayBetweenPoints,
				    from: NaN, // the point is initially skipped
				    delay(ctx) {
				      if (ctx.type !== 'data' || ctx.xStarted) {
				        return 0;
				      }
				      ctx.xStarted = true;
				      return ctx.index * delayBetweenPoints;
				    }
				  },
				  y: {
				    type: 'number',
				    easing: 'linear',
				    duration: delayBetweenPoints,
				    from: previousY,
				    delay(ctx) {
				      if (ctx.type !== 'data' || ctx.yStarted) {
				        return 0;
				      }
				      ctx.yStarted = true;
				      return ctx.index * delayBetweenPoints;
				    }
				  }
				};
				
				
				new Chart(progressiveLine, {
				    type: 'line',
				    data: {
				        datasets: [{
				            borderColor: window.CHART_COLORS.red,
				            borderWidth: 1,
				            radius: 0,
				            data: data3,
				          },
				          {
				            borderColor: window.CHART_COLORS.blue,
				            borderWidth: 1,
				            radius: 0,
				            data: data2,
				          }]
				        }
				    ,
				    options: {
				        animation,
				        interaction: {
				          intersect: false
				        },
				        plugins: {
				          legend: false
				        },
				        scales: {
				          x: {
				            type: 'linear'
				          }
				        }
				      }
				  });
			
			</script>
			
		</div>
		
		<!-- Line Styling -->
		<div class="stats">
			<div class="contentThirdTitle">Line Styling</div>
			<canvas id="lineStyling"></canvas>
			<script>
		
				const lineStyling = document.getElementById('lineStyling');
		
				const DATA_COUNT5 = 7;
				const NUMBER_CFG4 = {count: DATA_COUNT5, min: -100, max: 100};
				const labels4 = Samples.utils.months({count: DATA_COUNT5});
				
				new Chart(lineStyling, {
				    type: 'line',
				    data: {
				    	  labels: labels4,
				    	  datasets: [
				    	    {
				    	      label: 'Unfilled',
				    	      fill: false,
				    	      backgroundColor: window.CHART_COLORS.blue,
				    	      borderColor: window.CHART_COLORS.blue,
				    	      data: Samples.utils.numbers(NUMBER_CFG4),
				    	    }, {
				    	      label: 'Dashed',
				    	      fill: false,
				    	      backgroundColor: window.CHART_COLORS.green,
				    	      borderColor: window.CHART_COLORS.green,
				    	      borderDash: [5, 5],
				    	      data: Samples.utils.numbers(NUMBER_CFG4),
				    	    }, {
				    	      label: 'Filled',
				    	      backgroundColor: window.CHART_COLORS.red,
				    	      borderColor: window.CHART_COLORS.red,
				    	      data: Samples.utils.numbers(NUMBER_CFG4),
				    	      fill: true,
				    	    }
				    	  ]
				    	}
				    ,
				    options: {
				        responsive: true,
				        plugins: {
				          title: {
				            display: true,
				            text: 'Chart.js Line Chart'
				          },
				        },
				        interaction: {
				          mode: 'index',
				          intersect: false
				        },
				        scales: {
				          x: {
				            display: true,
				            title: {
				              display: true,
				              text: 'Month'
				            }
				          },
				          y: {
				            display: true,
				            title: {
				              display: true,
				              text: 'Value'
				            }
				          }
				        }
				      }
				  });
			
			</script>
			
		</div>
		
		<div class="stats">
			<div class="contentThirdTitle">버블차트</div>
			<canvas id="bubbleChart"></canvas>
			<script>
		
			const bubbleChart = document.getElementById('bubbleChart');
	
			const DATA_COUNT2 = 7;
			const NUMBER_CFG = {count: DATA_COUNT2, rmin: 5, rmax: 15, min: 0, max: 100};
			
			new Chart(bubbleChart, {
			    type: 'bubble',
			    data: {
		    	  datasets: [
		    		    {
		    		      label: 'Dataset 1',
		    		      data: Samples.utils.bubbles(NUMBER_CFG),
		    		      borderColor: window.CHART_COLORS.red,
		    		      backgroundColor: Samples.utils.transparentize(window.CHART_COLORS.red, 0.5),
		    		    },
		    		    {
		    		      label: 'Dataset 2',
		    		      data: Samples.utils.bubbles(NUMBER_CFG),
		    		      borderColor: window.CHART_COLORS.orange,
		    		      backgroundColor: Samples.utils.transparentize(window.CHART_COLORS.orange, 0.5),
		    		    }
		    		  ]
		    		}
			    ,
			    options: {
			        responsive: true,
			        plugins: {
			          legend: {
			            position: 'top',
			          },
			          title: {
			            display: true,
			            text: 'Chart.js Bubble Chart'
			          }
			        }
			      }
			  });
			
			</script>
			
		</div>
		
		<!-- Bubble Chart2 -->
		<div class="stats">
			<div class="contentThirdTitle">Bubble Chart</div>
			<canvas id="bubbleChart2"></canvas>
			<script>
		
				const bubbleChart2 = document.getElementById('bubbleChart2');
		
				const DATA_COUNT9 = 16;
				const MIN_XY = -150;
				const MAX_XY = 100;
				Samples.utils.srand(110);
				
				function channelValue(x, y, values) {
				  return x < 0 && y < 0 ? values[0] : x < 0 ? values[1] : y < 0 ? values[2] : values[3];
				}

				function colorize(opaque, context) {
				  const value = context.raw;
				  const x = value.x / 100;
				  const y = value.y / 100;
				  const r = channelValue(x, y, [250, 150, 50, 0]);
				  const g = channelValue(x, y, [0, 50, 150, 250]);
				  const b = channelValue(x, y, [0, 150, 150, 250]);
				  const a = opaque ? 1 : 0.5 * value.v / 1000;

				  return 'rgba(' + r + ',' + g + ',' + b + ',' + a + ')';
				}
				
				function generateData() {
				  const data = [];
				  let i;

				  for (i = 0; i < DATA_COUNT9; ++i) {
				    data.push({
				      x: Samples.utils.rand(MIN_XY, MAX_XY),
				      y: Samples.utils.rand(MIN_XY, MAX_XY),
				      v: Samples.utils.rand(0, 1000)
				    });
				  }

				  return data;
				}
			
				new Chart(bubbleChart2, {
				    type: 'bubble',
				    data: {
			    	  datasets: [{
			    		    data: generateData()
			    		  }, {
			    		    data: generateData()
			    		  }]
			    		}
				    ,
				    options: {
				        aspectRatio: 1,
				        plugins: {
				          legend: false,
				          tooltip: false,
				        },
				        elements: {
				          point: {
				            backgroundColor: colorize.bind(null, false),

				            borderColor: colorize.bind(null, true),

				            borderWidth: function(context) {
				              return Math.min(Math.max(1, context.datasetIndex + 1), 8);
				            },

				            hoverBackgroundColor: 'transparent',

				            hoverBorderColor: function(context) {
				              return Samples.utils.color(context.datasetIndex);
				            },

				            hoverBorderWidth: function(context) {
				              return Math.round(8 * context.raw.v / 1000);
				            },

				            radius: function(context) {
				              const size = context.chart.width;
				              const base = Math.abs(context.raw.v) / 1000;
				              return (size / 24) * base;
				            }
				          }
				        }
				      }
				  });
			
			</script>
			
		</div>
		
		<!-- Polar area -->
		<div class="stats">
			<div class="contentThirdTitle">Polar area</div>
			<canvas id="polarArea"></canvas>
			<script>
		
				const polarArea = document.getElementById('polarArea');
		
				const DATA_COUNT6 = 5;
				const NUMBER_CFG6 = {count: DATA_COUNT6, min: 0, max: 100};
				const labels5 = ['Red', 'Orange', 'Yellow', 'Green', 'Blue'];
				
				new Chart(polarArea, {
				    type: 'polarArea',
				    data: {
				    	  labels: labels5,
				    	  datasets: [
				    	    {
				    	      label: 'Dataset 1',
				    	      data: Samples.utils.numbers(NUMBER_CFG6),
				    	      backgroundColor: [
				    	        Samples.utils.transparentize(window.CHART_COLORS.red, 0.5),
				    	        Samples.utils.transparentize(window.CHART_COLORS.orange, 0.5),
				    	        Samples.utils.transparentize(window.CHART_COLORS.yellow, 0.5),
				    	        Samples.utils.transparentize(window.CHART_COLORS.green, 0.5),
				    	        Samples.utils.transparentize(window.CHART_COLORS.blue, 0.5),
				    	      ]
				    	    }
				    	  ]
				    	}
				    ,
				    options: {
				        responsive: true,
				        plugins: {
				          legend: {
				            position: 'top',
				          },
				          title: {
				            display: true,
				            text: 'Chart.js Polar Area Chart'
				          }
				        }
				      }
				  });
			
			</script>
			
		</div>
		
		<div class="stats">
			<div class="contentThirdTitle">파이차트</div>
			<canvas id="pieChart"></canvas>
			<script>
		
				const pieChart = document.getElementById('pieChart');
		
				const DATA_COUNT3 = 5;
				const NUMBER_CFG3 = {count: DATA_COUNT3, min: 0, max: 100};
	
				const data = {
				  labels: ['Red', 'Orange', 'Yellow', 'Green', 'Blue'],
				  datasets: [
				    {
				      label: 'Dataset 1',
				      data: Samples.utils.numbers(NUMBER_CFG3),
				      backgroundColor: Object.values(window.CHART_COLORS),
				    }
				  ]
				};
				
				new Chart(pieChart, {
				    type: 'pie',
				    data: data ,
				    options: {
				        responsive: true,
				        plugins: {
				          legend: {
				            position: 'top',
				          },
				          title: {
				            display: true,
				            text: 'Chart.js Pie Chart'
				          }
				        }
				      }
				  });
				
			</script>
			
		</div>
		
		<!-- Radar -->
		<div class="stats">
			<div class="contentThirdTitle">Radar</div>
			<canvas id="radar"></canvas>
			<script>
		
				const radar = document.getElementById('radar');
		
				const DATA_COUNT7 = 7;
				const NUMBER_CFG7 = {count: DATA_COUNT7, min: 0, max: 100};

				const labels6 = Samples.utils.months({count: 7});
				
				new Chart(radar, {
				    type: 'radar',
				    data: {
				    	  labels: labels6,
				    	  datasets: [
				    	    {
				    	      label: 'Dataset 1',
				    	      data: Samples.utils.numbers(NUMBER_CFG7),
				    	      borderColor: window.CHART_COLORS.red,
				    	      backgroundColor: Samples.utils.transparentize(window.CHART_COLORS.red, 0.5),
				    	    },
				    	    {
				    	      label: 'Dataset 2',
				    	      data: Samples.utils.numbers(NUMBER_CFG7),
				    	      borderColor: window.CHART_COLORS.blue,
				    	      backgroundColor: Samples.utils.transparentize(window.CHART_COLORS.blue, 0.5),
				    	    }
				    	  ]
				    	}
				    ,
				    options: {
				        responsive: true,
				        plugins: {
				          title: {
				            display: true,
				            text: 'Chart.js Radar Chart'
				          }
				        }
				      }
				  });
			
			</script>
			
		</div>
		
	</div>
		
</div>

<footer></footer>

</body>
</html>