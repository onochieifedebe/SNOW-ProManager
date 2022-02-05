var projData = decodeHtml(projData);
var chartJsonArray = JSON.parse(projData);

var arrayLength = chartJsonArray.length;
var numberData = [];
var labelData = ["On Projects", "Not On Projects"];

for(var i=0; i<arrayLength; i++){
	numberData[i] = chartJsonArray[i].label;
	numberData[i+1] = chartJsonArray[i].value;

}

new Chart(document.getElementById("empOnProjectChart"), {
  type: 'doughnut',
  data: {
    labels: labelData,
    datasets: [{
      label: 'Emp on Projs & Emp not on Projs',
      backgroundColor: ["#8e5ea2","#3cba9f"],
      data: numberData,
    }]
  },
  options: {
	title:{
		display: true,
		text: 'Projects and Employees'
	}
}
});

function decodeHtml(html){
	var txt = document.createElement("textarea");
	txt.innerHTML=html;
	return txt.value;
}