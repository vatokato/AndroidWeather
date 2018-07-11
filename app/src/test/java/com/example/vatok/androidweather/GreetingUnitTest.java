package com.example.vatok.androidweather;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
public class GreetingUnitTest {
    @Test
    public void getGreetingMorning() {
        GreetingStrings greetingStrings = new GreetingStrings() {
            @Override
            public String getNow() {
                return "Сейчас";
            }

            @Override
            public String getGreeter() {
                return "Пользователь";
            }

            @Override
            public String getMorning() {
                return "утро";
            }

            @Override
            public String getAfternoon() {
                return "день";
            }

            @Override
            public String getEvening() {
                return "вечер";
            }

            @Override
            public String getNight() {
                return "ночь";
            }
        };


        GreetingBuilder greetingBuilder = new GreetingBuilder(greetingStrings, 7);
        assertEquals(greetingBuilder.getText(), "утро, Пользователь. Сейчас 7");

        greetingBuilder = new GreetingBuilder(greetingStrings, 13);
        assertEquals(greetingBuilder.getText(), "день, Пользователь. Сейчас 13");

        greetingBuilder = new GreetingBuilder(greetingStrings, 18);
        assertEquals(greetingBuilder.getText(), "вечер, Пользователь. Сейчас 18");

        greetingBuilder = new GreetingBuilder(greetingStrings, 4);
        assertEquals(greetingBuilder.getText(), "ночь, Пользователь. Сейчас 4");
    }
}
