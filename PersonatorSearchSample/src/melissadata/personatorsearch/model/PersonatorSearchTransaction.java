package melissadata.personatorsearch.model;

import org.apache.sling.commons.json.JSONObject;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

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
        URI uri;
        URL url;
        try {
            uri = new URI(request);
            url = new URL(uri.toURL().toString());

            HttpURLConnection urlConn = (HttpURLConnection)(url.openConnection());

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringReader reader;
            StringWriter writer = new StringWriter();
            StringBuilder jsonResponse = new StringBuilder();
            String inputLine = "";

            if (format.equals("XML"))
            {
                String xmlLine = "";
                String xmlString = "";

                while((xmlLine = in.readLine()) != null) {
                    xmlString += xmlLine + "\n";
                }

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer();
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
                t.setOutputProperty(OutputKeys.INDENT, "yes");

                reader = new StringReader(xmlString);
                try {
                    t.transform(new javax.xml.transform.stream.StreamSource(reader), new javax.xml.transform.stream.StreamResult(writer));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                response = writer.toString();

            } else {
                while ((inputLine = in.readLine()) != null) {
                    jsonResponse.append(inputLine);
                }
                @SuppressWarnings("deprecation")
                JSONObject test = new JSONObject(jsonResponse.toString());
                response = test.toString(10);

            }
        } catch (Exception e){
            System.out.println("Error sending request: \n" + e);
        }
        return response;
    }

    public String generateRequestString() {
        String request = "";
        request = endpoint;
        request += "&id=" + getIdentNumber();
        request += "&opt=" + options.generateOptionString();
        request += "&cols=" + getColumns();
        try {
            if(!getFullName().equals(""))
                request += "&full=" + URLEncoder.encode(getFullName(), "UTF-8");

            if(!getFirstName().equals(""))
                request += "&first=" + URLEncoder.encode(getFirstName(), "UTF-8");

            if(!getLastName().equals(""))
                request += "&last=" + URLEncoder.encode(getLastName(), "UTF-8");

            if(!getPhoneNumber().equals(""))
                request += "&phone=" + URLEncoder.encode(getPhoneNumber(), "UTF-8");

            if(!getEmailAddress().equals(""))
                request += "&email=" + URLEncoder.encode(getEmailAddress(), "UTF-8");

            if(!getAddressLine1().equals(""))
                request += "&a1=" + URLEncoder.encode(getAddressLine1(), "UTF-8");

            if(!getCity().equals(""))
                request += "&city=" + URLEncoder.encode(getCity(), "UTF-8");

            if(!getState().equals(""))
                request += "&state=" + URLEncoder.encode(getState(), "UTF-8");

            if(!getPostalCode().equals(""))
                request += "&postal=" + URLEncoder.encode(getPostalCode(), "UTF-8");


        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding Exception: " +e);
        }

        if(!getFormat().equals(""))
            request += "&format=" + getFormat();

        return request;
    }
    public PersonatorSearchOptions getOptions() {
        return options;
    }

    public void setOptions(PersonatorSearchOptions options) {
        this.options = options;
    }
    private String getColumns() {
        String columnString = "";
        if(isSelectAllColumns()){
            columnString += "grpAll";
        } else {
            if (isColumnPreviousAddress())
                columnString += "PreviousAddress";

            if (isColumnPhone() && columnString.equals(""))
                columnString += "Phone";
            else if (isColumnPhone() && !columnString.equals(""))
                columnString += ",Phone";

            if (isColumnEmail() && columnString.equals(""))
                columnString += "Email";
            else if (isColumnEmail() && !columnString.equals(""))
                columnString += ",Email";

            if (isColumnMoveDate() && columnString.equals(""))
                columnString += "MoveDate";
            else if (isColumnMoveDate() && !columnString.equals(""))
                columnString += ",MoveDate";

            if (isColumnDateOfDeath() && columnString.equals(""))
                columnString += "DateOfDeath";
            else if (isColumnDateOfDeath() && !columnString.equals(""))
                columnString += ",DateOfDeath";

            if (isColumnDateOfBirth() && columnString.equals(""))
                columnString += "DateOfBirth";
            else if (isColumnDateOfBirth() && !columnString.equals(""))
                columnString += ",DateOfBirth";
        }
        return columnString;
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
