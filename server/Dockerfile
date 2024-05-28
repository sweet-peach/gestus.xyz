FROM gradle:7.5.1-jdk17

WORKDIR /app

COPY build.gradle /app/
COPY settings.gradle /app/
COPY src /app/src

EXPOSE 8080

ENTRYPOINT ["gradle", "bootRun", "--no-daemon"]