package com.intern.common;

import java.io.IOException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ZKConnection {
	private static final Logger logger = LoggerFactory.getLogger(ZKConnection.class);
	ZooKeeper zookeeper;
	java.util.concurrent.CountDownLatch connectedSignal = new java.util.concurrent.CountDownLatch(1);
	
	public ZKConnection() {
		// TODO Auto-generated constructor stub
	}
	
	public void connect(String host) throws IOException, InterruptedException {
		logger.info("========= ZooKeeper connect ... =======");
	    zookeeper = new ZooKeeper(host, 5000, 
	                              new Watcher() {
	                                  public void process(WatchedEvent event) {
	                                      if (event.getState() == KeeperState.SyncConnected) {
	                                          connectedSignal.countDown();
	                                      }
	                                  }
	                              });
	    connectedSignal.await();
	}
	
	public void close() throws InterruptedException {
		logger.info("========= ZooKeeper close ... =======");
	    zookeeper.close();
	}
	
	public ZooKeeper getZooKeeper() {
		logger.info("========= ZooKeeper getZooKeeper ... =======");
	    if (zookeeper == null || !zookeeper.getState().equals(States.CONNECTED)) {
	        throw new IllegalStateException("ZooKeeper is not connected.");
	    }
	    return zookeeper;
	}
	
	
}
/*
 * data node에 대한 zookeeper client관련 코드 
 * 아직 동작 x
 */