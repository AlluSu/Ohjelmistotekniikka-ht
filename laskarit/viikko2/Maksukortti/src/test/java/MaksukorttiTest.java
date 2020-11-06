/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Ohjelmistotekniikka-kurssin laskarit vk2
 * @author Aleksi Suuronen
 */
public class MaksukorttiTest {
    
    Maksukortti kortti;
    
    public MaksukorttiTest() {
    }

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }
    
     @Test
     public void hello() {}
     
     @Test
     public void konstruktoriAsettaaSaldonOikein() {
         assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
     }
     
     @Test
     public void syoEdullisestiVahentaaSaldoaOikein() {
         kortti.syoEdullisesti();
         assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
     }
     
     @Test
     public void syoMaukkaastiVahentaaSaldoaOikein() {
         kortti.syoMaukkaasti();
         assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
     }
     
     @Test
     public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
         kortti.syoMaukkaasti();
         kortti.syoMaukkaasti();
         kortti.syoEdullisesti();
         assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
     }
     
     @Test
     public void syoMaukkaastiEiVieSaldoaNegatiiviseksi() {
         kortti.syoEdullisesti();
         kortti.syoEdullisesti();
         kortti.syoEdullisesti();
         kortti.syoMaukkaasti();
         assertEquals("Kortilla on rahaa 2.5 euroa", kortti.toString());
    }
     
     @Test
     public void kortilleVoiLadataRahaa() {
         kortti.lataaRahaa(25);
         assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
     }
     
     @Test
     public void kortinSaldoEiYlitaMaksimiarvoa() {
         kortti.lataaRahaa(200);
         assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
     }
     
     @Test
     public void kortinArvoEiMuutuNegatiivisellaLatauksella() {
         kortti.lataaRahaa(-200);
         assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
     }
     
     @Test
     public void edullisenVoiMaksaaKunOnTasaraha() {
         kortti = new Maksukortti(2.5);
         kortti.syoEdullisesti();
         assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
     }
     
     @Test
     public void maukkaanVoiMaksaaKunOnTasaraha() {
         kortti = new Maksukortti(4.0);
         kortti.syoMaukkaasti();
         assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
     }
}
