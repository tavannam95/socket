package com.a2m.gen.constants;

public class CommonContants {
	
	public static String USE_Y = "Y";
	public static String USE_N = "N";
	
	public static String FILE_TYPE = "03";
	
	public static String PROJECT_STATUS_NEW = "02-01";
	public static String PROJECT_STATUS_RUNNING = "02-02";
	public static String PROJECT_STATUS_PAUSE = "02-03";
	public static String PROJECT_STATUS_STOP = "02-04";
	public static String PROJECT_STATUS_KILLED= "02-05";
	
	public static String LICENSE_STATUS_NEW = "05-01";
	public static String LICENSE_STATUS_PAID = "05-02";
	public static String LICENSE_STATUS_EXPIRED = "05-03";
	
	public static String USER_ROLE = "R013";
	public static String STUDENT_ROLE = "R014";
	
	public static final String APPROVAL_STATUS_PENDING = "14-01";
	public static final String APPROVAL_STATUS_APPROVAL = "14-02";
	public static final String APPROVAL_STATUS_REJECT = "14-03";
	
	public static final String DOCUMENT_STATUS_DRAFT = "DRAFT";
	public static final String DOCUMENT_STATUS_APPROVAL = "APPROVAL";
	
	public enum State {
		UPDATE("U"), DELETE("D"), CREATE("C");

		private String value;

		State(String id) {
			this.value = id;
		}

		public String getValue() {
			return value;
		}
	}
	
	public static final String	RESULT_OK					= "OK";
	public static final String	RESULT_WN					= "WN";
	public static final String	RESULT_NG					= "NG";
	
	public static final String	MESSAGES_KEY				= "messages";
	public static final String	STATUS_KEY					= "status";
	public static final String	RESULT_KEY					= "result";
	public static final String	LIST_KEY					= "list";
	public static final String	DETAIL_KEY					= "detail";
	public static final String	COUNT_ITEMS_KEY				= "totalItems";
	public static final String	KEY				= "key";
}
