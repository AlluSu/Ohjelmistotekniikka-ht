/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aleksi
 */
public class KassapaateTest {
    Kassapaate paate;
    Maksukortti kortti = new Maksukortti(1000);
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
    }
    
    @Test
    public void kassassaOikeaMaaraRahaaAlussa() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kassassaOikeaMaaraLounaitaAlussa() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanOnnistunutOsto() {
        assertEquals(60, paate.syoEdullisesti(300));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100240, paate.kassassaRahaa());
    }
    
    @Test
    public void edullisenLounaanEpaonnistunutOsto() {
        assertEquals(230, paate.syoEdullisesti(230));
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test public void maukkaanLounaanOnnistunutOsto() {
        assertEquals(10, paate.syoMaukkaasti(410));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void maukkaanLounaanEpaonnistunutOsto() {
        assertEquals(390, paate.syoMaukkaasti(390));
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortillaOstettuMaukasLounas() {
        assertTrue(paate.syoMaukkaasti(kortti));
        assertEquals(1,paate.maukkaitaLounaitaMyyty());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals("saldo: 6.0", kortti.toString());
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void kortillaOstettuEdullinenLounas() {
        assertTrue(paate.syoEdullisesti(kortti));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals("saldo: 7.60", kortti.toString());
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortillaOstettuMaukasLounasEpaonnistuu() {
        kortti = new Maksukortti(230);
        assertTrue(!paate.syoMaukkaasti(kortti));
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals("saldo: 2.30", kortti.toString());
    }
    
    @Test
    public void kortillaOstettuEdullinenLounasEpaonnistuu() {
        kortti = new Maksukortti(230);
        assertTrue(!paate.syoEdullisesti(kortti));
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals("saldo: 2.30", kortti.toString());
    }
    
    @Test
    public void kortilleLadatessaSaldoMuuttuuJaKassaKasvaa() {
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, paate.kassassaRahaa());
        assertEquals("saldo: 11.0", kortti.toString());
    }
    
    @Test
    public void kortilleLadatessaNegatiivinenArvo() {
        paate.lataaRahaaKortille(kortti, -100);
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals("saldo: 10.0", kortti.toString());
    }
}
