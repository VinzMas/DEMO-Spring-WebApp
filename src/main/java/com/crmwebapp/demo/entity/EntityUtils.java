package com.crmwebapp.demo.entity;

import java.util.Iterator;

final class EntityUtils {

	private static final String keysSeparator = " ";

	/** Return a string representation of the iterable collection of entities, which consists in the sequence of their ids delimitated by square brackets and separated by whitespaces.
	 * @param elements
	 * @return sequence of id of elements delimitated by square brackets and separated by whytespaces
	 */
	public static String entitiesToString(Iterable<? extends Entity> elements) {
		StringBuilder tempKeySequence = tempKeySequence(elements);
		return cleanKeySequence(tempKeySequence);
	}

	private static StringBuilder tempKeySequence(Iterable<? extends Entity> elements) {
		StringBuilder keySequence = new StringBuilder();
		Iterator<? extends Entity> iterator = elements.iterator();				
				
		if(!iterator.hasNext()) return null;
		
		while (iterator.hasNext()) {
			Entity entity = iterator.next();
			keySequence.append(keysSeparator + entity.getPrimaryKey()) ;			
		}
		return keySequence;
	}

	private static String cleanKeySequence(StringBuilder keySequence) {
		if(keySequence == null) {
			return null;
		}
		
		String result;
		int sepLength = keysSeparator.length();
		String firstSeparator = keySequence.substring(0,sepLength);
		if(!firstSeparator.equals(keysSeparator))
			throw new RuntimeException("invalid substring: first substring should be '" + keysSeparator + "', but is '" + firstSeparator + "'. idsSequence: " + keySequence);
		else {
			result = keySequence.substring(sepLength);
			return result;
		}
	}
	
}
