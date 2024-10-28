docker build -t system/s-pay-mall-mvc-app:1.0-SNAPSHOT -f ./Dockerfile .
docker-compose -f docker-compose-app.yml up -d