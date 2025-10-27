package com.upvj.latrix.graphicObjects.Rectangles;

import com.upvj.latrix.RenderableCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextLabel extends RectangleLabel {

    private Font font = Font.getDefault();

    private String text;

    private double FONT_SIZE = 25;

    private Paint TextColor;

    public TextLabel(RenderableCanvas parent, String text) {
        super(parent);
        this.text = text;
        TextColor = Color.BLACK;
    }

    public TextLabel(RenderableCanvas parent) {
        super(parent);
        this.text = "Label";
        TextColor = Color.BLACK;
    }

    @Override
    public boolean draw(GraphicsContext gc) {
        super.draw(gc);

        // Measure text at base size 1 to get accurate scaling
        Text t = new Text(text);
        t.setFont(Font.font("Arial", 1));
        double textW = t.getLayoutBounds().getWidth();
        double textH = t.getLayoutBounds().getHeight();

        // Compute scale so text fits inside the box
        double scale = Math.min(
                super.AbsoluteWidth / textW,
                super.AbsoluteHeight / textH
        );

        Font scaledFont = Font.font("Arial", Math.min( FONT_SIZE , scale));
        gc.setFont(scaledFont);
        gc.setFill(TextColor);

        // Re-compute measured values using scaled layout for proper centering
        t.setFont(scaledFont);
        double w = t.getLayoutBounds().getWidth();
        double h = t.getLayoutBounds().getHeight();

        // Center inside the rectangle (baseline adjusted)
        double x = super.AbsoluteX + (super.AbsoluteWidth - w) / 2;
        double y = super.AbsoluteY + (super.AbsoluteHeight - h) / 2 + h;

        gc.fillText(text, x, y);

        return true;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getFONT_SIZE() {
        return FONT_SIZE;
    }

    public void setFONT_SIZE(double FONT_SIZE) {
        this.FONT_SIZE = FONT_SIZE;
    }

    public Paint getTextColor() {
        return TextColor;
    }

    public void setTextColor(Paint textColor) {
        TextColor = textColor;
    }
}
