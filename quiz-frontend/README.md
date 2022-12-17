FRONTEND

Aufgaben:
- Anmeldung mit "Nickname" oder "Nickname" und "Game ID"
- Fragen können beantwortet werden
- Ergebnis wird in "Highscore Tabelle" angezeigt
- Kotlin Library zum Fetchen/http Request aufs Backend(API)




## Inhaltsverzeichnis:

- [Game API](#game-api)
___

*Mit dem Program [Postman](https://www.postman.com), kann man Requests and die Api schicken und die Responses im JSON format auslesen*
<br></br>Emulatoren können mit der Url "http://10.0.2.2:8085/game/..." auf das API zugreifen*
<br></br>Der Server muss auf dem selben PC laufen, (Ordner quiz_api mit IntelliJ öffnen und Main Klasse starten)*
<br></br>Alle PostRequests 'headers'
```
  Map<String, String> = {
        'Content-Type': 'application/json; charset=UTF-8',
      },
```


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
