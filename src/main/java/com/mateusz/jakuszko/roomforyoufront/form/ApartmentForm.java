package com.mateusz.jakuszko.roomforyoufront.form;

import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
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

@Setter
public class ApartmentForm extends FormLayout {
    private TextField city = new TextField("City");
    private TextField street = new TextField("Street");
    private TextField  streetNumber = new TextField("Street Number");
    private IntegerField apartmentNumber = new IntegerField("Apartment Number");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder <ApartmentDto> binder = new Binder<>();
    private MainView mainView;
    private RoomForYouApartmentApiClient roomForYouApiClient;
    private ApartmentDto apartmentDto = new ApartmentDto();

    public ApartmentForm(MainView mainView, RoomForYouApartmentApiClient roomForYouApiClient) {
        this.roomForYouApiClient = roomForYouApiClient;
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(city, street, streetNumber, apartmentNumber, buttons);
        binder.forField(city).bind(ApartmentDto::getCity, ApartmentDto::setCity);
        binder.forField(street).bind(ApartmentDto::getStreet, ApartmentDto::setStreet);
        binder.forField(streetNumber).bind(ApartmentDto::getStreetNumber, ApartmentDto::setStreetNumber);
        binder.forField(apartmentNumber).bind(ApartmentDto::getApartmentNumber, ApartmentDto::setApartmentNumber);
        //binder.bindInstanceFields(this);
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
        apartmentDto.setCustomerId(1L);
        roomForYouApiClient.postForApartment(apartmentDto);
        mainView.refresh();
        setApartmentDto(new ApartmentDto());
    }

    private void delete() {
        ApartmentDto apartmentDto = binder.getBean();
        //service.delete(book);
        mainView.refresh();
        //setBook(null);
    }
}
