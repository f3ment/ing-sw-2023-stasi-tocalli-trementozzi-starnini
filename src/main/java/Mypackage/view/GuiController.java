package Mypackage.view;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class GuiController {

        @FXML
        private TextField txtField;

        @FXML
        private Button btn;

        @FXML
        private Label lbl;

        @FXML
        void initialize() {
            btn.setOnAction(event -> {
                String text = txtField.getText().trim();
                if (!text.isEmpty()) {
                    lbl.setText(text);
                    txtField.clear();
                }
            });
        }
        public void AskLobbyInfo(){

        }

}
