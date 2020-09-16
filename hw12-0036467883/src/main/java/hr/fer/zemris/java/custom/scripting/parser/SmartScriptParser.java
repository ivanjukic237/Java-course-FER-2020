package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.*;

import java.util.EmptyStackException;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * Razred uzima tokene dobivene pomoću razreda Lexer, gradi čvorove od njih te vraća strukturu stabla gdje je
 * prvi čvor DocumentNode, a ostali čvorovi su njegova djeca.
 * 
 * 
 * @author Ivan Jukić
 *
 */

public class SmartScriptParser {
	
	/**
	 * Struktura podataka stog na koji se prvo "push-a" DocumentNode pa onda čvorovi koji mogu imati svoju djecu.
	 * Nakon što se u tim čvorovima dodaju sva djeca, čvor se "pop-a" sa stoga.
	 */
	
	private Stack<Object> stack = new Stack<>();
	private DocumentNode documentNode;
	
	/**
	 * Konstruktor koji prima dokument, stvara DocumentNode čvor te ga "push-a" na stog. Nakon toga
	 * pokreće metodu za parsiranje dobivenih Tokena.
	 * 
	 * @throws IllegalArgumentException ako je dokument prazan
	 * @param documentBody dokument koji se treba parsirati
	 */
	
	public SmartScriptParser(String documentBody) {
		if(documentBody == "") {
			throw new IllegalArgumentException("Dokument je prazan.");
		}
		documentNode = new DocumentNode(documentBody);
		stack.push(documentNode);
		parse(documentBody);
		
	}
	
