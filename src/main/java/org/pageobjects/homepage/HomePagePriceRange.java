package org.pageobjects.homepage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePagePriceRange extends LoadableComponent<HomePagePriceRange> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WebDriver driver;

    @FindBy(className = "ngx-slider-model-value")
    private WebElement lowerRange;

    @FindBy(className = "ngx-slider-model-high")
    private WebElement upperRange;

    @FindBy(className = "ngx-slider-pointer-min")
    private WebElement lowerRangeSlider;

    @FindBy(className = "ngx-slider-pointer-max")
    private WebElement upperRangeSlider;

    public HomePagePriceRange(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://practicesoftwaretesting.com/#/");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("#/"), "Not on the home page: " + url);
    }

    public HomePageProducts changePriceRange(int wantedLowerRange, int wantedHigherRange) {
        log.debug("Changing the price range, wanted lower range: {}, wanted higher range: {}", wantedLowerRange, wantedHigherRange);

        if (wantedLowerRange < 0) {
            throw new IllegalArgumentException("The lower range can only go to 0, the value entered: "
                    + wantedLowerRange + ", is out of bounds.");
        }

        if (wantedHigherRange > 200) {
            throw new IllegalArgumentException("The higher range can only go to 200, the value entered: "
                    + wantedHigherRange + ", is out of bounds.");
        }

        changeSlider(wantedLowerRange, true);
        changeSlider(wantedHigherRange, false);

        return new HomePageProducts(driver);
    }

    private void changeSlider(int wantedRange, boolean isLowerRange) {
        log.debug("Changing the price slider, wanted range: {}, is a lower range: {}", wantedRange, isLowerRange);

        int lowerRangeDifference = wantedRange - getLowerPriceRange();
        int upperRangeDifference = wantedRange - getUpperPriceRange();

        int rangeDifference = (isLowerRange) ? lowerRangeDifference : upperRangeDifference;
        WebElement slider = (isLowerRange) ? lowerRangeSlider : upperRangeSlider;

        if (rangeDifference > 0) {
            for (int i = 0; i < rangeDifference; i++) {
                slider.sendKeys(Keys.ARROW_RIGHT);
            }
        } else {
            for (int i = rangeDifference; i < 0; i++) {
                slider.sendKeys(Keys.ARROW_LEFT);
            }
        }
    }

    public int getLowerPriceRange() {
        log.debug("Getting the lower price range");

        return Integer.parseInt(lowerRange.getText());
    }

    public int getUpperPriceRange() {
        log.debug("Getting the upper price range");

        return Integer.parseInt(upperRange.getText());
    }
}
