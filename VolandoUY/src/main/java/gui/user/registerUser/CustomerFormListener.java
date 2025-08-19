package gui.user.registerUser;

import java.util.EventListener;

public interface CustomerFormListener extends EventListener {
    void formEventOccurred(CustomerFormEvent event);
}
