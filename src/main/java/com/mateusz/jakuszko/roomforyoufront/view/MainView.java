package com.mateusz.jakuszko.roomforyoufront.view;

import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.form.ApartmentForm;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouApartmentApiClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView2 extends VerticalLayout {
    private Button logIn = new Button("Login");
    private Button register = new Button("Register");
    private Button apartment = new Button("Apartment");
    private Button reservation = new Button("Reservation");
    private final RoomForYouApartmentApiClient roomForYouApiClient;

    public MainView2(@Autowired RoomForYouApartmentApiClient roomForYouApiClient) {
        this.roomForYouApiClient = roomForYouApiClient;
        HorizontalLayout mainContent = new HorizontalLayout(logIn, register, apartment, reservation);
        mainContent.setSizeFull();
        add(mainContent);
    }
}
