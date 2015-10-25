package business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.AnchorPaneBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static Stage stageArea;
	public static List<Author> AuthorList = new ArrayList<Author>();
	public static Scene previousScene;
	public static Node ParentScreen;

	@Override
	public Auth login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException(
					"Passord does not match password on record");
		}
		currentAuth = map.get(id).getAuthorization();
		return currentAuth;
	}

	/**
	 * This method checks if memberId already exists -- if so, it cannot be
	 * added as a new member, and an exception is thrown. If new, creates a new
	 * LibraryMember based on input data and uses DataAccess to store it.
	 * 
	 */
	public void addNewMember(String memberId, String firstName,
			String lastName, String telNumber, Address addr)
			throws LibrarySystemException {
		try {
			CheckOutRecord c = null;
			DataAccess da = new DataAccessFacade();
			HashMap<String, LibraryMember> map = da.readMemberMap();
			if (map.containsKey(memberId)) {
				throw new LibrarySystemException("Member ID" + memberId
						+ " already added");
			}

			else {
				da.saveNewMember(new LibraryMember(firstName, lastName,
						telNumber, addr, memberId, c));
			}

		}

		catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("This " + memberId + " is already added");
			alert.showAndWait();
		}

	}

	/**
	 * Reads data store for a library member with specified id. Ids begin at
	 * 1001... Returns a LibraryMember if found, null otherwise
	 * 
	 */
	public LibraryMember search(String memberId) {
		DataAccess da = new DataAccessFacade();
		return da.searchMember(memberId);
	}

	/**
	 * Same as creating a new member (because of how data is stored)
	 */
	public void updateMemberInfo(String memberId, String firstName,
			String lastName, String street, String city, String state,
			String zip, String telephone) {
		Alert alert = new Alert(AlertType.INFORMATION);
		try {
			LibraryMember member = search(memberId);
			if (member != null) {
				member.setFirstName(firstName);
				member.setLastName(lastName);
				member.setTelephone(telephone);
				Address a = member.getAddress();
				a.setStreet(street);
				a.setCity(city);
				a.setState(state);
				a.setZip(zip);
				DataAccess da = new DataAccessFacade();
				da.updateMember(member);
				alert.setContentText(memberId + " succesfully updated");
				alert.showAndWait();
			} else {

				alert.setContentText(memberId + " not found");
				alert.showAndWait();
			}
		} catch (Exception e) {

			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}

	/**
	 * Looks up Book by isbn from data store. If not found, an exception is
	 * thrown. If no copies are available for checkout, an exception is thrown.
	 * If found and a copy is available, member's checkout record is updated and
	 * copy of this publication is set to "not available"
	 */
	/*
	 * public void checkoutBook(String memberId, String isbn) throws
	 * LibrarySystemException { }
	 */
	@Override
	public Book searchBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		return da.searchBook(isbn);
	}

	/**
	 * Looks up book by isbn to see if it exists, throw exceptioni. Else add the
	 * book to storage
	 */
	public boolean addBook(String isbn, String title, int maxCheckoutLength,
			List<Author> authors) throws LibrarySystemException {
		Book book = searchBook(isbn);
		if (book != null)
			throw new LibrarySystemException("This book is alraedy in " + isbn
					+ "  library collection!");
		else if (book == null) {
			DataAccessFacade dataaccess = new DataAccessFacade();
			dataaccess.saveNewBook(new Book(isbn, title, maxCheckoutLength,
					authors));
		}
		return true;
	}

	public boolean addBookCopy(String isbn) throws LibrarySystemException {
		Book book = searchBook(isbn);
		if (book == null)
			throw new LibrarySystemException("No book with isbn " + isbn
					+ " is in the library collection!");
		book.addCopy();
		return true;
	}

	@Override
	public void checkoutBook(String memberId, String isbn)
			throws LibrarySystemException {
		LibraryMember member;
		Book book;
		member = search(memberId);
		book = searchBook(isbn);
		BookCopy[] bookcopies = book.getCopies();
		if (member != null && book != null && bookcopies.length != 0) {

		}
	}

}
