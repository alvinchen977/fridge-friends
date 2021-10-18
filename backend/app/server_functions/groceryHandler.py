import os, time
from django.http import JsonResponse, HttpResponse
from django.conf import settings
from django.core.files.storage import FileSystemStorage
from app.logging import logger
import json
import requests
import environ
# Called in views.py when a POST request is made to postGrocery endpoint

#need to remove before production!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
VISION_KEY="AIzaSyDQ8AbOW45Nsjr_koDS9u7qgUG3D_jhUTo"

def postGrocery(json_data):
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
							"type":"LABEL_DETECTION",
							"maxResults":5
						}
					]
				}
			]
		}

		#visionJson = json.loads(visionRequestBody)
		visionAPIUrl = 'https://vision.googleapis.com/v1/images:annotate?key='+ VISION_KEY
		visionAPIResponse = requests.post(visionAPIUrl, json=visionRequestBody)	
		logger.logVisionAPIResponse(visionAPIResponse)		
		return HttpResponse(visionAPIResponse, status=200)		
	else:
		ErrorMessage = "Malformed Request, Request must contain base64 encoded Image"
		return HttpResponse(ErrorMessage, status=422) #malformed request 

