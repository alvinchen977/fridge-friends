from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

from django.http import JsonResponse, HttpResponse

from app.server_functions import receiptHandler

from app.server_functions import groceryHandler

# If you create a new .py in server_functions to manage some endpoint, be sure to import it like this:
# from app.server_functions import fileName 
# you can then use the specific function as fileName.funcionName
#any folder you want to import from MUST have a __init__.py to be recognized as a module

# Create your views here.
@csrf_exempt
def postReceipt(request): 
    #If we access this method without making a POST, we will exit and error
    if request.method != 'POST':
        return HttpResponse(status=404)
    #otherwise call the functions
    return receiptHandler.handleReceipt(request)
@csrf_exempt
def postGrocery(request):
	if request.method != 'POST':
		return HttpResponse(status=404)
	return groceryHandler.handleGrocery(request)
