package org.example.listeners;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;

public class LogAttributeChanges implements ServletContextAttributeListener {
    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        event.getServletContext().log(String.format("[LogAttributeChanges] New value of", event.getName(), event.getValue()));
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        event.getServletContext().log(String.format("[LogAttributeChanges] Old value of", event.getName(), event.getValue()));

    }
}
