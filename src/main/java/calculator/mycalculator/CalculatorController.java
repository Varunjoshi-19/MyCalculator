package calculator.mycalculator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;


import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController extends Application implements Initializable {

    @FXML
    private Button clearButton, divideButton, addButton, minusButton, multipleButton, modulusButton, equalButton, eraseButton;
    @FXML
    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, decimalButton, Cbutton;
    @FXML
    private TextField myTextField;
    @FXML
    private Label myLabel, newLabel;
    @FXML
    private MenuItem exitButton, aboutButton, historyMenuButton;
    @FXML
    private RadioMenuItem DarkMode, LightMode;
    @FXML
    private AnchorPane myPane;

    private Button[] numberButtons = new Button[10];

    private char op = ' ';
    private boolean num1 = false;
    private boolean num2 = false;

    boolean clicked = false;
    boolean operators = true;
    private boolean calculatorDisabled = true;
    
    private ObservableList<String> items = FXCollections.observableArrayList();
    
    @FXML
    ListView<String> listView = new ListView<>(items);


    
    private String historyResult;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValues();
    }

    public void setValues() {
        numberButtons[0] = button0;
        numberButtons[1] = button1;
        numberButtons[2] = button2;
        numberButtons[3] = button3;
        numberButtons[4] = button4;
        numberButtons[5] = button5;
        numberButtons[6] = button6;
        numberButtons[7] = button7;
        numberButtons[8] = button8;
        numberButtons[9] = button9;

        myTextField.setText("");
        myLabel.setText("");
        
        disableCalculator();
    }

    private void disableCalculator() {
        calculatorDisabled = true;
        setCalculatorButtonsDisabled(true);
    }

    private void enableCalculator() {
        calculatorDisabled = false;
        setCalculatorButtonsDisabled(false);
    }

    private void setCalculatorButtonsDisabled(boolean disabled) {
        clearButton.setDisable(disabled);
        divideButton.setDisable(disabled);
        addButton.setDisable(disabled);
        minusButton.setDisable(disabled);
        multipleButton.setDisable(disabled);
        modulusButton.setDisable(disabled);
        equalButton.setDisable(disabled);
        eraseButton.setDisable(disabled);
        decimalButton.setDisable(disabled);

        for (Button button : numberButtons) {
            button.setDisable(disabled);
        }
    }

    private boolean checkOperator() {
        String text = myTextField.getText();
        if (text.isEmpty()) {
            return false;
        }
        int index = text.length() - 1;
        char op = text.charAt(index);
        char[] items = {'+', '-', 'x', '%', '/'};
        for (char value : items) {
            if (op == value) {
                return true;
            }
        }
        return false;
    }

    private void checkString() {
        String text = myTextField.getText();
        if (text.isEmpty()) {
            num1 = false;
            num2 = false;
            newLabel.setText("NEW");
            op = ' ';
            return;
        }
        int index = text.length() - 1;
        char op = text.charAt(index);
        char[] items = {'+', '-', 'x', '%', '/'};
        for (char value : items) {
            if (op == value) {
                num2 = false;
                return;
            }
        }
    }

    @FXML
    public void action(ActionEvent event) throws Exception {
        for (int i = 0; i < 10; i++) {
            if (event.getSource() == numberButtons[i]) {
                newLabel.setText("");
                if (clicked) {
                    myTextField.setText("");
                    num1 = false;
                    num2 = false;
                    op = ' ';
                    clicked = false;
                    operators = true;
                }
                myTextField.setText(myTextField.getText().concat(String.valueOf(i)));
                String text = myTextField.getText();
                if (op == ' ') {
                    num1 = true;
                } else {
                    num2 = true;
                    String exp = Solver.evaluateExpression(text);
                    myLabel.setText(exp);
                    historyResult = text + " = " + exp;
                }
            }
        }

        if (event.getSource() == clearButton) {
            myTextField.setText("");
            myLabel.setText("0");
            newLabel.setText("NEW");
            num1 = false;
            num2 = false;
            op = ' ';
        }

        if (event.getSource() == eraseButton) {
            String expression = myTextField.getText();
            if (!expression.isEmpty()) {
                expression = expression.substring(0, expression.length() - 1);
                myTextField.setText(expression);
            }
            if (myTextField.getText().isEmpty()) {
                num1 = false;
                num2 = false;
                op = ' ';
                myLabel.setText("0");
                newLabel.setText("NEW");
            }
            String exp = Solver.evaluateExpression(expression);
            checkString();
            myLabel.setText(exp);
        }

        if ((event.getSource() == divideButton || event.getSource() == addButton ||
                event.getSource() == multipleButton || event.getSource() == minusButton ||
                event.getSource() == modulusButton) && operators) {
            if (myTextField.getText().isEmpty()) {
                return;
            }
            if (checkOperator()) {
                String text = myTextField.getText();
                myTextField.setText(text.substring(0, text.length() - 1));
            }
            if (num2) {
                String output = myLabel.getText();
                myTextField.setText(output);
                myLabel.setText("");
                num2 = false;
                addItemToList(historyResult);
            }
            
            if (event.getSource() == divideButton) {
                op = '/';
            } else if (event.getSource() == addButton) {
                op = '+';
            } else if (event.getSource() == multipleButton) {
                op = 'x';
            } else if (event.getSource() == minusButton) {
                op = '-';
            } else if (event.getSource() == modulusButton) {
                op = '%';
            }
            myTextField.setText(myTextField.getText().concat(String.valueOf(op)));
        }

        if (event.getSource() == equalButton) {
            if (myLabel.getText().equals("Cannot divide by zero")) {
                return;
            }
            if (num2) {
                String output = myLabel.getText();
                myTextField.setText("= " + output);
                myLabel.setText("");
                num2 = false;
                clicked = true;
                operators = false;
                newLabel.setText("ANS");
                addItemToList(historyResult);
                historyResult = "";
            }
        }

        if (event.getSource() == decimalButton) {
            String text = myTextField.getText();
            if (text.isEmpty() || checkOperator()) {
                myTextField.setText(myTextField.getText().concat("0."));
            } else if (!text.endsWith(".")) {
                String[] tokens = text.split("[+\\-x/%]");
                String lastToken = tokens[tokens.length - 1];
                if (!lastToken.contains(".")) {
                    myTextField.setText(text.concat("."));
                }
            }
        }

        if (event.getSource() == Cbutton) {
            if (calculatorDisabled) {
                enableCalculator();
                Cbutton.setText("ON");
                myLabel.setText("0");
                newLabel.setText("NEW");
            } else {
                disableCalculator();
                Cbutton.setText("OFF");
                myTextField.setText("");
                myLabel.setText("");
                newLabel.setText("");
                num1 = false;
                num2 = false;
                op = ' ';
                clicked = false;
                operators = true;
            }
        }

        if (event.getSource() == exitButton) {
            System.exit(0);
        }

        if (event.getSource() == aboutButton) {
            showAboutDialog();
        }

        if (event.getSource() == DarkMode) {
            myPane.getScene().getStylesheets().clear();
            myPane.getScene().getStylesheets().add(getClass().getResource("DarkMode.css").toExternalForm());
        }

        if (event.getSource() == LightMode) {
            myPane.getScene().getStylesheets().clear();
            myPane.getScene().getStylesheets().add(getClass().getResource("LightMode.css").toExternalForm());
        }
        
        if (event.getSource() == historyMenuButton) {
            Stage stage = new Stage();
            start(stage);
        }
    }

    private void showAboutDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Calculator Application");
        alert.setContentText("This is a simple calculator application implemented using JavaFX..............By VARUN JOSHI.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    //------------------------------------HISTORY FRAME--------------------------//    
    public  void start(Stage stage) {
    	 AnchorPane anchorPane = new AnchorPane();

         // Create the Label
         Label historyLabel = new Label("CALCULATION HISTORY");
         historyLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

         // Create the ListView
         ListView<String> listView = new ListView<>(items);
        
       
         // Create the Buttons
         
         Button clearButton = new Button("Clear");
   
         // Positioning the elements
         AnchorPane.setTopAnchor(historyLabel, 10.0);
         AnchorPane.setLeftAnchor(historyLabel, 10.0);
         AnchorPane.setRightAnchor(historyLabel, 10.0);

         AnchorPane.setTopAnchor(listView, 40.0);
         AnchorPane.setLeftAnchor(listView, 10.0);
         AnchorPane.setRightAnchor(listView, 10.0);
         AnchorPane.setBottomAnchor(listView, 50.0);



         AnchorPane.setRightAnchor(clearButton, 10.0);
         AnchorPane.setBottomAnchor(clearButton, 10.0);

         // Add elements to the AnchorPane
         anchorPane.getChildren().addAll(historyLabel, listView,clearButton);

        
         // Create the Scene and set it on the Stage
         Scene scene = new Scene(anchorPane, 300, 595);

         stage.setTitle("HISTORY");
         stage.setScene(scene);
         stage.show();

         // Add functionality to buttons (Optional)
      
         clearButton.setOnAction(e -> listView.getItems().clear());
     }

    private void addItemToList(String newItem) {
        items.add(newItem);
    }
}
