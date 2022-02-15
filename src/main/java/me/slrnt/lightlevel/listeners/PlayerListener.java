package me.slrnt.lightlevel.listeners;

import me.slrnt.lightlevel.LightLevel;
import me.slrnt.lightlevel.commands.LightlevelCommand;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    private LightLevel plugin;
    private LightlevelCommand llc;

    private boolean isMoving;

    int radius;
    int maxHeightDiff;
    int lightlevel;

    public PlayerListener(LightLevel plugin) {
        this.plugin = plugin;
        this.llc = new LightlevelCommand(plugin);


        //TODO fix loading... load to hashmap onPlayerJoin
        radius = plugin.getConfig().getInt("radius");
        maxHeightDiff = plugin.getConfig().getInt("maxHeightDiff");
        lightlevel = plugin.getConfig().getInt("lightlevel");
    }

    @EventHandler
    private void PlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation();
        Block block = location.getBlock();

        //check if indicator is set to true
        if (!llc.getIndicator()) return;

        //check if player has moved
        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ())
            return;

        if ((player.getInventory().getItemInMainHand().getType() == Material.TORCH || player.getInventory().getItemInOffHand().getType() == Material.TORCH)) {


            //loop x,y,z with radius
            for (int i = block.getX() - radius; i <= block.getX() + radius; i++) {
                for (int j = block.getY() - maxHeightDiff; j <= block.getY() + maxHeightDiff; j++) {
                    for (int k = block.getZ() - radius; k <= block.getZ() + radius; k++) {
                        //set locations to i,j,k
                        location.setX(i);
                        location.setY(j);
                        location.setZ(k);
                        //check location in the same world as player
                        Location CheckLocation = new Location(player.getWorld(), i, j, k);

                        int blockLightLevel = CheckLocation.getBlock().getLightFromBlocks();
                        //return if light level is not correct
                        if (!(isLightlevelLower(blockLightLevel, lightlevel))) continue;
                        //return if block under is not solid
                        if (!(isBlockSolid(CheckLocation.subtract(0, 1, 0)))) continue;
                        //return if block on top is solid
                        if (isBlockSolid((CheckLocation.getBlock().getLocation().add(0, 1, 0)))) continue;

                        //center particle on a block
                        location.add(0.5, 0, 0.5);
                        //show particle
                        showRedstoneParticle(player, location, 255, 0, 0, 1, 1);
                    }
                }
            }
        }
    }

    //Check if block is solid
    private boolean isBlockSolid(Location location) {
        return location.getBlock().getType().isSolid();
    }

    //Check if block light level is lower than threshold light level
    private boolean isLightlevelLower(int blockLightLevel, int setLightLevel) {
        return blockLightLevel < setLightLevel;
    }

    //Show a particle to a player ex. showRedstoneParticle(player, location,255,0,0, 1, 1);
    private void showRedstoneParticle(Player player, Location location, int r, int g, int b, int size, int count) {
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(r, g, b), size);
        player.spawnParticle(Particle.REDSTONE, location, count, dustOptions);
    }
}
