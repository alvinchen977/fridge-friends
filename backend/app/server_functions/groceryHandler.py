import os, time, sys
from django.http import JsonResponse, HttpResponse
from django.conf import settings
from django.core.files.storage import FileSystemStorage
from app.logging import logger
import json
import requests
import environ
from collections import defaultdict; 
# Called in views.py when a POST request is made to postGrocery endpoint

#need to remove before production!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
VISION_KEY="AIzaSyDQ8AbOW45Nsjr_koDS9u7qgUG3D_jhUTo"

def handleGrocery(request):
	json_data = json.loads(request.body);
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
							"type":"OBJECT_LOCALIZATION",
							"maxResults":100
						}
					]
				}
			]
		}

		#visionJson = json.loads(visionRequestBody)
		visionAPIUrl = 'https://vision.googleapis.com/v1/images:annotate?key='+ VISION_KEY
		visionAPIResponse = requests.post(visionAPIUrl, json=visionRequestBody)	
		logger.logGroceryResponse(visionAPIResponse)
		#get list of objects from the json of the response 
		object_list = {}
		object_list = defaultdict(lambda:0, object_list)
		responseObjectList = visionAPIResponse.json()['responses'][0]['localizedObjectAnnotations']
		for	obj in responseObjectList:
			object_list[obj['name']] +=1
		returnDict = {}
		for key, value in object_list.items():
			returnDict[key] = value
		json_data = json.dumps(returnDict, indent=2)
		return HttpResponse(json_data, status=200)		
	else:
		ErrorMessage = "Malformed Request, Request must contain base64 encoded Image"
		return HttpResponse(ErrorMessage, status=422) #malformed request 
