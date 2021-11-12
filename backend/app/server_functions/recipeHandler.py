import json
import requests

from django.http import HttpResponse

#receives a post request with {"ingredients": [array of ingredients]} in the body
#returns a list of recipes containing all of the keywords listed  
def findRecipeByIngredients(request):
	return HttpResponse(status=200)

#receives a post request with {"Title": someVal } in the body
#returns a recipe if found in the DB iwth matching title
def findRecipeByTitle(request):
	return HttpResponse(status=200)

#receives a post request with { "keywords": [array of keywords] } in the body 
# returns list of recipes containing keywords in ingredients or instructions 
def findRecipeByKeyword(request):
	return HttpResponse(status=200)

#receives a post request with { "username": "someval" } in the body
#returns list of recipes the user has liked 
def findRecipeByLikeStatus(status=200):
	return HttpResponse(status=200) 
