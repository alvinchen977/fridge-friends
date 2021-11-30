
from django.http import JsonResponse, HttpResponse
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
        if not rows is None:
            cursor.execute("SELECT * FROM virtualfridges WHERE username = %s", (username,))
            row = cursor.fetchone()
            if row is None:
                cursor.execute("SELECT NEXTVAL('virtualfridges_fridgeid_seq')")
                fridgeID = cursor.fetchone()
                cursor.execute("INSERT INTO ownsfridge VALUES "
                        "(%s,%s);", (username,fridgeID))
                return HttpResponse("Fridge Create for user", status = 200)
            else:
                return HttpResponse("User fridge already Exists", status = 401)
        else:
            return HttpResponse("User does not exist", status=401)

