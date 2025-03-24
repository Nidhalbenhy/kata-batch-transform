# Étape 1 : Utiliser une image de base Java
FROM openjdk:17-jdk-slim

# Étape 2 : Ajouter le fichier JAR de votre application dans le conteneur
COPY target/Kata-batch-transform-0.0.1-SNAPSHOT.jar /app/api.jar

RUN mkdir -p /app/input /app/output

# Étape 3 : Exposer le port 8083 pour accéder à l'application
EXPOSE 8083

# Étape 4 : Définir la commande pour démarrer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app/api.jar"]