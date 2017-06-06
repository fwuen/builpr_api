Um sauber mit dem Projekt zu arbeiten sind folgende Dinge nötig:

    Gradle Installation
        
        Installiere Gradle und setze die Umgebungsvariable
            GRADLE_HOME
        Füge %GRADLE_HOME%\bin zum Path hinzu
        
    Lombok Installation
        
        Damit die IDE das Autocomplete für von Lombok generierten Code anzeigen kann,
        ist ein Plugin notwendig. "Lombok Plugin"
        
Um Hot-Swap zu nutzen, sprich Änderungen am Code live zu sehen, wird ein kleiner Trick verwendet.
Über "gradle build --continuous" wird Gradle befehligt das Projekt bei jeder Code-Änderung neu zu bauen.
Wird gleichzeitig der Server über den Gradle-Task bootRun gestartet, startet der Server im Hot-Swap Modus 
und erkennt Änderungen sofort und startet den Server neu.

Um Zugriff auf Datenbank und Solr zu erhalten, muss OpenVPN verwendet werden.
Konfigurationsdatei aus dem server-Repo herunterladen und in OpenVPN einbinden.
OpenVPN GUI öffnen und verbinden.
Zugangsdaten eintragen. Die Verbindung zum Testserver wird hergestellt.