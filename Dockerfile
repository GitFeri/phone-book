FROM adoptopenjdk:16-jre-hotspot as builder
WORKDIR application
COPY target/phone-book-1.0-SNAPSHOT.jar phone-book.jar
RUN java -Djarmode=layertools -jar phone-book.jar extract

FROM adoptopenjdk:16-jre-hotspot
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
