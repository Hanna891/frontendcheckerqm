package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PageLuceneWithItemsTest {

    public void checkingSorting(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox PageLuceneWithItemsSorting, TextField inputLucenePage, Text statusInfo, TextField inputSearch, Properties Homepage){
        Platform.runLater(() -> {
            PageLuceneWithItemsSorting.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Lucene Page with Items...");
        });


        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputSearch.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    boolean isAvailable = webDriver.findElementById(Homepage.getProperty("page.search.bar")) != null;
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Homepage.getProperty("page.search.bar"))));
                    WebElement element = webDriver.findElement(By.id(Homepage.getProperty("page.search.bar")));
                    element.sendKeys(inputLucenePage.getText().trim());
                    element.submit();

                    if (webDriver.getCurrentUrl().contains("?q=") ){

                        try{
                            /**wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))));
                            webDriver.findElementByXPath(Homepage.getProperty("lucenepage.sort.dropdown.button")).click();
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))));
                            List<WebElement> DropDownOptions = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sort.dropdown.options"));
                            for (WebElement DropDownItem : DropDownOptions){
                                System.out.println(DropDownItem.getText());
                            }*/
                            try{
                                Platform.runLater(() -> {
                                    statusInfo.setText("Checking Sorting Values from Low to High...");
                                });

                                List<WebElement> ItemsGridPage = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));
                                double checkStartingPriceFirstItem = Double.parseDouble(ItemsGridPage.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                report.writeToFile("Detected : "+ ItemsGridPage.size() + " items on this Page!");
                                report.writeToFile("");

                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))));
                                webDriver.findElement(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))).click();

                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                List<WebElement> DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sort.dropdown.options"));
                                DropDownListActions.get(1).click();
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));

                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                                List<WebElement> ItemsGridPageSortingLowToHigh = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));

                                double checkPriceLowToHighFirstItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                double checkPriceLowToHighLastItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(ItemsGridPageSortingLowToHigh.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                if (checkPriceLowToHighFirstItem < checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=price_asc")){
                                    report.writeToFile("Successful changed Sorting from Lowest to Highest Price!", "");
                                }
                                if (checkPriceLowToHighFirstItem < checkPriceLowToHighLastItem){
                                    report.writeToFile("Checking Lucene Page with Items Price Lowest to Highest: ", "Successful! First Item Price("+checkPriceLowToHighFirstItem+") is lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                                }else {
                                    report.writeToFile("Checking Lucene Page with Items Price Lowest to Highest: ", "Not Successful! First Item Price("+checkPriceLowToHighFirstItem+") is NOT lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                                }
                                report.writeToFile("");





                                Platform.runLater(() -> {
                                    statusInfo.setText("Checking Sorting Values from High to Low...");
                                });

                                //DropDownButtonSorting
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))));
                                webDriver.findElement(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))).click();

                                //DropDownButtonSorting
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sort.dropdown.options"));
                                DropDownListActions.get(2).click();
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));

                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                                List<WebElement> ItemsGridPageSortingHighToLow = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));
                                double checkPriceHighToLowFirstItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                double checkPriceHighToLowLastItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(ItemsGridPageSortingHighToLow.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                if (checkPriceHighToLowFirstItem > checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=price_desc")){
                                    report.writeToFile("Successful changed Sorting from Highest to Lowest Price!", "");
                                }
                                if (checkPriceHighToLowFirstItem > checkPriceHighToLowLastItem){
                                    report.writeToFile("Checking Lucene Page with Items Price Highest to Lowest: ", "Successful! First Item Price("+checkPriceHighToLowFirstItem+") is higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                                }else {
                                    report.writeToFile("Checking Lucene Page with Items Price Highest to Lowest: ", "Not Successful! First Item Price("+checkPriceHighToLowFirstItem+") is NOT higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                                }
                                report.writeToFile("");






                                Platform.runLater(() -> {
                                    statusInfo.setText("Checking Sorting Values Sales Price...");
                                });
                                //DropDownButtonSorting
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))));
                                webDriver.findElement(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))).click();

                                //DropDownButtonSorting
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sort.dropdown.options"));
                                DropDownListActions.get(3).click();
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));

                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                                List<WebElement> ItemsGridPageSortingSale = webDriver.findElementsByXPath(Homepage.getProperty("page.items.salesprice"));
                                double checkPriceItemsGridPageSortingSale = Double.parseDouble(ItemsGridPageSortingSale.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                if (checkPriceItemsGridPageSortingSale != checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=reduced_price_desc")){
                                    report.writeToFile("Successful changed Sorting to Sales!", "");
                                }
                                if (ItemsGridPageSortingSale.size() != 0){
                                    report.writeToFile("Checking Lucene Page with Items Sales Price: ", "Successful! Item contains Sales Price");
                                }else {
                                    report.writeToFile("Checking Lucene Page with Items Sales Price: ", "Not Successful! Couldn't find a item with Sales Price");
                                }
                                report.writeToFile("");





                                Platform.runLater(() -> {
                                    PageLuceneWithItemsSorting.setStyle("-fx-background-color: #CCFF99");
                                    PageLuceneWithItemsSorting.setSelected(true);
                                });
                                report.writeToFile("Checking Lucene Page with Items: ", "Complete!");
                            }catch (Exception noSortingPossible){
                                Platform.runLater(() -> {
                                    PageLuceneWithItemsSorting.setStyle("-fx-background-color: #FF0000");
                                    PageLuceneWithItemsSorting.setSelected(true);
                                });
                                report.writeToFile("Checking Lucene Page with Items: ", "Unable to sort any Items on Lucene Page!");
                                noSortingPossible.printStackTrace();
                            }

                        }catch (Exception noItemsOnLucenePage){
                            Platform.runLater(() -> {
                                PageLuceneWithItemsSorting.setStyle("-fx-background-color: #FF0000");
                                PageLuceneWithItemsSorting.setSelected(true);
                            });
                            report.writeToFile("Checking Lucene Page with Items: ", "Unable to find any Items on Lucene Page!");
                            noItemsOnLucenePage.printStackTrace();
                        }

                    }else{
                        Platform.runLater(() -> {
                            PageLuceneWithItemsSorting.setStyle("-fx-background-color: #FF0000");
                            PageLuceneWithItemsSorting.setSelected(true);
                        });
                        report.writeToFile("Checking Lucene Page with Items: ", "Unable to Complete! Couldn't find Lucene Page");
                    }


                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        PageLuceneWithItemsSorting.setStyle("-fx-background-color: #FF0000");
                        PageLuceneWithItemsSorting.setSelected(true);
                    });
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "GridPageErrorPagingWindows.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Lucene Page with Items Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Lucene Page with Items Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking Lucene Page with Items: ", "Could find any Searchbar");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                Platform.runLater(() -> {
                    PageLuceneWithItemsSorting.setStyle("-fx-background-color: #FF0000");
                    PageLuceneWithItemsSorting.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking Lucene Page with Items: ", "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            Platform.runLater(() -> {
                PageLuceneWithItemsSorting.setStyle("-fx-background-color: #FF0000");
                PageLuceneWithItemsSorting.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking Lucene Page with Items: ", "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}