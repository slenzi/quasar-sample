package org.lenzi.quasar.test;

import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.FiberForkJoinScheduler;
import co.paralleluniverse.fibers.FiberScheduler;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

/**
 * Fiber playground
 * 
 * @author sal
 *
 */
public class CustomFiberTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3993647927786726254L;
	
	private Logger logger = LoggerFactory.getLogger(CustomFiberTest.class);
	
	private transient FiberScheduler scheduler;
	
	public CustomFiberTest(){
		this.scheduler = new FiberForkJoinScheduler("MyTestScheduler", 1, null, false);
	}
	
	/*
	@Test
	public void basicFiberTest() throws Exception {
		
		logger.debug("Running basic fiber test...");
		
		logger.debug("Creating fiber");
		Fiber<Void> fiber = new Fiber<Void>(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution {
            	logger.debug("Fiber started");
                try {
                    
                	logger.debug("Fiber sleeping...");
                	Fiber.sleep(100);
                    
                    fail("InterruptedException not thrown");
                    
                } catch (InterruptedException e) {
                	logger.debug("Fiber interupted! " + e.getMessage());
                }
            }
        }).start();

		logger.debug("Main thread sleeping...");
        Thread.sleep(20);
        logger.debug("Main thread awake!");
        logger.debug("Interupt our fiber...");
        fiber.interrupt();
        logger.debug("Calling join on fiber...");
        fiber.join(5, TimeUnit.MILLISECONDS);
        
        logger.debug("Test complete");
	}
	*/
	
	@Test
	public void largeFiberTest() throws ExecutionException, InterruptedException, TimeoutException{
		
		Fiber<Void> fiber = null;
		int fiberCount = 5;
		Collection<Fiber<Void>> fibers = new ArrayList<Fiber<Void>>();
		
		// create fibers
		for(int i=0; i<fiberCount; i++){
			fibers.add(getTimeFiber(String.valueOf(i),333));
		}
		
		// start fibers
		for(Fiber<Void> f : fibers){
			f.start();
		}
		
		// sleep for awhile, let our fibers run
		Thread.sleep(30000);
		
		// interrupt all fibers
		for(Fiber<Void> f : fibers){
			f.interrupt();
		}
		
		Thread.sleep(100);
		
		logger.debug("Test complete");
	}
	
	public Fiber<Void> getTimeFiber(String name, long sleep){	
		return new Fiber<Void>(scheduler, new TimeLogger(name,sleep));		
	}

}
