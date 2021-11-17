from ._abstract import AbstractScraper
from ._utils import normalize_string


class FoodNetwork(AbstractScraper):

    @classmethod
    def host(self):
        return 'foodnetwork.com'

    def title(self):
        return self.soup.find('h1').find('span').get_text()

    def total_time(self):
        return self.soup.find(
            'dd', {'class': 'o-RecipeInfo__a-Description--Total'}).get_text()

    def ingredients(self):
        ingredients_html = self.soup.findAll('li', {'class': 'o-Ingredients__a-ListItem'})

        return [
            normalize_string(ingredient.get_text())
            for ingredient in ingredients_html
        ]

    def instructions(self):
        instructions_html = self.soup.find('section', {'class': 'o-Method'}).findAll('p')

        return '\n'.join([
            normalize_string(instruction.get_text())
            for instruction in instructions_html
        ])

    def picture(self):
        recipe_photo = self.soup.find('img', {'class': 'o-AssetMultiMedia__a-Image'})
        return recipe_photo.attrs['src']
