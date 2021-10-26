import os, time
from django.http import JsonResponse, HttpResponse
from django.conf import settings
from django.core.files.storage import FileSystemStorage
from app.logging import logger
import json
import requests
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
		logger.logVisionAPIResponse(visionAPIResponse)
		json_data = json.loads(visionAPIResponse.body)
		return HttpResponse(visionAPIResponse, status=200)		
	else:
		ErrorMessage = "Malformed Request, Request must contain base64 encoded Image"
		return HttpResponse(ErrorMessage, status=422) #malformed request 
