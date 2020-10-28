package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListControler implements Initializable {
	//interligaremos a classe departmentService no controlador
	
	private DepartmentService service;
	
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department>  obsList;
	
	//a��o do bot�o
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();//vazio
		createDialogForm(obj,"/gui/DepartmentForm.fxml", parentStage);
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
		
		
	}
	//inje��o de dependencias
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
 
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//para atingir a tela at� o final
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");	
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}

	private void createDialogForm(Department obj,String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController();//pegamos o controlador
			controller.setDepartment(obj);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter department Data");
			dialogStage.setScene(new Scene(pane));//para chamar a tela pane
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);//stage pai da janela
			dialogStage.initModality(Modality.WINDOW_MODAL);//n�o deixa acessar a tela de tras se nao preencher os dados da janela na frente
			dialogStage.showAndWait();
		}
		catch(IOException e) {
			Alerts.showAlert("Io Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
