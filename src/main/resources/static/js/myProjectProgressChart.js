var chartData = decodeHtml(chartData);
var chartJsonArray = JSON.parse(chartData);

var arrayLength = chartJsonArray.length;
var numberData = [];
var labelData = [];

for(var i=0; i<arrayLength; i++){
	numberData[i] = chartJsonArray[i].value;
	labelData[i] = chartJsonArray[i].label;
}


new Chart(document.getElementById("projectProgressChart"), {
  type: 'doughnut',
  data: {
    labels: labelData,
    datasets: [{
      label: 'My First dataset',
      backgroundColor: ["#3e95cd","#8e5ea2","#3cba9f"], 
      data: numberData,
    }]
  },
  options: {
	title:{
		display: true,
		text: 'Project Status'
	}
}  
});


function decodeHtml(html){
	var txt = document.createElement("textarea");
	txt.innerHTML=html;
	return txt.value;
}