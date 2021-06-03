# ITECH3107

ITECH3107-Mobile Device Programming

Assignment 3
HanlinZheng
30336872









Introduction:

At start Page of APP, App has 3 buttons to go to different activities: “MILLIONAIRE”, “HOT SEAT’, and “ LIFELINES”. “MILLIONAIRE” is Assignment1 part. Hot seat is Assignment 2 part and “LIFELINES” is assignment 3.

At this Application, question is read from 3 different way: Database, Local Json file and Internet URL address.
At millionaire function, question is read from database, which is also the place to store user Information.
At Hot seat function, Question is read from Local Json File:” \main\Assets\SampleQuestion.txt”.


At Lifelines part (Assignment 3), Application will read from Internet url first. If failed, it will read from local Json file.



Before start the game, Please use setExampleData(); function at “onCreate();” method of MainActivity(First Activity) to input example questions to the database, otherwise, there will be some errors on “millionaire” part. 

After Call this, please make it as annotation, to avoid input sample questions into database again every times when you run the emulator.
 


Now Application could be start.





1.
For first functionality.
There are advantages the player can use to help them: “50:50”, “Random Possibility”, and “Switch”.  At this Application, each question will only allow use one advantage. 

“50:50”: The function is achieved by Application divide four answers to two different group.
[1,3] or [2,4]. If correct answer belongs to one of group, another group will disappear. For example: if answer 1 is correct, answer 2 will disappear.

“Random Possibility”: at easy mode (question 1-6), correct answer will have highest possibility After question No.6 (Medium and hard), correct will have lowest possibility.

Switch: this will change the question: 
 

2.
At “Lifelines” activity start, if app can connect to internet. It will have toast and display question from url:

Url is created by use website which is recommend at Assignment 3 document.
Url Address store in Lifelines Activiry private String websiteaddress.
 
This application only shows multiple choice. Therefore, please choose multiple question if you want to change this address. Meanwhile, if there are too many questions, the application can not get whole String back (I tried 40 questions). There will always some data missing When use not full String value to transfer to entity by using Gson. There will shows some errors. And please input more than 12 questions to application to achieve “Switch” advantage.  

Sometime, question will show some symbol like: “&”, ”*”, ”^”; I checked the source, it is bring by the original source. Sometimes website give question will strange symbol. 
 


3.
I haven’t completed the function that to provide the google map. But I complete to store the location of the use by using two double value to store latitude and longitude. 

(method from Youtube:

https://www.youtube.com/watch?v=Ak1O9Gip-pg&t=655s&ab_channel=AndroidCodingAndroidCoding

Code:
 

Export the database from data/data/au.edu.fedunimillionaire30336872/database and database is like this:
 

Latitude: 37.4229923
Longitude: -122.0841856

This location is Feduni Berwick campus which is same location as I set on emulator.
4.
If use shake mobile with more than 5 m/s-2 twice at X-axis at Start page. Application will show one Entity which can not access by another ways.         
