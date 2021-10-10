from django.http import HttpResponse, JsonResponse
#called in views.py when a post request is made to the postReceipt endpoint 
def handleReceipt(request):
    return HttpResponse(status = 200) 
