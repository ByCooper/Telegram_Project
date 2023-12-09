package sky.pro.Animals.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sky.pro.Animals.configuration.TelegramBotConfig;
import sky.pro.Animals.entity.Client;
import sky.pro.Animals.entity.Pet;
import sky.pro.Animals.service.*;


import java.io.File;
import java.util.*;

@Component
public class ServiceTelegramBot extends TelegramLongPollingBot {
    @Autowired
    private PetServiceImpl petService;
    @Autowired
    private ClientServiceImpl clientService;
    @Autowired
    private InfoServiceImpl infoService;
    @Autowired
    private TelegramBotConfig botConfig;
    @Autowired
    private PetAvatarServiceImpl avatarService;
    @Autowired
    private VolunteerServiceImpl volunteerService;


    Logger LOG = LoggerFactory.getLogger(ServiceTelegramBot.class);
    static final String HELP_TEXT = "Привет,этот бот поможет выбрать животное из приюта.\n\n" +
            "Вы можете выполнять команды из главного меню слева или набрав команду:\n\n" +
            "Команда /start чтобы увидеть приветственное сообщение\n\n" +
            "Команда /mydata увидеть данные, хранящиеся о себе \n\n" +
            "Команда /help чтобы увидеть это сообщение снова\n\n";


    public ServiceTelegramBot(VolunteerServiceImpl volunteerService, PetServiceImpl petService, ClientServiceImpl clientService, InfoServiceImpl infoService, TelegramBotConfig botConfig) {
        this.petService = petService;
        this.clientService = clientService;
        this.infoService = infoService;
        this.botConfig = botConfig;
        this.volunteerService = volunteerService;
        //меню для бота в кострукторе
        List<BotCommand> listOfCommand = new ArrayList<>();
        listOfCommand.add(new BotCommand("/start", "получите приветственное сообщение"));
        listOfCommand.add(new BotCommand("/register", "для регистрации"));
        listOfCommand.add(new BotCommand("/mydata", "получить данные о вас "));
        listOfCommand.add(new BotCommand("/deletedata", "удалить данные о вас"));
        listOfCommand.add(new BotCommand("/help", "информация как пользоваться ботом"));
        listOfCommand.add(new BotCommand("/Приют для кошек", "информация как пользоваться ботом"));
        try {
            this.execute(new SetMyCommands(listOfCommand, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            LOG.error("error setting bot's command list : " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getNameBot();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        infoService.checkInfo();
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();     //сообщение от пользователя
            Long chatId = update.getMessage().getChatId();         //номер чата, для общения именно с этим пользователем
            ReplyKeyboardMarkup replyKeyboardMarkup = null;//кнопки для всех команд
            switch (message) {     //в командах применяем методы которые сами пишем ниже
                case "/start":
                    startCommand(chatId, update.getMessage().getChat().getFirstName());
                    registerUsers(update.getMessage());
                    break;
                case "Приют для кошек":
                    String c = "Вас приветствует приют для кошек";
                    sendMessage(replyKeyboardMarkup, chatId, c);
                    try {
                        execute(getCat(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Приют для собак":
                    String d = "Вас приветствует приют для собак";
                    sendMessage(replyKeyboardMarkup, chatId, d);
                    System.out.println(chatId);
                    try {
                        execute(getDog(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Информация о приюте для кошек":
                    String text = infoService.getInfoTextById(1L);
                    sendMessage(replyKeyboardMarkup, chatId, text);
                    break;
                case "Расписание, адрес, схема проезда(К)", "Расписание, адрес, схема проезда(С)":
                    String text1 = infoService.getInfoTextById(3L) + "\n" + infoService.getInfoTextById(4L) + "\n" + infoService.getInfoTextById(5L);
                    sendMessage(replyKeyboardMarkup, chatId, text1);
                    break;
                case "Оформить пропуск(К)":
                    break;
                case "ТБ(К)", "ТБ(С)":
                    String text2 = infoService.getInfoTextById(7L);
                    sendMessage(replyKeyboardMarkup, chatId, text2);
                    break;
                case "Информация о приюте для собак":
                    String text3 = infoService.getInfoTextById(2L);
                    sendMessage(replyKeyboardMarkup, chatId, text3);
                    break;
                case "Оформить пропуск(С)":
                    break;
                case "/register":
                    buttonsForRegister(chatId);
                    break;
                case "/help":
                    sendMessage(replyKeyboardMarkup, chatId, HELP_TEXT);
                    break;
                default:
                    sendMessage(replyKeyboardMarkup, chatId, ("извините,такой команды пока нет"));
            }
        }
        /**
         Этот else if отлавливает id прозрачных кнопок (Небольшие кирпичи с командами)
         например "YES_BUTTON" это id для кнопки yes. В зависимости от id кнопки возвращает функционал этой кнопки
         */
        else if (update.hasCallbackQuery()) {  //может в update передался id кнопки(yesButton.setCallbackData("YES_BUTTON"))

            if (update.hasCallbackQuery()) {
                String[] call_data = update.getCallbackQuery().getData().split(",");
                String tag = call_data[0];
                String id = call_data[1];
                long chat_id = update.getCallbackQuery().getMessage().getChatId();
                try {
                    switch (tag) {
                        case "dog", "cat", "nextPet": {
                            execute(sendPhoto(chat_id, Long.parseLong(id)));
                            execute(syncWithVolunteer(chat_id, Long.parseLong(id)));
                            break;
                        }
                        case "volunteer": {
                            execute(getAskVolunteer(Long.parseLong(id), update.getCallbackQuery().getFrom().getUserName()));
                            execute(getAnswerUser(chat_id));
                            break;
                        }
                        case "saveLife": {
                            execute(getInfoSaveLife(chat_id));
                            break;
                        }
                        default:
                            break;
                    }
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Регистрация клиента
     *
     * @param chatId
     */
    private void buttonsForRegister(Long chatId) { //прозрачные кнопки с сообщением (не клавиатура)
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Вы действительно хотите зарегистрироваться? ");
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();//класс для создания прозрачной кнопки под сообщением
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();//список списков для хранения кнопок
        List<InlineKeyboardButton> rowInline = new ArrayList<>();//список с кнопками для ряда
        var yesButton = new InlineKeyboardButton();
        yesButton.setText("Yes");
        yesButton.setCallbackData("YES_BUTTON");//индификатор кнопки (позволяет понять боту ,какая кнопка была нажата)
        var noButton = new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData("NO_BUTTON");
        rowInline.add(yesButton); //добавили кнопки в список для ряда
        rowInline.add(noButton);
        rowsInLine.add(rowInline); //добавили список с кнопками для ряда в список для хранения кнопок
        markupInLine.setKeyboard(rowsInLine);// в классе меняем значения для кнопки
        message.setReplyMarkup(markupInLine);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOG.error("Error occurred : " + e.getMessage());
        }
    }

    /**
     * Метод для записи в таблицу новых клиентов <br>
     * При помощи {@link sky.pro.Animals.repository.ClientRepository#findByChatId(Long)} проверяем на наличие id чата. <br>
     * При помощи {@link sky.pro.Animals.repository.ClientRepository#save(Object)} сохраняем клиента в таблицу Client. <br>
     *
     * @param message
     * @see sky.pro.Animals.repository.ClientRepository#findByChatId(Long)
     * @see sky.pro.Animals.repository.ClientRepository#save(Object)
     */
    private void registerUsers(Message message) {
        if (clientService.getByChatId(message.getChatId()) == null) {//если чатайди не найден
            Long chatId = message.getChatId();                       //то нужно создать новый
            var chat = message.getChat();
            Client newClient = new Client();
            newClient.setChatId(chatId);
            newClient.setFirstName(chat.getFirstName());
            newClient.setLastName(chat.getLastName());
            newClient.setUserName(chat.getUserName());
            clientService.save(newClient);
            LOG.info("client saved : " + newClient);
        }
    }

    /**
     * Метод для команды старт.
     * Переменная {@code ReplyKeyboardMarkup rep = buttonsForStart();} инициализируется
     * методом {@link ServiceTelegramBot#buttonsForStart()}
     *
     * @param chatId
     * @param name
     */
    private void startCommand(Long chatId, String name) {
        String answer = "Привет, " + name + ", какой приют вы хотите выбрать?";
        LOG.info("Replied to user " + name);
        ReplyKeyboardMarkup rep = buttonsForStart();
        sendMessage(rep, chatId, answer);
    }

    /**
     * В методе описаны не прозрачные кнопки(клавиатура) для команды старт.
     * Создаём объект: {@code ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup() } для
     * разметки клавиатуры.
     * Создаем коллекцию {@code List<KeyboardRow> keyboardRows = new ArrayList<>()} для кнопок.
     * Создаем объект класса KeyboardRow : {@code KeyboardRow row = new KeyboardRow()}.
     * Добавляем кнопки в наш ряд: {@code row.add("/Приют для кошек")} при помощи метода add класса KeyboardRow
     * {@link KeyboardRow#add(String)}.
     * Добавляем ряды в коллекцию: {@code keyboardRows.add(row)}.
     * У класса replyKeyboardMarkup инициализируем поле keyboard коллекцией keyboardRows :
     * {@code replyKeyboardMarkup.setKeyboard(keyboardRows)}
     *
     * @return
     */

    private ReplyKeyboardMarkup buttonsForStart() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();//разметка для клавиатуры}
        List<KeyboardRow> keyboardRows = new ArrayList<>(); //список из рядов(ряд в который добавляем кнопки
        KeyboardRow row1 = new KeyboardRow();//ряд1
        KeyboardRow row2 = new KeyboardRow();//ряд2
        KeyboardRow row3 = new KeyboardRow();//ряд3
        KeyboardRow row4 = new KeyboardRow();//ряд4
        row1.add("Приют для кошек");
        row1.add("Приют для собак");
        row2.add("Информация о приюте для кошек");
        row2.add("Информация о приюте для собак");
        row3.add("Расписание, адрес, схема проезда(К)");
        row3.add("Расписание, адрес, схема проезда(С)");
        row4.add("Оформить пропуск(К)");
        row4.add("ТБ(К)");
        row4.add("Оформить пропуск(С)");
        row4.add("ТБ(С)");

        keyboardRows.add(row1);      //ряд 1 добавили
        keyboardRows.add(row2);      //ряд 1 добавили
        keyboardRows.add(row3);      //ряд 1 добавили
        keyboardRows.add(row4);      //ряд 1 добавили
        replyKeyboardMarkup.setKeyboard(keyboardRows);//добавляем лист с рядами в метод для разметки
        return replyKeyboardMarkup;
    }

    private void sendMessage(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOG.error("Error occurred : " + e.getMessage());
        }
    }

    public SendMessage getCat(long chat_id) {

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Выберите питомца");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (Pet e : petService.getCat()
        ) {
            List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            inlineKeyboardButton1.setText(e.getName() + " Дата рождения " + e.getBirthday());
            inlineKeyboardButton1.setCallbackData("cat," + e.getId().toString());
            rowInline1.add(inlineKeyboardButton1);
            rowsInline.add(rowInline1);
        }
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }

    public SendMessage getDog(long chat_id) {

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Выберите питомца");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (Pet e : petService.getDog()
        ) {
            List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            inlineKeyboardButton1.setText(e.getName() + " Дата рождения " + e.getBirthday());
            inlineKeyboardButton1.setCallbackData("dog," + e.getId().toString());
            rowInline1.add(inlineKeyboardButton1);
            rowsInline.add(rowInline1);
        }
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }


    public SendMessage syncWithVolunteer(long chat_id, Long id) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Оставьте заявку нашему волонтеру, он свяжется с Вами в ближайшее время и предоставит интересующую Вас информацию");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Оставить заявку волонтеру");
        inlineKeyboardButton1.setCallbackData("volunteer," + id);
        inlineKeyboardButton2.setText("Посмотреть следующего");
        inlineKeyboardButton2.setCallbackData("nextPet," + getNextPet(id));
        inlineKeyboardButton3.setText("Как взять животное из приюта?");
        inlineKeyboardButton3.setCallbackData("saveLife," + id);
        rowInline1.add(inlineKeyboardButton1);
        rowInline1.add(inlineKeyboardButton2);
        rowInline2.add(inlineKeyboardButton3);
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }


    public SendPhoto sendPhoto(long chat_id, Long id) {
        String filePath = avatarService.findAvatar(id)
                .getFilePath();
        InputFile file = new InputFile(new File(filePath));
        return SendPhoto.builder()
                .chatId(chat_id)
                .photo(file)
                .caption(petService.getById(id).toString() + "\n" + "Если Вас заинтересовал питомец Вы можете связаться с волонтером для получения информации о дальнейших действиях")
                .build();
    }

    public Long getNextPet(Long id) {
        Pet currentPet = petService.getById(id);
        List<Pet> pets = petService.getAll(currentPet.getPetVariety());
        int index = pets.indexOf(currentPet);
        int nextIndex = index == (pets.size() - 1) ? 0 : (index + 1);
        return pets.get(nextIndex).getId();
    }


    public SendMessage getAnswerUser(long chat_id) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Ваша заявка отправлена. В ближайшее время с Вами свяжется волонтер. Спасибо за Вашу доброту!");
        return message;
    }

    public SendMessage getAskVolunteer(Long id, String name) {
        Long user = volunteerService.getById(1L).getChatId().longValue();
        SendMessage message = new SendMessage();
        message.setChatId(user);
        message.setText("Заявку на обратную связь оставил пользователь @" + name + ", обращение по питомцу с ID=" + id);
        return message;
    }

    public SendMessage getInfoSaveLife(long chat_id) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(infoService.getInfoTextById(9L));
        return message;
    }
}
