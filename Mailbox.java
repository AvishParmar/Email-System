import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Mailbox class that loads or creates a mailbox object and drives the program.
 * @author Avish Parmar
 */
public class Mailbox implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Folder inbox = new Folder();
	private Folder trash = new Folder();
	private ArrayList<Folder> folders = new ArrayList<Folder>();
	public static Mailbox mailbox;

	/**
	 * Default constructor
	 */
	public Mailbox() {
	
	}
	
	/**
	 * Adds a folder to the mail box
	 * @param folder
	 * New folder
	 */
	public void addFolder(Folder folder) {
		folders.add(folder);
	}
	
	/**
	 * Deletes a folder from the mail box
	 * @param name
	 * Name of the folder to delete
	 */
	public void deleteFolder(String name) {
		for(int i = 0; i < folders.size(); i++) {
			if(folders.get(i).getName().equals(name)) {
				Folder folder = folders.remove(i);
				System.out.println(folder.getName()+" deleted from mailbox.");
				return;
			}
		}
		
		System.out.println("Folder "+name+" does not exist in the mailbox.");
		
	}
	
	/**
	 * Composes an email and adds it to the current folder
	 */
	@SuppressWarnings("resource")
	public void composeEmail() {
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter recipient (To) : ");
		String recipient = input.nextLine();
		
		while(recipient.matches(".*?[@].*?[.].*") == false || recipient.contains(" ")) {
			System.out.print("Invalid email, please re-enter:");
			recipient = input.nextLine();
		}
		System.out.println();
		
		System.out.print("Enter carbon copy recipients (CC) : ");
		String cc = input.nextLine();
		
		while(cc.matches(".*?[@].*?[.].*?") == false) {
			System.out.print("Invalid email, please re-enter:");
			cc = input.nextLine();
		}
		if(cc.contains(",")) {
			while(cc.matches(".*?[,].*?[@].*?[.].*?") == false) {
				System.out.print("Invalid email, please re-enter:");
				cc = input.nextLine();
			}
		}
		System.out.println();
		
		System.out.print("Enter blind carbon copy recipients (BCC) : ");
		String bcc = input.nextLine();
		while(bcc.matches(".*?[@].*?[.].*?") == false) {
			System.out.print("Invalid email, please re-enter:");
			bcc = input.nextLine();
		}
		if(bcc.contains(",")) {
			while(bcc.matches(".*?[,].*?[@].*?[.].*?") == false) {
				System.out.print("Invalid email, please re-enter:");
				bcc = input.nextLine();
			}
		}
		System.out.println();
		
		System.out.print("Enter subject line : ");
		String subject = input.nextLine();
		System.out.println();
		if(subject.length() == 0) {
			subject = "No Subject";
		}
		System.out.print("Enter body : ");
		String body = input.nextLine();
	
		
		mailbox.inbox.addEmail(new Email(recipient, cc, bcc, subject, body));
		System.out.println("\nEmail successfully added to Inbox.");
	}
	
	/**
	 * Deletes a email from the given folder
	 * @param email
	 * Email to delete
	 */
	public void deleteEmail(Email email) {
		mailbox.trash.addEmail(email);
	}
	
	/**
	 * Deletes all emails in the trash
	 */
	public void clearTrash() {
		int count = 0;
		
		while(trash.size() != 0) {
			trash.removeEmail(trash.size()-1);
			count++;
		}
		
		System.out.println("\n"+count+" item(s) successfully deleted.");
	}
	
	/**
	 * Moves email from one folder to another
	 * @param email
	 * Email to move
	 * @param target
	 * Folder to move email to
	 */
	public void moveEmail(Email email, Folder target) {
		
		if(target.equals(mailbox.inbox)) {
			mailbox.inbox.addEmail(email);
		}
		else if(target.equals(mailbox.trash)) {
			mailbox.trash.addEmail(email);
		}
		else {
			mailbox.getFolder(target.getName()).addEmail(email);
		}
		
	}
	
	/**
	 * Returns the folder with the given name
	 * @param name
	 * Name of folder
	 * @return
	 * Folder with given name
	 * Null if folder with given name does not exist
	 */
	public Folder getFolder(String name) {
		for(int i = 0; i < folders.size(); i++) {
			if(folders.get(i).getName().equals(name))
				return folders.get(i);
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		
		
		try {
			 FileInputStream file = new FileInputStream("mailbox.obj");
			 ObjectInputStream fin = new ObjectInputStream(file); 
			 mailbox = (Mailbox) fin.readObject();
			 System.out.println("Loading data from mailbox.obj...");
			
			 fin.close();
			
		} catch (IOException e) {
			System.out.println("Previous save not found starting with an empty mailbox.");
		
			try {
				mailbox = new Mailbox();
				FileOutputStream file = new FileOutputStream("mailbox.obj");
				ObjectOutputStream fout = new ObjectOutputStream(file); 
				fout.writeObject(mailbox);
				fout.close();
			} catch (FileNotFoundException e1) {
				System.out.println("File Not found!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		;
		while(true) {
			
			System.out.println("\nMailbox:\n"+ 
					"--------" + 
					"\r\n" + 
					"Inbox\n" + 
					"Trash");
			
			for(int i = 0; i < mailbox.folders.size(); i++) {
				System.out.println(mailbox.folders.get(i).getName());
			}
			
			System.out.print(" \r\n" + 
					"A – Add folder\r\n" + 
					"R – Remove folder\r\n" +
					"C – Compose email\r\n" +
					"F – Open folder\r\n" + 
					"I – Open Inbox\r\n" +
					"T – Open Trash\r\n" +
					"E - Empty Trash\r\n"+
					"Q – Quit\n");
			
			System.out.println();
			
			System.out.print("Enter a user option: ");
			String option = stdin.nextLine();
			
			if(option.equalsIgnoreCase("A")) {
				System.out.print("\nEnter folder name: ");
				
				String folder = stdin.nextLine();
				mailbox.addFolder(new Folder(folder));
				
				System.out.println("Folder "+folder+" created successfully!");
			}
			else if(option.equalsIgnoreCase("R")) {
				System.out.print("\nEnter folder name: ");
				
				String folder = stdin.nextLine();
				
				mailbox.deleteFolder(folder);
				
			}
			else if(option.equalsIgnoreCase("C")) {
				
				mailbox.composeEmail();
			}
			else if(option.equalsIgnoreCase("F")) {
				System.out.print("\nEnter folder name: ");
				String folder = stdin.nextLine();
				boolean flag = false;
				Folder temp = null;
				temp = mailbox.getFolder(folder);
				
				if(temp != null)
					flag = true;
				
				while(flag) {
					
					System.out.println("\n"+temp.getName()+"\n");
					if(temp.size() != 0) {
						System.out.println("Index |        Time       | Subject\r\n" +  
								"-----------------------------------");
						temp.toString();
					}
					else
						System.out.println(temp.getName()+" is empty.\n");
					
					
					System.out.println("\nM – Move email\r\n" +
							"D – Delete email\r\n" +
							"V – View email contents\r\n" +
							"SA – Sort by subject line in ascending order\r\n"+
							"SD – Sort by subject line in descending order\r\n"+
							"DA – Sort by date in ascending order\r\n" +
							"DD – Sort by date in descending order\r\n" + 
							"R – Return to mailbox\n");
					
					System.out.print("Enter a user option: ");
					String selection = stdin.nextLine();
					
					if(selection.equalsIgnoreCase("M")) {
						
						if(temp.size() != 0) {
							
							
							System.out.println();
							System.out.print("Enter the index of the email to move : ");
							while(!stdin.hasNextInt()) {
								System.out.print("\nInvalid Index, please re-enter: ");
								stdin.next();
							}
							int index = stdin.nextInt();
							while((index <= 0) || (index >= temp.size()+1)) {
								System.out.println("Invalid Index, please re-enter: ");
								index = stdin.nextInt();
							}
							
							Email temp2 = temp.removeEmail(index-1);
							System.out.println("\nFolders:\r\n"+
												"Inbox\r\n"+
												"Trash");
							for(int i = 0; i < mailbox.folders.size(); i++) {
								System.out.print(mailbox.folders.get(i).getName()+"\n");
							}
							
							System.out.println();
							System.out.print("Select a folder to move \""+temp2.getSubject()+"\" to: ");
							stdin.nextLine();
							String folder2 = stdin.nextLine();
							
							if(folder2.equals("Inbox")) {
								mailbox.moveEmail(temp2, mailbox.inbox);
								System.out.println("\n\""+temp2.getSubject()+"\" successfully moved to Inbox");
							}
							else if(folder2.equals("Trash")) {
								mailbox.moveEmail(temp2, mailbox.trash);
								System.out.println("\n\""+temp2.getSubject()+"\" successfully moved to Trash");
							}
							else {
								Folder tempFolder = mailbox.getFolder(folder2);
								if(tempFolder == null) {
									System.out.println("\nFolder not found!");
									temp.addEmail(temp2);
								}
								else {
									tempFolder.addEmail(temp2);
									System.out.println("\n\""+temp2.getSubject()+"\" successfully move to "+tempFolder.getName());
								}
							}
							
						}
						else
							System.out.println("\n"+temp.getName()+" is empty.");
					}	
				
			
					else if(selection.equalsIgnoreCase("D")) {
						if(temp.size() != 0) {
							System.out.print("\nEnter email index: ");
							while(!stdin.hasNextInt()) {
								System.out.print("\nInvalid Index, please re-enter: ");
								stdin.next();
							}
							int index = stdin.nextInt();
							
							while((index <= 0) || (index >= temp.size()+1)) {
								System.out.print("\nInvalid Index, please re-enter: ");
								index = stdin.nextInt();
							}
							Email email = temp.removeEmail(index-1);
							mailbox.deleteEmail(email);
							
							System.out.println("\n\""+email.getSubject()+"\" has successfully been moved to the trash.");

						}
						else
							System.out.print("\n"+temp.getName()+" is Empty.");
						stdin.nextLine();
					}
					else if(selection.equalsIgnoreCase("V")) {
						if(temp.size() != 0) {
							System.out.print("\nEnter email index: ");
							while(!stdin.hasNextInt()) {
								System.out.print("\nInvalid Index, please re-enter: ");
								stdin.next();
							}
							int index = stdin.nextInt();
							while((index <= 0) || (index >= temp.size()+1)) {
								System.out.print("\nInvalid Index, please re-enter: ");
								index = stdin.nextInt();
							}
							
							temp.printEmail(index-1);
							stdin.nextLine();
						}
						else {
							System.out.println("\n"+temp.getName()+" is empty.");
						}
					}
					else if(selection.equalsIgnoreCase("SA")) {
						temp.sortBySubjectAscending();
					}
					else if(selection.equalsIgnoreCase("SD")) {
						temp.sortBySubjectDescending();
					}
					else if(selection.equalsIgnoreCase("DA")) {
						temp.sortByDateAscending();
					}	
					else if(selection.equalsIgnoreCase("DD")) {
						temp.sortByDateDescending();
					}
					else if(selection.equalsIgnoreCase("R")) {
						System.out.print("\nReturning to main menu!");
						break;
					}
					
				}
				
				if(flag == false) {
					System.out.println("\nFolder not found!");
				}
			}
			else if(option.equalsIgnoreCase("I")) {
				
				while(true) {
					
					System.out.println("\nInbox\n");
					if(mailbox.inbox.size() != 0) {
						System.out.println("Index |        Time       | Subject\r\n" +  
								"-----------------------------------");
						mailbox.inbox.toString();
					}
					else 
						System.out.println("Inbox is empty.\n");
					
					System.out.println("\nM – Move email\r\n" +
							"D – Delete email\r\n" +
							"V – View email contents\r\n" +
							"SA – Sort by subject line in ascending order\r\n"+
							"SD – Sort by subject line in descending order\r\n"+
							"DA – Sort by date in ascending order\r\n" +
							"DD – Sort by date in descending order\r\n" + 
							"R – Return to mailbox\n");
					
					System.out.print("Enter a user option: ");
					
					String selection = stdin.nextLine();
				
					if(selection.equalsIgnoreCase("M")) {
						
						if(mailbox.inbox.size() != 0) {
							
							System.out.println();
							System.out.print("Enter the index of the email to move : ");
							while(!stdin.hasNextInt()) {
								System.out.print("\nInvalid Index, please re-enter: ");
								stdin.next();
							}
							int index = stdin.nextInt();
							while((index >= mailbox.inbox.size()+1) || index <= 0){
								System.out.print("\nInvalid Index, please re-enter: ");
								index = stdin.nextInt();
							}
							 
							Email temp = mailbox.inbox.removeEmail(index-1);
							System.out.println("\nFolders:\r\n"+
									"Inbox\r\n"+
									"Trash");
							for(int i = 0; i < mailbox.folders.size(); i++) {
								System.out.print(mailbox.folders.get(i).getName()+"\n");
							}
				
							System.out.println();
							System.out.print("Select a folder to move "+"\""+temp.getSubject()+"\" to: ");
							stdin.nextLine();
							String folder = stdin.nextLine();
							
							if(folder.equals("Inbox")) {
								mailbox.moveEmail(temp, mailbox.inbox);
								System.out.println("\n\""+temp.getSubject()+"\" successfully moved to Inbox");
							}
							else if(folder.equals("Trash")) {
								mailbox.moveEmail(temp, mailbox.trash);
								System.out.println("\n\""+temp.getSubject()+"\" successfully moved to Trash");
							}
							else {
								Folder tempFolder = mailbox.getFolder(folder);
								if(tempFolder == null) {
									System.out.println("Folder not found!");
									mailbox.inbox.addEmail(temp);
								}
								else {
									tempFolder.addEmail(temp);
									System.out.println("\n\""+temp.getSubject()+"\" successfully move to "+tempFolder.getName());
								}
							}
						}
						else
							System.out.println("\nInbox is Empty.");
					}
					else if(selection.equalsIgnoreCase("D")) {
						if(mailbox.inbox.size() != 0) {
							System.out.print("\nEnter email index: ");
							while(!stdin.hasNextInt()) {
								System.out.print("\nInvalid Index, please re-enter: ");
								stdin.next();
							}
							int index = stdin.nextInt();
							
							while((index <= 0) || (index >= mailbox.inbox.size()+1)) {
								System.out.print("\nInvalid Index, please re-enter: ");
								index = stdin.nextInt();
							}
							Email email = mailbox.inbox.removeEmail(index-1);
							mailbox.deleteEmail(email);
							
							System.out.println("\n\""+email.getSubject()+"\" has successfully been moved to the trash.");
						}
						else
							System.out.print("\nInbox is Empty.");
						stdin.nextLine();
					}
					else if(selection.equalsIgnoreCase("V")) {
						if(mailbox.inbox.size() != 0) {
							System.out.print("\nEnter email index: ");
							while(!stdin.hasNextInt()) {
								System.out.print("\nInvalid Index, please re-enter: ");
								stdin.next();
							}
							int index = stdin.nextInt();
							while((index <= 0) || (index >= mailbox.inbox.size()+1)) {
								System.out.print("\nInvalid Index, please re-enter: ");
								index = stdin.nextInt();
							}
							
							mailbox.inbox.printEmail(index-1);
							stdin.nextLine();
						}
						else
							System.out.println("\nInbox is Empty");
						
					}
					else if(selection.equalsIgnoreCase("SA")) {
						mailbox.inbox.sortBySubjectAscending();
					}
					else if(selection.equalsIgnoreCase("SD")) {
						mailbox.inbox.sortBySubjectDescending();
					}
					else if(selection.equalsIgnoreCase("DA")) {
						mailbox.inbox.sortByDateAscending();
					}	
					else if(selection.equalsIgnoreCase("DD")) {
						mailbox.inbox.sortByDateDescending();
					}
					else if(selection.equalsIgnoreCase("R")) {
						System.out.print("\nReturning to main menu!");
						break;
					}
					
				}
			}
			else if(option.equalsIgnoreCase("T")) {
				while(true) {
					System.out.println("\nTrash\n");
					
					if(mailbox.trash.size() != 0) {
						System.out.println("Index |        Time       | Subject\r\n" +  
								"-----------------------------------");
						mailbox.trash.toString();
					}
					else
						System.out.println("Trash is empty.\n");
					
					
					System.out.println("\nM – Move email\r\n" +
							"D – Delete email\r\n" +
							"V – View email contents\r\n" +
							"SA – Sort by subject line in ascending order\r\n"+
							"SD – Sort by subject line in descending order\r\n"+
							"DA – Sort by date in ascending order\r\n" +
							"DD – Sort by date in descending order\r\n" + 
							"R – Return to mailbox\n");
					
					System.out.print("Enter a user option: ");
					String selection = stdin.nextLine();
					
					if(selection.equalsIgnoreCase("M")) {
						if(mailbox.trash.size() != 0) {
							
							System.out.println();
							System.out.print("Enter the index of the email to move : ");
							while(!stdin.hasNextInt()) {
								System.out.print("\nInvalid Index, please re-enter: ");
								stdin.next();
							}
							int index = stdin.nextInt();
							while((index >= mailbox.trash.size()+1) || index <= 0){
								System.out.print("Please input a correct index: ");
								index = stdin.nextInt();
							}
							 
							Email temp = mailbox.trash.removeEmail(index-1);
							System.out.println("\nFolders:\r\n"+
									"Inbox\r\n"+
									"Trash");
							for(int i = 0; i < mailbox.folders.size(); i++) {
								System.out.print(mailbox.folders.get(i).getName()+"\n");
							}
				
							System.out.println();
							System.out.print("Select a folder to move \""+temp.getSubject()+"\" to: ");
							stdin.nextLine();
							String folder = stdin.nextLine();
							
							if(folder.equals("Inbox")) {
								mailbox.moveEmail(temp, mailbox.inbox);
								System.out.println("\n\""+temp.getSubject()+"\" successfully moved to Inbox");
							}
							else if(folder.equals("Trash")) {
								mailbox.moveEmail(temp, mailbox.trash);
								System.out.println("\n\""+temp.getSubject()+"\" successfully moved to Trash");
							}
							else {
								Folder tempFolder = mailbox.getFolder(folder);
								if(tempFolder == null) {
									System.out.println("Folder not found!");
									mailbox.trash.addEmail(temp);
								}
								else {
									tempFolder.addEmail(temp);
									System.out.println("\n\""+temp.getSubject()+"\" successfully move to "+tempFolder.getName());
								}
							}
						}
						else
							System.out.println("\nTrash is Empty.");
					}
					else if(selection.equalsIgnoreCase("D")) {
						System.out.println("\nEmail cannot be deleted as it is already in the trash folder!");
						
					}
					else if(selection.equalsIgnoreCase("V")) {
						if(mailbox.trash.size() != 0) {
							System.out.print("\nEnter email index: ");
							while(!stdin.hasNextInt()) {
								System.out.print("\nInvalid Index, please re-enter: ");
								stdin.next();
							}
							int index = stdin.nextInt();
							while((index <= 0) || (index >= mailbox.trash.size()+1)) {
								System.out.print("\nInvalid Index, please re-enter: ");
								index = stdin.nextInt();
							}
							
							mailbox.trash.printEmail(index-1);
							stdin.nextLine();
						}
						else {
							System.out.println("\nTrash is empty");
						}
						
					}
					else if(selection.equalsIgnoreCase("SA")) {
						mailbox.trash.sortBySubjectAscending();
					}
					else if(selection.equalsIgnoreCase("SD")) {
						mailbox.trash.sortBySubjectDescending();
					}
					else if(selection.equalsIgnoreCase("DA")) {
						mailbox.trash.sortByDateAscending();
					}	
					else if(selection.equalsIgnoreCase("DD")) {
						mailbox.trash.sortByDateDescending();
					}
					else if(selection.equalsIgnoreCase("R")) {
						
						System.out.print("\nReturning to main menu!");
						break;
					}
					
				}
			}
			else if(option.equalsIgnoreCase("E")) {
				mailbox.clearTrash();
			}
			else if(option.equalsIgnoreCase("Q")) {
				FileOutputStream fileOut;
				try {
					fileOut = new FileOutputStream("mailbox.obj");
					ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			        objectOut.writeObject(mailbox);
			        objectOut.close();
				} catch (FileNotFoundException e) {
					System.out.println("File not found! Unable to save.");
				} catch (IOException e) {
					System.out.println("An IOException occurred, unable to save");
				}
		        System.out.println("Program successfully exited and mailbox saved.");
		        stdin.close();
		        System.exit(1);
			}
			
		}
	}
	
	
}
