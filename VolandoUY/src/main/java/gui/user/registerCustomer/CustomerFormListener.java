package gui.user.registerCustomer;

import java.util.EventListener;

public interface CustomerFormListener extends EventListener {
    void formEventOccurred(CustomerFormEvent event);
}
