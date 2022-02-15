# Light level Spigot plugin.
## Shows all the possible spawnpoints with radius and light level settings.
<img src="https://user-images.githubusercontent.com/72470168/154069808-d2becd62-5c3d-487f-8cbd-368415d5ab30.png" width="600">

<img src="https://user-images.githubusercontent.com/72470168/154069821-613f9a61-bead-4576-984f-99359db2c237.png" width="600">

---
## Ability to change range and light levels.
<img src="https://user-images.githubusercontent.com/72470168/154069949-86b90fb4-4dc3-4b97-bac5-730e21beeb90.png" width="600">

---
## Commands with tabcomplete.

<img src="https://user-images.githubusercontent.com/72470168/154070198-907911ef-f72f-4904-9d6f-ed21f24ff1d3.png" width="600">

---

## Vertical scan limit setting demonstration
### Default is 2 blocks up and down
<img src="https://user-images.githubusercontent.com/72470168/154076844-b08aa90f-2eff-45a7-ad25-aee9d3774a3e.gif" width="600">

---
## Default config.yml for min/max settings.

```yml
# 1.17 and before make sure to set the lightlevel setting to 8. No backwards compatibility yet!
# Since 1.18 mobs can only spawn on light level 0, so by setting it to 1 it will show all the spawnpoints.
lightlevel: 1
#Minimum and maximumm light level settings for players.
minLightLevel: 0
maxLightLevel: 15

# Radius setting for players.
radius: 25
# Minimum and maximumm radius settings for players.
minRadius: 0
maxRadius: 25

# Change how many blocks is scanned up/down.
maxHeightDiff: 2

# Indicator is turned off by default. Is turned on with command.
indicator: false

```


## Permissions (Coming soon)
---
### Admin
| Permission | Description |
| :-------------: | :-------------: |
| x.y.z		| blah			|
| x.*		| blah			|
| x.y.*		| blahblah		|
---
### Player

| Permission | Description |
| :-------------: | :-------------: |
| x.y.z		| blah			|
| x.*		| blah			|
| x.y.*		| blahblah		|
---