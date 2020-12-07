# Arkkitehtuurikuvaus

## Rakenne
Opintorekisteri-ohjelma noudattaa kolmitasoista kerrosarkkitehtuuria, joka on kuvattu alla:  
![Arkkitehtuuri](kuvat/Opintorekisteripakkauskaavio.jpg)  

Pakkaus opintorekisteri.ui sisältää graafisen JavaFX käyttöliittymän, opintorekisteri.domain taas varsinainen sovelluslogiikan ja opintorekisteri.dao hoitaa tiedon pysyväistalletuksesta vastaavan koodin. Suhteita on kuvattu katkoviivoilla, koska muun muassa käyttöliittymäpakkaus tarvitsee tietoa sovelluslogiikan puolelta.  

![Luokkakaavio](kuvat/Opintorekisteriluokkakaavio.jpg)  

Luokkakaavio Opintorekisteri-ohjelman luokista ja niiden välisistä suhteista. Ruudut joissa on lueteltu luokkien nimet kuvaavat ohjelman pakkauksia.

## Käyttöliittymä

## Sovelluslogiikka

## Tietojen pysyväistallennus

## Päätoiminnallisuudet
Seuraavaksi kuvataan ohjelman päätoiminnallisuuksia sekvenssikaavioina.  

#### Käyttäjän kirjautuminen (oletetaan että käyttäjä on olemassa)
 
Kun kirjautumisnäkymässä syötetään käyttäjätunnus joka on tietokannassa, tapahtuu suunnilleen seuraavanlainen tapahtumaketju:  

![Kirjautuminen](kuvat/Kirjautuminen.png)  
Eli kun painetaan "kirjaudu"-painiketta, UserService-luokka pyytää SqlUserDao-luokkaa tarkastamaan, että sellainen käyttäjätunnus on olemassa. Sen jälkeen luodaan tiedoista uusi User-olio, jotta saadaan loggedIn-muuttujaan oikea käyttäjä. Sen jälkeen näkymä vaihtuu loginScenestä mainSceneksi. Sen jälkeen suoritetaan refreshdata()-kutsu, missä CourseService hakee SqlCourseDao-luokan avustuksella käyttäjätunnukseen liitetyt aktiiviset ja epäaktiiviset kurssit kirjautuneelle käyttäjälle. Sen jälkeen ne näytetään käyttöliittymän komponenteissa käyttäjälle. Tämän kaiken välissä tapahtuu myös kaikkea pientä, kuten käyttöliittymäkomponenttien luontia ja tekstikenttien tyhjennyksiä. Lisäksi oletetaan, että kaikki tapahtuu oikein, ei tapahdu poikkeuksia tai muuta outoa.  

![Kurssin luonti](kuvat/onnistunut_kurssin_luonti.png)

