# MeeshQuest1
The project includes two self-designed data structures(BST and SGT) to store city information, including name, coordinates, radius and color. 
BST is a standard (unbalanced) binary search tree. 
SGT is a combination of extended binary tree and scapegoat tree. 

# Functions 

## Create City: This command has the following form:
<createCity name="Annapolis" y="14" x="12" radius="15" color="red"/>
This command creates a city object with the specified name, coordinates, radius, and color.
Given this command, the program will generate an XML element in the output document
that echoes back all the pertinent information. It has the following form:
<success>
  <command name="createCity"/>
  <parameters>
    <name value="Annapolis"/>
    <x value="12"/>
    <y value="14"/>
    <radius value="15"/>
    <color value="red"/>
  </parameters>
  <output/>
</success>

## List Cities: This command has the following form:
<listCities sortBy="name"/>
where the “sortBy” attribute can be either “name” or “coordnate”. This lists the cities
that exist so far. In the former case, cities are listed in ascending alphabetical order by
the city name (e.g., using java.lang.String.compareTo()), and in the latter case they are
sorted lexicographically according to their (x, y)-coordinates.

## Delete City: 
Removes a city with the specified name from both dictionaries. The command has
a single parameter, the name of the city to delete. Example:
<deleteCity name="Annapolis"/>
The command succeeds if the city is in the dictionary, and otherwise it generates the error
“cityDoesNotExist”. If successful, your program will generate an XML element in the
output document of the following form:
<success>
  <command name="deleteCity"/>
  <parameters>
    <name value="London"/>
  </parameters>
  <output>
    <cityDeleted color="yellow" name="London" radius="0" x="150" y="250"/>
  </output>
</success>

## Clear-All: 
Resets both of the dictionaries (having the effect of deleting all the cities). It has no parameters.
<clearAll/>

## Print the Binary Search Tree: 
This prints the current contents of the binary search tree in a
hierarchical (preorder) manner, so that the XML structure matches the tree’s structure (see
the example below). There are no parameters.
<printBinarySearchTree/>
