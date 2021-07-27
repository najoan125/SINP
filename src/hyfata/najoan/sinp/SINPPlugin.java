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
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[SINP]" + ChatColor.GREEN + " 플러그인이 활성화 되었습니다!");
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
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[SINP]" + ChatColor.RED + " 플러그인이 비활성화 되었습니다!");
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
					sender.sendMessage("\u00a7b----------SINP 명령어 목록----------\n/sinp info: \u00a7f플러그인 정보를 표시합니다.\n\u00a7b/sinp set: \u00a7f현재 설정을 표시합니다(seth, setm, sets는 각각 시간, 분, 초를 설정합니다)\n\u00a7b/sinp rl: \u00a7f플러그인 설정을 리로드합니다.\n\u00a7b-------------------------------");
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c이 명령어를 사용할 권한이 없습니다");
				}
			}
			//info
			else if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage("\u00a7b[SINP] SINP\u00a7e는 \u00a7aShutdown If there are No Players \u00a7e의 약자로, 서버에 일정 시간동안 플레이어가 없으면 서버를 꺼주는 플러그인입니다!\n\u00a76제작자: 나죠안");
			}
			//current SETTING!!
			else if(args[0].equalsIgnoreCase("set")) {
				if(sender.hasPermission("sinp.use")) {
					sender.sendMessage("\u00a7b[SINP] \u00a7f" + config.getInt("hour") + "시간 " + config.getInt("minute") + "분 " + config.getInt("second") + "초 동안 서버에 플레이어가 없으면 종료되도록 설정되어 있습니다!");
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c이 명령어를 사용할 권한이 없습니다");
				}
			}
			//RELOAD CONFIG
			else if (args[0].equalsIgnoreCase("rl")) {
				if(sender.hasPermission("sinp.use")) {
					config = YamlConfiguration.loadConfiguration(cfile);
					sender.sendMessage("\u00a7b[SINP] \u00a7a리로드 되었습니다!");
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c이 명령어를 사용할 권한이 없습니다");
				}
			}
			//seth
			else if (args[0].equalsIgnoreCase("seth")) {
				if(sender.hasPermission("sinp.use")){
					if (args.length == 1) {
						sender.sendMessage("\u00a7b[SINP] \u00a7f서버에 몇시간 동안 사람이 없으면 서버를 끌 지 설정합니다.\n구문: /sinp seth <시간>");
					}
					if (args.length == 2) {
						boolean integerOrNot = args[1].matches("\\d+");
						if (integerOrNot) {
							String hour = args[1];
							int inthour = Integer.parseInt(hour);
							config.set("hour", inthour);
							saveConfig();
							sender.sendMessage("\u00a7b[SINP] \u00a7f" + config.getInt("hour") + "시간 " + config.getInt("minute") + "분 " + config.getInt("second") + "초 동안 서버에 플레이어가 없으면 종료되도록 설정했습니다!");
						}else {
							sender.sendMessage("\u00a7b[SINP] \u00a7c자연수를 입력하세요!");
						}
					}
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c이 명령어를 사용할 권한이 없습니다");
				}
			}
			//setm
			else if (args[0].equalsIgnoreCase("setm")) {
				if (sender.hasPermission("sinp.use")) {
					if (args.length == 1) {
						sender.sendMessage("\u00a7b[SINP] \u00a7f서버에 몇 분 동안 사람이 없으면 서버를 끌 지 설정합니다.\n구문: /sinp setm <시간>");
					}
					if (args.length == 2) {
						boolean integerOrNot = args[1].matches("\\d+");
						if (integerOrNot) {
							String hour = args[1];
							int inthour = Integer.parseInt(hour);
							config.set("minute", inthour);
							saveConfig();
							sender.sendMessage("\u00a7b[SINP] \u00a7f" + config.getInt("hour") + "시간 " + config.getInt("minute") + "분 " + config.getInt("second") + "초 동안 서버에 플레이어가 없으면 종료되도록 설정했습니다!");
						}else {
							sender.sendMessage("\u00a7b[SINP] \u00a7c자연수를 입력하세요!");
						}
					}
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c이 명령어를 사용할 권한이 없습니다");
				}
			}
			//sets
			else if (args[0].equalsIgnoreCase("sets")) {
				if (sender.hasPermission("sinp.use")) {
					if (args.length == 1) {
						sender.sendMessage("\u00a7b[SINP] \u00a7f서버에 몇 초 동안 사람이 없으면 서버를 끌 지 설정합니다.\n구문: /sinp sets <시간>");
					}
					if (args.length == 2) {
						boolean integerOrNot = args[1].matches("\\d+");
						if (integerOrNot) {
							String hour = args[1];
							int inthour = Integer.parseInt(hour);
							config.set("second", inthour);
							saveConfig();
							sender.sendMessage("\u00a7b[SINP] \u00a7f" + config.getInt("hour") + "시간 " + config.getInt("minute") + "분 " + config.getInt("second") + "초 동안 서버에 플레이어가 없으면 종료되도록 설정했습니다!");
						}else {
							sender.sendMessage("\u00a7b[SINP] \u00a7c자연수를 입력하세요!");
						}
					}
				}else {
					sender.sendMessage("\u00a7b[SINP] \u00a7c이 명령어를 사용할 권한이 없습니다");
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
									sender.sendMessage("펑");
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
				sender.sendMessage("\u00a7b[SINP] \u00a7c구문이 잘못되었습니다! 명령어를 제대로 입력했는지 확인해주세요!");
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
