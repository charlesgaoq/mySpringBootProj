/**
 * 
 */
package com.eversec.kafkatohbase.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import com.eversec.kafkatohbase.service.IKafkaRecieveCustomerService;
import com.eversec.wisdom.policing.domain.LatestLocationEntity;
import com.eversec.wisdom.policing.domain.MachineCardBaseEntity;


/**
 * 
 * KAFKA消息消费者
 * @author HaDa
 *
 */
@Component
@EnableBinding(Sink.class)
public class KafkaRecieveCustomerHandler {

	private static final Logger logger = LoggerFactory.getLogger(KafkaRecieveCustomerHandler.class);
	
	public static final Map<String, Map<String, String>> PHONE_LIST1 = new HashMap<>();
	public static final Map<String, Map<String, String>> PHONE_LIST2 = new HashMap<>();
	
	public static final Map<String, Map<String, String>> KEY_PHONE1 = new HashMap<>();
	public static final Map<String, Map<String, String>> KEY_PHONE2 = new HashMap<>();
	
	public static boolean MASTER = true;
	
	private static int index = 0;
	
	@Resource
	private IKafkaRecieveCustomerService kafkaRecieveCustomerService;
	
//	@Resource
//	private RedisUtil redisUtil;
	
	@Value("${phone.on.off.index}")
	private String onOffIndex;
	
//	@PostConstruct
//	void init() {
//		PHONE_LIST1.putAll(kafkaRecieveCustomerService.getKeyPhoneList());
//		KEY_PHONE1.putAll(kafkaRecieveCustomerService.getKeyPhone());
//	}
	
	public final static AtomicInteger count = new AtomicInteger(0);
	
