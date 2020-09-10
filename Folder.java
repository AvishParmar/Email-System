package Homework5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Folder class that creates a Folder object and does operations on it.
 * @author Avish Parmar
 * SBUID: 112647892
 * Email: avish.parmar@stonybrook.edu
 * Course: CSE214
 * Recitation: Section 01
 */
public class Folder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Email> emails;
	private String name;
	private String currentSortingMethod;
	
	/**
	 * Default folder constructor
	 */
	public Folder() {
		setCurrentSortingMethod("Date Descending");
		emails = new ArrayList<Email>();
	}
	
	/**
	 * Folder constructor
	 * @param name
	 * Name of folder
	 */
	public Folder(String name) {
		setName(name);
		setCurrentSortingMethod("Date Descending");
		emails = new ArrayList<Email>();
		
	}
	
	/**
	 * Sets the name of folder
	 * @param name
	 * Name of folder
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of folder
	 * @return name
	 * Name of folder
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the current sorting method of the folder
	 * @param currentSortingMethod
	 * Current sorting method
	 */
	public void setCurrentSortingMethod(String currentSortingMethod) {
		this.currentSortingMethod = currentSortingMethod;
	}
	
	/**
	 * Returns the current sorting method of the folder
	 * @return currentSortingMethod
	 * Current sorting method of the folder
	 */
	public String getCurrentSortingMethod() {
		return this.currentSortingMethod;
	}

	/**
	 * Adds an email to the folder
	 * @param email
	 * Email to add to the folder
	 */
	@SuppressWarnings("unchecked")
	public void addEmail(Email email) {
		emails.add(email);
		if(getCurrentSortingMethod().equals("Subject Ascending")) {
			Collections.sort(emails, new SubjectAscendingComparator());
		}
		else if(getCurrentSortingMethod().equals("Subject Descending")) {
			Collections.sort(emails, new SubjectDescendingComparator());
		}
		else if(getCurrentSortingMethod().equals("Date Ascending")) {
			Collections.sort(emails, new DateAscendingComparator());
		}
		else if(getCurrentSortingMethod().equals("Date Descending")) {
			Collections.sort(emails, new DateDescendingComparator());
		}
	}
	
	/**
	 * Removes emails at the given index
	 * @param index
	 * Index of email
	 * @return emails.remove(index)
	 * Email that has been removed
	 * 
	 */
	public Email removeEmail(int index) {
		if(emails.get(index) == null) {
			return null;
		}
		return emails.remove(index);
	}
	
	/**
	 * Returns number of emails in the folder
	 * @return emails.size();
	 * Number of emails in the folder
	 */
	public int size() {
		return emails.size();
	}
	
	/**
	 * Sorts folder in subject ascending order
	 */
	@SuppressWarnings("unchecked")
	public void sortBySubjectAscending() {
		Collections.sort(emails, new SubjectAscendingComparator());
		setCurrentSortingMethod("Subject Ascending");
		
	}
	
	/**
	 * Sorts folder in subject descending order
	 */
	@SuppressWarnings("unchecked")
	public void sortBySubjectDescending() {
		Collections.sort(emails, new SubjectDescendingComparator());
		setCurrentSortingMethod("Subject Descending");
	}
	
	/**
	 * Sorts folder in date ascending order
	 */
	@SuppressWarnings("unchecked")
	public void sortByDateAscending() {
		Collections.sort(emails, new DateAscendingComparator());
		setCurrentSortingMethod("Date Ascending");
	}
	
	/**
	 * Sorts folder in date descending order
	 */
	@SuppressWarnings("unchecked")
	public void sortByDateDescending() {
		Collections.sort(emails, new DateDescendingComparator());
		setCurrentSortingMethod("Date Descending");
	}
	
	/**
	 * Prints the email at the given index
	 * @param index
	 * Index of the email
	 */
	public void printEmail(int index) {
		System.out.println("\nTo: "+emails.get(index).getTo());
		System.out.println("CC: "+emails.get(index).getCC());
		System.out.println("BCC: "+emails.get(index).getBCC());
		System.out.println("Subject: "+emails.get(index).getSubject());
		System.out.println(emails.get(index).getBody());
	}
	
	/**
	 * toString() method for Folder.
	 * Prints index, time, and subject of the email.
	 */
	public String toString() {

		
		for(int i = 0; i < emails.size(); i++) {
			String str = String.format("%3d%4s%-19s%s%-8s", (i+1),"|",emails.get(i).getDate(),"|", emails.get(i).getSubject());
			System.out.println(str);
		}
		return "";
	}
	
	
	
}
