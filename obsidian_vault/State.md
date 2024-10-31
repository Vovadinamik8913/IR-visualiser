```java
void setWorkspace(String path)
```
Задаёт папку, с которой будет работать. [[Folder Structure]]
Инициализирует пустой workspace, если в папке нет нужных файлов
Может `throws`, если папка не пустая и не workspace, а чтобы такую использовать, будет функция `forceSetWorkspace`

```java
void addOpt(String name, String path)
```
Добавляет опт с именем

```java
void addModule(String name, String moduleText, String optName)
```
Добавляет модуль

```java
CurrentState getCurrent()
```
Создаёт [[CurrentState]] из текущего модуля, имя которого находится в [[Folder Structure#current_module]]
Если их ещё нет, то создаёт dot и svg файлы
По возможности кэширует CurrentState

```java
void setCurrentFunction(String name)
```
Изменяет функцию, с которой производится работа пользователем. Svg этой функции покказыается пользователю




