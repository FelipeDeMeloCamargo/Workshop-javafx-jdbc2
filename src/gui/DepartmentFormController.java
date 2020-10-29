package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity;//dependencia para o departamento
	
	private DepartmentService service;
	
	private List <DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setDepartment(Department entity) { //instancia
		this.entity = entity;
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void subscribleDataChanceListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	//eventos dos botoes
	public void onBtSaveAction(ActionEvent event) { //salvar os dados no banco
		if(entity == null) {
			throw new IllegalStateException("Entity was null");//serve para que lembre o programador de inserir a dependencia
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();//para fechar a janela assim que salvar
		}
		catch(DbException e) {
			Alerts.showAlert("Error Saving objec", null, e.getMessage(), AlertType.ERROR );
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Department getFormData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();//para fechar a janela assim que salvar
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() { //para definir o que vai nos campos
		Constraints.setTextFieldDouble(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

	public void updateFormData() {
		if(entity ==null) {
			throw new IllegalStateException("Entity was null");//serve para se o Department é nullo
		}
		txtId.setText(String.valueOf(entity.getId()));//pega o id e vincula
		txtName.setText(entity.getName());//pegou o nome e vincula ao campo txt
	}
}
