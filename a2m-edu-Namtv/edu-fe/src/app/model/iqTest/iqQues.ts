import { searchModel } from "../search-model";

export class IqQues extends searchModel{

	quesContent?: String;
  category?: String;
  lev?: any
	quesType?: String;
	status?: any;
  answerList?: any[] = [];
  answerCorrect?: any;
  listAnswerCorrect?: any[] = [];
  answerDelList?: any[] = [];
  categoryNm?: String;
  quesTypeNm?: String;
}
