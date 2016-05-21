<!DOCTYPE html>
<meta charset="utf-8">
<style>

body {
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  margin: auto;
  position: relative;
  width: 960px;
}

text {
  font: 10px sans-serif;
}

.axis path,
.axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}

form {
  position: absolute;
  right: 10px;
  top: 10px;
}

</style>
<form>
  <label><input type="radio" name="mode" value="grouped"> Grouped</label>
  <label><input type="radio" name="mode" value="stacked" checked> Stacked</label>
</form>
<script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="//d3js.org/d3.v3.min.js"></script>
<script>
/* $(document).ready(function(){
	
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
   
}); */
var datat = 	{
		"x": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29],
		"y": [3.7665108320063814, 2.5883115643777934, 2.0620958257525257, 1.7215567463102213, 1.1729850141204023, 0.6347491516470846, 0.3203867597222644, 0.16598723915471483, 0.20450131939311086, 0.17233176766543984, 0.14291652691973486, 0.20169506041268956, 0.7078195620435339, 1.526821093455712, 1.0491560572044034, 0.27670668646472985, 0.1361535693051967, 0.1275290723333118, 0.13658504066459334, 0.17509671237422075, 0.1335430235009804, 0.1324883414547824, 0.17534222029295132, 0.17808359691313225, 0.15405225924703614, 0.16520020202548003, 0.11223611348302864, 0.18433354741082547, 0.1955369414327056, 0.129999789508652, 0.4731506809776687, 0.25286626797674994, 0.1348142155169806, 0.14991871473946386, 0.10110343331737665, 0.15356833856440996, 0.12349731915098583, 0.19281935625244973, 0.19860241946784243, 0.120997256251534, 0.1351319047669366, 0.168541981086321, 0.10632297590088753, 0.13973216226967994, 0.17344012555693417, 0.15058353458159052, 0.15002045175019904, 0.1561824415039244, 0.14458110963715254, 0.14146078406984963, 0.17374529988660484, 0.18270175817893514, 0.46301596208846174, 1.1625009983865844, 0.8433958313589747, 1.1651992280936518, 2.2005374380769895, 3.2432312465650908, 3.3035849063360594, 2.321512776798122, 0.5816055164042123, 0.4443263795187491, 1.144020356925339, 1.7118029271604664, 0.19512553145486386, 0.1736513783906726, 0.15166882929595396, 0.13145008168803526, 0.14729245722304204, 0.15657313254483565, 0.17264523096692172, 0.14033370044299945, 0.10720177687925725, 0.16221432441064526, 0.17268464938226946, 0.1802279527495397, 0.1881504681174657, 0.1417615873880485, 0.12719889287584174, 0.1476757056855984, 0.17313672873338762, 0.1696715487378823, 0.14767713474297295, 0.19187140237803396, 0.15000548584442636, 0.17954164205475312, 0.149299894242149, 0.11003710125652455, 0.1486260742098096, 0.1315100082409171]
		
		
	};
var n = 3, // number of layers within each course
    m = 7, // number of courses
    stack = d3.layout.stack(),
    fromBump = bumpLayer(m, .1); 
   layers = stack(d3.range(n).map(function() { return bumpLayer(m, .1); })),
   // layers = stack(d3.range(n).map(function() { debugger; return datat; })),
 // 	layers = datat;
    yGroupMax = d3.max(layers, function(layer) { return d3.max(layer, function(d) { return d.y; }); }),
    yStackMax = d3.max(layers, function(layer) { return d3.max(layer, function(d) { return d.y0 + d.y; }); });

var margin = {top: 40, right: 10, bottom: 20, left: 10},
    width = 960 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;

var x = d3.scale.ordinal()
    .domain(d3.range(m))
    .rangeRoundBands([0, width], .08);

var y = d3.scale.linear()
    .domain([0, yStackMax])
    .range([height, 0]);

var color = d3.scale.linear()
    .domain([0, n - 1])
    .range(["#aad", "#556"]);

var xAxis = d3.svg.axis()
    .scale(x)
    .tickSize(0)
    .tickPadding(6)
    .orient("bottom");

var svg = d3.select("body").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

var layer = svg.selectAll(".layer")
    .data(layers)					//here the date is being appended
  .enter().append("g")
    .attr("class", "layer")
    .style("fill", function(d, i) { return color(i); });

var rect = layer.selectAll("rect")
    .data(function(d) { return d; })
  .enter().append("rect")
    .attr("x", function(d) { return x(d.x); })
    .attr("y", height)
    .attr("width", x.rangeBand())
    .attr("height", 0);

rect.transition()
    .delay(function(d, i) { return i * 10; })
    .attr("y", function(d) { return y(d.y0 + d.y); })
    .attr("height", function(d) { return y(d.y0) - y(d.y0 + d.y); });

svg.append("g")
    .attr("class", "x axis")
    .attr("transform", "translate(0," + height + ")")
    .call(xAxis);

d3.selectAll("input").on("change", change);

var timeout = setTimeout(function() {
  d3.select("input[value=\"grouped\"]").property("checked", true).each(change);
}, 2000);

function change() {
  clearTimeout(timeout);
  if (this.value === "grouped") transitionGrouped();
  else transitionStacked();
}

function transitionGrouped() {
  y.domain([0, yGroupMax]);

  rect.transition()
      .duration(500)
      .delay(function(d, i) { return i * 10; })
      .attr("x", function(d, i, j) { return x(d.x) + x.rangeBand() / n * j; })
      .attr("width", x.rangeBand() / n)
    .transition()
      .attr("y", function(d) { return y(d.y); })
      .attr("height", function(d) { return height - y(d.y); });
}

function transitionStacked() {
  y.domain([0, yStackMax]);

  rect.transition()
      .duration(500)
      .delay(function(d, i) { return i * 10; })
      .attr("y", function(d) { return y(d.y0 + d.y); })
      .attr("height", function(d) { return y(d.y0) - y(d.y0 + d.y); })
    .transition()
      .attr("x", function(d) { return x(d.x); })
      .attr("width", x.rangeBand());
}

// Inspired by Lee Byron's test data generator.
function bumpLayer(n, o) {

  function bump(a) {
    var x = 1 / (.1 + Math.random()),
        y = 2 * Math.random() - .5,
        z = 10 / (.1 + Math.random());
    for (var i = 0; i < n; i++) {
      var w = (i / n - y) * z;
      a[i] += x * Math.exp(-w * w);
    }
  }

  var a = [], i;
  for (i = 0; i < n; ++i) a[i] = o + o * Math.random(); //array of size n - num of courses
  for (i = 0; i < 5; ++i) bump(a);
  return a.map(function(d, i) { return {x: i, y: Math.max(0, d)}; });
}

</script>