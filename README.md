<!-- ABOUT THE PROJECT -->
## About The Project

On a retail website , the following discounts apply

* If the user is an employee of the store , he gets 30% discount 
* If the user is an affilliate of the store , he gets 10% discount
* If the user is has been a customerfor over 2 years , he gets 5% discount
* For every $100 on the bill, there would be a $5 discount(e.g. for $990 ,
   you get $45 as discount)
* Percentage based discounts don't apply on groceries
* A User can get onlyone of the percentage based discounts on a bill

  


This project is about finding the net payable amount for a user provided with his
/her billing details.

### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

* Java 17
* Springboot 3.2.8
* Oracle 19c as Relational DB
* Flyway as sql state versioning tool database
* Maven as build tool
* Mockito based Junit unit tests


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Java 17
* Maven
* IntelliJ or Eclipse as IDE  

### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Clone the repo
   ```sh
   git clone https://github.com/pisharma01/nagarro-repo.git
   ```
2. Execute maven clean build command
   ```sh
   mvn clean build
   ```
  
3. Once successful build happens , execute the application hitting
   the Play Icon on RetailStoreApplication.java file
   ```sh
   src/main/java/com/dm/retail/RetailStoreApplication.java
   ```
4. We have 3 users As below with their type :
     * pisharma with type "EMPLOYEE"
     * ajkumar with type "AFFILIATE"
     * anilkumar with type "Customer with more than 2 years"

    I am mentioning below scenario for "EMPLOYEE" type , but it can
   be altered with above types as per use case.

5. Again we have 4 types of products :
    * GROCERY
    * CLOTHING
    * FURNITURE
    * SECURITIES_SUPPLIES

  NOTE : Users and Products are included as metadata via flyway migration
         insert scripts under name "V1_1_1__insert_retail_users_and_products.sql"
      
6. As application is up , open Postman and execute below request

   ```sh
    url : http://localhost:9000/retail-store/api/v1/billing/amount
    method : POST
    body :  {
    "username": "pisharma",
    "products": [
        {
            "productCode": "GRC001",
            "name": "COCA-COLA",
            "type": "GROCERY",
            "description": "DRINK",
            "price": 10,
            "quantity": 2,
            "totalPrice": 20
        },
        {
            "productCode": "CL001",
            "name": "POLO_SHIRT",
            "type": "CLOTHING",
            "description": "CLOTHING",
            "price": 10,
            "quantity": 2,
            "totalPrice": 20
        },
        {
            "productCode": "SEC001",
            "name": "CAMERA",
            "type": "SECURITY_SUPPLIES",
            "description": "SECURITY_SUPPLIES",
            "price": 25,
            "quantity": 8,
            "totalPrice": 200
        }
    ]
}

7. Below is the screenshot for above request via Postman :

   ```sh
   ![ScreenShot]( https://github.com/pisharma01/nagarro-repo.git/master/dm-assignment/retail/src/main/resources/execution_pisharma.png)
   ```
