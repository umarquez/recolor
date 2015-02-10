/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recolor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author umarquez
 */
public class ColorComparison {
    private static final String imgOrigen = "./original.jpg";
    private static final String imgDestino = "./resultado.jpg";
    private static final List<PalletColor> paletaColores = new ArrayList<>();
    private static imageProcessor transformador;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /**
         * Definiendo paleta para escala de grises
         */
        paletaColores.add(new PalletColor(0x000000));
        paletaColores.add(new PalletColor(0x111111));
        paletaColores.add(new PalletColor(0x222222));
        paletaColores.add(new PalletColor(0x333333));
        paletaColores.add(new PalletColor(0x444444));
        paletaColores.add(new PalletColor(0x555555));
        paletaColores.add(new PalletColor(0x666666));
        paletaColores.add(new PalletColor(0x777777));
        paletaColores.add(new PalletColor(0x888888));
        paletaColores.add(new PalletColor(0x999999));
        paletaColores.add(new PalletColor(0xaaaaaa));
        paletaColores.add(new PalletColor(0xbbbbbb));
        paletaColores.add(new PalletColor(0xcccccc));
        paletaColores.add(new PalletColor(0xdddddd));
        paletaColores.add(new PalletColor(0xeeeeee));
        paletaColores.add(new PalletColor(0xffffff));
        // ========== Colores adicionales ==========
        //paletaColores.add(new PalletColor(0x896C40));
        // =========================================
        
                
        transformador = new imageProcessor();
        transformador.setOriginJPEGFile(imgOrigen);
        transformador.setDestinyJPEGFile(imgDestino);
        transformador.setPallet(paletaColores);
        
        transformador.processImage();
    }    
}
