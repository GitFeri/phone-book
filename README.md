# Vizsgaremek - telefonkönyv (phone-book)

## Leírás

Egy kisvállalkozásnál merült fel az igény, hogy olyan telefonkönyet hozzunk létre,
amely egyidőben minden kolléga számára elérhető számítógépről. A feladat egy
szerveren futó alkalmazással valósítható meg leginkább. Ezért esett erre a
témára a választás a 'vizsgaremek' feladat megoldásához. 

Az alkalmazás személyek rögzítését, módosítását, törlését és megjelenítését
teszi lehetővé. A személyek kapcsolódó telefonszám nélkül is létrehozhatók.
Telefonszám mindig egy adott személyhez hozható létre. Egy személyhez természetesen
több telefonszám is megadható. A telefonszámhoz be kell állítani egy tulajdonságot
(MOBILE, HOME, WORK) illetve egy hozzáférési jogousltságot. (SECRET, PUBLIC)
A jogosultság alapján lehet eldönteni, hogy a szám kiadható-e hamadik fél
számára. Természetesen a telefonszámok illetve azok tulajdonsága is módosítható,
vagy a teljes telefonszám törölhető.

---

## Megvalósítás

Az alkalmazás Java nyelven, Spring Boot használatával történt,
három rétegű (controller, service és repository) webes alkalmazás implementálásval.
Az alkalmazás RestFul webszolgáltatást valósít meg. Az adatbázis MariaDB, melyben az
adatok tárolására SQL szabványnak megfelelően történik.
Az alkalmazáshoz Swagger UI felület is készült. Ezzel ki lehet próbálni
a Http kérések működését.

## Használata

Személyekkel történő műveletek:

GET     /api/phonebook/people/{id}  Egy személy lekérdezése
PUT     /api/phonebook/people/{id}  Egy személy módosítása
DELETE  /api/phonebook/people/{id}  Egy személy törlése
GET     /api/phonebook/people       Az összes személy lekérdezése
POST    /api/phonebook/people       Egy személy felvétele
DELETE  /api/phonebook/people       Az összes személy törlése

Telefonszámokkal történő műveletek:

GET     /api/phonebook/phonenumber/{id}  Telefonszám lekérdezése
PUT     /api/phonebook/phonenumber/{id}  Telefonszám módosítása
DELETE  /api/phonebook/phonenumber/{id}  Telefonszám törlése
POST    /api/phonebook/phonenumber       Telefonszám létrehozása

