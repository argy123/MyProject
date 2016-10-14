package myPack;





import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;


public class HelloWorldTopology {

	/**
	 * @param args
	 * @throws InvalidTopologyException 
	 * @throws AlreadyAliveException 
	 */
	public static void main(String[] args ) throws AlreadyAliveException, InvalidTopologyException {
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("randomHelloWorld", new HelloWorldSpout(), 1);
		builder.setBolt("HelloWorldBolt", new HelloWorldBolt(), 1).shuffleGrouping("randomHelloWorld");
		Config conf = new Config();
		conf.setDebug(true);
		
		if(args!=null && args.length > 0) {
			conf.setNumWorkers(3);
			StormSubmitter.submitTopology(args[0], conf,builder.createTopology());
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("test", conf,builder.createTopology());
			Utils.sleep(2000);
			cluster.killTopology("test");
			cluster.shutdown();
		}

	}

}
