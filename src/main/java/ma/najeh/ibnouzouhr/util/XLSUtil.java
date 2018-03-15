package ma.najeh.ibnouzouhr.util;


import ma.najeh.ibnouzouhr.constant.Constant;
import ma.najeh.ibnouzouhr.model.*;
import ma.najeh.ibnouzouhr.service.StudentService;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by youssef on 12/4/17.
 */
public class XLSUtil {
    static ConcurrentSkipListSet<Student> students = new ConcurrentSkipListSet<>();
    static int onThisRow =0;



    public static Set<Student> getStudent(File file) {
        try {
            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.removeRow(sheet.getRow(0));
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Student student = new Student();
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                int i = 0;
                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    String value = "";
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        value = currentCell.getStringCellValue();
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        value = String.valueOf((int) currentCell.getNumericCellValue());
                    }
                    switch (i) {
                        case 0:
                            student.setCodeAPOGEE(value);
                            break;
                        case 1:
                            student.setFirstInscription(value);
                            break;
                        case 2:
                            student.setCne(value);
                            break;
                        case 3:
                            student.setCin(value.toUpperCase());
                            break;
                        case 4:
                            student.setLastName(value);
                            break;
                        case 5:
                            student.setFirstName(value);
                            break;
                        case 6:
                            student.setLastNameAr(value);
                            break;
                        case 7:
                            student.setFirstNameAr(value);
                            break;
                        case 8:
                            student.setGender(value);
                            break;
                        case 9:
                            student.setHometown(value);
                            break;
                        case 10:
                            if (HSSFDateUtil.isCellDateFormatted(currentCell)) {
                                student.setBirthday(currentCell.getDateCellValue());
                            }
                            break;
                        case 11:
                            student.setHometownAr(value);
                            break;
                        case 12:
                            student.setBirthProvince(value);
                            break;
                        case 13:
                            student.setDiplome(value);
                            break;
                        case 14:
                            student.setEtablissement(value);
                            break;
                        case 15:
                            student.setBirthProvince(value);
                            break;
                        case 16:
                            student.setAdress(value);
                            break;
                    }

                    i++;
                }

