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

![Käyttäjän luonti](kuvat/kayttajan_luonti.png)
Kaavio joka kuvaa uuden käyttäjän luomista onnistuneesti. Kun käyttäjä painaa "luo uusi käyttäjä"-painiketta login-scenessä, vaihtuu scene  newUser-sceneen. Tämän jälkeen kun uuden käyttäjän tiedot on syötetty ja painetaan "luo uusi käyttäjä"-painiketta, käyttöliittymä kutsuu UserService-luokan createUser()-metodia. UserService-luokka taas kutsuu SqlUserDao-luokan metodeja. Ensiksi katsotaan, että onhan tietokanta olemassa, jonka jälkeen katsotaan että eihän käyttäjätunnus ole varattu jota yritetään luoda. Jos edellä mainitut askeleet ovat OK, luodaan uusi User-olio ja sitten lisätään se SqlUserDao-luokan välityksellä tietokantaan. Sitten palataan takaisin käyttöliittymään jossa scene vaihtuu jälleen newUser-scenestä login-sceneen.

![Kirjautuminen](kuvat/Kirjautuminen.png)  
Kaavio joka kuvaan käyttäjän kirjautumista sovellukseen onnistuneesti. Kun painetaan "kirjaudu"-painiketta, UserService-luokka pyytää SqlUserDao-luokkaa tarkastamaan, että tietokanta on olemassa ja jos on niin sitten katsotaan onko käyttäjätunnus on olemassa. Sen jälkeen luodaan tiedoista uusi User-olio, jotta saadaan loggedIn-muuttujaan oikea käyttäjä. Sen jälkeen näkymä vaihtuu loginScenestä mainSceneksi. Sen jälkeen suoritetaan refreshdata()-kutsu, missä CourseService hakee SqlCourseDao-luokan avustuksella käyttäjätunnukseen liitetyt aktiiviset ja epäaktiiviset kurssit kirjautuneelle käyttäjälle. Sen jälkeen ne näytetään käyttöliittymän komponenteissa käyttäjälle. Tämän kaiken välissä tapahtuu myös kaikkea pientä, kuten käyttöliittymäkomponenttien luontia ja tekstikenttien tyhjennyksiä. Lisäksi oletetaan, että kaikki tapahtuu oikein, ei tapahdu poikkeuksia tai muuta outoa.  

![Kurssin luonti](kuvat/onnistunut_kurssin_luonti.png)  
Kaavio joka kuvaa uuden kurssin luomista onnistuneesti sovellukseen. Kun painetaan "lisää kurssi"-painiketta tapahtuu suunnilleen seuraavanlainen tapahtumaketju. Käyttöliittymäluokka kutsuu CourseService-luokan createCourse-metodia, jossa on mukana lisättävän kurssin tiedot ja tieto mille käyttäjälle kurssi kuuluu. Sen jälkeen CourseService pyytää SqlCourseDao-luokkaa tarkastamaan, että tietokanta on olemassa ja sen jälkeen suoritetaan CourseService-luokassa kurssin validointi, eli tutkitaan syötteet ja katsotaan, että samalla käyttäjällä ei ole samannimistä aktiivista kurssia. Näiden jälkeen luodaan uus kurssi ja lisätään se tietokantaan ja jälleen päivitetään kirjautuneen käyttäjän data ja näytetään se käyttöliittymässä käyttäjälle.  

![Kurssin epäaktivointi](kuvat/epaaktivointi.png)  
Kaavio joka kuvaa tapahtumaketjua kun muutetaan aktiivisen kurssin status epäaktiiviseksi. Kun painetaan "siirrä kurssi epäaktiiviseksi"-painiketta, tapahtuu suunnilleen seuraavanlainen tapahtumaketju: Haetaan käyttöliittymästä kurssi joka on sillä hetkellä valittuna. Se kurssi välitetään CourseService-luokalle ja sen markCourseAsDone-metodille. Sen jälkeen kutsutaan kurssi-olion omaa setUnactive()-metodia. Tämän jäkeen kutsutaan SqlCourseDao-luokan changeActiveToUnactive joka hoitaa tietokannan puolelta samat operaatiot, eli muuttaa statuksen aktiivisesta epäaktiiviseksi. Tämän jälkeen palataan takaisin käyttöliittymään, jossa suoritetaan muutamia operaatioita kuten poistetaan toisesta käyttöliittymäkomponentista valittu kurssi ja lisätään se toiseen.  

![Kurssin poisto](kuvat/poisto.png)
Kaavio joka kuvaa kun poistetaan sovelluksesta kurssi onnistuneesti. Kun painetaan "poista kurssi"-painiketta, käyttöliittymä hakee valitun Course-olion, välittää tiedon siitä CourseServicen deleteCourse-metodille joka taas välittää tiedon SqlCourseDao-luokan deleteCourse-metodille. Sitten palataan takaisin käyttöliittymään, jossa päivitetään käyttöliittymän näkymää käyttäjälle.  
