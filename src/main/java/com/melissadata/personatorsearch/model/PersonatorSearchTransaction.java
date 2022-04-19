package com.melissadata.personatorsearch.model;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonatorSearchTransaction {

    private final String endpoint;

    private PersonatorSearchOptions options;
    private String columns;
    private String identNumber;
    private String fullName, firstName, lastName, addressLine1, city, state, postalCode, phoneNumber, emailAddress;
    private boolean selectAllColumns, columnPreviousAddress, columnPhone, columnEmail, columnMoveDate, columnDateOfDeath, columnDateOfBirth;
    private String format;

    public PersonatorSearchTransaction() {
        endpoint = "http://personatorsearch.melissadata.net/WEB/doPersonatorSearch?";
        options = new PersonatorSearchOptions();
        columns = "";
        identNumber = "";
        fullName = "";
        firstName = "";
        lastName = "";
        addressLine1 = "";
        city = "";
        state = "";
        postalCode = "";
        phoneNumber = "";
        emailAddress = "";
        format = "";
    }

    public String processTransaction(String request) {
        String response = "";
        try {
            URL url = new URL(request);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String responseBody = in.lines().collect(Collectors.joining());
            response = format.equals("XML")
                ? getPrettyXML(responseBody)
                : getPrettyJSON(responseBody);

        } catch (Exception e){
            System.out.println("Error sending request: \n" + e);
        }
        return response;
    }

    private String getPrettyJSON(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject responseObject = gson.fromJson(json, JsonObject.class);
        return gson.toJson(responseObject);
    }

    private String getPrettyXML(String xml) {
        String prettyXML = "";
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            String indentSize = "{http://xml.apache.org/xslt}indent-amount";
            t.setOutputProperty(indentSize, "2");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            Writer writer = new StringWriter();
            t.transform(new StreamSource(new StringReader(xml)),
                        new StreamResult(writer));
            prettyXML = writer.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return prettyXML;
    }

    public String generateRequestString() {
        StringBuilder sb = new StringBuilder();

        sb.append(endpoint)
            .append("&id=" + getIdentNumber())
            .append(options.generateOptionString())
            .append("&cols=" + getColumns())

            .append(generateRequestParam("full", getFullName()))
            .append(generateRequestParam("first", getFirstName()))
            .append(generateRequestParam("last", getLastName()))
            .append(generateRequestParam("phone", getPhoneNumber()))
            .append(generateRequestParam("email", getEmailAddress()))
            .append(generateRequestParam("a1", getAddressLine1()))
            .append(generateRequestParam("city", getCity()))
            .append(generateRequestParam("state", getState()))
            .append(generateRequestParam("postal", getPostalCode()))

            .append(generateRequestParam("format", getFormat()));

        return sb.toString();
    }

    private String generateRequestParam(String arg, String value) {
        String encodedValue = "";
        try {
            encodedValue = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding Exception: " + e);
        }

        if(!encodedValue.equals("")) {
            return "&" + arg + "=" + encodedValue;
        }

        return "";
    }

    public PersonatorSearchOptions getOptions() {
        return options;
    }

    public void setOptions(PersonatorSearchOptions options) {
        this.options = options;
    }

    private String getColumns() {

        if(isSelectAllColumns()) return "grpAll";

        List<String> columns = new ArrayList<>();
        if (isColumnPreviousAddress()) columns.add("PreviousAddress");
        if (isColumnPhone()) columns.add("Phone");
        if (isColumnEmail()) columns.add("Email");
        if (isColumnMoveDate()) columns.add("MoveDate");
        if (isColumnDateOfDeath()) columns.add("DateOfDeath");
        if (isColumnDateOfBirth()) columns.add("DateOfBirth");
        return String.join(",", columns);
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getIdentNumber() {
        return identNumber;
    }

    public void setIdentNumber(String identNumber) {
        this.identNumber = identNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isSelectAllColumns() {
        return selectAllColumns;
    }

    public void setSelectAllColumns(boolean selectAllColumns) {
        this.selectAllColumns = selectAllColumns;
    }

    public boolean isColumnPreviousAddress() {
        return columnPreviousAddress;
    }

    public void setColumnPreviousAddress(boolean columnPreviousAddress) {
        this.columnPreviousAddress = columnPreviousAddress;
    }

    public boolean isColumnPhone() {
        return columnPhone;
    }

    public void setColumnPhone(boolean columnPhone) {
        this.columnPhone = columnPhone;
    }

    public boolean isColumnEmail() {
        return columnEmail;
    }

    public void setColumnEmail(boolean columnEmail) {
        this.columnEmail = columnEmail;
    }

    public boolean isColumnMoveDate() {
        return columnMoveDate;
    }

    public void setColumnMoveDate(boolean columnMoveDate) {
        this.columnMoveDate = columnMoveDate;
    }

    public boolean isColumnDateOfDeath() {
        return columnDateOfDeath;
    }

    public void setColumnDateOfDeath(boolean columnDateOfDeath) {
        this.columnDateOfDeath = columnDateOfDeath;
    }

    public boolean isColumnDateOfBirth() {
        return columnDateOfBirth;
    }

    public void setColumnDateOfBirth(boolean columnDateOfBirth) {
        this.columnDateOfBirth = columnDateOfBirth;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
