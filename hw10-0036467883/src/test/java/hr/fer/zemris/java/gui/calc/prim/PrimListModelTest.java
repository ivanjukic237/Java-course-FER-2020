package hr.fer.zemris.java.gui.calc.prim;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JList;

/**
 * Testing the functionalities of PrimListModel.
 */

import org.junit.jupiter.api.Test;

class PrimListModelTest {

	PrimListModel<Integer> model = new PrimListModel<>();
	JList<Integer> list1 = new JList<>(model);
	JList<Integer> list2 = new JList<>(model);
	
	@Test
	void testNext() {
		model.add(model.next());
		assertEquals(2, list1.getModel().getElementAt(0));
		
	}

}
