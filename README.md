## LimitCrafting
LimitCrafting is the lightweight solution to disable crafting configurable recipe's in Minecraft.

### Configuration
```
# Set to false if you want to disable plugin
enabled: true
# Message that is sent to the player after failed attempt
message: '&cYou cannot craft items, sorry.'
# Set to true if you want to block ALL items in the game from crafting
block-all-items: false
# List of materials you would like to block from crafting if block-all-items is set to false
items:
  - 'LADDER'
  - 'TORCH'
  - 'GOLDEN_APPLE:1'
```
### Commands
NOTE: lc and restrict are command alias of limitcrafting
```
/limitcrafting reload - reloads plugin configuration
/limitcrafting reload plugin - reloads plugin
/limitcrafting add/block (id/name) - add item to block list
/limitcrafting rem/unblock (id/name) - remove item from block list
/limitcrafting list - list all items on block list (id will have block name in parenthasies)
/limitcrafting list perm - list all permissions for items on list to allow crafting
```
### Permissions
```
limitcrafting.bypass:
  default: op
  description: users with this permission will be able to craft recipes regardless if they're defined in the config.yml
limitcrafting.reload:
  default: op
  description: users with this permission will be able to reload the configuration using /limitcrafting reload
limitcrafting.reload.plugin:
  default: op
  description: users with this permission will be able to reload the plugin using /limitcrafting reload plugin
limitcrafting.block:
  default: op
  description: users with this permission will be able to add items to block list using /limitcrafting add/block
limitcrafting.unblock:
  default: op
  description: users with this permission will be able to remove items from block list using /limitcrafting rem/unblock
limitcrafting.list:
  default: op
  description: users with this permission will be able to see the block list using /limitcrafting list
limitcrafting.list.perm:
  default: op
  description: users with this permission will be able to see permissions of items in block list using /limitcrafting list perm
limitcrafting.*:
  default: op
  description: users with this permission will be able to have all permissions for the plugin and use all commands and items in block list
```
Original plugin request can be found [here](https://www.spigotmc.org/threads/how-to-ban-crafting-an-item.95560/).
