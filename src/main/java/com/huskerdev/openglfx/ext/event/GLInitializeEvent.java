package com.huskerdev.openglfx.ext.event;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * An event indicating a user input.
 * @since JavaFX 2.0
 */
public class GLInitializeEvent extends Event {

    private static final long serialVersionUID = 20121107L;

    /**
     * Common supertype for all event types.
     */
    public static final EventType<GLInitializeEvent> ANY =
            new EventType<GLInitializeEvent> (Event.ANY, "GL_INITIALIZE");

    /**
     * Creates new instance of RenderEvent.
     * @param eventType Type of the event
     */
    public GLInitializeEvent(final @NamedArg("eventType") EventType<? extends GLInitializeEvent> eventType) {
        super(eventType);
    }

    /**
     * Creates new instance of RenderEvent.
     * @param source Event source
     * @param target Event target
     * @param eventType Type of the event
     */
    public GLInitializeEvent(final @NamedArg("source") Object source,
                      final @NamedArg("target") EventTarget target,
                      final @NamedArg("eventType") EventType<? extends GLInitializeEvent> eventType) {
        super(source, target, eventType);
    }

    @SuppressWarnings("unchecked")
	@Override
    public EventType<? extends GLInitializeEvent> getEventType() {
        return (EventType<? extends GLInitializeEvent>) super.getEventType();
    }

}