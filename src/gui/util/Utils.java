package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	public static Stage currentStage(ActionEvent event) { //pegar o stage pelo objeto event
		
		return (Stage) ((Node)event.getSource()).getScene().getWindow();
		//serve para pegar o stage do botao clicado, a tela principal
	}

}
