# Structure du projet :

## Backend : 
dans le dossier ```src```

contient :
- ```dao``` : 
- ```database``` : Contient la classe qui gère la base de donnée
- ```model``` : Contient les classe java qui modèlisent les tables de la base de données
- ```main.java``` : Contient les routes pour le backend (API)

### Frontend :
dans le dossier ```front```

contient :
- ```public/images``` : les images pour le front
- ```src/components``` : Contient les different composants pour React
- ```src/App.js``` : Contient la base du frontend

# Installation du projet :

Il faut d'abord copier le repo github. Puis il faut avoir installer :
- ```Maeven```
- ```Node.js```

Une fois ceci installer :

## Partie backend :

Se situer dans le dossier ```demo``` (Racine du projet).
- Compiler le ```.jar``` : ```mvn clean package```
- Lancer le serveur : ```java -jar target/demo-1.0-SNAPSHOT-jar-with-dependencies.jar```

## Partie frontend :

Se situer dans le dossier ```front```

- Lancer le serveur frontend : ```npm start```