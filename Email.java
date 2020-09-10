package Homework5;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Email class that creates an Email object and instantiates it.
 * @author Avish Parmar
 * SBUID: 112647892
 * Email: avish.parmar@stonybrook.edu
 * Course: CSE214
 * Recitation: Section 01
 */
public class Email implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String body;
	private GregorianCalendar timestamp;
	private String date;
	
	/**
	 * Email constructor
	 * @param to
	 * Recipient of the email
	 * @param cc
	 * Carbon copy recipients of the email
	 * @param bcc
	 * Blind carbon copy recipients of the emai;
	 * @param subject
	 * Subject of the email
	 * @param body
	 * Body of the email
	 */
	public Email(String to, String cc, String bcc, String subject, String body) {
		setTo(to);
		setCC(cc);
		setBCC(bcc);
		setSubject(subject);
		setBody(body);
		this.timestamp = new GregorianCalendar();
		Date date = timestamp.getTime();
		String pattern = "h:mma M/d/yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String date2 = dateFormat.format(date);
		setDate(date2);
	}
	
	/**
	 * Sets recipient of the email
	 * @param to
	 * Recipient of the email
	 */
	public void setTo(String to) {
		this.to = to;
	}
	
	/**
	 * Returns recipient of the email
	 * @return to
	 * Recipient of the email
	 */
	public String getTo() {
		return this.to;
	}
	
	/**
	 * Sets the carbon copy recipient of the email
	 * @param cc
	 * Carbon copy recipient of the email
	 */
	public void setCC(String cc) {
		this.cc = cc;
	}
	
	/**
	 * Returns carbon copy recipient of the email
	 * @return cc
	 * Carbon copy recipient of the email
	 */
	public String getCC() {
		return this.cc;
	}
	
	/**
	 * Returns blind carbon copy recipients of the email
	 * @param bcc
	 * Blind carbon copy recipient of the email
	 */
	public void setBCC(String bcc) {
		this.bcc = bcc;
	}
	
	/**
	 * Returns blind carbon copy recipients of the email
	 * @return bcc
	 * Blind carbon copy recipient of the email
	 */
	public String getBCC() {
		return this.bcc;
	}
	
	/**
	 * Sets the subject of the email
	 * @param subject
	 * Subject of the email
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Returns subject of the email
	 * @return subject
	 * Subject of the email
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * Sets the body of the email
	 * @param body
	 * Body of the email
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
	/**
	 * Returns body of the email
	 * @return body
	 * Body of the email
	 */
	public String getBody() {
		return this.body;
	}
	
	/**
	 * Sets the date of the email
	 * @param date
	 * Date of the email
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Returns the date of the email
	 * @return date
	 * Date of the email
	 */
	public String getDate() {
		return this.date;
	}
	
	
}
