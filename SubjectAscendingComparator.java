package Homework5;

import java.util.Comparator;
/**
 * SubjectAscendingComparator class that is invoked to sort a folder in subject ascending order
 * @author Avish Parmar
 * SBUID: 112647892
 * Email: avish.parmar@stonybrook.edu
 * Course: CSE214
 * Recitation: Section 01
 */
@SuppressWarnings("rawtypes")
public class SubjectAscendingComparator implements Comparator {
	/**
	 * Inherited comparator method
	 */
	@Override
	public int compare(Object o1, Object o2) {
		Email e1 = (Email) o1;
		Email e2 = (Email) o2;
		return (e1.getSubject().compareTo(e2.getSubject()));
	}

}
