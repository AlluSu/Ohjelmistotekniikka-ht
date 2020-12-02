# Ohjelmistotekniikka
Tämä repositorio sisältää Helsingin yliopiston _Ohjelmistotekniikka_-kurssin **harjoitustyön** ja laskarit.

# Opintorekisteri
Opintorekisteri-sovellus on ohjelma, johon käyttäjän on mahdollista lisätä kursseja ja siten pitää kirjaa tällä hetkellä meneillään olevista kursseista.

## Dokumentaatio
[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)  
[Työaikakirjanpito](dokumentaatio/tuntikirjanpito.md)  
[Arkkitehtuurikuvaus](dokumentaatio/arkkitehtuuri.md)  
[Työaikakirjanpito](dokumentaatio/tuntikirjanpito.md)  

## Releaset
[viikko 5](releases/tag/viikko5)

## Komentorivitoiminnot

### Ohjelman ajaminen komentoriviltä
Oletetaan, että Opintorekisteri-hakemisto on käyttäjän koneella. Jos näin ei ole, niin tämän saa esimerkiksi komentoriviltä komennolla `git clone https://github.com/AlluSu/Ohjelmistotekniikka-ht.git`.   
Mennään Opintorekisteri-hakemistoon ja suoritetaan  
`mvn compile exec:java -Dexec.mainClass=opintorekisteri.ui.StudyRegisterUi`  

### Suoritettavan jarin generointi  
Mene komentorivillä projektihakemistoon `Opintorekisteri` ja suorita komento `mvn package`.  
Ajettava jar-tiedosto tulee hakemistoon *target* ja on nimeltään *Opintorekisteri-1.0-SNAPSHOT.jar*.  
Ohjelman voi ajaa komennolla `java -jar Opintorekisteri-1.0-SNAPSHOT.jar`.  

### Testaus
Testit voidaan suorittaa komennolla
`mvn test`.  

Testikattavuusraportti voidaan luoda komennolla `mvn jacoco:report`. 
Raportti on luettavissa siirtymällä hakemistossa paikkaan `target/site/jacoco/index.html` ja avaamalla index.html esimerkiksi selaimessa.

### Checkstyle
Tiedostoon [checkstyle.xml](Opintorekisteri/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla `mvn jxr:jxr checkstyle:checkstyle`.  
Raportti voidaan avata esimerkiksi selaimessa ja raportti löytyy paikasta `target/site/checkstyle.html`.
