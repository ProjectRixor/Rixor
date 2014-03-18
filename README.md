Rixor
==================

Overcast Scrimmage plugin utilising the OCN XML system!

*This plugin is a fork of ParaPGM by ParaPenguin. Thanks to him, we have the base for this plugin*

Todo List
---------
- [X] CORES!
- [ ] XML Parsing of Filters
- [ ] Filters that actually have connected events
- [ ] Add Blitz support
- [ ] Add the Rage module
- [X] Add support for Gear maps (not tested)

[PRE] Compiling your own version of ParaPGM (for developers only)
-------------------------------------------
First things first, you're going to need to download and **install** [Lombok] [6] onto Eclipse or IntelliJ, this is so that the methods work. Make sure you restart Eclipse or IntelliJ after install *cough* *cough*

Next, you're going to need to import the following jars into your project!
- [dom4j Library] [2]
- [Lombok] [6]

After that, export the plugin and you're ready to roll! Just add it to your plugins folder, and follow the setup process below.


2. Getting the libraries you need
---------------------------------
Rixor requires a few libraries, these are pretty easy to setup.
Where your craftbukkit.jar is located, make a folder called "**libs**", and add [dom4j] [2] to it.
Also, make sure to download BarAPI and place it as a plugin inside of your plugins folder. You can download BarAPI from the release section, located next to braches and commits at the top of the repo. 

3. Creating and adding to the maps repository
---------------------------------------------
Before you can go any further, you must make sure that your Maps repository contains AT LEAST 1 map, without this the plugin will have a little cry and fail to load correctly.

You can see the format of the repository [here] [4] or [here] [3]. Those steps are pretty easy and no modifications should be needed to setup your server, as the map.xml is read just like PGM would!

The maps repository should be a folder named 'maps' in the root of the server folder. (This is the same location as the server jar.)


4. To recap
-----------
Make sure to have a folder in your server root called **libs**. This is where you will put the dom4j library that you can download from the website or from any one of the release tags. After this, download BarAPI from the release tag and include it in your plugins folder as a plugin. Next, make sure to have a **maps** folder where you will put the map and the xml. Make sure the xml file is inside of the map folder, where you see the region folder and the level.dat. Oh, and don't forget the actual Rixor plugin, which you can download from the release section, as always. 

**Have fun**

[2]: http://scrimmage1.teamloading.com/dom4j.jar "dom4j"
[3]: https://maps.oc.tc/ "Overcast Maps"
[6]: http://projectlombok.org/ "Project Lombok"
