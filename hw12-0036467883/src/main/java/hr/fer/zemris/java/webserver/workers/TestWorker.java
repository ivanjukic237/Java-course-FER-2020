package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class TestWorker implements IWebWorker {
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		StringBuilder sb = new StringBuilder();
		int sum = 0;
		if(!context.getParameterNames().contains("sumiraj")) {
			sendTodaysDate(context);
		} else {
			boolean isWrong = false;
			String[] numbers = context.getParameter("sumiraj").split(",");
			for(int i = 0; i < numbers.length; i++) {
				try {
					if(i == numbers.length - 1) {
						sb.append(numbers[i]);
					} else {
						sb.append(numbers[i] + " + ");
					}
					sum += Integer.parseInt(numbers[i]);
				} catch(NumberFormatException ex) {
					isWrong = true;
				}
			}
			if(!isWrong) {
				sb.append(" = " + sum);
				context.setTemporaryParameter("zbroj", sb.toString());
				context.getDispatcher().dispatchRequest("/private/pages/zbroj.smscr");
			} else {
				sendTodaysDate(context);
			}
		}
		
	}
	
	private void sendTodaysDate(RequestContext context) throws IOException {
		context.setMimeType("text/plain");
		
		context.write(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		
	}

}
