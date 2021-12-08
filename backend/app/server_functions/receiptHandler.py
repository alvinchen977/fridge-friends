import os, time
from django.http import JsonResponse, HttpResponse
from django.conf import settings
from django.core.files.storage import FileSystemStorage
from app.logging import logger
from django.db import connection
import json
import requests
import re
# Called in views.py when a POST request is made to postGrocery endpoint

#need to remove before production!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
VISION_KEY="AIzaSyDQ8AbOW45Nsjr_koDS9u7qgUG3D_jhUTo"

def handleReceipt(request):
    json_data = json.loads(request.body)
    response = {}
    image = "";
    if "image" in json_data:
        image = json_data["image"]
        visionRequestBody ={
                "requests":[
                    {
                        "image":{
                            "content": image
                            },
                        "features":[
                            {
                                "type":"DOCUMENT_TEXT_DETECTION",
                                }
                            ]
                        }
                    ]
                }
        try:
            visionAPIUrl = 'https://vision.googleapis.com/v1/images:annotate?key='+ VISION_KEY
            visionAPIResponse = requests.post(visionAPIUrl, json=visionRequestBody)	
            responseObjectList = visionAPIResponse.json()['responses'][0]['textAnnotations']
            logger.logReceiptResponse(visionAPIResponse)		
            logger.logTest('responseObjectList',str(responseObjectList))
            wordList = []
            for obj in responseObjectList:
                wordList.append(obj['description'])
            returnWordList = {}
            counter = 0 
            for word in wordList:
                if (re.match("^\d*\.\d*", word)):
                    returnWordList[counter] = word
                    counter += 1
            return JsonResponse(returnWordList, status=200)		
        except Exception as e:
            return JsonResponse({"msg": "Unable to identify Image"}, status = 200) 
    else:
        response['message'] = "Malformed Request, Request must contain base64 encoded Image"
        return JsonResponse(response, status=422) #malformed request 

def getReceipts(request):
    json_data = json.loads(request.body) 
    if "username" in json_data:
        username = json_data["username"]
        cursor = connection.cursor()
        cursor.execute("SELECT * FROM users WHERE username = %s;", (username,))
        rows = cursor.fetchall()
        if not rows is None: 
            cursor.execute("SELECT * FROM receipts where ownerid = %s;", (username,))
            receipts = cursor.fetchall()
            response = {}
            response["receipts"] = receipts
            return JsonResponse(response, status=200)
        else:
            return JsonResponse({"msg": "user not found"}, status = 401)
    else: 
        return JsonResponse({"msg": "Malformed request, must contain username"}, status = 401)

def postReceipt(request):
    json_data = json.loads(request.body)
    if "username" in json_data and "total" in json_data:
        cursor = connection.cursor()
        username = json_data["username"]
        total = json_data["total"]
        cursor.execute("SELECT * FROM users WHERE username = %s", (username,))
        rows = cursor.fetchone()
        if not rows is None: 
            cursor.execute("INSERT INTO receipts VALUES "
                    "(%s,%s);", (username, total))
            return JsonResponse({"msg": "receipt recorded"}, status = 200)
        else:
            return JsonResponse({"msg": "user not found"}, status = 401)
    else:
        return JsonResponse({"msg": "malformed request, must contain username and total"})
