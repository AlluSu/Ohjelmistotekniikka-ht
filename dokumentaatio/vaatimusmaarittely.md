# Vaatimusmäärittely

## Sovelluksen tarkoitus  
Sovelluksen konsepti on eräänlainen ajopäiväkirja hevoselle. Päiväkirjan avulla pysytään kartalla hevosen kunnosta ja sen kehittymisestä, jotta hevonen saa treeniä tarpeeksi. Aluksi sovellus toimisi ikään
kuin yhtenä instanssia eli yksi käyttäjä pitää kirjaa omista treenattavistaan. Sovellusta voitaisiin ajan kanssa laajentaa esimerkiksi sellaiseksi, että sovelluksessa olisi useita käyttäjiä ja jokaiseen
käyttäjään olisi liitetty tällöin omat treenattavat eli siihen tulee kirjautumis ominaisuus.

## Käyttäjät
Aluksi sovelluksessa on vain yhdenlaisia käyttäjiä eli normaaleita käyttäjiä. Ajan puitteissa voitaisiin lisätä myös isommilla oikeuksilla varustettuja Admin-käyttäjiä. Adminit pystyisivät muun muassa
poistamaan tietokannasta dataa tai jotain muuta kriittistä.

## Käyttöliittymäluonnos
Tulossa

# Perusversion tarjoama toiminnallisuus

## Ennen kirjautumista
* Käyttäjä voi luoda sovellukseen käyttäjätunnuksen
  * täytyy olla uniikki, tarvittaessa järjestelmä ilmoittaa tästä

* Käyttäjä voi kirjautua järjestelmään
  * Jos kirjautuminen ei onnistu, niin siitä ilmoitetaan
  * Jos käyttäjää ei ole olemassa, niin siitä ilmoitetaan

## Kirjautumisen jälkeen
* Käyttäjä näkee omat treenattavat hevosensa ja historian hevosella ajetuista lenkeistä
* Käyttäjä voi lisätä uuden hevosen treenattavaksi
* Käyttäjä voi lisätä uuden treenin hevosen historiaan
* Käyttäjä voi poistaa hevosen ja treenin esimerkiksi jos hevonen lähtee pois
* Käyttäjä voi kirjautua ulos järjestelmästä

# Jatkokehitysideoita
* Salasana kirjautumisen yhteyteen
* Mahdollisuus käyttäjän ja käyttäjätietojen poistoon
* Statistiikkaa lenkeistä ja hevosista
  * Esimerkiksi lenkkien kilometrimäärä, tuntimäärä, useiten kuljetuimmat reitit jne.


