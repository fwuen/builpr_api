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

Für Speedment: 
1. Tool über Maven starten (speedment-generator/speedment/speedment:tool)
2. Mit der Datebank verbinden
3. Auf den obersten node in der Hierearchie gehen (builpr) und dort eingeben: 
    project name: builpr
    company name: com.builpr
    package name: com.builpr.databse
    Rest lassen
4. auf den node builpr.com gehen:
    Java alias: db
    Username: builpr
    Rest lassen
5. auf generate drücken 