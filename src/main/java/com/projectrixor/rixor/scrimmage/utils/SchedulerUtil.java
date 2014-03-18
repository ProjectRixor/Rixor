package com.projectrixor.rixor.scrimmage.utils;

import com.projectrixor.rixor.scrimmage.Rixor;
import org.bukkit.scheduler.BukkitTask;

import lombok.Getter;
import lombok.Setter;

public class SchedulerUtil {
	
	private @Getter @Setter int id;
	private @Getter BukkitTask task;
	
	public void runnable() {
		
	}
	
	public void repeatAsync(long interval, long delay) {
		if(this.task != null)
			this.task.cancel();
		
		this.task = Rixor.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Rixor.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				runnable();
			}
			
		}, delay, interval);
	}
	
	public void repeat(long interval, long delay) {
		if(this.task != null)
			this.task.cancel();
		
		this.task = Rixor.getInstance().getServer().getScheduler().runTaskTimer(Rixor.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				runnable();
			}
			
		}, delay, interval);
	}
	
	public void laterAsync(long delay) {
		if(this.task != null)
			this.task.cancel();
		
		this.task = Rixor.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Rixor.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				runnable();
			}
			
		}, delay);
	}
	
	public void later(long delay) {
		if(this.task != null)
			this.task.cancel();
		
		this.task = Rixor.getInstance().getServer().getScheduler().runTaskLater(Rixor.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				runnable();
			}
			
		}, delay);
	}
	
	public void nowAsync() {
		if(this.task != null)
			this.task.cancel();
		
		this.task = Rixor.getInstance().getServer().getScheduler().runTaskAsynchronously(Rixor.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				runnable();
			}
			
		});
	}
	
	public void now() {
		if(this.task != null)
			this.task.cancel();
		
		this.task = Rixor.getInstance().getServer().getScheduler().runTask(Rixor.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				runnable();
			}
			
		});
	}
	
}
