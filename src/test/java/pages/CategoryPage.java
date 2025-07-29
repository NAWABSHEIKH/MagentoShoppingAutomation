package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utils.WaitUtil;

public class CategoryPage {
    WebDriver driver;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "sorter")
    WebElement sortDropdown;

    @FindBy(css = ".product-item")
    List<WebElement> productItems;

    public void sortBy(String optionText) {
        Select dropdown = new Select(sortDropdown);
        dropdown.selectByVisibleText(optionText);
        WaitUtil.waitForSeconds(2);
        System.out.println("🔃 Sorted by: " + optionText);
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement item : productItems) {
            WebElement priceEl = item.findElement(By.cssSelector(".price"));
            String priceText = priceEl.getText().replaceAll("[^0-9.]", "");
            try {
                prices.add(Double.parseDouble(priceText));
            } catch (Exception e) {
                System.out.println("⚠️ Couldn't parse price: " + priceText);
            }
        }
        return prices;
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement item : productItems) {
            WebElement nameEl = item.findElement(By.cssSelector(".product-item-name a"));
            names.add(nameEl.getText().trim());
        }
        return names;
    }
}
