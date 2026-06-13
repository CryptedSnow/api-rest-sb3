# Application (map)

```
api-rest-sb3
|__ src
    |__ main
    |    |__ java
    |        |__ com.sb3.apirestsb3
    |            |__ Api
    |            |   |__ Controller
    |            |       |__ CharacterController.java
    |            |__ Entity
    |            |   |__ Character.java
    |            |__ Repository
    |            |   |__ CharacterRepository.java
    |            |__ Service
    |                |__ CharacterService.java
    |__ resources
        |__ application.properties
```

It is necessary install ```JDK```, the minimum version to perfomate Spring Boot 3 is **17** (I usually use **JDK 21** version). Don't forget about to use [IntelliJ IDEA](https://www.jetbrains.com/idea/) to facilitate your experience.

### Application structure pattern

See more about **[Three-Tier Architecture](https://www.ibm.com/topics/three-tier-architecture)**.

1 - Presentation tier:
* Nothing interface files in this application.

2 - Application tier:
* ```CharacterController.java```: HTTP requests.
* ```CharacterService.java```: Logic of application methods.

3 - Data tier:
* ```CharacterRepository.java```: Access the database.
* ```Character.java```: Represent the database informations.

4 - Others files:
* ```application.properties```: Application settings file.

## Database

You can use ```PostgreSQL``` or ```MySQL``` database, so follow the steps:

1 - Go to ```application.properties``` and insert the lines (Check the database name, user and password of your preference)

```
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/database_name
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/database_name
spring.datasource.username=postgres
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver
```

If you want use ```Docker``` to insert the lines:

```
# MySQL
spring.datasource.url=jdbc:mysql://mysql:3306/api-rest-sb3
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# PostgreSQL
spring.datasource.url=jdbc:postgresql://pgsql:5432/api-rest-sb3
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Panels

- phpMyAdmin: http://localhost:8081
    - User: ```user```
    - Password: ```password```
- pgAdmin: http://localhost:8082
    - User: ```admin@admin.com```
    - Password: ```admin```

To create a server to pgAdmin:
- Host name/address: ```pgsql```
- Port: ```5432```
- Maintenance database:	```postgres```
- Username:	```user```
- Password:	```password```

2 - Create a table called ```characters``` (the table name is configured in ```CharacterEntity.java```), then insert this code snippet to speed up the process.

```
-- MySQL
CREATE TABLE characters (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    race VARCHAR(15) NOT NULL,
    gender VARCHAR(15) NOT NULL,
    type_class VARCHAR(15) NOT NULL,
    age INT NOT NULL,
    height DECIMAL(3,2) NOT NULL,
    element VARCHAR(15) NOT NULL,
    origin VARCHAR(15) NOT NULL,
    weapon VARCHAR(15) NOT NULL,
    alignment VARCHAR(15) NOT NULL,
    alive BOOLEAN NOT NULL
);

-- PostgreSQL
CREATE TABLE characters (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    race VARCHAR(15) NOT NULL,
    gender VARCHAR(15) NOT NULL,
    type_class VARCHAR(15) NOT NULL,
    age INT NOT NULL,
    height DECIMAL(3,2) NOT NULL,
    element VARCHAR(15) NOT NULL,
    origin VARCHAR(15) NOT NULL,
    weapon VARCHAR(15) NOT NULL,
    alignment VARCHAR(15) NOT NULL,
    alive BOOLEAN NOT NULL
);

-- Insert lines
INSERT INTO characters (name, race, gender, type_class, age, height, element, origin, weapon, alignment, alive) 
VALUES
('Mardek Innanu El-Enkidu', 'Human', 'Male', 'Recruit', 18, 1.78, 'Light', 'Goznor', 'Sword', 'Lawful Good', true),
('Deugan Selmae Eh-Deredu', 'Human', 'Male', 'Recruit', 18, 1.77, 'Earth', 'Goznor', 'Greatsword', 'Neutral Good', true),
('Emela Andra Wu-Jardu', 'Human', 'Female', 'Elemance', 18, 1.75, 'Water', 'Water Temple', 'Rod', 'Lawful Neutral', true),
('Vehrn Juonour El-Ganobyi', 'Human', 'Male', 'Paladin', 25, 1.77, 'Light', 'Belfan', 'Sword', 'Lawful Good', true);
```

## API REST endpoints

To endpoint tests on **Swagger UI**:
```
localhost:8080/swagger-ui/index.html
```

To endpoint tests on your machine you need use some API platform to perfomate the endpoints, you can use [POSTMAN](https://www.postman.com/) for example.

**GET: localhost:8080/api/characters**
- Response: 200 OK
```
{
    "content": [
        {
            "id": 1,
            "name": "Mardek Innanu El-Enkidu",
            "race": "Human",
            "gender": "Male",
            "typeClass": "Recruit",
            "age": 18,
            "height": 1.78,
            "element": "Light",
            "origin": "Goznor",
            "weapon": "Sword",
            "alignment": "Lawful Good",
            "alive": true
        },
        {
            "id": 2,
            "name": "Deugan Selmae Eh-Deredu",
            "race": "Human",
            "gender": "Male",
            "typeClass": "Recruit",
            "age": 18,
            "height": 1.77,
            "element": "Earth",
            "origin": "Goznor",
            "weapon": "Greatsword",
            "alignment": "Neutral Good",
            "alive": true
        },
        {
            "id": 3,
            "name": "Emela Andra Wu-Jardu",
            "race": "Human",
            "gender": "Female",
            "typeClass": "Elemance",
            "age": 18,
            "height": 1.75,
            "element": "Water",
            "origin": "Water Temple",
            "weapon": "Rod",
            "alignment": "Lawful Neutral",
            "alive": true
        },
        {
            "id": 4,
            "name": "Vehrn Juonour El-Ganobyi",
            "race": "Human",
            "gender": "Male",
            "typeClass": "Paladin",
            "age": 25,
            "height": 1.77,
            "element": "Light",
            "origin": "Belfan",
            "weapon": "Sword",
            "alignment": "Lawful Good",
            "alive": true
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "sorted": true,
            "empty": false,
            "unsorted": false
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 4,
    "size": 10,
    "number": 0,
    "sort": {
        "sorted": true,
        "empty": false,
        "unsorted": false
    },
    "first": true,
    "numberOfElements": 4,
    "empty": false
}
```

**POST: localhost:8080/api/character**
**JSON body**
```
{
  "name": "Elwyen Sirene Wu-Nympha",
  "race": "Human",
  "gender": "Female",
  "typeClass": "Youngling",
  "age": 14,
  "height": 1.61,
  "element": "Water",
  "origin": "Canonia",
  "weapon": "Harp",
  "alignment": "Chaotic Neutral",
  "alive": true
}
```

- Response: 201 Created
```
{
  "id": 5,
  "name": "Elwyen Sirene Wu-Nympha",
  "race": "Human",
  "gender": "Female",
  "typeClass": "Youngling",
  "age": 14,
  "height": 1.61,
  "element": "Water",
  "origin": "Canonia",
  "weapon": "Harp",
  "alignment": "Chaotic Neutral",
  "alive": true
}
```

**GET: localhost:8080/api/character/{id}**
- You need change **{id}** for **5**.
- Response: 200 OK
```
{
  "id": 5,
  "name": "Elwyen Sirene Wu-Nympha",
  "race": "Human",
  "gender": "Female",
  "typeClass": "Youngling",
  "age": 14,
  "height": 1.61,
  "element": "Water",
  "origin": "Canonia",
  "weapon": "Harp",
  "alignment": "Chaotic Neutral",
  "alive": true
}
```

**GET: localhost:8080/api/search-character?name=**
- You need change **name=** for **name=Sirene**
- Response: 200 OK
```
{
    "content": [
        {
            "id": 5,
            "name": "Elwyen Sirene Wu-Nympha",
            "race": "Human",
            "gender": "Female",
            "typeClass": "Youngling",
            "age": 14,
            "height": 1.61,
            "element": "Water",
            "origin": "Canonia",
            "weapon": "Harp",
            "alignment": "Chaotic Neutral",
            "alive": true
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "sorted": true,
            "empty": false,
            "unsorted": false
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "size": 10,
    "number": 0,
    "sort": {
        "sorted": true,
        "empty": false,
        "unsorted": false
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
```

**PUT: localhost:8080/api/character/{id}**
- You need change **{id}** for **5**.

**JSON body**
```
{
  "name": "Elwyen Sirene Wu-Nympha",
  "race": "Human",
  "gender": "Female",
  "typeClass": "Youngling",
  "age": 14,
  "height": 1.65, // I changed 1.61 m to 1.65 m
  "element": "Water",
  "origin": "Canonia",
  "weapon": "Harp",
  "alignment": "Chaotic Neutral",
  "alive": true
}
```

- Response: 202 Accepted
```
{
  "id": 5,
  "name": "Elwyen Sirene Wu-Nympha",
  "race": "Human",
  "gender": "Female",
  "typeClass": "Youngling",
  "age": 14,
  "height": 1.65,
  "element": "Water",
  "origin": "Canonia",
  "weapon": "Harp",
  "alignment": "Chaotic Neutral",
  "alive": true
}
```

**PATCH: localhost:8080/api/character/{id}**
- You need change **{id}** for **5**.
- I changed 1.61 m to 1.65 m

**JSON body**
```
{
  "height": 1.65
}
```
- Response: 202 Accepted
```
{
  "id": 5,
  "name": "Elwyen Sirene Wu-Nympha",
  "race": "Human",
  "gender": "Female",
  "typeClass": "Youngling",
  "age": 14,
  "height": 1.65,
  "element": "Water",
  "origin": "Canonia",
  "weapon": "Harp",
  "alignment": "Chaotic Neutral",
  "alive": true
}
```

**DELETE: localhost:8080/api/character/{id}**
- You need change **{id}** for **5**. 
- Response: 200 OK
```
Elwyen Sirene Wu-Nympha was deleted.
```

### Reference
- **[Mardek (Series)](https://figverse.fandom.com/wiki/MARDEK_(Series))**