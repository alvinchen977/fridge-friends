from recipe_scrapers import scrape_me
from time import sleep 
import json
import multiprocessing 
import requests
import base64

base_url = "https://www.allrecipes.com/recipe/"
wait_time = .001
thread_count = 4

def makeRequest(start, end):
    recipes = {} 
    for i in range(start,end):
        recipe = {} 
        scraper = scrape_me(base_url + str(i) + "/")
        try:
            recipe["title"] = scraper.title()
            recipe["instructions"] = scraper.instructions()
            recipe["ingredients"] = scraper.ingredients()
        except:
            recipe["title"] = "null"
            recipe["instructions"] = "null"
            recipe["ingredients"] = "null"
        try:
            recipe["image"] = str(base64.b64encode(requests.get(scraper.image()).content))
        except Exception as ex:
            recipe["image"] = "null"
            print (ex)
            #print("image failed")
        try:
            recipe["total_time"] = scraper.total_time()
        except:
            recipe["total_time"] = "null"
            #print("totaltime failed")
        try:
            recipe["nutrients"] = scraper.nutrients()
        except:
            recipe["nutrients"] = "null"
            #print("nutrients failed")
        try:
            recipe["yield"] = scraper.yields()
        except:
            recipe["yield"] = "null"
            #print ("yield failed")
        if (recipe["title"] != "null"):
            recipes[i] = recipe
            print ("recipe: " + str(i) + "Succeeded")
    with open("./all_recipes_json/all_recipes_" + str(start) + "_" +str(end) + ".json", "w") as json_file:
            json_file.write(json.dumps(recipes))

increment = 499 
current = 7000
count = 0
while (count < 6 ):
    current = current + 10000 * count 
    print("ITERATTION: " + str(current))
    count = count + 1
    tasks = []
    for i in range (0,thread_count):
        p = multiprocessing.Process(target=makeRequest, args=(current, current + increment))
        current = current + increment + 1
        tasks.append(p)
        p.start()
    for task in tasks:
        task.join()
