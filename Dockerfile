FROM openjdk:11
VOLUME /tmp
ADD build/libs/GifCurrencyService-1.0.jar GifCurrencyService.jar
ENTRYPOINT ["java", "-jar", "/GifCurrencyService.jar" ]