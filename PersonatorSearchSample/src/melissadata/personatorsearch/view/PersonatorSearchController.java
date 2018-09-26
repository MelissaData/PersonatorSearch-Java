package melissadata.personatorsearch.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import melissadata.personatorsearch.model.PersonatorSearchTransaction;
import melissadata.personatorsearch.model.PersonatorSearchOptions;
import melissadata.personatorsearch.Main;

public class PersonatorSearchController  {

    private Main main;
    private PersonatorSearchOptions option;

    private PersonatorSearchTransaction Transaction;

    @FXML
    private Button buttonSend;
    @FXML
    private Button buttonClear;
    @FXML
    private TabPane tabPane;
    private final int CONFIGURATION_TAB = 0;
    private final int RESPONSE_TAB = 1;

    @FXML
    private TextField inputLicenseKeyText;
    @FXML
    private TextField inputFullNameText;
    @FXML
    private TextField inputFirstNameText;
    @FXML
    private TextField inputLastNameText;
    @FXML
    private TextField inputPhoneNumberText;
    @FXML
    private TextField inputEmailAddressText;


    @FXML
    private TextField inputAddressLine1Text;
    @FXML
    private TextField inputCityText;
    @FXML
    private TextField inputStateText;
    @FXML
    private TextField inputPostalText;

    @FXML
    private ComboBox<String> optionSearchTypeBox;
    @FXML
    private ComboBox<String> optionSearchConditionBox;
    @FXML
    private ComboBox<String> optionSortByBox;
    @FXML
    private ComboBox<String> optionPageBox;
    @FXML
    private ComboBox<String> optionRecordsPerPageBox;
    @FXML
    private ComboBox<String> optionReturnAllPagesBox;

    @FXML
    private CheckBox columnCheckAllColumnsCheckbox;

    @FXML
    private CheckBox columnPreviousAddressCheckbox;
    @FXML
    private CheckBox columnPhoneCheckbox;
    @FXML
    private CheckBox columnEmailCheckbox;
    @FXML
    private CheckBox columnMoveDateCheckbox;
    @FXML
    private CheckBox columnDateOfBirthCheckbox;
    @FXML
    private CheckBox columnDateOfDeathCheckbox;


    @FXML
    private TextArea RequestTextArea;
    @FXML
    private TextArea ResponseTextArea;

