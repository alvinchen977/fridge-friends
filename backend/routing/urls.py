"""routing URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path

#give us access to the functions in views.py
from app import views

urlpatterns = [

    path('admin/', admin.site.urls),
    path('postReceipt/', views.postReceipt, name='postReceipt'),
    path('postGrocery/', views.postGrocery, name='postGrocery'),
    path('findRecipeByIngredients/', views.findRecipeByIngredients, name='findRecipeByIngredients'),
    path('findRecipeByTitle/', views.findRecipeByTitle, name='findRecipeByTitle'),
    path('findRecipeByKeyword/', views.findRecipeByKeyword, name='findRecipeByKeyword'),	
    path('findRecipeByLikeStatus/', views.findRecipeByLikeStatus, name='findRecipeByLikeStatus'),
    path('findRecipeByDefault/', views.findRecipeByDefault, name='findRecipeByDefault'),
    path('likeRecipe/',views.likeRecipe, name='likeRecipe'),
    path('unlikeRecipe/', views.unlikeRecipe, name='unlikeRecipe'),
    path('userCreate/', views.userCreate, name='userCreate'),
    path('userLogin/', views.userLogin, name='userLogin'),
    path('userLogout/', views.userLogout, name='userLogout'),
    path('createUserFridge/', views.createUserFridge, name="createUserFridge"),
]
