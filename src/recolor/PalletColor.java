/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recolor;

/**
 *
 * @author umarquez
 */
public class PalletColor {
    private final int colorValue;
    private double colorDistance;
    
    public PalletColor(int value)
    {
        this.colorValue = value;
    }
    
    public void setDistance(double distance)
    {
        this.colorDistance = distance;
    }
    
    public double getDistance()
    {
        return this.colorDistance;
    }
    
    public int getRedValue()
    {
        return (colorValue & 0xff0000) >> 16;
    }
    
    public int getGreenValue()
    {
        return (colorValue & 0x00ff00) >> 8;
    }
    
    public int getBlueValue()
    {
        return colorValue & 0x0000ff;
    }
    
    public int getColor()
    {
        return this.colorValue;
    }
}
