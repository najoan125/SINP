package hyfata.najoan.sinp;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SINPPlugin extends JavaPlugin implements Listener {
	FileConfiguration config = getConfig();
	File cfile;
	@Override
	public void onEnable() {
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[SINP]" + ChatColor.GREEN + " �÷������� Ȱ��ȭ �Ǿ����ϴ�!");
		//config file load
		config.addDefault("second", 0);
		config.addDefault("minute", 1);
		config.addDefault("hour", 0);
		config.options().copyDefaults(true);
		saveConfig();
		cfile = new File(getDataFolder(), "config.yml");
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[SINP]" + ChatColor.RED + " �÷������� ��Ȱ��ȭ �Ǿ����ϴ�!");
	}
	//RepeatingTask
    public abstract class RepeatingTask implements Runnable {
        private int taskId;
        public RepeatingTask(JavaPlugin plugin, int arg1, int arg2) {
            taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, arg1, arg2);
        }
        public void canncel() {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }
	
	//command
	int number = 5;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sinp")) {
			//help
			if (args.length == 0) {
				if(sender.hasPermission("sinp.use")) {
					sender.sendMessage("\u00a7b----------SINP ��ɾ� ���----------\n/sinp info: \u00a7f�÷����� ������ ǥ���մϴ�.\n\u00a7b/sinp set: \u00a7f���� ������ ǥ���մϴ�(seth, setm, sets�� ���� �ð�, ��, �ʸ� �����մϴ�)\n\u00a7b/sinp rl: \u00a7f�÷����� ������ ���ε��մϴ�.\n\u00a7b-------------------------------");
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c�� ��ɾ ����� ������ �����ϴ�");
				}
			}
			//info
			else if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage("\u00a7b[SINP] SINP\u00a7e�� \u00a7aShutdown If there are No Players \u00a7e�� ���ڷ�, ������ ���� �ð����� �÷��̾ ������ ������ ���ִ� �÷������Դϴ�!\n\u00a76������: ���Ҿ�");
			}
			//current SETTING!!
			else if(args[0].equalsIgnoreCase("set")) {
				if(sender.hasPermission("sinp.use")) {
					sender.sendMessage("\u00a7b[SINP] \u00a7f" + config.getInt("hour") + "�ð� " + config.getInt("minute") + "�� " + config.getInt("second") + "�� ���� ������ �÷��̾ ������ ����ǵ��� �����Ǿ� �ֽ��ϴ�!");
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c�� ��ɾ ����� ������ �����ϴ�");
				}
			}
			//RELOAD CONFIG
			else if (args[0].equalsIgnoreCase("rl")) {
				if(sender.hasPermission("sinp.use")) {
					config = YamlConfiguration.loadConfiguration(cfile);
					sender.sendMessage("\u00a7b[SINP] \u00a7a���ε� �Ǿ����ϴ�!");
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c�� ��ɾ ����� ������ �����ϴ�");
				}
			}
			//seth
			else if (args[0].equalsIgnoreCase("seth")) {
				if(sender.hasPermission("sinp.use")){
					if (args.length == 1) {
						sender.sendMessage("\u00a7b[SINP] \u00a7f������ ��ð� ���� ����� ������ ������ �� �� �����մϴ�.\n����: /sinp seth <�ð�>");
					}
					if (args.length == 2) {
						boolean integerOrNot = args[1].matches("\\d+");
						if (integerOrNot) {
							String hour = args[1];
							int inthour = Integer.parseInt(hour);
							config.set("hour", inthour);
							saveConfig();
							sender.sendMessage("\u00a7b[SINP] \u00a7f" + config.getInt("hour") + "�ð� " + config.getInt("minute") + "�� " + config.getInt("second") + "�� ���� ������ �÷��̾ ������ ����ǵ��� �����߽��ϴ�!");
						}else {
							sender.sendMessage("\u00a7b[SINP] \u00a7c�ڿ����� �Է��ϼ���!");
						}
					}
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c�� ��ɾ ����� ������ �����ϴ�");
				}
			}
			//setm
			else if (args[0].equalsIgnoreCase("setm")) {
				if (sender.hasPermission("sinp.use")) {
					if (args.length == 1) {
						sender.sendMessage("\u00a7b[SINP] \u00a7f������ �� �� ���� ����� ������ ������ �� �� �����մϴ�.\n����: /sinp setm <�ð�>");
					}
					if (args.length == 2) {
						boolean integerOrNot = args[1].matches("\\d+");
						if (integerOrNot) {
							String hour = args[1];
							int inthour = Integer.parseInt(hour);
							config.set("minute", inthour);
							saveConfig();
							sender.sendMessage("\u00a7b[SINP] \u00a7f" + config.getInt("hour") + "�ð� " + config.getInt("minute") + "�� " + config.getInt("second") + "�� ���� ������ �÷��̾ ������ ����ǵ��� �����߽��ϴ�!");
						}else {
							sender.sendMessage("\u00a7b[SINP] \u00a7c�ڿ����� �Է��ϼ���!");
						}
					}
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c�� ��ɾ ����� ������ �����ϴ�");
				}
			}
			//sets
			else if (args[0].equalsIgnoreCase("sets")) {
				if (sender.hasPermission("sinp.use")) {
					if (args.length == 1) {
						sender.sendMessage("\u00a7b[SINP] \u00a7f������ �� �� ���� ����� ������ ������ �� �� �����մϴ�.\n����: /sinp sets <�ð�>");
					}
					if (args.length == 2) {
						boolean integerOrNot = args[1].matches("\\d+");
						if (integerOrNot) {
							String hour = args[1];
							int inthour = Integer.parseInt(hour);
							config.set("second", inthour);
							saveConfig();
							sender.sendMessage("\u00a7b[SINP] \u00a7f" + config.getInt("hour") + "�ð� " + config.getInt("minute") + "�� " + config.getInt("second") + "�� ���� ������ �÷��̾ ������ ����ǵ��� �����߽��ϴ�!");
						}else {
							sender.sendMessage("\u00a7b[SINP] \u00a7c�ڿ����� �Է��ϼ���!");
						}
					}
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c�� ��ɾ ����� ������ �����ϴ�");
				}
			}
			//easterEGG
			else if (args[0].equalsIgnoreCase("easteregg")) {
				if (sender.hasPermission("sinp.use")) {
					Player p = (Player) sender;
					RepeatingTask repeatingTask = new RepeatingTask(this, 0, 20) {

						@Override
						public void run() {
							if(number != -1) {
								if(number != 0) {
									sender.sendMessage("" + number);
									number--;
								}else {
									sender.sendMessage("��");
									p.getWorld().createExplosion(p.getLocation(), 3);
									number = 5;
									canncel();
								}
							}
							
						}
						
					};
				}
			}
			//wrong arguments
			else {
				sender.sendMessage("\u00a7b[SINP] \u00a7c������ �߸��Ǿ����ϴ�! ��ɾ ����� �Է��ߴ��� Ȯ�����ּ���!");
			}
		}
		return true;
	}
	//Core Technology!!!
	int minute = config.getInt("minute") * 60;
	int hour = config.getInt("hour") * 3600;
	int stop = config.getInt("second") + minute + hour;
	@EventHandler
	public void onleave(PlayerQuitEvent event) {
		minute = config.getInt("minute") * 60;
		hour = config.getInt("hour") * 3600;
		stop = config.getInt("second") + minute + hour;
		if (Bukkit.getOnlinePlayers().size() <= 1) {
			RepeatingTask repeatingTask = new RepeatingTask(this, 0, 20) {
				
				@Override
				public void run() {
					if(stop != -1) {
						if(stop != 0) {
							if (Bukkit.getOnlinePlayers().size() == 0) {
								stop--;
							}else {
								stop = config.getInt("second") + minute + hour;
								canncel();
							}
						}else {
							stop = config.getInt("second") + minute + hour;
							Bukkit.shutdown();
						}
					}
				}
			};
		}
	}
}
