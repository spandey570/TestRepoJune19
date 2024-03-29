package com.ttn.bflframework.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExcelUtils {

    public static FileInputStream inputStream = null;
    public static Workbook wb = null;
    public static File file = null;
    public static Row row = null;
    public static Sheet sheet = null;

    public static void getSheetData(String filePath, String fileName, String sheetName) throws IOException {

        InitializeExcel(filePath, fileName, sheetName);

        //Read sheet inside the workbook by its name

        sheet = wb.getSheet(sheetName);

        //Find number of rows in excel file

        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        //Create a loop over all the rows of excel file to read it

        for (int i = 0; i < rowCount + 1; i++) {

            Row row = sheet.getRow(i);

            //Create a loop to print cell values in a row

            for (int j = 0; j < row.getLastCellNum(); j++) {

                //Print Excel data in console

                System.out.print(row.getCell(j).getStringCellValue() + "|| ");

            }

            System.out.println();
        }

    }

    public static void setCellValue(String filePath, String fileName, String sheetName, String dataToWrite) throws IOException {

        InitializeExcel(filePath, fileName, sheetName);
        //Read excel sheet by sheet name

        Sheet sh = wb.getSheet(sheetName);

        //Get the current count of rows in excel file

        int rowCount = sh.getLastRowNum();

        //Get the first row from the sheet

        Row rw = sh.getRow(0);

        //Create a new row and append it at last of sheet

        Row newRow = sh.createRow(rowCount + 1);

        //Create a loop over the cell of newly created Row

        for (int j = 0; j < rw.getLastCellNum(); j++) {

            //Fill data in row

            Cell cl = newRow.createCell(j);

            cl.setCellValue(dataToWrite);

        }

        //Close input stream

        inputStream.close();

        //Create an object of FileOutputStream class to create write data in excel file

        FileOutputStream fos = new FileOutputStream(file);

        //write data in the excel file

        wb.write(fos);

        //close output stream

        fos.close();

    }

    public static void InitializeExcel(String filePath, String fileName, String sheetName) {
        file = new File(filePath + "\\" + fileName);

        //Create an object of FileInputStream class to read excel file

        //   FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Find the file extension by splitting  file name in substring and getting only extension name

        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file

        if (fileExtensionName.equals(".xlsx")) {

            //If it is xlsx file then create object of XSSFWorkbook class

            try {
                wb = new XSSFWorkbook(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //Check condition if the file is xls file

        else if (fileExtensionName.equals(".xls")) {

            //If it is xls file then create object of XSSFWorkbook class

            try {
                wb = new HSSFWorkbook(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public static String getCellValue(String filePath, String fileName, String sheetName, String primaryKey, String columnName) throws IOException {

        //Find Column Index on the basis of Column Name
        int index = getColumnIndex(filePath, fileName, sheetName, columnName);


        //Find number of rows in excel file

        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        //Create a loop over all the rows of excel file to read it
        outerloop:
        for (int i = 0; i < rowCount + 1; i++) {

            row = sheet.getRow(i);

            //Create a loop to print cell values in a row

            for (int j = 0; j < row.getLastCellNum(); j++) {

                if (row.getCell(j).getStringCellValue().equalsIgnoreCase(primaryKey)) {

                    break outerloop;
                }
            }
        }
        if (index > 0) {
            return row.getCell(index).getStringCellValue();
        } else {
            return "Column name or primary key is invalid";
        }
    }


    public static void updateCellValue(String filePath, String fileName, String sheetName, String primaryKey, String columnName, String dataToWrite) throws IOException {

        //Find Column Index on the basis of Column Name
        int index = getColumnIndex(filePath, fileName, sheetName, columnName);

        //Find number of rows in excel file

        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        //Create a loop over all the rows of excel file to read it
        outerloop:
        for (int i = 0; i < rowCount + 1; i++) {

            Row row = sheet.getRow(i);

            //Create a loop to print cell values in a row

            for (int j = 0; j < row.getLastCellNum(); j++) {

                //Print Excel data in console

                // System.out.print(row.getCell(j).getStringCellValue()+"|| ");
                if (row.getCell(j).getStringCellValue().equalsIgnoreCase(primaryKey)) {

                    row.getCell(index).setCellValue(dataToWrite);

                    inputStream.close();

                    //Create an object of FileOutputStream class to create write data in excel file

                    FileOutputStream fos = new FileOutputStream(file);

                    //write data in the excel file

                    wb.write(fos);

                    //close output stream

                    fos.close();

                    break outerloop;

                }

            }

        }
    }


    public static int getColumnIndex(String filePath, String fileName, String sheetName, String columnName) {
        InitializeExcel(filePath, fileName, sheetName);
        sheet = wb.getSheet(sheetName);
        row = sheet.getRow(0);
        //Create a loop to print cell values in a row
        int j;
        int i = 0;
        innerloop:
        for (j = 0; j < row.getLastCellNum(); j++) {
            if (row.getCell(j).getStringCellValue().equalsIgnoreCase(columnName)) {
                i = j;
                break innerloop;
            } else {
                i = -1;
            }
        }
        return i;
    }

    public static ArrayList<String> getColumnValue(String filePath, String fileName, String sheetName, String columnName) {
        ArrayList<String> colmunVal = new ArrayList<>();
        //Find Column Index on the basis of Column Name
        int index = getColumnIndex(filePath, fileName, sheetName, columnName);
        String value = null;

        //Find number of rows in excel file
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        for (int i = 1; i < rowCount + 1; i++) {
            row = sheet.getRow(i);
            Cell cell = row.getCell(index);

            if (DateUtil.isCellDateFormatted(cell)) {

                Date dateValue = cell.getDateCellValue();
                System.out.println(dateValue);
                SimpleDateFormat sdf = new SimpleDateFormat();
                String date = sdf.format(dateValue);
                colmunVal.add(date);
            } else {
                cell.setCellType(CellType.STRING);
                value = cell.getStringCellValue();
                colmunVal.add(value);
            }
            }
        return colmunVal;
    }

    public static int getRowCount(String filePath, String fileName, String sheetName) throws IOException {

        InitializeExcel(filePath, fileName, sheetName);

        //Read sheet inside the workbook by its name

        sheet = wb.getSheet(sheetName);

        //Find number of rows in excel file

        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        return rowCount;
    }

    public static int getColumnCount(String filePath, String fileName, String sheetName, int rowCount) throws IOException {

        row = null;

        for (int i = 0; i < rowCount + 1; i++) {

            row = sheet.getRow(i);



        }
        return row.getLastCellNum();
    }

    public static ArrayList<String> getStringCellValue(String filePath, String fileName, String sheetName) throws IOException {
        int rowCount = getRowCount(filePath, fileName, sheetName);
        int columnCount = getColumnCount(filePath, fileName, sheetName, rowCount);
        ArrayList<String> val = new ArrayList<>();
        String value = null;

        for (int i = 1; i < rowCount + 1; i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < columnCount; j++) {

                //Print Excel data in console

                Cell cell = row.getCell(j);
                cell.setCellType(CellType.STRING);
                value= cell.getStringCellValue();
                val.add(value);


            }

        }
        return val;
    }

    public static int getRowNumber(String filePath, String fileName, String sheetName, String data){

        InitializeExcel(filePath, fileName, sheetName);

        //Read sheet inside the workbook by its name

        sheet = wb.getSheet(sheetName);
        //Find number of rows in excel file

        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        int i;
        outerloop:
        for (i = 0; i < rowCount + 1; i++) {

            row = sheet.getRow(i);

            for (int j = 0; j < row.getLastCellNum(); j++) {

                Cell cell = row.getCell(j);
                cell.setCellType(CellType.STRING);

                if (cell.getStringCellValue().equalsIgnoreCase(data)) {

                    break outerloop;
                }
            }
        }

        return i;
    }


}