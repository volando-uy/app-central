package gui.user.registerAirline;

import java.util.EventListener;

public interface AirlineFormListener extends EventListener {
    void formEventOccurred(AirlineFormEvent event);
}
