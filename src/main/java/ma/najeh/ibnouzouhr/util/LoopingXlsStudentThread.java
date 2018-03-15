package ma.najeh.ibnouzouhr.util;

import ma.najeh.ibnouzouhr.constant.Constant;
import ma.najeh.ibnouzouhr.model.Branch;
import ma.najeh.ibnouzouhr.model.Student;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by youssef on 12/27/17.
 */
public class LoopingXlsStudentThread extends Thread {

    private Sheet sheet;
    private List<Branch> branches;
    private PasswordEncoder passwordEncoder;

    public LoopingXlsStudentThread(Sheet sheet, List<Branch> branches, PasswordEncoder passwordEncoder) {
        this.sheet = sheet;
        this.branches = branches;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run() {
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            XLSUtil.getRowData(iterator.next(),branches,passwordEncoder);
        }
    }





}
