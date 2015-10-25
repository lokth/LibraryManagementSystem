package business;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import dataaccess.Auth;

public final class ViewController {

	private HashMap<ViewType, String> GetViewHashMap = new HashMap<ViewType, String>();

	public HashMap<ViewType, String> getGetViewHashMap() {
		return GetViewHashMap;
	}

	public String getCSSLocation() {
		return CSSLocation;
	}

	private String CSSLocation = "..\\Style\\Style.css";

	public ViewController() {
		// TODO Auto-generated constructor stub
		GetViewHashMap.put(ViewType.Admin, "..\\View\\Admin.fxml");
		GetViewHashMap.put(ViewType.LIBRARIAN, "..\\View\\Librarian.fxml");
		GetViewHashMap.put(ViewType.BOTH, "..\\View\\AdminLibrarian.fxml");
		GetViewHashMap.put(ViewType.AddMember,
				"..\\View\\AddLibraryMember.fxml");
		GetViewHashMap.put(ViewType.UpdateMember,
				"..\\View\\SearchAndUpdateMember.fxml");
		GetViewHashMap.put(ViewType.SearchBook, "..\\View\\SearchBook.fxml");
		GetViewHashMap.put(ViewType.AddBook, "..\\View\\AddNewBook.fxml");
		GetViewHashMap.put(ViewType.AddAuthor, "..\\View\\AddAuthor.fxml");
		GetViewHashMap.put(ViewType.CheckOut, "..\\View\\Checkout.fxml");
		GetViewHashMap.put(ViewType.Login, "..\\View\\Login.fxml");
		GetViewHashMap.put(ViewType.viewHistry, "..\\View\\SearchCheckOutEntries.fxml");
		

	}

	public void setView(Auth author) {

		try {
			if (author == Auth.ADMIN) {
				Parent adminPanel = FXMLLoader.load(getClass().getResource(
						GetViewHashMap.get(ViewType.Admin)));
				SystemController.ParentScreen = adminPanel;
				SetSingleView(adminPanel);
			} else if (author == Auth.LIBRARIAN) {
				Parent librarian = FXMLLoader.load(getClass().getResource(
						GetViewHashMap.get(ViewType.LIBRARIAN)));
				SystemController.ParentScreen = librarian;
				SetSingleView(librarian);
			} else if (author == Auth.BOTH) {

				Parent both = FXMLLoader.load(getClass().getResource(
						GetViewHashMap.get(ViewType.BOTH)));
				SystemController.ParentScreen = both;
				SetSingleView(both);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SetSingleView(Parent panel) {
		SystemController.stageArea.setScene(new Scene(panel, 800, 500));
		SystemController.stageArea.show();
	}

	public void setViewAddLibraryMember() {
		try {
			Parent Current = FXMLLoader.load(getClass().getResource(
					GetViewHashMap.get(ViewType.AddMember)));
			SetView(Current);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SetViewSearchBookAndCopy() {
		Parent Current;
		try {
			Current = FXMLLoader.load(getClass().getResource(
					GetViewHashMap.get(ViewType.SearchBook)));
			SetView(Current);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void SetViewSearchAndUpdateLibraryMember() {
		try {
			Parent Current = FXMLLoader.load(getClass().getResource(
					GetViewHashMap.get(ViewType.UpdateMember)));
			SetView(Current);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void SetViewAddNewBook() {
		try {
			Parent Current = FXMLLoader.load(getClass().getResource(
					GetViewHashMap.get(ViewType.AddBook)));
			SetView(Current);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void SetViewBookAfterAuthorAdd(Scene previous) {
		// SplitPane splitPane1 = new SplitPane();
		// splitPane1.getItems().addAll(SystemController.ParentScreen,previous);
		// Scene scene = new Scene(previous,700,450);
		SystemController.stageArea.setScene(previous);
	}
	protected void setviewCheckoutHistry(){
		try {
			Parent Current = FXMLLoader.load(getClass().getResource(
					GetViewHashMap.get(ViewType.viewHistry)));
			SetView(Current);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void SetViewAddAuthor() {

		try {
			Parent Current = FXMLLoader.load(getClass().getResource(
					GetViewHashMap.get(ViewType.AddAuthor)));
			SetView(Current);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void SetViewLogOutSystem() {
		try {
			SetViewLoginSystem(SystemController.stageArea);
		} catch (Exception e) {

		}
	}

	public void SetViewLoginSystem(Stage stage) throws IOException {
		Parent current = FXMLLoader.load(getClass().getResource(
				GetViewHashMap.get(ViewType.Login)));
		stage.setTitle("Library Management System");
		Scene scene = new Scene(current, 800, 500);
		
		scene.getStylesheets().add(
				this.getClass().getResource(CSSLocation).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void SetViewCheckoutBook() {
		Parent Current = null;
		try {
			Current = FXMLLoader.load(getClass().getResource(
					GetViewHashMap.get(ViewType.CheckOut)));
			SetView(Current);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void SetView(Parent Current) {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.getItems().addAll(SystemController.ParentScreen, Current);

        splitPane1.setDividerPositions(0.4f, 0.8f, 0.9f);
		Scene scene = new Scene(splitPane1, 800, 500);
		SystemController.stageArea.setScene(scene);
	}

}
