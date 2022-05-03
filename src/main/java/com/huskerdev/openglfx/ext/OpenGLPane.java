package com.huskerdev.openglfx.ext;

import com.huskerdev.openglfx.DirectDrawPolicy;
import com.huskerdev.openglfx.FXGLInitializer;
import com.huskerdev.openglfx.GLCanvasAnimator;
import com.huskerdev.openglfx.OpenGLCanvas;
import com.huskerdev.openglfx.events.GLInitializeEvent;
import com.huskerdev.openglfx.events.GLRenderEvent;
import com.huskerdev.openglfx.lwjgl.LWJGLInitializer;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class OpenGLPane extends StackPane {
	private OpenGLCanvas canvas;

	private DoubleProperty desiredFPS;

	private static final double INITIAL_FPS = 60;

	public static final FXGLInitializer LWJGL_MODULE = new LWJGLInitializer();

	private OpenGLPane(OpenGLCanvas canvas) {
		this.canvas = canvas;

		// Setup desired FPS bridge
		GLCanvasAnimator animator = this.canvas.getAnimator();
		this.desiredFPS = new SimpleDoubleProperty(INITIAL_FPS);
		this.desiredFPS.addListener((observable, oldValue, newValue) -> {
			animator.setFps(newValue.doubleValue());
		});

		// Setup size bridge
		this.canvas.prefWidthProperty().bind(this.widthProperty());
		this.canvas.prefHeightProperty().bind(this.heightProperty());
		this.canvas.maxWidthProperty().bind(this.maxWidthProperty());
		this.canvas.maxHeightProperty().bind(this.maxHeightProperty());
		this.canvas.minWidthProperty().bind(this.minWidthProperty());
		this.canvas.minHeightProperty().bind(this.minHeightProperty());

		// OpenGL Initialize Bridge
		this.addEventHandler(GLInitializeEvent.getANY(), (object) -> {
			getOnGLInitialize().handle(object);
		});
		canvas.onInitialize((event) -> {
			this.fireEvent(event);
		});

		// OpenGL Rendering Bridge
		this.addEventHandler(GLRenderEvent.getANY(), (object) -> {
			getOnRender().handle(object);
		});
		canvas.onRender((event) -> {
			this.fireEvent(event);
		});

		// Force canvas in children
		this.getChildren().add(canvas);
		this.getChildren().addListener(new ListChangeListener<>() {
			@Override
			public void onChanged(Change<? extends Node> c) {
				while (c.next()) {
					for (Node node : c.getRemoved()) {
						if (node == canvas) {
							Platform.runLater(() -> {
								OpenGLPane.this.getChildren().add(0, canvas);
							});
						}
					}
				}
			}
		});
	}

	/**
	 * Initialize a new OpenGLPane instance. Backed by
	 * {@link OpenGLCanvas#create(FXGLInitializer)}. OpenGLPane provides a more
	 * JavaFX friendly API.
	 */
	public static OpenGLPane create(FXGLInitializer initializer, DirectDrawPolicy policy) {
		OpenGLCanvas canvas = OpenGLCanvas.create(initializer, policy);
		return new OpenGLPane(canvas);
	}

	/**
	 * Property for overriding the OpenGLPane's desired FPS.
	 * <p>
	 * Defaults to <code>60</code> fps.
	 */
	public DoubleProperty desiredFPSProperty() {
		return this.desiredFPS;
	}

	/**
	 * Sets the desired fps for the OpenGLPane.
	 */
	public void setDesiredFPS(double fps) {
		this.desiredFPS.set(fps);
	}

	/**
	 * Returns the desired fps for the OpenGLPane.
	 */
	public double getDesiredFPS() {
		return this.desiredFPS.get();
	}

	/**
	 * @return OpenGLCanvas backing rendering.
	 */
	public OpenGLCanvas getCanvas() {
		return this.canvas;
	}

	/**
	 * Set callback for when OpenGL is initialized with this pane.
	 */
	public final void setOnRender(EventHandler<? super GLRenderEvent> value) {
		onGLRenderProperty().set(value);
	}

	public final EventHandler<? super GLRenderEvent> getOnRender() {
		return onGLRenderProperty() == null ? null : onGLRenderProperty().get();
	}

	private SimpleObjectProperty<EventHandler<? super GLRenderEvent>> onGLRender = new SimpleObjectProperty<EventHandler<? super GLRenderEvent>>();
	private SimpleObjectProperty<EventHandler<? super GLRenderEvent>> onGLRenderProperty() {
		return onGLRender;
	}

	/**
	 * Set callback for when OpenGL is initialized with this pane.
	 */
	public final void setOnGLInitialize(EventHandler<? super GLInitializeEvent> value) {
		onGLInitializeProperty().set(value);
	}

	public final EventHandler<? super GLInitializeEvent> getOnGLInitialize() {
		return onGLInitializeProperty() == null ? null : onGLInitializeProperty().get();
	}

	private SimpleObjectProperty<EventHandler<? super GLInitializeEvent>> onGLInitialize = new SimpleObjectProperty<EventHandler<? super GLInitializeEvent>>();
	private SimpleObjectProperty<EventHandler<? super GLInitializeEvent>> onGLInitializeProperty() {
		return onGLInitialize;
	}
}
