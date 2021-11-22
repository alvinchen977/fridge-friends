
from django.http import JsonResponse, JsonResponse
from django.db import connection

import json 
import requests


def createUserFridge(request):
    json_data = json.loads(request.body)
    if "username" in json_data: 
        username = json_data["username"]
        cursor = connection.cursor()
        cursor.execute("SELECT username FROM users WHERE username = %s", (username, ))
        row = cursor.fetchone()
        if not row is None:
            cursor.execute("SELECT * FROM virtualfridges WHERE ownerid = %s", (username, ))
            row = cursor.fetchone()
            if row is None: 
                cursor.execute("INSERT INTO virtualfridges VALUES "
                        "(%s,%s);", (username, json.dumps({})))
                return JsonResponse({"msg": "User Fridge Created"}, status = 200)
            else: 
                return JsonResponse({"msg": "User fridge already exists"}, status = 401)
        else:
            return JsonResponse({"msg": "User does not exist"}, status=401)

def deleteFromFridge(request): 
    json_data = json.loads(request.body)
    if "username" in json_data and "itemName" in json_data:
        username = json_data["username"]
        itemName = json_data["itemName"]
        cursor = connection.cursor()
        cursor.execute("SELECT username FROM users WHERE username = %s", (username, ))
        row = cursor.fetchone()
        if not row is None:
            cursor.execute("SELECT items FROM virtualfridges WHERE ownerid = %s", (username,))
            currentItems = cursor.fetchone()[0]
            if not currentItems is None: 
                if itemName in currentItems:
                    currentItems[itemName] -= 1
                    if currentItems[itemName] <= 0:
                        del currentItems[itemName]
                cursor.execute("UPDATE virtualfridges SET items = %s WHERE ownerid = %s", (json.dumps(currentItems), username,))
                return JsonResponse({"msg": "Items Updated: " + str(currentItems)}, status = 200)
            else:
                return JsonResponse({"msg": "User " + username + " has no virtual fridge associated with them"}, status = 422) 
        else:
            return JsonResponse({"msg": "User " + username + " does not exist"}, status = 401)
    else: 
        return JsonResponse({"msg": "Malformed request, must contain username and name of item to delete"}, status = 422)

    
def updateFridgeItem(request):
    json_data = json.loads(request.body) 
    if "username" in json_data and "nameBefore" in json_data and "nameAfter" in json_data: 
        username = json_data["username"]
        nameBefore = json_data["nameBefore"]
        nameAfter = json_data["nameAfter"]
        cursor = connection.cursor()
        cursor.execute("SELECT username FROM users WHERE username = %s", (username, ))
        row = cursor.fetchone()
        if not row is None: 
            cursor.execute("SELECT items FROM virtualfridges WHERE ownerid = %s", (username, ))
            currentItems = cursor.fetchone()[0]
            if not currentItems is None: 
                if nameBefore in currentItems: 
                    quantity = currentItems[nameBefore]
                    if nameAfter in currentItems: 
                        quantity_two = currentItems[nameAfter]
                    else:
                        quantity_two = 0
                    del currentItems[nameBefore]
                    currentItems[nameAfter] = quantity + quantity_two
                    cursor.execute("UPDATE virtualfridges set items = %s WHERE ownerid = %s", (json.dumps(currentItems), username))
                    return JsonResponse({"msg": "Item Updated: " + str(currentItems)}, status = 200) 
                else: 
                    return JsonResponse({"msg": "Item name " + nameBefore + " found in fridge for user: " + username}, status = 200)
            else:
                return JsonResponse({"msg": "No virtual fridge for user: " + username + " found"}, status = 401) 
        else: 
            return JsonResponse({"msg": "User " + username + " does not exist"}, status=401)
    else: 
        return JsonResponse({"msg": "Malformed Request, must contain username, name before and name after"}, status = 422) 

def postToFridge(request): 
    json_data = json.loads(request.body)
    if "username" in json_data and "itemName" in json_data and "quantity" in json_data:
        username = json_data["username"]
        itemName = json_data["itemName"]
        quantity = json_data["quantity"]
        cursor = connection.cursor()
        cursor.execute("SELECT username FROM users WHERE username = %s", (username, ))
        row = cursor.fetchone()
        if not row is None:
            cursor.execute("SELECT items FROM virtualfridges WHERE ownerid = %s", (username,))
            currentItems = cursor.fetchone()[0]
            if not currentItems is None:
                if itemName not in currentItems: 
                    currentItems[itemName] = quantity
                else:
                    currentItems[itemName] += quantity
                if currentItems[itemName] <= 0:
                    del currentItems[itemName]
                cursor.execute("UPDATE virtualfridges SET items = %s WHERE ownerid = %s", (json.dumps(currentItems), username,))
                return JsonResponse({"msg" : "Items Updated: " + str(currentItems)}, status = 200)
            else:
                return JsonResponse({"msg": "User " + username + " has no virtual fridge associated with them"}, status = 422) 
        else:
            return JsonResponse({"msg": "User " + username + " does not exist"}, status = 401)
    else: 
        return JsonResponse({"msg": "Malformed request, must contain username and name of item to delete"}, status = 422)

def getUserFridge(request): 
    json_data = json.loads(request.body) 
    if "username" in json_data:
        username = json_data["username"]
        cursor = connection.cursor()
        cursor.execute("SELECT username from users WHERE username = %s", (username, ))
        row = cursor.fetchone()
        if not row is None: 
            cursor.execute("SELECT items from virtualfridges WHERE ownerid = %s", (username, ))
            result = cursor.fetchone()[0]
            if not result is None:
                response = {}
                response["items"] = [result] 
                return JsonResponse(response)
            else:
                return JsonResponse({"msg": "User " + username + " has no virtual fridge"}, status = 401)
        else: 
            return JsonResponse({"msg" : "User " + username + "does not exist"}, status = 401)
    else:
        return JsonResponse({"msg" : "Malformed Request, must include username in post"}, status = 401)
