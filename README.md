DinDag
======

Den gamla Eclipse-baserade repon. Uppdateras <b>inte!</b>

<h3>Hantera Git-Projekten</h3>
Gå till https://github.com/di7chro/DinDag i webbläsaren och tryck på "Fork" knappen. Detta kopierar hela mitt projekt till er Github användare. Använd kommandotolken och gå till mappen där du vill ladda ner projektet i din dator. Skriv sedan nedanstående för att klona ner projektet från er github-sida till er dator.


<h3>Hämta hem senaste versionen till lokal dator:</h3>

git clone git@github.com:XXX/learnGitHub.git (XXX är ditt användarnamn på Git)

git status

git add - A

git commit -m "Jag har gjort blaha"

git status

git push

Gå till ert github-projekt och tryck på "Pull Request" och följ instruktionerna för att be orginal-projektet att pulla ert projektet in till orginalet.

Klart!

<h3>Fel</h3>
Om du kör en push och får tillbaka ett fel innehållande [rejected non-fast-forward] kan du lölsa det genom att köra:

git pull

git push
