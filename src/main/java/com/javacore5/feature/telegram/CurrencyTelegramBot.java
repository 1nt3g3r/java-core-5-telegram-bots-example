package com.javacore5.feature.telegram;

import com.javacore5.feature.currency.CurrencyService;
import com.javacore5.feature.currency.PrivatBankCurrencyService;
import com.javacore5.feature.fsm.StateMachine;
import com.javacore5.feature.fsm.StateMachineListener;
import com.javacore5.feature.telegram.command.StartCommand;
import com.javacore5.feature.ui.PrettyPrintCurrencyService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private Map<String, StateMachine> stateMachines;
    private ScheduledExecutorService scheduledExecutorService;

    public CurrencyTelegramBot() {
        register(new StartCommand());

        stateMachines = new ConcurrentHashMap<>();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            String chatId = Long.toString(update.getMessage().getChatId());

            if (!stateMachines.containsKey(chatId)) {
                StateMachine fsm = new StateMachine();

                fsm.addListener(new MessageListener(chatId));

                stateMachines.put(chatId, fsm);
            }

            String message = update.getMessage().getText();
            stateMachines.get(chatId).handle(message);
        }
    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }

    private class MessageListener implements StateMachineListener {
        private String chatId;

        public MessageListener(String chatId) {
            this.chatId = chatId;
        }

        @Override
        public void onSwitchedToWaitForMessage() {
            sendText("Напишите текст заметки");
        }

        @Override
        public void onSwitchedToWaitForTime() {
            sendText("Ок, принял. Через сколько секунд напомнить?");
        }

        @Override
        public void onMessageAndTimeReceived(String message, int time) {
            sendText("Заметка поставлена, секунд до срабатывания: " + time);

            scheduledExecutorService.schedule(
                    () -> sendText(message),
                    time,
                    TimeUnit.SECONDS
            );
        }

        private void sendText(String text) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(text);
            sendMessage.setChatId(chatId);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
