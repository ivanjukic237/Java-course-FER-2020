package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Model of the visitor for the nodes.
 * 
 * @author Ivan Jukić
 *
 */

public interface INodeVisitor {

	/**
	 * Visits the text node.
	 * 
	 * @param node text node
	 */
	
	public void visitTextNode(TextNode node);

	/**
	 * Visits the ForLoop node.
	 * 
	 * @param node ForLoop node
	 */
	
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visits the Echo node.
	 * 
	 * @param node Echo node
	 */
	
	public void visitEchoNode(EchoNode node);

	/**
	 * Visits the document node.
	 * 
	 * @param node document node
	 */
	
	public void visitDocumentNode(DocumentNode node);

}
