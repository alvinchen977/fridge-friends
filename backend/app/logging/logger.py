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
	requests_log.write(log)
	requests_log.close()
	return

def logGroceryResponse(response):
	universalTime = datetime.now()
	date = "[" + universalTime.strftime("%m/%d/%Y, %H:%M:%S") + "]: "
	log = date + str(response.status_code) + " " + " " + str(response.content) + "\n\n"
	requests_log = open("./app/logging/logs/groceryHandlerLog.txt","a") 
	requests_log.write(log)
	requests_log.close()
	return 

def logTest(tag, value):
	universalTime = datetime.now()
	log = tag + ": " + value + "\n\n"
	requests_log = open("./app/logging/logs/testLog.txt","w") 
	requests_log.write(log)
	requests_log.close()

def logReceiptResponse(response):
    universalTime = datetime.now()
    date = "[" + universalTime.strftime("%m/%d/%Y, %H:%M:%S") + "]: "
    log = date + str(response.status_code) + " " + " " + str(response.content) + "\n\n"
    requests_log = open("./app/logging/logs/ReceiptHandlerLog.txt","a")
    requests_log.write(log)
    requests_log.close()
    return	
