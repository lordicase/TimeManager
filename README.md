# TimeManager

Jest to aplikacja mobilna do zarządzania czasem przeznaczonym na poszczególne projekty którymi zajmuje się użytkownik. Aplikacja umożliwia dodawanie, edycje oraz usuwanie projektów, zadań z nimi powiązanymi, oraz śledzenie statystyk dotyczących czasu przeznaczonego na poszczególne projekty w ciągu dnia, tygodnia oraz roku. Dane użytkownika mają być przechowywane  na urządzeniu użytkownika  oraz w zdalnej bazie danych. 

![image](https://user-images.githubusercontent.com/56389485/212644185-0549d2ab-1be4-48c0-9ccd-398d27e5ebc0.png)

Aby korzystać z aplikacji każdy użytkownik musi założyć indywidualne konto. Umożliwia to panel rejestracji dostępny tuż po uruchomieniu aplikacji. W polach wystarczy wpisać login oraz hasło. Po użyciu przycisku register urządzenie przy pomocy biblioteki Retrofit wyśle na serwer lokalny zapytanie w celu sprawdzenia czy nie występuje już użytkownik o podanym loginie. Jeśli wszystko zostało wykonane poprawnie w bazie zostanie utworzone nowe konto a użytkownik zostanie przekierowany do ekranu logowania.  


![image](https://user-images.githubusercontent.com/56389485/212644250-c562a497-bd71-4dc9-a581-24ccd5461278.png)

Ekran logowania umożliwia zalogowanie się w aplikacji, jeśli użytkownik nie posiada jeszcze konta w lewym dolnym rogu znajduje się przycisk przekierowujący do panelu rejestracji. CheckBox Remember me pozwala na zapamiętania użytkownika za pomocą obiektów SharedPreferences, pozwala to na szybsze logowanie użytkownika w przyszłości ponieważ nie musi on wpisywać ponownie swoich danych.
 
![image](https://user-images.githubusercontent.com/56389485/212644377-ef08d8b0-e6ce-4a52-b567-dbe9b99ab770.png)

Po zalogowaniu w aplikacji użytkownik zostaje przekierowany do ekranu projektów. Znajduje się w nim lista wszystkich obecnie utworzonych projektów, w zależności od ustawień mogą być wyświetlane wszystkie projekty lub tylko te które zostały przypisane do danego dnia tygodnia. Projekty wyświetlane są w formie listy zawierającej tytuł, przycisk start/stop czasu oraz wskaźnik dziennego czasu przypisanego do projektu oraz ile czasu użytkownik poświęcił dziś na dany projekt. Poza wskaźnikiem czasowym poniżej znajduje się również kolorowy pasek informujący o dziennym postępie. W przypadku zrealizowania dziennych założeń przycisk start/stop zostaje dezaktywowany. Każdego dnia o 24 czas zostaje zresetowany. Aby usunąć projekt wystarczy przesunąć go na liście w lewo, edycja dostępna jest po naciśnięciu projektu na liście natomiast aby utworzyć kolejny projekt należy użyć przycisku znajdującego się w prawym dolnym rogu.
 

![image](https://user-images.githubusercontent.com/56389485/212644412-c3a3cdfb-83db-49cc-bb71-4ed2fa0ce3b4.png)

Aby dodać nowy projekt użytkownik musi podać tytuł projektu. Następnie może wybrać kolor który będzie do niego przypisany, dni tygodnia w których będzie wyświetlany projekt oraz ile w Ciągu dnia chcę poświęcić na niego czasu. Po ustawieniu wszystkich parametrów należy użyć ikony zapisu która znajduje się w prawym górnym rogu. W ten sam sposób działa ekran edycji z tą różnicą że wczytywane są dane konkretnego projektu.
 


![image](https://user-images.githubusercontent.com/56389485/212644475-34b53c06-af35-4eb1-ae9d-8e8d7ebde1ba.png) ![image](https://user-images.githubusercontent.com/56389485/212644494-2b4277c4-ae3b-4b90-9d78-64dc4f8189c0.png)

Po użyciu dolnego menu użytkownik może przejść do zakładki z zadaniami, w przypadku gdy użytkownik znajduje się w ekranie z listą wszystkich projektów, zostanie on przeniesiony do listy z wszystkimi zadaniami, natomiast jeśli znajduje się w panelu edycji projektu zostaną mu wyświetlone tylko zadania przypisane do konkretnego projektu. Podobinie jak w przypadku projektów alby dodać nowe zadanie należy użyć przycisku +. Zostanie otworzone okno w którym należy wpisać zadanie. Lista zadań może wyświetlać tylko nie zrobione zadania lub też wszystkie zadania, podobnie jak w przypadku projektów edycja zadania dostępna jest po wybraniu go z listy natomiast usunięcie odbywa się poprzez przesunięcie go w lewą stronę.


![image](https://user-images.githubusercontent.com/56389485/212644532-0ee7563e-6059-4cc9-8dc7-876735d2e87f.png)

Po użyciu ostatniej zakładki z dolnego menu użytkownik zostanie przeniesiony do statystyk w których może sprawdzić ile czasu poświęcał na poszczególne projekty w ramach określonych ram czasowych (dzień, tydzień, miesiąc oraz rok).
 
Aplikacja została stworzona w oparciu o strukturę MVVM (Model, View, ViewModel) jest to jeden z wzorców architektonicznych zdefiniowanych w celu pisania czystego kodu, w którym istnieje odpowiednie rozdzielenie między logiką aplikacji a warstwą prezentacji. Zasadniczo dane przechowywane są na urządzeniu z wykorzystaniem biblioteki Room oraz w bazię internetowej z użyciem biblioteki Retrofit. Kolejną warstwa jest repozytorium, warstwa ta jest odpowiedzialna za kierowaniem strumieniem danych. Warstwa ViewModel odpowiedzialna jest za logikę aplikacji i przekazywanie danych do Interfejsu przy użyciu obiektów LIveData. Użycie obiektów LiveData pozwala na aktualizowanie interfejsu na bieżąco po wykryciu każdej zmiany w bazie danych. Dodatkowo pozwala to na aktualizowanie wyłącznie tej części interfejsu która uległa zmianie
![image](https://user-images.githubusercontent.com/56389485/212644636-a0b78fc5-01b7-4409-b892-5c94e12e0adf.png)
