package com.shixun.Job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class MyFirstJob  implements SimpleJob{

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void execute(ShardingContext shardingContext) {
		// TODO Auto-generated method stub
		System.out.println("现在是——"+sdf.format(new Date()));
	}
	
	
	
	 public static void main(String[] args) {
	        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
	    }
	    
	    private static CoordinatorRegistryCenter createRegistryCenter() {
	        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:2181", "elastic-job-demo"));
	        regCenter.init();
	        return regCenter;
	    }
	    
	    private static LiteJobConfiguration createJobConfiguration() {
	        // 创建作业配置
	        // ...
	    	 // 定义作业核心配置
	        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("myFirstJob", "0/10 * * * * ?", 10).build();
	        // 定义SIMPLE类型配置
	        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, MyFirstJob.class.getCanonicalName());
	        // 定义Lite作业根配置
	        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();
			return simpleJobRootConfig;
	    }

}
