package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {

	private Department entity;//dependencia para o departamento
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCanel;
	
	public void setDepartment(Department entity) { //instancia
		this.entity = entity;
	}
	
	//eventos dos botoes
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
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
