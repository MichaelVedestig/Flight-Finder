# Flight-Finder
API for searching and booking flights

To search the database for flights send POST request to "flight/filter" with data structure consisting of two mandatory requestDTOs for the departure airport and arrival airport and an list of optional requestDTOs:
```
{
    "from" :
    {
        "filterBy" : "departureDestination",
        "operation" : "JOIN",
        "value" : "Oslo",
        "joinTable" : "route"
    },
    "to" :
    {
        "filterBy" : "arrivalDestination",
        "operation" : "JOIN",
        "value" : "Amsterdamn",
        "joinTable" : "route"
    },
    "searchRequestDTOList" : [
        {
        "filterBy" : "availableSeats",
        "operation" : "GREATER_THAN",
        "value" : "5"
        },
        {
        "filterBy" : "adult",
        "operation" : "JOIN",
        "value" : "2000",
        "joinTable" : "prices"
        }
    ]
}
```
This will return a list of FlightDTOs.

Valid Operations are:

		"EQUALS", "LIKE", "IN", "GREATER_THAN", "LESS_THAN", "BETWEEN", "JOIN", "NOT_EQUAL"

Valid filter keys for route table are: 

		"route_id" 
		"departureDestination"(I did not name this...)
		"arrivalDestination"

Valid filter keys for flight table are:
            
                "flight_id"
                "departureAt"
                "arrivalAt"
                "availableSeats"
                
Valid filter keys for flight table are:
                  
                    "currency"  
                    "adult"
                    "child"
                    
To book a flight, a user must be registered by POST request to "/user/register" with structure:

```
  {
        "email" : 
        "password" :
        "role" :
   }  
```

Once registered an order can be sent to by POST request to  "/flight/booking" with structure:

```  
  {
        "email": "mike@mail.com",
        "flightDTO" : {
          "flightIdList": [
              "84d6f482"
          ],
          "departureAt": "2023-04-05T19:00:00.000Z",
          "arrivalAt": "2023-04-05T22:00:00.000Z",
          "prices": {
              "currency": "SEK",
              "adult": 187.27,
              "child": 117.34
          },
        "waitTime": "direct flight"
    },
    "adults" : "2",
    "children" : 1
}
```

This will return a Booking:

```
{
    "booking_id": "N92JQQ",
    "bookingDetails": {
        "email": "mike@mail.com",
        "flightDTO": {
            "flightIdList": [
                "84d6f482"
            ],
            "departureAt": "2023-04-05T19:00:00.000Z",
            "arrivalAt": "2023-04-05T22:00:00.000Z",
            "prices": {
                "currency": "SEK",
                "adult": 187.27,
                "child": 117.34
            },
            "waitTime": "direct flight"
        },
        "adults": 2,
        "children": 1
    },
    "total": 491.88,
    "currency": "SEK"
}
```

To see your users account info, including stored bookings, the user needs to login by POST request to "/user/login" with structure:

```
{
    "email" :
    "password" :
}
```

This will return JWT

You can the send GET request with said Bearer token to "/user" which will return all information on the user, except the encoded password:

```
{
    "id": 1,
    "email": "mike@mail.com",
    "role": "USER",
    "tokens": [
        {
            "id": 1,
            "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWtlQG1haWwuY29tIiwiaWF0IjoxNjc4NDI4NDA3LCJleHAiOjE2Nzg1MTQ4MDd9.GPtwNtf-			n5BIXtCqmc_xfSJBYFI3ZMxyxf6ZpvBQ5sw",
			
            "tokenType": "BEARER",
            "revoked": false,
            "expired": false
        }
    ],
    "bookings": [
        {
            "booking_id": "BTRRH9",
            "bookingDetails": {
                "email": "mike@mail.com",
                "flightDTO": {
                    "flightIdList": [
                        "b4e9fecb",
                        "f44c177a"
                    ],
                    "departureAt": "2023-03-30T21:00:00.000Z",
                    "arrivalAt": "2023-04-02T20:00:00.000Z",
                    "prices": {
                        "currency": "SEK",
                        "adult": 268.17,
                        "child": 284.68
                    },
                    "waitTime": "PT-65H"
                },
                "adults": 4,
                "children": 2
            },
            "total": 1642.04,
            "currency": "SEK"
        },
        {
            "booking_id": "7B9HNP",
            "bookingDetails": {
                "email": "mike@mail.com",
                "flightDTO": {
                    "flightIdList": [
                        "ee1d3931"
                    ],
                    "departureAt": "2023-03-30T11:00:00.000Z",
                    "arrivalAt": "2023-03-30T14:00:00.000Z",
                    "prices": {
                        "currency": "SEK",
                        "adult": 136.18,
                        "child": 79.27
                    },
                    "waitTime": "direct flight"
                },
                "adults": 4,
                "children": 2
            },
            "total": 703.26,
            "currency": "SEK"
        }
    ]
}
```
