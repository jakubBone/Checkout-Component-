# 🛒 Checkout Component

![](src/main/resources/logo.png)

This project is my approach to your recruitment task I had the pleasure of solving. 
I have created a simple but functional checkout system here that can scan and handle discounts.

## 🎯 Features 

The application is a REST service that handles basic checkout operations:
*   **Scanning products**: You add products to the cart, and the system remembers what you've put there
*   **Individual prices**: Each product has its base price

*   **Multi-Buy Promotions**: Buy N items, pay Y
*   **Bundle Offers**: Buy X and Y together, get a discount
*   **Receipt**: At the end, you get a receipt with a summary, prices, and discounts

## 💻 Technologies Used

*   **Java 21**
*   **Spring Boot 3**
*   **Maven**
*   **JUnit 5 & Mockito**
*   **Git & GitHub**

## 🛠️ Architecture and Design

I opted for a classic, layered architecture to keep it clear:

*   **`Controller`**: Here, the API receives HTTP requests
*   **`Service`**: The heart of the business logic (scanning, counting and handling discounts)
*   **`Repository`**: Warehouse for product and promotion data 

## 🤓 A small digression about DTO (Data Transfer Objects)

You might notice that I don't use separate DTO classes (e.g., `ReceiptResponse`) for API responses. 
Instead, I send domain objects (like `Receipt`) directly. 
This is a conscious decision not to complicate a small project with an additional mapping layer. 
In larger systems of course it would be a standard and good practice, but here I opted for simplicity.

## 🤓 A small digression about discounts 

In my store, I am exceptionally generous :)

The client gets a multi-buy discount, and then still get a bundle discount (`combine offer`)

I am aware that the "normal" business world, discounts often exclude each other. But here, it was a conscious design decision.

## 🎁 Regarding the discounts requirements:

### Req 1:  `"Some items are multi-priced: buy N of them, and they’ll cost you Y cents"`:

I initially wondered if it meant a discount on each item in the pack (e.g., for 3x A, each cheaper by 10, totaling 90). 
But ultimately, based on the phrasing `"they'll cost you Y cents"`, I assumed that "special price" refers to the price for the entire pack (i.e., 3x A costs 30)

### Req 2: `"Some items are cheaper when bought together - buy item X with item Y and save Z cents"`:

Here I assumed the discount values myself (-5 for A+B and -10 for C+D) 


## 🚀 How to Run It?

### Running the Application

The quickest way to see the service in action is to grab the ready-to-use `.jar` file from the **`"Releases" section on GitHub`**. Just download it and run:

```bash
java -jar checkout-1.0.0-SNAPSHOT.jar
```

If you're keen to verify the build process or dive into the code, you'll need to clone the repository and build it yourself:

```bash
# Clone the repository
git clone https://github.com/jakubBone/Checkout-Service
cd Checkout-Service

# Build the project
mvn clean package

# Run the application
java -jar target/checkout-1.0.0-SNAPSHOT.jar
```

### Running Tests

Tests are always run from the source code. After cloning the repository, navigate to the project's root directory and run:

```bash
mvn test
```



## 🌐 API Usage

Here are some examples of how you can play with the API using `curl`:

### 1. Scan Item
Adds a product to the cart.
```bash
curl -X POST http://localhost:8080/api/checkout/scan/A
curl -X POST http://localhost:8080/api/checkout/scan/B
```

### 2. Get Cart
Check what's in your cart.
```bash
curl http://localhost:8080/api/checkout/cart
```

### 3. Checkout
Finalizes the transaction, calculates everything, and returns a receipt. The cart will be cleared.
```bash
curl -X POST http://localhost:8080/api/checkout/checkout
```

---

I hope this `README` clarifies everything for you! 

###  See you on the 1st day of work on my onboarding! I hope... 😊
