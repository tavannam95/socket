
import { EamStudentInfo } from "../gen/eam-student-info";
import { EamTeacherInfo } from "../gen/eam-teacher-info";
import {TsstUserInfo} from "../gen/tsst-user-info";
export class TsstUser {
  index: any;
    userUid: any;
    userId: any;
    password: any;
    newPassword: any;
    confirmPassword: any;
    createdDate: any;
    updatedDate: any;
    status: any;
    userType: any;
    tsstUserInfo: TsstUserInfo;
    eamStudentInfo: EamStudentInfo;
    eamTeacherInfo: EamTeacherInfo;
    isChangePassword = false;
    constructor(){
      this.tsstUserInfo  = new TsstUserInfo();
      this.eamStudentInfo  = new EamStudentInfo();
      this.eamTeacherInfo  = new EamTeacherInfo();
    }
  }
