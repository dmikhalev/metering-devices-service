from telegram.ext import Updater, CommandHandler

from geocoder import get_my_news, get_last_check, get_ll_span
from telegram.ext import Updater, CommandHandler, MessageHandler, Filters
import base64
import requests
import json


def start(update, context):
    update.message.reply_text("Я бот-геокодер. Ищу объекты на карте.")


def geocoder(update, context):
    try:
        ll, spn = get_ll_span(update.message.text)
        if ll and spn:
            point = "{ll},pm2vvl".format(ll=ll)
            static_api_request = "http://static-maps.yandex.ru/1.x/?ll={ll}&spn={spn}&l=map&pt={point}".format(
                **locals())
            context.bot.sendPhoto(update.message.chat.id, static_api_request, caption=update.message.text)
        else:
            update.message.reply_text("По запросу ничего не найдено.")
    except RuntimeError as ex:
        update.message.reply_text(str(ex))


def get_news(update, context):
    try:
        news = get_my_news()
        if news:
            for i in news:
                update.message.reply_text(i)
        else:
            update.message.reply_text("По запросу ничего не найдено.")

    except RuntimeError as ex:
        update.message.reply_text(str(ex))

def get_checs(update, context):
    try:
        checks = get_last_check("sveta")
        if checks:
            for numb, i in enumerate(checks):

                check = open("Check" + str(numb) + '.pdf', "wb")
                check.write(base64.b64decode(i))
                check.close()
                context.bot.send_document(update.message.chat.id, open("Check" + str(numb) + '.pdf', "rb"))
                #context.bot.sendPhoto(update.message.chat.id, i)
        else:
            update.message.reply_text("По запросу ничего не найдено.")

    except RuntimeError as ex:
        update.message.reply_text(str(ex))

def main():
    TOKEN = '1785776928:AAH-W__TNnXCI6klyqjFCskuMH-EMOfZ_vQ'
    updater = Updater(TOKEN, use_context=True)
    dp = updater.dispatcher
    dp.add_handler(CommandHandler("start", start))
    #dp.add_handler(MessageHandler(Filters.text, geocoder))
    dp.add_handler(CommandHandler("news", get_news))
    dp.add_handler(CommandHandler("checks", get_checs))

    updater.start_polling()
    updater.idle()


if __name__ == '__main__':
    main()
