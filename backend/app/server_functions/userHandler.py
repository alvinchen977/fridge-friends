'''
from django.shortcuts import render
from django.http import JsonResponse, HttpResponse
from django.db import connection

from django.views.decorators.csrf import csrf_exempt
import json

import os, time
from django.conf import settings
from django.core.files.storage import FileSystemStorage

from google.oauth2 import id_token
from google.auth.transport import requests

import hashlib
def check_authorization(request):
    json_data = json.loads(request.body)

    sessionID = json_data['sessionID']

    cursor = connection.cursor()
    cursor.execute('SELECT username, expiration FROM sessions WHERE sessionID = %s;', (sessionID,))

    row = cursor.fetchone()
    now = time.time()
    if row is None or now > row[1]:
		return HttpResponse(status=401) # 401 Unauthorized
	return HttpResponse(status=200) # User is properly authorized

def registerNewUser(request):
	json_data = json.loads(request.body)
    clientID = json_data['clientID']   # the front end app's OAuth 2.0 Client ID
    idToken = json_data['idToken']     # user's OpenID ID Token, a JSon Web Token (JWT)

    now = time.time()                  # secs since epoch (1/1/70, 00:00:00 UTC)

    try:
        # Collect user info from the Google idToken, verify_oauth2_token checks
        # the integrity of idToken and throws a "ValueError" if idToken or
        # clientID is corrupted or if user has been disconnected from Google
        # OAuth (requiring user to log back in to Google).
        # idToken has a lifetime of about 1 hour
        idinfo = id_token.verify_oauth2_token(idToken, requests.Request(), clientID)
    except ValueError:
        # Invalid or expired token
        return HttpResponse(status=511)  # 511 Network Authentication Required

    # get username
    try:
        username = idinfo['name']
    except:
        username = "Profile NA"

    # Compute chatterID and add to database
    backendSecret = "fridgefriends"   # or server's private key
    nonce = str(now)
    hashable = idToken + backendSecret + nonce
   	sessionID = hashlib.sha256(hashable.strip().encode('utf-8')).hexdigest()

    # Lifetime of chatterID is min of time to idToken expiration
    # (int()+1 is just ceil()) and target lifetime, which should
    # be less than idToken lifetime (~1 hour).
    lifetime = min(int(idinfo['exp']-now)+1, 60) # secs, up to idToken's lifetime

    cursor = connection.cursor()
    # clean up db table of expired chatterIDs
    cursor.execute('DELETE FROM sessions WHERE %s > expiration;', (now, ))

    # insert new chatterID
    # Ok for chatterID to expire about 1 sec beyond idToken expiration
    cursor.execute('INSERT INTO sessions (sessionid, username, expiration) VALUES '
                   '(%s, %s, %s);', (sessionID, username, now+lifetime))

    # Return chatterID and its lifetime
    return JsonResponse({'sessionID': sessionID, 'lifetime': lifetime})
'''
