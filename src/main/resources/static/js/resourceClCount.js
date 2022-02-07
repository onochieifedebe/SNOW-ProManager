var MlData = decodeHtml(MlData);
var chartJsonArray = JSON.parse(MlData);

var arrayLength = chartJsonArray.length;
var numberData = [];
var labelData = [];
var onProjectData = [];

for(var i=0; i<arrayLength; i++){
	numberData[i] = chartJsonArray[i].value;
	labelData[i] = chartJsonArray[i].label;
	onProjectData[i] = chartJsonArray[i].onproject;
}


new Chart(document.getElementById("clEmpCountChart"), {
  type: 'bar',
  data: {
    labels: labelData,
    datasets: [
	{	  	
      label: 'Resources on Projects',
      fill: false,
      backgroundColor: [
            'rgb(54,162,235,0.8)',
            'rgb(54,162,235, 0.8)',
            'rgb(54,162,235, 0.8)',
            'rgb(54,162,235, 0.8)',
            'rgb(54,162,235, 0.8)',
		],
		borderWidth: 2, 
      data: onProjectData,
    },
    {
    label: 'Resources NOT on Projects',
      backgroundColor: [
			'rgb(142,124,195, 0.8)',
            'rgb(142,124,195, 0.8)',
            'rgb(142,124,195, 0.8)',
            'rgb(142,124,195, 0.8)',
            'rgb(142,124,195, 0.8)',
					],
		borderWidth: 2, 
      data: numberData,
    }
	]
  },
  options: {
	indexAxis: 'y',
	responsive: true,
    scales: {
      x: {
        stacked: true,
      },
      y: {
        stacked: true
      }
    }
}  
});


function decodeHtml(html){
	var txt = document.createElement("textarea");
	txt.innerHTML=html;
	return txt.value;
}