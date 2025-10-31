package com.upvj.latrix.graphicObjects.Rectangles;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Objects;

public class ImageLabel extends ARectangleLabel{

    private Image image;

    public static Image getImageFromResource(String url){
        return new Image(
                Objects.requireNonNull(
                        ImageLabel.class.getResource( "/com/upjv/latrix/" + url)
                ).toExternalForm()
        );
    }

    public static Image screen(Image src, Color tint) {
        int w = (int) src.getWidth();
        int h = (int) src.getHeight();
        WritableImage out = new WritableImage(w, h);
        PixelReader pr = src.getPixelReader();
        PixelWriter pw = out.getPixelWriter();

        double tr = tint.getRed();
        double tg = tint.getGreen();
        double tb = tint.getBlue();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = pr.getArgb(x, y);

                int a = (argb >> 24) & 0xFF;
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;

                int gray = (r + g + b) / 3;

                int nr = (int) (gray * tr);
                int ng = (int) (gray * tg);
                int nb = (int) (gray * tb);

                int newArgb = (a << 24)
                        | (nr << 16)
                        | (ng << 8)
                        | nb;
                pw.setArgb(x, y, newArgb);
            }
        }
        return out;
    }

    public static Image moveHSV(Image src, double dh, double ds, double dv) {
        int w = (int) src.getWidth();
        int h = (int) src.getHeight();
        WritableImage out = new WritableImage(w, h);
        PixelReader pr = src.getPixelReader();
        PixelWriter pw = out.getPixelWriter();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color c = pr.getColor(x, y);
                double hVal = (c.getHue() + dh) % 360;
                if (hVal < 0) hVal += 360;

                double sVal = Math.min(1, Math.max(0, c.getSaturation() + ds));
                double vVal = Math.min(1, Math.max(0, c.getBrightness() + dv));

                Color nc = Color.hsb(hVal, sVal, vVal, c.getOpacity());
                pw.setColor(x, y, nc);
            }
        }
        return out;
    }




    /* Unused
    public ImageLabel(Scene scene, Image image) {
        super(scene);
        this.image = image;
    }
    */
    public ImageLabel(Scene scene, String filePath) {
        super(scene);
        this.image = ImageLabel.getImageFromResource(filePath);

        super.setSizeOffset((int) image.getWidth(), (int)image.getHeight());



    }

    @Override
    public boolean draw(GraphicsContext gc) {
        try{
            super.draw(gc);


            gc.drawImage(image,AbsoluteX,AbsoluteY,AbsoluteWidth,AbsoluteHeight);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setSizeOffsetToImageSize(){
        super.SizeOffsetX = (int) image.getWidth();
        super.SizeOffsetY = (int) image.getHeight();
    }
}
