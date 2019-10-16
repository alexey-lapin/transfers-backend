### transfers backend
Simple money transfer api using Javalin framework

#### Run:
1. build `./gradlew clean build`
2. run `java -jar build/libs/transfers-backend-all.jar

#### Endpoints
base path /api

##### accounts
GET /accounts  
GET /accounts/:id  
GET /accounts/:id/transfers  
POST /accounts

create account:
```json
{
	"account": {
		"amount": 5
	}
}
```

##### transfers
GET /transfers  
GET /transfers/:id  
POST /transfers

make transfer:
```json
{
	"transfer": {
		"fromAccountId": 1,
		"toAccountId": 2,
		"amount": 1.17
	}
}
```
