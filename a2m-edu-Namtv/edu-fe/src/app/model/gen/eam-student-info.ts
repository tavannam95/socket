import { TsstUser } from "../sys/tsst-user"

export class EamStudentInfo{
  id?: number;
  fullName?: string;
  dob: any;
  email?: string;
  cellPhone?: string;
  address?: string;
  gender?: boolean;
  imgPath?: string;
  twofaEnable?: boolean;
  organization?: string;
  position?: string;
  emailVerifyKey?:String;
  twoFAKey?:String;
  tsstUser?: TsstUser;
  deleteFlag?: boolean;
  typicalFlag?: boolean;
  idea?: string;
}
