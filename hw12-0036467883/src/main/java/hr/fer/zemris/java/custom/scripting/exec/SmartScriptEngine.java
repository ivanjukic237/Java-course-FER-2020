package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.Stack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Examines the given document parsed by the smart script parser, does the
 * operations and writes to the context.
 * 
 * @author Ivan Jukić
 *
 */

public class SmartScriptEngine {
	/**
	 * Document node.
	 */
	private DocumentNode documentNode;
	/**
	 * Context that will be created by this class.
	 */
	private RequestContext requestContext;
	/**
	 * Multistack object.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Visitor that visits all the nodes and writes the text representation of those
	 * nodes to the context.
	 */

	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * Visits the text node and writes the text to the context.
		 */

		@Override
		public void visitTextNode(TextNode node) {

			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Visits the for loop node and writes to context the body of the node as many
		 * times as it's specified.
		 */

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper value = new ValueWrapper(node.getStartExpression().asText());
			String variableName = node.getVariable().asText();
			multistack.push(variableName, value);
			while (value.numCompare(node.getEndExpression().asText()) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
				if (node.stepExpression() != null) {
					value = multistack.peek(variableName);
					value.add(node.stepExpression().asText());
					multistack.push(variableName, value);
				}
			}
			multistack.pop(variableName);

		}

		/**
		 * Visits the echo node, does the computations of given functions and writes
		 * them to the context.
		 */

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> temporaryStack = new Stack<>();
			Element[] arrayOfElements = node.getElements();

			for (int i = 0; i < arrayOfElements.length; i++) {
				Element currentElement = arrayOfElements[i];
				if (currentElement != null) {
					if (currentElement.getClass().getName().contains("Constant")
							|| currentElement.getClass().getName().contains("String")) {
						temporaryStack.push(currentElement.asText());

					} else if (currentElement.getClass().getName().contains("Variable")) {
						// finding that variable in the multistack and push it on the temporary stack
						temporaryStack.push(multistack.peek(currentElement.asText()));

					} else if (currentElement.getClass().getName().contains("Operator")) {
						Object first = temporaryStack.pop().toString();
						Object second = temporaryStack.pop().toString();
						Object temp = first;
						ValueWrapper wrapper = new ValueWrapper(temp);
						String operator = currentElement.asText();

						if (operator.equals("+")) {
							wrapper.add(second);
						} else if (operator.equals("-")) {
							wrapper.subtract(second);
						} else if (operator.equals("*")) {
							wrapper.multiply(second);
						} else if (operator.equals("/")) {
							wrapper.divide(second);
						} else {
							throw new IllegalArgumentException("Unknown operation: " + operator);
						}
						temporaryStack.push(wrapper.getValue().toString());
					} else if (currentElement.getClass().getName().contains("Function")) {
						Functions functions = new Functions(temporaryStack, requestContext);
						switch (currentElement.asText()) {
						case "@sin":
							functions.sin();
							break;
						case "@decfmt":
							functions.decfmt();
							break;
						case "@dup":
							functions.dup();
							break;
						case "@swap":
							functions.swap();
							break;
						case "@setMimeType":
							functions.setMimeType();
							break;
						case "@paramGet":
							functions.paramGet();
							break;
						case "@pparamGet":
							functions.pparamGet();
							break;
						case "@pparamSet":
							functions.pparamSet();
							break;
						case "@pparamDel":
							functions.pparamDel();
							break;
						case "@tparamGet":
							functions.tparamGet();
							break;
						case "@tparamSet":
							functions.tparamSet();
							break;
						case "@tparamDel":
							functions.tparamDel();
							break;
						}
					}
				}
			}

			for (Object elem : temporaryStack) {
				try {
					requestContext.write(elem.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		/**
		 * Visits the document node and calls accept on all children.
		 */

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	};

	/**
	 * Creates an engine from document node and context.
	 *  
	 * @param documentNode document node
	 * @param requestContext context
	 */
	
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.requestContext = requestContext;
		this.documentNode = documentNode;

	}

	/**
	 * Executes the script.
	 */
	
	public void execute() {
		documentNode.accept(visitor);
	}

}
