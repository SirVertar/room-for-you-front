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
import com.vaadin.flow.data.binder.PropertyId;

public class ApartmentForm extends FormLayout {
    @PropertyId("city")
    private TextField city = new TextField("City");
    @PropertyId("street")
    private TextField street = new TextField("Street");
    @PropertyId("streetNumber")
    private TextField  streetNumber = new TextField("Street Number");
    @PropertyId("apartmentNumber")
    private IntegerField apartmentNumber = new IntegerField("Apartment Number");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder <ApartmentDto> binder = new Binder<>(ApartmentDto.class);
    private MainView mainView;
    private RoomForYouApartmentApiClient roomForYouApiClient;

    public ApartmentForm(MainView mainView, RoomForYouApartmentApiClient roomForYouApiClient) {
        this.roomForYouApiClient = roomForYouApiClient;
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(city, street, streetNumber, apartmentNumber, buttons);
        binder.bindInstanceFields(this);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        this.mainView = mainView;
    }

    private void save() {
        ApartmentDto apartmentDto = binder.getBean();
        roomForYouApiClient.postForApartment(apartmentDto);
        mainView.refresh();
        //setBook(null);
    }

    private void delete() {
        ApartmentDto apartmentDto = binder.getBean();
        //service.delete(book);
        mainView.refresh();
        //setBook(null);
    }
}
