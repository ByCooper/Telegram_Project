package sky.pro.Animals.initializer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import sky.pro.Animals.service.InfoServiceImpl;


@Component
public class BotInitializer {
    private final InfoServiceImpl infoService;
    Logger LOG = LoggerFactory.getLogger(BotInitializer.class);
    @Autowired
    TelegramBot Bot;

    public BotInitializer(InfoServiceImpl infoService) {
        this.infoService = infoService;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        infoService.checkInfo();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot((LongPollingBot) Bot);
        } catch (TelegramApiException e) {
            LOG.error("error occurred: " + e.getMessage());
        }
    }
}
