import sys 
import json 

from django.db import connection 

cursor = connection.cursor()
confirmation = "yes"
if confirmation == "yes":
		filelist = ['./recipes_raw/recipes_raw_nosource_ar.json', './recipes_raw/recipes_raw_nosource_epi.json', './recipes_raw/recipes_raw_nosource_fn.json']
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
				if 'picture_link' in recipe:
					picture_link = recipe['picture_link']
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
				cursor.execute("select nextval('recipes_recipeid_seq')")
				recipeID = cursor.fetchone()
				cursor.execute('INSERT INTO recipes (recipeid, title,ingredients, instructions, image_url) VALUES '
						'(%s, %s,%s,%s,%s);', (recipeID,title,ingredients,instructions,picture_link))
		print(str(count) + " recipes added to db")
else:
	quit()
