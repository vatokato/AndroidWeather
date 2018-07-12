package com.example.vatok.androidweather;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class GreetingUnitTest {
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

    @Test
    public void getGreetingMorning() {
        GreetingBuilder greetingBuilder = new GreetingBuilder(greetingStrings, 7);
        assertEquals(greetingBuilder.getText(), "утро, Пользователь. Сейчас 7");
    }

    @Test
    public void getGreetingAfternoon() {
        GreetingBuilder greetingBuilder = new GreetingBuilder(greetingStrings, 13);
        assertEquals(greetingBuilder.getText(), "день, Пользователь. Сейчас 13");

    }
    @Test
    public void getGreetingEvening() {
        GreetingBuilder greetingBuilder = new GreetingBuilder(greetingStrings, 20);
        assertEquals(greetingBuilder.getText(), "вечер, Пользователь. Сейчас 20");

    }
    @Test
    public void getGreetingNight() {
        GreetingBuilder greetingBuilder = new GreetingBuilder(greetingStrings, 3);
        assertEquals(greetingBuilder.getText(), "ночь, Пользователь. Сейчас 3");

    }
}
