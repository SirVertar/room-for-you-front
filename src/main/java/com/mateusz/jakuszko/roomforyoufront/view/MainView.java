package com.mateusz.jakuszko.roomforyoufront.view;

import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.dto.CustomerDto;
import com.mateusz.jakuszko.roomforyoufront.dto.ReservationDto;
import com.mateusz.jakuszko.roomforyoufront.encoder.LocalDateStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.encoder.LongToStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.form.ApartmentForm;
import com.mateusz.jakuszko.roomforyoufront.form.CustomerForm;
import com.mateusz.jakuszko.roomforyoufront.form.ReservationForm;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouApartmentApiClient;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouCustomerApiClient;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouReservationApiClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@Getter
public class MainView extends VerticalLayout {

    public static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String NUMBER_REGEX = "\\d+";
    private final Button buttonLogIn = new Button("Login");
    private final Button buttonRegister = new Button("Register");
    private final Button buttonApartment = new Button("Apartment");
    private final Button buttonReservation = new Button("Reservation");
    private final Button buttonCustomer = new Button("Customer");
    private final RoomForYouApartmentApiClient roomForYouApartmentApiClient;
    private final RoomForYouCustomerApiClient roomForYouCustomerApiClient;
    private final RoomForYouReservationApiClient roomForYouReservationApiClient;
    private final Grid<ApartmentDto> apartmentGrid = new Grid<>(ApartmentDto.class);
    private final Grid<CustomerDto> customerGrid = new Grid<>(CustomerDto.class);
    private final Grid<ReservationDto> reservationGrid = new Grid<>(ReservationDto.class);
    private final LongToStringEncoder longToStringEncoder;
    private final LocalDateStringEncoder localDateStringEncoder;

    public MainView(@Autowired RoomForYouApartmentApiClient roomForYouApartmentApiClient,
                    @Autowired RoomForYouCustomerApiClient roomForYouCustomerApiClient,
                    @Autowired RoomForYouReservationApiClient roomForYouReservationApiClient,
                    @Autowired LongToStringEncoder longToStringEncoder,
                    @Autowired LocalDateStringEncoder localDateStringEncoder) {
        this.longToStringEncoder = longToStringEncoder;
        this.roomForYouApartmentApiClient = roomForYouApartmentApiClient;
        this.roomForYouCustomerApiClient = roomForYouCustomerApiClient;
        this.roomForYouReservationApiClient = roomForYouReservationApiClient;
        this.localDateStringEncoder = localDateStringEncoder;
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

    public void customersView() {
        removeAll();
        addButtonsContent();
        CustomerForm customerForm = new CustomerForm(this, roomForYouCustomerApiClient, longToStringEncoder);
        customerGrid.setColumns("id", "username", "name", "surname", "email", "role");
        HorizontalLayout customerContent = new HorizontalLayout(customerGrid, customerForm);
        customerContent.setSizeFull();
        customerGrid.setSizeFull();
        add(customerGrid, customerContent);
        setSizeFull();
        refreshCustomers();
    }

    public void reservationView() {
        removeAll();
        addButtonsContent();
        ReservationForm reservationForm = new ReservationForm(this, roomForYouReservationApiClient,
                longToStringEncoder, localDateStringEncoder);
        reservationGrid.setColumns("id", "startDate", "endDate", "apartmentId", "customerId");
        HorizontalLayout reservationContent = new HorizontalLayout(reservationGrid, reservationForm);
        reservationContent.setSizeFull();
        reservationGrid.setSizeFull();
        add(reservationGrid, reservationContent);
        setSizeFull();
        refreshReservations();
    }


    public void refreshApartments() {
        apartmentGrid.setItems((roomForYouApartmentApiClient.getApartmentsResponse()));
    }

    public void refreshCustomers() {
        customerGrid.setItems((roomForYouCustomerApiClient.getCustomersResponse()));
    }

    public void refreshReservations() {
        reservationGrid.setItems((roomForYouReservationApiClient.getReservationResponse()));
    }
}
