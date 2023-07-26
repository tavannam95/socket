export class Lecture{
  lectureId?: number;
  lectureNm?: string;
  lectureContent?: string;
  lectureGoal?: string;

  lectureFileModel: any;

  startDt?: any;
  endDt?: any;
  ordNo?: number;
  status?: any; 

  Lecture(){
    this.lectureFileModel = [];
  }
}
