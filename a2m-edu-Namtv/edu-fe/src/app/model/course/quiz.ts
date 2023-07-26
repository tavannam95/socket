import { searchModel } from "../search-model";

export class Quiz extends searchModel{
  chapterId?: number;
  subjectId?: number;
  courseId?: number;

	quizNm?: String;
	quizContent?: String;
	quizType?: String;
	ordNo?: number;
	status?: any;
  quizItemlist?: any;
}