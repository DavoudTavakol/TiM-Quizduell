


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

## Create a Category

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
## Get All Categories and Questions

GET-Request:
```
localhost:8085/api/category
```

RequestBody: 
```
*EMPTY*
```
## Update Existing Category 
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