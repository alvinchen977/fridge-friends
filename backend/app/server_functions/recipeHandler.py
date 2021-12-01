import json
import requests

from django.http import HttpResponse, JsonResponse

from django.db import connection 

#receives a post request with {"ingredients": [array of ingredients]} in the body
#returns a list of recipes containing all of the keywords listed  
def likeRecipe(request): 
    json_data = json.loads(request.body)
    if "recipeid" in json_data:
        username = "test"
        recipeid = json_data["recipeid"]
        cursor = connection.cursor()
        cursor.execute("INSERT INTO liked_recipes VALUES "
                "(%s,%s);",(username,recipeid,))
        return HttpResponse("Recipe Liked", status=200)
    else:
        ErrorMessage = "Malformed request, request must contain recipeid and username"
        return HttpResponse(ErrorMessage, status=422)



def unlikeRecipe(request): 
    json_data = json.loads(request.body)
    if "username" in json_data and "recipeid" in json_data:
        username = json_data["username"]
        recipeid = json_data["recipeid"]
        cursor = connection.cursor()
        cursor.execute("DELETE FROM liked_recipes WHERE username = %s AND recipeid = %s", (username,recipeid))
        return HttpResponse("Recipe unliked", status = 200)
    else: 
        return HttpResponse("Malformed request, must have username and recipeid", status=422)


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
    json_data = json.loads(request.body)
    response = {}
    response['recipes'] = []
    if "username" in json_data: 
        username = json_data["username"]
        cursor = connection.cursor()
        cursor.execute("SELECT * FROM recipes WHERE recipeid IN (SELECT recipeid FROM liked_recipes WHERE username = %s);", (username,))
        rows = cursor.fetchall()
        response['recipes'].append(rows)
        return JsonResponse(response)
    else:
        return HttpResponse("Malformed Request, request must contain username", status=422)

def findRecipeByDefault(request):
    cursor = connection.cursor()
    cursor.execute("SELECT * FROM recipes ORDER BY random() LIMIT 15;")
    rows = cursor.fetchall()	
    response = {}
    response['recipes'] = rows
    return JsonResponse(response)
