import java.util.Comparator;
/**
 * DateAscendingComparator class that is invoked to sort a folder in date ascending order
 * @author Avish Parmar
 */
@SuppressWarnings("rawtypes")
public class DateAscendingComparator implements Comparator {
	/**
	 * Inherited comparator method
	 */
	@Override
	public int compare(Object o1, Object o2) {
		Email e1 = (Email) o1;
		Email e2 = (Email) o2;
		return (e1.getDate().compareTo(e2.getDate()));
	}

}
