package Archiver.command;

import Archiver.ConsoleHelper;

public class ExitCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
