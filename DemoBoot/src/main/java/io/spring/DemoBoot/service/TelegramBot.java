package io.spring.DemoBoot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import io.spring.DemoBoot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

	// TelegramLongPollingBot - уведомлений нет, бот сам периодически проверяет написал ли пользователь сообщение
	// есть еще WebhookBot - уведомляет бота о том что пользователь написал сообщение, более эффективен когда много пользователей
	
	final BotConfig config;
	
	public TelegramBot(BotConfig config) {
		this.config = config;
	}

	//предоставляет имя бота
	@Override
	public String getBotUsername() {
		
		return config.getBotName();
	}
	
	//предоставляет API ключ бота
	@Override
	public String getBotToken() {
		return config.getBotToken();
	}
	

	//что должен делать бот, когда ему кто-то пишет
	@Override
	public void onUpdateReceived(Update update) {
		//Update это класс, который содержит сообщение пользователя и многое другое
		
		//если пользователь прислал сообщение и в нем есть текст
		if(update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();
			long chatId = update.getMessage().getChatId();
			
			switch(messageText) {
			
			case "/start":
				startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
				break;
				
			default: sendMessage(chatId, "Sorry, command was not recognized");
									
			}
		}
		
	}
	
	private void startCommandReceived(long chatId, String name) {
		String answer = "Hi " + name + ", nice to meet you!";
		log.info("Replied to user " + name);
		
		sendMessage(chatId, answer);
	}
	
	private void sendMessage(long chatId, String textToSend) {
		SendMessage message = new SendMessage();
		message.setChatId(String.valueOf(chatId));
		message.setText(textToSend);
		
		try {
			execute(message);
		} catch (TelegramApiException e) {
			log.error("Error occurred: " + e.getMessage());
		}
		
	}
	
}
