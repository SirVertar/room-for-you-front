package com.mateusz.jakuszko.roomforyoufront.view;

import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.encoder.LongToStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.form.ApartmentForm;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouApartmentApiClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@Getter
public class MainView extends VerticalLayout {

    private Button buttonLogIn = new Button("Login");
    private Button buttonRegister = new Button("Register");
    private Button buttonApartment = new Button("Apartment");
    private Button buttonReservation = new Button("Reservation");
    private Button buttonCustomer = new Button("Customer");
    private final RoomForYouApartmentApiClient roomForYouApiClient;
    private Grid apartmentGrid = new Grid<>(ApartmentDto.class);
    private LongToStringEncoder longToStringEncoder;

    public MainView(@Autowired RoomForYouApartmentApiClient roomForYouApiClient,
                    @Autowired LongToStringEncoder longToStringEncoder) {
        this.longToStringEncoder = longToStringEncoder;
        this.roomForYouApiClient = roomForYouApiClient;
        addButtonsContent();
        buttonApartment.addClickListener(event -> apartmentView());
        buttonReservation.addClickListener(event -> reservationView());
    }

    public void addButtonsContent() {
        HorizontalLayout buttonsContent = new HorizontalLayout(buttonLogIn, buttonRegister, buttonApartment,
                buttonReservation, buttonCustomer);
        buttonsContent.setAlignItems(Alignment.CENTER);
        add(buttonsContent);
    }

    public void apartmentView() {
        removeAll();
        addButtonsContent();
        ApartmentForm apartmentForm = new ApartmentForm(this, roomForYouApiClient, longToStringEncoder);
        apartmentGrid.setColumns("city", "street", "streetNumber", "apartmentNumber", "id", "customerId");
        HorizontalLayout mainContent = new HorizontalLayout(apartmentGrid, apartmentForm);
        mainContent.setSizeFull();
        apartmentGrid.setSizeFull();
        add(apartmentGrid, mainContent);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        apartmentGrid.setItems((roomForYouApiClient.getApartmentsResponse()));
    }

    public void reservationView() {
        removeAll();
        addButtonsContent();
    }

    public void customersView() {
        removeAll();
        addButtonsContent();
    }
}
