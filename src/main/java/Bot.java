import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml").build();
    Metadata metadata = new MetadataSources(registry)
            .getMetadataBuilder().build();
    SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    private Set<Long> listUsersId = new HashSet<>();
    private HashMap<String, User> listUsers = new HashMap<>();
    public InlineKeyboardButton sendForStartTest = InlineKeyboardButton.builder()
            .text("Send for start test")
            .callbackData("send for start test")
            .build();
    public InlineKeyboardButton thereIsNoDifference = InlineKeyboardButton.builder()
            .text("Нет разницы")
            .callbackData("нет разницы")
            .build();
    public InlineKeyboardButton typeUsingData = InlineKeyboardButton.builder()
            .text("Разница в типе используемых данных")
            .callbackData("разница в типе используемых данных")
            .build();
    public InlineKeyboardButton createCopy = InlineKeyboardButton.builder()
            .text("Разница в создании копий")
            .callbackData("разница в создании копий")
            .build();
    public InlineKeyboardButton classOrPrimitive = InlineKeyboardButton.builder()
            .text("Один класс, а другой примитив")
            .callbackData("один класс, а другой примитив")
            .build();
    private InlineKeyboardMarkup keyboardM1 = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(sendForStartTest))
            .build();
    private InlineKeyboardMarkup sendQuestionOne = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(thereIsNoDifference))
            .keyboardRow(List.of(typeUsingData))
            .keyboardRow(List.of(createCopy))
            .keyboardRow(List.of(classOrPrimitive))
            .build();
    //Вопрос
    public InlineKeyboardButton primitivesAndClass = InlineKeyboardButton.builder()
            .text("int, double, Character")
            .callbackData("int, double, Character")
            .build();
    public InlineKeyboardButton classes = InlineKeyboardButton.builder()
            .text("Boolean, Integer, Double")
            .callbackData("Boolean, Integer, Double")
            .build();
    public InlineKeyboardButton primitives = InlineKeyboardButton.builder()
            .text("short, byte, char")
            .callbackData("short, byte, char")
            .build();
    public InlineKeyboardButton primitivesAndClassObject = InlineKeyboardButton.builder()
            .text("boolean, float, long, Object")
            .callbackData("boolean, float, long, Object")
            .build();
    private InlineKeyboardMarkup sendQuestionTwo = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(primitivesAndClass))
            .keyboardRow(List.of(classes))
            .keyboardRow(List.of(primitives))
            .keyboardRow(List.of(primitivesAndClassObject))
            .build();
    //Вопрос
    public InlineKeyboardButton goodTheWork = InlineKeyboardButton.builder()
            .text("Удобны в работе")
            .callbackData("удобны в работе")
            .build();
    public InlineKeyboardButton accurateTheWork = InlineKeyboardButton.builder()
            .text("Работают более точно")
            .callbackData("работают более точно")
            .build();
    public InlineKeyboardButton allFormat = InlineKeyboardButton.builder()
            .text("Преобразовывают в любой формат")
            .callbackData("преобразовывают в любой формат")
            .build();
    private InlineKeyboardMarkup sendQuestionThree = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(goodTheWork))
            .keyboardRow(List.of(accurateTheWork))
            .keyboardRow(List.of(allFormat))
            .build();
    //Вопрос
    public InlineKeyboardButton expand = InlineKeyboardButton.builder()
            .text("Expand")
            .callbackData("expand")
            .build();
    public InlineKeyboardButton extend = InlineKeyboardButton.builder()
            .text("Extend")
            .callbackData("extend")
            .build();
    public InlineKeyboardButton extended = InlineKeyboardButton.builder()
            .text("Extended")
            .callbackData("extended")
            .build();
    public InlineKeyboardButton varExtends = InlineKeyboardButton.builder()
            .text("Extends")
            .callbackData("extends")
            .build();
    private InlineKeyboardMarkup sendQuestionFour = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(expand))
            .keyboardRow(List.of(extend))
            .keyboardRow(List.of(extended))
            .keyboardRow(List.of(varExtends))
            .build();


    @Override
    public String getBotUsername() {
        return "@interview_developers_vadim_bot";
    }

    @Override
    public String getBotToken() {
        return "7433649850:AAGXgSxUMwXCC5vz0Hf4m0mulHG_DGD9y4o";
    }

    @Override
    public void onUpdateReceived(Update update) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry)
                .getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        System.out.println("Update: " + update);
        //update.getMessage().getFrom().getFirstName();
        buttonTab(update);
        isCommand(update.getMessage());
    }

    public void isCommand(Message message) {
        String text = message.getText();
        if (text.equals("/start_test") && !listUsersId.contains(message.getFrom().getId())) {
            User currentUser = new User();
            currentUser.setCountBallov(0);
            currentUser.setId(message.getFrom().getId());
            currentUser.setFirstName(message.getFrom().getFirstName());
            currentUser.setUserName(message.getFrom().getUserName());
            listUsers.put(currentUser.getFirstName(), currentUser);

            System.out.println("User \"" + listUsers.get(currentUser.getFirstName()) + "\" added!");
            sendMenu(message.getFrom().getId(), "<b>Go to the start test</b>", keyboardM1);
        }
    }

    public void sendMenu(Long who, String txt, InlineKeyboardMarkup km) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .parseMode("HTML")
                .text(txt)
                .replyMarkup(km)
                .build();

        try {
            execute(sm);
        } catch (TelegramApiException tae) {
            throw new RuntimeException(tae);
        }
    }

    public void buttonTab(Update update) {
        if (update.hasCallbackQuery()) {
            String idUser = update.getCallbackQuery().getMessage().getChatId().toString();
            int idMessage = update.getCallbackQuery().getMessage().getMessageId();
            String data = update.getCallbackQuery().getData();
            String queryId = update.getCallbackQuery().getId();

            EditMessageText editMessageText = EditMessageText.builder()
                    .chatId(idUser)
                    .messageId(idMessage)
                    .text("")
                    .build();

            EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                    .chatId(idUser.toString())
                    .messageId(idMessage)
                    .build();


            User updateUser = listUsers.get("Vadim"); //listUsers.get(update.getMessage().getFrom().getFirstName());
            System.out.println("Get user: " + updateUser);


            if (data.equals("send for start test")) {
                editMessageText.setText("Какая разница между классами String и StringBuilder?");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionOne);
            } else if (data.equals("нет разницы")) {
                editMessageText.setText("Выберите только примитивы");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionTwo);
            } else if (data.equals("разница в типе используемых данных")) {
                editMessageText.setText("Выберите только примитивы");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionTwo);
            } else if (data.equals("разница в создании копий")) {
                //listUsers.get(update.getMessage().getFrom().getFirstName());
                updateUser.addBall();
                editMessageText.setText("Выберите только примитивы");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionTwo);
            } else if (data.equals("один класс, а другой примитив")) {
                editMessageText.setText("Выберите только примитивы");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionTwo);
            } else if (data.equals("int, double, Character")) {
                editMessageText.setText("Чем современные классы LocalDate и LocalDateTime лучше классического класса Date?\n");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionThree);
            } else if (data.equals("Boolean, Integer, Double")) {
                editMessageText.setText("Чем современные классы LocalDate и LocalDateTime лучше классического класса Date?\n");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionThree);
            } else if (data.equals("short, byte, char")) {
                updateUser.addBall();
                editMessageText.setText("Чем современные классы LocalDate и LocalDateTime лучше классического класса Date?\n");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionThree);
            } else if (data.equals("boolean, float, long, Object")) {
                editMessageText.setText("Чем современные классы LocalDate и LocalDateTime лучше классического класса Date?\n");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionThree);
            } else if (data.equals("удобны в работе")) {
                updateUser.addBall();
                editMessageText.setText("Каким ключевым словом объявляется, что класс наследует другой класс?");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionFour);
            } else if (data.equals("работают более точно")) {
                editMessageText.setText("Каким ключевым словом объявляется, что класс наследует другой класс?");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionFour);
            } else if (data.equals("преобразовывают в любой формат")) {
                editMessageText.setText("Каким ключевым словом объявляется, что класс наследует другой класс?");
                editMessageReplyMarkup.setReplyMarkup(sendQuestionFour);
            } else if (data.equals("expand")) {
                editMessageText.setText(getPercent(updateUser.getCountBallov()));
            } else if (data.equals("extend")) {
                editMessageText.setText(getPercent(updateUser.getCountBallov()));
            } else if (data.equals("extended")) {
                editMessageText.setText(getPercent(updateUser.getCountBallov()));
            } else if (data.equals("extends")) {
                updateUser.addBall();
                editMessageText.setText(getPercent(updateUser.getCountBallov()));
            }

            AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder()
                    .callbackQueryId(queryId)
                    .build();

            try {
                execute(answerCallbackQuery);
                execute(editMessageText);
                execute(editMessageReplyMarkup);
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
    }

    private String getPercent(int countBallov) {
        User user = listUsers.get("Vadim"); //listUsers.get(update.getMessage().getFrom().getFirstName());
        int countQuestions = 4;
        int percent = (countBallov * 100) / countQuestions;
        String response = user.getFirstName() + ", Вы прошли тест и правильно выполнили " + percent + "% заданий!";
        if (percent >= 70) {
            System.out.println("Пользователь \"" + user.getFirstName() + "\" прошёл тест на " + percent + "%.");
            user.setCountBallov(percent);
            session.save(user);

                    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> courseCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = courseCriteriaQuery.from(User.class);
        courseCriteriaQuery.select(root);
        List<User> purchasesList = session.createQuery(courseCriteriaQuery).getResultList();

        for (User currentUser:purchasesList) {
            System.out.println("Текущий: " + currentUser);
        }

//            User updateUser = session.get(User.class, user.getId());
//            System.out.println("Update user \"" + updateUser + "\" added an Database");
            session.flush();
            session.clear();
            transaction.commit();
            sessionFactory.close();
            listUsersId.add(user.getId());

            return response + ".\nТеперь Вы можете связаться с заказчиком\nпо его нику в телеграмме - @sotnikov_vadim";
        }
        System.out.println("Пользователь \"" + user.getFirstName() + "\" прошёл тест на " + percent + "%.");
        return response;
    }
}
