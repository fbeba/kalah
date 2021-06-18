# Game of Kalah

## Application preparation
In order to run the application you will need Java 11 installed. Application already provides maven wrapper.

## How to run
To start the server execute following from root folder:

`./mvnw spring-boot:run`

Standard maven commands are also available.

## How to run with docker

If you're reading this you're clearly a pro, so following is just to speed things up with copy-paste ;) 

`./mvnw clean install`

`docker build -t kalah .`

`docker run -d -p 8080:8080 kalah`

## How to play
Application exposes three endpoints for playing Kalah:

### Create game
Endpoint available under
`http://localhost:8080/games`, it accepts POST requests to setup game. 

Responds with following data:

`{
"id": 7494,
"uri": "http://localhost:8080/games/7494"
}`

### Moving
Endpoint available under
`http://localhost:8080/games/{gameId}/pits/{pitId}`, it accepts PUT requests containing valid `gameId` and `pitId` which has to be player's own non-home pit which contains at least one stone. 

Responds with game data including board status:

`{
"id": 4908,
"url": "http://localhost:8080/games/4908",
"status": {"1": "6", "2": "6", "3": "6", "4": "6", "5": "0", "6": "7", "7": "1", "8": "7", "9": "7", "10": "7", "11": "7", "12": "6", "13": "6", "14": "0"}
}`

### Checking board
Endpoint available under
`http://localhost:8080/games/{gameId}`, it accepts GET requests containing valid `gameId` and returns its current board state. It executes no change to the game.

Responds with same as move request - game data including board status:

`{
"id": 4908,
"url": "http://localhost:8080/games/4908",
"status": {"1": "6", "2": "6", "3": "6", "4": "6", "5": "0", "6": "7", "7": "1", "8": "7", "9": "7", "10": "7", "11": "7", "12": "6", "13": "6", "14": "0"}
}`

###Example curl commands:

`curl 
--header "Content-Type: application/json" \
--request POST \
http://localhost:8080/games`

`curl 
--header "Content-Type: application/json" \
--request PUT \
http://localhost:8080/games/5436/pits/5`

`curl
--header "Content-Type: application/json" \
--request GET \
http://localhost:8080/games`


## Dev Notes

All games are stored in-memory, the consequence being games don't survive restarts. Application allows for multiple games being played in parallel and has some basic safeguards against concurrent access.

Requested API has been extended with convenience endpoint for checking board state.

Anonymous players are used for each game. There is no player authentication, players are expected to keep track of turn, although game mechanics involves some validation there.


## Potential improvements

Introduce persistence.

Extend player component for increased personalisation, authentication, history etc.