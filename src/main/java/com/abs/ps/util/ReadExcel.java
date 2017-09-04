package com.abs.ps.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ReadExcel {
	private int startIdx = 0;
	public void setStartIdx(int startIdx) {
		this.startIdx = startIdx;
	}
	public List<List<Object>> readExcel(InputStream in, String fileName) throws IOException {
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return read2003Excel(in);
		} else if ("xlsx".equals(extension)) {
			return read2007Excel(in);
		} else {
			throw new IOException("readExcel exception.");
		}
	}
	
	private List<List<Object>> read2003Excel(InputStream in)
			throws IOException {
		List<List<Object>> list = new ArrayList<List<Object>>();
		HSSFWorkbook hwb = new HSSFWorkbook(in);
		HSSFSheet sheet = hwb.getSheetAt(0);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		for (int i = startIdx; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} 
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					//linked.add("");
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");//
													
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 
				DecimalFormat nf = new DecimalFormat("0.00");
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				default:
					value = cell.toString();
				}

				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}
	
	private List<List<Object>> read2007Excel(InputStream in)
			throws IOException {
		List<List<Object>> list = new ArrayList<List<Object>>();

		XSSFWorkbook xwb = new XSSFWorkbook(in);

		XSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;

		for (int i = startIdx; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					//linked.add("");
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");//
															// 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 
				DecimalFormat nf = new DecimalFormat("0.00");// 
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString()) || "yyyy/m/d;@".equals(cell.getCellStyle().getDataFormatString())) {
						value = sdf.format(cell.getNumericCellValue());
					} else {
						value = nf.format(cell.getNumericCellValue());
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				default:
					value = cell.toString();
				}

				linked.add(value);
			}
			list.add(linked);
		}

		return list;
	}
	
}
