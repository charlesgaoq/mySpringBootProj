/**
 * 
 */
package com.eversec.kafkatohbase.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eversec.kafkatohbase.service.IKafkaRecieveCustomerService;
import com.eversec.wisdom.policing.domain.LatestLocationEntity;
import com.eversec.wisdom.policing.domain.MachineCardBaseEntity;

/**
 * @author HaDa
 *
 */
@Service
public class KafkaRecieveCustomerService implements IKafkaRecieveCustomerService {

//	@Resource
//	private IKafkaRecieveCustomerMapper kafkaRecieveCustomerMapper;
//	
//	@Override
//	public void batchInsertMachineCardBase(List<MachineCardBaseEntity> mcbe) {
//		kafkaRecieveCustomerMapper.batchInsertMachineCardBase(mcbe);
//	}
//
//	@Override
//	public void batchInsertLatestLocation(List<LatestLocationEntity> lle) {
//		kafkaRecieveCustomerMapper.batchInsertLatestLocation(lle);
//	}
//
//	@Override
//	public Map<String, Map<String, String>> getKeyPhone() {
//		return kafkaRecieveCustomerMapper.getKeyPhone();
//	}
//
//	@Override
//	public List<Map<String, Object>> selectKeyPersonAllPhone() {
//		return kafkaRecieveCustomerMapper.selectKeyPersonAllPhone();
//	}
//
//	@Override
//	public void updateKeyPhone() {
//		List<Map<String, Object>> kpa =  kafkaRecieveCustomerMapper.selectKeyPersonAllPhone();
//		kafkaRecieveCustomerMapper.updateKeyPhone(kpa);
//	}
//
//	@Override
//	public Map<String, Map<String, String>> getKeyPhoneList() {
//		return kafkaRecieveCustomerMapper.getKeyPhoneList();
//	}
//
//	@Override
//	public List<Long> disposeIdList() {
//		return kafkaRecieveCustomerMapper.disposeIdList();
//	}
//
//	@Override
//	public Map<String, Map<String, String>> getRealPhoneList() {
//		return kafkaRecieveCustomerMapper.getRealPhoneList();
//	}

}
