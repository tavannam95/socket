package com.a2m.gen.services.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.a2m.gen.entities.TccoStd;
import com.a2m.gen.entities.TsstUserInfo;
import com.a2m.gen.models.TsstUserInfoModel;
import com.a2m.gen.models.course.LectureModel;
import com.a2m.gen.models.course.QuizItemAnswerModel;
import com.a2m.gen.models.course.QuizItemModel;
import com.a2m.gen.models.course.QuizModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.course.SubjectStandardModel;
import com.a2m.gen.models.course.SubjectStandardNoteModel;
import com.a2m.gen.models.edu.CandidateModel;
import com.a2m.gen.models.edu.TeacherModel;
import com.a2m.gen.services.course.QuizService;
import com.a2m.gen.utils.CastUtil;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

@Service

public class ExportToPDFService {

    @Autowired
    QuizService quizService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TccoSTDService tccoSTDService;

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${path.upload.dir}")
    private String pathUploadDir;

    @Value("${path.font.times.dir}")
    private String pathFontTimeDir;

    public String exportCandidateToPDF(CandidateModel model) throws Exception {
        String afterDest = UUID.randomUUID().toString();
        // Creating a PdfDocument object
        String dest = pathUploadDir + afterDest + ".pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdf);

        // Creating a table
        float[] pointColumnWidths = { 3, 8, 5, 10, 5, 10, 30 };
        Table table = new Table(pointColumnWidths).useAllAvailableWidth();
        table.setFontSize(8);
        table.setWidthPercent(100);

        // Adding cells to the table
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Name"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Email"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Phone Number"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Progress"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Start Date"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Status"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Message"));

        table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                .add(model.getCandidateName() == null ? "" : model.getCandidateName()));
        table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                .add(model.getCandidateEmail() == null ? "" : model.getCandidateEmail()));
        table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                .add(model.getCandidatePhone() == null ? "" : model.getCandidatePhone()));
        table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                .add(model.getCandidateprogressType() == null ? "" : model.getCandidateprogressType()));
        table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                .add(new SimpleDateFormat("dd-MM-yyyy").format(model.getInsDate()).toString()));
        table.addCell(
                new Cell().setTextAlignment(TextAlignment.CENTER).add(model.getCandidateStatus() ? "Active" : "Block"));
        table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                .add(model.getCandidateMessage() == null ? "" : model.getCandidateMessage()));

        // Adding Table to document
        doc.add(table);

        // Closing the document
        doc.close();
        return dest;
    }

    public String exportCandidateToPDF(List<CandidateModel> list) throws Exception {
        String afterDest = UUID.randomUUID().toString();
        // Creating a PdfDocument object
        String dest = pathUploadDir + afterDest + ".pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdf, PageSize.A4);

        // Creating a table
        float[] pointColumnWidths = { 3, 8, 5, 10, 5, 10, 30 };
        Table table = new Table(pointColumnWidths).useAllAvailableWidth();
        table.setFontSize(8);
        table.setWidthPercent(100);

        // Adding cells to the table
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Name"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Email"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Phone Number"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Progress"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Start Date"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Status"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Message"));
        for (CandidateModel item : list) {

            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(item.getCandidateName() == null ? "" : item.getCandidateName()));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(item.getCandidateEmail() == null ? "" : item.getCandidateEmail()));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(item.getCandidatePhone() == null ? "" : item.getCandidatePhone()));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(item.getCandidateprogressType() == null ? "" : item.getCandidateprogressType()));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(new SimpleDateFormat("dd-MM-yyyy").format(item.getInsDate()).toString()));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(item.getCandidateStatus() ? "Active" : "Block"));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(item.getCandidateMessage() == null ? "" : item.getCandidateMessage()));

        }

        // Adding Table to document
        doc.add(table);

        // Closing the document
        doc.close();
        return dest;
    }

    public String exportQuizToPDF(QuizModel model) throws Exception {
        DocumentPDF tableItex = createTable(model.getQuizNm());
        Document doc = (Document) tableItex.getDoc();
        String afterDest = (String) tableItex.getPath();

        Paragraph quizName = new Paragraph(model.getQuizNm());
//		afterDest += "!#@"+model.getQuizNm();
        quizName.setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(14);
        doc.add(quizName);
        Paragraph quizContent = new Paragraph(model.getQuizContent());
        quizContent.setTextAlignment(TextAlignment.LEFT).setFontSize(12);
        doc.add(quizContent);
//		// Get List Quizz
        List<QuizItemModel> listQuizItem = model.getListQuizItem();
        for (int i = 0; i < listQuizItem.size(); i++) {
            Paragraph quizItem = new Paragraph("Câu hỏi " + (i + 1) + " : " + listQuizItem.get(i).getQuizItemContent());
            quizItem.setItalic();
            doc.add(quizItem);
            if (listQuizItem.get(i).getQuizItemType().equals("08-03")) {
                Paragraph answerA = new Paragraph(" A : " + listQuizItem.get(i).getAnswerA());
                Paragraph answerB = new Paragraph(" B : " + listQuizItem.get(i).getAnswerB());
                Paragraph answerC = new Paragraph(" C : " + listQuizItem.get(i).getAnswerC());
                Paragraph answerD = new Paragraph(" D : " + listQuizItem.get(i).getAnswerD());
                doc.add(answerA).add(answerB).add(answerC).add(answerD);

            } else if (listQuizItem.get(i).getQuizItemType().equals("08-01")) {
                List<QuizItemAnswerModel> listAnswer = listQuizItem.get(i).getListAnswer();
                int index = 0;
                int number = 0;
                int count = 0;
                char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                        'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
                for (QuizItemAnswerModel itemAnswerModel : listAnswer) {
                    if (index >= alphabet.length) {
                        Paragraph answer = new Paragraph(
                                alphabet[count] + "" + (number + 1) + " : " + itemAnswerModel.getAnswerContent());
                        doc.add(answer);
                        count++;
                        if (count >= alphabet.length) {
                            number++;
                            count = 0;
                        }
                    } else {
                        Paragraph answer = new Paragraph(alphabet[index] + " : " + itemAnswerModel.getAnswerContent());
                        doc.add(answer);
                    }
                    index++;
                }
            } else {
                Paragraph answer = new Paragraph("\n\n\n\n\n").setBorder(new SolidBorder(0.5F));
                doc.add(answer);
            }
        }

        // Closing the document
        doc.close();

        return afterDest;
    }

    public String exportRightAnswerToPDF(QuizModel model) throws Exception {
        DocumentPDF tableItex = createTable("Đáp án " + model.getQuizNm());
        Document doc = (Document) tableItex.getDoc();
        String afterDest = (String) tableItex.getPath();

        Paragraph quizName = new Paragraph("Đáp Án " + model.getQuizNm());
        quizName.setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(14);
        doc.add(quizName);
        Paragraph quizContent = new Paragraph(model.getQuizContent());
        quizContent.setTextAlignment(TextAlignment.LEFT).setFontSize(12);
        doc.add(quizContent);
        // Get List Right Answer Quizz
        List<QuizItemModel> listQuizItem = model.getListQuizItem();
        for (int i = 0; i < listQuizItem.size(); i++) {
            Paragraph quizItem = new Paragraph("Câu hỏi " + (i + 1) + " : " + listQuizItem.get(i).getQuizItemContent());
            quizItem.setItalic();
            doc.add(quizItem);
            if (listQuizItem.get(i).getQuizItemType().equals("08-01")) {
                List<QuizItemAnswerModel> listAnswer = listQuizItem.get(i).getListAnswer();
                int index = 0;
                int number = 0;
                int count = 0;
                char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                        'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
                for (QuizItemAnswerModel itemAnswerModel : listAnswer) {
                    if (index >= alphabet.length) {
                        if (itemAnswerModel.getIsAnswerCorrect() == true) {
                            Paragraph answer = new Paragraph(
                                    alphabet[count] + "" + (number + 1) + " : " + itemAnswerModel.getAnswerContent());
                            doc.add(answer);
                        }
                        count++;
                        if (count >= alphabet.length) {
                            number++;
                            count = 0;
                        }
                    } else {
                        if (itemAnswerModel.getIsAnswerCorrect() == true) {
                            Paragraph answer = new Paragraph(
                                    alphabet[index] + " : " + itemAnswerModel.getAnswerContent());
                            doc.add(answer);
                        }
                    }
                    index++;
                }
            } else {
                Paragraph answer = new Paragraph("Tự luận");
                doc.add(answer);
            }

        }

        int count = listQuizItem.size();

        if (count <= 10) {
            Table table = new Table(count);

            int index = 0;
            do {
                table.addCell(new Cell().add("" + (index + 1)));
                index++;
            } while (index < count);

            genResultTable(listQuizItem, table);

            doc.add(table);
        } else {
            int rows = count % 10 == 0 ? count / 10 : (count / 10) + 1;
            int cnt = 0;
            for (int i = 0; i < rows; i++) {
                if (i + 1 != rows) {
                    Table table = new Table(10);

                    for (int j = 0; j < 10; j++) {
                        Paragraph question = new Paragraph("" + (cnt + j + 1));
                        table.addCell(question);
                    }

                    List<QuizItemModel> listTenItem = listQuizItem.subList(cnt, cnt + 10);
                    genResultTable(listTenItem, table);
                    doc.add(new Paragraph(""));

                    doc.add(table);
                    cnt += 10;
                } else {
                    String columnWidths = "52F,";
                    columnWidths = columnWidths.repeat(count % 10);
                    String[] strings = columnWidths.split(",");
                    float[] floats = convertStringArraytoFloatArray(strings);
                    float[] pointColumnWidths = null;
                    Table table = new Table(floats);
                    for (int j = cnt; j < count; j++) {
                        Paragraph question = new Paragraph("" + (j));
                        table.addCell(question);
                    }

                    List<QuizItemModel> listItem = listQuizItem.subList(cnt, count);
                    genResultTable(listItem, table);
                    doc.add(new Paragraph(""));
                    doc.add(table);
                    break;
                }
            }
        }
        doc.close();
        return afterDest;
    }

    public static float[] convertStringArraytoFloatArray(String[] sarray) {
        float[] intarray = null;/* from w ww . ja va 2 s . c o m */

        if (sarray != null) {
            intarray = new float[sarray.length];

            try {
                for (int i = 0; i < sarray.length; i++) {
                    intarray[i] = Float.parseFloat(sarray[i]);
                }
            } catch (NumberFormatException e) {

            }
        }

        return intarray;
    }

    public void genResultTable(List<QuizItemModel> listQuizItem, Table table) {
        for (int i = 0; i < listQuizItem.size(); i++) {
            if (listQuizItem.get(i).getQuizItemType().equals("08-01")) {
                List<QuizItemAnswerModel> listAnswer = listQuizItem.get(i).getListAnswer();
                int indx = 0;
                int num = 0;
                int cnt = 0;
                char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                        'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

                List<QuizItemAnswerModel> collect = listAnswer.stream()
                        .filter(element -> element.getIsAnswerCorrect() == true).collect(Collectors.toList());
                System.out.println(collect.size());
                String answers = "";

                for (QuizItemAnswerModel itemAnswerModel : listAnswer) {
                    if (indx >= alphabet.length) {
                        if (itemAnswerModel.getIsAnswerCorrect() == true) {
                            Paragraph answer = new Paragraph(alphabet[cnt] + "" + (num + 1));
                            table.addCell(answer);
                        }
                        cnt++;
                        if (cnt >= alphabet.length) {
                            num++;
                            cnt = 0;
                        }
                    } else {
                        if (itemAnswerModel.getIsAnswerCorrect() == true && collect.size() > 1) {
                            String ans = alphabet[indx] + ",";
                            answers += ans;
                        } else if (itemAnswerModel.getIsAnswerCorrect() == true) {
                            answers = alphabet[indx] + "";
                        }
                    }
                    indx++;
                }

                if (collect.size() > 1) {

//					Start Remove last character in String 
                    StringBuffer sb = new StringBuffer(answers);
                    sb.deleteCharAt(sb.length() - 1);
//					End Remove last character in String 

                    Paragraph answer = new Paragraph(sb.toString());
                    table.addCell(answer);
                } else {

                    Paragraph answer = new Paragraph(answers);
                    table.addCell(answer);
                }
            } else {
                Paragraph answer = new Paragraph("Tự luận");
                table.addCell(answer);
            }

        }

    }

    public String archiveZip(List<String> pathList, List<String> listDelete) throws Exception {
        String fileName = UUID.randomUUID().toString();
        String dest = pathUploadDir + fileName + ".zip";

        System.out.println("===== dest ======" + dest);

        FileOutputStream fos = new FileOutputStream(dest);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : pathList) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();

        // Delete PDF
        for (String string : listDelete) {
            commonService.deleteFile(string);
        }
        return fileName;
    }

    public String exportPDFScheduleBySubject(List<HashMap<String, Object>> list) throws Exception {
        List<TccoStd> listTcco = tccoSTDService.getCommCdByUpCommCd("12");
        String randomUID = UUID.randomUUID().toString();

        DocumentPDF tableItex = createTable(randomUID);
        Document doc = (Document) tableItex.getDoc();
        String afterDest = (String) tableItex.getPath();

        addTableScheduleBySubject(doc, list);

        // Closing the document
        doc.close();
        return afterDest;
    }

    public void addTableScheduleBySubject(Document doc, List<HashMap<String, Object>> list) {
        List<TccoStd> listTcco = tccoSTDService.getCommCdByUpCommCd("12");
        // Creating a table
        String s = "5-30-10";
        for (TccoStd tcco : listTcco) {
            s += "-10";
        }
        String[] arr = s.split("-");
        float[] pointColumnWidths = new float[arr.length];
        for (int count = 0; count < pointColumnWidths.length; count++) {
            pointColumnWidths[count] = Float.parseFloat(arr[count]);
        }

        Table table = new Table(pointColumnWidths).useAllAvailableWidth();
        table.setFontSize(8);
        table.setWidthPercent(100);

        // Adding cells to the table
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("STT."));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Nội dung"));

        List<Long> lastRows = new ArrayList<Long>();
        for (TccoStd tcco : listTcco) {
            table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(tcco.getCommNmVi()));
            lastRows.add(Long.valueOf(0));
        }

        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Tổng"));

        int index = 1;

        Long totalALL = Long.valueOf(0);
        for (HashMap<String, Object> item : list) {

            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(String.valueOf(index++)));

            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add((String) item.get("chapterNm")));

            HashMap<String, Object> organization = (HashMap<String, Object>) item.get("organizationFormals");
            Long total = Long.valueOf(0);
            int indexTcco = 0;
            for (TccoStd tcco : listTcco) {
                Long value = (Long) organization.get(tcco.getCommCd() + "");
                total += value;
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(String.valueOf(value)));

                lastRows.set(indexTcco, lastRows.get(indexTcco) + value);
                indexTcco++;
            }
            totalALL += total;
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(String.valueOf(total)));

        }

        // footer
        table.addCell(new Cell(1, 2).setTextAlignment(TextAlignment.CENTER).add("Tổng cộng"));

        for (Long value : lastRows) {
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(String.valueOf(value)));
        }

        table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(String.valueOf(totalALL)));
        // Adding Table to document
        doc.add(table);
    }

    public String exportPDFStandSummaryBySubject(SubjectModel model) throws Exception {

        String randomUID = UUID.randomUUID().toString();

        DocumentPDF tableItex = createTable(randomUID);
        Document doc = (Document) tableItex.getDoc();
        String afterDest = (String) tableItex.getPath();

        addTableStandardBySubject(doc, model);

        // Closing the document
        doc.close();
        return afterDest;
    }

    public void addTableStandardBySubject(Document doc, SubjectModel model) {
        List<SubjectStandardModel> listStand = model.getListStand();
        List<SbjChapterModel> listChapter = model.getChapterModels();
        List<SubjectStandardModel> listKnow = new ArrayList<SubjectStandardModel>();
        List<SubjectStandardModel> listSkill = new ArrayList<SubjectStandardModel>();
        List<SubjectStandardModel> listQua = new ArrayList<SubjectStandardModel>();
        for (SubjectStandardModel stand : listStand) {
            String standType = stand.getStandType();
            if (standType.equals("18-01")) {
                listKnow.add(stand);
            } else if (standType.equals("18-02")) {
                listSkill.add(stand);
            } else {
                listQua.add(stand);
            }
        }

        Paragraph standKnow = new Paragraph("1) Về kiến thức: ");
        standKnow.setTextAlignment(TextAlignment.LEFT).setFontSize(10);
        doc.add(standKnow);
        int idx = 1;
        for (int i = 0; i < listStand.size(); i++) {
            if (listStand.get(i).getStandType().equals("18-01")) {
                Paragraph stand = new Paragraph(
                        "CLO" + String.valueOf(idx++) + ": " + listStand.get(i).getStandContent());
                stand.setTextAlignment(TextAlignment.LEFT).setFontSize(10);
                doc.add(stand);
            }
        }

        Paragraph standSkill = new Paragraph("2) Về kỹ năng: ");
        standSkill.setTextAlignment(TextAlignment.LEFT).setFontSize(10);
        doc.add(standSkill);
        for (int i = 0; i < listStand.size(); i++) {
            if (listStand.get(i).getStandType().equals("18-02")) {
                Paragraph stand = new Paragraph(
                        "CLO" + String.valueOf(idx++) + ": " + listStand.get(i).getStandContent());
                stand.setTextAlignment(TextAlignment.LEFT).setFontSize(10);
                doc.add(stand);
            }
        }

        Paragraph standQual = new Paragraph("3) Về phẩm chất: ");
        standQual.setTextAlignment(TextAlignment.LEFT).setFontSize(10);
        doc.add(standQual);
        for (int i = 0; i < listStand.size(); i++) {
            if (listStand.get(i).getStandType().equals("18-03")) {
                Paragraph stand = new Paragraph(
                        "CLO" + String.valueOf(idx++) + ": " + listStand.get(i).getStandContent());
                stand.setTextAlignment(TextAlignment.LEFT).setFontSize(10);
                doc.add(stand);
            }
        }

        Paragraph syntheticStand = new Paragraph(
                "Ghi chú: CLO = Course Learning Outcomes = Chuẩn đầu ra của học phần.");
        doc.add(syntheticStand);

        String s = "5-15";
        float width = 0;
        if (listStand.size() != 0) {
            width = (100 - 5 - 15) / listStand.size();
        }
        for (SubjectStandardModel stand : listStand) {
            s += "-" + width + "";
        }

        String[] arr = s.split("-");
        float[] pointColumnWidths = new float[arr.length];
        for (int count = 0; count < pointColumnWidths.length; count++) {
            pointColumnWidths[count] = Float.parseFloat(arr[count]);
        }

        Table table = new Table(pointColumnWidths).useAllAvailableWidth();

        // Adding cells to the table
        table.addHeaderCell(new Cell(2, 1).setTextAlignment(TextAlignment.CENTER).add("STT."));
        table.addHeaderCell(new Cell(2, 1).setTextAlignment(TextAlignment.CENTER).add("Nội dung"));
        table.addHeaderCell(new Cell(1, listKnow.size()).setTextAlignment(TextAlignment.CENTER).add("Knowledge"));
        table.addHeaderCell(new Cell(1, listSkill.size()).setTextAlignment(TextAlignment.CENTER).add("Skill"));
        table.addHeaderCell(new Cell(1, listQua.size()).setTextAlignment(TextAlignment.CENTER).add("Quality"));

        int idxClo = 1;
        for (SubjectStandardModel stand : listStand) {
            table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(stand.getStandCode() == null ? "" : stand.getStandCode() + " " + String.valueOf(idxClo++)));
        }

