import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Author ZhengYingjie
 * @Date 2019/1/19
 * @Description
 */
public class HbaseTest {

    private static final Logger logger = Logger.getLogger(HbaseTest.class);
    private static Configuration conf = null;
    private Connection connection = null;
    private Table table = null;

    /**
     * 鍒濆鍖栬繛鎺�
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","hadoop1001,hadoop1002,hadoop1003");
        conf.set("hbase.zookeeper.property", "2181");
        connection = ConnectionFactory.createConnection(conf);
        table = connection.getTable(TableName.valueOf("user"));
        logger.info("鍒濆鍖栬〃杩炴帴瀹屾垚锛�");
        System.out.println("鍒濆鍖栬〃杩炴帴瀹屾垚锛�");
    }

    /**
     * 鍒涘缓琛�
     * @throws IOException
     */
    @Test
    public void createTable() throws IOException {
        //鍒涘缓琛ㄧ鐞嗙被
//        HBaseAdmin admin = new HBaseAdmin(conf);
        Admin admin = connection.getAdmin();

        //鍒涘缓琛ㄦ弿杩扮被
        TableName tableName = TableName.valueOf("test3");
        if(admin.tableExists(tableName)){
            logger.error("琛ㄥ凡缁忓瓨鍦�!");
        }
        HTableDescriptor desc = new HTableDescriptor(tableName);

        //鍒涘缓鍒楃鎻忚堪绫�
        HColumnDescriptor famliy1= new HColumnDescriptor("info1");
        HColumnDescriptor famliy2= new HColumnDescriptor("info2");

        //灏嗗垪鏃忔坊鍔犲埌琛�
        desc.addFamily(famliy1);
        desc.addFamily(famliy2);

        //鍒涘缓琛�
        admin.createTable(desc);
        logger.info("鍒涘缓鎴愬姛");
    }

    /**
     * 鎻掑叆鏁版嵁
     * @throws IOException
     */
    @Test
    public void insert() throws IOException {
        //鍒涘缓鏁版嵁灏佽绫�
        Put zhangsan= new Put(Bytes.toBytes("zhangsan"));//rowkey
        zhangsan.addColumn(Bytes.toBytes("info1"), Bytes.toBytes("name"), Bytes.toBytes("zhangsan"));
        zhangsan.addColumn(Bytes.toBytes("info1"), Bytes.toBytes("age"), Bytes.toBytes("18"));
        zhangsan.addColumn(Bytes.toBytes("info2"), Bytes.toBytes("sex"), Bytes.toBytes("鐢�"));
        zhangsan.addColumn(Bytes.toBytes("info2"), Bytes.toBytes("address"), Bytes.toBytes("鍖椾含"));

        Put lisi= new Put(Bytes.toBytes("lisi"));
        lisi.addColumn(Bytes.toBytes("info1"), Bytes.toBytes("name"), Bytes.toBytes("lisi"));
        lisi.addColumn(Bytes.toBytes("info1"), Bytes.toBytes("age"), Bytes.toBytes("23"));
        lisi.addColumn(Bytes.toBytes("info2"), Bytes.toBytes("sex"), Bytes.toBytes("濂�"));
        lisi.addColumn(Bytes.toBytes("info2"), Bytes.toBytes("address"), Bytes.toBytes("涓婃捣"));

        List<Put> puts = new ArrayList<>();
        puts.add(zhangsan);
        puts.add(lisi);
        table.put(puts);
    }

    @Test
    public void update(){
        //鍏跺疄鏄鐩� 澧炲姞鏂板�煎嵆鍙�
    }

    /**
     * 鍒犻櫎
     * @throws IOException
     */
    @Test
    public void deleteDate() throws IOException {
        Delete zhangsan = new Delete(Bytes.toBytes("zhangsan"));
        zhangsan.addColumn(Bytes.toBytes("info1"),Bytes.toBytes( "name"));
        zhangsan.addFamily("info2".getBytes());
        ArrayList<Delete> deletes = new ArrayList<>();
        deletes.add(zhangsan);
        table.delete(deletes);
    }

