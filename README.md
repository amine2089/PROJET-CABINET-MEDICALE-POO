# PROJET-CABINET-MEDICALE-POO
Ce projet est un travail pratique pour le module Programmation Orientée Objet 1, visant à développer un logiciel de gestion pour un Cabinet Médical. Le logiciel propose plusieurs fonctionnalités essentielles :

Gestion des fiches patients
Gestion des dossiers médicaux
Gestion des paiements
Gestion des consultations
Gestion des ordonnances
Gestion des rendez-vous
Ce logiciel est destiné à deux catégories d’utilisateurs :

Les médecins : Ils auront accès à la majorité des fonctionnalités, à l’exception de la gestion directe des rendez-vous et des paiements. Cependant, ils pourront consulter les bases de données associées à ces deux aspects.
Les secrétaires ou infirmières (dans un contexte algérien) : Elles auront accès à l’ensemble des fonctionnalités.
Pour l’authentification (login et inscription), ainsi que pour la gestion des données, nous utilisons une base de données SQLite. Afin d’assurer la sécurité, les mots de passe sont hashés. De plus, toutes les informations saisies doivent être uniques pour éviter les doublons.

Concernant l’interface graphique (GUI), nous avons choisi d’utiliser les fonctionnalités de base de JavaFX afin d’offrir une expérience utilisateur simple et intuitive.

