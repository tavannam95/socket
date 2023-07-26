//package com.a2m.gen.repository.signUp;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class SignUpRepositoryImpl implements SignUpRepository{
//	@PersistenceContext
//	EntityManager em;
//
//	@Override
//	public Map insertTsstUserInfo(Map<String, Object> params) {
//		String sql = "INSERT INTO TSST_USER_INFO "
//				+ "		(FULL_NAME, DOB, EMAIL, CELL_PHONE, ADDRESS, GENDER, EMAIL_VERIFY_KEY, 2FA_ENABLE, 2FA_KEY, ORGANIZATION)"
//				+ "	VALUES"
//				+ "	( :fullName, :dob, :email, :cellPhone, :address, :gender, :emailKey, 0, :twoFaKey, :organization)";
//		Query query = em.createNativeQuery(sql).setParameter("fullName", params.get("params"))
//				.setParameter("dob", params.get("dob"))
//				.setParameter("email", params.get("email"))
//				.setParameter("cellPhone", params.get("cellPhone"))
//				.setParameter("address", params.get("address"))
//				.setParameter("gender", params.get("gender"))
//				.setParameter("emailKey", params.get("emailKey"))
//				.setParameter("twoFaKey", params.get("twoFaKey"))
//				.setParameter("organization", params.get("organization"));
//		int result = query.executeUpdate();
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getLastInsertedId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void insertTsstUser(String userUid, String userId, Long userInfoId, String password) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public List<Map<Object, Object>> getListPosition() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setRoleUser(String userUid) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	
//}