                students.add(student);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }



    public static List<Note> getNotes(File file,Integer year, Module module,StudentService studentService) {
        try {
            FileInputStream excelFile = new FileInputStream(file);
            return getNotesFromInputStream(excelFile,year,module,studentService);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<Note> getNotesFromInputStream(InputStream file,Integer year, Module module,StudentService studentService) {
        List<Note> notes = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.removeRow(sheet.getRow(0));
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                int i = 0;
                String codeAPPOGE = "";
                Note n=new Note();
                n.setModule(module);
                n.setYear(year);
                n.setCreatedAt(new Date());
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    switch (i) {
                        case 0:
                            if (currentCell.getCellTypeEnum() == CellType.STRING) {
                                codeAPPOGE = currentCell.getStringCellValue();
                            } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                                Double numberValue = currentCell.getNumericCellValue();
                                codeAPPOGE = String.valueOf(numberValue.intValue());
                            }
                            break;

                        case 3:
                            if (currentCell.getCellTypeEnum() == CellType.STRING) {
                                n.setSession(currentCell.getStringCellValue());
                                if (currentCell.getStringCellValue().toUpperCase().equals("ABJ")){
                                    n.setNote(0.25);
                                }
                                if (currentCell.getStringCellValue().toUpperCase().equals("ABI")){
                                    n.setNote(0.0);
                                }
                            } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                                n.setNote(currentCell.getNumericCellValue());
                                if (n.getNote() >= 10) {
                                    n.setSession("V");
                                } else {
                                    n.setSession("NV");
                                }
                            }
                            if (n.getNote()==null){
                                n.setNote(0.0);
                                n.setSession("NV");
                            }


                            break;
                    }

                    i++;
                }

                Student student = studentService.getByCodeAPOGEE(codeAPPOGE);
                if (Objects.nonNull(student)) {
                    n.setStudent(student);
                    notes.add(n);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return notes;
    }


    public static Set<Teacher> getTeachers(File file) {
        HashSet<Teacher> teachers = new HashSet<>();
        try {
            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.removeRow(sheet.getRow(0));
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Teacher teacher = new Teacher();
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                int i = 0;
                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    String value = "";
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        value = currentCell.getStringCellValue();
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        value = String.valueOf((int) currentCell.getNumericCellValue());
                    }
                    switch (i) {
                        case 1:
                            teacher.setCin(value);
                            break;
                        case 2:
                            teacher.setPpr(value);
                            break;
                        case 3:
                            teacher.setLastName(value);
                            break;
                        case 4:
                            teacher.setFirstName(value);
                            break;
                        case 5:
                            teacher.setFirstNameAr(value);
                            break;
                        case 6:
                            teacher.setGender(value);
                            break;
                        case 7:
                            //teacher.setBirthday(value);
                            break;
                        case 8:
                            teacher.setHometown(value);
                            break;
                        case 9:
                            teacher.setAdress(value);
                            break;
                        case 10:
                            teacher.setEmail(value);
                            break;
                        case 11:
                            teacher.setEmailPro(value);
                            break;
                        case 12:
                            teacher.setPhone(value);
                            break;
                        case 13:
                            //teacher.set(value);
                            break;
                        case 14:
                            //teacher.setAffectationMESRSFC(value);
                            break;
                        case 15:
                            //teacher.setAffectationEnseignement(value);
                            break;
                        case 16:
                            teacher.setDepartement(value);
                            break;
                        case 19:
                            teacher.setSpecialite(value);
                            break;
                        case 20:
                            teacher.setUniversiteDiplomante(value);
                            break;
                        case 21:
                            teacher.setStructureRecherche(value);
                            break;
                        case 22:
                            teacher.setFonctionExercee(value);
                            break;
                        case 23:
                            teacher.setGrade(value);
                            break;
                        case 24:
                            teacher.setAncienneteGrade(value);
                            break;
                        case 25:
                            teacher.setAncienneteEchelon(value);
                            break;

                        case 26:
                            teacher.setCategorie(value);
                            break;
                        case 27:
                            if ("".equals(value))
                                value = "0";
                            teacher.setEchelon(Integer.valueOf(value));
                            break;
                        case 28:
                            if ("".equals(value))
                                value = "0";
                            teacher.setIndice(Integer.valueOf(value));
                            break;
                        case 29:
                            teacher.setAncienneteEchelon(value);
                            break;
                    }

                    i++;
                }

                teachers.add(teacher);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public static Set<Student> getGlobalStudent(File file, List<Branch> branches, PasswordEncoder passwordEncoder) {

        try {
            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.removeRow(sheet.getRow(0));
            Iterator<Row> iterator = sheet.iterator();
            LoopingXlsStudentThread loopingXlsStudentThread1 = new LoopingXlsStudentThread(sheet, branches, passwordEncoder);
            LoopingXlsStudentThread loopingXlsStudentThread2 = new LoopingXlsStudentThread(sheet, branches, passwordEncoder);
            loopingXlsStudentThread1.start();
            loopingXlsStudentThread2.start();

            while (iterator.hasNext()) {
                getRowData(iterator.next(), branches, passwordEncoder);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;

    }


    public static void getRowData(Row currentRow, List<Branch> branches, PasswordEncoder passwordEncoder) {

        if (currentRow.getRowNum() >= onThisRow) {
            onThisRow++;

            Student student = new Student();
            Iterator<Cell> cellIterator = currentRow.iterator();
            int i = 0;
            while (cellIterator.hasNext()) {

                Cell currentCell = cellIterator.next();
                //getCellTypeEnum shown as deprecated for version 3.15
                //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                String value = "";
                if (currentCell.getCellTypeEnum() == CellType.STRING) {
                    value = currentCell.getStringCellValue();
                } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                    value = String.valueOf((int) currentCell.getNumericCellValue());
                }
                switch (i) {
                    case 0:
                        student.setCodeAPOGEE(value);
                        break;
                    case 1:
                        student.setCne(value);
                        break;
                    case 2:
                        student.setLastName(value);
                        break;
                    case 3:
                        student.setFirstName(value);
                        break;
                    case 4:
                        student.setStep(value);
                        String code = value.replaceAll("[^A-Za-z]", "");

                        if (code.contains("AFEET")) {
                            student.setBranch(branches.get(0));
                        } else if (code.contains("AFEEG")) {
                            student.setBranch(branches.get(1));
                        } else if (code.contains("AFEEL")) {
                            student.setBranch(branches.get(2));
                        } else if (code.contains("AFFR")) {
                            student.setBranch(branches.get(3));
                        } else if (code.contains("AFGE")) {
                            student.setBranch(branches.get(4));
                        }


                        break;
                    case 5:
                        student.setGender(value);
                        break;
                    case 6:
                        student.setHometown(value);
                        break;
                    case 7:
                        if (HSSFDateUtil.isCellDateFormatted(currentCell)) {
                            student.setBirthday(currentCell.getDateCellValue());
                        }
                        break;
                    case 8:
                        student.setCin(value);
                        break;
                    case 9:
                        student.setHometownAr(value);
                        break;
                    case 10:
                        student.setLastNameAr(value);
                        break;
                    case 11:
                        student.setFirstNameAr(value);
                        break;
                    case 12:
                        student.setAdress(value);
                        break;

                }

                i++;
            }
            student.setJoinAt(new Date());
            student.setUsername(student.getCodeAPOGEE());
            student.setRole(Constant.ROLE.STUDENT);
            student.setPassword(passwordEncoder.encode(student.getCin()).toCharArray());
            students.add(student);
            System.out.println(Thread.currentThread().getName()+" : "+students.size());
        }

    }

    public static XSSFWorkbook writeReclamations(Iterable<Reclamation> reclamations){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reclamations 2017");

        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        Cell cell = row.createCell(0);
        cell.setCellValue("Id");
        cell = row.createCell(1);
        cell.setCellValue("ModuleDto");
        cell = row.createCell(2);
        cell.setCellValue("Code ModuleDto");
        cell = row.createCell(3);
        cell.setCellValue("Note");
        cell = row.createCell(4);
        cell.setCellValue("Status");
        cell = row.createCell(5);
        cell.setCellValue("Code Appoge");
        cell = row.createCell(6);
        cell.setCellValue("Nom et Prenom");
        cell = row.createCell(7);
        cell.setCellValue("Etat Reclamation");

        System.out.println("Creating excel");


        for (Reclamation reclamation:reclamations) {
            row = sheet.createRow(rowNum++);
            for (int colNum=0;colNum<8;colNum++){
                cell = row.createCell(colNum);
                switch (colNum){
                    case 0:
                        cell.setCellValue(reclamation.getId().intValue());
                        break;
                    case 1:
                        cell.setCellValue(reclamation.getNote().getModule().getName());
                        break;
                    case 2:
                        cell.setCellValue(reclamation.getNote().getModule().getCode());
                        break;
                    case 3:
                        cell.setCellValue((Double) reclamation.getNote().getNote());
                        break;
                    case 4:
                        cell.setCellValue(reclamation.getNote().getSession());
                        break;
                    case 5:
                        cell.setCellValue(reclamation.getStudent().getCodeAPOGEE());
                        break;
                    case 6:
                        cell.setCellValue(reclamation.getStudent().getFullName());
                        break;
                    case 7:
                        cell.setCellValue(reclamation.getState());
                        break;
                }
            }

        }

        return workbook;
    }



}
