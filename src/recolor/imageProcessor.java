/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recolor;

import java.util.List;
import java.io.*;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Comparator;

/**
 *
 * @author umarquez
 */
public class imageProcessor{
    private List<PalletColor> colorsPallet;
    private String originFilePath;
    private String destinyFilePath;
    private File oFile, iFile;
        
    public imageProcessor()
    {
    }
    
    public final boolean processImage()
    {
        String errMsg = "";
        int err = 0;
        
        /**
         * ======================================================
         * Validación de parámetros
         * ======================================================
         */
        
        // - Paleta de colores
        if (colorsPallet.size() <= 1)
        {
            err++;
            errMsg += "No se ha definido una paleta de colores de 2 o más elementos.\n";
        }
        
        // - Imagen de origen
        this.iFile = new File(this.originFilePath);
        if (!iFile.exists())
        {
            err++;
            errMsg += "El archivo \"origen\" ("+this.iFile.getAbsolutePath()+") no existe.\n";
        }
        
        // - Imagen destino
        this.oFile = new File(this.destinyFilePath);
        if (oFile.exists())            
        {
            int msjOverwrite = JOptionPane.showConfirmDialog(null, 
                    "¿Desea sobreescribir el archivo \""+this.oFile.getAbsolutePath()+"\"?", 
                    "El archivo destino ya existe.", JOptionPane.YES_NO_OPTION);
            if (msjOverwrite == JOptionPane.YES_OPTION) {
                oFile.delete();
            }else{
                err++;
                errMsg += "El archivo \"destino\" ya existe.\n";
            }
        }
        
        // - Procesando imagen
        if (err == 0) {
            return imageTransformation();
        }else{
            JOptionPane.showMessageDialog(null, errMsg, "Se detectaron los siguientes errores:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private boolean imageTransformation()
    {
        boolean ret = false;
        
        try {
            BufferedImage origen = ImageIO.read(this.iFile);
            BufferedImage destino = new BufferedImage(origen.getWidth(), origen.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            
            /**
             * Iterando sobre los pixeles de la imagen
             * y obteniendo el color con mayor similitud.
             */
            int pixel, nColor;
            for (int i = 0; i < origen.getWidth(); i++) {
                for (int j = 0; j < origen.getHeight(); j++) {
                    // Obteniendo color del pixel(i,j)
                    pixel = origen.getRGB(i, j);
                    // Obteniendo color con mayor similitud
                    nColor = getColorFor(pixel);
                    // Almacenando nuevo color del pixel(i,j)
                    destino.setRGB(i, j, nColor);
                }
            }
            
            // Almacenando imagen procesada
            ImageIO.write(destino, "jpg", oFile);
            ret = true;
        } catch (Exception e) {
            ret = false;
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error al procesar imagen.", JOptionPane.ERROR_MESSAGE);
        } finally {
            return ret;
        }
    }
    
    private int getColorFor(int color)
    {
        double dr, dg, db;
        
        // Obteniendo valores RGB del pixel
        int  oRed   = (color & 0x00ff0000) >> 16;
        int  oGreen = (color & 0x0000ff00) >> 8;
        int  oBlue  =  color & 0x000000ff;
        
        for (PalletColor pColor : this.colorsPallet) {
            // Obteniendo catetos
            dr = Math.pow(Math.abs(pColor.getRedValue() - oRed), 2);
            dg = Math.pow(Math.abs(pColor.getGreenValue() - oGreen), 2);
            db = Math.pow(Math.abs(pColor.getBlueValue() - oBlue), 2);
            
            // Almacenando hipotenusa
            pColor.setDistance(Math.sqrt(dr+dg+db));
        }
        
        // Ordenando de menos a mayor
        this.colorsPallet.sort(Comparator.comparingDouble(PalletColor::getDistance));
        
        // Devolviendo color más "cercano"
        return this.colorsPallet.get(0).getColor();
    }
    
    public final void setDestinyJPEGFile(String imageJPEG)
    {
        this.destinyFilePath = imageJPEG;
    }
    
    public final String getDestinyJPEGFile()
    {
        return this.destinyFilePath;
    }
    
    public final void setOriginJPEGFile(String imageJPEG)
    {
        this.originFilePath = imageJPEG;
    }
    
    public final String getOriginJPEGFile()
    {
        return this.originFilePath;
    }
    
    public final void setPallet(List<PalletColor> colors)
    {
        this.colorsPallet = colors;
    }
    
    public final List<PalletColor> getPallet()
    {
        return colorsPallet;
    }
}
