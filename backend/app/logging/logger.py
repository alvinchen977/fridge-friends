import json
from datetime import datetime 

def logGroceryRequest(request):
	universalTime = datetime.now()
	date = "[" + universalTime.strftime("%m/%d/%Y, %H:%M:%S") + "]: " 
	log = date + str(request.method) + " " + str(request.path) + " - { "
	log += str(request.POST) + ", "
	for item in request.POST.items():
		log += str(item) + ", "
	log += "}\n\n"
	requests_log = open("./app/logging/logs/groceryHandlerLog.txt","a") #append mode
	requests_log.write(log);
	return

def logVisionAPIResponse(response):
	universalTime = datetime.now()
	date = "[" + universalTime.strftime("%m/%d/%Y, %H:%M:%S") + "]: "
	log = date + str(response.status_code) + " " + " " + str(response.content) + "\n\n"
	requests_log = open("./app/logging/logs/groceryHandlerLog.txt","a") 
	return 

