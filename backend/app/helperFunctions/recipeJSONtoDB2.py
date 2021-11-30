import sys
import json
import base64
import requests
import time
from django.db import connection

def get_as_base64(url):
	return base64.b64encode(requests.get(url).content)

cursor = connection.cursor()
confirmation = "yes"
filelist = ['./all_recipes.json']
count = 0
for filename in filelist:
	with open (filename, 'r') as json_file:
		data = json_file.read();
	json_obj = json.loads(data)
	for identifier, recipe in json_obj.items():
		count = count + 1
		if 'ingredients' in recipe:
			ingredients = recipe['ingredients']
		else:
			ingredients = 'null'
		if 'image' in recipe:
			url = recipe['image']
			picture_link = get_as_base64(url)
			time.sleep(.05)
		else:
			picture_link = 'null'
		if 'instructions' in recipe:
			instructions = recipe['instructions']
		else:
			instructions = 'null'
		if 'title' in recipe:
			title = recipe['title']
		else:
			title = 'null'
		if 'total_time' in recipe: 
			total_time = recipe['total_time']
		else:
			total_time = 'null'
		if 'nutrients' in recipe: 
			nutrients = recipe['nutrients']
		else:
			nutrients = 'null'
		if 'yield' in recipe:
			recipe_yield = recipe['yield']
		else:
			recipe_yield = 'null'
		cursor.execute("select nextval('recipes_recipeid_seq')")
		recipeID = cursor.fetchone()
		print ( title + " " + str(ingredients) + " " + str(instructions) + " " + str(picture_link))
		#cursor.execute('INSERT INTO recipes (recipeid, title,ingredients, instructions, image_url) VALUES '
				#'(%s, %s,%s,%s,%s);', (recipeID,title,ingredients,instructions,picture_link))
print(str(count) + " recipes added to db")
