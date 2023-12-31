# Simple Сinema Service

## Описание проекта:

#### - Главная страница. Общую информация о ресурсе;

#### - Расписание. Сеансы и связанные с ними фильмы. При выборе конкретного сеанса пользователь переходит на страницу покупки билета;

#### - Кинотека. Список фильмов;

#### - Страница покупки билета. Информацию о сеансе и фильм. Также 2 выпадающих списка - один для указания ряда, другой для указания места, и кнопки "Купить", "Отменить";

#### - Страница с результатом успешной покупки билета;

#### - Страница с результатом неудачной покупки билета (билет уже купили);

#### - Страница регистрации;

#### - Страница вход;

## Стек технологий

#### - Java 17, PostgreSQL 15.2, Liquibase, Maven, Mockito, Spring Boot, Thymeleaf, Bootstrap, Sql2o

## Требования к окружению

#### - Java 17, Maven 4.0, PostgreSQL 15

## Запуск проекта

#### - Создание бд в СУБД

```sql
create
database cinema;
```

#### - Создание таблиц в бд из файла /scripts/001_ddl_create_initial_schema.sql

```sql
create table files
(
    id   serial primary key,
    name varchar not null,
    path varchar not null unique
);

create table genres
(
    id   serial primary key,
    name varchar unique not null
);

create table films
(
    id                  serial primary key,
    name                varchar                    not null,
    description         varchar                    not null,
    "year"              int                        not null,
    genre_id            int references genres (id) not null,
    minimal_age         int                        not null,
    duration_in_minutes int                        not null,
    file_id             int references files (id)  not null
);

create table halls
(
    id          serial primary key,
    name        varchar not null,
    row_count   int     not null,
    place_count int     not null,
    description varchar not null
);

create table film_sessions
(
    id         serial primary key,
    film_id    int references films (id) not null,
    halls_id   int references halls (id) not null,
    start_time timestamp                 not null,
    end_time   timestamp                 not null,
    price      int                       not null
);

create table users
(
    id        serial primary key,
    full_name varchar        not null,
    email     varchar unique not null,
    password  varchar        not null
);

create table tickets
(
    id           serial primary key,
    session_id   int references film_sessions (id) not null,
    row_number   int                               not null,
    place_number int                               not null,
    user_id      int                               not null,
    unique (session_id, row_number, place_number)
);
```

#### - Заполнение таблиц в бд из файлов
1) /scripts/002_dml_insert_genres.sql
2) /scripts/002_dml_insert_files.sql
3) /scripts/002_dml_insert_films.sql
4) /scripts/002_dml_insert_halls.sql
5) /scripts/002_dml_insert_film_sessions.sql


## Взаимодействие с приложением
1) Главная страница
![img.png](img.png)

2. Кинотека
![img_1.png](img_1.png)

3. Страница фильма
![img_2.png](img_2.png)

4. Расписание
![img_3.png](img_3.png)

5. Страница сеанса
![img_4.png](img_4.png)

6. Страница входа 
![img_5.png](img_5.png)

7. Страница при успешной покупке
![img_6.png](img_6.png)

8. Страница при неуспешной покупке
![img_7.png](img_7.png)

## Контакты
#### - @urmoonfriend
