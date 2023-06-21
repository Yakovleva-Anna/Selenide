package ru.netology.selenide;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    private String generateDate(int daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));

    }

    @Test
    public void DeliveryCardTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Кызыл");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String currentDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[name='name']").setValue("Иванов-Пупкин Василий Петрович");
        $("[name='phone']").setValue("+79213222332");
        $("[data-test-id='agreement']").click();
        $$("[type='button']").find(Condition.exactText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
