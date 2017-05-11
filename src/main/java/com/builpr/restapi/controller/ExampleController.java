package com.builpr.restapi.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExampleController {

    /* Dieses RequestMapping liefert den String den es http://localhost:8080/stringBack?back=<hier> bekommt als Antwort zurück */
    @CrossOrigin(origins = "http://localhost:8081") /* Das erlaubt den Zugriff auf das Mapping von <origins> */
    @RequestMapping(value = "/stringBack", method = RequestMethod.GET) /* Hierdurch wird die Methode darunter ausgeführt, wenn der Url <value> mit der Methode <method> aufgerufen wird */
    public String stringBack( /* Hier bekomme ich nur einen String als ResponseBody, gibst du eine Klasse als Rückgabetyp an, wird das zurückgelieferte Objekt automatisch in json gewandelt */
            @RequestParam( /* Gibt dem Mapping einen Get-Parameter */
                    value = "back", /* Der Key des Get-Parameters */
                    required = true /* Der Key muss eine Value haben, ansonsten Exception */
            ) String backValue /* Hier steckt die Value des Get-Parameters drin */
    ) {
        return backValue;
    }

    /* Dieses RequestMapping liefert das Json dass es vom Post an http://localhost:8080 bekommt zurück
    * Ich schicke also folgendes per Post an http://localhost:8080/jsonBack
    * {
    *   "payload": "<Irgendein Text>"
    * }
    * und bekomme
    * {
    *   "payload": "<Irgendein Text>"
    * }
    * zurück. Das Konvertieren vom Json zum JsonExample-Objekt übernimmt Spring komplett.
    *   */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/jsonBack", method = RequestMethod.POST)
    public JsonExample jsonBack(
            @RequestBody JsonExample backValue
    ) {
        return backValue;
    }



    /* Auf diese Klasse wird JSON gemapped. Sollte natürlich als eigene Klasse und nicht als innere Klasse geführt werden. Für das Beispiel als innere Klasse */
    private class JsonExample {

        @Getter
        private String payload;

    }


}
