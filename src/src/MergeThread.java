package src;

import com.jagrosh.jdautilities.command.CommandEvent;

public class MergeThread extends Thread 
{
	int repetitions;
	private CommandEvent event;
	
	public MergeThread(CommandEvent e, int r)
	{
		event = e;
		repetitions = r;
	}
	
	@Override
	public void run()
	{
		for (int i = 0; i < repetitions; i++) 
		{
			UpgradeMerge.startWaiter(i, repetitions, event);
			try {
				UpgradeMerge.merge.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
