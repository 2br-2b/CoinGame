package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.Main;
import src.PointsAdder;

public class Pay_Command extends Command {

	public Pay_Command() {
		super();
		super.name = "pay";
		super.help = "pay another user";
		super.arguments = "@mention amount";

	}

	@Override
	protected void execute(CommandEvent event) {
		String[] mList = event.getArgs().split(" ");
		try {
			try {
				try {
					if (!Main.isGood(mList[0].replace("!", "").substring(2, 20))) {
						event.replyError("<@!" + mList[0].replace("!", "").substring(2, 20) + "> is not a valid user.");
						return;
					}
				} catch (Exception ex) {
					event.replyError(mList[0] + " is not a valid user.");
					return;
				}

				if (PointsAdder.userGiveUserCoins(event.getAuthor().getId(), Math.abs(Integer.parseInt(mList[1])),
						mList[0].replace("!", "").substring(2, 20))) {
					event.reply(event.getAuthor().getAsMention() + " paid " + mList[0] + " "
							+ Main.addCommas(Integer.parseInt(mList[1])) + Main.CURRENCY);
				} else {
					event.replyError("Something went wrong.  Please try again.");
				}
			} catch (java.lang.NumberFormatException ex) {
				event.replyError(mList[1] + "is not a number!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
