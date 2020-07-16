package com.mateusz.jakuszko.roomforyoufront.form;

import com.mateusz.jakuszko.roomforyoufront.dto.CustomerDto;
import com.mateusz.jakuszko.roomforyoufront.encoder.LongToStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouCustomerClient;
import com.mateusz.jakuszko.roomforyoufront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
    private TextField roleField = new TextField("Role");
    private TextField passwordField = new TextField("Password");
    private TextField customerIdField = new TextField("CustomerId");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<CustomerDto> binder = new Binder<>();
    private MainView mainView;
    private RoomForYouCustomerClient roomForYouCustomerClient;
    private CustomerDto customerDto = new CustomerDto();
    private com.mateusz.jakuszko.roomforyoufront.encoder.LongToStringEncoder LongToStringEncoder;

    public CustomerForm(MainView mainView, RoomForYouCustomerClient roomForYouCustomerClient,
                        @Autowired LongToStringEncoder longToStringEncoder) {
        this.LongToStringEncoder = longToStringEncoder;
        this.roomForYouCustomerClient = roomForYouCustomerClient;
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(usernameField, passwordField, nameField, surnameField, emailField, roleField, customerIdField, buttons);
        binder.forField(usernameField).bind(CustomerDto::getUsername, CustomerDto::setUsername);
        binder.forField(passwordField).bind(CustomerDto::getPassword, CustomerDto::setPassword);
        binder.forField(nameField).bind(CustomerDto::getName, CustomerDto::setName);
        binder.forField(surnameField).bind(CustomerDto::getSurname, CustomerDto::setSurname);
        binder.forField(emailField).bind(CustomerDto::getEmail, CustomerDto::setEmail);
        binder.forField(roleField).bind(CustomerDto::getRole, CustomerDto::setRole);
        binder.forField(customerIdField).withConverter(LongToStringEncoder::decode, LongToStringEncoder::encode)
                .bind(CustomerDto::getId, CustomerDto::setId);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        this.mainView = mainView;
    }

    private void save() {
        try {
            binder.writeBean(customerDto);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        roomForYouCustomerClient.postForCustomer(customerDto);
        setCustomerDto(new CustomerDto());
        mainView.refreshCustomers();
        clear();
    }

    private void delete() {
        Long customerID = LongToStringEncoder.decode(customerIdField.getValue());
        roomForYouCustomerClient.deleteCustomer(customerID);
        mainView.refreshCustomers();
    }

    public void clear() {
        nameField.clear();
        surnameField.clear();
        usernameField.clear();
        emailField.clear();
        roleField.clear();
    }
}
