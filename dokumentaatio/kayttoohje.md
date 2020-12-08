# Käyttöohje

## Konfigurointi

## Ohjelman käynnistäminen  
Oletetaan, että tietokoneelle on ladattu ohjelmasta uusin versio esimerkiksi GitHubin Releases paikasta. Tällöin ohjelma käynnistyy samassa hakemistossa ollessa komennolla `java -jar opintorekisteri-jar`.  

## Kirjautuminen  
Sovellus käynnistyy aluksi kirjautumisnäkymään:  
![kirjautuminen](kuvat/opintorekisterifinnisherlogin.png)  
Kirjautuminen onnistuu kirjoittamalla tietokannassa olemassaoleva käyttäjätunnus syötekentään ja painamalla *login*. Lisäksi on tarjolla mahdollisuus sulkea ohjelma painamalla *Poistu*-painiketta. *Ohje*-painiketta painamalla avautuu pieni dialogi, joka tarjoaa pientä infoa ohjelmasta ja sen käytöstä.  
## Uuden käyttäjän luominen
Kirjautumisnäkymästä on mahdollista siirtyä uuden käyttäjän luomisnäkymään painamalla *luo uusi käyttäjä*-painiketta jolloin avautuu seuraavanlainen näkymä:  
![uusi käyttäjä](kuvat/opintorekisteriuusikayttaja.png)  
Uusi käyttäjä luodaan kirjoittamalla käyttäjätunnus ja nimi teksikenttiin ja painamalla *luo uusi käyttäjä*. Jos luonti onnistuu, palataan takaisin edelliseen näkymään eli kirjautumisnäkymään. On myös mahdollisuus poistua näkymästä painamalla *Poistu*.  
## Opintorekisterin käyttö  
Opintorekisterin ikkuna jossa tapahtuu kaikki päätoiminnallisuus näyttää seuraavalta:  
![pääikkuna](kuvat/opintorekisterifinnishermain.png)  
Ohjelman tarkoitus on, että käyttäjä voi pitää kirjaa aktiivisista ja menneistä opinnoistaan. Vasemmalla on aktiiviset kurssit ja oikealla menneet kurssit.
### kurssin luonti  
Kurssi luodaan kirjoittamalla ikkunan alareunassa oleviin teksikenttiin kurssin tiedot (ks. ylempi kuva), jolloin ne ilmestyvät "aktiiviset"-listaan (ks. alempi kuva).  
![kurssin lisäys](kuvat/opintorekisterifinnishermain2.png)  

### kurssin poisto  
Kurssi voidaan poistaa valitsemalla haluttu kurssi jommasta kummasta listasta ja painamalla *poista kurssi*.  
### kurssin epäaktivointi
Aktiivinen kurssi voidaan muuttaa epäaktiiviseksi valitsemalla kurssi aktiivisten listasta ja painamalla *Siirrä kurssi epäaktiiviseksi*. Jos valitaan kurssi epaktiivisista niin tulee virhedialogi.  
![epäaktivointi](kuvat/opintorekisterifinnishermain3.png)
### ohjelman lopetus
