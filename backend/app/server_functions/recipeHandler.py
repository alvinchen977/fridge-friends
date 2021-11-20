import json
import requests

from django.http import HttpResponse, JsonResponse

from django.db import connection 

#receives a post request with {"ingredients": [array of ingredients]} in the body
#returns a list of recipes containing all of the keywords listed  
def findRecipeByIngredients(request):
    json_data = json.loads(request.body)
    response = {}
    response ['recipes'] = []
    data_dict = {}
    if "ingredients" in json_data: 
        ingredients = json_data["ingredients"]
        cursor = connection.cursor()
        for ingredient in ingredients:
            data_dict['like'] = ('%' + ingredient + '%')
            cursor.execute("SELECT * FROM recipes WHERE ingredients LIKE %(like)s LIMIT 10;", data_dict)
            rows = cursor.fetchall()
            response['recipes'].append(rows)
        return JsonResponse(response)
    else:
        ErrorMessage = "Malformed Request, Request must contain array of ingredients to search"
        return HttpResponse(ErrorMessage, status=422)
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
def findRecipeByLikeStatus(request):
    return HttpResponse(status=200) 

def findRecipeByDefault(request):
    cursor = connection.cursor()
    cursor.execute("SELECT * FROM recipes ORDER BY random() LIMIT 5;")
    rows = cursor.fetchall()	
    response = {}
    response['recipes'] = rows
    return JsonResponse(response)
