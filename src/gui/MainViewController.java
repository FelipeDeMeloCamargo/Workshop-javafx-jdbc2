package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	//métodos para tratar as acoes
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}

	@FXML
	public void onmenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml",(DepartmentListControler controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView(); 
		});
	}
	
	@FXML
	public void onmenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			//aqui iremos vincular uma janela a outra
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); //pega o primeiro elemento da view
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVbox.getChildren());
			
			T controller = loader.getController();//vao executar a função que passar por parametro		
			initializingAction.accept(controller);
		
		
		}
		catch (IOException e) {
			Alerts.showAlert("IoException", "Error load View", e.getMessage(), AlertType.ERROR);
		}
		
	}
}
