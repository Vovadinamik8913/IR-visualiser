State.getCurrent()

1) Смотрим [[Folder Structure#current_module]]. 
2) Создаём dot и svg, если таковых нет
3) return Parser.parse, передавая весь текст .ll и итератор над всеми текстами dot;

# Parser

```java
CurrentState parse(String irText, Iterator<String> dotTexts)
```
1) irText -> ModuleIr
```java
class ModuleIr {
	// Function name to function text
	Map<String, String> functions;
}
```
2) 
```java
for (String dotText : dotTexts) {
	String name = functionNameFromDot(dotText);
	Function function = new Function(moduleir.functions.get(name), dotText);
	//...
}
```

```java
class Module {
	HashMap<String, Function> functions;
	String currentFunction;
}

class Function {
	int line;
	int lineFromBlockId(String);
	String blockIdFromLine(int);
}
```
