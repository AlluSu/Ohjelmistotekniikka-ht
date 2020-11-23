# Ohjelmistotekniikka
Tämä repositorio sisältää Helsingin yliopiston _Ohjelmistotekniikka_-kurssin **harjoitustyön** ja laskarit.

# Opintorekisteri
Opintorekisteri-sovellus on ohjelma, johon käyttäjän on mahdollista lisätä kursseja ja siten pitää kirjaa tällä hetkellä meneillään olevista kursseista.

## Dokumentaatio
[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)  
[Työaikakirjanpito](dokumentaatio/tuntikirjanpito.md)
[Arkkitehtuurikuvaus](dokumentaatio/arkkitehtuuri.md)
[Työaikakirjanpito](dokumentaatio/tuntikirjanpito.md)
## Komentorivitoiminnot

### Ohjelman ajaminen komentoriviltä
Oletetaan, että Opintorekisteri-hakemisto on imuroitu Git:istä omalle koneelle. Mennään Opintorekisteri-hakemistoon ja suoritetaan  
`mvn compile exec:java -Dexec.mainClass=opintorekisteri.ui.StudyRegisterUi`  

### Testaus
Testit voidaan suorittaa komennolla
`mvn test`  

Testikattavuusraportti voidaan luoda komennolla `mvn jacoco:report` 
Raportti on luettavissa siirtymällä hakemistossa paikkaan `target/site/jacoco/index.html` ja avaamalla index.html esimerkiksi selaimessa.

### Checkstyle
Tiedostoon [checkstyle.xml](checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla  
`mvn jxr:jxr checkstyle:checkstyle`  
Raportti voidaan avata selaimessa ja raportti löytyy paikasta `target/site/checkstyle.html`