//        
        int index = 1;
        for (SbjChapterModel item : listChapter) {
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(String.valueOf(index++)));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(item.getChapterNm() == null ? "" : item.getChapterNm()));

            List<SubjectStandardNoteModel> listStandNote = item.getListStandNote();
            for (SubjectStandardNoteModel note : listStandNote) {
                for (SubjectStandardModel stand : listStand) {
                    if (note.getStandId() == stand.getKey()) {
                        stand.setStandResult(note.getStandResult());
                    }
                }
            }
            for (SubjectStandardModel stand : listStand) {
                String commCd = stand.getStandResult();
                if (commCd.equals("13-01")) {
                    commCd = "A";
                }
                if (commCd.equals("13-02")) {
                    commCd = "B";
                }
                if (commCd.equals("13-03")) {
                    commCd = "C";
                }
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(commCd));
            }
        }

        // Adding Table to document
        doc.add(table);
        Paragraph note = new Paragraph("Chú thích: ");
        note.setTextAlignment(TextAlignment.LEFT).setBold().setFontSize(10);
        doc.add(note);
        List<TccoStd> listTcco = tccoSTDService.getCommCdByUpCommCd("13");
        for (int i = 0; i < listTcco.size(); i++) {
            Paragraph rank = new Paragraph("Bậc " + (i + 1) + ": " + listTcco.get(i).getDescription() + " ("
                    + listTcco.get(i).getCommNm() + ")");
            doc.add(rank);
        }
    }

    public void addTeacherInfo(Document doc, SubjectModel model) {
        TsstUserInfo tsstUserInfo = model.getTsstUser().getTsstUserInfo();
        TsstUserInfoModel userInfoModel = new TsstUserInfoModel(tsstUserInfo);
        TeacherModel teacherModel = model.getTeacherModel();

        Paragraph teaTitle = new Paragraph("1.Thông tin về giảng viên");
        teaTitle.setTextAlignment(TextAlignment.LEFT).setBold().setFontSize(12);
        doc.add(teaTitle);
        // info teacher
        Paragraph tea = new Paragraph("1.1. Giảng viên:");
        tea.setTextAlignment(TextAlignment.LEFT).setFontSize(12);
        doc.add(tea);
        Paragraph teaName = new Paragraph("- Họ và tên: " + teacherModel.getFullName());
        doc.add(teaName);
        Paragraph teaPosition = new Paragraph("- Chức danh: Giảng viên");
        doc.add(teaPosition);
        Paragraph teaAddress = new Paragraph("- Địa chỉ liên hệ: " + teacherModel.getAddress());
        doc.add(teaAddress);
        Paragraph teaPhone = new Paragraph("- Điện thoại: " + teacherModel.getCellPhone());
        doc.add(teaPhone);
        Paragraph teaEmail = new Paragraph("- Họ và tên: " + teacherModel.getEmail());
        doc.add(teaEmail);

        // info assist
        Paragraph assist = new Paragraph("1.2. Trợ giảng:");
        assist.setTextAlignment(TextAlignment.LEFT).setFontSize(12);
        doc.add(assist);
        Paragraph assistName = new Paragraph("- Họ và tên: " + userInfoModel.getFullName());
        doc.add(assistName);
        Paragraph assistPosition = new Paragraph("- Chức danh: Trợ giảng");
        doc.add(assistPosition);
        Paragraph assistAddress = new Paragraph("- Địa chỉ liên hệ: " + userInfoModel.getAddress());
        doc.add(assistAddress);
        Paragraph assistPhone = new Paragraph("- Điện thoại: " + userInfoModel.getPhone());
        doc.add(assistPhone);
        Paragraph assistEmail = new Paragraph("- Họ và tên: " + userInfoModel.getEmail());
        doc.add(assistEmail);
    }

    public void addGoalSubject(Document doc, SubjectModel model) {

        Paragraph subGoal = new Paragraph("2. Mục tiêu môn học:");
        subGoal.setTextAlignment(TextAlignment.LEFT).setBold().setFontSize(12);
        doc.add(subGoal);
        Paragraph goalTitle = new Paragraph("2.1 Mục tiêu chung");
        goalTitle.setTextAlignment(TextAlignment.LEFT).setFontSize(12);
        doc.add(goalTitle);
        Paragraph goalContent = new Paragraph(model.getSubjectGoal());
        doc.add(goalContent);

    }

    public void addListChapter(Document doc, SubjectModel model) {
        Paragraph chapterTitle = new Paragraph("3. Nội dung môn học");
        chapterTitle.setTextAlignment(TextAlignment.LEFT).setBold().setFontSize(12);
        doc.add(chapterTitle);
        List<SbjChapterModel> chapterModel = model.getChapterModels();
        for (int i = 0; i < chapterModel.size(); i++) {
            Paragraph chapterContent = new Paragraph("Chương " + (i + 1) + ": " + chapterModel.get(i).getChapterNm());
            doc.add(chapterContent);
        }
        Paragraph noteChapter = new Paragraph("Chi tiết xem thêm phần kế hoạch giảng dạy");
        doc.add(noteChapter);
    }

    public void addTableDetailedSchedule(Document doc, SubjectModel model) {
        List<SbjChapterModel> listChapter = model.getChapterModels();

        float[] pointColumnWidths = { 5, 15, 80 };
        Table table = new Table(pointColumnWidths).useAllAvailableWidth();
        table.setFontSize(8);
        table.setWidthPercent(100);

        // Adding cells to the table
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("STT."));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Nội dung"));
        table.addHeaderCell(new Cell().setTextAlignment(TextAlignment.CENTER).add("Nội dung chi tiết"));
        int index = 1;
