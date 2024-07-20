module calculator.mycalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens calculator.mycalculator to javafx.fxml;
    exports calculator.mycalculator;
}