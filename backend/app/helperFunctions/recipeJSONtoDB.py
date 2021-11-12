import sys 
import json 

from django.db import connection 

with open ('../../../recipes_raw/recipes_raw_nosource_epi.json', 'r') as json_file: 
	data = json_file.read();
json_obj = json.loads(data)
cursor = connection.cursor()
for identifier, recipe in json_obj.items():
	ingredients = recipe['ingredients']
	picture_link = recipe['picture_link']
	instructions = recipe['instructions']
	title = recipe['title']
	cursor.execute("select nextval('recipes_recipeid_seq')")
	recipeID = cursor.fetchone()
	cursor.execute('INSERT INTO recipes (recipeid, title,ingredients, instructions, image_url) VALUES '
				'(%s, %s,%s,%s,%s);', (recipeID,title,ingredients,instructions,picture_link))
