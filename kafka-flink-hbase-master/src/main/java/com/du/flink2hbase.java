package com.du;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.table.shaded.org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import com.alibaba.fastjson.JSONObject;
import com.du.pool.hbase.HbaseConnectionPool;
import com.du.pool.tool.ConnectionPoolConfig;
import com.du.pool.tool.Message;


public class flink2hbase {

    private static TableName tableName = TableName.valueOf("Flink2HBase");
    private static final String columnFamily = "info";

    public static void main(String[] args) throws Exception {

        //final String ZOOKEEPER_HOST = "node71:2181,node72:2181,node73:2181";
        //final String KAFKA_HOST = "node71:9092,node72:9092,node73:9092,cm70:9092";
        final String KAFKA_HOST = "10.0.10.44:6667";
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(1000); // 非常关键，一定要设置启动检查点！！
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties props = new Properties();
        //props.setProperty("zookeeper.connect", ZOOKEEPER_HOST);
        props.setProperty("bootstrap.servers", KAFKA_HOST);
        //props.setProperty("group.id", "test-consumer-group");
        props.setProperty("group.id", "test-hello-group");

        DataStream<String> transction = env.addSource(new FlinkKafkaConsumer010<String>("hello", new SimpleStringSchema(), props));

        transction.rebalance().map(new MapFunction<String, Object>() {
			private static final long serialVersionUID = 1L;
			public String map(String value)throws IOException{
				System.out.println(value);
               writeIntoHBase(value);
               return null;
           }

        });


       env.execute();
    }

    public static void writeIntoHBase(String m)throws IOException{
		ConnectionPoolConfig config = new ConnectionPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000);
		config.setTestOnBorrow(true);

		
        Configuration hbaseConfig = HBaseConfiguration.create();
        
        hbaseConfig = HBaseConfiguration.create();
        //hbaseConfig.set("hbase.zookeeper.quorum", "node71:2181,node72:2181,node73:2181");
        hbaseConfig.set("hbase.zookeeper.quorum", "10.0.10.43");
        hbaseConfig.set("hbase.zookeeper.property.clientPort", "2181");
        hbaseConfig.set("hbase.defaults.for.version.skip", "true");
        hbaseConfig.set("zookeeper.znode.parent", "/hbase-unsecure");
        
        HbaseConnectionPool pool = null;
        
        try {
    		pool = new HbaseConnectionPool(config, hbaseConfig);

            Connection con = pool.getConnection();

            Admin admin = con.getAdmin();
            
            if(!admin.tableExists(tableName)){
                admin.createTable(new HTableDescriptor(tableName).addFamily(new HColumnDescriptor(columnFamily)));
            }
            Table table = con.getTable(tableName);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            Date date = new Date();
            List<Put> list = new ArrayList<Put>();
            if (StringUtils.isNoneBlank(m)) {
                String[] arr = m.split(",");
                String id = (arr[0].split(":"))[1];
                String msg = arr[1].split(":")[1];
                String dd =  arr[2].split(":")[1];
                
                Message msgObj = JSONObject.parseObject(m, Message.class);
                
                
                Put put = new Put(org.apache.hadoop.hbase.util.Bytes.toBytes(df.format(date)));
                
                put.addColumn(org.apache.hadoop.hbase.util.Bytes.toBytes(columnFamily), org.apache.hadoop.hbase.util.Bytes.toBytes("test"),
                        org.apache.hadoop.hbase.util.Bytes.toBytes("wuhan"));
                table.put(put);
                System.out.println("插入完成");
//                list.add(put);
//                
//                Put put1 = new Put(org.apache.hadoop.hbase.util.Bytes.toBytes(df.format(date)));
//                put.addColumn(org.apache.hadoop.hbase.util.Bytes.toBytes(columnFamily), org.apache.hadoop.hbase.util.Bytes.toBytes("msg"),
//                        org.apache.hadoop.hbase.util.Bytes.toBytes(id));
//                
//                list.add(put1);
//                
//                Put put2 = new Put(org.apache.hadoop.hbase.util.Bytes.toBytes(df.format(date)));
//                put.addColumn(org.apache.hadoop.hbase.util.Bytes.toBytes(columnFamily), org.apache.hadoop.hbase.util.Bytes.toBytes("time"),
//                        org.apache.hadoop.hbase.util.Bytes.toBytes(dd));
                
            }
//            table.put(list);
            table.close();
    		pool.returnConnection(con);
    		
		} catch (Exception e) {
			pool.close();
		}
    }
}