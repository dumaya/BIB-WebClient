# BibWeb : Application Web pour les usagers de la bibliothéque municipale

##Contexte
Cette application BibBatch a été developpée par Alexis Dumay dans le cadre de son parcours developpeur d'application Java effectué avec OpenClassrooms en 2020.

##Contenu
Il s'agit d'une application Spring Boot qui consomme l'API de gestion des usagers et des ouvrages de la bibliothèque municipale afin de permettre aux usagers un suivi de leurs prêts en cours et une consultation des ouvrages disponibles dans la bibliothèque.

##Pré-requis
Version de Java : 1.8
JDK : jdk1.8.0_201
Maven 3.6
Avoir lancé BibApp, l'API associée : https://github.com/dumaya/BIB-APIweb

##Installation et déploiement
Installer et déployer l'application :
`mvn clean install spring-boot:run`

ou, en ligne de commande sur le war : `java -jar BibWeb-0.0.1-SNAPSHOT.war`

Sources disponibles sur : https://github.com/dumaya/BIB-WebClient
