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
package client.command.commands.gm2;

import client.command.Command;
import client.MapleClient;
import client.MapleCharacter;
import config.YamlConfig;

public class LeveCharacterlCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        if (params.length < 2) {
            player.yellowMessage("Syntax: !level <playername> <newlevel>");
            return;
        }
        
        MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(params[0]);
        
        
        victim.loseExp(victim.getExp(), false, false);
        victim.setLevel(Math.min(Integer.parseInt(params[1]), victim.getMaxClassLevel()) - 1);

        victim.resetPlayerRates();
        if (YamlConfig.config.server.USE_ADD_RATES_BY_LEVEL) victim.setPlayerRates();
        victim.setWorldRates();

        victim.levelUp(false);
    }
}
