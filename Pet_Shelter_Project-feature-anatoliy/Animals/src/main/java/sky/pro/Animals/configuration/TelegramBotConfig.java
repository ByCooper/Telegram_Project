package sky.pro.Animals.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

@Configuration

@PropertySource("application.properties")
public class TelegramBotConfig {
    @Value("${telegram.bot.name}")
    String nameBot;
    @Value("${telegram.bot.token}")
    String token;

    public TelegramBotConfig() {
    }

    public TelegramBotConfig(String nameBot, String token) {
        this.nameBot = nameBot;
        this.token = token;
    }

    public String getNameBot() {
        return nameBot;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "BotConfig{" +
                "nameBot='" + nameBot + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramBotConfig botConfig = (TelegramBotConfig) o;
        return Objects.equals(nameBot, botConfig.nameBot) && Objects.equals(token, botConfig.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameBot, token);
    }
}