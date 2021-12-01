## Простая программа для предпросмотра 3D моделей

### _Первоочередные задачи:_ (на 25)

1. В программе в качестве библиотеки для работы с линейной алгеброй используется javax.vecmath. А
вам нужно использовать свою, либо труд коллеги, если вы не реализовывали алгебраические операции
на третьей задаче. Кто работает группой (об этом позже), выбирает лучшую реализацию среди
участников. (Кто делает?)


2. Вместо сырого ObjReader должна быть модная, протестированная реализация вас или другого
   студента, если вы ее не делали. (Кто делает? **Артем**)


3. В программе должна быть возможность сохранения модели также, по кнопке в меню. Использоваться
   должен либо ваш ObjWriter, либо реализация другого студента, если вы ее не делали. (Кто делает? **Артем**)


4. На данный момент во всех матричных преобразованиях в коде предполагается, что мы работаем с
   векторами-строками. Необходимо переделать методы так, чтобы векторы были столбцами. Кто делает?)

### _Что дальше?_ (на 35)

1. В программе реализована только часть графического конвейера. Нет перегонки из локальных координат в
   мировые координаты сцены. С этим можно жить, пока модель в программе одна. Теперь я прошу вас добавить в
   верхнее меню, рядом с настройками камеры, настройку модели. Хочу иметь возможность масштабировать ее
   вдоль каждой из осей, вокруг каждой из осей поворачивать и перемещать. При сохранении модели я могу
   выбрать, учитывать мне эти деформации или нет. То есть могу сохранить как исходную модель, так и модель
   после таких трансформаций. (Кто делает?)


2. Тестировать графические вещи достаточно сложно. Можно месяцами крутить модельку и не понимать: баг это
   или дурацкое управление. А баги в коде, что я вам даю, могут быть. И могут быть там, где вы будете
   дописывать новые алгоритмы. Поэтому что? Тесты! (Кто делает?)


3. Обработка ошибок. Пока это касается только ObjReader, но вы можете придумать что-нибудь еще. Модуль
   выплевывает exception’ы, но они пока никак не используются. Надо выводить окошко с ошибкой вроде “у вас
   тут дрянь какая-то”, чтобы пользователь мог обдумать свое поведение, щелкнуть “ок”, а затем долго и
   безрадостно всматриваться в мышь в углу комнаты, грызущую последний сухарь, оставшийся после ухода
   жены тем дождливым осенним утром. (Кто делает?)

### _Дополнительно_ (нв 50)

1. Сейчас в программе может быть только одна модель, но почему бы не добавить несколько? При этом
   нужно оставить возможность перемещать, вращать и как-то изменять только одну из них. Продумайте,
   как можно пользователю выбирать, какая/какие (множественный вариант сложнее в реализации, но
   интереснее) из моделей сейчас активные. (Выбраны для применения каких-то трансформаций,
   сохранения и т.д.)


2. На первую аттестацию некоторые из вас реализовывали удаление полигонов/вершин из модели. Теперь
   это можно сделать визуально интереснее. После загрузки модели в отдельном окошке отображается
   список вершин или полигонов (зависит от того, что вы реализуете). Можно выбрать некоторые из них и
   сделать “неактивными”. То есть они не будут отображаться, либо нарисуются каким-то другим цветом.
   При сохранении модели можно указать, сохранять выбранные вершины/полигоны или удалить их. Это
   только идея, как сделать это так, чтобы пользователю было удобно работать, продумайте сами.


3. В программе сейчас неудобное управление камерой. Улучшите его. Можно взять подход из какойнибудь программы для работы с графикой (ZBrush, Maya, R3DS Wrap) или из компьютерной игры.


4. Сделайте что-нибудь с оформление программы. Белое окошко с белым холстом, на котором рисуются
   черные линии - это, конечно, хорошо, но когда мы добавим текстуру, освещение, gui может начать
   раздражать. Если прикрутите что-то вроде css стилей, вообще расцелую.


5. Больше совет, чем требование. Используйте гит. Особенно при работе в команде.

