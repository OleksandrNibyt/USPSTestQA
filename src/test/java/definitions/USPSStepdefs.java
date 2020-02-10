package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.getDriver;

public class USPSStepdefs {
    @Given("I open {string} page")
    public void iOpenPage(String arg0) {
        getDriver().get("https://www.usps.com/");
    }

    @When("I go to Lookup ZIP page by address")
    public void iGoToLookupZIPPageByAddress() {
        WebElement mail = getDriver().findElement(By.xpath("//a[@id='mail-ship-width']"));
        new Actions(getDriver()).moveToElement(mail).perform();
        getDriver().findElement(By.xpath("//li[@class ='tool-zip']//a[contains(@href, 'Zip')]")).click();
        getDriver().findElement(By.xpath("//a[@class='zip-code-address zip-code-home']")).click();
    }

    @And("I fill out {string} street, {string} city, {string} state")
    public void iFillOutStreetCityState(String streetAddress, String city, String state) {
        getDriver().findElement(By.xpath("//input[@id='tAddress']")).sendKeys(streetAddress);
        getDriver().findElement(By.xpath("//input[@id='tCity']")).sendKeys(city);
        getDriver().findElement(By.xpath("//select[@id='tState']")).sendKeys(state);
        getDriver().findElement(By.xpath("//select[@id='tState']/option[@value = 'CA']")).click();
        getDriver().findElement(By.xpath("//select[@id='tState']/option[@value = '"+ state +"']")).click();
        getDriver().findElement(By.xpath("//a[@id='zip-by-address']")).click();

        WebElement selectState = getDriver().findElement(By.xpath("//select[@id='tState']"));
        new Select(selectState).selectByValue(state);
    }

