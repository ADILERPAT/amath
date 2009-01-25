package pl.edu.pw.iem.modrzejm.amath;

import java.util.HashMap;

public class CommandEvaluator {
	
	private ExpressionsDbAdapter dbAdapter;
	
	public CommandEvaluator(ExpressionsDbAdapter dbAdapter) {
		this.dbAdapter = dbAdapter;
		initKnownCommands();
	}

	public boolean evaluateCommand(String command) {

		if ("!".equals(String.valueOf(command.charAt(0)))) {
			String commandSplited[] = command.substring(1, command.length())
					.split("\\s");
			if (knownCommands.containsKey(commandSplited[0])) {
				switch (knownCommands.get(commandSplited[0])) {
				case 1: {
					if (!"".equals(commandSplited[1]))
						ecLoad(commandSplited[1]);
				}
					break;
				case 2: {
					if (!"".equals(commandSplited[1]))
						ecSession(commandSplited[1]);
				}
					break;
				case 3: {

				}
					break;
				case 4: {
					if (!"".equals(commandSplited[1]))
						if ("all".equals(commandSplited[1]))
							ecDeleteAll();
					if ("session".equals(commandSplited[1]))
						if (!"".equals(commandSplited[2]))
							ecDeleteSession(commandSplited[2]);
					if ("row".equals(commandSplited[1]))
						if (!"".equals(commandSplited[2]))
							ecDeleteRow(commandSplited[2]);
				}
					break;
				case 5: {

				}
					break;
				}
			}
			return false;
		}
		return true;
	}

	private void initKnownCommands() {
		knownCommands = new HashMap<String, Integer>();
		knownCommands.put("load", 1);
		knownCommands.put("session", 2);
		knownCommands.put("execute", 3);
		knownCommands.put("delete", 4);
	}

	private void ecSession(String s) {
		session = s;
	}

	private void ecLoad(String s) {
		session = s;
	}

	private void ecDeleteRow(String id) {
		dbAdapter.delete(Long.valueOf(id));
	}

	private void ecDeleteAll() {
		dbAdapter.deleteAll();
	}

	private void ecDeleteSession(String s) {
		dbAdapter.deleteSession(s);
	}

	private void ecExecute(String id) {

	}
}
