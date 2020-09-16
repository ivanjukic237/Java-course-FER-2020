/**
*Changes the background color of p tags for the entire document.
 */
function changeColor() {
	var x = document.getElementsByTagName("p");
	for (var i = 0; i < x.length; i++) {
		if (x[i].style.backgroundColor == "blue") {
			x[i].style.backgroundColor = document.body.style.backgroundColor;
		} else {
			x[i].style.backgroundColor = "blue";
		}
	}
}
