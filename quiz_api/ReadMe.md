


## Inhaltsverzeichnis:

- [Game API](#game-api)
- [Category and Questions API](#cat-and-que-api)
___

*Mit dem Program [Postman](https://www.postman.com), kann man Requests and die Api schicken und die Responses im JSON format auslesen*
___
<h1 id="game-api">Game API</h1>

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
             
             
## connect to gameroom

PostRequest : 
```
localhost:8085/game/connect
```
RequestBody: 
```json lines
{
  "gameId": "f28e2",
  "player2": {
    "nickname": "somebody"
  }
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
  "gameId": "f28e2",
  "token" : "toki",
  "answers" : [true,false,false,false]
}
```

<h1 id="cat-and-que-api">Category and Question API</h1>

## CREATE a Category (DONE)

POST-Request: 
```
localhost:8085/api/category
```

RequestBody:

```json lines
{
    "categoryName": "Test Test Test"
}
```
## READ All Categories (DOING)

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
[
  {
    "categoryName": "Fragen zum Thema Programmieren in Kotlin",
    "id": "942f3c05-bba2-47dc-8b37-11dc8847a1b5"
  },
  {
    "categoryName": "Fragen zum Thema Mathematik",
    "id": "11dc8847a1b5-bba2-47dc-8b37-47dc"
  }
]
```

## UPDATE Existing Category (DOING)
*Updating Questions will be handled separately*

PUT-Request:
```
localhost:8085/api/category
```

RequestBody:

```json lines
{
    "id": "1234567",
    "categoryName": "New Category Name"
}
```


## Read Category (DOING)
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
      "answer": [],
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