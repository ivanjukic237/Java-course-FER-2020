<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%!
private String randomColor(){
	java.util.Random random = new java.util.Random();
	int nextInt = random.nextInt(0xffffff + 1);
	String colorCode = String.format("#%06x", nextInt);
	return colorCode.toUpperCase();	
}
%>
<html>
<title>Ship</title>
<a href="/webapp2/pages/index.jsp" >HOME</a><br><br>

<%String color = randomColor();%>

<div style="color:<%=color%>">
I was once on a US military ship, having breakfast in the wardroom (officers lounge) when the Operations Officer (OPS) walks in. This guy was the definition of NOT a morning person; he's still half asleep, bleary eyed... basically a zombie with a bagel. He sits down across from me to eat his bagel and is just barely conscious. My back is to the outboard side of the ship, and the morning sun is blazing in one of the portholes putting a big bright-ass circle of light right on his barely conscious face. He's squinting and chewing and basically just remembering how to be alive for today. It's painful to watch.

But then zombie-OPS stops chewing, slowly picks up the phone, and dials the bridge. In his well-known I'm-still-totally-asleep voice, he says "heeeey. It's OPS. Could you... shift our barpat... yeah, one six five. Thanks." And puts the phone down. And then he just sits there. Squinting. Waiting.

And then, ever so slowly, I realize that that big blazing spot of sun has begun to slide off the zombie's face and onto the wall behind him. After a moment it clears his face and he blinks slowly a few times and the brilliant beauty of what I've just witnessed begins to overwhelm me. By ordering the bridge to adjust the ship's back-and-forth patrol by about 15 degrees, he's changed our course just enough to reposition the sun off of his face. He's literally just redirected thousands of tons of steel and hundreds of people so that he could get the sun out of his eyes while he eats his bagel. I am in awe.

He slowly picks up his bagel and for a moment I'm terrified at the thought that his own genius may escape him, that he may never appreciate the epic brilliance of his laziness (since he's not going to wake up for another hour). But between his next bites he pauses, looks at me, and gives me the faintest, sly grin, before returning to gnaw slowly on his zombie bagel.
</div>
<p>Color of the text: <%=color%></p>
</body>
</html>