urls.py is where we will add all the routes for our server 

to add a new route, be sure to call "from app import views" at the top of urls.py 

this will give us access to the functions in views.py 

from there we can define a new path in the urlpatterns array: 

path('endpoint/', views.functionToCall, name='someName');

now when the url https://ourserver/endpoint/ is accessed, views.functionToCall is executed. 
