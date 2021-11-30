from google.oauth2 import id_token
from google.auth.transport import requests 

import uuid
import hashlib 
import requests
import json

from django.http import HttpResponse, JsonResponse
from django.db import connection


def userLogin(request):
    if request.session.has_key('username'):
        return HttpResponse(request.session['username'])
    form_data = json.loads(request.body)
    user_form = form_data['username']
    pass_form = form_data['password']
    # Connect to database
    cursor = connection.cursor()
    exists_query = """SELECT username
                      FROM users
                      WHERE username = %s"""
    # Query database
    cursor.execute(exists_query, (user_form,))
    exists = cursor.fetchall()
    if not exists:
        return HttpResponse()
    password_query = """SELECT password
                        FROM users
                        WHERE username = %s"""
    # Query database
    cursor.execute(password_query, (user_form,))
    password_in_db = cursor.fetchone()
    password_in_db = password_in_db[0]

    password = hashalgo(user_form, pass_form)
    if password_in_db == password:
        request.session['username'] = user_form
        return HttpResponse(user_form)
    return HttpResponse()


def userLogout(request):
    try:
        del request.session['username']
    except:
        pass
    return HttpResponse()

def userCreate(request):
    form_data = json.loads(request.body)
    user_form = form_data['username']
    pass_form = form_data['password']
    # Connect to database
    cursor = connection.cursor()
    exists_query = """SELECT username
                      FROM users
                      WHERE username = %s"""
    # Query database
    cursor.execute(exists_query, (user_form,))
    exists = cursor.fetchall()
    if exists or not pass_form:
        return HttpResponse()

    salt = uuid.uuid4().hex
    hash_obj = hashlib.new('sha512')
    hash_obj.update((salt + pass_form).encode('utf-8'))
    password_hash = hash_obj.hexdigest()
    password_db_string = "$".join(['sha512', salt, password_hash])

    create_query = """INSERT INTO \
                      users(username, password)
                      VALUES (%s, %s);"""
    cursor.execute(create_query, (user_form, password_db_string))
    request.session['username'] = user_form
    return HttpResponse(user_form)


def hashalgo(username, password):
    """Hash user passwords."""
    algorithm = 'sha512'
    cursor = connection.cursor()
    salt_query = """SELECT password
                    FROM users
                    WHERE username = %s"""
    cursor.execute(salt_query, (username,))
    row = cursor.fetchone()
    unsplit = row[0]
    split = unsplit.split("$")
    salt = split[1]
    hash_obj = hashlib.new(algorithm)
    password_salted = salt + password
    hash_obj.update(password_salted.encode('utf-8'))
    password_hash = hash_obj.hexdigest()
    password_db_string = "$".join([algorithm, salt, password_hash])
    return password_db_string