	/**
	 * Metoda vraća DocumentNode tog razreda
	 * 
	 * @return DocumentNode tog razreda
	 */
	
	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}
	
	/**
	 * Metoda predstavlja for petlju. Vraća ForLoopNode čvor koji se može sastojati od 3 ili 4 članova.
	 * Prvi član je varijabla i predstavlj avarijablu koja će se mijenjati u petlji, drugi član je početna
	 * vrijednost varijable, treći član je vrijednost do koje će se for petlja odvijati, a može biti ili 
	 * varijabla ili broj. Zadnji član ne mora biti prisutan. Ako je prisutan, onda je to broj za koliko će
	 * se varijabla promijeniti za svaku iteraciju for petlje, a ako ga nema onda se pretpostavlja da se 
	 * povećava za jedan.
	 * 
	 * @throws SmartScriptParserException ako članovi for petlje nisu dobro definirani
	 * @throws SmartScriptParserException ako for petlja ima manje od 3 i više od 4 članova
	 * @param nextToken Token FOR od kojeg počinje parsiranje članova petlje
	 * @param lexer lexer koji provodi vraćanje Tokena
	 * @return ForLoopNode čvor
	 */
	
	private ForLoopNode getForNode(Token nextToken, Lexer lexer) {
		
		ElementVariable variable = null;
		Element startExpression = null;
		Element endExpression = null;
		Element stepExpression = null;
		ForLoopNode forLoopNode = null;
		
		if(nextToken.getType().equals(TokenType.VARIABLE)) {
			
			variable = new ElementVariable((String)nextToken.getValue());
			nextToken = lexer.nextToken();
			
		} else {
			throw new SmartScriptParserException("Prvi element u petlji nije varijabla.");
		}
		
		if(nextToken.getType().equals(TokenType.DOUBLE) || nextToken.getType().equals(TokenType.INTEGER)) {
			
				if(nextToken.getType().equals(TokenType.DOUBLE)) {
					startExpression = new ElementConstantDouble((Double)nextToken.getValue());
				} else if(nextToken.getType().equals(TokenType.INTEGER)) {
					startExpression = new ElementConstantInteger((Integer)nextToken.getValue());
				}
				nextToken = lexer.nextToken();	
		} else {
			throw new SmartScriptParserException("Drugi element u petlji nije broj.");
		}
		
		
		if(nextToken.getType().equals(TokenType.DOUBLE) || nextToken.getType().equals(TokenType.INTEGER) || nextToken.getType().equals(TokenType.VARIABLE)) {
			
			if(nextToken.getType().equals(TokenType.DOUBLE)) {
				endExpression = new ElementConstantDouble((Double)nextToken.getValue());
			} else if(nextToken.getType().equals(TokenType.INTEGER)) {
				endExpression = new ElementConstantInteger((Integer)nextToken.getValue());
			} else if(nextToken.getType().equals(TokenType.VARIABLE)) {
				endExpression = new ElementVariable((String)nextToken.getValue());	
			}	
			nextToken = lexer.nextToken();
	} else {
		throw new SmartScriptParserException("Treći element u petlji nije broj ili varijabla.");
	}
		if(nextToken.getType().equals(TokenType.DOUBLE) || 
				nextToken.getType().equals(TokenType.INTEGER) || 
				nextToken.getType().equals(TokenType.EndOfTag)) {
			
				if(nextToken.getType().equals(TokenType.DOUBLE)) {
					stepExpression = new ElementConstantDouble((Double)nextToken.getValue());
					nextToken = lexer.nextToken();
				} else if(nextToken.getType().equals(TokenType.INTEGER)) {
					stepExpression = new ElementConstantInteger((Integer)nextToken.getValue());
					nextToken = lexer.nextToken();
				}

			if(variable == null || startExpression == null || endExpression == null || !nextToken.getType().equals(TokenType.EndOfTag)) {
				throw new SmartScriptParserException("For petlja nije dobro inicijalizirana.");
			}
			forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		} 
		return forLoopNode;
	}
	
	/**
	 * Metoda poziva lexer koji vraća Tokene za određeni tekst te ih parsira u čvorove i radi strukturu drveta od njih.
	 * 
	 * @throws EmptyStackException ako imamo više END Tokena nego što imamo tagova
	 * @throws SmartScriptParserException ako for petlja nije pravilno zatvorena Tokenom END
	 * @param documentBody tekst iz kojeg se vade Tokeni pomoću lexera i onda se parsiraju
	 */
	
	private void parse(String documentBody) {
		Lexer lexer = new Lexer(documentBody);
		Token nextToken = lexer.nextToken();
		
		while(!nextToken.getType().equals(TokenType.EOF)) {
			
			 if(nextToken.getType().equals(TokenType.TEXT)){
				TextNode textNode = new TextNode((String)nextToken.getValue());
				Node parentNode = (Node) stack.peek();
				parentNode.addChildNode(textNode);
				
			} else if(nextToken.getType().equals(TokenType.StartOfTag)) {
				
				nextToken = lexer.nextToken();
				
				if(nextToken.getType().equals(TokenType.FOR)) {
					nextToken = lexer.nextToken();
					ForLoopNode forLoopNode = getForNode(nextToken, lexer);
					Node parentNode = (Node) stack.peek();
					parentNode.addChildNode(forLoopNode);
					stack.push(forLoopNode);
					
				} else if(nextToken.getType().equals(TokenType.EQUALS)) {
					nextToken = lexer.nextToken();
					Element[] elements = new Element[20];
					//elements[0] = new ElementOperator("=");
					int counter = 0;
					while(!nextToken.getType().equals(TokenType.EndOfTag)) {
						
						if(nextToken.getType().equals(TokenType.DOUBLE)) {
							elements[counter] = new ElementConstantDouble((Double)nextToken.getValue());
						
						} else if(nextToken.getType().equals(TokenType.INTEGER)) {
							elements[counter] = new ElementConstantInteger((Integer)nextToken.getValue());
						
						} else if(nextToken.getType().equals(TokenType.FUNCTION)) {
							elements[counter] = new ElementFunction((String)nextToken.getValue());
						
						} else if(nextToken.getType().equals(TokenType.VARIABLE)) {
							elements[counter] = new ElementVariable((String)nextToken.getValue());
						
						} else if(nextToken.getType().equals(TokenType.OPERATOR)) {
							elements[counter] = new ElementOperator((String)nextToken.getValue());
						
						}  else if(nextToken.getType().equals(TokenType.TEXT)) {
							elements[counter] = new ElementString((String)nextToken.getValue());
						}
						nextToken = lexer.nextToken();
						
						counter++;
					}
				
					EchoNode echoNode = new EchoNode(elements);
					Node parentNode = (Node) stack.peek();
					parentNode.addChildNode(echoNode);
					
				} else if(nextToken.getType().equals(TokenType.END)) {
					stack.pop();
					if(stack.isEmpty()) {
						System.out.println("Izraz ima previše {$END$} tagova.");
						throw new EmptyStackException() ;
					}
				} else {
					throw new SmartScriptParserException("Tag nije prepoznat.");
				}
			} 
			  
			nextToken = lexer.nextToken();

		}
		 
		 if(stack.size() > 1) {
			throw new SmartScriptParserException("For petlja nije pravilno zatvorena.") ;

		}
	}
}
