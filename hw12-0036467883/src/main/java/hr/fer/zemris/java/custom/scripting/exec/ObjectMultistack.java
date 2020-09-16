package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;

/**
 * Model of map that allows you to store a stack-like object for each key.
 * 
 * @author Ivan Jukić
 *
 */

public class ObjectMultistack {

	/**
	 * Map that maps the key to the MulstiStackEntry.
	 */

	private HashMap<String, MultiStackEntry> map = new HashMap<>();

	/**
	 * Pushes the value wrapper to the stack.
	 * 
	 * @param keyName      key
	 * @param valueWrapper value
	 */

	public void push(String keyName, ValueWrapper valueWrapper) {
		if (map.containsKey(keyName)) {
			MultiStackEntry newEntry = new MultiStackEntry(valueWrapper);
			newEntry.setNextEntry(map.get(keyName));
			map.put(keyName, newEntry);
		} else {
			map.put(keyName, new MultiStackEntry(valueWrapper));
		}
	}

	/**
	 * Pops the value from the stack.
	 * 
	 * @param keyName key
	 * @return popped value
	 */

	public ValueWrapper pop(String keyName) {
		if (!map.containsKey(keyName)) {
			throw new EmptyStackException();
		}
		MultiStackEntry temp = map.get(keyName);
		map.put(keyName, temp.getNextEntry());
		return temp.getWrapper();

	}

	/**
	 * Peeks at the object at the top of the stack.
	 * 
	 * @param keyName key
	 * @return object at the top of the stack
	 */

	public ValueWrapper peek(String keyName) {
		if (!map.containsKey(keyName)) {
			throw new EmptyStackException();
		}
		return map.get(keyName).getWrapper();
	}

	/**
	 * Checks if the stack for the given key in the multistack is empty.
	 * 
	 * @param keyName key
	 * @return
	 */

	public boolean isEmpty(String keyName) {
		return map.get(keyName).getNextEntry() == null ? true : false;
	}

	/**
	 * Stack like structure that represents the value for the key in the multistack.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class MultiStackEntry {

		/**
		 * Next entry in the stack.
		 */
		private MultiStackEntry nextEntry = null;
		/**
		 * Value of this entry.
		 */
		private ValueWrapper wrapper = null;

		/**
		 * Creates the multi stack entry
		 * 
		 * @param valueWrapper value of the multi stack entry
		 */
		public MultiStackEntry(ValueWrapper valueWrapper) {
			this.wrapper = valueWrapper;
		}

		/**
		 * Returns the next entry.
		 * 
		 * @return next entry
		 */

		public MultiStackEntry getNextEntry() {
			return nextEntry;
		}

		/**
		 * Sets the next entry.
		 * 
		 * @param nextEntry next entry
		 */

		public void setNextEntry(MultiStackEntry nextEntry) {
			this.nextEntry = nextEntry;
		}

		/**
		 * Returns the value of the entry.
		 * 
		 * @return value of the entry.
		 */

		public ValueWrapper getWrapper() {
			return wrapper;
		}
	}
}