	/**
	 * 	消息消费
	 */
	@StreamListener(value = Sink.INPUT)
	public void messageProcess (String message) {
		if (null == message) {
			return;
		}
		
		String [] msgs = message.split("\\|");
		/*
		 * 00 id_num 	身份证号码	String
		 * 01 msisdn	手机号码	String
		 * 02 imsi	手机卡串码	String
		 * 03 imei	手机串码	String
		 * 04 mac	MAC地址	String
		 * 05 meid	手机MEID（电信2/3G）	String
		 * 06 bsid	基站编号（电信2/3G）	String
		 * 07 rat	接入网类型	Integer
		 * 08 apn	网络接入类型	String
		 * 09 longitude	经度	Float
		 * 10 latitude	纬度	Float
		 * 11 location_source	经纬度来源	String
		 * 12 local_province	用户当前所在省	String
		 * 13 local_city	用户当前所在城市	String
		 * 14 owner_province	用户归属省	String
		 * 15 owner_city	用户归属城市	String
		 * 16 number_attribution	号码归属地	String
		 * 17 roaming_type	漫游类型	String
		 * 18 procedure_type	开关机状态标识	Integer
		 * 19 tac	跟踪区	Long
		 * 20 eci	小区	Long
		 * 21 other_tac	对端跟踪区	Long
		 * 22 other_eci	对端小区	Long
		 * 23 bearer_status	承载结果	Integer
		 * 24 start_time	业务开始时间	Long
		 * 25 end_time	业务结束时间	Long
		 * 26 data_source	数据来源，S1MME/S1-U	String
		 * 27 name	用户姓名	String
		 * 
		 * 0  "local_province",
 		 * 1  "local_city",
 		 * 2  "owner_province",
 		 * 3  "owner_city",
 		 * 4  "roaming_type",
 		 * 5  "rat",
 		 * 6  "imsi",
 		 * 7  "imei",
 		 * 8  "msisdn",
 		 * 9  "procedure_type",
 		 * 10 "start_time",
 		 * 11 "end_time",
 		 * 12 "longitude",
 		 * 13 "latitude",
 		 * 14 "location_source",
 		 * 15 "tac",
 		 * 16 "eci",
 		 * 17 "other_tac",
 		 * 18 "other_eci",
 		 * 19 "apn",
 		 * 20 "id_num",
 		 * 21 "number_attribution",
 		 * 22 "meid",
 		 * 23 "bsid",
 		 * 24 "mac",
 		 * 25 "bearer_status",
 		 * 26 "data_source",
 		 * 27 "name",
 		 * 28 "id_num_source",
 		 * 29 "msisdn_prefix",
		 */
                         		 

		if(null == msgs || msgs.length <= 28) {
			return;
		}
		
		// 28  idNum 身份证
//		String idNum = msgs[28];暂不使用
		
		// 08 msisdn 手机号码  String
		String msisdn = msgs[8];
		// 06 imsi 手机号码  String
		String imsi = msgs[6];
		// 07 imei   String
		String imei = msgs[7];
		// 24 mac MAC  String
		String mac = msgs[24];
		// 23 bsid 基站id  String
		String bsid = msgs[23];
		// 12 longitude 经度  String
		String longitude = msgs[12];
		// 13 longitude 维度  String
		String latitude = msgs[13];
		// 13 number_attribution 归属地 String
		String numberAttribution = msgs[21];
		// 9 procedure_type 开关机状态  String
		String procedureType = msgs[Integer.valueOf(onOffIndex)];
		// 16 ECI  String
		String eci = msgs[20];
		// 26 data_source 数据来源 String
		String dataSource = msgs[26];
		// 27 name 姓名 String
//		String personName = msgs[27];
		
		/* 
		 * 	为空，直接丢弃数据
		 */
		if (StringUtils.isBlank(msisdn)) {
			return;
		}
		
		/* 
		 * 	与重点人员信息无关的信息则丢弃
		 */
		if (!getPhoneName().keySet().contains(msisdn)) {
			return;
		}
		
		int mark = count.getAndIncrement();
		if (mark == 0 || mark > 100000) {
			count.set(1);
			logger.info("消费标记, 标记值：{}, 重置为1 ", mark);
		}
		
		MachineCardBaseEntity mcbe = new MachineCardBaseEntity();
		
		boolean isKeyPhone = getKeyPhoneName().containsKey(msisdn);//判断是否为重点人员主号码
		
		LatestLocationEntity lle = new LatestLocationEntity();
		
		if (isNotBlank(msisdn)) {
			mcbe.setMsisdn(msisdn);
			lle.setMsisdn(msisdn);
			if (getKeyPhoneName().keySet().contains(msisdn)) {//重点人员为录入
				mcbe.setNumberSource("录入");				
			} else {
				mcbe.setNumberSource("研判");	
			}
		} else {
			mcbe.setMsisdn("");
			lle.setMsisdn("");
			mcbe.setNumberSource("");
		}
		
		if (isNotBlank(imsi)) {
			mcbe.setImsi(imsi);
			lle.setImsi(imsi);
		} else {
			mcbe.setImsi("");
			lle.setImsi("");
		}
		
		//imei	手机串码	String
		if (isNotBlank(imei)) {
			mcbe.setImei(imei);
			lle.setImei(imei);
		} else {
			mcbe.setImei("");
			lle.setImei("");
		}
		
		//mac	MAC地址	String
		if (isNotBlank(mac)) {
			mcbe.setMac(mac);
			lle.setMac(mac);
		} else {
			mcbe.setMac("");
			lle.setMac("");
		}
		
		//bsid	基站编号（电信2/3G）	String
		if (isNotBlank(bsid)) {
			mcbe.setBsid(bsid);
			lle.setBsid(bsid);
		} else {
			mcbe.setBsid("");
			lle.setBsid("");
		}
		
		// longitude	经度	
		if (isNotBlank(longitude)) {
			try {
				mcbe.setLongitude(Double.valueOf(longitude));
				lle.setLongitude(Double.valueOf(longitude));
			} catch (Exception e) {
				logger.error("经度转化异常. value={}", longitude, e);
			}
		} else {
			mcbe.setLongitude(0.0);
			lle.setLongitude(0.0);
		}
		
		// latitude	纬度	Float
		if (isNotBlank(latitude)) {
			try {
				mcbe.setLatitude(Double.valueOf(latitude));
				lle.setLatitude(Double.valueOf(latitude));
			} catch (Exception e) {
				logger.error("维度转化异常. value={}", latitude, e);
			}
		} else {
			mcbe.setLatitude(0.0);
			lle.setLatitude(0.0);
		}
		
		// 所在位置
//		if (isNotBlank(longitude) && isNotBlank(latitude)) {
//			double [] point = new double[] {Double.valueOf(longitude), Double.valueOf(latitude)};
//			if (PlanarGeometryUtil.pointInPolygon(point, CommonConst.haimen)) {
//				mcbe.setLocation(0);
//				lle.setLocation(0);
//			} else if (PlanarGeometryUtil.pointInPolygon(point, CommonConst.nantong)) {				
//				mcbe.setLocation(1);
//				lle.setLocation(1);
//			} else {
//				mcbe.setLocation(2);
//				lle.setLocation(2);
//			}
//		} else {
//			mcbe.setLocation(2);
//			lle.setLocation(2);
//		}
		
		// number_attribution	号码归属地	String
		if (isNotBlank(numberAttribution)) {
			mcbe.setNumberAttribution(numberAttribution);
			lle.setNumberAttribution(numberAttribution);
		} else {
			mcbe.setNumberAttribution("");
			lle.setNumberAttribution("");
		}
		
		// eci	小区	Long
		if (isNotBlank(eci)) {
			mcbe.setEci(eci);
			lle.setEci(eci);
		} else {
			mcbe.setEci("");
			lle.setEci("");
		}
		
		//data_source	数据来源，S1MME/S1-U	String
		if (isNotBlank(dataSource)) {
			mcbe.setDataSource(dataSource);
			lle.setDataSource(dataSource);
		} else {
			mcbe.setDataSource("");
			lle.setDataSource("");
		}
		
//		//id_num 	身份证号码	String 
//		if (isNotBlank(idNum)) {
//			mcbe.setIdNum(idNum);
//			lle.setIdNum(idNum);
//		} else {
//			mcbe.setIdNum("");
//			lle.setIdNum("");
//		}
		
		// name	用户姓名	String
		if (isKeyPhone) {
			Map<String, String> phoneName = getKeyPhoneName().get(msisdn);//判断是否为重点人员主号码
			if (phoneName != null && !phoneName.isEmpty()) {
				String name = phoneName.get("name");
				String idNum = phoneName.get("id_num");
				if (isNotBlank(idNum)) {
					mcbe.setIdNum(idNum);
					lle.setIdNum(idNum);
					mcbe.setName(name);
					lle.setName(name);						
				} 
			} else {
				mcbe.setIdNum("");
				lle.setIdNum("");
				mcbe.setName("");
				lle.setName("");
			}
		} else {
			Map<String, String> phoneName = getPhoneName().get(msisdn);
			if (phoneName != null && !phoneName.isEmpty()) {
				String name = phoneName.get("name");
				String idNum = phoneName.get("id_num");
				String numberSource = phoneName.get("number_source");
				if (isNotBlank(idNum)) {
					mcbe.setIdNum(idNum);
					if (StringUtils.isNotBlank(numberSource)) {
						mcbe.setName(name + "(实名制获取)");
					} else {
						mcbe.setName(name);
					}
				} else {
					mcbe.setIdNum("");
					mcbe.setName("");
				}
			} else {
				mcbe.setIdNum("");
				mcbe.setName("");
			}
		}
		
		Date appear = null;
		try {
			appear = new Date(Long.valueOf(msgs[10]));			
		} catch (Exception e) {
			e.printStackTrace();
			appear = new Date();
		}
		
		Date date = new Date();
		// 生成基准表
		mcbe.setCreateTime(date);
		// 生成最后一次数据表
		lle.setCreateTime(date);
		
		// procedure_type	开关机状态标识	Integer
		if (isNotBlank(procedureType)) {
			int ps = Integer.valueOf(procedureType);
			mcbe.setPhoneStatus(ps);
			lle.setPhoneStatus(ps);
		} else {
			mcbe.setPhoneStatus(0);
			lle.setPhoneStatus(0);
		}
		
		lle.setUpdateTime(appear);
		mcbe.setUpdateTime(appear);
		
//		if (isKeyPhone) {
//			Level2QueueUtil.getLatestLocationQueue().add(lle);
//		}
//		
//		Level2QueueUtil.getMachineCardBaseQueue().add(mcbe);
		
		if (index <= 3) {
			logger.info("成功消费第{}条消息, {}", index, message);
			index ++;
		}

	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	private static boolean isNotBlank(String msg) {
		return StringUtils.isNotBlank(msg) && !"default".equalsIgnoreCase(msg);
	}
	
	public static Map<String, Map<String, String>> getPhoneName() {
		if (MASTER) {//切换缓存目录
			return PHONE_LIST1;
		} else {
			return PHONE_LIST2;
		}
	}
	
	public static Map<String, Map<String, String>> getKeyPhoneName() {
		if (MASTER) {//切换缓存目录
			return KEY_PHONE1;
		} else {
			return KEY_PHONE2;
		}
	}
}
