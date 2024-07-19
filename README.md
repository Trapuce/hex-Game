# Projet de Jeux de Hex et Bandits Manchots

Ce dépôt contient les implémentations des jeux de Hex et de Bandits Manchots en Java, utilisant le modèle MVC et une interface graphique. Le jeu de Hex inclut un algorithme de Monte Carlo Tree Search (MCTS) pour fournir une solution automatisée.

## Table des Matières

1. [Introduction](#introduction)
2. [Installation](#installation)
3. [Utilisation](#utilisation)
4. [Algorithme de Monte Carlo Tree Search](#algorithme-de-monte-carlo-tree-search)
5. [Contributeurs](#contributeurs)

## Introduction

Le jeu de Hex est un jeu de stratégie abstrait pour deux joueurs, joué sur une grille hexagonale. Le but est de connecter les côtés opposés du plateau avec des pièces de sa couleur. Le jeu des Bandits Manchots est une simulation de machines à sous.

## Installation

Pour compiler et exécuter les jeux, suivez les étapes ci-dessous :

1. Cloner le dépôt :
    ```bash
    git clone https://github.com/votre-utilisateur/votre-repo.git
    cd votre-repo
    ```

2. Compiler le projet :
    ```bash
    javac -d "build" jeu/*.java
    ```

3. Exécuter le projet :
    ```bash
    java -cp "build" jeu.Main
    ```

## Utilisation

1. Lancez l'application.
2. Choisissez le jeu auquel vous voulez jouer : Hex ou Bandits Manchots.
3. Suivez les instructions à l'écran pour jouer.

## Algorithme de Monte Carlo Tree Search

Le jeu de Hex utilise l'algorithme de Monte Carlo Tree Search (MCTS) pour simuler et trouver les meilleurs coups possibles. Voici une brève explication de l'algorithme :

1. **Sélection** : Choisir un nœud à explorer basé sur une politique d'arbre.
2. **Expansion** : Ajouter un nouveau nœud à l'arbre.
3. **Simulation** : Simuler le jeu jusqu'à la fin depuis le nouveau nœud.
4. **Rétropropagation** : Mettre à jour les valeurs des nœuds selon le résultat de la simulation.

## Contributeurs

- Amadou Bah
- Celestin Mireux
- Daouda Traore
- Junior Missoup

---

Merci de votre intérêt pour notre projet ! Pour plus de détails, consultez la documentation complète incluse dans le dépôt.
