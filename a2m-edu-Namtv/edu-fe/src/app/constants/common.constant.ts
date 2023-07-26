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

  public static readonly READ_YN = "readYn";
  public static readonly WRT_YN = "wrtYn";
  public static readonly MOD_YN = "modYn";
  public static readonly DEL_YN = "delYn";
  public static readonly PNT_YN = "pntYn";
  public static readonly EXCDN_YN = "excDnYn";

  public static readonly MAIL_REPLY = 'REPLY';
  public static readonly MAIL_REPLY_ALL = 'REPLY_ALL';
  public static readonly MAIL_FORWARD = 'FORWARD';
  public static readonly MAIL_DRAFT = 'DRAFT';

  public static readonly FOLDER_MAIL_INBOX = 'INBOX';
  public static readonly FOLDER_MAIL_TRASH = 'Trash';
  public static readonly FOLDER_MAIL_SENT = 'Sent';
  public static readonly FOLDER_MAIL_DRAFTS = 'Draft';

  public static readonly KEY_VERSION = '_a2m_version';

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
  public static readonly CHAPTER_TYPE_COMM_CD = '06';
  public static readonly QUIZ_TYPE_COMM_CD = '07';
  public static readonly QUIZ_ITEM_TYPE_COMM_CD = '08';
  public static readonly IQ_QUES_TYPE_COMM_CD = '09';
  public static readonly IQ_QUES_LEVEL_COMM_CD = '10';
  public static readonly IQ_QUES_CATEGORY_COMM_CD = '11';
  public static readonly ORGANIZATION_FORMAL = '12';
  public static readonly STAND_NOTE = '13';
  public static readonly APPROVAL_STATUS_COMM_CD = '14';
  public static readonly APPROVAL_STATUS_PENDING_COMM_CD = '14-01';
  public static readonly APPROVAL_STATUS_APPROVAL_COMM_CD = '14-02';
  public static readonly APPROVAL_STATUS_REJECT_COMM_CD = '14-03';
  public static readonly CLASS_TYPE_COMM_CD = '15';
  public static readonly EQUIPMENT_TYPE_COMM_CD = '16';
  public static readonly STANDARD = '18';
  public static readonly TYPE_DOCUMENT = '19';

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
  public static readonly ROLE_SYS_MANAGER = 'R001';
  public static readonly ROLE_SYS_TEA_ASSIS = 'R002';
  public static readonly ROLE_SYS_TEACHER = 'R013';
  public static readonly ROLE_SYS_STUDENT = 'R014';
  public static readonly ROLE_SYS_APPROVER = 'R015';

  public static readonly RESPONSE_MESSAGE_SUCCESS = 'OK';

  public static readonly POST_TYPE_LECTURE = 'LECTURE';
  public static readonly POST_TYPE_CHAPTER = 'CHAPTER';

  //table
  public static readonly TABLE_SUBJECT = 'EAM_COURSE_SUBJECT';

  public static readonly DOCUMENT_STATUS_DRAFT = 'DRAFT';
  public static readonly DOCUMENT_STATUS_APPROVAL = 'APPROVAL';

  // Default class
  public static readonly CLASS_ANNOUN_TITLE = 'Title Default';
  public static readonly CLASS_ANNOUN_CONTENT = 'Content Default';
}
