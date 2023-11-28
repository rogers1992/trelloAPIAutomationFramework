FROM openjdk:11

WORKDIR /automation

COPY . .

RUN curl -L https://services.gradle.org/distributions/gradle-8.4-bin.zip -o gradle-8.4-bin.zip
RUN apt-get install -y unzip
RUN unzip gradle-8.4-bin.zip
ENV GRADLE_HOME=/automation/gradle-8.4
RUN echo $GRADLE_HOME
ENV PATH=$PATH:$GRADLE_HOME/bin
RUN echo $PATH

expose 7070
ENTRYPOINT ["gradle", "clean"]
CMD ["apiFeatures"]