    @FXML
    private RadioButton jsonResponseFormatRadio;
    @FXML
    private RadioButton xmlResponseFormatRadio;
    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public PersonatorSearchController() {
        Transaction = new PersonatorSearchTransaction();
        option = new PersonatorSearchOptions();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        setupOptionSelections();
        initializeFormatRadioButtons();
        selectColumnCheckbox();
        selectAllColumnAction();
        initializeTextFields();
        sendButtonAction();
        clearButtonAction();
        updateRequestText();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * //@param mainApp
     */
    public void setMainApp(Main main) {
        this.main = main;
    }

    public void sendButtonAction() {
        buttonSend.setOnAction((event) -> {
            ResponseTextArea.setText(Transaction.processTransaction(RequestTextArea.getText()));
            tabPane.getSelectionModel().select(RESPONSE_TAB);
        });
    }

    public void clearButtonAction(){
        buttonClear.setOnAction((event) -> {
            inputFullNameText.clear();
            inputFirstNameText.clear();
            inputLastNameText.clear();
            inputPhoneNumberText.clear();
            inputEmailAddressText.clear();
            inputAddressLine1Text.clear();
            inputCityText.clear();
            inputPostalText.clear();
            inputStateText.clear();
            returnToConfiguration();
        });
    }

    public void initializeTextFields(){
        inputLicenseKeyText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setIdentNumber(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputFullNameText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setFullName(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputFirstNameText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setFirstName(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputLastNameText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setLastName(newvalue);
            updateRequestText();
            returnToConfiguration();
        });


        inputPhoneNumberText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setPhoneNumber(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputEmailAddressText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setEmailAddress(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputAddressLine1Text.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressLine1(newvalue);
            updateRequestText();
            returnToConfiguration();

        });

        inputCityText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCity(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputStateText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setState(newvalue);
            updateRequestText();
            returnToConfiguration();
        });


        inputPostalText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setPostalCode(newvalue);
            updateRequestText();
            returnToConfiguration();
        });
    }
    // Define what values each combo box can hold
    private void setupOptionSelections() {
        optionSearchTypeBox.setItems(FXCollections.observableArrayList("", "Auto", "CompanySearch", "AddressSearch", "PhoneSearch"));
        optionSearchConditionBox.setItems(FXCollections.observableArrayList("", "Strict", "Loose", "Progressive"));
        optionSortByBox.setItems(FXCollections.observableArrayList("", "PostalCode-Ascending", "PostalCode-Descending",
                "Locality-Ascending", "Locality-Descending",
                "AddressLine1-Ascending", "AddressLine1-Descending",
                "CompanyName-Ascending", "CompanyName-Descending",
                "MelissaEnterpriseKey-Ascending", "MelissaEnterpriseKey-Descending",
                "StockTicker-Ascending", "StockTicker-Descending",
                "WebAddress-Ascending", "WebAddress-Descending",
                "MoveDate-Ascending", "MoveDate-Descending",
                "MelissaAddressKey-Ascending", "MelissaAddressKey-Descending",
                "MelissaAddressKeyBase-Ascending", "MelissaAddressKeyBase-Descending"));
        optionPageBox.setItems(FXCollections.observableArrayList("", "1", "5", "10", "50"));
        optionRecordsPerPageBox.setItems(FXCollections.observableArrayList("", "1", "5", "10", "50"));
        optionReturnAllPagesBox.setItems(FXCollections.observableArrayList("", "True", "False"));
    }

    public void optionSearchTypeBox() {
        option.setOptionSearchType(optionSearchTypeBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }
    public void optionSearchConditionBox() {
        option.setOptionSearchCondition(optionSearchConditionBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    public void optionSortByBox() {
        option.setOptionSortBy(optionSortByBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    public void optionPageBox() {
        option.setOptionPage(optionPageBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    public void optionRecordsPerPageBox() {
        option.setOptionRecordsPerPage(optionRecordsPerPageBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    public void optionReturnAllPagesBox() {
        option.setOptionReturnAllPages(optionReturnAllPagesBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    private void initializeFormatRadioButtons(){
        jsonResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("JSON");
            xmlResponseFormatRadio.setSelected(false);
            updateRequestText();
        });

        xmlResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("XML");
            jsonResponseFormatRadio.setSelected(false);
            updateRequestText();
        });
    }

    private void returnToConfiguration(){
        if(tabPane.getSelectionModel().getSelectedIndex() != 0)	tabPane.getSelectionModel().select(CONFIGURATION_TAB);

    }

    private void selectAllColumnAction() {
        columnCheckAllColumnsCheckbox.setOnAction((event) -> {
            if(!Transaction.isSelectAllColumns()){
                Transaction.setColumnPreviousAddress(true);
                columnPreviousAddressCheckbox.setSelected(true);

                Transaction.setColumnPhone(true);
                columnPhoneCheckbox.setSelected(true);

                Transaction.setColumnEmail(true);
                columnEmailCheckbox.setSelected(true);

                Transaction.setColumnMoveDate(true);
                columnMoveDateCheckbox.setSelected(true);

                Transaction.setColumnDateOfDeath(true);
                columnDateOfDeathCheckbox.setSelected(true);

                Transaction.setColumnDateOfBirth(true);
                columnDateOfBirthCheckbox.setSelected(true);

            } else {
                Transaction.setColumnPreviousAddress(false);
                columnPreviousAddressCheckbox.setSelected(false);

                Transaction.setColumnPhone(false);
                columnPhoneCheckbox.setSelected(false);

                Transaction.setColumnEmail(false);
                columnEmailCheckbox.setSelected(false);

                Transaction.setColumnMoveDate(false);
                columnMoveDateCheckbox.setSelected(false);

                Transaction.setColumnDateOfDeath(false);
                columnDateOfDeathCheckbox.setSelected(false);

                Transaction.setColumnDateOfBirth(false);
                columnDateOfBirthCheckbox.setSelected(false);
            }
            Transaction.setSelectAllColumns(columnCheckAllColumnsCheckbox.isSelected());
            updateRequestText();
        });
    }

    private void selectColumnCheckbox() {
        columnPreviousAddressCheckbox.setOnAction((event) -> {
            Transaction.setColumnPreviousAddress(columnPreviousAddressCheckbox.isSelected());
            updateRequestText();
        });

        columnPhoneCheckbox.setOnAction((event) -> {
            Transaction.setColumnPhone(columnPhoneCheckbox.isSelected());
            updateRequestText();
        });

        columnEmailCheckbox.setOnAction((event) -> {
            Transaction.setColumnEmail(columnEmailCheckbox.isSelected());
            updateRequestText();
        });

        columnMoveDateCheckbox.setOnAction((event) -> {
            Transaction.setColumnMoveDate(columnMoveDateCheckbox.isSelected());
            updateRequestText();
        });

        columnDateOfDeathCheckbox.setOnAction((event) -> {
            Transaction.setColumnDateOfDeath(columnDateOfDeathCheckbox.isSelected());
            updateRequestText();
        });

        columnDateOfBirthCheckbox.setOnAction((event) -> {
            Transaction.setColumnDateOfBirth(columnDateOfBirthCheckbox.isSelected());
            updateRequestText();
        });
    }

    private void updateRequestText(){
        RequestTextArea.setText(Transaction.generateRequestString());
    }
}
