package library.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import library.DynamicValue;

public abstract class GraphicsObject {

    private Font font = new Font("Dialog",Font.PLAIN,12);
    private Color foreground = Color.BLACK;

    private int width;
    private int height;
    
    private float horizontalAlignment;
    private float verticalAlignment;

    private DynamicValue offsetX;
    private DynamicValue offsetY;

    public GraphicsObject(){
        horizontalAlignment = 0;
        verticalAlignment = 0;
        offsetX = new DynamicValue(0);
        offsetY = new DynamicValue(0);
    }

    public Color getForeground(){
        return foreground;
    }

    public void setFont(Font f){
        this.font = f;
    }

    public void setAlignment(float horizontal, float vertical){
        horizontalAlignment = horizontal;
        verticalAlignment = vertical;
    }

    public void setSize(int w, int h){
        this.width = w;
        this.height = h;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public float getHorizontalAlignment(){
        return horizontalAlignment;
    }

    public float getVerticalAlignment(){
        return verticalAlignment;
    }

    public int getPosX(){
        return offsetX.getValue();
    }

    public int getPosY(){
        return offsetY.getValue();
    }

    public DynamicValue getOffsetX(){
        return offsetX;
    }

    public DynamicValue getOffsetY(){
        return offsetY;
    }

    public Font getFont(){
        return font;
    }

    public abstract void update(int deltaTime);
    public abstract void draw(Graphics g);
}
