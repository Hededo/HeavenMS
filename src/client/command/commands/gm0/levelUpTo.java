/*
    This file is part of the HeavenMS MapleStory Server, commands OdinMS-based
    Copyleft (L) 2016 - 2019 RonanLana

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/*
   @Author: Jonathan Sanderson - Refactored command content into modules
*/
package client.command.commands.gm0;

import java.util.ArrayList;

import client.command.Command;
import client.MapleClient;
import client.MapleCharacter;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import client.inventory.ModifyInventory;
import client.inventory.manipulator.MapleInventoryManipulator;
import java.util.Collections;
import server.MapleItemInformationProvider;
import server.maps.MapleMap;
import tools.MaplePacketCreator;

public class levelUpTo extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        
        if (params.length < 1) {
            player.yellowMessage("Syntax: !levelupto <maxLevel>. <maxLevel> cannot be greater than 10");
            return;
        }
        
        if (player.getLevel() >= 10) {
            player.yellowMessage("this command cannot be used past level 10");
            return;
        }
        
        int maxLevel = Integer.parseInt(params[0]);
        if (maxLevel > 10) {
            player.yellowMessage("the max level cannot be greater than 10");
            return;
        }
        
        while (player.getLevel() < maxLevel) {
            player.levelUp(true);
        }
    }
}
