package com.mockaroo;

import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import java.io.Closeable.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation {

	WebDriver driver;
	
		@BeforeClass
		public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		driver.get("https://mockaroo.com/");
		driver.manage().window().maximize();
		
	}
		@Test(priority=1)
		public void validateDisplayed() {
			// verify title
			String actual = driver.getTitle();
			String expected = "Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel";
		    Assert.assertEquals(actual, expected);
		    // check if brand and tagLine is displayed
		    boolean brandIsDisplayed = driver.findElement(By.xpath("//div[@class='brand']")).isDisplayed();
			Assert.assertTrue(brandIsDisplayed);
			boolean tagLineDisplayed = driver.findElement(By.xpath("//div[@class='tagline']")).isDisplayed();
			Assert.assertTrue(tagLineDisplayed);
			// check if name, type and options are displayed
			boolean nameIsDisplayed = driver.findElement(By.xpath("//div[@class='column column-header column-name']")).isDisplayed();
			Assert.assertTrue(nameIsDisplayed);
			boolean typeIsDisplayed = driver.findElement(By.xpath("//div[@class='column column-header column-type']")).isDisplayed();
			Assert.assertTrue(typeIsDisplayed);
			boolean optionsIsDisplayed = driver.findElement(By.xpath("//div[@class='column column-header column-options']")).isDisplayed();
			Assert.assertTrue(optionsIsDisplayed);
		}
		@Test(priority=2)
		public void removeFields() {
			List <WebElement> links = driver.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));
		   
			for(int i=0; i<links.size(); i++) {
				links.get(i).click();
			}
			driver.findElement(By.xpath("//div[@class='column-fields']/div[@class='table-body']/a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		}

		@Test(priority=3)
		public void downloadAndData() throws Exception {
			// click to download button
			driver.findElement(By.id("download")).click(); 
			// Open the downloaded file
			String filename = "C:\\Users\\darin\\Downloads\\MOCK_DATA.csv";
			
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			reader.read();
			// store the the expected fields 
			Set<String> rows = new HashSet<>();
			rows.add("City");
			rows.add("Country");
			
			
		}
		@Test // read the value from a cell in Excel 
		public static String getcellValue(int testRowNo, int colNo)
	    {
	        String projectPath = System.getProperty("user.dir");
	        String excelPath = projectPath + "/TestSet.xlsx";
	        File excel = new File(excelPath);
	        FileInputStream fis = null;
	        Workbook workBook = null;
	        String cellValue = null;
	        
	        try
	        {
	            fis = new FileInputStream(excel);
	            workBook = WorkbookFactory.create(fis);
	            Sheet workSheet = workBook.getSheet(sheetName);
	            int totalRows = workSheet.getLastRowNum();
	            Row row = null;
	            cellValue  = workSheet.getRow(testRowNo).getCell(colNo).getStringCellValue();


	        } catch (InvalidFormatException e)
	        {
	            e.printStackTrace();
	        } catch (IOException e)
	        {
	            e.printStackTrace();
	        }finally
	        {
	            try
	            {
	                fis.close();
	            } catch (IOException e)
	            {
	                e.printStackTrace();
	            }
	        }
	        return cellValue;
	    }
		// store the Excel data into List
		
//			List<String> records = new ArrayList<String>();
//			  try
//			  {
//			    BufferedReader reader = new BufferedReader(new FileReader(filename));
//			    
//			    String line;
//			    while ((line = reader.readLine()) != null)
//			    {
//			      records.add(line);
//			    }
//			    reader.close();
//			  }
//			  catch (Exception e)
//			  {
//			    System.err.format("Exception occurred to read '%s'.", filename);
//			    e.printStackTrace();
//			  }
//			  System.out.println(records);
		
		
	
		
		@AfterClass
		public void close() {
			//driver.close();
		}

		
		
		
		
		
		
		
}
