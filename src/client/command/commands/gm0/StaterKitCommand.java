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
   @Author: Arthur L - Refactored command content into modules
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
import tools.MaplePacketCreator;

public class StaterKitCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();

        int gotStarterKit = player.getGotStarterKit();
        if (gotStarterKit > 0) {
            return;
        }

        this.modifyPlayerStats(player);
        this.addEquips(c, player);
        this.addUseItems(c, player);
        
        player.setGotStarterKit(1);
        player.message("Starter Kit Received");
    }

    /** modifies the player stats */
    private void modifyPlayerStats(MapleCharacter player) {
        player.getInventory(MapleInventoryType.EQUIP).setSlotLimit(64);
        player.getInventory(MapleInventoryType.USE).setSlotLimit(64);
        player.getInventory(MapleInventoryType.SETUP).setSlotLimit(64);
        player.getInventory(MapleInventoryType.ETC).setSlotLimit(64);
    }

    /** add equips */
    private void addEquips(MapleClient c, MapleCharacter player) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();

        ArrayList<Equip> equips = new ArrayList<Equip>();

        Equip whiteMapleBandana = (Equip)ii.getEquipById(1002515);
        whiteMapleBandana.setDex((short)2);
        whiteMapleBandana.setInt((short)2);
        whiteMapleBandana.setStr((short)2);
        whiteMapleBandana.setLuk((short)2);
        whiteMapleBandana.setHp((short)10);
        whiteMapleBandana.setMp((short)10);
        whiteMapleBandana.setWdef((short)10);
        whiteMapleBandana.setMdef((short)10);
        whiteMapleBandana.setItemLevel((byte)3);
        equips.add(whiteMapleBandana);
        
        Equip sandals = (Equip)ii.getEquipById(1072005);
        sandals.setJump((short)3);
        sandals.setSpeed((short)5);
        sandals.setWdef((short)5);
        sandals.setMdef((short)5);
        equips.add(sandals);

        Equip wings = (Equip)ii.getEquipById(1102236);
        wings.setJump((short)3);
        wings.setSpeed((short)3);
        equips.add(wings);

        Equip ring = (Equip)ii.getEquipById(1112000);
        ring.setDex((short)1);
        ring.setInt((short)1);
        ring.setStr((short)1);
        ring.setLuk((short)1);
        equips.add(ring);

        Equip workgloves = (Equip)ii.getEquipById(1082002);
        workgloves.setAcc((short)5);
        workgloves.setWatk((short)1);
        workgloves.setMatk((short)1);
        equips.add(workgloves);

        Equip mitten = (Equip)ii.getEquipById(1472063);
        mitten.setJump((short)3);
        mitten.setSpeed((short)5);
        mitten.setWatk((short)20);
        equips.add(mitten);
        
        Equip snowboard = (Equip)ii.getEquipById(1442012);
        snowboard.setStr((short)1);
        snowboard.setSpeed((short)1);
        snowboard.setJump((short)1);
        snowboard.setWatk((short)32);
        equips.add(snowboard);
        
        Equip umbrella = (Equip)ii.getEquipById(1302017);
        umbrella.setStr((short)1);
        umbrella.setInt((short)1);
        umbrella.setSpeed((short)1);
        umbrella.setMatk((short)19);
        umbrella.setWatk((short)19);
        equips.add(umbrella);

        MapleInventory equipInv = player.getInventory(MapleInventoryType.EQUIP);
        
        equipInv.lockInventory();

        for (Item gift : equips) {
            this.addItemToInventory(c, player, equipInv, gift);
        }
        
        equipInv.unlockInventory();
    }

    /** add item to inventory */
    private void addItemToInventory(MapleClient c, MapleCharacter player, MapleInventory inv, Item item) {
        item.setFlag((short) 0);
        item.setExpiration((short) -1);
        c.announce(MaplePacketCreator.modifyInventory(true, Collections.singletonList(new ModifyInventory(0, item))));
        
        short newSlot = inv.addItem(item);
        if (newSlot == -1) {
            c.announce(MaplePacketCreator.getInventoryFull());
            c.announce(MaplePacketCreator.getShowInventoryFull());
            return;
        }
        
        c.announce(MaplePacketCreator.modifyInventory(true, Collections.singletonList(new ModifyInventory(0, item))));
        if(MapleInventoryManipulator.isSandboxItem(item)) {
            player.setHasSandboxItem();
        }
    }

    /** add use items */
    private void addUseItems(MapleClient c, MapleCharacter player) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();

        ArrayList<Item> useItems = new ArrayList<Item>();
        
        Item redArrows = new Item(2060003, (short)0, (short)1000, (short)-1);
        useItems.add(redArrows);
        
        Item mightyBullet = new Item(2330002, (short)0, (short)1600, (short)-1);
        useItems.add(mightyBullet);
        
        Item paperPlanes = new Item(2070012, (short)0, (short)1000, (short)-1);
        useItems.add(paperPlanes);
        
        Item adminCongrats = new Item(2022118, (short)0, (short)1, (short)-1);
        useItems.add(adminCongrats);
        
        Item mushmomBag = new Item(2101000, (short)0, (short)3, (short)-1);
        useItems.add(mushmomBag);

        Item meat = new Item(2010001, (short)0, (short)100, (short)-1);
        useItems.add(meat);

        Item lemons = new Item(2010004, (short)0, (short)100, (short)-1);
        useItems.add(lemons);

        MapleInventory inv = player.getInventory(MapleInventoryType.USE);
        
        inv.lockInventory();

        for (Item gift : useItems) {
            this.addItemToInventory(c, player, inv, gift);
        }
        
        inv.unlockInventory();
    }
}
