from django.db import connection

file = open('../foods.txt', 'r')
lines = file.readlines()
cursor = connection.cursor()
for line in lines:
	item = line.strip()
	cursor.execute('INSERT INTO food_items (food_name, category) VALUES '
				   '(%s, %s);', (item, "produce"))
print("done")

