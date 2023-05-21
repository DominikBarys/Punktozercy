# Description
The "Punktożercy" project is intended to be a prototype of an online store application with the possibility of collecting points by purchasing products and spending them on new ones. After launching the application, the user will be presented with a login screen, he will be able to log in using a previously created account, which he can set up using the registration panel or by logging in using his Google account. After passing the login procedure, he is redirected to the main panel of the application which contains such things as his name, points and a list of product offers which he can easily filter according to his needs. After clicking on one of the available offers, the user will be directed to the list of its products, where he will get information about the product, such as: its price in PLN, points, product name and description. Using the buttons, he will be able to set the selected quantity of a given product and add it to the shopping cart. 
The user will be kept informed about the number of products in his cart by a visible number next to the cart icon. In the shopping cart tab, he will be able to view the products he has selected along with their total price including delivery. The user will be able to cancel a given product by clicking on the cross icon. The shopping cart will be updated on an ongoing basis. The user will be able to pay for his/her purchases using the points he/she has collected or in cash, using the button to set the payment method. For each purchase made, the user will receive points, the so-called cashback (a certain percentage of the total amount of purchases). The user will be able to track his purchase history, which includes information about the products he purchased, the date of purchase and the payment method (purchase with points or currency). In the settings tab, the user will be able to view his or her data, including username, e-mail address, phone number or home address, and set his or her preferences, such as the application theme.
Using the buttons, he will be able to edit his data or send points to another user by specifying his e-mail and the amount of points he wants to transfer to him. The application's main panel will also include a button responsible for displaying a map on which it will be possible to see the nearest stationary points and the user's current position to determine which point is closest to his.

# Used Technologies
- Kotlin
- ROOM
- Coroutines
- Android Studio

# External specification
After launching the application, the user is presented with a login screen in which he can log into the application with his account and via Google login or, if he does not yet have an account, go to the registration panel 
<br/>
![image](https://github.com/rixis321/Punktozercy/assets/58139675/711144ea-055b-4417-a481-2d9e44178e5a)
<br/>
<br/>
<br/>
![image](https://github.com/rixis321/Punktozercy/assets/58139675/e08faf33-97b7-4916-bc5e-d43bb231a317)
<br />
<br/>
<br/>
When registering and logging in, the data entered are strictly checked and if there is an error, the user will be notified
<br />
<br/>
![image](https://github.com/rixis321/Punktozercy/assets/58139675/cf4ccd72-d074-4bc3-a275-d228ee1ab878)
<br/>
<br />
![image](https://github.com/rixis321/Punktozercy/assets/58139675/211b220d-b9d2-4450-9b1c-1cb1f42bf508)
<br/>
<br />

After successfully logging in, the user is redirected to the main panel, which shows him, among other things, his name, the number of points and a list of product offers
<br/>
<br />
![image](https://github.com/rixis321/Punktozercy/assets/58139675/cf105be0-eb10-4a72-bb35-c875a58c59c6)
<br/>
<br />
Using the available icons, the user can filter the list of offers according to their categories
<br />
<br />
![image](https://github.com/rixis321/Punktozercy/assets/58139675/19607e53-afca-48aa-914f-cd6a062d4ca1)
<br/>
<br/>
After clicking on one of the offers, the user is shown a list of products related to the selected offer
<br/>
<br/>
![image](https://github.com/rixis321/Punktozercy/assets/58139675/67974b6a-4f99-43c6-bc1d-4f7f50e13a07)
<br/>
<br />
Using the available buttons, the user can select the quantity of a given product and add it to the shopping cart. The number of products currently in the shopping cart is continuously visible on the application navigation panel located at the bottom of the screen next to the shopping cart icon. The shopping cart panel contains previously selected products, information on their price, delivery price and total price with delivery as well as a button that allows changing the payment method and a button that allows the user to purchase the selected products
<br/>
<br />
![image](https://github.com/rixis321/Punktozercy/assets/58139675/14e1225f-7bbe-413d-bf51-5af4b7761b91)
<br />
<br/>
After the successful purchase of products, information on when the product was purchased and with what currency goes into the user's purchase history, which he can view in the history panel
<br />
<br/>
![image](https://github.com/rixis321/Punktozercy/assets/58139675/39412d03-daa6-45ca-b1d2-8214ef5d51aa)
<br />
<br/>
While using the application, the user can also use the settings panel which allows him, among other things, to modify his personal data, log out or send points to another user
<br />
<br/>
![image](https://github.com/rixis321/Punktozercy/assets/58139675/58167db4-7406-4582-91d1-5ed6214643b4)
<br />
<br/>
![image](https://github.com/rixis321/Punktozercy/assets/58139675/7d9df6f3-1802-462b-9cf8-8f5075a667a1)
<br />
<br/>
The functionality of sending points is validated for the existence of an email address in the database and whether the user has enough points that he wants to send, an additional security feature is also that the possibility of sending points to himself is blocked.
<br />
<br/>
# Testing
The program was tested in the Andorid Studio environment, using the built-in emulator of the Pixel 6 phone in API versions 24 and 33 with Adnroid 7.0 (Nougat) and Android 13.0 (Tiramisu). The functions of the application work correctly after testing, the least testing was done in terms of different screen resolutions. The app looks correct on the resolution used by the Pixel 6 while on devices with other resolutions, such as tablets, the app may look different and not be readable.

