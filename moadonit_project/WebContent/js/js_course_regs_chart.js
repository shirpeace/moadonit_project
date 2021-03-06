$(document).ready(function(){
	

	var margin = {top: 20, right: 20, bottom: 30, left: 40},
    width = 960 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;

var x = d3.scale.ordinal()
    .rangeRoundBands([0, width], .1);

var y = d3.scale.linear()
    .rangeRound([height, 0]);

var color = d3.scale.ordinal()
    .range(["#33FF99", "#3399FF", "#BCC3C4"/*, "#6b486b", "#a05d56", "#d0743c", "#ff8c00"*/]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left");
   // .tickFormat(d3.format(".2s"));

var svg = d3.select("#chart").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .style("background-color","#f5f5f5")
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

var tip = d3.tip()
.attr('class', 'd3-tip')
.offset([-10, 0])
.html(function(d) {
	var value = d.y1-d.y0;
    return " <span style='color:white'> " + value + "</span> <strong> "+ d.name +"</strong>";
});
svg.call(tip);

d3.json("ReportsController?action=getAllCourseRegsData", function(error, data) {
  if (error) throw error;

  color.domain(d3.keys(data[0]).filter(function(key) { return key !== "course"; }));

  data.forEach(function(d) {
    var y0 = 0;
    d.ages = color.domain().map(function(name) { return {name: name, y0: y0, y1: y0 += +d[name]}; });
    d.total = d.ages[d.ages.length - 1].y1;
  });

  data.sort(function(a, b) { return b.total - a.total; });
  
  x.domain(data.map(function(d) { return d.course; }));
  y.domain([0, d3.max(data, function(d) { return d.total; })]);

  svg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis);

  svg.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("");

  var course = svg.selectAll(".course")
      .data(data)
    .enter().append("g")
      .attr("class", "g")
      .attr("transform", function(d) { return "translate(" + x(d.course) + ",0)"; })
      ;
  
  course.selectAll("rect")
      .data(function(d) { return d.ages; })
    .enter().append("rect")
      .attr("width", x.rangeBand())
      .attr("y", function(d) { return y(d.y1); })
      .attr("height", function(d) { return y(d.y0) - y(d.y1); })
      .style("fill", function(d) { return color(d.name); })
      .style("opacity","0.6")
      .on('mouseover',function(d){
    	  tip.show(d);
    //	  d3.select(this).style("fill","#d0743c");
	}) 
      
	.on('mouseout',function(){
    	  tip.hide;
   // 	  d3.select(this).style("fill",function(d) { return color(d.name); });
	} );

  var legend = svg.selectAll(".legend")
      .data(color.domain().slice().reverse())
    .enter().append("g")
      .attr("class", "legend")
      .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

  legend.append("rect")
      .attr("x", width - 18)
      .attr("width", 18)
      .attr("height", 18)
      .style("fill", color);

  legend.append("text")
      .attr("x", width - 24)
      .attr("y", 9)
      .attr("dy", ".35em")
 //     .style("text-anchor", "end")
      .text(function(d) { return d; });
  
  d3.selectAll('g.y.axis > g.tick ')
 // .filter(".tick" )
   //only ticks that returned true for the filter will be included
   //in the rest of the method calls:
  .select('text') //grab the tick line
  .style('text-anchor', "start"); //or style directly with attributes or inline styles

});

}); 