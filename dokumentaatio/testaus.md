# Testausdokumentti  

Ohjelmaa on testattu yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti järjestelmätason testein.  

## Yksikkö- ja integraatiotestaus  
 
### sovelluslogiikka  
Integraatiotestit ovat testiluokat *CourseServiceCourseTest* ja *UserServiceUserTest*. Ne simuloivat pakkauksen *opintorekisteri.domain* sovelluslogiikkaa, jota *CourseService*- ja *UserService-luokat suorittavat. Tällä hetkellä ei ole käytössä mitään erillistä  testitietokantaa, vaan testit suoritetaan samaan tietokantaan mihin talletetaan oikeakin data.  
*User*- ja *Course*-luokille on suoritettu pari pientä yksikkötestiä. 
### DAO-luokat
DAO-luokkien testaus tapahtuu samalla kun testataan domain-luokkia, koska testit käyttävät samaa tietokantaa eikä testitietokantaa.  
### Testauskattavuus  
Käyttöliittymä on jätetty pois testikattavuudesta.  
## Järjestelmätestaus
Järjestelmätestausta on tehty manuaalisesti etätyöpöytä-sovelluksella Linux-ympäristössä.  
### Asennus ja konfigurointi  
Kuten edellä mainitsin, sovellusta on testattu etätyöpöytä-sovelluksessa ja se on asennettu käyttöohjeen mukaan. Käytetty vain Linux-ympäristö, missä sovellus on suunniteltu alunperin toimivaksi.  
Sovellusta on testattu tyhjällä tietokannalla ja tietokannalla jossa on jo dataa.
### Toiminnallisuudet
Kaikki toiminnallisuudet testattu. Tekstikentät eivät ole välilyöntien (" ") kanssa oikein toimivia.
## Sovellukseen jääneet laatuongelmat  
Ei ole testattu tilanteita, jossa tietokannan luonti ei onnistu.
