


## Inhaltsverzeichnis:

- [Game API](#game-api)
- [Highscore](#highscore)
- [Category and Questions API](#cat-and-que-api)
___

*Mit dem Program [Postman](https://www.postman.com), kann man Requests and die Api schicken und die Responses im JSON format auslesen*
___
<h1 id="game-api">Game API</h1>

## Get possible Catergory names (List of Strings)

GetRequest : 
```
localhost:8085/game/categories 
```


## Create gameroom

PostRequest : 
```
localhost:8085/game/create 
```
RequestBody: 
````json lines
{
    "nickname" : "someone"
}
````
             
             
## Connect to gameroom

PostRequest : 
```
localhost:8085/game/connect
```
RequestBody: 
```json lines
{
  "gameId": "778bb",
  "player2": {
    "nickname": "somebody"
  }
}
```
         
## Ready to Start Request
(Own nickname + gameId)

PostRequest : 
```
localhost:8085/game/ready
```
RequestBody: 
```json lines
{
    "gameId": "778bb",
    "nickname" : "somebody",
    "categories" : ["cat1", "cat2", "cat3"]
    
}
```

## Check if opponent is ready
(Wird jeder sekunde angerufen bis opponent isReady, Max = 20 sekunden danach ist das gameroom nicht mehr gültig, zurück zum Menu)
"Start Game" Clickbar nur wenn opponent isReady 
(Own nickname + gameId)

PostRequest : 
```
localhost:8085/game/ready
```
RequestBody: 
```json lines
{
    "gameId": "778bb",
    "nickname" : "somebody"
    
}
```

             
## Submit answers

PostRequest: 
```
localhost:8085/game/submitanswers
```
RequestBody: 
```json lines
{
    "gameId": "778bb",
    "nickname": "somebody",
    "answers": [
        {
            "answer":"blablab",
            "isAnswerCorrect": true
        },
        {
            "answer":"blablab2",
            "isAnswerCorrect": false
        }
    ]
}
```

<h1 id="highscore">Highscores</h1>

## Get Top Ten (DESC)
POST-Request:
```
localhost:8085/api/highscore/getTopTen
```
RequestBody:
```json lines
*EMPTY*
```
Response:
```json lines
 {
  [
    {
        "score": 63,
        "nickname": "name",
        "id": "63a5b5c8fa8a3f8a9a85e680"
    },
    {
        "score": 55,
        "nickname": "postman",
        "id": "6dad676a-90b4-451d-88d3-89c82e768066"
    },
    ...
]
}
```


<h1 id="cat-and-que-api">Category and Question API</h1>

#### Roadmap Category API

- [X] Create a Category
- [X] Read all categories (without Questions)
- [X] Update Category (name)
- [X] Read one Category (with Questions)
- [X] Delete Category

#### Roadmap Question API

- [X] Create Question
- [X] Read all questions
- [X] Update question
- [ ] Delete Question
- [X] Read Question
- [ ] Read Random Questions By Multiple Categories 


#### <span style='color:salmon'>Information about error handling for Category-API!</span>
1. In case you have specified a category ID which is 
not in our Database you will receive the following error message:
````json lines
{
    "msg": "No category found!",
    "date": "2023-01-06T09:30:11.462109",
    "statusCode": 400
}
````
2. In case you have specified a categoryName which is already taken or an empty-string 
you will receive the following error message:
```json lines
{
    "msg": "Invalid name.",
    "date": "2023-01-06T09:32:06.956463",
    "statusCode": 400
}
```

## CREATE a Category

POST-Request: 
```
localhost:8085/api/category/create
```

RequestBody:

```json lines
{
  "categoryName": "Allgemeinwissen",
  "questions": [{
    "question": "Wer war während des 2. Weltkriegs US-Präsident?",
    "answers": [{
      "answer": "Roosevelt",
      "isAnswerCorrect": true
    },
      {
        "answer": "Washington",
        "isAnswerCorrect": false
      }]
  }]
}
```
## READ All Categories 

GET-Request:
```
localhost:8085/api/category
```

RequestBody: 
```
*EMPTY*
```
Response:
```json lines
{
  "categories": [
    {
      "id": "54800863-0db5-472e-8799-4c6ee439b665",
      "categoryName": "Schätzfragen",
      "iconURL": "",
      "desc": ""
    },
    {
      "id": "c13be76d-ffce-4a91-b3df-9c66fe7d3fa5",
      "categoryName": "Allgemeinwissen",
      "iconURL": "",
      "desc": ""
    },
  ],
  "countCategories": 2,
  "countQuestions": 8
}
```

## UPDATE Existing Category 
*Updating Questions will be handled separately*

PUT-Request:
```
localhost:8085/api/category/update
```

RequestBody:

```json lines
{
    "id": "1234567",
    "categoryName": "New Category Name",
    "iconURL": "www.some-icon.de/123456?size=sm",
    "desc": "This is a description"
}
```


## Read Category
GET-Request:
```
localhost:8085/api/category/{categoryId}
```
RequestBody:
```json lines
*EMPTY*
```
Response:
```json lines
{
  "categoryName": "Fragen zum Thema Programmieren in Kotlin",
  "questions": [
    {
      "question": "Question Title",
      "answer": [
        {
          answer: "Answer Text 1",
          isCorrectAnswer: true
        },
        {
          answer: "Answer Text 2",
          isCorrectAnswer: false
        }
      ],
      "categoryId": "942f3c05-bba2-47dc-8b37-11dc8847a1b5",
      "id": "0f8e484d-e3e6-442b-95e6-2ca6c0a59e7b"
    }
  ],
  "id": "942f3c05-bba2-47dc-8b37-11dc8847a1b5"
}
```

## DELETE Category
DELETE-Request:
```
localhost:8085/api/category/{categoryId}
```
RequestBody:
```json lines
*EMPTY*
```
Response:
```json lines
*EMPTY*
```

## CREATE Question
POST-Request:
```
localhost:8085/api/questions/create
```
RequestBody:
```json lines
{
  "question": "Hallo",
  "answer": [],
  "categoryId": "54800863-0db5-472e-8799-4c6ee439b665"
}
```
Response:
```json lines
201 Created
```

## READ Questions
GET-Request:
```
localhost:8085/api/questions/{categoryId}
```
RequestBody:
```json lines
*EMPTY*
```
Response:
```json lines
{
  "categoryName": "Schätzfragen",
  "questions": [
    {
      "question": "Wie viele Weihnachtsbäume werden in Deutschland pro Jahr verkauft?",
      "answer": [
        {
          "answer": "Etwa 30 Millionen",
          "isAnswerCorrect": true
        },
        {
          "answer": "Etwa 10 Millionen",
          "isAnswerCorrect": false
        }
      ],
      "categoryId": "54800863-0db5-472e-8799-4c6ee439b665",
      "id": "12d38f34-be51-4909-8c04-a43440f497d1"
    }
  ],
  "countQuestions": 3
}
```

## UPDATE Question
PUT-Request:
```
localhost:8085/api/questions
```
RequestBody:
```json lines

{
  "question": "Wie viele Weihnachtsbäume werden in Deutschland pro Jahr verkauft?",
  "answer": [
    {
      "answer": "Etwa 30 Millionen",
      "isAnswerCorrect": true
    },
    {
      "answer": "Etwa 10 Millionen",
      "isAnswerCorrect": false
    }
  ],
  "categoryId": "54800863-0db5-472e-8799-4c6ee439b665",
  "id": "12d38f34-be51-4909-8c04-a43440f497d1"
}
```
Response:
```json lines
200 OK
```

## DELETE Question
DELETE-Request:
```
localhost:8085/api/questions
```
RequestBody:
```json lines
{
  id: "1234567",
  categoryId: "9876543"
}
```
Response:
```json lines
200 OK
```

## Read Question
POST-Request:
```
localhost:8085/api/question
```
RequestBody:
```json lines
{
  id: "1234567",
  categoryId: "9876543"
}
```
Response:
```json lines
 {
  id: "1234567",
  question: "Question Text",
  answers: [
    {
      answer: "Answer Text",
      isCorrectAnswer: false
    }
  ],
  categoryId: "9876543"
}
```

## BUILD DOCKER LOCAL
Prerequisites: Docker + WSL2

Create Image:
```
Navigate to dockerfile
r-click > open Powershellwindow
docker build -t wildfly ./
```
Run Image:
```
docker run -p 8080:8080 -p 9990:9990 -it wildfly
```
Dockerfile:
```
FROM quay.io/wildfly/wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh timadmin timadmin123 --silent
#copy deployment
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
```

## CREATE WAR-FILE AND DEPLOYMENT
Momentan nur auf Deployment-Branch möglich!

Generate WAR-File:
```
Gradle > quiz_api > Tasks > build > run bootWar

WAR-FILE found in
build > libs > quiz_api-0.0.1-SNAPSHOT.war
```
Start Wildfly Appserver:
```
localhost:8080 > click "Administration Console"
oder
localhost:9990

USER:   timadmin
PW:     timadmin123
```
Deployment:
```
Deployments > Add > Upload Deployment >
Choose a file or drag it here > myapp.war > next > finish
```
Acces:
```
Base URL:
http://localhost:8080/quiz
Bsp:
http://localhost:8080/quiz/api/category