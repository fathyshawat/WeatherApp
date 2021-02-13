# WeatherApp

# Description App
Displays temperatures of cities that were written in EditText. Or By using your location to get your city's temperature.

# How ?
By loading a file JSON "city.list.json" local in android studio and get the id of each  city name to using in https://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743

# Libraries and Architecture

- MVVM (Model-View-ViewModel) 
- Volley to get data from api 
- Dagger 2 (dependency injection) : to Simplifies access to shared instances like Volley object and make it singleton and  reducing boilerplate code.
- RxJava : to do a heavy tasks in app in background like(get name-id of all cities).
- DataBinding :  minimizes the necessary code in your application logic to connect to the user interface elements.avoid null pointer exception.
- Picasso : easy to deal with img url by(download the image in another thread and it manages for you) and display it in ui .
