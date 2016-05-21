<!DOCTYPE html>
<meta charset="utf-8">
<style>

#chart rect{
  fill: #4aaeea;
}

#chart text{
  fill: white;
  font: 10px sans-serif;
  text-anchor: end; 
}

.axis text{
  font: 10px sans-serif;
}

.axis path, .axis line{
  fill: none;
  stroke : #fff;
  shape-rendering: crispEdges;
}

body{
  background: #1a1a1a;
  color : #eaeaea;
  padding : 10px;
}

</style>
<h2>D3JS Simple JSON SVG barchart Horizontal with Axis</h2>
<i>using simple JSON data</i><br/>
taken from <a href="http://bost.ocks.org/mike/bar/3/">http://bost.ocks.org/mike/bar/3/</a> <br/><br/>
<div id="chart"></div> 
<script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="//d3js.org/d3.v3.min.js"></script>
<script>

 $(document).ready(function(){

$.ajax({
		async: false,
	type: 'GET',
	datatype: 'jsonp',
    url: "ReportsController",
    data: {action : 'getAllCourseRegsData' },
    success: function(data) {
    	if(data != undefined){
    		console.log(data);
    		
    	}
    	else
    		console.log("no data !!! ok shir");
    },
    error: function(e) {
    	console.log("error");
		
    }
    
  });

}); 
var margin ={top:20, right:30, bottom:30, left:40},
width=960-margin.left - margin.right, 
height=500-margin.top-margin.bottom;

//scale to ordinal because x axis is not numerical
var x = d3.scale.ordinal().rangeRoundBands([0, width], .1);

//scale to numerical value by height
var y = d3.scale.linear().range([height, 0]);

var chart = d3.select("#chart")  
          .append("svg")  //append svg element inside #chart
          .attr("width", width+(2*margin.left)+margin.right)    //set width
          .attr("height", height+margin.top+margin.bottom);  //set height
var xAxis = d3.svg.axis()
          .scale(x)
          .orient("bottom");  //orient bottom because x-axis will appear below the bars

var yAxis = d3.svg.axis()
          .scale(y)
          .orient("left");

d3.json("ReportsController?action=getAllCourseRegsData", function(error, data){
x.domain(data.map(function(d){ return d.activityName;}));
y.domain([0, d3.max(data, function(d){return d.NumberOfReg;})]);

var bar = chart.selectAll("g")
                .data(data)
              .enter()
                .append("g")
                .attr("transform", function(d, i){
                  return "translate("+x(d.activityName)+", 0)";
                });

bar.append("rect")
  .attr("y", function(d) { 
    return y(d.NumberOfReg); 
  })
  .attr("x", function(d,i){
    return x.rangeBand()-(margin.left);
  })
  .attr("height", function(d) { 
    return height - y(d.NumberOfReg); 
  })
  .attr("width", x.rangeBand()/2);  //set width base on range on ordinal data

bar.append("text")
  .attr("x", x.rangeBand() - (margin.left/2)+6 )
  .attr("y", function(d) { return y(d.NumberOfReg) -10; })
  .attr("dy", ".75em")
  .text(function(d) { return d.NumberOfReg; });

chart.append("g")
    .attr("class", "x axis")
    .attr("transform", "translate("+margin.left+","+ height+")")        
    .call(xAxis);

chart.append("g")
    .attr("class", "y axis")
    .attr("transform", "translate("+margin.left+",0)")
    .call(yAxis)
    .append("text")
    .attr("transform", "rotate(-90)")
    .attr("y", 6)
    .attr("dy", ".71em")
    .style("text-anchor", "end")
    .text("NumberOfReg");
});

function type(d) {
d.activityName = +d.activityName; // coerce to number
return d;
}

</script>