//        

//        Document docChapter = (Document) tableItex.getDoc();
        for (SbjChapterModel item : listChapter) {
            List<LectureModel> listLecture = item.getLectureModels();
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(String.valueOf(index)));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                    .add(item.getChapterNm() == null ? "" : item.getChapterNm()));
            Cell cell1 = new Cell();
            Paragraph subject = new Paragraph("Chương " + String.valueOf(index) + ". " + item.getChapterNm());
            cell1.setTextAlignment(TextAlignment.LEFT).add(subject);
            for (int i = 0; i < listLecture.size(); i++) {
                Paragraph lecture = new Paragraph(
                        String.valueOf(index) + "." + (i + 1) + ". " + listLecture.get(i).getLectureNm());
                cell1.setTextAlignment(TextAlignment.LEFT).add(lecture);

            }
            table.addCell(cell1);
            index++;
        }

        // Adding Table to document
        doc.add(table);
    }

    public void addPolicySubject(Document doc, SubjectModel model) {

        Paragraph policyTitle = new Paragraph("5. Chính sách đối với môn học");
        policyTitle.setTextAlignment(TextAlignment.LEFT).setBold().setFontSize(12);
        doc.add(policyTitle);
        Paragraph policy1 = new Paragraph(
                "- Sinh viên vắng mắt quá 20% số buổi sẽ không được làm bài thi kết thúc học phần.");
        doc.add(policy1);
        Paragraph policy2 = new Paragraph(
                "- Có điểm thưởng cho sinh viên tích cực phát biểu, ham học hỏi, có sự sáng tạo trong thảo luận, tranh luân.");
        doc.add(policy2);

    }

    public void addTestMethod(Document doc, SubjectModel model) {

        Paragraph methodTitle = new Paragraph("6. Phương pháp, hình thức kiểm tra - đángh giá kết quả học tập môn học");
        methodTitle.setTextAlignment(TextAlignment.LEFT).setBold().setFontSize(12);
        doc.add(methodTitle);
        Paragraph method1 = new Paragraph("- Bài kiểm tra trong kỳ, bài thi hết học phần: Thực hành");
        doc.add(method1);
        Paragraph method2 = new Paragraph("- Mô tả chi tiết:");
        doc.add(method2);
        Paragraph contentMethod1 = new Paragraph(
                "(1) Sinh viên sẽ làm 2 bài kiểm tra viết 60 phút vào giữa kỳ và cuối kỳ trong phạm vi nội dung đã được học");
        doc.add(contentMethod1);
        Paragraph contentMethod2 = new Paragraph(
                "(2) Sinh viên sẽ làm bài thi kết thúc học phần theo hình thức thực hành.");
        doc.add(contentMethod2);
        Paragraph contentMethod3 = new Paragraph(
                "(3) Các bài kiểm tra thực hành này thể hiện kết quả học tập trên lớp, tự học, thực hành, làm bài tập mà sinh viên đã thực hiện cũng như khả năng vận dụng những kiến thức đã được học.");
        doc.add(contentMethod3);

    }

    public DocumentPDF createTable(String name) throws Exception {
        String afterDest = name.replaceAll("[\\-\\+\\.\\!\\@\\#\\$\\%\\&\\*\\(\\)\\`\\~\\;\\/\\|\\?\\^:,]", "");
        
        //add file local
//        File files = new File(pathUploadDir + afterDest);
//        if (!files.exists()) {
//            if (files.mkdirs()) {
//                System.out.println("Multiple directories are created!");
//            } else {
//                System.out.println("Failed to create multiple directories!");
//            }
//        }
        
        // Creating a PdfDocument object
        String dest = pathUploadDir + afterDest + ".pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdf, PageSize.A4);

        // Custom Font

        String absolutePath = "";

        if (CastUtil.castToString(pathFontTimeDir).equals("")) {
            Resource resource = resourceLoader.getResource("classpath:fonts/times.ttf");
            File dbAsFile = resource.getFile();
            absolutePath = dbAsFile.getAbsolutePath();
            System.out.println(" ===== resource12 ===== " + absolutePath);

//          URL resource1 = getClass().getClassLoader().getResource("fonts/times.ttf");
//          File file = Paths.get(resource1.toURI()).toFile();
//          String absolutePath1 = file.getAbsolutePath();
//          System.out.println(" ===== resource ===== " + absolutePath1);
        } else {
            absolutePath = pathFontTimeDir;
        }

        PdfFont vntime = PdfFontFactory.createFont(absolutePath, PdfEncodings.IDENTITY_H, true);

        doc.setFont(vntime);
        DocumentPDF result = new DocumentPDF();
        result.setDoc(doc);
        result.setPath(afterDest);
        return result;
    }

    public class DocumentPDF {
        private String path;
        private Document doc;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Document getDoc() {
            return doc;
        }

        public void setDoc(Document doc) {
            this.doc = doc;
        }

    }

    public String exportPDFSubject(List<HashMap<String, Object>> list, SubjectModel model) throws Exception {
        String randomUID = UUID.randomUUID().toString();

        DocumentPDF tableItex = createTable(randomUID);
        Document doc = (Document) tableItex.getDoc();
        String afterDest = (String) tableItex.getPath();

        Paragraph title1 = new Paragraph("ĐỀ CƯƠNG MÔN HỌC");
        title1.setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(14);

        Paragraph title2 = new Paragraph(model.getSubjectNm());
        title2.setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(14);
        doc.add(title1);
        doc.add(title2);

        // 1
        addTeacherInfo(doc, model);

        // 2
        addGoalSubject(doc, model);
        Paragraph graph1 = new Paragraph("2.2: Mục tiêu chi tiết với chuẩn đầu ra");
        graph1.setTextAlignment(TextAlignment.LEFT).setFontSize(12);
        doc.add(graph1);
        Paragraph content1 = new Paragraph(
                "Chuẩn đầu ta của học phần là kiến thức, kỹ năng, thái độ, hành vi cần đạt được của sinh viên sau khi kế thúc học phần.");
        doc.add(content1);
        Paragraph content2 = new Paragraph("Sau khi học xong học phần, sinh viên có thể:");
        doc.add(content2);
        addTableStandardBySubject(doc, model);

        // 3
        addListChapter(doc, model);

        // 4
        Paragraph grapContent1 = new Paragraph("4. Hình thức tổ chức dạy học (kế hoạch dạy học)");
        grapContent1.setTextAlignment(TextAlignment.LEFT).setFontSize(12);
        doc.add(grapContent1);
        Paragraph graph2 = new Paragraph("4.1 Lịch trình chung");
        graph2.setTextAlignment(TextAlignment.LEFT).setFontSize(12);
        doc.add(graph2);
        addTableScheduleBySubject(doc, list);
        Paragraph graph3 = new Paragraph("4.2 Lịch trình chi tiết");
        graph3.setTextAlignment(TextAlignment.LEFT).setFontSize(12);
        doc.add(graph3);
        addTableDetailedSchedule(doc, model);

        // 5
        addPolicySubject(doc, model);

        // 6
        addTestMethod(doc, model);
        // Closing the document
        doc.close();
        return afterDest;
    }

    public String exportWordSubject(String path) throws Exception {
        return "";
    }
}
