FROM java:8
ENV PROJECT_ARTIFACTID="file" PROJECT_VERSION="0.0.1-SNAPSHOT"
COPY target/$PROJECT_ARTIFACTID-$PROJECT_VERSION.jar /file.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "file.jar"]