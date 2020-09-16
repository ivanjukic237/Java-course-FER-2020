<html>
<head>
<style type="text/css">
* {
text-aligh:center;
color:red;}
</style>
</head>
<body>
<a href="/webapp2/pages/index.jsp">HOME</a>

<p id="demo">This text will change when pressed</p>
<button type="button" onclick='document.getElementById("demo").innerHTML = "Hello :)"'>Click here!</button>
<br>
<button
onclick="document.getElementById('myImage').src='/webapp2/images/pic_bulbon.gif'">Turn on the light</button>
<img id="myImage" src="/webapp2/images/pic_bulboff.gif" style="width:100px">
<button
onclick="document.getElementById('myImage').src='/webapp2/images/pic_bulboff.gif'">Turn off the light</button>

<p id="demo1"> JS can change the style of an HTML element.</p>
<button type="button" onclick="document.getElementById('demo1').style.fontSize='35px'">Change font</button>

<p id="demo2"> JS can hide HTML elements.</p>
<button type="button" onclick="document.getElementById('demo2').style.display='none'">Hide element</button>
<button type="button" onclick="document.getElementById('demo2').style.display='block'">Show element</button>
<p id="demo3">Paragraf</p>
<button type="button" onclick="myFunction1()">Change again but with function</button>
<br>
<button onclick="myFunction()">Alert</button>
<p id="sumDemo"></p>
<p id="arrayDemo"></p>

<button onclick="document.getElementById('clock').innerHTML=Date()">The time is?</button>
<button onclick="this.innerHTML=Date()">The time is?</button>
<p id="clock"></p>

<div class="topRightCorner"><button onclick="changeColor()">Change color</button></div>
<div class="topRightCorner"><button onclick="changeBColor()">Change bcolor</button></div>

</body>

<style type="text/css">
.topRightCorner{
position:absolute;
top:0;
right:0;
}
</style>


<script>
function changeBColor(){ 
	var color = "${pickedBgCol}";
	document.body.style.backgroundColor = color;
}
</script>


<script type="text/javascript" src="/webapp2/js/test.js" ></script>
<script>
var cars = ["Audi", "Volvo", "BMW"];
document.getElementById("arrayDemo").innerHTML = cars;
</script>
<script>
var x, y, z;
x = 5;
y = 6;
z = x + y;
document.getElementById("sumDemo").innerHTML="The value of z is " + z + ".";
</script>

<script>
function myFunction() {
  alert(location.hostname);
}
</script>


<script>
document.write(5+6);
</script>
<script>
function myFunction1(){
	document.getElementById("demo3").innerHTML="Paragraph changed.";
}
</script>
</html>