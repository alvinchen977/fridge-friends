from recipe_scrapers import scrape_me
from time import sleep 
import json
import multiprocessing 
import requests
import base64
base_url = "https://www.allrecipes.com/recipe/"
wait_time = .001
thread_count = 12
def makeRequest(thread_number):
    recipes = {} 
    start_loop = 7000 + 10000 * thread_number
    end_loop = start_loop + 1000
    for i in range(start_loop,end_loop):
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
            recipe["image"] = base64.b64encode(requests.get(scraper.image()).content)
            sleep(.05)
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
        with open("./all_recipes_json/all_recipes_" + str(thread_number) + ".json", "w") as json_file:
            json_file.write(json.dumps(recipes))

for i in range (0,thread_count):
    multiprocessing.Process(target=makeRequest, args=(i,)).start()
