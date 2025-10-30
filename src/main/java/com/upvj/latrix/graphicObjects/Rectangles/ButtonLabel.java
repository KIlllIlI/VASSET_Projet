package com.upvj.latrix.graphicObjects.Rectangles;

import com.upvj.latrix.GraphicObject;
import com.upvj.latrix.RenderableCanvas;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class ButtonLabel<R extends ARectangleLabel> extends ARectangleLabel  {

    private R wrappedRectangle;

    // User-provided callbacks
    private EventHandler<MouseEvent> onClick;
    private Runnable onHoverEnter;
    private Runnable onHoverExit;

    private RenderableCanvas Parent;

    private boolean isHovered = false;



    public ButtonLabel(Scene scene,RenderableCanvas Parent , R WrappedRectangle) {
        super(scene);
        this.Parent = Parent;
        this.wrappedRectangle = WrappedRectangle;
        System.out.println(scene.toString());

        scene.addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        scene.addEventHandler(MouseEvent.MOUSE_EXITED, e -> handleMouseExited());
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseClicked);




    }

    @Override
    public boolean draw(GraphicsContext gc) {
        return wrappedRectangle.draw(gc);
    }

    public R getWrappedRectangle() {
        return wrappedRectangle;
    }

    public void setWrappedRectangle(R wrappedRectangle) {
        this.wrappedRectangle = wrappedRectangle;
    }

    private void handleMouseMoved(MouseEvent e) {
        if (wrappedRectangle.contains(e.getX(), e.getY())) {
            if (!isHovered) {
                isHovered = true;
                if (onHoverEnter != null) onHoverEnter.run();
            }
        } else {
            handleMouseExited();
        }
    }

    private void handleMouseExited() {
        if (isHovered) {
            isHovered = false;
            if (onHoverExit != null) onHoverExit.run();
        }
    }

    private void handleMouseClicked(MouseEvent e) {
        if (wrappedRectangle.contains(e.getX(), e.getY()) && onClick != null && Parent.isRendering()) {
            onClick.handle(e);
        }
    }

    public EventHandler<MouseEvent> getOnClick() {
        return onClick;
    }

    public void setOnClick(EventHandler<MouseEvent> onClick) {
        this.onClick = onClick;
    }

    public Runnable getOnHoverEnter() {
        return onHoverEnter;
    }

    public void setOnHoverEnter(Runnable onHoverEnter) {
        this.onHoverEnter = onHoverEnter;
    }

    public Runnable getOnHoverExit() {
        return onHoverExit;
    }

    public void setOnHoverExit(Runnable onHoverExit) {
        this.onHoverExit = onHoverExit;
    }

    public boolean isHovered(){
        return isHovered;
    }


}

