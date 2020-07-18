package com.mateusz.jakuszko.roomforyoufront.form;

import com.mateusz.jakuszko.roomforyoufront.dto.CustomerDto;
import com.mateusz.jakuszko.roomforyoufront.encoder.LongToStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouCustomerApiClient;
import com.mateusz.jakuszko.roomforyoufront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
public class CustomerForm extends FormLayout {
    private TextField usernameField = new TextField("Username");
    private TextField nameField = new TextField("Name");
    private TextField surnameField = new TextField("Surname");
    private TextField emailField = new TextField("Email");
    private TextField passwordField = new TextField("Password");
    private TextField customerIdField = new TextField("CustomerId");
    private Label spaceLabel1 = new Label("");
    private Label spaceLabel2 = new Label("");
    private Button saveButton = new Button("Create customer");
    private Button deleteButton = new Button("Delete customer");
    private Binder<CustomerDto> binder = new Binder<>();
    private MainView mainView;
    private RoomForYouCustomerApiClient roomForYouCustomerApiClient;
    private CustomerDto customerDto = new CustomerDto();
    private LongToStringEncoder LongToStringEncoder;

    public CustomerForm(MainView mainView, RoomForYouCustomerApiClient roomForYouCustomerApiClient,
                        @Autowired LongToStringEncoder longToStringEncoder) {
        this.LongToStringEncoder = longToStringEncoder;
        this.roomForYouCustomerApiClient = roomForYouCustomerApiClient;
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        spaceLabel1.setHeight("1em");
        spaceLabel2.setHeight("1em");
        add(usernameField, passwordField, nameField, surnameField, emailField,
                saveButton, spaceLabel1, spaceLabel2, customerIdField, deleteButton);
        binder.forField(usernameField).bind(CustomerDto::getUsername, CustomerDto::setUsername);
        binder.forField(passwordField).bind(CustomerDto::getPassword, CustomerDto::setPassword);
        binder.forField(nameField).bind(CustomerDto::getName, CustomerDto::setName);
        binder.forField(surnameField).bind(CustomerDto::getSurname, CustomerDto::setSurname);
        binder.forField(emailField).bind(CustomerDto::getEmail, CustomerDto::setEmail);
        setPlaceholders();
        saveButton.addClickListener(event -> save());
        deleteButton.addClickListener(event -> delete());
        this.mainView = mainView;
    }

    private void save() {
        if (usernameField.getValue().length() != 0 && passwordField.getValue().length() > 8 &&
                emailField.getValue().length() != 0) {
            try {
                binder.writeBean(customerDto);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            roomForYouCustomerApiClient.postForCustomer(customerDto);
            setCustomerDto(new CustomerDto());
            mainView.customersView();
        } else {
            clear();
            usernameField.setErrorMessage("You probably don't complete a username");
            passwordField.setErrorMessage("Your password probably is too short");
            emailField.setErrorMessage("You probably don't complete a email");
        }
    }

    private void delete() {
        if (customerIdField.getValue().length() != 0 && customerIdField.getValue().matches(MainView.NUMBER_REGEX)) {
            Long customerId = LongToStringEncoder.decode(customerIdField.getValue());
            roomForYouCustomerApiClient.deleteCustomer(customerId);
            mainView.customersView();
        } else {
            clear();
            customerIdField.setErrorMessage("You have probably put and word not a number");
        }
    }

    public void clear() {
        nameField.clear();
        surnameField.clear();
        usernameField.clear();
        emailField.clear();
    }

    public void setPlaceholders() {
        usernameField.setPlaceholder("Unique username");
        nameField.setPlaceholder("Your name");
        surnameField.setPlaceholder("Your surname");
        emailField.setPlaceholder("Your e-mal address");
        passwordField.setPlaceholder("You password, minimum 8 sings and 1 number");
        customerIdField.setPlaceholder("Customer id - use it only for delete!!");
        usernameField.setRequired(true);
        passwordField.setRequired(true);
        emailField.setRequired(true);
        customerIdField.setRequired(true);
    }
}
