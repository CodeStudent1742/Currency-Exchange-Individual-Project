# Currency Exchange Application
This is the BackEnd part of Currency Exchange Application.
The FrontEnd can be find under link : https://github.com/CodeStudent1742/Currency-Exchange_Front


Last commit in BackEnd application: https://github.com/CodeStudent1742/Currency-Exchange-Individual-Project/commit/9fd90b179b1d08f861b8e22464273a4829e8dd32


FrontEnd without GooglePlaces functionality on **main** branch


FrontEnd with GooglePlaces functionality on **with_Google_Nearby**  branch

This is simplified version o currency exchange application I prepared as final project of Kodilla Fast Track Java Developer Course.

# Getting started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
See deployment for notes on how to deploy the project on a live system. Just clone or download the files into a folder and it's ready to be used.*

## BackEnd

- Clone the repository
```
git clone https://github.com/CodeStudent1742/Currency-Exchange-Individual-Project.git
```
- Create a MySql connection as described in the application.properties.

- Build the project
```
./gradlew build
```
- Application  using external data sources GooglePlaces which require to create an Account and API Key.
GooglePlaces usage is free only to certain amount of HTTP requests, that is why I do not share my Key.
To test this functionality you need to create your account and API Key based on instruction in link below:
https://developers.google.com/maps/documentation/places/web-service/get-api-key?hl=pl
- Please input your key in application.properties
![enter image description here](https://snipboard.io/MV0n2o.jpg)

## FrontEnd
- Clone the repository
```
git clone  https://github.com/CodeStudent1742/Currency-Exchange_Front.git
```

- Build the project
```
./gradlew build
```
- There are 2 variants of FrontEnd: on  main branch(without GooglePlaces API usage) and  on with_Google_Nearby branch ( with GooglePlaces API usage)
- FrontEnd is also presented on PDF added to repository
## How to run it

Once you have the BackEnd and FrontEnd running start by this address:


http://localhost:8080/

