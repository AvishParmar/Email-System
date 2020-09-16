import java.util.Comparator;
/**
 * SubjectDescendingComparator class that is invoked to sort a folder in subject descending order
 * @author Avish Parmar
 */
@SuppressWarnings("rawtypes")
public class SubjectDescendingComparator implements Comparator {
	/**
	 * Inherited comparator method
	 */
	@Override
	public int compare(Object o1, Object o2) {
		Email e1 = (Email) o1;
		Email e2 = (Email) o2;
		return (e2.getSubject().compareTo(e1.getSubject()));
	}

	

}
