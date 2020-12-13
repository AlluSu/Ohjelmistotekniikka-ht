# Ohjelmistotekniikka
Tämä repositorio sisältää Helsingin yliopiston _Ohjelmistotekniikka_-kurssin **harjoitustyön** ja laskarit.

# Opintorekisteri
Opintorekisteri-sovellus on ohjelma, johon käyttäjän on mahdollista lisätä kursseja ja siten pitää kirjaa tällä hetkellä meneillään olevista kursseista ja menneistä kursseistaan. Sovellukseen on mahdollista usean käyttäjän luoda oma käyttäjätunnus, jolloin jokaisella käyttäjällä on oma yksilöllinen lista opinnoistaan.

## Dokumentaatio  
[Käyttöohje](dokumentaatio/kayttoohje.md)  

[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)  

[Arkkitehtuurikuvaus](dokumentaatio/arkkitehtuuri.md)  

[Työaikakirjanpito](dokumentaatio/tuntikirjanpito.md)  

## Releaset
[viikko 5](https://github.com/AlluSu/Ohjelmistotekniikka-ht/releases/tag/viikko5)

[viikko 6](https://github.com/AlluSu/Ohjelmistotekniikka-ht/releases/tag/vk6v3.0)
## Komentorivitoiminnot

### Ohjelman ajaminen komentoriviltä
Oletetaan, että Opintorekisteri-hakemisto on käyttäjän koneella. Jos näin ei ole, niin tämän saa esimerkiksi komentoriviltä komennolla   `git clone https://github.com/AlluSu/Ohjelmistotekniikka-ht.git`   (olettaen, että koneella on git asennettuna).   
Mennään Opintorekisteri-hakemistoon ja suoritetaan  
`mvn compile exec:java -Dexec.mainClass=opintorekisteri.ui.StudyRegisterUi`.  
Vaihtoehtoisesti voi ladata paikasta *Releases* tiedoston `opintorekisteri.jar`. Tällöin täytyy mennä komentorivillä samaan hakemistoon ja suorittaa komennon\`java -jar opintorekisteri.jar`\ jolloin ohjelma käynnistyy.  

### Suoritettavan jarin generointi  
Mene komentorivillä projektihakemistoon `Opintorekisteri` ja suorita komento\`mvn package`.  
Ajettava jar-tiedosto tulee hakemistoon *target* ja on nimeltään *Opintorekisteri-1.0-SNAPSHOT.jar*.  
Ohjelman voi ajaa komennolla\`java -jar Opintorekisteri-1.0-SNAPSHOT.jar`\  

### Testaus
Testit voidaan suorittaa komennolla  
`mvn test`.  

Testikattavuusraportti voidaan luoda komennolla\`mvn jacoco:report`\ 
Raportti on luettavissa siirtymällä hakemistossa paikkaan `target/site/jacoco/index.html` ja avaamalla index.html esimerkiksi selaimessa.

### Checkstyle
Tiedostoon [checkstyle.xml](Opintorekisteri/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla\`mvn jxr:jxr checkstyle:checkstyle`\  
Raportti voidaan avata esimerkiksi selaimessa ja raportti löytyy paikasta `target/site/checkstyle.html`.

### JavaDoc  
JavaDoc tehdään komennolla\`mvn javadoc:javadoc`\ JavaDocia voi tarkastella esimerkiksi selaimella ja se löytyy paikasta *target/site/apidocs/index.html*.
