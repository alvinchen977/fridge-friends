import sys
import json 
import glob 
import time 
from django.db import connection 
import ijson 
import gc
cursor = connection.cursor()
cursor.execute("TRUNCATE TABLE recipes;")
cursor.execute("ALTER SEQUENCE recipes_recipeid_seq RESTART WITH 1;")
filelist = glob.glob('./all_recipes_json/all_recipes_24000_24499.json')
print (filelist) 
count = 0
recipe= {}
for filename in filelist:
    #use ijson to parse large json object from file rather than loading into our limited memory
    parser = (ijson.parse(open(filename, 'r')))
    for prefix,event,value in parser: 
        if (event == 'map_key'):
            if(value == 'title'):
                prefix, event,value = next(parser)
                recipe['title'] = value
            if (value == 'instructions'):
                prefix, event, value = next(parser)
                recipe['instructions'] = value
            if (value == 'ingredients'):
                prefix, event, value = next(parser)
                recipe['ingredients'] = []
                while (True):
                    prefix, event, value = next(parser)
                    if (event == 'end_array'):
                        break
                    recipe['ingredients'].append(value)
            if (value == 'image'):
                prefix, event, value = next(parser)
                recipe['image'] = value
            if (value == 'total_time'):
                prefix, event, value = next(parser)
                recipe['total_time'] = value
            if (value == 'nutrients'):
                prefix, event, value = next(parser)
                nutrients = {}
                while(True):
                    prefix, event, value = next(parser)
                    if(event == 'end_map'):
                        break
                    nutrient_name = value
                    prefix,event, value = next(parser)
                    nutrients[nutrient_name] = value
                recipe['nutrients'] = nutrients
            if (value == 'yield'):
                prefix,event,value = next(parser)
                recipe['yield'] = value
        elif (event == 'end_map'):
            count = count + 1 
            cursor.execute("select nextval('recipes_recipeid_seq')")
            recipeID = cursor.fetchone()
            cursor.execute('INSERT INTO recipes (recipeid, title, ingredients, instructions, image, total_time, nutrients, yield) VALUES '
                    '(%s, %s, %s, %s, %s, %s, %s, %s);', (recipeID, recipe['title'], recipe['ingredients'], str(recipe['instructions']), recipe['image'], str(recipe['total_time']), str(recipe['nutrients']), str(recipe['yield'])))
            print (recipe['title'] + " added")
            del recipe 
            del prefix
            del value
            del event
            time.sleep(0.05)
            recipe = {}
        gc.collect()

print (str(count) + " recipes added to DB")
