package library.graphics;

import java.awt.Graphics;
import java.awt.Image;

public class ImageObject extends GraphicsObject {
    Image img;

    public ImageObject(Image image){
        this.img = image;
        setSize(img.getWidth(null), img.getHeight(null));
    }

    @Override
    public void update(int deltaTime) {}

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, getPosX()-getWidth()/2, getPosY()-getHeight()/2, getWidth(), getHeight(), null);
    }
}
