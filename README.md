# Дипломный проект профессии «Тестировщик»

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Документы
* [Как правильно работать над дипломом?](https://github.com/ArtemChurin/DIPLOMA/blob/master/Documentation/Howwork.md)
* [Описание приложения](https://github.com/ArtemChurin/DIPLOMA/blob/master/Documentation/Description.md)
* [План автоматизации тестирования](https://github.com/ArtemChurin/DIPLOMA/blob/master/Documentation/Plan2.md)
* [Отчет по итогам тестирования](https://github.com/ArtemChurin/DIPLOMA/blob/master/Documentation/Report.md)
* [Отчет по итогам автоматизированного тестирования](https://github.com/ArtemChurin/DIPLOMA/blob/master/Documentation/Itogo.md)

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

На локальном компьютере заранее должны быть установлены IntelliJ IDEA и Docker

## Подготовка среды перед тестированием:

**1.** Склонировать в локальный репозиторий [Дипломный проект](https://github.com/ArtemChurin/DIPLOMA.git) и открыть его в IDE IntelliJ IDEA

**2.** Запустить Docker Desktop

**3.** В терминале запустить контейнеры с помощью команды:

    docker-compose up -d

**4.** Запустить целевой веб-сервис командой:

     для mySQL: 
    java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

     для postgresgl:
     java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

# Процедура запуска авто-тестов:

**1.** Во втором терминале запустить тесты:

    для mySQL:
    ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"

    для postgresgl: 
    ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

**2.** Создать отчёт Allure и открыть в браузере:

    ./gradlew allureServe

# Действия после завершения авто-тестов:

**1.** Для завершения работы allureServe выполнить команду:

    Ctrl+C

**2.** Для остановки работы контейнеров выполнить команду:

    docker-compose down

### Дополнительные материалы:
[Руководство по оформлению Markdown файлов](https://gist.github.com/Jekins/2bf2d0638163f1294637#Emphasis)