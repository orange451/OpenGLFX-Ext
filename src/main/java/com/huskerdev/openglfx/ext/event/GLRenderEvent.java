package com.huskerdev.openglfx.ext.event;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * An event indicating a render event.
 * @since JavaFX 2.0
 */
public class GLRenderEvent extends Event {

    private static final long serialVersionUID = 20121107L;
    
    private double deltaTime;

    /**
     * Common supertype for all event types.
     */
    public static final EventType<GLRenderEvent> ANY =
            new EventType<GLRenderEvent> (Event.ANY, "RENDER");

    /**
     * Creates new instance of RenderEvent.
     * @param eventType Type of the event
     * @param deltaTime 
     */
    public GLRenderEvent(final @NamedArg("eventType") EventType<? extends GLRenderEvent> eventType, double deltaTime) {
        super(eventType);
        this.deltaTime = deltaTime;
    }

    @SuppressWarnings("unchecked")
	@Override
    public EventType<? extends GLRenderEvent> getEventType() {
        return (EventType<? extends GLRenderEvent>) super.getEventType();
    }

    public double getDeltaTime() {
    	return this.deltaTime;
    }
}