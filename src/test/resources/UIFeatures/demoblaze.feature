Feature: demo blaze shopping 

Scenario Outline: validate demo blaze online purchase flow

Given launch browser and url
When custumer navigate to Laptop to select "<LaptopModel>"
When customer adds item to cart 
When customer navigates to "Home" page
When custumer navigate to Laptop to select "<LaptopMode2>"
When customer adds item to cart
When customer navigates to "Cart" page
When customer deletes item "<LaptopMode2>" from cart
When customer clicks on place Order button
When customer fills the purchase form
Then assert expected price matches with the actual price of the item
Then close the browser
Examples: 
|LaptopModel|LaptopMode2|
|Sony vaio i5|Dell i7 8gb |