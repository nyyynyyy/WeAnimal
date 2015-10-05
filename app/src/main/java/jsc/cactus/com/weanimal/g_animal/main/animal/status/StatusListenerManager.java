package jsc.cactus.com.weanimal.g_animal.main.animal.status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by INSI on 2015. 10. 2..
 */
public class StatusListenerManager {

    private static List<StatusChangeListener> statusChangeListeners = new ArrayList<StatusChangeListener>();

    public static void addStatusChangeListener(StatusChangeListener listener) {
        statusChangeListeners.add(listener);
    }

    public void StatusChangeEventCall() {
        for (StatusChangeListener listener : statusChangeListeners)
            listener.StatusChangeEvent();
    }
}
