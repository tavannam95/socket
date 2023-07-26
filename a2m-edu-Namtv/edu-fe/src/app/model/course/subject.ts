
export class Subject{
  subjectId?: number;
  documentStatus?: String;
  subjectCode?: String;
  subjectNm?: string;
  subjectContent?: string;
  subjectGoal?: string;
  startDt?: any;
  endDt?: any;
  ordNo?: number;
  status?: any;
  approveHistory: any = {
    empAprUid: ""
  };
}
