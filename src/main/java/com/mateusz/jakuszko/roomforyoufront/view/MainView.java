package com.mateusz.jakuszko.roomforyoufront.view;

import com.mateusz.jakuszko.roomforyoufront.form.ApartmentForm;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client.RoomForYouApartmentApiClient;
import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {
    private Grid grid = new Grid<>(ApartmentDto.class);
    private final RoomForYouApartmentApiClient roomForYouApiClient;

    public MainView(@Autowired RoomForYouApartmentApiClient roomForYouApiClient) {
        ApartmentForm apartmentForm = new ApartmentForm(this, roomForYouApiClient);
        this.roomForYouApiClient = roomForYouApiClient;
        grid.setColumns("city", "street", "streetNumber", "apartmentNumber");
        HorizontalLayout mainContent = new HorizontalLayout(grid, apartmentForm);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(grid, mainContent);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        grid.setItems((roomForYouApiClient.getApartmentsResponse()));
    }

}
