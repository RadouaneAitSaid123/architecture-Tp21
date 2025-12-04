-- Script d'initialisation de la base de données

-- Extension pour UUID (si nécessaire)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Vérification de la création
SELECT version();
