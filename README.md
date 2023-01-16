# parking-management-system

## Steps to start the application

### Locally : 

1. Clone the application in eclipse/intelliJ
2. On running - it will by default start on http://localhost:8080
3. H2 Console will get available on : http://localhost:8080/h2-console
4. H2 console will be accessible using default credentials : sa/password
5. The postman collection is attached inside the directory /design

### Docker

## Features
- Create Parking Space
Curl : ``` curl --location --request POST 'http://localhost:8080/parking' \
--header 'Content-Type: application/json' \
--data-raw '{
    "parkingName": "BLR Parking1",
    "city": "Bangalore",
    "small": 5,
    "medium": 5,
    "large": 5,
    "extraLarge": 5
}' ```

- Allocate Parking Space
Curl : ``` curl --location --request POST 'http://localhost:8080/getslot/01db76ae-e9d5-40f7-ac1d-c733f7fb18df/EXTRALARGE?carNumber=KA05KY1352' ```

- Release Parking Space
Curl : ```curl --location --request POST 'http://localhost:8080/releaseslot/0845eb64-ac40-4f68-9a20-a365e66fd328/4e7e06d1-a5bc-4254-a090-ab66bc05c415'```

- Get Current Parking Status
Curl : ``` curl --location --request GET 'http://localhost:8080/parking/01db76ae-e9d5-40f7-ac1d-c733f7fb18df'```
