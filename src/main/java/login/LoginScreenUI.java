package login;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import mainapp.*;
import userinterface.*;

public class LoginScreenUI {

    @FXML private TextField txtFieldUsername;
    @FXML private TextField txtFieldError;
    @FXML private TextField txtFieldEmail;
    @FXML private PasswordField passwordFieldPassword;
    @FXML private PasswordField passwordFieldReType;
    @FXML private Button btnCancelRegistration;
    @FXML private Button btnRegister;
    @FXML private Button btnLogin;
    @FXML private AnchorPane paneRegister;

    //private LoginScreenController controller = new LoginScreenController();

    private boolean clicked = false;
    private static final double SHIFTDOWN = 50;
    private static final double SHIFTUP = -50;

    @FXML
    private void login(){
        System.out.println("Logging in");

        if(!LoginScreenController.hasErrors(txtFieldUsername.getText(),passwordFieldPassword.getText())){
            txtFieldError.setText(LoginScreenController.errorMessage);
        }else {
            SynkApp.getInstance().gotoMainUI();
            AppData.getInstance().populateData();
        }
    }
    @FXML
    private void register(){
        txtFieldError.clear();
        //keeping track of if the register button has been clicked yet, and change our logic depending on that.
        if(!clicked) {
            System.out.println("Registering");
            SynkApp.getInstance().getStage().setTitle("Register");
            changeUseability();
            shiftScreenItems(SHIFTDOWN);
            clicked = true;
        }else {
            if(fieldsAreEmpty()){
                txtFieldError.setText("Please fill in all fields.");
            }else if(LoginScreenController.success(txtFieldUsername.getText(),passwordFieldPassword.getText(),passwordFieldReType.getText(),txtFieldEmail.getText())){
                SynkApp.getInstance().getStage().setTitle("Synk Login");
                changeUseability();
                shiftScreenItems(SHIFTUP);
                clicked = false;
            }else {
                txtFieldError.setText(LoginScreenController.errorMessage);
            }
        }

    }

    /**
     * Checks the fields required for registering, if any of them are null will return false
     * @return true if any of the fields are empty
     */
    private boolean fieldsAreEmpty(){
        return txtFieldUsername.getText().length() < 1 ||
                passwordFieldPassword.getText().length() < 1 ||
                passwordFieldReType.getText().length() < 1||
                txtFieldEmail.getText().length() < 1;
    }
    /**
     * Method to show/hide and disable/enable parts of the ui for registering, allows the same screen to be reused for
     * logging in and registering.
     */
    private void changeUseability(){
        passwordFieldReType.clear();
        txtFieldEmail.clear();
        paneRegister.setVisible(!paneRegister.isVisible());
    }
    @FXML
    private void cancelRegistration(){
        txtFieldError.clear();
        SynkApp.getInstance().getStage().setTitle("Synk Login");
        changeUseability();
        shiftScreenItems(SHIFTUP);
        clicked = false;
    }
    private void shiftScreenItems(double movement){
        SynkApp.getInstance().getStage().setHeight(SynkApp.getInstance().getStage().getHeight() + movement);
        btnRegister.setLayoutY(btnRegister.getLayoutY() + movement);
        btnLogin.setLayoutY(btnLogin.getLayoutY() + movement);
        txtFieldError.setLayoutY(txtFieldError.getLayoutY() + movement);
    }
}
