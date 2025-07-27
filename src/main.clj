(ns _ (:require
       ["./vendor/chat_ui/chat_ui" :as ui]
       ["./vendor/effects_tools/date" :as date]
       ["./vendor/effects/effects" :as e]
       ["./vendor/random/index" :as rand]))

(def- WORDS
  [["Zdravo" "здра́во" "Привет"]
   ["Hvala" "хва́ла" "Спасибо"]
   ["Molim" "мо́лим" "Пожалуйста"]
   ["Da" "да" "Да"]
   ["Ne" "не" "Нет"]
   ["Ljubav" "лю́бав" "Любовь"]
   ["Prijatelj" "при́ятель" "Друг"]
   ["Porodica" "по́родица" "Семья"]
   ["Hrana" "хра́на" "Еда"]
   ["Voda" "во́да" "Вода"]
   ["Ku a" "ку́ча" "Дом"]
   ["Grad" "град" "Город"]
   ["kola" "шко́ла" "Школа"]
   ["Posao" "по́сао" "Работа"]
   ["Novac" "но́вац" "Деньги"]
   ["Auto" "а́уто" "Машина"]
   ["Telefon" "телеfóн" "Телефон"]
   ["Sunce" "су́нце" "Солнце"]
   ["More" "мо́рэ" "Море"]
   ["срећа" "срэ́ча" "Счастье"]
   ["Добро јутро" "добро ю́тро" "Доброе утро"]
   ["Добар дан" "до́бар дан" "Добрый день"]
   ["Добро вече" "до́бро вэ́чэ" "Добрый вечер"]
   ["Лаку ноћ" "ла́ку но́ч" "Спокойной ночи"]
   ["Хвала" "хва́ла" "Спасибо"]
   ["Молим" "мо́лим" "Пожалуйста / Не за что"]
   ["Извините" "изви́ните" "Извините"]
   ["Где је тоалет?" "гдэ́ йе тоале́т" "Где туалет?"]
   ["Колико кошта?" "ко́лико ко́шта" "Сколько стоит?"]
   ["Имам питање" "и́мам пи́танье" "У меня вопрос"]
   ["Немам појма" "не́мам по́йма" "Понятия не имею"]
   ["Шта радиш?" "шта́ ра́диш" "Что ты делаешь?"]
   ["Где си?" "гдэ́ си" "Где ты?"]
   ["Видимо се" "ви́димо сэ" "Увидимся"]
   ["Срећан пут" "срэ́чян пут" "Счастливого пути"]
   ["Добро сам" "до́бро сам" "Я в порядке"]
   ["Помоћ!" "по́моч" "Помогите!"]
   ["У реду" "у рэ́ду" "Хорошо / Ладно"]
   ["Не разумем" "нэ разу́мем" "Я не понимаю"]
   ["Говорите ли руски?" "говори́тэ ли ру́ски?" "Вы говорите по-русски?"]])

(defn- start []
  (e/then
   (date/get_unixtime)
   (fn [now]
     (ui/replace
      (let [words2 (shuffle (rand/next now) WORDS)
            [[srb_word trasnc]] words2
            words3 (shuffle (rand/next now) (take 3 words2))]
        [:column {}
         [:label {:text (str srb_word " (" trasnc ")")}]
         [:button {:title (get (get words3 0) 2) :onclick (start)}]
         [:button {:title (get (get words3 1) 2) :onclick (start)}]
         [:button {:title (get (get words3 2) 2) :onclick (start)}]])))))

(defn home []
  (ui/replace
   [:button {:title "Reset"
             :onclick (start)}]))

(defn main [event payload]
  (let [fx (home)
        w_atom (:state payload)]
    (swap! w_atom (fn [w] (date/effect_handler w)))
    (fx (deref w_atom))))
