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



---


Run Application on Debian8
Install Java8 
Install supervisor 
Place extras/builpr.conf under /etc/supervisor/conf.d 
Run "iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080" to redirect port 80 requests to port 8080 
Create user with "useradd builpr -r --shell \bin\false" 
Upload builpr-embedded.jar to /opt/builpr/ 
Run "chown -hR builpr: /opt/builpr" 
Start service with supervisorctl and "start builpr"