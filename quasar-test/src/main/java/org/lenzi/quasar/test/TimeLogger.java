/**
 * 
 */
package org.lenzi.quasar.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

/**
 * @author sal
 *
 */
public class TimeLogger implements SuspendableRunnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9000621016507698459L;
	
	private Logger logger = LoggerFactory.getLogger(TimeLogger.class);
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private String name = null;
	private long sleep = 100;

	/**
	 * 
	 * @param name
	 * @param sleep
	 */
	public TimeLogger(String name, long sleep) {
		this.name = name;
		this.sleep = sleep;
	}

	/* (non-Javadoc)
	 * @see co.paralleluniverse.strands.SuspendableRunnable#run()
	 */
	@Override
	public void run() throws SuspendExecution {
		try {
			for(;;){
				//System.out.println(name + ": Time is " + dateFormat.format(Calendar.getInstance().getTime()));
				logger.debug(name + ": Time is " + dateFormat.format(Calendar.getInstance().getTime()));
				Fiber.sleep(sleep);			
			}
		} catch (InterruptedException e) {
			logger.warn(name + " was interrupted. " + e.getMessage());
		}
	}

}
