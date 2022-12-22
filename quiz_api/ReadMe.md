


## Inhaltsverzeichnis:

- [Game API](#game-api)
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
"player1":{
    "nickname" : "someone"
},
"categories" : ["cat1", "cat2", "cat3"]
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
    "nickname" : "somebody"
    
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

<h1 id="cat-and-que-api">Category and Question API</h1>

#### Roadmap Category API

- [X] Create a Category
- [X] Read all categories (without Questions)
- [X] Update Category (name)
- [X] Read one Category (with Questions)
- [X] Delete Category
- [ ] Count All Categories

#### Roadmap Question API

- [ ] Create Questions (multiple)
- [ ] Read all questions
- [ ] Update question
- [ ] Delete Question
- [ ] Read Question
- [ ] Count All Questions




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
      "categoryName": "Schätzfragen"
    },
    {
      "id": "c13be76d-ffce-4a91-b3df-9c66fe7d3fa5",
      "categoryName": "Allgemeinwissen"
    },
    {
      "id": "5cd67945-ed50-47fd-a476-fddc125bae76",
      "categoryName": "Allgemeinwissen 2"
    }
  ],
  "countCategories": 3
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
    "categoryName": "New Category Name"
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

## CREATE Questions (multiple)
POST-Request:
```
localhost:8085/api/questions
```
RequestBody:
```json lines
{
  categoryId: "1231234",
  questions: [
    {
      question: "Question Text",
      answers: [
        {
          answer: "Answer Text",
          isCorrectAnswer: false
        }
      ]
    }
  ]
}
```
Response:
```json lines
200 OK
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
[
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
  },
  {
    id: "6622222",
    question: "Question Text 2",
    answers: [
      {
        answer: "Answer Text",
        isCorrectAnswer: false
      }
    ],
    categoryId: "9876543"
  }
]
```

## UPDATE Question
PUT-Request:
```
localhost:8085/api/questions
```
RequestBody:
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

## COUNT ALL QUESTIONS
DELETE-Request:
```

```
RequestBody:
```json lines

```
Response:
```json lines

```
## COUNT ALL CATEGORIES
DELETE-Request:
```

```
RequestBody:
```json lines

```
Response:
```json lines

```