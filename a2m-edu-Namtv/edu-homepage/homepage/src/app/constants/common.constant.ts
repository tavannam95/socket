export class CommonConstant {
  public static readonly DATE_YYYMMDD = 'yyyy/MM/dd';
  public static readonly YES = 'Y';
  public static readonly NO = 'N';
  public static readonly PAGE_SIZE: number = 10;
  public static readonly LANG_KOREA = 'kr';
  public static readonly LANG_VIET = 'vi';
  public static readonly LANG_ENGLISH = 'en';

  public static readonly	RESULT_OK					= "OK";
	public static readonly	RESULT_WN					= "WN";
	public static readonly	RESULT_NG					= "NG";

	public static readonly	MESSAGES_KEY				= "messages";
	public static readonly  STATUS_KEY					= "status";
	public static readonly	RESULT_KEY					= "result";
	public static readonly	LIST_KEY					= "list";
	public static readonly	DETAIL_KEY					= "detail";
	public static readonly	COUNT_ITEMS_KEY				= "totalItems";
  public static readonly  KEY                   = "key";

  public static readonly CONNECT_TYPE = {
    MARIA_DB: '01-01',
    SQL_SERVER: '01-02',
    ORACLE: '01-03',
    POSTGRESQL: '01-04',
    MYSQL: '01-05',
    MONGO_DB: '01-06',
    FTP: '01-07',
  };

  public static readonly OPTION_DEFAULT_ALL = {
    label: 'All',
    labelen: 'All',
    labelkr: '전체',
    labelvi: 'Tất cả',
    value: '',
  };

  //COM CODE
  public static readonly CONNECT_TYPE_UP_COMM_CD = '01';
  public static readonly PROJECT_STATUS_UP_COMM_CD = '02';
  public static readonly FILE_TYPE_COMM_CD = '03';
  public static readonly POSITION_COMM_CD = '04';
  public static readonly LECTURE_TYPE_COMM_CD = '06';
  public static readonly QUIZ_TYPE_COMM_CD = '07';

  //END COM CODE

  public static readonly PROJECT_STATUS = {
    NEW: '02-01',
    RUNNING: '02-02',
    PAUSE: '02-03',
    STOP: '02-04',
    KILLED: '02-05',
  };

  public static readonly DAY_OF_WEEK = [
    { lable: 'Monday', value: 0 },
    { lable: 'Tuesday', value: 1 },
    { lable: 'Wednesday', value: 2 },
    { lable: 'Thursday', value: 3 },
    { lable: 'Friday', value: 4 },
    { lable: 'Saturday', value: 5 },
    { lable: 'Sunday', value: 6 },
  ];

  public static readonly MONTH_OF_YEAR = [
    { lable: 'January', value: 1 },
    { lable: 'February', value: 2 },
    { lable: 'March', value: 3 },
    { lable: 'April', value: 4 },
    { lable: 'May', value: 5 },
    { lable: 'June', value: 6 },
    { lable: 'July', value: 7 },
    { lable: 'August', value: 8 },
    { lable: 'September', value: 9 },
    { lable: 'October', value: 10 },
    { lable: 'November', value: 11 },
    { lable: 'December', value: 12 },
  ];

  public static readonly ROLE_SYS_ADMIN = 'R000';

  public static readonly RESPONSE_MESSAGE_SUCCESS = 'OK';

  public static readonly ATOWM_EDU_CONSTANT = 'A2MEDU';
}
