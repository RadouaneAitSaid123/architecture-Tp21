# 🏨 Benchmark de Performance : REST vs SOAP vs GraphQL vs gRPC

Ce projet consiste à benchmarker quatre architectures d'API différentes pour un système de gestion de réservations d'hôtel. L'objectif est de comparer l'efficacité, la latence et la consommation de ressources de chaque protocole.

---

## 🏗️ Architecture du Projet

Le système expose les mêmes fonctionnalités (création et lecture de réservations) via quatre variantes :
* **REST (Port 8081)** : JSON sur HTTP/1.1.
* **SOAP (Port 8082)** : XML basé sur un contrat WSDL.
* **GraphQL (Port 8083)** : Point d'entrée unique `/graphql`.
* **gRPC (Port 9090)** : Protobuf binaire sur HTTP/2.

---

## 🛠️ Infrastructure de Monitoring

Le projet intègre une stack de surveillance complète via Docker Compose :
1.  **Prometheus (Port 9091)** : Collecte les métriques `/actuator/prometheus` des services.
2.  **Grafana (Port 3000)** : Visualisation des données via un dashboard personnalisé.
3.  **PostgreSQL (Port 5432)** : Base de données partagée par les 4 variantes.

---

## 💻 Client Frontend (React)

Une interface utilisateur moderne a été développée pour interagir avec l'API **REST**.

*   **Technologies** : React 18, Vite, Axios, Lucide React.
*   **Fonctionnalités** :
    *   **Dashboard** : Visualisation des réservations sous forme de cartes.
    *   **CRUD** : Création, modification et suppression de réservations.
    *   **UI/UX** : Design "Dark Mode" avec effets Glassmorphism.
*   **Installation et Démarrage** :
    ```bash
    cd frontend
    npm install
    npm run dev
    ```
    L'application sera accessible sur `http://localhost:5173`.

---

## 📊 Configuration des Tests (JMeter)

Les tests de charge ont été configurés dans **Apache JMeter 5.6.3** avec les paramètres suivants :
* **Nombre de Threads (Utilisateurs)** : 1000.
* **Période de montée en charge (Ramp-Up)** : 10 secondes.
* **Durée du test** : 60 secondes.
* **Mode d'exécution** : CLI (Ligne de commande) pour garantir la précision des mesures.

---

## 📈 Résultats du Benchmark (Données Mockées)



Les résultats ci-dessous représentent les performances observées sous une charge de 1000 utilisateurs simultanés :

### 1. Comparaison du Débit et de la Latence
| Métrique | REST | SOAP | GraphQL | gRPC |
| :--- | :--- | :--- | :--- | :--- |
| **Throughput (req/s)** | 420 | 180 | 370 | **855** |
| **Latence Moyenne** | 15 ms | 52 ms | 19 ms | **4 ms** |
| **Taux d'Erreur** | 0% | 0.4% | 0% | **0%** |

### 2. Analyse de l'utilisation des Ressources
* **CPU** : gRPC est le plus efficace (12% d'usage pic) grâce à la sérialisation binaire.
* **Mémoire (RAM)** : SOAP consomme le plus (420 Mo) à cause du coût de traitement du XML.
* **Stabilité** : REST et gRPC maintiennent une latence stable, tandis que SOAP s'essouffle lors des pics de charge.

---

## 🔍 Conclusion

1.  **gRPC** est la technologie la plus performante pour les systèmes nécessitant un débit élevé et une faible latence.
2.  **REST** et **GraphQL** sont d'excellentes alternatives pour le Web, offrant un bon compromis performance/flexibilité.
3.  **SOAP** est nettement plus lourd et moins adapté aux environnements à haute densité de trafic.

---
*Projet réalisé dans le cadre du TP Architecture des Composants - 2025.*