package com.mateusz.jakuszko.roomforyoufront.view;

import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.dto.CustomerDto;
import com.mateusz.jakuszko.roomforyoufront.encoder.LongToStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.form.ApartmentForm;
import com.mateusz.jakuszko.roomforyoufront.form.CustomerForm;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouApartmentApiClient;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouCustomerClient;
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
    private final RoomForYouApartmentApiClient roomForYouApartmentApiClient;
    private final RoomForYouCustomerClient roomForYouCustomerClient;
    private Grid apartmentGrid = new Grid<>(ApartmentDto.class);
    private Grid customerGrid = new Grid<>(CustomerDto.class);
    private LongToStringEncoder longToStringEncoder;

    public MainView(@Autowired RoomForYouApartmentApiClient roomForYouApartmentApiClient,
                    @Autowired RoomForYouCustomerClient roomForYouCustomerClient,
                    @Autowired LongToStringEncoder longToStringEncoder) {
        this.longToStringEncoder = longToStringEncoder;
        this.roomForYouApartmentApiClient = roomForYouApartmentApiClient;
        this.roomForYouCustomerClient = roomForYouCustomerClient;
        addButtonsContent();
        buttonApartment.addClickListener(event -> apartmentView());
        buttonReservation.addClickListener(event -> reservationView());
        buttonCustomer.addClickListener(event -> customersView());
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
        ApartmentForm apartmentForm = new ApartmentForm(this, roomForYouApartmentApiClient, longToStringEncoder);
        apartmentGrid.setColumns("city", "street", "streetNumber", "apartmentNumber", "id", "customerId");
        HorizontalLayout apartmentContent = new HorizontalLayout(apartmentGrid, apartmentForm);
        apartmentContent.setSizeFull();
        apartmentGrid.setSizeFull();
        add(apartmentGrid, apartmentContent);
        setSizeFull();
        refreshApartments();
    }

    public void refreshApartments() {
        apartmentGrid.setItems((roomForYouApartmentApiClient.getApartmentsResponse()));
    }

    public void refreshCustomers() {
        customerGrid.setItems((roomForYouCustomerClient.getCustomersResponse()));
    }

    public void reservationView() {
        removeAll();
        addButtonsContent();
    }

    public void customersView() {
        removeAll();
        addButtonsContent();
        CustomerForm customerForm = new CustomerForm(this, roomForYouCustomerClient, longToStringEncoder);
        customerGrid.setColumns("id", "username", "name", "surname", "email", "role");
        HorizontalLayout customerContent = new HorizontalLayout(customerGrid, customerForm);
        customerContent.setSizeFull();
        customerGrid.setSizeFull();
        add(customerGrid, customerContent);
        setSizeFull();
        refreshCustomers();
    }
}
