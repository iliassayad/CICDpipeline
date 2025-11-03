# ===============================
# 🏗️ Étape 1 : Build de l’application
# ===============================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Définir le répertoire de travail
WORKDIR /app

# Copier le pom.xml et télécharger les dépendances (meilleur cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source et compiler
COPY src ./src
RUN mvn clean package -DskipTests

# ===============================
# 🚀 Étape 2 : Image finale (exécution)
# ===============================
FROM eclipse-temurin:17-jre-alpine

# Répertoire de travail
WORKDIR /app

# Copier le jar depuis l’étape de build
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port de l’application Spring Boot
EXPOSE 8080

# Commande de démarrage )
ENTRYPOINT ["java", "-jar", "app.jar"]
