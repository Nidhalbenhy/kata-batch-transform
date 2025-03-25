    **Kata Batch Transform**

    Ce projet est une application Spring Boot utilisée pour effectuer :
        . Des traitements par lots sur des fichiers d'entrée et de sortie ==> transformer plusieurs nombres  entre 0 et 100 
            en une chaîne encaractères selon des règles dans le fichier PDF.
        . Un endpoint permettant de transformer un nombre en une chaîne encaractères selon les memes règles.

    Prérequis
        Avant d'exécuter le projet, assurez-vous d'avoir installé les outils suivants :

        Java 17 : Assurez-vous d'avoir installé Java 17 sur votre machine.

        Maven 3 : Vous aurez besoin de Maven 3 pour construire l'application.

        Docker : Vous aurez besoin de Docker pour exécuter l'application dans un conteneur.


    Étapes d'exécution:

        1. Clonez ce projet:

            git clone <url_du_repertoire>.

        2. Construisez l'application:

            Exécutez la commande suivante pour générer le fichier JAR de l'application :
                .mvn clean package
            Cela générera un fichier JAR dans le répertoire target/(exemple: Kata-batch-transform-0.0.1-SNAPSHOT.jar).

        3. Construire l'image Docker

            Exécuter la commande suivante dans le répertoire de ton projet :
                 docker build -t kata-batch-transform .

        4. Exécuter l'application Dockerisée

            Pour exécuter l'application dans un conteneur Docker. Exécutez la commande suivante :
                docker run -v C:/input:/app/input -v C:/output:/app/output -p 8083:8083 kata-batch-transform
    
        5. Accéder à l'application
             l'application sera disponible sur le port 8083

            - Pour tester Endpoint il suffit de taper dans le navigateur: 
            http://localhost:8083/api/transform/7  ==> Résultat: 7 ==>QUIX
            http://localhost:8083/api/transform/33  ==> Résultat: 33 ==>FOOFOOFOO

            -Pour tester le fichier outpout du batch il suffit d'acceder au container 

                * docker ps: pour récuperer CONTAINER ID
                * docker exec -it <container_id_or_name> /bin/bash : pour accéder au container
                * cat /app/output/output.txt : pour vérifier les résultats dans le fichier outpout.txt