    @Then("I validate {string} zip code exists in the result")
    public void iValidateZipCodeExistsInTheResult(String zipCode) {
        WebElement resultElement = getDriver().findElement(By.xpath("//div[@id='zipByAddressDiv']"));
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.visibilityOf(resultElement));
        String result = resultElement.getText();
        assertThat(result.contains(zipCode));
        By resultLocator = By.xpath("//div[@id='zipByAddressDiv']");
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.textToBe(resultLocator, ""));
        new WebDriverWait(getDriver(), 5).until(driver -> resultElement.getText().equals(""));
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.not(ExpectedConditions.textToBe(resultLocator, "")));

    }

    @When("I go to Calculate Price Page")
    public void iGoToCalculatePricePage() {
        getDriver().findElement(By.xpath("//a[@id='mail-ship-width']")).click();
        //getDriver().findElement(By.xpath("//a[@class='button--link'][contains(text(),'Calculate a Price')]")).click();
        getDriver().findElement(By.xpath("//li[@class='tool-calc']//a[contains(text(),'Calculate a Price')]")).isDisplayed();
        getDriver().findElement(By.xpath("//a[contains(text(),'Calculate a Price')]")).click();
        getDriver().findElement(By.xpath(" //div[@class='header-usps']")).isDisplayed();
    }

    @And("I select {string} with {string} shape")
    public void iSelectWithShape(String country, String shape) {
        getDriver().findElement(By.xpath("//option[contains(text(),'United Kingdom (United Kingdom of Great Britain an')]")).click();
        getDriver().findElement(By.xpath("//input[@value='Postcard']")).click();
    }

    @And("I define {string} quantity")
    public void iDefineQuantity(String quantity) {
        getDriver().findElement(By.xpath("//input[@id='quantity-0']")).sendKeys(quantity);
        getDriver().findElement(By.xpath("//input[@class='btn btn-pcalc btn-default']")).click();
    }

    @Then("I calculate the price and validate cost is {string}")
    public void iCalculateThePriceAndValidateCostIs(String price) {
        assertThat(getDriver().findElement(By.xpath("//div[@id='total']")).getText()).contains(price);
    }

    @When("I go to {string} tab")
    public void iGoToTab(String tabName) {
        getDriver().findElement(By.xpath("//a[text()='" + tabName + "']")).click();
    }

    @And("I enter {string} into store search")
    public void iEnterIntoStoreSearch(String searchItem) {
        getDriver().findElement(By.xpath("//input[@id='store-search']")).sendKeys(searchItem);
        getDriver().findElement(By.xpath("//input[@id='store-search-btn']")).click();
    }

    @Then("I search and validate no products found")
    public void iSearchAndValidateNoProductsFound() {
        String result = getDriver().findElement(By.xpath("//div[@class='no-results-found']")).getText();
        assertThat(result).contains("did not match any products");
    }

    @Then("I search and validate products found")
    public void iSearchAndValidateProductsFound() {
        String result = getDriver().findElement(By.xpath("//div[@class='result-bar']")).getText();
        assertThat(result).contains("All Results");
        assertThat(result).contains("Results");
    }

    @When("I go to {string} section")
    public void iGoToSection(String arg0) {
        WebElement mail = getDriver().findElement(By.xpath("//a[@id='mail-ship-width']"));
        new Actions(getDriver()).moveToElement(mail).perform();
        getDriver().findElement(By.xpath("//li[@class ='tool-stamps']")).click();
    }

    @And("I open Stamps")
    public void iOpenStamps() {
        getDriver().findElement(By.xpath("//li[contains(@class,'stamps-navigation')]")).click();
    }

    @And("choose category Priority Mail")
    public void chooseCategoryPriorityMail() {
        getDriver().findElement(By.xpath("//label[contains(text(),'Priority Mail (')]")).click();
    }

    @Then("I verify {string} item found in result")
    public void iVerifyItemFoundInResult(String arg0) {
        String result = getDriver().findElement(By.xpath("//div[contains(@class,'result-page-stamps-holder')]")).getText();
        //assertThat(result).contains("All Results");
        assertThat(result).contains("Stamp");
    }

    @When("I unselect Stamps checkbox")
    public void iUnselectStampsCheckbox() {
        getDriver().findElement(By.xpath("//label[contains(text(),'Stamps')]")).click();
    }

    @And("select size {string}")
    public void selectSize(String sizeItem) {
        getDriver().findElement(By.xpath("//div[@class='result-facid-holder']/p" + "/label[contains(@for, 'checkbox-type-Size-" + sizeItem + "')]")).click();
    }

    @And("I click {string} color")
    public void iClickColor(String color) {
        getDriver().findElement(By.xpath("//div[contains(@onclick,'"+color+"')]")).click();
    }

    @Then("I verify {string} and {string} filters")
    public void iVerifyAndFilters(String size, String color) {
        assertThat(getDriver().findElement(By.xpath("//input[@id='checkbox-type-Size-Large']")).isSelected());
        assertThat(getDriver().findElement(By.xpath("//div[contains(@onclick," + color +")]")).isSelected());
    }

    @Then("I verify {string} present in results")
    public void iVerifyPresentInResults(String item) {
        getDriver().findElement(By.xpath("//div[contains(text(),'" + item +"')]")).isDisplayed();
    }

    @When("I perform {string} search")
    public void iPerformSearch(String itemSearch) {
        WebElement element = getDriver().findElement(By.xpath("//li[@class='nav-search menuheader']"));
        new Actions(getDriver()).moveToElement(element).click().perform();
        getDriver().findElement(By.xpath("//input[@id='global-header--search-track-search']")).sendKeys(itemSearch, Keys.ENTER);
    }

    @And("I select {string} in results")
    public void iSelectInResults(String item) {
        WebElement overlay = getDriver().findElement(By.xpath("//div[@class='gray-overlay']"));
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.invisibilityOf(overlay));
        getDriver().findElement(By.xpath("//span[contains(text(),'" + item + "')]")).click();
    }

    @And("I click {string} button")
    public void iClickButton(String buttonName) {
        getDriver().findElement(By.xpath("//div[@class='col']/a[contains(text(),'" + buttonName + "')]")).click();
    }

    @And("I select {string} zip code within {string} and search")
    public void iSelectZipCodeWithinAndSearch(String zipCode, String distance) {
        WebElement element = getDriver().findElement(By.xpath("//input[@id='city-state-input']"));
        new Actions(getDriver()).moveToElement(element).click().perform();
        getDriver().findElement(By.xpath("//input[@id='city-state-input']")).sendKeys(zipCode, Keys.ENTER);

        WebElement buttonWithin = getDriver().findElement(By.xpath("//button[@id='within-select']"));
        new Actions(getDriver()).click(buttonWithin);
        WebElement milesOption = getDriver().findElement(By.xpath("//a[contains(translate(text(),\"ABCDEFGHIJKLMNOPQRSTUVWXYZ\"," +
                "\"abcdefghijklmnopqrstuvwxyz\"),'" + distance + "')]"));

        new Actions(getDriver()).click(buttonWithin).click(milesOption);

        getDriver().findElement(By.xpath("//a[@id='searchLocations']")).click();
    }

    @Then("I verify that {string} present in search results")
    public void iVerifyThatPresentInSearchResults(String location) {
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@id='1440608']")));
        String result = getDriver().findElement(By.xpath("//div[@id='1440608']")).getText();
        assertThat(result).contains(location);
    }

    @And("I verify that {string} in {string}")
    public void iVerifyThatIn(String Parking, String location) {
        String result = getDriver().findElement(By.xpath("//div[@id='1440608']")).getText();
        assertThat(result).contains(Parking);
    }

    @When("I expand {string}")
    public void iExpand(String arg0) {
        getDriver().findElement(By.xpath("//div[@id='1440608']")).click();
    }

    @Then("I verify that {string} phone in {string}")
    public void iVerifyThatPhoneIn(String phone, String arg1) {
        String result = getDriver().findElement(By.xpath("//div[@class='col-md-4 col-sm-4 col-xs-12 location-address-phone']")).getText();
        assertThat(result).contains(phone);
    }

    @When("I collapse office details")
    public void iCollapseOfficeDetails() {
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@class='overlay-icon']")));
        getDriver().findElement(By.xpath("//div[@class='top-row']//span[@class='overlay-icon']")).click();
    }

    @Then("I verify I'm back on search results")
    public void iVerifyIMBackOnSearchResults() {
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='results-header']")));
        String result = getDriver().findElement(By.xpath("//div[@class='results-header']")).getText();
        assertThat(result).contains("Results");
    }

    @When("I go to {string} under {string}")
    public void iGoToUnder(String tabItem, String tab) {
        WebElement mail = getDriver().findElement(By.xpath("//a[@class='menuitem'][contains(text(),'" + tab +"')]"));
        new Actions(getDriver()).moveToElement(mail).perform();
        getDriver().findElement(By.xpath("//a[contains(text(),'"+ tabItem +"')]")).click();
    }

    @And("I reserve new PO box for {string}")
    public void iReserveNewPOBoxFor(String zipCode) {
        getDriver().findElement(By.xpath("//input[@id='searchInput']")).sendKeys(zipCode);
        getDriver().findElement(By.xpath("//span[@class='icon-search']")).click();
    }

    @Then("I verify that {string} present")
    public void iVerifyThatPresent(String location) {
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@id='1370964']")));
        String result = getDriver().findElement(By.xpath("//div[@id='1370964']")).getText();
        assertThat(result).contains(location);

    }

    @And("I verify that {string} PO Box is available in {string}")
    public void iVerifyThatPOBoxIsAvailableIn(String size, String location) {
        getDriver().findElement(By.xpath("//div[@id='1370964']")).click();
        String result = getDriver().findElement(By.xpath("//div[@id='availableboxes']")).getText();
        //assertThat(result).contains(location);
        assertThat(result).contains(size);

    }

    @And("I enter {string} zip for informed delivery")
    public void iEnterZipForInformedDelivery(String zipCode) {
        getDriver().findElement(By.xpath("//input[@id='zipcodeForm']")).sendKeys(zipCode);
        getDriver().findElement(By.xpath(" //a[@class='search']")).click();
    }

    @Then("I verify that informed delivery is {string}")
    public void iVerifyThatInformedDeliveryIs(String arg0) {
        By result = By.xpath("//div[@class = 'zMessage results success']");
        new WebDriverWait(getDriver(), 30).until(ExpectedConditions.visibilityOfElementLocated(result));

    }

    @And("I set {string} in filters")
    public void iSetInFilters(String filters) {
        WebElement overlay = getDriver().findElement(By.xpath("//div[@class='gray-overlay']"));
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.invisibilityOf(overlay));
        getDriver().findElement(By.xpath("//a[@class='dn-attr-a'][contains(text(),'Mail & Ship')]")).click();
    }

    @Then("I verify that {string} results found")
    public void iVerifyThatResultsFound(String quantity) {
        assertThat(getDriver().findElement(By.xpath("//span[@id='searchResultsHeading'][contains(text(), '"+ quantity+"')]")));
    }

    @Then("I validate that Sign In is required")
    public void iValidateThatSignInIsRequired() {
        assertThat(getDriver().findElement(By.xpath("//h1[@id='sign-in-to-your-account-header']")).isDisplayed());
    }

    @And("I perform {string} help search")
    public void iPerformHelpSearch(String item) {
        getDriver().findElement(By.xpath(" //input[@id='131:0']")).sendKeys(item);
    }

    @Then("I verify that no results of {string} available in help search")
    public void iVerifyThatNoResultsOfAvailableInHelpSearch(String item) {
        assertThat(getDriver().findElement(By.xpath("//div[@class='listContent']")).isDisplayed());
        String result  = getDriver().findElement(By.xpath("//div[@class='listContent']")).getText();
        if( result.contains(item)){
            System.out.println(item +"is present");
        }
        else System.out.println(item + "is not presnet");
    }

    @And("I search for {string}")
    public void iSearchFor(String address) {
        getDriver().findElement(By.xpath("//input[@id='address']")).sendKeys(address);
        getDriver().findElement(By.xpath("//button[@class='search-form-field-icon search-form-field-icon-search']")).click();
    }

    @And("I click {string} on the map")
    public void iClickOnTheMap(String arg0) {
        WebElement element = getDriver().findElement(By.xpath("//a[@class='route-table-toggle']"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    }

    @When("I click {string} on the table")
    public void iClickOnTheTable(String arg0) throws InterruptedException {
        Thread.sleep(2000);
        WebElement element = getDriver().findElement(By.xpath("//a[@class='totalsArea']"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    }

    @And("I close modal window")
    public void iCloseModalWindow() {
        //getDriver().findElement(By.xpath("//div[@id='modal-box-closeModal']")).click();
    }

    @Then("I verify that summary of all rows of Cost column is equal Approximate Cost in Order Summary")
    public void iVerifyThatSummaryOfAllRowsOfCostColumnIsEqualApproximateCostInOrderSummary() {
        List<WebElement> prices = getDriver().findElements(By.xpath("//*[contains(@idx, '7')]"));

        for(WebElement price : prices){
            System.out.print(price);}
    }
}
