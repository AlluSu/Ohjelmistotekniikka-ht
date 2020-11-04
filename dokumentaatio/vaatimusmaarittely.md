# Vaatimusmäärittely

## Sovelluksen tarkoitus  
Sovelluksen tarkoitus on olla opintojen seurantajärjestelmä. Sovellusta voi käyttää usea käyttäjä kirjautumalla aina omilla tunnuksillaan sovellukseen.

## Käyttäjät
Aluksi sovelluksessa on vain yhdenlaisia käyttäjiä eli normaaleita käyttäjiä. Ajan puitteissa voitaisiin lisätä myös isommilla oikeuksilla varustettuja Admin-käyttäjiä. Adminit pystyisivät muun muassa
poistamaan tietokannasta dataa esimerkiksi vaikka muita käyttäjiä yms.

## Käyttöliittymäluonnos
![Kuva käyttöliittymästä](OpintorekisterGUI1.jpg)
![Kuva modaalisesta ikkunasta](OpintorekisteriGUI2.jpg)

Ensimmäisessä kuvassa on käyttöliittymä-luonnokset kirjautumisesta, uuden käyttäjän luomisesta ja näkymästä kun käyttäjä on kirjautunut onnistuneesti.  
Toisessa kuvassa on luonnos millainen modaalinen ikkuna avautuu kun painetaan painiketta "ADD NEW".  

# Perusversion tarjoama toiminnallisuus

## Ennen kirjautumista
* Käyttäjä voi luoda sovellukseen käyttäjätunnuksen
  * täytyy olla uniikki, tarvittaessa järjestelmä ilmoittaa onko käyttäjä varattu vai ei

* Käyttäjä voi kirjautua järjestelmään
  * Jos kirjautuminen ei onnistu, niin siitä ilmoitetaan
  * Jos käyttäjää ei ole olemassa, niin siitä ilmoitetaan

## Kirjautumisen jälkeen
* Käyttäjä näkee omat aktiiviset kurssinsa
* Käyttäjä voi lisätä uuden kurssin aktiivisten kurssien listaan
* Käyttäjä voi merkitä aktiivisen kurssin epäaktiiviseksi jolloin se muuttuu menneeksi kurssiksi
* Käyttäjä näkee menneet kurssinsa 
* Käyttäjä voi kirjautua ulos järjestelmästä

# Jatkokehitysideoita
* Salasana kirjautumisen yhteyteen
* Mahdollisuus käyttäjän ja käyttäjätietojen poistoon
  * Admin-käyttäjä järjestelmään
* Statistiikkaa kursseista ja opinnoista
  * Esimerkiksi kaikkien suoritettujen opintojen keskiarvo, opintopistelaskuri
* Aktiivisten ja menneiden kurssien lajittelu ja haku
* Info-painike aktiivisen ja menneen kurssin viereen, joka kertoo olennaista tietoa kurssista.


