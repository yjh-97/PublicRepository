package com.cxytiandi.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class Server {
	public static void main(String[] args) {
		// 1 重试策略：初试时间为1s 重试10次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
		// 2 通过工厂创建连接
		CuratorFramework cf = CuratorFrameworkFactory.builder()
				.connectString("localhost:2181")
				.sessionTimeoutMs(1000 * 10)
				.retryPolicy(retryPolicy)
				.build();
		// 3 开启连接
		cf.start();
		try {
			cf.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/urls/192.168.1.12", "".getBytes());
			Thread.sleep(200000000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
