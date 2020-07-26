package com.mateusz.jakuszko.roomforyoufront.form;

import com.mateusz.jakuszko.roomforyoufront.dto.ReservationDto;
import com.mateusz.jakuszko.roomforyoufront.encoder.LocalDateStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.encoder.LongToStringEncoder;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouReservationApiClient;
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
public class ReservationForm extends FormLayout {
    private TextField startDateField = new TextField("Start date");
    private TextField endDateField = new TextField("End date");
    private TextField apartmentIdField = new TextField("Apartment Id");
    private TextField reservationIdField = new TextField("Reservation Id");
    private TextField customerIdField = new TextField("Customer Id");
    private Button saveButton = new Button("Make reservation");
    private Button deleteButton = new Button("Delete reservation");
    private Label spaceLabel1 = new Label("");
    private Binder<ReservationDto> binder = new Binder<>();
    private MainView mainView;
    private RoomForYouReservationApiClient roomForYouReservationApiClient;
    private ReservationDto reservation = new ReservationDto();
    private LongToStringEncoder longToStringEncoder;
    private LocalDateStringEncoder localDateStringEncoder;

    public ReservationForm(MainView mainView, RoomForYouReservationApiClient roomForYouReservationApiClient,
                           @Autowired LongToStringEncoder longToStringEncoder,
                           @Autowired LocalDateStringEncoder localDateStringEncoder) {
        this.longToStringEncoder = longToStringEncoder;
        this.localDateStringEncoder = localDateStringEncoder;
        this.roomForYouReservationApiClient = roomForYouReservationApiClient;
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        spaceLabel1.setHeight("1em");
        add(startDateField, endDateField, apartmentIdField, customerIdField, spaceLabel1, saveButton,
                reservationIdField, deleteButton);
        binder.forField(apartmentIdField).withConverter(this.longToStringEncoder::decode, this.longToStringEncoder::encode)
                .bind(ReservationDto::getApartmentId, ReservationDto::setApartmentId);
        binder.forField(customerIdField).withConverter(this.longToStringEncoder::decode, this.longToStringEncoder::encode)
                .bind(ReservationDto::getCustomerId, ReservationDto::setCustomerId);
        binder.forField(startDateField).withConverter(this.localDateStringEncoder::decode, this.localDateStringEncoder::encode)
                .bind(ReservationDto::getStartDate, ReservationDto::setStartDate);
        binder.forField(endDateField).withConverter(this.localDateStringEncoder::decode, this.localDateStringEncoder::encode)
                .bind(ReservationDto::getEndDate, ReservationDto::setEndDate);
        setPlaceHolders();
        saveButton.addClickListener(event -> save());
        deleteButton.addClickListener(event -> delete());
        this.mainView = mainView;
    }

    private void save() {
        if (startDateField.getValue().matches(MainView.DATE_REGEX) &&
                endDateField.getValue().matches(MainView.DATE_REGEX) &&
                apartmentIdField.getValue().matches(MainView.NUMBER_REGEX) &&
                customerIdField.getValue().matches(MainView.NUMBER_REGEX)) {
            try {
                binder.writeBean(reservation);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            roomForYouReservationApiClient.postForReservation(reservation);
            setReservation(new ReservationDto());
            mainView.reservationView();
            clear();
        }
        if (startDateField.getValue().matches(MainView.DATE_REGEX)) {
            startDateField.setErrorMessage("Wrong pattern to date");
        }
        if (endDateField.getValue().matches(MainView.DATE_REGEX)) {
            endDateField.setErrorMessage("Wrong pattern to date");
        }
        if (apartmentIdField.getValue().matches(MainView.NUMBER_REGEX)) {
            apartmentIdField.setErrorMessage("You have probably put a word not a number");
        }
        if (customerIdField.getValue().matches(MainView.NUMBER_REGEX)) {
            customerIdField.setErrorMessage("You have probably put a word not a number");
        }
    }

    private void delete() {
        if (reservationIdField.getValue().length() != 0 && reservationIdField.getValue().matches(MainView.NUMBER_REGEX)) {
            Long reservationId = longToStringEncoder.decode(reservationIdField.getValue());
            roomForYouReservationApiClient.deleteReservation(reservationId);
            mainView.reservationView();
            clear();
        } else {
            clear();
            reservationIdField.setErrorMessage("You have probably put a word not a number");
        }
    }

    public void clear() {
        startDateField.clear();
        endDateField.clear();
        reservationIdField.clear();
        apartmentIdField.clear();
        customerIdField.clear();
    }

    private void setPlaceHolders() {
        reservationIdField.setPlaceholder("Reservation id - use it only for delete!!");
        startDateField.setPlaceholder("RRRR-MM-DD - required format!!");
        endDateField.setPlaceholder("RRRR-MM-DD - required format!!");
        apartmentIdField.setPlaceholder("You can check apartment id by click Apartment button above");
        customerIdField.setPlaceholder("You can check customer id by click Customer button above");
        startDateField.setRequired(true);
        endDateField.setRequired(true);
        apartmentIdField.setRequired(true);
        customerIdField.setRequired(true);
        reservationIdField.setRequired(true);
    }
}