    /**
     * 鏌ヨ鍗曟潯鏁版嵁
     * @throws IOException
     */
    @Test
    public void queryOneData() throws IOException {
        //灏佽鏌ヨ鏉′欢鐨勭被
        Get get = new Get(Bytes.toBytes("zhangsan"));
//        get.addColumn(, )
//        get.addFamily()
        Result result = table.get(get);

        byte[] value = result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name"));


        System.out.println("鏌ヨ鏁版嵁锛�");
        System.out.println(Bytes.toString(value));
//        System.out.println(new String(value));
    }

    /**
     * 鎵弿
     * @throws IOException
     */
    @Test
    public void scanData() throws IOException {
        Scan scan = new Scan();
//        scan.setStartRow() //璧峰琛� rowkey瀛楀吀鎺掑簭
//        scan.setStopRow()
//        scan.addFamily("info2".getBytes());   //鍙煡鏌愬垪鏃�
        scan.addColumn("info2".getBytes(),"address".getBytes()); //鍙煡鏌愬垪
        ResultScanner scanner = table.getScanner(scan);
        listValue(scanner);
    }

    private void listValue(ResultScanner scanner) {
        for (Result next : scanner) {
            byte[] value = next.getValue("info2".getBytes(), "address".getBytes());
            byte[] name = next.getValue("info1".getBytes(), "name".getBytes());
            System.out.println(new String(value));
            System.out.println(new String(name));
            System.out.println(new String(next.getRow()));
            System.out.println("***************");

        }
    }

    /**
     * 杩囨护鏌ヨ 鍒楀�艰繃婊ゅ櫒 SingleColumnValueFilter
     */
    @Test
    public void queryByFilter1() throws IOException {
        Scan scan = new Scan();

        //鍒楀�煎皬浜�19
        SingleColumnValueFilter singleColumnValueFilter =
                new SingleColumnValueFilter("info1".getBytes(),"age".getBytes(),CompareFilter.CompareOp.LESS,"19".getBytes());
//        FilterList filterList = new FilterList();
//        filterList.addFilter();
        scan.setFilter(singleColumnValueFilter);
        ResultScanner scanner = table.getScanner(scan);
        listValue(scanner);
    }



    /**
     * 杩囨护鏌ヨ 鍒楀悕鍓嶇紑杩囨护鍣� ColumnPrefixFilter
     */
    @Test
    public void queryByFilter2() throws IOException {
        Scan scan = new Scan();

        // 浠モ�渘鈥濆紑澶寸殑鍒�
        ColumnPrefixFilter filter =
                new ColumnPrefixFilter("a".getBytes());
        FilterList filterList = new FilterList();
        filterList.addFilter(filter);
        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        listValue(scanner);
    }

    /**
     * 杩囨护鏌ヨ 澶氫釜鍒楀悕鍓嶇紑杩囨护鍣� MultipleColumnPrefixFilter
     */
    @Test
    public void queryByFilter3() throws IOException {
        Scan scan = new Scan();

        // 浠モ�渘鈥濆紑澶寸殑鍒� 鍜屼互"a"寮�澶寸殑鍒�
        byte[][] prefix = {("n".getBytes()),("a".getBytes())};
        MultipleColumnPrefixFilter filter =
                new MultipleColumnPrefixFilter(prefix);
        FilterList filterList = new FilterList();
        filterList.addFilter(filter);
        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        listValue(scanner);
    }

    /**
     * 杩囨护鏌ヨ rowKey杩囨护鍣� RowFilter
     */
    @Test
    public void queryByFilter4() throws IOException {
        Scan scan = new Scan();

        //浠寮�澶寸殑rowkey
        RowFilter filter =
                new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator("^l"));
        //涓�
//        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

        // 鎴�
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        filterList.addFilter(filter);
        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        listValue(scanner);
    }

    @After
    public void closeCOnnection(){
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("鍏抽棴杩炴帴锛侊紒");
        System.out.println("鍏抽棴杩炴帴锛侊紒");
    }




}
