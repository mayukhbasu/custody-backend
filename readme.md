set -a; source .env; set +a; mvn spring-boot:run -Dspring-boot.run.profiles=prod
Run this command to test secrets in google cloud