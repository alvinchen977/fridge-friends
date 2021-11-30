from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

from django.http import JsonResponse, HttpResponse

from app.server_functions import receiptHandler

from app.server_functions import groceryHandler

from app.server_functions import recipeHandler

from app.server_functions import userHandler
# from app.server_functions import fileName 
# you can then use the specific function as fileName.funcionName
#any folder you want to import from MUST have a __init__.py to be recognized as a module

# Create your views here.
@csrf_exempt
def postReceipt(request): 
    #If we access this method without making a POST, we will exit and error
    if request.method != 'POST':
        return HttpResponse(status=404)
    #otherwise call the functions
    return receiptHandler.handleReceipt(request)

@csrf_exempt
def postGrocery(request):
    if request.method != 'POST':
        return HttpResponse(status=404)
    return groceryHandler.handleGrocery(request)

@csrf_exempt
def findRecipeByIngredients(request):
    if request.method != 'POST': 
        return HttpResponse(status=404)
    return recipeHandler.findRecipeByIngredients(request)

@csrf_exempt
def findRecipeByTitle(request): 
    if request.method != 'POST':
        return HttpResponse(status=404)
    return recipeHandler.findRecipeByTitle(request)

@csrf_exempt
def findRecipeByKeyword(request):
    if request.method != 'POST':
        return HttpResponse(status=404)
    return recipeHandler.findRecipeByKeyword(request)

@csrf_exempt
def findRecipeByLikeStatus(request):
    if request.method != 'POST':
        return HttpResponse(status=404)
    return recipeHandler.findRecipeByLikeStatus(request)

def findRecipeByDefault(request):
    if request.method != "GET":
        return HttpResponse(status=404)
    return recipeHandler.findRecipeByDefault(request)

@csrf_exempt
def likeRecipe(request):
    if request.method != "POST":
        return HttpResponse(status = 404)
    return recipeHandler.likeRecipe(request)

@csrf_exempt 
def unlikeRecipe(request): 
    if request.method != "POST":
        return HttpResponse(status = 404) 
    return recipeHandler.unlikeRecipe(request) 

@csrf_exempt
def userLogin(request):
    if request.method != 'POST':
        return HttpResponse(status=404)
    return userHandler.userLogin(request)

@csrf_exempt
def userLogout(request):
    if request.method != 'POST':
        return HTtpResponse(status=404)
    return userHandler.userLogout(request)

@csrf_exempt
def userCreate(request):
    if request.method != 'POST':
        return HttpResponse(status=404)
    return userHandler.userCreate(request)
