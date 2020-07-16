package com.mateusz.jakuszko.roomforyoufront.form;

import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.encoder.LongToStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouApartmentApiClient;
import com.mateusz.jakuszko.roomforyoufront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder <ApartmentDto> binder = new Binder<>();
    private MainView mainView;
    private RoomForYouApartmentApiClient roomForYouApiClient;
    private ApartmentDto apartmentDto = new ApartmentDto();
    private LongToStringEncoder LongToStringEncoder;

    public ApartmentForm(MainView mainView, RoomForYouApartmentApiClient roomForYouApiClient,
                         @Autowired LongToStringEncoder longToStringEncoder) {
        this.LongToStringEncoder = longToStringEncoder;
        this.roomForYouApiClient = roomForYouApiClient;
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(cityField, streetField, streetNumberField, apartmentNumberField, apartmentIdField, customerIdField, buttons);
        binder.forField(cityField).bind(ApartmentDto::getCity, ApartmentDto::setCity);
        binder.forField(streetField).bind(ApartmentDto::getStreet, ApartmentDto::setStreet);
        binder.forField(streetNumberField).bind(ApartmentDto::getStreetNumber, ApartmentDto::setStreetNumber);
        binder.forField(apartmentNumberField).bind(ApartmentDto::getApartmentNumber, ApartmentDto::setApartmentNumber);
//        binder.forField(apartmentIdField).withConverter(LongToStringEncoder::decode, LongToStringEncoder::encode)
//                .bind(ApartmentDto::getId, ApartmentDto::setId);
        binder.forField(customerIdField).withConverter(LongToStringEncoder::decode, LongToStringEncoder::encode)
                .bind(ApartmentDto::getCustomerId, ApartmentDto::setCustomerId);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        this.mainView = mainView;
    }

    private void save() {
        try {
            binder.writeBean(apartmentDto);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        roomForYouApiClient.postForApartment(apartmentDto);
        setApartmentDto(new ApartmentDto());
        mainView.refresh();
        clear();
    }

    private void delete() {
        Long apartmentId = LongToStringEncoder.decode(apartmentIdField.getValue());
        roomForYouApiClient.deleteApartment(apartmentId);
        mainView.refresh();
    }

    public void clear() {
        apartmentNumberField.clear();
        cityField.clear();;
        streetField.clear();
        streetNumberField.clear();
    }
}
