package com.eversec.kafkatohbase.service;

public interface IKafkaRecieveCustomerService {

//	/**
//	 * 	基准数据插入.
//	 * @param mcbe
//	 */
//	public void batchInsertMachineCardBase(List<MachineCardBaseEntity> mcbe);
//	
//	/**
//	 * 	最后一次位置数据插入.
//	 * @param mcbe
//	 */
//	public void batchInsertLatestLocation(List<LatestLocationEntity> lle);
//	
//	/**
//	 * 	重点人员电话，主号
//	 * @return
//	 */
//	@MapKey("msisdn")
//	public Map<String, Map<String, String>> getKeyPhone();
//	
//	/**
//	 * 	重点人员电话列表
//	 * @return
//	 */
//	@MapKey("msisdn")
//	public Map<String, Map<String, String>> getKeyPhoneList();
//	
//	/**
//	 * 	重点人员全量关系手机号码列表
//	 * @return
//	 */
//	public List<Map<String, Object>> selectKeyPersonAllPhone();
//	
//	/**
//	 * 更新重点人员与手机信息
//	 * @return
//	 */
//	public void updateKeyPhone();
//	
//	/**
//	 * 获取可用布控任务id列表
//	 * @return
//	 */
//	public List<Long> disposeIdList();
//	
//	/**
//	 * 	实名制电话
//	 * @return
//	 */
//	@MapKey("msisdn")
//	public Map<String, Map<String, String>> getRealPhoneList();
}