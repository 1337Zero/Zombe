---
author:
- Julius Schönhut
date: Januar 2017
title: 'Zombe-Liteloader Fork and his usage'
---

Summary: This file is about, Julius Schönhut’s (aka Zero’s) Fork of the
Zombe-Modpack. How to configure it, use it commands and functions.It
hasn’t any relationship between the original mod and any fork of it, but
it contains code of the original one.It wont affect minecraft’s code,
only works with minecraft’s apis.So basicly it should be compatible with
every mod compatible with Liteloader

Installation
============

1.  Download the right version of LiteLoader from
    [http://www.liteloader.com/download](http://www.liteloader.com/download)

2.  Download the right version of this mod from
    [https://www.github.com/1337Zero/Zombe/](https://www.github.com/1337Zero/Zombe/).

3.  Start the installer of LiteLoader, and wait it to be finished, make
    sure the right minecraft version is installed.

4.  Copy the zombe&lt;xxx&gt;.litemod file into your mods folder. For
    Windows this will be in %appdata%/roaming/.minecraft. On Linux your
    mods folder will be in /home/.minecraft. If there is no mods folder
    create one and put the .litemod in there.

If u want to use Optifine with LiteLoader, there is an easy way to do
this. Go to [https://www.optifine.net/downloads](https://www.optifine.net/downloads) and
download the right optifine version for your minecraft version. Put the
Optifine.jar into your mods/&lt;mcversion&gt; folder. LiteLoader will
load Optifine for you.

How this Mod was created
========================

This mod was created, because the original mod hasn’t been update for a
long time, also that mod wasn’t (fully) compatible with Optifine. The
original mod itself was hard to ’fix’ because all the code was written
into a single file. Most mods works in MP because they are based on the
copy of the world in your client.

Mods and their config
=====================

The following mods are currently in the modpack. Most settings are
accessible in game via F7 button, and under the right mod sub menu,but
some settings are only accessible over the config.cfg.

FlyMod
------

This mod works in MP if not blocked. This mod adds the ability to move
faster up and down. It also adds an option to fix the swimming effect of
creative fly. If enabled, u can stay in the air without falling down.

### FlyMod Config

Thoose values are configurable in the
$minecraft/mod/lite\_zombe/config.cfg$ File

$Fly-Mod.fly-enabled:false$, sets if the mod is
enabled.$Fly-Mod.fly-Pos:UP\_LEFT$, sets the positions of the info of
the mod’s speed.$Fly-Mod.Key-Down:16$, sets the key which enables the
mod.$Fly-Mod.showfly:true$, set this to true if you want to see the
information$Fly-Mod.flyspeed:1.0$, sets the speed you are flying down ur
up.$Fly-Mod.Key-Up:57$, sets the key which let you fly
up.$Fly-Mod.maxflyspeed:10$, sets the max speed
value$Fly-Mod.nerfcreaetivefly:false$, set this to true if u want the
creative-swim-fix$Fly-Mod.toggle-fly:false$, set this to true if the mod
ony works with two buttons$Fly-Mod.key-Toggle-fly:46$, sets the key for
toggle fly$Fly-Mod.ignoreshift:false$ will half the speed flying down if
shift is pressed

InfoMod
-------

This mod works in MP. This mod shows some information on the in game
screen, so u don’t have to open the debug menu of minecraft.

### InfoMod Config

$Info-Mod.Dir-POS:UP_RIGHT$, sets the position of the directory
info$Info-Mod.showdir:false$, set this to true if you want to see your
direction$Info-Mod.Worldage-Pos:UP_LEFT$, sets the position of the
worldage info$Info-Mod.showworldage:false$, set this to true if you want
to see the worldage$Info-Mod.showFPS:true$, set this to true if you want
to see your fps$Info-Mod.FPS-Pos:UP_CENTER$, sets the position of the
fps info$Info-Mod.showcoor:true$, set this to true if you want to see
your coordinates$Info-Mod.Coor-Pos:UP_RIGHT$, sets the position of the
coordination info

LightMod
--------

This mod works in MP. This mod brightens up your day ... or the night.
It sets the gamma value of minecraft to 100, which means no shadows or
darkness any more.

### LightMod Config

$Light-Mod.Toggle-Light:38$, sets the key for enabling this
mod$Light-Mod.lightmod-enabled:false$, set this to true if you want to
enable the mod

MobHighlighter
--------------

This mod works in MP. This mod marks every configured entity, in your
loaded worlds entity-list. Colour and what entities are marked is
configurable.

### MobHighlighter Config

$MobHighlighter.info-Pos:UP_LEFT$, set the position of the info, how
much entities are marked$MobHighlighter.enabled:false$, set this to true
if u want to enable this mod$MobHighlighter.Toggle-Mobhighlighter:55$,
set the key which enable this mod$MobHighlighter.showinfo:false$, set
this to true if u want to see the counted marked entities

### MobHighlighter Entity-Highlight config

This config is called Mobhighlighter.cfg. Colour values are given in a
float value(0 means 0, and 1.0 means 255), with Red/Green/Blue and alpha
values. This side provides some colour examples.
<http://prideout.net/archive/colors.php>As you should see, the name of
an entity is used to find it, entities with a space in its name (aka
Polar Bear) are replaced with ’\_’. If u want to add an new entry add
the name of the entity to the Moblist. After that u need to provide a
colour which it should be marked. The format for setting the colour of
an entity is Color.&lt;name&gt;:Rf,Gf,Bf,Af

$Moblist:Polar\_Bear,Stray,Llama,Evoker,Vindicator,Vex,Chicken,Cow,\newline Horse,Ocelot,Pig,Sheep,Bat,Squid,Villager,Rabbit,Creeper,Skeleton,\newline Zombie,Spider,Enderman,PigZombie,Cavespider,Silverfish,Lohe,Magmacube,\newline Witch,Endermite,Guardian,Wolf,Item,Player,Mushroomcow,Slime,Ghast,\newline Snowman,Giant,Irongolem,Enderdragon,Boat,Minecart,Spawnerminecart,\newline Chestminecart,Tntminecart,Furnaceminecart,Xp,Wither$

$Color.Chicken:0.0F,1.0F,0.0F,1.0F$$Color.Cow:0.0F,1.0F,0.0F,1.0F$$Color.Horse:0.0F,1.0F,0.0F,1.0F$$Color.Ocelot:0.0F,1.0F,0.0F,1.0F$$Color.Pig:0.0F,1.0F,0.0F,1.0F$$Color.Sheep:0.0F,1.0F,0.0F,1.0F$$Color.Bat:0.0F,1.0F,0.0F,1.0F$$Color.Mushroomcow:0.0F,1.0F,0.0F,1.0F$$Color.Squid:0.0F,1.0F,0.0F,1.0F$$Color.Villager:0.0F,1.0F,0.0F,1.0F$$Color.Rabbit:0.0F,1.0F,0.0F,1.0F$$Color.Creeper:1.0F,0.0F,0.0F,1.0F$$Color.Skeleton:1.0F,0.0F,0.0F,1.0F$$Color.Zombie:1.0F,0.0F,0.0F,1.0F$$Color.Spider:1.0F,0.0F,0.0F,1.0F$$Color.Enderman:1.0F,0.0F,0.0F,1.0F$$Color.PigZombie:1.0F,0.4F,0.1F,1.0F$$Color.Cavespider:1.0F,0.0F,0.0F,1.0F$$Color.Silverfish:1.0F,0.0F,0.0F,1.0F$$Color.Blaze:1.0F,0.0F,0.0F,1.0F$$Color.Magmacube:1.0F,0.0F,0.0F,1.0F$$Color.Witch:1.0F,0.0F,0.0F,1.0F$$Color.Endermite:1.0F,0.0F,0.0F,1.0F$$Color.Guardian:1.0F,0.0F,0.0F,1.0F$$Color.Wither:1.0F,0.0F,0.0F,1.0F$$Color.Wolf:1.0F,0.0F,0.0F,1.0F$$Color.Item:1.0F,0.0F,0.0F,1.0F$$Color.Xp:1.0F,0.0F,0.0F,1.0F$$Color.Player:1.0F,1.0F,1.0F,1.0F$$Color.Slime:1.0F,0.0F,0.0F,1.0F$$Color.Ghast:1.0F,0.0F,0.0F,1.0F$$Color.Giant:1.0F,0.0F,0.0F,1.0F$$Color.Snowman:0.0F,1.0F,0.0F,1.0F$$Color.Boat:0.0F,1.0F,0.0F,1.0F$$Color.Minecart:0.0F,1.0F,0.0F,1.0F$$Color.Spawnerminecart:0.0F,1.0F,0.0F,1.0F$$Color.Tntminecart:0.0F,1.0F,0.0F,1.0F$$Color.Hopperminecart:0.0F,1.0F,0.0F,1.0F$$Color.Furnaceminecart:0.0F,1.0F,0.0F,1.0F$$Color.Chestminecart:0.0F,1.0F,0.0F,1.0F$$Color.Irongolem:0.0F,1.0F,0.0F,1.0F$$Color.Enderdragon:1.0F,0.0F,0.0F,1.0F$$Color.Stray:1.0F,0.0F,0.0F,1.0F$$Color.Llama:0.0F,1.0F,0.0F,1.0F$$Color.Vex:1.0F,0.0F,0.0F,1.0F$$Color.Vindicator:1.0F,0.0F,0.0F,1.0F$$Color.Evoker:1.0F,0.0F,0.0F,1.0F$$Color.Polar_Bear:0.0F,1.0F,0.0F,1.0F"$

OreHighlighter
--------------

This mod works in MP. OreHighlighter or X-Ray is a mod which marks
specific blocks in the world. You can configure which blocks and with
what colour in the orehighlighter.cfg. The format looks like the format
for mobhighlighter. It has a intelligent “dynamic selection” which
ignores blocks which aren’t visible for u. This results in more power,
and the view distance of the mod is a bit longer. If the amount of marks
slows your computer you can enable “fast marks” which uses 2 instead of
4 lines per block, cutting the time calculating marks by half.

### OreHighlighter Config

$OreHighlighter.radius:2$, this multiplyed with 10 is the radius the mod
will search for blocks.$OreHighlighter.enabled:false$, set this to true
if you want to enable the mod.$OreHighlighter.showinfo:false$, set this
to true if u want to see the amount of marked
blocks.$OreHighlighter.Toggle-OreHighlighter:27$, set the key for
enabling this mod.$OreHighlighter.info-Pos:UP_LEFT$, set the position
for the info line of this mod.

### OreHighlighter Block-Highlight Config

This config is called Orehighlighter.cfg. Colour values are given in a
float value(0 means 0, and 1.0 means 255), with Red/Green/Blue and alpha
values. This side provides some colour examples.
<http://prideout.net/archive/colors.php>A normal block is give by its
id, but if u want a specific block with a sub-id,you can add it with
&lt;blockid-subid&gt;. If u want to add an new entry add the id of the
block to the Blocklist. After that u need to provide a colour which it
should be marked. The format for setting the colour of a block is
Color.&lt;name&gt;:Rf,Gf,Bf,Af
$Blocklist:14,15,16,21,56,73,74,129,153,35-5$

$Color.14:1.0F, 0.84F, 0.0F, 1.0F$$Color.15:0.82F, 0.41F, 0.11F, 1.0F$$Color.16:0.0F, 0.0F, 0.0F, 1.0F$$Color.21:0.46F, 0.53F, 0.6F, 1.0F$$Color.56:0.0F, 0.74F, 1.0F, 1.0F$$Color.73:1.0F, 0.0F, 0.0F, 1.0F$$Color.74:1.0F, 0.0F, 0.0F, 1.0F$$Color.129:0.26F, 0.8F, 0.5F, 1.0F$$Color.153:0.78F, 0.78F, 0.78F, 1.0F$$Color.35-5:1.0F, 0.78F, 0.78F, 0.5F$

PathMod
-------

This mod works in MP. Do you ever got stuck in a cave running in a
circle? This mod can help you. It adds marks which, if u enable it, are
visible through walls. You can see where the way to the surface is, or
where you already mined in a cave.

### PathMod Config

$PathMod.showinfo:false$, set this to true if u want to see the amount
of marks.$PathMod.info-Pos:UP\_LEFT$, sets the position of info
line.$PathMod.seethroughwall:true$, set this to true if u want to see
marks through blocks.$PathMod.mark.r:1.0F$, set the red value of the
markers Colour.$PathMod.mark.g:0.0F$, set the red value of the markers
Colour.$PathMod.mark.b:1.0F$, set the red value of the markers
Colour.$PathMod.mark.alpha:1.0F$, set the red value of the markers
Colour.$PathMod.Toggle-PathMod:11$, set the key which enable the
mod.$PathMod.enabled:false$, set this to true if you want to enable this
mod.

RangeMod
--------

This mod gives you the ability to extend your range. You can pick,break
and place blocks over a chosen distance. You can choose if u want, if u
place blocks remove them from your inventory.If u break blocks you can
get them into your inventory.Picking up like in creative mod with extra
range is possible too. For people (like me) who aren’t that precisely
with the marker of minecraft, you can enable an extra mark with marks
the block you will interact with.

### RangeMod Config

$RangeMod.DropBlock:false$, set this to true if u want that destroyed
blocks should drop their items.$RangeMod.showinfo:false$, set this to
true if u want to see your range setting in the
info-line.$RangeMod.reachpickonkey:55$, set the key for enable
range-pickup blocks.$RangeMod.destroydelay:125$, set the delay for
destroying blocks,be careful with changes.$RangeMod.reachplace:true$,
set this to true if you want to place blocks over
distances.$RangeMod.info-Pos:UP_LEFT$, set the position of the
info-line.$RangeMod.placedelay:125$, set the delay for placing blocks,
be careful with changes.$RangeMod.MarkBlock:false$, set this to true if
u want a mark for the block interacting with.$RangeMod.Range:16$, set
the range you want to interact with
blocks.$RangeMod.reachplaceonkey:52$, set the key to enable placing
blocks over distance.$RangeMod.reachbreakonkey:12$, set the key to
enable breaking blocks over distance.$RangeMod.pickdelay:250$, set the
delay for picking up blocks, be careful with
changes.$RangeMod.addtoinventory:true$, set this to true if u want to
get the block.$RangeMod.reachpick:true$, set this to true if u want to
enable distance picking of a block.$RangeMod.reachbreak:true$, set this
to true to enable distance block
breaking.$RangeMod.removefrominventory:true$, set this to true if u want
to remove placed blocks from your inventory.

RecipeMod
---------

Did you ever opened a chest and thought “what the heck should i do with
this stuff”. This mod can help you in this situation. Just put some
items into a working bench and see what u can craft out of it. If u
enable the setting it will display the recipe for this item too. But
that is not all, do you ever thought diamonds are stardust and dirt is
it too ? Yes ? than custom recipes can help you. Add new recipes like 3
dirt creates 1 diamond to your recipe list.

### RecipeMod Config

$RecipeMod.showCraftingPattern:false$, if enabled this will show you the
crafting recipe for the given item.$RecipeMod.loadCustomRecipes:false$,
if set to true customrecipes will be loaded and put into minecraft
recipe list.$RecipeMod.Enabled:false$, set this to true if u want to
enable this mod.

### RecipeMod custom-config

This is the File for Custom ItemsPlease use this Format to create
Custom-Items$\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\newline
Example1:\newline
0\tilde{~}46:0:1;XXX,XXX,X\#X;X=3:0,\#=85:0\newline$ This creates a new
TNT Recept, for crafting you need: XXX in the first line,XXX in the
second line and X\#X in the last line of the workbenchX will be replaced
with 3:0,\# will be replaced with
85:0Example2:$1\tilde{~}264:0:64;X,X,\#;X=3:0,\#=1:0\newline$ \#This
creates a new Diamond Recept, for crafting you need: X in the first
line,X in the second line and \# in the last line of the workbenchX will
be replaced with 3:0,\# will be replaced with 1:0$
\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\newline$
\#So basicly it
is:\#customrecipe-id$\tilde{~}$itemid:itemsubid:amount;pattern for the
first line,pattern for the second line,pattern for the last line,item
you need to craft that recipe with identifier.

SpeedMod
--------

This mod changes the speed you are running around, if not blocked it
should work in MP. You can choose between 0 which looks like normal
human walking (slower than minecraft walking) and 10 which is very fast
running around, other player maybe guess it’s some sort of teleporting.

### SpeedMod Config

$Speed-Mod.speed-Pos:UP_LEFT$, set the position of the info how fast the
speed value is.$Speed-Mod.maxspeed:10$, set the max speed of this
mod.$Speed-Mod.Toggle-speed:21$, set the key to enable the
speedmod.$Speed-Mod.enabled:false$, set this to true if u want to enable
the mod.$Speed-Mod.togglespeed:false$, set this to true if u want to use
2 buttons instead of one.$Speed-Mod.showspeed:true$, set this to true if
u want to see the speed value.$Speed-Mod.speed:1.0$, set the speed
value.

TimeMod
-------

This mod works in MP, but only affects your client. Do you ever thought
“How does my house looks in the night” ?, now u can look at it without
waiting for the night. You can freeze the time, add or subtract time or
speed it up.

### TimeMod Config

$Time-Mod.Key-subtime:74$, set the key for subtracting
time.$Time-Mod.showTimeinfo:false$, set this to true if u want to see
how much u changed the time.$Time-Mod.Key-freezetime:55$, set the key to
freeze the time.$Time-Mod.showtimepos:UP_CENTER$,set the position of the
info-line.$Time-Mod.timetoadd:0$, set the value which will be added to
your time.$Time-Mod.time-multiplier:0$, set the multiplier which speeds
up your time.$Time-Mod.Time-Mod-enabled:false$,set this to true if u
want to enable this mod.$Time-Mod.Key-addtime:78$,set the key to add
time.$Time-Mod.time-freezed:false$,set this to true if you want the time
to freeze.

Commands
========

Commands are only functional in single player. There are some easy
command which are save to use, but also other which can crash ur client,
depending on the work it will start. To mark a command u wont use the
’/’ character, instead u use an ’\#’.

Command ’near’
--------------

The syntax of this command is: \#nearThis command list the name, and a
summary of all Entities loaded in ur client. Items which lay on the
ground will be named special (item.tile.&lt;name&gt;), but that
shouldn’t be a big problem for the usage.

Command ’killall’
-----------------

The syntax of this command is: \#killallThis command will kill entities
which are loaded by your client. Be careful where you are using it.

Command ’shift’
---------------

The Syntax of this command is: \#shift &lt;x Blocks&gt;This Command
moves your selection x Blocks in the directory you are looking.

Command ’expand’
----------------

The Syntax of this command is: \#expand &lt;x Blocks&gt;This Command
expands your selection x Blocks in the directory you are looking.

Command ’set’
-------------

The syntax of this command is: \#set &lt;Block ID&gt; &lt;sub-id&gt;This
command will start the replacing of your selection with the give block
id and his sub-id.Be careful, too much work at once could crash ur
client. You can prevent it by giving your Minecraft and your Java more
ram.

Command ’replace’
-----------------

The syntax of this command is: \#replace &lt;Block ID&gt; &lt;sub-id&gt;
&lt;Block ID&gt; &lt;sub-id&gt; This command will start the replacing of
your selection with the right block and sub-id with the give block id
and his sub-id.Be careful, too much work at once could crash ur client.

Command ’move’
--------------

The syntax of this command is: \#move &lt;x BlocksThis command will
start the moving of the Blocks in your selection by the given amount of
blocks.The directory is calculated by your view. Be aware that pushing
blocks over the loaded world could cause serious trouble. Once again,
this can crash ur client, if not enough memory is given.

Command ’copy’
--------------

The syntax of this command is: \#copyThis command will store a copy of
your selection in your ram. It is used later to write it to a .schematic
file.

Command ’paste’
---------------

The syntax of this command is: \#pasteThis command will paste a loaded
.schematic file to the games world. Be aware that large buildings can
crash your client, if not enough memory could be used. For large
buildings, it could happen that there are “unloaded” chunks, it is easy
to fix it reload the world you are playing in. If u wish to paste a copy
of your selection, you can save it first with the ’save’ command, and
load it from a schematic file.

Command ’save’
--------------

The syntax of this command is: \#save &lt;name&gt;This commands writes
your selection into an .schematic file. It should be compatible with
WorldEdit.Be aware that the schematic folder in your
$minecraft/mod/lite\_zombe/$ folder wont be created automaticly, and it
won’t work until this folder exists.You can download schematics which
are created with WorldEdit and store them into that folder to load them
later.

Command ’load’
--------------

The syntax of this command is: \#load &lt;name&gt;This command loads a
.schematic file from your $minecraft/mod/lite\_zombe/schematic$ folder.
You can paste it with the ’paste’ command.

I don’t like your work and want to change it!
=============================================

Fell free to fork my work on github
[http://www.github.com/1337Zero/Zombe](http://www.github.com/1337Zero/Zombe)!

I don’t trust you and want to build my own version
==================================================

LiteLoader uses Grandle to build and set-up everything. To build this
mod u have to follow this steps:

1.  Download Eclipse Development Environment, make sure u get a version
    with Git and Grandle.

2.  For decompile and compile minecraft’s code, you need a working Java
    Development Kit installation. You will get this via
    [https://www.oracle.com](https://www.oracle.com)

3.  Open the ’Git repositories’ view, in the upper right corner there is
    a search bar where you can type and search it.

4.  Click on ’Clone Repository’ and paste this link
    [https://github.com/1337Zero/Zombe.git]

5.  now you need the LiteLoader Source, clone LiteLoader’s repository.

6.  Import both LiteLoader and Zombe modpack (from git).

7.  Righclick on Zombe and do Gradle-&gt;Refresh Gradle Projects.

8.  Under Gradle Tasks, you have to start this task to set everything
    up: setupdecompworkspace and eclipse.

9.  After everything worked, you can test it with ’runClient’ task, or
    if you want to build it start the build task.

10. The freshly created .litemod will be in the mods saved place under
    build/libs

Donate me a Pizza,a glass of cola or a cookie
=============================================

If you like my work, you can donate me some money via
paypal[https://www.paypal.com/cgi-bin/webscr?gameCategorySlug=bukkit-plugins&projectID=65875&cn=Add+special+instructions+to+the+addon+author()&business=julius.schoenhut%40gmail.com&bn=PP-DonationsBF:btn\_donateCC\_LG.gif:NonHosted&lc=US&item\_name=Donation for your Mod&cmd=\_donations&rm=1&no\_shipping=1&currency\_code=USD](https://www.paypal.com/cgi-bin/webscr?gameCategorySlug=bukkit-plugins&projectID=65875&cn=Add+special+instructions+to+the+addon+author()&business=julius.schoenhut%40gmail.com&bn=PP-DonationsBF:btn_donateCC_LG.gif:NonHosted&lc=US&item_name=Donation for your Mod&cmd=_donations&rm=1&no_shipping=1&currency_code=USD)
this link is untested, but you can send me money to that email as a
friend :)
