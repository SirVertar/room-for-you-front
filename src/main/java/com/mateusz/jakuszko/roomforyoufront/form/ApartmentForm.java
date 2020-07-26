package com.mateusz.jakuszko.roomforyoufront.form;

import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.encoder.LongToStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouApartmentApiClient;
import com.mateusz.jakuszko.roomforyoufront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
public class ApartmentForm extends FormLayout {
    private TextField cityField = new TextField("City");
    private TextField streetField = new TextField("Street");
    private TextField streetNumberField = new TextField("Street Number");
    private IntegerField apartmentNumberField = new IntegerField("Apartment Number");
    private TextField apartmentIdField = new TextField("Apartment id");
    private TextField customerIdField = new TextField("Customer id");
    private Button saveButton = new Button("Create apartment");
    private Button deleteButton = new Button("Delete apartment");
    private Label spaceLabel1 = new Label("");
    private Label spaceLabel2 = new Label("");
    private Binder<ApartmentDto> binder = new Binder<>();
    private MainView mainView;
    private RoomForYouApartmentApiClient roomForYouApiClient;
    private ApartmentDto apartmentDto = new ApartmentDto();
    private LongToStringEncoder LongToStringEncoder;

    public ApartmentForm(MainView mainView, RoomForYouApartmentApiClient roomForYouApiClient,
                         @Autowired LongToStringEncoder longToStringEncoder) {
        this.LongToStringEncoder = longToStringEncoder;
        this.roomForYouApiClient = roomForYouApiClient;
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        spaceLabel1.setHeight("1em");
        spaceLabel2.setHeight("1em");
        setPlaceholders();
        add(cityField, streetField, streetNumberField, apartmentNumberField, customerIdField, saveButton,
                spaceLabel1, spaceLabel2, apartmentIdField, deleteButton);
        binder.forField(cityField).bind(ApartmentDto::getCity, ApartmentDto::setCity);
        binder.forField(streetField).bind(ApartmentDto::getStreet, ApartmentDto::setStreet);
        binder.forField(streetNumberField).bind(ApartmentDto::getStreetNumber, ApartmentDto::setStreetNumber);
        binder.forField(apartmentNumberField).bind(ApartmentDto::getApartmentNumber, ApartmentDto::setApartmentNumber);
        binder.forField(customerIdField).withConverter(LongToStringEncoder::decode, LongToStringEncoder::encode)
                .bind(ApartmentDto::getCustomerId, ApartmentDto::setCustomerId);
        saveButton.addClickListener(event -> save());
        deleteButton.addClickListener(event -> delete());
        this.mainView = mainView;
    }

    private void save() {
        if (customerIdField.getValue().length() != 0 && cityField.getValue().length() >= 3 &&
                customerIdField.getValue().matches(MainView.NUMBER_REGEX)) {
            try {
                binder.writeBean(apartmentDto);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            roomForYouApiClient.postForApartment(apartmentDto);
            setApartmentDto(new ApartmentDto());
            mainView.apartmentView();
        }
        if (!customerIdField.getValue().matches(MainView.NUMBER_REGEX)) {
            customerIdField.setErrorMessage("You have probably put a word not a number");
        }
        if (cityField.getValue().length() < 3) {
            cityField.setErrorMessage("You probably don't complete a city");
        }
    }

    private void delete() {
        if (apartmentIdField.getValue().length() != 0 &&
                apartmentIdField.getValue().matches(MainView.NUMBER_REGEX)) {
            Long apartmentId = LongToStringEncoder.decode(apartmentIdField.getValue());
            roomForYouApiClient.deleteApartment(apartmentId);
            mainView.apartmentView();
        } else {
            clear();
            apartmentIdField.setErrorMessage("You have probably put a word not a number");
        }
    }

    public void clear() {
        cityField.clear();
        streetField.clear();
        streetNumberField.clear();
        apartmentNumberField.clear();
        customerIdField.clear();
        apartmentIdField.clear();
    }

    private void setPlaceholders() {
        cityField.setPlaceholder("The city where Your apartment is");
        streetField.setPlaceholder("The street where Your apartment is");
        streetNumberField.setPlaceholder("The street number of Your apartment");
        apartmentNumberField.setPlaceholder("The number of your Apartment");
        customerIdField.setPlaceholder("Your customer id");
        apartmentIdField.setPlaceholder("Apartment id - use it only for delete!!");
        cityField.setRequired(true);
        customerIdField.setRequired(true);
        apartmentIdField.setRequired(true);
    }
}
