/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class ActionListenerController {

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event)
			throws LoginException {
		SystemController sc = new SystemController();
		ViewController factoryView = new ViewController();

		Auth author = sc
				.login(UserNameField.getText(), passwordField.getText());
		// actiontarget.setText("Sign in button pressed");
		if (author == Auth.ADMIN) {
			factoryView.setView(Auth.ADMIN);
		} else if (author == Auth.LIBRARIAN) {
			factoryView.setView(Auth.LIBRARIAN);
		} else if (author == Auth.BOTH) {
			factoryView.setView(Auth.BOTH);
		}
	}

	@SuppressWarnings({ "null", "unused" })
	@FXML
	protected void handleSearchMemberButtonAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		SystemController sc = new SystemController();
		LibraryMember member = sc.search(searchedMemberID.getText());

		if (member == null) {
			alert.setContentText(searchedMemberID.getText() + " not found");
			alert.showAndWait();

		} else {
			// alert.setContentText(member.getMember_ID() + " is found");
			// alert.showAndWait();
			Address add = member.getAddress();
			memberIDUpdateText.setText(member.getMember_ID());
			firstNameTextUpdate.setText(member.getFirstName());
			lastNameTextUpdate.setText(member.getLastName());
			streetTextUpdate.setText(add.getStreet());
			cityTextUpdate.setText(add.getCity());
			stateTextUpdate.setText(add.getState());
			zipTextUpdate.setText(add.getZip());
			telephoneTextUpdate.setText(member.getTelephone());
			updateButton.setVisible(true);

		}
	}

	// for updating information based on member_ID

	@FXML
	protected void handleUpdateButtonAction(ActionEvent event) {
		Alert alertconf = new Alert(AlertType.CONFIRMATION);
		SystemController sys = new SystemController();
		alertconf.setContentText("Do you want to update");
		alertconf.showAndWait();
		if (alertconf.getResult() == ButtonType.OK) {
			sys.updateMemberInfo(memberIDUpdateText.getText(),
					firstNameTextUpdate.getText(),
					lastNameTextUpdate.getText(), streetTextUpdate.getText(),
					cityTextUpdate.getText(), stateTextUpdate.getText(),
					zipTextUpdate.getText(), telephoneTextUpdate.getText());
		}

	}

	@FXML
	protected void handleAddMemberButtonAction(ActionEvent event) {

		SystemController sc = new SystemController();
		try {
			sc.addNewMember(memberIDText.getText(), firstnameText.getText(),
					lastnameText.getText(), telephoneText.getText(),
					new Address(streetText.getText(), cityText.getText(),
							stateText.getText(), zipText.getText()));
		} catch (LibrarySystemException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	protected void handleISBNSearchButtonAction(ActionEvent event) {

		DataAccessFacade dataAccess = new DataAccessFacade();
		try {
			Book SearchedBook = dataAccess.searchBook(ISBNidTextBox.getText());
			ObservableList<String> items = FXCollections
					.observableArrayList("ISBN:" + SearchedBook.getIsbn()
							+ " Title: " + SearchedBook.getTitle());
			ListViewBook.setItems(items);
			CopyBookButton.setVisible(true);
		} catch (Exception e) {

		}
	}

	@FXML
	protected void handleCopyToBookAction(ActionEvent event) {
		DataAccessFacade dataAccess = new DataAccessFacade();
		Book SearchedBook = dataAccess.searchBook(ISBNidTextBox.getText());
		SearchedBook.addCopy();
		dataAccess.saveNewBook(SearchedBook);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Copy of Book is successfully added in DB.");
		alert.show();
	}

	@SuppressWarnings("unchecked")
	private void LoadAuthor() {

		ListView<String> list = ListViewAuthor;
		Iterator itr = SystemController.AuthorList.iterator();
		int itemelement = 0;
		ObservableList<String> items = FXCollections.observableArrayList();
		while (itemelement < SystemController.AuthorList.size()) {
			String item = "First Name: "
					+ SystemController.AuthorList.get(itemelement)
							.getFirstName()
					+ " Last Name: "
					+ SystemController.AuthorList.get(itemelement)
							.getLastName()
					+ " City: "
					+ SystemController.AuthorList.get(itemelement).getAddress()
							.getCity()
					+ " State: "
					+ SystemController.AuthorList.get(itemelement).getAddress()
							.getState()
					+ " Zip: "
					+ SystemController.AuthorList.get(itemelement).getAddress()
							.getZip();
			itemelement++;
			items.add(item);
		}
		list.setItems(items);
	}

	@FXML
	protected void AddBookButtonAction(ActionEvent event) {
		SystemController sc = new SystemController();
		try {
			sc.addBook(isBNFieldBook.getText(), titleFieldBook.getText(),
					Integer.parseInt(MaxCharFieldBook.getText()),
					SystemController.AuthorList);
			SendMessage("Book Is Successfully Uploaded");
		} catch (NumberFormatException | LibrarySystemException e) {
			// TODO Auto-generated catch block
			SendMessage("SomeThing is mistake while adding book");
		}
		new ViewController().SetViewAddNewBook();

	}

	@FXML
	private TextField checkOutISBN;
	@FXML private ListView<String> ListViewOverDueBook;
	
	@FXML
	protected void handleViewCheckoutHistryButtonAction(ActionEvent event){
		DataAccessFacade dataAccess = new DataAccessFacade();
		searchedlibraryMem = dataAccess.searchMember(memIdText
				.getText());
		checkOutRecord=searchedlibraryMem.getCheckoutrecord();
		ListViewCheckOutBook.setVisible(true);
		ListView<String> list = ListViewCheckOutBook;
		ObservableList<String> items = FXCollections
				.observableArrayList();

		ArrayList<CheckoutRecordEntry> checkoutRecordEntry = checkOutRecord
				.getChkoutRecEntry();
		if (checkoutRecordEntry != null) {
			for (CheckoutRecordEntry entry : checkoutRecordEntry) {
				String item = "Memeber Name: "
						+ searchedlibraryMem.getFirstName()
						+ ' '
						+ searchedlibraryMem.getFirstName()
						+ '\n'
						+ "BookISBN: "
						+ entry.getBkCopy().getBook().getIsbn()
						+ '\n'
						+ "Book Title: "
						+ entry.getBkCopy().getBook()
								.getTitle() + '\n'
						+ "Checkout Date: "
						+ entry.getCheckoutDate() + '\n'
						+ "Due Date: " + entry.getDueDate();

				items.add(item);
			}

			list.setItems(items);
		}

	}
	
	@FXML
	protected void handleCheckoutHistryButtonAction(ActionEvent event) {
		
		ListView<String> list = ListViewOverDueBook;
		ObservableList<String> items = FXCollections.observableArrayList();
		HashMap<LibraryMember, List<BookCopy>> DueMemberInfo=GetOverDueBooksAndMember();
		
		Iterator itr=DueMemberInfo.keySet().iterator();
		while(itr.hasNext()){
			
			for( BookCopy bookcopy:   DueMemberInfo.get(itr.next())){
			
			String item = "Memeber Name: "
					+ ((LibraryMember)itr.next()).getFirstName()  + '\n'
					
					+ "BookISBN: " + bookcopy.getBook().getIsbn()
					+ '\n' + "Book Title: "
					+  bookcopy.getBook().getTitle() + '\n'
					+ "Copy Number: " +  bookcopy.getBook().getNumCopies();
					

			items.add(item);
		}
			
		}
		list.setItems(items);
	

	}

	private HashMap<LibraryMember, List<BookCopy>> GetOverDueBooksAndMember() {
		Date date = new Date();
		int compareDate = 0;
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> membermap = da.readMemberMap();
		List<LibraryMember> libmem = new ArrayList<LibraryMember>();
		//List<BookCopy> overduebooks = new ArrayList<BookCopy>();
		HashMap<LibraryMember, List<BookCopy>> overduebookAndMem = new HashMap<LibraryMember, List<BookCopy>>();
		List<CheckoutRecordEntry> checkentry;
		Iterator itr = membermap.keySet().iterator();
		while (itr.hasNext()) {
			libmem.add(membermap.get(itr.next()));
		}

		for (LibraryMember lib : libmem) {
			 Boolean Addrecord=false;
			 List<BookCopy> overduebooks = new ArrayList<BookCopy>();
			if(lib.getCheckoutrecord()!=null){
			checkentry = lib.getCheckoutrecord().getChkoutRecEntry();
			for (CheckoutRecordEntry entry : checkentry) {
				compareDate = date.compareTo(entry.getDueDate());
				if (entry.getBkCopy().getBook().getIsbn()
						.equalsIgnoreCase(checkOutISBN.getText())
						&& compareDate > 0) {
					overduebooks.add(entry.getBkCopy());
					Addrecord=true;
				}

			}
			if(Addrecord)
			overduebookAndMem.put(lib, overduebooks);
		 }
		}
		return overduebookAndMem;
	}

	@FXML
	protected void handleCheckoutHistryViewLoad(ActionEvent event) {
		
		new ViewController().setviewCheckoutHistry();
	}

	@FXML
	protected void handleCheckoutBookButtonAction(ActionEvent event) {
		
		CheckOutBook();
	}

	private CheckOutRecord CheckOutBook() {
		DataAccessFacade dataAccess = new DataAccessFacade();
		try {
			ArrayList<String> memBookISBN = new ArrayList<String>();
			StringBuilder sb = new StringBuilder();

			if ((memIdText.getText().isEmpty() || isbnText.getText().isEmpty())) {
				sb.append("Both fields are required\n");
			} else {
				searchedlibraryMem = dataAccess.searchMember(memIdText
						.getText());
				searchedBookMy = dataAccess.searchBook(isbnText.getText());

				if (searchedlibraryMem.getCheckoutrecord() != null) {
					ArrayList<CheckoutRecordEntry> sMemCheckoutRec = searchedlibraryMem
							.getCheckoutrecord().getChkoutRecEntry();
					if (sMemCheckoutRec != null) {
						for (CheckoutRecordEntry entry : sMemCheckoutRec) {
							BookCopy bkcopy = entry.getBkCopy();
							if (bkcopy != null) {
								memBookISBN.add(bkcopy.getBook().getIsbn());
							}
						}
					}
				}
			
				if (searchedlibraryMem == null) {
					sb.append("Member not found\n");
				} else if (searchedBookMy == null ) {
					sb.append("Book not found\n");
				} else if (memBookISBN.contains(isbnText.getText())) {
					sb.append("You have already added this book: "
							+ isbnText.getText());
				} else {
					if (searchedBookMy.isAvailable()) {
						BookCopy nextAvailCopy = searchedBookMy
								.getNextAvailableCopy();
						int maxCheckoutLen = searchedBookMy
								.getMaxCheckoutLength();

						Date today = new Date();

						Calendar c = Calendar.getInstance();
						c.setTime(new Date()); // Now use today date.
						c.add(Calendar.DATE, maxCheckoutLen); // Adding 21 days
						Date output = c.getTime();

						CheckOutRecord chkRec = searchedlibraryMem.checkout(
								nextAvailCopy, today, output);
						
						dataAccess.updateMember(searchedlibraryMem);
						dataAccess.saveNewBook(searchedBookMy);
						
						DataAccess da = new DataAccessFacade();
						HashMap<String, LibraryMember> map = da.readMemberMap();
						checkOutRecord=chkRec;
						SendMessage("You have Successfully CheckedOut the book. Thank you.!!!!");
						

					} else {
						sb.append("Book copy is not available");
					}
				}
			}
			
			actiontErrorMessages.setText(sb.toString());
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return  checkOutRecord;
	}
	private CheckOutRecord checkOutRecord;
	@FXML
	protected void SearchBookAndCopyHandler(ActionEvent event) {
		new ViewController().SetViewSearchBookAndCopy();
	}

	@FXML
	protected void SearchAndUpdateLibraryMemberHandler(ActionEvent event) {
		new ViewController().SetViewSearchAndUpdateLibraryMember();
	}

	@FXML
	protected void AddNewMemberHandler(ActionEvent event) {
		new ViewController().setViewAddLibraryMember();
	}

	@FXML
	protected void AddAuthorViewButtonAction(ActionEvent event) {
		SystemController.previousScene = SystemController.stageArea.getScene();
		new ViewController().SetViewAddAuthor();
	}

	@FXML
	protected void ExitSystemHandler(ActionEvent event) {
		new ViewController().SetViewLogOutSystem();
	}

	@FXML
	protected void handleCheckoutBookViewLoad(ActionEvent event) {
		new ViewController().SetViewCheckoutBook();
	}

	@FXML
	protected void AddNewBookHandler(ActionEvent event) {
		new ViewController().SetViewAddNewBook();
	}

	@FXML
	protected void ViewAuthorButtonAction(ActionEvent event) {
		LoadAuthor();
	}

	@FXML
	protected void AddAuthorButtonAction(ActionEvent event) {
		new ViewController()
				.SetViewBookAfterAuthorAdd(SystemController.previousScene);

		SystemController.AuthorList.add(new Author(FirstNameText.getText(),
				LastNameText.getText(), AuthorCredentialText.getText(),
				new Address(AuthorAddressText.getText(), AuthorZipText
						.getText(), AuthorCityText.getText(), AuthorZipText
						.getText()), AuthorCredentialText.getText()));

	}

	private void SendMessage(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(message);
		alert.show();
	}

	// for login
	@FXML
	private Text actiontarget;
	@FXML
	private TextField UserNameField;
	@FXML
	private TextField passwordField;

	// for searching library member
	@FXML
	private TextField firstNameTextUpdate;
	@FXML
	private TextField lastNameTextUpdate;
	@FXML
	private TextField streetTextUpdate;
	@FXML
	private TextField cityTextUpdate;
	@FXML
	private TextField stateTextUpdate;
	@FXML
	private TextField zipTextUpdate;
	@FXML
	private TextField telephoneTextUpdate;
	@FXML
	private TextField memberIDUpdateText;

	@FXML
	private TextField searchedMemberID;
	@FXML
	private Button updateButton;
	// All Addmember

	@FXML
	private TextField firstnameText;
	@FXML
	private TextField lastnameText;
	@FXML
	private TextField streetText;
	@FXML
	private TextField cityText;
	@FXML
	private TextField stateText;
	@FXML
	private TextField zipText;
	@FXML
	private TextField telephoneText;
	@FXML
	private TextField memberIDText;
	// for SearchBOok and add copy
	@SuppressWarnings("rawtypes")
	@FXML
	private ListView ListViewBook;
	@FXML
	private Button CopyBookButton;
	Book SearchedBook;
	// AddBOok Action listeners:

	@FXML
	private TextField FirstNameText;
	@FXML
	private TextField LastNameText;
	@FXML
	private TextField AuthorCredentialText;
	@FXML
	private TextField AuthorPhoneText;
	@FXML
	private TextField AuthorCityText;
	@FXML
	private TextField AuthorAddressText;
	@FXML
	private TextField AthourStateText;
	@FXML
	private TextField AuthorZipText;
	@FXML
	private ListView<String> ListViewAuthor;
	// @FXML private TableView<String> TableViewAuthor;
	// for Checkout
	@FXML
	private TextField memIdText;
	@FXML
	private TextField isbnText;
	@FXML
	private Text actiontErrorMessages;
	@FXML
	private ListView<String> ListViewCheckOutBook;
	LibraryMember searchedlibraryMem;
	Book searchedBookMy;

	// for Add book
	@FXML
	private TextField MaxCharFieldBook;
	@FXML
	private TextField isBNFieldBook;
	@FXML
	private TextField titleFieldBook;
	@FXML
	private TextField ISBNidTextBox;

}
