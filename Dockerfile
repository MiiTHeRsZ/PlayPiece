# Usa a imagem oficial do OpenJDK 21 como base
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR gerado pelo Spring Boot para o contêiner
COPY target/*.jar app.jar

# Expõe a porta da aplicação (se estiver configurada para rodar na 8080)
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]