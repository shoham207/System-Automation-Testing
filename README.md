# Software Quality Engineering - System Testing
This is a repository for the system-testing assignment of the Software Quality Engineering course at the [Ben-Gurion University](https://in.bgu.ac.il/), Israel.

## Assignment Description
In this assignment, we tested an open-source software called [PrestaShop](https://github.com/PrestaShop/PrestaShop).

PrestaShop is an open-source e-commerce platform that allows businesses to create and manage online stores. It provides a wide range of features and functionalities to help merchants set up their online storefronts, manage products, process orders, handle payments, and more. PrestaShop is highly customizable, offering various themes and modules to tailor the store's appearance and functionality to specific needs. It also includes tools for marketing, SEO optimization, and analytics to help merchants attract customers and grow their online businesses.

## What we tested
Module Description:
The wishlist module enables users to add products they are interested in to a wishlist for future reference or purchase consideration. It provides functionality for managing the wishlist, including adding products with specific quantities.

User Stories Tested:

1. **User Story:** Adding products to wishlist with quantity X.

   *Preconditions:* The user is logged in and navigates to a product page. The product is available for adding to the wishlist.

   *Expected Outcome:* The user successfully adds the product to their wishlist with the specified quantity X. The product is listed in the user's wishlist with the desired quantity.

2. **User Story:** Admin changes the quantity of a product to less than X.

   *Preconditions:* The admin is logged in and accesses the product management interface. The product with a wishlist quantity greater than X exists.

   *Expected Outcome:* Upon changing the quantity of the product to less than X, the system appropriately updates the wishlist. If the quantity change results in the wishlist quantity being less than X, the product is still retained in the wishlist but with the updated quantity. If the quantity becomes zero, the product may be removed from the wishlist, depending on the system's implementation.

These user stories encompass common interactions with the wishlist module, covering both user and admin perspectives, ensuring the module's functionality meets the intended requirements and expectations.

## How we tested
We used two different testing methods:
1. [Cucumber](https://cucumber.io/), a behavior-driven testing framework.
2. [Provengo](https://provengo.tech/), a story-based testing framework.

Each of the testing methods is elaborated in its own directory. 

## Detected Bugs
We detected the following bugs:

1. Bug 1: 
   1. General description: ...
   2. Steps to reproduce: ...
   3. Expected result: ...
   4. Actual result: ...
   5. Link to the bug report: (you are encouraged to report the bug to the developers of the software)
2. Bug 2: ...

$$*TODO* if you did not detect the bug, you should delete this section$$  
