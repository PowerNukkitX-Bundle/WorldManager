package de.buddelbubi.commands.subcommand;

import java.io.File;
import java.util.LinkedList;
import org.iq80.leveldb.util.FileUtils;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import de.buddelbubi.WorldManager;

public class DeleteCommand extends SubCommand {

    public DeleteCommand() {
        super("delete");
        this.setAliases(new String[] {
            "delete",
            "del",
            "remove",
            "purge"
        });
    }

    @Override
    public CommandParameter[] getParameters() {

        LinkedList < CommandParameter > parameters = new LinkedList < > ();
        parameters.add(CommandParameter.newEnum(this.getName(), this.getAliases()));
        parameters.add(CommandParameter.newType("world", true, CommandParamType.STRING));
        return parameters.toArray(new CommandParameter[parameters.size()]);

    }

    @Override
    public boolean execute(CommandSender sender, String arg1, String[] args) {
        if (!sender.hasPermission("worldmanager.admin") && !sender.hasPermission("worldmanager.delete")) {

            sender.sendMessage(WorldManager.prefix + "�cYou are lacking the permission �e'worldmanager.delete'.");
            return false;

        } else {

            try {
                if (args.length == 2) {

                    if (Server.getInstance().getLevelByName(args[1]) != null) {

                        Level l = Server.getInstance().getLevelByName(args[1]);
                        String name = l.getName();
                        l.unload();
                        File regionfolder = new File(Server.getInstance().getDataPath() + "worlds/" + name + "/region");
                        File worldfolder = new File(Server.getInstance().getDataPath() + "worlds/" + name);
                        FileUtils.deleteDirectoryContents(regionfolder);
                        FileUtils.deleteDirectoryContents(worldfolder);
                        worldfolder.delete();

                        sender.sendMessage(WorldManager.prefix + "�7Deleted the world �8" + name + ".");
                    } else sender.sendMessage(WorldManager.prefix + "�cThis world is not loaded or does not exist.");

                } else sender.sendMessage(WorldManager.prefix + "�cDo /worldmanager delete [Name].");
            } catch (Exception e) {

            }
        }
        return false;
    }

}