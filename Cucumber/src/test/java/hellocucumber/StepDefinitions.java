package hellocucumber;

import io.cucumber.java.en.*;

import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class StepDefinitions {

    private final static String CHROME_DRIVER_PATH = "C:/Users/shoha/IdeaProjects/2023-mbt-29-33/Selenium/chromedriver.exe"; //"/Users/brunomachado/CucumberExample/Selenium/chromedriver";

    private ChromeDriver driver;
    private WebDriverWait wait;
    private int numberOfProductBeforeAdd;
    private String quantityOfProductBeforeRemoved;
    private String quantity;
    private String userEmail = "pub@prestashop.com";
    private String userPassword = "123456789";
    //private boolean isAdminWindowOpened = false;
    private boolean isUserWindowOpened = false;

    //Auxiliary function for checking that the product has indeed been added to the wish list
    private int getNumberOfProductInWishList(){
        //go to my account
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/header/nav/div/div/div[1]/div[2]/div[2]/div/a[2]/span"))).click();
        //go to the wishList button
        driver.findElement(By.xpath("//a[5]/span[1]")).click();
        //get into my wishList
        WebElement wishlist = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/ul/li/a")));
        // Retrieve the text of the element
        String Text = wishlist.getText();
        // Define a regex pattern to extract the number
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(Text);
        assertTrue(matcher.find());
        String numberString = matcher.group(1);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(numberString);
    }

    // User-related steps

    // Navigate to the home page
    @Given("User is in Home Page")
    public void userIsInHomePage() {
        if (!isUserWindowOpened) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            this.driver = new ChromeDriver();
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));

            driver.get("http://localhost:8080/");

            driver.manage().window().setPosition(new Point(700, 5));
            isUserWindowOpened = true;
        }
    }

    // Log in as a user with provided credentials
    @When("User is logged in with {string} and {string}")
    public void userIsLoggedInWith(String email, String password) {
        driver.findElement(By.xpath("//body/main[1]/header[1]/nav[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/a[1]/span[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='field-email']"))).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='field-password']"))).sendKeys(password);
        driver.findElement(By.xpath("/html/body/main/section/div/div/div/section/div/section/form/footer/button")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Add a product to the wish list with a specified quantity
    @And("User add a product {string} to the wishList with quantity {string}")
    public void userAddAProductToTheWishListWithQuantityX(String product, String quantity) {
        //get the number of product before adding the product
        numberOfProductBeforeAdd = getNumberOfProductInWishList();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[1]/input[2]"))).sendKeys(product);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[1]/input[2]"))).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//picture[1]/img[1]")).click();

        // Locate the input field where we want to send the quantity
        WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='quantity_wanted']")));

        // Execute JavaScript to clear the input field
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", quantityInput);

        // Send the new quantity to the input field
        quantityInput.sendKeys(quantity);

        driver.findElement(By.xpath("//div[2]/div[1]/button[1]/i[1]")).click();
        driver.findElement(By.xpath("//li[1]/p[1]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Check if the product is successfully added to the user's wish list
    @Then("the product should be successfully added to the user's wishlist")
    public void theProductShouldBeSuccessfullyAddedToTheUserSWishlist() {
        int numberOfProductAfterAdd = getNumberOfProductInWishList();

        //check if the product was added to the wishList
        assertEquals(numberOfProductBeforeAdd + 1, numberOfProductAfterAdd, "The product did not added to the cart");
    }

    // Check if the quantity of the product in the wish list matches the expected quantity
    @And("the quantity is {string}")
    public void theQuantityIs(String quantity) {
        //check if the quantity equals to the right one
        //go to my account
        driver.findElement(By.xpath("//div[2]/div[1]/a[2]/span[1]")).click();
        //go to the wishList button
        driver.findElement(By.xpath("//a[5]/span[1]")).click();
        //get into my wishList
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/ul/li/a"))).click();
        WebElement quantityDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/ul/li/div/a/div[2]/div/p/span")));
        // Retrieve the text of the element
        String Text = quantityDescription.getText();
        // Define a regex pattern to extract the number
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(Text);
        assertTrue(matcher.find());
        String numberString = matcher.group(1);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(quantity, numberString, "the product's quantity is incorrect");
    }

    // Admin-related steps

    // Navigate to the admin home page
    @Given("Admin is in Home Page")
    public void adminIsInHomePage() {
        //if (!isAdminWindowOpened) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            this.driver = new ChromeDriver();
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));

            driver.get("http://localhost:8080/admina");

            driver.manage().window().maximize();
            //isAdminWindowOpened = true;
        //}
    }

    // Log in as an admin with provided credentials
    @When("Admin is logged in with {string} and {string}")
    public void adminIsLoggedInWithAnd(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='email']"))).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='passwd']"))).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"stay_logged_in\"]")).click();
        driver.findElement(By.xpath("//*[@id='submit_login']")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getQuantityOfProduct(){
        WebElement quantityDescription = driver.findElement(By.xpath("//*[@id=\"product_stock_quantities_delta_quantity_quantity\"]/span[1]"));
        // Retrieve the text of the element
        String Text = quantityDescription.getText();
        // Define a regex pattern to extract the number
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(Text);
        assertTrue(matcher.find());
        String numberString = matcher.group(1);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return numberString;
    }

    // Change the quantity of a product to the specified quantity
    @And("admin change the quantity of the product {string} to {string}")
    public void adminChangeTheQuantityOfTheProductTo(String product, String quantity) {
        this.quantity = quantity;

        //get into the product page
        driver.findElement(By.xpath("//*[@id=\"subtab-AdminCatalog\"]/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"subtab-AdminProducts\"]/a"))).click();

        // Finding the product's input field
        WebElement productNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product_name\"]")));

        // Clear the input field
        productNameInput.clear();

        // Send the product name to the input field
        productNameInput.sendKeys(product);
        productNameInput.sendKeys(Keys.ENTER);

        //go to the product quantity description
        driver.findElement(By.xpath("//*[@id=\"product_grid_table\"]/tbody/tr/td[9]/a")).click();

        //get the current quantity for testing after
        quantityOfProductBeforeRemoved = getQuantityOfProduct();

        // Locate the input field where we want to send the quantity
        WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product_stock_quantities_delta_quantity_delta\"]")));

        // Clear the existing content of the input field
        quantityInput.clear();

        // Send the new quantity to the input field
        quantityInput.sendKeys(quantity);

        //save the changes
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product_footer_save\"]"))).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Check if the quantity of the product in the admin panel matches the expected quantity
    @Then("the quantity of the product should be {string}")
    public void theQuantityOfTheProductShouldBe(String Y) {
        WebElement quantityDescription = driver.findElement(By.xpath("//*[@id=\"product_stock_quantities_delta_quantity_quantity\"]/span[1]"));
        // Retrieve the text of the element
        String Text = quantityDescription.getText();
        // Define a regex pattern to extract the number
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(Text);
        assertTrue(matcher.find());
        String numberString = matcher.group(1);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int expectedQuantity = Integer.parseInt(quantityOfProductBeforeRemoved) + Integer.parseInt(quantity);
        assertEquals(expectedQuantity+"",numberString,"The quantity of the product is not changed");
    }

    // Check if the user's wish list is adjusted accordingly after admin actions
    @And("the user's wishlist should be adjusted accordingly for scenario {int}")
    public void theUserSWishlistShouldBeAdjustedAccordingly(int scenarioIndex) {
        //go to the user home page
        userIsInHomePage();
        userIsLoggedInWith(userEmail,userPassword);
        //go to my account
        driver.findElement(By.xpath("//div[2]/div[1]/a[2]/span[1]")).click();
        //go to the wishList button
        driver.findElement(By.xpath("//a[5]/span[1]")).click();
        //get into my wishList
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/ul/li/a"))).click();
        // Define the default XPath for the product description
        String productDescriptionXPath = "//*[@id=\"content\"]/ul/li/div/a/div[1]/p";

        // Check if the row index is 2 (indexing starts from 0 in Java)
        if (scenarioIndex == 1) { // Assuming scenarioRowIndex is the index of the current scenario
            // Modify the XPath for the second example
            productDescriptionXPath = "//*[@id=\"content\"]/ul/li[2]/div/a/div[1]/p";
        }

        // Wait for the product description element and retrieve its text
        WebElement productDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(productDescriptionXPath)));
        String text = productDescription.getText();
        assertEquals("block\n" + "Out-of-Stock", text, "The user wishList is not adjusted accordingly");
    }

    // Remove added product from the wish list
    @And("User remove all product from the wishList")
    public void userRemoveAProductFromTheWishList() {
        //go to my account
        //driver.findElement(By.xpath("//div[2]/div[1]/a[2]/span[1]")).click();
        //go to the wishList button
        //driver.findElement(By.xpath("//a[5]/span[1]")).click();
        int numberOfProductAfterAdd = getNumberOfProductInWishList();
        System.out.printf(numberOfProductAfterAdd+"");
        //get into my wishList
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/ul/li/a"))).click();
        for (int i=0; i<numberOfProductAfterAdd; i++) {
            //press the delete button
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/ul/li/div/div/button[2]/i"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"footer\"]/div[2]/div/div[1]/div[5]/div[1]/div/div/div[3]/button[2]"))).click();
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Then("the product should be successfully removed from the user's wishlist")
    public void theProductShouldBeSuccessfullyRemovedFromTheUserSWishlist() {
        int numberOfProductAfterRemove = getNumberOfProductInWishList();
        assertEquals("0", numberOfProductAfterRemove+"", "The wishList isn't empty");
    }

    // Add the removed items back from the admin panel
    /*private void addTheRemovedItemsFromAdminPage() {
        adminIsInHomePage();
        adminIsLoggedInWithAnd(adminEmail, adminPassword);

        //get into the product page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"subtab-AdminCatalog\"]/a"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"subtab-AdminProducts\"]/a"))).click();

        // Finding the product's input field
        WebElement productNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product_name\"]")));

        // Clear the input field
        productNameInput.clear();

        // Send the product name to the input field
        productNameInput.sendKeys(product);
        productNameInput.sendKeys(Keys.ENTER);


        //go to the product quantity description
        driver.findElement(By.xpath("//*[@id=\"product_grid_table\"]/tbody/tr/td[9]/a")).click();


        // Locate the input field where we want to send the quantity
        WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product_stock_quantities_delta_quantity_delta\"]")));

        // Clear the existing content of the input field
        quantityInput.clear();
        // Send the new quantity to the input field
        quantityInput.sendKeys(quantityOfProductBeforeRemoved);

        //save the changes
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product_footer_save\"]"))).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
    //@AfterEach
    /*private void logoutUser() {
        if (driver != null) {
            driver.findElement(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a[1]")).click();
        }
    }*/

    // Perform cleanup actions after test execution
    @After
    public void tearDown() {
        // Remove the added item from the wishList
        //removeAddedProductFromWishList();
        // Add the removed items
        //addTheRemovedItemsFromAdminPage();
        // Close the browser session
        if (driver != null) {
            driver.quit();
        }

    }



}

