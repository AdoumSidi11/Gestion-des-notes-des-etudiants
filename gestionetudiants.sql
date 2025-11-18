-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 18 nov. 2025 à 19:05
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestionetudiants`
--

-- --------------------------------------------------------

--
-- Structure de la table `etudiants`
--

CREATE TABLE `etudiants` (
  `id_etudiant` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `moyenne` double DEFAULT 0,
  `avis` varchar(50) DEFAULT 'En attente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `etudiants`
--

INSERT INTO `etudiants` (`id_etudiant`, `nom`, `moyenne`, `avis`) VALUES
(1, 'Thomas Dupont', 16.38888888888889, 'Admis'),
(2, 'Thomas Dupont', 16.38888888888889, 'Admis'),
(3, 'Thomas Dupont', 16.38888888888889, 'Admis');

-- --------------------------------------------------------

--
-- Structure de la table `notations`
--

CREATE TABLE `notations` (
  `id_notation` int(11) NOT NULL,
  `note` double NOT NULL,
  `coef` int(11) NOT NULL,
  `id_etudiant_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `notations`
--

INSERT INTO `notations` (`id_notation`, `note`, `coef`, `id_etudiant_fk`) VALUES
(1, 14.5, 3, 1),
(2, 18, 4, 1),
(3, 16, 2, 1),
(4, 14.5, 3, 2),
(5, 18, 4, 2),
(6, 16, 2, 2),
(7, 14.5, 3, 3),
(8, 18, 4, 3),
(9, 16, 2, 3);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `etudiants`
--
ALTER TABLE `etudiants`
  ADD PRIMARY KEY (`id_etudiant`);

--
-- Index pour la table `notations`
--
ALTER TABLE `notations`
  ADD PRIMARY KEY (`id_notation`),
  ADD KEY `id_etudiant_fk` (`id_etudiant_fk`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `etudiants`
--
ALTER TABLE `etudiants`
  MODIFY `id_etudiant` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `notations`
--
ALTER TABLE `notations`
  MODIFY `id_notation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `notations`
--
ALTER TABLE `notations`
  ADD CONSTRAINT `notations_ibfk_1` FOREIGN KEY (`id_etudiant_fk`) REFERENCES `etudiants` (`id_etudiant`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
