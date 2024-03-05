package hellocucumber;

import io.cucumber.java.en.*;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
    private String quantityOfRemovedItem;
    private String product = "shirt";
    private String userEmail = "pub@prestashop.com";
    private String userPassword = "123456789";
    private boolean isAdminWindowOpened = false;
    private boolean isUserWindowOpened = false;



    // $$*TODO* explain what this step does$$
    @Given("an example scenario")
    public void anExampleScenario() {
    }

    // $$*TODO* explain what this step does$$
    @When("all step definitions are implemented")
    public void allStepDefinitionsAreImplemented() {
    }

    // $$*TODO* explain what this step does$$
    @Then("the scenario passes")
    public void theScenarioPasses() {
    }

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
        System.out.println(Text);
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

    @And("User add a product {string} to the wishList with quantity {string}")
    public void userAddAProductToTheWishListWithQuantityX(String product, String quantity) {
        this.product = product;
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



    @Then("the product should be successfully added to the user's wishlist")
    public void theProductShouldBeSuccessfullyAddedToTheUserSWishlist() {
        int numberOfProductAfterAdd = getNumberOfProductInWishList();

        //check if the product was added to the wishList
        assertEquals(numberOfProductBeforeAdd + 1, numberOfProductAfterAdd, "The product did not added to the cart");
    }
    @And("the quantity is {string}")
    public void theQuantityIs(String quantity) {
        //check if the quantity equals to the right one
        //go to my account
        driver.findElement(By.xpath("//div[2]/div[1]/a[2]/span[1]")).click();
        //go to the wishList button
        driver.findElement(By.xpath("//a[5]/span[1]")).click();
        //get into my wishList
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/ul/li/a"))).click();
        WebElement quantityDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/section/div/div/div/section/div[1]/section/ul/li/div/a/div[2]/div/p/span[3]")));
        // Retrieve the text of the element
        String Text = quantityDescription.getText();
        System.out.println(Text);
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


    @Given("Admin is in Home Page")
    public void adminIsInHomePage() {
        if (!isAdminWindowOpened) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            this.driver = new ChromeDriver();
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));

            driver.get("http://localhost:8080/admina");

            driver.manage().window().maximize();
            isAdminWindowOpened = true;
        }
    }

    @When("Admin is logged in with {string} and {string}")
    public void adminIsLoggedInWithAnd(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='email']"))).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='passwd']"))).sendKeys(password);
        driver.findElement(By.xpath("//*[@id='submit_login']")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getQuantityOfProduct(){
        WebElement quantityDescription = driver.findElement(By.xpath("//*[@id=\"combination_list_0_delta_quantity_quantity\"]/span[1]"));
        // Retrieve the text of the element
        String Text = quantityDescription.getText();
        System.out.println(Text);
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

    @And("admin change the quantity of the product {string} to zero")
    public void adminChangeTheQuantityOfTheProductToZero(String product) {
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

        // Locate the input field where we want to send the quantity
        WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"combination_list_0_delta_quantity_delta\"]")));

        // Clear the existing content of the input field
        quantityInput.clear();
        quantityOfRemovedItem = getQuantityOfProduct();
        String quantity = "-"+quantityOfRemovedItem; //need to subtract

        // Send the new quantity to the input field
        quantityInput.sendKeys(quantity);

        //save the changes
        driver.findElement(By.xpath("//*[@id=\"save-combinations-edition\"]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product_footer_save\"]"))).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Then("the quantity of the product should be zero")
    public void theQuantityOfTheProductShouldBe() {
        WebElement quantityDescription = driver.findElement(By.xpath("//*[@id=\"combination_list_0_delta_quantity_quantity\"]/span[1]"));
        // Retrieve the text of the element
        String Text = quantityDescription.getText();
        System.out.println(Text);
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
        assertEquals("0",numberString,"The quantity of the product is not changed");
    }


    @And("the quantity in the user's wishlist should be adjusted accordingly")
    public void theQuantityInTheUserSWishlistShouldBeAdjustedAccordingly() {
        //go to the user home page
        userIsInHomePage();
        userIsLoggedInWith(userEmail,userPassword);
        //go to my account
        driver.findElement(By.xpath("//div[2]/div[1]/a[2]/span[1]")).click();
        //go to the wishList button
        driver.findElement(By.xpath("//a[5]/span[1]")).click();
        //get into my wishList
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/ul/li/a"))).click();
        WebElement productDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/ul/li/div/a/div[1]/p")));
        // Retrieve the text of the element
        String text = productDescription.getText();
        System.out.printf(text);//debug
        assertEquals("block\n" + "Product available with different options", text, "The user wishList is not adjusted accordingly");
    }

    private void removeAddedProductFromWishList() {
        //go to the user home page
        userIsInHomePage();
        //userIsLoggedInWith(userEmail,userPassword);
        //go to my account
        driver.findElement(By.xpath("//div[2]/div[1]/a[2]/span[1]")).click();
        //go to the wishList button
        driver.findElement(By.xpath("//a[5]/span[1]")).click();
        //get into my wishList
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/ul/li/a"))).click();

        //press the delete button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/ul/li/div/div/button[2]/i"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"footer\"]/div[2]/div/div[1]/div[5]/div[1]/div/div/div[3]/button[2]"))).click();

    }

    private void addTheRemovedItemsFromAdminPage() {
        adminIsInHomePage();
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
        WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"combination_list_0_delta_quantity_delta\"]")));

        // Clear the existing content of the input field
        quantityInput.clear();
        // Send the new quantity to the input field
        quantityInput.sendKeys(quantityOfRemovedItem);

        //save the changes
        driver.findElement(By.xpath("//*[@id=\"save-combinations-edition\"]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product_footer_save\"]"))).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //@AfterEach
    private void logoutUser() {
        if (driver != null) {
            driver.findElement(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a[1]")).click();
        }
    }


    @AfterAll
    public void tearDown() {
        // Remove the added item from the wishList
        removeAddedProductFromWishList();
        // Add the removed items
        addTheRemovedItemsFromAdminPage();
        // Close the browser session
        if (driver != null) {
            driver.quit();
        }
        System.out.printf("here");
    }



}

