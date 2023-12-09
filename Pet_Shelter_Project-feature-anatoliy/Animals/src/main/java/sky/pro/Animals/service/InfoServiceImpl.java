package sky.pro.Animals.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import sky.pro.Animals.entity.Info;
import sky.pro.Animals.repository.InfoRepository;

import java.util.Collection;
import java.util.List;

/**
 * Service for working with the content of standard information messages
 * <p>
 * <hr>
 * <p>
 * Сервис для работы с содержанием стандартных информационных сообщений
 */
@Service
public class InfoServiceImpl implements InfoService {
    private final InfoRepository infoRepository;

    public InfoServiceImpl(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    /**
     * Method for exist check of standard messages in DB. <br>
     * Used repository method {@link JpaRepository#findAll()} <br>
     * Also used repository method {@link JpaRepository#save(Object)}
     * <hr>
     * Метод для проверки существования стандартных сообщений в БД. <br>
     * Используется метод репозитория {@link JpaRepository#findAll()} <br>
     * Также использован метод репозитория {@link JpaRepository#save(Object)}
     * <hr>
     * 1L "Information about the shelter"/"Информация о приюте" <br>
     * 2L "How to take an animal from a shelter"/"Как взять животное из приюта" <br>
     * 3L "The shelter's work schedule"/"Расписание работы приюта" <br>
     * 4L "Address of the shelter"/"Адрес приюта" <br>
     * 5L "Driving directions"/"Схема проезда" <br>
     * 6L "Security contact details for registration of a car pass"/"Контактные данные охраны для оформления пропуска на машину" <br>
     * 7L "General safety recommendations on the territory of the shelter"/"Общие рекомендации о технике безопасности на территории приюта" <br>
     * 8L "Rules for getting to know an animal before picking it up from a shelter"/"Правила знакомства с животным до того, как забрать его из приюта" <br>
     * 9L "List of documents required to take an animal from a shelter"/"Список документов, необходимых для того, чтобы взять животное из приюта" <br>
     * 10L "List of recommendations for animal transportation"/"Список рекомендаций по транспортировке животного" <br>
     * 11L "List of recommendations for home improvement for a puppy"/"Список рекомендаций по обустройству дома для щенка" <br>
     * 12L "List of recommendations for home improvement for a kitten"/"Список рекомендаций по обустройству дома для котенка" <br>
     * 13L "List of recommendations for home improvement for an adult animal with disabilities"/"Список рекомендаций по обустройству дома для взрослого животного с ограниченными возможностями" <br>
     * 14L "Tips of a dog handler on primary communication with a dog"/"Советы кинолога по первичному общению с собакой" <br>
     * 15L "Recommendations for proven dog handlers for further handling of the dog"/"Рекомендации по проверенным кинологам для дальнейшего обращения с собакой" <br>
     * 16L "A list of reasons why they may refuse and not let you take the dog from the shelter"/"Список причин, почему могут отказать и не дать забрать собаку из приюта" <br>
     * 17L "Daily Report form"/"Форма ежедневного отчета" <br>
     * 18L "Congratulations on the successful completion of the probation period"/"Поздравление с успешным прохождением испытательного срока" <br>
     * 19L "An additional time of probation has been appointed"/"Назначено дополнительное время испытательного срока" <br>
     * 20L "Notification that the adoptive parent has not passed the probation period"/"Уведомление о том, что усыновитель не прошел испытательный срок" <br>
     *
     * @see JpaRepository#findAll()
     * @see JpaRepository#save(Object)
     */
    @Override
    public void checkInfo() {
        List<String> infoList = infoRepository.findAll().stream()
                .map(Info::getKey)
                .toList();
        if (!infoList.contains("Информация о приюте")) {
            Info info = new Info(1L, "Информация о приюте для котов", "Приют для бездомных кошек работает с 2013 года. Сейчас там содержат 100 кошек. За время существования приюта 500 животных обрели новых хозяев. Животные после поступления в приют стерилизуются, получают прививки и проходят необходимое лечение.");
            infoRepository.save(info);
        }
        if (!infoList.contains("Как взять животное из приюта")) {
            Info info = new Info(2L, "Информация о приюте для собак", "Приют для бездомных собак – спасение для погибающих на улице животных. Очень важно, чтобы собакам было там хорошо. Для этого нужна большая работа волонтёров. Самое главное – найти собачке дом.");
            infoRepository.save(info);
        }
        if (!infoList.contains("Расписание работы приюта")) {
            Info info = new Info(3L, "Расписание работы приюта", "ежедневно, круглосуточно");
            infoRepository.save(info);
        }
        if (!infoList.contains("Адрес приюта")) {
            Info info = new Info(4L, "Адрес приюта", "г. Астана ул. Садовая д. 211\n" +
                    "Контакты:  тел. 08–922-60-99-774, e-mail: fond@helppetastana.ru ,\n");
            infoRepository.save(info);
        }
        if (!infoList.contains("Схема проезда")) {
            Info info = new Info(5L, "Схема проезда", "Схема проезда");
            infoRepository.save(info);
        }
        if (!infoList.contains("Контактные данные охраны для оформления пропуска на машину")) {
            Info info = new Info(6L, "Контактные данные охраны для оформления пропуска на машину", "Контактные данные охраны для оформления пропуска на машину");
            infoRepository.save(info);
        }
        if (!infoList.contains("Общие рекомендации о технике безопасности на территории приюта")) {
            Info info = new Info(7L, "Общие рекомендации о технике безопасности на территории приюта",
                    "Запрещается открывать и заходить в вольеры. Кормить животных. Оставлять после себя мусор. Пугать и дразнить животных. Посещение приюта для детей без взрослых. Посещение приюта в состоянии алкогольного, наркотического опьянения.");
            infoRepository.save(info);
        }
        if (!infoList.contains("Правила знакомства с животным до того, как забрать его из приюта")) {
            Info info = new Info(8L, "Правила знакомства с животным до того, как забрать его из приюта", "Правила знакомства с животным до того, как забрать его из приюта");
            infoRepository.save(info);
        }
        if (!infoList.contains("Список документов, необходимых для того, чтобы взять животное из приюта")) {
            Info info = new Info(9L, "Список документов, необходимых для того, чтобы взять животное из приюта", "Список документов, необходимых для того, чтобы взять животное из приюта");
            infoRepository.save(info);
        }
        if (!infoList.contains("Список рекомендаций по транспортировке животного")) {
            Info info = new Info(10L, "Список рекомендаций по транспортировке животного", "Список рекомендаций по транспортировке животного");
            infoRepository.save(info);
        }
        if (!infoList.contains("Список рекомендаций по обустройству дома для щенка")) {
            Info info = new Info(11L, "Список рекомендаций по обустройству дома для щенка", "Список рекомендаций по обустройству дома для щенка");
            infoRepository.save(info);
        }
        if (!infoList.contains("Список рекомендаций по обустройству дома для котенка")) {
            Info info = new Info(12L, "Список рекомендаций по обустройству дома для котенка", "Список рекомендаций по обустройству дома для котенка");
            infoRepository.save(info);
        }
        if (!infoList.contains("Список рекомендаций по обустройству дома для взрослого животного с ограниченными возможностями")) {
            Info info = new Info(13L, "Список рекомендаций по обустройству дома для взрослого животного с ограниченными возможностями", "Список рекомендаций по обустройству дома для взрослого животного с ограниченными возможностями");
            infoRepository.save(info);
        }
        if (!infoList.contains("Советы кинолога по первичному общению с собакой")) {
            Info info = new Info(14L, "Советы кинолога по первичному общению с собакой", "Советы кинолога по первичному общению с собакой");
            infoRepository.save(info);
        }
        if (!infoList.contains("Рекомендации по проверенным кинологам для дальнейшего обращения с собакой")) {
            Info info = new Info(15L, "Рекомендации по проверенным кинологам для дальнейшего обращения с собакой", "Рекомендации по проверенным кинологам для дальнейшего обращения с собакой");
            infoRepository.save(info);
        }
        if (!infoList.contains("Список причин, почему могут отказать и не дать забрать собаку из приюта")) {
            Info info = new Info(16L, "Список причин, почему могут отказать и не дать забрать собаку из приюта", "Список причин, почему могут отказать и не дать забрать собаку из приюта");
            infoRepository.save(info);
        }
        if (!infoList.contains("Форма ежедневного отчета")) {
            Info info = new Info(17L, "Форма ежедневного отчета", "Форма ежедневного отчета");
            infoRepository.save(info);
        }
        if (!infoList.contains("Поздравление с успешным прохождением испытательного срока")) {
            Info info = new Info(18L, "Поздравление с успешным прохождением испытательного срока", "Поздравление с успешным прохождением испытательного срока");
            infoRepository.save(info);
        }
        if (!infoList.contains("Назначено дополнительное время испытательного срока")) {
            Info info = new Info(19L, "Назначено дополнительное время испытательного срока", "Назначено дополнительное время испытательного срока");
            infoRepository.save(info);
        }
        if (!infoList.contains("Уведомление о том, что усыновитель не прошел испытательный срок")) {
            Info info = new Info(20L, "Уведомление о том, что усыновитель не прошел испытательный срок", "Уведомление о том, что усыновитель не прошел испытательный срок");
            infoRepository.save(info);
        }
    }

    /**
     * Method for edit standard info messages in DB. <br>
     * Used repository method {@link JpaRepository#findById(Object)} <br>
     * Also used repository method {@link JpaRepository#save(Object)}
     * <hr>
     * Метод для редактирования стандартных информационных сообщений в БД. <br>
     * Используется метод репозитория {@link JpaRepository#findById(Object)} <br>
     * Также использован метод репозитория {@link JpaRepository#save(Object)}
     * <hr>
     *
     * @param id
     * @param editedText
     * @return name of the edited item / название отредактированного пункта
     * @see JpaRepository#findById(Object)
     * @see JpaRepository#save(Object)
     */
    @Override
    public String editInfo(Long id, String editedText) {
        checkInfo();
        Info info = infoRepository.getById(id);
        info.setText(editedText);
        infoRepository.save(info);
        return info.getKey() + " изменен(а)";
    }

    /**
     * Method for getting collection of all standard messages. <br>
     * Used repository method {@link JpaRepository#findAll()}
     * <hr>
     * Метод для получения коллекции всех типовых сообщений. <br>
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * <hr>
     *
     * @return collection of all standard messages / коллекция всех типовых сообщений
     * @see JpaRepository#findAll()
     */
    @Override
    public Collection<Info> getAllInfo() {
        checkInfo();
        return infoRepository.findAll();
    }

    /**
     * Method for getting of standard messages from DB by Id. <br>
     * Used repository method {@link JpaRepository#findById(Object)}
     * <hr>
     * Метод для получения стандартного сообщения из БД по номеру Id. <br>
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * <hr>
     * 1L "Information about the shelter"/"Информация о приюте" <br>
     * 2L "How to take an animal from a shelter"/"Как взять животное из приюта" <br>
     * 3L "The shelter's work schedule"/"Расписание работы приюта" <br>
     * 4L "Address of the shelter"/"Адрес приюта" <br>
     * 5L "Driving directions"/"Схема проезда" <br>
     * 6L "Security contact details for registration of a car pass"/"Контактные данные охраны для оформления пропуска на машину" <br>
     * 7L "General safety recommendations on the territory of the shelter"/"Общие рекомендации о технике безопасности на территории приюта" <br>
     * 8L "Rules for getting to know an animal before picking it up from a shelter"/"Правила знакомства с животным до того, как забрать его из приюта" <br>
     * 9L "List of documents required to take an animal from a shelter"/"Список документов, необходимых для того, чтобы взять животное из приюта" <br>
     * 10L "List of recommendations for animal transportation"/"Список рекомендаций по транспортировке животного" <br>
     * 11L "List of recommendations for home improvement for a puppy"/"Список рекомендаций по обустройству дома для щенка" <br>
     * 12L "List of recommendations for home improvement for a kitten"/"Список рекомендаций по обустройству дома для котенка" <br>
     * 13L "List of recommendations for home improvement for an adult animal with disabilities"/"Список рекомендаций по обустройству дома для взрослого животного с ограниченными возможностями" <br>
     * 14L "Tips of a dog handler on primary communication with a dog"/"Советы кинолога по первичному общению с собакой" <br>
     * 15L "Recommendations for proven dog handlers for further handling of the dog"/"Рекомендации по проверенным кинологам для дальнейшего обращения с собакой" <br>
     * 16L "A list of reasons why they may refuse and not let you take the dog from the shelter"/"Список причин, почему могут отказать и не дать забрать собаку из приюта" <br>
     * 17L "Daily Report form"/"Форма ежедневного отчета" <br>
     * 18L "Congratulations on the successful completion of the probation period"/"Поздравление с успешным прохождением испытательного срока" <br>
     * 19L "An additional time of probation has been appointed"/"Назначено дополнительное время испытательного срока" <br>
     * 20L "Notification that the adoptive parent has not passed the probation period"/"Уведомление о том, что усыновитель не прошел испытательный срок" <br>
     *
     * @return standard message text / текст стандартного сообщения
     * @see JpaRepository#findById(Object)
     */
    @Override
    public String getInfoTextById(Long id) {
        return infoRepository.findById(id).get().getText();
    }
}
