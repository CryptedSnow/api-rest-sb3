# API REST application

```
api-rest-sb3
|__ src
    |__ main
    |    |__ java
    |        |__ com.sb3.apirestsb3
    |            |__ DAO
    |            |   |__ CharacterDAO.java
    |            |
    |            |__ Model
    |            |   |__ CharacterModel.java
    |            |
    |            |__ REST
    |            |   |__ CharacterREST.java
    |            |
    |            |__ Service
    |                |__ CharacterService.java
    |__ resources
         |__ application.properties
```

### Application Structure

* ```CharacterModel.java```: Work with informations of database.
* ```CharacterDAO.java```: Interface containing methods for data management.
* ```CharacterService.java```: implementing methods defined in the interface.
* ```CharacterREST.java```: HTTP CRUD (Create, Read, Update, Delete) methods to enable client-server communication.
* ```application.properties```: Application settings file.

## Database

You can use ```PostgreSQL``` or ```MySQL```, so follow the steps:

1 - Go to ```application.properties``` and insert the lines
```
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/database-name
spring.datasource.username=postgres
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver

# MySQL
spring.datasource.mysql.url=jdbc:mysql://localhost:3306/database-name
spring.datasource.mysql.username=root
spring.datasource.mysql.password=
spring.datasource.mysql.driver-class-name=com.mysql.cj.jdbc.Driver
```

2 - Create a database with the name that you desire (it needs be the same configured in ```application.properties```)

3 - Create a table called ```characters``` (the table name is configured in ```CharacterModel.java```), then insert this code snippet to speed up the process.

```
# PostgreSQL
CREATE TABLE characters (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    race VARCHAR(10) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    type_class VARCHAR(10) NOT NULL,
    age INT NOT NULL,
    height DECIMAL(3,2) NOT NULL,
    element VARCHAR(10) NOT NULL,
    origin VARCHAR(10) NOT NULL,
    weapon VARCHAR(10) NOT NULL,
    alignment VARCHAR(10) NOT NULL,
    alive BOOLEAN NOT NULL
);

# MySQL
CREATE TABLE characters (
    id PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    race VARCHAR(10) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    type_class VARCHAR(10) NOT NULL,
    age INT NOT NULL,
    height DECIMAL(3,2) NOT NULL,
    element VARCHAR(10) NOT NULL,
    origin VARCHAR(10) NOT NULL,
    weapon VARCHAR(10) NOT NULL,
    alignment VARCHAR(10) NOT NULL,
    alive BOOLEAN NOT NULL
);
```

## API REST Address 

Go for some API Client tool to perform the API testing, use [POSTMAN](https://www.postman.com/) for example.

**GET: localhost:8080/api/character**
```
// Response
[
  {
    "id": 1,
    "name": "Mardek Innanu El-Enkidu",
    "race": "Human",
    "gender": "Male",
    "type_class": "Royal Knight",
    "age": 21,
    "height": 1.78,
    "element": "Light",
    "origin": "Belfan",
    "weapon": "Sword",
    "alignment": "Lawful Good",
    "alive": true
  },
  {
    "id": 2,
    "name": "Elwyen Sirene Wu-Nympha",
    "race": "Human",
    "gender": "Female",
    "type_class": "Siren",
    "age": 17,
    "height": 1.7,
    "element": "Water",
    "origin": "Belfan",
    "weapon": "Harp",
    "alignment": "Chaotic Neutral",
    "alive": true
  }
]
```

**POST: localhost:8080/api/character**
```
// JSON Content
{
  "name": "Deugan Selmae Eh-Deredu",
  "race": "Human",
  "gender": "Male",
  "type_class": "War Hero",
  "age": 21,
  "height": 1.77,
  "element": "Earth",
  "origin": "Belfan",
  "weapon": "Greatsword",
  "alignment": "Neutral Good",
  "alive": true
}

// Response
{
  "id": 3,
  "name": "Deugan Selmae Eh-Deredu",
  "race": "Human",
  "gender": "Male",
  "type_class": "War Hero",
  "age": 21,
  "height": 1.77,
  "element": "Earth",
  "origin": "Belfan",
  "weapon": "Greatsword",
  "alignment": "Neutral Good",
  "alive": true
}
```

**GET: localhost:8080/api/character/id**
```
// Response
{
  "id": 3,
  "name": "Deugan Selmae Eh-Deredu",
  "race": "Human",
  "gender": "Male",
  "type_class": "War Hero",
  "age": 21,
  "height": 1.77,
  "element": "Earth",
  "origin": "Belfan",
  "weapon": "Greatsword",
  "alignment": "Neutral Good",
  "alive": true
}
```

**PUT: localhost:8080/api/character/id**
```
// JSON Content
{
  "name": "Deugan Selmae Eh-Deredu",
  "race": "Human",
  "gender": "Male",
  "type_class": "War Hero",
  "age": 21,
  "height": 1.75, // Changed here
  "element": "Earth",
  "origin": "Belfan",
  "weapon": "Greatsword",
  "alignment": "Neutral Good",
  "alive": true
}

// Response
{
  "id": 3,
  "name": "Deugan Selmae Eh-Deredu",
  "race": "Human",
  "gender": "Male",
  "type_class": "War Hero",
  "age": 21,
  "height": 1.75,
  "element": "Earth",
  "origin": "Belfan",
  "weapon": "Greatsword",
  "alignment": "Neutral Good",
  "alive": true
}
```

**DELETE: localhost:8080/api/character/id**
```
// Response
Deugan Selmae Eh-Deredu was deleted successfully.
```
### Reference
- **[Mardek](https://figverse.fandom.com/wiki/MARDEK_(Series))**
